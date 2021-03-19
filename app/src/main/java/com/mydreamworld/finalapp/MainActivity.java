package com.mydreamworld.finalapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Menu;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.r0adkll.slidr.model.SlidrInterface;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;
    Switch mySwitch;
    Button logout,needhelp,urgetNeedHelp,updateProfile,getCounselling,addReport,emergencyDeatils;
    TextView Topics,categories;
    private FirebaseAuth mAuth;
    OptionalPendingResult<GoogleSignInResult> opr;
    DrawerLayout drawerLayout;
    boolean flag;
    private SlidrInterface slidrInterface;
    String personEmail;
    Uri personPhoto;
    GoogleSignInAccount acct;
    private GoogleSignInClient mGoogleSignInClient;
    DatabaseReference rootRef, demoRef;
    Map<String,String> values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        //get the menu from the navigation view
        //Menu menu=navigationView.getMenu();


        /*NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);*/

        //add switch to the navigation view
        //SwitchCompat switchCompat=(SwitchCompat) MenuItemCompat.getActionView(menu.findItem(R.id.nav_view)).findViewById(R.id.drawer_switch);
        //add listerner to navigation
        navigationView.setNavigationItemSelectedListener(this);


        drawerLayout=findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.nav_app_bar_open_drawer_description,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //get buttons


        logout=(Button) findViewById(R.id.logout);

        needhelp=(Button) findViewById(R.id.e1);
        urgetNeedHelp=(Button) findViewById(R.id.e2);
        updateProfile=(Button) findViewById(R.id.UpdateProfile);
        getCounselling=(Button) findViewById(R.id.getcounselling);
        addReport=(Button) findViewById(R.id.addreport);
        emergencyDeatils=(Button) findViewById(R.id.emergencyDetails);


        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        googleApiClient=new GoogleApiClient.Builder(this).enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();
        opr=Auth.GoogleSignInApi.silentSignIn(googleApiClient);
         acct = GoogleSignIn.getLastSignedInAccount(this);

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference();

        values= new HashMap<>();



        //navigation header bar
        View HeaderView=navigationView.getHeaderView(0);
        TextView navUserName=(TextView) HeaderView.findViewById(R.id.emailProfileBy);
        ImageView profile=(ImageView) HeaderView.findViewById(R.id.profile);


        try {
            GoogleSignInAccount account=GoogleSignIn.getLastSignedInAccount(this);
            if(account!=null){
                personEmail=account.getDisplayName();


                try{
                    values.put("emailId",personEmail);

                    rootRef.push().setValue(values, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                            if(error==null){
                                Log.i("save","save success");
                            }else{
                                Log.i("save","save failed");
                            }
                        }
                    });
                }catch (Exception e){
                    Log.i("errorFireBase",e.getMessage());

                }

                personPhoto= account.getPhotoUrl();
               // Log.i("TAG", String.valueOf(personPhoto));
               // Log.i("TAG", personEmail);
                navUserName.setText(personEmail);
              //  Bitmap bmap= BitmapFactory.decodeStream(personPhoto)
                if(personPhoto!=null){
                    Picasso.get()
                            .load(personPhoto)
                            .placeholder(R.drawable.profile_round)
                            .into(profile);
                }


            }

        }
        catch (Exception e){
            Log.i("TAGERROR",e.getMessage());
        }

       //SessionManagement sessionManagement=new SessionManagement(MainActivity.this);

        //String username= sessionManagement.getName();

        //SlidrConfig config = new SlidrConfig.Builder()
          //      .position(SlidrPosition.RIGHT)
            //    .build();

        //Slidr.attach(this, );
       // slidrInterface= Slidr.attach(this,config);
        /*
        if(mAuth!=null){
            mAuth = FirebaseAuth.getInstance();
             personEmail = mAuth.getCurrentUser().getEmail();
             Log.i("emailPerson",personEmail);
        }else if(acct != null){
             personEmail = acct.getEmail();
            Log.i("emailPerson",personEmail);
        }*/
       // emailProfile=findViewById(R.id.emailProfile);
       // emailProfile.setText(personEmail);
        // needhelp,urgetNeedHelp,updateProfile,getCounselling,addReport,emergencyDeatils

        //needhelp.setOnClickListener(new )

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main,menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()){
            case R.id.logout:
                mAuth.signOut();
                remove();
                removeNight();
                gotoMainActivity();

                /*
                if(opr.isDone()){
                    GoogleSignInResult result=opr.get();
                    logout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                                @Override
                                public void onResult(@NonNull Status status) {
                                    if (status.isSuccess()){
                                        remove();
                                        Toast.makeText(MainActivity.this, "signout successfull.", Toast.LENGTH_SHORT).show();
                                        gotoMainActivity();
                                    }else{
                                        Toast.makeText(MainActivity.this, "signout failed.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });

                }else{


                    FirebaseAuth.getInstance().signOut();
                    remove();
                    Toast.makeText(MainActivity.this, "Logout successfull!..", Toast.LENGTH_SHORT).show();
                    gotoMainActivity();
                    startActivity(new Intent(MainActivity.this,Startuppage.class));
                    finish();
                    Toast.makeText(this, "Send Report now.Logout not working.", Toast.LENGTH_SHORT).show();


                }
                removeNight();*/


            default:
                return false;

        }
    }

    private void removeNight() {
        SessionOfNightmode sessionOfNightmode=new SessionOfNightmode(MainActivity.this);
        sessionOfNightmode.removeSession();
    }

    private void remove() {
        Session sessionManagement=new Session(MainActivity.this);
        sessionManagement.removeSession();
    }

    private void gotoMainActivity() {
        startActivity(new Intent(MainActivity.this,Splash.class));
        finish();
    }


  /*  @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
*/
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.Nightmode:
                flag=true;
                changeMode(flag);
                break;
            case R.id.lightmode:
                flag=false;
                changeMode(flag);
                break;
            case R.id.feedback:
                sendEmail();
                break;
            case R.id.story:
                startActivity(new Intent(this,Tabview.class));
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    private void sendEmail() {
        Log.i("Send email", "");
        Location location = new Location(LocationManager.GPS_PROVIDER);
        Date date = new Date(location.getTime());
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());

        //Device Id
        String android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        String uniqueID = UUID.randomUUID().toString();

        String Device_Name=android.os.Build.MODEL;

        String Android_Version = android.os.Build.VERSION.RELEASE;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        double x = Math.pow(width/displayMetrics.xdpi,2);
        double y = Math.pow(height/displayMetrics.ydpi,2);
        double screenInches = Math.sqrt(x+y);



        //Date date=new Date(getTime())
        String to = "itprawin@gmail.com";
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "["+dateFormat.format(date)+"] Feedback for mech-app");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Device Id:"+android_id+"\n"+"UniqueID:"+uniqueID+"\n"+"Device Name:"+Device_Name+"\n"+"Android version:"+Android_Version+"\n"+
        "Device Width(in DP):"+width+"\nDevice Width(in DP):"+height+"\nScreen Height (in Inches):"+screenInches+"\n---Please Don't edit the above information, Write your  feedback below");

        //need this to prompts email client only
        emailIntent.setType("message/rfc822");
        startActivity(Intent.createChooser(emailIntent, "Choose an Email client :"));
    }

    private void changeMode(boolean flag) {


        if(flag==true){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            Nightmode nightmode=new Nightmode(flag);
            SessionOfNightmode sessionOfNightmode=new SessionOfNightmode(MainActivity.this);
            sessionOfNightmode.saveSession(nightmode);
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();

        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            Nightmode nightmode=new Nightmode(flag);
            SessionOfNightmode sessionOfNightmode=new SessionOfNightmode(MainActivity.this);
            sessionOfNightmode.saveSession(nightmode);
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
    }
    public void needhelpCall(View v){
        Toast.makeText(this,"Application is under constrruction",Toast.LENGTH_LONG).show();
    }
    public void needUrgetHelpCall(View v){
        Toast.makeText(this,"Application is under constrruction",Toast.LENGTH_LONG).show();
    }
    public void UpdateProfileCall(View v){
        Intent i=new Intent(MainActivity.this,UpdateProfile.class);
        startActivity(i);
    }
    public void addReportCall(View v){
        Toast.makeText(this,"Application is under constrruction",Toast.LENGTH_LONG).show();
    }
    public void getCounsellingCall(View v){
        Toast.makeText(this,"Application is under constrruction",Toast.LENGTH_LONG).show();
    }
    public void emergencyDetailsCall(View v){
        Intent i=new Intent(MainActivity.this,Emergencydeatils.class);
        startActivity(i);
    }
}