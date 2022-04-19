package com.example.scooksproject.logics;

import com.example.scooksproject.Instruction;
import com.example.scooksproject.Recipe;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Algorithm {

    private class AlgoRecipe
    {
        private List<Instruction> instructionsList;
        private double totalFreeTime;
        private double totalWorkTime;

    }

    public static Recipe scooksAlgorithm(List<Recipe> recipeList)
    {
        List<AlgoRecipe> algoRecipeList=new LinkedList<>();
        for (Recipe recipe:recipeList) {
            algoRecipeList.add(convertRecipeToAlgoRecipe(recipe));


        }



        return null;
    }
    private static AlgoRecipe convertRecipeToAlgoRecipe(Recipe recipe)
    {
        double timeOfWork=getTimeOfWork(recipe.getTimeOfWorkNeeded());
        List<Instruction> listOfInstructions=convertListStringToInstructionList(recipe.getRecipeInstructions(),recipe.getTotalTimeRecipe(),timeOfWork);


      return null;
    }

    private static List<Instruction> convertListStringToInstructionList(List<String> recipeInstructionsStr, String totalTime, double preperationTime)
    {

        List<Instruction> list = new LinkedList<>();

        double instructionPrepareTime = preperationTime / recipeInstructionsStr.size();
        for (String content : recipeInstructionsStr) {

              list.add(getInstructionFromStr(content));
        }

        return list;
    }

    private static Instruction getInstructionFromStr(String content) {

     //   List<String> timeUnitList=createTimeUnitDict();
       // for (String unit:timeUnitList) {
         //   if(content.contains(unit))
           // {
             //   content.split(unit);
            //}

        //}

        return null;
    }

    private static Dictionary<String,Double> createTimeStringToDoubleDict() {
        Dictionary<String, Double> dict = new Hashtable<>();
        dict.put("חצי שעה",30.0);
        dict.put( "שעה",60.0);
        dict.put("שלושת רבע שעה",105.0);
        dict.put("רבע שעה",15.0);
        dict.put("שעה ורבע",75.0);
        dict.put("שעה וחצי",90.0);
        dict.put("שעה וארבעים דקות",100.0);
        dict.put("שעה ועשרים דקות",80.0);

        dict.put("ארבעים דקות",40.0);
        dict.put("ארבעים וחמש דקות",45.0);
        dict.put("עשרים דקות",20.0);
        dict.put("חמש דקות",5.0);
        dict.put("עשר דקות",10.0);

        dict.put("שעתיים",120.0);
        dict.put("שעתיים וחצי",150.0);
        dict.put("שלוש שעות",180.0);
        dict.put("שלוש וחצי שעות",210.0);


        return dict;
    }
    private static double getTimeOfWork(String timeString) {

        if (timeString.startsWith("עד"))
        {
            timeString=timeString.substring(2);
        }

        return createTimeStringToDoubleDict().get(timeString);
    }
}
