package com.example.scooksproject;

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

    private GridView favouritesGridView;
    private ListAdapter adapter;
    private List<Recipe> favouriteRecipesList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favourites_fragment, null);
        initComponents(view);

        return view;
    }

    private  void initComponents(View view){
        favouritesGridView = view.findViewById(R.id.favouritesGridView);
        favouriteRecipesList = StorageManager.ReadFromFile("Fav1.txt",getContext().getFilesDir());
        adapter = new AllRecipesGridAdapter(getContext(),favouriteRecipesList);
        favouritesGridView.setAdapter(adapter);
    }

    private void setFavouriteBtnColor(int color, ImageView favouritesBtn){
        DrawableCompat.setTint(
                DrawableCompat.wrap(favouritesBtn.getDrawable()),
                ContextCompat.getColor(getContext(), color)
        );
    }
}
