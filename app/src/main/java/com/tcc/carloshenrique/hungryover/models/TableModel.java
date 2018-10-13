package com.tcc.carloshenrique.hungryover.models;

import com.squareup.moshi.Json;

public class TableModel {
    @Json(name = "idMesa")
    private Integer Id;
    @Json(name = "capacidade")
    private Integer capacity;
}