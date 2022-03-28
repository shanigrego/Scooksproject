package com.example.scooksproject;

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


import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;




public class MainActivity extends AppCompatActivity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.buttonTry);
        btn.setOnClickListener(this::playOnClick);
    }


    public void playOnClick(View view) {
        try {
            onClick(view);
        } catch (IOException ex) {
            btn.setText(ex.getMessage());
        }
    }

    public void onClick(View view) throws IOException {

        new RetrieveFeedTask().execute("https://www.wikipedia.org/");
    }

    class RetrieveFeedTask extends AsyncTask<String, Void, String> {

        private Exception exception;

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
            }
            //Elements titleContainer=doc.getElementsByClass("titleContainer");


            return null;
        }

        protected void onPostExecute(String feed) {
            // TODO: check this.exception
            // TODO: do something with the feed
        }
    }

}