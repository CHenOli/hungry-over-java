package com.tcc.carloshenrique.hungryover.models;

import android.content.res.Resources;

import com.squareup.moshi.Json;
import com.tcc.carloshenrique.hungryover.R;
import com.tcc.carloshenrique.hungryover.network.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

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

    private UserModel user;

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

    public UserModel getUserInformation(int userId)
    {
        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Resources.getSystem().getString(R.string.url))
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        final UserService clientService = retrofit.create(UserService.class);

        Call<UserModel> call = clientService.getInfo(userId);
        call.enqueue(new Callback<UserModel>() {

            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                user = response.body();
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

            }
        });
        */
        return user;
    }
}