package com.example.scooksproject;

import androidx.appcompat.app.AppCompatActivity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    private Button startBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startBtn = findViewById(R.id.startBtn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Document doc = Jsoup.connect("https://www.wikipedia.org/").get();
//                String docTitle=doc.title();
//                startBtn.setText(docTitle);
//                Elements newsHeadlines = doc.select("#mp-itn b a");
//                for (Element headline : newsHeadlines) {
//                    docTitle=headline.attr("title");
//                    startBtn.setText(docTitle);
//                    docTitle=headline.absUrl("href");
//                    startBtn.setText(docTitle);
//                }
                Intent intent = new Intent(MainActivity.this, HomePage.class);
                startActivity(intent);
            }
        });
    }
}