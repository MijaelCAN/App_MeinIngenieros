package com.mijael.mein.HELPER;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.mijael.mein.Utilidades.Util_Formato_pTrabajo;

public class FormatoTrabajoSQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "DB_FORMATOS";
    private static final int DATABASE_VERSION = 1;
    private static FormatoTrabajoSQLiteHelper instance;
    public FormatoTrabajoSQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
    public static FormatoTrabajoSQLiteHelper getInstance(Context context) {
        if (instance == null) {
            instance = new FormatoTrabajoSQLiteHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Util_Formato_pTrabajo.CrearTablaformatoTrabajo);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ Util_Formato_pTrabajo.TABLA_FORMATO_TRABAJO);
    }
}
