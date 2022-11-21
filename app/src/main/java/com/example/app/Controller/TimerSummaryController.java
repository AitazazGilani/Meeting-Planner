package com.example.app.Controller;

import com.example.app.database.Contact;
import com.example.app.database.ManageDB;
import com.example.app.database.TimerWithDate;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.ArrayList;

public class TimerSummaryController {

    @FXML
    protected Label contactWithGreatestTotalTimeLabel, totalTimeLabel, totalAverageLabel, longestTimerLabel;

    @FXML
    protected ListView<String> timersListView;

    private ManageDB database = new ManageDB();

    @FXML
    private void initialize(){
        ArrayList<String> cellTextList = new ArrayList<>();


        //algo : 1. get all contacts and loop through them
        //       2. while looping look at the timer array for each contact and create a string for each index
        //          "Contact : Date : Length of timer"
        //       3. also convert the string into integers
        //          i. get the total time spent on all contacts.
        //          ii. get the contact with the highest total time.
        //          iii. get the average time spent on a contact.
        //       4. fill the text boxes

        //Timer with date format : YYYY-MM-DD;00h:00m:00s

        int totalHours = 0,
            totalMinutes = 0,
            totalSeconds = 0,
            highestTimeHours = 0,
            highestTimeMinutes = 0,
            highestTimeSeconds = 0,
            averageTimeHours = 0,
            averageTimeMinutes = 0,
            averageTimeSeconds = 0,
            contactTimeHours = 0,
            contactTimeMinutes = 0,
            contactTimeSeconds = 0,
            highestContactTimeHours = 0,
            highestContactTimeMinutes = 0,
            highestContactTimeSeconds = 0;

        String highestContact = null;

        database.getAllContacts();

        int i = 0;
        for(Contact contact : database.getAllContacts()){
            ArrayList<String> currentTimerList = contact.getTimers();
            contactTimeHours = 0;
            contactTimeMinutes = 0;
            contactTimeSeconds = 0;
            for (String timerWithDate : currentTimerList){
                //clean up string
                String[] dateTimer = timerWithDate.split(";");
                String timerStr = dateTimer[1];
                String[] timerHrsMinsSecs = timerStr.split(":");

                //set the format of the string to "Contact | Date | Length of timer" to be displayed into a cell.
                cellTextList.add(contact.getName() + " | " + dateTimer[0] + " | " + dateTimer[1]);

                //get the strings that just contain the number values, excluding h, m, and s
                //from xxh, xxm, xxs to xx, xx, xx
                String hoursStr = timerHrsMinsSecs[0].replaceAll("h", "");
                String minutesStr = timerHrsMinsSecs[1].replaceAll("m", "");
                String secondsStr = timerHrsMinsSecs[2].replaceAll("s", "");

                //get the values and convert to ints
                int hours = Integer.parseInt(hoursStr);
                int minutes = Integer.parseInt(minutesStr);
                int seconds = Integer.parseInt(secondsStr);

                //add the times to the total.
                totalHours += hours;
                totalMinutes += minutes;
                totalSeconds += seconds;
                if (totalSeconds >= 60) {
                    totalMinutes ++;
                    totalSeconds = totalSeconds % 60;
                }
                if (totalMinutes >= 60) {
                    totalHours ++;
                    totalMinutes = totalMinutes % 60;
                }

                //convert the time into seconds only for comparison
                int totalTimeInSecs = ((hours*60*60) + (minutes*60) + seconds);
                int highestTimeInSecs = ((highestTimeHours*60*60) + (highestTimeMinutes*60) + highestTimeSeconds);

                if(totalTimeInSecs > highestTimeInSecs){
                    highestTimeHours = hours;
                    highestTimeMinutes = minutes;
                    highestTimeSeconds = seconds;
                }

                //update the contacts total current time
                contactTimeHours += hours;
                contactTimeMinutes += minutes;
                contactTimeSeconds += seconds;
                if (contactTimeSeconds >= 60) {
                    contactTimeMinutes ++;
                    contactTimeSeconds = contactTimeSeconds % 60;
                }
                if (contactTimeMinutes >= 60) {
                    contactTimeHours ++;
                    contactTimeMinutes = contactTimeMinutes % 60;
                }


                int contactTimeInSecs = ((contactTimeHours*60*60) + (contactTimeMinutes*60) + contactTimeSeconds);
                int highestContactTimeInSecs = ((highestContactTimeHours*60*60) + (highestContactTimeMinutes*60) +
                        highestContactTimeSeconds);

                if(contactTimeInSecs > highestContactTimeInSecs){
                    highestContact = contact.getName();
                    highestContactTimeHours = contactTimeHours;
                    highestContactTimeMinutes = contactTimeMinutes;
                    highestContactTimeSeconds = contactTimeSeconds;
                }
            }
        }

        averageTimeHours = totalHours/cellTextList.size();
        averageTimeMinutes = totalMinutes/cellTextList.size();
        averageTimeSeconds = totalSeconds/cellTextList.size();

        //convert all the times to strings for displaying
        totalTimeLabel.setText(String.format("%02dh:%02dm:%02ds", totalHours, totalMinutes, totalSeconds));
        totalAverageLabel.setText(String.format("%02dh:%02dm:%02ds", averageTimeHours, averageTimeMinutes, averageTimeSeconds));
        longestTimerLabel.setText(String.format("%02dh:%02dm:%02ds", highestTimeHours, highestTimeMinutes, highestTimeSeconds));
        contactWithGreatestTotalTimeLabel.setText(String.format(highestContact +": %02dh:%02dm:%02ds", highestContactTimeHours, highestContactTimeMinutes, highestContactTimeSeconds));
        timersListView.getItems().setAll(cellTextList);

    }



}
