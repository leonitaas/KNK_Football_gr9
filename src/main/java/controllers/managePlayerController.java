package controllers;

import models.*;
import repository.LeagueRepository;
import repository.PlayerRepository;
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
import java.sql.Date;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class managePlayerController extends TranslatorController implements Initializable {

    @FXML
    private Label birthday;

    @FXML
    private Label photo;

    @FXML
    private Label position;

    @FXML
    private Label team;

    @FXML
    private Label league;

    @FXML
    private Label selectLeague;

    @FXML
    private Label selectTeam;

    @FXML
    private Label name;

    @FXML
    private ComboBox<League> choseLeagueToTable;

    @FXML
    private ComboBox<League> chosePlayerLeague;

    @FXML
    private ComboBox<Team> chosePlayerTeam;

    @FXML
    private TableColumn<Player, Integer> colIdPlayer;

    @FXML
    private TableColumn<Player, String> colNamePlayer;

    @FXML
    private TableColumn<Player, Date> colPlayerBirthday;

    @FXML
    private TableColumn<Player, League> colPlayerLeague;

    @FXML
    private TableColumn<Player, String> colPlayerPos;

    @FXML
    private TableColumn<Player, Team> colPlayerTeam;

    @FXML
    private DatePicker datePlayerBirthday;

    @FXML
    private ImageView imagePlayer;

    @FXML
    private TableView<Player> tablePlayer;

    @FXML
    private TextField txtPlayerName;

    @FXML
    private TextField txtPlayerPosition;

    @FXML
    private ComboBox<Team> choseTeamToTable;
    private File fileSource;

    @FXML
    private Pagination pagination;
    private static String  imagePath = ImagesToResources.getImagePath();

    @FXML
    void addPlayer(ActionEvent event) {
        if (validateInputs()) {
            League league = chosePlayerLeague.getValue();
            Team team = chosePlayerTeam.getValue();
            String playerName = txtPlayerName.getText();
            String playerPosition = txtPlayerPosition.getText();
            Date birthday = Date.valueOf(datePlayerBirthday.getValue());
            String imageName = fileSource.getName();
            Path imagePath = fileSource.toPath();

            try {
                Player player = new Player(-1, playerName, playerPosition, birthday, imageName);
                Squad squad = new Squad(0, team);
                PlayerRepository.insert(player, squad, team);
                ImagesToResources.imageToResources(league.getName(), playerName, imageName, imagePath);
                CostumedAlerts.costumeAlert(Alert.AlertType.CONFIRMATION,
                        "ManagePlayers",
                        "Manage Player",
                        "The Player has been added successfully ");
                fetchData();
            } catch (SQLException e) {
                CostumedAlerts.costumeAlert(Alert.AlertType.ERROR,
                        "ManagePlayer",
                        "Manage Player",
                        "Failed to add the player. Please try again.");
                e.printStackTrace();
            } catch (Exception e) {
                CostumedAlerts.costumeAlert(Alert.AlertType.ERROR,
                        "ManagePlayer",
                        "Manage Player",
                        "An unexpected error occurred. Please contact support.");
                e.printStackTrace();
            }
        }
    }

    private boolean validateInputs() {
        if (chosePlayerLeague.getValue() == null || chosePlayerTeam.getValue() == null ||
                txtPlayerName.getText().isEmpty() || txtPlayerPosition.getText().isEmpty() ||
                datePlayerBirthday.getValue() == null || fileSource == null) {
            CostumedAlerts.costumeAlert(Alert.AlertType.WARNING,
                    "Manage Player",
                    "Manage Player",
                    "Please fill in all fields.");
            return false;
        }
        return true;
    }

    @FXML
    void browseImagePlayer(ActionEvent event) {
        fileSource = BrowseImage.browseImage(imagePath, fileSource, imagePlayer);
    }

    public void fetchData() {
        try {
            PlayerRepository.fetchToTable(tablePlayer, colIdPlayer, colNamePlayer, colPlayerBirthday, colPlayerLeague, colPlayerPos, colPlayerTeam);
        } catch (SQLException e) {
            e.printStackTrace();
            CostumedAlerts.costumeAlert(Alert.AlertType.ERROR,
                    "Error",
                    "Error fetching data",
                    "Failed to fetch player data from the database. Please try again later.");
        }
    }

    public void fetchDataToTable() {
        pagination.setPageCount(100);
        int rowsPerPage = 10;
        pagination.setPageFactory(pageIndex -> {
            try {
                PlayerRepository.fetchToTablePaginaton(pageIndex, rowsPerPage, tablePlayer, colIdPlayer, colNamePlayer, colPlayerBirthday, colPlayerLeague, colPlayerPos, colPlayerTeam);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return tablePlayer;
        });
    }

    @FXML
    void clearPlayer(ActionEvent event) {
        txtPlayerPosition.clear();
        txtPlayerName.clear();
        datePlayerBirthday.setValue(null);
        if (chosePlayerTeam != null) {
            chosePlayerTeam.setValue(null);
        }
        if (chosePlayerLeague != null) {
            chosePlayerLeague.setValue(null);
        }
        if (imagePlayer != null) {
            imagePlayer.setImage(null);
        }
        CostumedAlerts.costumeAlert(Alert.AlertType.INFORMATION,
                "Clear Fields",
                "Fields Cleared",
                "All fields have been cleared.");
    }

    @FXML
    void deletePlayer(ActionEvent event) {
        PlayerRepository.Delete(tablePlayer);
        fetchData();
        CostumedAlerts.costumeAlert(Alert.AlertType.INFORMATION,
                "Delete Player",
                "Player Deleted",
                "The selected player has been deleted successfully.");
    }

    @FXML
    void displayFilteredData(ActionEvent event) {
        if (choseLeagueToTable.getValue() == null) {
            CostumedAlerts.costumeAlert(Alert.AlertType.WARNING, "Manage Player", "Manage Player", "Select a league ");
        } else {
            League league = choseLeagueToTable.getValue();
            try {
                if (choseTeamToTable.getValue() == null) {
                    PlayerRepository.fetchToTableByLeague(tablePlayer, colIdPlayer, colNamePlayer, colPlayerBirthday, colPlayerPos, colPlayerTeam, colPlayerLeague, league);
                } else {
                    Team team = choseTeamToTable.getValue();
                    PlayerRepository.fetchToTableByTeam(tablePlayer, colIdPlayer, colNamePlayer, colPlayerBirthday, colPlayerPos, colPlayerTeam, colPlayerLeague, team);
                }
            } catch (SQLException e) {
                CostumedAlerts.costumeAlert(Alert.AlertType.ERROR,
                        "Error",
                        "Error fetching filtered data",
                        "Failed to fetch filtered player data. Please try again.");
                e.printStackTrace();
            }
        }
    }

    @FXML
    void clearTableFilter() {
        choseTeamToTable.setValue(null);
        choseLeagueToTable.setValue(null);
        try {
            fetchData();
            CostumedAlerts.costumeAlert(Alert.AlertType.INFORMATION,
                    "Clear Filters",
                    "Filters Cleared",
                    "Table filters have been cleared.");
        } catch (Exception e) {
            CostumedAlerts.costumeAlert(Alert.AlertType.ERROR,
                    "Error",
                    "Error clearing table filters",
                    "Failed to clear table filters. Please try again later.");
            e.printStackTrace();
        }
    }

    @FXML
    void updatePlayer(ActionEvent event) {
        Player selectedPlayer = tablePlayer.getSelectionModel().getSelectedItem();
        if (selectedPlayer == null) {
            CostumedAlerts.costumeAlert(Alert.AlertType.WARNING,
                    "Update Player",
                    "Update Player",
                    "Please select a player to update.");
            return;
        }
        String newName = txtPlayerName.getText();
        String newPosition = txtPlayerPosition.getText();
        Date newBirthday = Date.valueOf(datePlayerBirthday.getValue());
        Team newTeam = chosePlayerTeam.getValue();
        selectedPlayer.setName(newName);
        selectedPlayer.setPosition(newPosition);
        selectedPlayer.setBirthday(newBirthday);
        selectedPlayer.setTeam(newTeam);
        try {
            PlayerRepository.update(selectedPlayer, tablePlayer, colIdPlayer, colNamePlayer, colPlayerBirthday, colPlayerLeague, colPlayerPos, colPlayerTeam);
            CostumedAlerts.costumeAlert(Alert.AlertType.INFORMATION,
                    "Update Player",
                    "Player Updated",
                    "The selected player has been updated successfully.");
            clearPlayer(event);
        } catch (SQLException e) {
            CostumedAlerts.costumeAlert(Alert.AlertType.ERROR,
                    "Error",
                    "Error updating player",
                    "Failed to update the selected player. Please try again later.");
            e.printStackTrace();
        }
    }

    static void setValuesToTeams(ComboBox<League> chosePlayerLeague, ComboBox<Team> chosePlayerTeam) {
        chosePlayerLeague.setOnAction(actionEvent -> {
            if (chosePlayerLeague.getValue() != null) {
                chosePlayerTeam.getItems().clear();
                League league = chosePlayerLeague.getValue();
                TeamRepository.setValues(chosePlayerTeam, league);
            }
        });
    }

    public void getDataFromTable() {
        tablePlayer.setRowFactory(tv -> {
            TableRow<Player> myRow = new TableRow<>();
            myRow.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!myRow.isEmpty())) {
                    int myIndex = tablePlayer.getSelectionModel().getSelectedIndex();
                    int id = tablePlayer.getItems().get(myIndex).getId();
                    try {
                        Player player = PlayerRepository.findById(id);
                        String name = player.getName();
                        String image = player.getImage();
                        Date birthday = player.getBirthday();
                        String position = player.getPosition();
                        League league = tablePlayer.getItems().get(myIndex).getLeague();
                        Team team = tablePlayer.getItems().get(myIndex).getTeam();
                        txtPlayerName.setText(name);
                        datePlayerBirthday.setValue(birthday.toLocalDate());
                        chosePlayerTeam.setValue(team);
                        txtPlayerPosition.setText(position);
                        chosePlayerLeague.setValue(league);
                        String path = imagePath + "\\" + league + "\\" + name + "\\" + image;
                        File file = new File(path);
                        if (file.exists()) {
                            this.imagePlayer.setImage(new Image(file.toURI().toString()));
                        } else {
                            this.imagePlayer.setImage(null);
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            return myRow;
        });
    }

    @Override
    void translateEnglish() {
        Locale currentLocale = new Locale("en");
        ResourceBundle translate = ResourceBundle.getBundle("translations.content", currentLocale);
        birthday.setText(translate.getString("label.birthday"));
        name.setText(translate.getString("label.name"));
        league.setText(translate.getString("label.league"));
        team.setText(translate.getString("label.team"));
        photo.setText(translate.getString("label.photo"));
        selectLeague.setText(translate.getString("label.selectLeague"));
        selectTeam.setText(translate.getString("label.selectTeam"));
    }

    @Override
    void translateAlbanian() {
        Locale currentLocale = new Locale("sq");
        ResourceBundle translate = ResourceBundle.getBundle("translations.content", currentLocale);
        birthday.setText(translate.getString("label.birthday"));
        name.setText(translate.getString("label.name"));
        league.setText(translate.getString("label.league"));
        team.setText(translate.getString("label.team"));
        photo.setText(translate.getString("label.photo"));
        selectLeague.setText(translate.getString("label.selectLeague"));
        selectTeam.setText(translate.getString("label.selectTeam"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setValuesToTeams(this.chosePlayerLeague, this.chosePlayerTeam);
        setValuesToTeams(this.choseLeagueToTable, this.choseTeamToTable);
        LeagueRepository.setValues(this.chosePlayerLeague);
        LeagueRepository.setValues(this.choseLeagueToTable);
        fetchDataToTable();
        getDataFromTable();
    }
}
