package com.nishiket.meme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.io.File;

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
                // get the cache file and call delete method
                File dir = MainActivity2.this.getCacheDir();
                deleteDir(dir);
                // go to the new activity
                startActivity(go);
                finish();
            }
        },3000);
    }

    // delete dir method
    private static boolean deleteDir(File dir) {
        if(dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
                return dir.delete();}
            else if(dir !=null && dir.isFile()){
                return dir.delete();
            }
            else{
                return false;
            }
        }
    }