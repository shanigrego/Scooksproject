package com.example.scooksproject;

import androidx.appcompat.app.AppCompatActivity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;



import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn=(Button) findViewById(R.id.buttonTry);
        btn.setOnClickListener(this::playOnClick);
    }


    public void playOnClick(View view)
    {
        try {
            onClick(view);
        }
        catch (IOException ex)
        {
            btn.setText(ex.getMessage());
        }
    }
    public void onClick(View view) throws IOException
    {
        Document doc = Jsoup.connect("https://www.wikipedia.org/").get();
        String docTitle=doc.title();
        btn.setText(docTitle);
        Elements newsHeadlines = doc.select("#mp-itn b a");
        for (Element headline : newsHeadlines) {
            docTitle=headline.attr("title");
            btn.setText(docTitle);
            docTitle=headline.absUrl("href");
            btn.setText(docTitle);
        }
    }

}