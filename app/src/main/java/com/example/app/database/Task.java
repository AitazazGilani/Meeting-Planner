package com.example.app.database;

/**
 * A Task object holds information about a single task and is
 * used for interaction between the views/controllers and managedb.
 */
public class Task implements TableObject<Task> {
    String name, date, time, category, duration, timespent ,contactName;

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
    public Task(String name, String date, String time, String category, String duration, String timespent, String contactName) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.category = category;
        this.duration = duration;
        this.timespent = timespent;
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

    public String getTimespent() {
        return this.timespent;
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

    public void setTimespent(String s) {
        this.timespent = s;
    }

    public void getContactName(String s) {
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

    /**
     * Overriden toString method that formats the correct string for a Task
     * @return the formatted string for a Task
     */
    @Override
    public String toString() {
        return "";
    }

    /**
     * Overriden equals method for Task object to check equality between this object and another Task
     * Does not check if id's are equal
     * @param task the task to compare to
     * @return whether the input task is equal to this task
     */
    public boolean equals(Task task) {
        return task.getName().equals(this.getName()) &&
                task.getDate().equals(this.getDate()) &&
                task.getTime().equals(this.getTime()) &&
                task.getCategory().equals(this.getCategory()) &&
                task.getDuration().equals(this.getDuration()) &&
                task.getTimespent().equals(this.getTimespent()) &&
                task.getContactName().equals(this.getContactName());
    }
}
