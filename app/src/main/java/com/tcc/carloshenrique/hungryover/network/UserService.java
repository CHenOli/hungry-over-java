package com.tcc.carloshenrique.hungryover.network;

import com.tcc.carloshenrique.hungryover.models.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserService {
    @GET("clientes")
    Call<List<UserModel>> all();


    @GET("id")
    Call<UserModel> getID(@Query("id") int id);
    @GET("nome")
    Call<UserModel> getName(@Query("nome") String name);
    @GET("email")
    Call<UserModel> getEmail(@Query("email") String email);
    @GET("senha")
    Call<UserModel> getPassword(@Query("senha") String password);
}
