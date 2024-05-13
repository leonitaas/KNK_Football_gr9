package models;

public class MatchStatistics {
    private int id;
    private Match match_id;
    private int home_team_goals;
    private int away_team_goals;
    private double possession_home;
    private double possession_away;
    private int shots_home;
    private int shots_away;
    private int corners_home;
    private int corners_away;
    private int fouls_home;
    private int fouls_away;
    private int yellow_cards_home;
    private int yellow_cards_away;
    private int red_cards_home;
    private int red_cards_away;

    public MatchStatistics(int id, Match match_id, int home_team_goals, int away_team_goals, double possession_home, double possession_away, int shots_home, int shots_away, int corners_home, int corners_away, int fouls_home, int fouls_away, int yellow_cards_home, int yellow_cards_away, int red_cards_home, int red_cards_away) {
        this.id= id;
        this.match_id = match_id;
        this.home_team_goals = home_team_goals;
        this.away_team_goals = away_team_goals;
        this.possession_home = possession_home;
        this.possession_away = possession_away;
        this.shots_home = shots_home;
        this.shots_away = shots_away;
        this.corners_home = corners_home;
        this.corners_away = corners_away;
        this.fouls_home = fouls_home;
        this.fouls_away = fouls_away;
        this.yellow_cards_home= yellow_cards_home;
        this.yellow_cards_away= yellow_cards_away;
        this.red_cards_home= red_cards_home;
        this.red_cards_away= red_cards_away;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Match getMatch_id() {
        return match_id;
    }
    public void setMatch_id(Match match_id) {
        this.match_id = match_id;
    }
    public int getHome_team_goals() {
        return home_team_goals;
    }
    public void setHome_team_goals(int home_team_goals) {
        this.home_team_goals = home_team_goals;
    }
    public int getAway_team_goals() {
        return away_team_goals;
    }
    public void setAway_team_goals(int away_team_goals) {
        this.away_team_goals = away_team_goals;
    }
    public double getPossession_home() {
        return possession_home;
    }
    public void setPossession_home(double possession_home) {
        this.possession_home = possession_home;
    }
    public double getPossession_away() {
        return possession_away;
    }
    public void setPossession_away(double possession_away) {
        this.possession_away = possession_away;
    }
    public int getShots_home() {
        return shots_home;
    }
    public void setShots_home(int shots_home) {
        this.shots_home = shots_home;
    }
    public int getShots_away() {
        return shots_away;
    }
    public void setShots_away(int shots_away) {
        this.shots_away = shots_away;
    }
    public int getCorners_home() {
        return corners_home;
    }
    public void setCorners_home(int corners_home) {
        this.corners_home = corners_home;
    }
    public int getCorners_away() {
        return corners_away;
    }
    public void setCorners_away(int corners_away) {
        this.corners_away = corners_away;
    }
    public int getFouls_home() {
        return fouls_home;
    }
    public void setFouls_home(int fouls_home) {
        this.fouls_home = fouls_home;
    }
    public int getFouls_away() {
        return fouls_away;
    }
    public void setFouls_away(int fouls_away) {
        this.fouls_away = fouls_away;
    }
    public int getYellow_cards_home() {
        return yellow_cards_home;
    }
    public void setYellow_cards_home(int yellow_cards_home) {
        this.yellow_cards_home = yellow_cards_home;
    }
    public int getYellow_cards_away() {
        return yellow_cards_away;
    }
    public void setYellow_cards_away(int yellow_cards_away) {
        this.yellow_cards_away = yellow_cards_away;
    }
    public int getRed_cards_home() {
        return red_cards_home;
    }
    public void setRed_cards_home(int red_cards_home) {
        this.red_cards_home = red_cards_home;
    }
    public int getRed_cards_away() {
        return red_cards_away;
    }
    public void setRed_cards_away(int red_cards_away) {
        this.red_cards_away = red_cards_away;
    }
}
