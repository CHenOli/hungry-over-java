package com.tcc.carloshenrique.hungryover.models;

import com.squareup.moshi.Json;

public class UserModel {

    @Json(name = "id")
    private Integer id;
    @Json(name = "nome")
    private String nome;
    @Json(name = "email")
    private String email;
    @Json(name = "senha")
    private String senha;

    public UserModel() {
    }

    public UserModel(String nome, String email, String senha) {
        super();
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserModel withId(Integer id) {
        this.id = id;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public UserModel withNome(String nome) {
        this.nome = nome;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserModel withEmail(String email) {
        this.email = email;
        return this;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public UserModel withSenha(String senha) {
        this.senha = senha;
        return this;
    }
}