package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import models.*;
import repository.PlayerRepository;

public class GoalController {

    @FXML
    private RadioButton Penalty;

    @FXML
    private ComboBox<Player> choseAssister;

    @FXML
    private ComboBox<Player> choseScorer;

    @FXML
    private ToggleGroup goalTypeGroup;

    @FXML
    private TextField minute;

    @FXML
    private RadioButton ownGoal;




    public void setData(Team team){
        choseScorer.getItems().clear();
        choseAssister.getItems().clear();
        PlayerRepository.setValuesByTeam(choseScorer,team);
        PlayerRepository.setValuesByTeam(choseAssister,team);
    }

    public RadioButton getOwnGoal() {
        return ownGoal;
    }

    public void setDataOwnGoal(Team homeTeam, Team awayTeam) {
        choseScorer.getItems().clear();
        choseAssister.getItems().clear();
        PlayerRepository.setValuesByTeam(choseScorer,awayTeam);
        PlayerRepository.setValuesByTeam(choseAssister,homeTeam);


    }
    public Goals getGoal(ComboBox<Team> team){
        Goals goal = new Goals(1,null,null,null,null,null,null,null);
        goal.setMinute(minute.getText());
        goal.setScored(choseScorer.getValue());
        goal.setAssisted(choseAssister.getValue());
        goal.setOwngoal(ownGoal.isSelected());
        goal.setPenalty(Penalty.isSelected());
        goal.setTeam(team.getValue());

        return goal;
    }

}
