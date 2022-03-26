package com.example.scooksproject;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class IngredientsActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private Button amountTypeBtn7;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingredients);
    }

    private void initComponents(){
        amountTypeBtn7 = findViewById(R.id.amountTypeBtn7);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    public void showPopupMenu(View v){
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }
}
