package com.mijael.mein.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.mijael.mein.Entidades.Confort_Registro;
import com.mijael.mein.Entidades.Confort_RegistroDetalle;
import com.mijael.mein.Entidades.EstresFrio_Registro;
import com.mijael.mein.Entidades.EstresFrio_RegistroDetalle;
import com.mijael.mein.HELPER.MeinSQLiteHelper;
import com.mijael.mein.Utilidades.Util_RegistroFormatos;
import com.mijael.mein.Utilidades.Util_RegistroFormatos_Detalle;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DAO_RegistroConfort {
    public MeinSQLiteHelper datHelper;

    public DAO_RegistroConfort(Context context){
        this.datHelper = MeinSQLiteHelper.getInstance(context);
    }

    public boolean InsertCabecera(Confort_Registro registro){
        SQLiteDatabase db = datHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Util_RegistroFormatos.CAMPO_COD_FORMATO, registro.getCod_formato());
        values.put(Util_RegistroFormatos.CAMPO_COD_REGISTRO, registro.getCod_registro());
        values.put(Util_RegistroFormatos.CAMPO_ID_FORMATO, registro.getId_formato());
        values.put(Util_RegistroFormatos.CAMPO_ID_PLAN_TRABAJO, registro.getId_plan_trabajo());
        values.put(Util_RegistroFormatos.CAMPO_ID_PT_FORMATO, registro.getId_pt_formato());
        values.put(Util_RegistroFormatos.CAMPO_ID_EQUIPO1, registro.getId_equipo1());
        values.put(Util_RegistroFormatos.CAMPO_COD_EQUIPO1, registro.getCod_equipo1());
        values.put(Util_RegistroFormatos.CAMPO_NOM_EQUIPO1, registro.getNom_equipo1());
        values.put(Util_RegistroFormatos.CAMPO_SERIE_EQ1, registro.getSerie_eq1());
        values.put(Util_RegistroFormatos.CAMPO_ID_EQUIPO2, registro.getId_equipo2());
        values.put(Util_RegistroFormatos.CAMPO_COD_EQUIPO2, registro.getCod_equipo2());
        values.put(Util_RegistroFormatos.CAMPO_NOM_EQUIPO2, registro.getNom_equipo2());
        values.put(Util_RegistroFormatos.CAMPO_SERIE_EQ2, registro.getSerie_eq2());
        values.put(Util_RegistroFormatos.CAMPO_ID_ANALISTA, registro.getId_analista());
        values.put(Util_RegistroFormatos.CAMPO_NOM_ANALISTA, registro.getNom_analista());
        values.put(Util_RegistroFormatos.CAMPO_HORA_SITU, registro.getHora_situ());
        values.put(Util_RegistroFormatos.CAMPO_VERF_INSITU, registro.getVerf_insitu());
        values.put(Util_RegistroFormatos.CAMPO_FEC_MONITOREO, registro.getFec_monitoreo());
        values.put(Util_RegistroFormatos.CAMPO_HORA_INICIAL, registro.getHora_inicial());
        values.put(Util_RegistroFormatos.CAMPO_HORA_FINAL, registro.getHora_final());
        values.put(Util_RegistroFormatos.CAMPO_TIEMPO_MEDICION, registro.getTiempo_medicion());
        values.put(Util_RegistroFormatos.CAMPO_TIEMPO_EXPOSICION, registro.getTiempo_exposicion());
        values.put(Util_RegistroFormatos.CAMPO_JORNADA, registro.getJornada());
        values.put(Util_RegistroFormatos.CAMPO_TIPO_DOC_TRABAJADOR, registro.getTipo_doc_trabajador());
        values.put(Util_RegistroFormatos.CAMPO_NUM_DOC_TRABAJADOR, registro.getNum_doc_trabajador());
        values.put(Util_RegistroFormatos.CAMPO_NOM_TRABAJADOR, registro.getNom_trabajador());
        values.put(Util_RegistroFormatos.CAMPO_PUESTO_TRABAJADOR, registro.getPuesto_trabajador());
        values.put(Util_RegistroFormatos.CAMPO_AREA_TRABAJO, registro.getArea_trabajo());
        values.put(Util_RegistroFormatos.CAMPO_ACTIVIDADES_REALIZADAS, registro.getActividades_realizadas());
        values.put(Util_RegistroFormatos.CAMPO_PESO_TRABAJADOR, registro.getPeso_trabajador());
        values.put(Util_RegistroFormatos.CAMPO_EDAD_TRABAJADOR, registro.getEdad_trabajador());
        values.put(Util_RegistroFormatos.CAMPO_HORA_TRABAJO, registro.getHora_trabajo());
        values.put(Util_RegistroFormatos.CAMPO_HORARIO_REFRIGERIO, registro.getHorario_refrigerio());
        values.put(Util_RegistroFormatos.CAMPO_REGIMEN_LABORAL, registro.getRegimen_laboral());
        values.put(Util_RegistroFormatos.CAMPO_ANIO_OCU_CARGO, registro.getAnio_ocu_cargo());
        values.put(Util_RegistroFormatos.CAMPO_MES_OCU_CARGO, registro.getMes_ocu_cargo());
        values.put(Util_RegistroFormatos.CAMPO_OBSERVACION, registro.getObservacion());
        values.put(Util_RegistroFormatos.CAMPO_RUTA_FOTO, registro.getRuta_foto());
        values.put(Util_RegistroFormatos.CAMPO_ESTADO, registro.getEstado());
        values.put(Util_RegistroFormatos.CAMPO_FEC_REG, registro.getFec_reg());
        values.put(Util_RegistroFormatos.CAMPO_USER_REG, registro.getUser_reg());
        values.put(Util_RegistroFormatos.CAMPO_ESTADO_SINCRO,1);// MARCANDO PARA SABER QUE FALTA SINCRONIZAR

        db.insert(Util_RegistroFormatos.TABLA_REGISTRO_FORMATOS,null,values);

        return true;
    }
    public boolean InsertDetalle(Confort_RegistroDetalle detalle){
        SQLiteDatabase db = datHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Util_RegistroFormatos_Detalle.CAMPO_ID_PLAN_TRABAJO_FORMATO_REG, detalle.getId_plan_trabajo_formato_reg());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_FUENTE_GENERADORA, detalle.getFuente_generadora());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_DESC_FUENTE_FRIO, detalle.getDesc_fuente_gen());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_DESC_ATUENDO, detalle.getDesc_atuendo());
        /*values.put(Util_RegistroFormatos_Detalle.CAMPO_AT_LIGERO, detalle.getLigero());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_AT_MEDIO, detalle.getMedio());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_AT_PESADO, detalle.getPesado());*/
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ID_NIVEL_D, detalle.getId_nivel_d());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_NOM_NIVEL_D, detalle.getNom_nivel_d());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ID_METODO_DETERM, detalle.getId_metodo_determ());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_METODO_DETERM, detalle.getMetodo_determ());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ID_TIPO_TRABAJO, detalle.getId_tipo_trabajo());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TIPO_TRABAJO, detalle.getTipo_trabajo());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_OCUPACION, detalle.getOcupacion());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_RANGO_TASA_METAB, detalle.getRango_tasa_metab());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CLASE, detalle.getClase());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ACTIVIDAD_DETER, detalle.getActividad_deter());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TASA_METAB, detalle.getTasa_metab());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TASA_METAB_KCAL, detalle.getTasa_metab_kcal());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_FRECUENCIA_DETER, detalle.getFrecuencia_deter());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_GENERO_DETER, detalle.getGenero_deter());

        values.put(Util_RegistroFormatos_Detalle.CAMPO_N_TAREAS, detalle.getNtareas());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_NOM_TAREA1, detalle.getNom_tarea1());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CICLO_TRABAJO1, detalle.getCiclo_trabajo1());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_POSICION_1, detalle.getPosicion_1());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PCUERPO_1, detalle.getPcuerpo_1());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_INTENSIDAD_1, detalle.getIntensidad_1());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_NOM_TAREA2, detalle.getNom_tarea2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CICLO_TRABAJO2, detalle.getCiclo_trabajo2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_POSICION_2, detalle.getPosicion_2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PCUERPO_2, detalle.getPcuerpo_2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_INTENSIDAD_2, detalle.getIntensidad_2());

        values.put(Util_RegistroFormatos_Detalle.CAMPO_NOM_TAREA3, detalle.getNom_tarea3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CICLO_TRABAJO3, detalle.getCiclo_trabajo3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_POSICION_3, detalle.getPosicion_3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PCUERPO_3, detalle.getPcuerpo_3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_INTENSIDAD_3, detalle.getIntensidad_3());

        values.put(Util_RegistroFormatos_Detalle.CAMPO_NOM_TAREA4, detalle.getNom_tarea4());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CICLO_TRABAJO4, detalle.getCiclo_trabajo4());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_POSICION_4, detalle.getPosicion_4());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PCUERPO_4, detalle.getPcuerpo_4());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_INTENSIDAD_4, detalle.getIntensidad_4());

        values.put(Util_RegistroFormatos_Detalle.CAMPO_NOM_TAREA5, detalle.getNom_tarea5());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CICLO_TRABAJO5, detalle.getCiclo_trabajo5());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_POSICION_5, detalle.getPosicion_5());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PCUERPO_5, detalle.getPcuerpo_5());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_INTENSIDAD_5, detalle.getIntensidad_5());

        values.put(Util_RegistroFormatos_Detalle.CAMPO_MTR_SUBIDA, detalle.getMtr_subida());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_T_AIRE, detalle.getT_aire());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_T_GLOBO, detalle.getT_globo());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_T_BULBO, detalle.getT_bulbo());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_H_RELATIVA, detalle.getH_relativa());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_V_VIENTO, detalle.getV_viento());

        values.put(Util_RegistroFormatos_Detalle.CAMPO_ESTADO, detalle.getEstado());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_FEC_REG, detalle.getFec_reg());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_USER_REG, detalle.getUser_reg());

        db.insert(Util_RegistroFormatos_Detalle.TABLA_REGISTRO_DETALLE,null,values);
        return true;
    }

    public boolean ActualizarConfort(Confort_Registro registro,int id_plan_trabajo_formato_reg){
        SQLiteDatabase db = datHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fecha = dateFormat.format(new Date());

        values.put(Util_RegistroFormatos.CAMPO_COD_FORMATO, registro.getCod_formato());
        values.put(Util_RegistroFormatos.CAMPO_COD_REGISTRO, registro.getCod_registro());
        values.put(Util_RegistroFormatos.CAMPO_ID_FORMATO, registro.getId_formato());
        values.put(Util_RegistroFormatos.CAMPO_ID_PLAN_TRABAJO, registro.getId_plan_trabajo());
        values.put(Util_RegistroFormatos.CAMPO_ID_PT_FORMATO, registro.getId_pt_formato());
        values.put(Util_RegistroFormatos.CAMPO_ID_EQUIPO1, registro.getId_equipo1());
        values.put(Util_RegistroFormatos.CAMPO_COD_EQUIPO1, registro.getCod_equipo1());
        values.put(Util_RegistroFormatos.CAMPO_NOM_EQUIPO1, registro.getNom_equipo1());
        values.put(Util_RegistroFormatos.CAMPO_SERIE_EQ1, registro.getSerie_eq1());
        values.put(Util_RegistroFormatos.CAMPO_ID_EQUIPO2, registro.getId_equipo2());
        values.put(Util_RegistroFormatos.CAMPO_COD_EQUIPO2, registro.getCod_equipo2());
        values.put(Util_RegistroFormatos.CAMPO_NOM_EQUIPO2, registro.getNom_equipo2());
        values.put(Util_RegistroFormatos.CAMPO_SERIE_EQ2, registro.getSerie_eq2());
        values.put(Util_RegistroFormatos.CAMPO_ID_ANALISTA, registro.getId_analista());
        values.put(Util_RegistroFormatos.CAMPO_NOM_ANALISTA, registro.getNom_analista());
        values.put(Util_RegistroFormatos.CAMPO_HORA_SITU, registro.getHora_situ());
        values.put(Util_RegistroFormatos.CAMPO_VERF_INSITU, registro.getVerf_insitu());
        values.put(Util_RegistroFormatos.CAMPO_FEC_MONITOREO, registro.getFec_monitoreo());
        values.put(Util_RegistroFormatos.CAMPO_HORA_INICIAL, registro.getHora_inicial());
        values.put(Util_RegistroFormatos.CAMPO_HORA_FINAL, registro.getHora_final());
        values.put(Util_RegistroFormatos.CAMPO_TIEMPO_MEDICION, registro.getTiempo_medicion());
        values.put(Util_RegistroFormatos.CAMPO_TIEMPO_EXPOSICION, registro.getTiempo_exposicion());
        values.put(Util_RegistroFormatos.CAMPO_JORNADA, registro.getJornada());
        values.put(Util_RegistroFormatos.CAMPO_TIPO_DOC_TRABAJADOR, registro.getTipo_doc_trabajador());
        values.put(Util_RegistroFormatos.CAMPO_NUM_DOC_TRABAJADOR, registro.getNum_doc_trabajador());
        values.put(Util_RegistroFormatos.CAMPO_NOM_TRABAJADOR, registro.getNom_trabajador());
        values.put(Util_RegistroFormatos.CAMPO_PUESTO_TRABAJADOR, registro.getPuesto_trabajador());
        values.put(Util_RegistroFormatos.CAMPO_AREA_TRABAJO, registro.getArea_trabajo());
        values.put(Util_RegistroFormatos.CAMPO_ACTIVIDADES_REALIZADAS, registro.getActividades_realizadas());
        values.put(Util_RegistroFormatos.CAMPO_PESO_TRABAJADOR, registro.getPeso_trabajador());
        values.put(Util_RegistroFormatos.CAMPO_EDAD_TRABAJADOR, registro.getEdad_trabajador());
        values.put(Util_RegistroFormatos.CAMPO_HORA_TRABAJO, registro.getHora_trabajo());
        values.put(Util_RegistroFormatos.CAMPO_HORARIO_REFRIGERIO, registro.getHorario_refrigerio());
        values.put(Util_RegistroFormatos.CAMPO_REGIMEN_LABORAL, registro.getRegimen_laboral());
        values.put(Util_RegistroFormatos.CAMPO_ANIO_OCU_CARGO, registro.getAnio_ocu_cargo());
        values.put(Util_RegistroFormatos.CAMPO_MES_OCU_CARGO, registro.getMes_ocu_cargo());
        values.put(Util_RegistroFormatos.CAMPO_OBSERVACION, registro.getObservacion());
        values.put(Util_RegistroFormatos.CAMPO_RUTA_FOTO, registro.getRuta_foto());
        values.put(Util_RegistroFormatos.CAMPO_ESTADO, registro.getEstado());
        values.put(Util_RegistroFormatos.CAMPO_FEC_REG, registro.getFec_reg());
        values.put(Util_RegistroFormatos.CAMPO_USER_REG, registro.getUser_reg());
        values.put(Util_RegistroFormatos.CAMPO_FEC_ACT, fecha);
        values.put(Util_RegistroFormatos.CAMPO_USER_ACT, registro.getId_analista());
        values.put(Util_RegistroFormatos.CAMPO_ESTADO_SINCRO,1);// MARCANDO PARA SABER QUE FALTA SINCRONIZAR

        String whereClause = Util_RegistroFormatos.CAMPO_ID_PLAN_TRABAJO_FORMATO_REG + " = ?";
        String[] whereArgs = {String.valueOf(id_plan_trabajo_formato_reg)};
        db.update(Util_RegistroFormatos.TABLA_REGISTRO_FORMATOS, values, whereClause, whereArgs);

        return true;
    }
    public boolean ActualizarConfort_Detalle(Confort_RegistroDetalle detalle, int id_formato_reg_detalle){
        SQLiteDatabase db = datHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fecha = dateFormat.format(new Date());

        values.put(Util_RegistroFormatos_Detalle.CAMPO_ID_PLAN_TRABAJO_FORMATO_REG, detalle.getId_plan_trabajo_formato_reg());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_FUENTE_GENERADORA, detalle.getFuente_generadora());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_DESC_FUENTE_FRIO, detalle.getDesc_fuente_gen());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_DESC_ATUENDO, detalle.getDesc_atuendo());
        /*values.put(Util_RegistroFormatos_Detalle.CAMPO_AT_LIGERO, detalle.getLigero());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_AT_MEDIO, detalle.getMedio());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_AT_PESADO, detalle.getPesado());*/
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ID_NIVEL_D, detalle.getId_nivel_d());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_NOM_NIVEL_D, detalle.getNom_nivel_d());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ID_METODO_DETERM, detalle.getId_metodo_determ());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_METODO_DETERM, detalle.getMetodo_determ());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ID_TIPO_TRABAJO, detalle.getId_tipo_trabajo());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TIPO_TRABAJO, detalle.getTipo_trabajo());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_OCUPACION, detalle.getOcupacion());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_RANGO_TASA_METAB, detalle.getRango_tasa_metab());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CLASE, detalle.getClase());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ACTIVIDAD_DETER, detalle.getActividad_deter());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TASA_METAB, detalle.getTasa_metab());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TASA_METAB_KCAL, detalle.getTasa_metab_kcal());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_FRECUENCIA_DETER, detalle.getFrecuencia_deter());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_GENERO_DETER, detalle.getGenero_deter());

        values.put(Util_RegistroFormatos_Detalle.CAMPO_N_TAREAS, detalle.getNtareas());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_NOM_TAREA1, detalle.getNom_tarea1());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CICLO_TRABAJO1, detalle.getCiclo_trabajo1());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_POSICION_1, detalle.getPosicion_1());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PCUERPO_1, detalle.getPcuerpo_1());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_INTENSIDAD_1, detalle.getIntensidad_1());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_NOM_TAREA2, detalle.getNom_tarea2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CICLO_TRABAJO2, detalle.getCiclo_trabajo2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_POSICION_2, detalle.getPosicion_2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PCUERPO_2, detalle.getPcuerpo_2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_INTENSIDAD_2, detalle.getIntensidad_2());

        values.put(Util_RegistroFormatos_Detalle.CAMPO_NOM_TAREA3, detalle.getNom_tarea3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CICLO_TRABAJO3, detalle.getCiclo_trabajo3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_POSICION_3, detalle.getPosicion_3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PCUERPO_3, detalle.getPcuerpo_3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_INTENSIDAD_3, detalle.getIntensidad_3());

        values.put(Util_RegistroFormatos_Detalle.CAMPO_NOM_TAREA4, detalle.getNom_tarea4());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CICLO_TRABAJO4, detalle.getCiclo_trabajo4());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_POSICION_4, detalle.getPosicion_4());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PCUERPO_4, detalle.getPcuerpo_4());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_INTENSIDAD_4, detalle.getIntensidad_4());

        values.put(Util_RegistroFormatos_Detalle.CAMPO_NOM_TAREA5, detalle.getNom_tarea5());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CICLO_TRABAJO5, detalle.getCiclo_trabajo5());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_POSICION_5, detalle.getPosicion_5());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PCUERPO_5, detalle.getPcuerpo_5());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_INTENSIDAD_5, detalle.getIntensidad_5());

        values.put(Util_RegistroFormatos_Detalle.CAMPO_MTR_SUBIDA, detalle.getMtr_subida());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_T_AIRE, detalle.getT_aire());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_T_GLOBO, detalle.getT_globo());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_T_BULBO, detalle.getT_bulbo());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_H_RELATIVA, detalle.getH_relativa());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_V_VIENTO, detalle.getV_viento());

        values.put(Util_RegistroFormatos_Detalle.CAMPO_ESTADO, detalle.getEstado());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_FEC_REG, detalle.getFec_reg());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_USER_REG, detalle.getUser_reg());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_FEC_ACT, fecha);
        values.put(Util_RegistroFormatos_Detalle.CAMPO_USER_ACT, detalle.getUser_reg());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ESTADO_SINCRO,1);// MARCANDO PARA SABER QUE FALTA SINCRONIZAR

        String whereClause = Util_RegistroFormatos_Detalle.CAMPO_ID_FORMATO_REG_DETALLE + " = ?";
        String[] whereArgs = {String.valueOf(id_formato_reg_detalle)};
        db.update(Util_RegistroFormatos_Detalle.TABLA_REGISTRO_DETALLE, values, whereClause, whereArgs);
        return true;
    }
}
