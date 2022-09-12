package com.example.scooksproject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.scooksproject.logics.Algorithm;
import com.google.android.material.button.MaterialButton;

import java.util.LinkedList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MealRecipesFragment extends Fragment {

    private static NonScrollListView nonScrollView;
    private static AllRecipesGridAdapter adapter;
    private static List<Recipe> chosenRecipes;
    private TextView mealRecipesNoRecipes;
    private ImageView backBtn;

    public static List<Recipe> getChosenRecipes() {
        if (chosenRecipes == null)
            chosenRecipes = new LinkedList<>();
        return chosenRecipes;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.meal_recipes_fragment, null);
        initComponents(view);

//        HomePageActivity.getBottomNavigationView().getMenu().setGroupCheckable(0, false, true);

        return view;
    }

    private void initComponents(View view) {
        final Recipe[] finalRecipe = new Recipe[1];
        nonScrollView = view.findViewById(R.id.mealRecipeGridView);
        MaterialButton startMealBtn = view.findViewById(R.id.mealRecipeStartBtn);
        backBtn = view.findViewById(R.id.mealRecipesBackBtn);
        mealRecipesNoRecipes = view.findViewById(R.id.mealRecipesNoRecipes);

        adapter = new AllRecipesGridAdapter(getContext(), chosenRecipes);
        HomePageActivity.hideBottomNavigationBar();
        nonScrollView.setAdapter(adapter);

        //No Recipes TV initialization
        if(chosenRecipes != null && !chosenRecipes.isEmpty())
            mealRecipesNoRecipes.setVisibility(View.INVISIBLE);
        else if(chosenRecipes != null && chosenRecipes.isEmpty())
            startMealBtn.setVisibility(View.INVISIBLE);


        //Start Meal Button initialization
        startMealBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalRecipe[0] =  Algorithm.scooksAlgorithm(chosenRecipes);
                GroceriesListFragment.setMealStarted(true);
                Fragment fragment = new FinalRecipeFragment(finalRecipe[0]);
                getParentFragmentManager().beginTransaction().replace(R.id.scrollViewLinearLayout, fragment).addToBackStack("back").commit();
            }
        });

        //Back Button initialization
        backBtn.setOnClickListener(item -> {
            Fragment fragment = new RecipeBookFragment();
            getParentFragmentManager().beginTransaction().replace(R.id.scrollViewLinearLayout, fragment).commit();
        });
    }

    public static void addRecipe(Recipe recipe) {
        chosenRecipes.add(recipe);
        if (adapter != null)
            nonScrollView.setAdapter(adapter);
    }

    public static void removeRecipe(Recipe recipe) {
        chosenRecipes.remove(recipe);
        if (adapter != null)
            nonScrollView.setAdapter(adapter);
    }

    public static void removeAllRecipes() {
        chosenRecipes.clear();
        if (adapter != null)
            nonScrollView.setAdapter(adapter);
    }
}
