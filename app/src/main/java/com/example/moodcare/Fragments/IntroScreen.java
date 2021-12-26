package com.example.moodcare.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.moodcare.R;
import androidx.fragment.app.Fragment;

public class IntroScreen extends Fragment {
    private static int MODE_PRIVATE = 0;

    public IntroScreen(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstancesState) {
        View view = inflater.inflate(R.layout.fragment_intro_screen, container, false);
        Button continueButton = view.findViewById(R.id.cont);

        // change value, INTRO fragment already opened.
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();

        continueButton.setOnClickListener(v -> {
            getActivity().onBackPressed(); //For ending the IntroScreen Fragment
        });

        return view;
    }

}