package com.example.app;

import com.example.app.database.ManageDB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLOutput;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        //stage.show();
    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        ManageDB db = new ManageDB();
        System.exit(0);
        //launch();
    }
}