package com.tcc.carloshenrique.hungryover.network;

import com.tcc.carloshenrique.hungryover.models.OrderModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OrderService {
    @POST("/pedidos/adicionarcarrinho")
    Call<OrderModel> sendOrder(@Body OrderModel order);
}
