package com.mijael.mein.GET;

import com.mijael.mein.SERVICIOS.CinesService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiCinesService {
    private static final String BASE_URL = "https://mijaelcano70131373.000webhostapp.com/cinestar_sweb/";

    private static ApiCinesService instance;
    private Retrofit retrofit;
    private CinesService cinesService;

    private ApiCinesService() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        cinesService = retrofit.create(CinesService.class);
    }

    public static synchronized ApiCinesService getInstance() {
        if (instance == null) {
            instance = new ApiCinesService();
        }
        return instance;
    }
    public CinesService getUserService() {
        return cinesService;
    }
}
