package com.example.app.Controller;

import com.example.app.App;
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



    public void clickUnlockButton() throws IOException {

        //Load the Calendar view into the loader
        Parent fxmlLoader = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("CalendarView.fxml")));
        //create a new window for the new task
        Stage newTaskWindow = new Stage();
        newTaskWindow.setTitle("TODO Application");
        newTaskWindow.setScene(new Scene(fxmlLoader, 1200, 700));
        //open the window
        newTaskWindow.show();


        //Gets current stage (new contact window)
        Stage cur = (Stage) unlockBtn.getScene().getWindow();
        //Close the window
        cur.close();


    }




}
