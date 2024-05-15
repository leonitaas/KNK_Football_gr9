package models;

public class Standings {
    private int id;
    private Team team_id;
    private League league_id;
    private int matches_played;
    private int wins;
    private int losses;
    private int goals_for;
    private int goals_against;
    private int points;

    public Standings(int id, Team team_id, League league_id, int matches_played, int wins, int loses, int goals_for, int goals_against, int points) {
        this.id = id;
        this.team_id = team_id;
        this.league_id = league_id;
        this.matches_played = matches_played;
        this.wins = wins;
        this.losses = losses;
        this.goals_for = goals_for;
        this.goals_against = goals_against;
        this.points = points;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Team getTeam_id() {
        return team_id;
    }

    public void setTeam_id(Team team_id) {
        this.team_id = team_id;
    }

    public League getLeague_id() {
        return league_id;
    }

    public void setLeague_id(League league_id) {
        this.league_id = league_id;
    }

    public int getMatches_played() {
        return matches_played;
    }

    public void setMatches_played(int matches_played) {
        this.matches_played = matches_played;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLoses() {
        return losses;
    }

    public void setLoses(int loses) {
        this.losses = loses;
    }

    public int getGoals_for() {
        return goals_for;
    }

    public void setGoals_for(int goals_for) {
        this.goals_for = goals_for;
    }

    public int getGoals_against() {
        return goals_against;
    }

    public void setGoals_against(int goals_against) {
        this.goals_against = goals_against;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
