package com.cartola.jonatas.assistentefc.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by jonatas on 22/08/2017.
 */

public class Match {

    @SerializedName("partida_id")
    private long id;

    @SerializedName("local")
    private String local;

    @SerializedName("clube_casa_id")
    private long houseTeamID;

    @SerializedName("clube_visitante_id")
    private long visitantTeamID;

    @SerializedName("placar_oficial_mandante")
    private int scoreLocalTeam;

    @SerializedName("placar_oficial_visitante")
    private int scoreVisitantTeam;

    private Team houseTeam;

    private Team visitantTeam;

    @SerializedName("partida_data")
    private String matchDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public long getHouseTeamID() {
        return houseTeamID;
    }

    public void setHouseTeamID(long houseTeamID) {
        this.houseTeamID = houseTeamID;
    }

    public long getVisitantTeamID() {
        return visitantTeamID;
    }

    public void setVisitantTeamID(long visitantTeamID) {
        this.visitantTeamID = visitantTeamID;
    }

    public int getScoreLocalTeam() {
        return scoreLocalTeam;
    }

    public void setScoreLocalTeam(int scoreLocalTeam) {
        this.scoreLocalTeam = scoreLocalTeam;
    }

    public int getScoreVisitantTeam() {
        return scoreVisitantTeam;
    }

    public void setScoreVisitantTeam(int scoreVisitantTeam) {
        this.scoreVisitantTeam = scoreVisitantTeam;
    }

    public Team getHouseTeam() {
        return houseTeam;
    }

    public void setHouseTeam(Team houseTeam) {
        this.houseTeam = houseTeam;
    }

    public Team getVisitantTeam() {
        return visitantTeam;
    }

    public void setVisitantTeam(Team visitantTeam) {
        this.visitantTeam = visitantTeam;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }
}
