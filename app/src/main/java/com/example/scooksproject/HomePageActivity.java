package com.example.scooksproject;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DatabaseReference;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class HomePageActivity extends AppCompatActivity {

    private Button recipeBookBtn;
    private BottomNavigationView bottomNavigationView;
    private static LinearLayout scrollView;
    private static com.google.android.material.bottomappbar.BottomAppBar bottomAppBar;
    private static com.google.android.material.floatingactionbutton.FloatingActionButton chefButton;

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

    public static LinearLayout getScrollView() {
        return scrollView;
    }

    private void initComponents() {
        scrollView = findViewById(R.id.scrollViewLinearLayout);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomAppBar = findViewById(R.id.bottomAppBar);
        chefButton = findViewById(R.id.chefButton);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
                    default:
                        break;
                }
                return true;
            }
        });
    }
}
