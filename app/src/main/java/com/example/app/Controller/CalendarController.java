package com.example.app.Controller;

import com.example.app.*;

import java.io.IOException;
import java.util.Calendar;
import java.util.Objects;
import java.util.Stack;

import com.example.app.database.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CalendarController {

    //TODO Future reference, the ListView may not be of the Task Object, confirm this in the future.
    @FXML
    protected ListView<Task> selectedDateTaskListView;

    @FXML
    protected AnchorPane centerAnchorPane;

    @FXML
    protected Button tasksTabBtn, calendarTabBtn, contactsTabBtn, newTaskBtn;

    @FXML
    protected Label selectedDateLabel, numberOfTasksLabel;

    /**
     * This initializes the CalendarView with information on startup.
     */
    @FXML
    private void initialize(){
        //TODO CalendarTab Initializer


        //init with the information that would be displayed in the calendar, however, currently we do not
        // have any calendar to work with.

        //basis: 1. Pull every task stored into the database
        //       2. one by one (hopefully if they are sorted) update the box in the calendar that corresponds with the
        //          date and display the required information.
    }

    /**
     * Move and display the Calendar Tab
     */
    @FXML
    private void clickCalendarTab() {
        //TODO CalendarTab Button refresh

        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.

        //Just refresh the calendar page
        //refresh by:
        // 1. clearing the page
        // 2. re-initialize.

        // or for now, just leave it blank I suppose.

    }

    /**
     * Move and display the Tasks Tab
     */
    @FXML
    private void clickTasksTab() throws IOException {
        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.

        //loads the TaskView into fxmlLoader.
        Parent fxmlLoader = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("TasksView.fxml")));
        //gets the current stage.
        Stage stage = (Stage)calendarTabBtn.getScene().getWindow();
        //sets the current stage to the new scene.
        stage.getScene().setRoot(fxmlLoader);

    }

    /**
     * Move and display the Contacts Tab
     */
    @FXML
    private void clickContactsTab() throws IOException {
        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.

        //loads the TaskView into fxmlLoader.
        Parent fxmlLoader = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("ContactsView.fxml")));
        //gets the current stage.
        Stage stage = (Stage)calendarTabBtn.getScene().getWindow();
        //sets the current stage to the new scene.
        stage.getScene().setRoot(fxmlLoader);
    }

    /**
     * Move and display the TaskForm Tab
     */
    @FXML
    private void clickNewTask() throws IOException {
        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.

        //Load the Task form view into the loader
        Parent fxmlLoader = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("TaskFormView.fxml")));
        //create a new window for the new task
        Stage newTaskWindow = new Stage();
        newTaskWindow.setTitle("New Task");
        newTaskWindow.setScene(new Scene(fxmlLoader, 900, 600));
        //open the window
        newTaskWindow.show();
    }
}
