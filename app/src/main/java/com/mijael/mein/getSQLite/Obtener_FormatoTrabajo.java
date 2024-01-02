package com.mijael.mein.getSQLite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mijael.mein.HELPER.FormatoTrabajoSQLiteHelper;
import com.mijael.mein.Utilidades.Util_Formato_pTrabajo;

public class Obtener_FormatoTrabajo {
    private Context context;

    public Obtener_FormatoTrabajo(Context context) {
        this.context = context;
    }
    public Cursor obtenerRegistro(int idPlanTrabajo) {
        FormatoTrabajoSQLiteHelper con = FormatoTrabajoSQLiteHelper.getInstance(context);
        SQLiteDatabase db = con.getReadableDatabase();
        String[] campos = {
                Util_Formato_pTrabajo.CAMPO_NOM_FORMATO,
                Util_Formato_pTrabajo.CAMPO_NOM_CLIENTE,
                Util_Formato_pTrabajo.CAMPO_REALIZADO,
                Util_Formato_pTrabajo.CAMPO_POR_REALIZAR,
                Util_Formato_pTrabajo.CAMPO_CANTIDAD};
        String whereClause = Util_Formato_pTrabajo.CAMPO_ID_PLAN_TRABAJO + " = ?";
        String[] whereArgs = { String.valueOf(idPlanTrabajo) };
        Cursor cursor = db.query(Util_Formato_pTrabajo.TABLA_FORMATO_TRABAJO, campos, whereClause, whereArgs, null, null, null);
        return cursor;
    }
}
