package com.example.app.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class TaskEditFormController {
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

    /**
     * This initializes the Task Form with the appropriate information on startup.
     */
    @FXML
    private void initialize(){
        //TODO TaskEditForm Initializer

        //initialize with the data of the selected task when edit it clicked.
    }

    /**
     * Save the current Task to the database
     */
    @FXML
    private void onSaveTaskClick() {
        //TODO TaskEditForm Save Button

        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.
    }

    /**
     * Delete Current Task?
     */
    @FXML
    private void onDeleteClick() {
        //TODO TaskEditForm Delete Button

        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.
    }

    /**
     * Close the Task Form and discard all entered information
     */
    @FXML
    private void onCancelClick() {
        //TODO TaskEditForm Cancel Button

        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.
    }
}
