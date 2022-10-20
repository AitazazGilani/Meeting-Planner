package com.example.app.database;

public class Contact {
    String name, email, timeSpent, category;

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
}
