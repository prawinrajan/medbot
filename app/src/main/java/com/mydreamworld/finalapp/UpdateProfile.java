package com.mydreamworld.finalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

public class UpdateProfile extends AppCompatActivity {

    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        /*
        radioGroup=(RadioGroup) findViewById(R.id.radiogroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
            int id= radioGroup.getCheckedRadioButtonId();

            View radioButton = radioGroup.findViewById(id);

            if(radioButton.getId()==R.id.yes){

            }
            }
            }
        );*/


    }
    public void updateprofile(View v){

        Toast.makeText(this,"Emergency Details added successfully!..",Toast.LENGTH_LONG).show();
    }

}