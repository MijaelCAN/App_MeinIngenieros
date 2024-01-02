package com.mijael.mein.SERVICIOS;

import com.mijael.mein.Entidades.Cines;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CinesService {
    @GET("cines")
    Call<List<Cines>> getCines();

    @POST("createcines")
    Call<Cines> createCine(@Body Cines cinesRequest);

    @PUT("cines/{id}")
    Call<Cines> updateCine(@Path("id") int cineId, @Body Cines cinesRequest);

    @DELETE("cines/{id}")
    Call<Void> deleteCine(@Path("id") int cineId);

    @POST("cines/sync")
    Call<List<Cines>> syncCines(@Body List<Cines> cinesRequests);
}
