package repository;

import models.League;
import models.Standings;
import service.ConnectionUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StandingsRepository {
    public static void insertByTeam(Standings standings) throws SQLException {
        String sql = "INSERT INTO standings (team_id,league_id,matches_played,wins,losses,goals_for,goals_against,points) " +
                "Values (?,?,?,?,?,?,?,?)";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(2, standings.getLeague_id().getId());
        statement.setInt(1, standings.getTeam_id().getId());
        statement.setInt(3,0);
        statement.setInt(4,0);
        statement.setInt(5,0);
        statement.setInt(6,0);
        statement.setInt(7,0);
        statement.setInt(8,0);


        statement.executeUpdate();

    }
    public static ObservableList<Standings> getAllStandings(League league) throws SQLException {
        ObservableList standings = FXCollections.observableArrayList();
        String sql = "Select * from standings where league_id =? order by points desc";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,league.getId());
        ResultSet result = statement.executeQuery();

        while (result.next()){
            Standings standing = new Standings(result.getInt("id"),
                    TeamRepository.findById(result.getInt("team_id")),
                    LeagueRepository.findById(result.getInt("league_id")),
                    result.getInt("matches_played"),
                    result.getInt("wins"),
                    result.getInt("losses"),
                    result.getInt("goals_for"),
                    result.getInt("goals_against"),
                    result.getInt("points")
            );
            standings.add(standing);
        }
        return standings;
    }
}
