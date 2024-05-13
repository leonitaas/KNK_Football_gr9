package models;

public class LeagueTeam {
    private League league_id;
    private Team team_id;

    public LeagueTeam(League league_id, Team team_id) {
        this.league_id=league_id;
        this.team_id=team_id;
    }

    public League getLeague_id() {
        return league_id;
    }
    public void setLeague_id(League league_id) {
        this.league_id = league_id;
    }
    public Team getTeam_id() {
        return team_id;
    }
    public void setTeam_id(Team team_id) {
        this.team_id = team_id;
    }
}
