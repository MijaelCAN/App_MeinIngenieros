package com.mijael.mein.SERVICIOS;

import com.mijael.mein.Entidades.Equipos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EquiposService {

    @GET("index.php?/Apk/v_lista_equipo_formato")
    Call<List<Equipos>> getEquipos();
}
