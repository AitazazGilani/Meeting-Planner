package com.example.app.Controller;

import com.example.app.App;
import com.example.app.database.Contact;
import com.example.app.database.ManageDB;
import com.example.app.database.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

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
    @FXML
    protected ChoiceBox<String> categoryChoice, contactChoice;

    protected ManageDB database = new ManageDB();

    /**
     * This initializes the Task Form with the appropriate information on startup.
     */
    @FXML
    private void initialize(){
        //get the categories in the database
        ArrayList<String> categoryList = database.getAllCategories();

        //initialize the
        if(!categoryList.isEmpty()){
            if(!categoryList.get(0).equals("None")){
                categoryList.add(0, "None");
            }
        }
        else{
            //if it is empty, add None as a category.
            categoryList.add("None");
        }

        categoryChoice.setValue("None");
        categoryChoice.getItems().setAll(categoryList);

        //create a list of strings from the names of the contacts in the database to initialize the contacts choice box
        ArrayList<String> contactList = new ArrayList<>();
        for(Contact contact : database.getAllContacts()){
            contactList.add(contact.getName());
        }
        //if there are contacts in the database, set the first item to None
        if(!contactList.isEmpty()){
            if(!contactList.get(0).equals("None")){
                contactList.add(0, "None");
            }
        }
        else{
            //if it is empty, add None as a category.
            contactList.add("None");
        }
        contactChoice.setValue("None");
        contactChoice.getItems().setAll(contactList);
    }

    /**
     * Save the current Task to the database
     */
    @FXML
    private void onSaveTaskClick() {
        //Create a new task t with info from text fields/dropdowns/etc
        Task t = new Task(titleTextField.getText(),
                taskDatePicker.getValue().toString(),
                timeTextField.getText(),
                categoryChoice.getValue(),
                durationTextField.getText(),
                "",
                contactChoice.getValue());

        //Add new task to the db
        database.createNewTask(t);

        //Gets current stage (new task window)
        Stage cur = (Stage) saveTaskBtn.getScene().getWindow();
        //Close the window after saving the task
        cur.close();
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
    }

    /**
     * Opens the New Category window to create a new category
     */
    public void onCategoryClick() throws IOException {
        Parent fxmlLoader = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("CategoryFormView.fxml")));
        //create a new window for the new task
        Stage newContactWindow = new Stage();
        newContactWindow.setTitle("New Category");
        newContactWindow.setScene(new Scene(fxmlLoader, 600, 200));
        //open the window
        newContactWindow.show();
    }
}
