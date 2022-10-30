package com.example.cmpt_370_test;

/* Code sourced from: http://www.java2s.com/ref/java/javafx-gridpane-layout-calendar.html */

import java.util.Calendar;
import java.util.GregorianCalendar;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

class GraphicalCalendar extends VBox {
    private Calendar currentMonth;

    public GraphicalCalendar() {
        currentMonth = new GregorianCalendar();
        currentMonth.set(Calendar.DAY_OF_MONTH, 1);

        //setSpacing(0);

        drawCalendar(); /* from www.java2s.com */
    }

    private void drawCalendar() {
        drawHeader();
        drawBody();
        // drawFooter();    Removed Footer
    }

    /* Modified header from original source */
    private void drawHeader() {

        // Get current month and year, set as label
        String monthString = getMonthName(currentMonth.get(Calendar.MONTH));
        String yearString = String.valueOf(currentMonth.get(Calendar.YEAR));

        Label calendarTitle = new Label(monthString + ", " + yearString);
        calendarTitle.setStyle("-fx-font-size: 24; -fx-font-weight: BOLD;");

        // Buttons to change between months
        Button prevBtn = new Button("<--");
        prevBtn.setPrefWidth(45);
        prevBtn.setPrefHeight(30);
        prevBtn.setStyle("-fx-font-size: 14; -fx-font-weight: BOLD;");

        Button nextBtn = new Button("-->");
        nextBtn.setPrefWidth(45);
        nextBtn.setPrefHeight(30);
        nextBtn.setStyle("-fx-font-size: 14; -fx-font-weight: BOLD;");

        prevBtn.setOnAction(e -> previous());
        nextBtn.setOnAction(e -> next());

        BorderPane calendarHeader = new BorderPane();
        calendarHeader.setLeft(prevBtn);
        calendarHeader.setCenter(calendarTitle);
        calendarHeader.setRight(nextBtn);

        calendarHeader.setPadding(new Insets(0, 20, 5, 20));

        // Hbox settings..
        // calendarHeader.getChildren().addAll(prevBtn, calendarTitle, nextBtn);
        // calendarHeader.setAlignment(Pos.CENTER);
        // calendarHeader.setSpacing(100);

        getChildren().add(calendarHeader);
    }

    /* Slightly modified size and spacing values from www.java2s.com */
    private void drawBody() {
        GridPane gpBody = new GridPane();
        gpBody.setGridLinesVisible(false);
        //gpBody.setHgap(40);
        //gpBody.setVgap(50);
        gpBody.setAlignment(Pos.TOP_CENTER);
        gpBody.setMinHeight(300);
        gpBody.setMinHeight(400);
        gpBody.setStyle("-fx-background-color: #DDDDDD;");

        /* Added row and column constraints */
        ColumnConstraints calendarColumn = new ColumnConstraints();
        calendarColumn.setMinWidth(100);
        calendarColumn.setHalignment(HPos.CENTER);
        for(int x = 0; x < 7; x++) {
            gpBody.getColumnConstraints().add(calendarColumn);
        }
        /* For row containing week names */
        RowConstraints calendarHeaderRow = new RowConstraints();
        calendarHeaderRow.setMinHeight(40);
        calendarHeaderRow.setValignment(VPos.CENTER);

        gpBody.getRowConstraints().add(calendarHeaderRow);

        /* Original source */

        // Draw days of the week
        for (int day = 1; day <= 7; day++) {
            Text tDayName = new Text(getDayName(day));
            gpBody.add(tDayName, day - 1, 0);
        }

        // Draw days in month
        int currentDay = currentMonth.get(Calendar.DAY_OF_MONTH);
        int daysInMonth = currentMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
        int dayOfWeek = currentMonth.get(Calendar.DAY_OF_WEEK);
        int row = 1;
        for (int i = currentDay; i <= daysInMonth; i++) {
            if (dayOfWeek == 8) {
                dayOfWeek = 1;
                row++;
            }
            Text tDate = new Text(String.valueOf(currentDay));
            gpBody.add(tDate, dayOfWeek - 1, row);
            currentDay++;
            dayOfWeek++;
        }

        // Draw previous month days
        dayOfWeek = currentMonth.get(Calendar.DAY_OF_WEEK);
        if (currentDay != 1) {
            Calendar prevMonth = getPreviousMonth(currentMonth);
            int daysInPrevMonth = prevMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
            for (int i = dayOfWeek - 2; i >= 0; i--) {
                Text tDate = new Text(String.valueOf(daysInPrevMonth));
                tDate.setFill(Color.GRAY);
                gpBody.add(tDate, i, 1);
                daysInPrevMonth--;
            }
        }

        // Draw next month days
        currentMonth.set(Calendar.DAY_OF_MONTH, currentMonth.getActualMaximum(Calendar.DAY_OF_MONTH));
        dayOfWeek = currentMonth.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek != 7) {
            int day = 1;
            for (int i = dayOfWeek; i < 7; i++) {
                Text tDate = new Text(String.valueOf(day));
                tDate.setFill(Color.GRAY);
                gpBody.add(tDate, i, row);
                day++;
            }
        }


        /* Added constraints, for rows containing days of the month */
        RowConstraints calendarRow = new RowConstraints();
        calendarRow.setMinHeight(60);
        calendarRow.setValignment(VPos.TOP);
        for(int x = 1; x < gpBody.getRowCount(); x++) {
            gpBody.getRowConstraints().add(calendarRow);
        }

        getChildren().add(gpBody);
    }


    /* Unmodified from original source */

    private void previous() {
        getChildren().clear();
        currentMonth = getPreviousMonth(currentMonth);
        drawCalendar();
    }

    private void next() {
        getChildren().clear();
        currentMonth = getNextMonth(currentMonth);
        drawCalendar();
    }

    private GregorianCalendar getPreviousMonth(Calendar cal) {
        int cMonth = cal.get(Calendar.MONTH);
        int pMonth = cMonth == 0 ? 11 : cMonth - 1;
        int pYear = cMonth == 0 ? cal.get(Calendar.YEAR) - 1 : cal.get(Calendar.YEAR);
        return new GregorianCalendar(pYear, pMonth, 1);
    }

    private GregorianCalendar getNextMonth(Calendar cal) {
        int cMonth = cal.get(Calendar.MONTH);
        int pMonth = cMonth == 11 ? 0 : cMonth + 1;
        int pYear = cMonth == 11 ? cal.get(Calendar.YEAR) + 1 : cal.get(Calendar.YEAR);
        return new GregorianCalendar(pYear, pMonth, 1);
    }

    private String getDayName(int n) {
        StringBuilder sb = new StringBuilder();
        switch (n) {
            case 1:
                sb.append("Sunday");
                break;
            case 2:
                sb.append("Monday");
                break;
            case 3:
                sb.append("Tuesday");
                break;
            case 4:
                sb.append("Wednesday");
                break;
            case 5:
                sb.append("Thursday");
                break;
            case 6:
                sb.append("Friday");
                break;
            case 7:
                sb.append("Saturday");
        }
        return sb.toString();
    }

    private String getMonthName(int n) {
        String[] monthNames = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
                "October", "November", "December" };
        return monthNames[n];
    }
}
