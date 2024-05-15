package repository;

import models.Goals;
import service.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GoalsRepository {
    public static void insert(Goals goal) throws SQLException {
        String sql = "INSERT INTO goal (game,team,scored,assisted,minute,owngoal,penalty) " +
                "Values (?,?,?,?,?,?,?)";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,goal.getGame().getId());
        statement.setInt(2,goal.getTeam().getId());
        statement.setInt(3,goal.getScored().getId());
        statement.setInt(4,goal.getAssisted().getId());
        statement.setString(5,goal.getMinute());
        statement.setBoolean(6,goal.getOwngoal());
        statement.setBoolean(7,goal.getPenalty());
        statement.executeUpdate();
    }
}

