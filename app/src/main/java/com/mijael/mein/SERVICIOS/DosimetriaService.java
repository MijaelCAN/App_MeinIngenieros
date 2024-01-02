package com.mijael.mein.SERVICIOS;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface DosimetriaService {
    @POST("index.php?/ApkI/Insert_Dosimetria")
    Call<ResponseBody> insertData(@Body RequestBody json);
}
