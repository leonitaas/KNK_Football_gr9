package repository;

import models.League;
import models.Squad;
import models.Team;
import service.ConnectionUtil;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SquadRepository {

    public static void insertByTeam(Squad squad) throws SQLException {
        String sql = "INSERT INTO squad (team_id) " +
                "Values (?)";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, squad.getTeam_id().getId());
        statement.executeUpdate();

    }

    public static Squad findById(int squadId) throws SQLException {
        String sql = "Select * from squad where id = ?";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,squadId);
        ResultSet result = statement.executeQuery();
        while (result.next()){
            Squad squad = new Squad(result.getInt("id"),
                    TeamRepository.findById(result.getInt("team_id"))
            );
            return squad;
        }
        return null;
    }

    public static Squad findIdByTeam(Team team) throws SQLException {
        String sql = "Select id, team_id, coach_id from squad where team_id = ?";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,team.getId());
        ResultSet result = statement.executeQuery();
        while (result.next()){
            Squad squad = new Squad(result.getInt("id"),
                    TeamRepository.findById(result.getInt("team_id"))
            );
            return squad;
        }
        return null;
    }


}
