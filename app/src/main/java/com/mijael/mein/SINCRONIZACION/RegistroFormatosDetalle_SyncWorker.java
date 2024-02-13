package com.mijael.mein.SINCRONIZACION;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mijael.mein.DAO.DAO_RegistroFormatos;
import com.mijael.mein.DAO.DAO_RegistroFormatosDetalle;
import com.mijael.mein.Entidades.RegistroFormatos;
import com.mijael.mein.Entidades.RegistroFormatos_Detalle;
import com.mijael.mein.GET.ApiRegistroFormatosDetalleService;
import com.mijael.mein.HELPER.MeinSQLiteHelper;
import com.mijael.mein.SERVICIOS.RegistrosFormatos_DetalleService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroFormatosDetalle_SyncWorker extends Worker {
    MeinSQLiteHelper instace;
    DAO_RegistroFormatosDetalle dao_registroFormatosDetalle;
    DAO_RegistroFormatos dao_registroFormatos;
    RegistrosFormatos_DetalleService detalleService;
    public Result result;
    public RegistroFormatosDetalle_SyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        instace = MeinSQLiteHelper.getInstance(context);
        dao_registroFormatosDetalle = new DAO_RegistroFormatosDetalle(context);
        dao_registroFormatos = new DAO_RegistroFormatos(context);
        detalleService = ApiRegistroFormatosDetalleService.getInstance().getRegistrosFormatos_detalleService();
    }

    @NonNull
    @Override
    public Result doWork() {
        Call<List<RegistroFormatos_Detalle>> call = detalleService.getRegistrosFormatos_Detalle();
        call.enqueue(new Callback<List<RegistroFormatos_Detalle>>() {
            @Override
            public void onResponse(Call<List<RegistroFormatos_Detalle>> call, Response<List<RegistroFormatos_Detalle>> response) {
                if(response.isSuccessful()){
                    List<RegistroFormatos_Detalle> detalleServidor = response.body();
                    int cantidadDetalle = dao_registroFormatosDetalle.contarRegistroDetalle();

                    if(cantidadDetalle == 0){
                        dao_registroFormatosDetalle.insertarRegistrosDetalle(detalleServidor);
                    }else{
                        syncLocalToWeb();
                        for (RegistroFormatos_Detalle registroDetalle : detalleServidor) {
                            Log.e("tyrty",String.valueOf(registroDetalle.getId_formato_reg_detalle()));
                            RegistroFormatos_Detalle existe = dao_registroFormatosDetalle.Buscar(String.valueOf(registroDetalle.getId_formato_reg_detalle()));
                            if(existe!=null){
                                dao_registroFormatosDetalle.actualizarRegistroDetalle(registroDetalle);
                            }else{
                                dao_registroFormatosDetalle.insertarRegistroDetalle(registroDetalle);
                            }
                        }
                        result = Result.success();
                    }
                }else{
                    result = Result.retry();
                }
            }

            @Override
            public void onFailure(Call<List<RegistroFormatos_Detalle>> call, Throwable t) {

            }
        });


        return result;
    }

    private void syncLocalToWeb() {
        List<RegistroFormatos_Detalle> detallesLocales = dao_registroFormatosDetalle.listarRegistrosConEstadoSincro(1);
        Gson gson = new Gson();
        Log.e("fsddfs", "Hubo "+ detallesLocales.size() + " nuevo Registros_Detalle actualizados o insertados");

        for (RegistroFormatos_Detalle detalle: detallesLocales) {
            RegistroFormatos regist = dao_registroFormatos.Buscar(String.valueOf(detalle.getId_plan_trabajo_formato_reg()));
            List<JsonObject> nuevo = new ArrayList<>();

            JsonObject jsonObject = gson.toJsonTree(detalle).getAsJsonObject();
            jsonObject.addProperty("id_formato", regist.getId_formato());
            jsonObject.addProperty("id_analista", regist.getId_analista());
            jsonObject.addProperty("tipo_medicion", regist.getTipo_medicion());

            nuevo.add(jsonObject);
            String json = gson.toJson(nuevo);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);

            Call<ResponseBody> call = detalleService.updateRegistrosDetalle(requestBody);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.isSuccessful()){
                        dao_registroFormatosDetalle.cambiarEstadoSincronizacion(detalle.getId_plan_trabajo_formato_reg(),true);
                        String responseBody = null;
                        try {
                            responseBody = response.body() != null ? response.body().string(): "No hay contenido en la respuesta";
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Log.e("sfsdf","Detalle actualizado en Web"+ responseBody);
                    }else{
                        try {
                            String errorBody = response.errorBody() != null ? response.errorBody().string() : "No hay información de error disponible";
                            Log.e("TAG", "Error al sincronizar el detalle: " + detalle.id_plan_trabajo_formato_reg + " - " + errorBody);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }
    }
}
