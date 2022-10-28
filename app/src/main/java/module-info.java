module com.example.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;


    opens com.example.app to javafx.fxml;
    exports com.example.app;
    exports com.example.app.Controller;
    exports com.example.app.database;
    opens com.example.app.Controller to javafx.fxml;
}