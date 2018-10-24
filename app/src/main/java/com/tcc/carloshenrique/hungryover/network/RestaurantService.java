package com.tcc.carloshenrique.hungryover.network;

import com.tcc.carloshenrique.hungryover.models.RestaurantModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RestaurantService {
    @GET("/getrestaurante/{table_id}")
    Call<RestaurantModel> getId(@Path(value = "table_id", encoded = true) int id);
}
