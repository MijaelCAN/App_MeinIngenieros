package com.mijael.mein.SINCRONIZACION;

import static java.lang.Integer.parseInt;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.mijael.mein.DAO.DAO_OrdenTrabajo;
import com.mijael.mein.Entidades.Orden_Trabajo;
import com.mijael.mein.GET.ApiOrdenesService;
import com.mijael.mein.HELPER.OrdenTrabajoSQLiteHelper;
import com.mijael.mein.SERVICIOS.OrdenesTrabajoService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Ordenes_SyncWorker extends Worker {
    OrdenTrabajoSQLiteHelper instance;
    DAO_OrdenTrabajo dao_orden;
    OrdenesTrabajoService ordenesService;
    public Result resul ;
    public Ordenes_SyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        instance = OrdenTrabajoSQLiteHelper.getInstance(context);
        dao_orden = new DAO_OrdenTrabajo(context);
        ordenesService = ApiOrdenesService.getInstance().getOrdenesService();
    }

    @NonNull
    @Override
    public Result doWork() {

        Call<List<Orden_Trabajo>> call = ordenesService.getOrdenesTrabajo();
        call.enqueue(new Callback<List<Orden_Trabajo>>() {
            @Override
            public void onResponse(Call<List<Orden_Trabajo>> call, Response<List<Orden_Trabajo>> response) {
                if (response.isSuccessful()) {
                    Log.e("PASO1","ENTRO A ON RESPONSE ORDENES");
                    List<Orden_Trabajo> todoItems = response.body();

                    // Obtener la cantidad de registros actualmente en la base de datos local
                    int cantidadRegistros = dao_orden.contarOrdenes();

                    if (cantidadRegistros == 0) {
                        // Si no hay registros, insertar todos los datos del servidor
                        dao_orden.insertarOrdenes(todoItems);
                    }else {
                        // Si hay registros, actualizar o insertar según corresponda
                        for (Orden_Trabajo orden : todoItems) {
                            // Verificar si el cine ya existe en la base de datos local
                            Orden_Trabajo existe = dao_orden.BuscarOrden(orden.getId_ot());
                            //boolean existe = dao_orden.verificarExistenciaOrden(orden.getCod_ot());

                            if (existe!=null) {
                                // Si existe, actualizar el registro en la base de datos local
                                dao_orden.ActualizarOrdenes(orden);
                            } else {
                                // Si no existe, insertar el registro en la base de datos local
                                dao_orden.insertarOrden(orden);
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
            public void onFailure(Call<List<Orden_Trabajo>> call, Throwable t) {
                Log.e("TAG", "Error en la llamada a la API: " + t.getMessage());
                t.printStackTrace();
                resul = Result.retry();
            }
        });

        return resul;
    }
}
