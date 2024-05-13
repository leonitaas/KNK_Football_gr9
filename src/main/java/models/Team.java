package models;

public class Team {
    private int id;
    private String name;
    private String stadium;
    //private League league;

    private Team (int id, String name, String stadium) {
        this.id=id;
        this.name=name;
        this.stadium=stadium;
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

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }
//    public League getLeague() {
//        return league;
//    }
//
//    public void setLeague(League league) {
//        this.league = league;
//    }

    @Override
    public String toString() {
        return this.name;
    }
}
