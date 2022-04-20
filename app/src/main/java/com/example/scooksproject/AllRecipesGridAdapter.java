package com.example.scooksproject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

public class AllRecipesGridAdapter extends BaseAdapter {

    private Context context;
    private List<Recipe> allRecipes;
    private boolean isFav;
    private List<Recipe> favouriteRecipesList;
    private Recipe currentRecipe;
    private ImageView chefIcon;
    private ImageView chefIconPink;
    private boolean chosenForMeal;

    public AllRecipesGridAdapter(Context context, List<Recipe> allRecipes/*, List<Recipe> favouriteRecipesList*/) {
        this.context = context;
        this.allRecipes = allRecipes;
        favouriteRecipesList = StorageManager.ReadFromFile("Fav1.txt", context.getFilesDir());
        chosenForMeal = false;
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
            chefIcon = convertView.findViewById(R.id.chefIconSelectionRecipe);
            chefIconPink = convertView.findViewById(R.id.chefIconSelectionRecipePink);
            currentRecipe = allRecipes.get(position);
            isFav = isFavourite() == null ? false : true;

            if (isFavourite() != null)
                setFavouriteBtnColor(R.color.pink, favouritesBtn);

            //Chef Icon initialization
            chefIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    chosenForMeal = !chosenForMeal;
                    chefIcon.setVisibility(View.INVISIBLE);
                    chefIconPink.setVisibility(View.VISIBLE);
                }
            });

            chefIconPink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    chosenForMeal = !chosenForMeal;
                    chefIcon.setVisibility(View.VISIBLE);
                    chefIconPink.setVisibility(View.INVISIBLE);
                }
            });


            //Favourites Button initialization
            favouritesBtn.setOnClickListener(new View.OnClickListener() {
                int color;

                @Override
                public void onClick(View v) {
                    isFav = !isFav;
                    if (isFav == true) {
                        color = R.color.pink;
                        StorageManager.WriteToFile("Fav1.txt", allRecipes.get(position), context.getFilesDir(), true);
                    } else {
                        color = R.color.white;
                        removeRecipeFromFavourites();
                    }
                    setFavouriteBtnColor(color, favouritesBtn);
                }
            });
            recipeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = new RecipeDetailsFragment(allRecipes.get(position));
                    FragmentManager fm = ((FragmentActivity) context).getSupportFragmentManager();
                    fm.beginTransaction().replace(R.id.scrollViewLinearLayout, fragment).addToBackStack("tag").commit();
                }
            });
        }
        return convertView;
    }

    private void removeRecipeFromFavourites() {
        favouriteRecipesList.remove(isFavourite());
        StorageManager.WriteToFile("Fav1.txt", favouriteRecipesList, context.getFilesDir(), false);
        notifyDataSetChanged();
    }

    private void setFavouriteBtnColor(int color, ImageView favouritesBtn) {
        DrawableCompat.setTint(
                DrawableCompat.wrap(favouritesBtn.getDrawable()),
                ContextCompat.getColor(context, color)
        );
    }

    private Recipe isFavourite() {
        for (Recipe recipe :
                favouriteRecipesList) {
            if (recipe.getName().equals(currentRecipe.getName()))
                return recipe;
        }
        return null;
    }


}
