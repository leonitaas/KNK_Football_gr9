package repository;

import models.*;
import service.ConnectionUtil;
import service.CostumedAlerts;
import service.ImagesToResources;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.sql.*;

public class TeamRepository {
    public static void insert(Team team, League league) throws SQLException {
        Connection connection = ConnectionUtil.getConnection();
        try {
            connection.setAutoCommit(false);

            String sqlTeam = "INSERT INTO team (name, stadium, team_logo) VALUES (?, ?, ?)";
            PreparedStatement statementTeam = connection.prepareStatement(sqlTeam, Statement.RETURN_GENERATED_KEYS);
            statementTeam.setString(1, team.getName());
            statementTeam.setString(2, team.getStadium());
            statementTeam.setString(3, team.getLogo());
            statementTeam.executeUpdate();

            ResultSet rsTeam = statementTeam.getGeneratedKeys();
            if (rsTeam.next()) {
                team.setId(rsTeam.getInt(1));
            }

            if (league != null) {
                String sqlLeagueTeam = "INSERT INTO league_team (league_id, team_id) VALUES (?, ?)";
                PreparedStatement statementLeagueTeam = connection.prepareStatement(sqlLeagueTeam);
                statementLeagueTeam.setInt(1, league.getId());
                statementLeagueTeam.setInt(2, team.getId());
                statementLeagueTeam.executeUpdate();
            }

            Squad squad= new Squad(1, team);
            SquadRepository.insertByTeam(squad);

            connection.commit();
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
    }


    public static void Delete(TableView<Team> teamTable) {
        int index = teamTable.getSelectionModel().getSelectedIndex();
        int id = teamTable.getItems().get(index).getId();
        League league = teamTable.getItems().get(index).getLeague();
        try {
            Team team = findById(id);
            String sql = "Delete From team where id = ?";
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,id);
            statement.executeUpdate();
            String path = ImagesToResources.getImagePath()+"\\"+league+"\\"+team.getName() +"\\"+team.getLogo();
            File file = new File(path);
            if(file.delete())
            {System.out.println("File deleted successfully");
            }
            CostumedAlerts.costumeAlert(Alert.AlertType.INFORMATION,"Manage Teams","Manage Teams","The team has been deleted!");
        } catch (SQLException e) {
            CostumedAlerts.costumeAlert(Alert.AlertType.ERROR,"Manage Teams","Manage Teams","The team failed to be deleted!");

            throw new RuntimeException(e);
        }
    }


    public static void fetchToTable(TableView<Team> table,
                                    TableColumn<Team, Integer> colId, TableColumn<Team,String > colName,
                                    TableColumn<Team,String> colStadium,
                                    TableColumn<Team, League> colLeague
    ) throws SQLException {
        ObservableList<Team> teams = FXCollections.observableArrayList();
        String sql = "Select t.id, t.name ,t.stadium, l.id as leagueId from Team t\n" +
                "inner join league_team lt on lt.team_id = t.id\n" +
                "inner join league l on l.id = lt.league_id;";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet result  = statement.executeQuery();

        while (result.next()){
            dataToList(teams,result);
        }
        objectToTable(table,colId,colName,colStadium,colLeague,teams);

    }
    public static void fetchToTableByLeague(TableView<Team> table,
                                            TableColumn<Team, Integer> colId, TableColumn<Team,String > colName, TableColumn<Team,String> colStadium,
                                            TableColumn<Team, League> colLeague, League league
    ) throws SQLException {

        ObservableList<Team> teams = FXCollections.observableArrayList();
        String sql = "Select t.id, t.name ,t.stadium , l.id as leagueId from Team t\n" +
                "inner join league_team lt on lt.team_id = t.id\n" +
                "inner join league l on l.id = lt.league_id Where l.id = ?;";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,league.getId());
        ResultSet result  = statement.executeQuery();

        while (result.next()){
            dataToList(teams,result);
        }
        objectToTable(table,colId,colName,colStadium,colLeague,teams);

    }
    static void dataToList(ObservableList<Team> teams , ResultSet result) throws SQLException {
        Team team = new Team(1,null,null,null);
        team.setId(result.getInt("id"));
        team.setName(result.getString("name"));
        team.setStadium(result.getString("stadium"));
        team.setLeague(LeagueRepository.findById(result.getInt("leagueId")));

        teams.add(team);
    }
    static void objectToTable(TableView<Team> table,
                              TableColumn<Team, Integer> colId, TableColumn<Team,String > colName, TableColumn<Team,String> colStadium,
                              TableColumn<Team, League> colLeague, ObservableList<Team> teams){
        table.setItems(teams);
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colStadium.setCellValueFactory(new PropertyValueFactory<>("stadium"));
        colLeague.setCellValueFactory(new PropertyValueFactory<>("league"));
    }

    public static Team findById(int teamId) throws SQLException {
        String sql = "Select * from team where id = ?";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,teamId);
        ResultSet result = statement.executeQuery();
        while (result.next()){
            Team team = new Team(result.getInt("id"),
                    result.getString("name"),
                    result.getString("stadium"),
                    result.getString("team_logo"));
            return team;
        }
        return null;
    }
    public static int findId(Team team) throws SQLException {
        String sql = "Select id from team where name=? and stadium =?  and team_logo=?";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,team.getName());
        statement.setString(2,team.getStadium());
        statement.setString(3,team.getLogo());
        ResultSet result = statement.executeQuery();
        if(result.next()){
            int id  = result.getInt("id");
            return id;
        }
        else {
            return -1;
        }
    }

    public static ObservableList<Team> getAllTeamsFromLeague(League league) throws SQLException {
        ObservableList teams = FXCollections.observableArrayList();
        String sql = "Select t.id , t.name, t.stadium, t.team_logo  from team t " +
                "inner join league_team lt on lt.team_id = t.id " +
                "inner join league l on l.id = lt.league_id " +
                "where l.id = ?";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,league.getId());
        ResultSet result = statement.executeQuery();

        while (result.next()){
            Team team = new Team(
                    result.getInt("id"),
                    result.getString("name"),
                    result.getString("stadium"),
                    result.getString("team_logo"));

            teams.add(team);
        }
        return teams;
    }
    public static ObservableList<Team> getAllTeamsFromLeagueExcept(League league,Team team) throws SQLException {
        ObservableList teams = FXCollections.observableArrayList();
        String sql = "Select t.id , t.name, t.stadium, t.team_logo  from team t " +
                "inner join league_team lt on lt.team_id = t.id " +
                "inner join league l on l.id = lt.league_id " +
                "where l.id = ? and t.id != ?";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,league.getId());
        statement.setInt(2,team.getId());
        ResultSet result = statement.executeQuery();

        while (result.next()){
            Team theTeam = new Team(
                    result.getInt("id"),
                    result.getString("name"),
                    result.getString("stadium"),
                    result.getString("team_logo"));

            teams.add(theTeam);
        }
        return teams;
    }

    public static ComboBox<Team> setValues(ComboBox<Team> teams, League league) {
        try {
            for (Team team : getAllTeamsFromLeague(league)) {
                teams.getItems().add(team);
            }
            return teams;
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static ComboBox<Team> setValuesAwayTeam(ComboBox<Team> teams, League league,Team team) {
        try {
            for (Team theTeam : getAllTeamsFromLeagueExcept(league,team)) {
                teams.getItems().add(theTeam);
            }
            return teams;
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }


}