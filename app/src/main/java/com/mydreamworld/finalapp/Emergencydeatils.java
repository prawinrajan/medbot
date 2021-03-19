package com.mydreamworld.finalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Emergencydeatils extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergencydeatils);
    }
    public void emergencyDetailsupdate(View v){
        Toast.makeText(this,"Emergency Details added successfully!..",Toast.LENGTH_LONG).show();
    }
}