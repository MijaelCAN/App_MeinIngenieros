package com.mijael.mein.GET;

import com.mijael.mein.SERVICIOS.EquiposService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiEquiposService {
    private static final String BASE_URL = "https://test.meiningenieros.pe/";

    private static ApiEquiposService instance;
    private final EquiposService equiposService;

    public ApiEquiposService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        equiposService = retrofit.create(EquiposService.class);
    }

    public static synchronized ApiEquiposService getInstance() {
        if (instance == null) {
            instance = new ApiEquiposService();
        }
        return instance;
    }
    public EquiposService getEquiposService() {
        return equiposService;
    }
}
