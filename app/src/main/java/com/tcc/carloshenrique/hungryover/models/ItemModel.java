package com.tcc.carloshenrique.hungryover.models;

import com.squareup.moshi.Json;

public class ItemModel {
    @Json(name = "id")
    private Integer id;
    @Json(name = "nome")
    private String nome;
    @Json(name = "urlImagem")
    private String urlImagem;
    @Json(name = "valor")
    private Float valor;
    
    public ItemModel() {
        
    }

    public ItemModel(Integer id, String nome, String urlImagem, Float valor) {
        super();
        this.id = id;
        this.nome = nome;
        this.urlImagem = urlImagem;
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

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public ItemModel withUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
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