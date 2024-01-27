package com.mijael.mein.SERVICIOS;


import com.mijael.mein.Entidades.RegistroFormatos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RegistrosFormatosService {
    @GET("index.php?/Apk/v_lista_plan_trabajo_formato_reg")
    Call<List<RegistroFormatos>> getRegistrosFormatos();
}
