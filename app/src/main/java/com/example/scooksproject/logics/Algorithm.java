package com.example.scooksproject.logics;

import com.example.scooksproject.Ingredient;
import com.example.scooksproject.Instruction;
import com.example.scooksproject.Recipe;

import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Algorithm {

    //TODO לשים עשרים שניות הפרש בין הסיום של הסטופר לבין סיום שלב הביניים




    public static Recipe scooksAlgorithm(List<Recipe> recipeList)
    {

        Recipe resRecipe=null;
        List<Recipe> notUsedRecipesList=new LinkedList<>(recipeList);
        List<Instruction> instructionList=runAlgorithm(notUsedRecipesList);
        List<Ingredient> ingredientList=getIngredientsFromAllRecipe(recipeList);
        String timeOfWorkNeeded=getTimeWorkAllRecipes(instructionList);
        String totalTimeRecipe=getTotalTimeAllRecipes(instructionList);
        String difficultLevel=getDifficultLevelAllRecipes(recipeList);
        List<String> instructionListStr=getStrFormInstructionList(instructionList);

        resRecipe=new Recipe("תוכנית עבודה",timeOfWorkNeeded,totalTimeRecipe,difficultLevel,ingredientList,instructionListStr,null ,instructionList,0,0,0);
        return resRecipe;
    }

    private static List<String> getStrFormInstructionList(List<Instruction> instructionList) {
      List<String> res=new LinkedList<>();
        for (Instruction instr:instructionList) {
            res.add(instr.getContent());
        }
        return res;
    }

    private static String getDifficultLevelAllRecipes(List<Recipe> recipeList) {
        Dictionary<String, Integer> dict=createDifficultLevelDict();
        Dictionary<Integer, String> opsDict=createDifficultLevelDictOps();
        int difficultArr[]=new int[recipeList.size()];
        for (int i=0; i< recipeList.size(); i++) {
            if(dict.get(recipeList.get(i).getDifficultLevel())!=null)
               difficultArr[i]=dict.get(recipeList.get(i).getDifficultLevel());
            else
                difficultArr[i]=1;
        }
        int max=maxInArr(difficultArr);

        return opsDict.get(max);
    }
    private static int maxInArr(int arr[])
    {
        int max=arr[0];
        for(int i=0; i<arr.length; i++)
        {
            max=Math.max(max,arr[i]);
        }
        return max;
    }
    private static Dictionary<String,Integer> createDifficultLevelDict() {
        Dictionary<String, Integer> dict = new Hashtable<>();

        dict.put("כל אחד יכול",1);
        dict.put("בינוני",2);
        dict.put("נדרשת מיומנות",3);

        return dict;
    }
    private static Dictionary<Integer, String> createDifficultLevelDictOps() {
        Dictionary<Integer, String> dict = new Hashtable<>();

        dict.put(1,"כל אחד יכול");
        dict.put(2,"בינוני");
        dict.put(3,"נדרשת מיומנות");

        return dict;
    }
    private static String getTotalTimeAllRecipes( List<Instruction> instructionList) {

        return null;
    }

    private static String convertTimeToString(int hours, int minutes) {
        String res="";
        switch (hours)
        {
            case 0:
                res+=minutes+" דקות ";
                break;
            case 1:
                res+=" שעה";
                if(minutes!=0) {
                    res += " ו- " +minutes+" דקות ";
                }
                break;
            default:
                res+=hours;
                res+=" שעות ";
                if(minutes!=0) {
                    res += " ו- " +minutes+" דקות ";
                }
                break;
        }
        return res;
    }

    private static String getTimeWorkAllRecipes( List<Instruction> instructionList) {
       int timeWorkAllRecipes=0,hours,minutes;
        for (Instruction inst:instructionList) {
            timeWorkAllRecipes+=inst.getWorkTime();
        }
        hours=timeWorkAllRecipes/60;
        minutes=timeWorkAllRecipes-(hours*60);
        return convertTimeToString(hours,minutes);
    }

    private static List<Instruction> runAlgorithm(List<Recipe> notUsedRecipeList)
    {
        List<Instruction> resInstructionList=new LinkedList<>();
      while(notUsedRecipeList.size()>1)
      {
          Recipe root=getMaxFreeTimeRecipe(notUsedRecipeList);
          handleAllInstructions(root.getRecipeInstructions(),notUsedRecipeList,resInstructionList);
          notUsedRecipeList.remove(root);
      }
      if(notUsedRecipeList.size()==1) {
          resInstructionList.addAll(notUsedRecipeList.get(0).getRecipeInstructions());
          notUsedRecipeList.remove(notUsedRecipeList.get(0));
      }
      return resInstructionList;
    }
    //הפונקציה שעוברת על כל ההורואת הנחה ומפעילה את המעטפת על הההורואת עם זמן המתנה
    public static void handleAllInstructions(List<Instruction> instructionList,List<Recipe> notUsedRecipeList,List<Instruction> resInstruction)
    {
        for (Instruction instr:instructionList) {
            handleSingleInstruction(instr,resInstruction,notUsedRecipeList);
        }
    }
    private static void handleSingleInstruction(Instruction instr,List<Instruction> resInstruction,List<Recipe> notUsedRecipeList)
    {
        resInstruction.add(instr);
        if(instr.getFreeTime()>0)
        {
            helper(instr.getFreeTime(), notUsedRecipeList,resInstruction);
        }
    }

    private static void helper(int freeTime , List<Recipe> notUsedRecipe,List<Instruction> resInstruction) {

        if(notUsedRecipe.size()==0)
        {
            return;
        }
        List<Recipe> knapsackCandidate=  knapsackHelper(freeTime,notUsedRecipe);
        if(knapsackCandidate==null)
            return;
        Recipe maxFreeTimeRecipe=getMaxFreeTimeRecipe(knapsackCandidate);
        if(maxFreeTimeRecipe.getTotalFreeTime()==0)
        {
            chooseRecipe(knapsackCandidate,resInstruction,notUsedRecipe);

            return;
        }
        else
        {
            //we used this recipe
            notUsedRecipe.remove(maxFreeTimeRecipe);

            Instruction instr=null;
            int size=maxFreeTimeRecipe.getRecipeInstructions().size();

            for (int i=0;i<size;i++) {

                instr=maxFreeTimeRecipe.getRecipeInstructions().get(i);

                if(i!=size-1)
                {
                   handleSingleInstruction(instr,resInstruction,notUsedRecipe);
                }
                else
                {
                    resInstruction.add(instr);
                    if(instr.getFreeTime()>0) {
                        int newFreeTime = freeTime - (maxFreeTimeRecipe.getPreparationTime() - instr.getFreeTime());

                        helper(newFreeTime,notUsedRecipe,resInstruction);
                    }
                }
            }
        }


    }

    private static void chooseRecipe(List<Recipe> knapsackCandidate, List<Instruction> resInstruction,List<Recipe> notUsedRecipe) {
        for (Recipe recipe:knapsackCandidate) {
            resInstruction.addAll(recipe.getRecipeInstructions());
            notUsedRecipe.remove(recipe);
        }
    }

    public static List<Recipe> knapsackHelper(int freeTime , List<Recipe> notUsedRecipe)
    {
       Item[] items=createItemFromRecipeList(notUsedRecipe);
        Knapsack knapsack = new Knapsack(items, freeTime);
        knapsack.display();
        Solution solution = knapsack.solve();
        solution.display();

        List<Recipe> solutionsRecipe=null;
        if(solution.value>0) {
            solutionsRecipe = convertSolutionToRecipeList(solution, notUsedRecipe);
        }
        return solutionsRecipe;
    }

    private static List<Recipe> convertSolutionToRecipeList(Solution solution,List<Recipe> notUsedRecipe) {

        List<Recipe> recipeList=new LinkedList<>();
        String name=null;
        for (int i=0; i<solution.items.size(); i++)
        {
            name=solution.items.get(i).name;

            recipeList.add(getRecipeByName(notUsedRecipe,name));
        }

       return recipeList;
    }

    private static Recipe getRecipeByName(List<Recipe> notUsedRecipe,String name) {

        Recipe resRecipe=null;

        for (Recipe recipe:notUsedRecipe) {
            if(recipe.getName().equals(name))
                resRecipe=recipe;
        }

        return resRecipe;
    }

    private static Item[] createItemFromRecipeList(List<Recipe> notUsedRecipe) {
        Item[] items= new Item[notUsedRecipe.size()];
        Recipe recipe=null;
        for (int i=0; i<notUsedRecipe.size(); i++) {
            recipe=notUsedRecipe.get(i);
            items[i]= new Item(recipe.getName(), recipe.getTotalFreeTime(), recipe.getPreparationTime());
        }
        return items;
    }

    public static List<Ingredient> getIngredientsFromAllRecipe(List<Recipe> recipeList)
    {
        List<Ingredient> listRes = new LinkedList<>();
        for (Recipe rec:recipeList) {
            listRes.addAll(rec.getIngredients());
        }
        return listRes;
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
}
