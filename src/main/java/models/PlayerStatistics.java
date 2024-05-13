package models;

public class PlayerStatistics {
    private int id;
    private Player player_id;
    private int goal;
    private int assist;
    private int minutes_played;

    public PlayerStatistics(int id, Player player_id, int goal, int assist, int minutes_played ) {
        this.id = id;
        this.player_id = player_id;
        this.goal = goal;
        this.assist = assist;
        this.minutes_played = minutes_played;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Player getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(Player player_id) {
        this.player_id = player_id;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public int getAssist() {
        return assist;
    }

    public void setAssist(int assist) {
        this.assist = assist;
    }
    public int getMinutes_played() {
        return minutes_played;
    }
    public void setMinutes_played(int minutes_played) {
        this.minutes_played = minutes_played;
    }
}
