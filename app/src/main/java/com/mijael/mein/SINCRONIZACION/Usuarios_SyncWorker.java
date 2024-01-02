package com.mijael.mein.SINCRONIZACION;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.mijael.mein.DAO.DAO_Usuario;
import com.mijael.mein.Entidades.Usuario;
import com.mijael.mein.GET.ApiUsuariosService;
import com.mijael.mein.HELPER.UsuarioSQLiteHelper;
import com.mijael.mein.SERVICIOS.UsuariosService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Usuarios_SyncWorker extends Worker {
    UsuarioSQLiteHelper instance;
    DAO_Usuario dao_usuario;
    UsuariosService usuariosService;
    public Result resul ;
    public Usuarios_SyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        instance = UsuarioSQLiteHelper.getInstance(context);
        dao_usuario = new DAO_Usuario(context);
        usuariosService = ApiUsuariosService.getInstance().getUserService();
    }

    @NonNull
    @Override
    public Result doWork() {
        Call<List<Usuario>> call = usuariosService.getUsuario();
        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if (response.isSuccessful()) {
                    Log.e("PASO1","ENTRO A ONRESPONSE USUARIOS");
                    List<Usuario> todoItems = response.body();

                    // Obtener la cantidad de registros actualmente en la base de datos local
                    int cantidadRegistros = dao_usuario.contarUsuarios();

                    if (cantidadRegistros == 0) {
                        // Si no hay registros, insertar todos los datos del servidor
                        dao_usuario.insertarUsuarios(todoItems);
                        //AdapterDatos nuevo = new AdapterDatos(dao_persona.getPersonas());
                        //recyclerView.setAdapter(nuevo);
                    }else {
                        // Si hay registros, actualizar o insertar según corresponda
                        for (Usuario usuario : todoItems) {
                            // Verificar si el cine ya existe en la base de datos local
                            Usuario existe = dao_usuario.BuscarUsuario(usuario.getId_usuario());
                            //boolean existe = dao_usuario.verificarExistenciaUsuario(usuario.getId_usuario());

                            if (existe!=null) {
                                // Si existe, actualizar el registro en la base de datos local
                                dao_usuario.ActualizarUsuario(usuario);
                            } else {
                                // Si no existe, insertar el registro en la base de datos local
                                dao_usuario.insertarUsuario(usuario);
                            }
                            //enviarCambiosAlServidor(persona);
                        }
                        resul =  Result.success(); // Sincronización exit
                    }
                } else {
                    // Manejo de errores si la solicitud no fue exitosa
                    //resul = Result.retry();
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Log.e("TAG", "Error en la llamada a la API: " + t.getMessage());
                t.printStackTrace();
                resul = Result.retry();
            }
        });
        return resul;
    }
}
