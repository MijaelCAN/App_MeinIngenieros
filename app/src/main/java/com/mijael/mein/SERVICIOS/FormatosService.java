package com.mijael.mein.SERVICIOS;

import com.mijael.mein.Entidades.Formatos_Trabajo;
import com.mijael.mein.Entidades.Orden_Trabajo;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface FormatosService {
    @GET("index.php?/Apk/lista_formatos_ptrabajo")
    Call<List<Formatos_Trabajo>> getFormatos();
    @POST("index.php?/Apk/lista_formatos_ptrabajo")
    Call<List<Formatos_Trabajo>> getFormatosTrabajobyId(@Body Map<String, String> idColaborador);
}
