package com.example.scooksproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class GroceriesListAllRecipesListAdapter extends ArrayAdapter<Recipe> {

    private final Context context;
    private final List<Recipe> items;
    TextView recipeName;
    NonScrollListView groceriesList;
    GroceriesListInnerListAdapter adapter;

    public GroceriesListAllRecipesListAdapter(@NonNull Context context, List<Recipe> items) {
        super(context, R.layout.groceries_list_fragment, items);
        this.context = context;
        this.items = items;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.groceries_list_single_recipe, null);
            Recipe currentRecipe = items.get(position);
            recipeName = convertView.findViewById(R.id.groceriesListSingleRecipeTitle);
            groceriesList = convertView.findViewById(R.id.groceriesListSingleRecipeListView);
            adapter = new GroceriesListInnerListAdapter(context, (ArrayList<Ingredient>) currentRecipe.getIngredients());
            recipeName.setText(items.get(position).getName());
            groceriesList.setAdapter(adapter);
        }
        return convertView;
    }

}
