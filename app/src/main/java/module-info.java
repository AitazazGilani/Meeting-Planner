module com.example.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;


    opens com.example.app to javafx.fxml;
    exports com.example.app;
}