package com.mijael.mein.SINCRONIZACION;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.mijael.mein.DAO.DAO_FormatosTrabajo;
import com.mijael.mein.Entidades.Formatos_Trabajo;
import com.mijael.mein.Entidades.Orden_Trabajo;
import com.mijael.mein.GET.ApiFormatosService;
import com.mijael.mein.HELPER.FormatoTrabajoSQLiteHelper;
import com.mijael.mein.SERVICIOS.FormatosService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Formatos_SyncWorker extends Worker {
    FormatoTrabajoSQLiteHelper instance;
    DAO_FormatosTrabajo dao_formatos;
    FormatosService formatosService;
    public Result resul ;
    public Formatos_SyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        instance = FormatoTrabajoSQLiteHelper.getInstance(context);
        dao_formatos = new DAO_FormatosTrabajo(context);
        formatosService = ApiFormatosService.getInstance().getFormatosService();
    }

    @NonNull
    @Override
    public Result doWork() {
        Call<List<Formatos_Trabajo>> call = formatosService.getFormatos();
        call.enqueue(new Callback<List<Formatos_Trabajo>>() {
            @Override
            public void onResponse(Call<List<Formatos_Trabajo>> call, Response<List<Formatos_Trabajo>> response) {
                if (response.isSuccessful()) {
                    Log.e("PASO1","ENTRO A ON RESPONSE FORMATOS");
                    List<Formatos_Trabajo> todoItems = response.body();

                    // Obtener la cantidad de registros actualmente en la base de datos local
                    int cantidadRegistros = dao_formatos.contarFormatos();

                    if (cantidadRegistros == 0) {
                        // Si no hay registros, insertar todos los datos del servidor
                        dao_formatos.insertarFormatos(todoItems);
                        //AdapterDatos nuevo = new AdapterDatos(dao_persona.getPersonas());
                        //recyclerView.setAdapter(nuevo);
                    }else {
                        // Si hay registros, actualizar o insertar según corresponda
                        for (Formatos_Trabajo formato : todoItems) {
                            // Verificar si el cine ya existe en la base de datos local
                            Formatos_Trabajo nuevo = dao_formatos.Buscar(String.valueOf(formato.getId_plan_trabajo()),String.valueOf(formato.getId_formato()));
                            //boolean existe = dao_formatos.verificarExistenciaFormato(Integer.valueOf(formato.getId_pt_formato()));

                            if (nuevo!=null) {
                                // Si existe, actualizar el registro en la base de datos local
                                dao_formatos.actualizarFormatoTrabajo(formato);
                            } else {
                                // Si no existe, insertar el registro en la base de datos local
                                dao_formatos.insertarFormatoTrabajo(formato);
                            }
                            //enviarCambiosAlServidor(persona);
                        }
                        resul =  Result.success(); // Sincronización exit
                    }
                } else {
                    // Manejo de errores si la solicitud no fue exitosa
                    resul = Result.retry();
                }
            }

            @Override
            public void onFailure(Call<List<Formatos_Trabajo>> call, Throwable t) {
                Log.e("TAG", "Error en la llamada a la API: " + t.getMessage());
                t.printStackTrace();
                resul = Result.retry();
            }
        });
        return resul;
    }
}
