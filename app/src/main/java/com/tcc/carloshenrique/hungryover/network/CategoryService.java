package com.tcc.carloshenrique.hungryover.network;

import com.tcc.carloshenrique.hungryover.models.CategoryModel;
import com.tcc.carloshenrique.hungryover.models.CategoryModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CategoryService {
    @GET("/getcategorias")
    Call<List<CategoryModel>> all();

    @GET("/getcategorias/{restaurant_id}")
    Call<List<CategoryModel>> getCategories(@Path(value = "restaurant_id", encoded = true) int id);
}
