package com.tcc.carloshenrique.hungryover.network;

import com.tcc.carloshenrique.hungryover.components.Session;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface SessionService {
    @GET("/getSession")
    Call<List<Session>> getSessions();

    @POST("/sessao/{idUser}/{idTable}")
    Call<Session> Create(@Field("idUser") int idUser,
                         @Field("idTable") int idTable);
}