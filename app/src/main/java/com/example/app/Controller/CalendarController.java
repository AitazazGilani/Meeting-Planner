package com.example.app.Controller;

import com.example.*;

import java.io.IOException;

import com.example.app.database.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

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
     *
     * return: Nothing
     */
    @FXML
    private void initialize(){

    }

    /**
     * Move and display the Calendar Tab
     */
    @FXML
    private void clickCalendarTab() {
        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.
    }

    /**
     * Move and display the Tasks Tab
     */
    @FXML
    private void clickTasksTab() {
        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.
    }

    /**
     * Move and display the Contacts Tab
     */
    @FXML
    private void clickContactsTab() {
        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.
    }

    /**
     * Move and display the TaskForm Tab
     */
    @FXML
    private void clickNewTask() {
        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.
    }
}
