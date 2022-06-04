package com.example.scooksproject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;

public class TimersListViewAdapter extends ArrayAdapter<SingleTimer> {

    private List<SingleTimer> items;
    private Context context;

    public TimersListViewAdapter(@NonNull Context context, @NonNull List<SingleTimer> items) {
        super(context, R.layout.timer_list_item, items);
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.timer_list_item, parent, false);

            TextView minutes = convertView.findViewById(R.id.notificationTimerMinutes);
            TextView hours = convertView.findViewById(R.id.notificationTimerHours);
            SwitchCompat aSwitch = convertView.findViewById(R.id.notificationSwitch);


            minutes.setText(Integer.toString(items.get(position).getMinutes()));
            hours.setText(Integer.toString(items.get(position).getHours()));

            aSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if(!isChecked)
                    StorageManager.cancelNotification(Integer.parseInt(items.get(position).getIdByStep()));
                else
                    StorageManager.unCancelNotification(Integer.parseInt(items.get(position).getIdByStep()));
            });

            if(!StorageManager.isNotificationCanceled(items.get(position).getIdByStep())){
                aSwitch.setChecked(true);
            }
            else aSwitch.setChecked(false);
        }
        return convertView;
    }
}
