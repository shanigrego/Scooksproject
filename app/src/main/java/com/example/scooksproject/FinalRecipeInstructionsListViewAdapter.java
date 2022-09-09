package com.example.scooksproject;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;

public class FinalRecipeInstructionsListViewAdapter extends ArrayAdapter<Instruction> {

    private Context context;
    private List<Instruction> items;
    private TextView instructionText;


    public FinalRecipeInstructionsListViewAdapter(@NonNull Context context, List<Instruction> instructions) {
        super(context, R.layout.single_final_recipe_instruction_item, instructions);
        this.context = context;
        this.items = instructions;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.single_final_recipe_instruction_item, null);
            FloatingActionButton timerIcon = convertView.findViewById(R.id.finalTimerIcon);
            initComponents(convertView, position, timerIcon);
        }
        return convertView;
    }

    private void initComponents(View view, int position, FloatingActionButton timerIcon){
        Instruction currentInstruction = items.get(position);

        instructionText = view.findViewById(R.id.singleFinalRecipeInstructionTV);
        instructionText.setText(items.get(position).getContent());
        instructionText.setMaxLines(100);
        instructionText.setVerticalScrollBarEnabled(true);
        instructionText.setMovementMethod(new ScrollingMovementMethod());

        View.OnTouchListener listener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                boolean isLarger;

                isLarger = ((TextView) v).getLineCount()
                        * ((TextView) v).getLineHeight() > v.getHeight();
                if (event.getAction() == MotionEvent.ACTION_MOVE
                        && isLarger) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);

                } else {
                    v.getParent().requestDisallowInterceptTouchEvent(false);

                }
                return false;
            }
        };
        instructionText.setOnTouchListener(listener);

        // Timer Icon initialization
        if (currentInstruction.getFreeTime() == 0) {
            timerIcon.setVisibility(View.INVISIBLE);
        }
        else
            timerIcon.setOnClickListener(icon -> showAlarmPicker(position));

    }


    public void showAlarmPicker(int position) {
        System.out.println("time picker invoked");
        int alarmours = items.get(position).getFreeTime() / 60;
        int alarmMinutes = items.get(position).getFreeTime() % 60;
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                getContext(),
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        SimpleDateFormat f24Hours = new SimpleDateFormat("HH:mm");
                        SimpleDateFormat f12Hours = new SimpleDateFormat("HH:mm");
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(0, 0, 0, hourOfDay, minute);

                        scheduleAlarm(hourOfDay, minute, position);
//                        scheduleAlarm(0, 0, position);
                    }
                }, 12, 0, true);
        timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        timePickerDialog.updateTime(alarmours, alarmMinutes);
        timePickerDialog.show();
    }

    private void scheduleAlarm(int hours, int minutes, int position){
        String alarmAction = "recipe name";
        PendingIntent pendingIntent = createPendingIntent(alarmAction,position);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        long timeInMillis = convertTimeToMillis(hours, minutes);
//        long timeInMillis = System.currentTimeMillis() + 10000;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);
        }

        // Create a new timer
        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int currentMinutes = Calendar.getInstance().get(Calendar.MINUTE);

        int timerHour = currentHour + hours;
        int timerMinutes = currentMinutes + minutes;
        int minutesToCountdown = minutes + hours * 60;

        SingleTimer singleTimer = new SingleTimer(timerHour, timerMinutes % 60, Integer.toString(position), minutesToCountdown);
        TimersFragment.addTimer(singleTimer);

    }

    private long convertTimeToMillis(int hours, int minutes){
//        return(10000);
        return (System.currentTimeMillis() + TimeUnit.HOURS.toMillis(hours) + TimeUnit.MINUTES.toMillis(minutes));
    }

    private PendingIntent createPendingIntent(String action, int stepNum) {
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        intent.putExtra("NotificationID", Integer.toString(stepNum));
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_ONE_SHOT);
    }

}
