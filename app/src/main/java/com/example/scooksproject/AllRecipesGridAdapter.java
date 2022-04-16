package com.example.scooksproject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AllRecipesGridAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Recipe> allRecipes;

    public AllRecipesGridAdapter(Context context, ArrayList<Recipe> allRecipes) {
        this.context = context;
        this.allRecipes = allRecipes;
    }

    @Override
    public int getCount() {
        return allRecipes.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.single_recipe_grid_layout, null);

            ImageView recipeImage = convertView.findViewById(R.id.gridLayoutRecipeImage);
            recipeImage.setBackgroundResource(R.drawable.rice_image);
            TextView recipeName = convertView.findViewById(R.id.gridLayoutRecipeName);
            recipeName.setText(allRecipes.get(position).getName());
//
//
//
//
//            String uri = "@drawable/rice_image";  // where myresource (without the extension) is the file
//
//            int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
//            Drawable res = context.getResources().getDrawable(imageResource);
//            tv.setImageDrawable(res);


        }
        return convertView;
    }
}
