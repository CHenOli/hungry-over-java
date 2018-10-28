package com.tcc.carloshenrique.hungryover.models;

import com.squareup.moshi.Json;

public class ItemModel {
    @Json(name = "id")
    private Integer id;
    @Json(name = "nome")
    private String nome;
    @Json(name = "urlImagem")
    private String urlImage;
    @Json(name = "caminhoImagem")
    private String pathImage;
    @Json(name = "valor")
    private Float valor;
    
    public ItemModel() {

    }

    public ItemModel(Integer id, String nome, String urlImage, Float valor) {
        super();
        this.id = id;
        this.nome = nome;
        this.urlImage = urlImage;
        this.valor = valor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ItemModel withId(Integer id) {
        this.id = id;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ItemModel withNome(String nome) {
        this.nome = nome;
        return this;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public ItemModel withUrlImage(String urlImage) {
        this.urlImage = urlImage;
        return this;
    }

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

    public ItemModel withPathImage(String pathImage) {
        this.pathImage = pathImage;
        return this;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public ItemModel withValor(Float valor) {
        this.valor = valor;
        return this;
    }
}
