package com.example.moodcare;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class EntryAdapter extends BaseAdapter {
    private Activity context;
    private ArrayList<Entry> list;
    private static LayoutInflater inflater = null;

    public EntryAdapter(Activity context, ArrayList<Entry> list){
        this.context = context;
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        itemView = (itemView==null) ? inflater.inflate(R.layout.list_object, null): itemView;

        ImageView mood = itemView.findViewById(R.id.imageView);
        TextView txtDateTime = itemView.findViewById(R.id.date_time);
        TextView txtActivity = itemView.findViewById(R.id.textAct);
        TextView txtNotes = itemView.findViewById(R.id.textNotes);
        Entry selectedEntry = list.get(position);

        // MOOD ICON
        mood.setImageResource(moodIconId(selectedEntry.getReportMood(), itemView));

        // DATE & TIME
        StringBuilder showDateTime = new StringBuilder();
        showDateTime.append(selectedEntry.getReportDate());
        showDateTime.append("\n");
        showDateTime.append(selectedEntry.getReportTime());
        txtDateTime.setText(showDateTime); // show in MAIN screen

        // ACTIVITY & NOTES
        txtActivity.setText(selectedEntry.getReportActivity());
        txtNotes.setText(selectedEntry.getReportNotes());

        return itemView;
    }

    private int moodIconId(String name, View view) {
        int moodId=0;

        final int love = R.drawable.mood_love;
        final int peaceful = R.drawable.mood_peaceful;
        final int happy = R.drawable.mood_happy;
        final int good = R.drawable.mood_good;
        final int ok = R.drawable.mood_ok;
        final int meh = R.drawable.mood_meh;
        final int sad = R.drawable.mood_sad;
        final int angry = R.drawable.mood_angry;
        final int awful = R.drawable.mood_awful;
        final int annoyed = R.drawable.mood_annoyed;
        final int tired = R.drawable.mood_tired;

        switch (name) {
            case "love":
                moodId = love;
                break;

            case "peaceful":
                moodId = peaceful;
                break;

            case "happy":
                moodId = happy;
                break;

            case "good":
                moodId = good;
                break;

            case "ok":
                moodId = ok;
                break;

            case "meh":
                moodId = meh;
                break;

            case "sad":
                moodId = sad;
                break;

            case "angry":
                moodId = angry;
                break;

            case "awful":
                moodId = awful;
                break;

            case "annoyed":
                moodId = annoyed;
                break;

            case "tired":
                moodId = tired;
                break;
        }
        return moodId;
    }

}
