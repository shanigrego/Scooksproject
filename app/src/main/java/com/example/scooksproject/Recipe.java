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
    private int timeOfWorkNeeded;
    private int totalFreeTime;
    private int preparationTime;

    //Required for firebase
    //Do not erase!!!
    public Recipe() {
    }

    public void setTimeOfWorkNeeded(int timeOfWorkNeeded) {
        this.timeOfWorkNeeded = timeOfWorkNeeded;
    }

    public void setTotalFreeTime(int totalFreeTime) {
        this.totalFreeTime = totalFreeTime;
    }

    public int getTotalFreeTime() {
        return totalFreeTime;
    }
    public int getPreparationTime()
    {
        return this.preparationTime;
    }

    public void setInstructions(List<Instruction> recipeInstructions) {
        this.recipeInstructions = recipeInstructions;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public Recipe(String name, String timeOfWorkNeededStr, String totalTimeRecipe, String difficultLevel,
                  List<Ingredient> ingredients, List<String> recipeInstructionsStr/*, Bitmap recipeImg*/, List<Instruction> recipeInstructions, int timeOfWorkNeeded, int totalFreeTime, int preparationTime) {
        this.name = name;
        this.timeOfWorkNeededStr = timeOfWorkNeededStr;
        this.totalTimeRecipeStr = totalTimeRecipe;
        this.difficultLevel = difficultLevel;
        this.ingredients = ingredients;
        this.recipeInstructionsStr = recipeInstructionsStr;
        this.recipeImg = recipeImg;

        this.recipeInstructions=recipeInstructions;
        this.timeOfWorkNeeded=timeOfWorkNeeded;
        this.totalFreeTime=totalFreeTime;
        this.preparationTime=preparationTime;
    }


    public String getName() {
        return name;
    }

    public List<Instruction> getRecipeInstructions() {
        return recipeInstructions;
    }

    public int getTimeOfWorkNeeded() {
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
