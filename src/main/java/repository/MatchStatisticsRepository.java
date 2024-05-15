package repository;

import models.Match;
import models.MatchStatistics;
import service.ConnectionUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MatchStatisticsRepository {
    public static void insert(MatchStatistics matchStatistics) throws SQLException {
        String sql = "INSERT INTO match_statistics (match_id, home_team_goals, away_team_goals, " +
                "possession_home,possession_away,shots_home,shots_away,corners_home," +
                "corners_away,fouls_home,fouls_away,yellow_cards_home, " +
                "yellow_cards_away,red_cards_home, red_cards_away) " +
                "Values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,matchStatistics.getMatch_id().getId());
        statement.setInt(2,matchStatistics.getHome_team_goals());
        statement.setInt(3,matchStatistics.getAway_team_goals());
        statement.setDouble(4,matchStatistics.getPossession_home());
        statement.setDouble(5,matchStatistics.getPossession_away());
        statement.setInt(6,matchStatistics.getShots_home());
        statement.setInt(7,matchStatistics.getShots_away());
        statement.setInt(8,matchStatistics.getCorners_home());
        statement.setInt(9,matchStatistics.getCorners_away());
        statement.setInt(10,matchStatistics.getFouls_home());
        statement.setInt(11,matchStatistics.getFouls_away());
        statement.setInt(12,matchStatistics.getYellow_cards_home());
        statement.setInt(13,matchStatistics.getYellow_cards_away());
        statement.setInt(14,matchStatistics.getRed_cards_home());
        statement.setInt(15,matchStatistics.getRed_cards_away());
        statement.executeUpdate();
    }

    public static MatchStatistics findByMatch(Match match) throws SQLException {
        ObservableList<MatchStatistics> matchStatistics= FXCollections.observableArrayList();
        String sql = "Select * from match_statistics where match_id = ?";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,match.getId());
        ResultSet result = statement.executeQuery();

        while (result.next()){
            MatchStatistics matchStatistic = new MatchStatistics(
                    result.getInt("id"),
                    match,
                    result.getInt("home_team_goals"),
                    result.getInt("away_team_goals"),
                    result.getDouble("possession_home"),
                    result.getDouble("possession_away"),
                    result.getInt("shots_home"),
                    result.getInt("shots_away"),
                    result.getInt("corners_home"),
                    result.getInt("corners_away"),
                    result.getInt("fouls_home"),
                    result.getInt("fouls_away"),
                    result.getInt("yellow_cards_home"),
                    result.getInt("yellow_cards_away"),
                    result.getInt("red_cards_home"),
                    result.getInt("red_cards_away")
            );
            matchStatistics.add(matchStatistic);
            return matchStatistic;
        }
        return null;
    }
}
