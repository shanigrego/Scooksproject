package com.example.scooksproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddRecipeActivity extends AppCompatActivity {

    private EditText recipeName;
    private EditText makingTimeET;
    private EditText preperationTimeET;
    private EditText levelEV;
    private Button addIngredientsBtn;
    private Button addWorkingProccessBtn;
    private Button submitRecipe;
    private TextView recipeNameErrorTV;
    private TextView otherErrorTV;
    private LinearLayout singleRecipeLinearLayout;
    private ScrollView scrollViewRecipeBook;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_book_activity);
        initComponents();

        //Change levelOfPreperation to popup menu
        //Change preperationTime to popup clock
    }

    private void submitRecipeFunc(){
        String recipeNameStr = recipeName.getText().toString();
        String makingTime = makingTimeET.getText().toString();
        String preperationTime = preperationTimeET.getText().toString();
        checkRecipeName(recipeNameStr);
    }

    private void checkRecipeName(String name){
        if(name.isEmpty()){
            recipeNameErrorTV.setText("שם המתכון ריק");
        }
        //check for existence in database
    }

    private void initComponents(){
        //Fetch components from insert_single_recipe.xml layout
        LayoutInflater inflater = getLayoutInflater();
        View insertionSingleRecipeView = inflater.inflate(R.layout.insertion_single_recipe, null);
        recipeName = insertionSingleRecipeView.findViewById(R.id.recipeNameET);
        recipeNameErrorTV = insertionSingleRecipeView.findViewById(R.id.recipeNameError);
        otherErrorTV = insertionSingleRecipeView.findViewById(R.id.otherErrors);
        makingTimeET = insertionSingleRecipeView.findViewById(R.id.makingTimeET);
        preperationTimeET = insertionSingleRecipeView.findViewById(R.id.preperationTimeET);
        levelEV = insertionSingleRecipeView.findViewById(R.id.levelEV);
        addIngredientsBtn = insertionSingleRecipeView.findViewById(R.id.addIngredientsBtn);
        addWorkingProccessBtn = insertionSingleRecipeView.findViewById(R.id.addWorkingProccessBtn);
        submitRecipe = insertionSingleRecipeView.findViewById(R.id.submitRecipeBtn);
        singleRecipeLinearLayout = insertionSingleRecipeView.findViewById(R.id.singleRecipeLinearLayout);
        //Fetching Finished

//        scrollViewRecipeBook = findViewById(R.id.recipeBookScrollView);
//        ((ViewGroup)singleRecipeLinearLayout.getParent()).removeView(singleRecipeLinearLayout);
//        scrollViewRecipeBook.addView(singleRecipeLinearLayout);
        submitRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitRecipeFunc();
            }
        });

        addIngredientsBtn.setOnClickListener(new View.OnClickListener() {
            Intent intent = new Intent(AddRecipeActivity.this, IngredientsActivity.class);
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }
}
