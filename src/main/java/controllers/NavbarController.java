package controllers;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import app.Navigator;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import service.LanguageUtil;

public class NavbarController extends TranslatorController {

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
    private ChoiceBox<String> choseLanguage;

    private ResourceBundle bundle;

    public void setLanguage(Event event) {
        String selectedLanguage = choseLanguage.getValue();
        if ("Albanian".equals(selectedLanguage)) {
            translateAlbanian();
            LanguageUtil.setLanguage("Albanian");
        } else {
            translateEnglish();
            LanguageUtil.setLanguage("English");
        }
    }

    @Override
    void translateEnglish() {
        Locale currentLocale = new Locale("en");
        bundle = ResourceBundle.getBundle("translations.content", currentLocale);
        updateButtonLabels();
    }

    @Override
    void translateAlbanian() {
        Locale currentLocale = new Locale("sq");
        bundle = ResourceBundle.getBundle("translations.content", currentLocale);
        updateButtonLabels();
    }

    private void updateButtonLabels() {
        homeButton.setText(bundle.getString("homeButton"));
        matchesButton.setText(bundle.getString("matchesButton"));
        teamsButton.setText(bundle.getString("teamsButton"));
        playersButton.setText(bundle.getString("playersButton"));
        leaguesButton.setText(bundle.getString("leaguesButton"));
        logoutButton.setText(bundle.getString("logoutButton"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choseLanguage.getItems().addAll("English", "Albanian");
        choseLanguage.setValue(LanguageUtil.getLanguage());
        choseLanguage.setOnAction(this::setLanguage);
        setLanguage(null); // Initialize with the default language
    }

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
}
