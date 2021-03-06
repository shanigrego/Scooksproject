package com.example.scooksproject.logics;

import com.example.scooksproject.Ingredient;
import com.example.scooksproject.Instruction;
import com.example.scooksproject.Recipe;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

public class Algorithm {

    //TODO לשים עשרים שניות הפרש בין הסיום של הסטופר לבין סיום שלב הביניים


    public static Recipe scooksAlgorithm(List<Recipe> recipeList) {

        Recipe resRecipe = null;
        updateRecipeNameForInstructions(recipeList);
        List<Recipe> notUsedRecipesList = new LinkedList<>(recipeList);
        List<Instruction> instructionList=new LinkedList<>();
        int totalFreeTime=runAlgorithm(notUsedRecipesList,instructionList);
        List<Ingredient> ingredientList = getIngredientsFromAllRecipe(recipeList);
        int timeOfWorkNeededInt = getTimeWorkAllRecipes(instructionList);
        String timeOfWorkNeeded=convertTimeToString(timeOfWorkNeededInt);
        int totalTimeRecipeInt=timeOfWorkNeededInt+totalFreeTime;
        String totalTimeRecipe = convertTimeToString(totalTimeRecipeInt);
        String difficultLevel = getDifficultLevelAllRecipes(recipeList);
        List<String> instructionListStr = getStrFormInstructionList(instructionList);

        resRecipe = new Recipe("תוכנית עבודה", timeOfWorkNeeded, totalTimeRecipe, difficultLevel, ingredientList, instructionListStr, null, instructionList, 0, 0, 0);
        return resRecipe;
    }

    private static void updateRecipeNameForInstructions(List<Recipe> recipeList) {

        for (Recipe rec:recipeList) {
            for (Instruction inst:rec.getRecipeInstructions()) {
                inst.setRecipeName(rec.getName());
            }
        }
    }

    private static List<String> getStrFormInstructionList(List<Instruction> instructionList) {
        List<String> res = new LinkedList<>();
        for (Instruction instr : instructionList) {
            res.add(instr.getContent());
        }
        return res;
    }

    private static String getDifficultLevelAllRecipes(List<Recipe> recipeList) {
        Dictionary<String, Integer> dict = createDifficultLevelDict();
        Dictionary<Integer, String> opsDict = createDifficultLevelDictOps();
        int difficultArr[] = new int[recipeList.size()];
        for (int i = 0; i < recipeList.size(); i++) {
            if (dict.get(recipeList.get(i).getDifficultLevel()) != null)
                difficultArr[i] = dict.get(recipeList.get(i).getDifficultLevel());
            else
                difficultArr[i] = 1;
        }
        int max = maxInArr(difficultArr);

        return opsDict.get(max);
    }

    private static int maxInArr(int arr[]) {
        int max = arr[0];
        for (int i = 0; i < arr.length; i++) {
            max = Math.max(max, arr[i]);
        }
        return max;
    }

    private static Dictionary<String, Integer> createDifficultLevelDict() {
        Dictionary<String, Integer> dict = new Hashtable<>();

        dict.put("כל אחד יכול", 1);
        dict.put("בינוני", 2);
        dict.put("נדרשת מיומנות", 3);

        return dict;
    }

    private static Dictionary<Integer, String> createDifficultLevelDictOps() {
        Dictionary<Integer, String> dict = new Hashtable<>();

        dict.put(1, "כל אחד יכול");
        dict.put(2, "בינוני");
        dict.put(3, "נדרשת מיומנות");

        return dict;
    }

    private static String convertTimeToString(int time) {
        int hours = time / 60;
        int minutes = time - (hours * 60);
        String res = "";
        switch (hours) {
            case 0:
                res += minutes + " דקות ";
                break;
            case 1:
                res += " שעה";
                if (minutes != 0) {
                    res += " ו- " + minutes + " דקות ";
                }
                break;
            default:
                res += hours;
                res += " שעות ";
                if (minutes != 0) {
                    res += " ו- " + minutes + " דקות ";
                }
                break;
        }
        return res;
    }

    private static int getTimeWorkAllRecipes(List<Instruction> instructionList) {
        int timeWorkAllRecipes = 0, hours, minutes;
        for (Instruction inst : instructionList) {
            timeWorkAllRecipes += inst.getWorkTime();
        }
        return timeWorkAllRecipes;
    }

