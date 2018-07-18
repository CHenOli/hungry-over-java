package com.tcc.carloshenrique.hungryover.network;

import com.tcc.carloshenrique.hungryover.models.UserModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService {
    @GET("/clientes")
    Call<List<UserModel>> all();

    @GET("/clientes/id")
    Call<UserModel> getID(@Query("id") int id);
    @GET("/clientes/nome")
    Call<UserModel> getName(@Query("nome") String name);
    @GET("/clientes/email")
    Call<UserModel> getEmail(@Query("email") String email);
    @GET("/clientes/senha")
    Call<UserModel> getPassword(@Query("senha") String password);

    @POST("/clientes")
    Call<UserModel> create(@Body UserModel user);
}