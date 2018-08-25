package com.tcc.carloshenrique.hungryover.models;

import com.squareup.moshi.Json;

public class UserModel {

    @Json(name = "id")
    private Integer id;
    @Json(name = "nome")
    private String name;
    @Json(name = "email")
    private String email;
    @Json(name = "senha")
    private String password;
    @Json(name = "url")
    private String image;

    public UserModel() {

    }

    public UserModel(String name, String email, String password, String image) {
        super();
        this.name = name;
        this.email = email;
        this.password = password;
        this.image = image;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserModel withName(String name) {
        this.name = name;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserModel withPassword(String password) {
        this.password = password;
        return this;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String password) {
        this.image = image;
    }

    public UserModel withImage (String image) {
        this.image = image;
        return this;
    }
}