package com.mijael.mein.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mijael.mein.Entidades.RegistroFormatos;
import com.mijael.mein.HELPER.RegistroFormatosSQLiteHelper;
import com.mijael.mein.Utilidades.Util_RegistroFormatos;

import java.util.ArrayList;
import java.util.List;

public class DAO_RegistroFormatos {
    private RegistroFormatosSQLiteHelper dataHelper;
    public DAO_RegistroFormatos(Context context){dataHelper = RegistroFormatosSQLiteHelper.getInstance(context);}

    public List<RegistroFormatos> listarRegistros(){
        List<RegistroFormatos> registrosList = new ArrayList<>();
        SQLiteDatabase db = dataHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Util_RegistroFormatos.TABLA_REGISTRO_FORMATOS , null);
        if(cursor.moveToFirst()){
            do{
                RegistroFormatos registros = new RegistroFormatos(
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_ID_PLAN_TRABAJO_FORMATO_REG)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_ID_FORMATO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_COD_REGISTRO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_COD_FORMATO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_ID_PLAN_TRABAJO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_ID_PT_FORMATO)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_ID_ANALISTA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_NOM_ANALISTA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_COD_EQUIPO1)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_NOM_EQUIPO1)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_COD_EQUIPO2)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_NOM_EQUIPO2)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_COD_EQUIPO3)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_NOM_EQUIPO3)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_SERIE_EQ1)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_SERIE_EQ2)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_SERIE_EQ3)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_ID_EQUIPO1)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_ID_EQUIPO2)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_ID_EQUIPO3)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_VERF_INSITU)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_HORA_SITU)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_NIVEL)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_VARIACION)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_LUZ)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_AREA_TRABAJO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_DESC_AREA_TRABAJO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_AREA_TRAB_DETA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_ACTIVIDADES_REALIZADAS)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_ID_HORARIO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_HORA_TRABAJO)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_N_PERSONAS)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_FEC_MONITOREO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_HORA_INICIAL)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_TIEMPO_MEDICION)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_HORA_FINAL)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_V_VIENTO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_H_RELATIVA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_HORA_JORNADA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_MIN_JORNADA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_HORA_EXPOSICION)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_MIN_EXPOSICION)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_JORNADA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_NOM_TRABAJADOR)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_TIPO_DOC_TRABAJADOR)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_NUM_DOC_TRABAJADOR)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_PUESTO_TRABAJADOR)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_EDAD_TRABAJADOR)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_PESO_TRABAJADOR)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_REGIMEN_LABORAL)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_HORARIO_REFRIGERIO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_TIEMPO_OCUPADO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_MOLESTIA_OIDO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_ENFERMEDAD_OIDO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_DETALLE_ENF_OIDO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_FECHA_ULTIMO_EXAMEN)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_MES_ULTIMO_EXAMEN)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_ANIO_ULTIMO_EXAMEN)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_CTRL_INGENIERIA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_CTRL_ADMINISTRATIVO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_NOM_EPP)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_PROT_AUDITIVO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_NOM_CTRL_INGENIERIA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_NOM_CTRL_ADMIN)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_AISLAMIENTO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_TECHOS)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_CABINAS)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_ORIENTACION)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_CERRAMIENTO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_OTRO_INGENIERIA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_CAPACITACION)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_SENIALIZACION_PRECION)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_SENIALIZACION_EPP)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_SENIALIZACION_VIBRACION)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_SENIALIZACION_AREA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_MANTENIMIENTO_VIBRACION)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_ROTACION)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_AISLANTE_DETALLE)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_CABINA_DETALLE)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_CAPACITACION_DETALLE)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_SENIALIZACION_NIVEL_DETALLE)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_SENIALIZACION_EPP_DETALLE)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_ROTACION_DETALLE)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_TIEMPO_EXPOSICION_DETALLE)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_TAPONES_AU)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_MARCA_TAPONES_AUDI)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_MODELO_TAPONES_AUDI)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_OREJERAS)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_MARCA_OREJERAS)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_MODELO_OREJERAS)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_NRR_OREJERAS)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_NRR_TAPONES_AUDI)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_ADM_TIEMPO_EXPO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_TIEMPO_EXPOSICION)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_OTRO_ADMINISTRATIVO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_TAREAS_MEDICION)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_TAREA_VISUAL)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_TIPO_TAREA_VISUAL)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_NIVEL_MIN_ILU)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_OBSERVACION)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_CH_RUIDO_EXTERNO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_CH_RUIDO_ANTIGUO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_CH_RUIDO_GENERADO_POR)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_RUIDO_GENERADO_POR)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_OTRO_RUIDO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_AREA_REQ_CONCENTR)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_LIM_MAX_PERMIS)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_TIPO_MEDICION)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_NOM_TIPO_MEDICION)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_LMIN)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_LMAX)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_LEQUI)),
// ... campos adicionales ...
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_LEQUI_MD1)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_LEQUI_MD2)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_LEQUI_MD3)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_LEQUI_MD4)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_LEQUI_MD5)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_LMAX_MD1)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_LMAX_MD2)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_LMAX_MD3)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_LMAX_MD4)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_LMAX_MD5)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_LMIN_MD1)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_LMIN_MD2)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_LMIN_MD3)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_LMIN_MD4)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_LMIN_MD5)),

                        cursor.getDouble(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_LPICO_DBA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_CONFORMIDAD)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_EVAL_20P)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_TIPO_VIBRACION)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_LATERALIDAD_MANO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_UBIC_EQUIP)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_ANIO_OCU_CARGO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_MES_OCU_CARGO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_COND_TRAB)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_PORC_DESCAN)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_FOTO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_RESULTADO)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_ESTADO_RESULTADO)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_ESTADO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_FEC_REG)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_USER_REG)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_FEC_ACT)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_USER_ACT)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_FEC_ELI)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_USER_ELI))

                );
                registrosList.add(registros);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return registrosList;
    }

    public void actualizarRegistro(RegistroFormatos registro) {
        SQLiteDatabase db = dataHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        //values.put(Util_RegistroFormatos.CAMPO_ID_PLAN_TRABAJO_FORMATO_REG, registro.getId_plan_trabajo_formato_reg());
        values.put(Util_RegistroFormatos.CAMPO_ID_FORMATO, registro.getId_formato());
        values.put(Util_RegistroFormatos.CAMPO_COD_REGISTRO, registro.getCod_registro());
        values.put(Util_RegistroFormatos.CAMPO_COD_FORMATO, registro.getCod_formato());
        values.put(Util_RegistroFormatos.CAMPO_ID_PLAN_TRABAJO, registro.getId_plan_trabajo());
        values.put(Util_RegistroFormatos.CAMPO_ID_PT_FORMATO, registro.getId_pt_formato());
        values.put(Util_RegistroFormatos.CAMPO_ID_ANALISTA, registro.getId_analista());
        values.put(Util_RegistroFormatos.CAMPO_NOM_ANALISTA, registro.getNom_analista());
        values.put(Util_RegistroFormatos.CAMPO_COD_EQUIPO1, registro.getCod_equipo1());
        values.put(Util_RegistroFormatos.CAMPO_NOM_EQUIPO1, registro.getNom_equipo1());
        values.put(Util_RegistroFormatos.CAMPO_COD_EQUIPO2, registro.getCod_equipo2());
        values.put(Util_RegistroFormatos.CAMPO_NOM_EQUIPO2, registro.getNom_equipo2());
        values.put(Util_RegistroFormatos.CAMPO_COD_EQUIPO3, registro.getCod_equipo3());
        values.put(Util_RegistroFormatos.CAMPO_NOM_EQUIPO3, registro.getNom_equipo3());
        values.put(Util_RegistroFormatos.CAMPO_SERIE_EQ1, registro.getSerie_eq1());
        values.put(Util_RegistroFormatos.CAMPO_SERIE_EQ2, registro.getSerie_eq2());
        values.put(Util_RegistroFormatos.CAMPO_SERIE_EQ3, registro.getSerie_eq3());
        values.put(Util_RegistroFormatos.CAMPO_ID_EQUIPO1, registro.getId_equipo1());
        values.put(Util_RegistroFormatos.CAMPO_ID_EQUIPO2, registro.getId_equipo2());
        values.put(Util_RegistroFormatos.CAMPO_ID_EQUIPO3, registro.getId_equipo3());
        values.put(Util_RegistroFormatos.CAMPO_VERF_INSITU, registro.getVerf_insitu());
        values.put(Util_RegistroFormatos.CAMPO_HORA_SITU, registro.getHora_situ());
        values.put(Util_RegistroFormatos.CAMPO_NIVEL, registro.getNivel());
        values.put(Util_RegistroFormatos.CAMPO_VARIACION, registro.getVariacion());
        values.put(Util_RegistroFormatos.CAMPO_LUZ, registro.getLuz());
        values.put(Util_RegistroFormatos.CAMPO_AREA_TRABAJO, registro.getArea_trabajo());
        values.put(Util_RegistroFormatos.CAMPO_DESC_AREA_TRABAJO, registro.getDesc_area_trabajo());
        values.put(Util_RegistroFormatos.CAMPO_AREA_TRAB_DETA, registro.getArea_trab_deta());
        values.put(Util_RegistroFormatos.CAMPO_ACTIVIDADES_REALIZADAS, registro.getActividades_realizadas());
        values.put(Util_RegistroFormatos.CAMPO_ID_HORARIO, registro.getId_horario());
        values.put(Util_RegistroFormatos.CAMPO_HORA_TRABAJO, registro.getHora_trabajo());
        values.put(Util_RegistroFormatos.CAMPO_N_PERSONAS, registro.getN_personas());
        values.put(Util_RegistroFormatos.CAMPO_FEC_MONITOREO, registro.getFec_monitoreo());
        values.put(Util_RegistroFormatos.CAMPO_HORA_INICIAL, registro.getHora_inicial());
        values.put(Util_RegistroFormatos.CAMPO_TIEMPO_MEDICION, registro.getTiempo_medicion());
        values.put(Util_RegistroFormatos.CAMPO_HORA_FINAL, registro.getHora_final());
        values.put(Util_RegistroFormatos.CAMPO_V_VIENTO, registro.getV_viento());
        values.put(Util_RegistroFormatos.CAMPO_H_RELATIVA, registro.getH_relativa());
        values.put(Util_RegistroFormatos.CAMPO_HORA_JORNADA, registro.getHora_jornada());
        values.put(Util_RegistroFormatos.CAMPO_MIN_JORNADA, registro.getMin_jornada());
        values.put(Util_RegistroFormatos.CAMPO_HORA_EXPOSICION, registro.getHora_exposicion());
        values.put(Util_RegistroFormatos.CAMPO_MIN_EXPOSICION, registro.getMin_exposicion());
        values.put(Util_RegistroFormatos.CAMPO_JORNADA, registro.getJornada());
        values.put(Util_RegistroFormatos.CAMPO_NOM_TRABAJADOR, registro.getNom_trabajador());
        values.put(Util_RegistroFormatos.CAMPO_TIPO_DOC_TRABAJADOR, registro.getTipo_doc_trabajador());
        values.put(Util_RegistroFormatos.CAMPO_NUM_DOC_TRABAJADOR, registro.getNum_doc_trabajador());
        values.put(Util_RegistroFormatos.CAMPO_PUESTO_TRABAJADOR, registro.getPuesto_trabajador());
        values.put(Util_RegistroFormatos.CAMPO_EDAD_TRABAJADOR, registro.getEdad_trabajador());
        values.put(Util_RegistroFormatos.CAMPO_PESO_TRABAJADOR, registro.getPeso_trabajador());
        values.put(Util_RegistroFormatos.CAMPO_REGIMEN_LABORAL, registro.getRegimen_laboral());
        values.put(Util_RegistroFormatos.CAMPO_HORARIO_REFRIGERIO, registro.getHorario_refrigerio());
        values.put(Util_RegistroFormatos.CAMPO_TIEMPO_OCUPADO, registro.getTiempo_ocupado());
        values.put(Util_RegistroFormatos.CAMPO_MOLESTIA_OIDO, registro.getMolestia_oido());
        values.put(Util_RegistroFormatos.CAMPO_ENFERMEDAD_OIDO, registro.getEnfermedad_oido());
        values.put(Util_RegistroFormatos.CAMPO_DETALLE_ENF_OIDO, registro.getDetalle_enf_oido());
        values.put(Util_RegistroFormatos.CAMPO_FECHA_ULTIMO_EXAMEN, registro.getFecha_ultimo_examen());
        values.put(Util_RegistroFormatos.CAMPO_MES_ULTIMO_EXAMEN, registro.getMes_ultimo_examen());
        values.put(Util_RegistroFormatos.CAMPO_ANIO_ULTIMO_EXAMEN, registro.getAnio_ultimo_examen());
        values.put(Util_RegistroFormatos.CAMPO_CTRL_INGENIERIA, registro.getCtrl_ingenieria());
        values.put(Util_RegistroFormatos.CAMPO_CTRL_ADMINISTRATIVO, registro.getCtrl_administrativo());
        values.put(Util_RegistroFormatos.CAMPO_NOM_EPP, registro.getNom_epp());
        values.put(Util_RegistroFormatos.CAMPO_PROT_AUDITIVO, registro.getProt_auditivo());
        values.put(Util_RegistroFormatos.CAMPO_NOM_CTRL_INGENIERIA, registro.getNom_ctrl_ingenieria());
        values.put(Util_RegistroFormatos.CAMPO_NOM_CTRL_ADMIN, registro.getNom_ctrl_admin());
        values.put(Util_RegistroFormatos.CAMPO_AISLAMIENTO, registro.getAislamiento());
        values.put(Util_RegistroFormatos.CAMPO_TECHOS, registro.getTechos());
        values.put(Util_RegistroFormatos.CAMPO_CABINAS, registro.getCabinas());
        values.put(Util_RegistroFormatos.CAMPO_ORIENTACION, registro.getOrientacion());
        values.put(Util_RegistroFormatos.CAMPO_CERRAMIENTO, registro.getCerramiento());
        values.put(Util_RegistroFormatos.CAMPO_OTRO_INGENIERIA, registro.getOtro_ingenieria());
        values.put(Util_RegistroFormatos.CAMPO_CAPACITACION, registro.getCapacitacion());
        values.put(Util_RegistroFormatos.CAMPO_SENIALIZACION_PRECION, registro.getSenializacion_precion());
        values.put(Util_RegistroFormatos.CAMPO_SENIALIZACION_EPP, registro.getSenializacion_epp());
        values.put(Util_RegistroFormatos.CAMPO_SENIALIZACION_VIBRACION, registro.getSenializacion_vibracion());
        values.put(Util_RegistroFormatos.CAMPO_SENIALIZACION_AREA, registro.getSenializacion_area());
        values.put(Util_RegistroFormatos.CAMPO_MANTENIMIENTO_VIBRACION, registro.getMantenimiento_vibracion());
        values.put(Util_RegistroFormatos.CAMPO_ROTACION, registro.getRotacion());
        values.put(Util_RegistroFormatos.CAMPO_AISLANTE_DETALLE, registro.getAislante_detalle());
        values.put(Util_RegistroFormatos.CAMPO_CABINA_DETALLE, registro.getCabina_detalle());
        values.put(Util_RegistroFormatos.CAMPO_CAPACITACION_DETALLE, registro.getCapacitacion_detalle());
        values.put(Util_RegistroFormatos.CAMPO_SENIALIZACION_NIVEL_DETALLE, registro.getSenializacion_nivel_detalle());
        values.put(Util_RegistroFormatos.CAMPO_SENIALIZACION_EPP_DETALLE, registro.getSenializacion_epp_detalle());
        values.put(Util_RegistroFormatos.CAMPO_ROTACION_DETALLE, registro.getRotacion_detalle());
        values.put(Util_RegistroFormatos.CAMPO_TIEMPO_EXPOSICION_DETALLE, registro.getTiempo_exposicion_detalle());
        values.put(Util_RegistroFormatos.CAMPO_TAPONES_AU, registro.getTapones_au());
        values.put(Util_RegistroFormatos.CAMPO_MARCA_TAPONES_AUDI, registro.getMarca_tapones_audi());
        values.put(Util_RegistroFormatos.CAMPO_MODELO_TAPONES_AUDI, registro.getModelo_tapones_audi());
        values.put(Util_RegistroFormatos.CAMPO_OREJERAS, registro.getOrejereas());
        values.put(Util_RegistroFormatos.CAMPO_MARCA_OREJERAS, registro.getMarca_orejeras());
        values.put(Util_RegistroFormatos.CAMPO_MODELO_OREJERAS, registro.getModelo_orejeras());
        values.put(Util_RegistroFormatos.CAMPO_NRR_OREJERAS, registro.getNrr_orejeras());
        values.put(Util_RegistroFormatos.CAMPO_NRR_TAPONES_AUDI, registro.getNrr_tapones_audi());
        values.put(Util_RegistroFormatos.CAMPO_ADM_TIEMPO_EXPO, registro.getAdm_tiempo_expo());
        values.put(Util_RegistroFormatos.CAMPO_TIEMPO_EXPOSICION, registro.getTiempo_exposicion());
        values.put(Util_RegistroFormatos.CAMPO_OTRO_ADMINISTRATIVO, registro.getOtro_administrativo());
        values.put(Util_RegistroFormatos.CAMPO_TAREAS_MEDICION, registro.getTareas_medicion());
        values.put(Util_RegistroFormatos.CAMPO_TAREA_VISUAL, registro.getTarea_visual());
        values.put(Util_RegistroFormatos.CAMPO_TIPO_TAREA_VISUAL, registro.getTipo_tarea_visual());
        values.put(Util_RegistroFormatos.CAMPO_NIVEL_MIN_ILU, registro.getNivel_min_ilu());
        values.put(Util_RegistroFormatos.CAMPO_OBSERVACION, registro.getObservacion());
        values.put(Util_RegistroFormatos.CAMPO_CH_RUIDO_EXTERNO, registro.getCh_ruido_externo());
        values.put(Util_RegistroFormatos.CAMPO_CH_RUIDO_ANTIGUO, registro.getCh_ruido_antiguo());
        values.put(Util_RegistroFormatos.CAMPO_CH_RUIDO_GENERADO_POR, registro.getCh_ruido_generado_por());
        values.put(Util_RegistroFormatos.CAMPO_RUIDO_GENERADO_POR, registro.getRuido_generado_por());
        values.put(Util_RegistroFormatos.CAMPO_OTRO_RUIDO, registro.getOtro_ruido());
        values.put(Util_RegistroFormatos.CAMPO_AREA_REQ_CONCENTR, registro.getArea_req_concentr());
        values.put(Util_RegistroFormatos.CAMPO_LIM_MAX_PERMIS, registro.getLim_max_permis());
        values.put(Util_RegistroFormatos.CAMPO_TIPO_MEDICION, registro.getTipo_medicion());
        values.put(Util_RegistroFormatos.CAMPO_NOM_TIPO_MEDICION, registro.getNom_tipo_medicion());
        values.put(Util_RegistroFormatos.CAMPO_LMIN, registro.getLmin());
        values.put(Util_RegistroFormatos.CAMPO_LMAX, registro.getLmax());
        values.put(Util_RegistroFormatos.CAMPO_LEQUI, registro.getLequi());
        // ... (otros campos)
        values.put(Util_RegistroFormatos.CAMPO_LEQUI_MD1, registro.getLequi_md1());
        values.put(Util_RegistroFormatos.CAMPO_LEQUI_MD2, registro.getLequi_md2());
        values.put(Util_RegistroFormatos.CAMPO_LEQUI_MD3, registro.getLequi_md3());
        values.put(Util_RegistroFormatos.CAMPO_LEQUI_MD4, registro.getLequi_md4());
        values.put(Util_RegistroFormatos.CAMPO_LEQUI_MD5, registro.getLequi_md5());
        values.put(Util_RegistroFormatos.CAMPO_LMIN_MD1, registro.getLmin_md1());
        values.put(Util_RegistroFormatos.CAMPO_LMIN_MD2, registro.getLmin_md2());
        values.put(Util_RegistroFormatos.CAMPO_LMIN_MD3, registro.getLmin_md3());
        values.put(Util_RegistroFormatos.CAMPO_LMIN_MD4, registro.getLmin_md4());
        values.put(Util_RegistroFormatos.CAMPO_LMIN_MD5, registro.getLmin_md5());
        values.put(Util_RegistroFormatos.CAMPO_LMAX_MD1, registro.getLmax_md1());
        values.put(Util_RegistroFormatos.CAMPO_LMAX_MD2, registro.getLmax_md2());
        values.put(Util_RegistroFormatos.CAMPO_LMAX_MD3, registro.getLmax_md3());
        values.put(Util_RegistroFormatos.CAMPO_LMAX_MD4, registro.getLmax_md4());
        values.put(Util_RegistroFormatos.CAMPO_LMAX_MD5, registro.getLmax_md5());

        values.put(Util_RegistroFormatos.CAMPO_LPICO_DBA, registro.getLpico_dba());
        values.put(Util_RegistroFormatos.CAMPO_CONFORMIDAD, registro.getConformidad());
        values.put(Util_RegistroFormatos.CAMPO_EVAL_20P, registro.getEval_20p());
        values.put(Util_RegistroFormatos.CAMPO_TIPO_VIBRACION, registro.getTipo_vibracion());
        values.put(Util_RegistroFormatos.CAMPO_LATERALIDAD_MANO, registro.getLateralidad_mano());
        values.put(Util_RegistroFormatos.CAMPO_UBIC_EQUIP, registro.getUbic_equip());
        values.put(Util_RegistroFormatos.CAMPO_ANIO_OCU_CARGO, registro.getAnio_ocu_cargo());
        values.put(Util_RegistroFormatos.CAMPO_MES_OCU_CARGO, registro.getMes_ocu_cargo());
        values.put(Util_RegistroFormatos.CAMPO_COND_TRAB, registro.getCond_trab());
        values.put(Util_RegistroFormatos.CAMPO_PORC_DESCAN, registro.getPorc_descan());
        values.put(Util_RegistroFormatos.CAMPO_FOTO, registro.getFoto());
        values.put(Util_RegistroFormatos.CAMPO_RESULTADO, registro.getResultado());
        values.put(Util_RegistroFormatos.CAMPO_ESTADO_RESULTADO, registro.getEstado_resultado());
        values.put(Util_RegistroFormatos.CAMPO_ESTADO, registro.getEstado());
        values.put(Util_RegistroFormatos.CAMPO_FEC_REG, registro.getFec_reg());
        values.put(Util_RegistroFormatos.CAMPO_USER_REG, registro.getUser_reg());
        values.put(Util_RegistroFormatos.CAMPO_FEC_ACT, registro.getFec_act());
        values.put(Util_RegistroFormatos.CAMPO_USER_ACT, registro.getUser_act());
        values.put(Util_RegistroFormatos.CAMPO_FEC_ELI, registro.getFec_eli());
        values.put(Util_RegistroFormatos.CAMPO_USER_ELI, registro.getUser_eli());


        String whereClause = Util_RegistroFormatos.CAMPO_ID_PLAN_TRABAJO_FORMATO_REG + "= ?"; // suponiendo que el identificador sea 'id'
        String[] whereArgs = { String.valueOf(registro.getId_plan_trabajo_formato_reg()) };

        long resultado = db.update(Util_RegistroFormatos.TABLA_REGISTRO_FORMATOS, values, whereClause, whereArgs);
        if (resultado == -1) {
            Log.e("TAG-E", "Error al actualizar REGISTRO-FORMATOS en la base de datos local");
        } else {
            Log.d("TAG-D", "REGISTRO-FORMATOS  actualizado en la base de datos local");
        }
        db.close();
    }

    public int contarRegistros() {
        SQLiteDatabase db = dataHelper.getReadableDatabase();
        Cursor cursor = null;
        int count = 0;

        try {
            cursor = db.rawQuery("SELECT COUNT(*) FROM " + Util_RegistroFormatos.TABLA_REGISTRO_FORMATOS, null);
            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null && db.isOpen()) {
                db.close(); // Cerrar la base de datos si está abierta
            }
        }
        return count;
    }
    public void insertarRegistros(List<RegistroFormatos> registrosList) {
        SQLiteDatabase db = dataHelper.getWritableDatabase();

        try {
            db.beginTransaction();
            ContentValues values;

            for (RegistroFormatos registro : registrosList) {
                values = new ContentValues();
                // Configurar los valores para la inserción
                values.put(Util_RegistroFormatos.CAMPO_ID_PLAN_TRABAJO_FORMATO_REG, registro.getId_plan_trabajo_formato_reg());
                values.put(Util_RegistroFormatos.CAMPO_ID_FORMATO, registro.getId_formato());
                values.put(Util_RegistroFormatos.CAMPO_COD_REGISTRO, registro.getCod_registro());
                values.put(Util_RegistroFormatos.CAMPO_COD_FORMATO, registro.getCod_formato());
                values.put(Util_RegistroFormatos.CAMPO_ID_PLAN_TRABAJO, registro.getId_plan_trabajo());
                values.put(Util_RegistroFormatos.CAMPO_ID_PT_FORMATO, registro.getId_pt_formato());
                values.put(Util_RegistroFormatos.CAMPO_ID_ANALISTA, registro.getId_analista());
                values.put(Util_RegistroFormatos.CAMPO_NOM_ANALISTA, registro.getNom_analista());
                values.put(Util_RegistroFormatos.CAMPO_COD_EQUIPO1, registro.getCod_equipo1());
                values.put(Util_RegistroFormatos.CAMPO_NOM_EQUIPO1, registro.getNom_equipo1());
                values.put(Util_RegistroFormatos.CAMPO_COD_EQUIPO2, registro.getCod_equipo2());
                values.put(Util_RegistroFormatos.CAMPO_NOM_EQUIPO2, registro.getNom_equipo2());
                values.put(Util_RegistroFormatos.CAMPO_COD_EQUIPO3, registro.getCod_equipo3());
                values.put(Util_RegistroFormatos.CAMPO_NOM_EQUIPO3, registro.getNom_equipo3());
                values.put(Util_RegistroFormatos.CAMPO_SERIE_EQ1, registro.getSerie_eq1());
                values.put(Util_RegistroFormatos.CAMPO_SERIE_EQ2, registro.getSerie_eq2());
                values.put(Util_RegistroFormatos.CAMPO_SERIE_EQ3, registro.getSerie_eq3());
                values.put(Util_RegistroFormatos.CAMPO_ID_EQUIPO1, registro.getId_equipo1());
                values.put(Util_RegistroFormatos.CAMPO_ID_EQUIPO2, registro.getId_equipo2());
                values.put(Util_RegistroFormatos.CAMPO_ID_EQUIPO3, registro.getId_equipo3());
                values.put(Util_RegistroFormatos.CAMPO_VERF_INSITU, registro.getVerf_insitu());
                values.put(Util_RegistroFormatos.CAMPO_HORA_SITU, registro.getHora_situ());
                values.put(Util_RegistroFormatos.CAMPO_NIVEL, registro.getNivel());
                values.put(Util_RegistroFormatos.CAMPO_VARIACION, registro.getVariacion());
                values.put(Util_RegistroFormatos.CAMPO_LUZ, registro.getLuz());
                values.put(Util_RegistroFormatos.CAMPO_AREA_TRABAJO, registro.getArea_trabajo());
                values.put(Util_RegistroFormatos.CAMPO_DESC_AREA_TRABAJO, registro.getDesc_area_trabajo());
                values.put(Util_RegistroFormatos.CAMPO_AREA_TRAB_DETA, registro.getArea_trab_deta());
                values.put(Util_RegistroFormatos.CAMPO_ACTIVIDADES_REALIZADAS, registro.getActividades_realizadas());
                values.put(Util_RegistroFormatos.CAMPO_ID_HORARIO, registro.getId_horario());
                values.put(Util_RegistroFormatos.CAMPO_HORA_TRABAJO, registro.getHora_trabajo());
                values.put(Util_RegistroFormatos.CAMPO_N_PERSONAS, registro.getN_personas());
                values.put(Util_RegistroFormatos.CAMPO_FEC_MONITOREO, registro.getFec_monitoreo());
                values.put(Util_RegistroFormatos.CAMPO_HORA_INICIAL, registro.getHora_inicial());
                values.put(Util_RegistroFormatos.CAMPO_TIEMPO_MEDICION, registro.getTiempo_medicion());
                values.put(Util_RegistroFormatos.CAMPO_HORA_FINAL, registro.getHora_final());
                values.put(Util_RegistroFormatos.CAMPO_V_VIENTO, registro.getV_viento());
                values.put(Util_RegistroFormatos.CAMPO_H_RELATIVA, registro.getH_relativa());
                values.put(Util_RegistroFormatos.CAMPO_HORA_JORNADA, registro.getHora_jornada());
                values.put(Util_RegistroFormatos.CAMPO_MIN_JORNADA, registro.getMin_jornada());
                values.put(Util_RegistroFormatos.CAMPO_HORA_EXPOSICION, registro.getHora_exposicion());
                values.put(Util_RegistroFormatos.CAMPO_MIN_EXPOSICION, registro.getMin_exposicion());
                values.put(Util_RegistroFormatos.CAMPO_JORNADA, registro.getJornada());
                values.put(Util_RegistroFormatos.CAMPO_NOM_TRABAJADOR, registro.getNom_trabajador());
                values.put(Util_RegistroFormatos.CAMPO_TIPO_DOC_TRABAJADOR, registro.getTipo_doc_trabajador());
                values.put(Util_RegistroFormatos.CAMPO_NUM_DOC_TRABAJADOR, registro.getNum_doc_trabajador());
                values.put(Util_RegistroFormatos.CAMPO_PUESTO_TRABAJADOR, registro.getPuesto_trabajador());
                values.put(Util_RegistroFormatos.CAMPO_EDAD_TRABAJADOR, registro.getEdad_trabajador());
                values.put(Util_RegistroFormatos.CAMPO_PESO_TRABAJADOR, registro.getPeso_trabajador());
                values.put(Util_RegistroFormatos.CAMPO_REGIMEN_LABORAL, registro.getRegimen_laboral());
                values.put(Util_RegistroFormatos.CAMPO_HORARIO_REFRIGERIO, registro.getHorario_refrigerio());
                values.put(Util_RegistroFormatos.CAMPO_TIEMPO_OCUPADO, registro.getTiempo_ocupado());
                values.put(Util_RegistroFormatos.CAMPO_MOLESTIA_OIDO, registro.getMolestia_oido());
                values.put(Util_RegistroFormatos.CAMPO_ENFERMEDAD_OIDO, registro.getEnfermedad_oido());
                values.put(Util_RegistroFormatos.CAMPO_DETALLE_ENF_OIDO, registro.getDetalle_enf_oido());
                values.put(Util_RegistroFormatos.CAMPO_FECHA_ULTIMO_EXAMEN, registro.getFecha_ultimo_examen());
                values.put(Util_RegistroFormatos.CAMPO_MES_ULTIMO_EXAMEN, registro.getMes_ultimo_examen());
                values.put(Util_RegistroFormatos.CAMPO_ANIO_ULTIMO_EXAMEN, registro.getAnio_ultimo_examen());
                values.put(Util_RegistroFormatos.CAMPO_CTRL_INGENIERIA, registro.getCtrl_ingenieria());
                values.put(Util_RegistroFormatos.CAMPO_CTRL_ADMINISTRATIVO, registro.getCtrl_administrativo());
                values.put(Util_RegistroFormatos.CAMPO_NOM_EPP, registro.getNom_epp());
                values.put(Util_RegistroFormatos.CAMPO_PROT_AUDITIVO, registro.getProt_auditivo());
                values.put(Util_RegistroFormatos.CAMPO_NOM_CTRL_INGENIERIA, registro.getNom_ctrl_ingenieria());
                values.put(Util_RegistroFormatos.CAMPO_NOM_CTRL_ADMIN, registro.getNom_ctrl_admin());
                values.put(Util_RegistroFormatos.CAMPO_AISLAMIENTO, registro.getAislamiento());
                values.put(Util_RegistroFormatos.CAMPO_TECHOS, registro.getTechos());
                values.put(Util_RegistroFormatos.CAMPO_CABINAS, registro.getCabinas());
                values.put(Util_RegistroFormatos.CAMPO_ORIENTACION, registro.getOrientacion());
                values.put(Util_RegistroFormatos.CAMPO_CERRAMIENTO, registro.getCerramiento());
                values.put(Util_RegistroFormatos.CAMPO_OTRO_INGENIERIA, registro.getOtro_ingenieria());
                values.put(Util_RegistroFormatos.CAMPO_CAPACITACION, registro.getCapacitacion());
                values.put(Util_RegistroFormatos.CAMPO_SENIALIZACION_PRECION, registro.getSenializacion_precion());
                values.put(Util_RegistroFormatos.CAMPO_SENIALIZACION_EPP, registro.getSenializacion_epp());
                values.put(Util_RegistroFormatos.CAMPO_SENIALIZACION_VIBRACION, registro.getSenializacion_vibracion());
                values.put(Util_RegistroFormatos.CAMPO_SENIALIZACION_AREA, registro.getSenializacion_area());
                values.put(Util_RegistroFormatos.CAMPO_MANTENIMIENTO_VIBRACION, registro.getMantenimiento_vibracion());
                values.put(Util_RegistroFormatos.CAMPO_ROTACION, registro.getRotacion());
                values.put(Util_RegistroFormatos.CAMPO_AISLANTE_DETALLE, registro.getAislante_detalle());
                values.put(Util_RegistroFormatos.CAMPO_CABINA_DETALLE, registro.getCabina_detalle());
                values.put(Util_RegistroFormatos.CAMPO_CAPACITACION_DETALLE, registro.getCapacitacion_detalle());
                values.put(Util_RegistroFormatos.CAMPO_SENIALIZACION_NIVEL_DETALLE, registro.getSenializacion_nivel_detalle());
                values.put(Util_RegistroFormatos.CAMPO_SENIALIZACION_EPP_DETALLE, registro.getSenializacion_epp_detalle());
                values.put(Util_RegistroFormatos.CAMPO_ROTACION_DETALLE, registro.getRotacion_detalle());
                values.put(Util_RegistroFormatos.CAMPO_TIEMPO_EXPOSICION_DETALLE, registro.getTiempo_exposicion_detalle());
                values.put(Util_RegistroFormatos.CAMPO_TAPONES_AU, registro.getTapones_au());
                values.put(Util_RegistroFormatos.CAMPO_MARCA_TAPONES_AUDI, registro.getMarca_tapones_audi());
                values.put(Util_RegistroFormatos.CAMPO_MODELO_TAPONES_AUDI, registro.getModelo_tapones_audi());
                values.put(Util_RegistroFormatos.CAMPO_OREJERAS, registro.getOrejereas());
                values.put(Util_RegistroFormatos.CAMPO_MARCA_OREJERAS, registro.getMarca_orejeras());
                values.put(Util_RegistroFormatos.CAMPO_MODELO_OREJERAS, registro.getModelo_orejeras());
                values.put(Util_RegistroFormatos.CAMPO_NRR_OREJERAS, registro.getNrr_orejeras());
                values.put(Util_RegistroFormatos.CAMPO_NRR_TAPONES_AUDI, registro.getNrr_tapones_audi());
                values.put(Util_RegistroFormatos.CAMPO_ADM_TIEMPO_EXPO, registro.getAdm_tiempo_expo());
                values.put(Util_RegistroFormatos.CAMPO_TIEMPO_EXPOSICION, registro.getTiempo_exposicion());
                values.put(Util_RegistroFormatos.CAMPO_OTRO_ADMINISTRATIVO, registro.getOtro_administrativo());
                values.put(Util_RegistroFormatos.CAMPO_TAREAS_MEDICION, registro.getTareas_medicion());
                values.put(Util_RegistroFormatos.CAMPO_TAREA_VISUAL, registro.getTarea_visual());
                values.put(Util_RegistroFormatos.CAMPO_TIPO_TAREA_VISUAL, registro.getTipo_tarea_visual());
                values.put(Util_RegistroFormatos.CAMPO_NIVEL_MIN_ILU, registro.getNivel_min_ilu());
                values.put(Util_RegistroFormatos.CAMPO_OBSERVACION, registro.getObservacion());
                values.put(Util_RegistroFormatos.CAMPO_CH_RUIDO_EXTERNO, registro.getCh_ruido_externo());
                values.put(Util_RegistroFormatos.CAMPO_CH_RUIDO_ANTIGUO, registro.getCh_ruido_antiguo());
                values.put(Util_RegistroFormatos.CAMPO_CH_RUIDO_GENERADO_POR, registro.getCh_ruido_generado_por());
                values.put(Util_RegistroFormatos.CAMPO_RUIDO_GENERADO_POR, registro.getRuido_generado_por());
                values.put(Util_RegistroFormatos.CAMPO_OTRO_RUIDO, registro.getOtro_ruido());
                values.put(Util_RegistroFormatos.CAMPO_AREA_REQ_CONCENTR, registro.getArea_req_concentr());
                values.put(Util_RegistroFormatos.CAMPO_LIM_MAX_PERMIS, registro.getLim_max_permis());
                values.put(Util_RegistroFormatos.CAMPO_TIPO_MEDICION, registro.getTipo_medicion());
                values.put(Util_RegistroFormatos.CAMPO_NOM_TIPO_MEDICION, registro.getNom_tipo_medicion());
                values.put(Util_RegistroFormatos.CAMPO_LMIN, registro.getLmin());
                values.put(Util_RegistroFormatos.CAMPO_LMAX, registro.getLmax());
                values.put(Util_RegistroFormatos.CAMPO_LEQUI, registro.getLequi());
                // ... (otros campos)
                values.put(Util_RegistroFormatos.CAMPO_LEQUI_MD1, registro.getLequi_md1());
                values.put(Util_RegistroFormatos.CAMPO_LEQUI_MD2, registro.getLequi_md2());
                values.put(Util_RegistroFormatos.CAMPO_LEQUI_MD3, registro.getLequi_md3());
                values.put(Util_RegistroFormatos.CAMPO_LEQUI_MD4, registro.getLequi_md4());
                values.put(Util_RegistroFormatos.CAMPO_LEQUI_MD5, registro.getLequi_md5());
                values.put(Util_RegistroFormatos.CAMPO_LMIN_MD1, registro.getLmin_md1());
                values.put(Util_RegistroFormatos.CAMPO_LMIN_MD2, registro.getLmin_md2());
                values.put(Util_RegistroFormatos.CAMPO_LMIN_MD3, registro.getLmin_md3());
                values.put(Util_RegistroFormatos.CAMPO_LMIN_MD4, registro.getLmin_md4());
                values.put(Util_RegistroFormatos.CAMPO_LMIN_MD5, registro.getLmin_md5());
                values.put(Util_RegistroFormatos.CAMPO_LMAX_MD1, registro.getLmax_md1());
                values.put(Util_RegistroFormatos.CAMPO_LMAX_MD2, registro.getLmax_md2());
                values.put(Util_RegistroFormatos.CAMPO_LMAX_MD3, registro.getLmax_md3());
                values.put(Util_RegistroFormatos.CAMPO_LMAX_MD4, registro.getLmax_md4());
                values.put(Util_RegistroFormatos.CAMPO_LMAX_MD5, registro.getLmax_md5());

                values.put(Util_RegistroFormatos.CAMPO_LPICO_DBA, registro.getLpico_dba());
                values.put(Util_RegistroFormatos.CAMPO_CONFORMIDAD, registro.getConformidad());
                values.put(Util_RegistroFormatos.CAMPO_EVAL_20P, registro.getEval_20p());
                values.put(Util_RegistroFormatos.CAMPO_TIPO_VIBRACION, registro.getTipo_vibracion());
                values.put(Util_RegistroFormatos.CAMPO_LATERALIDAD_MANO, registro.getLateralidad_mano());
                values.put(Util_RegistroFormatos.CAMPO_UBIC_EQUIP, registro.getUbic_equip());
                values.put(Util_RegistroFormatos.CAMPO_ANIO_OCU_CARGO, registro.getAnio_ocu_cargo());
                values.put(Util_RegistroFormatos.CAMPO_MES_OCU_CARGO, registro.getMes_ocu_cargo());
                values.put(Util_RegistroFormatos.CAMPO_COND_TRAB, registro.getCond_trab());
                values.put(Util_RegistroFormatos.CAMPO_PORC_DESCAN, registro.getPorc_descan());
                values.put(Util_RegistroFormatos.CAMPO_FOTO, registro.getFoto());
                values.put(Util_RegistroFormatos.CAMPO_RESULTADO, registro.getResultado());
                values.put(Util_RegistroFormatos.CAMPO_ESTADO_RESULTADO, registro.getEstado_resultado());
                values.put(Util_RegistroFormatos.CAMPO_ESTADO, registro.getEstado());
                values.put(Util_RegistroFormatos.CAMPO_FEC_REG, registro.getFec_reg());
                values.put(Util_RegistroFormatos.CAMPO_USER_REG, registro.getUser_reg());
                values.put(Util_RegistroFormatos.CAMPO_FEC_ACT, registro.getFec_act());
                values.put(Util_RegistroFormatos.CAMPO_USER_ACT, registro.getUser_act());
                values.put(Util_RegistroFormatos.CAMPO_FEC_ELI, registro.getFec_eli());
                values.put(Util_RegistroFormatos.CAMPO_USER_ELI, registro.getUser_eli());

                // Insertar el cine en la base de datos
                long resultado = db.insert(Util_RegistroFormatos.TABLA_REGISTRO_FORMATOS, null, values);
                if(resultado == -1){
                    Log.e("TAG-E", "Error al insertar REGISTRO-FORMATOS en la base de datos local");
                } else {
                    Log.d("TAG-D", "REGISTRO-FORMATOS insertado en la base de datos local");
                }
            }
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (db != null && db.isOpen()) {
                db.endTransaction(); // Finalizar la transacción si aún está abierta
                db.close(); // Cerrar la base de datos
            }
        }
    }
    /*public boolean verificarExistenciaEquipo(int idEquipo) {
        SQLiteDatabase db = dataHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(Util_RegistroFormatos.TABLA_EQUIPOS, null,
                    Util_RegistroFormatos.CAMPO_ID_EQUIPO + " = ?", new String[]{String.valueOf(idEquipo)},
                    null, null, null);

            return cursor != null && cursor.getCount() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }*/
    public void insertarRegistro(RegistroFormatos registro) {
        SQLiteDatabase db = dataHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util_RegistroFormatos.CAMPO_ID_PLAN_TRABAJO_FORMATO_REG, registro.getId_plan_trabajo_formato_reg());
        values.put(Util_RegistroFormatos.CAMPO_ID_FORMATO, registro.getId_formato());
        values.put(Util_RegistroFormatos.CAMPO_COD_REGISTRO, registro.getCod_registro());
        values.put(Util_RegistroFormatos.CAMPO_COD_FORMATO, registro.getCod_formato());
        values.put(Util_RegistroFormatos.CAMPO_ID_PLAN_TRABAJO, registro.getId_plan_trabajo());
        values.put(Util_RegistroFormatos.CAMPO_ID_PT_FORMATO, registro.getId_pt_formato());
        values.put(Util_RegistroFormatos.CAMPO_ID_ANALISTA, registro.getId_analista());
        values.put(Util_RegistroFormatos.CAMPO_NOM_ANALISTA, registro.getNom_analista());
        values.put(Util_RegistroFormatos.CAMPO_COD_EQUIPO1, registro.getCod_equipo1());
        values.put(Util_RegistroFormatos.CAMPO_NOM_EQUIPO1, registro.getNom_equipo1());
        values.put(Util_RegistroFormatos.CAMPO_COD_EQUIPO2, registro.getCod_equipo2());
        values.put(Util_RegistroFormatos.CAMPO_NOM_EQUIPO2, registro.getNom_equipo2());
        values.put(Util_RegistroFormatos.CAMPO_COD_EQUIPO3, registro.getCod_equipo3());
        values.put(Util_RegistroFormatos.CAMPO_NOM_EQUIPO3, registro.getNom_equipo3());
        values.put(Util_RegistroFormatos.CAMPO_SERIE_EQ1, registro.getSerie_eq1());
        values.put(Util_RegistroFormatos.CAMPO_SERIE_EQ2, registro.getSerie_eq2());
        values.put(Util_RegistroFormatos.CAMPO_SERIE_EQ3, registro.getSerie_eq3());
        values.put(Util_RegistroFormatos.CAMPO_ID_EQUIPO1, registro.getId_equipo1());
        values.put(Util_RegistroFormatos.CAMPO_ID_EQUIPO2, registro.getId_equipo2());
        values.put(Util_RegistroFormatos.CAMPO_ID_EQUIPO3, registro.getId_equipo3());
        values.put(Util_RegistroFormatos.CAMPO_VERF_INSITU, registro.getVerf_insitu());
        values.put(Util_RegistroFormatos.CAMPO_HORA_SITU, registro.getHora_situ());
        values.put(Util_RegistroFormatos.CAMPO_NIVEL, registro.getNivel());
        values.put(Util_RegistroFormatos.CAMPO_VARIACION, registro.getVariacion());
        values.put(Util_RegistroFormatos.CAMPO_LUZ, registro.getLuz());
        values.put(Util_RegistroFormatos.CAMPO_AREA_TRABAJO, registro.getArea_trabajo());
        values.put(Util_RegistroFormatos.CAMPO_DESC_AREA_TRABAJO, registro.getDesc_area_trabajo());
        values.put(Util_RegistroFormatos.CAMPO_AREA_TRAB_DETA, registro.getArea_trab_deta());
        values.put(Util_RegistroFormatos.CAMPO_ACTIVIDADES_REALIZADAS, registro.getActividades_realizadas());
        values.put(Util_RegistroFormatos.CAMPO_ID_HORARIO, registro.getId_horario());
        values.put(Util_RegistroFormatos.CAMPO_HORA_TRABAJO, registro.getHora_trabajo());
        values.put(Util_RegistroFormatos.CAMPO_N_PERSONAS, registro.getN_personas());
        values.put(Util_RegistroFormatos.CAMPO_FEC_MONITOREO, registro.getFec_monitoreo());
        values.put(Util_RegistroFormatos.CAMPO_HORA_INICIAL, registro.getHora_inicial());
        values.put(Util_RegistroFormatos.CAMPO_TIEMPO_MEDICION, registro.getTiempo_medicion());
        values.put(Util_RegistroFormatos.CAMPO_HORA_FINAL, registro.getHora_final());
        values.put(Util_RegistroFormatos.CAMPO_V_VIENTO, registro.getV_viento());
        values.put(Util_RegistroFormatos.CAMPO_H_RELATIVA, registro.getH_relativa());
        values.put(Util_RegistroFormatos.CAMPO_HORA_JORNADA, registro.getHora_jornada());
        values.put(Util_RegistroFormatos.CAMPO_MIN_JORNADA, registro.getMin_jornada());
        values.put(Util_RegistroFormatos.CAMPO_HORA_EXPOSICION, registro.getHora_exposicion());
        values.put(Util_RegistroFormatos.CAMPO_MIN_EXPOSICION, registro.getMin_exposicion());
        values.put(Util_RegistroFormatos.CAMPO_JORNADA, registro.getJornada());
        values.put(Util_RegistroFormatos.CAMPO_NOM_TRABAJADOR, registro.getNom_trabajador());
        values.put(Util_RegistroFormatos.CAMPO_TIPO_DOC_TRABAJADOR, registro.getTipo_doc_trabajador());
        values.put(Util_RegistroFormatos.CAMPO_NUM_DOC_TRABAJADOR, registro.getNum_doc_trabajador());
        values.put(Util_RegistroFormatos.CAMPO_PUESTO_TRABAJADOR, registro.getPuesto_trabajador());
        values.put(Util_RegistroFormatos.CAMPO_EDAD_TRABAJADOR, registro.getEdad_trabajador());
        values.put(Util_RegistroFormatos.CAMPO_PESO_TRABAJADOR, registro.getPeso_trabajador());
        values.put(Util_RegistroFormatos.CAMPO_REGIMEN_LABORAL, registro.getRegimen_laboral());
        values.put(Util_RegistroFormatos.CAMPO_HORARIO_REFRIGERIO, registro.getHorario_refrigerio());
        values.put(Util_RegistroFormatos.CAMPO_TIEMPO_OCUPADO, registro.getTiempo_ocupado());
        values.put(Util_RegistroFormatos.CAMPO_MOLESTIA_OIDO, registro.getMolestia_oido());
        values.put(Util_RegistroFormatos.CAMPO_ENFERMEDAD_OIDO, registro.getEnfermedad_oido());
        values.put(Util_RegistroFormatos.CAMPO_DETALLE_ENF_OIDO, registro.getDetalle_enf_oido());
        values.put(Util_RegistroFormatos.CAMPO_FECHA_ULTIMO_EXAMEN, registro.getFecha_ultimo_examen());
        values.put(Util_RegistroFormatos.CAMPO_MES_ULTIMO_EXAMEN, registro.getMes_ultimo_examen());
        values.put(Util_RegistroFormatos.CAMPO_ANIO_ULTIMO_EXAMEN, registro.getAnio_ultimo_examen());
        values.put(Util_RegistroFormatos.CAMPO_CTRL_INGENIERIA, registro.getCtrl_ingenieria());
        values.put(Util_RegistroFormatos.CAMPO_CTRL_ADMINISTRATIVO, registro.getCtrl_administrativo());
        values.put(Util_RegistroFormatos.CAMPO_NOM_EPP, registro.getNom_epp());
        values.put(Util_RegistroFormatos.CAMPO_PROT_AUDITIVO, registro.getProt_auditivo());
        values.put(Util_RegistroFormatos.CAMPO_NOM_CTRL_INGENIERIA, registro.getNom_ctrl_ingenieria());
        values.put(Util_RegistroFormatos.CAMPO_NOM_CTRL_ADMIN, registro.getNom_ctrl_admin());
        values.put(Util_RegistroFormatos.CAMPO_AISLAMIENTO, registro.getAislamiento());
        values.put(Util_RegistroFormatos.CAMPO_TECHOS, registro.getTechos());
        values.put(Util_RegistroFormatos.CAMPO_CABINAS, registro.getCabinas());
        values.put(Util_RegistroFormatos.CAMPO_ORIENTACION, registro.getOrientacion());
        values.put(Util_RegistroFormatos.CAMPO_CERRAMIENTO, registro.getCerramiento());
        values.put(Util_RegistroFormatos.CAMPO_OTRO_INGENIERIA, registro.getOtro_ingenieria());
        values.put(Util_RegistroFormatos.CAMPO_CAPACITACION, registro.getCapacitacion());
        values.put(Util_RegistroFormatos.CAMPO_SENIALIZACION_PRECION, registro.getSenializacion_precion());
        values.put(Util_RegistroFormatos.CAMPO_SENIALIZACION_EPP, registro.getSenializacion_epp());
        values.put(Util_RegistroFormatos.CAMPO_SENIALIZACION_VIBRACION, registro.getSenializacion_vibracion());
        values.put(Util_RegistroFormatos.CAMPO_SENIALIZACION_AREA, registro.getSenializacion_area());
        values.put(Util_RegistroFormatos.CAMPO_MANTENIMIENTO_VIBRACION, registro.getMantenimiento_vibracion());
        values.put(Util_RegistroFormatos.CAMPO_ROTACION, registro.getRotacion());
        values.put(Util_RegistroFormatos.CAMPO_AISLANTE_DETALLE, registro.getAislante_detalle());
        values.put(Util_RegistroFormatos.CAMPO_CABINA_DETALLE, registro.getCabina_detalle());
        values.put(Util_RegistroFormatos.CAMPO_CAPACITACION_DETALLE, registro.getCapacitacion_detalle());
        values.put(Util_RegistroFormatos.CAMPO_SENIALIZACION_NIVEL_DETALLE, registro.getSenializacion_nivel_detalle());
        values.put(Util_RegistroFormatos.CAMPO_SENIALIZACION_EPP_DETALLE, registro.getSenializacion_epp_detalle());
        values.put(Util_RegistroFormatos.CAMPO_ROTACION_DETALLE, registro.getRotacion_detalle());
        values.put(Util_RegistroFormatos.CAMPO_TIEMPO_EXPOSICION_DETALLE, registro.getTiempo_exposicion_detalle());
        values.put(Util_RegistroFormatos.CAMPO_TAPONES_AU, registro.getTapones_au());
        values.put(Util_RegistroFormatos.CAMPO_MARCA_TAPONES_AUDI, registro.getMarca_tapones_audi());
        values.put(Util_RegistroFormatos.CAMPO_MODELO_TAPONES_AUDI, registro.getModelo_tapones_audi());
        values.put(Util_RegistroFormatos.CAMPO_OREJERAS, registro.getOrejereas());
        values.put(Util_RegistroFormatos.CAMPO_MARCA_OREJERAS, registro.getMarca_orejeras());
        values.put(Util_RegistroFormatos.CAMPO_MODELO_OREJERAS, registro.getModelo_orejeras());
        values.put(Util_RegistroFormatos.CAMPO_NRR_OREJERAS, registro.getNrr_orejeras());
        values.put(Util_RegistroFormatos.CAMPO_NRR_TAPONES_AUDI, registro.getNrr_tapones_audi());
        values.put(Util_RegistroFormatos.CAMPO_ADM_TIEMPO_EXPO, registro.getAdm_tiempo_expo());
        values.put(Util_RegistroFormatos.CAMPO_TIEMPO_EXPOSICION, registro.getTiempo_exposicion());
        values.put(Util_RegistroFormatos.CAMPO_OTRO_ADMINISTRATIVO, registro.getOtro_administrativo());
        values.put(Util_RegistroFormatos.CAMPO_TAREAS_MEDICION, registro.getTareas_medicion());
        values.put(Util_RegistroFormatos.CAMPO_TAREA_VISUAL, registro.getTarea_visual());
        values.put(Util_RegistroFormatos.CAMPO_TIPO_TAREA_VISUAL, registro.getTipo_tarea_visual());
        values.put(Util_RegistroFormatos.CAMPO_NIVEL_MIN_ILU, registro.getNivel_min_ilu());
        values.put(Util_RegistroFormatos.CAMPO_OBSERVACION, registro.getObservacion());
        values.put(Util_RegistroFormatos.CAMPO_CH_RUIDO_EXTERNO, registro.getCh_ruido_externo());
        values.put(Util_RegistroFormatos.CAMPO_CH_RUIDO_ANTIGUO, registro.getCh_ruido_antiguo());
        values.put(Util_RegistroFormatos.CAMPO_CH_RUIDO_GENERADO_POR, registro.getCh_ruido_generado_por());
        values.put(Util_RegistroFormatos.CAMPO_RUIDO_GENERADO_POR, registro.getRuido_generado_por());
        values.put(Util_RegistroFormatos.CAMPO_OTRO_RUIDO, registro.getOtro_ruido());
        values.put(Util_RegistroFormatos.CAMPO_AREA_REQ_CONCENTR, registro.getArea_req_concentr());
        values.put(Util_RegistroFormatos.CAMPO_LIM_MAX_PERMIS, registro.getLim_max_permis());
        values.put(Util_RegistroFormatos.CAMPO_TIPO_MEDICION, registro.getTipo_medicion());
        values.put(Util_RegistroFormatos.CAMPO_NOM_TIPO_MEDICION, registro.getNom_tipo_medicion());
        values.put(Util_RegistroFormatos.CAMPO_LMIN, registro.getLmin());
        values.put(Util_RegistroFormatos.CAMPO_LMAX, registro.getLmax());
        values.put(Util_RegistroFormatos.CAMPO_LEQUI, registro.getLequi());
        // ... (otros campos)
        values.put(Util_RegistroFormatos.CAMPO_LEQUI_MD1, registro.getLequi_md1());
        values.put(Util_RegistroFormatos.CAMPO_LEQUI_MD2, registro.getLequi_md2());
        values.put(Util_RegistroFormatos.CAMPO_LEQUI_MD3, registro.getLequi_md3());
        values.put(Util_RegistroFormatos.CAMPO_LEQUI_MD4, registro.getLequi_md4());
        values.put(Util_RegistroFormatos.CAMPO_LEQUI_MD5, registro.getLequi_md5());
        values.put(Util_RegistroFormatos.CAMPO_LMIN_MD1, registro.getLmin_md1());
        values.put(Util_RegistroFormatos.CAMPO_LMIN_MD2, registro.getLmin_md2());
        values.put(Util_RegistroFormatos.CAMPO_LMIN_MD3, registro.getLmin_md3());
        values.put(Util_RegistroFormatos.CAMPO_LMIN_MD4, registro.getLmin_md4());
        values.put(Util_RegistroFormatos.CAMPO_LMIN_MD5, registro.getLmin_md5());
        values.put(Util_RegistroFormatos.CAMPO_LMAX_MD1, registro.getLmax_md1());
        values.put(Util_RegistroFormatos.CAMPO_LMAX_MD2, registro.getLmax_md2());
        values.put(Util_RegistroFormatos.CAMPO_LMAX_MD3, registro.getLmax_md3());
        values.put(Util_RegistroFormatos.CAMPO_LMAX_MD4, registro.getLmax_md4());
        values.put(Util_RegistroFormatos.CAMPO_LMAX_MD5, registro.getLmax_md5());

        values.put(Util_RegistroFormatos.CAMPO_LPICO_DBA, registro.getLpico_dba());
        values.put(Util_RegistroFormatos.CAMPO_CONFORMIDAD, registro.getConformidad());
        values.put(Util_RegistroFormatos.CAMPO_EVAL_20P, registro.getEval_20p());
        values.put(Util_RegistroFormatos.CAMPO_TIPO_VIBRACION, registro.getTipo_vibracion());
        values.put(Util_RegistroFormatos.CAMPO_LATERALIDAD_MANO, registro.getLateralidad_mano());
        values.put(Util_RegistroFormatos.CAMPO_UBIC_EQUIP, registro.getUbic_equip());
        values.put(Util_RegistroFormatos.CAMPO_ANIO_OCU_CARGO, registro.getAnio_ocu_cargo());
        values.put(Util_RegistroFormatos.CAMPO_MES_OCU_CARGO, registro.getMes_ocu_cargo());
        values.put(Util_RegistroFormatos.CAMPO_COND_TRAB, registro.getCond_trab());
        values.put(Util_RegistroFormatos.CAMPO_PORC_DESCAN, registro.getPorc_descan());
        values.put(Util_RegistroFormatos.CAMPO_FOTO, registro.getFoto());
        values.put(Util_RegistroFormatos.CAMPO_RESULTADO, registro.getResultado());
        values.put(Util_RegistroFormatos.CAMPO_ESTADO_RESULTADO, registro.getEstado_resultado());
        values.put(Util_RegistroFormatos.CAMPO_ESTADO, registro.getEstado());
        values.put(Util_RegistroFormatos.CAMPO_FEC_REG, registro.getFec_reg());
        values.put(Util_RegistroFormatos.CAMPO_USER_REG, registro.getUser_reg());
        values.put(Util_RegistroFormatos.CAMPO_FEC_ACT, registro.getFec_act());
        values.put(Util_RegistroFormatos.CAMPO_USER_ACT, registro.getUser_act());
        values.put(Util_RegistroFormatos.CAMPO_FEC_ELI, registro.getFec_eli());
        values.put(Util_RegistroFormatos.CAMPO_USER_ELI, registro.getUser_eli());

        Long idResultante = db.insert(Util_RegistroFormatos.TABLA_REGISTRO_FORMATOS, Util_RegistroFormatos.CAMPO_ID_PLAN_TRABAJO_FORMATO_REG,values);

        db.close();
    }



    /*public List<String> obtener_CodEquipos(){
        List<String> codigosList = new ArrayList<>();
        SQLiteDatabase db = dataHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT "+ Util_RegistroFormatos.CAMPO_CODIGO +" FROM "+ Util_RegistroFormatos.TABLA_EQUIPOS, null);

        if(cursor.moveToFirst()){
            do{
                codigosList.add(cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_CODIGO)));
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return codigosList;
    }*/

    public RegistroFormatos Buscar(String id_formato_reg){
        RegistroFormatos registro = null;
        SQLiteDatabase db = dataHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Util_RegistroFormatos.TABLA_REGISTRO_FORMATOS + " WHERE id_plan_trabajo_formato_reg = ?", new String[]{id_formato_reg});

        if(cursor.moveToFirst()){
            do{
                registro = new RegistroFormatos(
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_ID_PLAN_TRABAJO_FORMATO_REG)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_ID_FORMATO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_COD_REGISTRO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_COD_FORMATO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_ID_PLAN_TRABAJO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_ID_PT_FORMATO)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_ID_ANALISTA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_NOM_ANALISTA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_COD_EQUIPO1)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_NOM_EQUIPO1)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_COD_EQUIPO2)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_NOM_EQUIPO2)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_COD_EQUIPO3)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_NOM_EQUIPO3)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_SERIE_EQ1)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_SERIE_EQ2)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_SERIE_EQ3)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_ID_EQUIPO1)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_ID_EQUIPO2)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_ID_EQUIPO3)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_VERF_INSITU)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_HORA_SITU)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_NIVEL)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_VARIACION)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_LUZ)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_AREA_TRABAJO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_DESC_AREA_TRABAJO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_AREA_TRAB_DETA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_ACTIVIDADES_REALIZADAS)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_ID_HORARIO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_HORA_TRABAJO)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_N_PERSONAS)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_FEC_MONITOREO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_HORA_INICIAL)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_TIEMPO_MEDICION)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_HORA_FINAL)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_V_VIENTO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_H_RELATIVA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_HORA_JORNADA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_MIN_JORNADA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_HORA_EXPOSICION)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_MIN_EXPOSICION)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_JORNADA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_NOM_TRABAJADOR)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_TIPO_DOC_TRABAJADOR)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_NUM_DOC_TRABAJADOR)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_PUESTO_TRABAJADOR)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_EDAD_TRABAJADOR)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_PESO_TRABAJADOR)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_REGIMEN_LABORAL)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_HORARIO_REFRIGERIO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_TIEMPO_OCUPADO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_MOLESTIA_OIDO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_ENFERMEDAD_OIDO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_DETALLE_ENF_OIDO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_FECHA_ULTIMO_EXAMEN)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_MES_ULTIMO_EXAMEN)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_ANIO_ULTIMO_EXAMEN)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_CTRL_INGENIERIA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_CTRL_ADMINISTRATIVO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_NOM_EPP)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_PROT_AUDITIVO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_NOM_CTRL_INGENIERIA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_NOM_CTRL_ADMIN)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_AISLAMIENTO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_TECHOS)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_CABINAS)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_ORIENTACION)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_CERRAMIENTO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_OTRO_INGENIERIA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_CAPACITACION)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_SENIALIZACION_PRECION)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_SENIALIZACION_EPP)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_SENIALIZACION_VIBRACION)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_SENIALIZACION_AREA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_MANTENIMIENTO_VIBRACION)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_ROTACION)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_AISLANTE_DETALLE)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_CABINA_DETALLE)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_CAPACITACION_DETALLE)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_SENIALIZACION_NIVEL_DETALLE)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_SENIALIZACION_EPP_DETALLE)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_ROTACION_DETALLE)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_TIEMPO_EXPOSICION_DETALLE)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_TAPONES_AU)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_MARCA_TAPONES_AUDI)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_MODELO_TAPONES_AUDI)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_OREJERAS)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_MARCA_OREJERAS)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_MODELO_OREJERAS)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_NRR_OREJERAS)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_NRR_TAPONES_AUDI)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_ADM_TIEMPO_EXPO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_TIEMPO_EXPOSICION)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_OTRO_ADMINISTRATIVO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_TAREAS_MEDICION)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_TAREA_VISUAL)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_TIPO_TAREA_VISUAL)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_NIVEL_MIN_ILU)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_OBSERVACION)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_CH_RUIDO_EXTERNO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_CH_RUIDO_ANTIGUO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_CH_RUIDO_GENERADO_POR)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_RUIDO_GENERADO_POR)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_OTRO_RUIDO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_AREA_REQ_CONCENTR)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_LIM_MAX_PERMIS)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_TIPO_MEDICION)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_NOM_TIPO_MEDICION)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_LMIN)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_LMAX)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_LEQUI)),
// ... campos adicionales ...
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_LEQUI_MD1)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_LEQUI_MD2)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_LEQUI_MD3)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_LEQUI_MD4)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_LEQUI_MD5)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_LMAX_MD1)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_LMAX_MD2)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_LMAX_MD3)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_LMAX_MD4)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_LMAX_MD5)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_LMIN_MD1)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_LMIN_MD2)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_LMIN_MD3)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_LMIN_MD4)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_LMIN_MD5)),

                        cursor.getDouble(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_LPICO_DBA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_CONFORMIDAD)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_EVAL_20P)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_TIPO_VIBRACION)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_LATERALIDAD_MANO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_UBIC_EQUIP)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_ANIO_OCU_CARGO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_MES_OCU_CARGO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_COND_TRAB)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_PORC_DESCAN)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_FOTO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_RESULTADO)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_ESTADO_RESULTADO)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_ESTADO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_FEC_REG)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_USER_REG)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_FEC_ACT)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_USER_ACT)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_FEC_ELI)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos.CAMPO_USER_ELI))

                );
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return registro;
    }
}
