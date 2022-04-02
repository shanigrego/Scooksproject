package com.example.scooksproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class HomePageActivity extends AppCompatActivity {

    private Button recipeBookBtn;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        initComponents();
    }

    private void initComponents(){

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = new Intent(HomePageActivity.this, RecipeBookActivity.class);
                switch(item.getItemId()){
                    case(R.id.recipeBookIcon):
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
//        recipeBookBtn = findViewById(R.id.recipeBookBtn);
//
//        recipeBookBtn.setOnClickListener(new View.OnClickListener() {
//            Intent intent = new Intent(HomePageActivity.this, RecipeBookActivity.class);
//            @Override
//            public void onClick(View v) {
//                startActivity(intent);
//            }
//        });
    }
}
