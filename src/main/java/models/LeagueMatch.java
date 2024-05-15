package models;



public class LeagueMatch {
    private League league_id;
    private Match match_id;
    public LeagueMatch(League league_id, Match match_id) {
        this.league_id = league_id;
        this.match_id = match_id;
    }

    public League getLeague_id() {
        return league_id;
    }

    public void setLeague_id(League league_id) {
        this.league_id = league_id;
    }

    public Match getMatch_id() {
        return match_id;
    }

    public void setMatch_id(Match match_id) {
        this.match_id = match_id;
    }
}
