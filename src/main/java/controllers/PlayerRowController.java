package controllers;

import models.Player;
import models.PlayerStatistics;
import repository.PlayerStatisticsRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.SQLException;

public class PlayerRowController {
    @FXML
    private Label playerAssists;

    @FXML
    private Label playerGoals;

    @FXML
    private ImageView playerImage;

    @FXML
    private Label playerName;



    @FXML
    private Label playerPositon;

    public void setData(Player player, Image playerImage) {
        this.playerName.setText(player.getName());
        this.playerImage.setImage(playerImage);
        this.playerPositon.setText(player.getPosition());

        try {
            PlayerStatistics playerStatistics = PlayerStatisticsRepository.getPlayerStatisticsByPlayer(player);
            this.playerGoals.setText(String.valueOf(playerStatistics.getGoal()));
            this.playerAssists.setText(String.valueOf(playerStatistics.getAssist()));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
