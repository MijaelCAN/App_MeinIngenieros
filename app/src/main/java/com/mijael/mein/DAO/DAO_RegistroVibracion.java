package com.mijael.mein.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.mijael.mein.Entidades.Vibracion_Registro;
import com.mijael.mein.Entidades.Vibracion_RegistroDetalle;
import com.mijael.mein.HELPER.RegistroFormatosSQLiteHelper;
import com.mijael.mein.Utilidades.Util_IluminacionRegistro;
import com.mijael.mein.Utilidades.Util_RegistroFormatos;
import com.mijael.mein.Utilidades.Util_RegistroFormatos_Detalle;
import com.mijael.mein.Utilidades.Util_VibracionRegistro;
import com.mijael.mein.Utilidades.Util_VibracionRegistroDetalle;

public class DAO_RegistroVibracion {
    public RegistroFormatosSQLiteHelper datHelper;
    public DAO_RegistroVibracion(Context context){
        this.datHelper = RegistroFormatosSQLiteHelper.getInstance(context);
    }

    public boolean RegistroVibracion(Vibracion_Registro registro){
        SQLiteDatabase db = datHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Util_RegistroFormatos.CAMPO_COD_FORMATO, registro.getCod_formato());
        values.put(Util_RegistroFormatos.CAMPO_ID_FORMATO, registro.getId_formato());
        values.put(Util_RegistroFormatos.CAMPO_ID_PLAN_TRABAJO, registro.getId_plan_trabajo());
        values.put(Util_RegistroFormatos.CAMPO_ID_PT_FORMATO, registro.getId_pt_formato());
        values.put(Util_RegistroFormatos.CAMPO_COD_EQUIPO1, registro.getCod_equipo1());
        values.put(Util_RegistroFormatos.CAMPO_NOM_EQUIPO1, registro.getNom_equipo1());
        values.put(Util_RegistroFormatos.CAMPO_SERIE_EQ1, registro.getSerie_eq1());
        values.put(Util_RegistroFormatos.CAMPO_ID_EQUIPO1, registro.getId_equipo1());
        values.put(Util_RegistroFormatos.CAMPO_ID_ANALISTA, registro.getId_analista());
        values.put(Util_RegistroFormatos.CAMPO_NOM_ANALISTA, registro.getNom_analista());
        values.put(Util_RegistroFormatos.CAMPO_TIPO_VIBRACION, registro.getTipo_vibracion());
        values.put(Util_RegistroFormatos.CAMPO_VERF_INSITU, registro.getVerf_insitu());
        values.put(Util_RegistroFormatos.CAMPO_HORA_SITU, registro.getHora_situ());
        values.put(Util_RegistroFormatos.CAMPO_FEC_MONITOREO, registro.getFec_monitoreo());
        values.put(Util_RegistroFormatos.CAMPO_HORA_INICIAL, registro.getHora_inicial());
        values.put(Util_RegistroFormatos.CAMPO_HORA_FINAL, registro.getHora_final());
        values.put(Util_RegistroFormatos.CAMPO_TIEMPO_EXPOSICION, registro.getTiempo_exposicion());
        values.put(Util_RegistroFormatos.CAMPO_JORNADA, registro.getJornada());
        values.put(Util_RegistroFormatos.CAMPO_TIPO_DOC_TRABAJADOR, registro.getTipo_doc_trabajador());
        values.put(Util_RegistroFormatos.CAMPO_NUM_DOC_TRABAJADOR, registro.getNum_doc_trabajador());
        values.put(Util_RegistroFormatos.CAMPO_NOM_TRABAJADOR, registro.getNom_trabajador());
        values.put(Util_RegistroFormatos.CAMPO_PUESTO_TRABAJADOR, registro.getPuesto_trabajador());
        values.put(Util_RegistroFormatos.CAMPO_AREA_TRABAJO, registro.getArea_trabajo());
        values.put(Util_RegistroFormatos.CAMPO_ACTIVIDADES_REALIZADAS, registro.getActividades_realizadas());
        values.put(Util_RegistroFormatos.CAMPO_EDAD_TRABAJADOR, registro.getEdad_trabajador());
        values.put(Util_RegistroFormatos.CAMPO_HORA_TRABAJO, registro.getHora_trabajo());
        values.put(Util_RegistroFormatos.CAMPO_HORARIO_REFRIGERIO, registro.getHorario_refrigerio());
        values.put(Util_RegistroFormatos.CAMPO_REGIMEN_LABORAL, registro.getRegimen_laboral());
        values.put(Util_RegistroFormatos.CAMPO_CTRL_INGENIERIA, registro.getCtrl_ingenieria());
        values.put(Util_RegistroFormatos.CAMPO_NOM_CTRL_INGENIERIA, registro.getNom_ctrl_ingenieria());
        values.put(Util_RegistroFormatos.CAMPO_CTRL_ADMINISTRATIVO, registro.getCtrl_administrativo());
        values.put(Util_RegistroFormatos.CAMPO_SENIALIZACION_VIBRACION, registro.getSenializacion_vibracion());
        values.put(Util_RegistroFormatos.CAMPO_CAPACITACION, registro.getCapacitacion());
        values.put(Util_RegistroFormatos.CAMPO_MANTENIMIENTO_VIBRACION, registro.getMantenimiento_vibracion());
        values.put(Util_RegistroFormatos.CAMPO_ESTADO, registro.getEstado());
        values.put(Util_RegistroFormatos.CAMPO_FEC_REG, registro.getFec_reg());
        values.put(Util_RegistroFormatos.CAMPO_USER_REG, registro.getUser_reg());


