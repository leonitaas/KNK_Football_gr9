package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import app.Navigator;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import service.LanguageUtil;

public class NavbarController extends TranslatorController implements Initializable {

    @FXML
    private Button homeButton;
    @FXML
    private Button matchesButton;

    @FXML
    private Button teamsButton;

    @FXML
    private Button playersButton;

    @FXML
    private Button leaguesButton;

    @FXML
    private Button logoutButton;

    @FXML
    private ResourceBundle resources;
    @FXML
    private ChoiceBox choseLanguage;

    @FXML
    private URL location;


    @FXML
    void managePlayerController(ActionEvent event) {
        Navigator.navigate(event, Navigator.Player_Page);
    }

    @FXML
    void changeToLogin(ActionEvent event) {
        Navigator.navigate(event, Navigator.LOGIN_PAGE);
    }

    @FXML
    void BackAtHome(ActionEvent event) {
        Navigator.navigate(event, Navigator.HOME_PAGE);
    }

    @FXML
    void changetoleague(ActionEvent event) {
        Navigator.navigate(event, Navigator.League_page);
    }

    @FXML
    void changetoMatch(ActionEvent event) {
        Navigator.navigate(event, Navigator.Match_page);
    }

    @FXML
    void changeToTeam(ActionEvent event) {
        Navigator.navigate(event, Navigator.Team_page);
    }






    public void setLanguage(Event event) {
        if (choseLanguage.getValue() == "Albanian") {
            try {
                translateAlbanian();
            } catch (Exception e) {
                System.out.println(e);
            }
            LanguageUtil.setLanguage("Albanian");
        } else {
            try {
                translateEnglish();
            } catch (Exception e) {
                System.out.println(e);
            }
            LanguageUtil.setLanguage("English");
        }
    }

    @Override
    void translateEnglish() {
        Locale currentLocale = new Locale("en");
        ResourceBundle translate = ResourceBundle.getBundle("translations.content", currentLocale);

        matchesButton.setText(translate.getString("button.matchesButton"));
        leaguesButton.setText(translate.getString("button.leaguesButton"));
        playersButton.setText(translate.getString("button.playersButton"));
        homeButton.setText(translate.getString("button.homeButton"));
        logoutButton.setText(translate.getString("button.logoutButton"));
    }

    @Override
    void translateAlbanian() {

        Locale currentLocale = new Locale("sq");
        ResourceBundle translate = ResourceBundle.getBundle("translations.content", currentLocale);

        matchesButton.setText(translate.getString("button.matchesButton"));
        leaguesButton.setText(translate.getString("button.leaguesButton"));
        playersButton.setText(translate.getString("button.playersButton"));
        teamsButton.setText(translate.getString("button.teamsButton"));
        homeButton.setText(translate.getString("button.homeButton"));
        logoutButton.setText(translate.getString("button.logoutButton"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choseLanguage.setValue(LanguageUtil.getLanguage());
        this.setLanguage(null);
        choseLanguage.getItems().addAll("English", "Albanian");
        choseLanguage.setOnAction(this::setLanguage);
    }
}
