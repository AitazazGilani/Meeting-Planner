package com.example.app.Controller;

import com.example.app.App;
import com.example.app.database.Contact;
import com.example.app.database.ManageDB;
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

public class ContactFormController {
    @FXML
    protected Button cancelBtn, newCategoryBtn, saveContactBtn;

    @FXML
    protected Label viewHeader;

    @FXML
    protected TextField titleTextField, emailTextField;

    @FXML
    protected ChoiceBox<String> categoryChoice;

    protected ManageDB database = new ManageDB();

    /**
     * This initializes the ContactForm with the appropriate information on startup.
     */
    @FXML
    private void initialize(){
        //get the categories in the database
        ArrayList<String> categoryList = database.getAllCategories();

        //if the database has not categories, dont try to manipulate it
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
        //add the categories to the choice box.
        categoryChoice.getItems().setAll(categoryList);
    }

    /**
     * Closes the Contact Form and discards and entered information
     */
    @FXML
    private void onCancelClick() {

        //Gets current stage (new contact window)
        Stage cur = (Stage) cancelBtn.getScene().getWindow();
        //Close the window
        cur.close();
    }

    /**
     * Saves the information entered into the Contact Form and creates a new Contact, sends it to the database.
     */
    @FXML
    private void onSaveContactClick() {
        //Create a new contact with info from the form
        Contact contact = new Contact(titleTextField.getText(),
                emailTextField.getText(),
                "",
                categoryChoice.getValue());

        System.out.println(categoryChoice.getValue());

        //Add new contact c to DB
        database.createNewContact(contact);

        //Gets current stage (new contact window)
        Stage cur = (Stage) saveContactBtn.getScene().getWindow();
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
}
