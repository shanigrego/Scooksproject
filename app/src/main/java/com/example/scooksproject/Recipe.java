package com.example.scooksproject;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.List;

public class Recipe implements Serializable {

    private String name;
    private String timeOfWorkNeededStr;
    private String totalTimeRecipeStr;
    private String difficultLevel;
    private List<Ingredient> ingredients;

    private List<String> recipeInstructionsStr;


    private Bitmap recipeImg;

    private List<Instruction> recipeInstructions;
    private double timeOfWorkNeeded;
    private double totalFreeTime;
    private boolean isUsed;

    //Required for firebase
    //Do not erase!!!
    public Recipe() {
    }

    public double getTotalFreeTime() {
        return totalFreeTime;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public Recipe(String name, String timeOfWorkNeededStr, String totalTimeRecipe, String difficultLevel,
                  List<Ingredient> ingredients, List<String> recipeInstructionsStr/*, Bitmap recipeImg*/) {
        this.name = name;
        this.timeOfWorkNeededStr = timeOfWorkNeededStr;
        this.totalTimeRecipeStr = totalTimeRecipe;
        this.difficultLevel = difficultLevel;
        this.ingredients = ingredients;
        this.recipeInstructionsStr = recipeInstructionsStr;
        this.recipeImg = recipeImg;
        this.isUsed=false;
    }

    public String getName() {
        return name;
    }

    public List<Instruction> getRecipeInstructions() {
        return recipeInstructions;
    }

    public double getTimeOfWorkNeeded() {
        return timeOfWorkNeeded;
    }

    public String getTimeOfWorkNeededStr() {
        return timeOfWorkNeededStr;
    }
    public String getTotalTimeRecipeStr() {
        return totalTimeRecipeStr;
    }

    public String getDifficultLevel() {
        return difficultLevel;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<String> getRecipeInstructionsStr() {
         return recipeInstructionsStr;
    }
    public Bitmap getRecipeImg() { return recipeImg; }

    @Override
    public String toString() {
        return "Recipe{" +
                "name='" + name + '\'' +
                ", timeOfWorkNeeded='" + timeOfWorkNeededStr + '\'' +
                ", totalTimeRecipe='" + totalTimeRecipeStr + '\'' +
                ", difficultLevel='" + difficultLevel + '\'' +
                ", ingredients=" + ingredients +
                ", recipeInstructions=" + recipeInstructionsStr +
                '}';
    }
}
