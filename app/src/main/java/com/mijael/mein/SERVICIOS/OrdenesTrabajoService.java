package com.mijael.mein.SERVICIOS;

import com.mijael.mein.Entidades.Orden_Trabajo;

import java.util.List;

import retrofit2.Call;

import retrofit2.http.GET;


public interface OrdenesTrabajoService {

    @GET("index.php?/Apk/v_lista_orden_trabajo")
    Call<List<Orden_Trabajo>> getOrdenesTrabajo();
}
