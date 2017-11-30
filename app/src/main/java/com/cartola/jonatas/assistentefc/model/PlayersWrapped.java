package com.cartola.jonatas.assistentefc.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jonatas on 29/10/2017.
 */

public class PlayersWrapped {

    @SerializedName("atletas")
    private List<PlayerScore> players;

    public List<PlayerScore> getPlayers() {
        return players;
    }
}
