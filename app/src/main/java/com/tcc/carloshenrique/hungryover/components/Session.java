package com.tcc.carloshenrique.hungryover.components;

import com.squareup.moshi.Json;

public class Session {

    @Json(name = "idMesa")
    private Integer idMesa;
    @Json(name = "idMesa")
    private Integer idUser;

    public Session() {
    }

    public Session(int idMesa, int idUser) {
        super();
        this.idMesa = idMesa;
        this.idUser = idUser;
    }

    public Integer getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(Integer idMesa) {
        this.idMesa = idMesa;
    }

    public Session withIdMesa(Integer idMesa) {
        this.idMesa = idMesa;
        return this;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Session withIdUser(int idUser) {
        this.idUser = idUser;
        return this;
    }
}