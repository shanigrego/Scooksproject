package com.example.scooksproject;

import org.jsoup.select.Elements;

import java.util.List;

public class Recipe {

    private String name;
    private String timeOfWorkNeeded;
    private String totalTimeRecipe;
    private String difficultLevel;
    private List<Ingredient> ingredients;
    private Elements recipeInstructions;

    public Recipe(String name, String timeOfWorkNeeded, String totalTimeRecipe, String difficultLevel,
                    List<Ingredient> ingredients, Elements recipeInstructions) {
        this.name=name;
        this.timeOfWorkNeeded=timeOfWorkNeeded;
        this.totalTimeRecipe=totalTimeRecipe;
        this.difficultLevel=difficultLevel;
        this.ingredients=ingredients;
        this.recipeInstructions=recipeInstructions;
    }



}
