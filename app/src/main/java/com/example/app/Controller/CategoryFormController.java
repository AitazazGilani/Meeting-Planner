package com.example.app.Controller;

import com.example.app.database.ManageDB;
import javafx.event.ActionEvent;
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

    }

    @FXML
    private void onCancelClick() {
        Stage cur = (Stage) cancelBtn.getScene().getWindow();
        //Close the window
        cur.close();
    }

    @FXML
    private void onAddCategoryClick() {
        database.createNewCategory(categoryNameTextField.getText());
        Stage cur = (Stage) addCategoryBtn.getScene().getWindow();
        //Close the window
        cur.close();
    }
}
