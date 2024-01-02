package com.mijael.mein.Utilidades;

public class Util_IluminacionRegistro {
    public static final String TABLA_ILUMINACION = "tb_iluminacion";
    public static final String CAMPO_ID = "ID_Iluminacion_Reg";
    public static final String CAMPO_COD_FORMATO = "cod_formato";
    public static final String CAMPO_ID_FORMATO = "id_formato";
    public static final String CAMPO_ID_PLAN_TRABAJO = "id_plan_trabajo";
    public static final String CAMPO_ID_PT_FORMATO = "id_pt_formato";
    public static final String CAMPO_ID_ANALISTA = "id_analista";
    public static final String CAMPO_NOM_ANALISTA = "nom_analista";
    public static final String CAMPO_ID_EQUIPO1 = "id_equipo1";
    public static final String CAMPO_COD_EQUIPO1 = "cod_equipo1";
    public static final String CAMPO_NOM_EQUIPO1 = "nom_equipo1";
    public static final String CAMPO_SERIE_EQ1 = "serie_eq1";
    public static final String CAMPO_HORA_SITU = "hora_situ";
    public static final String CAMPO_LUX = "lux";
    public static final String CAMPO_TIPO_DOC_TRABAJADOR = "tipo_doc_trabajador";
    public static final String CAMPO_NUM_DOC_TRABAJADOR = "num_doc_trabajador";
    public static final String CAMPO_NOM_TRABAJADOR = "nom_trabajador";
    public static final String CAMPO_PUESTO_TRABAJADOR = "puesto_trabajador";
    public static final String CAMPO_AREA_TRABAJO = "area_trabajo";
    public static final String CAMPO_N_PERSONAS = "n_personas";
    public static final String CAMPO_HORA_TRABAJO = "hora_trabajo";
    public static final String CAMPO_REGIMEN_LABORAL = "regimen_laboral";
    public static final String CAMPO_FEC_MONITOREO = "fec_monitoreo";
    public static final String CAMPO_HORA_INICIAL = "hora_inicial";
    public static final String CAMPO_ACTIVIDADES_REALIZADAS = "actividades_realizadas";
    public static final String CAMPO_OBSERVACION = "observacion";
    public static final String CAMPO_UBIC_EQUIP = "ubic_equip";
    public static final String CAMPO_TAREA_VISUAL = "tarea_visual";
    public static final String CAMPO_TIPO_TAREA_VISUAL = "tipo_tarea_visual";
    public static final String CAMPO_NIVEL_MIN_ILU = "nivel_min_ilu";
    public static final String CAMPO_ESTADO = "estado";
    public static final String CAMPO_FEC_REG = "fec_reg";
    public static final String CAMPO_USER_REG = "user_reg";

    public static final String CrearTablaIluminacion = "CREATE TABLE " + TABLA_ILUMINACION + "("
            + CAMPO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CAMPO_COD_FORMATO + " TEXT, "
            + CAMPO_ID_FORMATO + " TEXT, "
            + CAMPO_ID_PLAN_TRABAJO + " TEXT, "
            + CAMPO_ID_PT_FORMATO + " TEXT, "
            + CAMPO_ID_ANALISTA + " TEXT, "
            + CAMPO_NOM_ANALISTA + " TEXT, "
            + CAMPO_ID_EQUIPO1 + " TEXT, "
            + CAMPO_COD_EQUIPO1 + " TEXT, "
            + CAMPO_NOM_EQUIPO1 + " TEXT, "
            + CAMPO_SERIE_EQ1 + " TEXT, "
            + CAMPO_HORA_SITU + " TEXT, "
            + CAMPO_LUX + " TEXT, "
            + CAMPO_TIPO_DOC_TRABAJADOR + " TEXT, "
            + CAMPO_NUM_DOC_TRABAJADOR + " TEXT, "
            + CAMPO_NOM_TRABAJADOR + " TEXT, "
            + CAMPO_PUESTO_TRABAJADOR + " TEXT, "
            + CAMPO_AREA_TRABAJO + " TEXT, "
            + CAMPO_N_PERSONAS + " TEXT, "
            + CAMPO_HORA_TRABAJO + " TEXT, "
            + CAMPO_REGIMEN_LABORAL + " TEXT, "
            + CAMPO_FEC_MONITOREO + " TEXT, "
            + CAMPO_HORA_INICIAL + " TEXT, "
            + CAMPO_ACTIVIDADES_REALIZADAS + " TEXT, "
            + CAMPO_OBSERVACION + " TEXT, "
            + CAMPO_UBIC_EQUIP + " TEXT, "
            + CAMPO_TAREA_VISUAL + " TEXT, "
            + CAMPO_TIPO_TAREA_VISUAL + " TEXT, "
            + CAMPO_NIVEL_MIN_ILU + " TEXT, "
            + CAMPO_ESTADO + " TEXT, "
            + CAMPO_FEC_REG + " TEXT, "
            + CAMPO_USER_REG + " TEXT"
            + ")";
}
