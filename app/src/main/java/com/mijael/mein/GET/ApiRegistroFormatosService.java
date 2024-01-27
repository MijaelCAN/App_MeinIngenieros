package com.mijael.mein.GET;

import com.mijael.mein.SERVICIOS.RegistrosFormatosService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRegistroFormatosService {
    private static final String BASE_URL = "https://test.meiningenieros.pe/";
    private static ApiRegistroFormatosService instance;
    private final RegistrosFormatosService registrosFormatosService;

    public ApiRegistroFormatosService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        registrosFormatosService = retrofit.create(RegistrosFormatosService.class);
    }
    public static synchronized ApiRegistroFormatosService getInstance(){
        if(instance == null){
            instance = new ApiRegistroFormatosService();
        }
        return instance;
    }
    public RegistrosFormatosService getRegistrosFormatosService(){return registrosFormatosService;}
}
