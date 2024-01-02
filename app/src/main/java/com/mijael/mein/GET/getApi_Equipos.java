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
import com.mijael.mein.Entidades.Equipos;
import com.mijael.mein.HELPER.EquiposSQLiteHelper;
import com.mijael.mein.Utilidades.Util_Equipos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class getApi_Equipos {
    RequestQueue queue;
    private Context context;
    private EquiposSQLiteHelper dataHelper;
    private String URL = "https://sistema.meiningenieros.pe/index.php?/Apk/v_lista_equipo_formato";
    public getApi_Equipos(Context context){
        this.context = context;
        this.queue = Volley.newRequestQueue(context);
        dataHelper = EquiposSQLiteHelper.getInstance(context);
    }
    public void getRegistros(){
        //Log.e("XXXX","ENTRO A getRegistros");
        JsonArrayRequest ArrayEquipos = new JsonArrayRequest(
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
                                String idEquipo= jsonObject.getString("id_equipo_registro");
                                String cod_equipo = jsonObject.getString("cod_equipo");
                                String modelo = jsonObject.getString("modelo");
                                String proveedor = jsonObject.getString("proveedor");
                                String serie = jsonObject.getString("serie");
                                String codigo = jsonObject.getString("codigo");
                                String cantidad = jsonObject.getString("cantidad");
                                String alquiler = jsonObject.getString("alquiler");
                                String observaciones = jsonObject.getString("observaciones");
                                String estado = jsonObject.getString("estado");
                                String mom_marca = jsonObject.getString("nom_marca");
                                String nombre = jsonObject.getString("nombre");
                                String nom_modelo = jsonObject.getString("nom_modelo");
                                String cod_moneda = jsonObject.getString("cod_monedaf");
                                String vigencia = jsonObject.getString("vigencia");
                                String fecha_calibra = jsonObject.getString("fecha_calibracion");
                                String fecha_vencimiento = jsonObject.getString("fecha_vencimiento");
                                // Do something with the retrieved values
                                Equipos equipos = new Equipos(Integer.valueOf(idEquipo),cod_equipo,modelo,proveedor,serie,codigo,Integer.valueOf(cantidad),Float.valueOf(alquiler),
                                        observaciones, estado,mom_marca,nombre,nom_modelo,cod_moneda,vigencia,fecha_calibra,fecha_vencimiento);
                                registrarEquipos(equipos);
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
        queue.add(ArrayEquipos);
    }

    private void registrarEquipos(Equipos equipos) {
        SQLiteDatabase db = dataHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util_Equipos.CAMPO_ID_EQUIPO,equipos.getId_equipo_registro());
        values.put(Util_Equipos.CAMPO_CODIGO,equipos.getCod_equipo());
        values.put(Util_Equipos.CAMPO_MODELO,equipos.getModelo());
        values.put(Util_Equipos.CAMPO_PROVEEDOR,equipos.getProveedor());
        values.put(Util_Equipos.CAMPO_SERIE,equipos.getSerie());
        values.put(Util_Equipos.CAMPO_CODIGO,equipos.getCodigo());
        values.put(Util_Equipos.CAMPO_CANTIDAD,equipos.getCantidad());
        values.put(Util_Equipos.CAMPO_ALQUILER,equipos.getAlquiler());
        values.put(Util_Equipos.CAMPO_OBSERVACIONES,equipos.getObservaciones());
        values.put(Util_Equipos.CAMPO_ESTADO,equipos.getEstado());
        values.put(Util_Equipos.CAMPO_NOM_MARCA,equipos.getNom_marca());
        values.put(Util_Equipos.CAMPO_NOMBRE,equipos.getNombre());
        values.put(Util_Equipos.CAMPO_NOM_MODELO,equipos.getNom_modelo());
        values.put(Util_Equipos.CAMPO_COD_MONEDA,equipos.getCod_monedaf());
        values.put(Util_Equipos.CAMPO_VIGENCIA,equipos.getVigencia());
        values.put(Util_Equipos.CAMPO_FECHA_CALIBRA,equipos.getFecha_calibracion());
        values.put(Util_Equipos.CAMPO_VENCIMIENTO,equipos.getFecha_vencimiento());

        Long idResultante = db.insert(Util_Equipos.TABLA_EQUIPOS, Util_Equipos.CAMPO_ID_EQUIPO,values);

    }
}
