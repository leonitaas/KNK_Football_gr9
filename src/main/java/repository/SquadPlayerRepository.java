package repository;

import models.SquadPlayer;
import models.Standings;
import service.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SquadPlayerRepository {
    public static void insert(SquadPlayer squadPlayer) throws SQLException {
        String sql = "INSERT INTO playersquad (sid,pid) " +
                "Values (?,?)";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, squadPlayer.getSquad_id().getId());
        statement.setInt(2, squadPlayer.getPlayer_id().getId());

        statement.executeUpdate();

    }
}