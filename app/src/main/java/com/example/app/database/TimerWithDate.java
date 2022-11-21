package com.example.app.database;

public class TimerWithDate {
    String timer, date;

    public TimerWithDate(String date, String timer) {
        this.date = date;
        this.timer = timer;
    }

    public String getTimer() {
        return timer;
    }

    public void setTimer(String timer) {
        this.timer = timer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
