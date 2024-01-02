package com.mijael.mein.Utilidades;

public class Util_VibracionRegistro {
    public static final String TABLA_VIBRACION = "tb_Vibracion";
    public static final String CAMPO_ID = "ID_Vibracion_Reg";
    public static final String CAMPO_COD_FORMATO = "cod_formato";
    public static final String CAMPO_ID_FORMATO = "id_formato";
    public static final String CAMPO_ID_PLAN_TRABAJO = "id_plan_trabajo";
    public static final String CAMPO_ID_PT_FORMATO = "id_pt_formato";
    public static final String CAMPO_COD_EQUIPO1 = "cod_equipo1";
    public static final String CAMPO_NOM_EQUIPO1 = "nom_equipo1";
    public static final String CAMPO_SERIE_EQ1 = "serie_eq1";
    public static final String CAMPO_ID_EQUIPO1 = "id_equipo1";
    public static final String CAMPO_ID_ANALISTA = "id_analista";
    public static final String CAMPO_NOM_ANALISTA = "nom_analista";
    public static final String CAMPO_TIPO_VIBRACION = "tipo_vibracion";
    public static final String CAMPO_VERF_INSITU = "verf_insitu";
    public static final String CAMPO_HORA_SITU = "hora_situ";
    public static final String CAMPO_FEC_MONITOREO = "fec_monitoreo";
    public static final String CAMPO_HORA_INICIAL = "hora_inicial";
    public static final String CAMPO_HORA_FINAL = "hora_final";
    public static final String CAMPO_TIEMPO_EXPOSICION = "tiempo_exposicion";
    public static final String CAMPO_JORNADA = "jornada";
    public static final String CAMPO_TIPO_DOC_TRABAJADOR = "tipo_doc_trabajador";
    public static final String CAMPO_NUM_DOC_TRABAJADOR = "num_doc_trabajador";
    public static final String CAMPO_NOM_TRABAJADOR = "nom_trabajador";
    public static final String CAMPO_PUESTO_TRABAJADOR = "puesto_trabajador";
    public static final String CAMPO_AREA_TRABAJO = "area_trabajo";
    public static final String CAMPO_ACTIVIDADES_REALIZADAS = "actividades_realizadas";
    public static final String CAMPO_EDAD_TRABAJADOR = "edad_trabajador";
    public static final String CAMPO_HORA_TRABAJO = "hora_trabajo";
    public static final String CAMPO_HORARIO_REFRIGERIO = "horario_refrigerio";
    public static final String CAMPO_REGIMEN_LABORAL = "regimen_laboral";
    public static final String CAMPO_CTRL_INGENIERIA = "ctrl_ingenieria";
    public static final String CAMPO_NOM_CTRL_INGENIERIA = "nom_ctrl_ingenieria";
    public static final String CAMPO_CTRL_ADMINISTRATIVO = "ctrl_administrativo";
    public static final String CAMPO_SENIALIZACION_VIBRACION = "senializacion_vibracion";
    public static final String CAMPO_CAPACITACION = "capacitacion";
    public static final String CAMPO_MANTENIMIENTO_VIBRACION = "mantenimiento_vibracion";
    public static final String CAMPO_ESTADO = "estado";
    public static final String CAMPO_FEC_REG = "fec_reg";
    public static final String CAMPO_USER_REG = "user_reg";

    public static final String CrearTablaVibracion = "CREATE TABLE " + TABLA_VIBRACION + "("
            + CAMPO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CAMPO_COD_FORMATO + " TEXT, "
            + CAMPO_ID_FORMATO + " TEXT, "
            + CAMPO_ID_PLAN_TRABAJO + " TEXT, "
            + CAMPO_ID_PT_FORMATO + " TEXT, "
            + CAMPO_COD_EQUIPO1 + " TEXT, "
            + CAMPO_NOM_EQUIPO1 + " TEXT, "
            + CAMPO_SERIE_EQ1 + " TEXT, "
            + CAMPO_ID_EQUIPO1 + " TEXT, "
            + CAMPO_ID_ANALISTA + " TEXT, "
            + CAMPO_NOM_ANALISTA + " TEXT, "
            + CAMPO_TIPO_VIBRACION + " TEXT, "
            + CAMPO_VERF_INSITU + " TEXT, "
            + CAMPO_HORA_SITU + " TEXT, "
            + CAMPO_FEC_MONITOREO + " TEXT, "
            + CAMPO_HORA_INICIAL + " TEXT, "
            + CAMPO_HORA_FINAL + " TEXT, "
            + CAMPO_TIEMPO_EXPOSICION + " TEXT, "
            + CAMPO_JORNADA + " TEXT, "
            + CAMPO_TIPO_DOC_TRABAJADOR + " TEXT, "
            + CAMPO_NUM_DOC_TRABAJADOR + " TEXT, "
            + CAMPO_NOM_TRABAJADOR + " TEXT, "
            + CAMPO_PUESTO_TRABAJADOR + " TEXT, "
            + CAMPO_AREA_TRABAJO + " TEXT, "
            + CAMPO_ACTIVIDADES_REALIZADAS + " TEXT, "
            + CAMPO_EDAD_TRABAJADOR + " TEXT, "
            + CAMPO_HORA_TRABAJO + " TEXT, "
            + CAMPO_HORARIO_REFRIGERIO + " TEXT, "
            + CAMPO_REGIMEN_LABORAL + " TEXT, "
            + CAMPO_CTRL_INGENIERIA + " TEXT, "
            + CAMPO_NOM_CTRL_INGENIERIA + " TEXT, "
            + CAMPO_CTRL_ADMINISTRATIVO + " TEXT, "
            + CAMPO_SENIALIZACION_VIBRACION + " TEXT, "
            + CAMPO_CAPACITACION + " TEXT, "
            + CAMPO_MANTENIMIENTO_VIBRACION + " TEXT, "
            + CAMPO_ESTADO + " TEXT, "
            + CAMPO_FEC_REG + " TEXT, "
            + CAMPO_USER_REG + " TEXT"
            + ");";

}
