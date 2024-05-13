package models;

public class SquadPlayer {
    private Squad squad_id;
    private Player player_id;

    public SquadPlayer(Squad squad_id, Player player_id) {
        this.squad_id = squad_id;
        this.player_id = player_id;
    }

    public Squad getSquad_id() {
        return squad_id;
    }

    public void setSquad_id(Squad squad_id) {
        this.squad_id = squad_id;
    }

    public Player getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(Player player_id) {
        this.player_id = player_id;
    }
}
