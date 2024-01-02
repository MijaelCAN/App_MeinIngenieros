package com.mijael.mein.SERVICIOS;

import com.mijael.mein.Entidades.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UsuariosService {

    @GET("index.php?/Apk/usuarios")
    Call<List<Usuario>> getUsuario();
}
