package com.mijael.mein.Utilidades;

public class Util_Usuario {
    public static final int VERSION_BD = 1;
    public static final String TABLA_USUARIO = "usuario";
    public static final String CAMPO_ID = "id_usuario";
    public static final String CAMPO_NOMBRES = "usuario_nombres";
    public static final String CAMPO_APATER = "usuario_apater";
    public static final String CAMPO_AMATER = "usuario_amater";
    public static final String CAMPO_CODIGO_USU = "usuario_codigo";
    public static final String CAMPO_PASS_ENCRIP = "usuario_password";
    public static final String CAMPO_PASS_DESENCRIP = "password_desc";
    public static final String CAMPO_ID_NIVEL = "id_nivel";
    public static final String CAMPO_CODIGO = "codigo";
    public static final String CrearTablaUsuario = "CREATE TABLE " + TABLA_USUARIO + "("
            + CAMPO_ID +" INTEGER, "
            + CAMPO_NOMBRES +" TEXT, "
            + CAMPO_APATER +" TEXT, "
            + CAMPO_AMATER + " TEXT, "
            + CAMPO_CODIGO_USU + " TEXT, "
            + CAMPO_PASS_ENCRIP + " TEXT, "
            + CAMPO_PASS_DESENCRIP + " TEXT, "
            + CAMPO_ID_NIVEL + " INTEGER, "
            + CAMPO_CODIGO + " TEXT)";

}
