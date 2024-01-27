package com.mijael.mein.SERVICIOS;

import com.mijael.mein.Entidades.RegistroFormatos_Detalle;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RegistrosFormatos_DetalleService {
    @GET("index.php?/Apk/v_lista_plan_trabajo_formato_reg_det")
    Call<List<RegistroFormatos_Detalle>> getRegistrosFormatos_Detalle();
}
