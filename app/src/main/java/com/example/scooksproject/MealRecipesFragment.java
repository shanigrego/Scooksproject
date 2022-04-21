package com.example.scooksproject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.scooksproject.logics.Algorithm;
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

        List<Recipe> tryRecipes=getTesterRecipes();
        gridView.setAdapter(adapter);
        startMealBtn.setOnClickListener(item->{
            Algorithm.scooksAlgorithm(tryRecipes);
        });
    }

    private List<Recipe> getTesterRecipes() {

        List<Recipe> test=new LinkedList<>();

        List<Instruction> instructionList1=new LinkedList<>();
        Instruction recipe1inst1=new Instruction("Recipe 1 Press1",5,0);
        Instruction recipe1inst2=new Instruction("Recipe 1 Press2",5,0);

        Instruction recipe1inst3=new Instruction("Recipe 1 Press3",0,15);
        Instruction recipe1inst4=new Instruction("Recipe 1 Press4",10,0);
        instructionList1.add(recipe1inst1);
        instructionList1.add(recipe1inst2);
        instructionList1.add(recipe1inst3);
        instructionList1.add(recipe1inst4);
        Recipe recipe1=new Recipe("recipe 1","20","35","כל אחד יכול",null,null);
        recipe1.setRecipeInstructions(instructionList1);
        recipe1.setPreparationTime(35);
        recipe1.setTimeOfWorkNeeded(20);
        recipe1.setTotalFreeTime(15);

        List<Instruction> instructionList2=new LinkedList<>();
        Instruction recipe2inst1=new Instruction("Recipe 2 Press1",10,0);
        Instruction recipe2inst2=new Instruction("Recipe 2 Press2",0,10);
        instructionList2.add(recipe2inst1);
        instructionList2.add(recipe2inst2);
        Recipe recipe2=new Recipe("recipe 2","10","20","כל אחד יכול",null,null);
        recipe2.setRecipeInstructions(instructionList2);
        recipe2.setPreparationTime(20);
        recipe2.setTimeOfWorkNeeded(10);
        recipe2.setTotalFreeTime(10);

        List<Instruction> instructionList3=new LinkedList<>();
        Instruction recipe3inst1=new Instruction("Recipe 3 Press1",20,0);
        instructionList3.add(recipe3inst1);
        Recipe recipe3=new Recipe("recipe 3","20","20","כל אחד יכול",null,null);
        recipe3.setRecipeInstructions(instructionList3);
        recipe3.setPreparationTime(20);
        recipe3.setTimeOfWorkNeeded(20);
        recipe3.setTotalFreeTime(0);


        List<Instruction> instructionList4=new LinkedList<>();
        Instruction recipe4inst1=new Instruction("Recipe 4 Press1",10,0);
        instructionList4.add(recipe4inst1);
        Recipe recipe4=new Recipe("recipe 4","10","10","כל אחד יכול",null,null);
        recipe4.setRecipeInstructions(instructionList4);
        recipe4.setPreparationTime(10);
        recipe4.setTimeOfWorkNeeded(10);
        recipe4.setTotalFreeTime(0);

        List<Instruction> instructionList5=new LinkedList<>();
        Instruction recipe5inst1=new Instruction("Recipe 5 Press1",5,0);
        Instruction recipe5inst2=new Instruction("Recipe 5 Press2",0,10);
        instructionList5.add(recipe5inst1);
        instructionList5.add(recipe5inst2);
        Recipe recipe5=new Recipe("recipe 5","5","15","כל אחד יכול",null,null);
        recipe5.setRecipeInstructions(instructionList5);
        recipe5.setPreparationTime(15);
        recipe5.setTimeOfWorkNeeded(5);
        recipe5.setTotalFreeTime(10);

        List<Instruction> instructionList6=new LinkedList<>();
        Instruction recipe6inst1=new Instruction("Recipe 6 Press1",10,0);
        Instruction recipe6inst2=new Instruction("Recipe 6 Press2",0,45);
        Instruction recipe6inst3=new Instruction("Recipe 6 Press3",5,0);
        instructionList6.add(recipe6inst1);
        instructionList6.add(recipe6inst2);
        instructionList6.add(recipe6inst3);
        Recipe recipe6=new Recipe("recipe 6","15","60","כל אחד יכול",null,null);
        recipe6.setRecipeInstructions(instructionList6);
        recipe6.setPreparationTime(60);
        recipe6.setTimeOfWorkNeeded(15);
        recipe6.setTotalFreeTime(45);

        test.add(recipe1);
        test.add(recipe2);
        test.add(recipe3);
        test.add(recipe4);
        test.add(recipe5);
        test.add(recipe6);






        return test;
    }

    public static void addRecipe(Recipe recipe){
        chosenRecipes.add(recipe);
    }
}
