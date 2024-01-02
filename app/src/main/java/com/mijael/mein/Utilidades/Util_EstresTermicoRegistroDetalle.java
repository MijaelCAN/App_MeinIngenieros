package com.mijael.mein.Utilidades;

public class Util_EstresTermicoRegistroDetalle {
    public static final String TABLA_ESTRES_DETALLE = "tb_Estres_Detalle";
    public static final String CAMPO_ID_PLAN_TRABAJO_FORMATO_REG = "ID_Estres_Detalle";
    public static final String CAMPO_FUENTE_GENERADORA = "fuente_generadora";
    public static final String CAMPO_DESC_FUENTE_FRIO = "desc_fuente_frio";
    public static final String CAMPO_ZONA_SOMBRA = "zona_sombra";
    public static final String CAMPO_ROTACION_PERSONAL = "rotacion_personal";
    public static final String CAMPO_TIEMPO_RECUPERACION = "tiempo_recuperacion";
    public static final String CAMPO_DISPENSADOR = "dispensador";
    public static final String CAMPO_CAPA_EXPO_FRIO = "capa_expo_frio";
    public static final String CAMPO_CAT_TRABAJO = "cat_trabajo";
    public static final String CAMPO_PORC_DESCA = "porc_desca";
    public static final String CAMPO_VESTIMENTA_PERSONAL = "vestimenta_personal";
    public static final String CAMPO_MATERIAL_PRENDA = "material_prenda";
    public static final String CAMPO_COLOR_PREDOMINANTE = "color_predominante";
    public static final String CAMPO_EPP_ZS = "epp_zs";
    public static final String CAMPO_EPP_CASCO = "epp_casco";
    public static final String CAMPO_EPP_LENTES = "epp_lentes";
    public static final String CAMPO_EPP_GUANTES = "epp_guantes";
    public static final String CAMPO_EPP_OREJERAS = "epp_orejeras";
    public static final String CAMPO_EPP_TAPONES = "epp_tapones";
    public static final String CAMPO_EPP_CNUCA = "epp_cnuca";
    public static final String CAMPO_OTRO_EPP = "otro_epp";
    public static final String CAMPO_NOM_TAREA1 = "nom_tarea1";
    public static final String CAMPO_CICLO_TRABAJO1 = "ciclo_trabajo1";
    public static final String CAMPO_POSICION_1 = "posicion_1";
    public static final String CAMPO_PCUERPO_1 = "pcuerpo_1";
    public static final String CAMPO_INTENSIDAD_1 = "intensidad_1";
    public static final String CAMPO_NOM_TAREA2 = "nom_tarea2";
    public static final String CAMPO_CICLO_TRABAJO2 = "ciclo_trabajo2";
    public static final String CAMPO_POSICION_2 = "posicion_2";
    public static final String CAMPO_PCUERPO_2 = "pcuerpo_2";
    public static final String CAMPO_INTENSIDAD_2 = "intensidad_2";
    public static final String CAMPO_NOM_TAREA3 = "nom_tarea3";
    public static final String CAMPO_CICLO_TRABAJO3 = "ciclo_trabajo3";
    public static final String CAMPO_POSICION_3 = "posicion_3";
    public static final String CAMPO_PCUERPO_3 = "pcuerpo_3";
    public static final String CAMPO_INTENSIDAD_3 = "intensidad_3";
    public static final String CAMPO_MTR_SUBIDA = "mtr_subida";
    public static final String CAMPO_WBGT = "wbgt";
    public static final String CAMPO_WBGT_2 = "wbgt_2";
    public static final String CAMPO_WBGT_3 = "wbgt_3";
    public static final String CAMPO_T_AIRE = "t_aire";
    public static final String CAMPO_T_AIRE_2 = "t_aire_2";
    public static final String CAMPO_T_AIRE_3 = "t_aire_3";
    public static final String CAMPO_T_GLOBO = "t_globo";
    public static final String CAMPO_T_GLOBO_2 = "t_globo_2";
    public static final String CAMPO_T_GLOBO_3 = "t_globo_3";
    public static final String CAMPO_H_RELATIVA = "h_relativa";
    public static final String CAMPO_H_RELATIVA_2 = "h_relativa_2";
    public static final String CAMPO_H_RELATIVA_3 = "h_relativa_3";
    public static final String CAMPO_V_VIENTO = "v_viento";
    public static final String CAMPO_ESTADO_DETALLE = "estado";
    public static final String CAMPO_FEC_REG_DETALLE = "fec_reg";
    public static final String CAMPO_USER_REG_DETALLE = "user_reg";

