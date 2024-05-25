package controllers;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class Home implements Initializable {

    @FXML
    private Label Menu;

    @FXML
    private Label MenuBack;

    @FXML
    private AnchorPane slider;

    @FXML
    private PieChart piechart;

    private List<LeagueData> leagues;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        slider.setTranslateX(-260);
        Menu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(0);
            slide.play();

            slider.setTranslateX(-260);

            slide.setOnFinished((ActionEvent e) -> {
                Menu.setVisible(false);
                MenuBack.setVisible(true);
            });
        });

        MenuBack.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(-260);
            slide.play();

            slider.setTranslateX(0);

            slide.setOnFinished((ActionEvent e) -> {
                Menu.setVisible(true);
                MenuBack.setVisible(false);
            });
        });

        // Initialize the list
        leagues = new ArrayList<>();

        fetchDataFromdb();
    }

    private void fetchDataFromdb() {
        String url = "jdbc:mysql://localhost:3306/knk_football";
        String user = "root";
        String password = "leonita123";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {


            ResultSet rs = stmt.executeQuery("SELECT league_id, match_id FROM league_matches");
            Map<String, Integer> leagueMatchCount = new HashMap<>();
            while (rs.next()) {
                String league_id = rs.getString("league_id");
                leagueMatchCount.put(league_id, leagueMatchCount.getOrDefault(league_id, 0) + 1);
            }

            // Fetch data from matches
            ResultSet rs2 = stmt.executeQuery("SELECT hometeam_goals, awayteam_goals FROM matches");
            int totalHomeGoals = 0;
            int totalAwayGoals = 0;
            int totalMatches = 0;
            while (rs2.next()) {
                totalHomeGoals += rs2.getInt("hometeam_goals");
                totalAwayGoals += rs2.getInt("awayteam_goals");
                totalMatches++;
            }

            // Determine the best statistic
            String bestStatistic = "League Matches";
            int bestValue = leagueMatchCount.size(); // Number of different leagues
            if (totalMatches > 0) {
                int averageGoalsPerMatch = (totalHomeGoals + totalAwayGoals) / totalMatches;
                if (averageGoalsPerMatch > bestValue) {
                    bestStatistic = "Matches";
                    bestValue = averageGoalsPerMatch;
                }
            }

            // Add the best statistic to the pie chart
            leagues.add(new LeagueData(bestStatistic, bestValue));
            updatePieChart();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updatePieChart() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
//        Map<String, String> colorMap = new HashMap<>();
//        colorMap.put("League Matches", "#1f77b4"); // Blue
//        colorMap.put("Matches", "#00FFFF"); // Aqua Blue
        for (LeagueData league : leagues) {
            pieChartData.add(new PieChart.Data(league.getName(), league.getValue()));
        }
        piechart.setData(pieChartData);
    }

    public static class LeagueData {
        private String name;
        private int value;

        public LeagueData(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
}