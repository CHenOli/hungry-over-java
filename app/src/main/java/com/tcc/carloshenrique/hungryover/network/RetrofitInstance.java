package com.tcc.carloshenrique.hungryover.network;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class RetrofitInstance {
    private static Retrofit retrofit;
    private static final String BASE_URL = "https://hungryover-api.herokuapp.com/clientes";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
