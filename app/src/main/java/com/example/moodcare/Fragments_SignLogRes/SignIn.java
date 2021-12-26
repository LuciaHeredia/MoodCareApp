package com.example.moodcare.Fragments_SignLogRes;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.moodcare.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends Fragment {
    private static final String TAG = "";
    private EditText mEmail, mPassword;
    private Button mSignIn;
    private FirebaseAuth fAuth;
    private ProgressBar progressBar;
    private String email = ""; // email input
    private String password = ""; // password input

    public SignIn(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstancesState) {
        View view = inflater.inflate(R.layout.fragment_signin, container, false);

        mEmail = view.findViewById(R.id.email);
        mPassword = view.findViewById(R.id.password);
        mSignIn = view.findViewById(R.id.save);
        fAuth = FirebaseAuth.getInstance();
        progressBar = view.findViewById(R.id.progressBar);

        mSignIn.setOnClickListener(v -> {
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

            // register user
            fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if(task.isSuccessful()){

                    //SEND AUTHENTICATION LINK
                    FirebaseUser userVer = fAuth.getCurrentUser();
                    userVer.sendEmailVerification().addOnSuccessListener(aVoid -> Toast.makeText(getActivity(), "Verification email has been sent.", Toast.LENGTH_LONG).show()).addOnFailureListener(e -> Log.d(TAG, "OnFailure: Email not sent"+ e.getMessage()));

                    Toast.makeText(getActivity(), "User Created. Let's LOGIN now.", Toast.LENGTH_LONG).show();
                    LogIn fragment = new LogIn(); // fragment
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, fragment).addToBackStack(null);
                    transaction.commit(); // go to LOGIN screen
                }else {
                    Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
                }
            });
        });

        return view;
    }
}
