package com.example.app.Controller;

import com.example.app.database.ManageDB;
import com.example.app.database.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class TaskFormController {
    @FXML
    protected Button saveTaskBtn, deleteBtn, cancelBtn;

    @FXML
    protected Label viewHeader;

    @FXML
    protected TextField titleTextField, timeTextField;

    @FXML
    protected DatePicker taskDatePicker;

    @FXML
    protected CheckBox repeatingCheckBox, reminderCheckBox;

    //? Category Object?
    @FXML
    protected ChoiceBox categoryChoice;

    protected ManageDB database = new ManageDB();

    /**
     * This initializes the Task Form with the appropriate information on startup.
     */
    @FXML
    private void initialize(){
        //TODO TaskForm Initializer

        //init the task forms information, text boxes should be blank, and dropdown boxes need information
    }

    /**
     * Save the current Task to the database
     */
    @FXML
    private void onSaveTaskClick() {
        //TODO TaskForm Save Button
        //Create a new task t with info from text fields/dropdowns/etc
        Task t = new Task(titleTextField.getText(),
                taskDatePicker.getValue().toString(),
                timeTextField.getText(),
                "",
                "",
                "",
                "self");

        //Add new task to the db
        database.createNewTask(t);

        //Gets current stage (new task window)
        Stage cur = (Stage) saveTaskBtn.getScene().getWindow();
        //Close the window after saving the task
        cur.close();
        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.
    }

    /**
     * Delete Current Task?
     */
    @FXML
    private void onDeleteClick() {
        //Doesn't need to be here currently

        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.
    }

    /**
     * Close the Task Form and discard all entered information
     */
    @FXML
    private void onCancelClick() {
        //Gets current stage (new task window)
        Stage cur = (Stage) cancelBtn.getScene().getWindow();
        //Close the new task window
        cur.close();

        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.
    }
}
