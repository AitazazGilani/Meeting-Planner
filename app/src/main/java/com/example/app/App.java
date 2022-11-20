package com.example.app;

import com.example.app.Controller.*;
import com.example.app.database.ManageDB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Launches the Application on the Calendar UI, this will likely change in the future to account for the login page.
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        //creates the db file if there isnt one.
        new ManageDB();

        FXMLLoader fxml = new FXMLLoader(App.class.getResource("NewLoginView.fxml"));

        Scene scene = new Scene(fxml.load());
        stage.setTitle("TODO Application");
        stage.setScene(scene);
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
