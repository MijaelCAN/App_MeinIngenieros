package com.mijael.mein.SERVICIOS;

import com.mijael.mein.Entidades.Orden_Trabajo;

import java.util.List;
import java.util.Map;

import retrofit2.Call;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface OrdenesTrabajoService {

    @GET("index.php?/Apk/v_lista_orden_trabajo")
    Call<List<Orden_Trabajo>> getOrdenesTrabajo();
    @POST("index.php?/Apk/v_lista_orden_trabajo")
    Call<List<Orden_Trabajo>> getOrdenesTrabajobyId(@Body Map<String, String> idColaborador);

}
