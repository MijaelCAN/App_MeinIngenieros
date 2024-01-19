package com.mijael.mein.Utilidades;

public class Util_IluminacionRegistroDetalle {
    public static final String TABLA_ILUMINACION_DETALLE = "tb_IluminacionDetalle";
    public static final String CAMPO_ID_PLAN_TRABAJO_FORMATO_REG = "id_plan_trabajo_formato_reg";
    public static final String CAMPO_TIPO_ILUMINACION_ART = "tipo_iluminacion_art";
    public static final String CAMPO_TIPO_ILUMINACION_NAT = "tipo_iluminacion_nat";
    public static final String CAMPO_CANT_ILUMINARIAS = "cant_iluminarias";
    public static final String CAMPO_IL_1 = "il_1"; //ELIMINAR ESTOS CAMPOS QUE YA NO SE USARAN  a cambio de PUNTOS_MED --*
    public static final String CAMPO_IL_2 = "il_2";
    public static final String CAMPO_IL_3 = "il_3";
    public static final String CAMPO_IL_4 = "il_4";
    public static final String CAMPO_IL_5 = "il_5";
    public static final String CAMPO_IL_6 = "il_6";
    public static final String CAMPO_IL_7 = "il_7";
    public static final String CAMPO_IL_8 = "il_8";
    public static final String CAMPO_PLAN_MANTENIMIENTO_ILUM = "plan_mantenimiento_ilum";
    public static final String CAMPO_AREA_TRABAJO_M2 = "area_trabajo_m2";
    public static final String CAMPO_ALTURA_P_TRABAJO = "altura_p_trabajo";
    public static final String CAMPO_N_LAMPARAS = "n_lamparas";
    public static final String CAMPO_ALTURA_P_LUMINARIA = "altura_p_luminaria";
    public static final String CAMPO_COLOR_PARED = "color_pared";
    public static final String CAMPO_COLOR_PISO = "color_piso";
    public static final String CAMPO_ESTADO_FISICO = "estado_fisico";
    public static final String CAMPO_ESTADO = "estado";
    public static final String CAMPO_FEC_REG = "fec_reg";
    public static final String CAMPO_USER_REG = "user_reg";

    public static final String CrearTablaIluminacionDetalle = "CREATE TABLE " + TABLA_ILUMINACION_DETALLE + "("
            + CAMPO_ID_PLAN_TRABAJO_FORMATO_REG + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CAMPO_TIPO_ILUMINACION_ART + " TEXT, "
            + CAMPO_TIPO_ILUMINACION_NAT + " TEXT, "
            + CAMPO_CANT_ILUMINARIAS + " TEXT, "
            + CAMPO_IL_1 + " TEXT, "
            + CAMPO_IL_2 + " TEXT, "
            + CAMPO_IL_3 + " TEXT, "
            + CAMPO_IL_4 + " TEXT, "
            + CAMPO_IL_5 + " TEXT, "
            + CAMPO_IL_6 + " TEXT, "
            + CAMPO_IL_7 + " TEXT, "
            + CAMPO_IL_8 + " TEXT, "
            + CAMPO_PLAN_MANTENIMIENTO_ILUM + " TEXT, "
            + CAMPO_AREA_TRABAJO_M2 + " TEXT, "
            + CAMPO_ALTURA_P_TRABAJO + " TEXT, "
            + CAMPO_N_LAMPARAS + " TEXT, "
            + CAMPO_ALTURA_P_LUMINARIA + " TEXT, "
            + CAMPO_COLOR_PARED + " TEXT, "
            + CAMPO_COLOR_PISO + " TEXT, "
            + CAMPO_ESTADO_FISICO + " TEXT, "
            + CAMPO_ESTADO + " TEXT, "
            + CAMPO_FEC_REG + " TEXT, "
            + CAMPO_USER_REG + " TEXT"
            + ")";

}
