package com.example.scooksproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.PopupMenu;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class IngredientsFragment extends Fragment implements PopupMenu.OnMenuItemClickListener {

    private ListView listView;
    private static IngredientListViewAdapter adapter;
    private static ArrayList<String> items;
    //private ImageView backBtn;
    private static int lastBtnClicked;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ingredients_activity, null);
        initComponents(view);
        return view;
    }
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.ingredients_activity);
//        initComponents();
//    }

    private void initComponents(View view){
        listView = view.findViewById(R.id.ingredientsListView);
        items = new ArrayList<>();
        adapter = new IngredientListViewAdapter(getContext(), items);
        listView.setAdapter(adapter);
        addItem("קמח");
        addItem("סוכר");
        addItem("לחם");
        addItem("רום");
//
//        backBtn.setOnClickListener(new View.OnClickListener() {
//            RecipeBookFragment fragment = new RecipeBookFragment();
//            @Override
//            public void onClick(View v) {
//                getParentFragmentManager().beginTransaction().replace(R.id.scrollViewLinearLayout, fragment).commit();
//            }
//        });
    }

    public static void setLastBtnClicked(int lastBtnClicked) {
        IngredientsFragment.lastBtnClicked = lastBtnClicked;
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
        PopupMenu popupMenu = new PopupMenu(getContext(), v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu_amount);
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
    }
}
