package com.example.app.database;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;

/**
 * Serves as the model to provide interactions with the database.
 */
public class ManageDB {
    // Directory to the database. !!! CAN AND MAYBE SHOULD BE CHANGED FOR RELEASE
    private static final String PATH = "res";

    //private static final String URL = "jdbc:sqlite:res/database.db";
    private static final String URL = "jdbc:sqlite:" + PATH + "/database.db";

    /**
     * Constructor which checks if database file exists, if not, calls helper method to create a new database
     */
    public ManageDB() {
        File f = new File(PATH + "/database.db");
        if (!f.exists()) {
            System.out.println(".db file does not exist");
            try {
                createNewDB();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    /**
     * Create a new User in the db
     * @param name the username
     * @param pass the password
     * @precond db does not exist (first time opening the application)
     * @postcond only one user exists in the db. NO MORE USERS SHOULD BE CREATED
     */
    public void createNewUser(String name, String pass) {
        String sql1 = "INSERT INTO LoginTable (UserName, Password) VALUES (?,?)";
        //for inserting a user, using sql1 string
        try(Connection conn = DriverManager.getConnection(URL)){
            PreparedStatement pstmt = conn.prepareStatement(sql1); //Prepared statements are used for parametized statements
            pstmt.setString(1,name);
            pstmt.setString(2,pass);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    /**
     * Create a new Contact in the db
     * @param p Contact to add to the db
     * @postcond new contact row is created in ContactsTable in the db
     */
    public void createNewContact(Contact p){
        String sql1 = "INSERT INTO ContactsTable (Name, Email, Category, TimeSpent) VALUES (?,?,?,?)";
        //for inserting a contact, using sql1 string
        try(Connection conn = DriverManager.getConnection(URL)){
            PreparedStatement pstmt = conn.prepareStatement(sql1); //Prepared statements are used for parametized statements
            pstmt.setString(1,p.getName());
            pstmt.setString(2,p.getEmail());
            pstmt.setString(3,p.getCategory());
            pstmt.setString(4,p.getTimeSpent());
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Create a new Task in the db, Task object must have date in the format YYYY-MM-DD
     * Time format must be in: HH:mm:ss
     * Time must be in 24hr format
     * @param t Task to add to the db
     * @postcond new task row is created in TaskTable in the db
     */
    public void createNewTask(Task t){
        String sql1 = "INSERT INTO TaskTable(TaskName, Date, Time, Category, TaskDuration, TimeSpent, ContactName) VALUES (?,?,?,?,?,?,?)";
        //for inserting a contact, using sql1 string
        try(Connection conn = DriverManager.getConnection(URL)){
            PreparedStatement pstmt = conn.prepareStatement(sql1); //Prepared statements are used for parametized statements
            pstmt.setString(1,t.getName());
            pstmt.setString(2,t.getDate());
            pstmt.setString(3,t.getTime());
            pstmt.setString(4,t.getCategory());
            pstmt.setString(5,t.getDuration());
            pstmt.setString(6,t.getTimespent());
            pstmt.setString(7,t.getContactName());

            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Failed to insert the given task, Reason:\n" + e);
        }
    }

    /**
     * Create a new category in the CategoryTable
     * @param c category name to add
     * @postcond new category row is created in CategoryTable in the db
     */
    public void createNewCategory(String c) {
        String sql1 = "INSERT INTO ContactsTable (Category) VALUES (?)";
        try(Connection conn = DriverManager.getConnection(URL)){
            PreparedStatement pstmt = conn.prepareStatement(sql1);
            pstmt.setString(1,c);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    /**
     * Delete a task by a given task object, the object must originate from the db
     * Or else it won't contain a unique ID
     * @param t: Task to be deleted, originates from the database
     * @precond Task t exists in db
     * @postcond Task t is removed from TaskTable in db
     */
    public void deleteTask(Task t) throws Exception {
        if(t.getUID() == 0){
            throw new Exception("The given task does not contain an ID, Please fetch the task from the database");
        }
        int id = t.getUID();

        String sql = "DELETE FROM TaskTable WHERE UID=?";
        try(Connection conn = DriverManager.getConnection(URL)){
            PreparedStatement pstmt = conn.prepareStatement(sql); //Prepared statements are used for parametized statements
            pstmt.setInt(1,id);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Failed to delete the given task, reason:\n" + e);
        }
    }

    /**
     * Delete a contact by a given contact object, the object must originate from the db
     * Or else it won't contain a unique ID
     * @param c: Contact to be deleted, originates from the database
     * @precond Contact c exists in db
     * @postcond Contact c is removed from ContactsTable in db
     */
    public void deleteContact(Contact c) {
        //todo: method to delete a contact
    }

    /**
     * Delete a category by a given category name, the string must originate from the db
     * Or else it won't contain a unique ID
     * @param c: category name to be deleted, originates from the database
     * @precond Category c exists in db
     * @postcond Category c is removed from CategoryTable in db
     */
    public void deleteCategory(String c) {
        //todo: method to delete a category
    }

    /**
     * Updates a contact, using uid to find the contact to update in the db
     * @param c Updated contact to query and update in the db
     * @postcond the information in a row of ContactsTable is updated
     */
    public void updateContact(Contact c){
        //todo: method to update/edit a contact
    }

    /**
     * Updates a task, using uid to find the task to update in the db
     * @param t Updated task to query and update in the db
     * @postcond the information in a row of TaskTable is updated
     */
    public void updateTask(Task t){
        //todo: function to update tasks, must preserve table ordering
    }

    /**
     * Query tasks by date, time, category, contacts
     * @param t Task to query
     * @postcond
     */
    private void queryTask(Task t){
        //todo: function to query tasks by date, time, category, contacts
    }

    /**
     * Queries contacts by time spent with them
     * @param c Contact to query
     * @postcond
     */
    private void queryContact(Contact c){
        //todo: function to query contacts by time spent with them
    }

    /**
     * Get all the tasks present in the database
     * @return An ArrayList of Task objects
     */
    public ArrayList<Task> getAllTasks(){
        ArrayList<Task> tasks = new ArrayList<Task>();
        String sql = "SELECT * FROM TaskTable";
        try(Connection conn = DriverManager.getConnection(URL)){
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            while (rs.next()) {
                Task t = new Task(
                        rs.getString("TaskName"),
                        rs.getString("Date"),
                        rs.getString("Time"),
                        rs.getString("Category"),
                        rs.getString("TaskDuration"),
                        rs.getString("TimeSpent"),
                        rs.getString("ContactName")
                );
                t.setUID(rs.getInt("UID"));
                tasks.add(t);
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return tasks;
    }

    /**NOT TESTED
     * Get all present contacts from the database
     * @return an ArrayList of Contact objects
     */
    public ArrayList<Contact> getAllContacts(){
        ArrayList<Contact> contacts = new ArrayList<Contact>();
        String sql = "SELECT * FROM ContactsTable";
        try(Connection conn = DriverManager.getConnection(URL)){
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            while (rs.next()) {
                Contact c = new Contact(
                        rs.getString("Name"),
                        rs.getString("Email"),
                        rs.getString("Category"),
                        rs.getString("TimeSpent")
                );

                contacts.add(c);
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return contacts;
    }

    /**NOT TESTED
     * Get all present categories from the database
     * @return an ArrayList of category strings
     */
    public ArrayList<String> getAllCategories() {
        ArrayList<String> categories = new ArrayList<>();
        String sql = "SELECT * FROM ContactsTable";
        try(Connection conn = DriverManager.getConnection(URL)){
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            while (rs.next()) {
                String c = rs.getString("Category");
                categories.add(c);
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return categories;
    }

    /**
     * Create a new SQLITE database file and creates all the necessary tables for the application
     * @postcond new db file with the necessary tables
     */
    private void createNewDB() {
        System.out.println("Current Working Directory: " + System.getProperty("user.dir"));

        // create the directory if it doesn't exist
        File dbDir = new File("./" + PATH);
        if (!dbDir.exists()){
            System.out.println("Creating new directory ./" + PATH);
            dbDir.mkdirs();
        }

        // create the database file if it doesn't exist
        try (Connection conn = DriverManager.getConnection(URL)) {
            System.out.println("Creating a new database in ./" + PATH);
            if (conn != null) {
                System.out.println("A new database file has been created.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        // create all necessary tables for the application
        String createLoginTable = "CREATE TABLE LoginTable(\n" +
                "    Username varchar(255),\n" +
                "    Password varchar(255)\n" +
                ");\n";

        String createContactsTable = "CREATE TABLE ContactsTable(\n" +
                "    UID integer primary key autoincrement,\n"+
                "    Name varchar(255),\n" +
                "    Email varchar(255),\n" +
                "    Category varchar(255),\n" +
                "    TimeSpent varchar(255)\n" +
                ");";

        String createTaskTable = "CREATE TABLE TaskTable(\n" +
                "    UID integer primary key autoincrement,\n"+
                "    TaskName varchar(255),\n" +
                "    Date varchar(255),\n" +
                "    Time varchar(255),\n" +
                "    Category varchar(255),\n" +
                "    TaskDuration varchar(255),\n" +
                "    TimeSpent varchar(255), \n" +
                "    ContactName varchar(255)\n" +
                ");\n";

        String createCategoryTable = "CREATE TABLE CategoryTable(\n" +
                "    UID integer primary key autoincrement,\n"+
                "    Category varchar(255)\n" +
                ");\n";

        // Execute the strings above as SQL statements to create the necessary tables in the db
        try (Connection conn = DriverManager.getConnection(URL)) {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(createLoginTable);
            stmt.executeUpdate(createContactsTable);
            stmt.executeUpdate(createTaskTable);
            stmt.executeUpdate(createCategoryTable);
            System.out.println("tables created in db");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * main method used for testing
     */
    public static void main(String[] args){
        System.out.println(System.getProperty("user.dir"));
        ManageDB db = new ManageDB();

        //regression tests, must be done on a new db

        //test case 1: add several tasks
        Task a = new Task("Study for 370","2022-10-27","12:00:00","Uni","02:00:00","","Self");
        Task b = new Task("Study for 381","2022-10-27","12:00:00","Uni","02:00:00","","Self");
        Task c = new Task("Study for 360","2022-10-27","12:00:00","Uni","02:00:00","","Self");
        try{
            db.createNewTask(a);
            db.createNewTask(b);
            db.createNewTask(c);
        }
        catch(Exception e){
            System.out.println("Failed to insert tasks to table, reason:\n"+e);
        }

        //Test case 2: Check if the tasks exist and test getAllTasks()
        try{
            ArrayList<Task> arr = db.getAllTasks();
            for(Task t: arr){
                if(!t.getDate().equals("2022-10-27")) throw new Exception("Tasks were not inserted or queryed correctly");
            }
        }
        catch (Exception e){
            System.out.println("Failed to query all tasks, reason:\n"+e);
        }

        //Test case 3: delete a task
        try{
            ArrayList<Task> arr = db.getAllTasks();
            for(Task t: arr){
                if(!t.getDate().equals("2022-10-27")) throw new Exception("Tasks were not inserted or queried correctly");
            }
            //delete the first task
            db.deleteTask(arr.get(0));
        }
        catch (Exception e){
            System.out.println("Failed to delete the given task, reason:\n"+e);
        }

        System.exit(0);
    }
}