package com.example.hindwar.linksaver;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Hindwar on 6/11/2017.
 */

public class SaverWall extends Activity {

    TextView msg, info;
    Button post;
    LinearLayout li;
    ScrollView scrollview;
    String url, imgUrl, des, title, textInfo;
    LinearLayout.LayoutParams layoutParams;
    protected void onCreate(Bundle rekha){
        super.onCreate(rekha);
        setContentView(R.layout.SaverWall);
        initialize();
        Bundle gotBasket = getIntent().getExtras();
        url = gotBasket.getString("url");
        imgUrl = gotBasket.getString("imgURL");
        des = gotBasket.getString("des");
        if(des.length() > 30)
            des = des.substring(0, 29);
        title = gotBasket.getString("title");
        textInfo = url + "\n" + title + "\n";
        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        post.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                msg.setBackgroundColor(Color.GRAY);
                msg.setWidth(100);
                Spanned span2 = Html.fromHtml(imgUrl,getImageHTML(),null);
                msg.setText(textInfo+span2+"\n"+des);
                li.addView(msg, layoutParams);
            }
        });
    }
    public Html.ImageGetter getImageHTML() {

        Html.ImageGetter imageGetter = new Html.ImageGetter() {
            public Drawable getDrawable(String source) {
                try {
                    Drawable drawable = Drawable.createFromStream(new URL(source).openStream(), "src");
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());

                    return drawable;
                } catch(IOException exception) {
                    Log.v("IOException", exception.getMessage());
                    return null;
                }
            }
        };

        return imageGetter;
    }
    private void initialize() {
        info = (TextView) findViewById(R.id.info);
        post = (Button) findViewById(R.id.post);
        li = (LinearLayout) findViewById(R.id.li);
        scrollview = (ScrollView)findViewById(R.id.scrollview);
    }

}
