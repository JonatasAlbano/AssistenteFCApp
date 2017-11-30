package com.cartola.jonatas.assistentefc.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Giovana on 24/08/2017.
 */

public class Team {

    @SerializedName("nome")
    private String nome;

    @SerializedName("escudos")
    private TeamShield teamShield;

    public String getNome() {
        return nome;
    }

    public TeamShield getTeamShield() {
        return teamShield;
    }
}
