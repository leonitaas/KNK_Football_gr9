package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import app.Navigator;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class NavbarController {

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
    private URL location;

    @FXML
    private void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void  managePlayerController( ActionEvent event) {
        Navigator.navigate(event, Navigator.Player_Page);
    }
    @FXML
    void  changeToLogin( ActionEvent event) {
        Navigator.navigate(event, Navigator.LOGIN_PAGE);
    }
    @FXML
    void BackAtHome(ActionEvent event){
        Navigator.navigate(event, Navigator.HOME_PAGE);
    }
    @FXML
    void changetoleague(ActionEvent event){
        Navigator.navigate(event, Navigator.League_page);
    }
    @FXML
    void changetoMatch(ActionEvent event){
        Navigator.navigate(event, Navigator.Match_page);
    }
}

