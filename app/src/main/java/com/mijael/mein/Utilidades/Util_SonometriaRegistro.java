package com.mijael.mein.Utilidades;

public class Util_SonometriaRegistro {
    public static final String TABLA_SONOMETRIA = "tb_sonometria";
    public static final String CAMPO_ID = "ID_Sonometria";
    public static final String CAMPO_COD_FORMATO = "COD_Formato";
    public static final String CAMPO_ID_FORMATO = "id_formato";
    public static final String CAMPO_ID_PLAN_TRABAJO = "id_plan_trabajo";
    public static final String CAMPO_ID_PT_FORMATO = "id_pt_formato";
    public static final String CAMPO_COD_EQUIPO1 = "cod_equipo1";
    public static final String CAMPO_NOM_EQUIPO1 = "nom_equipo1";
    public static final String CAMPO_COD_EQUIPO2 = "cod_equipo2";
    public static final String CAMPO_NOM_EQUIPO2 = "nom_equipo2";
    public static final String CAMPO_COD_EQUIPO3 = "cod_equipo3";
    public static final String CAMPO_NOM_EQUIPO3 = "nom_equipo3";
    public static final String CAMPO_SERIE_EQ1 = "serie_eq1";
    public static final String CAMPO_SERIE_EQ2 = "serie_eq2";
    public static final String CAMPO_SERIE_EQ3 = "serie_eq3";
    public static final String CAMPO_ID_EQUIPO1 = "id_equipo1";
    public static final String CAMPO_ID_EQUIPO2 = "id_equipo2";
    public static final String CAMPO_ID_EQUIPO3 = "id_equipo3";
    public static final String CAMPO_ID_ANALISTA = "id_analista";
    public static final String CAMPO_NOM_ANALISTA = "nom_analista";
    public static final String CAMPO_HORA_SITU = "hora_situ";
    public static final String CAMPO_NIVEL = "nivel";
    public static final String CAMPO_VARIACION = "variacion";
    public static final String CAMPO_AREA_TRABAJO = "area_trabajo";
    public static final String CAMPO_ACTIVIDADES_REALIZADAS = "actividades_realizadas";
    public static final String CAMPO_ID_HORARIO = "id_horario";
    public static final String CAMPO_HORA_TRABAJO = "hora_trabajo";
    public static final String CAMPO_N_PERSONAS = "n_personas";
    public static final String CAMPO_RUIDO_GENERADO_POR = "ruido_generado_por";
    public static final String CAMPO_AREA_REQ_CONCENTR = "area_req_concentr";
    public static final String CAMPO_LIM_MAX_PERMIS = "lim_max_permis";
    public static final String CAMPO_FEC_MONITOREO = "fec_monitoreo";
    public static final String CAMPO_HORA_INICIAL = "hora_inicial";
    public static final String CAMPO_TIEMPO_MEDICION = "tiempo_medicion";
    public static final String CAMPO_HORA_FINAL = "hora_final";
    public static final String CAMPO_V_VIENTO = "v_viento";
    public static final String CAMPO_H_RELATIVA = "h_relativa";
    public static final String CAMPO_LMIN = "lmin";
    public static final String CAMPO_LMAX = "lmax";
    public static final String CAMPO_LEQUI = "lequi";
    public static final String CAMPO_CTRL_INGENIERIA = "ctrl_ingenieria";
    public static final String CAMPO_CTRL_ADMINISTRATIVO = "ctrl_administrativo";
    public static final String CAMPO_AISLAMIENTO = "aislamiento";
    public static final String CAMPO_CABINAS = "cabinas";
    public static final String CAMPO_OTRO_INGENIERIA = "otro_ingenieria";
    public static final String CAMPO_CAPACITACION = "capacitacion";
    public static final String CAMPO_SENIALIZACION_PRECION = "senializacion_precion";
    public static final String CAMPO_SENIALIZACION_EPP = "senializacion_epp";
    public static final String CAMPO_ROTACION = "rotacion";
    public static final String CAMPO_TIEMPO_EXPOSICION = "tiempo_exposicion";
    public static final String CAMPO_OTRO_ADMINISTRATIVO = "otro_administrativo";
    public static final String CAMPO_OBSERVACION = "observacion";
    public static final String CAMPO_TAPONES_AU = "tapones_au";
    public static final String CAMPO_MARCA_TAPONES_AUDI = "marca_tapones_audi";
    public static final String CAMPO_MODELO_TAPONES_AUDI = "modelo_tapones_audi";
    public static final String CAMPO_NRR_TAPONES_AUDI = "nrr_tapones_audi";
    public static final String CAMPO_OREJERAS = "orejereas";
    public static final String CAMPO_MARCA_OREJERAS = "marca_orejeras";
    public static final String CAMPO_MODELO_OREJERAS = "modelo_orejeras";
    public static final String CAMPO_NRR_OREJERAS = "nrr_orejeras";
    public static final String CAMPO_JORNADA = "jornada";
    public static final String CAMPO_ESTADO = "estado";
    public static final String CAMPO_FEC_REG = "fec_reg";
    public static final String CAMPO_USER_REG = "user_reg";

