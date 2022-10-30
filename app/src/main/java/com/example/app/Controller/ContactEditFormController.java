package com.example.app.Controller;

import com.example.app.database.Contact;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ContactEditFormController {
    @FXML
    protected Button cancelBtn, deleteBtn, saveContactBtn;
    @FXML
    protected Label viewHeader;
    @FXML
    protected TextField titleTextField, emailTextField;
    //just defaulted the object type to Contact, as im entirely sure or don't remember what would go here for a Category.
    @FXML
    protected ChoiceBox<Contact> categoryChoice;

    /**
     * This initializes the ContactForm with the appropriate information on startup.
     */
    @FXML
    private void initialize(){
        //TODO ContactEditForm Initializer

        //initialize with the data of the selected contact when edit it clicked.
    }

    /**
     * Closes the Contact Form and discards and entered information
     */
    @FXML
    private void onCancelClick() {
        //TODO ContactEditForm Cancel Button
        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.

        //Close the Edit Form
    }

    /**
     * Deletes the currently selected Contact,
     */
    @FXML
    private void onDeleteClick() {
        //TODO ContactEditForm Delete Button
        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.

        //there is already a delete button in the previous page...?
    }

    /**
     * Saves the information entered into the Contact Form and creates a new Contact, sends it to the database.
     */
    @FXML
    private void onSaveContactClick() {
        //TODO ContactEditForm Save Button
        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.

        //update the current task and save/overwrite it to the database
    }
}
