package com.example.app;

import com.example.app.database.Contact;
import com.example.app.database.ManageDB;
import com.example.app.database.RowDoesNotExistException;
import com.example.app.database.Task;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;

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
        Task task = new Task("a","a","a","a","a","a","a");
        db.createNewTask(task);
        ArrayList<Task> t = db.getAllTasks();
        System.out.println(t.size());
        for (Task tt : t) {
            System.out.println(tt.toString() + "1");
        }

        System.out.println(t.get(t.size()-1).getName());
        t.get(t.size()-1).setName("BBBBBB");
        System.out.println(t.get(t.size()-1).getName());
        try {
            db.updateTask(t.get(t.size() - 1));
            t = db.getAllTasks();
            System.out.println(t.get(t.size()-1).getName());
        } catch (RowDoesNotExistException e) {
            System.out.println(e);
        }



        System.exit(0);
        //launch();
    }
}