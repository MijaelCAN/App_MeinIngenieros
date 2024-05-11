package com.mijael.mein.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mijael.mein.Entidades.Orden_Trabajo;
import com.mijael.mein.Entidades.Usuario;
import com.mijael.mein.HELPER.OrdenTrabajoSQLiteHelper;
import com.mijael.mein.Utilidades.Util_OrdenTrabajo;
import com.mijael.mein.Utilidades.Util_Usuario;

import java.util.ArrayList;
import java.util.List;

public class DAO_OrdenTrabajo {
    private OrdenTrabajoSQLiteHelper databaseHelper;

    public DAO_OrdenTrabajo(Context context) {
        databaseHelper = OrdenTrabajoSQLiteHelper.getInstance(context);
    }

    public List<Orden_Trabajo> obtenerOrdenesTrabajo(int idColaborador) {
        List<Orden_Trabajo> ordenesTrabajo = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Util_OrdenTrabajo.TABLA_ORDEN_TRABAJO +
                        " WHERE id_colaborador LIKE ? OR id_colaborador LIKE ? OR id_colaborador LIKE ? OR id_colaborador LIKE ? OR id_coordinador = " + idColaborador +
                        " ORDER BY _id DESC ",
                new String[]{idColaborador + ",%", "%," + idColaborador + ",%", "%," + idColaborador, ""+idColaborador});

        //Cursor cursor = db.rawQuery("SELECT * FROM "+ Util_OrdenTrabajo.TABLA_ORDEN_TRABAJO + " WHERE id_colaborador LIKE '%"+ idColaborador + ",%' OR  id_colaborador LIKE '%,"+ idColaborador + ",%' OR  id_colaborador LIKE '%,"+ idColaborador +"')", null);
        //String query = "SELECT * FROM " + Util_OrdenTrabajo.TABLA_ORDEN_TRABAJO + " WHERE id_colaborador LIKE '%" + idColaborador + ",%' OR id_colaborador LIKE '%," + idColaborador + ",%' OR id_colaborador LIKE '%," + idColaborador + "')";
        //Cursor cursor = db.rawQuery("SELECT * FROM orden_trabajo WHERE id_colaborador IN ("+ idColaborador + ") OR id_coordinador = " + idColaborador , null);
        if (cursor.moveToFirst()) {
            do {
                Orden_Trabajo orden = new Orden_Trabajo(
                        cursor.getString(cursor.getColumnIndex("_id")),
                        cursor.getString(cursor.getColumnIndex("cod_cotizacion")),
                        cursor.getString(cursor.getColumnIndex("nom_cliente")),
                        cursor.getString(cursor.getColumnIndex("nom_tipo_servicio")),
                        cursor.getString(cursor.getColumnIndex("fecha_registro")),
                        cursor.getString(cursor.getColumnIndex("id_ot")),
                        cursor.getString(cursor.getColumnIndex("id_plan_trabajo")),
                        cursor.getString(cursor.getColumnIndex("dformatos")),
                        cursor.getString(cursor.getColumnIndex("id_colaborador")),
                        cursor.getString(cursor.getColumnIndex("id_coordinador")),
                        cursor.getString(cursor.getColumnIndex("url_plan_trabajo"))
                );
                ordenesTrabajo.add(orden);
            } while (cursor.moveToNext());
        }

