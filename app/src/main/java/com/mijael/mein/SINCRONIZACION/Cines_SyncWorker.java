package com.mijael.mein.SINCRONIZACION;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.mijael.mein.DAO.DAO_Cines;
import com.mijael.mein.Entidades.Cines;
import com.mijael.mein.GET.ApiCinesService;
import com.mijael.mein.HELPER.CinesSQLiteHelper;
import com.mijael.mein.SERVICIOS.CinesService;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Cines_SyncWorker extends Worker {
    private static final String TAG = Cines_SyncWorker.class.getSimpleName();

    private CinesSQLiteHelper instance;
    private DAO_Cines cines;
    private CinesService cineService;
    public Cines_SyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        instance = CinesSQLiteHelper.getInstance(context);
        cines = new DAO_Cines(context);
        //cineService = RetrofitClient.getClient().create(UserService.class);
        cineService = ApiCinesService.getInstance().getUserService();
    }
    String URL_Personas = "https://mijaelcano70131373.000webhostapp.com/cinestar_sweb/";
    public Result resul ;
    @NonNull
    @Override
    public Result doWork() {

        /*try {
            // Obtener todos los datos del servidor
            Response<List<Cines>> response = obtenerCinesDelServidor();

            if (response != null && response.isSuccessful()) {
                List<Cines> cinesServidor = response.body();

                // Verificar si la tabla local está vacía
                List<Cines> cinesLocales = obtenerCinesLocales();

                if (cinesLocales == null || cinesLocales.isEmpty()) {
                    guardarCinesLocales(cinesServidor);
                } else {
                    actualizarCinesLocales(cinesLocales, cinesServidor);
                }

                return Result.success(); // Sincronización exitosa
            } else {
                return Result.failure(); // Error al obtener datos del servidor
            }
        } catch (IOException e) {
            manejarErrorDeConexion(e);
            return Result.retry(); // Reintentar en caso de error
        }*/
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_Personas)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //TodoService service = retrofit.create(TodoService.class);
        CinesService service = retrofit.create(CinesService.class);

        Call<List<Cines>> call = cineService.getCines();
        call.enqueue(new Callback<List<Cines>>() {
            @Override
            public void onResponse(Call<List<Cines>> call, Response<List<Cines>> response) {
                if (response.isSuccessful()) {
                    Log.e("PASO1","ENTRO A ON RESPONSE CINES");
                    List<Cines> todoItems = response.body();

                    // Obtener la cantidad de registros actualmente en la base de datos local
                    int cantidadRegistros = cines.contarCines();

                    if (cantidadRegistros == 0) {
                        // Si no hay registros, insertar todos los datos del servidor
                        cines.insertarCines(todoItems);
                        //AdapterDatos nuevo = new AdapterDatos(dao_persona.getPersonas());
                        //recyclerView.setAdapter(nuevo);
                    }else {
                        // Si hay registros, actualizar o insertar según corresponda
                        for (Cines cine : todoItems) {
                            // Verificar si el cine ya existe en la base de datos local
                            boolean existe = cines.verificarExistenciaCine(cine.getId());

                            if (existe) {
                                // Si existe, actualizar el registro en la base de datos local
                                cines.ActualizarCine(cine);
                            } else {
                                // Si no existe, insertar el registro en la base de datos local
                                cines.insertarCine(cine);
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
            public void onFailure(Call<List<Cines>> call, Throwable t) {
                Log.e("TAG", "Error en la llamada a la API: " + t.getMessage());
                t.printStackTrace();
                resul = Result.retry();
            }
        });
        return resul;
    }

    private Response<List<Cines>> obtenerCinesDelServidor() throws IOException {
        Call<List<Cines>> getCinesCall = cineService.getCines();
        return getCinesCall.execute();
    }

    private List<Cines> obtenerCinesLocales() {
        return cines.ListarCines();
    }

    private void guardarCinesLocales(List<Cines> cinesServidor) {
        cines.guardarCinesActualizadosLocalmente(cinesServidor);
    }

    private void actualizarCinesLocales(List<Cines> cinesLocales, List<Cines> cinesServidor) throws IOException {
        List<Cines> cinesActualizados = cines.actualizarCinesLocalmente(cinesLocales, cinesServidor, cineService);
        Log.e("MUESTRA", cinesActualizados.toString());
        /*if (cinesActualizados != null && !cinesActualizados.isEmpty()) {
            enviarCinesActualizadosAlServidor(cinesActualizados);
        }*/
    }

    private void enviarCinesActualizadosAlServidor(List<Cines> cinesActualizados) throws IOException {
        Call<List<Cines>> syncCall = cineService.syncCines(cinesActualizados);
        Response<List<Cines>> syncResponse = syncCall.execute();

        if (!syncResponse.isSuccessful()) {
            Log.e("Error2", "Error al sincronizar datos con el servidor");
            // Aquí podrías considerar manejar el error o registrar información adicional si es necesario
        }

        Log.e("ENTRADA6", "Actualización de cines local completada.");
    }

    private void manejarErrorDeConexion(IOException e) {
        Log.e("EXCEPCION", "Error al ejecutar la llamada: " + e.getMessage());
        e.printStackTrace();
        // Aquí podrías agregar lógica adicional para manejar errores de conexión si es necesario
    }

        /*try {
            // Obtener todos los datos del servidor
            Call<List<Cines>> getCinesCall = cineService.getCines();
            Response<List<Cines>> response = getCinesCall.execute();

            if (response.isSuccessful()) {
                List<Cines> cinesServidor = response.body();

                // Verificar si la tabla local está vacía
                List<Cines> cinesLocales = cines.ListarCines();
                if (cinesLocales == null || cinesLocales.isEmpty()) {
                    // Si la tabla local está vacía, guardar los datos del servidor en la tabla local
                    cines.guardarCinesActualizadosLocalmente(cinesServidor);
                } else {
                    Log.e("ENTRADA2","LA LISTA NO ESTABA VACIA");
                    // Comparar los datos del servidor con los datos locales y actualizar la tabla local si es necesario
                    List<Cines> cinesActualizados = cines.actualizarCinesLocalmente(cinesLocales, cinesServidor,cineService);

                    // Si se actualizaron los datos locales, enviar los datos actualizados al servidor
                    if (cinesActualizados != null && !cinesActualizados.isEmpty()) {
                        Log.e("ENTRADA5","ENVIAR DATOS AL SERVIDOR");
                        Call<List<Cines>> syncCall = cineService.syncCines(cinesActualizados);
                        Response<List<Cines>> syncResponse = syncCall.execute();

                        if (!syncResponse.isSuccessful()) {
                            Log.e("Error2","Error al sincronizar datos con el servidor");
                            return Result.failure(); // Error al sincronizar datos con el servidor
                        }
                    }
                    Log.e("ENTRADA6","Actualización de cines local completada.");
                }

                return Result.success(); // Sincronización exitosa
            } else {
                return Result.failure(); // Error al obtener datos del servidor
            }
        } catch (IOException e) {
            Log.e("EXCEPCION", "Error al ejecutar la llamada: " + e.getMessage());
            e.printStackTrace();
            return Result.retry(); // Reintentar en caso de error
        }
    }*/
}
        /*try {
            // Verificar si la tabla local está vacía
            List<Cines> cinesLocales = cines.GetCines();

           // if (cinesLocales == null || cinesLocales.isEmpty()) {
                // Si la tabla local está vacía, obtener todos los datos del servidor
                //Call<List<Cines>> getCinesCall = cineService.getCines();
                //Response<List<Cines>> response = getCinesCall.execute();
                Call<List<Cines>> syncCall = cineService.syncCines(cinesLocales);
                Response<List<Cines>> response = syncCall.execute();

                if (response.isSuccessful()) {
                    List<Cines> cinesServidor = response.body();

                    if (cinesServidor != null && !cinesServidor.isEmpty()) {
                        // Guardar los datos del servidor en la tabla local
                        cines.guardarCinesActualizadosLocalmente(cinesServidor, getApplicationContext());
                        return Result.success(); // Sincronización exitosa
                    } else {
                        return Result.failure(); // No hay datos del servidor para sincronizar
                    }
                } else {
                    return Result.failure(); // Error al obtener datos del servidor
                }
            /*} else {
                return Result.success(); // La tabla local ya tiene datos, no es necesario sincronizar
            }*/

        /*} catch (IOException e) {
            return Result.retry(); // Reintentar en caso de error
        }*/