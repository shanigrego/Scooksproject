package com.example.scooksproject;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class RecipeBookFragment extends Fragment {

    private FloatingActionButton myRecpiesButton;
    private GridView allRecipesGridView;
    private AllRecipesGridAdapter adapter;
    private AllRecipesGridAdapter filteredAdapter;
    private ArrayList<Recipe> allRecipes;
    private EditText searchET;
    ArrayList<Recipe> filteredItems;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_book_activity, null);

        myRecpiesButton = view.findViewById(R.id.myRecipeFloatingBtn);
        allRecipesGridView = view.findViewById(R.id.allRecipesGridView);
        searchET = view.findViewById(R.id.searchRecipesET);

        allRecipes = DataBase.getAllRecipes();
        adapter = new AllRecipesGridAdapter(getContext(), allRecipes);
        allRecipesGridView.setAdapter(adapter);

        filteredItems = new ArrayList<>();

        initComponents();

        return view;
    }

    void initComponents(){

        myRecpiesButton.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = new MyRecipiesFragment();
            @Override
            public void onClick(View v) {
                ((HomePageActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.scrollViewLinearLayout, fragment).addToBackStack("back").commit();
            }
        });

        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filteredItems.clear();
                if(!s.toString().isEmpty()) {
                    for (Recipe recipe :
                            allRecipes) {
                        if (recipe.getName().contains(s))
                            filteredItems.add(recipe);
                    }
                    filteredAdapter = new AllRecipesGridAdapter(getContext(), filteredItems);
                    allRecipesGridView.setAdapter(filteredAdapter);
                }
                else{
                    allRecipesGridView.setAdapter(adapter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
