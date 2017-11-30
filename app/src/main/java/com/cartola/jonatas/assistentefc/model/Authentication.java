package com.cartola.jonatas.assistentefc.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Giovana on 13/11/2017.
 */

public class Authentication {

    @SerializedName("id")
    private String id;
    @SerializedName("glbId")
    private String glbId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGlbId() {
        return glbId;
    }

    public void setGlbId(String glbId) {
        this.glbId = glbId;
    }
}
