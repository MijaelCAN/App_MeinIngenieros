package com.mijael.mein.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.mijael.mein.Entidades.RadiacionElec_Registro;
import com.mijael.mein.Entidades.RadiacionElect_RegistroDetalle;
import com.mijael.mein.Entidades.Vibracion_Registro;
import com.mijael.mein.Entidades.Vibracion_RegistroDetalle;
import com.mijael.mein.HELPER.RegistroFormatosSQLiteHelper;
import com.mijael.mein.Utilidades.Util_RadiacionElectRegistroDetalle;
import com.mijael.mein.Utilidades.Util_VibracionRegistro;
import com.mijael.mein.Utilidades.Util_RadiacionElectRegistro;
import com.mijael.mein.Utilidades.Util_VibracionRegistroDetalle;

public class DAO_RegistroRadiacion {
    public RegistroFormatosSQLiteHelper datHelper;
    public DAO_RegistroRadiacion(Context context){
        this.datHelper = RegistroFormatosSQLiteHelper.getInstance(context);
    }
    public boolean RegistroRadiacion(RadiacionElec_Registro registro){
        SQLiteDatabase db = datHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util_RadiacionElectRegistro.CAMPO_COD_FORMATO, registro.getCod_formato());
        values.put(Util_RadiacionElectRegistro.CAMPO_ID_FORMATO, registro.getId_formato());
        values.put(Util_RadiacionElectRegistro.CAMPO_ID_PLAN_TRABAJO, registro.getId_plan_trabajo());
        values.put(Util_RadiacionElectRegistro.CAMPO_ID_PT_FORMATO, registro.getId_pt_formato());
        values.put(Util_RadiacionElectRegistro.CAMPO_ID_EQUIPO1, registro.getId_equipo1());
        values.put(Util_RadiacionElectRegistro.CAMPO_COD_EQUIPO1, registro.getCod_equipo1());
        values.put(Util_RadiacionElectRegistro.CAMPO_NOM_EQUIPO1, registro.getNom_equipo1());
        values.put(Util_RadiacionElectRegistro.CAMPO_SERIE_EQ1, registro.getSerie_eq1());
        values.put(Util_RadiacionElectRegistro.CAMPO_ID_ANALISTA, registro.getId_analista());
        values.put(Util_RadiacionElectRegistro.CAMPO_NOM_ANALISTA, registro.getNom_analista());
        values.put(Util_RadiacionElectRegistro.CAMPO_HORA_SITU, registro.getHora_situ());
        values.put(Util_RadiacionElectRegistro.CAMPO_VERF_INSITU, registro.getVerf_insitu());
        values.put(Util_RadiacionElectRegistro.CAMPO_FEC_MONITOREO, registro.getFec_monitoreo());
        values.put(Util_RadiacionElectRegistro.CAMPO_HORA_INICIAL, registro.getHora_inicial());
        values.put(Util_RadiacionElectRegistro.CAMPO_HORA_FINAL, registro.getHora_final());
        values.put(Util_RadiacionElectRegistro.CAMPO_TIPO_DOC_TRABAJADOR, registro.getTipo_doc_trabajador());
        values.put(Util_RadiacionElectRegistro.CAMPO_NUM_DOC_TRABAJADOR, registro.getNum_doc_trabajador());
        values.put(Util_RadiacionElectRegistro.CAMPO_NOM_TRABAJADOR, registro.getNom_trabajador());
        values.put(Util_RadiacionElectRegistro.CAMPO_PUESTO_TRABAJADOR, registro.getPuesto_trabajador());
        values.put(Util_RadiacionElectRegistro.CAMPO_AREA_TRABAJO, registro.getArea_trabajo());
        values.put(Util_RadiacionElectRegistro.CAMPO_ACTIVIDADES_REALIZADAS, registro.getActividades_realizadas());
        values.put(Util_RadiacionElectRegistro.CAMPO_EDAD_TRABAJADOR, registro.getEdad_trabajador());
        values.put(Util_RadiacionElectRegistro.CAMPO_HORA_TRABAJO, registro.getHora_trabajo());
        values.put(Util_RadiacionElectRegistro.CAMPO_HORARIO_REFRIGERIO, registro.getHorario_refrigerio());
        values.put(Util_RadiacionElectRegistro.CAMPO_REGIMEN_LABORAL, registro.getRegimen_laboral());
        values.put(Util_RadiacionElectRegistro.CAMPO_JORNADA, registro.getJornada());
        values.put(Util_RadiacionElectRegistro.CAMPO_TIEMPO_MEDICION, registro.getTiempo_medicion());
        values.put(Util_RadiacionElectRegistro.CAMPO_TIEMPO_EXPOSICION, registro.getTiempo_exposicion());
        values.put(Util_RadiacionElectRegistro.CAMPO_DESC_AREA_TRABAJO, registro.getDesc_area_trabajo());
        values.put(Util_RadiacionElectRegistro.CAMPO_NOM_CTRL_INGENIERIA, registro.getNom_ctrl_ingenieria());
        values.put(Util_RadiacionElectRegistro.CAMPO_NOM_CTRL_ADMIN, registro.getNom_ctrl_admin());
        values.put(Util_RadiacionElectRegistro.CAMPO_NOM_EPP, registro.getNom_epp());
        values.put(Util_RadiacionElectRegistro.CAMPO_ESTADO, registro.getEstado());
        values.put(Util_RadiacionElectRegistro.CAMPO_FEC_REG, registro.getFec_reg());
        values.put(Util_RadiacionElectRegistro.CAMPO_USER_REG, registro.getUser_reg());

