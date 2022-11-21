package com.example.app.Controller;

import com.example.app.App;
import com.example.app.database.Contact;
import com.example.app.database.ManageDB;
import com.example.app.database.RowDoesNotExistException;
import com.example.app.database.Task;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class TasksController {
    public Menu accountMenu;
    public MenuItem logOutMenuItem;

    @FXML
    protected CheckBox favouriteTaskCheckBox, favouritesSortCheckBox;

    @FXML
    protected TableColumn<Task, String> taskTitleTableColumn, taskDateTableColumn, taskTimeTableColumn, taskDurationTableColumn,
            taskFavoriteTableColumn, taskReminderSetTableColumn, taskCategoryTableColumn, taskContactTableColumn, taskTimeSpentTableColumn;

    @FXML
    protected Button calendarTabBtn, tasksTabBtn, contactsTabBtn, newCategoryBtn, newTaskBtn, deleteBtn, editBtn, lockBtn;

    @FXML
    protected VBox selectedTaskInfoBox;

    @FXML
    protected Label taskNameLabel, taskDateLabel, taskReminderSetLabel, taskTimeLabel,
            taskCategoryLabel, taskTimeSpentLabel, taskContactLabel, taskDurationLabel;

    @FXML
    protected TableView<Task> tasksTableView;

    @FXML
    protected ChoiceBox<String> sortByChoiceBox;

    protected ManageDB database = new ManageDB();

    /**
     * This initializes the Tasks Tab with the appropriate information on startup.
     */
    @FXML
    private void initialize(){

        //set the cell values to match the task objects for displaying
        taskTitleTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        taskDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        taskTimeTableColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        taskCategoryTableColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        taskDurationTableColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        taskContactTableColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        taskFavoriteTableColumn.setCellValueFactory(new PropertyValueFactory<>("favorite"));

        //get a list of tasks and add them to the table
        tasksTableView.getItems().setAll(database.getAllTasks());

        //initialize the category choice box with the categories in the database
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
        ArrayList<String> sortList = new ArrayList<>();
        sortList.add("Name");
        sortList.add("Date & Time");
        sortList.add("Category");
        sortList.add("Contact");
        sortList.add("TimeSpent");
        sortList.add("Favorite");
        sortByChoiceBox.setValue(sortList.get(1));
        sortByChoiceBox.getItems().setAll(sortList);
        onSortChoice();

        //update the details on the right of the scene when a new table cell is selected by the user.
        tasksTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Task>() {
            @Override
            public void changed(ObservableValue<? extends Task> observableValue, Task task, Task t1) {
                taskNameLabel.setText("Name: " + tasksTableView.getSelectionModel().getSelectedItem().getName());
                taskDateLabel.setText("Date: " + tasksTableView.getSelectionModel().getSelectedItem().getDate());
                taskReminderSetLabel.setText("Reminder Set: Currently not implemented");
                taskTimeLabel.setText("Time: " + tasksTableView.getSelectionModel().getSelectedItem().getTime());
                taskDurationLabel.setText("Duration: " + tasksTableView.getSelectionModel().getSelectedItem().getDuration());
                favouriteTaskCheckBox.setSelected(tasksTableView.getSelectionModel().getSelectedItem().isFavorite());
                if (Objects.equals(tasksTableView.getSelectionModel().getSelectedItem().getCategory(), "")){
                    taskCategoryLabel.setText("Category: None");
                }
                else{
                    taskCategoryLabel.setText("Category: " + tasksTableView.getSelectionModel().getSelectedItem().getCategory());
                }
                if (Objects.equals(tasksTableView.getSelectionModel().getSelectedItem().getContactName(), "")){
                    taskContactLabel.setText("Contact: None");
                }
                else{
                    taskContactLabel.setText("Contact: " + tasksTableView.getSelectionModel().getSelectedItem().getContactName());
                }


            }
        });

    }

    /**
     * Move to Tasks Tab on Click
     */
    @FXML
    private void onTasksTabClick() {
        //TODO Minor: TaskTab Refresher

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
        Stage stage = (Stage)tasksTabBtn.getScene().getWindow();
        //sets the current stage to the new scene.
        stage.getScene().setRoot(fxmlLoader);

    }

    /**
     * Move to Contacts Tab on Click
     */
    @FXML
    private void onContactsTabClick() throws IOException {
        //loads the TaskView into fxmlLoader.
        Parent fxmlLoader = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("ContactsView.fxml")));
        //gets the current stage.
        Stage stage = (Stage)tasksTabBtn.getScene().getWindow();
        //sets the current stage to the new scene.
        stage.getScene().setRoot(fxmlLoader);
    }

    /**
     * Open a Task Form initialized with the currently selected Task
     */
    @FXML
    private void onEditClick() throws IOException {
        //check if a cell is selected, if so open the init edit menu
        if(tasksTableView.getSelectionModel().getSelectedItem() != null){
            Task task = tasksTableView.getSelectionModel().getSelectedItem();
            TaskEditFormController taskEditFormController = new TaskEditFormController();
            taskEditFormController.editTaskData(task);
        }
        else{
            return;
        }

        //Load the Task form view into the loader
        Parent fxmlLoader = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("TaskEditFormView.fxml")));
        //create a new window for the new task
        Stage newTaskWindow = new Stage();
        newTaskWindow.setTitle("New Task");
        newTaskWindow.setScene(new Scene(fxmlLoader, 900, 700));
        //open the window
        newTaskWindow.show();
    }

    /**
     * Delete the currently selected Task
     */
    @FXML
    private void onDeleteClick() throws RowDoesNotExistException {
        //TODO Minor: Delete Button refreshes page.

        if(tasksTableView.getSelectionModel().getSelectedItem() != null){
            database.deleteTask(tasksTableView.getSelectionModel().getSelectedItem());
        }
    }

    /**
     * Open a blank Task Form to create a new task
     */
    @FXML
    private void onNewTaskClick() throws IOException {
        //TODO Minor: Check if the window is already open, as to not create 300 tabs.

        //Load the Task form view into the loader
        Parent fxmlLoader = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("TaskFormView.fxml")));
        //create a new window for the new task
        Stage newTaskWindow = new Stage();
        newTaskWindow.setTitle("New Task");
        newTaskWindow.setScene(new Scene(fxmlLoader, 900, 700));
        //open the window
        newTaskWindow.show();
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

    /**
     * toggles the favorite boolean of the selected task
     */
    public void onFavoriteTaskClick() throws RowDoesNotExistException {
        if(tasksTableView.getSelectionModel().getSelectedItem() != null){
            Task task = tasksTableView.getSelectionModel().getSelectedItem();
            task.setFavorite(favouriteTaskCheckBox.isSelected());
            database.updateTask(task);
        }
    }

    /**
     * Sorts the contact list and displays the favorited Tasks
     */
    public void onFavoriteSortClick() {
        tasksTableView.getItems().clear();
        ArrayList<Task> favorites = new ArrayList<>();
        if(favouritesSortCheckBox.isSelected()){
            for (Task task : database.getAllTasks()){
                if (task.isFavorite()){
                    favorites.add(task);
                }
            }
            tasksTableView.getItems().setAll(favorites);
        }
    }

    public void onSortChoice() {
        ArrayList<Task> newSortedList;
        // if already filtered favorites only, then
        if (favouritesSortCheckBox.isSelected()) {
            newSortedList = (ArrayList<Task>) tasksTableView.getItems();
        } else {
            newSortedList = database.getAllTasks();
        }

        switch (sortByChoiceBox.getValue()) {
            case "Name":
                // sort by name then date & time
                newSortedList = database.sortTasks(newSortedList, "Name");
                break;
            case "Date & Time":
                // convert date and time of every task into unix time and sort by number
                newSortedList = database.sortTasks(newSortedList, "Date & Time");
                break;
            case "Category":
                // sort by category name then date & time
                newSortedList = database.sortTasks(newSortedList, "Category");
                break;
            case "Contact":
                // sort by contact name then date & time
                newSortedList = database.sortTasks(newSortedList, "Contact");
                break;
            case "TimeSpent":
                // convert TimeSpent of every task into unix time and sort by number then date & time
                newSortedList = database.sortTasks(newSortedList, "TimeSpent");
                break;
            case "Favorite":
                // sort by fav on top then by date & time
                newSortedList = database.sortTasks(newSortedList, "Favorite");
                // TODO: implement fav sorting in managedb
                break;
        }

        tasksTableView.getItems().setAll(newSortedList);
    }

    /**
     * 'Locks' the application by hiding everything with a blank screen
     */
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

        //Gets current stage (Tasks view)
        Stage cur = (Stage) lockBtn.getScene().getWindow();
        //Close the window
        cur.close();
    }

    /**
     * Logs the current user out of the application, returning them to the returning user login page.
     */
    @FXML
    public void ClickLogOut() throws IOException {
        //Load the returning user login view into the loader
        Parent fxmlLoader = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("ReturningLoginView.fxml")));
        //create a new window for the returning user login view
        Stage newTaskWindow = new Stage();
        newTaskWindow.setTitle("TODO Application");
        newTaskWindow.setScene(new Scene(fxmlLoader, 1200, 700));
        //open the window
        newTaskWindow.show();


        //Gets current stage (task view)
        Stage cur = (Stage) lockBtn.getScene().getWindow();
        //Close the window
        cur.close();
    }
}
