package com.mijael.mein.SERVICIOS;

import com.mijael.mein.Entidades.RegistroFormatos;
import com.mijael.mein.Entidades.RegistroFormatos_Detalle;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RegistrosFormatos_DetalleService {
    @GET("index.php?/Apk/v_lista_plan_trabajo_formato_reg_det")
    Call<List<RegistroFormatos_Detalle>> getRegistrosFormatos_Detalle();

    @POST("index.php?/ApkI/Update_RegistroDetalle")
    Call<ResponseBody> updateRegistrosDetalle(@Body RequestBody json);

    @POST("index.php?/Apk/v_plan_trabajo_formato_reg_detalle_id")
    Call<RegistroFormatos_Detalle> getRegistroDetalleFormatosById(@Body Map<String, String> requestBody);
}
