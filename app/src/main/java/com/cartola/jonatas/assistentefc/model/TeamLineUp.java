package com.cartola.jonatas.assistentefc.model;

import java.util.List;

/**
 * Created by Giovana on 22/11/2017.
 */

public class TeamLineUp {

    private int esquema;
    private List<Long> atleta;

    public int getEsquema() {
        return esquema;
    }

    public void setEsquema(int esquema) {
        this.esquema = esquema;
    }

    public List<Long> getAtleta() {
        return atleta;
    }

    public void setAtleta(List<Long> atleta) {
        this.atleta = atleta;
    }
}
