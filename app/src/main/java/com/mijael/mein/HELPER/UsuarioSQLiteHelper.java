package com.mijael.mein.HELPER;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mijael.mein.Utilidades.Util_Usuario;

public class UsuarioSQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "DB_USUARIOS";
    private static final int DATABASE_VERSION = 1;

    private static UsuarioSQLiteHelper instance;
    public UsuarioSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public static UsuarioSQLiteHelper getInstance(Context context) {
        if (instance == null) {
            instance = new UsuarioSQLiteHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("DROP TABLE usuario");
        db.execSQL(Util_Usuario.CrearTablaUsuario);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Util_Usuario.TABLA_USUARIO);
    }
}
