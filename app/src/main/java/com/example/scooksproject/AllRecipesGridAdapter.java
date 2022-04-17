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

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

public class AllRecipesGridAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Recipe> allRecipes;
    private boolean isFav;

    public AllRecipesGridAdapter(Context context, ArrayList<Recipe> allRecipes) {
        this.context = context;
        this.allRecipes = allRecipes;
        this.isFav = false;
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
            ImageView favouritesBtn = convertView.findViewById(R.id.favouriteGridViewBtn);
            favouritesBtn.setOnClickListener(new View.OnClickListener() {
                int color;

                @Override
                public void onClick(View v) {
                    isFav = !isFav;
                    if (isFav == true)
                        color = R.color.pink;
                    else
                        color = R.color.white;
                    DrawableCompat.setTint(
                            DrawableCompat.wrap(favouritesBtn.getDrawable()),
                            ContextCompat.getColor(context, color)
                    );
                }
            });

        }
        return convertView;
    }
}
