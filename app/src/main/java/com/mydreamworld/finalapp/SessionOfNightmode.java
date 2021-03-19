package com.mydreamworld.finalapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionOfNightmode {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    boolean flag=false;
    String SESSION_KEY="session_Night";
    public SessionOfNightmode(Context context){
        sharedPreferences= context.getSharedPreferences(String.valueOf(flag),Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }
    public void saveSession(Nightmode nightmode){
        //save session of user whenever user is logged in

        boolean flag=nightmode.isFlag();
        editor.putBoolean(SESSION_KEY,flag).commit();

    }
    public boolean getSession(){
        //return user whose session is saved
        return  sharedPreferences.getBoolean(SESSION_KEY,false);

    }
    public  void removeSession(){
        editor.putBoolean(SESSION_KEY,false).commit();
    }
}
