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
import javafx.scene.Node;
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
    @FXML
    protected TableColumn<Task, String> taskTitleTableColumn, taskDateTableColumn, taskTimeTableColumn, taskDurationTableColumn,
            taskRepeatingTableColumn, taskReminderSetTableColumn, taskCategoryTableColumn, taskContactTableColumn, taskTimeSpentTableColumn;

    @FXML
    protected TextField searchBarTextField;

    @FXML
    protected Button calendarTabBtn, tasksTabBtn, contactsTabBtn, newCategoryBtn, newTaskBtn, deleteBtn, editBtn;

    @FXML
    protected VBox selectedTaskInfoBox;

    @FXML
    protected Label taskNameLabel, taskDateLabel, taskRepeatingLabel, taskReminderSetLabel, taskTimeLabel,
            taskCategoryLabel, taskTimeSpentLabel, taskContactLabel, taskDurationLabel;

    @FXML
    protected TableView<Task> tasksTableView;

    //figure out what obj this is made of.
    @FXML
    protected ChoiceBox<String> sortByChoiceBox;

//    @FXML
//    protected TableColumn<Task, String> titleColumn = new TableColumn<>("Title"),
//            dateColumn = new TableColumn<>("Date"),
//            timeColumn = new TableColumn<>("Time"),
//            categoryColumn = new TableColumn<>("Category");

    protected ManageDB database = new ManageDB();

    /**
     * This initializes the Tasks Tab with the appropriate information on startup.
     */
    @FXML
    private void initialize(){
        //init and display all tasks in order by date

//        titleColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
//        tasksTableView.getColumns().add(titleColumn);
//        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
//        tasksTableView.getColumns().add(dateColumn);
//        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
//        tasksTableView.getColumns().add(timeColumn);
//        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
//        tasksTableView.getColumns().add(categoryColumn);

        taskTitleTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        taskDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        taskTimeTableColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        taskCategoryTableColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        taskDurationTableColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        taskContactTableColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        //these three don't need to be displayed right now, so they will be blank
//        taskTimeSpentTableColumn
//        taskRepeatingTableColumn
//        taskReminderSetTableColumn

        ArrayList<Task> tasks = database.getAllTasks();

        for(Task task : tasks){
            tasksTableView.getItems().add(task);
        }

        ArrayList<String> categoryList = database.getAllCategories();

        if(!categoryList.isEmpty()){
            if(!categoryList.get(0).equals("None")){
                categoryList.add(0, "None");
            }
        }

        sortByChoiceBox.setValue("None");
        sortByChoiceBox.getItems().setAll(categoryList);

        tasksTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Task>() {
            @Override
            public void changed(ObservableValue<? extends Task> observableValue, Task task, Task t1) {
                taskNameLabel.setText("Name: " + tasksTableView.getSelectionModel().getSelectedItem().getName());
                taskDateLabel.setText("Date: " + tasksTableView.getSelectionModel().getSelectedItem().getDate());
                taskRepeatingLabel.setText("Repeating: Currently not implemented");
                taskReminderSetLabel.setText("Reminder Set: Currently not implemented");
                taskTimeLabel.setText("Time: " + tasksTableView.getSelectionModel().getSelectedItem().getTime());
                taskDurationLabel.setText("Duration: " + tasksTableView.getSelectionModel().getSelectedItem().getDuration());
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
        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.

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
        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.

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
        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.

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
        newTaskWindow.setScene(new Scene(fxmlLoader, 900, 600));
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

        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.
    }

    /**
     * Open a blank Task Form to create a new task
     */
    @FXML
    private void onNewTaskClick() throws IOException {
        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.

        //TODO Minor: Check if the window is already open, as to not create 300 tabs.

        //Load the Task form view into the loader
        Parent fxmlLoader = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("TaskFormView.fxml")));
        //create a new window for the new task
        Stage newTaskWindow = new Stage();
        newTaskWindow.setTitle("New Task");
        newTaskWindow.setScene(new Scene(fxmlLoader, 900, 600));
        //open the window
        newTaskWindow.show();
    }

    /**
     * ? is Category its own object? My tired brain can't remember
     */
    @FXML
    private void onNewCategoryClick() throws IOException {
        //note, there used to be a param for: ActionEvent actionEvent
        //I removed it as it doesn't seem necessary at the moment, just keep it in mind.

        Parent fxmlLoader = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("CategoryFormView.fxml")));
        //create a new window for the new task
        Stage newContactWindow = new Stage();
        newContactWindow.setTitle("New Category");
        newContactWindow.setScene(new Scene(fxmlLoader, 600, 200));
        //open the window
        newContactWindow.show();
    }
}
