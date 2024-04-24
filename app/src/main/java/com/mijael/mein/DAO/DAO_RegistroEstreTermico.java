package com.mijael.mein.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.mijael.mein.Entidades.EstresTermico_Registro;
import com.mijael.mein.Entidades.EstresTermico_RegistroDetalle;
import com.mijael.mein.HELPER.MeinSQLiteHelper;
import com.mijael.mein.Utilidades.Util_RegistroFormatos;
import com.mijael.mein.Utilidades.Util_RegistroFormatos_Detalle;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DAO_RegistroEstreTermico {
    public MeinSQLiteHelper datHelper;
    public DAO_RegistroEstreTermico(Context context){
        this.datHelper = MeinSQLiteHelper.getInstance(context);
    }

    public boolean RegistroEstresTermico(EstresTermico_Registro registro){
        SQLiteDatabase db = datHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        //AÑADIR EL CAMPO DE ID DE REGISTRO CUANDO SE HAGA LA SINCRONIZACION
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
        values.put(Util_RegistroFormatos.CAMPO_DESC_AREA_TRABAJO, registro.getDesc_area_trabajo());
        values.put(Util_RegistroFormatos.CAMPO_AREA_TRAB_DETA, registro.getArea_trab_deta());
        values.put(Util_RegistroFormatos.CAMPO_CTRL_INGENIERIA, registro.getCtrl_ingenieria());
        values.put(Util_RegistroFormatos.CAMPO_NOM_CTRL_INGENIERIA, registro.getNom_ctrl_ingenieria());
        values.put(Util_RegistroFormatos.CAMPO_CTRL_ADMINISTRATIVO, registro.getCtrl_administrativo());
        values.put(Util_RegistroFormatos.CAMPO_ANIO_OCU_CARGO, registro.getAnio_ocu_cargo());
        values.put(Util_RegistroFormatos.CAMPO_MES_OCU_CARGO, registro.getMes_ocu_cargo());
        values.put(Util_RegistroFormatos.CAMPO_COND_TRAB, registro.getCond_trab());
        values.put(Util_RegistroFormatos.CAMPO_OBSERVACION, registro.getObservacion());
        values.put(Util_RegistroFormatos.CAMPO_NOM_TIPO_MEDICION, registro.getNom_tipo_medicion());
        values.put(Util_RegistroFormatos.CAMPO_RUTA_FOTO, registro.getRuta_foto());
        values.put(Util_RegistroFormatos.CAMPO_ESTADO, registro.getEstado());
        values.put(Util_RegistroFormatos.CAMPO_FEC_REG, registro.getFec_reg());
        values.put(Util_RegistroFormatos.CAMPO_USER_REG, registro.getUser_reg());
        values.put(Util_RegistroFormatos.CAMPO_ESTADO_SINCRO,1);// MARCANDO PARA SABER QUE FALTA SINCRONIZAR




        //values.put(Util_EstresTermicoRegistro.CAMPO_ID, registro.getCodigo());
        /*values.put(Util_EstresTermicoRegistro.CAMPO_COD_FORMATO, registro.getCod_formato());
        values.put(Util_EstresTermicoRegistro.CAMPO_ID_FORMATO, registro.getId_formato());
        values.put(Util_EstresTermicoRegistro.CAMPO_ID_PLAN_TRABAJO, registro.getId_plan_trabajo());
        values.put(Util_EstresTermicoRegistro.CAMPO_ID_PT_FORMATO, registro.getId_pt_formato());
        values.put(Util_EstresTermicoRegistro.CAMPO_ID_EQUIPO1, registro.getId_equipo1());
        values.put(Util_EstresTermicoRegistro.CAMPO_COD_EQUIPO1, registro.getCod_equipo1());
        values.put(Util_EstresTermicoRegistro.CAMPO_NOM_EQUIPO1, registro.getNom_equipo1());
        values.put(Util_EstresTermicoRegistro.CAMPO_SERIE_EQ1, registro.getSerie_eq1());
        values.put(Util_EstresTermicoRegistro.CAMPO_ID_EQUIPO2, registro.getId_equipo2());
        values.put(Util_EstresTermicoRegistro.CAMPO_COD_EQUIPO2, registro.getCod_equipo2());
        values.put(Util_EstresTermicoRegistro.CAMPO_NOM_EQUIPO2, registro.getNom_equipo2());
        values.put(Util_EstresTermicoRegistro.CAMPO_SERIE_EQ2, registro.getSerie_eq2());
        values.put(Util_EstresTermicoRegistro.CAMPO_ID_ANALISTA, registro.getId_analista());
        values.put(Util_EstresTermicoRegistro.CAMPO_NOM_ANALISTA, registro.getNom_analista());
        values.put(Util_EstresTermicoRegistro.CAMPO_HORA_SITU, registro.getHora_situ());
        values.put(Util_EstresTermicoRegistro.CAMPO_VERF_INSITU, registro.getVerf_insitu());
        values.put(Util_EstresTermicoRegistro.CAMPO_FEC_MONITOREO, registro.getFec_monitoreo());
        values.put(Util_EstresTermicoRegistro.CAMPO_HORA_INICIAL, registro.getHora_inicial());
        values.put(Util_EstresTermicoRegistro.CAMPO_HORA_FINAL, registro.getHora_final());
        values.put(Util_EstresTermicoRegistro.CAMPO_TIEMPO_MEDICION, registro.getTiempo_medicion());
        values.put(Util_EstresTermicoRegistro.CAMPO_TIEMPO_EXPOSICION, registro.getTiempo_exposicion());
        values.put(Util_EstresTermicoRegistro.CAMPO_JORNADA, registro.getJornada());
        values.put(Util_EstresTermicoRegistro.CAMPO_TIPO_DOC_TRABAJADOR, registro.getTipo_doc_trabajador());
        values.put(Util_EstresTermicoRegistro.CAMPO_NUM_DOC_TRABAJADOR, registro.getNum_doc_trabajador());
        values.put(Util_EstresTermicoRegistro.CAMPO_NOM_TRABAJADOR, registro.getNom_trabajador());
        values.put(Util_EstresTermicoRegistro.CAMPO_PUESTO_TRABAJADOR, registro.getPuesto_trabajador());
        values.put(Util_EstresTermicoRegistro.CAMPO_AREA_TRABAJO, registro.getArea_trabajo());
        values.put(Util_EstresTermicoRegistro.CAMPO_ACTIVIDADES_REALIZADAS, registro.getActividades_realizadas());
        values.put(Util_EstresTermicoRegistro.CAMPO_PESO_TRABAJADOR, registro.getPeso_trabajador());
        values.put(Util_EstresTermicoRegistro.CAMPO_EDAD_TRABAJADOR, registro.getEdad_trabajador());
        values.put(Util_EstresTermicoRegistro.CAMPO_HORA_TRABAJO, registro.getHora_trabajo());
        values.put(Util_EstresTermicoRegistro.CAMPO_HORARIO_REFRIGERIO, registro.getHorario_refrigerio());
        values.put(Util_EstresTermicoRegistro.CAMPO_REGIMEN_LABORAL, registro.getRegimen_laboral());
        values.put(Util_EstresTermicoRegistro.CAMPO_DESC_AREA_TRABAJO, registro.getDesc_area_trabajo());
        values.put(Util_EstresTermicoRegistro.CAMPO_AREA_TRAB_DETA, registro.getArea_trab_deta());
        values.put(Util_EstresTermicoRegistro.CAMPO_CTRL_INGENIERIA, registro.getCtrl_ingenieria());
        values.put(Util_EstresTermicoRegistro.CAMPO_NOM_CTRL_INGENIERIA, registro.getNom_ctrl_ingenieria());
        values.put(Util_EstresTermicoRegistro.CAMPO_CTRL_ADMINISTRATIVO, registro.getCtrl_administrativo());
        values.put(Util_EstresTermicoRegistro.CAMPO_ANIO_OCU_CARGO, registro.getAnio_ocu_cargo());
        values.put(Util_EstresTermicoRegistro.CAMPO_MES_OCU_CARGO, registro.getMes_ocu_cargo());
        values.put(Util_EstresTermicoRegistro.CAMPO_COND_TRAB, registro.getCond_trab());
        values.put(Util_EstresTermicoRegistro.CAMPO_OBSERVACION, registro.getObservacion());
        values.put(Util_EstresTermicoRegistro.CAMPO_ESTADO, registro.getEstado());
        values.put(Util_EstresTermicoRegistro.CAMPO_FEC_REG, registro.getFec_reg());
        values.put(Util_EstresTermicoRegistro.CAMPO_USER_REG, registro.getUser_reg());*/

        db.insert(Util_RegistroFormatos.TABLA_REGISTRO_FORMATOS,null,values);

        return true;
    }

    public boolean RegistrarEstresTermicoDetalle(EstresTermico_RegistroDetalle registro){
        SQLiteDatabase db = datHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        //values.put(Util_RegistroFormatos_Detalle.CAMPO_ID_FORMATO_REG_DETALLE,-1); //--------------------> PRUEBA PARA EL REGISTRO EN LA TABLA GENERAL
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ID_PLAN_TRABAJO_FORMATO_REG, registro.getId_plan_trabajo_formato_reg());//----------------> PRUEBA PARA EL REGISTRO EN LA TABLA GENERAL
        values.put(Util_RegistroFormatos_Detalle.CAMPO_FUENTE_GENERADORA, registro.getFuente_generadora());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_DESC_FUENTE_FRIO, registro.getDesc_fuente_frio());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ZONA_SOMBRA, registro.getZona_sombra());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ROTACION_PERSONAL, registro.getRotacion_personal());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TIEMPO_RECUPERACION, registro.getTiempo_recuperacion());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_DISPENSADOR, registro.getDispensador());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CAPA_EXPO_FRIO, registro.getCapa_expo_frio());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CAT_TRABAJO, registro.getCat_trabajo());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PORC_DESCA, registro.getPorc_desca());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_VESTIMENTA_PERSONAL, registro.getVestimenta_personal());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_MATERIAL_PRENDA, registro.getMaterial_prenda());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_COLOR_PREDOMINANTE, registro.getColor_predominante());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_ZS, registro.getEpp_zs());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_CASCO, registro.getEpp_casco());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_LENTES, registro.getEpp_lentes());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_GUANTES, registro.getEpp_guantes());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_OREJERAS, registro.getEpp_orejeras());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_TAPONES, registro.getEpp_tapones());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_CNUCA, registro.getEpp_cnuca());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_OTRO_EPP, registro.getOtro_epp());

        values.put(Util_RegistroFormatos_Detalle.CAMPO_NOM_NIVEL_D, registro.getNom_nivel_d());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_METODO_DETERM, registro.getMetodo_determ());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TIPO_TRABAJO, registro.getTipo_trabajo());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_OCUPACION, registro.getOcupacion());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_RANGO_TASA_METAB, registro.getRango_tasa_metab());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CLASE, registro.getClase());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ACTIVIDAD_DETER, registro.getActividad_deter());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TASA_METAB, registro.getTasa_metab());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TASA_METAB_KCAL, registro.getTasa_metab_kcal());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_FRECUENCIA_DETER, registro.getFrecuencia_deter());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_GENERO_DETER, registro.getGenero_deter());

        values.put(Util_RegistroFormatos_Detalle.CAMPO_N_TAREAS, registro.getNtareas());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_NOM_TAREA1, registro.getNom_tarea1());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CICLO_TRABAJO1, registro.getCiclo_trabajo1());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_POSICION_1, registro.getPosicion_1());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PCUERPO_1, registro.getPcuerpo_1());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_INTENSIDAD_1, registro.getIntensidad_1());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_NOM_TAREA2, registro.getNom_tarea2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CICLO_TRABAJO2, registro.getCiclo_trabajo2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_POSICION_2, registro.getPosicion_2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PCUERPO_2, registro.getPcuerpo_2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_INTENSIDAD_2, registro.getIntensidad_2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_NOM_TAREA3, registro.getNom_tarea3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CICLO_TRABAJO3, registro.getCiclo_trabajo3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_POSICION_3, registro.getPosicion_3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PCUERPO_3, registro.getPcuerpo_3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_INTENSIDAD_3, registro.getIntensidad_3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_NOM_TAREA4, registro.getNom_tarea4());//agregado recientemente
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CICLO_TRABAJO4, registro.getCiclo_trabajo4());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_POSICION_4, registro.getPosicion_4());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PCUERPO_4, registro.getPcuerpo_4());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_INTENSIDAD_4, registro.getIntensidad_4());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_NOM_TAREA5, registro.getNom_tarea5());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CICLO_TRABAJO5, registro.getCiclo_trabajo5());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_POSICION_5, registro.getPosicion_5());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PCUERPO_5, registro.getPcuerpo_5());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_INTENSIDAD_5, registro.getIntensidad_5());//agregado recientemente
        values.put(Util_RegistroFormatos_Detalle.CAMPO_MTR_SUBIDA, registro.getMtr_subida());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_T_BULBO, registro.getT_bulbo());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_T_BULBO2, registro.getT_bulbo2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_T_BULBO3, registro.getT_bulbo3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_T_AIRE, registro.getT_aire());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_T_AIRE_2, registro.getT_aire_2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_T_AIRE_3, registro.getT_aire_3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_T_GLOBO, registro.getT_globo());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_T_GLOBO_2, registro.getT_globo_2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_T_GLOBO_3, registro.getT_globo_3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_H_RELATIVA, registro.getH_relativa());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_H_RELATIVA_2, registro.getH_relativa_2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_H_RELATIVA_3, registro.getH_relativa_3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_V_VIENTO, registro.getV_viento());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_V_VIENTO_2, registro.getV_viento_2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_V_VIENTO_3, registro.getV_viento_3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ESTADO, registro.getEstado());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_FEC_REG, registro.getFec_reg());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_USER_REG, registro.getUser_reg());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ESTADO_SINCRO,1);// MARCANDO PARA SABER QUE FALTA SINCRONIZAR

        //values.put(Util_EstresTermicoDetalle.CAMPO_ID_PLAN_TRABAJO_FORMATO_REG, registro.getIdEstrésReg());
        /*values.put(Util_EstresTermicoRegistroDetalle.CAMPO_FUENTE_GENERADORA, registro.getFuente_generadora());
        values.put(Util_EstresTermicoRegistroDetalle.CAMPO_DESC_FUENTE_FRIO, registro.getDesc_fuente_frio());
        values.put(Util_EstresTermicoRegistroDetalle.CAMPO_ZONA_SOMBRA, registro.getZona_sombra());
        values.put(Util_EstresTermicoRegistroDetalle.CAMPO_ROTACION_PERSONAL, registro.getRotacion_personal());
        values.put(Util_EstresTermicoRegistroDetalle.CAMPO_TIEMPO_RECUPERACION, registro.getTiempo_recuperacion());
        values.put(Util_EstresTermicoRegistroDetalle.CAMPO_DISPENSADOR, registro.getDispensador());
        values.put(Util_EstresTermicoRegistroDetalle.CAMPO_CAPA_EXPO_FRIO, registro.getCapa_expo_frio());
        values.put(Util_EstresTermicoRegistroDetalle.CAMPO_CAT_TRABAJO, registro.getCat_trabajo());
        values.put(Util_EstresTermicoRegistroDetalle.CAMPO_PORC_DESCA, registro.getPorc_desca());
        values.put(Util_EstresTermicoRegistroDetalle.CAMPO_VESTIMENTA_PERSONAL, registro.getVestimenta_personal());
        values.put(Util_EstresTermicoRegistroDetalle.CAMPO_MATERIAL_PRENDA, registro.getMaterial_prenda());
        values.put(Util_EstresTermicoRegistroDetalle.CAMPO_COLOR_PREDOMINANTE, registro.getColor_predominante());
        values.put(Util_EstresTermicoRegistroDetalle.CAMPO_EPP_ZS, registro.getEpp_zs());
        values.put(Util_EstresTermicoRegistroDetalle.CAMPO_EPP_CASCO, registro.getEpp_casco());
        values.put(Util_EstresTermicoRegistroDetalle.CAMPO_EPP_LENTES, registro.getEpp_lentes());
        values.put(Util_EstresTermicoRegistroDetalle.CAMPO_EPP_GUANTES, registro.getEpp_guantes());
        values.put(Util_EstresTermicoRegistroDetalle.CAMPO_EPP_OREJERAS, registro.getEpp_orejeras());
        values.put(Util_EstresTermicoRegistroDetalle.CAMPO_EPP_TAPONES, registro.getEpp_tapones());
        values.put(Util_EstresTermicoRegistroDetalle.CAMPO_EPP_CNUCA, registro.getEpp_cnuca());
        values.put(Util_EstresTermicoRegistroDetalle.CAMPO_OTRO_EPP, registro.getOtro_epp());
        values.put(Util_EstresTermicoRegistroDetalle.CAMPO_NOM_TAREA1, registro.getNom_tarea1());
        values.put(Util_EstresTermicoRegistroDetalle.CAMPO_CICLO_TRABAJO1, registro.getCiclo_trabajo1());
        values.put(Util_EstresTermicoRegistroDetalle.CAMPO_POSICION_1, registro.getPosicion_1());
        values.put(Util_EstresTermicoRegistroDetalle.CAMPO_PCUERPO_1, registro.getPcuerpo_1());
        values.put(Util_EstresTermicoRegistroDetalle.CAMPO_INTENSIDAD_1, registro.getIntensidad_1());
        values.put(Util_EstresTermicoRegistroDetalle.CAMPO_NOM_TAREA2, registro.getNom_tarea2());
        values.put(Util_EstresTermicoRegistroDetalle.CAMPO_CICLO_TRABAJO2, registro.getCiclo_trabajo2());
        values.put(Util_EstresTermicoRegistroDetalle.CAMPO_POSICION_2, registro.getPosicion_2());
        values.put(Util_EstresTermicoRegistroDetalle.CAMPO_PCUERPO_2, registro.getPcuerpo_2());
        values.put(Util_EstresTermicoRegistroDetalle.CAMPO_INTENSIDAD_2, registro.getIntensidad_2());
        values.put(Util_EstresTermicoRegistroDetalle.CAMPO_NOM_TAREA3, registro.getNom_tarea3());
        values.put(Util_EstresTermicoRegistroDetalle.CAMPO_CICLO_TRABAJO3, registro.getCiclo_trabajo3());
        values.put(Util_EstresTermicoRegistroDetalle.CAMPO_POSICION_3, registro.getPosicion_3());
        values.put(Util_EstresTermicoRegistroDetalle.CAMPO_PCUERPO_3, registro.getPcuerpo_3());
        values.put(Util_EstresTermicoRegistroDetalle.CAMPO_INTENSIDAD_3, registro.getIntensidad_3());
        values.put(Util_EstresTermicoRegistroDetalle.CAMPO_MTR_SUBIDA, registro.getMtr_subida());
        values.put(Util_EstresTermicoRegistroDetalle.CAMPO_WBGT, registro.getWbgt());
        values.put(Util_EstresTermicoRegistroDetalle.CAMPO_WBGT_2, registro.getWbgt_2());
        values.put(Util_EstresTermicoRegistroDetalle.CAMPO_WBGT_3, registro.getWbgt_3());
        values.put(Util_EstresTermicoRegistroDetalle.CAMPO_T_AIRE, registro.getT_aire());
        values.put(Util_EstresTermicoRegistroDetalle.CAMPO_T_AIRE_2, registro.getT_aire_2());
        values.put(Util_EstresTermicoRegistroDetalle.CAMPO_T_AIRE_3, registro.getT_aire_3());
        values.put(Util_EstresTermicoRegistroDetalle.CAMPO_T_GLOBO, registro.getT_globo());
        values.put(Util_EstresTermicoRegistroDetalle.CAMPO_T_GLOBO_2, registro.getT_globo_2());
        values.put(Util_EstresTermicoRegistroDetalle.CAMPO_T_GLOBO_3, registro.getT_globo_3());
        values.put(Util_EstresTermicoRegistroDetalle.CAMPO_H_RELATIVA, registro.getH_relativa());
        values.put(Util_EstresTermicoRegistroDetalle.CAMPO_H_RELATIVA_2, registro.getH_relativa_2());
        values.put(Util_EstresTermicoRegistroDetalle.CAMPO_H_RELATIVA_3, registro.getH_relativa_3());
        values.put(Util_EstresTermicoRegistroDetalle.CAMPO_V_VIENTO, registro.getV_viento());
        values.put(Util_EstresTermicoRegistroDetalle.CAMPO_ESTADO_DETALLE, registro.getEstado());
        values.put(Util_EstresTermicoRegistroDetalle.CAMPO_FEC_REG_DETALLE, registro.getFec_reg());
        values.put(Util_EstresTermicoRegistroDetalle.CAMPO_USER_REG_DETALLE, registro.getUser_reg());*/


        db.insert(Util_RegistroFormatos_Detalle.TABLA_REGISTRO_DETALLE,null,values);

        return true;
    }

    public boolean ActualizarEstresTermico(EstresTermico_Registro registro, int id_plan_trabajo_formato_reg){
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
        values.put(Util_RegistroFormatos.CAMPO_DESC_AREA_TRABAJO, registro.getDesc_area_trabajo());
        values.put(Util_RegistroFormatos.CAMPO_AREA_TRAB_DETA, registro.getArea_trab_deta());
        values.put(Util_RegistroFormatos.CAMPO_CTRL_INGENIERIA, registro.getCtrl_ingenieria());
        values.put(Util_RegistroFormatos.CAMPO_NOM_CTRL_INGENIERIA, registro.getNom_ctrl_ingenieria());
        values.put(Util_RegistroFormatos.CAMPO_CTRL_ADMINISTRATIVO, registro.getCtrl_administrativo());
        values.put(Util_RegistroFormatos.CAMPO_ANIO_OCU_CARGO, registro.getAnio_ocu_cargo());
        values.put(Util_RegistroFormatos.CAMPO_MES_OCU_CARGO, registro.getMes_ocu_cargo());
        values.put(Util_RegistroFormatos.CAMPO_COND_TRAB, registro.getCond_trab());
        values.put(Util_RegistroFormatos.CAMPO_OBSERVACION, registro.getObservacion());
        values.put(Util_RegistroFormatos.CAMPO_NOM_TIPO_MEDICION, registro.getNom_tipo_medicion());
        values.put(Util_RegistroFormatos.CAMPO_RUTA_FOTO, registro.getRuta_foto());
        values.put(Util_RegistroFormatos.CAMPO_ESTADO, registro.getEstado());
        values.put(Util_RegistroFormatos.CAMPO_FEC_REG, registro.getFec_reg());
        values.put(Util_RegistroFormatos.CAMPO_USER_REG, registro.getUser_reg());
        values.put(Util_RegistroFormatos.CAMPO_FEC_ACT, fecha);
        values.put(Util_RegistroFormatos.CAMPO_USER_ACT, registro.getUser_reg());
        values.put(Util_RegistroFormatos.CAMPO_ESTADO_SINCRO,1);// MARCANDO PARA SABER QUE FALTA SINCRONIZAR

        String whereClause = Util_RegistroFormatos.CAMPO_ID_PLAN_TRABAJO_FORMATO_REG + " = ?";
        String[] whereArgs = {String.valueOf(id_plan_trabajo_formato_reg)};
        db.update(Util_RegistroFormatos.TABLA_REGISTRO_FORMATOS, values, whereClause, whereArgs);

        return true;
    }

    public boolean ActualizarEstresTermicoDetalle(EstresTermico_RegistroDetalle registro, int id_formato_reg_detalle){
        SQLiteDatabase db = datHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fecha = dateFormat.format(new Date());

        values.put(Util_RegistroFormatos_Detalle.CAMPO_ID_PLAN_TRABAJO_FORMATO_REG, registro.getId_plan_trabajo_formato_reg());//----------------> PRUEBA PARA EL REGISTRO EN LA TABLA GENERAL
        values.put(Util_RegistroFormatos_Detalle.CAMPO_FUENTE_GENERADORA, registro.getFuente_generadora());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_DESC_FUENTE_FRIO, registro.getDesc_fuente_frio());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ZONA_SOMBRA, registro.getZona_sombra());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ROTACION_PERSONAL, registro.getRotacion_personal());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TIEMPO_RECUPERACION, registro.getTiempo_recuperacion());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_DISPENSADOR, registro.getDispensador());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CAPA_EXPO_FRIO, registro.getCapa_expo_frio());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CAT_TRABAJO, registro.getCat_trabajo());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PORC_DESCA, registro.getPorc_desca());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_VESTIMENTA_PERSONAL, registro.getVestimenta_personal());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_MATERIAL_PRENDA, registro.getMaterial_prenda());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_COLOR_PREDOMINANTE, registro.getColor_predominante());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_ZS, registro.getEpp_zs());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_CASCO, registro.getEpp_casco());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_LENTES, registro.getEpp_lentes());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_GUANTES, registro.getEpp_guantes());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_OREJERAS, registro.getEpp_orejeras());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_TAPONES, registro.getEpp_tapones());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_CNUCA, registro.getEpp_cnuca());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_OTRO_EPP, registro.getOtro_epp());

        values.put(Util_RegistroFormatos_Detalle.CAMPO_NOM_NIVEL_D, registro.getNom_nivel_d());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ID_METODO_DETERM, registro.getId_metodo_determ());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_METODO_DETERM, registro.getMetodo_determ());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TIPO_TRABAJO, registro.getTipo_trabajo());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_OCUPACION, registro.getOcupacion());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_RANGO_TASA_METAB, registro.getRango_tasa_metab());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CLASE, registro.getClase());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ACTIVIDAD_DETER, registro.getActividad_deter());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TASA_METAB, registro.getTasa_metab());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TASA_METAB_KCAL, registro.getTasa_metab_kcal());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_FRECUENCIA_DETER, registro.getFrecuencia_deter());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_GENERO_DETER, registro.getGenero_deter());

        values.put(Util_RegistroFormatos_Detalle.CAMPO_N_TAREAS, registro.getNtareas());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_NOM_TAREA1, registro.getNom_tarea1());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CICLO_TRABAJO1, registro.getCiclo_trabajo1());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_POSICION_1, registro.getPosicion_1());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PCUERPO_1, registro.getPcuerpo_1());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_INTENSIDAD_1, registro.getIntensidad_1());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_NOM_TAREA2, registro.getNom_tarea2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CICLO_TRABAJO2, registro.getCiclo_trabajo2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_POSICION_2, registro.getPosicion_2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PCUERPO_2, registro.getPcuerpo_2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_INTENSIDAD_2, registro.getIntensidad_2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_NOM_TAREA3, registro.getNom_tarea3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CICLO_TRABAJO3, registro.getCiclo_trabajo3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_POSICION_3, registro.getPosicion_3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PCUERPO_3, registro.getPcuerpo_3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_INTENSIDAD_3, registro.getIntensidad_3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_NOM_TAREA4, registro.getNom_tarea4());//agregado recientemente
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CICLO_TRABAJO4, registro.getCiclo_trabajo4());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_POSICION_4, registro.getPosicion_4());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PCUERPO_4, registro.getPcuerpo_4());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_INTENSIDAD_4, registro.getIntensidad_4());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_NOM_TAREA5, registro.getNom_tarea5());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CICLO_TRABAJO5, registro.getCiclo_trabajo5());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_POSICION_5, registro.getPosicion_5());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PCUERPO_5, registro.getPcuerpo_5());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_INTENSIDAD_5, registro.getIntensidad_5());//agregado recientemente
        values.put(Util_RegistroFormatos_Detalle.CAMPO_MTR_SUBIDA, registro.getMtr_subida());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_T_BULBO, registro.getT_bulbo());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_T_BULBO2, registro.getT_bulbo2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_T_BULBO3, registro.getT_bulbo3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_T_AIRE, registro.getT_aire());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_T_AIRE_2, registro.getT_aire_2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_T_AIRE_3, registro.getT_aire_3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_T_GLOBO, registro.getT_globo());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_T_GLOBO_2, registro.getT_globo_2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_T_GLOBO_3, registro.getT_globo_3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_H_RELATIVA, registro.getH_relativa());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_H_RELATIVA_2, registro.getH_relativa_2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_H_RELATIVA_3, registro.getH_relativa_3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_V_VIENTO, registro.getV_viento());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_V_VIENTO_2, registro.getV_viento_2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_V_VIENTO_3, registro.getV_viento_3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ESTADO, registro.getEstado());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_FEC_REG, registro.getFec_reg());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_USER_REG, registro.getUser_reg());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_FEC_ACT, fecha);
        values.put(Util_RegistroFormatos_Detalle.CAMPO_USER_ACT, registro.getUser_reg());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ESTADO_SINCRO,1);// MARCANDO PARA SABER QUE FALTA SINCRONIZAR

        String whereClause = Util_RegistroFormatos_Detalle.CAMPO_ID_FORMATO_REG_DETALLE + " = ?";
        String[] whereArgs = {String.valueOf(id_formato_reg_detalle)};
        db.update(Util_RegistroFormatos_Detalle.TABLA_REGISTRO_DETALLE, values, whereClause, whereArgs);

        return true;
    }

}
