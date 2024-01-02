package com.mijael.mein.Utilidades;

public class Util_EstresTermicoRegistro {
    public static final String TABLA_ESTRES = "tb_Estres";
    public static final String CAMPO_ID = "ID_Estres_Reg";
    public static final String CAMPO_COD_FORMATO = "cod_formato";
    public static final String CAMPO_ID_FORMATO = "id_formato";
    public static final String CAMPO_ID_PLAN_TRABAJO = "id_plan_trabajo";
    public static final String CAMPO_ID_PT_FORMATO = "id_pt_formato";
    public static final String CAMPO_ID_EQUIPO1 = "id_equipo1";
    public static final String CAMPO_COD_EQUIPO1 = "cod_equipo1";
    public static final String CAMPO_NOM_EQUIPO1 = "nom_equipo1";
    public static final String CAMPO_SERIE_EQ1 = "serie_eq1";
    public static final String CAMPO_ID_EQUIPO2 = "id_equipo2";
    public static final String CAMPO_COD_EQUIPO2 = "cod_equipo2";
    public static final String CAMPO_NOM_EQUIPO2 = "nom_equipo2";
    public static final String CAMPO_SERIE_EQ2 = "serie_eq2";
    public static final String CAMPO_ID_ANALISTA = "id_analista";
    public static final String CAMPO_NOM_ANALISTA = "nom_analista";
    public static final String CAMPO_HORA_SITU = "hora_situ";
    public static final String CAMPO_VERF_INSITU = "verf_insitu";
    public static final String CAMPO_FEC_MONITOREO = "fec_monitoreo";
    public static final String CAMPO_HORA_INICIAL = "hora_inicial";
    public static final String CAMPO_HORA_FINAL = "hora_final";
    public static final String CAMPO_TIEMPO_MEDICION = "tiempo_medicion";
    public static final String CAMPO_TIEMPO_EXPOSICION = "tiempo_exposicion";
    public static final String CAMPO_JORNADA = "jornada";
    public static final String CAMPO_TIPO_DOC_TRABAJADOR = "tipo_doc_trabajador";
    public static final String CAMPO_NUM_DOC_TRABAJADOR = "num_doc_trabajador";
    public static final String CAMPO_NOM_TRABAJADOR = "nom_trabajador";
    public static final String CAMPO_PUESTO_TRABAJADOR = "puesto_trabajador";
    public static final String CAMPO_AREA_TRABAJO = "area_trabajo";
    public static final String CAMPO_ACTIVIDADES_REALIZADAS = "actividades_realizadas";
    public static final String CAMPO_PESO_TRABAJADOR = "peso_trabajador";
    public static final String CAMPO_EDAD_TRABAJADOR = "edad_trabajador";
    public static final String CAMPO_HORA_TRABAJO = "hora_trabajo";
    public static final String CAMPO_HORARIO_REFRIGERIO = "horario_refrigerio";
    public static final String CAMPO_REGIMEN_LABORAL = "regimen_laboral";
    public static final String CAMPO_DESC_AREA_TRABAJO = "desc_area_trabajo";
    public static final String CAMPO_AREA_TRAB_DETA = "area_trab_deta";
    public static final String CAMPO_CTRL_INGENIERIA = "ctrl_ingenieria";
    public static final String CAMPO_NOM_CTRL_INGENIERIA = "nom_ctrl_ingenieria";
    public static final String CAMPO_CTRL_ADMINISTRATIVO = "ctrl_administrativo";
    public static final String CAMPO_ANIO_OCU_CARGO = "anio_ocu_cargo";
    public static final String CAMPO_MES_OCU_CARGO = "mes_ocu_cargo";
    public static final String CAMPO_COND_TRAB = "cond_trab";
    public static final String CAMPO_OBSERVACION = "observacion";
    public static final String CAMPO_ESTADO = "estado";
    public static final String CAMPO_FEC_REG = "fec_reg";
    public static final String CAMPO_USER_REG = "user_reg";

    public static final String CrearTablaEstres = "CREATE TABLE " + TABLA_ESTRES + "("
            + CAMPO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CAMPO_COD_FORMATO + " TEXT, "
            + CAMPO_ID_FORMATO + " TEXT, "
            + CAMPO_ID_PLAN_TRABAJO + " TEXT, "
            + CAMPO_ID_PT_FORMATO + " TEXT, "
            + CAMPO_ID_EQUIPO1 + " TEXT, "
            + CAMPO_COD_EQUIPO1 + " TEXT, "
            + CAMPO_NOM_EQUIPO1 + " TEXT, "
            + CAMPO_SERIE_EQ1 + " TEXT, "
            + CAMPO_ID_EQUIPO2 + " TEXT, "
            + CAMPO_COD_EQUIPO2 + " TEXT, "
            + CAMPO_NOM_EQUIPO2 + " TEXT, "
            + CAMPO_SERIE_EQ2 + " TEXT, "
            + CAMPO_ID_ANALISTA + " TEXT, "
            + CAMPO_NOM_ANALISTA + " TEXT, "
            + CAMPO_HORA_SITU + " TEXT, "
            + CAMPO_VERF_INSITU + " TEXT, "
            + CAMPO_FEC_MONITOREO + " TEXT, "
            + CAMPO_HORA_INICIAL + " TEXT, "
            + CAMPO_HORA_FINAL + " TEXT, "
            + CAMPO_TIEMPO_MEDICION + " TEXT, "
            + CAMPO_TIEMPO_EXPOSICION + " TEXT, "
            + CAMPO_JORNADA + " TEXT, "
            + CAMPO_TIPO_DOC_TRABAJADOR + " TEXT, "
            + CAMPO_NUM_DOC_TRABAJADOR + " TEXT, "
            + CAMPO_NOM_TRABAJADOR + " TEXT, "
            + CAMPO_PUESTO_TRABAJADOR + " TEXT, "
            + CAMPO_AREA_TRABAJO + " TEXT, "
            + CAMPO_ACTIVIDADES_REALIZADAS + " TEXT, "
            + CAMPO_PESO_TRABAJADOR + " TEXT, "
            + CAMPO_EDAD_TRABAJADOR + " TEXT, "
            + CAMPO_HORA_TRABAJO + " TEXT, "
            + CAMPO_HORARIO_REFRIGERIO + " TEXT, "
            + CAMPO_REGIMEN_LABORAL + " TEXT, "
            + CAMPO_DESC_AREA_TRABAJO + " TEXT, "
            + CAMPO_AREA_TRAB_DETA + " TEXT, "
            + CAMPO_CTRL_INGENIERIA + " TEXT, "
            + CAMPO_NOM_CTRL_INGENIERIA + " TEXT, "
            + CAMPO_CTRL_ADMINISTRATIVO + " TEXT, "
            + CAMPO_ANIO_OCU_CARGO + " TEXT, "
            + CAMPO_MES_OCU_CARGO + " TEXT, "
            + CAMPO_COND_TRAB + " TEXT, "
            + CAMPO_OBSERVACION + " TEXT, "
            + CAMPO_ESTADO + " TEXT, "
            + CAMPO_FEC_REG + " TEXT, "
            + CAMPO_USER_REG + " TEXT)";
}
