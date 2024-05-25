module com.example.knk_football {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
   // requires mysql.connector.j;
    opens app to javafx.fxml;
    opens controllers to javafx.fxml;
    exports app;
}