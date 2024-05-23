package controllers;

import models.*;

import repository.PlayerRepository;
import repository.SquadRepository;
import service.ImagesToResources;
import service.LanguageUtil;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class TeamsController extends TranslatorController{

    @FXML
    private Label assists;



    @FXML
    private Label goals;

    @FXML
    private Label image;

    @FXML
    private Label name;



    @FXML
    private Label players;

    @FXML
    private Label position;

    @FXML
    private ImageView teamLogo;

    @FXML
    private Label teamName;

    @FXML
    private VBox vbox1;



    public void setData(League league, Team team) {
        Image image= new Image(ImagesToResources.getImagePath()+"\\"+league.getName()+"\\"+team.getName()+"\\"+team.getLogo());
        try {
            Squad squad = SquadRepository.findIdByTeam(team);
            teamLogo.setImage(image);
            teamName.setText(team.getName());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            ObservableList<Player> players = PlayerRepository.getAllPlayerByTeam(team);
            if(!players.isEmpty()) {
                for (int i = 0; i < players.size(); i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("player-form.fxml"));
                    GridPane gridPane = fxmlLoader.load();
                    PlayerRowController PlayerController = fxmlLoader.getController();
                    this.vbox1.getChildren().add(gridPane);
                    Image playerImage = new Image(ImagesToResources.getImagePath() + "\\" + league.getName() + "\\" + players.get(i).getName() + "\\" + players.get(i).getImage());
                   PlayerController.setData(players.get(i), playerImage);
                }
            }else{
                //
            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }


    @Override
    void translateEnglish() {
        Locale currentLocale = new Locale("en");
        ResourceBundle translate = ResourceBundle.getBundle("translations.content", currentLocale);
        name.setText(translate.getString("label.name"));
        assists.setText(translate.getString("label.assists"));
        position.setText(translate.getString("label.position"));
        image.setText(translate.getString("label.image"));
        goals.setText(translate.getString("label.goals"));
        players.setText(translate.getString("label.players"));

    }

    @Override
    void translateAlbanian() {
        Locale currentLocale = new Locale("sq");
        ResourceBundle translate = ResourceBundle.getBundle("translations.content", currentLocale);
        name.setText(translate.getString("label.name"));
        assists.setText(translate.getString("label.assists"));
        position.setText(translate.getString("label.position"));
        image.setText(translate.getString("label.image"));
        goals.setText(translate.getString("label.goals"));
        players.setText(translate.getString("label.players"));

    }

    public void changeLanguage(){
        LanguageUtil.languageProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("Albanian")) {
                translateAlbanian();
            } else {
                translateEnglish();
            }
        });
    }
}
