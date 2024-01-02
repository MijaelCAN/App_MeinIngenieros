package com.mijael.mein.HELPER;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mijael.mein.Utilidades.Util_Cines;

public class CinesSQLiteHelper extends SQLiteOpenHelper {
    private static CinesSQLiteHelper instance;
    public CinesSQLiteHelper(Context context) {
        super(context, Util_Cines.TABLA_CINES, null, Util_Cines.VERSION_BD);
    }

    public static CinesSQLiteHelper getInstance(Context context) {
        if (instance == null) {
            instance = new CinesSQLiteHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Util_Cines.CrearTablaCines);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Util_Cines.TABLA_CINES);
        onCreate(db);
    }
}
