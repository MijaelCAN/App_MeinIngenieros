package com.mijael.mein.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mijael.mein.Entidades.Equipos;
import com.mijael.mein.Entidades.Formatos_Trabajo;
import com.mijael.mein.Entidades.Orden_Trabajo;
import com.mijael.mein.HELPER.FormatoTrabajoSQLiteHelper;
import com.mijael.mein.Utilidades.Util_Equipos;
import com.mijael.mein.Utilidades.Util_Formato_pTrabajo;
import com.mijael.mein.Utilidades.Util_OrdenTrabajo;

import java.util.ArrayList;
import java.util.List;

public class DAO_FormatosTrabajo {
    private FormatoTrabajoSQLiteHelper databaseHelper;
    public DAO_FormatosTrabajo(Context context){
        databaseHelper = FormatoTrabajoSQLiteHelper.getInstance(context);
    }

    // LISTAR FORMATOS DE TRABAJOS
    public List<Formatos_Trabajo> obtenerFormatosTrabajo(int id_plan_trabajo){
        List<Formatos_Trabajo> listFormatos = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ Util_Formato_pTrabajo.TABLA_FORMATO_TRABAJO +" WHERE " + Util_Formato_pTrabajo.CAMPO_ID_PLAN_TRABAJO +" = " +id_plan_trabajo, null);

