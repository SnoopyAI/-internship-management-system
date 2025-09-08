package com.informationconfig.spring.bootcamp.bootcamp_proyect.models;

public class LogBook {
    
    private String logBookId;
    private String date;
    private String[] activities; 
    private String content;

    public String getLogBookId() {
        return logBookId;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String[] getActivities() { 
        return activities;
    }
    public void setActivities(String[] activities) { 
        this.activities = activities;
    }
    public String getObservations() {
        return content;
    }
    public void setObservations(String observations) {
        this.content = observations;
    }
    public void addEntry(String entry) {
        // Logic to add an entry to the log book
    }
    public void removeEntry(String entry) {
        // Logic to remove an entry from the log book
    }
    public void editEntry(String oldEntry, String newEntry) {
        // Logic to edit an existing entry in the log book
    }
}
