module com.example.knk_football {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    opens app to javafx.fxml;
    opens controllers to javafx.fxml; // Sigurohu që kjo linjë është e pranishme
    exports app;
}