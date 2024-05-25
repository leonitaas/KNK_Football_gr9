package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.League;
import models.Team;
import repository.LeagueRepository;
import service.BrowseImage;
import service.CostumedAlerts;
import service.ImagesToResources;

import java.io.File;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LeagueManagementController implements Initializable {

    @FXML
    private TextField txtLeagueId, txtLeagueName;
    @FXML
    private ImageView leaguePhoto;
    @FXML
    private Button btnBrowseImage, btnAddLeague, btnUpdateLeague, btnDeleteLeague, btnClearLeague;
    @FXML
    private TableView<League> tableLeagues;
    @FXML
    private TableColumn<League, Integer> colLeagueId;
    @FXML
    private TableColumn<League, String> colLeagueName;
    @FXML
    private TableColumn<League, Integer> colLeagueNumberOfTeams;

    private File fileSource;
    private static final Logger logger = Logger.getLogger(LeagueManagementController.class.getName());
    private String imageName;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupTable();
        fetchLeagues();
    }

    private void setupTable() {
        colLeagueId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colLeagueName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colLeagueNumberOfTeams.setCellValueFactory(new PropertyValueFactory<>("numberOfTeams"));
    }

    private void fetchLeagues() {
        try {
            tableLeagues.setItems(LeagueRepository.getAllLeagues());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error fetching leagues", e);
        }
    }

    @FXML
    private void addLeague() {
        if (fileSource == null) {
            logger.log(Level.SEVERE, "Image file not selected");
            return;
        }

        League league = new League(
                Integer.parseInt(txtLeagueId.getText()),
                txtLeagueName.getText(),
                fileSource.getName()
        );

        try {
            LeagueRepository.insert(league);
            ImagesToResources.imageToResources("leagues", league.getName(), imageName, fileSource.toPath());
            fetchLeagues();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error adding league", e);
        }
    }

    @FXML
    private void updateLeague(ActionEvent event) {
        League selectedLeague = tableLeagues.getSelectionModel().getSelectedItem();
        if (selectedLeague == null) {
            CostumedAlerts.costumeAlert(Alert.AlertType.WARNING, "Update League", "Update League", "Please select a league to update.");
            return;
        }

        // Updating the name from the text field.
        String newName = txtLeagueName.getText();
        selectedLeague.setName(newName);

        try {
            // Check if a new image file was selected before updating.
            if (fileSource != null) {
                LeagueRepository.Update(tableLeagues, selectedLeague, fileSource.toPath(), imageName);
            } else {
                // Optionally handle the situation where no new image file is selected
                LeagueRepository.UpdateWithoutImage(selectedLeague);
            }
            fetchLeagues();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating league", e);
            CostumedAlerts.costumeAlert(Alert.AlertType.ERROR, "Update League", "Update Failed", "Failed to update the league: " + e.getMessage());
        }
    }




    @FXML
    private void deleteLeague() {
        try {
            LeagueRepository.Delete(tableLeagues);
            fetchLeagues();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error deleting league", e);
        }
    }

    @FXML
    private void clearLeagueFields() {
        txtLeagueId.clear();
        txtLeagueName.clear();
        leaguePhoto.setImage(null);
    }

    @FXML
    private void browseImage() {
        fileSource = BrowseImage.browseImage(ImagesToResources.getImagePath(), fileSource, leaguePhoto);
        if (fileSource != null) {
            leaguePhoto.setImage(new Image(fileSource.toURI().toString()));
        }
    }
}
