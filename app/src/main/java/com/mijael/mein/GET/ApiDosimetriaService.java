package com.mijael.mein.GET;

import com.mijael.mein.SERVICIOS.DosimetriaService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiDosimetriaService {
    private static final String BASE_URL = "https://test.meiningenieros.pe/";
    private static ApiDosimetriaService instance;
    private Retrofit retrofit;
    private DosimetriaService service;

    public ApiDosimetriaService(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(DosimetriaService.class);
    }
    public static synchronized ApiDosimetriaService getInstance(){
        if(instance == null){
            instance = new ApiDosimetriaService();
        }
        return instance;
    }

    public DosimetriaService getService() {return service;}
}
