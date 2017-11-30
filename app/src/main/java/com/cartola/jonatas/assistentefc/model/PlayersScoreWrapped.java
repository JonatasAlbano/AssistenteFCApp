package com.cartola.jonatas.assistentefc.model;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Created by Giovana on 13/09/2017.
 */

public class PlayersScoreWrapped {

    @SerializedName("atletas")
    private Map<String, PlayerScore> players;

    public Map<String, PlayerScore> getPlayers() {
        return players;
    }

    public void setPlayers(Map<String, PlayerScore> players) {
        this.players = players;
    }
}
