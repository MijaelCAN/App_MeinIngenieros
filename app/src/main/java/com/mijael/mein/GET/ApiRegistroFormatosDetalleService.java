package com.mijael.mein.GET;

import com.mijael.mein.SERVICIOS.RegistrosFormatos_DetalleService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRegistroFormatosDetalleService {
    private static final String BASE_URL = "https://test.meiningenieros.pe/";
    private static ApiRegistroFormatosDetalleService instance;
    private final RegistrosFormatos_DetalleService formatos_detalleService;

    public ApiRegistroFormatosDetalleService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        formatos_detalleService = retrofit.create(RegistrosFormatos_DetalleService.class);
    }
    public static synchronized ApiRegistroFormatosDetalleService getInstance(){
        if(instance == null){
            instance = new ApiRegistroFormatosDetalleService();
        }
        return instance;
    }
    public RegistrosFormatos_DetalleService getRegistrosFormatos_detalleService(){return formatos_detalleService;}
}
