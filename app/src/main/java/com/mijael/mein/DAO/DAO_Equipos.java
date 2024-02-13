package com.mijael.mein.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mijael.mein.Entidades.Equipos;
import com.mijael.mein.HELPER.EquiposSQLiteHelper;
import com.mijael.mein.Utilidades.Util_Equipos;

import java.util.ArrayList;
import java.util.List;

public class DAO_Equipos {
    private EquiposSQLiteHelper dataHelper;

    public DAO_Equipos(Context context) {
        dataHelper = EquiposSQLiteHelper.getInstance(context);
    }

    public List<Equipos> listarEquipos(){
        List<Equipos> equiposList = new ArrayList<>();
        SQLiteDatabase db = dataHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Util_Equipos.TABLA_EQUIPOS , null);
        if(cursor.moveToFirst()){
            do{
                Equipos equipo = new Equipos(
                        cursor.getInt(cursor.getColumnIndex(Util_Equipos.CAMPO_ID_EQUIPO)),
                        cursor.getString(cursor.getColumnIndex(Util_Equipos.CAMPO_CODIGO_EQUIPO)),
                        cursor.getString(cursor.getColumnIndex(Util_Equipos.CAMPO_MODELO)),
                        cursor.getString(cursor.getColumnIndex(Util_Equipos.CAMPO_PROVEEDOR)),
                        cursor.getString(cursor.getColumnIndex(Util_Equipos.CAMPO_SERIE)),
                        cursor.getString(cursor.getColumnIndex(Util_Equipos.CAMPO_CODIGO)),
                        cursor.getInt(cursor.getColumnIndex(Util_Equipos.CAMPO_CANTIDAD)),
                        cursor.getInt(cursor.getColumnIndex(Util_Equipos.CAMPO_ALQUILER)),
                        cursor.getString(cursor.getColumnIndex(Util_Equipos.CAMPO_OBSERVACIONES)),
                        cursor.getString(cursor.getColumnIndex(Util_Equipos.CAMPO_ESTADO)),
                        cursor.getString(cursor.getColumnIndex(Util_Equipos.CAMPO_NOM_MARCA)),
                        cursor.getString(cursor.getColumnIndex(Util_Equipos.CAMPO_NOMBRE)),
                        cursor.getString(cursor.getColumnIndex(Util_Equipos.CAMPO_NOM_MODELO)),
                        cursor.getString(cursor.getColumnIndex(Util_Equipos.CAMPO_COD_MONEDA)),
                        cursor.getString(cursor.getColumnIndex(Util_Equipos.CAMPO_VIGENCIA)),
                        cursor.getString(cursor.getColumnIndex(Util_Equipos.CAMPO_FECHA_CALIBRA)),
                        cursor.getString(cursor.getColumnIndex(Util_Equipos.CAMPO_VENCIMIENTO))
                );
                equiposList.add(equipo);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return equiposList;
    }

        public void actualizarEquipo(Equipos equipo) {
        SQLiteDatabase db = dataHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

            //values.put(Util_Equipos.CAMPO_ID_EQUIPO,equipo.getId_equipo_registro());
            values.put(Util_Equipos.CAMPO_CODIGO,equipo.getCod_equipo());
            values.put(Util_Equipos.CAMPO_MODELO,equipo.getModelo());
            values.put(Util_Equipos.CAMPO_PROVEEDOR,equipo.getProveedor());
            values.put(Util_Equipos.CAMPO_SERIE,equipo.getSerie());
            values.put(Util_Equipos.CAMPO_CODIGO,equipo.getCodigo());
            values.put(Util_Equipos.CAMPO_CANTIDAD,equipo.getCantidad());
            values.put(Util_Equipos.CAMPO_ALQUILER,equipo.getAlquiler());
            values.put(Util_Equipos.CAMPO_OBSERVACIONES,equipo.getObservaciones());
            values.put(Util_Equipos.CAMPO_ESTADO,equipo.getEstado());
            values.put(Util_Equipos.CAMPO_NOM_MARCA,equipo.getNom_marca());
            values.put(Util_Equipos.CAMPO_NOMBRE,equipo.getNombre());
            values.put(Util_Equipos.CAMPO_NOM_MODELO,equipo.getNom_modelo());
            values.put(Util_Equipos.CAMPO_COD_MONEDA,equipo.getCod_monedaf());
            values.put(Util_Equipos.CAMPO_VIGENCIA,equipo.getVigencia());
            values.put(Util_Equipos.CAMPO_FECHA_CALIBRA,equipo.getFecha_calibracion());
            values.put(Util_Equipos.CAMPO_VENCIMIENTO,equipo.getFecha_vencimiento());

        String whereClause = Util_Equipos.CAMPO_ID_EQUIPO + "= ?"; // suponiendo que el identificador sea 'id'
        String[] whereArgs = { String.valueOf(equipo.getId_equipo_registro()) };

        long resultado = db.update(Util_Equipos.TABLA_EQUIPOS, values, whereClause, whereArgs);
        if (resultado == -1) {
            Log.e("TAG-E", "Error al actualizar EQUIPOS en la base de datos local");
        } else {
            Log.d("TAG-D", "EQUIPOS actualizado en la base de datos local");
        }
        db.close();
    }

    public int contarEquipos() {
        SQLiteDatabase db = dataHelper.getReadableDatabase();
        Cursor cursor = null;
        int count = 0;

        try {
            cursor = db.rawQuery("SELECT COUNT(*) FROM " + Util_Equipos.TABLA_EQUIPOS, null);
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
                db.close(); // Cerrar la base de datos si está abierta
            }
        }
        return count;
    }
    public void insertarEquipos(List<Equipos> equiposList) {
        SQLiteDatabase db = dataHelper.getWritableDatabase();

        try {
            db.beginTransaction();
            ContentValues values;

            for (Equipos equipo : equiposList) {
                values = new ContentValues();
                // Configurar los valores para la inserción
                values.put(Util_Equipos.CAMPO_ID_EQUIPO,equipo.getId_equipo_registro());
                values.put(Util_Equipos.CAMPO_CODIGO_EQUIPO,equipo.getCod_equipo());
                values.put(Util_Equipos.CAMPO_MODELO,equipo.getModelo());
                values.put(Util_Equipos.CAMPO_PROVEEDOR,equipo.getProveedor());
                values.put(Util_Equipos.CAMPO_SERIE,equipo.getSerie());
                values.put(Util_Equipos.CAMPO_CODIGO,equipo.getCodigo());
                values.put(Util_Equipos.CAMPO_CANTIDAD,equipo.getCantidad());
                values.put(Util_Equipos.CAMPO_ALQUILER,equipo.getAlquiler());
                values.put(Util_Equipos.CAMPO_OBSERVACIONES,equipo.getObservaciones());
                values.put(Util_Equipos.CAMPO_ESTADO,equipo.getEstado());
                values.put(Util_Equipos.CAMPO_NOM_MARCA,equipo.getNom_marca());
                values.put(Util_Equipos.CAMPO_NOMBRE,equipo.getNombre());
                values.put(Util_Equipos.CAMPO_NOM_MODELO,equipo.getNom_modelo());
                values.put(Util_Equipos.CAMPO_COD_MONEDA,equipo.getCod_monedaf());
                values.put(Util_Equipos.CAMPO_VIGENCIA,equipo.getVigencia());
                values.put(Util_Equipos.CAMPO_FECHA_CALIBRA,equipo.getFecha_calibracion());
                values.put(Util_Equipos.CAMPO_VENCIMIENTO,equipo.getFecha_vencimiento());

                // Insertar el cine en la base de datos
                long resultado = db.insert(Util_Equipos.TABLA_EQUIPOS, null, values);
                if(resultado == -1){
                    Log.e("TAG-E", "Error al insertar EQUIPOS en la base de datos local");
                } else {
                    Log.d("TAG-D", "EQUIPOS insertado en la base de datos local");
                }
            }
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (db != null && db.isOpen()) {
                db.endTransaction(); // Finalizar la transacción si aún está abierta
                db.close(); // Cerrar la base de datos
            }
        }
    }
    /*public boolean verificarExistenciaEquipo(int idEquipo) {
        SQLiteDatabase db = dataHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(Util_Equipos.TABLA_EQUIPOS, null,
                    Util_Equipos.CAMPO_ID_EQUIPO + " = ?", new String[]{String.valueOf(idEquipo)},
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
    }*/
    public void insertarEquipo(Equipos equipo) {
        SQLiteDatabase db = dataHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util_Equipos.CAMPO_ID_EQUIPO,equipo.getId_equipo_registro());
        values.put(Util_Equipos.CAMPO_CODIGO,equipo.getCod_equipo());
        values.put(Util_Equipos.CAMPO_MODELO,equipo.getModelo());
        values.put(Util_Equipos.CAMPO_PROVEEDOR,equipo.getProveedor());
        values.put(Util_Equipos.CAMPO_SERIE,equipo.getSerie());
        values.put(Util_Equipos.CAMPO_CODIGO,equipo.getCodigo());
        values.put(Util_Equipos.CAMPO_CANTIDAD,equipo.getCantidad());
        values.put(Util_Equipos.CAMPO_ALQUILER,equipo.getAlquiler());
        values.put(Util_Equipos.CAMPO_OBSERVACIONES,equipo.getObservaciones());
        values.put(Util_Equipos.CAMPO_ESTADO,equipo.getEstado());
        values.put(Util_Equipos.CAMPO_NOM_MARCA,equipo.getNom_marca());
        values.put(Util_Equipos.CAMPO_NOMBRE,equipo.getNombre());
        values.put(Util_Equipos.CAMPO_NOM_MODELO,equipo.getNom_modelo());
        values.put(Util_Equipos.CAMPO_COD_MONEDA,equipo.getCod_monedaf());
        values.put(Util_Equipos.CAMPO_VIGENCIA,equipo.getVigencia());
        values.put(Util_Equipos.CAMPO_FECHA_CALIBRA,equipo.getFecha_calibracion());
        values.put(Util_Equipos.CAMPO_VENCIMIENTO,equipo.getFecha_vencimiento());

