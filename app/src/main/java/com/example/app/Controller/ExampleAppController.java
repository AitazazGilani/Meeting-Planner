package com.example.app.Controller;

import javafx.fxml.FXML;

import java.io.IOException;

public class ExampleAppController {

    //
    //Currently, as this isn't linked to any particular view,
    //this won't really work for anything, but
    //this would initialize whatever view it's linked to
    //filling it with information, and since I *think* the program lexi is using
    //creates empty controllers to work with, this is more of as example.
    //
    // Normally there would only be one controller for each ui element (depending on how its set up
    //

    /**
     * This would initialize the view of the ui, starting it with blank boxes, or with information
     * of particular tasks
     */
    @FXML
    private void initialize(){

    }

    /**
     * This would be linked to a button press
     * removes a task that's selected from the database, and updates the ui
     */
    @FXML
    public void onDeleteClick() throws IOException {

    }

    /**
     * This would be for an editing view, something that would update a task given new information
     * could be renamed to onEditClick or onUpdateClick
     */
    @FXML
    public void updateTask(){

    }

    /**
     * This would be for a view used to edit/update contacts rather than tasks.
     * and since it would likely be in a different controller, could also be named
     * onEditClick or onUpdateClick
     */
    @FXML
    public void updateContact(){

    }

    /**
     * This would be for a view used to delete contacts rather than tasks.
     * and since it would likely be in a different controller, could also be named
     * onDeleteClick, IF it's in a different controller.
     */
    @FXML
    public void deleteContact(){

    }

}