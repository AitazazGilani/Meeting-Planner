package com.example.app.Controller;

import com.example.app.database.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class TasksController {
    @FXML
    protected Button calendarTabBtn, tasksTabBtn, contactsTabBtn, newCategoryBtn, newTaskBtn, deleteBtn, editBtn;

    @FXML
    protected VBox selectedTaskInfoBox;

    @FXML
    protected Label taskNameLabel, taskDateLabel, taskRepeatingLabel, taskReminderSetLabel, taskTimeLabel,
            taskCategoryLabel;

    @FXML
    protected ListView<Task> tasksListView;

    //figure out what obj this is made of.
    @FXML
    protected ChoiceBox sortByChoiceBox;

    /**
     * Move to Calendar Tab on click
     */
    @FXML
    private void onCalendarTabClick() {
        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.
    }

    /**
     * Move to Tasks Tab on Click
     */
    @FXML
    private void onTasksTabClick() {
        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.
    }

    /**
     * Move to Contacts Tab on Click
     */
    @FXML
    private void onContactsTabClick() {
        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.
    }

    /**
     * Open a Task Form initialized with the currently selected Task
     */
    @FXML
    private void onEditClick() {
        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.
    }

    /**
     * Delete the currently selected Task
     */
    @FXML
    private void onDeleteClick() {
        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.
    }

    /**
     * Open a blank Task Form to create a new task
     */
    @FXML
    private void onNewTaskClick() {
        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.
    }

    /**
     * ? is Category its own object? My tired brain can't remember
     */
    @FXML
    private void onNewCategoryClick() {
        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.
    }
}
