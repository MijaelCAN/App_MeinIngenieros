package com.mijael.mein.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mijael.mein.HELPER.DatosLocalSQLiteHelper;

import java.util.ArrayList;
import java.util.List;

public class DAO_DatosLocal {
    public static List<String> obtenerDatosParaSpinner(String nombreTabla,String campoTabla, Context context) {
        SQLiteDatabase db = new DatosLocalSQLiteHelper(context, "TablasLocales", null, 1).getReadableDatabase();
        List<String> listaDatos = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + nombreTabla, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String dato = cursor.getString(cursor.getColumnIndex(campoTabla)); // Cambia "nombre_columna" por el nombre real de la columna que quieres mostrar en el Spinner
                listaDatos.add(dato);
            } while (cursor.moveToNext());
            cursor.close();
        }
        //db.close();

        return listaDatos;
    }
    public static List<Object[]> obtenerDatos(String nombreTabla, Context context) {
        SQLiteDatabase db = new DatosLocalSQLiteHelper(context, "TablasLocales", null, 1).getReadableDatabase();
        List<Object[]> listaDatos = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + nombreTabla, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Object[] datos = new Object[2];
                datos[0] = cursor.getString(cursor.getColumnIndex("nom_modelo"));
                datos[1] = cursor.getString(cursor.getColumnIndex("nrr"));
                listaDatos.add(datos);
            } while (cursor.moveToNext());
            cursor.close();
        }
        //db.close();
        return listaDatos;
    }

}
