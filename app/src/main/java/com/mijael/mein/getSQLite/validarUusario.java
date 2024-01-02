package com.mijael.mein.getSQLite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.mijael.mein.Entidades.Usuario;
import com.mijael.mein.HELPER.UsuarioSQLiteHelper;
import com.mijael.mein.Utilidades.Util_Usuario;

public class validarUusario {
    private Context context;
    private UsuarioSQLiteHelper dataHelper;
    public validarUusario(Context context){
        dataHelper = UsuarioSQLiteHelper.getInstance(context);
    }
    public Usuario Consultar(String user, String password) {
        Log.e("Consulta","Entro a consultar");
        SQLiteDatabase db = dataHelper.getReadableDatabase();
        String[] parametro = {user, password};
        String[] campos = {Util_Usuario.CAMPO_ID, Util_Usuario.CAMPO_NOMBRES, Util_Usuario.CAMPO_APATER, Util_Usuario.CAMPO_AMATER, Util_Usuario.CAMPO_CODIGO, Util_Usuario.CAMPO_PASS_ENCRIP,
        Util_Usuario.CAMPO_PASS_DESENCRIP, Util_Usuario.CAMPO_ID_NIVEL, Util_Usuario.CAMPO_CODIGO};

        try {
            Cursor cursor = db.query(Util_Usuario.TABLA_USUARIO,campos, Util_Usuario.CAMPO_CODIGO_USU+"=? AND "+ Util_Usuario.CAMPO_PASS_DESENCRIP+"=?",parametro,null,null,null);
            cursor.moveToFirst();
            //Log.e("ID:", String.valueOf(cursor.getInt(0)));
            //Log.e("Nombre:",cursor.getString(1));
            //Log.e("Salas",cursor.getString(2));
            Usuario usuario = new Usuario(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getInt(7),
                    cursor.getString(8));
            cursor.close();
            return usuario;
        }catch (Exception e){
            Toast.makeText(context,"Usuario no existe",Toast.LENGTH_LONG).show();
        }
        return null;
    }
}
