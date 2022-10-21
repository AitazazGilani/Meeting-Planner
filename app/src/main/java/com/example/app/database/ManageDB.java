package com.example.app.database;

import java.io.File;
import java.sql.*;

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
            System.out.println(e);
        }

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
                "    TimeSpent float(64)\n" +
                ");";

        String createTaskTable = "CREATE TABLE TaskTable(\n" +
                "    TaskName varchar(255),\n" +
                "    Date varchar(255),\n" +
                "    Time float(64),\n" +
                "    Category varchar(255),\n" +
                "    TaskDuration float(64),\n" +
                "    TimeSpent float(64), \n" +
                "    ContactName varchar(64)\n" +
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
        System.exit(0);
    }
}