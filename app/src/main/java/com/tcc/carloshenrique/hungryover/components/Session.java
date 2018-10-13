package com.tcc.carloshenrique.hungryover.components;

import com.squareup.moshi.Json;

public class Session {

    @Json(name = "idMesa")
    private Integer idMesa;
    @Json(name = "idUser")
    private Integer idUser;

    public Session() {
    }
}