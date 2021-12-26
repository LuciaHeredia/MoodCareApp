package com.example.moodcare;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.moodcare.Fragments_NewEntry.PickDay;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Created by: lucia heredia inga
 * runs on: Nexus 5X API 30(best)
 */

public class MainScreen extends AppCompatActivity {
    private final FirebaseAuth fAuth = FirebaseAuth.getInstance();
    private final FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private final CollectionReference users = fStore.collection("users");
    private TextView textViewData;
    StringBuilder showText;
    private static final String TAG = "";
    int selector;
    private String todayDay;
    private String givenDay;
    private final String defaultText = "No Entries today.";
    private ListView listViewEntries;
    private EntryAdapter adapter;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);

        // DATE
        TextView textDate= findViewById(R.id.date);
        String currentDatedTime = new SimpleDateFormat("EEEEEEE dd/MM/yyyy", Locale.getDefault()).format(new Date());
        textDate.setText(currentDatedTime);

        // QUOTES
        Resources res = getResources();
        String[] quoteTexts = res.getStringArray(R.array.quotes_array);

        Random randomNumber = new Random();
        selector = randomNumber.nextInt(quoteTexts.length); // choose random quote number

        TextView textQuote = findViewById(R.id.quote);
        textQuote.setText(quoteTexts[4]); // show quote

    }

    @Override
    protected void onStart() {   // TODAY'S ENTRIES TO DISPLAY
        super.onStart();

        final String TodayDate, folderName, userID;
        textViewData = findViewById(R.id.entryText);
        listViewEntries = findViewById(R.id.listView);

        // Today's date
        TodayDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        todayDay =  TodayDate.substring(0, TodayDate.indexOf("/")); // today's day
        folderName = folderToSave(TodayDate.substring(TodayDate.indexOf("/")+1)); // save month/year in words

        userID = fAuth.getCurrentUser().getUid(); // user ID

        // will stop getting today's entries once gone from MAIN screen
        users.document(userID).collection(folderName).addSnapshotListener(this, (value, error) -> {
            if(error!=null){
                Log.d(TAG, error.toString());
                return;
            }

            users.document(userID).collection(folderName).get().addOnSuccessListener(queryDocumentSnapshots -> {

                ArrayList<Entry> entryList = new ArrayList<>(); // list of entries
                Entry tmp;

                if (!queryDocumentSnapshots.isEmpty()) {
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                        tmp = snapshot.toObject(Entry.class); // Entry

                        assert tmp != null;
                        givenDay = tmp.getReportDate();
                        givenDay =  givenDay.substring(0, givenDay.indexOf("/")); // given day

                        // check if its an entry of today's date
                        if(givenDay.equals(todayDay))
                            entryList.add(tmp); // add today's entries to list
                    }

                    // sort entries from last to first - in reverse order
                    Collections.sort(entryList, (o1, o2) -> {
                        int c;
                        c = o2.getReportDate().compareTo(o1.getReportDate());
                        if (c == 0)
                            c = o2.getReportTime().compareTo(o1.getReportTime());
                        return c;
                    });

                    if(!entryList.isEmpty()) {
                        adapter = new EntryAdapter(MainScreen.this, entryList);
                        listViewEntries.setAdapter(adapter);
                    }else{
                        textViewData.setText(defaultText); // show in MAIN screen
                    }
                }
            });

        });
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod =
            item -> {
                Intent activity = null;
                Fragment fragment = null;

                switch (item.getItemId())
                {
                    case R.id.mainScreen:
                        activity = new Intent(MainScreen.this, MainScreen.class);
                        break;

                    case R.id.newEntryScreen:
                        Button logout= findViewById(R.id.logout);
                        logout.setClickable(false);
                        fragment = new PickDay();
                        break;

                    case R.id.searchScreen:
                        activity = new Intent(MainScreen.this, SearchScreen.class);
                        break;
                }

                if(activity!=null)
                    startActivity(activity);
                else{
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    assert fragment != null;
                    transaction.replace(R.id.fragment_container, fragment).addToBackStack(null);
                    transaction.commit();
                }

                return true;
            };

    public void onBackPressed() {
        finishAffinity(); // If BACK ANDROID pressed->EXIT APP
    }

    public void logout(View view){  // LOG OUT button clicked --> open activity SignLogRes
        final Button buttonLogOut = findViewById(R.id.logout);
        buttonLogOut.setBackgroundResource(R.drawable.logout_clicked); // when clicked

        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getApplicationContext(), SignLogResScreen.class);
        startActivity(intent);
        finish();
    }

    String folderToSave(String dateInNumbers){
        String folder="";
        String month = dateInNumbers.substring( 0, dateInNumbers.indexOf("/"));
        String year = dateInNumbers.substring(dateInNumbers.indexOf("/")+1);

        switch (month){
            case "01":
            case "1":
                folder = "Jan " + year;
                break;
            case "02":
            case "2":
                folder = "Feb " + year;
                break;
            case "03":
            case "3":
                folder = "Mar " + year;
                break;
            case "04":
            case "4":
                folder = "Apr " + year;
                break;
            case "05":
            case "5":
                folder = "May " + year;
                break;
            case "06":
            case "6":
                folder = "Jun " + year;
                break;
            case "07":
            case "7":
                folder = "Jul " + year;
                break;
            case "08":
            case "8":
                folder = "Aug " + year;
                break;
            case "09":
            case "9":
                folder = "Sep " + year;
                break;
            case "10":
                folder = "Oct " + year;
                break;
            case "11":
                folder = "Nov " + year;
                break;
            case "12":
                folder = "Dec " + year;
                break;
        }
        return folder;
    }

}
