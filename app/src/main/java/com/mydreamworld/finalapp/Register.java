package com.mydreamworld.finalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
public class Register extends AppCompatActivity {

    Button regsiter;
    EditText password,email;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email=(EditText) findViewById(R.id.uname);
        password=(EditText) findViewById(R.id.pwd);

        regsiter=(Button) findViewById(R.id.register);

        auth=FirebaseAuth.getInstance();

    }
    public void regsiter(View v){
        String emailid=email.getText().toString();
        String pwd=password.getText().toString();

        if(TextUtils.isEmpty(emailid)||TextUtils.isEmpty(pwd) ){
            Toast.makeText(Register.this,"empty user name or password",Toast.LENGTH_SHORT).show();
        }else if(pwd.length()<6){
            Toast.makeText(Register.this,"Password too short",Toast.LENGTH_SHORT).show();
        }else{
            registerUser(emailid,pwd);
        }

    }

    private void registerUser(String emailid, String pwd) {
        try{
            //LOGIN & SAVE  SESSION
            User user=new User(12,emailid);

            Session sessionManagement=new Session(Register.this);
            sessionManagement.saveSession(user);

            auth.createUserWithEmailAndPassword(emailid,pwd).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.d("registerc", "createUserWithEmail:success");
                        startActivity(new Intent(Register.this, MainActivity.class));
                        Toast.makeText(Register.this," Registeration completed.",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(Register.this,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(Register.this,"Registeration failed.",Toast.LENGTH_SHORT).show();
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("error",e.getMessage());
        }
    }
    private void moveToMainActivity() {
        Intent intent=new Intent(Register.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    @Override
    protected void onStart() {
        super.onStart();

        //checkSession();
    }

    private void checkSession() {
        //check if user is logged in
        // if user is logged in ---> move to main
        Session sessionManagement=new Session(Register.this);

        int userid= sessionManagement.getSession();


        if(userid!=-1){
            //user id loggedin & move to main
            moveToMainActivity();
        }
        else {
            //do nothing
        }
    }


}