package com.tcc.carloshenrique.hungryover.models;

import com.squareup.moshi.Json;

public class CategoryModel {
    @Json(name = "id")
    private Integer id;
    @Json(name = "nome")
    private String name;
    @Json(name = "urlImagem")
    private String image;

    public CategoryModel() {

    }

    public CategoryModel(int id, String name, String image) {
        super();
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CategoryModel withId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryModel withName(String name) {
        this.name = name;
        return this;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public CategoryModel withImage(String image) {
        this.image = image;
        return this;
    }
}
