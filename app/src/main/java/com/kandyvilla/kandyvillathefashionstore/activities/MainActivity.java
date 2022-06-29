package com.kandyvilla.kandyvillathefashionstore.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
//import android.window.SplashScreen;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.kandyvilla.kandyvillathefashionstore.R;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    private final int SPLASH_TIME_OUT = 2800;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                auth = FirebaseAuth.getInstance();

                if(auth.getCurrentUser() != null)
                {
                    Intent intent= new Intent(MainActivity.this,HomeActivity.class );
                    //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    // set the new task and clear flags
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else
                {
                    Intent intent= new Intent(MainActivity.this, LoginActivity.class);
                    //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    // set the new task and clear flags
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}