package com.cartola.jonatas.assistentefc.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jonatas on 22/08/2017.
 */

public class MarketStatus {

    @SerializedName("rodada_atual")
    private int actualRound;

    @SerializedName("status_mercado")
    private int statusMarket;

    public int getActualRound() {
        return actualRound;
    }

    public int getStatusMarket() {
        return statusMarket;
    }
}
