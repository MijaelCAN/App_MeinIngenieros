package com.mijael.mein.HELPER;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.mijael.mein.Utilidades.Util_OrdenTrabajo;
import com.mijael.mein.Utilidades.Util_Usuario;

public class OrdenTrabajoSQLiteHelper extends SQLiteOpenHelper {
    private static OrdenTrabajoSQLiteHelper instance;
    public OrdenTrabajoSQLiteHelper(Context context) {
        super(context, Util_OrdenTrabajo.TABLA_ORDEN_TRABAJO, null,Util_OrdenTrabajo.VERSION_BD);
    }
    public static OrdenTrabajoSQLiteHelper getInstance(Context context) {
        if (instance == null) {
            instance = new OrdenTrabajoSQLiteHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Util_OrdenTrabajo.CrearTablaOrdenTrabajo);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Util_OrdenTrabajo.TABLA_ORDEN_TRABAJO);
    }
}
