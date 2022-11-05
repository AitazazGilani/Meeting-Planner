package com.example.app.database;

import java.io.File;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

/**
 * Serves as the model to provide interactions with the database.
 */
public class ManageDB {
    // Directory to the database. !!! CAN AND MAYBE SHOULD BE CHANGED FOR RELEASE
    private static final String PATH = "res";

    //private static final String URL = "jdbc:sqlite:res/database.db";
    private static final String URL = "jdbc:sqlite:" + PATH + "/database.db";

    // Available task query options
    public enum TaskQuery {
        DATE, TIME, CATEGORY, CONTACT
    }

    // Available contact query options
    public enum ContactQuery {
        CATEGORY, TIMESPENT
    }

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
            pstmt.setString(6,t.getTimeSpent());
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
        // make sure to not add the same category twice
        if (this.getAllCategories().contains(c))
            return;
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
    public void deleteTask(Task t) throws RowDoesNotExistException {
        if(t.getUID() == 0){
            throw new RowDoesNotExistException("The given task does not contain an ID, Please fetch the task from the database");
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
    public void deleteContact(Contact c) throws RowDoesNotExistException {
        if(c.getUID() == 0){
            throw new RowDoesNotExistException("The given contact does not contain an ID, Please fetch the contact from the database");
        }
        int id = c.getUID();

        String sql = "DELETE FROM ContactsTable WHERE UID=?";
        try(Connection conn = DriverManager.getConnection(URL)){
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,id);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Failed to delete the given contact, reason:\n" + e);
        }

        // remove all associations to the deleted contact from TaskTable
        // get the list of all tasks
        ArrayList<Task> tasks = this.getAllTasks();
        // for each task in list, check to see if the task includes this contact in it
        for (Task task : tasks) {
            if (task.getContactName().equals(c.getName())) {
                // if contact name exists in task, update task to set an empty string as the contact name
                task.setContactName("");
                this.updateTask(task);
            }
        }
    }

    /**
     * Delete a category by a given category name, the string must originate from the db
     * Or else it won't contain a unique ID
     * @param c: category name to be deleted, originates from the database
     * @precond Category c exists in db
     * @postcond Category c is removed from CategoryTable in db
     */
    public void deleteCategory(String c) throws RowDoesNotExistException {
        if(!this.getAllCategories().contains(c)){
            throw new RowDoesNotExistException("The given category does not exist in the database. Please fetch the category from the database");
        }

        String sql = "DELETE FROM CategoryTable WHERE Category=?";
        try(Connection conn = DriverManager.getConnection(URL)){
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,c);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Failed to delete the given category, reason:\n" + e);
        }

        // remove all associations to the deleted category from TaskTable
        // get the list of all tasks
        ArrayList<Task> tasks = this.getAllTasks();
        // for each task in list, check to see if the task includes this category in it
        for (Task task : tasks) {
            if (task.getCategory().equals(c)) {
                // if category exists in task, update task to set an empty string as the category
                task.setCategory("");
                this.updateTask(task);
            }
        }
        // remove all associations to the deleted category from ContactsTable
        // get the list of all contacts
        ArrayList<Contact> contacts = this.getAllContacts();
        // for each contact in list, check to see if the contact includes this category in it
        for (Contact contact : contacts) {
            if (contact.getCategory().equals(c)) {
                // if category exists in contact, update contact to set an empty string as the category
                contact.setCategory("");
                this.updateContact(contact);
            }
        }
    }


