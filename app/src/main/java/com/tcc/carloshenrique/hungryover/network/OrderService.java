package com.tcc.carloshenrique.hungryover.network;

import com.tcc.carloshenrique.hungryover.models.OrderModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OrderService {
    @POST("/pedidos/adicionarcarrinho")
    Call<OrderModel> sendOrder(@Body OrderModel order);

    @GET("/pedidos/buscar/{order_id}")
    Call<OrderModel> getOrder(@Path(value = "order_id", encoded = true) int id);
}