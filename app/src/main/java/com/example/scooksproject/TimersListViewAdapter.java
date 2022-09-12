package com.example.scooksproject;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

public class TimersListViewAdapter extends ArrayAdapter<SingleTimer> {

    private List<SingleTimer> items;
    private Context context;
    private Resources resources;
    private Integer[] colors;
    private static int isFirstProgressBar = 0;

    public TimersListViewAdapter(@NonNull Context context, @NonNull List<SingleTimer> items) {
        super(context, R.layout.timer_list_item, items);
        this.items = items;
        this.context = context;
        resources = context.getResources();
        colors = new Integer[]{
                resources.getColor(R.color.pink),
                resources.getColor(R.color.purple_200),
                resources.getColor(R.color.greenish),
                resources.getColor(R.color.green)};
        isFirstProgressBar++;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = position == 0 ? layoutInflater.inflate(R.layout.timer_list_item, parent, false)
                    : layoutInflater.inflate(R.layout.timer_list_item_without_pb, parent, false);
            CardView cardView = convertView.findViewById(R.id.timerCardView);
            ConstraintLayout constraintLayout = convertView.findViewById(R.id.timerContraintLayout);
            TextView minutes;
            TextView hours;

            if (position == 0) {
                constraintLayout.setBackgroundColor(context.getResources().getColor(R.color.white));
                cardView.setRadius(0);
                minutes = convertView.findViewById(R.id.notificationTimerMinutesInPB);
                hours = convertView.findViewById(R.id.notificationTimerHoursInPB);
                convertView.findViewById(R.id.notificationTimerDoubleDots).setVisibility(View.INVISIBLE);
            } else {
                constraintLayout.setBackgroundColor(colors[(position - 1) % colors.length]);
                minutes = convertView.findViewById(R.id.notificationTimerMinutes);
                hours = convertView.findViewById(R.id.notificationTimerHours);
                convertView.findViewById(R.id.notificationTimerDoubleDotsInPB).setVisibility(View.INVISIBLE);

                View firstPositionView = layoutInflater.inflate(R.layout.timer_list_item, parent, false);
                constraintLayout.setOnClickListener(item -> {
                    TextView minutesFView = firstPositionView.findViewById(R.id.notificationTimerMinutes);
                    TextView hoursFView = firstPositionView.findViewById(R.id.notificationTimerHours);
                    firstPositionView.findViewById(R.id.notificationTimerDoubleDotsInPB).setVisibility(View.INVISIBLE);
                    setTimer(firstPositionView, position, minutesFView, hoursFView);
                });
            }
            setTimer(convertView, position, minutes, hours);
//            SwitchCompat aSwitch = convertView.findViewById(R.id.notificationSwitch);
//
//            int minutesInt = items.get(position).getMinutes();
//            int hoursInt = items.get(position).getHours();
//            int minutesToCountdown = items.get(position).getMinutesForCountdown();
//
//
//            minutes.setText(Integer.toString(minutesInt));
//            hours.setText(Integer.toString(hoursInt));
//
//            aSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
//                if (!isChecked)
//                    StorageManager.cancelNotification(Integer.parseInt(items.get(position).getIdByStep()));
//                else
//                    StorageManager.unCancelNotification(Integer.parseInt(items.get(position).getIdByStep()));
//            });
//
//            if (!StorageManager.isNotificationCanceled(items.get(position).getIdByStep())) {
//                aSwitch.setChecked(true);
//            } else aSwitch.setChecked(false);
//
//            startTimer(minutesToCountdown, convertView, position);
        }
        return convertView;
    }

    private void setTimer(View convertView, int position, TextView minutes, TextView hours){
        SwitchCompat aSwitch = convertView.findViewById(R.id.notificationSwitch);

        SingleTimer currentTimer = items.get(position);
        
        int minutesInt = currentTimer.getMinutes();
        String minutesString = minutesInt < 10 ? "0" + minutesInt : String.valueOf(minutesInt);
        int hoursInt = currentTimer.getHours() + (currentTimer.getMinutesForCountdown() + currentTimer.getMinuteOfTeDayStarted()) / 60;
        int minutesToCountdown = currentTimer.getMinutesForCountdown();


        if (position == 0) {
            minutes.setText(minutesString);
        } else {
            minutes.setText(minutesString);
        }
        hours.setText(Integer.toString(hoursInt));

        aSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked)
                StorageManager.cancelNotification(Integer.parseInt(items.get(position).getIdByStep()));
            else
                StorageManager.unCancelNotification(Integer.parseInt(items.get(position).getIdByStep()));
        });

        if (!StorageManager.isNotificationCanceled(items.get(position).getIdByStep())) {
            aSwitch.setChecked(true);
        } else aSwitch.setChecked(false);

//        if(isFirstProgressBar == 1) {
            startTimer(minutesToCountdown, convertView, position);
//            isFirstProgressBar++;
//        }
    }

    private void startTimer(final int minuti, View view, int position) {
        ProgressBar barTimer = (ProgressBar) view.findViewById(R.id.barTimer);
        if (position == 0) {
            TextView textTimer = view.findViewById(R.id.timerTextView);

            int maxInSeconds = (int) minuti * 60;
            int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            int currentMinutes = Calendar.getInstance().get(Calendar.MINUTE);
            SingleTimer currentTimer = items.get(position);

            barTimer.setMax(maxInSeconds);
            long currentProgress = (maxInSeconds - ((currentHour * 3600 + currentMinutes * 60) - (currentTimer.getHourOfTheDayStarted() * 3600L + currentTimer.getMinuteOfTeDayStarted() * 60L)));

            CountDownTimer countDownTimer = new CountDownTimer(currentProgress * 1000, 1000) {
                // 500 means, onTick function will be called at every 500 milliseconds

                @Override
                public void onTick(long leftTimeInMilliseconds) {

                    long seconds = leftTimeInMilliseconds / 1000;
                    barTimer.setProgress((int) seconds);
                    textTimer.setText(String.format("%02d", seconds / 60) + ":" + String.format("%02d", (seconds % 60)));
                    // format the textview to show the easily readable format

                }

                @Override
                public void onFinish() {
                    if (textTimer.getText().equals("00:00")) {
                        textTimer.setText("STOP");
                    } else {
                        textTimer.setText("2:00");
                        barTimer.setProgress(60 * minuti);
                    }
                }
            }.start();
        } else {
            barTimer.setVisibility(View.INVISIBLE);
        }
    }
}
