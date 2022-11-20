package com.example.app.database;

/**
 * A Contact object holds information about a single contact and is
 * used for interaction between the views/controllers and ManageDB.
 */
public class Contact implements TableObject<Contact> {
    String name, email, timeSpent, category;

    /**to track if the task is favourited or not
     * initialized to be false by default**/
    boolean favorite;

    /**
     * ID for the contact, must be null if the views/controller are creating a contact.
     * handled by ManageDB
     */
    int UID;


    public Contact(String name, String email, String timeSpent, String category) {
        this.name = name;
        this.email = email;
        this.timeSpent = timeSpent;
        this.category = category;
        favorite = false;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getTimeSpent() {
        return this.timeSpent;
    }

    public String getCategory() {
        return this.category;
    }

    public void setName(String s) {
        this.name = s;
    }

    public void setEmail(String s) {
        this.email = s;
    }

    public void setTimeSpent(String s) {
        this.timeSpent = s;
    }

    public void setCategory(String s) {
        this.category = s;
    }

    public boolean isFavorite() { return favorite; }

    public void setFavorite(boolean favorite) { this.favorite = favorite; }

    /**
     * DO NOT USE IN VIEWS OR CONTROLLER, Set the UID for a task.
     * Handled by ManageDB
     * @param x: id to be added
     * @postcond uid is set
     */
    public void setUID(int x){this.UID = x;}

    /**
     * get UID for that task, not needed by controller or views.
     * Handled by ManageDB.
     * @return uid
     */
    public int getUID(){return this.UID;}

    public boolean equals(Contact contact) {
        return contact.getName().equals(this.getName()) &&
                contact.getEmail().equals(this.getEmail()) &&
                contact.getTimeSpent().equals(this.getTimeSpent()) &&
                contact.getCategory().equals(this.getCategory());
    }
}
