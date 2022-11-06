package com.example.app.Controller;

import com.example.app.database.Contact;
import com.example.app.database.ManageDB;
import com.example.app.database.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;

public class TaskFormController {

    @FXML
    protected Button saveTaskBtn, newCategoryBtn, cancelBtn;

    @FXML
    protected Label viewHeader;

    @FXML
    protected TextField titleTextField, timeTextField, durationTextField;

    @FXML
    protected DatePicker taskDatePicker;

    @FXML
    protected CheckBox repeatingCheckBox, reminderCheckBox;

    //? Category Object?
    @FXML
    protected ChoiceBox<String> categoryChoice;
    public ChoiceBox<Contact> contactChoice;

    protected ManageDB database = new ManageDB();

    /**
     * This initializes the Task Form with the appropriate information on startup.
     */
    @FXML
    private void initialize(){
        ArrayList<String> categoryList = database.getAllCategories();

        if(!categoryList.isEmpty()){
            if(!categoryList.get(0).equals("None")){
                categoryList.add(0, "None");
            }
        }

        categoryChoice.setValue("None");
        categoryChoice.getItems().setAll(categoryList);

        //init the task forms information, text boxes should be blank, and dropdown boxes need information
    }

    /**
     * Save the current Task to the database
     */
    @FXML
    private void onSaveTaskClick() {
        //Grab all info from text fields/dropdowns/options and create a new task with that info

        Task t = new Task(titleTextField.getText(),
                taskDatePicker.getValue().toString(),
                timeTextField.getText(),
                categoryChoice.getValue(),
                "",
                "",
                "");

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
        //Close the window
        cur.close();

        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.
    }
}
