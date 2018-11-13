package com.tcc.carloshenrique.hungryover.network;

import com.tcc.carloshenrique.hungryover.components.Session;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SessionService {
    @POST("/sessao/{idUser}/{idTable}")
    Call<Session> Create(@Path("idUser") int idUser,
                         @Path("idTable") int idTable);

    @POST("/sessao/fechar/{idSessao}/{dividir}")
    Call<Session> Pay(@Path("idSessao") int idSession,
                      @Path("dividir") int divide);
}