package com.example.scooksproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

public class AllRecipesGridAdapter extends BaseAdapter {

    private final Context context;
    private final List<Recipe> allRecipes;
    private final List<Recipe> favouriteRecipesList;
    private final List<Recipe> chosenRecipes;

    public AllRecipesGridAdapter(Context context, List<Recipe> allRecipes/*, List<Recipe> favouriteRecipesList*/) {
        this.context = context;
        this.allRecipes = allRecipes;
        favouriteRecipesList = StorageManager.ReadFromFile("Fav1.txt", context.getFilesDir());
        chosenRecipes = MealRecipesFragment.getChosenRecipes();
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

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.single_recipe_grid_layout, null);
            initComponents(convertView, position);
        }
        return convertView;
    }

    private void initComponents(View convertView, int position) {

        ProgressBar progressBar = convertView.findViewById(R.id.recipeImgaeProgreeBar);
        ImageView recipeImage = convertView.findViewById(R.id.gridLayoutRecipeImage);
        TextView recipeName = convertView.findViewById(R.id.gridLayoutRecipeName);
        recipeName.setText(allRecipes.get(position).getName());
        ImageView favouritesBtn = convertView.findViewById(R.id.favouriteGridViewBtn);
        TextView imageErrorTV = convertView.findViewById(R.id.recipeImageErrorTV);

        ImageView chefIconUnChosen;
        ImageView chefIconChosen;
        chefIconUnChosen = convertView.findViewById(R.id.chefIconSelectionRecipeUnChosen);
        chefIconChosen = convertView.findViewById(R.id.chefIconSelectionRecipeChosen);
        Recipe currentRecipe = allRecipes.get(position);

        //Image loading
        if (currentRecipe.getRecipeImg() != null)
            Picasso.get().load(currentRecipe.getRecipeImg()).centerCrop().fit().into(recipeImage, new Callback() {
                @Override
                public void onSuccess() {
                    progressBar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onError(Exception e) {
                    progressBar.setVisibility(View.INVISIBLE);
                    imageErrorTV.setText("לא ניתן לטעון את התמונה");
                }
            });


        //Chef Icon initialization
        if (isChosenForMeal(currentRecipe))
            toggleChosenForMeal(true, false, chefIconChosen, chefIconUnChosen, currentRecipe);
        chefIconUnChosen.setOnClickListener(v -> toggleChosenForMeal(true, true, chefIconChosen, chefIconUnChosen, currentRecipe));
        chefIconChosen.setOnClickListener(v -> toggleChosenForMeal(false, false, chefIconChosen, chefIconUnChosen, currentRecipe));

        //Favourites Button initialization
        final boolean[] isFav = {isFavourite(currentRecipe) != null};
        if (isFavourite(currentRecipe) != null)
            setFavouriteBtnColor(R.color.pink, favouritesBtn);
        favouritesBtn.setOnClickListener(new View.OnClickListener() {
            int color;

            @Override
            public void onClick(View v) {
                isFav[0] = !isFav[0];
                if (isFav[0]) {
                    color = R.color.pink;
                    StorageManager.WriteToFile("Fav1.txt", allRecipes.get(position), context.getFilesDir(), true);
                } else {
                    color = R.color.white;
                    removeRecipeFromFavourites(currentRecipe);
                }
                setFavouriteBtnColor(color, favouritesBtn);
            }
        });

        //Recipe Image initialization
        recipeImage.setOnClickListener(v -> {
            Fragment fragment = new RecipeDetailsFragment(allRecipes.get(position));
            FragmentManager fm = ((FragmentActivity) context).getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.scrollViewLinearLayout, fragment).addToBackStack("tag").commit();
        });
    }

    private void removeRecipeFromFavourites(Recipe currentRecipe) {
        favouriteRecipesList.remove(isFavourite(currentRecipe));
        StorageManager.WriteToFile("Fav1.txt", favouriteRecipesList, context.getFilesDir(), false);
        notifyDataSetChanged();
    }

    private void setFavouriteBtnColor(int color, ImageView favouritesBtn) {
        DrawableCompat.setTint(
                DrawableCompat.wrap(favouritesBtn.getDrawable()),
                ContextCompat.getColor(context, color)
        );
    }

    private Recipe isFavourite(Recipe currentRecipe) {
        for (Recipe recipe :
                favouriteRecipesList) {
            if (recipe.getName().equals(currentRecipe.getName()))
                return recipe;
        }
        return null;
    }

    private boolean isChosenForMeal(Recipe currentRecipe) {
        for (Recipe recipe :
                chosenRecipes) {
            if (currentRecipe.getName().equals(recipe.getName()))
                return true;
        }
        return false;
    }

    private void toggleChosenForMeal(boolean isChosen, boolean addToChosenRecipes, ImageView chefIconChosen, ImageView chefIconUnChosen, Recipe currentRecipe) {
        if (isChosen) {
            if (addToChosenRecipes)
                MealRecipesFragment.addRecipe(currentRecipe);
            chefIconUnChosen.setVisibility(View.INVISIBLE);
            chefIconChosen.setVisibility(View.VISIBLE);
        } else {
            MealRecipesFragment.removeRecipe(currentRecipe);
            chefIconUnChosen.setVisibility(View.VISIBLE);
            chefIconChosen.setVisibility(View.INVISIBLE);
            HomePageActivity.showSnackBar();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        if (getCount() > 0)
            return getCount();
        else
            return super.getViewTypeCount();
    }
}
