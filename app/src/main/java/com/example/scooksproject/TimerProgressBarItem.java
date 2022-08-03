package com.example.scooksproject;

import android.app.Application;
import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TimerProgressBarItem {

    private void startTimer(final int minuti, Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.timer_list_item, null);
        ProgressBar barTimer = (ProgressBar) view.findViewById(R.id.barTimer);
        TextView textTimer = view.findViewById(R.id.timerTextView);
        CountDownTimer countDownTimer = new CountDownTimer(60 * minuti * 1000, 500) {
            // 500 means, onTick function will be called at every 500 milliseconds

            @Override
            public void onTick(long leftTimeInMilliseconds) {
                long seconds = leftTimeInMilliseconds / 1000;
                barTimer.setProgress((int)seconds);
                textTimer.setText(String.format("%02d", seconds/60) + ":" + String.format("%02d", seconds%60));
                // format the textview to show the easily readable format

            }
            @Override
            public void onFinish() {
                if(textTimer.getText().equals("00:00")){
                    textTimer.setText("STOP");
                }
                else{
                    textTimer.setText("2:00");
                    barTimer.setProgress(60*minuti);
                }
            }
        }.start();
    }
}
