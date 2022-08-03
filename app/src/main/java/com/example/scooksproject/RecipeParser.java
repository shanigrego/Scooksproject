package com.example.scooksproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import androidx.annotation.RequiresApi;

import com.example.scooksproject.Exceptions.NoNumberBeforeHoursException;
import com.example.scooksproject.Exceptions.NoNumberBeforeMinutesException;


public class RecipeParser extends AsyncTask<String, Void, String> {



    //call this function from other classes
    public void parseAllRecipes() {
        execute("https://www.mako.co.il/food-cooking_magazine/food-store/Recipe-910ee37d7ebfc31006.htm?sCh=c7250a2610f26110&pId=1595820704"
        ,"https://www.mako.co.il/food-recipes/recipes_column-hospitality/Recipe-36c292336036931006.htm?Partner=interlink"
      ,"https://www.mako.co.il/food-recipes/recipes_column-salads/Recipe-261a96650645c71026.htm"
       ,"https://www.mako.co.il/food-cooking_magazine/mazola-recipes/Recipe-a6d3937a7418151006.htm?partner=obarticle");
    }

    //parse a single recipe
    public void parseSingleRecipe(String recipeUrl) {
        execute(recipeUrl);
    }

    private static ArrayList<Recipe> allRecipes = new ArrayList<>();
    private Exception exception;

    public static ArrayList<Recipe> getAllRecipes() {
        return allRecipes;
    }

