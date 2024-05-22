package repository;

import models.League;
import service.ConnectionUtil;
import service.CostumedAlerts;
import service.ImagesToResources;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LeagueRepository {

    public static void insert(League league) throws SQLException {
        String sql = "INSERT INTO league (name, league_logo) VALUES (?, ?)";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, league.getName());
        statement.setString(2, league.getLeague_logo());
        statement.executeUpdate();
    }

    public static void Delete(TableView<League> table) {
        int index = table.getSelectionModel().getSelectedIndex();
        int id = table.getItems().get(index).getId();

        try {
            League league = findById(id);
            String sql = "DELETE FROM league WHERE id = ?";
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
            String path = ImagesToResources.getImagePath() + "\\" + league.getName() + "\\" + league.getLeague_logo();
            File file = new File(path);
            if (file.delete()) {
                System.out.println("File deleted successfully");
            }
            CostumedAlerts.costumeAlert(Alert.AlertType.INFORMATION, "Manage Leagues", "Manage Leagues", "The league has been deleted!");
        } catch (SQLException e) {
            CostumedAlerts.costumeAlert(Alert.AlertType.ERROR, "Manage Leagues", "Manage Leagues", "The league failed to be deleted!");

            throw new RuntimeException(e);
        }
    }

    public static void Update(TableView<League> table, League league, Path fileSource) {
        int index = table.getSelectionModel().getSelectedIndex();
        int id = table.getItems().get(index).getId();
        String name = table.getItems().get(index).getName();
        String image = table.getItems().get(index).getLeague_logo();

        try {
            String sql = "UPDATE league SET name = ?, league_logo = ? WHERE id = ?";
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, league.getName());
            statement.setString(2, league.getLeague_logo());
            statement.setInt(3, id);

            if (!league.getName().equals(name) || !league.getLeague_logo().equals(image)) {
                statement.executeUpdate();
                CostumedAlerts.costumeAlert(Alert.AlertType.INFORMATION, "Manage League", "Manage League", "The League Updated Successfully");
                try {
                    ImagesToResources.imageToResources(league.getName(), league.getLeague_logo(), fileSource);
                    File oldImageFile = new File(ImagesToResources.getImagePath() + "\\" + name + "\\" + image);
                    oldImageFile.delete();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            CostumedAlerts.costumeAlert(Alert.AlertType.ERROR, "Manage League", "Manage League", "The League Failed To Be Updated");

            throw new RuntimeException(e);
        }
    }

    public static void fetchToTable(TableView<League> table,
                                    TableColumn<League, Integer> colid, TableColumn<League, String> colname, TableColumn<League, Integer> colNumberOfTeams) throws SQLException {
        ObservableList<League> leagues = FXCollections.observableArrayList();
        String sql = "SELECT l.id, l.name, COUNT(t.id) AS team_count " +
                "FROM League l " +
                "LEFT JOIN League_Team lt ON l.id = lt.league_id " +
                "LEFT JOIN Team t ON lt.team_id = t.id " +
                "GROUP BY l.id, l.name";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet result = statement.executeQuery();

        while (result.next()) {
            League league = new League(result.getInt("id"), result.getString("name"), result.getString("league_logo"));
            league.setNumberOfTeams(result.getInt("team_count"));
            leagues.add(league);
        }
        table.setItems(leagues);
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colname.setCellValueFactory(new PropertyValueFactory<>("name"));
        colNumberOfTeams.setCellValueFactory(new PropertyValueFactory<>("numberOfTeams"));
    }

    public static ObservableList<League> getAllLeagues() throws SQLException {
        ObservableList<League> leagues = FXCollections.observableArrayList();
        String sql = "SELECT id, name, league_logo FROM league";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet result = statement.executeQuery();

        while (result.next()) {
            League league = new League(result.getInt("id"), result.getString("name"), result.getString("league_logo"));
            leagues.add(league);
        }
        return leagues;
    }

    public static League findById(int leagueId) throws SQLException {
        String sql = "SELECT id, name, league_logo FROM league WHERE id = ?";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, leagueId);
        ResultSet result = statement.executeQuery();
        if (result.next()) {
            return new League(result.getInt("id"), result.getString("name"), result.getString("league_logo"));
        }
        return null;
    }

    public static ComboBox<League> setValues(ComboBox<League> leagues) {
        try {
            for (League league : getAllLeagues()) {
                leagues.getItems().add(league);
            }
            return leagues;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static League findId(League league) throws SQLException {
        String sql = "SELECT id FROM league WHERE name = ?";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, league.getName());
        ResultSet result = statement.executeQuery();
        if (result.next()) {
            return new League(result.getInt("id"), league.getName(), league.getLeague_logo());
        }
        return null;
    }
}
