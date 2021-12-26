package com.example.moodcare.Fragments_SignLogRes;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.moodcare.R;
import com.google.firebase.auth.FirebaseAuth;

public class Reset extends Fragment {
    private EditText mEmail;
    private Button mReset;
    private ProgressBar progressBar;
    private FirebaseAuth fAuth;
    private String email = ""; // email input

    public Reset(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstancesState) {
        View view = inflater.inflate(R.layout.fragment_reset, container, false);

        mEmail = view.findViewById(R.id.email);
        mReset = view.findViewById(R.id.enter);
        fAuth = FirebaseAuth.getInstance();
        progressBar = view.findViewById(R.id.progressBar);

        mReset.setOnClickListener(v -> {
            email = mEmail.getText().toString().trim();
            if(TextUtils.isEmpty(email)) {
                mEmail.setError("Email is Required.");
                return;
            }

            progressBar.setVisibility(View.VISIBLE);

            // EXTRACT EMAIL AND SEND RESET LINK TO MAIL
            fAuth.sendPasswordResetEmail(email).addOnSuccessListener(aVoid -> {
                Toast.makeText(getActivity(), "Reset link was sent to your email.", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE); // stop loading
                getActivity().onBackPressed(); // ending RESET Fragment --> SignLogRes
            }).addOnFailureListener(e -> {
                Toast.makeText(getActivity(), "ERROR: Reset link was not sent. "+e.getMessage(), Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE); // stop loading
            });
        });

        return view;
    }

}
