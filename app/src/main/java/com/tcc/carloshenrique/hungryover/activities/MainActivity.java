package com.tcc.carloshenrique.hungryover.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tcc.carloshenrique.hungryover.R;


public class MainActivity extends AppCompatActivity{
    private static int SPLASH_TIME_OUT = 1000;

    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        //Essa linha esconde a Barra de Notificações
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}
