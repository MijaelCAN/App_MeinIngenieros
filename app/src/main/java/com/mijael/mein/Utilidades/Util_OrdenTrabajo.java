package com.mijael.mein.Utilidades;

public class Util_OrdenTrabajo {
    public static  final int VERSION_BD = 1;
    public static final String TABLA_ORDEN_TRABAJO = "orden_trabajo";
    public static final String CAMPO_COD_OT = "_id";
    public static final String CAMPO_COD_COTIZACION = "cod_cotizacion";
    public static final String CAMPO_NOM_CLIENTE = "nom_cliente";
    public static final String CAMPO_NOM_TIPO_SERVICIO = "nom_tipo_servicio";
    public static final String CAMPO_FECHA_REGISTRO = "fecha_registro";
    public static final String CAMPO_ID_OT = "id_ot";
    public static final String CAMPO_ID_PLAN_TRABAJO = "id_plan_trabajo";
    public static final String CAMPO_DOFRMATOS = "dformatos";
    public static final String CAMPO_ID_COLABORADOR = "id_colaborador";
    public static final String CAMPO_ID_COORDINADOR = "id_coordinador";
    public static final String CAMPO_URL_PLAN_TRABAJO = "url_plan_trabajo";

    public static final String CrearTablaOrdenTrabajo= "CREATE TABLE " + TABLA_ORDEN_TRABAJO + "("
            + CAMPO_COD_OT +" TEXT, "
            + CAMPO_COD_COTIZACION +" TEXT, "
            + CAMPO_NOM_CLIENTE +" TEXT, "
            + CAMPO_NOM_TIPO_SERVICIO + " TEXT, "
            + CAMPO_FECHA_REGISTRO + " TEXT, "
            + CAMPO_ID_OT + " TEXT, "
            + CAMPO_ID_PLAN_TRABAJO + " TEXT, "
            + CAMPO_DOFRMATOS + " TEXT, "
            + CAMPO_ID_COLABORADOR + " TEXT, "
            + CAMPO_ID_COORDINADOR + " TEXT, "
            + CAMPO_URL_PLAN_TRABAJO + " TEXT)";

}
