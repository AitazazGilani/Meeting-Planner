package com.example.app.Controller;

import com.example.app.App;
import com.example.app.database.Contact;
import com.example.app.database.ManageDB;

import com.example.app.database.RowDoesNotExistException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class ContactsController {

    public Menu accountMenu;
    public MenuItem logOutMenuItem;
    //currently, doesn't need to be implemented
    @FXML
    protected TextField searchBarTextField;

    @FXML
    protected TableView<Contact> contactsTableView;

    @FXML
    protected TableColumn<Contact, String> contactNameTableColumn, contactEmailTableColumn, contactCategoryTableColumn;

    @FXML
    protected TableView<String> currentTimersTableView;

    @FXML
    protected TableColumn<String, String> currentTimersContactTableColumn, currentTimersTimeElapsedTableColumn;

    @FXML
    protected Button calendarTabBtn, tasksTabBtn, contactsTabBtn, editContactBtn, deleteContactBtn,
            newContactBtn, newCategoryBtn, lockBtn;

    @FXML
    protected VBox selectedContactInfoBox;

    @FXML
    protected Label contactNameLabel, contactEmailLabel, contactCategoryLabel, selectedTimerLabel;

    @FXML
    protected HBox timerBtnBox;

    @FXML
    protected Button startBtn, pauseBtn, finishBtn, timerSummaryBtn;

    @FXML
    protected ChoiceBox<String> sortByChoiceBox;

    protected ManageDB database = new ManageDB();

    /**
     * This initializes the Contacts Tab with the appropriate information on startup.
     */
    @FXML
    private void initialize(){
        //TODO ContactTab Timers List (NOT CURRENT SPRINT)

        //set all the cells in the table to what is in the database.
        contactsTableView.getItems().setAll(database.getAllContacts());

        //set the cell values to match the contact object for displaying
        contactNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        contactEmailTableColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        contactCategoryTableColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        //add a "None" option to the categories.
        ArrayList<String> categoryList = database.getAllCategories();
        if(!categoryList.isEmpty()){
            if(!categoryList.get(0).equals("None")){
                categoryList.add(0, "None");
            }
        }
        else{
            //if it is empty, add None as a category.
            categoryList.add("None");
        }

        sortByChoiceBox.setValue("None");
        sortByChoiceBox.getItems().setAll(categoryList);

        //this block looks for when a cell in the table is selected, then when a cell is selected, updates the details
        // on the right side of the scene
        contactsTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Contact>() {
            @Override
            public void changed(ObservableValue<? extends Contact> observableValue, Contact contact, Contact t1) {
                contactNameLabel.setText("Name: " + contactsTableView.getSelectionModel().getSelectedItem().getName());
                contactEmailLabel.setText("Email: " + contactsTableView.getSelectionModel().getSelectedItem().getEmail());
                if (Objects.equals(contactsTableView.getSelectionModel().getSelectedItem().getCategory(), "")){
                    contactCategoryLabel.setText("Category: None");
                }
                else{
                    contactCategoryLabel.setText("Category: " + contactsTableView.getSelectionModel().getSelectedItem().getCategory());
                }
            }
        });
    }

    /**
     * Move to Contacts Tab on Click
     */
    @FXML
    private void onContactsTabClick() {
        //TODO Minor: ContactTab refresh

        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.

        //Just refresh the calendar page
        //refresh by:
        // 1. clearing the page
        // 2. re-initialize.

        // or for now, just leave it blank I suppose.
    }

    /**
     * Move to Calendar Tab on click
     */
    @FXML
    private void onCalendarTabClick() throws IOException {
        //loads the TaskView into fxmlLoader.
        Parent fxmlLoader = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("CalendarView.fxml")));
        //gets the current stage.
        Stage stage = (Stage)contactsTabBtn.getScene().getWindow();
        //sets the current stage to the new scene.
        stage.getScene().setRoot(fxmlLoader);
    }

    /**
     * Move to Tasks Tab on click
     */
    @FXML
    private void onTasksTabClick() throws IOException {
        //loads the TaskView into fxmlLoader.
        Parent fxmlLoader = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("TasksView.fxml")));
        //gets the current stage.
        Stage stage = (Stage)contactsTabBtn.getScene().getWindow();
        //sets the current stage to the new scene.
        stage.getScene().setRoot(fxmlLoader);
    }

    /**
     * Open ContactForm on click with current contacts information and id etc
     */
    @FXML
    private void onEditContactClick() throws IOException {
        //check if a contact is selected.
        if(contactsTableView.getSelectionModel().getSelectedItem() != null){
            Contact contact = contactsTableView.getSelectionModel().getSelectedItem();
            ContactEditFormController contactEditFormController = new ContactEditFormController();
            contactEditFormController.editContactData(contact);
        }
        else{
            return;
        }

        Parent fxmlLoader = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("ContactEditFormView.fxml")));
        //create a new window for the new task
        Stage newContactWindow = new Stage();
        newContactWindow.setTitle("New Task");
        newContactWindow.setScene(new Scene(fxmlLoader, 900, 600));
        //open the window
        newContactWindow.show();

    }

    /**
     * Delete Currently selected Contact
     */
    @FXML
    private void onDeleteContactClick() throws RowDoesNotExistException {
        //TODO Minor: ContactTab Delete Button refreshes page.

        if(contactsTableView.getSelectionModel().getSelectedItem() != null){
            database.deleteContact(contactsTableView.getSelectionModel().getSelectedItem());
        }
    }

    /**
     * Open blank ContactForm with no information
     */
    @FXML
    private void onNewContactClick() throws IOException {
        //TODO Minor: Check if the window is already open, as to not create 300 tabs.

        //Load the Task form view into the loader
        Parent fxmlLoader = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("ContactFormView.fxml")));
        //create a new window for the new task
        Stage newContactWindow = new Stage();
        newContactWindow.setTitle("New Task");
        newContactWindow.setScene(new Scene(fxmlLoader, 900, 600));
        //open the window
        newContactWindow.show();
    }

    /**
     * ? Display Timer summary page, there doesn't seem to be a fxml file for it yet
     * so ignore for now
     */
    @FXML
    private void onTimerSummaryClick() {
        //TODO Minor: ContactTab Timer Button
    }

    /**
     * Opens the New Category window to create a new category
     */
    @FXML
    private void onNewCategoryClick() throws IOException {
        Parent fxmlLoader = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("CategoryFormView.fxml")));
        //create a new window for the new task
        Stage newContactWindow = new Stage();
        newContactWindow.setTitle("New Category");
        newContactWindow.setScene(new Scene(fxmlLoader, 600, 200));
        //open the window
        newContactWindow.show();
    }

    @FXML
    private void clickLockButton() throws IOException {
        //Load the locked screen view into the loader
        Parent fxmlLoader = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("LockedView.fxml")));
        //create a new window for the locked screen
        Stage newTaskWindow = new Stage();
        newTaskWindow.setTitle("Screen Locked");
        newTaskWindow.setScene(new Scene(fxmlLoader, 1200, 700));
        //open the window
        newTaskWindow.show();

        //Gets current stage (Contacts view)
        Stage cur = (Stage) lockBtn.getScene().getWindow();
        //Close the window
        cur.close();
    }

    public void ClickLogOut() throws IOException {

        //Load the returning user login view into the loader
        Parent fxmlLoader = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("ReturningLoginView.fxml")));
        //create a new window for the returning user login view
        Stage newTaskWindow = new Stage();
        newTaskWindow.setTitle("TODO Application");
        newTaskWindow.setScene(new Scene(fxmlLoader, 1200, 700));
        //open the window
        newTaskWindow.show();


        //Gets current stage (contacts view)
        Stage cur = (Stage) lockBtn.getScene().getWindow();
        //Close the window
        cur.close();

    }
}
