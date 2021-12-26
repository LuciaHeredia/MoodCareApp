package com.example.moodcare.Fragments_Search;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moodcare.R;

public class ByMood extends Fragment {
    private static int MODE_PRIVATE = 0;
    private String mood = "";

    public ByMood() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_by_mood, container, false);

        final TextView textOfMood =  view.findViewById(R.id.textMood); // name of MOOD icon

        final Button love = view.findViewById(R.id.love);
        final Button peaceful = view.findViewById(R.id.peaceful);
        final Button happy = view.findViewById(R.id.happy);
        final Button good = view.findViewById(R.id.good);
        final Button ok = view.findViewById(R.id.ok);
        final Button meh = view.findViewById(R.id.meh);
        final Button sad = view.findViewById(R.id.sad);
        final Button angry = view.findViewById(R.id.angry);
        final Button awful = view.findViewById(R.id.awful);
        final Button annoyed = view.findViewById(R.id.annoyed);
        final Button tired = view.findViewById(R.id.tired);

        final Button[] moodButtons = new Button[11];
        moodButtons[0] = love;
        moodButtons[1] = peaceful;
        moodButtons[2] = happy;
        moodButtons[3] = good;
        moodButtons[4] = ok;
        moodButtons[5] = meh;
        moodButtons[6] = sad;
        moodButtons[7] = angry;
        moodButtons[8] = awful;
        moodButtons[9] = annoyed;
        moodButtons[10] = tired;

        // MOOD: LOVE
        love.setOnClickListener(v -> {
            if(!mood.equals(""))
                unClickMood(moodButtons); // un-click others
            love.setBackgroundResource(R.drawable.mood_love_clicked); // when clicked
            mood = "in love";
            textOfMood.setText(mood);
            mood = "love";
        });

        // MOOD: PEACEFUL
        peaceful.setOnClickListener(v -> {
            if(!mood.equals(""))
                unClickMood(moodButtons); // un-click others
            peaceful.setBackgroundResource(R.drawable.mood_peaceful_clicked); // when clicked
            mood = "peaceful";
            textOfMood.setText(mood);
        });

        // MOOD: HAPPY
        happy.setOnClickListener(v -> {
            if(!mood.equals(""))
                unClickMood(moodButtons); // un-click others
            happy.setBackgroundResource(R.drawable.mood_happy_clicked); // when clicked
            mood = "happy";
            textOfMood.setText(mood);
        });

        // MOOD: GOOD
        good.setOnClickListener(v -> {
            if(!mood.equals(""))
                unClickMood(moodButtons); // un-click others
            good.setBackgroundResource(R.drawable.mood_good_clicked); // when clicked
            mood = "good";
            textOfMood.setText(mood);
        });

        // MOOD: OK
        ok.setOnClickListener(v -> {
            if(!mood.equals(""))
                unClickMood(moodButtons); // un-click others
            ok.setBackgroundResource(R.drawable.mood_ok_clicked); // when clicked
            mood = "ok";
            textOfMood.setText(mood);
        });

        // MOOD: MEH
        meh.setOnClickListener(v -> {
            if(!mood.equals(""))
                unClickMood(moodButtons); // un-click others
            meh.setBackgroundResource(R.drawable.mood_meh_clicked); // when clicked
            mood = "meh";
            textOfMood.setText(mood);
        });

        // MOOD: SAD
        sad.setOnClickListener(v -> {
            if(!mood.equals(""))
                unClickMood(moodButtons); // un-click others
            sad.setBackgroundResource(R.drawable.mood_sad_clicked); // when clicked
            mood = "sad";
            textOfMood.setText(mood);
        });

        // MOOD: ANGRY
        angry.setOnClickListener(v -> {
            if(!mood.equals(""))
                unClickMood(moodButtons); // un-click others
            angry.setBackgroundResource(R.drawable.mood_angry_clicked); // when clicked
            mood = "angry";
            textOfMood.setText(mood);
        });

        // MOOD: AWFUL
        awful.setOnClickListener(v -> {
            if(!mood.equals(""))
                unClickMood(moodButtons); // un-click others
            awful.setBackgroundResource(R.drawable.mood_awful_clicked); // when clicked
            mood = "awful";
            textOfMood.setText(mood);
        });

        // MOOD: ANNOYED
        annoyed.setOnClickListener(v -> {
            if(!mood.equals(""))
                unClickMood(moodButtons); // un-click others
            annoyed.setBackgroundResource(R.drawable.mood_annoyed_clicked); // when clicked
            mood = "annoyed";
            textOfMood.setText(mood);
        });

        // MOOD: TIRED
        tired.setOnClickListener(v -> {
            if(!mood.equals(""))
                unClickMood(moodButtons); // un-click others
            tired.setBackgroundResource(R.drawable.mood_tired_clicked); // when clicked
            mood = "tired";
            textOfMood.setText(mood);
        });

        // SHOW ME button clicked --> open REPORT
        Button showMeButton = view.findViewById(R.id.showMe);
        showMeButton.setOnClickListener(v -> {
            if(!mood.equals("")) { // if a MOOD icon was chosen
                SavePreferencesIcon(mood); // saving mood icon selected
                Report fragmentReport = new Report();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragmentReport).addToBackStack(null);
                transaction.commit();
            }else{
                Toast.makeText(getActivity(), "A MOOD must be chosen.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void unClickMood(Button[] moodGroup){
        // un-click all
        moodGroup[0].setBackgroundResource(R.drawable.mood_love);
        moodGroup[1].setBackgroundResource(R.drawable.mood_peaceful);
        moodGroup[2].setBackgroundResource(R.drawable.mood_happy);
        moodGroup[3].setBackgroundResource(R.drawable.mood_good);
        moodGroup[4].setBackgroundResource(R.drawable.mood_ok);
        moodGroup[5].setBackgroundResource(R.drawable.mood_meh);
        moodGroup[6].setBackgroundResource(R.drawable.mood_sad);
        moodGroup[7].setBackgroundResource(R.drawable.mood_angry);
        moodGroup[8].setBackgroundResource(R.drawable.mood_awful);
        moodGroup[9].setBackgroundResource(R.drawable.mood_annoyed);
        moodGroup[10].setBackgroundResource(R.drawable.mood_tired);
    }

    private void SavePreferencesIcon(String icon){ // saving ICON selected
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MY_SHARED_PREF_ICON", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Icon", icon);
        editor.apply();
    }

}
