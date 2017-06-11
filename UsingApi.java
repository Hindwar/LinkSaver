package com.example.hindwar.linksaverapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class UsingApi extends AppCompatActivity {

    TextView urlText;
    Button button;
    String siteUrl;
    String sourceCode;
    String keywords;
    String description = null;
    String imageTag = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        urlText = (TextView) findViewById(R.id.urlText);
        button = (Button) findViewById(R.id.post);


        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                siteUrl = urlText.getText().toString();
                Document doc = null;
                try {
                    doc = Jsoup.connect(siteUrl).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                final String title = doc.title();
                System.out.println("Title : " + title);
                keywords = doc.select("meta[name=keywords]").first().attr("content");
                System.out.println("Meta keyword : " + keywords);
                description = doc.select("meta[name=description]").get(0).attr("content");
                System.out.println("Meta description : " + description);
                Elements images = doc.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
                String site = siteUrl.substring(siteUrl.indexOf('.')+1,siteUrl.indexOf('/',siteUrl.indexOf('.')));
                //Element requiredImage;
                String src = null;
                for (Element image : images){

                    if(src.contains(site) && !src.contains("logo") && !src.contains("icon")){
                        src = image.attr("src");
                        break;

                    }
                }
                Bundle basket = new Bundle();
                basket.putString("imgURL", src);
                basket.putString("title", title);
                basket.putString("url",siteUrl);
                basket.putString("des",description);
                Intent a = new Intent(UsingApi.this, Test.class);
                a.putExtras(basket);
                startActivity(a);
            }
        });
    }


}
