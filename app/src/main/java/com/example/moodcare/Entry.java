package com.example.moodcare;

public class Entry {
    private String reportDate;
    private String reportTime;
    private String reportMood;
    private String reportActivity;
    private String reportNotes;

    public Entry(){
        // public no-arg constructor needed
    }

    Entry(String date, String time, String mood, String activity, String notes){
        this.reportDate = date;
        this.reportTime = time;
        this.reportMood = mood;
        this.reportActivity = activity;
        this.reportNotes = notes;
    }

    public String getReportDate() {
        return reportDate;
    }

    public String getReportTime() {
        return reportTime;
    }

    public String getReportMood() {
        return reportMood;
    }

    public String getReportActivity() {
        return reportActivity;
    }

    public String getReportNotes() {
        return reportNotes;
    }

}
