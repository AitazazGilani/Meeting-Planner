package com.example.app.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;


/**
 * Class to do regression testing on ManageDB
 */
public class ManageDBTests {
    private static final String PATH = "res";
    private static final String URL = "jdbc:sqlite:" + PATH + "/database.db";

    /**
     * main method used for testing
     * WARNING: RESETS WHOLE DATABASE, REMOVING ALL DATA
     */
    public static void main(String[] args){
        // removes database if it already exists
        File f = new File("res/database.db");
        f.delete();

        // create new db
        ManageDB db = new ManageDB();

        // remove all data if any exists (fallback from f.delete() incase database file is active and cannot be deleted)
        reset(db);

        // REGRESSION TESTS
        System.out.println("\nRUNNING REGRESSION TESTS");

        //test case 0: create and connect to the database
        try{
            db = new ManageDB();
        }
        catch(Exception e){
            System.out.println("ERROR: could not create and connect a new DB\n"+e);
        }

        // test case 1: create a new user (tests createNewUser())
        try {
            db.createNewUser("username", "password");
        } catch (UserAlreadyExistsException e) {
            System.out.println("ERROR: expected no users to exist in db after first time run");
        } catch (Exception e) {
            System.out.println("ERROR: no exception expected");
        }

        // test case 2: create a new user when a user already exists (tests createNewUser())
        try {
            db.createNewUser("username2", "password2");
        } catch (UserAlreadyExistsException e) {
            // Exception expected
        } catch (Exception e) {
            System.out.println("ERROR: UserAlreadyExistsException expected but got another exception");
        }

        // test case 3: add several contacts (tests createNewContact())
        Contact c0 = new Contact("John Doe", "john.doe@gmail.com", "0", "Work");
        Contact c1 = new Contact("Jane Doe", "jane.doe@usask.ca", "0", "Uni");
        try{
            db.createNewContact(c0);
            db.createNewContact(c1);
        }
        catch(Exception e){
            System.out.println("ERROR: Failed to insert contacts into table, reason: "+e);
        }
        if (db.getAllContacts().size() != 2) {
            System.out.println("ERROR: expected 2 contacts to be created in ContactsTable, got: " + db.getAllContacts().size());
        }

        // test case 4: add several tasks (tests createNewTask())
        Task t0 = new Task("Study for 370","2022-10-27","12:00:00","Uni","02:00:00","","");
        Task t1 = new Task("Study for 381","2022-10-27","12:00:00","Uni","02:00:00","","");
        Task t2 = new Task("Study for 360","2022-10-27","12:00:00","Uni","02:00:00","","Jane Doe");
        try{
            db.createNewTask(t0);
            db.createNewTask(t1);
            db.createNewTask(t2);
        }
        catch(Exception e){
            System.out.println("ERROR: Failed to insert tasks into table, reason: "+e);
        }
        if (db.getAllTasks().size() != 3) {
            System.out.println("ERROR: expected 3 tasks to be created in TaskTable, got: " + db.getAllTasks().size());
        }

        // test case 5: add several categories (including a duplicate which should not be added) (tests createNewCategory() and getAllCategories())
        ArrayList<String> categories = new ArrayList<>();
        categories.add("Work");
        categories.add("Uni");
        categories.add("Personal");
        categories.add("Uni");
        try {
            for (String c : categories) {
                db.createNewCategory(c);
            }
        } catch(Exception e){
            System.out.println("ERROR: Failed to insert categories into table, reason: "+e);
        }
        if (db.getAllCategories().size() != 3) {
            System.out.println("ERROR: expected 3 categories to be created in CategoryTable, got: " + db.getAllCategories().size());
        }
        if (!(categories.get(0).equals(db.getAllCategories().get(0)) &&
                categories.get(1).equals(db.getAllCategories().get(1)) &&
                categories.get(2).equals(db.getAllCategories().get(2)))) {
            System.out.println("ERROR: expected 3 categories to be Work, Uni, and Personal. Got: " +
                    db.getAllCategories().get(0) + ", " +
                    db.getAllCategories().get(1) + ", and " +
                    db.getAllCategories().get(2));
        }

        // test case 6: Check if the tasks exist (tests getAllTasks())
        try {
            ArrayList<Task> arr = db.getAllTasks();
            for(Task t: arr){
                if(!t.getDate().equals("2022-10-27")) throw new Exception("Tasks were not inserted or queried correctly");
            }
        } catch (Exception e){
            System.out.println("ERROR: Failed to query all tasks, reason: "+e);
        }

        // test case 7: Check if the contacts exist (tests getAllContacts())
        try {
            ArrayList<Contact> arr = db.getAllContacts();
            for(Contact c: arr){
                if(!(c.getName().equals("John Doe") || c.getName().equals("Jane Doe")))
                    throw new Exception("Contacts were not inserted or queried correctly");
            }
        } catch (Exception e){
            System.out.println("ERROR: Failed to query all contacts, reason: "+e);
        }

        //Test case 8: delete a task (tests deleteTask())
        try {
            ArrayList<Task> arr = db.getAllTasks();
            int numTasks = arr.size();
            for(Task t: arr){
                if(!t.getDate().equals("2022-10-27")) throw new Exception("Tasks were not inserted or queried correctly.");
            }
            // delete the first task
            db.deleteTask(arr.get(0));
            if (!(db.getAllTasks().size() == (numTasks - 1)))
                throw new Exception("Expected size of tasksTable to be only one smaller after deleting one task.");
        }
        catch (Exception e){
            System.out.println("ERROR: Failed to delete the given task, reason: "+e);
        }

        //test case 8.5: delete a task with no UID
        try{
            Task t = new Task("test","dd-mm-yyyy","00:00:00","","","","");
            db.deleteTask(t);
        } catch (RowDoesNotExistException e) {
            //caught exception, passed
        }
        catch (Exception e){
            System.out.println("ERROR: failed to throw RowDoesNotExist exception for a task with no UID, reason:\n"+e);
        }

        //Test case 9: delete a Category (tests deleteCategory())
        try {
            ArrayList<String> arr = db.getAllCategories();
            int numCategories = arr.size();

            // delete the "Uni" category
            db.deleteCategory("Uni");
            if (!(db.getAllCategories().size() == (numCategories - 1)))
                throw new Exception("Expected size of CategoryTable to be only one smaller after deleting one category.");

            // test to see if category association has be removed from tasks and contacts:
            ArrayList<Task> tasks = db.getAllTasks();
            for (Task task : tasks) {
                if (task.getCategory().equals("Uni"))
                    throw new Exception("Category association has not been removed from a task after deleting \"Uni\" category.");
            }
            ArrayList<Contact> contacts = db.getAllContacts();
            for (Contact contact : contacts) {
                if (contact.getCategory().equals("Uni"))
                    throw new Exception("Category association has not been removed from a contact after deleting \"Uni\" category.");
            }
        }
        catch (Exception e){
            System.out.println("ERROR: Failed to delete the given category, reason: "+e);
        }

        //Test case 10: delete a Contact (tests deleteContact())
        try {
            ArrayList<Contact> arr = db.getAllContacts();
            int numContacts = arr.size();

            // delete the "Jane Doe" contact
            for (Contact contact : arr) {
                if (contact.getName().equals("Jane Doe"))
                    db.deleteContact(contact);
            }
            if (!(db.getAllContacts().size() == (numContacts - 1)))
                throw new Exception("Expected size of ContactsTable to be only one smaller after deleting one contact.");

            // test to see if contact association has be removed from tasks:
            ArrayList<Task> tasks = db.getAllTasks();
            for (Task task : tasks) {
                if (task.getContactName().equals("Jane Doe"))
                    throw new Exception("Contact association has not been removed from a task after deleting \"Jane Doe\" contact.");
            }
        }
        catch (Exception e){
            System.out.println("ERROR: Failed to delete the given contact, reason: "+e);
        }

        //test case 10.5: delete a Contact with no UID
        try{
            Contact c = new Contact("john","juandoe@email.com","","");
            db.deleteContact(c);
        } catch (RowDoesNotExistException e) {
            //caught exception, passed
        }
        catch (Exception e){
            System.out.println("ERROR: failed to throw RowDoesNotExist exception for a contact with no UID, reason:\n"+e);
        }

        // test case 11: update a contact (tests updateContact())
        ArrayList<Contact> contacts = db.getAllContacts();
        Contact c = contacts.get(0);
        c.setEmail("JohnD@gmail.com");
        c.setTimeSpent("01:20:00");
        try {
            db.updateContact(c);
        } catch (Exception e) {
            System.out.println("ERROR: failed to update contact, reason: "+e);
        }
        contacts = db.getAllContacts();
        c = contacts.get(0);
        if (!c.getTimeSpent().equals("01:20:00") && !c.getEmail().equals("JohnD@gmail.com")) {
            System.out.println("ERROR: expected updated contact timeSpent to be \"01:20:00\" but got \"" +
                    c.getTimeSpent() +
                    "\" and contact Email to be \"JohnD@gmail.com\" but got: \"" +
                    c.getEmail() +
                    "\"");
        }

        // test case 12: update a task (tests updateTask())
        ArrayList<Task> tasks = db.getAllTasks();
        Task t = tasks.get(1);
        t.setName("Study for 281");
        try {
            db.updateTask(t);
        } catch (Exception e) {
            System.out.println("ERROR: failed to update task, reason: "+e);
        }
        tasks = db.getAllTasks();
        t = tasks.get(1);
        if (!t.getName().equals("Study for 281")) {
            System.out.println("ERROR: expected updated task name to be \"Study for 281\" but got \"" + t.getName() + "\"");
        }

        // test case 13: queries tasks by date and confirms query is correctly sorted (tests queryTasksByDate())
        // but first clear tasks from taskTable
        ArrayList<Task> allTasks = db.getAllTasks();
        for (Task task: allTasks) {
            try {
                db.deleteTask(task);
            } catch (RowDoesNotExistException e) {
                System.out.println(e);
            }
        }
        // query empty taskTable
        ArrayList<Task> query = db.queryTasksByDate("2022-11-01");
        if (!query.isEmpty())
            System.out.println("ERROR: Querying an empty task table should return an empty query, but got query with the size: " + query.size());
        // create new tasks
        db.createNewTask(new Task("t0", "2022-10-31", "13:11:11", "", "", "", ""));
        db.createNewTask(new Task("t1", "2022-11-01", "13:11:11", "", "", "", ""));
        db.createNewTask(new Task("t2", "2022-11-01", "13:11:11", "", "", "", ""));
        db.createNewTask(new Task("t3", "2022-11-01", "13:11:05", "", "", "", ""));
        db.createNewTask(new Task("t4", "2022-11-30", "13:05:11", "", "", "", ""));
        db.createNewTask(new Task("t5", "2022-11-01", "20:00:01", "", "", "", ""));
        db.createNewTask(new Task("t6", "2022-11-01", "00:00:00", "", "", "", ""));
        db.createNewTask(new Task("t7", "2022-11-02", "00:01:01", "", "", "", ""));
        // query tasks for the date of 2022-11-01
        query = db.queryTasksByDate("2022-11-01");
        if (query.size() != 5)
            System.out.println("ERROR: Expected query by date to return 5 tasks with the date 2022-11-01, but got: " + query.size());
        if (!(query.get(0).getName().equals("t6") &&
                query.get(1).getName().equals("t3") &&
                query.get(2).getName().equals("t1") &&
                query.get(3).getName().equals("t2") &&
                query.get(4).getName().equals("t5"))) {
            System.out.println("ERROR: Query is not correctly sorted by time. Expected time sorted order of tasks to be [t6, t3, t1, t2, t5], but got :" + query);
        }

        // test case 14: queries tasks again by date (tests queryTasks())
        ArrayList<Task> newQuery = db.queryTasks(ManageDB.TaskQuery.DATE, "2022-11-01");
        if (!newQuery.toString().equals(query.toString()))
            System.out.println("ERROR: queryTasks() method did not yield the same results as queryTasksByDate().");





        // TC30: Query tasks by a date which doesn't exist in any tasks
        reset(db); // remove all table entries
        // create some mock tasks
        t0 = new Task("t0", "2022-10-31", "13:11:11", "", "", "", "");
        t1 = new Task("t1", "2022-11-01", "13:11:11", "", "", "", "");
        t2 = new Task("t2", "2022-11-01", "13:11:11", "", "", "", "");
        Task t3 = new Task("t3", "2022-11-01", "13:11:05", "", "", "", "");
        Task t4 = new Task("t4", "2022-11-30", "13:05:11", "", "", "", "");
        Task t5 = new Task("t5", "2022-11-01", "20:00:01", "", "", "", "");
        Task t6 = new Task("t6", "2022-11-01", "00:00:00", "", "", "", "");
        Task t7 = new Task("t7", "2022-11-02", "00:01:01", "", "", "", "");
        Task t8 = new Task("t8", "2022-10-31", "13:11:11", "", "", "", "");
        Task t9 = new Task("t9", "2022-10-31", "13:11:11", "", "", "", "");
        db.createNewTask(t0);
        db.createNewTask(t1);
        db.createNewTask(t2);
        db.createNewTask(t3);
        db.createNewTask(t4);
        db.createNewTask(t5);
        db.createNewTask(t6);
        db.createNewTask(t7);
        db.createNewTask(t8);
        db.createNewTask(t9);
        // query a date which doesn't exist in any tasks
        ArrayList<Task> queryTC30 = db.queryTasksByDate("2022-01-01");
        // make sure query arraylist is empty
        if (!queryTC30.isEmpty())
            System.out.println("ERROR: Querying an empty task table should return an empty query, but got query with the size: " + queryTC30.size());

        // TC31: Query tasks by a date which exists in only one task
        ArrayList<Task> queryTC31 = db.queryTasksByDate("2022-11-02");
        // make sure query arraylist includes only one task (t7)
        if (!(queryTC31.size() == 1 && queryTC31.get(0).equals(t7)))
            System.out.println("ERROR: Expected arraylist containing t7 to be returned when Querying tasks by a date which exists in only one task, but got : " + queryTC31);

        // TC32: Query tasks by a date which exists in multiple tasks which have no repeating times
        ArrayList<Task> queryTC32 = db.queryTasksByDate("2022-10-31");
        // make sure query arraylist includes only three tasks (t1, t8, and t9)
        if (!(queryTC32.size() == 3 && queryTC32.get(0).equals(t0) && queryTC32.get(1).equals(t8) && queryTC32.get(2).equals(t9)))
            System.out.println("ERROR: Expected arraylist containing t0, t8, and t9 to be returned when Querying tasks by a date which exists in multiple tasks which have no repeating times, but got : " + queryTC32);

        // TC33: Query tasks by a date which exists in multiple tasks which have repeating times
        ArrayList<Task> queryTC33 = db.queryTasksByDate("2022-11-01");
        // make sure query arraylist includes only five tasks (t6, t3, t1, t2, and t5)
        if (!(queryTC33.size() == 5 && queryTC33.get(0).equals(t6) && queryTC33.get(1).equals(t3) && queryTC33.get(2).equals(t1) && queryTC33.get(3).equals(t2) && queryTC33.get(4).equals(t5)))
            System.out.println("ERROR: Expected arraylist containing t6, t3, t1, t2, and t5 to be returned when Querying tasks by a date which exists in multiple tasks which have repeating times, but got : " + queryTC33);

        // TC34: Query for all tasks, contacts, and categories when none of each exists in their tables
        reset(db); // remove all table entries
        ArrayList<Task> queryTC34Tasks = db.getAllTasks();
        ArrayList<Contact> queryTC34Contacts = db.getAllContacts();
        ArrayList<String> queryTC34Categories = db.getAllCategories();
        if (!(queryTC34Tasks.isEmpty() && queryTC34Contacts.isEmpty() && queryTC34Categories.isEmpty()))
            System.out.println("ERROR: Querying empty tables of tasks, contacts or categories did not return empty arraylists of tasks, contacts, or categories.");

        // TC35: Query for all tasks, contacts, and categories when some of each exists in their tables
        // add some data to each table:
        db.createNewCategory("Personal");
        db.createNewCategory("School");
        db.createNewCategory("Work");
        db.createNewContact(new Contact("John Doe", "john.doe@gmail.com", "00:00:00", "Work"));
        db.createNewContact(new Contact("Jane Doe", "jane.doe@usask.ca", "00:00:00", "School"));
        db.createNewTask(new Task("Study for 370 Midterm", "2022-11-03", "14:30:00", "School", "01:00:00", "00:00:00", ""));
        db.createNewTask(new Task("Meeting with John", "2022-11-20", "13:00:00", "Work", "00:30:00", "", "John Doe"));
        ArrayList<Task> queryTC35Tasks = db.getAllTasks();
        ArrayList<Contact> queryTC35Contacts = db.getAllContacts();
        ArrayList<String> queryTC35Categories = db.getAllCategories();
        if (!(queryTC35Tasks.size() == 2 && queryTC35Tasks.get(0).getName().equals("Study for 370 Midterm") && queryTC35Tasks.get(1).getName().equals("Meeting with John")))
            System.out.println("ERROR: Querying for all tasks did not return expected tasks.");
        if (!(queryTC35Contacts.size() == 2 && queryTC35Contacts.get(0).getName().equals("John Doe") && queryTC35Contacts.get(1).getName().equals("Jane Doe")))
            System.out.println("ERROR: Querying for all contacts did not return expected contacts.");
        if (!(queryTC35Categories.size() == 3 && queryTC35Categories.get(0).equals("Personal") && queryTC35Categories.get(1).equals("School") && queryTC35Categories.get(2).equals("Work")))
            System.out.println("ERROR: Querying for all categories did not return expected categories.");



        System.out.println("REGRESSION TESTING COMPLETE");
        System.exit(0);
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
