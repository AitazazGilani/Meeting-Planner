package com.example.app.Controller;

import com.example.app.App;
import com.example.app.database.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

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
     * This initializes the Tasks Tab with the appropriate information on startup.
     */
    @FXML
    private void initialize(){

    }

    /**
     * Move to Tasks Tab on Click
     */
    @FXML
    private void onTasksTabClick() {
        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.

        //Just refresh the calendar page
        //refresh by:
        // 1. clearing the page
        // 2. re-initialize.

        // or for now, just leave it blank I suppose.
    }

    /**
     * Move to Calendar Tab on click
     */
    @FXML
    private void onCalendarTabClick() throws IOException {
        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.

        //loads the TaskView into fxmlLoader.
        Parent fxmlLoader = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("CalendarView.fxml")));
        //gets the current stage.
        Stage stage = (Stage)tasksTabBtn.getScene().getWindow();
        //sets the current stage to the new scene.
        stage.getScene().setRoot(fxmlLoader);

    }

    /**
     * Move to Contacts Tab on Click
     */
    @FXML
    private void onContactsTabClick() throws IOException {
        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.

        //loads the TaskView into fxmlLoader.
        Parent fxmlLoader = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("ContactsView.fxml")));
        //gets the current stage.
        Stage stage = (Stage)tasksTabBtn.getScene().getWindow();
        //sets the current stage to the new scene.
        stage.getScene().setRoot(fxmlLoader);
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
    private void onNewTaskClick() throws IOException {
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

    /**
     * ? is Category its own object? My tired brain can't remember
     */
    @FXML
    private void onNewCategoryClick() {
        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.
    }
}
