package app;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Navigator.navigate(stage, Navigator.LOGIN_PAGE);
        stage.setTitle("Football Results Management");
    }

    public static void main(String[] args) {
        launch(args);
    }
}