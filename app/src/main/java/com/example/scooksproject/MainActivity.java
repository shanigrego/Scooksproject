package com.example.scooksproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;


import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;




public class MainActivity extends AppCompatActivity {

    private Button btn;
    private Button nextPageBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }

    class RetrieveFeedTask extends AsyncTask<String, Void, String> {

        private Exception exception;

        @RequiresApi(api = Build.VERSION_CODES.N)
        protected String doInBackground(String... urls) {
            Document doc = null;
            try {
                doc = Jsoup.connect("https://www.mako.co.il/food-cooking_magazine/food-store/Recipe-490bcc516297d31006.htm?sCh=c7250a2610f26110&pId=1595820704?referrer=https%3A%2F%2Fwww.mako.co.il%2Ffood%3Fpartner%3DSecondNav&referrer=https%3A%2F%2Fwww.mako.co.il%2Ffood-cooking_magazine%2Fquiche_recipes%2FRecipe-490bcc516297d31006.htm%3FsCh%3Dc7250a2610f26110%26pId%3D1595820704").get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String recipeName = doc.title();

            Elements ingerderins =doc.getElementsByClass("recipeIngredients");
           // btn.setText(docTitle);
           Element el=ingerderins.get(0);
           List<Node> list= el.childNodes();
           List<String>  listOfIngredients =new LinkedList<String>();
            for (int i =1; i<list.size(); i+=2) {

                String val=list.get(i).childNodes().get(0).childNodes().get(0).toString();
                listOfIngredients.add(val);
                Log.d("Ingredient", val);
            }
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

        protected void onPostExecute(String feed) {
            // TODO: check this.exception
            // TODO: do something with the feed
        }
    }

    private void initComponents(){
        btn = findViewById(R.id.buttonTry);
        nextPageBtn = findViewById(R.id.startBtn);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RetrieveFeedTask().execute("https://www.wikipedia.org/");
            }
        });

        nextPageBtn.setOnClickListener(new View.OnClickListener() {
            Intent intent = new Intent(MainActivity.this, HomePage.class);
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }

}