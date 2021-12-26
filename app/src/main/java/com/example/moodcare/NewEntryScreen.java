package com.example.moodcare;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NewEntryScreen extends AppCompatActivity {
    private String text_date; // DATE
    private String folderName; // name of folder to save the report
    private String currentTime; // TIME
    private String mood = ""; // MOOD
    private String activity = ""; // ACTIVITY
    private String notes; // NOTES
    private final FirebaseAuth fAuth = FirebaseAuth.getInstance();
    private final FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private final CollectionReference users = fStore.collection("users");
    private String userID;
    private  int hourNum;
    TimePickerDialog picker;
    TextView txtTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);

        // DATE
        loadDate();

        // TIME
        currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        txtTime = findViewById(R.id.hourText); // TIME
        String hour = currentTime.substring(0, currentTime.indexOf(":")); // get HOUR
        switch (hour) {
            case "21":
                hour = "00";
                break;
            case "22":
                hour = "01";
                break;
            case "23":
                hour = "02";
                break;
            case "00":
                hour = "03";
                break;
            default:
                hourNum = Integer.parseInt(hour) + 3;
                hour = String.valueOf(hourNum);
                break;
        }
        currentTime = hour + ":" + currentTime.substring(currentTime.indexOf(":")+1);
        txtTime.setText(currentTime);

        // CHANGE TIME
        final Button changeTime = findViewById(R.id.changeTime);
        changeTime.setOnClickListener(v -> {
            final Calendar cal = Calendar.getInstance();
            int hour1 = cal.get(Calendar.HOUR_OF_DAY);
            int minutes = cal.get(Calendar.MINUTE);

            // time picker dialog
            picker = new TimePickerDialog(NewEntryScreen.this,
                    (tp, sHour, sMinute) -> {
                        String tmp;
                        if ((sHour >= 0 && sHour < 10))
                            currentTime = "0" + sHour + ":" + sMinute;
                        if ((sMinute >= 0 && sMinute < 10))
                            currentTime = sHour + ":0" + sMinute;
                        if(sHour >= 10 && sMinute >=10)
                            currentTime = sHour + ":" + sMinute;
                        txtTime.setText(currentTime);
                    }, hour1, minutes, true);
            picker.show();
        });

        // MOOD
        moodChosen();

        // ACTIVITY
        activityChosen();

        // button SAVE pressed
        Button saveEntry = findViewById(R.id.saveEntry);
        saveEntry.setOnClickListener(v -> {

            if(!mood.equals("") && !activity.equals("")) { // mood & activity must be selected
                // NOTES
                EditText editText = findViewById(R.id.words);
                notes = editText.getText().toString();
                if(notes.isEmpty())
                    notes = "-";

                folderName = folderToSave(text_date.substring(text_date.indexOf("/")+1)); // save month/year in words

                // STORE USER REPORT
                userID = fAuth.getCurrentUser().getUid(); // user ID
                Entry entry = new Entry(text_date, currentTime, mood, activity, notes); // new entry object
                users.document(userID).collection(folderName).add(entry).addOnSuccessListener(documentReference -> {
                    Toast.makeText(NewEntryScreen.this, "Report saved.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(NewEntryScreen.this, MainScreen.class)); // go to MAIN screen
                }).addOnFailureListener(e -> Toast.makeText(NewEntryScreen.this, "ERROR: " + e.getMessage(), Toast.LENGTH_LONG).show());
            } else{
                Toast.makeText(NewEntryScreen.this, "MOOD & ACTIVITY must be selected.", Toast.LENGTH_SHORT).show();
            }

        });

    }

    private void loadDate(){
        SharedPreferences sharedPreferencesDate = getSharedPreferences("MY_SHARED_PREF_DATE_ENTRY", MODE_PRIVATE);
        text_date = sharedPreferencesDate.getString("Date Entry", ""); // DATE
        TextView txtDate = findViewById(R.id.dateText);
        txtDate.setText(text_date);
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

    private void unClick(Button[] group, String str){
        // un-click all
        if(str.equals("mood")) {
            group[0].setBackgroundResource(R.drawable.mood_love);
            group[1].setBackgroundResource(R.drawable.mood_peaceful);
            group[2].setBackgroundResource(R.drawable.mood_happy);
            group[3].setBackgroundResource(R.drawable.mood_good);
            group[4].setBackgroundResource(R.drawable.mood_ok);
            group[5].setBackgroundResource(R.drawable.mood_meh);
            group[6].setBackgroundResource(R.drawable.mood_sad);
            group[7].setBackgroundResource(R.drawable.mood_angry);
            group[8].setBackgroundResource(R.drawable.mood_awful);
            group[9].setBackgroundResource(R.drawable.mood_annoyed);
            group[10].setBackgroundResource(R.drawable.mood_tired);
        }
        else if(str.equals("activity")){
            group[0].setBackgroundResource(R.drawable.act_me);
            group[1].setBackgroundResource(R.drawable.act_friends);
            group[2].setBackgroundResource(R.drawable.act_family);
            group[3].setBackgroundResource(R.drawable.act_date);
            group[4].setBackgroundResource(R.drawable.act_study);
            group[5].setBackgroundResource(R.drawable.act_work);
            group[6].setBackgroundResource(R.drawable.act_sick);
            group[7].setBackgroundResource(R.drawable.act_workout);
            group[8].setBackgroundResource(R.drawable.act_party);
            group[9].setBackgroundResource(R.drawable.act_travel);
            group[10].setBackgroundResource(R.drawable.act_eating);
            group[11].setBackgroundResource(R.drawable.act_cleaning);
            group[12].setBackgroundResource(R.drawable.act_shopping);
            group[13].setBackgroundResource(R.drawable.act_reading);
            group[14].setBackgroundResource(R.drawable.act_sleeping);
            group[15].setBackgroundResource(R.drawable.act_movie);
            group[16].setBackgroundResource(R.drawable.act_other);
        }
    }

    private void moodChosen(){
        final TextView textOfMood =  findViewById(R.id.textMood); // name of MOOD icon

        final Button love = findViewById(R.id.love);
        final Button peaceful = findViewById(R.id.peaceful);
        final Button happy = findViewById(R.id.happy);
        final Button good = findViewById(R.id.good);
        final Button ok = findViewById(R.id.ok);
        final Button meh = findViewById(R.id.meh);
        final Button sad = findViewById(R.id.sad);
        final Button angry = findViewById(R.id.angry);
        final Button awful = findViewById(R.id.awful);
        final Button annoyed = findViewById(R.id.annoyed);
        final Button tired = findViewById(R.id.tired);

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
                unClick(moodButtons,"mood"); // un-click others
            love.setBackgroundResource(R.drawable.mood_love_clicked); // when clicked
            mood = "in love";
            textOfMood.setText(mood);
            mood = "love";
        });

        // MOOD: PEACEFUL
        peaceful.setOnClickListener(v -> {
            if(!mood.equals(""))
                unClick(moodButtons, "mood"); // un-click others
            peaceful.setBackgroundResource(R.drawable.mood_peaceful_clicked); // when clicked
            mood = "peaceful";
            textOfMood.setText(mood);
        });

        // MOOD: HAPPY
        happy.setOnClickListener(v -> {
            if(!mood.equals(""))
                unClick(moodButtons, "mood"); // un-click others
            happy.setBackgroundResource(R.drawable.mood_happy_clicked); // when clicked
            mood = "happy";
            textOfMood.setText(mood);
        });

        // MOOD: GOOD
        good.setOnClickListener(v -> {
            if(!mood.equals(""))
                unClick(moodButtons, "mood"); // un-click others
            good.setBackgroundResource(R.drawable.mood_good_clicked); // when clicked
            mood = "good";
            textOfMood.setText(mood);
        });

        // MOOD: OK
        ok.setOnClickListener(v -> {
            if(!mood.equals(""))
                unClick(moodButtons, "mood"); // un-click others
            ok.setBackgroundResource(R.drawable.mood_ok_clicked); // when clicked
            mood = "ok";
            textOfMood.setText(mood);
        });

        // MOOD: MEH
        meh.setOnClickListener(v -> {
            if(!mood.equals(""))
                unClick(moodButtons, "mood"); // un-click others
            meh.setBackgroundResource(R.drawable.mood_meh_clicked); // when clicked
            mood = "meh";
            textOfMood.setText(mood);
        });

        // MOOD: SAD
        sad.setOnClickListener(v -> {
            if(!mood.equals(""))
                unClick(moodButtons, "mood"); // un-click others
            sad.setBackgroundResource(R.drawable.mood_sad_clicked); // when clicked
            mood = "sad";
            textOfMood.setText(mood);
        });

        // MOOD: ANGRY
        angry.setOnClickListener(v -> {
            if(!mood.equals(""))
                unClick(moodButtons, "mood"); // un-click others
            angry.setBackgroundResource(R.drawable.mood_angry_clicked); // when clicked
            mood = "angry";
            textOfMood.setText(mood);
        });

        // MOOD: AWFUL
        awful.setOnClickListener(v -> {
            if(!mood.equals(""))
                unClick(moodButtons, "mood"); // un-click others
            awful.setBackgroundResource(R.drawable.mood_awful_clicked); // when clicked
            mood = "awful";
            textOfMood.setText(mood);
        });

        // MOOD: ANNOYED
        annoyed.setOnClickListener(v -> {
            if(!mood.equals(""))
                unClick(moodButtons, "mood"); // un-click others
            annoyed.setBackgroundResource(R.drawable.mood_annoyed_clicked); // when clicked
            mood = "annoyed";
            textOfMood.setText(mood);
        });

        // MOOD: TIRED
        tired.setOnClickListener(v -> {
            if(!mood.equals(""))
                unClick(moodButtons, "mood"); // un-click others
            tired.setBackgroundResource(R.drawable.mood_tired_clicked); // when clicked
            mood = "tired";
            textOfMood.setText(mood);
        });
    }

    private void activityChosen(){
        final TextView textOfAct =  findViewById(R.id.textActivity); // name of ACTIVITY icon

        final Button me = findViewById(R.id.meTime);
        final Button friends = findViewById(R.id.friends);
        final Button family = findViewById(R.id.family);
        final Button date = findViewById(R.id.date);
        final Button study = findViewById(R.id.study);
        final Button work = findViewById(R.id.work);
        final Button sick = findViewById(R.id.sick);
        final Button workout = findViewById(R.id.workout);
        final Button party = findViewById(R.id.party);
        final Button travel = findViewById(R.id.travel);
        final Button eating = findViewById(R.id.eating);
        final Button cleaning = findViewById(R.id.cleaning);
        final Button shopping = findViewById(R.id.shopping);
        final Button reading = findViewById(R.id.reading);
        final Button sleeping = findViewById(R.id.sleeping);
        final Button movie = findViewById(R.id.movie);
        final Button other = findViewById(R.id.other);

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
                unClick(actButtons, "activity"); // un-click others
            me.setBackgroundResource(R.drawable.act_me_clicked); // when clicked
            activity = "me time";
            textOfAct.setText(activity);
            activity = "me";
        });

        // ACTIVITY: FRIENDS
        friends.setOnClickListener(v -> {
            if(!activity.equals(""))
                unClick(actButtons, "activity"); // un-click others
            friends.setBackgroundResource(R.drawable.act_friends_clicked); // when clicked
            activity = "friends";
            textOfAct.setText(activity);
        });

        // ACTIVITY: FAMILY
        family.setOnClickListener(v -> {
            if(!activity.equals(""))
                unClick(actButtons, "activity"); // un-click others
            family.setBackgroundResource(R.drawable.act_family_clicked); // when clicked
            activity = "family";
            textOfAct.setText(activity);
        });

        // ACTIVITY: DATE
        date.setOnClickListener(v -> {
            if(!activity.equals(""))
                unClick(actButtons, "activity"); // un-click others
            date.setBackgroundResource(R.drawable.act_date_clicked); // when clicked
            activity = "date";
            textOfAct.setText(activity);
        });

        // ACTIVITY: STUDY
        study.setOnClickListener(v -> {
            if(!activity.equals(""))
                unClick(actButtons, "activity"); // un-click others
            study.setBackgroundResource(R.drawable.act_study_clicked); // when clicked
            activity = "study";
            textOfAct.setText(activity);
        });

        // ACTIVITY: WORK
        work.setOnClickListener(v -> {
            if(!activity.equals(""))
                unClick(actButtons, "activity"); // un-click others
            work.setBackgroundResource(R.drawable.act_work_clicked); // when clicked
            activity = "work";
            textOfAct.setText(activity);
        });

        // ACTIVITY: SICK
        sick.setOnClickListener(v -> {
            if(!activity.equals(""))
                unClick(actButtons, "activity"); // un-click others
            sick.setBackgroundResource(R.drawable.act_sick_clicked); // when clicked
            activity = "sick";
            textOfAct.setText(activity);
        });

        // ACTIVITY: WORKOUT
        workout.setOnClickListener(v -> {
            if(!activity.equals(""))
                unClick(actButtons, "activity"); // un-click others
            workout.setBackgroundResource(R.drawable.act_workout_clicked); // when clicked
            activity = "workout";
            textOfAct.setText(activity);
        });

        // ACTIVITY: PARTY
        party.setOnClickListener(v -> {
            if(!activity.equals(""))
                unClick(actButtons, "activity"); // un-click others
            party.setBackgroundResource(R.drawable.act_party_clicked); // when clicked
            activity = "party";
            textOfAct.setText(activity);
        });

        // ACTIVITY: TRAVEL
        travel.setOnClickListener(v -> {
            if(!activity.equals(""))
                unClick(actButtons, "activity"); // un-click others
            travel.setBackgroundResource(R.drawable.act_travel_clicked); // when clicked
            activity = "travel";
            textOfAct.setText(activity);
        });

        // ACTIVITY: EATING
        eating.setOnClickListener(v -> {
            if(!activity.equals(""))
                unClick(actButtons, "activity"); // un-click others
            eating.setBackgroundResource(R.drawable.act_eating_clicked); // when clicked
            activity = "eating";
            textOfAct.setText(activity);
        });

        // ACTIVITY: CLEANING
        cleaning.setOnClickListener(v -> {
            if(!activity.equals(""))
                unClick(actButtons, "activity"); // un-click others
            cleaning.setBackgroundResource(R.drawable.act_cleaning_clicked); // when clicked
            activity = "cleaning";
            textOfAct.setText(activity);
        });

        // ACTIVITY: SHOPPING
        shopping.setOnClickListener(v -> {
            if(!activity.equals(""))
                unClick(actButtons, "activity"); // un-click others
            shopping.setBackgroundResource(R.drawable.act_shopping_clicked); // when clicked
            activity = "shopping";
            textOfAct.setText(activity);
        });

        // ACTIVITY: READING
        reading.setOnClickListener(v -> {
            if(!activity.equals(""))
                unClick(actButtons, "activity"); // un-click others
            reading.setBackgroundResource(R.drawable.act_reading_clicked); // when clicked
            activity = "reading";
            textOfAct.setText(activity);
        });

        // ACTIVITY: SLEEPING
        sleeping.setOnClickListener(v -> {
            if(!activity.equals(""))
                unClick(actButtons, "activity"); // un-click others
            sleeping.setBackgroundResource(R.drawable.act_sleeping_clicked); // when clicked
            activity = "sleeping";
            textOfAct.setText(activity);
        });

        // ACTIVITY: MOVIE
        movie.setOnClickListener(v -> {
            if(!activity.equals(""))
                unClick(actButtons, "activity"); // un-click others
            movie.setBackgroundResource(R.drawable.act_movie_clicked); // when clicked
            activity = "movie";
            textOfAct.setText(activity);
        });

        // ACTIVITY: OTHER
        other.setOnClickListener(v -> {
            if(!activity.equals(""))
                unClick(actButtons, "activity"); // un-click others
            other.setBackgroundResource(R.drawable.act_other_clicked); // when clicked
            activity = "other";
            textOfAct.setText(activity);
        });
    }
}
