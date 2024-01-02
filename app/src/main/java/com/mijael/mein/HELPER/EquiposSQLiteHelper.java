package com.mijael.mein.HELPER;

import android.content.Context;
import android.content.QuickViewConstants;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.mijael.mein.Utilidades.Util_Equipos;

public class EquiposSQLiteHelper extends SQLiteOpenHelper {
    private static EquiposSQLiteHelper instance;

    public EquiposSQLiteHelper(Context context) {
        super(context, Util_Equipos.TABLA_EQUIPOS, null, Util_Equipos.VERSION_BD);

    }

    public static EquiposSQLiteHelper getInstance(Context context) {
        if (instance == null) {
            instance = new EquiposSQLiteHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Util_Equipos.CrearTablaEquipos);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Util_Equipos.TABLA_EQUIPOS);
        onCreate(db);
    }
}