    private static int runAlgorithm(List<Recipe> notUsedRecipeList,List<Instruction> resInstructionList) {
        int totalFreeTimeWasted=0;
        while (notUsedRecipeList.size() > 1) {
            Recipe root = getMaxFreeTimeRecipe(notUsedRecipeList);
            notUsedRecipeList.remove(root);
            List<Instruction> rootInstruction = root.getRecipeInstructions();
            totalFreeTimeWasted+=handleInstructions(rootInstruction, notUsedRecipeList, resInstructionList);
            Instruction lastInst = rootInstruction.get(rootInstruction.size() - 1);
            resInstructionList.add(lastInst);
            if (lastInst.getFreeTime() > 0) {
                totalFreeTimeWasted+=runAlgorithm(notUsedRecipeList,resInstructionList);
            }
        }
        if (notUsedRecipeList.size() == 1) {
            resInstructionList.addAll(notUsedRecipeList.get(0).getRecipeInstructions());
            totalFreeTimeWasted+=notUsedRecipeList.get(0).getTotalFreeTime();
            notUsedRecipeList.remove(notUsedRecipeList.get(0));

        }
        return totalFreeTimeWasted;
    }

    //הפונקציה שעוברת על כל ההורואת הנחה ומפעילה את המעטפת על הההורואת עם זמן המתנה
    public static int handleInstructions(List<Instruction> instructionList, List<Recipe> notUsedRecipeList, List<Instruction> resInstruction) {
//        for (Instruction instr:instructionList) {
//            handleSingleInstruction(instr,resInstruction,notUsedRecipeList);
//        }
        int sumFreeTimeWasted=0;
        for (int i = 0; i < instructionList.size() - 1; i++) {
            sumFreeTimeWasted+=handleSingleInstruction(instructionList.get(i), resInstruction, notUsedRecipeList);
        }
        return sumFreeTimeWasted;
    }

    private static int handleSingleInstruction(Instruction instr, List<Instruction> resInstruction, List<Recipe> notUsedRecipeList) {
        resInstruction.add(instr);
        int freeTimeWasted=0;
        if (instr.getFreeTime() > 0) {
            freeTimeWasted= helper(instr.getFreeTime(), notUsedRecipeList, resInstruction);
        }
        return freeTimeWasted;
    }

    private static int helper(int freeTime, List<Recipe> notUsedRecipe, List<Instruction> resInstruction) {
        int freeTimeWasted=0;
        if (notUsedRecipe.size() == 0) {
            return freeTime;
        }
        List<Recipe> knapsackCandidate = knapsackHelper(freeTime, notUsedRecipe);
        if (knapsackCandidate == null)
            return freeTime;
        Recipe maxFreeTimeRecipe = getMaxFreeTimeRecipe(knapsackCandidate);
        if (maxFreeTimeRecipe.getTotalFreeTime() == 0) {
            chooseRecipe(knapsackCandidate, resInstruction, notUsedRecipe);
            return 0;
        }
        else {
            //we used this recipe
            notUsedRecipe.remove(maxFreeTimeRecipe);

            Instruction instr = null;
            int size = maxFreeTimeRecipe.getRecipeInstructions().size();
            freeTimeWasted = handleInstructions(maxFreeTimeRecipe.getRecipeInstructions(), notUsedRecipe, resInstruction);
            instr = maxFreeTimeRecipe.getRecipeInstructions().get(size - 1);
            resInstruction.add(instr);
            if (instr.getFreeTime() > 0) {
                int newFreeTime = freeTime - (maxFreeTimeRecipe.getPreparationTime() - instr.getFreeTime());
                return helper(newFreeTime, notUsedRecipe, resInstruction) + freeTimeWasted;
            }
            return freeTimeWasted;
        }
//            for (int i=0;i<size;i++) {
//
//                instr=maxFreeTimeRecipe.getRecipeInstructions().get(i);
//
//                if(i!=size-1)
//                {
//                   handleSingleInstruction(instr,resInstruction,notUsedRecipe);
//                }
//                else
//                 {
//                    resInstruction.add(instr);
//                    if(instr.getFreeTime()>0) {
//                        int newFreeTime = freeTime - (maxFreeTimeRecipe.getPreparationTime() - instr.getFreeTime());
//
//                        helper(newFreeTime,notUsedRecipe,resInstruction);
//                    }
//                }
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
