package com.mijael.mein.Utilidades;

public class Util_VibracionRegistroDetalle {
    public static final String TABLA_VIBRACION_DETALLE = "tb_VibracionDetalle";
    public static final String CAMPO_ID_PLAN_TRABAJO_FORMATO_REG = "id_plan_trabajo_formato_reg";
    public static final String CAMPO_FUENTE_GENERADORA = "fuente_generadora";
    public static final String CAMPO_DESC_FUENTE_FRIO = "desc_fuente_frio";
    public static final String CAMPO_FRECUENCIA = "frecuencia";
    public static final String CAMPO_EPP_BOTAS = "epp_botas";
    public static final String CAMPO_EPP_GUANTES = "epp_guantes";
    public static final String CAMPO_EPP_CASCO = "epp_casco";
    public static final String CAMPO_EPP_OREJERAS = "epp_orejeras";
    public static final String CAMPO_OTRO_EPP = "otro_epp";
    public static final String CAMPO_X = "x";
    public static final String CAMPO_Y = "y";
    public static final String CAMPO_Z = "z";
    public static final String CAMPO_ESTADO = "estado";
    public static final String CAMPO_FEC_REG = "fec_reg";
    public static final String CAMPO_USER_REG = "user_reg";

    public static final String CrearTablaVibracionDetalle = "CREATE TABLE " + TABLA_VIBRACION_DETALLE + "("
            + CAMPO_ID_PLAN_TRABAJO_FORMATO_REG + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CAMPO_FUENTE_GENERADORA + " TEXT, "
            + CAMPO_DESC_FUENTE_FRIO + " TEXT, "
            + CAMPO_FRECUENCIA + " TEXT, "
            + CAMPO_EPP_BOTAS + " TEXT, "
            + CAMPO_EPP_GUANTES + " TEXT, "
            + CAMPO_EPP_CASCO + " TEXT, "
            + CAMPO_EPP_OREJERAS + " TEXT, "
            + CAMPO_OTRO_EPP + " TEXT, "
            + CAMPO_X + " TEXT, "
            + CAMPO_Y + " TEXT, "
            + CAMPO_Z + " TEXT, "
            + CAMPO_ESTADO + " TEXT, "
            + CAMPO_FEC_REG + " TEXT, "
            + CAMPO_USER_REG + " TEXT"
            + ");";
}
