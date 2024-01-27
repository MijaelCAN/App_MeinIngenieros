package com.mijael.mein.SINCRONIZACION;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.mijael.mein.DAO.DAO_RegistroFormatosDetalle;
import com.mijael.mein.Entidades.RegistroFormatos_Detalle;
import com.mijael.mein.GET.ApiRegistroFormatosDetalleService;
import com.mijael.mein.HELPER.RegistroFormatosSQLiteHelper;
import com.mijael.mein.SERVICIOS.RegistrosFormatos_DetalleService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroFormatosDetalle_SyncWorker extends Worker {
    RegistroFormatosSQLiteHelper instace;
    DAO_RegistroFormatosDetalle dao_registroFormatosDetalle;
    RegistrosFormatos_DetalleService detalleService;
    public Result result;
    public RegistroFormatosDetalle_SyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        instace = RegistroFormatosSQLiteHelper.getInstance(context);
        dao_registroFormatosDetalle = new DAO_RegistroFormatosDetalle(context);
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
                        for (RegistroFormatos_Detalle registroDetalle : detalleServidor) {
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
}
