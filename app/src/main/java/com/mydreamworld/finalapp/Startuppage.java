package com.mydreamworld.finalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class Startuppage extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    Button regiser;
    Button login;
    SignInButton signInButton;
    private  GoogleApiClient googleApiClient;
    private static final int SIGN_IN=1;
    String personEmail;
    Session session;
    DatabaseReference rootRef, demoRef;
    Map<String,String> values;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startuppage);

        //regiser=(Button) findViewById(R.id.regsiter);
        //login=(Button) findViewById(R.id.login);
        rootRef = FirebaseDatabase.getInstance().getReference();

         values= new HashMap<>();

        // Database reference pointing to demo node
        //demoRef = rootRef.child("personEmail");

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient=new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();
        session=new Session(Startuppage.this);
        signInButton=findViewById(R.id.signin);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent,SIGN_IN);
            }
        });
    }

    public void login(View v) {
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish(); //does not return to this page
    }

    public void regsiter(View v) {
        startActivity(new Intent(getApplicationContext(), Register.class));
        finish();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==SIGN_IN){
            GoogleSignInResult result=Auth.GoogleSignInApi.getSignInResultFromIntent(data);


            if(result.isSuccess()){
                try {
                    GoogleSignInAccount account=GoogleSignIn.getLastSignedInAccount(this);
                    if(account!=null){
                        //Log.i("googleacct","null data");
                        personEmail=account.getDisplayName();

                        Log.i("database",personEmail);
                        //String id= account.getId();
                        int userid= 56;


                        // Write a message to the database
                       //  database = FirebaseDatabase.getInstance();
                         //myRef = database.getReference("signedEmailId");



                        User user=new User(userid,personEmail);
                        session.saveSession(user);
                    }

                }
                catch (Exception e){
                    Log.i("TAGERROR",e.getMessage());
                }
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();

            }else{
                Toast.makeText(this, String.valueOf(resultCode) , Toast.LENGTH_SHORT).show();
                Log.i("resultCode", String.valueOf(resultCode));
            }
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}