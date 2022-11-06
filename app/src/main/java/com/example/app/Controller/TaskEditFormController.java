package com.example.app.Controller;

import com.example.app.App;
import com.example.app.database.ManageDB;
import com.example.app.database.RowDoesNotExistException;
import com.example.app.database.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class TaskEditFormController {

    protected static Task task;

    @FXML
    protected Button saveTaskBtn, deleteBtn, cancelBtn;

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

    protected ManageDB database = new ManageDB();

    public void editTaskData(Task editTask){
        task = editTask;
    }

    /**
     * This initializes the Task Form with the appropriate information on startup.
     */
    @FXML
    private void initialize(){
        titleTextField.setText(task.getName());
        taskDatePicker.setValue(LocalDate.parse(task.getDate()));
        //Task object doesn't have a repeat boolean, ignore for now
        //repeatingCheckBox.setSelected(false);
        //Task object doesn't have a reminder boolean, ignore for now
        //reminderCheckBox.setSelected(false);
        timeTextField.setText(task.getTime());

        ArrayList<String> categoryList = database.getAllCategories();
        if(!categoryList.isEmpty()){
            if(!categoryList.get(0).equals("None")){
                categoryList.add(0, "None");
            }
        }

        categoryChoice.setValue(task.getCategory());
        categoryChoice.getItems().setAll(categoryList);

        //initialize with the data of the selected task when edit it clicked.
    }

    /**
     * Save the current Task to the database
     */
    @FXML
    private void onSaveTaskClick() throws RowDoesNotExistException {

        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.

        task.setName(titleTextField.getText());
        task.setDate(taskDatePicker.getValue().toString());
        task.setTime(timeTextField.getText());
        task.setCategory(categoryChoice.getValue());

        //GUI doesnt give these values, ignore for now.
        //task.setContactName();
        //task.setDuration();

        database.updateTask(task);

        Stage cur = (Stage) saveTaskBtn.getScene().getWindow();
        //Close the window
        cur.close();
    }

    @FXML
    private void onCategoryClick() throws IOException {
        //doesn't need to be here currently

        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.

        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.
        Parent fxmlLoader = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("CategoryFormView.fxml")));
        //create a new window for the new task
        Stage newContactWindow = new Stage();
        newContactWindow.setTitle("New Category");
        newContactWindow.setScene(new Scene(fxmlLoader, 600, 200));
        //open the window
        newContactWindow.show();
    }

    /**
     * Close the Task Form and discard all entered information
     */
    @FXML
    private void onCancelClick() {

        Stage cur = (Stage) cancelBtn.getScene().getWindow();
        //Close the window
        cur.close();

        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.
    }
}
