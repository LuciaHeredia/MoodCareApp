package com.example.moodcare.Fragments_NewEntry;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.moodcare.NewEntryScreen;
import com.example.moodcare.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PickDay extends Fragment {
    private static int MODE_PRIVATE = 0;
    private String saveDateEntry;

    public PickDay() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pick_day, container, false);

        // TODAY button clicked --> open NEW ENTRY
        Button todayButton = view.findViewById(R.id.today);
        todayButton.setOnClickListener(v -> {
            // saving today's date
            saveDateEntry = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
            SavePreferencesDateEntry(saveDateEntry); // saving current DATE, for NEW ENTRY

            // going to NEW ENTRY
            Intent intentToday = new Intent(getActivity(), NewEntryScreen.class);
            startActivity(intentToday);
        });

        // YESTERDAY button clicked --> open NEW ENTRY
        Button yesterdayButton = view.findViewById(R.id.yesterday);
        yesterdayButton.setOnClickListener(v -> {
            // saving yesterday's date
            saveDateEntry = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24)));
            SavePreferencesDateEntry(saveDateEntry); // saving yesterday's DATE, for NEW ENTRY

            // going to NEW ENTRY
            Intent intentYesterday = new Intent(getActivity(), NewEntryScreen.class);
            startActivity(intentYesterday);
        });

        // OTHER DAY button clicked --> open PICK CALENDAR DAY
        Button otherButton = view.findViewById(R.id.other);
        otherButton.setOnClickListener(v -> {
            PickCalendarDay fragmentOther = new PickCalendarDay();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, fragmentOther).addToBackStack(null);
            transaction.commit();
        });

        return view;
    }

    private void SavePreferencesDateEntry(String saveDate){ // saving DATE selected for NEW ENTRY
        SharedPreferences sharedPreferencesReport = getActivity().getSharedPreferences("MY_SHARED_PREF_DATE_ENTRY", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferencesReport.edit();
        editor.putString("Date Entry", saveDate);
        editor.apply();
    }

}
