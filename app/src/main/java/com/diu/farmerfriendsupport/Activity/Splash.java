package com.diu.farmerfriendsupport.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.diu.farmerfriendsupport.R;

import java.util.Objects;

import static java.lang.Thread.sleep;

public class Splash extends AppCompatActivity {

    static Splash splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splash=this;
        Thread thread= new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(1000);
                    Intent i = new Intent(Splash.this,MainActivity.class);
                    startActivity(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public static Splash getInstance(){
        return   splash;
    }
}