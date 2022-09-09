package com.example.scooksproject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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

        HomePageActivity.getBottomNavigationView().getMenu().setGroupCheckable(0, false, true);

        return view;
    }

    private void initComponents(View view) {
        final Recipe[] finalRecipe = new Recipe[1];
        nonScrollView = view.findViewById(R.id.mealRecipeGridView);
        MaterialButton startMealBtn = view.findViewById(R.id.mealRecipeStartBtn);
        backBtn = view.findViewById(R.id.mealRecipesBackBtn);

        adapter = new AllRecipesGridAdapter(getContext(), chosenRecipes);
        HomePageActivity.hideBottomNavigationBar();
        List<Recipe> tryRecipes = getTesterRecipes();
        nonScrollView.setAdapter(adapter);
        //chosenRecipes=getTesterRecipes();
        //Start Meal Button initialization
        startMealBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalRecipe[0] =  Algorithm.scooksAlgorithm(chosenRecipes);
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

    private List<Recipe> getTesterRecipes() {

        List<Recipe> test = new LinkedList<>();

        List<Instruction> instructionList1 = new LinkedList<>();
        Instruction recipe1inst1 = new Instruction(1, "Recipe 1 Press1", 5, 0);
        Instruction recipe1inst2 = new Instruction(2, "Recipe 1 Press2", 0, 10);

        Instruction recipe1inst3 = new Instruction(3, "Recipe 1 Press3", 15, 0);
        Instruction recipe1inst4 = new Instruction(4, "Recipe 1 Press4", 0, 5);
        instructionList1.add(recipe1inst1);
        instructionList1.add(recipe1inst2);
        instructionList1.add(recipe1inst3);
        instructionList1.add(recipe1inst4);
        Recipe recipe1 = new Recipe("recipe 1", "20", "35", "כל אחד יכול", null, null, null, null, 0, 0, 0);
        recipe1.setInstructions(instructionList1);
        recipe1.setPreparationTime(35);
        recipe1.setTimeOfWorkNeeded(20);
        recipe1.setTotalFreeTime(15);

        List<Instruction> instructionList2 = new LinkedList<>();
        Instruction recipe2inst1 = new Instruction(1, "Recipe 2 Press1", 10, 0);
        Instruction recipe2inst2 = new Instruction(2, "Recipe 2 Press2", 0, 10);
        instructionList2.add(recipe2inst1);
        instructionList2.add(recipe2inst2);
        Recipe recipe2 = new Recipe("recipe 2", "10", "20", "כל אחד יכול", null, null, null, null, 0, 0, 0);
        recipe2.setInstructions(instructionList2);
        recipe2.setPreparationTime(20);
        recipe2.setTimeOfWorkNeeded(10);
        recipe2.setTotalFreeTime(10);

        List<Instruction> instructionList3 = new LinkedList<>();
        Instruction recipe3inst1 = new Instruction(1, "Recipe 3 Press1", 20, 0);
        instructionList3.add(recipe3inst1);
        Recipe recipe3 = new Recipe("recipe 3", "20", "20", "כל אחד יכול", null, null, null, null, 0, 0, 0);
        recipe3.setInstructions(instructionList3);
        recipe3.setPreparationTime(20);
        recipe3.setTimeOfWorkNeeded(20);
        recipe3.setTotalFreeTime(0);


        List<Instruction> instructionList4 = new LinkedList<>();
        Instruction recipe4inst1 = new Instruction(1, "Recipe 4 Press1", 10, 0);
        instructionList4.add(recipe4inst1);
        Recipe recipe4 = new Recipe("recipe 4", "10", "10", "כל אחד יכול", null, null, null, null, 0, 0, 0);
        recipe4.setInstructions(instructionList4);
        recipe4.setPreparationTime(10);
        recipe4.setTimeOfWorkNeeded(10);
        recipe4.setTotalFreeTime(0);

        List<Instruction> instructionList5 = new LinkedList<>();
        Instruction recipe5inst1 = new Instruction(1, "Recipe 5 Press1", 5, 0);
        Instruction recipe5inst2 = new Instruction(2, "Recipe 5 Press2", 0, 10);
        instructionList5.add(recipe5inst1);
        instructionList5.add(recipe5inst2);
        Recipe recipe5 = new Recipe("recipe 5", "5", "15", "כל אחד יכול", null, null, null, null, 0, 0, 0);
        recipe5.setInstructions(instructionList5);
        recipe5.setPreparationTime(15);
        recipe5.setTimeOfWorkNeeded(5);
        recipe5.setTotalFreeTime(10);

        List<Instruction> instructionList6 = new LinkedList<>();
        Instruction recipe6inst1 = new Instruction(1, "Recipe 6 Press1", 10, 0);
        Instruction recipe6inst2 = new Instruction(2, "Recipe 6 Press2", 0, 45);
      //  Instruction recipe6inst3 = new Instruction(3, "Recipe 6 Press3", 5, 0);
        instructionList6.add(recipe6inst1);
        instructionList6.add(recipe6inst2);
        //instructionList6.add(recipe6inst3);
//        Recipe recipe6 = new Recipe("recipe 6", "15", "60", "כל אחד יכול", null, null, null, null, 0, 0, 0);
//        recipe6.setInstructions(instructionList6);
//        recipe6.setPreparationTime(60);
//        recipe6.setTimeOfWorkNeeded(15);
//        recipe6.setTotalFreeTime(45);
        Recipe recipe6=new Recipe("recipe 6","10","55","כל אחד יכול",null,null,null,instructionList6,10,45,55);
        Instruction recipe7inst = new Instruction(1, "Recipe 7 Press1", 30, 0);
        Instruction recipe8inst = new Instruction(1, "Recipe 8 Press1", 20, 0);
        List<Instruction>instructionList7=new LinkedList<>();
        List<Instruction>instructionList8=new LinkedList<>();
        instructionList7.add(recipe7inst);
        instructionList8.add(recipe8inst);
        Recipe recipe7 = new Recipe("recipe 7", "30","30","כל אחד יכול",null,null,null,instructionList7,30,0,30);
        Recipe recipe8 = new Recipe("recipe 8", "20","20","כל אחד יכול",null,null,null,instructionList8,20,0,20);

        test.add(recipe1);
        test.add(recipe2);
        test.add(recipe3);
        test.add(recipe4);
        test.add(recipe5);
        test.add(recipe6);
        test.add(recipe7);
        test.add(recipe8);

        return test;
    }
}
