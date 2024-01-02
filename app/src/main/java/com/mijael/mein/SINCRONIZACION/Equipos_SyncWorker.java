package com.mijael.mein.SINCRONIZACION;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.mijael.mein.DAO.DAO_Equipos;
import com.mijael.mein.Entidades.Equipos;
import com.mijael.mein.Entidades.Formatos_Trabajo;
import com.mijael.mein.GET.ApiEquiposService;
import com.mijael.mein.HELPER.EquiposSQLiteHelper;
import com.mijael.mein.SERVICIOS.EquiposService;

import java.util.List;

import javax.xml.transform.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Equipos_SyncWorker extends Worker {
    EquiposSQLiteHelper instance;
    DAO_Equipos dao_equipos;
    EquiposService equiposService;
    public Result result;

    public Equipos_SyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        instance = EquiposSQLiteHelper.getInstance(context);
        dao_equipos = new DAO_Equipos(context);
        equiposService = ApiEquiposService.getInstance().getEquiposService();
    }

    @NonNull
    @Override
    public Result doWork() {

        Call<List<Equipos>> call = equiposService.getEquipos();
        call.enqueue(new Callback<List<Equipos>>() {
            @Override
            public void onResponse(Call<List<Equipos>> call, Response<List<Equipos>> response) {
                if (response.isSuccessful()) {
                    Log.e("PASO1","ENTRO A ON RESPONSE EQUIPOS");
                    List<Equipos> todoItems = response.body();

                    // Obtener la cantidad de registros actualmente en la base de datos local
                    int cantidadRegistros = dao_equipos.contarEquipos();

                    if (cantidadRegistros == 0) {
                        // Si no hay registros, insertar todos los datos del servidor
                        dao_equipos.insertarEquipos(todoItems);
                        //AdapterDatos nuevo = new AdapterDatos(dao_persona.getPersonas());
                        //recyclerView.setAdapter(nuevo);
                    }else {
                        // Si hay registros, actualizar o insertar según corresponda
                        for (Equipos equipo : todoItems) {
                            // Verificar si el cine ya existe en la base de datos local
                            Equipos existe = dao_equipos.Buscar(String.valueOf(equipo.getCod_equipo()));
                            //boolean existe = dao_equipos.verificarExistenciaEquipo(Integer.valueOf(equipo.getId_equipo_registro()));

                            if (existe!=null) {
                                // Si existe, actualizar el registro en la base de datos local
                                dao_equipos.actualizarEquipo(equipo);
                            } else {
                                // Si no existe, insertar el registro en la base de datos local
                                dao_equipos.insertarEquipo(equipo);
                            }
                            //enviarCambiosAlServidor(persona);
                        }
                        result =  Result.success(); // Sincronización exit
                    }
                } else {
                    // Manejo de errores si la solicitud no fue exitosa
                    result = Result.retry();
                }
            }

            @Override
            public void onFailure(Call<List<Equipos>> call, Throwable t) {
                Log.e("TAG", "Error en la llamada a la API: " + t.getMessage());
                t.printStackTrace();
                result = Result.retry();
            }
        });
        return result;
    }
}
