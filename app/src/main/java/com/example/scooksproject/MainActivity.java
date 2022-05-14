package com.example.scooksproject;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private Button btn;
    private Button nextPageBtn;
    private Button alarmClockTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        DataBase db = DataBase.getInstance();
        DataBase.getAllRecipes();
    }

    private void initComponents(){
        btn = findViewById(R.id.buttonTry);
        nextPageBtn = findViewById(R.id.startBtn);
        alarmClockTest = findViewById(R.id.alarmClock);

        alarmClockTest.setOnClickListener(item -> {
            Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
            intent.putExtra(AlarmClock.EXTRA_HOUR, 10);
            intent.putExtra(AlarmClock.EXTRA_MESSAGE, "set by AlarmClock");
            intent.putExtra(AlarmClock.EXTRA_MINUTES, 10);
            startActivity(intent);
        });

        btn.setOnClickListener(v -> new RecipeParser().parseAllRecipes());

        nextPageBtn.setOnClickListener(new View.OnClickListener() {
            Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
            @Override
            public void onClick(View v) { startActivity(intent); }
        });
    }
}