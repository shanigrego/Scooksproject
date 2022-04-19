package com.example.scooksproject;

import android.graphics.Bitmap;

import org.jsoup.select.Elements;

import java.io.Serializable;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class Recipe implements Serializable {

    private String name;
    private String timeOfWorkNeeded;
    private String totalTimeRecipe;
    private String difficultLevel;
    private List<Ingredient> ingredients;
    private List<String> recipeInstructions;
    private Bitmap recipeImg;

    //Required for firebase
    //Do not erase!!!
    public Recipe() {
    }

    public Recipe(String name, String timeOfWorkNeeded, String totalTimeRecipe, String difficultLevel,
                  List<Ingredient> ingredients, List<String> recipeInstructions/*, Bitmap recipeImg*/) {
        this.name = name;
        this.timeOfWorkNeeded = timeOfWorkNeeded;
        this.totalTimeRecipe = totalTimeRecipe;
        this.difficultLevel = difficultLevel;
        this.ingredients = ingredients;
        this.recipeInstructions = recipeInstructions;
        this.recipeImg = recipeImg;
    }

    public String getName() {
        return name;
    }

    public String getTimeOfWorkNeeded() {
        return timeOfWorkNeeded;
    }
    public String getTotalTimeRecipe() {
        return totalTimeRecipe;
    }

    public String getDifficultLevel() {
        return difficultLevel;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<String> getRecipeInstructions() {
         return recipeInstructions;
    }
    public Bitmap getRecipeImg() { return recipeImg; }

    @Override
    public String toString() {
        return "Recipe{" +
                "name='" + name + '\'' +
                ", timeOfWorkNeeded='" + timeOfWorkNeeded + '\'' +
                ", totalTimeRecipe='" + totalTimeRecipe + '\'' +
                ", difficultLevel='" + difficultLevel + '\'' +
                ", ingredients=" + ingredients +
                ", recipeInstructions=" + recipeInstructions +
                '}';
    }
}
