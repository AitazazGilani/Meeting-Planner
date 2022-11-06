package com.example.app.Controller;

import com.example.app.App;
import com.example.app.database.Contact;
import com.example.app.database.ManageDB;
import com.example.app.database.RowDoesNotExistException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class ContactEditFormController {
    @FXML
    protected static Contact contact;
    @FXML
    protected Button cancelBtn, deleteBtn, saveContactBtn;
    @FXML
    protected Label viewHeader;
    @FXML
    protected TextField titleTextField, emailTextField;
    //just defaulted the object type to Contact, as im entirely sure or don't remember what would go here for a Category.
    @FXML
    protected ChoiceBox<String> categoryChoice;

    protected ManageDB database = new ManageDB();

    /**
     * Used to get the Contact data into the form for editing purposes.
     * @param editContact the contact to be edited
     */
    public void editContactData(Contact editContact) {
        contact = editContact;
    }

    /**
     * This initializes the ContactForm with the appropriate information on startup.
     */
    @FXML
    private void initialize(){

        //initialize with the data of the selected contact when edit it clicked.
        ArrayList<String> categoryList = database.getAllCategories();
        if(!categoryList.isEmpty()){
            if(!categoryList.get(0).equals("None")){
                categoryList.add(0, "None");
            }
        }

        categoryChoice.setValue(contact.getCategory());
        categoryChoice.getItems().setAll(categoryList);

        titleTextField.setText(contact.getName());
        emailTextField.setText(contact.getEmail());


    }

    /**
     * Closes the Contact Form and discards and entered information
     */
    @FXML
    private void onCancelClick() {
        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.

        //Close the Edit Form

        Stage cur = (Stage) cancelBtn.getScene().getWindow();
        //Close the window
        cur.close();

    }

    /**
     * Deletes the currently selected Contact,
     */
    @FXML
    private void onDeleteClick() {
        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.

        //there is already a delete button in the previous page...?
    }

    /**
     * Saves the information entered into the Contact Form and creates a new Contact, sends it to the database.
     */
    @FXML
    private void onSaveContactClick() throws RowDoesNotExistException {
        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.

        //update the current task and save/overwrite it to the database

        contact.setCategory(categoryChoice.getValue());
        contact.setEmail(emailTextField.getText());
        contact.setName(titleTextField.getText());

        database.updateContact(contact);

        Stage cur = (Stage) saveContactBtn.getScene().getWindow();
        //Close the window
        cur.close();
    }


    public void onCategoryClick() throws IOException {
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
}
