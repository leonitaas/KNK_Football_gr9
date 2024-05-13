package models;

public class Squad {
    private int id;
    private Team team_id;

    public Squad(int id, Team team_id) {
        this.id = id;
        this.team_id = team_id;
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
}
