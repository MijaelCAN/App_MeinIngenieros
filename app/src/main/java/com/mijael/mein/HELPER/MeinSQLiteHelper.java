package com.mijael.mein.HELPER;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mijael.mein.Utilidades.Util_DosimetriaRegistro;
import com.mijael.mein.Utilidades.Util_EstresTermicoRegistro;
import com.mijael.mein.Utilidades.Util_EstresTermicoRegistroDetalle;
import com.mijael.mein.Utilidades.Util_Formato_pTrabajo;
import com.mijael.mein.Utilidades.Util_IluminacionRegistro;
import com.mijael.mein.Utilidades.Util_IluminacionRegistroDetalle;
import com.mijael.mein.Utilidades.Util_RadiacionElectRegistro;
import com.mijael.mein.Utilidades.Util_RadiacionElectRegistroDetalle;
import com.mijael.mein.Utilidades.Util_RegistroFormatos;
import com.mijael.mein.Utilidades.Util_RegistroFormatos_Detalle;
import com.mijael.mein.Utilidades.Util_SonometriaRegistro;
import com.mijael.mein.Utilidades.Util_VibracionRegistro;
import com.mijael.mein.Utilidades.Util_VibracionRegistroDetalle;

public class MeinSQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "DB_RegistrosFormatos";
    private static final int DATABASE_VERSION = 1;
    private static MeinSQLiteHelper instance;
    public MeinSQLiteHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }
    public static MeinSQLiteHelper getInstance(Context context) {
        if (instance == null) {
            instance = new MeinSQLiteHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*db.execSQL(Util_DosimetriaRegistro.CrearTablaRegistroDosimetria);
        db.execSQL(Util_SonometriaRegistro.CrearTablaRegistroSonometria);
        db.execSQL(Util_IluminacionRegistro.CrearTablaIluminacion);
        db.execSQL(Util_IluminacionRegistroDetalle.CrearTablaIluminacionDetalle);
        db.execSQL(Util_VibracionRegistro.CrearTablaVibracion);
        db.execSQL(Util_VibracionRegistroDetalle.CrearTablaVibracionDetalle);
        db.execSQL(Util_EstresTermicoRegistro.CrearTablaEstres);
        db.execSQL(Util_EstresTermicoRegistroDetalle.CrearTablaEstresDetalle);
        db.execSQL(Util_RadiacionElectRegistro.CrearTablaRadiacion);
        db.execSQL(Util_RadiacionElectRegistroDetalle.CrearTablaRadiacionDetalle);*/
        db.execSQL(Util_RegistroFormatos.CrearTablaRegistroFormato);
        db.execSQL(Util_RegistroFormatos_Detalle.CrearTablaRegistroDetalle);
        db.execSQL(Util_Formato_pTrabajo.CrearTablaformatoTrabajo);
        //db.execSQL("ALTER TABLE " + Util_RegistroFormatos.TABLA_REGISTRO_FORMATOS + " ADD COLUMN pendiente_sincronizacion INTEGER DEFAULT 0");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Util_DosimetriaRegistro.TABLA_REGISTRO_DOSIMETRIA);
        db.execSQL("DROP TABLE IF EXISTS " + Util_SonometriaRegistro.TABLA_SONOMETRIA);
        db.execSQL("DROP TABLE IF EXISTS " + Util_IluminacionRegistro.TABLA_ILUMINACION);
        db.execSQL("DROP TABLE IF EXISTS " + Util_IluminacionRegistroDetalle.TABLA_ILUMINACION_DETALLE);
        db.execSQL("DROP TABLE IF EXISTS " + Util_VibracionRegistro.TABLA_VIBRACION);
        db.execSQL("DROP TABLE IF EXISTS " + Util_VibracionRegistroDetalle.TABLA_VIBRACION_DETALLE);
        db.execSQL("DROP TABLE IF EXISTS " + Util_EstresTermicoRegistro.TABLA_ESTRES);
        db.execSQL("DROP TABLE IF EXISTS " + Util_EstresTermicoRegistroDetalle.TABLA_ESTRES_DETALLE);
        db.execSQL("DROP TABLE IF EXISTS " + Util_RadiacionElectRegistro.TABLA_RADIACION);
        db.execSQL("DROP TABLE IF EXISTS " + Util_RadiacionElectRegistroDetalle.TABLA_RADIACION_DETALLE);
        db.execSQL("DROP TABLE IF EXISTS " + Util_RegistroFormatos.TABLA_REGISTRO_FORMATOS);
        db.execSQL("DROP TABLE IF EXISTS " + Util_RegistroFormatos_Detalle.TABLA_REGISTRO_DETALLE);
    }
}
