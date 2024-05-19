package app;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Navigator {
    //  public static final String Hello_PAGE = "/com.example.knk_football/hello-view.fxml";
    public  static  final String View_Page= "/app/home_form.fxml";
    public static final String LOGIN_PAGE = "/app/login_form.fxml";


    public static final String CREATE_ACCOUNT_PAGE = "/app/create_user_form.fxml";
    //public static final String Hello_PAGE="/com.example.knk_football/hello-view.fxml";

    public static void navigate(Event event, String form) {
        Node eventNode = (Node) event.getSource();
        Stage stage = (Stage) eventNode.getScene().getWindow();
        navigate(stage, form);
    }

    public static void navigate(Stage stage, String form) {
        Pane formPane = loadPane(form);
        if (formPane != null) {
            Scene newScene = new Scene(formPane);
            stage.setScene(newScene);
            stage.show();
        } else {
            System.err.println("Failed to load FXML: " + form);
        }
    }

    public static void navigate(Pane pane, String form) {
        Pane formPane = loadPane(form);
        if (formPane != null) {
            pane.getChildren().clear();
            pane.getChildren().add(formPane);
        } else {
            System.err.println("Failed to load FXML: " + form);
        }
    }

    private static Pane loadPane(String form) {
        FXMLLoader loader = new FXMLLoader(Navigator.class.getResource(form));
        try {
            System.out.println("Loading FXML: " + form);
            Pane pane = loader.load();
            System.out.println("FXML loaded successfully.");
            return pane;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        }
    }
}