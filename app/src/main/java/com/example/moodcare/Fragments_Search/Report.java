package com.example.moodcare.Fragments_Search;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.moodcare.Entry;
import com.example.moodcare.EntryAdapter;
import com.example.moodcare.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.Collections;

public class Report extends Fragment {
    private static int MODE_PRIVATE = 0;
    private final FirebaseAuth fAuth = FirebaseAuth.getInstance();
    private final FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private final CollectionReference users = fStore.collection("users");
    private TextView textViewData;
    private StringBuilder showText;
    private static final String TAG = "";
    int selector;
    private String chosenDay;
    private String givenDay;
    private final String defaultText = "No Entries found.";
    private String text_date; // date
    private String text_report; // daily/monthly
    private String text_by; // mood/activity/default
    private String text_icon; // icon type
    private ListView listViewEntries;
    private EntryAdapter adapter;

    public Report() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstancesState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);

        loadData(view); // load date, day/month, mood/activity/default, mood/activity icon
        loadReports(view); // load reports

        return view;
    }

    private void loadData(View view){
        //Load Date
        SharedPreferences sharedPreferencesDate = getActivity().getSharedPreferences("MY_SHARED_PREF_DATE", MODE_PRIVATE);
        text_date = sharedPreferencesDate.getString("Date", ""); // DATE

        // Load Report Type
        SharedPreferences sharedPreferencesReport = getActivity().getSharedPreferences("MY_SHARED_PREF_REPORT", MODE_PRIVATE);
        text_report = sharedPreferencesReport.getString("Report", ""); // REPORT
        if(text_report.equals("Day"))
            text_report = "Daily Report";
        else
            text_report = "Monthly Report";
        TextView txtOne = view.findViewById(R.id.textReport);
        txtOne.setText(text_report);

        // Load Search By Type
        SharedPreferences sharedPreferencesBy = getActivity().getSharedPreferences("MY_SHARED_PREF_BY", MODE_PRIVATE);
        text_by = "By: " + sharedPreferencesBy.getString("By", ""); // BY
        TextView txtTwo = view.findViewById(R.id.textBy);
        txtTwo.setText(text_by);

        // Load Icon Type
        SharedPreferences sharedPreferencesIcon = getActivity().getSharedPreferences("MY_SHARED_PREF_ICON", MODE_PRIVATE);
        text_icon = sharedPreferencesIcon.getString("Icon", ""); // ICON
    }

    private void loadReports(View view){
        final String TodayDate, folderName, userID;
        textViewData = view.findViewById(R.id.reports);
        listViewEntries = view.findViewById(R.id.listView);

        folderName = folderToSave(text_date.substring(text_date.indexOf("/")+1)); // save month/year in words
        chosenDay = text_date.substring(0, text_date.indexOf("/")); // chosen day
        userID = fAuth.getCurrentUser().getUid(); // user ID

        users.document(userID).collection(folderName).get().addOnSuccessListener(queryDocumentSnapshots -> {

            ArrayList<Entry> entryList = new ArrayList<>(); // list of entries
            Entry tmp;

            if (!queryDocumentSnapshots.isEmpty()) {
                for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                    tmp = snapshot.toObject(Entry.class); // Entry

                    assert tmp != null;
                    givenDay = tmp.getReportDate();
                    givenDay =  givenDay.substring(0, givenDay.indexOf("/")); // given day

                    if(text_report.equals("Daily Report")) { // DAILY

                        if (givenDay.equals(chosenDay)) { // check if its an entry of today's date

                            if(text_by.equals("By: Mood") && text_icon.equals(tmp.getReportMood()))
                                entryList.add(tmp); // add chosen entries to list

                            else if(text_by.equals("By: Activity") && text_icon.equals(tmp.getReportActivity()))
                                entryList.add(tmp); // add chosen entries to list

                            else if(text_by.equals("By: Default"))
                                entryList.add(tmp); // add chosen entries to list

                        }

                    }else{ // MONTHLY

                        if(text_by.equals("By: Mood") && text_icon.equals(tmp.getReportMood()))
                            entryList.add(tmp); // add chosen entries to list

                        else if(text_by.equals("By: Activity") && text_icon.equals(tmp.getReportActivity()))
                            entryList.add(tmp); // add chosen entries to list

                        else if(text_by.equals("By: Default"))
                            entryList.add(tmp); // add chosen entries to list

                    }
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

                    adapter = new EntryAdapter(getActivity(), entryList);
                    listViewEntries.setAdapter(adapter);

                }else// No entries found
                    textViewData.setText(defaultText);

            }else // No entries found
                textViewData.setText(defaultText);
        });

    }

    private String folderToSave(String dateInNumbers){
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