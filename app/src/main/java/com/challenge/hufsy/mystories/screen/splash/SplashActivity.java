package com.challenge.hufsy.mystories.screen.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.challenge.hufsy.mystories.R;
import com.challenge.hufsy.mystories.screen.main.MainActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final Intent openMain = new Intent(this, MainActivity.class);
        startActivity(openMain);
        finish();

    }
}
