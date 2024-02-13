package com.mijael.mein.SERVICIOS;


import com.mijael.mein.Entidades.RegistroFormatos;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RegistrosFormatosService {
    @GET("index.php?/Apk/v_lista_plan_trabajo_formato_reg")
    Call<List<RegistroFormatos>> getRegistrosFormatos();

    @POST("index.php?/ApkI/Update_Registro")
    Call<ResponseBody> updateRegistrosFormatos(@Body RequestBody json);

}
