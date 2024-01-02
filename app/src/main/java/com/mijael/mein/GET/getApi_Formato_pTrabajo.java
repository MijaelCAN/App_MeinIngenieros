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
import com.mijael.mein.Entidades.Formatos_Trabajo;
import com.mijael.mein.HELPER.FormatoTrabajoSQLiteHelper;
import com.mijael.mein.Utilidades.Util_Formato_pTrabajo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class getApi_Formato_pTrabajo {
    RequestQueue queue;
    Context context;
    private String URL = "https://test.meiningenieros.pe/index.php?/Apk/lista_formatos_ptrabajo";
    public getApi_Formato_pTrabajo(Context context){
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
                                String id_pt_formato = jsonObject.getString("id_pt_formato");
                                String id_plan_trabajo = jsonObject.getString("id_plan_trabajo");
                                String id_formato = jsonObject.getString("id_formato");
                                String nom_formato = jsonObject.getString("nom_formato");
                                String cantidad = jsonObject.getString("cantidad");
                                String nom_cliente = jsonObject.getString("nom_cliente");
                                String realizado = jsonObject.getString("realizado");
                                String por_realizar = jsonObject.getString("por_realizar");
                                String valida_gremision = jsonObject.getString("valida_gremision");

                                // Instanciar Orde_Trabajo para retornar al registro de SQLite
                                Formatos_Trabajo formatos_pTrabajo = new Formatos_Trabajo(Integer.valueOf(id_pt_formato),Integer.valueOf(id_plan_trabajo),Integer.valueOf(id_formato),nom_formato,
                                        Integer.valueOf(cantidad),nom_cliente,Integer.valueOf(realizado),Integer.valueOf(por_realizar),Integer.valueOf(valida_gremision));
                                registrarFormatoTrabajo(formatos_pTrabajo);
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

    private void registrarFormatoTrabajo(Formatos_Trabajo formatos_Trabajo) {
        //Log.e("Preuba2", "Entro a RegistrarOrdenTrabajo");
        FormatoTrabajoSQLiteHelper con = FormatoTrabajoSQLiteHelper.getInstance(context);//Instancia para la conexion a la Base de datos de SQLite
        SQLiteDatabase db = con.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util_Formato_pTrabajo.CAMPO_ID_PT_FORMATO,formatos_Trabajo.getId_pt_formato());
        values.put(Util_Formato_pTrabajo.CAMPO_ID_PLAN_TRABAJO,formatos_Trabajo.getId_plan_trabajo());
        values.put(Util_Formato_pTrabajo.CAMPO_ID_FORMATO,formatos_Trabajo.getId_formato());
        values.put(Util_Formato_pTrabajo.CAMPO_NOM_FORMATO,formatos_Trabajo.getNom_formato());
        values.put(Util_Formato_pTrabajo.CAMPO_CANTIDAD,formatos_Trabajo.getCantidad());
        values.put(Util_Formato_pTrabajo.CAMPO_NOM_CLIENTE,formatos_Trabajo.getNom_cliente());
        values.put(Util_Formato_pTrabajo.CAMPO_REALIZADO,formatos_Trabajo.getRealizado());
        values.put(Util_Formato_pTrabajo.CAMPO_POR_REALIZAR,formatos_Trabajo.getPor_realizar());
        values.put(Util_Formato_pTrabajo.CAMPO_VALIDA_GREMISION,formatos_Trabajo.getValida_gremision());



        Long idResultante = db.insert(Util_Formato_pTrabajo.TABLA_FORMATO_TRABAJO, Util_Formato_pTrabajo.CAMPO_ID_PT_FORMATO,values);
        //Log.e("Registro","Se registro usuario "+ idResultante);
    }

}