    public static final String CrearTablaEstresDetalle = "CREATE TABLE " + TABLA_ESTRES_DETALLE + "("
            + CAMPO_ID_PLAN_TRABAJO_FORMATO_REG + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CAMPO_FUENTE_GENERADORA + " TEXT, "
            + CAMPO_DESC_FUENTE_FRIO + " TEXT, "
            + CAMPO_ZONA_SOMBRA + " TEXT, "
            + CAMPO_ROTACION_PERSONAL + " TEXT, "
            + CAMPO_TIEMPO_RECUPERACION + " TEXT, "
            + CAMPO_DISPENSADOR + " TEXT, "
            + CAMPO_CAPA_EXPO_FRIO + " TEXT, "
            + CAMPO_CAT_TRABAJO + " TEXT, "
            + CAMPO_PORC_DESCA + " TEXT, "
            + CAMPO_VESTIMENTA_PERSONAL + " TEXT, "
            + CAMPO_MATERIAL_PRENDA + " TEXT, "
            + CAMPO_COLOR_PREDOMINANTE + " TEXT, "
            + CAMPO_EPP_ZS + " TEXT, "
            + CAMPO_EPP_CASCO + " TEXT, "
            + CAMPO_EPP_LENTES + " TEXT, "
            + CAMPO_EPP_GUANTES + " TEXT, "
            + CAMPO_EPP_OREJERAS + " TEXT, "
            + CAMPO_EPP_TAPONES + " TEXT, "
            + CAMPO_EPP_CNUCA + " TEXT, "
            + CAMPO_OTRO_EPP + " TEXT, "
            + CAMPO_NOM_TAREA1 + " TEXT, "
            + CAMPO_CICLO_TRABAJO1 + " TEXT, "
            + CAMPO_POSICION_1 + " TEXT, "
            + CAMPO_PCUERPO_1 + " TEXT, "
            + CAMPO_INTENSIDAD_1 + " TEXT, "
            + CAMPO_NOM_TAREA2 + " TEXT, "
            + CAMPO_CICLO_TRABAJO2 + " TEXT, "
            + CAMPO_POSICION_2 + " TEXT, "
            + CAMPO_PCUERPO_2 + " TEXT, "
            + CAMPO_INTENSIDAD_2 + " TEXT, "
            + CAMPO_NOM_TAREA3 + " TEXT, "
            + CAMPO_CICLO_TRABAJO3 + " TEXT, "
            + CAMPO_POSICION_3 + " TEXT, "
            + CAMPO_PCUERPO_3 + " TEXT, "
            + CAMPO_INTENSIDAD_3 + " TEXT, "
            + CAMPO_MTR_SUBIDA + " TEXT, "
            + CAMPO_WBGT + " TEXT, "
            + CAMPO_WBGT_2 + " TEXT, "
            + CAMPO_WBGT_3 + " TEXT, "
            + CAMPO_T_AIRE + " TEXT, "
            + CAMPO_T_AIRE_2 + " TEXT, "
            + CAMPO_T_AIRE_3 + " TEXT, "
            + CAMPO_T_GLOBO + " TEXT, "
            + CAMPO_T_GLOBO_2 + " TEXT, "
            + CAMPO_T_GLOBO_3 + " TEXT, "
            + CAMPO_H_RELATIVA + " TEXT, "
            + CAMPO_H_RELATIVA_2 + " TEXT, "
            + CAMPO_H_RELATIVA_3 + " TEXT, "
            + CAMPO_V_VIENTO + " TEXT, "
            + CAMPO_ESTADO_DETALLE + " TEXT, "
            + CAMPO_FEC_REG_DETALLE + " TEXT, "
            + CAMPO_USER_REG_DETALLE + " TEXT)";

}
