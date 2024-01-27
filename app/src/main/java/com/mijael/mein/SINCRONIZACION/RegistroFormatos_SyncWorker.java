package com.mijael.mein.SINCRONIZACION;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.mijael.mein.DAO.DAO_RegistroFormatos;
import com.mijael.mein.Entidades.RegistroFormatos;
import com.mijael.mein.GET.ApiRegistroFormatosService;
import com.mijael.mein.HELPER.RegistroFormatosSQLiteHelper;
import com.mijael.mein.SERVICIOS.RegistrosFormatosService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroFormatos_SyncWorker extends Worker {
    RegistroFormatosSQLiteHelper instace;
    DAO_RegistroFormatos dao_registroFormatos;
    RegistrosFormatosService formatosService;
    public Result result;
    public RegistroFormatos_SyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        instace = RegistroFormatosSQLiteHelper.getInstance(context);
        dao_registroFormatos = new DAO_RegistroFormatos(context);
        formatosService = ApiRegistroFormatosService.getInstance().getRegistrosFormatosService();
    }

    @NonNull
    @Override
    public Result doWork() {
        Call<List<RegistroFormatos>> call = formatosService.getRegistrosFormatos();
        call.enqueue(new Callback<List<RegistroFormatos>>() {
            @Override
            public void onResponse(Call<List<RegistroFormatos>> call, Response<List<RegistroFormatos>> response) {
                if(response.isSuccessful()){
                    Log.e("PASO5","ENTRO A ON RESPONSE REGISTROS-FORMATOS");
                    List<RegistroFormatos> registrosServidor = response.body();
                    int cantidadRegistros = dao_registroFormatos.contarRegistros();

                    if(cantidadRegistros == 0){
                        Log.e("ASDA","NINGUN REGISTRO DEBIO INSERTAR");
                        dao_registroFormatos.insertarRegistros(registrosServidor);
                    }else{
                        Log.e("ASSDFDA","HUBO REGISTROS DEBIO ACTUALIZAR O INSERTAR UN REGISTRO");
                        for (RegistroFormatos registro: registrosServidor) {
                            RegistroFormatos existe = dao_registroFormatos.Buscar(String.valueOf(registro.id_plan_trabajo_formato_reg));
                            if(existe!=null){
                                dao_registroFormatos.actualizarRegistro(registro);
                            }else{
                                dao_registroFormatos.insertarRegistro(registro);
                            }
                        }
                        result = Result.success();
                    }
                }else{
                    result = Result.retry();
                }
            }

            @Override
            public void onFailure(Call<List<RegistroFormatos>> call, Throwable t) {
                Log.e("TAG", "Error en la llamada a la API: " + t.getMessage());
                t.printStackTrace();
                result = Result.retry();
            }
        });


        return result;
    }
}
