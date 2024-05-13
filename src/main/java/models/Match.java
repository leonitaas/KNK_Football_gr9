package models;

import java.util.Date;

public class Match {
    private int id;
    private Team hometeam_id;
    private Team awayteam_id;
    private Date match_date;

    public Match(int id, Team hometeam_id, Team awayteam_id, Date match_date) {
        this.id = id;
        this.hometeam_id = hometeam_id;
        this.awayteam_id = awayteam_id;
        this.match_date = match_date;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Team getHometeam_id() {
        return hometeam_id;
    }
    public void setHometeam_id(Team hometeam_id) {
        this.hometeam_id = hometeam_id;
    }

    public Team getAwayteam_id() {
        return awayteam_id;
    }
    public void setAwayteam_id(Team awayteam_id) {
        this.awayteam_id = awayteam_id;
    }
    public Date getMatch_date() {
        return match_date;
    }
    public void setMatch_date(Date match_date) {
        this.match_date = match_date;
    }
}
