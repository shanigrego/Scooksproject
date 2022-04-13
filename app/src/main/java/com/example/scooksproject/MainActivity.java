package com.example.scooksproject;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private Button btn;
    private Button nextPageBtn;

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

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RecipeParser().parseAllRecipes();
            }
        });

        nextPageBtn.setOnClickListener(new View.OnClickListener() {
            Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }
}