        Long idResultante = db.insert(Util_Equipos.TABLA_EQUIPOS, Util_Equipos.CAMPO_ID_EQUIPO,values);

        db.close();
    }

    public List<String> obtener_CodEquipos(){
        List<String> codigosList = new ArrayList<>();
        SQLiteDatabase db = dataHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT "+ Util_Equipos.CAMPO_CODIGO +" FROM "+ Util_Equipos.TABLA_EQUIPOS, null);

        if(cursor.moveToFirst()){
            do{
                codigosList.add(cursor.getString(cursor.getColumnIndex(Util_Equipos.CAMPO_CODIGO)));
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return codigosList;
    }

    public Equipos Buscar(String cod_equipo){
        Equipos equipo = null;
        SQLiteDatabase db = dataHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Util_Equipos.TABLA_EQUIPOS + " WHERE codigo = ?", new String[]{cod_equipo});

        if(cursor.moveToFirst()){
            do{
                equipo = new Equipos(
                        cursor.getInt(cursor.getColumnIndex(Util_Equipos.CAMPO_ID_EQUIPO)),
                        cursor.getString(cursor.getColumnIndex(Util_Equipos.CAMPO_CODIGO_EQUIPO)),
                        cursor.getString(cursor.getColumnIndex(Util_Equipos.CAMPO_MODELO)),
                        cursor.getString(cursor.getColumnIndex(Util_Equipos.CAMPO_PROVEEDOR)),
                        cursor.getString(cursor.getColumnIndex(Util_Equipos.CAMPO_SERIE)),
                        cursor.getString(cursor.getColumnIndex(Util_Equipos.CAMPO_CODIGO)),
                        cursor.getInt(cursor.getColumnIndex(Util_Equipos.CAMPO_CANTIDAD)),
                        cursor.getInt(cursor.getColumnIndex(Util_Equipos.CAMPO_ALQUILER)),
                        cursor.getString(cursor.getColumnIndex(Util_Equipos.CAMPO_OBSERVACIONES)),
                        cursor.getString(cursor.getColumnIndex(Util_Equipos.CAMPO_ESTADO)),
                        cursor.getString(cursor.getColumnIndex(Util_Equipos.CAMPO_NOM_MARCA)),
                        cursor.getString(cursor.getColumnIndex(Util_Equipos.CAMPO_NOMBRE)),
                        cursor.getString(cursor.getColumnIndex(Util_Equipos.CAMPO_NOM_MODELO)),
                        cursor.getString(cursor.getColumnIndex(Util_Equipos.CAMPO_COD_MONEDA)),
                        cursor.getString(cursor.getColumnIndex(Util_Equipos.CAMPO_VIGENCIA)),
                        cursor.getString(cursor.getColumnIndex(Util_Equipos.CAMPO_FECHA_CALIBRA)),
                        cursor.getString(cursor.getColumnIndex(Util_Equipos.CAMPO_VENCIMIENTO))

                );
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return equipo;
    }

}
