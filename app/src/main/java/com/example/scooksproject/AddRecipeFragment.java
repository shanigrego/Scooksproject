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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AddRecipeFragment extends Fragment {

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


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.insertion_single_recipe, null);
        initComponents(view);
        return view;
    }

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.recipe_book_activity);
//        initComponents();
//
//        //Change levelOfPreperation to popup menu
//        //Change preperationTime to popup clock
//    }

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

    private void initComponents(View view){
        //Fetch components from insert_single_recipe.xml layout
//        LayoutInflater inflater = getLayoutInflater();
//        View view = inflater.inflate(R.layout.insertion_single_recipe, null);
        recipeName = view.findViewById(R.id.recipeNameET);
        recipeNameErrorTV = view.findViewById(R.id.recipeNameError);
        otherErrorTV = view.findViewById(R.id.otherErrors);
        makingTimeET = view.findViewById(R.id.makingTimeET);
        preperationTimeET = view.findViewById(R.id.preperationTimeET);
        levelEV = view.findViewById(R.id.levelEV);
        addIngredientsBtn = view.findViewById(R.id.addIngredientsBtn);
        addWorkingProccessBtn = view.findViewById(R.id.addWorkingProccessBtn);
        submitRecipe = view.findViewById(R.id.submitRecipeBtn);
        singleRecipeLinearLayout = view.findViewById(R.id.singleRecipeLinearLayout);
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

            Fragment fragment = new IngredientsActivity();
            //Intent intent = new Intent(AddRecipeFragment.this, IngredientsActivity.class);
            @Override
            public void onClick(View v) {
                ((HomePageActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.scrollViewLinearLayout, fragment).commit();
            }
        });
    }
}
