package com.example.scooksproject;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import androidx.annotation.RequiresApi;


//TODO:1.כל מצרך צריך להפריד לכמות, יחידת מידה ושם המצרך
// 2. אופן ההכנה
public class RecipeParser extends AsyncTask<String, Void, String> {


    //call this function from other classes


    public void parseAllRecipes() {
        parseSingleRecipe("https://www.wikipedia.org/");
    }

    //parse a single recipe
    public void parseSingleRecipe(String recipeUrl) {
        execute(recipeUrl);
    }


    private Exception exception;

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected String doInBackground(String... urls) {



        Document doc = getRecipeAccordingToUrl("https://www.mako.co.il/food-shavuot/Recipe-bab935b63871b61026.htm?sCh=426d1e17c1f26310&pId=1595820704");
        String recipeName = doc.title();
        List<Ingredient> listOfIngredients=createIngredientListFromDoc(doc);

        Elements titleContainer = doc.getElementsByClass("titleContainer");
        String timeOfWorkNeeded = titleContainer.get(0).childNodes().get(0).childNodes().get(1).childNodes().get(0).toString();
        String totalTimeRecipe = titleContainer.get(1).childNodes().get(0).childNodes().get(1).childNodes().get(0).toString();
        String difficultLevel = titleContainer.get(2).childNodes().get(0).childNodes().get(1).childNodes().get(0).toString();


        Elements recipeInstructions =getRecipeInstructions(doc);



    return null;

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private Elements getRecipeInstructions(Document doc)
    {
        Elements recipeInstructions = doc.getElementsByClass("recipeInstructions ArticleText fontSize");
        recipeInstructions=recipeInstructions.get(0).children();
        recipeInstructions.removeIf(element -> (element.tagName() != "p"));
        recipeInstructions.removeIf(element -> !element.childNodes().get(0).toString().contains("small"));

        return recipeInstructions;
    }
    private List<Ingredient> createIngredientListFromDoc(Document doc) {
        Elements ingredients = doc.getElementsByClass("ingredients");
        Element  ingred= ingredients.get(0);
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

    private Document getRecipeAccordingToUrl(String url)
    {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }
    private List<Ingredient> createIngredientList(List<String> ingredientsStringList) {
       List<Ingredient> list=new LinkedList<>();

        for (String ingred : ingredientsStringList) {
            list.add( parseStringToIngredient(ingred));
        }
        return list;
    }

    private Ingredient parseStringToIngredient(String ingredientAsString) {

        double amount = 0;
        String measureUnit = "", name = "";
        int i = 0;
        Set<String> measureUnitDict = createMeasureUnitDict();
        //Set<String> measureAmount = createMeasureAmountDict();
        Dictionary<String, Double> amountStrToDoubleDict = createAmountToDoubleDict();
        String[] arraySplit = ingredientAsString.split(" ");
        if (isNumber(arraySplit[i].charAt(0))|| arraySplit[i].charAt(0) == '½') {
            if( arraySplit[i].charAt(0) == '½')
                amount = 0.5;
            else
                amount = Double.parseDouble(arraySplit[0]);
            i++;
        } else {
            while (amountStrToDoubleDict.get(arraySplit[i])!=null) {
                amount += amountStrToDoubleDict.get(arraySplit[i]);
                i++;
            }
        }
        while (measureUnitDict.contains(arraySplit[i])) {

            measureUnit += arraySplit[i];
            i++;
        }
        while (amountStrToDoubleDict.get(arraySplit[i])!=null) {
            amount += amountStrToDoubleDict.get(arraySplit[i]);
            i++;
        }
        if(amount == 0){
            amount = 1;
        }
        for (; i < arraySplit.length; i++) {
            if(arraySplit[i].contains("(")){
                while( i<arraySplit.length && !arraySplit[i].contains(")"))
                    i++;
                i++;
            }
            if(i<arraySplit.length)
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
        return dict;
    }

    private Set<String> createMeasureAmountDict() {
        Set<String> DictionaryUnitOfMeasure = new HashSet<>();
        //List<String> DictionaryUnitOfMeasure= new LinkedList<>();
        DictionaryUnitOfMeasure.add("חצי");
        DictionaryUnitOfMeasure.add("רבע");
        DictionaryUnitOfMeasure.add("שלושת");
        DictionaryUnitOfMeasure.add("רבעי");
        DictionaryUnitOfMeasure.add("אחד");
        DictionaryUnitOfMeasure.add("וחצי");
        DictionaryUnitOfMeasure.add("ושלושת");
        return DictionaryUnitOfMeasure;
    }

    private boolean isNumber(char ch) {
        boolean res = false;
        if (ch >= '0' && ch <= '9') {
            res = true;
        }
        return res;
    }

    private Set<String> createMeasureUnitDict() {
        Set<String> DictionaryUnitOfMeasure = new HashSet<>();
        //List<String> DictionaryUnitOfMeasure= new LinkedList<>();
        DictionaryUnitOfMeasure.add("כפות");
        DictionaryUnitOfMeasure.add("כף");
        DictionaryUnitOfMeasure.add("כפית");
        DictionaryUnitOfMeasure.add("גרם");
        DictionaryUnitOfMeasure.add("כפיות");
        DictionaryUnitOfMeasure.add("כוס");
        DictionaryUnitOfMeasure.add("כוסות");
        DictionaryUnitOfMeasure.add("פחית");
        DictionaryUnitOfMeasure.add("מכל");
        DictionaryUnitOfMeasure.add("מיכל");
        return DictionaryUnitOfMeasure;
    }
}