        db.insert(Util_RadiacionElectRegistro.TABLA_RADIACION,null,values);

        return true;
    }

    public boolean RegistrarRadiacionDetalle(RadiacionElect_RegistroDetalle registro){
        SQLiteDatabase db = datHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(Util_VibracionRegistroDetalle.CAMPO_ID_PLAN_TRABAJO_FORMATO_REG, registro.getId_plan_trabajo_formato_reg());
        values.put(Util_RadiacionElectRegistroDetalle.CAMPO_ID_PLAN_TRABAJO_FORMATO_REG, registro.getId_plan_trabajo_formato_reg());
        values.put(Util_RadiacionElectRegistroDetalle.CAMPO_FUENTE_GENERADORA, registro.getFuente_generadora());
        values.put(Util_RadiacionElectRegistroDetalle.CAMPO_VESTIMENTA_PERSONAL, registro.getVestimenta_personal());
        values.put(Util_RadiacionElectRegistroDetalle.CAMPO_X, registro.getX());
        values.put(Util_RadiacionElectRegistroDetalle.CAMPO_X2, registro.getX2());
        values.put(Util_RadiacionElectRegistroDetalle.CAMPO_X3, registro.getX3());
        values.put(Util_RadiacionElectRegistroDetalle.CAMPO_X4, registro.getX4());
        values.put(Util_RadiacionElectRegistroDetalle.CAMPO_Y, registro.getY());
        values.put(Util_RadiacionElectRegistroDetalle.CAMPO_Y2, registro.getY2());
        values.put(Util_RadiacionElectRegistroDetalle.CAMPO_Y3, registro.getY3());
        values.put(Util_RadiacionElectRegistroDetalle.CAMPO_Y4, registro.getY4());
        values.put(Util_RadiacionElectRegistroDetalle.CAMPO_Z, registro.getZ());
        values.put(Util_RadiacionElectRegistroDetalle.CAMPO_Z2, registro.getZ2());
        values.put(Util_RadiacionElectRegistroDetalle.CAMPO_Z3, registro.getZ3());
        values.put(Util_RadiacionElectRegistroDetalle.CAMPO_Z4, registro.getZ4());
        values.put(Util_RadiacionElectRegistroDetalle.CAMPO_ESTADO, registro.getEstado());
        values.put(Util_RadiacionElectRegistroDetalle.CAMPO_FEC_REG, registro.getFec_reg());
        values.put(Util_RadiacionElectRegistroDetalle.CAMPO_USER_REG, registro.getUser_reg());


        db.insert(Util_RadiacionElectRegistroDetalle.TABLA_RADIACION_DETALLE,null,values);

        return true;
    }
}
