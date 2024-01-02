package com.mijael.mein.GET;

import com.mijael.mein.SERVICIOS.FormatosService;
import com.mijael.mein.SERVICIOS.OrdenesTrabajoService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFormatosService {
    private static final String BASE_URL = "https://test.meiningenieros.pe/";

    private static ApiFormatosService instance;
    private Retrofit retrofit;
    private FormatosService formatosService;

    public ApiFormatosService() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        formatosService = retrofit.create(FormatosService.class);
    }

    public static synchronized ApiFormatosService getInstance() {
        if (instance == null) {
            instance = new ApiFormatosService();
        }
        return instance;
    }
    public FormatosService getFormatosService() {
        return formatosService;
    }
}
