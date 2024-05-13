package models;

public class League {
    private int id;
    private String name;
    // private String league_logo

    public League(int id, String name) {
        this.id = id;
        this.name = name;
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
    public String toString() {
        return this.name;
    }

    private int noOfteams;
    public int getNoOfteams() {
        return noOfteams;
    }
    public void setNumberOfTeams(int noOfteams) {
        this.noOfteams = noOfteams;
    }

}
