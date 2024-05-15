package repository;

import models.LeagueMatch;
import service.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LeagueMatchRepository {
    public static void insert(LeagueMatch leagueMatches) throws SQLException {
        String sql = "INSERT INTO league_matches (league_id,match_id) " +
                "Values (?,?)";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,leagueMatches.getLeague_id().getId());
        statement.setInt(2, leagueMatches.getMatch_id().getId());
        statement.executeUpdate();
    }
}

