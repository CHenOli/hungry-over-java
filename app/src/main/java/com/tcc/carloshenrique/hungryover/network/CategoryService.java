package com.tcc.carloshenrique.hungryover.network;

import com.tcc.carloshenrique.hungryover.models.CategoryModel;
import com.tcc.carloshenrique.hungryover.models.CategoryModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CategoryService {
    @GET("/categorias")
    Call<List<CategoryModel>> all();
    @GET("/categorias")
    Call<CategoryModel> getCategory();

    @GET("/categorias/id")
    Call<CategoryModel> getId(@Query("id") int id);
    @GET("/categorias/nome")
    Call<CategoryModel> getName(@Query("name") String name);
    @GET("/categorias/imagem")
    Call<CategoryModel> getImage(@Query("image") String image);

    @POST("/categorias")
    Call<CategoryModel> create(@Body CategoryModel user);
}
