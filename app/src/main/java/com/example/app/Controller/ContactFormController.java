package com.example.app.Controller;

import com.example.app.database.Contact;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ContactFormController {
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
        //TODO ContactForm Initializer

        //don't really need much here, as it would be mostly blanked, mainly init and dropdown boxes with their information
        // with their information.
    }

    /**
     * Closes the Contact Form and discards and entered information
     */
    @FXML
    private void onCancelClick() {
        //TODO ContactForm Cancel Button

        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.
    }

    /**
     * Deletes the currently selected Contact,
     */
    @FXML
    private void onDeleteClick() {
        //TODO ContactForm Delete Button

        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.
    }

    /**
     * Saves the information entered into the Contact Form and creates a new Contact, sends it to the database.
     */
    @FXML
    private void onSaveContactClick() {
        //TODO ContactForm Save Button

        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.
    }
}
