package com.example.app.database;

public class Task {
    String name, date, time, category, duration, contactName;

    public Task(String name, String date, String time, String category, String duration, String contactName) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.category = category;
        this.duration = duration;
        this.contactName = contactName;
    }

    public String getName() {
        return this.name;
    }

    public String getDate() {
        return this.date;
    }

    public String getTime() {
        return this.time;
    }

    public String getCategory() {
        return this.category;
    }

    public String getDuration() {
        return this.duration;
    }

    public String getContactName() {
        return this.contactName;
    }
}
