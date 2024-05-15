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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TeamRepository {
    public static void insert(Team team, League league) throws SQLException {
        String sql = "INSERT INTO team (name,stadium,logo) " +
                "Values (?,?,?)";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,team.getName());
        statement.setString(2,team.getStadium());
       // statement.setString(3,team.getYear());
        statement.setString(3,team.getLogo());
        statement.executeUpdate();

        team.setId(TeamRepository.findId(team));
        LeagueTeam league_team = new LeagueTeam(league,team);
        LeagueTeamRepository.insert(league_team);
        Squad squad = new Squad(1,team);
        SquadRepository.insertByTeam(squad);
        Standings standing = new Standings(1,team,league,0,0,0,0,0,0);
        StandingsRepository.insertByTeam(standing);
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
            {System.out.println("File deleted successfuly");
            }
            CostumedAlerts.costumeAlert(Alert.AlertType.INFORMATION,"Manage Teams","Manage Teams","The team has been deleted!");
        } catch (SQLException e) {
            CostumedAlerts.costumeAlert(Alert.AlertType.ERROR,"Manage Teams","Manage Teams","The team failed to be deleted!");

            throw new RuntimeException(e);
        }
    }


    public static void fetchToTable(TableView<Team> table,
                                    TableColumn<Team, Integer> colId, TableColumn<Team,String > colName,
                                    TableColumn<Team,String> colStadium, TableColumn<Team, String> colYear,
                                    TableColumn<Team, League> colLeague
    ) throws SQLException {
        ObservableList<Team> teams = FXCollections.observableArrayList();
        String sql = "Select t.id, t.name ,t.stadium, l.id as leagueId from Team t\n" +
                "inner join league_teams lt on lt.team_id = t.id\n" +
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
                "inner join league_teams lt on lt.team_id = t.id\n" +
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
                    result.getString("logo"));
            return team;
        }
        return null;
    }
    public static int findId(Team team) throws SQLException {
        String sql = "Select id from team where name=? and stadium =?  and logo=?";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,team.getName());
        statement.setString(2,team.getStadium());
        //statement.setString(3,team.getYear());
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
        String sql = "Select t.id , t.name, t.stadium, t.logo  from team t " +
                "inner join league_teams lt on lt.team_id = t.id " +
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
                    result.getString("logo"));

            teams.add(team);
        }
        return teams;
    }
    public static ObservableList<Team> getAllTeamsFromLeagueExcept(League league,Team team) throws SQLException {
        ObservableList teams = FXCollections.observableArrayList();
        String sql = "Select t.id , t.name, t.stadium, t.logo  from team t " +
                "inner join league_teams lt on lt.team_id = t.id " +
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
                    result.getString("logo"));

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
