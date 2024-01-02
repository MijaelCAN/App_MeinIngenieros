package com.mijael.mein.Utilidades;

import android.os.Build;

public class Util_Cines{
    public static final int VERSION_BD = 1;
    public static final String TABLA_CINES = "cines";
    public static final String CAMPO_ID = "id";
    public static final String CAMPO_RAZON_SOCIAL = "RazonSocial";
    public static final String CAMPO_SALAS = "Salas";
    public static final String CAMPO_ID_DISTRITO = "idDistrito";
    public static final String CAMPO_DIRECCION = "Direccion";
    public static final String CAMPO_TELEFONOS = "Telefonos";
    public static final String CAMPO_DETALLE = "Detalle";
    public static final String CrearTablaCines = "CREATE TABLE " + TABLA_CINES + "( " + CAMPO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + CAMPO_RAZON_SOCIAL + " TEXT,"+
            CAMPO_SALAS + " INTEGER," + CAMPO_ID_DISTRITO + " INTEGER," + CAMPO_DIRECCION + " TEXT," + CAMPO_TELEFONOS + " TEXT," + CAMPO_DETALLE + " TEXT)";

}
