package com.example.app.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ManageDB {
    private static final String PATH = "res/database.db";
    private static final String URL = "jdbc:sqlite:res/database.db";
    public ManageDB() {
        File f = new File(PATH);
        if (!f.exists()) {
            System.out.println(".db file does not exist");
            try {
                createNewDB();
                createNewUser();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    private void createNewUser() {
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
