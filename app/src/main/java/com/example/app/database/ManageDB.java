package com.example.app.database;

import java.io.File;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;

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
                createNewCategory("Completed");
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
    public void createNewUser(String name, String pass) throws UserAlreadyExistsException {
        // initial check to make sure a user does not exist
        int count = 0;
        try(Connection conn = DriverManager.getConnection(URL)){
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery("SELECT COUNT(*) AS rowcount FROM LoginTable");
            r.next();
            count = r.getInt("rowcount");
            r.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        if (count == 1)
            throw new UserAlreadyExistsException("A user already exists");
        else if (count > 1)
            throw new UserAlreadyExistsException("Multiple users exist, there should only be one user");

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
     * Checks whether there is a user in LoginTable
     *
     * @return true if a user exists, false if a user does not exist in db
     * @throws UserAlreadyExistsException if more than one user exists (should not happen)
     */
    public static Boolean userExists() throws UserAlreadyExistsException {
        int count = 0;
        try(Connection conn = DriverManager.getConnection(URL)){
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery("SELECT COUNT(*) AS rowcount FROM LoginTable");
            r.next();
            count = r.getInt("rowcount");
            r.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        if (count == 0) {
            return false;
        } else if (count == 1)
            return true;
        else {
            throw new UserAlreadyExistsException("Multiple users exist, there should only be one user");
        }
    }

    /**
     * Authenticates login attempt with user info in db
     *
     * @return true if login attempt was correct, false if username/password entered was incorrect
     * @throws Exception if attempted to login when no users exist (should not happen)
     */
    public Boolean correctLogin(String username, String password) throws Exception {
        if (!userExists())
            throw new Exception("Attempted to login when no users exist.");

        String[] userInfo = getUser();
        return userInfo[0].equals(username) && userInfo[1].equals(password);
    }

    /**
     * returns username and password stored in the db
     * @return string array of username then password
     */
    private String[] getUser() {
        String[] ret = new String[2];
        String sql = "SELECT * FROM LoginTable WHERE rowid = 1";
        try(Connection conn = DriverManager.getConnection(URL)){
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            ret[0] = rs.getString("Username");
            ret[1] = rs.getString("Password");
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return ret;
    }

    //todo: test out favorite, and if timers shows up correctly
    /**
     * Create a new Contact in the db
     * @param p Contact to add to the db
     * @precond category name string exists in CategoryTable or is blank for no category
     * @postcond new contact row is created in ContactsTable in the db
     */
    public void createNewContact(Contact p){
        String sql1 = "INSERT INTO ContactsTable (Name, Email, Category, TimeSpent, Favorite, Timers) VALUES (?,?,?,?,?,?)";
        //for inserting a contact, using sql1 string
        try(Connection conn = DriverManager.getConnection(URL)){
            PreparedStatement pstmt = conn.prepareStatement(sql1); //Prepared statements are used for parametized statements
            pstmt.setString(1,p.getName());
            pstmt.setString(2,p.getEmail());
            pstmt.setString(3,p.getCategory());
            pstmt.setString(4,p.getTimeSpent());
            pstmt.setBoolean(5,p.isFavorite());
            pstmt.setString(6,p.timersToString());
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
     * @precond category name string exists in CategoryTable or is blank for no category
     * @precond contact name string exists in ContactsTable or is blank for no contact
     * @postcond new task row is created in TaskTable in the db
     */
    public void createNewTask(Task t){
        String sql1 = "INSERT INTO TaskTable(TaskName, Date, Time, Category, TaskDuration, TimeSpent, ContactName, Favorite) VALUES (?,?,?,?,?,?,?,?)";
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
            pstmt.setBoolean(8,t.isFavorite());

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
        String sql1 = "INSERT INTO CategoryTable (Category) VALUES (?)";
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

//todo: test out if favorite and timers are setup correctly
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
                + "TimeSpent = ? , "
                + "Favorite = ?, "
                + "Timers = ? "
                + "WHERE UID = ?";

        try(Connection conn = DriverManager.getConnection(URL)){
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,c.getName());
            pstmt.setString(2,c.getEmail());
            pstmt.setString(3,c.getCategory());
            pstmt.setString(4,c.getTimeSpent());
            pstmt.setBoolean(5,c.isFavorite());
            pstmt.setString(6,c.timersToString());
            pstmt.setInt(7,id);
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
                + "ContactName = ?, "
                + "Favorite = ?"
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
            pstmt.setBoolean(8,t.isFavorite());
            pstmt.setInt(9,id);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Failed to update the given task, reason:\n" + e);
        }
    }

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
    public ArrayList<Task> queryTasks(TaskQuery queryType, String query) {
        // TODO: task name and category
        // TODO: CLARIFY WHICH QUERIES WE ACTUALLY NEED
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
                // TODO: Sort Time query?
                break;
            }
            case CATEGORY -> {
                for (Task task : allTasks) {
                    if (task.getCategory().equals(query)) {
                        ret.add(task);
                    }
                }
                // TODO: Sort Category query?
                break;
            }
            case CONTACT -> {
                for (Task task : allTasks) {
                    if (task.getContactName().equals(query)) {
                        ret.add(task);
                    }
                }
                // TODO: Sort Contact query?
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
    public ArrayList<Contact> queryContacts(ContactQuery queryType, String query){
        // TODO: CLARIFY WHICH QUERIES WE ACTUALLY NEED
        ArrayList<Contact> allContacts = getAllContacts();
        ArrayList<Contact> ret = new ArrayList<>();
        switch (queryType) {
            case CATEGORY -> {
                for (Contact contact : allContacts) {
                    if (contact.getCategory().equals(query)) {
                        ret.add(contact);
                    }
                }
                // TODO: Sort Category query?
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
                // TODO: Sort TimeSpent query?
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
                t.setFavorite(rs.getBoolean("Favorite"));
                tasks.add(t);
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return tasks;
    }

    /**
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
                        rs.getString("TimeSpent"),
                        rs.getString("Category")
                );
                c.setUID(rs.getInt("UID"));
                c.setFavorite(rs.getBoolean("Favorite"));

                //todo:test out whether if timers are added correctly, check for any unique cases?

                //pharse the timers into the array list
                String t = rs.getString("Timers");
                if(!t.equals(null) || !t.equals("")){
                 String[] items = t.split(",");
                 ArrayList<String> lst = new ArrayList<>();
                 for(String item:items){
                     lst.add(item);
                 }
                 c.setTimers(lst);
                }
                contacts.add(c);
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return contacts;
    }

    /**
     * Get all present categories from the database
     * @return an ArrayList of category strings
     */
    public ArrayList<String> getAllCategories() {
        ArrayList<String> categories = new ArrayList<>();
        String sql = "SELECT * FROM CategoryTable";
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
     * Create a new SQLITE database file and create all the necessary tables for the application
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
                "    TimeSpent varchar(255),\n" +
                "    Favorite varchar(255), \n" +
                "    Timers varchar(255) \n" +
                ");";

        String createTaskTable = "CREATE TABLE TaskTable(\n" +
                "    UID integer primary key autoincrement,\n"+
                "    TaskName varchar(255),\n" +
                "    Date varchar(255),\n" +
                "    Time varchar(255),\n" +
                "    Category varchar(255),\n" +
                "    TaskDuration varchar(255),\n" +
                "    TimeSpent varchar(255), \n" +
                "    ContactName varchar(255), \n" +
                "    Favorite varchar(255), \n" +
                "    Timers varchar(255) \n" +
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

    public static void main(String[] args) {
        // removes database if it already exists
        File f = new File("res/database.db");
        f.delete();

        // create new db
        ManageDB db = new ManageDB();

        // REGRESSION TESTS
        System.out.println("\nRUNNING REGRESSION TESTS");
        try {
            if (!db.userExists()) {
                System.out.println("user does not exist, creating user");
                db.createNewUser("shrek", "123abc");
            } else {
                System.out.println("user should not exist");
            }
        } catch (Exception e) {
            System.out.println("Error");
        }
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

        Contact person = new Contact("Guy","guy@guymail.com","","");
        ArrayList<String> lst = new ArrayList<>();
        lst.add("YYYY-MM-DD;HH:MM:SS"); lst.add("YYYY-MM-DD;HH:MM:SS"); lst.add("YYYY-MM-DD;HH:MM:SS");
        person.setTimers(lst);
        db.createNewContact(person);

        
        ArrayList<Contact> c = db.getAllContacts();
        try {
            Contact a = c.get(0);
            a.setTimers(new ArrayList<>());
            db.updateContact(a);
        } catch (RowDoesNotExistException e) {
            e.printStackTrace();
        }

    }
}