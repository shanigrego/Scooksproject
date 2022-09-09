package com.example.scooksproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;

public class FavouritesFragment extends Fragment {

    private AllRecipesGridAdapter adapter;
    private AllRecipesGridAdapter filteredAdapter;
    private EditText searchET;
    private ArrayList<Recipe> filteredItems;
    private ImageView backBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.favourites_fragment, null);
        initComponents(view);
        HomePageActivity.showBottomNavigationBar();
        HomePageActivity.getBottomNavigationView().performClick();
        return view;
    }

    private void initComponents(View view) {
        GridView favouritesGridView = view.findViewById(R.id.favouritesGridView);
        List<Recipe> favouriteRecipesList = StorageManager.ReadFromFile("Fav1.txt", getContext().getFilesDir());
        adapter = new AllRecipesGridAdapter(getContext(), favouriteRecipesList);
        favouritesGridView.setAdapter(adapter);
        searchET = view.findViewById(R.id.favouritesSearchET);
        filteredItems = new ArrayList<>();
        backBtn = view.findViewById(R.id.favouritesBackBtn);

        backBtn.setOnClickListener(item -> {
            if (backBtn.getVisibility() == View.VISIBLE) {
                searchET.setText("");
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });

        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = s.length();
                if(length > 0) {
                    char lastChar = s.charAt(length - 1);
                    if (lastChar == '\n') {
                        searchET.setText(s.subSequence(0, length - 1));
                        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        return;
                    }
                }
                filteredItems.clear();
                if (!s.toString().isEmpty()) {
                    for (Recipe recipe :
                            favouriteRecipesList) {
                        if (recipe.getName().contains(s))
                            filteredItems.add(recipe);
                    }
                    filteredAdapter = new AllRecipesGridAdapter(getContext(), filteredItems);
                    favouritesGridView.setAdapter(filteredAdapter);
                } else {
                    favouritesGridView.setAdapter(adapter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0)
                    backBtn.setVisibility(View.INVISIBLE);
                else backBtn.setVisibility(View.VISIBLE);
            }
        });
    }
}
