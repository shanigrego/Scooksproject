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
import java.util.LinkedList;
import java.util.List;

import androidx.annotation.RequiresApi;


//TODO:1.כל מצרך צריך להפריד לכמות, יחידת מידה ושם המצרך
// 2. אופן ההכנה
public class RecipeParser extends AsyncTask<String, Void, String> {



    //call this function from other classes
    public void parseAllRecipes(){
        parseSingleRecipe("https://www.wikipedia.org/");
    }

    //parse a single recipe
    public void parseSingleRecipe(String recipeUrl){
        execute(recipeUrl);
    }

    private Exception exception;

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected String doInBackground(String... urls) {


        Document doc = null;
        try {
            doc = Jsoup.connect("https://www.mako.co.il/food-holiday-recipes/sukkot-recipes/Article-7f96f603fd95841006.htm").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String recipeName = doc.title();

        Elements ingerderins =doc.getElementsByClass("recipeIngredients");
        Element el=ingerderins.get(0);
        List<Node> list= el.childNodes();
        List<String>  listOfIngredients =new LinkedList<>();
        for (int i =1; i<list.size(); i+=2) {

            String val=list.get(i).childNodes().get(0).childNodes().get(0).toString();
            listOfIngredients.add(val);
            Log.d("Ingredient", val);
        }
        createIngredientList(listOfIngredients);
        //TODO forloop: titleContainer.get(i).childNodes().get(0).childNodes().get(1).childNodes().get(0).toString();
        //i keep it that way because is more readable
        // doesnt work with kosher in the forloop need a special treat

        Elements titleContainer=doc.getElementsByClass("titleContainer");
        String timeOfWorkNeeded=titleContainer.get(0).childNodes().get(0).childNodes().get(1).childNodes().get(0).toString();
        String totalTimeRecipe=titleContainer.get(1).childNodes().get(0).childNodes().get(1).childNodes().get(0).toString();
        String difficultLevel=titleContainer.get(2).childNodes().get(0).childNodes().get(1).childNodes().get(0).toString();
        String Kosher=titleContainer.get(3).childNodes().get(0).childNodes().get(2).childNodes().get(0).toString();

        Elements recipeInstructions=doc.getElementsByClass("recipeInstructions ArticleText fontSize");

        List<Node> levelBetwwen=recipeInstructions.get(0).childNodes();

        Elements children=recipeInstructions.get(0).children();
        children.removeIf(element -> (element.tagName()!="p"));

        return null;
    }

    private List<Ingredient> createIngredientList(List<String> ingredientsStringList){
        for (String ingred : ingredientsStringList){
            Ingredient ingredient = parseStringToIngredient(ingred);
        }
        return null;
    }

    private Ingredient parseStringToIngredient(String ingredientAsString){

        double amount=1;
        String measureUnit="",name="";
        int i=0;
        String[] ArraySplit=ingredientAsString.split(" ");
        if(isNumber(ArraySplit[i].charAt(0)))
        {
           amount=Double.parseDouble(ArraySplit[0]);
           i++;
        }
        while(isMeasureUnit(ArraySplit[i]))
        {
            measureUnit+=ArraySplit[i];
            i++;
        }
        for (;i<ArraySplit.length; i++)
        {
            name+=ArraySplit[i]+" ";
        }

        Ingredient ingredient=new Ingredient(name,amount,measureUnit);
        return ingredient;
    }
    private boolean isNumber(char ch)
    {
        boolean res=false;
        if(ch>='0' &&ch<='9') {
            res = true;
        }
        return res;
    }
    private boolean isMeasureUnit(String str)
    {
        List<String> DictionaryUnitOfMeasure= new LinkedList<>();
        DictionaryUnitOfMeasure.add("כפות");
        DictionaryUnitOfMeasure.add("כף");
        DictionaryUnitOfMeasure.add("כפית");
        DictionaryUnitOfMeasure.add("גרם");
        DictionaryUnitOfMeasure.add("כפיות");
        DictionaryUnitOfMeasure.add("כוס");
        DictionaryUnitOfMeasure.add("כוסות");
        DictionaryUnitOfMeasure.add("פחית");
        boolean res=false;

        for (String unit :DictionaryUnitOfMeasure) {
            if(str.equals(unit))
            {
                res=true;
                break;
            }
        }

        return res;
    }



}