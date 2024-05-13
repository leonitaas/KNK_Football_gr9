package models;

import java.util.Date;

public class Season {
    private int id;
    private String name;
    private Date start;
    private Date end;
    private League league_id;

    public Season(int id, String name, Date start, Date end, League league_id) {
        this.id = id;
        this.name = name;
        this.start = start;
        this.end = end;
        this.league_id = league_id;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public League getLeague_id() {
        return league_id;
    }

    public void setLeague_id(League league_id) {
        this.league_id = league_id;
    }
}