        if(cursor.moveToFirst()){
            do{
                Formatos_Trabajo formato = new Formatos_Trabajo(
                        cursor.getInt(cursor.getColumnIndex(Util_Formato_pTrabajo.CAMPO_ID_PT_FORMATO)),
                        cursor.getInt(cursor.getColumnIndex(Util_Formato_pTrabajo.CAMPO_ID_PLAN_TRABAJO)),
                        cursor.getInt(cursor.getColumnIndex(Util_Formato_pTrabajo.CAMPO_ID_FORMATO)),
                        cursor.getString(cursor.getColumnIndex(Util_Formato_pTrabajo.CAMPO_NOM_FORMATO)),
                        cursor.getInt(cursor.getColumnIndex(Util_Formato_pTrabajo.CAMPO_CANTIDAD)),
                        cursor.getString(cursor.getColumnIndex(Util_Formato_pTrabajo.CAMPO_NOM_CLIENTE)),
                        cursor.getInt(cursor.getColumnIndex(Util_Formato_pTrabajo.CAMPO_REALIZADO)),
                        cursor.getInt(cursor.getColumnIndex(Util_Formato_pTrabajo.CAMPO_POR_REALIZAR)),
                        cursor.getInt(cursor.getColumnIndex(Util_Formato_pTrabajo.CAMPO_VALIDA_GREMISION))
                );
                listFormatos.add(formato);
            }while (cursor.moveToNext());
        }
        cursor.close();
        //db.close();
        return listFormatos;
    }

    public void actualizarFormatoTrabajo(Formatos_Trabajo formatoTrabajo) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();

        valores.put(Util_Formato_pTrabajo.CAMPO_ID_PLAN_TRABAJO, formatoTrabajo.getId_plan_trabajo());
        valores.put(Util_Formato_pTrabajo.CAMPO_ID_FORMATO, formatoTrabajo.getId_formato());
        valores.put(Util_Formato_pTrabajo.CAMPO_NOM_FORMATO, formatoTrabajo.getNom_formato());
        valores.put(Util_Formato_pTrabajo.CAMPO_CANTIDAD, formatoTrabajo.getCantidad());
        valores.put(Util_Formato_pTrabajo.CAMPO_NOM_CLIENTE, formatoTrabajo.getNom_cliente());
        valores.put(Util_Formato_pTrabajo.CAMPO_REALIZADO, formatoTrabajo.getRealizado());
        valores.put(Util_Formato_pTrabajo.CAMPO_POR_REALIZAR, formatoTrabajo.getPor_realizar());
        valores.put(Util_Formato_pTrabajo.CAMPO_VALIDA_GREMISION, formatoTrabajo.getValida_gremision());

        String whereClause = Util_Formato_pTrabajo.CAMPO_ID_PT_FORMATO + "= ?"; // suponiendo que el identificador sea 'id'
        String[] whereArgs = { String.valueOf(formatoTrabajo.getId_pt_formato()) };

        long resultado = db.update(Util_Formato_pTrabajo.TABLA_FORMATO_TRABAJO, valores, whereClause, whereArgs);
        if (resultado == -1) {
            Log.e("TAG-E", "Error al actualizar FORMATOS TRABAJO en la base de datos local");
        } else {
            Log.d("TAG-D", "FORMATOS TRABAJO actualizado en la base de datos local");
        }
        //db.close();
    }

    public int contarFormatos() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = null;
        int count = 0;

        try {
            cursor = db.rawQuery("SELECT COUNT(*) FROM " + Util_Formato_pTrabajo.TABLA_FORMATO_TRABAJO, null);
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
    public void insertarFormatos(List<Formatos_Trabajo> formatosList) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        try {
            db.beginTransaction();
            ContentValues valores;

            for (Formatos_Trabajo formatos : formatosList) {
                valores = new ContentValues();
                // Configurar los valores para la inserción
                valores.put(Util_Formato_pTrabajo.CAMPO_ID_PT_FORMATO, formatos.getId_pt_formato());
                valores.put(Util_Formato_pTrabajo.CAMPO_ID_PLAN_TRABAJO, formatos.getId_plan_trabajo());
                valores.put(Util_Formato_pTrabajo.CAMPO_ID_FORMATO, formatos.getId_formato());
                valores.put(Util_Formato_pTrabajo.CAMPO_NOM_FORMATO, formatos.getNom_formato());
                valores.put(Util_Formato_pTrabajo.CAMPO_CANTIDAD, formatos.getCantidad());
                valores.put(Util_Formato_pTrabajo.CAMPO_NOM_CLIENTE, formatos.getNom_cliente());
                valores.put(Util_Formato_pTrabajo.CAMPO_REALIZADO, formatos.getRealizado());
                valores.put(Util_Formato_pTrabajo.CAMPO_POR_REALIZAR, formatos.getPor_realizar());
                valores.put(Util_Formato_pTrabajo.CAMPO_VALIDA_GREMISION, formatos.getValida_gremision());

                // Insertar el cine en la base de datos
                long resultado = db.insert(Util_Formato_pTrabajo.TABLA_FORMATO_TRABAJO, null, valores);
                if(resultado == -1){
                    Log.e("TAG-E", "Error al insertar FORMATOS TRABAJO en la base de datos local");
                } else {
                    Log.d("TAG-D", "FORMATOS TRABAJO insertado en la base de datos local");
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
    public boolean verificarExistenciaFormato(int idFormato) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(Util_Formato_pTrabajo.TABLA_FORMATO_TRABAJO, null,
                    Util_Formato_pTrabajo.CAMPO_ID_PT_FORMATO + " = ?", new String[]{String.valueOf(idFormato)},
                    null, null, null);

            return cursor != null && cursor.getCount() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (cursor != null) {
                //cursor.close();
            }
        }
    }

    public void insertarFormatoTrabajo(Formatos_Trabajo formatoTrabajo) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(Util_Formato_pTrabajo.CAMPO_ID_PT_FORMATO, formatoTrabajo.getId_pt_formato());
        valores.put(Util_Formato_pTrabajo.CAMPO_ID_PLAN_TRABAJO, formatoTrabajo.getId_plan_trabajo());
        valores.put(Util_Formato_pTrabajo.CAMPO_ID_FORMATO, formatoTrabajo.getId_formato());
        valores.put(Util_Formato_pTrabajo.CAMPO_NOM_FORMATO, formatoTrabajo.getNom_formato());
        valores.put(Util_Formato_pTrabajo.CAMPO_CANTIDAD, formatoTrabajo.getCantidad());
        valores.put(Util_Formato_pTrabajo.CAMPO_NOM_CLIENTE, formatoTrabajo.getNom_cliente());
        valores.put(Util_Formato_pTrabajo.CAMPO_REALIZADO, formatoTrabajo.getRealizado());
        valores.put(Util_Formato_pTrabajo.CAMPO_POR_REALIZAR, formatoTrabajo.getPor_realizar());
        valores.put(Util_Formato_pTrabajo.CAMPO_VALIDA_GREMISION, formatoTrabajo.getValida_gremision());

        long resultado = db.insert(Util_Formato_pTrabajo.TABLA_FORMATO_TRABAJO, null, valores);

        if(resultado == -1){
            Log.e("TAG-E", "Error al insertar FORMATO TRABAJO en la base de datos local");
        } else {
            Log.d("TAG-D", "FROMATO TRABAJO insertado en la base de datos local");
        }
        //db.close();
    }

    //BUSCAR UN FORMATO DE TRABAJO
    public Formatos_Trabajo Buscar(String idPlanTrabajo, String idFformato){
        Formatos_Trabajo formato = null;
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Util_Formato_pTrabajo.TABLA_FORMATO_TRABAJO + " WHERE "+Util_Formato_pTrabajo.CAMPO_ID_PLAN_TRABAJO+" = ? and "+ Util_Formato_pTrabajo.CAMPO_ID_FORMATO +" = ?", new String[]{idPlanTrabajo,idFformato});

        if(cursor.moveToFirst()){
            do{
                formato = new Formatos_Trabajo(
                        cursor.getInt(cursor.getColumnIndex(Util_Formato_pTrabajo.CAMPO_ID_PT_FORMATO)),
                        cursor.getInt(cursor.getColumnIndex(Util_Formato_pTrabajo.CAMPO_ID_PLAN_TRABAJO)),
                        cursor.getInt(cursor.getColumnIndex(Util_Formato_pTrabajo.CAMPO_ID_FORMATO)),
                        cursor.getString(cursor.getColumnIndex(Util_Formato_pTrabajo.CAMPO_NOM_FORMATO)),
                        cursor.getInt(cursor.getColumnIndex(Util_Formato_pTrabajo.CAMPO_CANTIDAD)),
                        cursor.getString(cursor.getColumnIndex(Util_Formato_pTrabajo.CAMPO_NOM_CLIENTE)),
                        cursor.getInt(cursor.getColumnIndex(Util_Formato_pTrabajo.CAMPO_REALIZADO)),
                        cursor.getInt(cursor.getColumnIndex(Util_Formato_pTrabajo.CAMPO_POR_REALIZAR)),
                        cursor.getInt(cursor.getColumnIndex(Util_Formato_pTrabajo.CAMPO_VALIDA_GREMISION))

                );
            }while (cursor.moveToNext());
        }
        cursor.close();
        //db.close();
        return formato;
    }


}
