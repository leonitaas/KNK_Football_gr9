module com.example.knk_football {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens models to javafx.base;
    opens controllers to javafx.fxml; // Add this line
    exports app to javafx.graphics;

    exports com.example.knk_football to javafx.graphics;
}
