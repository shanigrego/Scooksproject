package com.example.scooksproject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

public class HomePageActivity extends AppCompatActivity {

    private static BottomNavigationView bottomNavigationView;
    @SuppressLint("StaticFieldLeak")
    private static LinearLayout scrollView;
    private static com.google.android.material.bottomappbar.BottomAppBar bottomAppBar;
    private static com.google.android.material.floatingactionbutton.FloatingActionButton chefButton;
    @SuppressLint("StaticFieldLeak")
    private static View snackBarView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        initComponents();

    }

    //Being used. Do not erase!
    public static BottomAppBar getBottomAppBar() {
        return bottomAppBar;
    }

    //Being used. Do not erase!
    public static FloatingActionButton getChefButton() {
        return chefButton;
    }

    //Being uses. Do not erase!
    public static void hideBottomNavigationBar() {
        bottomAppBar.setVisibility(View.INVISIBLE);
        chefButton.setVisibility(View.INVISIBLE);
        bottomNavigationView.setVisibility(View.INVISIBLE);
        scrollView.setLayoutParams(new CoordinatorLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
    }

    public static void showBottomNavigationBar() {
        bottomAppBar.setVisibility(View.VISIBLE);
        chefButton.setVisibility(View.VISIBLE);
        bottomNavigationView.setVisibility(View.VISIBLE);
        scrollView.setLayoutParams(new CoordinatorLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
    }

    public static void showSnackBar(Recipe lastDeletedRecipe, ImageView chefIconChosen, ImageView chefIconUnChosen) {
        Snackbar.make(snackBarView, "", Snackbar.LENGTH_LONG).setAction("ביטול", item -> {
            MealRecipesFragment.addRecipe(lastDeletedRecipe);
            chefIconChosen.setVisibility(View.VISIBLE);
            chefIconUnChosen.setVisibility(View.INVISIBLE);
        }).show();
    }


    private void initComponents() {
        scrollView = findViewById(R.id.scrollViewLinearLayout);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomAppBar = findViewById(R.id.bottomAppBar);
        chefButton = findViewById(R.id.chefButton);
        bottomNavigationView.setBackground(null);
        snackBarView = findViewById(android.R.id.content);

        // SnackBar initialization
        snackBarView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);

        // Bottom Navigation View initialization
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment fragment;
            switch (item.getItemId()) {
                case (R.id.recipeBookIcon):
                    fragment = new RecipeBookFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.scrollViewLinearLayout, fragment).commit();
                    break;
                case (R.id.groceryList):
                    fragment = new GroceriesListFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.scrollViewLinearLayout, fragment).commit();
                    break;
                case (R.id.favouritesNavBtn):
                    fragment = new FavouritesFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.scrollViewLinearLayout, fragment).commit();
                    break;
                case (R.id.timers):
                    fragment = new TimersFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.scrollViewLinearLayout, fragment).commit();
                    break;
            }
            return true;
        });

        // Chef Button initialization
        chefButton.setOnClickListener(btn -> {
            Fragment fragment = new MealRecipesFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.scrollViewLinearLayout, fragment).addToBackStack("tag").commit();
        });
    }
}
