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
import com.mijael.mein.Entidades.Orden_Trabajo;
import com.mijael.mein.HELPER.OrdenTrabajoSQLiteHelper;
import com.mijael.mein.Utilidades.Util_OrdenTrabajo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class getApi_OrdenTrabajo {
    RequestQueue queue;
    private Context context;
    private String URL = "https://test.meiningenieros.pe/index.php?/Apk/v_lista_orden_trabajo";
    public getApi_OrdenTrabajo(Context context){
        this.context = context;
        this.queue = Volley.newRequestQueue(context);
    }
    public void getRegistros(){
        //Log.e("Preuba1", "Entro a getRegistros");
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int size = response.length();
                        Log.e("dato",String.valueOf(size));
                        for (int i = 0; i < size; i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String cod_ot = jsonObject.getString("cod_ot");
                                String cod_cotizacion = jsonObject.getString("cod_cotizacion");
                                String nom_cliente = jsonObject.getString("nom_cliente");
                                String nom_tipo_servicio = jsonObject.getString("nom_tipo_servicio");
                                String fecha_registro = jsonObject.getString("fecha_registro");
                                String id_ot = jsonObject.getString("id_ot");
                                String id_plan_trabajo = jsonObject.getString("id_plan_trabajo");
                                String dformatos = jsonObject.getString("dformatos");
                                String id_colaborador = jsonObject.getString("id_colaborador");
                                String id_coordinador = jsonObject.getString("id_coordinador");
                                String url_plan_trabajo = jsonObject.getString("url_plan_trabajo");
                                // Instanciar Orde_Trabajo para retornar al registro de SQLite
                                Orden_Trabajo ordenTrabajo = new Orden_Trabajo(cod_ot,cod_cotizacion,nom_cliente,nom_tipo_servicio,fecha_registro,id_ot,
                                        id_plan_trabajo,dformatos,id_colaborador,id_coordinador,url_plan_trabajo);
                                registrarOrdenTrabajo(ordenTrabajo);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                    }
                }
        );
        queue.add(jsonArrayRequest);
    }

    private void registrarOrdenTrabajo(Orden_Trabajo ordenTrabajo) {
        //Log.e("Preuba2", "Entro a RegistrarOrdenTrabajo");
        OrdenTrabajoSQLiteHelper con = OrdenTrabajoSQLiteHelper.getInstance(context);//Instancia para la conexion a la Base de datos de SQLite
        SQLiteDatabase db = con.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util_OrdenTrabajo.CAMPO_COD_OT,ordenTrabajo.getCod_ot());
        values.put(Util_OrdenTrabajo.CAMPO_COD_COTIZACION,ordenTrabajo.getCod_cotizacion());
        values.put(Util_OrdenTrabajo.CAMPO_NOM_CLIENTE,ordenTrabajo.getNom_cliente());
        values.put(Util_OrdenTrabajo.CAMPO_NOM_TIPO_SERVICIO,ordenTrabajo.getNom_tipo_servicio());
        values.put(Util_OrdenTrabajo.CAMPO_FECHA_REGISTRO,ordenTrabajo.getFecha_registro());
        values.put(Util_OrdenTrabajo.CAMPO_ID_OT,ordenTrabajo.getId_ot());
        values.put(Util_OrdenTrabajo.CAMPO_ID_PLAN_TRABAJO,ordenTrabajo.getId_plan_trabajo());
        values.put(Util_OrdenTrabajo.CAMPO_DOFRMATOS,ordenTrabajo.getDformatos());
        values.put(Util_OrdenTrabajo.CAMPO_ID_COLABORADOR,ordenTrabajo.getId_colaborador());
        values.put(Util_OrdenTrabajo.CAMPO_ID_COORDINADOR,ordenTrabajo.getId_coordinador());
        values.put(Util_OrdenTrabajo.CAMPO_URL_PLAN_TRABAJO,ordenTrabajo.getUrl_plan_trabajo());


        Long idResultante = db.insert(Util_OrdenTrabajo.TABLA_ORDEN_TRABAJO, Util_OrdenTrabajo.CAMPO_COD_OT,values);
        //Log.e("Registro","Se registro usuario "+ idResultante);
    }
}
