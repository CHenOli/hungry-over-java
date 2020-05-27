package com.tcc.carloshenrique.hungryover.network;

import com.tcc.carloshenrique.hungryover.models.UserModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserService {
    @GET("/clientes")
    Call<List<UserModel>> all();

    @GET("/clientes/buscar/{userId}")
    Call<UserModel> getUser(@Path(value = "userId", encoded = true) int userId);

    @POST("/clientes")
    Call<UserModel> create(@Body UserModel user);

    @POST("/clientes/login")
    Call<UserModel> login(@Body UserModel user);
}