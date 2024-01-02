package com.mijael.mein.GET;

import com.mijael.mein.SERVICIOS.OrdenesTrabajoService;
import com.mijael.mein.SERVICIOS.UsuariosService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiOrdenesService {
    private static final String BASE_URL = "https://test.meiningenieros.pe/";

    private static ApiOrdenesService instance;
    private Retrofit retrofit;
    private OrdenesTrabajoService ordenesService;

    public ApiOrdenesService() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ordenesService = retrofit.create(OrdenesTrabajoService.class);
    }

    public static synchronized ApiOrdenesService getInstance() {
        if (instance == null) {
            instance = new ApiOrdenesService();
        }
        return instance;
    }
    public OrdenesTrabajoService getOrdenesService() {
        return ordenesService;
    }
}
