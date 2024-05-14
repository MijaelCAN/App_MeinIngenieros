package com.mijael.mein.SERVICIOS;


import com.mijael.mein.Entidades.RegistroFormatos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RegistrosFormatosService {
    @GET("index.php?/Apk/v_lista_plan_trabajo_formato_reg")
    Call<List<RegistroFormatos>> getRegistrosFormatos();

    @POST("index.php?/Apk/v_lista_plan_trabajo_formato_reg")
    Call<ArrayList<HashMap<String, String>>> getRegistrosFormatosById(@Body Map<String, String> requestBody);
    @POST("index.php?/ApkI/Update_Registro")
    Call<ResponseBody> updateRegistrosFormatos(@Body RequestBody json);
    @POST("index.php?/Apk/v_plan_trabajo_formato_reg_and_detalle_by_id")
    Call<Map<String, Object>> getRegistroFormatosById(@Body Map<String, String> requestBody);

}
