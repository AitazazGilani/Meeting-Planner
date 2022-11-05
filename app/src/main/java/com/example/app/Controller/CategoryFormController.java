package com.example.app.Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CategoryFormController {
    public TextField categoryNameTextField;
    public Button cancelBtn;
    public Button addCategoryBtn;

    public void initialize(){

    }

    public void onCancelClick() {
        Stage cur = (Stage) cancelBtn.getScene().getWindow();
        //Close the window
        cur.close();
    }

    public void onAddCategoryClick() {
        //TODO Add Category Button


    }
}
