package com.example.app.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ManageDB {
    private static final String PATH = "C:/Monke-Business-DB/database.db";
    private static final String URL = "jdbc:sqlite:C:/Monke-Business-DB/database.db/database.db";
    //    Connection dbConnection;
    public ManageDB() {
        File f = new File(PATH);
        if (!f.exists()) {
            System.out.println(".db file does not exist");
            try {
                createNewDB();
                createNewUser("username", "password");
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    private void createNewUser(String name, String pass) {
        String sql = "CREATE TABLE USER (\n"
                +    "username TEXT NOT NULL,\n"
                +    "password TEXT NOT NULL\n"
                +    ");";
        String sql1 = "INSERT INTO USER (username,password)\n"
                +     "VALUES(" + name +","+ pass + "\n"
                +     ");";
        try (Connection conn = DriverManager.getConnection(URL)) {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            System.out.println("Created table in db");
            stmt.executeUpdate(sql1);
            System.out.println("Created new user");
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
}