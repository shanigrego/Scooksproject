package com.example.scooksproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class IngredientsFragment extends Fragment implements PopupMenu.OnMenuItemClickListener {

    private static ListView listView;
    private static IngredientListViewAdapter adapter;
    private static LinkedList<Ingredient> ingredients;
    private ImageView backBtn;



    public static LinkedList<Ingredient> getIngredients() {
        return ingredients;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ingredients_activity, null);
        initComponents(view);
        com.google.android.material.textview.MaterialTextView addIngredient = view.findViewById(R.id.addSingleIngredientBtn);
        HomePageActivity.getBottomAppBar().setVisibility(View.INVISIBLE);
        HomePageActivity.getChefButton().setVisibility(View.INVISIBLE);

        addIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem("");
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                HomePageActivity.getBottomAppBar().setVisibility(View.VISIBLE);
                HomePageActivity.getChefButton().setVisibility(View.VISIBLE);
                Fragment fragment = new AddRecipeFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.scrollViewLinearLayout, fragment).commit();
            }
        });

        return view;
    }

    private void initComponents(View view) {
        listView = view.findViewById(R.id.ingredientsListView);
        if(ingredients == null)
            ingredients = new LinkedList<>();
        adapter = new IngredientListViewAdapter(getContext(), ingredients);
        listView.setAdapter(adapter);
        backBtn = view.findViewById(R.id.recipeIngredientsBackArraow);

    }

    public static void addItem(String str) {
        ingredients.add(new Ingredient(
                str,
                0,
                "גרם"
                ));
        listView.setAdapter(adapter);
    }

    public static void removeItem(int index) {
        ingredients.remove(index);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    private void saveData(){
        View view1;
        EditText ingName, ingAmount;
        Button ingMeasureUnit;
        for (int i = 0; i < listView.getCount(); i++) {
            view1 = listView.getChildAt(i);
            ingName = view1.findViewById(R.id.nameIngredient);
            ingAmount = view1.findViewById(R.id.amountIngredient);
            ingMeasureUnit = view1.findViewById(R.id.measureUnitIngredient);
            ingredients.get(i).setName(ingName.getText().toString());
            ingredients.get(i).setMeasureUnit(ingMeasureUnit.getText().toString());
            ingredients.get(i).setAmount(Double.parseDouble(ingAmount.getText().toString()));
        }
    }
}
