package com.example.scooksproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class RecipeDetailsFragment extends Fragment {

    private Recipe currentRecipe;
    private NonScrollListView ingredientsList;
    private NonScrollListView instructionsList;
    private TextView name;
    private TextView difficulty;
    private TextView preparationTime;
    private TextView makingTime;
    private ListAdapter ingredientsListAdapter;
    private ListAdapter instructionsListAdapter;
    private ImageView backBtn;

    public RecipeDetailsFragment(Recipe currentRecipe) {
        super();
        this.currentRecipe = currentRecipe;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_details_fragment, null);
        HomePageActivity.hideBottomNavigationBar();
        initComponents(view);
        setView();
        return view;
    }

    private void setView() {
        name.setText(currentRecipe.getName());
        difficulty.setText(currentRecipe.getDifficultLevel());
        preparationTime.setText(currentRecipe.getTimeOfWorkNeededStr());
        makingTime.setText(currentRecipe.getTotalTimeRecipeStr());
        ingredientsList.setAdapter(ingredientsListAdapter);
        instructionsList.setAdapter(instructionsListAdapter);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new RecipeBookFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.scrollViewLinearLayout, fragment).commit();
            }
        });
    }

    private void initComponents(View view) {
        ingredientsList = view.findViewById(R.id.recipeDetailsIngredientsList);
        instructionsList = view.findViewById(R.id.recipeDetailsStepsList);
        name = view.findViewById(R.id.recipeDetailsRecipeName);
        difficulty = view.findViewById(R.id.levelTV);
        preparationTime = view.findViewById(R.id.preperationTimeTV);
        makingTime = view.findViewById(R.id.makingTimeTV);
        backBtn = view.findViewById(R.id.recipeDetailsBackBtn);
        ingredientsListAdapter = new IngredientListViewAdapter(getContext(), currentRecipe.getIngredients(), true);
        instructionsListAdapter = new RecipeInstructionsListAdapter(getContext(), currentRecipe.getRecipeInstructionsStr(),true);
    }
}
