package com.example.moodcare.Fragments_SignLogRes;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.example.moodcare.MainScreen;
import com.example.moodcare.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogIn extends Fragment {
    private EditText mEmail, mPassword;
    private Button mLogIn;
    private FirebaseAuth fAuth;
    private ProgressBar progressBar;
    private String email = ""; // email input
    private String password = ""; // password input

    public LogIn() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstancesState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mEmail = view.findViewById(R.id.email);
        mPassword = view.findViewById(R.id.password);
        mLogIn = view.findViewById(R.id.enter);
        fAuth = FirebaseAuth.getInstance();
        progressBar = view.findViewById(R.id.progressBar);


        mLogIn.setOnClickListener(v -> {
            email = mEmail.getText().toString().trim();
            password = mPassword.getText().toString().trim();
            if(TextUtils.isEmpty(email)) {
                mEmail.setError("Email is Required.");
                return;
            }

            if(TextUtils.isEmpty(password)){
                mPassword.setError("Password is Required.");
                return;
            }

            if(password.length()<8){
                mPassword.setError("Password must be >= 8 characters.");
                return;
            }

            progressBar.setVisibility(View.VISIBLE);

            // AUTHENTICATE USER
            fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {

                //AUTHENTICATION LINK
                FirebaseUser user = fAuth.getCurrentUser();

                if(task.isSuccessful()){
                    if(!user.isEmailVerified()) {
                        Toast.makeText(getActivity(), "Email not verified, please check your email before LOGIN.", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.INVISIBLE); // stop loading
                    }else {
                        Toast.makeText(getActivity(), "Logged In Successfully!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(), MainScreen.class)); // go to MAIN screen
                    }
                }else{
                    Toast.makeText(getActivity(), "ERROR: Email & Password don't match.", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE); // stop loading
                }
            });
        });
        return view;

    }
}
