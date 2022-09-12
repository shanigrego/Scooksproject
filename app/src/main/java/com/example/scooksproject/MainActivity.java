package com.example.scooksproject;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.View;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private MaterialButton nextPageBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        DataBase db = DataBase.getInstance();
        DataBase.getAllRecipes();
    }

    private void initComponents(){
        nextPageBtn = findViewById(R.id.startBtn);

        nextPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
                startActivity(intent);
            }
        });
    }
}