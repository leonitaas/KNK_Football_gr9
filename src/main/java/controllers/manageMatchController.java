package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import models.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import repository.LeagueRepository;
import repository.PlayerRepository;
import repository.TeamRepository;
import service.BrowseImage;
import service.CostumedAlerts;
import service.ImagesToResources;
import service.LanguageUtil;



public class manageMatchController implements Initializable {

    @FXML
    private ComboBox<?> choseAwayTeam;

    @FXML
    private DatePicker choseDate;

    @FXML
    private ComboBox<?> choseHomeTeam;

    @FXML
    private ComboBox<League> choseLeagueMatch;

    @FXML
    private TextField fieldAwayTeamGoal;

    @FXML
    private TextField fieldHomeTeamGoal;

    @FXML
    private TextField txtAwayCorners;

    @FXML
    private TextField txtAwayFouls;

    @FXML
    private TextField txtAwayPossesion;

    @FXML
    private TextField txtAwayRedCard;

    @FXML
    private TextField txtAwayShots;

    @FXML
    private Label txtAwayTeamName;

    @FXML
    private TextField txtAwayYellowCard;

    @FXML
    private TextField txtHomeCorner;

    @FXML
    private TextField txtHomeFouls;

    @FXML
    private TextField txtHomePossesion;

    @FXML
    private TextField txtHomeRedCard;

    @FXML
    private TextField txtHomeShots;

    @FXML
    private Label txtHomeTeamName;

    @FXML
    private TextField txtHomeYellowCard;

    private String imagePath = ImagesToResources.getImagePath();

    private Team team ;




    static void setValuesToHomeTeam(ComboBox<League>  choseLeagueMatch,ComboBox<Team> choseHomeTeam,ComboBox<Team> choseAwayTeam){
        choseLeagueMatch.setOnAction(actionEvent -> {
            if(choseLeagueMatch.getValue() !=null){

                choseHomeTeam.getItems().clear();
                choseAwayTeam.getItems().clear();

                League league = choseLeagueMatch.getValue();
                TeamRepository.setValues(choseHomeTeam,league);
            }
        });
    }


    static void setValuesFromHome(ComboBox<League> choseLeagueMatch, ComboBox<Team> choseAwayTeam,
                                  ComboBox<Team> choseHomeTeam, Label txtTeamName, ImageView imageTeam , String imagePath){
        choseHomeTeam.setOnAction(actionEvent -> {
            if(choseHomeTeam.getValue() !=null){
                choseAwayTeam.getItems().clear();
                Team team = choseHomeTeam.getValue();
                League league = choseLeagueMatch.getValue();
                TeamRepository.setValuesAwayTeam(choseAwayTeam,league,team);
                txtTeamName.setText(choseHomeTeam.getValue().getName());
                Image image = new Image(imagePath+ "\\"+choseLeagueMatch.getValue().getName() + "\\"+ choseHomeTeam.getValue().getName()+"\\"+choseHomeTeam.getValue().getLogo());
                imageTeam.setImage(image);
            }else{
                txtTeamName.setText(null);
                imageTeam.setImage(null);
            }
        });
    }

    static void setValuesFromAway(ComboBox<Team> choseTeam,ComboBox<League> choseLeagueMatch,Label txtTeamName, ImageView imageTeam ,String imagePath){
        choseTeam.setOnAction(actionEvent -> {
            if(choseTeam.getValue() != null){
                txtTeamName.setText(choseTeam.getValue().getName());
                Image image = new Image(imagePath+ "\\"+choseLeagueMatch.getValue().getName() + "\\"+ choseTeam.getValue().getName()+"\\"+choseTeam.getValue().getLogo());
                imageTeam.setImage(image);
            }else{
                txtTeamName.setText(null);
                imageTeam.setImage(null);
            }
        });
    }



    private void addGoals(TextField fieldTeamGoal, VBox vbox1, ComboBox<Team> homeTeam , ComboBox<Team> awayTeam){
        fieldTeamGoal.setOnKeyTyped(actionEvent -> {
            String text  = fieldTeamGoal.getText();
            if(text != null && !text.isEmpty()){
                vbox1.getChildren().clear();
                int numberOfGoals = Integer.parseInt(text);
                try {
                    for (int i = 0; i < numberOfGoals; i++) {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("Goal.fxml"));
                        VBox vbox = fxmlLoader.load();
                        GoalController goalController = fxmlLoader.getController();
                        vbox.getProperties().put("fxmlLoader",fxmlLoader);
                        vbox1.getChildren().add(vbox);
                        RadioButton ownGoal =  goalController.getOwnGoal();

                        goalController.setData(homeTeam.getValue());

                        ownGoal.setOnAction(actionEvent1 -> {
                            if(ownGoal.isSelected()){
                                goalController.setDataOwnGoal(homeTeam.getValue(),awayTeam.getValue());
                            }else{
                                goalController.setData(homeTeam.getValue());

                            }
                        });
                    }
                }
                catch (IOException e){

                }
            }
            else {
                vbox1.getChildren().clear();

            }
        });
    }

//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
////        List<String> leagueMatchOptions = Servise.getLeagueMatchOptions();
//        /*
//        Fillimisht permes servisit e thirrim repositoryn qe na kthen rezultatet per dropdownlisten
//        Servisi duhet me na kthy listen e formatume
//
//         */
////        this.choseLeagueMatch.getItems().addAll(leagueMatchOptions);
//        //propozimi i Blendit
//    }
private void configureEnterKeyNavigation() {
    choseDate.setOnAction(e -> choseLeagueMatch.requestFocus());
    choseLeagueMatch.setOnAction(e -> choseHomeTeam.requestFocus());
    choseHomeTeam.setOnAction(e -> choseAwayTeam.requestFocus());
    choseAwayTeam.setOnAction(e -> fieldHomeTeamGoal.requestFocus());
    fieldHomeTeamGoal.setOnAction(e -> fieldAwayTeamGoal.requestFocus());
    fieldAwayTeamGoal.setOnAction(e -> txtHomePossesion.requestFocus());
    txtHomePossesion.setOnAction(e -> txtHomeShots.requestFocus());
    txtHomeShots.setOnAction(e -> txtHomeCorner.requestFocus());
    txtHomeCorner.setOnAction(e -> txtHomeFouls.requestFocus());
    txtHomeFouls.setOnAction(e -> txtHomeYellowCard.requestFocus());
    txtHomeYellowCard.setOnAction(e -> txtHomeRedCard.requestFocus());
    txtHomeRedCard.setOnAction(e -> txtAwayPossesion.requestFocus());
    txtAwayPossesion.setOnAction(e -> txtAwayShots.requestFocus());
    txtAwayShots.setOnAction(e -> txtAwayCorners.requestFocus());
    txtAwayCorners.setOnAction(e -> txtAwayFouls.requestFocus());
    txtAwayFouls.setOnAction(e -> txtAwayYellowCard.requestFocus());
    txtAwayYellowCard.setOnAction(e -> txtAwayRedCard.requestFocus());


    // Handling button press on Enter


}
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LeagueRepository.setValues(this.choseLeagueMatch);
        configureEnterKeyNavigation();


    }

}
