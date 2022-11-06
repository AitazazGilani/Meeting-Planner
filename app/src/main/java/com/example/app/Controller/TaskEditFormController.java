package com.example.app.Controller;

import com.example.app.App;
import com.example.app.database.Contact;
import com.example.app.database.ManageDB;
import com.example.app.database.RowDoesNotExistException;
import com.example.app.database.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class TaskEditFormController {

    protected static Task task;

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

    @FXML
    protected ChoiceBox<String> categoryChoice, contactChoice;

    protected ManageDB database = new ManageDB();

    public void editTaskData(Task editTask){
        task = editTask;
    }

    /**
     * This initializes the Task Form with the appropriate information on startup.
     */
    @FXML
    private void initialize(){
        //init text and fields with the given tasks data.
        titleTextField.setText(task.getName());
        taskDatePicker.setValue(LocalDate.parse(task.getDate()));
        //Task object doesn't have a repeat boolean, ignore for now
        //repeatingCheckBox.setSelected(false);
        //Task object doesn't have a reminder boolean, ignore for now
        //reminderCheckBox.setSelected(false);
        timeTextField.setText(task.getTime());
        durationTextField.setText(task.getDuration());

        //init the category choice box with the given tasks data and with the databases data
        ArrayList<String> categoryList = database.getAllCategories();
        if(!categoryList.isEmpty()){
            if(!categoryList.get(0).equals("None")){
                categoryList.add(0, "None");
            }
        }
        else{
            //if it is empty, add None as a category.
            categoryList.add("None");
        }
        categoryChoice.setValue(task.getCategory());
        categoryChoice.getItems().setAll(categoryList);

        //init the contact choice box with the given tasks data and with the databases data
        ArrayList<String> contactList = new ArrayList<>();
        for(Contact contact : database.getAllContacts()){
            contactList.add(contact.getName());
        }
        if(!contactList.isEmpty()){
            if(!contactList.get(0).equals("None")){
                contactList.add(0, "None");
            }
        }
        else{
            //if it is empty, add None as a category.
            contactList.add("None");
        }
        contactChoice.setValue(task.getContactName());
        contactChoice.getItems().setAll(contactList);
    }

    /**
     * Save the current Task to the database
     */
    @FXML
    private void onSaveTaskClick() throws RowDoesNotExistException {
        //update the given tasks data with the new entered data
        task.setName(titleTextField.getText());
        task.setDate(taskDatePicker.getValue().toString());
        task.setTime(timeTextField.getText());
        task.setCategory(categoryChoice.getValue());
        task.setContactName(contactChoice.getValue());
        task.setDuration(durationTextField.getText());

        //update the task in the database
        database.updateTask(task);

        //get the stage
        Stage cur = (Stage) saveTaskBtn.getScene().getWindow();
        //Close the window
        cur.close();
    }

    /**
     * Opens the New Category window to create a new category
     */
    @FXML
    private void onCategoryClick() throws IOException {
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
    }
}
