package com.mijael.mein.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.mijael.mein.Entidades.VelocidadAire_Registro;
import com.mijael.mein.Entidades.VelocidadAire_RegistroDetalle;
import com.mijael.mein.HELPER.MeinSQLiteHelper;
import com.mijael.mein.Utilidades.Util_RegistroFormatos;
import com.mijael.mein.Utilidades.Util_RegistroFormatos_Detalle;

public class DAO_VelocidadAire {
    public MeinSQLiteHelper helper;
    public DAO_VelocidadAire(Context context){
        this.helper = MeinSQLiteHelper.getInstance(context);
    }

    public boolean RegistroVelocidad(VelocidadAire_Registro registro){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Util_RegistroFormatos.CAMPO_ID_PLAN_TRABAJO_FORMATO_REG, -1);
        values.put(Util_RegistroFormatos.CAMPO_COD_FORMATO, registro.getCod_formato());
        values.put(Util_RegistroFormatos.CAMPO_COD_REGISTRO, registro.getCod_registro());
        values.put(Util_RegistroFormatos.CAMPO_ID_FORMATO, registro.getId_formato());
        values.put(Util_RegistroFormatos.CAMPO_ID_PLAN_TRABAJO, registro.getId_plan_trabajo());
        values.put(Util_RegistroFormatos.CAMPO_ID_PT_FORMATO, registro.getId_pt_formato());
        values.put(Util_RegistroFormatos.CAMPO_ID_EQUIPO1, registro.getId_equipo1());
        values.put(Util_RegistroFormatos.CAMPO_COD_EQUIPO1, registro.getCod_equipo1());
        values.put(Util_RegistroFormatos.CAMPO_NOM_EQUIPO1, registro.getNom_equipo1());
        values.put(Util_RegistroFormatos.CAMPO_SERIE_EQ1, registro.getSerie_eq1());;
        values.put(Util_RegistroFormatos.CAMPO_ID_ANALISTA, registro.getId_analista());
        values.put(Util_RegistroFormatos.CAMPO_NOM_ANALISTA, registro.getNom_analista());
        values.put(Util_RegistroFormatos.CAMPO_HORA_INICIAL, registro.getHora_inicial());
        values.put(Util_RegistroFormatos.CAMPO_HORA_FINAL, registro.getHora_final());
        values.put(Util_RegistroFormatos.CAMPO_AREA_TRABAJO, registro.getArea_trabajo());
        values.put(Util_RegistroFormatos.CAMPO_ACTIVIDADES_REALIZADAS, registro.getActividades_realizadas());
        values.put(Util_RegistroFormatos.CAMPO_HORA_TRABAJO, registro.getHora_trabajo());
        values.put(Util_RegistroFormatos.CAMPO_DESC_AREA_TRABAJO, registro.getDesc_area_trabajo());
        values.put(Util_RegistroFormatos.CAMPO_FEC_MONITOREO, registro.getFec_monitoreo());
        values.put(Util_RegistroFormatos.CAMPO_OBSERVACION, registro.getObservacion());
        values.put(Util_RegistroFormatos.CAMPO_ESTADO, registro.getEstado());
        values.put(Util_RegistroFormatos.CAMPO_FEC_REG, registro.getFec_reg());
        values.put(Util_RegistroFormatos.CAMPO_USER_REG, registro.getId_analista());
        values.put(Util_RegistroFormatos.CAMPO_ESTADO_SINCRO,1);// MARCANDO PARA SABER QUE FALTA SINCRONIZAR

        db.insert(Util_RegistroFormatos.TABLA_REGISTRO_FORMATOS,null, values);
        return true;
    }

    public boolean RegistroVelocidad_Detalle(VelocidadAire_RegistroDetalle registro){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Util_RegistroFormatos_Detalle.CAMPO_ID_PLAN_TRABAJO_FORMATO_REG, registro.getId_plan_trabajo_formato_reg());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TECNICA_ACONDAIRE, registro.getTecnica_acondaire());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_DETALLE_TECNICA_ACONDAIRE, registro.getDetalle_tecnica_acondaire());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_V_VIENTO, registro.getV_viento());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_V_VIENTO_2, registro.getV_viento_2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_V_VIENTO_3, registro.getV_viento_3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_V_VTO4, registro.getV_vto4());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_V_VTO5, registro.getV_vto5());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_V_VTO6, registro.getV_vto6());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_V_VTO7, registro.getV_vto7());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_V_VTO8, registro.getV_vto8());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_V_VTO9, registro.getV_vto9());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_V_VTO10, registro.getV_vto10());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TEMP, registro.getTemp());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ESTADO, registro.getEstado());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_FEC_REG, registro.getFec_reg());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_USER_REG, registro.getUser_reg());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ESTADO_SINCRO,1);// MARCANDO PARA SABER QUE FALTA SINCRONIZAR

        db.insert(Util_RegistroFormatos_Detalle.TABLA_REGISTRO_DETALLE, null, values);
        return true;
    }
}
