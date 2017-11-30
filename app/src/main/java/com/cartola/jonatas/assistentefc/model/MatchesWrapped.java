package com.cartola.jonatas.assistentefc.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jonatas on 22/08/2017.
 */

public class MatchesWrapped {

    @SerializedName("partidas")
    private List<Match> matches;

    public List<Match> getMatches() {
        return matches;
    }
}
