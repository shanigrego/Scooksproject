package com.example.scooksproject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;

public class FavouritesFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.favourites_fragment, null);
        initComponents(view);
        HomePageActivity.showBottomNavigationBar();

        return view;
    }

    private void initComponents(View view) {
        GridView favouritesGridView = view.findViewById(R.id.favouritesGridView);
        List<Recipe> favouriteRecipesList = StorageManager.ReadFromFile("Fav1.txt", getContext().getFilesDir());
        ListAdapter adapter = new AllRecipesGridAdapter(getContext(), favouriteRecipesList);
        favouritesGridView.setAdapter(adapter);
    }
}
