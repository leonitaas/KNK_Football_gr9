package models;

public class League {
    private int id;
    private String name;
    private String league_logo;
    private int numberOfTeams;

    public League(int id, String name, String league_logo) {
        this.id = id;
        this.name = name;
        this.league_logo = league_logo;
    }

    public League(int id, String name, String league_logo, int numberOfTeams) {
        this.id = id;
        this.name = name;
        this.league_logo = league_logo;
        this.numberOfTeams = numberOfTeams;
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

    public String getLeague_logo() {
        return league_logo;
    }

    public void setLeague_logo(String league_logo) {
        this.league_logo = league_logo;
    }

    public int getNumberOfTeams() {
        return numberOfTeams;
    }

    public void setNumberOfTeams(int numberOfTeams) {
        this.numberOfTeams = numberOfTeams;
    }
}
