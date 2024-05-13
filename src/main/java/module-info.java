module com.example.knk_football {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.knk_football to javafx.fxml;
    exports com.example.knk_football;
}