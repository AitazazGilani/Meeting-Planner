package com.example.app.database;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;

public class ManageDB {
    private static final String PATH = "res/database.db";
    private static final String URL = "jdbc:sqlite:res/database.db";
    //    Connection dbConnection;
    public ManageDB() {
        File f = new File(PATH);
        if (!f.exists()) {
            System.out.println(".db file does not exist");
            try {
                createNewDB();
                //createNewUser("Shrek", "something");
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    private void createNewUser(String name, String pass) {
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

    private void createNewContact(Contact p){
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
     * Create a new task, Task object must have date in the format YYYY-MM-DD
     * Time format must be in: HH:mm:ss
     * Time must be in 24hr format
     * @param t
     */
    private void createNewTask(Task t){
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
    //todo: function to delete a task

    /**
     * Delete a task by a given task object, the object must originate from the Databse
     * Or else it wont contain a unique ID
     * @param t: Task to be deleted, originates from the database
     */
    private void deleteTask(Task t) throws Exception {
        if(t.getUID() == 0){
            throw new Exception("The given task does not contain an ID, Please fetch the task from the database");
        }



    }

    //todo: OPTIONAL function to delete a contact
    private void updateContact(Contact c){

    }

    //todo: function to update tasks, must perserve table ordering
    private void updateTask(Task t){

    }

    //todo: function to query tasks by date, time, category, contacts
    private void queryTask(Task t){

    }

    //todo: function to query contacts by time spent with them
    private void queryContact(Contact c){

    }

    //todo: function to get all tasks
    private ArrayList<Task> getAllTasks(){
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

    //todo: function to get all contacts
    private ArrayList<Contact> getAllContacts(){
        return new ArrayList<Contact>();
    }

    private void createNewDB() {
        System.out.println("Current Working Directory: " + System.getProperty("user.dir"));
        // create appropriate tables in current direcory?
        File dbDir = new File("./res");
        if (!dbDir.exists()){
            System.out.println("Creating new directory ./res");
            dbDir.mkdirs();
        }

        try (Connection conn = DriverManager.getConnection(URL)) {
            System.out.println("Creating a new database in ./res");
            if (conn != null) {
                System.out.println("A new database has been created.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        String createLoginTable = "CREATE TABLE LoginTable(\n" +
                "    Username varchar(255),\n" +
                "    Password varchar(255)\n" +
                ");\n";

        String createContactsTable = "CREATE TABLE ContactsTable(\n" +
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


        //for creating a user/login table, using sql string
        try (Connection conn = DriverManager.getConnection(URL)) {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(createLoginTable);
            stmt.executeUpdate(createContactsTable);
            stmt.executeUpdate(createTaskTable);
        } catch (Exception e) {
            System.out.println(e);
        }



    }

    //testing
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


        System.exit(0);
    }
}