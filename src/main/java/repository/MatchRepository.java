package repository;

import models.*;
import service.ConnectionUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

import static repository.PlayerRepository.findIdByData;

public class MatchRepository {
    public static void insert(Match match, League league, MatchStatistics matchStatistics) throws SQLException {
        String sql = "INSERT INTO matches (hometeam_id,awayteam_id,match_date) " +
                "Values (?,?,?)";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,match.getHometeam_id().getId());
        statement.setInt(2,match.getAwayteam_id().getId());
        statement.setDate(3, (Date) match.getMatch_date());
        statement.executeUpdate();

        match.setId(findIdByData(match));
        matchStatistics.setMatch_id(match);
        MatchStatisticsRepository.insert(matchStatistics);
        LeagueMatch leagueMatches = new LeagueMatch(league,match);
        LeagueMatchRepository.insert(leagueMatches);
    }

    public static int findIdByData(Match match) throws SQLException {
        String sql = "Select id from matches where hometeam_id = ? and awayteam_id = ?  and match_date = ?";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,match.getHometeam_id().getId());
        statement.setInt(2,match.getAwayteam_id().getId());
        statement.setDate(3, (Date) match.getMatch_date());
        ResultSet result = statement.executeQuery();
        if(result.next()){
            int id  = result.getInt("id");
            return id;
        }
        else {
            return -1;
        }
    }

    public static ObservableList<Match> getAllMatchesByDate(Date date) throws SQLException {
        ObservableList matches = FXCollections.observableArrayList();
        String sql = "Select * from matches where match_date = ?";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDate(1,date);
        ResultSet result = statement.executeQuery();

        while (result.next()){
            Match match = new Match(
                    result.getInt("id"),
                    TeamRepository.findById(result.getInt("hometeam_id")),
                    TeamRepository.findById(result.getInt("awayteam_id")),
                    result.getDate("match_date")
            );
            matches.add(match);
        }
        return matches;
    }
    public static ObservableList<Match> getAllMatchesToday() throws SQLException {
        ObservableList matches = FXCollections.observableArrayList();
        String sql = "Select * from matches where match_date = ?";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        LocalDate now = LocalDate.now();
        Date date = Date.valueOf(now);
        statement.setDate(1,date);
        ResultSet result = statement.executeQuery();

        while (result.next()){
            Match match = new Match(
                    result.getInt("id"),
                    TeamRepository.findById(result.getInt("hometeam_id")),
                    TeamRepository.findById(result.getInt("awayteam_id")),
                    result.getDate("match_date")
            );
            matches.add(match);
        }
        return matches;
    }
}

