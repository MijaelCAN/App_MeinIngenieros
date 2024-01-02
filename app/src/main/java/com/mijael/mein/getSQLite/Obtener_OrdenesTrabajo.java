package com.mijael.mein.getSQLite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mijael.mein.HELPER.OrdenTrabajoSQLiteHelper;
import com.mijael.mein.Utilidades.Util_OrdenTrabajo;

public class Obtener_OrdenesTrabajo {
    private Context context;
    public Obtener_OrdenesTrabajo(){

    }
    public Obtener_OrdenesTrabajo(Context context){
        this.context = context;
    }
    public Cursor obtenerTodos(int idColaborador) {
        OrdenTrabajoSQLiteHelper con = OrdenTrabajoSQLiteHelper.getInstance(context);
        SQLiteDatabase db = con.getReadableDatabase();
        String[] campos = {Util_OrdenTrabajo.CAMPO_COD_OT, Util_OrdenTrabajo.CAMPO_COD_COTIZACION, Util_OrdenTrabajo.CAMPO_NOM_CLIENTE, Util_OrdenTrabajo.CAMPO_NOM_TIPO_SERVICIO, Util_OrdenTrabajo.CAMPO_FECHA_REGISTRO, Util_OrdenTrabajo.CAMPO_ID_OT,
                Util_OrdenTrabajo.CAMPO_ID_PLAN_TRABAJO, Util_OrdenTrabajo.CAMPO_DOFRMATOS, Util_OrdenTrabajo.CAMPO_ID_COLABORADOR,Util_OrdenTrabajo.CAMPO_URL_PLAN_TRABAJO};
        String whereClause = Util_OrdenTrabajo.CAMPO_ID_COLABORADOR + " = ?";
        String[] whereArgs = { String.valueOf(idColaborador) };
        Cursor cursor = db.query(Util_OrdenTrabajo.TABLA_ORDEN_TRABAJO, campos, whereClause, whereArgs, null, null, null);
        return cursor;
    }

}
