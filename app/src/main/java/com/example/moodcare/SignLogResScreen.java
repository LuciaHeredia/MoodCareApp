package com.example.moodcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import com.example.moodcare.Fragments.IntroScreen;
import com.example.moodcare.Fragments_SignLogRes.LogIn;
import com.example.moodcare.Fragments_SignLogRes.Reset;
import com.example.moodcare.Fragments_SignLogRes.SignIn;
import com.google.firebase.auth.FirebaseAuth;

public class SignLogResScreen extends AppCompatActivity {
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signlogres_screen);

        fAuth = FirebaseAuth.getInstance();
        if(fAuth.getCurrentUser()!=null) {
            startActivity(new Intent(getApplicationContext(), MainScreen.class)); // go to MAIN screen
            finish();
        }

        // get if it's the first time the app opened
        SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
        boolean firstStart = sharedPreferences.getBoolean("firstStart", true);
        // Check if we need to display our INTRO Fragment
        if (firstStart) {
            // The user hasn't seen the INTRO fragment yet, so show it
            IntroScreen fragment = new IntroScreen(); // fragment
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, fragment).addToBackStack(null);
            transaction.commit();
        }

        // LOG IN button clicked --> open fragment LOG IN
        Button buttonFragLogIn = findViewById(R.id.log);
        buttonFragLogIn.setOnClickListener(v -> {
            LogIn fragmentLog = new LogIn();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, fragmentLog).addToBackStack(null);
            transaction.commit();
        });

        // SIGN IN button clicked --> open fragment SIGN IN
        Button buttonFragSignIn = findViewById(R.id.sign);
        buttonFragSignIn.setOnClickListener(v -> {
            SignIn fragmentSign = new SignIn();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, fragmentSign).addToBackStack(null);
            transaction.commit();
        });

        // RESET PASSWORD button clicked --> open fragment RESET
        Button buttonFragReset = findViewById(R.id.res);
        buttonFragReset.setOnClickListener(v -> {
            Reset fragmentReset = new Reset();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, fragmentReset).addToBackStack(null);
            transaction.commit();
        });
    }

    @Override
    public void onBackPressed() { //For ending the IntroScreen Fragment
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStackImmediate();
        } else {
            super.onBackPressed();
        }
    }
}
