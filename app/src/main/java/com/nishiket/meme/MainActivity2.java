package com.nishiket.meme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity2 extends AppCompatActivity {
    Animation pop;
    ImageView logo;

    @Override
    // This is flash screen activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //set animation on logo
        pop= AnimationUtils.loadAnimation(this,R.anim.pop);
        logo=findViewById(R.id.pic);
        // passing Inten to MainActivity
        Intent go = new Intent(MainActivity2.this,MainActivity.class);
        logo.setAnimation(pop);// this will start animation
        //hold screen for 3sec
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(go);
                finish();
            }
        },3000);
    }
}