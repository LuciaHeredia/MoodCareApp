package com.example.moodcare.Fragments_NewEntry;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import androidx.fragment.app.Fragment;
import com.example.moodcare.NewEntryScreen;
import com.example.moodcare.R;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PickCalendarDay extends Fragment {

    private static int MODE_PRIVATE = 0 ;
    private String saveDateEntry;

    public PickCalendarDay() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pick_calendar_day, container, false);

        // CALENDAR
        final CalendarView mCalendarView = view.findViewById(R.id.calendarView);
        saveDateEntry = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(mCalendarView.getDate());
        SavePreferencesDateEntry(saveDateEntry); // saving current DATE, for NEW ENTRY

        Calendar c = Calendar.getInstance();
        mCalendarView.setMaxDate(c.getTimeInMillis()); // set max date to pick

        mCalendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            // block come dates in calendar
            String newDayOfMonth = changeFormat(dayOfMonth);
            String newMonth = changeFormat(month+1);
            saveDateEntry =  newDayOfMonth + "/" + newMonth + "/" + year;
            SavePreferencesDateEntry(saveDateEntry); // saving DATE selection, for NEW ENTRY
        });

        // CONTINUE button clicked --> open NEW ENTRY
        Button continueButton = view.findViewById(R.id.continueEntry);
        continueButton.setOnClickListener(v -> {
            // going to NEW ENTRY
            Intent intentOther = new Intent(getActivity(), NewEntryScreen.class);
            startActivity(intentOther);
        });

        return view;
    }

    private void SavePreferencesDateEntry(String saveDate){ // saving DATE selected for NEW ENTRY
        SharedPreferences sharedPreferencesReport = getActivity().getSharedPreferences("MY_SHARED_PREF_DATE_ENTRY", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferencesReport.edit();
        editor.putString("Date Entry", saveDate);
        editor.apply();
    }

    private String changeFormat(int num){
        String tmp = String.valueOf(num);

        switch (num){
            case 1:
                tmp = "01";
                break;
            case 2:
                tmp = "02";
                break;
            case 3:
                tmp = "03";
                break;
            case 4:
                tmp = "04";
                break;
            case 5:
                tmp = "05";
                break;
            case 6:
                tmp = "06";
                break;
            case 7:
                tmp = "07";
                break;
            case 8:
                tmp = "08";
                break;
            case 9:
                tmp = "09";
                break;
        }

        return tmp;
    }

}

