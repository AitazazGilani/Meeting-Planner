package com.example.app.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;


/**
 * Class to do regression testing on ManageDB
 */
public class ManageDBTests2 {
    private static final String PATH = "res";
    private static final String URL = "jdbc:sqlite:" + PATH + "/database.db";

    /**
     * main method used for testing
     * WARNING: RESETS WHOLE DATABASE, REMOVING ALL DATA
     */
    public static void main(String[] args) {
        // removes database if it already exists
        File f = new File("res/database.db");
        f.delete();

        // create new db
        ManageDB db = new ManageDB();

        // remove all data if any exists (fallback from f.delete() incase database file is active and cannot be deleted)
        reset(db);

        // REGRESSION TESTS
        System.out.println("\nRUNNING SORTING REGRESSION TESTS");


        // create some tasks and contacts for tests:
        Task t0 = new Task("t0", "2022-10-31", "13:11:11", "None", "01:30:00", "", "None");
        Task t1 = new Task("t1", "2022-11-01", "13:11:11", "School", "00:00:30", "", "None");
        Task t2 = new Task("t2", "2022-12-01", "13:11:11", "Completed", "05:00:00", "", "c4");
        Task t3 = new Task("t3", "2022-11-01", "13:11:05", "Work", "01:00:01", "", "c2");
        Task t4 = new Task("t4", "2022-11-30", "13:05:11", "School", "01:00:00", "", "None");
        Task t5 = new Task("t5", "2022-11-01", "20:00:01", "Work", "01:00:00", "", "c2");
        t2.setFavorite(true);
        t4.setFavorite(true);
        t5.setFavorite(true);
        ArrayList<Task> taskList = new ArrayList<>();
        taskList.add(t0);
        taskList.add(t1);
        taskList.add(t2);
        taskList.add(t3);
        taskList.add(t4);
        taskList.add(t5);
        Contact c0 = new Contact("c0", "c0@gmail.com", "00:00:00", "School");
        Contact c1 = new Contact("c1", "c1@gmail.com", "00:00:00", "None");
        Contact c2 = new Contact("c2", "c2@gmail.com", "00:00:00", "Work");
        Contact c3 = new Contact("c3", "c3@gmail.com", "00:00:00", "School");
        Contact c4 = new Contact("c4", "c4@gmail.com", "00:00:00", "School");
        Contact c5 = new Contact("c5", "c5@gmail.com", "00:00:00", "Personal");
        c5.setFavorite(true);
        c2.setFavorite(true);
        ArrayList<Contact> contactList = new ArrayList<>();
        contactList.add(c0);
        contactList.add(c1);
        contactList.add(c2);
        contactList.add(c3);
        contactList.add(c4);
        contactList.add(c5);


        // TEST CASE 1: test sortTasks() by Name
        try {
            ArrayList<Task> test = db.sortTasks(taskList, "Name");
            // sorted order must be: t0, t1, t2, t3, t4, t5
            String result = "";
            for (Task task: test) {
                result += task.getName();
            }
            if (!result.equals("t0t1t2t3t4t5")) System.out.println("Error: test case 1 failed");
        } catch (Exception e) {
            System.out.println("Error: exception not expected in test case 1, but got:" + e);
        }

        // TEST CASE 2: test sortTasks() by Date & Time
        try {
            ArrayList<Task> test = db.sortTasks(taskList, "Date & Time");
            // sorted order must be: t0,t3,t1,t5,t4,t2
            String result = "";
            for (Task task: test) {
                result += task.getName();
            }
            if (!result.equals("t0t3t1t5t4t2")) System.out.println("Error: test case 2 failed");
        } catch (Exception e) {
            System.out.println("Error: exception not expected in test case 2, but got:" + e);
        }

        // TEST CASE 3: test sortTasks() by Category
        try {
            ArrayList<Task> test = db.sortTasks(taskList, "Category");
            // sorted order must be: t2,t0,t1,t4,t3,t5
            String result = "";
            for (Task task: test) {
                result += task.getName();
            }
            if (!result.equals("t2t0t1t4t3t5")) System.out.println("Error: test case 3 failed");
        } catch (Exception e) {
            System.out.println("Error: exception not expected in test case 3, but got:" + e);
        }

        // TEST CASE 4: test sortTasks() by Contact
        try {
            ArrayList<Task> test = db.sortTasks(taskList, "Contact");
            // sorted order must be: t3,t5,t2,t0,t1,t4
            String result = "";
            for (Task task: test) {
                result += task.getName();
            }
            if (!result.equals("t3t5t2t0t1t4")) System.out.println("Error: test case 4 failed");
        } catch (Exception e) {
            System.out.println("Error: exception not expected in test case 4, but got:" + e);
        }

        // TEST CASE 5: test sortTasks() by Duration
        try {
            ArrayList<Task> test = db.sortTasks(taskList, "Duration");
            // sorted order must be: t1,t5,t4,t3,t0,t2
            String result = "";
            for (Task task: test) {
                result += task.getName();
            }
            if (!result.equals("t1t5t4t3t0t2")) System.out.println("Error: test case 5 failed");
        } catch (Exception e) {
            System.out.println("Error: exception not expected in test case 5, but got:" + e);
        }

        // TEST CASE 6: test sortTasks() by Favorites on Top
        try {
            ArrayList<Task> test = db.sortTasks(taskList, "Favorite");
            // sorted order must be: t5,t4,t2,t0,t3,t1
            String result = "";
            for (Task task: test) {
                result += task.getName();
            }
            if (!result.equals("t5t4t2t0t3t1")) System.out.println("Error: test case 6 failed");
        } catch (Exception e) {
            System.out.println("Error: exception not expected in test case 6, but got:" + e);
        }


        // TEST CASE 7: test sortContacts() by Name
        try {
            ArrayList<Contact> test = db.sortContacts(contactList, "Name");
            // sorted order must be: c0,c1,c2,c3,c4,c5
            String result = "";
            for (Contact contact: test) {
                result += contact.getName();
            }
            if (!result.equals("c0c1c2c3c4c5")) System.out.println("Error: test case 7 failed");
        } catch (Exception e) {
            System.out.println("Error: exception not expected in test case 7, but got:" + e);
        }

        // TEST CASE 8: test sortContacts() by Category
        try {
            ArrayList<Contact> test = db.sortContacts(contactList, "Category");
            // sorted order must be: c1,c5,c0,c3,c4,c2
            String result = "";
            for (Contact contact: test) {
                result += contact.getName();
            }
            if (!result.equals("c1c5c0c3c4c2")) System.out.println("Error: test case 7 failed");
        } catch (Exception e) {
            System.out.println("Error: exception not expected in test case 7, but got:" + e);
        }

        // TEST CASE 9: test sortContacts() by Favorites on top
        try {
            ArrayList<Contact> test = db.sortContacts(contactList, "Favorite");
            // sorted order must be: c2,c5,c0,c1,c3,c4
            String result = "";
            for (Contact contact: test) {
                result += contact.getName();
            }
            if (!result.equals("c2c5c0c1c3c4")) System.out.println("Error: test case 7 failed");
        } catch (Exception e) {
            System.out.println("Error: exception not expected in test case 7, but got:" + e);
        }


        System.out.println("SORTING REGRESSION TESTING COMPLETE");
        System.out.println("RUNNING USER CONTROL REGRESSION TESTS\n");

        // Test Case 10: test userExists() and createNewUser()
        try {
            if (!db.userExists()) {
                System.out.println("user does not exist, creating user");
                db.createNewUser("shrek", "123abc");
            } else {
                System.out.println("user should not exist");
            }
        } catch (Exception e) {
            System.out.println("Error: exception not expected when creating new user");
        }

        // Test Case 11: test authenticating incorrect user using correctLogin()
        System.out.println("authenticating user");
        try {
            if (!db.correctLogin("ss", "1")) {
                System.out.println("Correct: incorrect username/password resulted in failed authentication");
            } else {
                System.out.println("Incorrect: incorrect username/password resulted in PASSED authentication");
            }
        } catch (Exception e) {
            System.out.println("Error" + e);
        }

        // Test Case 12: test authenticating correct user using correctLogin()
        System.out.println("authenticating user");
        try {
            if (!db.correctLogin("shrek", "123abc")) {
                System.out.println("Incorrect: correct username/password resulted in failed authentication");
            } else {
                System.out.println("Correct: correct username/password resulted in PASSED authentication");
            }
        } catch (Exception e) {
            System.out.println("Error" + e);
        }

        System.out.println("\nREGRESSION TESTING COMPLETE");
    }


    /**
     * Helper method used for regression testing.
     * Removes all table entries.
     */
    private static void reset(ManageDB db) {
        // reset TaskTable in db by removing all tasks
        ArrayList<Task> allTasks = db.getAllTasks();
        for (Task task: allTasks) {
            try {
                db.deleteTask(task);
            } catch (RowDoesNotExistException e) {
                System.out.println(e);
            }
        }

        // reset ContactsTable in db by removing all contacts
        ArrayList<Contact> allContacts = db.getAllContacts();
        for (Contact contact: allContacts) {
            try {
                db.deleteContact(contact);
            } catch (RowDoesNotExistException e) {
                System.out.println(e);
            }
        }

        // reset CategoryTable in db by removing all categories
        ArrayList<String> allCategories = db.getAllCategories();
        for (String category: allCategories) {
            try {
                db.deleteCategory(category);
            } catch (RowDoesNotExistException e) {
                System.out.println(e);
            }
        }

        // reset LoginTable
        String sql = "DELETE FROM LoginTable";
        try(Connection conn = DriverManager.getConnection(URL)){
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Failed to delete the user from LoginTable, reason:\n" + e);
        }
    }
}
