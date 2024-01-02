package com.mijael.mein.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.mijael.mein.Entidades.Sonometria_Registro;
import com.mijael.mein.HELPER.RegistroFormatosSQLiteHelper;
import com.mijael.mein.Utilidades.Util_DosimetriaRegistro;
import com.mijael.mein.Utilidades.Util_SonometriaRegistro;

public class DAO_RegistroSonometria {
    private RegistroFormatosSQLiteHelper dbHelper;

    public DAO_RegistroSonometria(Context context) {
        this.dbHelper = RegistroFormatosSQLiteHelper.getInstance(context);
    }

    public boolean RegistroSonometria(Sonometria_Registro registro){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util_SonometriaRegistro.CAMPO_COD_FORMATO, registro.getCod_formato());
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
        values.put(Util_SonometriaRegistro.CAMPO_USER_REG, registro.getUser_reg());

        db.insert(Util_SonometriaRegistro.TABLA_SONOMETRIA,null,values);
        db.close();
        return true;
    }
}