        /*values.put(Util_VibracionRegistro.CAMPO_COD_FORMATO, registro.getCod_formato());
        values.put(Util_VibracionRegistro.CAMPO_ID_FORMATO, registro.getId_formato());
        values.put(Util_VibracionRegistro.CAMPO_ID_PLAN_TRABAJO, registro.getId_plan_trabajo());
        values.put(Util_VibracionRegistro.CAMPO_ID_PT_FORMATO, registro.getId_pt_formato());
        values.put(Util_VibracionRegistro.CAMPO_COD_EQUIPO1, registro.getCod_equipo1());
        values.put(Util_VibracionRegistro.CAMPO_NOM_EQUIPO1, registro.getNom_equipo1());
        values.put(Util_VibracionRegistro.CAMPO_SERIE_EQ1, registro.getSerie_eq1());
        values.put(Util_VibracionRegistro.CAMPO_ID_EQUIPO1, registro.getId_equipo1());
        values.put(Util_VibracionRegistro.CAMPO_ID_ANALISTA, registro.getId_analista());
        values.put(Util_VibracionRegistro.CAMPO_NOM_ANALISTA, registro.getNom_analista());
        values.put(Util_VibracionRegistro.CAMPO_TIPO_VIBRACION, registro.getTipo_vibracion());
        values.put(Util_VibracionRegistro.CAMPO_VERF_INSITU, registro.getVerf_insitu());
        values.put(Util_VibracionRegistro.CAMPO_HORA_SITU, registro.getHora_situ());
        values.put(Util_VibracionRegistro.CAMPO_FEC_MONITOREO, registro.getFec_monitoreo());
        values.put(Util_VibracionRegistro.CAMPO_HORA_INICIAL, registro.getHora_inicial());
        values.put(Util_VibracionRegistro.CAMPO_HORA_FINAL, registro.getHora_final());
        values.put(Util_VibracionRegistro.CAMPO_TIEMPO_EXPOSICION, registro.getTiempo_exposicion());
        values.put(Util_VibracionRegistro.CAMPO_JORNADA, registro.getJornada());
        values.put(Util_VibracionRegistro.CAMPO_TIPO_DOC_TRABAJADOR, registro.getTipo_doc_trabajador());
        values.put(Util_VibracionRegistro.CAMPO_NUM_DOC_TRABAJADOR, registro.getNum_doc_trabajador());
        values.put(Util_VibracionRegistro.CAMPO_NOM_TRABAJADOR, registro.getNom_trabajador());
        values.put(Util_VibracionRegistro.CAMPO_PUESTO_TRABAJADOR, registro.getPuesto_trabajador());
        values.put(Util_VibracionRegistro.CAMPO_AREA_TRABAJO, registro.getArea_trabajo());
        values.put(Util_VibracionRegistro.CAMPO_ACTIVIDADES_REALIZADAS, registro.getActividades_realizadas());
        values.put(Util_VibracionRegistro.CAMPO_EDAD_TRABAJADOR, registro.getEdad_trabajador());
        values.put(Util_VibracionRegistro.CAMPO_HORA_TRABAJO, registro.getHora_trabajo());
        values.put(Util_VibracionRegistro.CAMPO_HORARIO_REFRIGERIO, registro.getHorario_refrigerio());
        values.put(Util_VibracionRegistro.CAMPO_REGIMEN_LABORAL, registro.getRegimen_laboral());
        values.put(Util_VibracionRegistro.CAMPO_CTRL_INGENIERIA, registro.getCtrl_ingenieria());
        values.put(Util_VibracionRegistro.CAMPO_NOM_CTRL_INGENIERIA, registro.getNom_ctrl_ingenieria());
        values.put(Util_VibracionRegistro.CAMPO_CTRL_ADMINISTRATIVO, registro.getCtrl_administrativo());
        values.put(Util_VibracionRegistro.CAMPO_SENIALIZACION_VIBRACION, registro.getSenializacion_vibracion());
        values.put(Util_VibracionRegistro.CAMPO_CAPACITACION, registro.getCapacitacion());
        values.put(Util_VibracionRegistro.CAMPO_MANTENIMIENTO_VIBRACION, registro.getMantenimiento_vibracion());
        values.put(Util_VibracionRegistro.CAMPO_ESTADO, registro.getEstado());
        values.put(Util_VibracionRegistro.CAMPO_FEC_REG, registro.getFec_reg());
        values.put(Util_VibracionRegistro.CAMPO_USER_REG, registro.getUser_reg());*/

