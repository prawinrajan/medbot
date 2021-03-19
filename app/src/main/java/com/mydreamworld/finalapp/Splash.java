package com.mydreamworld.finalapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;


public class Splash extends AppCompatActivity {
    boolean flag;
    SessionOfNightmode sessionOfNightmode;
    Session sessionManagement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        Handler handler=new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Session();
                checkSessionHere();

            }
        },3000);

    }

    private void checkSessionHere() {
        sessionManagement=new Session(Splash.this);

        String name=sessionManagement.getName();

        sessionOfNightmode=new SessionOfNightmode(Splash.this);
        boolean flag=sessionOfNightmode.getSession();

        if(name.contentEquals("user") && flag==false){
            goTostartup();
        } else if (!"user".equals(name) && flag==false){
           moveToMainActivity();
        }else if(name.contentEquals("user") && flag==true){
            changeModeStart();
        }else if (!"user".equals(name) && flag==true){
            moveToMainActivitydark();
        }


    }

    private void changeModeStart() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        Nightmode nightmode=new Nightmode(flag);
        sessionOfNightmode.saveSession(nightmode);
        startActivity(new Intent(getApplicationContext(),Startuppage.class));
        finish();
    }

    private void goTostartup() {
        Intent intent=new Intent(Splash.this,Startuppage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    private void moveToMainActivity() {
        Intent intent=new Intent(Splash.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    private void moveToMainActivitydark() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        Nightmode nightmode=new Nightmode(flag);
        sessionOfNightmode.saveSession(nightmode);
        Intent intent=new Intent(Splash.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

   /* public void Session(){
        Session sessionManagement=new Session(Splash.this);

        String name=sessionManagement.getName();
       // Log.i("TAGFLAG", name);

        if(name.contentEquals("user")){
            //user id loggedin & move to main
            goTostartup();
        }
        else {
            moveToMainActivity();
        }
    }




    public void getnightmode(){
            SessionOfNightmode sessionOfNightmode=new SessionOfNightmode(Splash.this);
            boolean flag=sessionOfNightmode.getSession();
            Log.i("TAGFLAG", String.valueOf(flag));

            if(flag==true){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                Nightmode nightmode=new Nightmode(flag);
                sessionOfNightmode.saveSession(nightmode);
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                Nightmode nightmode=new Nightmode(flag);
                //SessionOfNightmode sessionOfNightmode=new SessionOfNightmode(Splash.this);
                sessionOfNightmode.saveSession(nightmode);
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }

    }*/


    }
