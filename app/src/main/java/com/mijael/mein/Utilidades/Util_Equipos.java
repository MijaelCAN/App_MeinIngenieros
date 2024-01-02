package com.mijael.mein.Utilidades;

public class Util_Equipos {
    public static final int VERSION_BD = 1;
    public static final String TABLA_EQUIPOS = "equipos";
    public static final String CAMPO_ID_EQUIPO = "id_equipo_registro";
    public static final String CAMPO_CODIGO_EQUIPO = "cod_equipo";
    public static final String CAMPO_MODELO = "modelo";
    public static final String CAMPO_PROVEEDOR = "proveedor";
    public static final String CAMPO_SERIE = "serie";
    public static final String CAMPO_CODIGO = "codigo";
    public static final String CAMPO_CANTIDAD = "cantidad";
    public static final String CAMPO_ALQUILER = "alquiler";
    public static final String CAMPO_OBSERVACIONES = "observaciones";
    public static final String CAMPO_ESTADO = "estado";
    public static final String CAMPO_NOM_MARCA = "nom_marca";
    public static final String CAMPO_NOMBRE = "nombre";
    public static final String CAMPO_NOM_MODELO = "nom_modelo";
    public static final String CAMPO_COD_MONEDA = "cod_monedaf";
    public static final String CAMPO_VIGENCIA = "vigencia";
    public static final String CAMPO_FECHA_CALIBRA = "fecha_calibracion";
    public static final String CAMPO_VENCIMIENTO = "fecha_vencimiento";
    public static final String CrearTablaEquipos = "CREATE TABLE " + TABLA_EQUIPOS + "("
            + CAMPO_ID_EQUIPO +" INTEGER, "
            + CAMPO_CODIGO_EQUIPO +" TEXT, "
            + CAMPO_MODELO +" TEXT, "
            + CAMPO_PROVEEDOR + " TEXT, "
            + CAMPO_SERIE + " TEXT, "
            + CAMPO_CODIGO + " TEXT, "
            + CAMPO_CANTIDAD + " INTEGER, "
            + CAMPO_ALQUILER + " FLOAT, "
            + CAMPO_OBSERVACIONES + " TEXT, "
            + CAMPO_ESTADO + " TEXT, "
            + CAMPO_NOM_MARCA + " TEXT, "
            + CAMPO_NOMBRE + " TEXT, "
            + CAMPO_NOM_MODELO + " TEXT, "
            + CAMPO_COD_MONEDA + " TEXT, "
            + CAMPO_VIGENCIA + " TEXT, "
            + CAMPO_FECHA_CALIBRA + " TEXT, "
            + CAMPO_VENCIMIENTO + " TEXT )";
}
