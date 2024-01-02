package com.mijael.mein.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mijael.mein.Entidades.Cines;
import com.mijael.mein.HELPER.CinesSQLiteHelper;
import com.mijael.mein.SERVICIOS.CinesService;
import com.mijael.mein.Utilidades.Util_Cines;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class DAO_Cines {
    private CinesSQLiteHelper dataHelper;
    public DAO_Cines(Context context) {
        dataHelper = CinesSQLiteHelper.getInstance(context);
    }
    public List<Cines> ListarCines(){
        List<Cines> listacines = new ArrayList<>();
        SQLiteDatabase db = dataHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ Util_Cines.TABLA_CINES, null);

        if(cursor.moveToFirst()){
            do{
                Cines cines = new Cines(
                        cursor.getInt(cursor.getColumnIndex(Util_Cines.CAMPO_ID)),
                        cursor.getString(cursor.getColumnIndex(Util_Cines.CAMPO_RAZON_SOCIAL)),
                        cursor.getInt(cursor.getColumnIndex(Util_Cines.CAMPO_SALAS)),
                        cursor.getInt(cursor.getColumnIndex(Util_Cines.CAMPO_ID_DISTRITO)),
                        cursor.getString(cursor.getColumnIndex(Util_Cines.CAMPO_DIRECCION)),
                        cursor.getString(cursor.getColumnIndex(Util_Cines.CAMPO_TELEFONOS)),
                        cursor.getString(cursor.getColumnIndex(Util_Cines.CAMPO_DETALLE))
                );
                listacines.add(cines);
            }while (cursor.moveToNext());
        }
        //cursor.close();
        //db.close();
        return listacines;
    }
    public void ActualizarCine(Cines cine) {
        Log.e("ENTRADA4","ENTRO A ActualizarCine()");
        SQLiteDatabase db = dataHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Util_Cines.CAMPO_RAZON_SOCIAL, cine.getRazonSocial());
        values.put(Util_Cines.CAMPO_SALAS, cine.getSalas());
        values.put(Util_Cines.CAMPO_ID_DISTRITO, cine.getIdDistrito());
        values.put(Util_Cines.CAMPO_DIRECCION, cine.getDireccion());
        values.put(Util_Cines.CAMPO_TELEFONOS, cine.getTelefonos());
        values.put(Util_Cines.CAMPO_DETALLE, cine.getDetalle());

        String whereClause = Util_Cines.CAMPO_ID + "=?";
        String[] whereArgs = {String.valueOf(cine.getId())};

        int resultado = db.update(Util_Cines.TABLA_CINES, values, whereClause, whereArgs);

        if (resultado == -1) {
            Log.e("TAG-E", "Error al actualizar cine en la base de datos local");
        } else {
            Log.d("TAG-D", "Cine actualizado en la base de datos local");
        }

        db.close();
    }
    public int contarCines() {
        SQLiteDatabase db = dataHelper.getReadableDatabase();
        Cursor cursor = null;
        int count = 0;

        try {
            cursor = db.rawQuery("SELECT COUNT(*) FROM " + Util_Cines.TABLA_CINES, null);
            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return count;
    }
    public void insertarCines(List<Cines> cinesList) {
        SQLiteDatabase db = dataHelper.getWritableDatabase();

        try {
            db.beginTransaction();
            ContentValues values;

            for (Cines cine : cinesList) {
                values = new ContentValues();
                // Configurar los valores para la inserción
                values.put(Util_Cines.CAMPO_RAZON_SOCIAL, cine.getRazonSocial());
                values.put(Util_Cines.CAMPO_SALAS, cine.getSalas());
                values.put(Util_Cines.CAMPO_ID_DISTRITO, cine.getIdDistrito());
                values.put(Util_Cines.CAMPO_DIRECCION, cine.getDireccion());
                values.put(Util_Cines.CAMPO_TELEFONOS, cine.getTelefonos());
                values.put(Util_Cines.CAMPO_DETALLE, cine.getDetalle());

                // Insertar el cine en la base de datos
                db.insert(Util_Cines.TABLA_CINES, null, values);
            }

            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }
    public boolean verificarExistenciaCine(int idCine) {
        SQLiteDatabase db = dataHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(Util_Cines.TABLA_CINES, null,
                    Util_Cines.CAMPO_ID + " = ?", new String[]{String.valueOf(idCine)},
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
    public void insertarCine(Cines cine){

        Log.e("PASO 2", "ENTRO A METODO INSERTAR");
        SQLiteDatabase database = dataHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util_Cines.CAMPO_RAZON_SOCIAL, cine.getRazonSocial());
        values.put(Util_Cines.CAMPO_SALAS, cine.getSalas());
        values.put(Util_Cines.CAMPO_ID_DISTRITO, cine.getIdDistrito());
        values.put(Util_Cines.CAMPO_DIRECCION, cine.getDireccion());
        values.put(Util_Cines.CAMPO_TELEFONOS, cine.getTelefonos());
        values.put(Util_Cines.CAMPO_DETALLE, cine.getDetalle());

        long resultado = database.insert(Util_Cines.TABLA_CINES,null,values);
        if(resultado == -1){
            Log.e("TAG-E", "Error al insertar Cines en la base de datos local");
        } else {
            Log.d("TAG-D", "Cines insertados en la base de datos local");
        }
    }
















    public void guardarCinesActualizadosLocalmente(List<Cines> cinesActualizados) {
        SQLiteDatabase db = dataHelper.getWritableDatabase();

        for (Cines cine : cinesActualizados) {
            ContentValues values = new ContentValues();
            values.put(Util_Cines.CAMPO_RAZON_SOCIAL, cine.getRazonSocial());
            values.put(Util_Cines.CAMPO_SALAS, cine.getSalas());
            values.put(Util_Cines.CAMPO_ID_DISTRITO, cine.getIdDistrito());
            values.put(Util_Cines.CAMPO_DIRECCION, cine.getDireccion());
            values.put(Util_Cines.CAMPO_TELEFONOS, cine.getTelefonos());
            values.put(Util_Cines.CAMPO_DETALLE, cine.getDetalle());

            long resultado = db.insert(Util_Cines.TABLA_CINES, null, values);

            if (resultado == -1) {
                Log.e("TAG-E", "Error al insertar cines en la base de datos local");
            } else {
                Log.d("TAG-D", "Cines insertados en la base de datos local");
            }
        }

        db.close();
    }
    public List<Cines>  actualizarCinesLocalmente(List<Cines> cinesLocales, List<Cines> cinesServidor, CinesService cinesService) throws IOException {
        Log.e("ENTRADA3","COMPARA SI HAY CAMBIOS");
        // Crear una lista para almacenar los cines actualizados
        List<Cines> cinesActualizados = new ArrayList<>();

        // Recorrer los cines del servidor y actualizar los cines locales si es necesario
        for (Cines itemcine : cinesServidor) {
            Cines cineLocal = null;

            // Buscar el cine local por su ID
            for (Cines cine : cinesLocales) {
                if (cine.getId() == itemcine.getId()) {
                    cineLocal = cine;
                    break;
                }
            }

            if (cineLocal == null) {
                // Si el cine no existe en la tabla local, agregarlo a la lista de cines actualizados
                cinesActualizados.add(itemcine);
            } else if (!cineLocal.equals(itemcine)) {
                // Si el cine existe en la tabla local pero es diferente al cine del servidor, actualizarlo en la tabla local
                Call<Cines> call = cinesService.updateCine(cineLocal.getId(), itemcine);
                Response<Cines> response = call.execute();

                if (response.isSuccessful()) {
                    // Si la actualización fue exitosa, actualizar el cine local
                    cineLocal.setRazonSocial(itemcine.getRazonSocial());
                    cineLocal.setSalas(itemcine.getSalas());
                    cineLocal.setIdDistrito(itemcine.getIdDistrito());
                    cineLocal.setDireccion(itemcine.getDireccion());
                    cineLocal.setTelefonos(itemcine.getTelefonos());
                    cineLocal.setDetalle(itemcine.getDetalle());
                    ActualizarCine(cineLocal);

                } else {
                    // Si la actualización falló, agregar el cine del servidor a la lista de cines actualizados
                    Log.e("ERROR1","LA ACTUALIZACION FALLO");
                    cinesActualizados.add(itemcine);
                }
            }
        }

        return cinesActualizados;
    }

}
