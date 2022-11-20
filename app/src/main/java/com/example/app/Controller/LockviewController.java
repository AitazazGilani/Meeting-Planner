package com.example.app.Controller;

import com.example.app.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LockviewController {

    @FXML
    protected Button unlockBtn;

    @FXML
    protected AnchorPane centerAnchorPane;

    @FXML
    protected Menu accountMenu;

    @FXML
    protected MenuItem logOutMenuItem;


    @FXML
    protected void clickUnlockButton() throws IOException {

        //Load the Calendar view into the loader
        Parent fxmlLoader = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("CalendarView.fxml")));
        //create a new window for the calendar view
        Stage newTaskWindow = new Stage();
        newTaskWindow.setTitle("TODO Application");
        newTaskWindow.setScene(new Scene(fxmlLoader, 1200, 700));
        //open the window
        newTaskWindow.show();


        //Gets current stage (Locked screen)
        Stage cur = (Stage) unlockBtn.getScene().getWindow();
        //Close the window
        cur.close();


    }


    public void ClickLogOut() throws IOException {
        //Load the returning user login view into the loader
        Parent fxmlLoader = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("ReturningLoginView.fxml")));
        //create a new window for the returning user login view
        Stage newTaskWindow = new Stage();
        newTaskWindow.setTitle("TODO Application");
        newTaskWindow.setScene(new Scene(fxmlLoader, 1200, 700));
        //open the window
        newTaskWindow.show();


        //Gets current stage (Locked screen)
        Stage cur = (Stage) unlockBtn.getScene().getWindow();
        //Close the window
        cur.close();
    }
}
