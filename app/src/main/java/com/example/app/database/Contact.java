package com.example.app.database;

/**
 * A Contact object holds information about a single contact and is
 * used for interaction between the views/controllers and ManageDB.
 */
public class Contact implements TableObject<Contact> {
    String name, email, timeSpent, category;

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

    /**
     * Overriden toString method that formats the correct string for a Contact
     * @return the formatted string for a Contact
     */
    @Override
    public String toString() {
        return "";
    }

    /**
     * Overriden equals method for Contact object to check equality between this object and another Contact
     * Does not check if id's are equal
     * @param contact the contact to compare to
     * @return whether the input contact is equal to this contact
     */
    public boolean equals(Contact contact) {
        return contact.getName().equals(this.getName()) &&
                contact.getEmail().equals(this.getEmail()) &&
                contact.getTimeSpent().equals(this.getTimeSpent()) &&
                contact.getCategory().equals(this.getCategory());
    }
}
