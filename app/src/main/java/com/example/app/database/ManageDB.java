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
                createNewUser("Shrek", "something");
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    private void createNewUser(String name, String pass) {
        String sql = "CREATE TABLE LoginTable(\n" +
                "    Username varchar(255),\n" +
                "    Password varchar(255)\n" +
                ");\n";


        //for creating table, using sql string
        try (Connection conn = DriverManager.getConnection(URL)) {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            System.out.println("Created table in db");
        } catch (Exception e) {
            System.out.println(e);
        }

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
    }

    //testing
    public static void main(String[] args){
        System.out.println(System.getProperty("user.dir"));
        ManageDB db = new ManageDB();
        System.exit(0);
    }
}