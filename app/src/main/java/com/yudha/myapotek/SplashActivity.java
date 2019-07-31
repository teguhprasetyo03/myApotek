package com.yudha.myapotek;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    ImageView app_logo,name_logo;

    Animation splash_app,btt;

    String USERNAME_KEY="usernamekey";
    String username_key="";
    String username_key_new="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        splash_app = AnimationUtils.loadAnimation(this,R.anim.splash_app);
        btt=AnimationUtils.loadAnimation(this,R.anim.btt);

        app_logo=findViewById(R.id.app_logo);
        name_logo=findViewById(R.id.name_logo);

        app_logo.startAnimation(splash_app);
        name_logo.startAnimation(btt);

        getUsernameLocal();
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY,MODE_PRIVATE);
        username_key_new=sharedPreferences.getString(username_key,"");
        if (username_key_new.isEmpty()){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // merubah activity ke activity lain
                    Intent gologin = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(gologin);
                    finish();
                }
            }, 3000);
        }
        else {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // merubah activity ke activity lain
                    Intent godashboard = new Intent(SplashActivity.this,DashboardActivity.class);
                    startActivity(godashboard);
                    finish();
                }
            }, 3000);
        }
    }
}
