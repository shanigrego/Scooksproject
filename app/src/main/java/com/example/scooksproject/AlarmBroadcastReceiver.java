package com.example.scooksproject;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Icon;
import android.icu.text.SimpleDateFormat;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.TimePicker;

import java.io.IOException;
import java.util.Calendar;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.graphics.drawable.IconCompat;

public class AlarmBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

//        Uri sound = Uri.parse(String.valueOf(context.getResources().getIdentifier("FILENAME_WITHOUT_EXTENSION",
//                "raw", context.getPackageName())));
        String canceledStepID = intent.getStringExtra("NotificationID");
        String uriPath = ContentResolver.SCHEME_ANDROID_RESOURCE
                + "://" + context.getPackageName() + "/raw/alarm_sample2.mp3";
        Uri alarmSound = Uri.parse(uriPath);
        System.out.println(uriPath);
        System.out.println("notification called");
        Log.i("notification sound", "sound");
        final Intent emptyIntent = new Intent();
        createNotificationChannel(context, alarmSound);
        NotificationCompat.Builder notification = new NotificationCompat.Builder(
                 context,
                 context.getString(R.string.channel_name))
                .setSmallIcon(android.R.mipmap.sym_def_app_icon)
                .setContentTitle("התראת Scook")
                .setContentText("נא להוציא מהתנור!");
//                .setSound(alarmSound);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification.setChannelId("com.scook.alarmNotificationChannel");
        }
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if(!StorageManager.isNotificationCanceled(canceledStepID)) {
            notificationManager.notify(1, notification.build());
        }

        TimersFragment.removeTimerByStepId(canceledStepID);
//        MediaPlayer mMediaPlayer = MediaPlayer.create(context, R.raw.alarm_sample);
//        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//        mMediaPlayer.setLooping(true);
//        mMediaPlayer.start();
        System.out.println("notification called");
    }

    private void createNotificationChannel(Context context, Uri sound) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.channel_name);
            String description = context.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("com.scook.alarmNotificationChannel", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
