package com.mijael.mein.SINCRONIZACION;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.gson.Gson;
import com.mijael.mein.DAO.DAO_RegistroFormatos;
import com.mijael.mein.Entidades.RegistroFormatos;
import com.mijael.mein.Extras.InputDateConfiguration;
import com.mijael.mein.GET.ApiRegistroFormatosService;
import com.mijael.mein.HELPER.MeinSQLiteHelper;
import com.mijael.mein.SERVICIOS.RegistrosFormatosService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroFormatos_SyncWorker extends Worker {
    MeinSQLiteHelper instace;
    DAO_RegistroFormatos dao_registroFormatos;
    RegistrosFormatosService formatosService;
    InputDateConfiguration config;
    public Result result;
    public RegistroFormatos_SyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        instace = MeinSQLiteHelper.getInstance(context);
        dao_registroFormatos = new DAO_RegistroFormatos(context);
        formatosService = ApiRegistroFormatosService.getInstance().getRegistrosFormatosService();
        config = new InputDateConfiguration();
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
                        syncLocalToWeb();
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

    public void syncLocalToWeb() {
        // Obtén los registros locales que necesitan ser sincronizados
        List<RegistroFormatos> registrosLocales = dao_registroFormatos.listarRegistrosConEstadoSincro(1);
        Gson gson = new Gson();
        Log.e("fsdfs", "Hubo "+ registrosLocales.size() + " nuevo Registros actualizados o insertados");
        for (RegistroFormatos registro : registrosLocales) {
            List<RegistroFormatos> arrayRegiatros = new ArrayList<>();
            arrayRegiatros.add(registro);
            String json = gson.toJson(arrayRegiatros);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);
            // Crea una llamada a la API para actualizar el registro
            Call<ResponseBody> call = formatosService.updateRegistrosFormatos(requestBody);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        // Si la respuesta es exitosa, marca el registro como sincronizado en la base de datos local
                        dao_registroFormatos.cambiarEstadoSincronizacion(registro.id_plan_trabajo_formato_reg,true);
                        String rutaFoto = registro.getRuta_foto();
                        if (rutaFoto != null && !rutaFoto.trim().isEmpty()) {
                            File imageFile = new File(rutaFoto);
                            config.uploadImage(imageFile, registro.cod_formato,registro.id_pt_formato,registro.cod_registro);
                            // Resto del código...
                        }
                        String responseBody = null;
                        try {
                            responseBody = response.body() != null ? response.body().string(): "No hay contenido en la respuesta";
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Log.e("sfsdf","Registro actualizado en Web"+ responseBody);
                    } else {
                        // Si la respuesta no es exitosa, maneja el error
                        try {
                            String errorBody = response.errorBody() != null ? response.errorBody().string() : "No hay información de error disponible";
                            Log.e("TAG", "Error al sincronizar el registro: " + registro.id_plan_trabajo_formato_reg + " - " + errorBody);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    // Si la llamada falla, maneja el error
                    Log.e("TAG", "Error en la llamada a la API: " + t.getMessage());
                    t.printStackTrace();
                }
            });
        }
    }
}
