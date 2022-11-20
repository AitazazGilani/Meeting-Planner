package com.example.app.Controller;

import com.example.app.App;
import com.example.app.database.ManageDB;
import com.example.app.database.UserAlreadyExistsException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class NewLoginController {
    @FXML
    protected Button loginBtn;

    @FXML
    protected AnchorPane centerAnchorPane;

    @FXML
    protected Label errorMessageLabel;

    @FXML
    protected TextField usernameTextField;

    @FXML
    protected TextField passwordTextField;

    @FXML
    protected TextField reEnterPasswordTextField;

    @FXML
    protected Menu accountMenu;

    protected ManageDB database = new ManageDB();


    @FXML
    protected void ClickLoginButton() throws UserAlreadyExistsException {

        //If the passwords match, try to create a new user
        if(passwordTextField.getText().equals(reEnterPasswordTextField.getText())){
            try{
                database.createNewUser(usernameTextField.getText(), passwordTextField.getText());

                //If the new user is created, go to the 'home' calendar view
                //Load the Calendar view into the loader
                Parent fxmlLoader = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("CalendarView.fxml")));
                //create a new window for the calendar view
                Stage newTaskWindow = new Stage();
                newTaskWindow.setTitle("TODO Application");
                newTaskWindow.setScene(new Scene(fxmlLoader, 1200, 700));
                //open the window
                newTaskWindow.show();

                //Gets current stage (Locked screen)
                Stage cur = (Stage) loginBtn.getScene().getWindow();
                //Close the window
                cur.close();


            }catch (UserAlreadyExistsException | IOException e){
                errorMessageLabel.setText("User already exists!");
                System.out.println(e);
            }

        }else{
            //passwords didn't match
            errorMessageLabel.setText("Passwords dont match!");
        }
    }
}
