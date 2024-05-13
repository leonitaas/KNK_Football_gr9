package models;

public class Goals {
    private int id;
    private Match game;
    private Team team;
    private Player assisted;
    private Player scored;
    private  String minute;
    private Boolean penalty;
    private Boolean owngoal;

    public Goals(int id, Match game,  Team team,  Player scored, Player assisted, String minute, Boolean owngoal, Boolean penalty) {
        this.id = id;
        this.game = game;
        this.team = team;
        this.scored = scored;
        this.assisted = assisted;
        this.minute = minute;
        this.penalty = penalty;
        this.owngoal = owngoal;
    }
    public Match getGame() {
        return game;
    }

    public void setGame(Match game) {
        this.game = game;
    }


    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
    public Player getAssisted() {
        return assisted;
    }

    public void setAssisted(Player assisted) {
        this.assisted = assisted;
    }

    public Player getScored() {
        return scored;
    }

    public void setScored(Player scored) {
        this.scored = scored;
    }
    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }
    public Boolean getPenalty() {
        return penalty;
    }

    public void setPenalty(Boolean penalty) {
        this.penalty = penalty;
    }
    public Boolean getOwngoal() {
        return owngoal;
    }

    public void setOwngoal(Boolean owngoal) {

    }
}
