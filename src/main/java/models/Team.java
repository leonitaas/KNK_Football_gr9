package models;

public class Team {
    private int id;
    private String name;
    private String stadium;
    private League league;

    private String logo;

    public Team(int id, String name, String stadium,String logo) {
        this.id=id;
        this.name=name;
        this.stadium=stadium;
        this.logo=logo;
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

   public League getLeague() {
       return league;}


       public void setLeague(League league) {
        this.league = league;
         }

       @Override
       public String toString () {
           return this.name;
       }


    public String getLogo() {
        return null;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
