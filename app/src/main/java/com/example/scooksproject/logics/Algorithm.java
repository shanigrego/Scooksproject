package com.example.scooksproject.logics;

import com.example.scooksproject.Ingredient;
import com.example.scooksproject.Instruction;
import com.example.scooksproject.Recipe;
import com.google.android.gms.common.internal.constants.ListAppsActivityContract;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

public class Algorithm {

    //TODO לשים עשרים שניות הפרש בין הסיום של הסטופר לבין סיום שלב הביניים




    public static Recipe scooksAlgorithm(List<Recipe> recipeList)
    {

        Recipe resRecipe=null;
        List<Ingredient> ingredientList=getIngredientsFromAllRecipe(recipeList);

        List<Recipe> notUsedRecipesList=new LinkedList<>(recipeList);
        List<Instruction> instructionList=runAlgorithm(notUsedRecipesList);
        //Recipe maxFreeTimeRecipe = getMaxFreeTimeRecipe(recipeList);

        resRecipe=new Recipe();
        return resRecipe;
    }
    private static List<Instruction> runAlgorithm(List<Recipe> notUsedRecipeList)
    {
        List<Instruction> resInstructionList=new LinkedList<>();
      while(notUsedRecipeList.size()!=1)
      {
          Recipe root=getMaxFreeTimeRecipe(notUsedRecipeList);
          handleAllInstructions(root.getRecipeInstructions(),notUsedRecipeList,resInstructionList);
          notUsedRecipeList.remove(root);
      }
        resInstructionList.addAll(notUsedRecipeList.get(0).getRecipeInstructions());
        notUsedRecipeList.remove(notUsedRecipeList.get(0));
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
//        Item[] items=new Item[2];
//        items[0]=new Item("recipe1",30,50);
//        items[1]=new Item("recipe1",30,50);
//        Knapsack knapsack = new Knapsack(items, 46);
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
}