    public static final String CrearTablaRegistroSonometria = "CREATE TABLE " + TABLA_SONOMETRIA + "("
            + CAMPO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CAMPO_COD_FORMATO + " TEXT, "
            + CAMPO_ID_FORMATO + " TEXT, "
            + CAMPO_ID_PLAN_TRABAJO + " TEXT, "
            + CAMPO_ID_PT_FORMATO + " TEXT, "
            + CAMPO_COD_EQUIPO1 + " TEXT, "
            + CAMPO_NOM_EQUIPO1 + " TEXT, "
            + CAMPO_COD_EQUIPO2 + " TEXT, "
            + CAMPO_NOM_EQUIPO2 + " TEXT, "
            + CAMPO_COD_EQUIPO3 + " TEXT, "
            + CAMPO_NOM_EQUIPO3 + " TEXT, "
            + CAMPO_SERIE_EQ1 + " TEXT, "
            + CAMPO_SERIE_EQ2 + " TEXT, "
            + CAMPO_SERIE_EQ3 + " TEXT, "
            + CAMPO_ID_EQUIPO1 + " TEXT, "
            + CAMPO_ID_EQUIPO2 + " TEXT, "
            + CAMPO_ID_EQUIPO3 + " TEXT, "
            + CAMPO_ID_ANALISTA + " TEXT, "
            + CAMPO_NOM_ANALISTA + " TEXT, "
            + CAMPO_HORA_SITU + " TEXT, "
            + CAMPO_NIVEL + " TEXT, "
            + CAMPO_VARIACION + " TEXT, "
            + CAMPO_AREA_TRABAJO + " TEXT, "
            + CAMPO_ACTIVIDADES_REALIZADAS + " TEXT, "
            + CAMPO_ID_HORARIO + " TEXT, "
            + CAMPO_HORA_TRABAJO + " TEXT, "
            + CAMPO_N_PERSONAS + " TEXT, "
            + CAMPO_RUIDO_GENERADO_POR + " TEXT, "
            + CAMPO_AREA_REQ_CONCENTR + " TEXT, "
            + CAMPO_LIM_MAX_PERMIS + " TEXT, "
            + CAMPO_FEC_MONITOREO + " TEXT, "
            + CAMPO_HORA_INICIAL + " TEXT, "
            + CAMPO_TIEMPO_MEDICION + " TEXT, "
            + CAMPO_HORA_FINAL + " TEXT, "
            + CAMPO_V_VIENTO + " TEXT, "
            + CAMPO_H_RELATIVA + " TEXT, "
            + CAMPO_LMIN + " TEXT, "
            + CAMPO_LMAX + " TEXT, "
            + CAMPO_LEQUI + " TEXT, "
            + CAMPO_CTRL_INGENIERIA + " TEXT, "
            + CAMPO_CTRL_ADMINISTRATIVO + " TEXT, "
            + CAMPO_AISLAMIENTO + " TEXT, "
            + CAMPO_CABINAS + " TEXT, "
            + CAMPO_OTRO_INGENIERIA + " TEXT, "
            + CAMPO_CAPACITACION + " TEXT, "
            + CAMPO_SENIALIZACION_PRECION + " TEXT, "
            + CAMPO_SENIALIZACION_EPP + " TEXT, "
            + CAMPO_ROTACION + " TEXT, "
            + CAMPO_TIEMPO_EXPOSICION + " TEXT, "
            + CAMPO_OTRO_ADMINISTRATIVO + " TEXT, "
            + CAMPO_OBSERVACION + " TEXT, "
            + CAMPO_TAPONES_AU + " TEXT, "
            + CAMPO_MARCA_TAPONES_AUDI + " TEXT, "
            + CAMPO_MODELO_TAPONES_AUDI + " TEXT, "
            + CAMPO_NRR_TAPONES_AUDI + " TEXT, "
            + CAMPO_OREJERAS + " TEXT, "
            + CAMPO_MARCA_OREJERAS + " TEXT, "
            + CAMPO_MODELO_OREJERAS + " TEXT, "
            + CAMPO_NRR_OREJERAS + " TEXT, "
            + CAMPO_JORNADA + " TEXT, "
            + CAMPO_ESTADO + " TEXT, "
            + CAMPO_FEC_REG + " TEXT, "
            + CAMPO_USER_REG + " TEXT)";

}
