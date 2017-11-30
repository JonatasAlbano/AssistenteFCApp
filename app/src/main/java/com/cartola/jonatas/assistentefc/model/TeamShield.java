package com.cartola.jonatas.assistentefc.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Giovana on 12/09/2017.
 */

public class TeamShield {

    @SerializedName("60x60")
    private String urlBigShield;

    @SerializedName("45x45")
    private String urlMediumShield;

    @SerializedName("30x30")
    private String urlSmallShield;

    public String getUrlBigShield() {
        return urlBigShield;
    }

    public void setUrlBigShield(String urlBigShield) {
        this.urlBigShield = urlBigShield;
    }

    public String getUrlMediumShield() {
        return urlMediumShield;
    }

    public void setUrlMediumShield(String urlMediumShield) {
        this.urlMediumShield = urlMediumShield;
    }

    public String getUrlSmallShield() {
        return urlSmallShield;
    }

    public void setUrlSmallShield(String urlSmallShield) {
        this.urlSmallShield = urlSmallShield;
    }
}
