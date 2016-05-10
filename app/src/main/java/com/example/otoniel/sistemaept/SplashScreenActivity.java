package com.example.otoniel.sistemaept;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Otoniel on 06/03/2016.
 */
public class SplashScreenActivity extends Activity {
    private static final long SPLASH_SCREEN_PLAY = 2000;

    @Override
    protected void onCreate(Bundle salvar) {
        super.onCreate(salvar);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_ept);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent main = new Intent().setClass(SplashScreenActivity.this, MainActivity.class);
                startActivity(main);

                finish();
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_PLAY);

    }
}
