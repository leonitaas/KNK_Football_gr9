package repository;

import models.*;
import service.ConnectionUtil;
import service.CostumedAlerts;
import service.ImagesToResources;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.sql.*;

public class PlayerRepository {
    public static void insert(Player player,Squad squad, Team team) throws SQLException {
        String sql = "INSERT INTO player (name,position,birthday,image) " +
                "Values (?,?,?,?)";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,player.getName());
        statement.setString(2,player.getPosition());
        statement.setDate(3,player.getBirthday());
        statement.setString(4,player.getImage());
        statement.executeUpdate();

        player.setId(findIdByData(player));
        squad.setId(SquadRepository.findIdByTeam(team).getId());
        SquadPlayer squad_players = new SquadPlayer(squad,player);
        SquadPlayerRepository.insert(squad_players);
        PlayerStatistics playerStatistics = new PlayerStatistics(0,player,0,0,0);
        PlayerStatisticsRepository.insertByPlayer(playerStatistics);


    }
    public static void Delete(TableView<Player> tablePlayer) {
        int index = tablePlayer.getSelectionModel().getSelectedIndex();
        int id = tablePlayer.getItems().get(index).getId();
        League league = tablePlayer.getItems().get(index).getLeague();
        Team team = tablePlayer.getItems().get(index).getTeam();

        Connection connection = null;
        PreparedStatement deletePlayerSquadStmt = null;
        PreparedStatement deleteStatsStmt = null;
        PreparedStatement deletePlayerStmt = null;

        try {

            connection = ConnectionUtil.getConnection();
            connection.setAutoCommit(false);

            String deletePlayerSquadSql = "DELETE FROM playersquad WHERE pid = ?";
            deletePlayerSquadStmt = connection.prepareStatement(deletePlayerSquadSql);
            deletePlayerSquadStmt.setInt(1, id);
            deletePlayerSquadStmt.executeUpdate();

            String deleteStatsSql = "DELETE FROM player_statistics WHERE player_id = ?";
            deleteStatsStmt = connection.prepareStatement(deleteStatsSql);
            deleteStatsStmt.setInt(1, id);
            deleteStatsStmt.executeUpdate();

            String deletePlayerSql = "DELETE FROM player WHERE id = ?";
            deletePlayerStmt = connection.prepareStatement(deletePlayerSql);
            deletePlayerStmt.setInt(1, id);
            deletePlayerStmt.executeUpdate();

            connection.commit();

            Player player = findById(id);

            System.out.println("File deleted successfully");

            CostumedAlerts.costumeAlert(Alert.AlertType.INFORMATION, "Manage Players", "Manage Players", "The player has been deleted!");

        } catch (SQLException e) {

            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            CostumedAlerts.costumeAlert(Alert.AlertType.ERROR, "Manage Players", "Manage Players", "The player failed to be deleted!");
            e.printStackTrace();

        } finally {

            if (deletePlayerSquadStmt != null) {
                try {
                    deletePlayerSquadStmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (deleteStatsStmt != null) {
                try {
                    deleteStatsStmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (deletePlayerStmt != null) {
                try {
                    deletePlayerStmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static int findIdByData(Player player) throws SQLException {
        String sql = "Select * from player where name=? and position =? and birthday= ? and image = ?";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,player.getName());
        statement.setString(2,player.getPosition());
        statement.setDate(3,player.getBirthday());
        statement.setString(4,player.getImage());
        ResultSet result = statement.executeQuery();
        if(result.next()){
            int id  = result.getInt("id");
            return id;
        }
        else {
            return -1;
        }

    }

    public static Player findById(int playerId) throws SQLException {
        String sql = "Select * from player where id = ?";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,playerId);
        ResultSet result = statement.executeQuery();
        while (result.next()){
            Player player = new Player(result.getInt("id"),
                    result.getString("name"),
                    result.getString("position"),
                    result.getDate("birthday"),
                    result.getString("image"));
            return player;
        }
        return null;
    }


    public static void fetchToTable(
            TableView<Player> tablePlayer,
            TableColumn<Player, Integer> colIdPlayer,
            TableColumn<Player, String> colNamePlayer,
            TableColumn<Player, Date> colPlayerBirthday,
            TableColumn<Player, League> colPlayerLeague,
            TableColumn<Player, String> colPlayerPos,
            TableColumn<Player, Team> colPlayerTeam
    ) throws SQLException {
        ObservableList<Player> players = FXCollections.observableArrayList();
        String sql = "Select p.id as id, p.name as playerName, p.birthday as birthday, " +
                "l.id as leagueId, p.position , t.id as teamId From player p " +
                "Inner join playersquad sp on sp.pid = p.id " +
                "Inner join squad s on sp.sid = s.id " +
                "Inner join team t on t.id = s.team_id " +
                "Inner join league_team lt on lt.team_id = t.id " +
                "Inner join league l on l.id = lt.league_id;";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet result  = statement.executeQuery();

        while (result.next()){
            dataToList(players,result);
        }
        objectToTable(tablePlayer,colIdPlayer,colNamePlayer,colPlayerBirthday,colPlayerPos,colPlayerTeam,colPlayerLeague,players);

    }

    public static void fetchToTableByLeague(TableView<Player> tablePlayer, TableColumn<Player, Integer> colIdPlayer,
                                            TableColumn<Player, String> colNamePlayer, TableColumn<Player, Date> colPlayerBirthday,
                                            TableColumn<Player, String> colPlayerPos,
                                            TableColumn<Player, Team> colPlayerTeam,TableColumn<Player, League> colPlayerLeague ,League league) throws SQLException {

        ObservableList<Player> players = FXCollections.observableArrayList();
        String sql = "Select p.id as id, p.name as playerName, p.birthday as birthday, " +
                "l.id as leagueId,  p.position , t.id as teamId From player p " +
                "Inner join playersquad sp on sp.pid = p.id " +
                "Inner join squad s on sp.sid = s.id " +
                "Inner join team t on t.id = s.team_id " +
                "Inner join league_team lt on lt.team_id = t.id " +
                "Inner join league l on l.id = lt.league_id " +
                "Where l.id = ?";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,league.getId());
        ResultSet result  = statement.executeQuery();

        while (result.next()){
            dataToList(players,result);

        }
        objectToTable(tablePlayer,colIdPlayer,colNamePlayer,colPlayerBirthday,colPlayerPos,colPlayerTeam,colPlayerLeague,players);
    }

    public static void fetchToTableByTeam(TableView<Player> tablePlayer, TableColumn<Player, Integer> colIdPlayer,
                                          TableColumn<Player, String> colNamePlayer, TableColumn<Player, Date> colPlayerBirthday, TableColumn<Player, String> colPlayerPos,
                                          TableColumn<Player, Team> colPlayerTeam,TableColumn<Player, League> colPlayerLeague ,Team team) throws SQLException{

        ObservableList<Player> players = FXCollections.observableArrayList();
        String sql = "Select p.id as id, p.name as playerName, p.birthday as birthday, " +
                "l.id as leagueId,p.position , t.id as teamId From player p " +
                "Inner join playersquad sp on sp.pid = p.id " +
                "Inner join squad s on sp.sid = s.id " +
                "Inner join team t on t.id = s.team_id " +
                "Inner join league_team lt on lt.team_id = t.id " +
                "Inner join league l on l.id = lt.league_id " +
                "Where t.id = ?";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,team.getId());
        ResultSet result  = statement.executeQuery();

        while (result.next()){
            dataToList(players,result);
        }
        objectToTable(tablePlayer,colIdPlayer,colNamePlayer,colPlayerBirthday,colPlayerPos,colPlayerTeam,colPlayerLeague,players);

    }
    static void dataToList(ObservableList<Player> players,ResultSet result) throws SQLException {
        Player player = new Player(1,null,null, null,null);
        player.setId(result.getInt("id"));
        player.setName(result.getString("playerName"));
        player.setPosition(result.getString("position"));
        player.setBirthday(result.getDate("birthday"));
        player.setTeam(TeamRepository.findById(result.getInt("teamId")));
        player.setLeague(LeagueRepository.findById(result.getInt("leagueId")));

        players.add(player);
    }
    static void objectToTable(TableView<Player> tablePlayer, TableColumn<Player, Integer> colIdPlayer,
                              TableColumn<Player, String> colNamePlayer, TableColumn<Player, Date> colPlayerBirthday,
                              TableColumn<Player, String> colPlayerPos,
                              TableColumn<Player, Team> colPlayerTeam,TableColumn<Player, League> colPlayerLeague ,ObservableList<Player> players){
        tablePlayer.setItems(players);
        colIdPlayer.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNamePlayer.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPlayerBirthday.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        colPlayerPos.setCellValueFactory(new PropertyValueFactory<>("position"));
        colPlayerTeam.setCellValueFactory(new PropertyValueFactory<>("team"));
        colPlayerLeague.setCellValueFactory(new PropertyValueFactory<>("league"));
    }

    public static ObservableList<Player> getAllPlayerByTeam(Team team) throws SQLException{
        ObservableList players = FXCollections.observableArrayList();
        String sql = "Select * from player p " +
                "inner join playersquad sp on sp.pid = p.id " +
                "inner join squad s on s.id = sp.sid " +
                "where s.team_id = ? ";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,team.getId());
        ResultSet result = statement.executeQuery();

        while (result.next()){
            Player player = new Player(
                    result.getInt("id"),
                    result.getString("name"),
                    result.getString("position"),
                    result.getDate("birthday"),
                    result.getString("image")

            );
            players.add(player);
        }
        return players;
    }


    public static ComboBox<Player> setValuesByTeam(ComboBox<Player> choseScorer, Team team) {
        try {
            for (Player player : getAllPlayerByTeam(team)) {
                choseScorer.getItems().add(player);
            }
            return choseScorer;
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }


    public static void update(Player player, TableView<Player> tablePlayer, TableColumn<Player, Integer> colIdPlayer,
                              TableColumn<Player, String> colNamePlayer, TableColumn<Player, Date> colPlayerBirthday,
                              TableColumn<Player, League> colPlayerLeague, TableColumn<Player, String> colPlayerPos,
                              TableColumn<Player, Team> colPlayerTeam) throws SQLException {
        String sql = "UPDATE player SET name=?, position=?, birthday=?, image=? WHERE id=?";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, player.getName());
        statement.setString(2, player.getPosition());
        statement.setDate(3, player.getBirthday());
        statement.setString(4, player.getImage());
        statement.setInt(5, player.getId());

        int rowsAffected = statement.executeUpdate();

        if (rowsAffected == 0) {
            CostumedAlerts.costumeAlert(Alert.AlertType.ERROR,
                    "Error",
                    "Player Update Error",
                    "Failed to update player with ID: " + player.getId());
            throw new SQLException("Failed to update player with ID: " + player.getId());
        } else {
            CostumedAlerts.costumeAlert(Alert.AlertType.INFORMATION,
                    "Success",
                    "Player Updated",
                    "Player with ID: " + player.getId() + " has been updated successfully.");
        }

        fetchToTable(tablePlayer, colIdPlayer, colNamePlayer, colPlayerBirthday, colPlayerLeague, colPlayerPos, colPlayerTeam);
    }






    public static void fetchToTablePaginaton(Integer pageIndex, int rowsPerPage, TableView<Player> tablePlayer, TableColumn<Player, Integer> colIdPlayer, TableColumn<Player, String> colNamePlayer, TableColumn<Player, Date> colPlayerBirthday, TableColumn<Player, League> colPlayerLeague, TableColumn<Player, String> colPlayerPos, TableColumn<Player, Team> colPlayerTeam) throws SQLException {
        ObservableList<Player> players = FXCollections.observableArrayList();
        int offset = pageIndex* rowsPerPage;
        String sql = "Select p.id as id, p.name as playerName, p.birthday as birthday, " +
                "l.id as leagueId, p.position , t.id as teamId From player p " +
                "Inner join playersquad sp on sp.pid = p.id " +
                "Inner join squad s on sp.sid = s.id " +
                "Inner join team t on t.id = s.team_id " +
                "Inner join league_team lt on lt.team_id = t.id " +
                "Inner join league l on l.id = lt.league_id " +
                "Limit ? Offset ?";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,rowsPerPage);
        statement.setInt(2,offset);
        ResultSet result  = statement.executeQuery();

        while (result.next()){
            dataToList(players,result);
        }
        objectToTable(tablePlayer,colIdPlayer,colNamePlayer,colPlayerBirthday,colPlayerPos,colPlayerTeam,colPlayerLeague,players);
    }
}
