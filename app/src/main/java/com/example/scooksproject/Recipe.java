package com.example.scooksproject;

import android.graphics.Bitmap;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.List;

public class Recipe implements Serializable {

    private String name;
    private String timeOfWorkNeededStr;
    private String totalTimeRecipeStr;
    private String difficultLevel;
    private List<Ingredient> ingredients;
    private List<String> recipeInstructionsStr;
    private List<Instruction> recipeInstructions;
    private int timeOfWorkNeeded;
    private int totalFreeTime;
    private int preparationTime;
    //@Exclude
//    private Bitmap recipeImg;
    private String recipeImg;

    //Required for firebase
    //Do not erase!!!
    public Recipe() {
    }

    public void setTimeOfWorkNeeded(int timeOfWorkNeeded) {
        this.timeOfWorkNeeded = timeOfWorkNeeded;
    }

    public Recipe(String name, String timeOfWorkNeededStr, String totalTimeRecipe, String difficultLevel,
                  List<Ingredient> ingredients, List<String> recipeInstructionsStr, String recipeImg,
                  List<Instruction> recipeInstructions, int timeOfWorkNeeded, int totalFreeTime, int preparationTime) {
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

    public int getTotalFreeTime() {
        return totalFreeTime;
    }

    public int getPreparationTime()
    {
        return this.preparationTime;
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

    public List<Instruction> getRecipeInstructions() {
        return recipeInstructions;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

//    public Bitmap getRecipeImg() { return recipeImg; }
    public String getRecipeImg() { return recipeImg; }

//    public void setRecipeImg(Bitmap recipeImg) { this.recipeImg = recipeImg; }
    public void setRecipeImg(String recipeImg) { this.recipeImg = recipeImg; }

    public List<String> getRecipeInstructionsStr() {
         return recipeInstructionsStr;
    }

    public void setTotalFreeTime(int totalFreeTime) {
        this.totalFreeTime = totalFreeTime;
    }

    public void setInstructions(List<Instruction> recipeInstructions) { this.recipeInstructions = recipeInstructions; }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

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
