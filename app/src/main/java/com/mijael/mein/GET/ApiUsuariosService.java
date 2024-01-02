package com.mijael.mein.GET;


import com.mijael.mein.SERVICIOS.UsuariosService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiUsuariosService {
    private static final String BASE_URL = "https://test.meiningenieros.pe/";

    private static ApiUsuariosService instance;
    private Retrofit retrofit;
    private UsuariosService usuarioService;

    public ApiUsuariosService() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        usuarioService = retrofit.create(UsuariosService.class);
    }

    public static synchronized ApiUsuariosService getInstance() {
        if (instance == null) {
            instance = new ApiUsuariosService();
        }
        return instance;
    }
    public UsuariosService getUserService() {
        return usuarioService;
    }
}
