package com.mijael.mein.SERVICIOS;

import com.mijael.mein.Entidades.Formatos_Trabajo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FormatosService {
    @GET("index.php?/Apk/lista_formatos_ptrabajo")
    Call<List<Formatos_Trabajo>> getFormatos();
}
