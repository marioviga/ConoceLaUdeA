package com.example.samirvega.practica2actividades;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import java.util.Timer;
import java.util.TimerTask;

public class  SplashActivity extends Activity {

    private static final long SPLASH_DELAY = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);



        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                Intent i = new Intent().setClass(SplashActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, SPLASH_DELAY);

    }
}
