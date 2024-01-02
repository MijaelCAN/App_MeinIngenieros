package com.mijael.mein.Entidades;

public class Usuario {
    private int id_usuario;
    private String usuario_nombres;
    private String usuario_apater;
    private String usuario_amater;
    private String usuario_codigo;
    private String usuario_password;
    private String password_desc;
    private int id_nivel;
    private String codigo;

    public Usuario(int id_usuario, String usuario_nombres, String usuario_apater, String usuario_amater, String usuario_codigo, String usuario_password, String password_desc, int id_nivel, String codigo) {
        this.id_usuario = id_usuario;
        this.usuario_nombres = usuario_nombres;
        this.usuario_apater = usuario_apater;
        this.usuario_amater = usuario_amater;
        this.usuario_codigo = usuario_codigo;
        this.usuario_password = usuario_password;
        this.password_desc = password_desc;
        this.id_nivel = id_nivel;
        this.codigo = codigo;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getUsuario_nombres() {
        return usuario_nombres;
    }

    public void setUsuario_nombres(String usuario_nombres) {
        this.usuario_nombres = usuario_nombres;
    }

    public String getUsuario_apater() {
        return usuario_apater;
    }

    public void setUsuario_apater(String usuario_apater) {
        this.usuario_apater = usuario_apater;
    }

    public String getUsuario_amater() {
        return usuario_amater;
    }

    public void setUsuario_amater(String usuario_amater) {
        this.usuario_amater = usuario_amater;
    }

    public String getUsuario_codigo() {
        return usuario_codigo;
    }

    public void setUsuario_codigo(String usuario_codigo) {
        this.usuario_codigo = usuario_codigo;
    }

    public String getUsuario_password() {
        return usuario_password;
    }

    public void setUsuario_password(String usuario_password) {
        this.usuario_password = usuario_password;
    }

    public String getPassword_desc() {
        return password_desc;
    }

    public void setPassword_desc(String password_desc) {
        this.password_desc = password_desc;
    }

    public int getId_nivel() {
        return id_nivel;
    }

    public void setId_nivel(int id_nivel) {
        this.id_nivel = id_nivel;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id_usuario=" + id_usuario +
                ", usuario_nombres='" + usuario_nombres + '\'' +
                ", usuario_apater='" + usuario_apater + '\'' +
                ", usuario_amater='" + usuario_amater + '\'' +
                ", usuario_codigo='" + usuario_codigo + '\'' +
                ", usuario_password='" + usuario_password + '\'' +
                ", password_desc='" + password_desc + '\'' +
                ", id_nivel=" + id_nivel +
                ", codigo='" + codigo + '\'' +
                '}';
    }
}
