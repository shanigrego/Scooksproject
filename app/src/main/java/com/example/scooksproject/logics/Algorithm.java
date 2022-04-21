package com.example.scooksproject.logics;

import com.example.scooksproject.Ingredient;
import com.example.scooksproject.Instruction;
import com.example.scooksproject.Recipe;
import com.google.android.gms.common.internal.constants.ListAppsActivityContract;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

public class Algorithm {

    //TODO לשים עשרים שניות הפרש בין הסיום של הסטופר לבין סיום שלב הביניים
    //TODO לטפל במצב שבו יש - בין מספרים


    public static Recipe scooksAlgorithm(List<Recipe> recipeList)
    {
        Recipe resRecipe=null;
        List<Ingredient> ingredientList=getIngredientsFromAllRecipe(recipeList);
        List<Instruction> instructionList=runAlgorithm(recipeList);

        List<Recipe> notUsedRecipesList=new LinkedList<>(recipeList);
        Recipe maxFreeTimeRecipe = getMaxFreeTimeRecipe(recipeList);


        resRecipe=new Recipe();
        return resRecipe;
    }
    private static List<Instruction> runAlgorithm(List<Recipe> recipeList)
    {



    return null;
    }
    public static List<Ingredient> getIngredientsFromAllRecipe(List<Recipe> recipeList)
    {
        List<Ingredient> list = new LinkedList<>();


        return list;
    }
    private static Recipe getMaxFreeTimeRecipe(List<Recipe> recipeList) {

        double max=recipeList.get(0).getTotalFreeTime();
        Recipe maxRecipe=recipeList.get(0);

        for (Recipe recipe:recipeList) {

            if(recipe.getTotalFreeTime() > max)
            {
                maxRecipe=recipe;
                max=recipe.getTotalFreeTime();
            }
        }
        return maxRecipe;
    }

    private static double getFreeTime(List<Instruction> instList){
        double sumFreeTime = 0;

        for (Instruction inst : instList) {
            sumFreeTime += inst.getFreeTime();
        }
        return sumFreeTime;
    }

//    private static AlgoRecipe convertRecipeToAlgoRecipe(Recipe recipe)
//    {
//        double timeOfWork=getTimeOfWork(recipe.getTimeOfWorkNeededStr());
//        List<Instruction> listOfInstructions=convertListStringToInstructionList(recipe.getRecipeInstructionsStr(),recipe.getTotalTimeRecipeStr(),timeOfWork);
//        double freeTime=getFreeTime(listOfInstructions);
//
//      return null;
//    }

    private static List<Instruction> convertListStringToInstructionList(List<String> recipeInstructionsStr, String totalTime, double preperationTime)
    {

        List<Instruction> list = new LinkedList<>();

        double instructionPrepareTime = preperationTime / recipeInstructionsStr.size();
        for (String content : recipeInstructionsStr) {

              list.add(getInstructionFromStr(content,instructionPrepareTime));
        }

        return list;
    }

    private static Instruction getInstructionFromStr(String content,double instructionPrepareTime) {

        List<String> timeUnitList=createTimeUnitList();
        String[] splitContent=content.split(" ");
        String part=null;
        double freeTimeWork=0;
        for (int i=0; i< splitContent.length;i++) {
            part=splitContent[i];
            for (String timeUnit:timeUnitList) {
                if(part.equals(timeUnit))
                {
                  freeTimeWork+=getTimeInstruction(splitContent,i,part);
                }

            }

        }

        for (String unit:timeUnitList) {
            if(content.contains(unit))
            {
                content.split(unit);
            }

        }
        return null;
    }

    private static String handlePrefix(String str)
    {
        if(str.startsWith("-כ"))
        {
            str=str.substring(2);
        }
        return str;
    }
    private static double handleCharBetweenNumbers(String str)
    {
        double number=0;
        String[] numbers=null;
        if(str.contains("-"))
        {
            numbers=str.split("-");
            number=Math.min(Integer.parseInt(numbers[0]),Integer.parseInt(numbers[1]));
        }

        return number;
    }
    private static double getTimeInstruction(String[] splitContent, int i, String part) {

        double freeTime=0;
        Dictionary<String,Double> dict=createTimeInstructionDict();
        String str=null;
        switch (part)
        {
          case "דקות":
              //רק לפני
              str=splitContent[i-1];
              str=handlePrefix(str);
              freeTime=handleCharBetweenNumbers(str);
              if(freeTime == 0)
              {
                  if(isAllNumbers(str))
                  {
                    freeTime=Integer.parseInt(str);
                  }
                  else if(str.startsWith("ו"))
                  {
                   freeTime+= dict.get(str);
                   freeTime+=dict.get(splitContent[i-2]);
                  }
              }
              break;
          case "שעות":
              // רק לפני
              str=splitContent[i-1];
              str=handlePrefix(str);
              if(isAllNumbers(str))
              {
                  freeTime=Integer.parseInt(str);
              }
              break;
          case "שעה":
              //גם לפני

              break;
          case "שעתיים":

              break;

      }
      return freeTime;
    }

    private static boolean isAllNumbers(String str) {

        boolean res=true;
        for (int i=0; i< str.length(); i++) {
            if(!isNumber(str.charAt(i)))
            {
                res=false;
            }
        }
        return res;
    }

    private static boolean isNumber(char ch) {
        boolean res = false;
        if (ch >= '0' && ch <= '9') {
            res = true;
        }
        return res;
    }

    private static List<String> createTimeUnitList() {
        List<String> timeUnitList=new LinkedList<>();

        timeUnitList.add("דקות");
        timeUnitList.add("שעות");
        timeUnitList.add("שעה");
        timeUnitList.add("שעתיים");

    return timeUnitList;
    }

    private static Dictionary<String,Double> createTimeStringToDoubleDict() {
        Dictionary<String, Double> dict = new Hashtable<>();

        dict.put("חצי שעה",30.0);
        dict.put("שעה",60.0);
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
    private static Dictionary<String,Double> createTimeInstructionDict() {
        Dictionary<String, Double> dict = new Hashtable<>();

        dict.put("חצי",30.0);
        dict.put("וחצי",30.0);
        dict.put("כמה",0.0);
        dict.put("שלושת רבע",45.0);
        dict.put("ושלושת רבע",45.0);
        dict.put("רבע",15.0);
        dict.put("ורבע",15.0);
        dict.put("שלושים",30.0);
        dict.put("ושלושים",30.0);
        dict.put("ארבעים",40.0);
        dict.put("וארבעים",40.0);
        dict.put("ועשרים",20.0);
        dict.put("עשרים",20.0);

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
