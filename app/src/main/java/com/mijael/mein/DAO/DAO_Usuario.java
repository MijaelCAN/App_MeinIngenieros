package com.mijael.mein.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mijael.mein.Entidades.Usuario;
import com.mijael.mein.HELPER.UsuarioSQLiteHelper;
import com.mijael.mein.Utilidades.Util_Cines;
import com.mijael.mein.Utilidades.Util_IluminacionRegistro;
import com.mijael.mein.Utilidades.Util_Usuario;

import java.util.ArrayList;
import java.util.List;

public class DAO_Usuario {
    private UsuarioSQLiteHelper dataHelper;
    public DAO_Usuario(Context context) {
        dataHelper = UsuarioSQLiteHelper.getInstance(context);
    }
    public List<Usuario> listarUsuarios(){
        List<Usuario> listaUsuarios = new ArrayList<>();
        SQLiteDatabase db = dataHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ Util_Usuario.TABLA_USUARIO, null);

        if(cursor.moveToFirst()){
            do{
                Usuario usuarios = new Usuario(
                        cursor.getInt(cursor.getColumnIndex(Util_Usuario.CAMPO_ID)),
                        cursor.getString(cursor.getColumnIndex(Util_Usuario.CAMPO_NOMBRES)),
                        cursor.getString(cursor.getColumnIndex(Util_Usuario.CAMPO_APATER)),
                        cursor.getString(cursor.getColumnIndex(Util_Usuario.CAMPO_AMATER)),
                        cursor.getString(cursor.getColumnIndex(Util_Usuario.CAMPO_CODIGO_USU)),
                        cursor.getString(cursor.getColumnIndex(Util_Usuario.CAMPO_PASS_ENCRIP)),
                        cursor.getString(cursor.getColumnIndex(Util_Usuario.CAMPO_PASS_DESENCRIP)),
                        cursor.getInt(cursor.getColumnIndex(Util_Usuario.CAMPO_ID_NIVEL)),
                        cursor.getString(cursor.getColumnIndex(Util_Usuario.CAMPO_CODIGO))
                );
                listaUsuarios.add(usuarios);
            }while (cursor.moveToNext());
        }
        cursor.close();
        //db.close();
        return listaUsuarios;
    }
    public void ActualizarUsuario(Usuario usuario) {
        Log.e("ENTRADA4","ENTRO A ActualizaruSUARIO()");
        SQLiteDatabase db = dataHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Util_Usuario.CAMPO_NOMBRES, usuario.getUsuario_nombres());
        values.put(Util_Usuario.CAMPO_APATER, usuario.getUsuario_apater());
        values.put(Util_Usuario.CAMPO_AMATER, usuario.getUsuario_amater());
        values.put(Util_Usuario.CAMPO_CODIGO_USU, usuario.getUsuario_codigo());
        values.put(Util_Usuario.CAMPO_PASS_ENCRIP, usuario.getUsuario_password());
        values.put(Util_Usuario.CAMPO_PASS_DESENCRIP, usuario.getPassword_desc());
        values.put(Util_Usuario.CAMPO_ID_NIVEL, usuario.getId_nivel());
        values.put(Util_Usuario.CAMPO_CODIGO, usuario.getCodigo());

        String whereClause = Util_Usuario.CAMPO_ID + "=?";
        String[] whereArgs = {String.valueOf(usuario.getId_usuario())};

        int resultado = db.update(Util_Usuario.TABLA_USUARIO, values, whereClause, whereArgs);

        if (resultado == -1) {
            Log.e("TAG-E", "Error al actualizar USUARIO en la base de datos local");
        } else {
            Log.d("TAG-D", "USUARIO actualizado en la base de datos local");
        }

        //db.close();
    }
    public int contarUsuarios() {
        SQLiteDatabase db = dataHelper.getReadableDatabase();
        Cursor cursor = null;
        int count = 0;

        try {
            cursor = db.rawQuery("SELECT COUNT(*) FROM " + Util_Usuario.TABLA_USUARIO, null);
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
    public void insertarUsuarios(List<Usuario> usuariosList) {
        SQLiteDatabase db = dataHelper.getWritableDatabase();

        try {
            db.beginTransaction();
            ContentValues values;

            for (Usuario usuario : usuariosList) {
                values = new ContentValues();
                // Configurar los valores para la inserción
                values.put(Util_Usuario.CAMPO_ID, usuario.getId_usuario());
                values.put(Util_Usuario.CAMPO_NOMBRES, usuario.getUsuario_nombres());
                values.put(Util_Usuario.CAMPO_APATER, usuario.getUsuario_apater());
                values.put(Util_Usuario.CAMPO_AMATER, usuario.getUsuario_amater());
                values.put(Util_Usuario.CAMPO_CODIGO_USU, usuario.getUsuario_codigo());
                values.put(Util_Usuario.CAMPO_PASS_ENCRIP, usuario.getUsuario_password());
                values.put(Util_Usuario.CAMPO_PASS_DESENCRIP, usuario.getPassword_desc());
                values.put(Util_Usuario.CAMPO_ID_NIVEL, usuario.getId_nivel());
                values.put(Util_Usuario.CAMPO_CODIGO, usuario.getCodigo());

                // Insertar el cine en la base de datos
                long resultado = db.insert(Util_Usuario.TABLA_USUARIO, null, values);
                if(resultado == -1){
                    Log.e("TAG-E", "Error al insertar USUARIO en la base de datos local");
                } else {
                    Log.d("TAG-D", "USUARIO insertado en la base de datos local");
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
    public boolean verificarExistenciaUsuario(int idUsuario) {
        SQLiteDatabase db = dataHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(Util_Usuario.TABLA_USUARIO, null,
                    Util_Usuario.CAMPO_ID + " = ?", new String[]{String.valueOf(idUsuario)},
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
    public void insertarUsuario(Usuario usuario){

        Log.e("PASO 2", "ENTRO A METODO INSERTAR");
        SQLiteDatabase database = dataHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util_Usuario.CAMPO_ID, usuario.getId_usuario());
        values.put(Util_Usuario.CAMPO_NOMBRES, usuario.getUsuario_nombres());
        values.put(Util_Usuario.CAMPO_APATER, usuario.getUsuario_apater());
        values.put(Util_Usuario.CAMPO_AMATER, usuario.getUsuario_amater());
        values.put(Util_Usuario.CAMPO_CODIGO_USU, usuario.getUsuario_codigo());
        values.put(Util_Usuario.CAMPO_PASS_ENCRIP, usuario.getUsuario_password());
        values.put(Util_Usuario.CAMPO_PASS_DESENCRIP, usuario.getPassword_desc());
        values.put(Util_Usuario.CAMPO_ID_NIVEL, usuario.getId_nivel());
        values.put(Util_Usuario.CAMPO_CODIGO, usuario.getCodigo());

        long resultado = database.insert(Util_Usuario.TABLA_USUARIO,null,values);
        if(resultado == -1){
            Log.e("TAG-E", "Error al insertar USUARIO en la base de datos local");
        } else {
            Log.d("TAG-D", "USAURIO insertado en la base de datos local");
        }
        //database.close();
    }
    public Usuario BuscarUsuario(String user, String pass) {
        Usuario usuario = null;
        SQLiteDatabase db = dataHelper.getReadableDatabase();
        String[] columns = {
                Util_Usuario.CAMPO_ID,
                Util_Usuario.CAMPO_NOMBRES,
                Util_Usuario.CAMPO_APATER,
                Util_Usuario.CAMPO_AMATER,
                Util_Usuario.CAMPO_CODIGO_USU,
                Util_Usuario.CAMPO_PASS_ENCRIP,
                Util_Usuario.CAMPO_PASS_DESENCRIP,
                Util_Usuario.CAMPO_ID_NIVEL,
                Util_Usuario.CAMPO_CODIGO
        };
        String selection = Util_Usuario.CAMPO_CODIGO_USU + " = ? AND " + Util_Usuario.CAMPO_PASS_DESENCRIP + " = ?";
        String[] selectionArgs = {user, pass};
        Cursor cursor = db.query(Util_Usuario.TABLA_USUARIO, columns, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            usuario = new Usuario(
                    cursor.getInt(cursor.getColumnIndex(Util_Usuario.CAMPO_ID)),
                    cursor.getString(cursor.getColumnIndex(Util_Usuario.CAMPO_NOMBRES)),
                    cursor.getString(cursor.getColumnIndex(Util_Usuario.CAMPO_APATER)),
                    cursor.getString(cursor.getColumnIndex(Util_Usuario.CAMPO_AMATER)),
                    cursor.getString(cursor.getColumnIndex(Util_Usuario.CAMPO_CODIGO_USU)),
                    cursor.getString(cursor.getColumnIndex(Util_Usuario.CAMPO_PASS_ENCRIP)),
                    cursor.getString(cursor.getColumnIndex(Util_Usuario.CAMPO_PASS_DESENCRIP)),
                    cursor.getInt(cursor.getColumnIndex(Util_Usuario.CAMPO_ID_NIVEL)),
                    cursor.getString(cursor.getColumnIndex(Util_Usuario.CAMPO_CODIGO))
            );
        }
        cursor.close();
        //db.close();
        return usuario;
    }


    public Usuario BuscarUsuario(int idColaborador){
        Usuario usuario = null;
        SQLiteDatabase db = dataHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ Util_Usuario.TABLA_USUARIO + " WHERE "+ Util_Usuario.CAMPO_ID + " = " + idColaborador, null);
        if(cursor.moveToFirst()){
            do{
                 usuario = new Usuario(
                        cursor.getInt(cursor.getColumnIndex(Util_Usuario.CAMPO_ID)),
                        cursor.getString(cursor.getColumnIndex(Util_Usuario.CAMPO_NOMBRES)),
                        cursor.getString(cursor.getColumnIndex(Util_Usuario.CAMPO_APATER)),
                        cursor.getString(cursor.getColumnIndex(Util_Usuario.CAMPO_AMATER)),
                        cursor.getString(cursor.getColumnIndex(Util_Usuario.CAMPO_CODIGO_USU)),
                        cursor.getString(cursor.getColumnIndex(Util_Usuario.CAMPO_PASS_ENCRIP)),
                        cursor.getString(cursor.getColumnIndex(Util_Usuario.CAMPO_PASS_DESENCRIP)),
                        cursor.getInt(cursor.getColumnIndex(Util_Usuario.CAMPO_ID_NIVEL)),
                        cursor.getString(cursor.getColumnIndex(Util_Usuario.CAMPO_CODIGO))
                );
            }while (cursor.moveToNext());
        }
        cursor.close();
        //db.close();
        return usuario;
    }
}
