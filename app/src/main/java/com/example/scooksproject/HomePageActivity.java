package com.example.scooksproject;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

public class HomePageActivity extends AppCompatActivity {

    private Button recipeBookBtn;
    private static BottomNavigationView bottomNavigationView;
    private static LinearLayout scrollView;
    private static com.google.android.material.bottomappbar.BottomAppBar bottomAppBar;
    private static com.google.android.material.floatingactionbutton.FloatingActionButton chefButton;
    private static View snackBarView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        initComponents();

    }

    public static BottomAppBar getBottomAppBar() {
        return bottomAppBar;
    }

    public static FloatingActionButton getChefButton() {
        return chefButton;
    }

    public static void HideBottomNavigationBar() {
        bottomAppBar.setVisibility(View.INVISIBLE);
        chefButton.setVisibility(View.INVISIBLE);
        bottomNavigationView.setVisibility(View.INVISIBLE);
        scrollView.setLayoutParams(new CoordinatorLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
    }

    public static void ShowBottomNavigationBar() {
        bottomAppBar.setVisibility(View.VISIBLE);
        chefButton.setVisibility(View.VISIBLE);
        bottomNavigationView.setVisibility(View.VISIBLE);
        scrollView.setLayoutParams(new CoordinatorLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
    }

    public static void showSnackBar(){
        Snackbar.make(snackBarView, "הוסר מתכון", Snackbar.LENGTH_LONG).setAction("ביטול", item -> {
            //TODO
        }).show();
    }

    private void initComponents() {
        scrollView = findViewById(R.id.scrollViewLinearLayout);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomAppBar = findViewById(R.id.bottomAppBar);
        chefButton = findViewById(R.id.chefButton);
        bottomNavigationView.setBackground(null);
        snackBarView = findViewById(android.R.id.content);

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
                default:
                    break;
            }
            return true;
        });

        chefButton.setOnClickListener(btn -> {
            Fragment fragment = new MealRecipesFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.scrollViewLinearLayout, fragment).addToBackStack("tag").commit();
        });
    }
}
