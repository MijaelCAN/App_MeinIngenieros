package com.mijael.mein.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.mijael.mein.Entidades.Iluminacion_Registro;
import com.mijael.mein.Entidades.Iluminacion_RegistroDetalle;
import com.mijael.mein.HELPER.RegistroFormatosSQLiteHelper;
import com.mijael.mein.Utilidades.Util_IluminacionRegistro;
import com.mijael.mein.Utilidades.Util_IluminacionRegistroDetalle;
import com.mijael.mein.Utilidades.Util_RegistroFormatos;
import com.mijael.mein.Utilidades.Util_RegistroFormatos_Detalle;
import com.mijael.mein.Utilidades.Util_SonometriaRegistro;

public class DAO_RegistroIluminacion {
    public int id_plan_trabajo_formato_reg;
    public RegistroFormatosSQLiteHelper datHelper;
    public DAO_RegistroIluminacion(Context context){
        this.datHelper = RegistroFormatosSQLiteHelper.getInstance(context);
    }
     public boolean RegistroIluminacion(Iluminacion_Registro registro){
         SQLiteDatabase db = datHelper.getWritableDatabase();
         ContentValues values = new ContentValues();

         values.put(Util_RegistroFormatos.CAMPO_COD_FORMATO, registro.getCod_formato());
         values.put(Util_RegistroFormatos.CAMPO_ID_FORMATO, registro.getId_formato());
         values.put(Util_RegistroFormatos.CAMPO_ID_PLAN_TRABAJO, registro.getId_plan_trabajo());
         values.put(Util_RegistroFormatos.CAMPO_ID_PT_FORMATO, registro.getId_pt_formato());
         values.put(Util_RegistroFormatos.CAMPO_ID_ANALISTA, registro.getId_analista());
         values.put(Util_RegistroFormatos.CAMPO_NOM_ANALISTA, registro.getNom_analista());
         values.put(Util_RegistroFormatos.CAMPO_ID_EQUIPO1, registro.getId_equipo1());
         values.put(Util_RegistroFormatos.CAMPO_COD_EQUIPO1, registro.getCod_equipo1());
         values.put(Util_RegistroFormatos.CAMPO_NOM_EQUIPO1, registro.getNom_equipo1());
         values.put(Util_RegistroFormatos.CAMPO_SERIE_EQ1, registro.getSerie_eq1());
         values.put(Util_RegistroFormatos.CAMPO_HORA_SITU, registro.getHora_situ());
         values.put(Util_RegistroFormatos.CAMPO_LUZ, registro.getLuz());
         values.put(Util_RegistroFormatos.CAMPO_TIPO_DOC_TRABAJADOR, registro.getTipo_doc_trabajador());
         values.put(Util_RegistroFormatos.CAMPO_NUM_DOC_TRABAJADOR, registro.getNum_doc_trabajador());
         values.put(Util_RegistroFormatos.CAMPO_NOM_TRABAJADOR, registro.getNom_trabajador());
         values.put(Util_RegistroFormatos.CAMPO_PUESTO_TRABAJADOR, registro.getPuesto_trabajador());
         values.put(Util_RegistroFormatos.CAMPO_AREA_TRABAJO, registro.getArea_trabajo());
         values.put(Util_RegistroFormatos.CAMPO_N_PERSONAS, registro.getN_personas());
         values.put(Util_RegistroFormatos.CAMPO_HORA_TRABAJO, registro.getHora_trabajo());
         values.put(Util_RegistroFormatos.CAMPO_REGIMEN_LABORAL, registro.getRegimen_laboral());
         values.put(Util_RegistroFormatos.CAMPO_FEC_MONITOREO, registro.getFec_monitoreo());
         values.put(Util_RegistroFormatos.CAMPO_HORA_INICIAL, registro.getHora_inicial());
         values.put(Util_RegistroFormatos.CAMPO_ACTIVIDADES_REALIZADAS, registro.getActividades_realizadas());
         values.put(Util_RegistroFormatos.CAMPO_OBSERVACION, registro.getObservacion());
         values.put(Util_RegistroFormatos.CAMPO_UBIC_EQUIP, registro.getUbic_equip());
         values.put(Util_RegistroFormatos.CAMPO_TAREA_VISUAL, registro.getTarea_visual());
         values.put(Util_RegistroFormatos.CAMPO_TIPO_TAREA_VISUAL, registro.getTipo_tarea_visual());
         values.put(Util_RegistroFormatos.CAMPO_NIVEL_MIN_ILU, registro.getNivel_min_ilu());
         values.put(Util_RegistroFormatos.CAMPO_ESTADO, registro.getEstado());
         values.put(Util_RegistroFormatos.CAMPO_FEC_REG, registro.getFec_reg());
         values.put(Util_RegistroFormatos.CAMPO_USER_REG, registro.getUser_reg());

         /*values.put(Util_IluminacionRegistro.CAMPO_COD_FORMATO, registro.getCod_formato());
         values.put(Util_IluminacionRegistro.CAMPO_ID_FORMATO, registro.getId_formato());
         values.put(Util_IluminacionRegistro.CAMPO_ID_PLAN_TRABAJO, registro.getId_plan_trabajo());
         values.put(Util_IluminacionRegistro.CAMPO_ID_PT_FORMATO, registro.getId_pt_formato());
         values.put(Util_IluminacionRegistro.CAMPO_ID_ANALISTA, registro.getId_analista());
         values.put(Util_IluminacionRegistro.CAMPO_NOM_ANALISTA, registro.getNom_analista());
         values.put(Util_IluminacionRegistro.CAMPO_ID_EQUIPO1, registro.getId_equipo1());
         values.put(Util_IluminacionRegistro.CAMPO_COD_EQUIPO1, registro.getCod_equipo1());
         values.put(Util_IluminacionRegistro.CAMPO_NOM_EQUIPO1, registro.getNom_equipo1());
         values.put(Util_IluminacionRegistro.CAMPO_SERIE_EQ1, registro.getSerie_eq1());
         values.put(Util_IluminacionRegistro.CAMPO_HORA_SITU, registro.getHora_situ());
         values.put(Util_IluminacionRegistro.CAMPO_LUX, registro.getLuz());
         values.put(Util_IluminacionRegistro.CAMPO_TIPO_DOC_TRABAJADOR, registro.getTipo_doc_trabajador());
         values.put(Util_IluminacionRegistro.CAMPO_NUM_DOC_TRABAJADOR, registro.getNum_doc_trabajador());
         values.put(Util_IluminacionRegistro.CAMPO_NOM_TRABAJADOR, registro.getNom_trabajador());
         values.put(Util_IluminacionRegistro.CAMPO_PUESTO_TRABAJADOR, registro.getPuesto_trabajador());
         values.put(Util_IluminacionRegistro.CAMPO_AREA_TRABAJO, registro.getArea_trabajo());
         values.put(Util_IluminacionRegistro.CAMPO_N_PERSONAS, registro.getN_personas());
         values.put(Util_IluminacionRegistro.CAMPO_HORA_TRABAJO, registro.getHora_trabajo());
         values.put(Util_IluminacionRegistro.CAMPO_REGIMEN_LABORAL, registro.getRegimen_laboral());
         values.put(Util_IluminacionRegistro.CAMPO_FEC_MONITOREO, registro.getFec_monitoreo());
         values.put(Util_IluminacionRegistro.CAMPO_HORA_INICIAL, registro.getHora_inicial());
         values.put(Util_IluminacionRegistro.CAMPO_ACTIVIDADES_REALIZADAS, registro.getActividades_realizadas());
         values.put(Util_IluminacionRegistro.CAMPO_OBSERVACION, registro.getObservacion());
         values.put(Util_IluminacionRegistro.CAMPO_UBIC_EQUIP, registro.getUbic_equip());
         values.put(Util_IluminacionRegistro.CAMPO_TAREA_VISUAL, registro.getTarea_visual());
         values.put(Util_IluminacionRegistro.CAMPO_TIPO_TAREA_VISUAL, registro.getTipo_tarea_visual());
         values.put(Util_IluminacionRegistro.CAMPO_NIVEL_MIN_ILU, registro.getNivel_min_ilu());
         values.put(Util_IluminacionRegistro.CAMPO_ESTADO, registro.getEstado());
         values.put(Util_IluminacionRegistro.CAMPO_FEC_REG, registro.getFec_reg());
         values.put(Util_IluminacionRegistro.CAMPO_USER_REG, registro.getUser_reg());*/

         db.insert(Util_RegistroFormatos.TABLA_REGISTRO_FORMATOS,null,values);

         return true;
     }

