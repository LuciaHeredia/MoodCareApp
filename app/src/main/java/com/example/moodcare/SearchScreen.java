package com.example.moodcare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.moodcare.Fragments_NewEntry.PickDay;
import com.example.moodcare.Fragments_Search.ByActivity;
import com.example.moodcare.Fragments_Search.ByMood;
import com.example.moodcare.Fragments_Search.Report;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SearchScreen extends AppCompatActivity {
    private RadioGroup radioByGroup;
    private RadioGroup radioReportGroup;
    private String saveDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);

        // CALENDAR
        CalendarView mCalendarView = findViewById(R.id.calendarView);
        saveDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(mCalendarView.getDate());
        SavePreferencesDate(saveDate); // saving current DATE, for next fragment

        Calendar c = Calendar.getInstance();
        mCalendarView.setMaxDate(c.getTimeInMillis()); // set max date to pick

        mCalendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String newDayOfMonth = changeDayFormat(dayOfMonth);
            saveDate =  newDayOfMonth + "/" + (month+1) + "/" + year;
            SavePreferencesDate(saveDate); // saving DATE selection, for next fragment
        });

        // CONTINUE button clicked
        addListenerOnButton();
    }

    private String changeDayFormat(int day){
        String newDay = String.valueOf(day);

        switch (day){
            case 1:
                newDay = "01";
                break;
            case 2:
                newDay = "02";
                break;
            case 3:
                newDay = "03";
                break;
            case 4:
                newDay = "04";
                break;
            case 5:
                newDay = "05";
                break;
            case 6:
                newDay = "06";
                break;
            case 7:
                newDay = "07";
                break;
            case 8:
                newDay = "08";
                break;
            case 9:
                newDay = "09";
                break;
        }

        return newDay;
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod =
            item -> {
                Intent activity = null;
                Fragment fragment = null;
                switch (item.getItemId())
                {
                    case R.id.mainScreen:
                        activity = new Intent(SearchScreen.this, MainScreen.class);
                        break;

                    case R.id.newEntryScreen:
                        fragment = new PickDay();
                        break;

                    case R.id.searchScreen:
                        activity = new Intent(SearchScreen.this, SearchScreen.class);
                        break;
                }

                if(activity!=null)
                    startActivity(activity);
                else{
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    assert fragment != null;
                    transaction.replace(R.id.fragment_container_search, fragment).addToBackStack(null);
                    transaction.commit();
                }

                return true;
            };

    private void addListenerOnButton() {
        Button buttonFragment = findViewById(R.id.continueSearch);
        radioByGroup = findViewById(R.id.radioBy);
        radioReportGroup = findViewById(R.id.radioReport);

        buttonFragment.setOnClickListener(v -> {
            Fragment fragment = null;
            String saveBy;
            String saveReport;

            // get selected radio button from radioByGroup
            int selectedIdBy = radioByGroup.getCheckedRadioButtonId();

            switch (selectedIdBy) {
                case R.id.radioMood:
                    fragment = new ByMood();
                    break;

                case R.id.radioActivity:
                    fragment = new ByActivity();
                    break;

                case R.id.radioDefault:
                    fragment = new Report();
                    break;
            }

            RadioButton radioByButton = findViewById(selectedIdBy); // find radio_By_button by id
            saveBy = (String) radioByButton.getText();
            SavePreferencesBy(saveBy); // saving BY selection, for next fragment

            // get selected radio button from radioReportGroup
            int selectedIdReport = radioReportGroup.getCheckedRadioButtonId();
            RadioButton radioReportButton = findViewById(selectedIdReport); // find radio_Report_button by id
            saveReport = (String) radioReportButton.getText();
            SavePreferencesReport(saveReport); // saving REPORT selection, for next fragment

            if(fragment!=null) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_search, fragment).addToBackStack(null);
                transaction.commit();
            }
        });
    }

    private void SavePreferencesBy(String saveBy){ // saving BY selection for the search
        SharedPreferences sharedPreferencesBy = getSharedPreferences("MY_SHARED_PREF_BY", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferencesBy.edit();
        editor.putString("By", saveBy);
        editor.apply();
    }

    private void SavePreferencesReport(String saveReport){ // saving REPORT selection for the search
        SharedPreferences sharedPreferencesReport = getSharedPreferences("MY_SHARED_PREF_REPORT", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferencesReport.edit();
        editor.putString("Report", saveReport);
        editor.apply();
    }

    private void SavePreferencesDate(String saveDate){ // saving DATE selected for the search
        SharedPreferences sharedPreferencesReport = getSharedPreferences("MY_SHARED_PREF_DATE", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferencesReport.edit();
        editor.putString("Date", saveDate);
        editor.apply();
    }

}