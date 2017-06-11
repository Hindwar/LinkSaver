package com.example.hindwar.linksaver;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;



public class CustomMethod extends Activity {


    TextView urlText;
    Button button;
    String url;
    String sourceCode;
    String siteTitle;
    String description = null;
    String imageTag = null;
    LinearLayout.LayoutParams layoutParams;
    LinearLayout ll;
    static int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        urlText = (TextView) findViewById(R.id.urlText);
        button = (Button) findViewById(R.id.post);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                url = urlText.getText().toString();
                //System.out.println("URL-> " + url);
                Ion.with(getApplicationContext()).load(url).asString().setCallback(new FutureCallback<String>() {

                    public void onCompleted(Exception e, String result) {
                        sourceCode = result.replaceAll("\n"," ");
                        System.out.println("URL!!!! = "+sourceCode);
                        int siteTitleBeginIndex = sourceCode.indexOf("<title");
                        sourceCode = sourceCode.substring(siteTitleBeginIndex);
                        siteTitleBeginIndex = sourceCode.indexOf(">");
                        sourceCode = sourceCode.substring(siteTitleBeginIndex+1);
                        int siteTitleEndIndex = sourceCode.indexOf("</title>");
                        siteTitle = sourceCode.substring(0, siteTitleEndIndex);
                        System.out.println("siteTitle = " + siteTitle);
                        String dsourceCode = result.replaceAll("\n"," ");
                        String imgSourceCode = result.replaceAll("\n"," ");
                        while(description == null) {
                            //System.out.println("inside while");
                            if(!dsourceCode.contains("<p>"))
                                break;
                            int descriptionBeginIndex = dsourceCode.indexOf("<p>");
                            //dsourceCode = dsourceCode.substring(descriptionBeginIndex+3);
                            int descriptionEndIndex = dsourceCode.indexOf("</p>");
                            description = dsourceCode.substring(descriptionBeginIndex+3, descriptionEndIndex);
                            dsourceCode = dsourceCode.substring(descriptionEndIndex+4);

                                if (description.contains("<"))
                                    description = null;


                        }
                        //System.out.println("exit while");
                        if(description == null)
                            description = "No description found.";
                        System.out.println("description : " + description);
                        do{
                            //System.out.println("inside do");
                            if(!imgSourceCode.contains("<img"))
                                break;
                            int imageBeginIndex = imgSourceCode.indexOf("<img");
                            imgSourceCode = imgSourceCode.substring(imageBeginIndex);
                            imageBeginIndex = Math.min(imgSourceCode.indexOf('"',imgSourceCode.indexOf("src")),imgSourceCode.indexOf("'",imgSourceCode.indexOf("src")));
                            int imageEndIndex = Math.min(imgSourceCode.indexOf('"',imageBeginIndex+1),imgSourceCode.indexOf("'",imageBeginIndex+1));
                            imageTag = imgSourceCode.substring(imageBeginIndex+1,imageEndIndex);
                            imgSourceCode = imgSourceCode.substring(imageEndIndex);
                            //
                        }while(!filter(imageTag));
                        System.out.println("image url : " + imageTag);

                        Bundle basket = new Bundle();
                        basket.putString("imgURL",imageTag );
                        basket.putString("title", siteTitle);
                        basket.putString("url",url);
                        basket.putString("des",description);
                        Intent a = new Intent(CustomMethod.this, Test.class);
                        a.putExtras(basket);
                        startActivity(a);
                    }
                });
            }
        });
    }


    private boolean filter(String imageUrl) {
        String site = imageUrl.substring(imageUrl.indexOf('.')+1, imageUrl.indexOf("/",imageUrl.indexOf('.')+1));
        if(imageUrl.contains(site) && !imageUrl.contains("logo") && !imageUrl.contains("icon")){
            return true;
        }
       return false;
    }
}