    public boolean RegistroIluminacionDetalle(Iluminacion_RegistroDetalle registro){
        SQLiteDatabase db = datHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(Util_IluminacionRegistroDetalle.CAMPO_ID_PLAN_TRABAJO_FORMATO_REG, registro.getId_plan_trabajo_formato_reg());

        values.put(Util_RegistroFormatos_Detalle.CAMPO_ID_FORMATO_REG_DETALLE, -1); //--------------------> PRUEBA PARA EL REGISTRO EN LA TABLA GENERAL
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ID_PLAN_TRABAJO_FORMATO_REG, registro.getId_plan_trabajo_formato_reg());//----------------> PRUEBA PARA EL REGISTRO EN LA TABLA GENERAL
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TIPO_ILUMINACION, registro.getTipo_iluminacion());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ID_TIPO_MEDICION_IL, registro.getTipo_medicion_ilu());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TIPO_MEDICION_ILU, registro.getTipo_medicion_ilu());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_LARG_ESCRIT, registro.getLarg_escrit());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ANCH_ESCRIT, registro.getAnch_escrit());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_NUM_PMEDICION, registro.getNum_pmedicion());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ALT_PLTRABAJO, registro.getAlt_pltrabajo());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_LONG_SALON, registro.getLong_salon());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ANCH_SALON, registro.getAnch_salon());

        values.put(Util_RegistroFormatos_Detalle.CAMPO_NUM_MIN_PMEDIC, registro.getNum_min_pmedic());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CANT_ILUMINARIAS, registro.getCant_iluminarias());

        /*values.put(Util_RegistroFormatos_Detalle.CAMPO_IL_1, registro.getIl_1());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_IL_2, registro.getIl_2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_IL_3, registro.getIl_3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_IL_4, registro.getIl_4());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_IL_5, registro.getIl_5());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_IL_6, registro.getIl_6());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_IL_7, registro.getIl_7());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_IL_8, registro.getIl_8());*/
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PLAN_MANTENIMIENTO_ILUM, registro.getPlan_mantenimiento_ilum());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_AREA_TRABAJO_M2, registro.getArea_trabajo_m2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ALTURA_P_TRABAJO, registro.getAltura_p_trabajo());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_N_LAMPARAS, registro.getN_lamparas());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ALTURA_P_LUMINARIA, registro.getAltura_p_luminaria());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_COLOR_PARED, registro.getColor_pared());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_COLOR_PISO, registro.getColor_piso());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ESTADO_FISICO, registro.getEstado_fisico());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_FEC_REG, registro.getFec_reg());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_USER_REG, registro.getUser_reg());



        /*values.put(Util_IluminacionRegistroDetalle.CAMPO_TIPO_ILUMINACION_ART, registro.getTipo_iluminacion_art());
        values.put(Util_IluminacionRegistroDetalle.CAMPO_TIPO_ILUMINACION_NAT, registro.getTipo_iluminacion_nat());
        values.put(Util_IluminacionRegistroDetalle.CAMPO_CANT_ILUMINARIAS, registro.getCant_iluminarias());
        values.put(Util_IluminacionRegistroDetalle.CAMPO_IL_1, registro.getIl_1());
        values.put(Util_IluminacionRegistroDetalle.CAMPO_IL_2, registro.getIl_2());
        values.put(Util_IluminacionRegistroDetalle.CAMPO_IL_3, registro.getIl_3());
        values.put(Util_IluminacionRegistroDetalle.CAMPO_IL_4, registro.getIl_4());
        values.put(Util_IluminacionRegistroDetalle.CAMPO_IL_5, registro.getIl_5());
        values.put(Util_IluminacionRegistroDetalle.CAMPO_IL_6, registro.getIl_6());
        values.put(Util_IluminacionRegistroDetalle.CAMPO_IL_7, registro.getIl_7());
        values.put(Util_IluminacionRegistroDetalle.CAMPO_IL_8, registro.getIl_8());
        values.put(Util_IluminacionRegistroDetalle.CAMPO_PLAN_MANTENIMIENTO_ILUM, registro.getPlan_mantenimiento_ilum());
        values.put(Util_IluminacionRegistroDetalle.CAMPO_AREA_TRABAJO_M2, registro.getArea_trabajo_m2());
        values.put(Util_IluminacionRegistroDetalle.CAMPO_ALTURA_P_TRABAJO, registro.getAltura_p_trabajo());
        values.put(Util_IluminacionRegistroDetalle.CAMPO_N_LAMPARAS, registro.getN_lamparas());
        values.put(Util_IluminacionRegistroDetalle.CAMPO_ALTURA_P_LUMINARIA, registro.getAltura_p_luminaria());
        values.put(Util_IluminacionRegistroDetalle.CAMPO_COLOR_PARED, registro.getColor_pared());
        values.put(Util_IluminacionRegistroDetalle.CAMPO_COLOR_PISO, registro.getColor_piso());
        values.put(Util_IluminacionRegistroDetalle.CAMPO_ESTADO_FISICO, registro.getEstado_fisico());
        values.put(Util_IluminacionRegistroDetalle.CAMPO_FEC_REG, registro.getFec_reg());
        values.put(Util_IluminacionRegistroDetalle.CAMPO_USER_REG, registro.getUser_reg());*/

        db.insert(Util_RegistroFormatos_Detalle.TABLA_REGISTRO_DETALLE,null,values);

        return true;
    }
}