    public static void uploadRecipe(Recipe recipe) {
        DataBase db = DataBase.getInstance();
        db.uploadRecipe(recipe);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected String doInBackground(String... urls) {

        for (String url:urls) {
            Recipe recipe= null;
            try {
                recipe = getRecipeFromUrl(url);
            } catch (NoNumberBeforeMinutesException | NoNumberBeforeHoursException e) {
                e.printStackTrace();
            }
            allRecipes.add(recipe);
            uploadRecipe(recipe);
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private Recipe getRecipeFromUrl(String urlRecipe) throws NoNumberBeforeMinutesException,NoNumberBeforeHoursException {
        Document doc = getDocumentAccordingToUrl(urlRecipe);
        String recipeName = doc.title().split(":")[1];
        List<Ingredient> listOfIngredients = createIngredientListFromDoc(doc);
        Elements titleContainer = doc.getElementsByClass("titleContainer");

        String workTimeStr =titleContainer.get(0).childNodes().get(0).childNodes().get(1).childNodes().get(0).toString();
        String preparationTimeStr = titleContainer.get(1).childNodes().get(0).childNodes().get(1).childNodes().get(0).toString();
        String difficultLevel = titleContainer.get(2).childNodes().get(0).childNodes().get(1).childNodes().get(0).toString();

        Elements image=doc.getElementsByClass("imgInside");
        String imageUrlString= image.get(0).childNode(1).attributes().get("src");
        URL urlImg = null;
        Bitmap bmp = null;
        try {
            if(imageUrlString.isEmpty()){
                imageUrlString = "https://unsplash.com/photos/08bOYnH_r_E";
            }
            urlImg = new URL(imageUrlString);
            bmp = BitmapFactory.decodeStream(urlImg.openConnection().getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> recipeInstructionsStr = getRecipeInstructions(doc);
        int workTime=getTimeOfWork(workTimeStr);
        List<Instruction> recipeInstructionList = convertListStringToInstructionList(recipeInstructionsStr,workTime);
        int totalFreeTime=getFreeTime(recipeInstructionList);
        int preparationTime=getPreparationTime(recipeInstructionList);

        Recipe recipe = new Recipe(recipeName, workTimeStr, preparationTimeStr, difficultLevel, listOfIngredients, recipeInstructionsStr, imageUrlString,recipeInstructionList,workTime,totalFreeTime,preparationTime);
        return recipe;
    }

    public static int getPreparationTime(List<Instruction> recipeInstructionList) {
    int res=0;
        for (Instruction instr:recipeInstructionList) {
            res+=instr.getFreeTime()+instr.getWorkTime();
        }
        return res;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<String> getRecipeInstructions(Document doc) {
        Elements recipeInstructions = doc.getElementsByClass("recipeInstructions ArticleText fontSize");
        recipeInstructions = recipeInstructions.get(0).children();
        recipeInstructions.removeIf(element -> (element.tagName() != "p"));
        recipeInstructions.removeIf(element -> !element.childNodes().get(0).toString().contains("small"));

        List<String> instructionsList = new LinkedList<>();
        for (Element element : recipeInstructions) {
            instructionsList.add(element.childNodes().get(1).toString());
        }
        return instructionsList;
    }


    private List<Ingredient> createIngredientListFromDoc(Document doc) {
        Elements ingredients = doc.getElementsByClass("ingredients");
        Element ingred = ingredients.get(0);
        List<String> listOfIngredientsString = new LinkedList<>();
        for (int i = 1; i < ingred.childNodes().size(); i += 2) {

            if (ingred.childNodes().get(i).toString().contains("recipeIngredients")) {
                List<Node> list = ingred.childNodes().get(i).childNodes();

                for (int j = 1; j < list.size(); j += 2) {

                    String val = list.get(j).childNodes().get(0).childNodes().get(0).toString();
                    listOfIngredientsString.add(val);
                    Log.d("Ingredient", val);
                }
            }

        }
        return createIngredientList(listOfIngredientsString);
    }

    private Document getDocumentAccordingToUrl(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }

    private List<Ingredient> createIngredientList(List<String> ingredientsStringList) {
        List<Ingredient> list = new LinkedList<>();

        for (String ingred : ingredientsStringList) {
            list.add(parseStringToIngredient(ingred));
        }
        return list;
    }

    private Ingredient parseStringToIngredient(String ingredientAsString) {

        double amount = 0;
        String measureUnit = "", name = "";
        int i = 0;
        Set<String> measureUnitDict = createMeasureUnitDict();
        Dictionary<String, Double> amountStrToDoubleDict = createAmountToDoubleDict();
        String[] arraySplit = ingredientAsString.split(" ");
        amount+=handleCharBetweenNumbers(arraySplit[0]);
        if(amount!=0)
        {
            i++;
        }
        if (isAllNumbers(arraySplit[i]))
        {
            amount = Double.parseDouble(arraySplit[i]);
            i++;
        }
        else {
            while (amountStrToDoubleDict.get(arraySplit[i]) != null) {
                amount += amountStrToDoubleDict.get(arraySplit[i]);
                i++;
            }
        }

        while ((measureUnitDict.contains(arraySplit[i]) || (arraySplit[i].contains("מ")&& arraySplit[i].contains("ל")))&& (i< arraySplit.length)) {

            if(measureUnitDict.contains(arraySplit[i]))
            {
                measureUnit += arraySplit[i];
                i++;
            }
            else if(arraySplit[i].length()==3 && !arraySplit[i].equals("מלח")) {
                    measureUnit += "מל";
                     i++;
                }
            else
                break;
        }
        while (amountStrToDoubleDict.get(arraySplit[i]) != null && i< arraySplit.length) {
            amount += amountStrToDoubleDict.get(arraySplit[i]);
            i++;
        }
        if (amount == 0) {
            amount = 1;
        }
        for (; i < arraySplit.length; i++) {
            if (arraySplit[i].contains("(")) {
                while (i < arraySplit.length && !arraySplit[i].contains(")"))
                       i++;
                i++;
            }
            if (i < arraySplit.length)
                name += arraySplit[i] + " ";
        }
        Log.d("ing", "name: " + name + "\n amount: " + amount + "\n measureUnit: " + measureUnit);
        Ingredient ingredient = new Ingredient(name, amount, measureUnit);
        return ingredient;
    }

    private Dictionary<String, Double> createAmountToDoubleDict() {
        Dictionary<String, Double> dict = new Hashtable<>();
        dict.put("וחצי", 1.5);
        dict.put("ורבע", 1.25);
        dict.put("ושלושת", 1.75);
        dict.put("שלושת", 0.75);
        dict.put("חצי", 0.5);
        dict.put("רבע", 0.25);
        dict.put("1/2", 0.5);
        dict.put("½", 0.5);

        return dict;
    }
    private static boolean isNumber(char ch) {
        boolean res = false;
        if (ch >= '0' && ch <= '9') {
            res = true;
        }
        return res;
    }

    private Set<String> createMeasureUnitDict() {
        Set<String> DictionaryUnitOfMeasure = new HashSet<>();
        DictionaryUnitOfMeasure.add("כפות");
        DictionaryUnitOfMeasure.add("כף");
        DictionaryUnitOfMeasure.add("כפית");
        DictionaryUnitOfMeasure.add("גרם");
        DictionaryUnitOfMeasure.add("קילו");
        DictionaryUnitOfMeasure.add("כפיות");
        DictionaryUnitOfMeasure.add("כוס");
        DictionaryUnitOfMeasure.add("כוסות");
        DictionaryUnitOfMeasure.add("פחית");
        DictionaryUnitOfMeasure.add("מכל");
        DictionaryUnitOfMeasure.add("מיכל");
        DictionaryUnitOfMeasure.add("מל");
        return DictionaryUnitOfMeasure;
    }

    public static int getFreeTime(List<Instruction> instList){
        int sumFreeTime = 0;

        for (Instruction inst : instList) {
            sumFreeTime += inst.getFreeTime();
        }
        return sumFreeTime;
    }


    public static List<Instruction> convertListStringToInstructionList(List<String> recipeInstructionsStr, int timeWorkNeeded) throws NoNumberBeforeMinutesException,NoNumberBeforeHoursException {

        List<Instruction> list = new LinkedList<>();

        int instructionWorkTime = timeWorkNeeded / recipeInstructionsStr.size();
        for (int i=0; i<recipeInstructionsStr.size(); i++) {

            list.add(getInstructionFromStr(recipeInstructionsStr.get(i),instructionWorkTime,i+1));
        }

        return list;
    }

    private static Instruction getInstructionFromStr(String content,int instructionWorkTime,int index) throws NoNumberBeforeMinutesException,NoNumberBeforeHoursException {

        List<String> timeUnitList=createTimeUnitList();
        String[] splitContent=content.split(" ");
        String part=null;
        int freeTimeWork=0;

        for(int j=0; j<arr.length; j++)
            arr[j]=0;

        for (int i=0; i< splitContent.length;i++) {
            part=splitContent[i];
            for (String timeUnit:timeUnitList) {
                if(part.contains(timeUnit))
                {
                    setTimeInstructionInArr(splitContent,i,timeUnit);
                }
            }
        }
        for(int i=0;i< arr.length; i++ )
        {
            freeTimeWork+=arr[i];
        }
        if(freeTimeWork<10)
            freeTimeWork=0;


        return new Instruction(index,content,instructionWorkTime,freeTimeWork);
    }

    private static String handlePrefix(String str)
    {
        if((str.charAt(0)=='כ'|| str.charAt(0)=='ל') && str.charAt(1)=='-')
        {
            str=str.substring(2);
        }
        return str;
    }
    private static int handleCharBetweenNumbers(String str)
    {
        int number=0;
        String[] numbers=null;
        if(str.contains("-"))
        {
            numbers=str.split("-");
            number=Math.max(Integer.parseInt(numbers[0]),Integer.parseInt(numbers[1]));
        }

        return number;
    }
    static int[] arr=new int[4];

    private static void setTimeInstructionInArr(String[] splitContent, int i, String part) throws NoNumberBeforeMinutesException,NoNumberBeforeHoursException {

      int freeTime=0;
        Dictionary<String,Integer> dict=createTimeInstructionDict();
        String str=null,strAfter=null;
        switch (part) {
            case "דקות":
                //רק לפני
                str = splitContent[i - 1];
                str = handlePrefix(str);
                freeTime = handleCharBetweenNumbers(str);
                if (freeTime == 0) {
                    if (isAllNumbers(str)) {
                        freeTime = Integer.parseInt(str);
                    }
                    else if(dict.get(str)!=null)
                    {
                        freeTime+= dict.get(str);
                    }
                    else if (str.charAt(0) == 'ו') {
                        str = str.substring(1);
                        if (str.charAt(0) == '-')
                            str = str.substring(1);
                        if(dict.get(str)!=null) {
                            freeTime += dict.get(str);
                        }
                        if(dict.get(splitContent[i-2])!=null)
                        {
                        freeTime += dict.get(splitContent[i - 2]);
                        }
                    }
                }
                if(freeTime==0)
                {
                  throw new NoNumberBeforeMinutesException();
                }
                arr[0] = Math.max(arr[0], freeTime);
                freeTime = arr[0];
                break;
            case "שעות":
                // ואחרי לפני
                str = splitContent[i - 1];
                str = handlePrefix(str);
                freeTime = handleCharBetweenNumbers(str);
                if(freeTime==0) {
                    if (isAllNumbers(str)) {
                        freeTime = Integer.parseInt(str) * 60;
                    }
                }
                if (i + 1 < splitContent.length) {
                    strAfter = splitContent[i + 1];
                    if (strAfter.charAt(0) == 'ו')
                    {
                        strAfter = strAfter.substring(1);
                        if (strAfter.charAt(0) == '-')
                            strAfter = strAfter.substring(1);
                        if (dict.get(strAfter)!=null)
                            freeTime += dict.get(strAfter);
                    }
                }
                if(freeTime==0)
                {
                    throw new NoNumberBeforeHoursException();
                }

                arr[1] = Math.max(arr[1], freeTime);
                break;
            case "שעה":
                //גם לפני
                str = splitContent[i - 1];
                str = handlePrefix(str);
                if(dict.get(str)!=null) {
                    freeTime = dict.get(str);
                }
                if (str.equals("רבע") && i > 2 && splitContent[i - 2].equals("שלושת")) {
                    freeTime += 45;
                }
                if (i + 1 < splitContent.length) {
                    strAfter = splitContent[i + 1];
                    if (strAfter.charAt(0) == 'ו')
                    {
                        strAfter = strAfter.substring(1);
                        if (strAfter.charAt(0) == '-')
                            strAfter = strAfter.substring(1);
                        if (dict.get(strAfter)!=null)
                            freeTime += dict.get(strAfter)+60;
                     }
                }
                if(freeTime==0)
                {
                    freeTime=60;
                }
                arr[2] = Math.max(arr[2], freeTime);
                break;
            case "שעתיים":
                if (i + 1 < splitContent.length) {
                    strAfter = splitContent[i + 1];
                    if(dict.get(strAfter)!=null)
                        freeTime = dict.get(strAfter);
                    freeTime += 120;
                }
                arr[3] = Math.max(arr[3], freeTime);
                break;

        }
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

    private static List<String> createTimeUnitList() {
        List<String> timeUnitList=new LinkedList<>();

        timeUnitList.add("דקות");
        timeUnitList.add("שעות");
        timeUnitList.add("שעה");
        timeUnitList.add("שעתיים");

        return timeUnitList;
    }

    private static Dictionary<String,Integer> createTimeStringToDoubleDict() {
        Dictionary<String, Integer> dict = new Hashtable<>();

        dict.put("חצי שעה",30);
        dict.put("שעה",60);
        dict.put("שלושת רבע שעה",105);
        dict.put("רבע שעה",15);
        dict.put("שעה ורבע",75);
        dict.put("שעה וחצי",90);
        dict.put("שעה וארבעים דקות",100);
        dict.put("שעה ועשרים דקות",80);

        dict.put("ארבעים דקות",40);
        dict.put("ארבעים וחמש דקות",45);
        dict.put("עשרים דקות",20);
        dict.put("חמש דקות",5);
        dict.put("עשר דקות",10);

        dict.put("שעתיים",120);
        dict.put("שעתיים וחצי",150);
        dict.put("שלוש שעות",180);
        dict.put("שלוש וחצי שעות",210);


        return dict;
    }
    private static Dictionary<String,Integer> createTimeInstructionDict() {
        Dictionary<String, Integer> dict = new Hashtable<>();

        dict.put("חצי",30);
        dict.put("כמה",2);
        dict.put("לכמה",2);
        dict.put("כחמש",5);
        dict.put("כעשר",5);
        dict.put("עשר",5);


        dict.put("שלושת רבע",45);
        dict.put("רבע",15);
        dict.put("שלושים",30);
        dict.put("ארבעים",40);
        dict.put("עשרים",20);

        return dict;
    }
    public static int getTimeOfWork(String timeString) {

        if (timeString.startsWith("עד"))
        {
            timeString=timeString.substring(2);
        }

        return createTimeStringToDoubleDict().get(timeString);
    }
}