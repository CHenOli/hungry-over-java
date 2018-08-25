package com.tcc.carloshenrique.hungryover.models;

import com.squareup.moshi.Json;

public class RestaurantModel {
    @Json(name = "id")
    private Integer id;
    @Json(name = "nome")
    private String nome;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RestaurantModel withId(Integer id) {
        this.id = id;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public RestaurantModel withNome(String nome) {
        this.nome = nome;
        return this;
    }
}
