package com.example.scooksproject;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class IngredientsActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private ListView listView;
    private static IngredientListViewAdapter adapter;
    private static ArrayList<String> items;
    private static int lastBtnClicked;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingredients_activity);
        initComponents();
    }

    private void initComponents(){
        listView = findViewById(R.id.ingredientsListView);
        items = new ArrayList<>();
        adapter = new IngredientListViewAdapter(this, items);
        listView.setAdapter(adapter);
        addItem("קמח");
        addItem("סוכר");
        addItem("לחם");
        addItem("רום");
    }

    public static void setLastBtnClicked(int lastBtnClicked) {
        IngredientsActivity.lastBtnClicked = lastBtnClicked;
    }

    public static void addItem(String str){
        items.add(str);
        adapter.notifyDataSetChanged();
    }

    public static void removeItem(int index){
        items.remove(index);
        adapter.notifyDataSetChanged();
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
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
    }
}
