package com.mydreamworld.finalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
public class Login extends AppCompatActivity {
    Button login;
    EditText password,email;
    private FirebaseAuth auth;



    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login=(Button) findViewById(R.id.login);

        email=(EditText) findViewById(R.id.uname);
        password=(EditText) findViewById(R.id.pwd);

        auth=FirebaseAuth.getInstance();

    }
    public void login(View v){
        String emailtext=email.getText().toString();
        String pwdtext=password.getText().toString();
        if(TextUtils.isEmpty(emailtext)||TextUtils.isEmpty(pwdtext) ){
            Toast.makeText(Login.this,"email id and password is none. enter email id & password.",Toast.LENGTH_SHORT).show();
        }
        loginUser(emailtext,pwdtext);
    }
    @Override
    protected void onStart() {
        super.onStart();

        //checkSession();
    }

    private void checkSession() {
        //check if user is logged in
        // if user is logged in ---> move to main
        Session sessionManagement=new Session(Login.this);

       int userid= sessionManagement.getSession();

        if(userid!=-1){
            //user id loggedin & move to main
            moveToMainActivity();
        }
        else {
            //do nothing
        }
    }
    private void moveToMainActivity() {
        Intent intent=new Intent(Login.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void loginUser(String emailtext, String pwdtext) {
        //LOGIN & SAVE  SESSION
        User user=new User(12,emailtext);

        Session sessionManagement=new Session(Login.this);
        sessionManagement.saveSession(user);

        auth.signInWithEmailAndPassword(emailtext,pwdtext).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(Login.this,"Login sucessfull!.",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Login.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}