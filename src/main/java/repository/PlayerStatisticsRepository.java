package repository;

import models.League;
import models.Player;
import models.PlayerStatistics;
import service.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerStatisticsRepository {
    public static void insertByPlayer(PlayerStatistics playerStatistics) throws SQLException {
        String sql = "INSERT INTO player_statistics (player_id,goals,assists) " +
                "Values (?,?,?)";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, playerStatistics.getPlayer_id().getId());
        statement.setInt(2, 0);
        statement.setInt(3, 0);

        statement.executeUpdate();
    }
    public static PlayerStatistics getPlayerStatisticsByPlayer(Player player) throws SQLException {

        String sql = "Select * from player_statistics where player_id= ?";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,player.getId());

        ResultSet result = statement.executeQuery();
        while (result.next()){
            PlayerStatistics playerStatistics= new PlayerStatistics(result.getInt("id"),player,result.getInt("goals"),result.getInt("assists"),result.getInt("minutesplayed"));
            return playerStatistics;
        }
        return null;
    }
}
