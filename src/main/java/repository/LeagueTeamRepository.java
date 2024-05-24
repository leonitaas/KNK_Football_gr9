package repository;

import models.League;
import models.LeagueTeam;
import models.Squad;
import models.Team;
import service.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LeagueTeamRepository {

    public static void insert(LeagueTeam leagueTeams) throws SQLException {
        String sql = "INSERT INTO league_team (league_id,team_id) " +
                "Values (?,?)";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, leagueTeams.getLeague_id().getId());
        statement.setInt(2, leagueTeams.getTeam_id().getId());
        statement.executeUpdate();

    }

    public static LeagueTeam findByLeague(League league) throws SQLException {
        String sql = "Select * from league_team where league_id = ?";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,league.getId());
        ResultSet result = statement.executeQuery();
        while (result.next()){
            LeagueTeam leagueTeams = new LeagueTeam(LeagueRepository.findById(result.getInt("league_id")),
                    TeamRepository.findById(result.getInt("team_id")));
            return leagueTeams;
        }
        return null;
    }


}