    //todo: OPTIONAL function to delete a contact
    /**
     * Updates a contact, using uid to find the contact to update in the db
     * @param c Updated contact to query and update in the db
     * @postcond the information in a row of ContactsTable is updated
     */
    public void updateContact(Contact c) throws RowDoesNotExistException{
        if(c.getUID() == 0){
            throw new RowDoesNotExistException("The given task does not contain an ID, Please fetch the contact from the database");
        }
        int id = c.getUID();

        String sql = "UPDATE ContactsTable SET Name = ? , "
                + "Email = ? , "
                + "Category = ? , "
                + "TimeSpent = ? "
                + "WHERE UID = ?";

        try(Connection conn = DriverManager.getConnection(URL)){
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,c.getName());
            pstmt.setString(2,c.getEmail());
            pstmt.setString(3,c.getTimeSpent());
            pstmt.setInt(4,id);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Failed to update the given contact, reason:\n" + e);
        }
    }

    /**
     * Updates a task, using uid to find the task to update in the db
     * @param t Updated task to query and update in the db
     * @postcond the information in a row of TaskTable is updated
     */
    public void updateTask(Task t) throws RowDoesNotExistException {
        if(t.getUID() == 0){
            throw new RowDoesNotExistException("The given task does not contain an ID, Please fetch the contact from the database");
        }
        int id = t.getUID();

        String sql = "UPDATE TaskTable SET TaskName = ? , "
                + "Date = ? , "
                + "Time = ? , "
                + "Category = ? , "
                + "TaskDuration = ? , "
                + "TimeSpent = ? , "
                + "ContactName = ? "
                + "WHERE UID = ?";

        try(Connection conn = DriverManager.getConnection(URL)){
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,t.getName());
            pstmt.setString(2,t.getDate());
            pstmt.setString(3,t.getTime());
            pstmt.setString(4,t.getCategory());
            pstmt.setString(5,t.getDuration());
            pstmt.setString(6,t.getTimeSpent());
            pstmt.setString(7,t.getContactName());
            pstmt.setInt(8,id);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Failed to update the given task, reason:\n" + e);
        }
    }


    //todo: function to query tasks by date, time, category, contacts

    //TODO: Test out the method
    /**
     * Query all the tasks in a given date, and sort by time before returning
     * @param date date string in the format YYYY-MM-DD to query tasks by
     * @return array list of sorted tasks by time in a given date
     */
    public ArrayList<Task> queryTasksByDate(String date) {
        ArrayList<Task> allTasks = this.getAllTasks();
        ArrayList<Task> queriedTasks = new ArrayList<>();
        // for each of the tasks, if the date matches the parameter date, add it to queriedTasks
        for (Task task : allTasks) {
            if (task.getDate().equals(date)) {
                queriedTasks.add(task);
            }
        }

        //todo check if the time sorting is done correctly, time sorted is in increasing order or ?

        // custom comparator used to sort the queriedTasks arraylist by time
        queriedTasks.sort(new Comparator<Task>() {
            final DateFormat df = new SimpleDateFormat("HH:mm:ss");
            @Override
            public int compare(Task t1, Task t2) {
                try {
                    Date d1 = df.parse(t1.getTime());
                    Date d2 = df.parse(t2.getTime());
                    return d1.compareTo(d2);
                } catch (ParseException e) {
                    System.out.println("Parse failed: " + e);
                    return 0;
                }
            }
        });
        return queriedTasks;
    }


    //todo: function to query contacts by time spent with them

    /**
     * Query tasks by date, time, category, or contact
     * @param queryType Task to query
     * @return an arraylist of tasks which match the query
     */
    private ArrayList<Task> queryTasks(TaskQuery queryType, String query) {
        // TODO: Implement queryTasks Method
        ArrayList<Task> allTasks = this.getAllTasks();
        ArrayList<Task> ret = new ArrayList<>();
        switch (queryType) {
            case DATE -> {
                ret = queryTasksByDate(query);
                break;
            }
            case TIME -> { //note this is just getting the tasks by that same exact time
                for (Task task : allTasks) {
                    if (task.getTime().equals(query)) {
                        ret.add(task);
                    }
                }
                break;
            }
            case CATEGORY -> {
                for (Task task : allTasks) {
                    if (task.getCategory().equals(query)) {
                        ret.add(task);
                    }
                }
                break;
            }
            case CONTACT -> {
                for (Task task : allTasks) {
                    if (task.getContactName().equals(query)) {
                        ret.add(task);
                    }
                }
                break;
            }
            default -> {
                break;
            }
        }
        return ret;
    }

    /**
     * Query contacts by category or time spent with them
     * @param queryType Contact to query
     * @return an arraylist of tasks which match the query
     */
    private ArrayList<Contact> queryContacts(ContactQuery queryType, String query){
        // TODO: Implement queryContacts Method
        ArrayList<Contact> allContacts = getAllContacts();
        ArrayList<Contact> ret = new ArrayList<>();
        switch (queryType) {
            case CATEGORY -> {
                for (Contact contact : allContacts) {
                    if (contact.getCategory().equals(query)) {
                        ret.add(contact);
                    }
                }
                break;
            }
            case TIMESPENT -> { //todo: check if the time spent sorting is done correctly
                ret = allContacts;
                ret.sort(new Comparator<Contact>() {
                    final DateFormat df = new SimpleDateFormat("HH:mm:ss");
                    @Override
                    public int compare(Contact c1, Contact c2) {
                        try {
                            Date d1 = df.parse(c1.getTimeSpent());
                            Date d2 = df.parse(c2.getTimeSpent());
                            return d1.compareTo(d2);
                        } catch (ParseException e) {
                            System.out.println("Parse failed: " + e);
                            return 0;
                        }
                    }
                });
                break;
            }
            default -> {
                break;
            }
        }
        return ret;
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
        // TODO: Create more tests
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

        // reset TaskTable in db by removing all tasks
        ArrayList<Task> allTasks = db.getAllTasks();
        for (Task task: allTasks) {
            try {
                db.deleteTask(task);
            } catch (RowDoesNotExistException e) {
                System.out.println(e);
            }
        }
        // tests queryTasksByDate method
        db.createNewTask(new Task("t0", "2022-10-31", "13:11:11", "", "", "", ""));
        db.createNewTask(new Task("t1", "2022-11-01", "13:11:11", "", "", "", ""));
        db.createNewTask(new Task("t2", "2022-11-01", "13:11:11", "", "", "", ""));
        db.createNewTask(new Task("t3", "2022-11-01", "13:11:05", "", "", "", ""));
        db.createNewTask(new Task("t4", "2022-11-01", "13:05:11", "", "", "", ""));
        db.createNewTask(new Task("t5", "2022-11-01", "20:00:01", "", "", "", ""));
        db.createNewTask(new Task("t6", "2022-11-01", "00:00:00", "", "", "", ""));
        db.createNewTask(new Task("t7", "2022-11-02", "00:01:01", "", "", "", ""));

        ArrayList<Task> query = db.queryTasksByDate("2022-11-01");
        System.out.println(query);

        File f = new File(PATH + "/database.db");
        f.delete();

        System.exit(0);
    }
}