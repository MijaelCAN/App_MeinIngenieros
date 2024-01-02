package com.mijael.mein.Utilidades;

public class Util_DosimetriaRegistro {
    public static final int VERSION_BD = 1;
    public static final String TABLA_REGISTRO_DOSIMETRIA = "tb_dosimetria";
    public static final String CAMPO_ID = "id";
    public static final String CAMPO_COD_FORMATO = "cod_formato";
    public static final String CAMPO_ID_FORMATO = "id_formato";
    public static final String CAMPO_ID_PLAN_TRABAJO = "id_plan_trabajo";
    public static final String CAMPO_ID_PT_FORMATO = "id_pt_formato";
    public static final String CAMPO_COD_DOSIMETRO = "cod_dosimetro";
    public static final String CAMPO_NOM_DOSIMETRO = "nom_dosimetro";
    public static final String CAMPO_COD_CALIBRADOR = "cod_calibrador";
    public static final String CAMPO_NOM_CALIBRADOR = "nom_calibrador";
    public static final String CAMPO_SERIE_EQ1 = "serie_eq1";
    public static final String CAMPO_SERIE_EQ2 = "serie_eq2";
    public static final String CAMPO_ID_DOSIMETRO = "id_dosimetro";
    public static final String CAMPO_ID_CALIBRADOR = "id_calibrador";
    public static final String CAMPO_ID_ANALISTA = "id_analista";
    public static final String CAMPO_NOM_ANALISTA = "nom_analista";
    public static final String CAMPO_HORA_SITU = "hora_situ";
    public static final String CAMPO_NIVEL = "nivel";
    public static final String CAMPO_VARIACION = "variacion";
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
    public static final String CAMPO_CH_RUIDO_EXTERNO = "ch_ruido_externo";
    public static final String CAMPO_CH_RUIDO_ANTIGUO = "ch_ruido_antiguo";
    public static final String CAMPO_CH_RUIDO_GENERADO_POR = "ch_ruido_generado_por";
    public static final String CAMPO_RUIDO_GENERADO_POR = "ruido_generado_por";
    public static final String CAMPO_OTRO_RUIDO = "otro_ruido";
    public static final String CAMPO_HORA_TRABAJO = "hora_trabajo";
    public static final String CAMPO_REGIMEN_LABORAL = "regimen_laboral";
    public static final String CAMPO_HORARIO_REFRIGERIO = "horario_refrigerio";
    public static final String CAMPO_TIEMPO_OCUPADO = "tiempo_ocupado";
    public static final String CAMPO_MOLESTIA_OIDO = "molestia_oido";
    public static final String CAMPO_ENFERMEDAD_OIDO = "enfermedad_oido";
    public static final String CAMPO_DETALLE_ENF_OIDO = "detalle_enf_oido";
    public static final String CAMPO_FECHA_ULTIMO_EXAMEN = "fecha_ultimo_examen";
    public static final String CAMPO_MES_ULTIMO_EXAMEN = "mes_ultimo_examen";
    public static final String CAMPO_ANIO_ULTIMO_EXAMEN = "anio_ultimo_examen";
    public static final String CAMPO_CTRL_INGENIERIA = "ctrl_ingenieria";
    public static final String CAMPO_AISLAMIENTO = "aislamiento";
    public static final String CAMPO_TECHOS = "techos";
    public static final String CAMPO_CABINAS = "cabinas";
    public static final String CAMPO_ORIENTACION = "orientacion";
    public static final String CAMPO_CERRAMIENTO = "cerramiento";
    public static final String CAMPO_OTRO_INGENIERIA = "otro_ingenieria";
    public static final String CAMPO_CTRL_ADMINISTRATIVO = "ctrl_administrativo";
    public static final String CAMPO_CAPACITACION = "capacitacion";
    public static final String CAMPO_SENIALIZACION_PRECION = "senializacion_precion";
    public static final String CAMPO_SENIALIZACION_EPP = "senializacion_epp";
    public static final String CAMPO_ROTACION = "rotacion";
    public static final String CAMPO_ADM_TIEMPO_EXPO = "adm_tiempo_expo";
    public static final String CAMPO_OTRO_ADMINISTRATIVO = "otro_administrativo";
    public static final String CAMPO_TAPONES_AU = "tapones_au";
    public static final String CAMPO_MARCA_TAPONES_AUDI = "marca_tapones_audi";
    public static final String CAMPO_MODELO_TAPONES_AUDI = "modelo_tapones_audi";
    public static final String CAMPO_NRR_TAPONES_AUDI = "nrr_tapones_audi";
    public static final String CAMPO_OREJERAS = "orejeras";
    public static final String CAMPO_MARCA_OREJERAS = "marca_orejeras";
    public static final String CAMPO_MODELO_OREJERAS = "modelo_orejeras";
    public static final String CAMPO_NRR_OREJERAS = "nrr_orejeras";
    public static final String CAMPO_LEQ_DBA = "leq_dba";
    public static final String CAMPO_LPICO_DBA = "lpico_dba";
    public static final String CAMPO_LMAX_DBA = "lmax_dba";
    public static final String CAMPO_LMIN_DBA = "lmin_dba";
    public static final String CAMPO_OBSERVACION = "observacion";
    public static final String CAMPO_ESTADO_RESULTADO = "estado_resultado";
    public static final String CAMPO_ESTADO = "estado";
    public static final String CAMPO_USER_REG = "user_reg";
    public static final String CAMPO_FEC_REG = "fec_reg";

