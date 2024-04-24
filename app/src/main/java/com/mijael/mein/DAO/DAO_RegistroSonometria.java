package com.mijael.mein.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.mijael.mein.Entidades.Sonometria_Registro;
import com.mijael.mein.HELPER.MeinSQLiteHelper;
import com.mijael.mein.Utilidades.Util_RegistroFormatos;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DAO_RegistroSonometria {
    private MeinSQLiteHelper dbHelper;

    public DAO_RegistroSonometria(Context context) {
        this.dbHelper = MeinSQLiteHelper.getInstance(context);
    }

    public boolean RegistroSonometria(Sonometria_Registro registro){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Util_RegistroFormatos.CAMPO_COD_FORMATO, registro.getCod_formato());
        values.put(Util_RegistroFormatos.CAMPO_COD_REGISTRO, registro.getCod_registro());
        values.put(Util_RegistroFormatos.CAMPO_ID_FORMATO, registro.getId_formato());
        values.put(Util_RegistroFormatos.CAMPO_ID_PLAN_TRABAJO, registro.getId_plan_trabajo());
        values.put(Util_RegistroFormatos.CAMPO_ID_PT_FORMATO, registro.getId_pt_formato());
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
        values.put(Util_RegistroFormatos.CAMPO_ID_ANALISTA, registro.getId_analista());
        values.put(Util_RegistroFormatos.CAMPO_NOM_ANALISTA, registro.getNom_analista());
        values.put(Util_RegistroFormatos.CAMPO_HORA_SITU, registro.getHora_situ());
        values.put(Util_RegistroFormatos.CAMPO_NIVEL, registro.getNivel());
        values.put(Util_RegistroFormatos.CAMPO_VARIACION, registro.getVariacion());
        values.put(Util_RegistroFormatos.CAMPO_AREA_TRABAJO, registro.getArea_trabajo());
        values.put(Util_RegistroFormatos.CAMPO_ACTIVIDADES_REALIZADAS, registro.getActividades_realizadas());
        values.put(Util_RegistroFormatos.CAMPO_ID_HORARIO, registro.getId_horario());
        values.put(Util_RegistroFormatos.CAMPO_HORA_TRABAJO, registro.getHora_trabajo());
        values.put(Util_RegistroFormatos.CAMPO_N_PERSONAS, registro.getN_personas());
        values.put(Util_RegistroFormatos.CAMPO_RUIDO_GENERADO_POR, registro.getRuido_generado_por());
        values.put(Util_RegistroFormatos.CAMPO_AREA_REQ_CONCENTR, registro.getArea_req_concentr());
        values.put(Util_RegistroFormatos.CAMPO_LIM_MAX_PERMIS, registro.getLim_max_permis());
        values.put(Util_RegistroFormatos.CAMPO_FEC_MONITOREO, registro.getFec_monitoreo());
        values.put(Util_RegistroFormatos.CAMPO_HORA_INICIAL, registro.getHora_inicial());
        values.put(Util_RegistroFormatos.CAMPO_TIEMPO_MEDICION, registro.getTiempo_medicion());
        values.put(Util_RegistroFormatos.CAMPO_HORA_FINAL, registro.getHora_final());
        values.put(Util_RegistroFormatos.CAMPO_V_VIENTO, registro.getV_viento());
        values.put(Util_RegistroFormatos.CAMPO_H_RELATIVA, registro.getH_relativa());
        values.put(Util_RegistroFormatos.CAMPO_LMIN, registro.getLmin());
        values.put(Util_RegistroFormatos.CAMPO_LMAX, registro.getLmax());
        values.put(Util_RegistroFormatos.CAMPO_LEQUI, registro.getLequi());

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

        values.put(Util_RegistroFormatos.CAMPO_CTRL_INGENIERIA, registro.getCtrl_ingenieria());
        values.put(Util_RegistroFormatos.CAMPO_CTRL_ADMINISTRATIVO, registro.getCtrl_administrativo());
        values.put(Util_RegistroFormatos.CAMPO_AISLAMIENTO, registro.getAislamiento());
        values.put(Util_RegistroFormatos.CAMPO_CABINAS, registro.getCabinas());
        values.put(Util_RegistroFormatos.CAMPO_OTRO_INGENIERIA, registro.getOtro_ingenieria());
        values.put(Util_RegistroFormatos.CAMPO_CAPACITACION, registro.getCapacitacion());
        values.put(Util_RegistroFormatos.CAMPO_SENIALIZACION_PRECION, registro.getSenializacion_precion());
        values.put(Util_RegistroFormatos.CAMPO_SENIALIZACION_EPP, registro.getSenializacion_epp());
        values.put(Util_RegistroFormatos.CAMPO_ROTACION, registro.getRotacion());
        values.put(Util_RegistroFormatos.CAMPO_TIEMPO_EXPOSICION, registro.getTiempo_exposicion());
        values.put(Util_RegistroFormatos.CAMPO_OTRO_ADMINISTRATIVO, registro.getOtro_administrativo());
        values.put(Util_RegistroFormatos.CAMPO_OBSERVACION, registro.getObservacion());
        values.put(Util_RegistroFormatos.CAMPO_TAPONES_AU, registro.getTapones_au());
        values.put(Util_RegistroFormatos.CAMPO_MARCA_TAPONES_AUDI, registro.getMarca_tapones_audi());
        values.put(Util_RegistroFormatos.CAMPO_MODELO_TAPONES_AUDI, registro.getModelo_tapones_audi());
        values.put(Util_RegistroFormatos.CAMPO_NRR_TAPONES_AUDI, registro.getNrr_tapones_audi());
        values.put(Util_RegistroFormatos.CAMPO_OREJERAS, registro.getOrejereas());
        values.put(Util_RegistroFormatos.CAMPO_MARCA_OREJERAS, registro.getMarca_orejeras());
        values.put(Util_RegistroFormatos.CAMPO_MODELO_OREJERAS, registro.getModelo_orejeras());
        values.put(Util_RegistroFormatos.CAMPO_NRR_OREJERAS, registro.getNrr_orejeras());
        values.put(Util_RegistroFormatos.CAMPO_JORNADA, registro.getJornada());
        values.put(Util_RegistroFormatos.CAMPO_RUTA_FOTO, registro.getRuta_foto());
        values.put(Util_RegistroFormatos.CAMPO_ESTADO, registro.getEstado());
        values.put(Util_RegistroFormatos.CAMPO_FEC_REG, registro.getFec_reg());
        values.put(Util_RegistroFormatos.CAMPO_USER_REG, registro.getUser_reg());
        values.put(Util_RegistroFormatos.CAMPO_ESTADO_SINCRO,1);// MARCANDO PARA SABER QUE FALTA SINCRONIZAR

        /*values.put(Util_SonometriaRegistro.CAMPO_COD_FORMATO, registro.getCod_formato());
        values.put(Util_SonometriaRegistro.CAMPO_ID_FORMATO, registro.getId_formato());
        values.put(Util_SonometriaRegistro.CAMPO_ID_PLAN_TRABAJO, registro.getId_plan_trabajo());
        values.put(Util_SonometriaRegistro.CAMPO_ID_PT_FORMATO, registro.getId_pt_formato());
        values.put(Util_SonometriaRegistro.CAMPO_COD_EQUIPO1, registro.getCod_equipo1());
        values.put(Util_SonometriaRegistro.CAMPO_NOM_EQUIPO1, registro.getNom_equipo1());
        values.put(Util_SonometriaRegistro.CAMPO_COD_EQUIPO2, registro.getCod_equipo2());
        values.put(Util_SonometriaRegistro.CAMPO_NOM_EQUIPO2, registro.getNom_equipo2());
        values.put(Util_SonometriaRegistro.CAMPO_COD_EQUIPO3, registro.getCod_equipo3());
        values.put(Util_SonometriaRegistro.CAMPO_NOM_EQUIPO3, registro.getNom_equipo3());
        values.put(Util_SonometriaRegistro.CAMPO_SERIE_EQ1, registro.getSerie_eq1());
        values.put(Util_SonometriaRegistro.CAMPO_SERIE_EQ2, registro.getSerie_eq2());
        values.put(Util_SonometriaRegistro.CAMPO_SERIE_EQ3, registro.getSerie_eq3());
        values.put(Util_SonometriaRegistro.CAMPO_ID_EQUIPO1, registro.getId_equipo1());
        values.put(Util_SonometriaRegistro.CAMPO_ID_EQUIPO2, registro.getId_equipo2());
        values.put(Util_SonometriaRegistro.CAMPO_ID_EQUIPO3, registro.getId_equipo3());
        values.put(Util_SonometriaRegistro.CAMPO_ID_ANALISTA, registro.getId_analista());
        values.put(Util_SonometriaRegistro.CAMPO_NOM_ANALISTA, registro.getNom_analista());
        values.put(Util_SonometriaRegistro.CAMPO_HORA_SITU, registro.getHora_situ());
        values.put(Util_SonometriaRegistro.CAMPO_NIVEL, registro.getNivel());
        values.put(Util_SonometriaRegistro.CAMPO_VARIACION, registro.getVariacion());
        values.put(Util_SonometriaRegistro.CAMPO_AREA_TRABAJO, registro.getArea_trabajo());
        values.put(Util_SonometriaRegistro.CAMPO_ACTIVIDADES_REALIZADAS, registro.getActividades_realizadas());
        values.put(Util_SonometriaRegistro.CAMPO_ID_HORARIO, registro.getId_horario());
        values.put(Util_SonometriaRegistro.CAMPO_HORA_TRABAJO, registro.getHora_trabajo());
        values.put(Util_SonometriaRegistro.CAMPO_N_PERSONAS, registro.getN_personas());
        values.put(Util_SonometriaRegistro.CAMPO_RUIDO_GENERADO_POR, registro.getRuido_generado_por());
        values.put(Util_SonometriaRegistro.CAMPO_AREA_REQ_CONCENTR, registro.getArea_req_concentr());
        values.put(Util_SonometriaRegistro.CAMPO_LIM_MAX_PERMIS, registro.getLim_max_permis());
        values.put(Util_SonometriaRegistro.CAMPO_FEC_MONITOREO, registro.getFec_monitoreo());
        values.put(Util_SonometriaRegistro.CAMPO_HORA_INICIAL, registro.getHora_inicial());
        values.put(Util_SonometriaRegistro.CAMPO_TIEMPO_MEDICION, registro.getTiempo_medicion());
        values.put(Util_SonometriaRegistro.CAMPO_HORA_FINAL, registro.getHora_final());
        values.put(Util_SonometriaRegistro.CAMPO_V_VIENTO, registro.getV_viento());
        values.put(Util_SonometriaRegistro.CAMPO_H_RELATIVA, registro.getH_relativa());
        values.put(Util_SonometriaRegistro.CAMPO_LMIN, registro.getLmin());
        values.put(Util_SonometriaRegistro.CAMPO_LMAX, registro.getLmax());
        values.put(Util_SonometriaRegistro.CAMPO_LEQUI, registro.getLequi());
        values.put(Util_SonometriaRegistro.CAMPO_CTRL_INGENIERIA, registro.getCtrl_ingenieria());
        values.put(Util_SonometriaRegistro.CAMPO_CTRL_ADMINISTRATIVO, registro.getCtrl_administrativo());
        values.put(Util_SonometriaRegistro.CAMPO_AISLAMIENTO, registro.getAislamiento());
        values.put(Util_SonometriaRegistro.CAMPO_CABINAS, registro.getCabinas());
        values.put(Util_SonometriaRegistro.CAMPO_OTRO_INGENIERIA, registro.getOtro_ingenieria());
        values.put(Util_SonometriaRegistro.CAMPO_CAPACITACION, registro.getCapacitacion());
        values.put(Util_SonometriaRegistro.CAMPO_SENIALIZACION_PRECION, registro.getSenializacion_precion());
        values.put(Util_SonometriaRegistro.CAMPO_SENIALIZACION_EPP, registro.getSenializacion_epp());
        values.put(Util_SonometriaRegistro.CAMPO_ROTACION, registro.getRotacion());
        values.put(Util_SonometriaRegistro.CAMPO_TIEMPO_EXPOSICION, registro.getTiempo_exposicion());
        values.put(Util_SonometriaRegistro.CAMPO_OTRO_ADMINISTRATIVO, registro.getOtro_administrativo());
        values.put(Util_SonometriaRegistro.CAMPO_OBSERVACION, registro.getObservacion());
        values.put(Util_SonometriaRegistro.CAMPO_TAPONES_AU, registro.getTapones_au());
        values.put(Util_SonometriaRegistro.CAMPO_MARCA_TAPONES_AUDI, registro.getMarca_tapones_audi());
        values.put(Util_SonometriaRegistro.CAMPO_MODELO_TAPONES_AUDI, registro.getModelo_tapones_audi());
        values.put(Util_SonometriaRegistro.CAMPO_NRR_TAPONES_AUDI, registro.getNrr_tapones_audi());
        values.put(Util_SonometriaRegistro.CAMPO_OREJERAS, registro.getOrejereas());
        values.put(Util_SonometriaRegistro.CAMPO_MARCA_OREJERAS, registro.getMarca_orejeras());
        values.put(Util_SonometriaRegistro.CAMPO_MODELO_OREJERAS, registro.getModelo_orejeras());
        values.put(Util_SonometriaRegistro.CAMPO_NRR_OREJERAS, registro.getNrr_orejeras());
        values.put(Util_SonometriaRegistro.CAMPO_JORNADA, registro.getJornada());
        values.put(Util_SonometriaRegistro.CAMPO_ESTADO, registro.getEstado());
        values.put(Util_SonometriaRegistro.CAMPO_FEC_REG, registro.getFec_reg());
        values.put(Util_SonometriaRegistro.CAMPO_USER_REG, registro.getUser_reg());*/

        db.insert(Util_RegistroFormatos.TABLA_REGISTRO_FORMATOS,null,values);
        db.close();
        return true;
    }

    public boolean ActualizarSonometria(Sonometria_Registro registro, int id_plan_trabajo_formato_reg){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fecha = dateFormat.format(new Date());

        values.put(Util_RegistroFormatos.CAMPO_COD_FORMATO, registro.getCod_formato());
        values.put(Util_RegistroFormatos.CAMPO_COD_REGISTRO, registro.getCod_registro());
        values.put(Util_RegistroFormatos.CAMPO_ID_FORMATO, registro.getId_formato());
        values.put(Util_RegistroFormatos.CAMPO_ID_PLAN_TRABAJO, registro.getId_plan_trabajo());
        values.put(Util_RegistroFormatos.CAMPO_ID_PT_FORMATO, registro.getId_pt_formato());
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
        values.put(Util_RegistroFormatos.CAMPO_ID_ANALISTA, registro.getId_analista());
        values.put(Util_RegistroFormatos.CAMPO_NOM_ANALISTA, registro.getNom_analista());
        values.put(Util_RegistroFormatos.CAMPO_HORA_SITU, registro.getHora_situ());
        values.put(Util_RegistroFormatos.CAMPO_NIVEL, registro.getNivel());
        values.put(Util_RegistroFormatos.CAMPO_VARIACION, registro.getVariacion());
        values.put(Util_RegistroFormatos.CAMPO_AREA_TRABAJO, registro.getArea_trabajo());
        values.put(Util_RegistroFormatos.CAMPO_ACTIVIDADES_REALIZADAS, registro.getActividades_realizadas());
        values.put(Util_RegistroFormatos.CAMPO_ID_HORARIO, registro.getId_horario());
        values.put(Util_RegistroFormatos.CAMPO_HORA_TRABAJO, registro.getHora_trabajo());
        values.put(Util_RegistroFormatos.CAMPO_N_PERSONAS, registro.getN_personas());
        values.put(Util_RegistroFormatos.CAMPO_RUIDO_GENERADO_POR, registro.getRuido_generado_por());
        values.put(Util_RegistroFormatos.CAMPO_AREA_REQ_CONCENTR, registro.getArea_req_concentr());
        values.put(Util_RegistroFormatos.CAMPO_LIM_MAX_PERMIS, registro.getLim_max_permis());
        values.put(Util_RegistroFormatos.CAMPO_FEC_MONITOREO, registro.getFec_monitoreo());
        values.put(Util_RegistroFormatos.CAMPO_HORA_INICIAL, registro.getHora_inicial());
        values.put(Util_RegistroFormatos.CAMPO_TIEMPO_MEDICION, registro.getTiempo_medicion());
        values.put(Util_RegistroFormatos.CAMPO_HORA_FINAL, registro.getHora_final());
        values.put(Util_RegistroFormatos.CAMPO_V_VIENTO, registro.getV_viento());
        values.put(Util_RegistroFormatos.CAMPO_H_RELATIVA, registro.getH_relativa());
        values.put(Util_RegistroFormatos.CAMPO_LMIN, registro.getLmin());
        values.put(Util_RegistroFormatos.CAMPO_LMAX, registro.getLmax());
        values.put(Util_RegistroFormatos.CAMPO_LEQUI, registro.getLequi());

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

        values.put(Util_RegistroFormatos.CAMPO_CTRL_INGENIERIA, registro.getCtrl_ingenieria());
        values.put(Util_RegistroFormatos.CAMPO_CTRL_ADMINISTRATIVO, registro.getCtrl_administrativo());
        values.put(Util_RegistroFormatos.CAMPO_AISLAMIENTO, registro.getAislamiento());
        values.put(Util_RegistroFormatos.CAMPO_CABINAS, registro.getCabinas());
        values.put(Util_RegistroFormatos.CAMPO_OTRO_INGENIERIA, registro.getOtro_ingenieria());
        values.put(Util_RegistroFormatos.CAMPO_CAPACITACION, registro.getCapacitacion());
        values.put(Util_RegistroFormatos.CAMPO_SENIALIZACION_PRECION, registro.getSenializacion_precion());
        values.put(Util_RegistroFormatos.CAMPO_SENIALIZACION_EPP, registro.getSenializacion_epp());
        values.put(Util_RegistroFormatos.CAMPO_ROTACION, registro.getRotacion());
        values.put(Util_RegistroFormatos.CAMPO_TIEMPO_EXPOSICION, registro.getTiempo_exposicion());
        values.put(Util_RegistroFormatos.CAMPO_OTRO_ADMINISTRATIVO, registro.getOtro_administrativo());
        values.put(Util_RegistroFormatos.CAMPO_OBSERVACION, registro.getObservacion());
        values.put(Util_RegistroFormatos.CAMPO_TAPONES_AU, registro.getTapones_au());
        values.put(Util_RegistroFormatos.CAMPO_MARCA_TAPONES_AUDI, registro.getMarca_tapones_audi());
        values.put(Util_RegistroFormatos.CAMPO_MODELO_TAPONES_AUDI, registro.getModelo_tapones_audi());
        values.put(Util_RegistroFormatos.CAMPO_NRR_TAPONES_AUDI, registro.getNrr_tapones_audi());
        values.put(Util_RegistroFormatos.CAMPO_OREJERAS, registro.getOrejereas());
        values.put(Util_RegistroFormatos.CAMPO_MARCA_OREJERAS, registro.getMarca_orejeras());
        values.put(Util_RegistroFormatos.CAMPO_MODELO_OREJERAS, registro.getModelo_orejeras());
        values.put(Util_RegistroFormatos.CAMPO_NRR_OREJERAS, registro.getNrr_orejeras());
        values.put(Util_RegistroFormatos.CAMPO_JORNADA, registro.getJornada());
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
        db.close();
        return true;
    }
}
