package com.mijael.mein.GET;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.mijael.mein.Entidades.Usuario;
import com.mijael.mein.Utilidades.Util_Usuario;
import com.mijael.mein.HELPER.UsuarioSQLiteHelper;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class getApi_Usuarios {
    RequestQueue queue;
    private Context context;
    private String URL = "https://sistema.meiningenieros.pe/index.php?/Apk/usuarios";
    public getApi_Usuarios(Context context){
        this.context = context;
        this.queue = Volley.newRequestQueue(context);
    }
    public void getRegistros(){
        Log.e("XXXX","ENTRO A getRegistros");
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int size = response.length();
                        for (int i = 0; i < size; i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String idUsuario = jsonObject.getString("id_usuario");
                                String usuarioNombres = jsonObject.getString("usuario_nombres");
                                String usuarioApater = jsonObject.getString("usuario_apater");
                                String usuarioAmater = jsonObject.getString("usuario_amater");
                                String usuarioCodigo = jsonObject.getString("usuario_codigo");
                                String usuarioPassword = jsonObject.getString("usuario_password");
                                String passwordDesc = jsonObject.getString("password_desc");
                                String idNivel = jsonObject.getString("id_nivel");
                                String codigo = jsonObject.getString("codigo");
                                // Do something with the retrieved values
                                Usuario usuario = new Usuario(Integer.valueOf(idUsuario),usuarioNombres,usuarioApater,usuarioAmater,usuarioCodigo,usuarioPassword,passwordDesc,Integer.valueOf(idNivel),codigo);
                                Log.e("Usuario",usuarioNombres);
                                registrarUsuario(usuario);
                            } catch (JSONException e) {
                                Log.e("Error","Algo salio mal");
                                throw new RuntimeException(e);
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Log.e("Error", "Error en la solicitud re Red: " + error.getMessage());
                    }
                }
        );
        queue.add(jsonArrayRequest);
    }

    private void registrarUsuario(Usuario usuario) {

        UsuarioSQLiteHelper con = UsuarioSQLiteHelper.getInstance(context);//Instancia para la conexion a la Base de datos de SQLite
        SQLiteDatabase db = con.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util_Usuario.CAMPO_NOMBRES, usuario.getUsuario_nombres());
        values.put(Util_Usuario.CAMPO_APATER, usuario.getUsuario_apater());
        values.put(Util_Usuario.CAMPO_AMATER, usuario.getUsuario_amater());
        values.put(Util_Usuario.CAMPO_CODIGO_USU, usuario.getUsuario_codigo());
        values.put(Util_Usuario.CAMPO_PASS_ENCRIP, usuario.getUsuario_password());
        values.put(Util_Usuario.CAMPO_PASS_DESENCRIP, usuario.getPassword_desc());
        values.put(Util_Usuario.CAMPO_ID_NIVEL, usuario.getId_nivel());
        values.put(Util_Usuario.CAMPO_CODIGO, usuario.getCodigo());

        Long idResultante = db.insert(Util_Usuario.TABLA_USUARIO, Util_Usuario.CAMPO_ID,values);
        Log.e("Registro","Se registro usuario "+ idResultante);
    }
}