    public static final String CrearTablaRegistroDosimetria = "CREATE TABLE " + TABLA_REGISTRO_DOSIMETRIA + "("
            + CAMPO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CAMPO_COD_FORMATO + " TEXT, "
            + CAMPO_ID_FORMATO + " TEXT, "
            + CAMPO_ID_PLAN_TRABAJO + " TEXT, "
            + CAMPO_ID_PT_FORMATO + " TEXT, "
            + CAMPO_COD_DOSIMETRO + " TEXT, "
            + CAMPO_NOM_DOSIMETRO + " TEXT, "
            + CAMPO_COD_CALIBRADOR + " TEXT, "
            + CAMPO_NOM_CALIBRADOR + " TEXT, "
            + CAMPO_SERIE_EQ1 + " TEXT, "
            + CAMPO_SERIE_EQ2 + " TEXT, "
            + CAMPO_ID_DOSIMETRO + " TEXT, "
            + CAMPO_ID_CALIBRADOR + " TEXT, "
            + CAMPO_ID_ANALISTA + " TEXT, "
            + CAMPO_NOM_ANALISTA + " TEXT, "
            + CAMPO_HORA_SITU + " TEXT, "
            + CAMPO_NIVEL + " TEXT, "
            + CAMPO_VARIACION + " TEXT, "
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
            + CAMPO_CH_RUIDO_EXTERNO + " TEXT, "
            + CAMPO_CH_RUIDO_ANTIGUO + " TEXT, "
            + CAMPO_CH_RUIDO_GENERADO_POR + " TEXT, "
            + CAMPO_RUIDO_GENERADO_POR + " TEXT, "
            + CAMPO_OTRO_RUIDO + " TEXT, "
            + CAMPO_HORA_TRABAJO + " TEXT, "
            + CAMPO_REGIMEN_LABORAL + " TEXT, "
            + CAMPO_HORARIO_REFRIGERIO + " TEXT, "
            + CAMPO_TIEMPO_OCUPADO + " TEXT, "
            + CAMPO_MOLESTIA_OIDO + " TEXT, "
            + CAMPO_ENFERMEDAD_OIDO + " TEXT, "
            + CAMPO_DETALLE_ENF_OIDO + " TEXT, "
            + CAMPO_FECHA_ULTIMO_EXAMEN + " TEXT, "
            + CAMPO_MES_ULTIMO_EXAMEN + " TEXT, "
            + CAMPO_ANIO_ULTIMO_EXAMEN + " TEXT, "
            + CAMPO_CTRL_INGENIERIA + " TEXT, "
            + CAMPO_AISLAMIENTO + " TEXT, "
            + CAMPO_TECHOS + " TEXT, "
            + CAMPO_CABINAS + " TEXT, "
            + CAMPO_ORIENTACION + " TEXT, "
            + CAMPO_CERRAMIENTO + " TEXT, "
            + CAMPO_OTRO_INGENIERIA + " TEXT, "
            + CAMPO_CTRL_ADMINISTRATIVO + " TEXT, "
            + CAMPO_CAPACITACION + " TEXT, "
            + CAMPO_SENIALIZACION_PRECION + " TEXT, "
            + CAMPO_SENIALIZACION_EPP + " TEXT, "
            + CAMPO_ROTACION + " TEXT, "
            + CAMPO_ADM_TIEMPO_EXPO + " TEXT, "
            + CAMPO_OTRO_ADMINISTRATIVO + " TEXT, "
            + CAMPO_TAPONES_AU + " TEXT, "
            + CAMPO_MARCA_TAPONES_AUDI + " TEXT, "
            + CAMPO_MODELO_TAPONES_AUDI + " TEXT, "
            + CAMPO_NRR_TAPONES_AUDI + " TEXT, "
            + CAMPO_OREJERAS + " TEXT, "
            + CAMPO_MARCA_OREJERAS + " TEXT, "
            + CAMPO_MODELO_OREJERAS + " TEXT, "
            + CAMPO_NRR_OREJERAS + " TEXT, "
            + CAMPO_LEQ_DBA + " TEXT, "
            + CAMPO_LPICO_DBA + " TEXT, "
            + CAMPO_LMAX_DBA + " TEXT, "
            + CAMPO_LMIN_DBA + " TEXT, "
            + CAMPO_OBSERVACION + " TEXT, "
            + CAMPO_ESTADO_RESULTADO + " TEXT, "
            + CAMPO_ESTADO + " INTEGER, "
            + CAMPO_USER_REG + " TEXT, "
            + CAMPO_FEC_REG + " TEXT"
            + ")";
}
