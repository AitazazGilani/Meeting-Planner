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

public class ReturningLoginController {
    @FXML
    protected AnchorPane centerAnchorPane;

    @FXML
    protected Label errorMessageLabel;

    @FXML
    protected TextField usernameTextField;

    @FXML
    protected TextField passwordTextField;

    @FXML
    protected Button loginBtn;

    @FXML
    protected Menu accountMenu;

    ManageDB database = new ManageDB();


    /**
     * Checks to make sure the user exists and the credentials entered match the db.
     * If all is well the application is opened to the 'home' calendar view
     */
    @FXML
    protected void ClickLoginButton() throws Exception {

        String pass = passwordTextField.getText();
        String user = usernameTextField.getText();

        try{
            if(database.userExists() && database.correctLogin(user, pass)){

                //If the user exists and the credentials are correct
                //Load the Calendar view into the loader
                Parent fxmlLoader = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("CalendarView.fxml")));
                //create a new window for the calendar view
                Stage newTaskWindow = new Stage();
                newTaskWindow.setTitle("TODO Application");
                newTaskWindow.setScene(new Scene(fxmlLoader, 1200, 700));
                //open the window
                newTaskWindow.show();

                //Gets current stage (returning user view)
                Stage cur = (Stage) loginBtn.getScene().getWindow();
                //Close the window
                cur.close();
            }else{
                errorMessageLabel.setText("Incorrect username or password!");
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
