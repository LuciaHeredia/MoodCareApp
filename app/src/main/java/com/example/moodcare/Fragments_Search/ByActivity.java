package com.example.moodcare.Fragments_Search;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.moodcare.R;

public class ByActivity extends Fragment {
    private static int MODE_PRIVATE = 0;
    private String activity = "";

    public ByActivity() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_by_activity, container, false);

        final TextView textOfAct =  view.findViewById(R.id.textActivity); // name of ACTIVITY icon

        final Button me = view.findViewById(R.id.meTime);
        final Button friends = view.findViewById(R.id.friends);
        final Button family = view.findViewById(R.id.family);
        final Button date = view.findViewById(R.id.date);
        final Button study = view.findViewById(R.id.study);
        final Button work = view.findViewById(R.id.work);
        final Button sick = view.findViewById(R.id.sick);
        final Button workout = view.findViewById(R.id.workout);
        final Button party = view.findViewById(R.id.party);
        final Button travel = view.findViewById(R.id.travel);
        final Button eating = view.findViewById(R.id.eating);
        final Button cleaning = view.findViewById(R.id.cleaning);
        final Button shopping = view.findViewById(R.id.shopping);
        final Button reading = view.findViewById(R.id.reading);
        final Button sleeping = view.findViewById(R.id.sleeping);
        final Button movie = view.findViewById(R.id.movie);
        final Button other = view.findViewById(R.id.other);

        final Button[] actButtons = new Button[17];
        actButtons[0] = me;
        actButtons[1] = friends;
        actButtons[2] = family;
        actButtons[3] = date;
        actButtons[4] = study;
        actButtons[5] = work;
        actButtons[6] = sick;
        actButtons[7] = workout;
        actButtons[8] = party;
        actButtons[9] = travel;
        actButtons[10] = eating;
        actButtons[11] = cleaning;
        actButtons[12] = shopping;
        actButtons[13] = reading;
        actButtons[14] = sleeping;
        actButtons[15] = movie;
        actButtons[16] = other;

        // ACTIVITY: ME TIME
        me.setOnClickListener(v -> {
            if(!activity.equals(""))
                unClickAct(actButtons); // un-click others
            me.setBackgroundResource(R.drawable.act_me_clicked); // when clicked
            activity = "me time";
            textOfAct.setText(activity);
            activity = "me";
        });

        // ACTIVITY: FRIENDS
        friends.setOnClickListener(v -> {
            if(!activity.equals(""))
                unClickAct(actButtons); // un-click others
            friends.setBackgroundResource(R.drawable.act_friends_clicked); // when clicked
            activity = "friends";
            textOfAct.setText(activity);
        });

        // ACTIVITY: FAMILY
        family.setOnClickListener(v -> {
            if(!activity.equals(""))
                unClickAct(actButtons); // un-click others
            family.setBackgroundResource(R.drawable.act_family_clicked); // when clicked
            activity = "family";
            textOfAct.setText(activity);
        });

        // ACTIVITY: DATE
        date.setOnClickListener(v -> {
            if(!activity.equals(""))
                unClickAct(actButtons); // un-click others
            date.setBackgroundResource(R.drawable.act_date_clicked); // when clicked
            activity = "date";
            textOfAct.setText(activity);
        });

        // ACTIVITY: STUDY
        study.setOnClickListener(v -> {
            if(!activity.equals(""))
                unClickAct(actButtons); // un-click others
            study.setBackgroundResource(R.drawable.act_study_clicked); // when clicked
            activity = "study";
            textOfAct.setText(activity);
        });

        // ACTIVITY: WORK
        work.setOnClickListener(v -> {
            if(!activity.equals(""))
                unClickAct(actButtons); // un-click others
            work.setBackgroundResource(R.drawable.act_work_clicked); // when clicked
            activity = "work";
            textOfAct.setText(activity);
        });

        // ACTIVITY: SICK
        sick.setOnClickListener(v -> {
            if(!activity.equals(""))
                unClickAct(actButtons); // un-click others
            sick.setBackgroundResource(R.drawable.act_sick_clicked); // when clicked
            activity = "sick";
            textOfAct.setText(activity);
        });

        // ACTIVITY: WORKOUT
        workout.setOnClickListener(v -> {
            if(!activity.equals(""))
                unClickAct(actButtons); // un-click others
            workout.setBackgroundResource(R.drawable.act_workout_clicked); // when clicked
            activity = "workout";
            textOfAct.setText(activity);
        });

        // ACTIVITY: PARTY
        party.setOnClickListener(v -> {
            if(!activity.equals(""))
                unClickAct(actButtons); // un-click others
            party.setBackgroundResource(R.drawable.act_party_clicked); // when clicked
            activity = "party";
            textOfAct.setText(activity);
        });

        // ACTIVITY: TRAVEL
        travel.setOnClickListener(v -> {
            if(!activity.equals(""))
                unClickAct(actButtons); // un-click others
            travel.setBackgroundResource(R.drawable.act_travel_clicked); // when clicked
            activity = "travel";
            textOfAct.setText(activity);
        });

        // ACTIVITY: EATING
        eating.setOnClickListener(v -> {
            if(!activity.equals(""))
                unClickAct(actButtons); // un-click others
            eating.setBackgroundResource(R.drawable.act_eating_clicked); // when clicked
            activity = "eating";
            textOfAct.setText(activity);
        });

        // ACTIVITY: CLEANING
        cleaning.setOnClickListener(v -> {
            if(!activity.equals(""))
                unClickAct(actButtons); // un-click others
            cleaning.setBackgroundResource(R.drawable.act_cleaning_clicked); // when clicked
            activity = "cleaning";
            textOfAct.setText(activity);
        });

        // ACTIVITY: SHOPPING
        shopping.setOnClickListener(v -> {
            if(!activity.equals(""))
                unClickAct(actButtons); // un-click others
            shopping.setBackgroundResource(R.drawable.act_shopping_clicked); // when clicked
            activity = "shopping";
            textOfAct.setText(activity);
        });

        // ACTIVITY: READING
        reading.setOnClickListener(v -> {
            if(!activity.equals(""))
                unClickAct(actButtons); // un-click others
            reading.setBackgroundResource(R.drawable.act_reading_clicked); // when clicked
            activity = "reading";
            textOfAct.setText(activity);
        });

        // ACTIVITY: SLEEPING
        sleeping.setOnClickListener(v -> {
            if(!activity.equals(""))
                unClickAct(actButtons); // un-click others
            sleeping.setBackgroundResource(R.drawable.act_sleeping_clicked); // when clicked
            activity = "sleeping";
            textOfAct.setText(activity);
        });

        // ACTIVITY: MOVIE
        movie.setOnClickListener(v -> {
            if(!activity.equals(""))
                unClickAct(actButtons); // un-click others
            movie.setBackgroundResource(R.drawable.act_movie_clicked); // when clicked
            activity = "movie";
            textOfAct.setText(activity);
        });

        // ACTIVITY: OTHER
        other.setOnClickListener(v -> {
            if(!activity.equals(""))
                unClickAct(actButtons); // un-click others
            other.setBackgroundResource(R.drawable.act_other_clicked); // when clicked
            activity = "other";
            textOfAct.setText(activity);
        });

        // SHOW ME button clicked --> open REPORT
        Button showMeButton = view.findViewById(R.id.showMe);
        showMeButton.setOnClickListener(v -> {
            if(!activity.equals("")) { // if an ACTIVITY icon was chosen
                SavePreferencesIcon(activity); // saving activity icon selected
                Report fragmentReport = new Report();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragmentReport).addToBackStack(null);
                transaction.commit();
            }else{
                Toast.makeText(getActivity(), "An ACTIVITY  must be chosen.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void unClickAct(Button[] actGroup){
        // un-click all
        actGroup[0].setBackgroundResource(R.drawable.act_me);
        actGroup[1].setBackgroundResource(R.drawable.act_friends);
        actGroup[2].setBackgroundResource(R.drawable.act_family);
        actGroup[3].setBackgroundResource(R.drawable.act_date);
        actGroup[4].setBackgroundResource(R.drawable.act_study);
        actGroup[5].setBackgroundResource(R.drawable.act_work);
        actGroup[6].setBackgroundResource(R.drawable.act_sick);
        actGroup[7].setBackgroundResource(R.drawable.act_workout);
        actGroup[8].setBackgroundResource(R.drawable.act_party);
        actGroup[9].setBackgroundResource(R.drawable.act_travel);
        actGroup[10].setBackgroundResource(R.drawable.act_eating);
        actGroup[11].setBackgroundResource(R.drawable.act_cleaning);
        actGroup[12].setBackgroundResource(R.drawable.act_shopping);
        actGroup[13].setBackgroundResource(R.drawable.act_reading);
        actGroup[14].setBackgroundResource(R.drawable.act_sleeping);
        actGroup[15].setBackgroundResource(R.drawable.act_movie);
        actGroup[16].setBackgroundResource(R.drawable.act_other);
    }

    private void SavePreferencesIcon(String icon){ // saving ICON selected
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MY_SHARED_PREF_ICON", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Icon", icon);
        editor.apply();
    }

}
