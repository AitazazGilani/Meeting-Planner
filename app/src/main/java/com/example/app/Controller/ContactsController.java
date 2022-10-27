package com.example.app.Controller;

import com.example.app.database.Contact;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ContactsController {
    @FXML
    protected Button calendarTabBtn, tasksTabBtn, contactsTabBtn, editContactBtn, deleteContactBtn,
            newContactBtn, newCategoryBtn;

    @FXML
    protected VBox selectedContactInfoBox;

    @FXML
    protected Label contactNameLabel, contactEmailLabel, contactCategoryLabel, selectedTimerLabel;

    @FXML
    protected HBox timerBtnBox;

    @FXML
    protected Button startBtn, pauseBtn, finishBtn, timerSummaryBtn;

    @FXML
    protected ListView<Contact> contactsListView;

    //Timer needs an object, don't really have much experience with timers yet so im not sure what to set the object as
    // yet.
    @FXML
    protected ListView currentTimersListView;

    //Also figure out what object this should be.
    @FXML
    protected ChoiceBox sortByChoiceBox;

    /**
     * Move to Calendar Tab on click
     */
    @FXML
    private void onCalendarTabClick() {
        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.
    }

    /**
     * Move to Tasks Tab on click
     */
    @FXML
    private void onTasksTabClick() {
        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.
    }

    /**
     * Move to Contacts Tab on Click
     */
    @FXML
    private void onContactsTabClick() {
        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.
    }

    /**
     * Open ContactForm on click with current contacts information and id etc
     */
    @FXML
    private void onEditContactClick() {
        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.
    }

    /**
     * Delete Currently selected Contact
     */
    @FXML
    private void onDeleteContactClick() {
        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.
    }

    /**
     * Open blank ContactForm with no information
     */
    @FXML
    private void onNewContactClick() {
        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.
    }

    /**
     * ? Display Timer summary page, there doesn't seem to be a fxml file for it yet
     * so ignore for now
     */
    @FXML
    private void onTimerSummaryClick() {
        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.
    }

    /**
     * ? new Category, again figure out the Category. is it a new Object?
     */
    @FXML
    private void onNewCategoryClick() {
        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.
    }
}
