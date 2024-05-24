package controllers;

import models.League;
import models.Team;
import repository.LeagueRepository;
import repository.TeamRepository;
import service.BrowseImage;
import service.CostumedAlerts;
import service.ImagesToResources;
import service.LanguageUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class manageTeamsController extends  TranslatorController implements Initializable {


    @FXML
    private Label league;

    @FXML
    private Label name;

    @FXML
    private Label photo;

    @FXML
    private Label selectLeague;

    @FXML
    private Label stadium;



    @FXML
    private Button btnAddTeam;

    @FXML
    private Button btnBrowse;

    @FXML
    public Button btnDeleteTeam;

    @FXML
    public Button btnUpdateTeam;

    @FXML

    public Button btnClearTeam;


    @FXML
    private ComboBox<League> choseLeague;

    @FXML
    private ImageView teamImage;

    @FXML
    private TextField txtStadiumName;

    @FXML
    private TextField txtTeamName;


    @FXML
    private TableColumn<Team, Integer> colId;

    @FXML
    private TableColumn<Team, League> colLeague;


    @FXML
    private TableColumn<Team, String> colName;

    @FXML
    private TableColumn<Team, String> colStadium;


    @FXML
    private TableView<Team> teamTable;
    @FXML
    private ComboBox<League> choseLeagueToTable;
    private File fileSource ;
    private static String imagePath = ImagesToResources.getImagePath();



    @FXML
    void addTeam(ActionEvent event) {
        League league = choseLeague.getValue();
        String teamName,teamStadium, imageName;
        teamName = txtTeamName.getText();
        teamStadium =txtStadiumName.getText();
        imageName = fileSource.getName();
        Path imagePath = fileSource.toPath();
        Team team = new Team(-1,teamName,teamStadium,imageName);
        try {
            TeamRepository.insert(team,league);
            ImagesToResources.imageToResourcesTeam(league.getName(),teamName,imageName,imagePath);
            CostumedAlerts.costumeAlert(Alert.AlertType.CONFIRMATION,
                    "ManageTeams",
                    "Manage Teams",
                    "The Team has been added successfully ");
            fetchData();

        } catch (Exception e) {
            CostumedAlerts.costumeAlert(Alert.AlertType.CONFIRMATION,
                    "ManageTeams",
                    "Manage Teams",
                    "The Team failed to be added");
            throw new RuntimeException(e);
        }
    }

    public void fetchData(){
        try {
            TeamRepository.fetchToTable(teamTable,colId,colName,colStadium,colLeague);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void getDataFromTable(){
        teamTable.setRowFactory( tv -> {
            TableRow<Team> myRow = new TableRow<>();
            myRow.setOnMouseClicked( event ->{
                if (event.getClickCount() == 1 && (!myRow.isEmpty())){
                    int myIndex = teamTable.getSelectionModel().getSelectedIndex();
                    int id = teamTable.getItems().get(myIndex).getId();

                    try {
                        Team team = TeamRepository.findById(id);
                        String name = team.getName();
                        String image = team.getLogo();
                        String stadium = team.getStadium();
                        League league = teamTable.getItems().get(myIndex).getLeague();
                        txtStadiumName.setText(stadium);
                        txtTeamName.setText(name);
                        choseLeague.setValue(league);
                        String path = imagePath+"\\"+league+"\\"+name+"\\"+image;
                        this.teamImage.setImage( new Image(path));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                }
            });
            return myRow;
        });
    }


    @FXML
    void browseImage(ActionEvent event) {
        fileSource = BrowseImage.browseImage(imagePath,fileSource,teamImage);
    }

    @FXML
    void clearTeam(ActionEvent event) {
        txtTeamName.clear();
        txtStadiumName.clear();
        choseLeague.getItems().clear();
        teamImage.setImage(null);
    }

    @FXML
    void deleteTeam(ActionEvent event) {
        TeamRepository.Delete(teamTable);
        fetchData();
    }

    @FXML
    void updateTeam(ActionEvent event) {

    }

    @FXML
    void fetchFilteredData(ActionEvent event){
        League league = choseLeagueToTable.getValue();
        try {
            TeamRepository.fetchToTableByLeague(teamTable,colId,colName,colStadium,colLeague,league);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    void translateEnglish() {
        Locale currentLocale = new Locale("en");
        ResourceBundle translate = ResourceBundle.getBundle("translations.content", currentLocale);
        name.setText(translate.getString("label.name"));
        stadium.setText(translate.getString("label.stadium"));
        league.setText(translate.getString("label.league"));
        photo.setText(translate.getString("label.photo"));
        selectLeague.setText(translate.getString("label.selectLeague"));


    }

    @Override
    void translateAlbanian() {
        Locale currentLocale = new Locale("sq");
        ResourceBundle translate = ResourceBundle.getBundle("translations.content", currentLocale);
        name.setText(translate.getString("label.name"));
        stadium.setText(translate.getString("label.stadium"));
        league.setText(translate.getString("label.league"));
        photo.setText(translate.getString("label.photo"));
        selectLeague.setText(translate.getString("label.selectLeague"));


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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LeagueRepository.setValues(this.choseLeague);
        LeagueRepository.setValues(this.choseLeagueToTable);

        fetchData();
        getDataFromTable();
        changeLanguage();
    }

}
