package com.nishiket.meme;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.AbstractCollection;


public class MainActivity extends AppCompatActivity {
    // make global variable so it can be access to all function
    ImageView img; // image variable
    TextView txt; // text variable
    ProgressBar p; // progress bar variable
    AppCompatButton shb,nxb; // shb: share button nxb: next button
    String curl = null; // api url variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Finding Id
        img = findViewById(R.id.img);
        txt = findViewById(R.id.txt);
        p = findViewById(R.id.load);
        nxb = findViewById(R.id.btnnext);
        shb = findViewById(R.id.btnshare);

        // make a API call for first time when app is launched
        call(img, txt, p);

        // on next button click action
        nxb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // calling API call function
                call(img, txt, p);
            }
        });

        // on share button click action
        shb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // passing a implicit Intent
                Intent ishare = new Intent(Intent.ACTION_SEND);
                // type of intent passing
                ishare.setType("text/plain");
                //massage to pass
                ishare.putExtra(Intent.EXTRA_TEXT, "Here a funny meme for you:" + curl);
                // will start pass intent
                startActivity(ishare);
            }
        });


    } // on create is over


    // API call function which accept image,text and progressbar view
    // volley library
    private void call(ImageView img,TextView txt,ProgressBar p) {
        // make progress bar visible and image visible gone and set text "LOADING"
        p.setVisibility(View.VISIBLE);
        img.setVisibility(View.GONE);
        txt.setText("LOADING...");

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://meme-api.com/gimme"; //GET API url to hit

        // Request a string response from the provided URL which return an json object.
        JsonObjectRequest JsonObjectRequst = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response){
                        String name= null; // name variable to store name of meme author
                        try {
                            curl = response.getString("url"); // get url of meme in string format
                            name = response.getString("author"); // get name of author in string format
                        } catch (JSONException e) {
                            throw new RuntimeException(e); // Run time error
                        }
                        // using Glide library to set url image in image view
                        Glide.with(MainActivity.this).load(curl).listener(new RequestListener<Drawable>() {
                            // this method will execute during glide download and set image
                            @Override
                            // if it get failed
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                // make image visible and progressbar visility gone
                                img.setVisibility(View.VISIBLE);
                                p.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            // if it get successes
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                // make image visible and progressbar visility gone
                                img.setVisibility(View.VISIBLE);
                                p.setVisibility(View.GONE);
                                return false;
                            }
                        }).into(img);// glide ends here

                        // make image visible and set text to author name we got
                        img.setVisibility(View.VISIBLE);
                        txt.setText("Author : "+name);
                    }
                }, new Response.ErrorListener() {
            @Override
            // if API do not hit
            public void onErrorResponse(VolleyError error) {
                // make progressbar visibility gone and set text
                p.setVisibility(View.GONE);
                txt.setText("CLICK ON NEXT BUTTON TO REFRASH");
                // make toast for user to notify
                Toast.makeText(MainActivity.this, "Faild! Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

    // Add the request to the RequestQueue.
        queue.add(JsonObjectRequst);
    }
}