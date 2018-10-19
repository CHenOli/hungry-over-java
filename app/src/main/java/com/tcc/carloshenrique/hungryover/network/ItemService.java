package com.tcc.carloshenrique.hungryover.network;

import com.tcc.carloshenrique.hungryover.models.ItemModel;
import com.tcc.carloshenrique.hungryover.models.ItemModel;
import com.tcc.carloshenrique.hungryover.models.ItemModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ItemService {
    @GET("/getprodutos/{idCategory}")
    Call<List<ItemModel>> getAll(@Path(value = "idCategory", encoded = true) int idCategory);

    @GET("/getprodutos/{category_id}/id")
    Call<ItemModel> getId(@Path(value = "category_id", encoded = true) int id);
    @GET("/getprodutos/{category_id}/nome")
    Call<ItemModel> getName(@Path(value = "category_id", encoded = true) String name);
    @GET("/getprodutos/{category_id}/urlImagem")
    Call<ItemModel> getImage(@Path(value = "category_id", encoded = true) String image);
    @GET("/getprodutos/{category_id}/valor")
    Call<ItemModel> getPrice(@Path(value = "category_id", encoded = true) double price);
}