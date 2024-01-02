package com.mijael.mein.Utilidades;

public class Util_Formato_pTrabajo {
    public static final String TABLA_FORMATO_TRABAJO = "formato_trabajo";
    public static final String CAMPO_ID_PT_FORMATO = "id_pt_formato";
    public static final String CAMPO_ID_PLAN_TRABAJO = "id_plan_trabajo";
    public static final String CAMPO_ID_FORMATO = "id_formato";
    public static final String CAMPO_NOM_FORMATO = "nom_formato";
    public static final String CAMPO_CANTIDAD = "cantidad";
    public static final String CAMPO_NOM_CLIENTE = "nom_cliente";
    public static final String CAMPO_REALIZADO = "realizado";
    public static final String CAMPO_POR_REALIZAR = "por_realizar";
    public static final String CAMPO_VALIDA_GREMISION = "valida_gremision";

    public static final String CrearTablaformatoTrabajo= "CREATE TABLE " + TABLA_FORMATO_TRABAJO + "("
            + CAMPO_ID_PT_FORMATO +" TEXT, "
            + CAMPO_ID_PLAN_TRABAJO +" TEXT, "
            + CAMPO_ID_FORMATO +" TEXT, "
            + CAMPO_NOM_FORMATO + " TEXT, "
            + CAMPO_CANTIDAD + " TEXT, "
            + CAMPO_NOM_CLIENTE + " TEXT, "
            + CAMPO_REALIZADO + " TEXT, "
            + CAMPO_POR_REALIZAR + " TEXT, "
            + CAMPO_VALIDA_GREMISION + " TEXT)";
}