        db.insert(Util_RegistroFormatos.TABLA_REGISTRO_FORMATOS,null,values);

        return true;
    }

    public boolean RegistrarVibracionDetalle(Vibracion_RegistroDetalle registro){
        SQLiteDatabase db = datHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Util_RegistroFormatos_Detalle.CAMPO_ID_FORMATO_REG_DETALLE,-1); //--------------------> PRUEBA PARA EL REGISTRO EN LA TABLA GENERAL
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ID_PLAN_TRABAJO_FORMATO_REG, registro.getId_plan_trabajo_formato_reg());//----------------> PRUEBA PARA EL REGISTRO EN LA TABLA GENERAL
        values.put(Util_RegistroFormatos_Detalle.CAMPO_FUENTE_GENERADORA, registro.getFuente_generadora());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_DESC_FUENTE_FRIO, registro.getDesc_fuente_frio());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_FRECUENCIA, registro.getFrecuencia());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_BOTAS, registro.getEpp_botas());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_GUANTES, registro.getEpp_guantes());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_CASCO, registro.getEpp_casco());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_OREJERAS, registro.getEpp_orejeras());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_OTRO_EPP, registro.getOtro_epp());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_X, registro.getX());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_Y, registro.getY());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_Z, registro.getZ());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ESTADO, registro.getEstado());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_FEC_REG, registro.getFec_reg());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_USER_REG, registro.getUser_reg());


        //values.put(Util_VibracionRegistroDetalle.CAMPO_ID_PLAN_TRABAJO_FORMATO_REG, registro.getId_plan_trabajo_formato_reg());
        /*values.put(Util_VibracionRegistroDetalle.CAMPO_FUENTE_GENERADORA, registro.getFuente_generadora());
        values.put(Util_VibracionRegistroDetalle.CAMPO_DESC_FUENTE_FRIO, registro.getDesc_fuente_frio());
        values.put(Util_VibracionRegistroDetalle.CAMPO_FRECUENCIA, registro.getFrecuencia());
        values.put(Util_VibracionRegistroDetalle.CAMPO_EPP_BOTAS, registro.getEpp_botas());
        values.put(Util_VibracionRegistroDetalle.CAMPO_EPP_GUANTES, registro.getEpp_guantes());
        values.put(Util_VibracionRegistroDetalle.CAMPO_EPP_CASCO, registro.getEpp_casco());
        values.put(Util_VibracionRegistroDetalle.CAMPO_EPP_OREJERAS, registro.getEpp_orejeras());
        values.put(Util_VibracionRegistroDetalle.CAMPO_OTRO_EPP, registro.getOtro_epp());
        values.put(Util_VibracionRegistroDetalle.CAMPO_X, registro.getX());
        values.put(Util_VibracionRegistroDetalle.CAMPO_Y, registro.getY());
        values.put(Util_VibracionRegistroDetalle.CAMPO_Z, registro.getZ());
        values.put(Util_VibracionRegistroDetalle.CAMPO_ESTADO, registro.getEstado());
        values.put(Util_VibracionRegistroDetalle.CAMPO_FEC_REG, registro.getFec_reg());
        values.put(Util_VibracionRegistroDetalle.CAMPO_USER_REG, registro.getUser_reg());*/

        db.insert(Util_RegistroFormatos_Detalle.TABLA_REGISTRO_DETALLE,null,values);

        return true;
    }
}
