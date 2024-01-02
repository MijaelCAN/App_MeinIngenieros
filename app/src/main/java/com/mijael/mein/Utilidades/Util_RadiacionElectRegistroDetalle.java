package com.mijael.mein.Utilidades;

public class Util_RadiacionElectRegistroDetalle {
    public static final String TABLA_RADIACION_DETALLE = "tb_RadiacionDetalle";
    public static final String CAMPO_ID_PLAN_TRABAJO_FORMATO_REG = "id_plan_trabajo_formato_reg";
    public static final String CAMPO_FUENTE_GENERADORA = "fuente_generadora";
    public static final String CAMPO_VESTIMENTA_PERSONAL = "vestimenta_personal";
    public static final String CAMPO_X = "x";
    public static final String CAMPO_X2 = "x2";
    public static final String CAMPO_X3 = "x3";
    public static final String CAMPO_X4 = "x4";
    public static final String CAMPO_Y = "y";
    public static final String CAMPO_Y2 = "y2";
    public static final String CAMPO_Y3 = "y3";
    public static final String CAMPO_Y4 = "y4";
    public static final String CAMPO_Z = "z";
    public static final String CAMPO_Z2 = "z2";
    public static final String CAMPO_Z3 = "z3";
    public static final String CAMPO_Z4 = "z4";
    public static final String CAMPO_ESTADO = "estado";
    public static final String CAMPO_FEC_REG = "fec_reg";
    public static final String CAMPO_USER_REG = "user_reg";

    public static final String CrearTablaRadiacionDetalle = "CREATE TABLE " + TABLA_RADIACION_DETALLE + " ("
            + CAMPO_ID_PLAN_TRABAJO_FORMATO_REG + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CAMPO_FUENTE_GENERADORA + " TEXT, "
            + CAMPO_VESTIMENTA_PERSONAL + " TEXT, "
            + CAMPO_X + " TEXT, "
            + CAMPO_X2 + " TEXT, "
            + CAMPO_X3 + " TEXT, "
            + CAMPO_X4 + " TEXT, "
            + CAMPO_Y + " TEXT, "
            + CAMPO_Y2 + " TEXT, "
            + CAMPO_Y3 + " TEXT, "
            + CAMPO_Y4 + " TEXT, "
            + CAMPO_Z + " TEXT, "
            + CAMPO_Z2 + " TEXT, "
            + CAMPO_Z3 + " TEXT, "
            + CAMPO_Z4 + " TEXT, "
            + CAMPO_ESTADO + " TEXT, "
            + CAMPO_FEC_REG + " TEXT, "
            + CAMPO_USER_REG + " TEXT)";
}
