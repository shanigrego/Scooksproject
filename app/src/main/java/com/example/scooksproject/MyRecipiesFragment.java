package com.example.scooksproject;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MyRecipiesFragment extends Fragment {

    private FloatingActionButton addRecipeBtn;
    private ImageView backBtn;
    private GridView myRecipesGridView;
    private ListAdapter adapter;
    private List<Recipe> myRecipes;
    private EditText searchET;
    private AllRecipesGridAdapter filteredAdapter;
    ArrayList<Recipe> filteredItems;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_recipies, null);
        HomePageActivity.showBottomNavigationBar();
        initComponents(view);
        myRecipesGridView.setAdapter(adapter);

        return view;
    }

    private void initComponents(View view) {
        addRecipeBtn = view.findViewById(R.id.addRecipeBtn);
        backBtn = view.findViewById(R.id.backBtn);
        myRecipesGridView = view.findViewById(R.id.myRecipesGridView);
        myRecipes = StorageManager.ReadFromFile("MyRecipe3.txt", getContext().getFilesDir());
        adapter = new AllRecipesGridAdapter(getContext(), myRecipes);
        searchET = view.findViewById(R.id.myRecipesSearchET);
        filteredItems = new ArrayList<>();

        addRecipeBtn.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = new AddRecipeFragment();

            @Override
            public void onClick(View v) {
                ((HomePageActivity) getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.scrollViewLinearLayout, fragment).addToBackStack("back").commit();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            RecipeBookFragment fragment = new RecipeBookFragment();

            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.scrollViewLinearLayout, fragment).addToBackStack("back").commit();
            }
        });

        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filteredItems.clear();
                if (!s.toString().isEmpty()) {
                    for (Recipe recipe :
                            myRecipes) {
                        if (recipe.getName().contains(s))
                            filteredItems.add(recipe);
                    }
                    filteredAdapter = new AllRecipesGridAdapter(getContext(), filteredItems);
                    myRecipesGridView.setAdapter(filteredAdapter);
                } else {
                    myRecipesGridView.setAdapter(adapter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
