module com.example.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.xerial.sqlitejdbc;
    requires java.sql;


    opens com.example.app to javafx.fxml;
    exports com.example.app;
}