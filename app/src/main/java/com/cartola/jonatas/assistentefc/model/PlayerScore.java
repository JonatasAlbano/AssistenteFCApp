package com.cartola.jonatas.assistentefc.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Giovana on 13/09/2017.
 */

public class PlayerScore implements  Comparable<PlayerScore>, Serializable{

    @SerializedName("atleta_id")
    private long id;

    @SerializedName("apelido")
    private String nickName;

    @SerializedName("pontuacao")
    private double partialScore;

    @SerializedName("clube_id")
    private long teamID;

    @SerializedName("posicao_id")
    private int position;

    @SerializedName("foto")
    private String foto;

    @SerializedName("status_id")
    private int status;

    @SerializedName("media_num")
    private double media;

    @SerializedName("preco_num")
    private double price;

    public long getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public String getNickName() {
        return nickName;
    }

    public double getPartialScore() {
        return partialScore;
    }

    public long getTeamID() {
        return teamID;
    }

    public int getPosition() {
        return position;
    }

    public String getFoto() {
        return foto;
    }

    public double getMedia() {
        return media;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public int compareTo(@NonNull PlayerScore comparePlayer) {
        if(this.getMedia() > comparePlayer.getMedia())
            return -1;
        else if(this.getMedia() < comparePlayer.getMedia())
            return 1;
        else
            return 0;
    }
}
