package com.mydreamworld.finalapp;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PRE_NAME= "session";
    String SESSION_KEY="session_user";

    public Session(Context context){
        sharedPreferences= context.getSharedPreferences(SHARED_PRE_NAME,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();

    }

    public void saveSession(User user){
        //save session of user whenever user is logged in

        int id=user.getId();
        String name=user.getName();

        editor.putInt(SESSION_KEY,id).commit();
        editor.putString(SESSION_KEY,name).commit();

    }
    public int getSession(){
        //return user whose session is saved
        return  sharedPreferences.getInt(SESSION_KEY,-1);

    }
    public String getName(){
        return sharedPreferences.getString(SESSION_KEY,"user");
    }
    public  void removeSession(){
        editor.putInt(SESSION_KEY,-1).commit();
        editor.putString(SESSION_KEY,"user").commit();
    }
}
