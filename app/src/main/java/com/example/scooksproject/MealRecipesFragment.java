package com.example.scooksproject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.google.android.material.button.MaterialButton;

import java.util.LinkedList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class MealRecipesFragment extends Fragment {

    private GridView gridView;
    private MaterialButton startMealBtn;
    private AllRecipesGridAdapter adapter;
    private static List<Recipe> chosenRecipes;

    public static List<Recipe> getChosenRecipes() {
        if(chosenRecipes == null)
            chosenRecipes = new LinkedList<>();
        return chosenRecipes;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.meal_recipes_fragment, null);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        gridView = view.findViewById(R.id.mealRecipeGridView);
        startMealBtn = view.findViewById(R.id.mealRecipeStartBtn);

        adapter = new AllRecipesGridAdapter(getContext(), chosenRecipes);

        gridView.setAdapter(adapter);
    }

    public static void addRecipe(Recipe recipe){
        chosenRecipes.add(recipe);
    }
}
