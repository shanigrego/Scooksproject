package com.example.scooksproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class HomePage extends AppCompatActivity {

    private Button recipeBookBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        initComponents();
    }

    private void initComponents(){
        recipeBookBtn = findViewById(R.id.recipeBookBtn);

        recipeBookBtn.setOnClickListener(new View.OnClickListener() {
            Intent intent = new Intent(HomePage.this, RecipeBook.class);
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }
}
