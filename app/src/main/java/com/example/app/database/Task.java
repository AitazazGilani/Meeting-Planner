package com.example.app.database;

/**
 * A Task object holds information about a single task and is
 * used for interaction between the views/controllers and managedb.
 */
public class Task implements TableObject<Task> {
    String name, date, time, category, duration, timeSpent ,contactName;

    /**
     * ID for the task, must be null if the views/controller are creating a task.
     * handled by ManageDB
     */
    int UID;

    /**
     *Time, duration, timespent must be in 24hour format
     * format: HH:MM:SS
     * Date must be in YYYY-MM-DD format
     */
    public Task(String name, String date, String time, String category, String duration, String timeSpent, String contactName) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.category = category;
        this.duration = duration;
        this.timeSpent = timeSpent;
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

    public String getTimeSpent() {
        return this.timeSpent;
    }

    public String getContactName() {
        return this.contactName;
    }

    public void setName(String s) {
        this.name = s;
    }

    public void setDate(String s) {
        this.date = s;
    }

    public void setTime(String s) {
        this.time = s;
    }

    public void setCategory(String s) {
        this.category = s;
    }

    public void setDuration(String s) {
        this.duration = s;
    }

    public void setTimeSpent(String s) {
        this.timeSpent = s;
    }

    public void setContactName(String s) {
        this.contactName = s;
    }

    /**
     * DO NOT USE IN VIEWS OR CONTROLLER, Set the UID for a task.
     * Handled by ManageDB
     * @param x: id to be added
     * @postcond uid is set
     */
    public void setUID(int x){this.UID = x;}

    /**
     * get UID for that task, not needed by controller or views.
     * Handled by ManageDB
     * @return uid
     */
    public int getUID(){return this.UID;}

    public String toString() {
        // TODO: implement toString method
        return this.getName();
    }

    public boolean equals(Task task) {
        return task.getName().equals(this.getName()) &&
                task.getDate().equals(this.getDate()) &&
                task.getTime().equals(this.getTime()) &&
                task.getCategory().equals(this.getCategory()) &&
                task.getDuration().equals(this.getDuration()) &&
                task.getTimeSpent().equals(this.getTimeSpent()) &&
                task.getContactName().equals(this.getContactName());
    }
}
