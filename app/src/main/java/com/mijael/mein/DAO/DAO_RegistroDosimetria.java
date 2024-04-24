package com.mijael.mein.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.mijael.mein.Entidades.Dosimetria_Registro;
import com.mijael.mein.HELPER.MeinSQLiteHelper;
import com.mijael.mein.Utilidades.Util_RegistroFormatos;
import com.mijael.mein.Utilidades.Util_RegistroFormatos_Detalle;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DAO_RegistroDosimetria {
    private MeinSQLiteHelper dbHelper;

    public DAO_RegistroDosimetria(Context context) {
        dbHelper = MeinSQLiteHelper.getInstance(context);
    }
    public boolean RegistrarFormato(Dosimetria_Registro registros){//METODO QUE REGISTRA EN SQLITE TODOS LOS DATOS DEL FORMATO DE DOSIMETRIA
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Util_RegistroFormatos.CAMPO_COD_FORMATO, registros.getCod_formato());
        values.put(Util_RegistroFormatos.CAMPO_COD_REGISTRO, registros.getCod_registro());
        values.put(Util_RegistroFormatos.CAMPO_ID_FORMATO, registros.getId_formato());
        values.put(Util_RegistroFormatos.CAMPO_ID_PLAN_TRABAJO, registros.getId_plan_trabajo());
        values.put(Util_RegistroFormatos.CAMPO_ID_PT_FORMATO, registros.getId_pt_formato());
        values.put(Util_RegistroFormatos.CAMPO_COD_EQUIPO1, registros.getCod_equipo1());
        values.put(Util_RegistroFormatos.CAMPO_NOM_EQUIPO1,registros.getNom_equipo1());
        values.put(Util_RegistroFormatos.CAMPO_COD_EQUIPO2,registros.getCod_equipo2());
        values.put(Util_RegistroFormatos.CAMPO_NOM_EQUIPO2,registros.getNom_equipo2());
        values.put(Util_RegistroFormatos.CAMPO_SERIE_EQ1,registros.getSerie_eq1());
        values.put(Util_RegistroFormatos.CAMPO_SERIE_EQ2,registros.getSerie_eq2());
        values.put(Util_RegistroFormatos.CAMPO_ID_EQUIPO1,registros.getId_equipo1());
        values.put(Util_RegistroFormatos.CAMPO_ID_EQUIPO2,registros.getId_equipo2());
        values.put(Util_RegistroFormatos.CAMPO_ID_ANALISTA,registros.getId_analista());
        values.put(Util_RegistroFormatos.CAMPO_NOM_ANALISTA,registros.getNom_analista());
        values.put(Util_RegistroFormatos.CAMPO_HORA_SITU,registros.getHora_situ());
        values.put(Util_RegistroFormatos.CAMPO_NIVEL,registros.getNivel());
        values.put(Util_RegistroFormatos.CAMPO_VARIACION,registros.getVariacion());
        values.put(Util_RegistroFormatos.CAMPO_FEC_MONITOREO, registros.getFec_monitoreo());
        values.put(Util_RegistroFormatos.CAMPO_HORA_INICIAL,registros.getHora_inicial());
        values.put(Util_RegistroFormatos.CAMPO_HORA_FINAL,registros.getHora_final());
        values.put(Util_RegistroFormatos.CAMPO_TIEMPO_EXPOSICION,registros.getTiempo_exposicion());
        values.put(Util_RegistroFormatos.CAMPO_JORNADA,registros.getJornada());
        values.put(Util_RegistroFormatos.CAMPO_TIPO_DOC_TRABAJADOR,registros.getTipo_doc_trabajador());
        values.put(Util_RegistroFormatos.CAMPO_NUM_DOC_TRABAJADOR,registros.getNum_doc_trabajador());
        values.put(Util_RegistroFormatos.CAMPO_NOM_TRABAJADOR,registros.getNom_trabajador());
        values.put(Util_RegistroFormatos.CAMPO_PUESTO_TRABAJADOR,registros.getPuesto_trabajador());
        values.put(Util_RegistroFormatos.CAMPO_AREA_TRABAJO,registros.getArea_trabajo());
        values.put(Util_RegistroFormatos.CAMPO_ACTIVIDADES_REALIZADAS,registros.getActividades_realizadas());
        values.put(Util_RegistroFormatos.CAMPO_EDAD_TRABAJADOR,registros.getEdad_trabajador());
        values.put(Util_RegistroFormatos.CAMPO_CH_RUIDO_EXTERNO,registros.getCh_ruido_externo());
        values.put(Util_RegistroFormatos.CAMPO_CH_RUIDO_ANTIGUO,registros.getCh_ruido_antiguo());
        values.put(Util_RegistroFormatos.CAMPO_CH_RUIDO_GENERADO_POR,registros.getCh_ruido_generado_por());
        values.put(Util_RegistroFormatos.CAMPO_RUIDO_GENERADO_POR,registros.getRuido_generado_por());
        values.put(Util_RegistroFormatos.CAMPO_OTRO_RUIDO,registros.getOtro_ruido());
        //values.put(Util_DosimetriaRegistro.CAMPO_OT);
        values.put(Util_RegistroFormatos.CAMPO_HORA_TRABAJO,registros.getHora_trabajo());
        values.put(Util_RegistroFormatos.CAMPO_REGIMEN_LABORAL,registros.getRegimen_laboral());
        values.put(Util_RegistroFormatos.CAMPO_HORARIO_REFRIGERIO,registros.getHorario_refrigerio());
        values.put(Util_RegistroFormatos.CAMPO_ANIO_OCU_CARGO,registros.getAnio_ocu_cargo());
        values.put(Util_RegistroFormatos.CAMPO_MES_OCU_CARGO,registros.getMes_ocu_cargo());
        values.put(Util_RegistroFormatos.CAMPO_MOLESTIA_OIDO,registros.getMolestia_oido());
        values.put(Util_RegistroFormatos.CAMPO_ENFERMEDAD_OIDO,registros.getEnfermedad_oido());
        values.put(Util_RegistroFormatos.CAMPO_DETALLE_ENF_OIDO,registros.getDetalle_enf_oido());
        values.put(Util_RegistroFormatos.CAMPO_FECHA_ULTIMO_EXAMEN,registros.getFecha_ultimo_examen());
        values.put(Util_RegistroFormatos.CAMPO_MES_ULTIMO_EXAMEN,registros.getMes_ultimo_examen());
        values.put(Util_RegistroFormatos.CAMPO_ANIO_ULTIMO_EXAMEN,registros.getAnio_ultimo_examen());
        values.put(Util_RegistroFormatos.CAMPO_CTRL_INGENIERIA, registros.getCtrl_ingenieria());
        values.put(Util_RegistroFormatos.CAMPO_AISLAMIENTO,registros.getAislamiento());
        values.put(Util_RegistroFormatos.CAMPO_TECHOS,registros.getTechos());
        values.put(Util_RegistroFormatos.CAMPO_CABINAS,registros.getCabinas());
        values.put(Util_RegistroFormatos.CAMPO_ORIENTACION,registros.getOrientacion());
        values.put(Util_RegistroFormatos.CAMPO_CERRAMIENTO,registros.getCerramiento());
        values.put(Util_RegistroFormatos.CAMPO_ANIO_ULTIMO_EXAMEN,registros.getAnio_ultimo_examen());
        values.put(Util_RegistroFormatos.CAMPO_OTRO_INGENIERIA,registros.getOtro_ingenieria());
        values.put(Util_RegistroFormatos.CAMPO_CTRL_ADMINISTRATIVO,registros.getCtrl_administrativo());
        values.put(Util_RegistroFormatos.CAMPO_CAPACITACION,registros.getCapacitacion());
        values.put(Util_RegistroFormatos.CAMPO_SENIALIZACION_PRECION,registros.getSenializacion_precion());
        values.put(Util_RegistroFormatos.CAMPO_SENIALIZACION_EPP,registros.getSenializacion_epp());
        values.put(Util_RegistroFormatos.CAMPO_ROTACION,registros.getRotacion());
        values.put(Util_RegistroFormatos.CAMPO_ADM_TIEMPO_EXPO,registros.getTiempo_exposicion());
        values.put(Util_RegistroFormatos.CAMPO_OTRO_ADMINISTRATIVO,registros.getOtro_administrativo());
        values.put(Util_RegistroFormatos.CAMPO_TAPONES_AU,registros.getTapones_au());
        values.put(Util_RegistroFormatos.CAMPO_MARCA_TAPONES_AUDI,registros.getMarca_tapones_audi());
        values.put(Util_RegistroFormatos.CAMPO_MODELO_TAPONES_AUDI,registros.getModelo_tapones_audi());
        values.put(Util_RegistroFormatos.CAMPO_NRR_TAPONES_AUDI,registros.getNrr_tapones_audi());
        values.put(Util_RegistroFormatos.CAMPO_OREJERAS,registros.getTapones_au());
        values.put(Util_RegistroFormatos.CAMPO_MARCA_OREJERAS,registros.getMarca_orejeras());
        values.put(Util_RegistroFormatos.CAMPO_MODELO_OREJERAS,registros.getModelo_orejeras());
        values.put(Util_RegistroFormatos.CAMPO_NRR_OREJERAS,registros.getNrr_orejeras());
        values.put(Util_RegistroFormatos.CAMPO_LEQUI,registros.getLeq_dba());
        values.put(Util_RegistroFormatos.CAMPO_LPICO_DBA,registros.getLpico_dba());
        values.put(Util_RegistroFormatos.CAMPO_LMAX,registros.getLmax_dba());
        values.put(Util_RegistroFormatos.CAMPO_LMIN,registros.getLmin_dba());
        values.put(Util_RegistroFormatos.CAMPO_OBSERVACION,registros.getObservacion());
        values.put(Util_RegistroFormatos.CAMPO_ESTADO_RESULTADO, registros.getEstado_resultado());
        values.put(Util_RegistroFormatos.CAMPO_RUTA_FOTO, registros.getRuta_foto());
        values.put(Util_RegistroFormatos.CAMPO_ESTADO,registros.getEstado());
        values.put(Util_RegistroFormatos.CAMPO_USER_REG,registros.getNom_analista());
        values.put(Util_RegistroFormatos.CAMPO_FEC_REG,registros.getFec_reg());
        values.put(Util_RegistroFormatos.CAMPO_ESTADO_SINCRO,1);// MARCANDO PARA SABER QUE FALTA SINCRONIZAR


        /*values.put(Util_DosimetriaRegistro.CAMPO_COD_FORMATO, registros.getCod_formato());
        values.put(Util_DosimetriaRegistro.CAMPO_ID_FORMATO, registros.getId_formato());
        values.put(Util_DosimetriaRegistro.CAMPO_ID_PLAN_TRABAJO, registros.getId_plan_trabajo());
        values.put(Util_DosimetriaRegistro.CAMPO_ID_PT_FORMATO, registros.getId_pt_formato());
        values.put(Util_DosimetriaRegistro.CAMPO_COD_DOSIMETRO, registros.getCod_dosimetro());
        values.put(Util_DosimetriaRegistro.CAMPO_NOM_DOSIMETRO,registros.getNom_dosimetro());
        values.put(Util_DosimetriaRegistro.CAMPO_COD_CALIBRADOR,registros.getCod_calibrador());
        values.put(Util_DosimetriaRegistro.CAMPO_NOM_CALIBRADOR,registros.getNom_calibrador());
        values.put(Util_DosimetriaRegistro.CAMPO_SERIE_EQ1,registros.getSerie_eq1());
        values.put(Util_DosimetriaRegistro.CAMPO_SERIE_EQ2,registros.getSerie_eq2());
        values.put(Util_DosimetriaRegistro.CAMPO_ID_DOSIMETRO,registros.getId_dosimetro());
        values.put(Util_DosimetriaRegistro.CAMPO_ID_CALIBRADOR,registros.getId_calibrador());
        values.put(Util_DosimetriaRegistro.CAMPO_ID_ANALISTA,registros.getId_analista());
        values.put(Util_DosimetriaRegistro.CAMPO_NOM_ANALISTA,registros.getNom_analista());
        values.put(Util_DosimetriaRegistro.CAMPO_HORA_SITU,registros.getHora_situ());
        values.put(Util_DosimetriaRegistro.CAMPO_NIVEL,registros.getNivel());
        values.put(Util_DosimetriaRegistro.CAMPO_VARIACION,registros.getVariacion());
        values.put(Util_DosimetriaRegistro.CAMPO_FEC_MONITOREO,registros.getFec_monitoreo());
        values.put(Util_DosimetriaRegistro.CAMPO_HORA_INICIAL,registros.getHora_inicial());
        values.put(Util_DosimetriaRegistro.CAMPO_HORA_FINAL,registros.getHora_final());
        values.put(Util_DosimetriaRegistro.CAMPO_TIEMPO_EXPOSICION,registros.getTiempo_exposicion());
        values.put(Util_DosimetriaRegistro.CAMPO_JORNADA,registros.getJornada());
        values.put(Util_DosimetriaRegistro.CAMPO_TIPO_DOC_TRABAJADOR,registros.getTipo_doc_trabajador());
        values.put(Util_DosimetriaRegistro.CAMPO_NUM_DOC_TRABAJADOR,registros.getNum_doc_trabajador());
        values.put(Util_DosimetriaRegistro.CAMPO_NOM_TRABAJADOR,registros.getNom_trabajador());
        values.put(Util_DosimetriaRegistro.CAMPO_PUESTO_TRABAJADOR,registros.getPuesto_trabajador());
        values.put(Util_DosimetriaRegistro.CAMPO_AREA_TRABAJO,registros.getArea_trabajo());
        values.put(Util_DosimetriaRegistro.CAMPO_ACTIVIDADES_REALIZADAS,registros.getActividades_realizadas());
        values.put(Util_DosimetriaRegistro.CAMPO_EDAD_TRABAJADOR,registros.getEdad_trabajador());
        values.put(Util_DosimetriaRegistro.CAMPO_CH_RUIDO_EXTERNO,registros.getCh_ruido_externo());
        values.put(Util_DosimetriaRegistro.CAMPO_CH_RUIDO_ANTIGUO,registros.getCh_ruido_antiguo());
        values.put(Util_DosimetriaRegistro.CAMPO_CH_RUIDO_GENERADO_POR,registros.getCh_ruido_generado_por());
        values.put(Util_DosimetriaRegistro.CAMPO_RUIDO_GENERADO_POR,registros.getRuido_generado_por());
        values.put(Util_DosimetriaRegistro.CAMPO_OTRO_RUIDO,registros.getOtro_ruido());
        //values.put(Util_DosimetriaRegistro.CAMPO_OT);
        values.put(Util_DosimetriaRegistro.CAMPO_HORA_TRABAJO,registros.getHora_trabajo());
        values.put(Util_DosimetriaRegistro.CAMPO_REGIMEN_LABORAL,registros.getRegimen_laboral());
        values.put(Util_DosimetriaRegistro.CAMPO_HORARIO_REFRIGERIO,registros.getHorario_refrigerio());
        values.put(Util_DosimetriaRegistro.CAMPO_TIEMPO_OCUPADO,registros.getTiempo_ocupado());
        values.put(Util_DosimetriaRegistro.CAMPO_MOLESTIA_OIDO,registros.getMolestia_oido());
        values.put(Util_DosimetriaRegistro.CAMPO_ENFERMEDAD_OIDO,registros.getEnfermedad_oido());
        values.put(Util_DosimetriaRegistro.CAMPO_DETALLE_ENF_OIDO,registros.getDetalle_enf_oido());
        values.put(Util_DosimetriaRegistro.CAMPO_FECHA_ULTIMO_EXAMEN,registros.getFecha_ultimo_examen());
        values.put(Util_DosimetriaRegistro.CAMPO_MES_ULTIMO_EXAMEN,registros.getMes_ultimo_examen());
        values.put(Util_DosimetriaRegistro.CAMPO_ANIO_ULTIMO_EXAMEN,registros.getAnio_ultimo_examen());
        values.put(Util_DosimetriaRegistro.CAMPO_CTRL_INGENIERIA, registros.getCtrl_ingenieria());
        values.put(Util_DosimetriaRegistro.CAMPO_AISLAMIENTO,registros.getAislamiento());
        values.put(Util_DosimetriaRegistro.CAMPO_TECHOS,registros.getTechos());
        values.put(Util_DosimetriaRegistro.CAMPO_CABINAS,registros.getCabinas());
        values.put(Util_DosimetriaRegistro.CAMPO_ORIENTACION,registros.getOrientacion());
        values.put(Util_DosimetriaRegistro.CAMPO_CERRAMIENTO,registros.getCerramiento());
        values.put(Util_DosimetriaRegistro.CAMPO_ANIO_ULTIMO_EXAMEN,registros.getAnio_ultimo_examen());
        values.put(Util_DosimetriaRegistro.CAMPO_OTRO_INGENIERIA,registros.getOtro_ingenieria());
        values.put(Util_DosimetriaRegistro.CAMPO_CTRL_ADMINISTRATIVO,registros.getCtrl_administrativo());
        values.put(Util_DosimetriaRegistro.CAMPO_CAPACITACION,registros.getCapacitacion());
        values.put(Util_DosimetriaRegistro.CAMPO_SENIALIZACION_PRECION,registros.getSenializacion_precion());
        values.put(Util_DosimetriaRegistro.CAMPO_SENIALIZACION_EPP,registros.getSenializacion_epp());
        values.put(Util_DosimetriaRegistro.CAMPO_ROTACION,registros.getRotacion());
        values.put(Util_DosimetriaRegistro.CAMPO_ADM_TIEMPO_EXPO,registros.getTiempo_exposicion());
        values.put(Util_DosimetriaRegistro.CAMPO_OTRO_ADMINISTRATIVO,registros.getOtro_administrativo());
        values.put(Util_DosimetriaRegistro.CAMPO_TAPONES_AU,registros.getTapones_au());
        values.put(Util_DosimetriaRegistro.CAMPO_MARCA_TAPONES_AUDI,registros.getMarca_tapones_audi());
        values.put(Util_DosimetriaRegistro.CAMPO_MODELO_TAPONES_AUDI,registros.getModelo_tapones_audi());
        values.put(Util_DosimetriaRegistro.CAMPO_NRR_TAPONES_AUDI,registros.getNrr_tapones_audi());
        values.put(Util_DosimetriaRegistro.CAMPO_OREJERAS,registros.getTapones_au());
        values.put(Util_DosimetriaRegistro.CAMPO_MARCA_OREJERAS,registros.getMarca_orejeras());
        values.put(Util_DosimetriaRegistro.CAMPO_MODELO_OREJERAS,registros.getModelo_orejeras());
        values.put(Util_DosimetriaRegistro.CAMPO_NRR_OREJERAS,registros.getNrr_orejeras());
        values.put(Util_DosimetriaRegistro.CAMPO_LEQ_DBA,registros.getLeq_dba());
        values.put(Util_DosimetriaRegistro.CAMPO_LPICO_DBA,registros.getLpico_dba());
        values.put(Util_DosimetriaRegistro.CAMPO_LMAX_DBA,registros.getLmax_dba());
        values.put(Util_DosimetriaRegistro.CAMPO_LMIN_DBA,registros.getLmin_dba());
        values.put(Util_DosimetriaRegistro.CAMPO_OBSERVACION,registros.getObservacion());
        values.put(Util_DosimetriaRegistro.CAMPO_ESTADO_RESULTADO, registros.getEstado_resultado());
        values.put(Util_DosimetriaRegistro.CAMPO_ESTADO,registros.getEstado());
        values.put(Util_DosimetriaRegistro.CAMPO_USER_REG,registros.getNom_analista());
        values.put(Util_DosimetriaRegistro.CAMPO_FEC_REG,registros.getFec_reg());*/

        db.insert(Util_RegistroFormatos.TABLA_REGISTRO_FORMATOS,null,values);
        //long nuevoRegistroId = db.insert(Util_RegistroFormatos.TABLA_REGISTRO_FORMATOS, null, values);

        //db.close();
        return true;
    }

    public boolean ActualizarFormato(Dosimetria_Registro registros, int id_plan_trabajo_formato_reg) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fecha = dateFormat.format(new Date());

        values.put(Util_RegistroFormatos.CAMPO_COD_FORMATO, registros.getCod_formato());
        values.put(Util_RegistroFormatos.CAMPO_COD_REGISTRO, registros.getCod_registro());
        values.put(Util_RegistroFormatos.CAMPO_ID_FORMATO, registros.getId_formato());
        values.put(Util_RegistroFormatos.CAMPO_ID_PLAN_TRABAJO, registros.getId_plan_trabajo());
        values.put(Util_RegistroFormatos.CAMPO_ID_PT_FORMATO, registros.getId_pt_formato());
        values.put(Util_RegistroFormatos.CAMPO_COD_EQUIPO1, registros.getCod_equipo1());
        values.put(Util_RegistroFormatos.CAMPO_NOM_EQUIPO1, registros.getNom_equipo1());
        values.put(Util_RegistroFormatos.CAMPO_COD_EQUIPO2, registros.getCod_equipo2());
        values.put(Util_RegistroFormatos.CAMPO_NOM_EQUIPO2, registros.getNom_equipo2());
        values.put(Util_RegistroFormatos.CAMPO_SERIE_EQ1, registros.getSerie_eq1());
        values.put(Util_RegistroFormatos.CAMPO_SERIE_EQ2, registros.getSerie_eq2());
        values.put(Util_RegistroFormatos.CAMPO_ID_EQUIPO1, registros.getId_equipo1());
        values.put(Util_RegistroFormatos.CAMPO_ID_EQUIPO2, registros.getId_equipo2());
        values.put(Util_RegistroFormatos.CAMPO_ID_ANALISTA, registros.getId_analista());
        values.put(Util_RegistroFormatos.CAMPO_NOM_ANALISTA, registros.getNom_analista());
        values.put(Util_RegistroFormatos.CAMPO_HORA_SITU, registros.getHora_situ());
        values.put(Util_RegistroFormatos.CAMPO_NIVEL, registros.getNivel());
        values.put(Util_RegistroFormatos.CAMPO_VARIACION, registros.getVariacion());
        values.put(Util_RegistroFormatos.CAMPO_FEC_MONITOREO, registros.getFec_monitoreo());
        values.put(Util_RegistroFormatos.CAMPO_HORA_INICIAL, registros.getHora_inicial());
        values.put(Util_RegistroFormatos.CAMPO_HORA_FINAL, registros.getHora_final());
        values.put(Util_RegistroFormatos.CAMPO_TIEMPO_EXPOSICION, registros.getTiempo_exposicion());
        values.put(Util_RegistroFormatos.CAMPO_JORNADA, registros.getJornada());
        values.put(Util_RegistroFormatos.CAMPO_TIPO_DOC_TRABAJADOR, registros.getTipo_doc_trabajador());
        values.put(Util_RegistroFormatos.CAMPO_NUM_DOC_TRABAJADOR, registros.getNum_doc_trabajador());
        values.put(Util_RegistroFormatos.CAMPO_NOM_TRABAJADOR, registros.getNom_trabajador());
        values.put(Util_RegistroFormatos.CAMPO_PUESTO_TRABAJADOR, registros.getPuesto_trabajador());
        values.put(Util_RegistroFormatos.CAMPO_AREA_TRABAJO, registros.getArea_trabajo());
        values.put(Util_RegistroFormatos.CAMPO_ACTIVIDADES_REALIZADAS, registros.getActividades_realizadas());
        values.put(Util_RegistroFormatos.CAMPO_EDAD_TRABAJADOR, registros.getEdad_trabajador());
        values.put(Util_RegistroFormatos.CAMPO_CH_RUIDO_EXTERNO, registros.getCh_ruido_externo());
        values.put(Util_RegistroFormatos.CAMPO_CH_RUIDO_ANTIGUO, registros.getCh_ruido_antiguo());
        values.put(Util_RegistroFormatos.CAMPO_CH_RUIDO_GENERADO_POR, registros.getCh_ruido_generado_por());
        values.put(Util_RegistroFormatos.CAMPO_RUIDO_GENERADO_POR, registros.getRuido_generado_por());
        values.put(Util_RegistroFormatos.CAMPO_OTRO_RUIDO, registros.getOtro_ruido());
        values.put(Util_RegistroFormatos.CAMPO_HORA_TRABAJO, registros.getHora_trabajo());
        values.put(Util_RegistroFormatos.CAMPO_REGIMEN_LABORAL, registros.getRegimen_laboral());
        values.put(Util_RegistroFormatos.CAMPO_HORARIO_REFRIGERIO, registros.getHorario_refrigerio());
        values.put(Util_RegistroFormatos.CAMPO_ANIO_OCU_CARGO, registros.getAnio_ocu_cargo());
        values.put(Util_RegistroFormatos.CAMPO_MES_OCU_CARGO, registros.getMes_ocu_cargo());
        values.put(Util_RegistroFormatos.CAMPO_MOLESTIA_OIDO, registros.getMolestia_oido());
        values.put(Util_RegistroFormatos.CAMPO_ENFERMEDAD_OIDO, registros.getEnfermedad_oido());
        values.put(Util_RegistroFormatos.CAMPO_DETALLE_ENF_OIDO, registros.getDetalle_enf_oido());
        values.put(Util_RegistroFormatos.CAMPO_FECHA_ULTIMO_EXAMEN, registros.getFecha_ultimo_examen());
        values.put(Util_RegistroFormatos.CAMPO_MES_ULTIMO_EXAMEN, registros.getMes_ultimo_examen());
        values.put(Util_RegistroFormatos.CAMPO_ANIO_ULTIMO_EXAMEN, registros.getAnio_ultimo_examen());
        values.put(Util_RegistroFormatos.CAMPO_CTRL_INGENIERIA, registros.getCtrl_ingenieria());
        values.put(Util_RegistroFormatos.CAMPO_AISLAMIENTO, registros.getAislamiento());
        values.put(Util_RegistroFormatos.CAMPO_TECHOS, registros.getTechos());
        values.put(Util_RegistroFormatos.CAMPO_CABINAS, registros.getCabinas());
        values.put(Util_RegistroFormatos.CAMPO_ORIENTACION, registros.getOrientacion());
        values.put(Util_RegistroFormatos.CAMPO_CERRAMIENTO, registros.getCerramiento());
        values.put(Util_RegistroFormatos.CAMPO_OTRO_INGENIERIA, registros.getOtro_ingenieria());
        values.put(Util_RegistroFormatos.CAMPO_CTRL_ADMINISTRATIVO, registros.getCtrl_administrativo());
        values.put(Util_RegistroFormatos.CAMPO_CAPACITACION, registros.getCapacitacion());
        values.put(Util_RegistroFormatos.CAMPO_SENIALIZACION_PRECION, registros.getSenializacion_precion());
        values.put(Util_RegistroFormatos.CAMPO_SENIALIZACION_EPP, registros.getSenializacion_epp());
        values.put(Util_RegistroFormatos.CAMPO_ROTACION, registros.getRotacion());
        values.put(Util_RegistroFormatos.CAMPO_ADM_TIEMPO_EXPO, registros.getTiempo_exposicion());
        values.put(Util_RegistroFormatos.CAMPO_OTRO_ADMINISTRATIVO, registros.getOtro_administrativo());
        values.put(Util_RegistroFormatos.CAMPO_TAPONES_AU, registros.getTapones_au());
        values.put(Util_RegistroFormatos.CAMPO_MARCA_TAPONES_AUDI, registros.getMarca_tapones_audi());
        values.put(Util_RegistroFormatos.CAMPO_MODELO_TAPONES_AUDI, registros.getModelo_tapones_audi());
        values.put(Util_RegistroFormatos.CAMPO_NRR_TAPONES_AUDI, registros.getNrr_tapones_audi());
        values.put(Util_RegistroFormatos.CAMPO_OREJERAS, registros.getTapones_au());
        values.put(Util_RegistroFormatos.CAMPO_MARCA_OREJERAS, registros.getMarca_orejeras());
        values.put(Util_RegistroFormatos.CAMPO_MODELO_OREJERAS, registros.getModelo_orejeras());
        values.put(Util_RegistroFormatos.CAMPO_NRR_OREJERAS, registros.getNrr_orejeras());
        values.put(Util_RegistroFormatos.CAMPO_LEQUI, registros.getLeq_dba());
        values.put(Util_RegistroFormatos.CAMPO_LPICO_DBA, registros.getLpico_dba());
        values.put(Util_RegistroFormatos.CAMPO_LMAX, registros.getLmax_dba());
        values.put(Util_RegistroFormatos.CAMPO_LMIN, registros.getLmin_dba());
        values.put(Util_RegistroFormatos.CAMPO_OBSERVACION, registros.getObservacion());
        values.put(Util_RegistroFormatos.CAMPO_ESTADO_RESULTADO, registros.getEstado_resultado());
        values.put(Util_RegistroFormatos.CAMPO_RUTA_FOTO, registros.getRuta_foto());
        values.put(Util_RegistroFormatos.CAMPO_ESTADO, registros.getEstado());
        values.put(Util_RegistroFormatos.CAMPO_USER_REG, registros.getUser_reg());
        values.put(Util_RegistroFormatos.CAMPO_FEC_REG, registros.getFec_reg());
        values.put(Util_RegistroFormatos.CAMPO_FEC_ACT, fecha);
        values.put(Util_RegistroFormatos.CAMPO_USER_ACT, registros.getUser_reg());
        values.put(Util_RegistroFormatos.CAMPO_ESTADO_SINCRO, 1); // MARCANDO PARA SABER QUE FALTA SINCRONIZAR

        // Condiciones WHERE para identificar el registro a actualizar
        String whereClause = Util_RegistroFormatos.CAMPO_ID_PLAN_TRABAJO_FORMATO_REG + " = ?";
        String[] whereArgs = {String.valueOf(id_plan_trabajo_formato_reg)};

        // Ejecutar la actualización
        int rowsAffected = db.update(Util_RegistroFormatos.TABLA_REGISTRO_FORMATOS, values, whereClause, whereArgs);

        // Cerrar la base de datos
        db.close();

        // Devolver true si se actualizó al menos una fila
        return rowsAffected > 0;
    }

}