        cursor.close();
        //db.close();
        return ordenesTrabajo;
    }

    public void ActualizarOrdenes(Orden_Trabajo ordenes) {
        Log.e("ENTRADA4","ENTRO A ActualizaroRDEN()");
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(Util_OrdenTrabajo.CAMPO_COD_OT, ordenes.getCod_ot());
        values.put(Util_OrdenTrabajo.CAMPO_COD_COTIZACION,ordenes.getCod_cotizacion());
        values.put(Util_OrdenTrabajo.CAMPO_NOM_CLIENTE, ordenes.getNom_cliente());
        values.put(Util_OrdenTrabajo.CAMPO_NOM_TIPO_SERVICIO, ordenes.getNom_tipo_servicio());
        values.put(Util_OrdenTrabajo.CAMPO_FECHA_REGISTRO, ordenes.getFecha_registro());
        values.put(Util_OrdenTrabajo.CAMPO_ID_OT, ordenes.getId_ot());
        values.put(Util_OrdenTrabajo.CAMPO_ID_PLAN_TRABAJO, ordenes.getId_plan_trabajo());
        values.put(Util_OrdenTrabajo.CAMPO_DOFRMATOS, ordenes.getDformatos());
        values.put(Util_OrdenTrabajo.CAMPO_ID_COLABORADOR, ordenes.getId_colaborador());
        values.put(Util_OrdenTrabajo.CAMPO_ID_COORDINADOR, ordenes.getId_coordinador());
        values.put(Util_OrdenTrabajo.CAMPO_URL_PLAN_TRABAJO, ordenes.getUrl_plan_trabajo());

        String whereClause = Util_OrdenTrabajo.CAMPO_COD_OT + "=?";
        String[] whereArgs = {String.valueOf(ordenes.getCod_cotizacion())};

        int resultado = db.update(Util_OrdenTrabajo.TABLA_ORDEN_TRABAJO, values, whereClause, whereArgs);

        if (resultado == -1) {
            Log.e("TAG-E", "Error al actualizar ORDEN TRABAJO en la base de datos local");
        } else {
            Log.d("TAG-D", "ORDEN TRABAJO actualizado en la base de datos local");
        }

        //db.close();
    }
    public int contarOrdenes() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = null;
        int count = 0;

        try {
            cursor = db.rawQuery("SELECT COUNT(*) FROM " + Util_OrdenTrabajo.TABLA_ORDEN_TRABAJO, null);
            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null && db.isOpen()) {
                //db.close(); // Cerrar la base de datos si está abierta
            }
        }
        return count;
    }
    public void insertarOrdenes(List<Orden_Trabajo> ordenesList) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        try {
            db.beginTransaction();
            ContentValues values;

            for (Orden_Trabajo ordenes : ordenesList) {
                values = new ContentValues();
                // Configurar los valores para la inserción
                values.put(Util_OrdenTrabajo.CAMPO_COD_OT, ordenes.getCod_ot());
                values.put(Util_OrdenTrabajo.CAMPO_COD_COTIZACION,ordenes.getCod_cotizacion());
                values.put(Util_OrdenTrabajo.CAMPO_NOM_CLIENTE, ordenes.getNom_cliente());
                values.put(Util_OrdenTrabajo.CAMPO_NOM_TIPO_SERVICIO, ordenes.getNom_tipo_servicio());
                values.put(Util_OrdenTrabajo.CAMPO_FECHA_REGISTRO, ordenes.getFecha_registro());
                values.put(Util_OrdenTrabajo.CAMPO_ID_OT, ordenes.getId_ot());
                values.put(Util_OrdenTrabajo.CAMPO_ID_PLAN_TRABAJO, ordenes.getId_plan_trabajo());
                values.put(Util_OrdenTrabajo.CAMPO_DOFRMATOS, ordenes.getDformatos());
                values.put(Util_OrdenTrabajo.CAMPO_ID_COLABORADOR, ordenes.getId_colaborador());
                values.put(Util_OrdenTrabajo.CAMPO_ID_COORDINADOR, ordenes.getId_coordinador());
                values.put(Util_OrdenTrabajo.CAMPO_URL_PLAN_TRABAJO, ordenes.getUrl_plan_trabajo());

                // Insertar el cine en la base de datos
                long resultado = db.insert(Util_OrdenTrabajo.TABLA_ORDEN_TRABAJO, null, values);
                if(resultado == -1){
                    Log.e("TAG-E", "Error al insertar ORDENES TRABAJO en la base de datos local");
                } else {
                    Log.d("TAG-D", "ORDENES TRABAJO insertado en la base de datos local");
                }
            }
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (db != null && db.isOpen()) {
                db.endTransaction(); // Finalizar la transacción si aún está abierta
                //db.close(); // Cerrar la base de datos
            }
        }
    }
    public boolean verificarExistenciaOrden(String idOrden) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(Util_OrdenTrabajo.TABLA_ORDEN_TRABAJO, null,
                    Util_OrdenTrabajo.CAMPO_COD_OT + " = ?", new String[]{String.valueOf(idOrden)},
                    null, null, null);

            return cursor != null && cursor.getCount() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
    public void insertarOrden(Orden_Trabajo orden){

        Log.e("PASO 2", "ENTRO A METODO INSERTAR ORDEN");
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util_OrdenTrabajo.CAMPO_COD_OT, orden.getCod_ot());
        values.put(Util_OrdenTrabajo.CAMPO_COD_COTIZACION,orden.getCod_cotizacion());
        values.put(Util_OrdenTrabajo.CAMPO_NOM_CLIENTE, orden.getNom_cliente());
        values.put(Util_OrdenTrabajo.CAMPO_NOM_TIPO_SERVICIO, orden.getNom_tipo_servicio());
        values.put(Util_OrdenTrabajo.CAMPO_FECHA_REGISTRO, orden.getFecha_registro());
        values.put(Util_OrdenTrabajo.CAMPO_ID_OT, orden.getId_ot());
        values.put(Util_OrdenTrabajo.CAMPO_ID_PLAN_TRABAJO, orden.getId_plan_trabajo());
        values.put(Util_OrdenTrabajo.CAMPO_DOFRMATOS, orden.getDformatos());
        values.put(Util_OrdenTrabajo.CAMPO_ID_COLABORADOR, orden.getId_colaborador());
        values.put(Util_OrdenTrabajo.CAMPO_ID_COORDINADOR, orden.getId_coordinador());
        values.put(Util_OrdenTrabajo.CAMPO_URL_PLAN_TRABAJO, orden.getUrl_plan_trabajo());

        long resultado = database.insert(Util_OrdenTrabajo.TABLA_ORDEN_TRABAJO,null,values);
        if(resultado == -1){
            Log.e("TAG-E", "Error al insertar ORDEN TRABAJO en la base de datos local");
        } else {
            Log.d("TAG-D", "ORDEN TRABAJO insertado en la base de datos local");
        }
        //database.close();
    }
    public Orden_Trabajo BuscarOrden(String cod_OT) {
        Orden_Trabajo orden = null;
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] columns = {
                Util_OrdenTrabajo.CAMPO_COD_OT,
                Util_OrdenTrabajo.CAMPO_COD_COTIZACION,
                Util_OrdenTrabajo.CAMPO_NOM_CLIENTE,
                Util_OrdenTrabajo.CAMPO_NOM_TIPO_SERVICIO,
                Util_OrdenTrabajo.CAMPO_FECHA_REGISTRO,
                Util_OrdenTrabajo.CAMPO_ID_OT,
                Util_OrdenTrabajo.CAMPO_ID_PLAN_TRABAJO,
                Util_OrdenTrabajo.CAMPO_DOFRMATOS,
                Util_OrdenTrabajo.CAMPO_ID_COLABORADOR,
                Util_OrdenTrabajo.CAMPO_ID_COORDINADOR,
                Util_OrdenTrabajo.CAMPO_URL_PLAN_TRABAJO
        };
        String selection = Util_OrdenTrabajo.CAMPO_COD_OT + " = ? ";
        String[] selectionArgs = {cod_OT};
        Cursor cursor = db.query(Util_OrdenTrabajo.TABLA_ORDEN_TRABAJO, columns, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            orden = new Orden_Trabajo(
                    cursor.getString(cursor.getColumnIndex("_id")),
                    cursor.getString(cursor.getColumnIndex("cod_cotizacion")),
                    cursor.getString(cursor.getColumnIndex("nom_cliente")),
                    cursor.getString(cursor.getColumnIndex("nom_tipo_servicio")),
                    cursor.getString(cursor.getColumnIndex("fecha_registro")),
                    cursor.getString(cursor.getColumnIndex("id_ot")),
                    cursor.getString(cursor.getColumnIndex("id_plan_trabajo")),
                    cursor.getString(cursor.getColumnIndex("dformatos")),
                    cursor.getString(cursor.getColumnIndex("id_colaborador")),
                    cursor.getString(cursor.getColumnIndex("id_coordinador")),
                    cursor.getString(cursor.getColumnIndex("url_plan_trabajo"))
            );
        }
        cursor.close();
        //db.close();
        return orden;
    }
}
