package com.cartola.jonatas.assistentefc.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jonatas on 13/11/2017.
 */

public class PayloadWrapped {

    @SerializedName("payload")
    private Payload payload;

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }
}
