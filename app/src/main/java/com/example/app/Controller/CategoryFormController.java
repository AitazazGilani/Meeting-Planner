package com.example.app.Controller;

import com.example.app.database.ManageDB;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CategoryFormController {

    @FXML
    protected TextField categoryNameTextField;

    @FXML
    protected Button cancelBtn;

    @FXML
    protected Button addCategoryBtn;

    protected ManageDB database = new ManageDB();

    @FXML
    private void initialize(){
        //doesn't need to be initialized with anything
    }

    @FXML
    private void onCancelClick() {
        //get the stage
        Stage cur = (Stage) cancelBtn.getScene().getWindow();
        //Close the window
        cur.close();
    }

    /**
     * Opens the New Category window to create a new category
     */
    @FXML
    private void onAddCategoryClick() {
        //add the category to the database
        database.createNewCategory(categoryNameTextField.getText());
        //get the stage
        Stage cur = (Stage) addCategoryBtn.getScene().getWindow();
        //Close the window
        cur.close();
    }
}
