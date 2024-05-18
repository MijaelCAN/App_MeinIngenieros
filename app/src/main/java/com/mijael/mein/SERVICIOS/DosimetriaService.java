package com.mijael.mein.SERVICIOS;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface DosimetriaService {
    // --------------------------- INSERT ---------------------------------------
    @POST("index.php?/ApkI/Insert_Dosimetria")
    Call<ResponseBody> insertDosimetria(@Body RequestBody json);

    @POST("index.php?/ApkI/Insert_Vibracion")
    Call<ResponseBody> insertVibracion(@Body RequestBody json);

    @POST("index.php?/ApkI/Insert_Humedad_Relativa")
    Call<ResponseBody> insertHumedadRelativa(@Body RequestBody json);
    @POST("index.php?/ApkI/Insert_Velocidad_Aire")
    Call<ResponseBody> insertVelocidadAire(@Body RequestBody json);

    @POST("index.php?/ApkI/Insert_Radiacion_Electromag")
    Call<ResponseBody> insertRadiacionElectro(@Body RequestBody json);

    @POST("index.php?/ApkI/Insert_Estres_Termico")
    Call<ResponseBody> insertEstresTermico(@Body RequestBody json);
    @POST("index.php?/ApkI/Insert_Estres_Frio")
    Call<ResponseBody> insertEstresFrio(@Body RequestBody json);

    @POST("index.php?/ApkI/Insert_Confort")
    Call<ResponseBody> insertConfort(@Body RequestBody json);

    @POST("index.php?/ApkI/Insert_Radiacion_Uv")
    Call<ResponseBody> insertRadiacionUV(@Body RequestBody json);

    @POST("index.php?/ApkI/Insert_Iluminacion")
    Call<ResponseBody> insertIluminacion(@Body RequestBody json);

    @POST("index.php?/ApkI/Insert_Sonometria")
    Call<ResponseBody> insertSonometria(@Body RequestBody json);

    // -------------------------- UPDATE -----------------------------------------------
    @POST("index.php?/ApkI/Update_Velocidad_Aire")
    Call<ResponseBody> UpdateVelocidadAire(@Body Map<String, String> json);
    @POST("index.php?/Apk/Update_Velocidad_Aire")
    Call<String> getOrdenesTrabajobyId(@Body Map<String, String> idColaborador);

}
