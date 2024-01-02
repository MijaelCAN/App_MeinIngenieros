package com.mijael.mein.Entidades;

public class Orden_Trabajo {
    private String cod_ot;
    private String cod_cotizacion;
    private String nom_cliente;
    private String nom_tipo_servicio;
    private String fecha_registro;
    private String id_ot;
    private String id_plan_trabajo;
    private String dformatos;
    private String id_colaborador;
    private String id_coordinador;
    private String url_plan_trabajo;

    public Orden_Trabajo(String cod_ot, String cod_cotizacion, String nom_cliente, String nom_tipo_servicio, String fecha_registro, String id_ot, String id_plan_trabajo,
                         String dformatos, String id_colaborador, String id_coordinador, String url_plan_trabajo) {
        this.cod_ot = cod_ot;
        this.cod_cotizacion = cod_cotizacion;
        this.nom_cliente = nom_cliente;
        this.nom_tipo_servicio = nom_tipo_servicio;
        this.fecha_registro = fecha_registro;
        this.id_ot = id_ot;
        this.id_plan_trabajo = id_plan_trabajo;
        this.dformatos = dformatos;
        this.id_colaborador = id_colaborador;
        this.id_coordinador = id_coordinador;
        this.url_plan_trabajo = url_plan_trabajo;
    }

    public String getCod_ot() {
        return cod_ot;
    }

    public void setCod_ot(String cod_ot) {
        this.cod_ot = cod_ot;
    }

    public String getCod_cotizacion() {
        return cod_cotizacion;
    }

    public void setCod_cotizacion(String cod_cotizacion) {
        this.cod_cotizacion = cod_cotizacion;
    }

    public String getNom_cliente() {
        return nom_cliente;
    }

    public void setNom_cliente(String nom_cliente) {
        this.nom_cliente = nom_cliente;
    }

    public String getNom_tipo_servicio() {
        return nom_tipo_servicio;
    }

    public void setNom_tipo_servicio(String nom_tipo_servicio) {
        this.nom_tipo_servicio = nom_tipo_servicio;
    }

    public String getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(String fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public String getId_ot() {
        return id_ot;
    }

    public void setId_ot(String id_ot) {
        this.id_ot = id_ot;
    }

    public String getId_plan_trabajo() {
        return id_plan_trabajo;
    }

    public void setId_plan_trabajo(String id_plan_trabajo) {
        this.id_plan_trabajo = id_plan_trabajo;
    }

    public String getDformatos() {
        return dformatos;
    }

    public void setDformatos(String dformatos) {
        this.dformatos = dformatos;
    }

    public String getId_colaborador() {
        return id_colaborador;
    }

    public void setId_colaborador(String id_colaborador) {
        this.id_colaborador = id_colaborador;
    }

    public String getId_coordinador() {
        return id_coordinador;
    }

    public void setId_coordinador(String id_coordinador) {
        this.id_coordinador = id_coordinador;
    }

    public String getUrl_plan_trabajo() {
        return url_plan_trabajo;
    }

    public void setUrl_plan_trabajo(String url_plan_trabajo) {
        this.url_plan_trabajo = url_plan_trabajo;
    }

    @Override
    public String toString() {
        return "Orden_Trabajo{" +
                "cod_ot='" + cod_ot + '\'' +
                ", cod_cotizacion='" + cod_cotizacion + '\'' +
                ", nom_cliente='" + nom_cliente + '\'' +
                ", nom_tipo_servicio='" + nom_tipo_servicio + '\'' +
                ", fecha_registro='" + fecha_registro + '\'' +
                ", id_ot='" + id_ot + '\'' +
                ", id_plan_trabajo='" + id_plan_trabajo + '\'' +
                ", dformatos='" + dformatos + '\'' +
                ", id_colaborador='" + id_colaborador + '\'' +
                ", url_plan_trabajo='" + url_plan_trabajo + '\'' +
                '}';
    }
}
