package com.mijael.mein.Entidades;

public class Equipos {
    private int id_equipo_registro;
    private String cod_equipo;
    private String modelo;
    private String proveedor;
    private String serie;
    private String codigo;
    private int cantidad;
    private float alquiler;
    private String observaciones;
    private String estado;
    private String nom_marca;
    private String nombre;
    private String nom_modelo;
    private String cod_monedaf;
    private String vigencia;
    private String fecha_calibracion;
    private String fecha_vencimiento;

    public Equipos(int id_equipo_registro, String cod_equipo, String modelo, String proveedor, String serie, String codigo,
                   int cantidad, float alquiler, String observaciones, String estado, String nom_marca, String nombre,
                   String nom_modelo, String cod_monedaf, String vigencia, String fecha_calibracion, String fecha_vencimiento) {
        this.id_equipo_registro = id_equipo_registro;
        this.cod_equipo = cod_equipo;
        this.modelo = modelo;
        this.proveedor = proveedor;
        this.serie = serie;
        this.codigo = codigo;
        this.cantidad = cantidad;
        this.alquiler = alquiler;
        this.observaciones = observaciones;
        this.estado = estado;
        this.nom_marca = nom_marca;
        this.nombre = nombre;
        this.nom_modelo = nom_modelo;
        this.cod_monedaf = cod_monedaf;
        this.vigencia = vigencia;
        this.fecha_calibracion = fecha_calibracion;
        this.fecha_vencimiento = fecha_vencimiento;
    }

    public int getId_equipo_registro() {
        return id_equipo_registro;
    }

    public void setId_equipo_registro(int id_equipo_registro) {
        this.id_equipo_registro = id_equipo_registro;
    }

    public String getCod_equipo() {
        return cod_equipo;
    }

    public void setCod_equipo(String cod_equipo) {
        this.cod_equipo = cod_equipo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getAlquiler() {
        return alquiler;
    }

    public void setAlquiler(float alquiler) {
        this.alquiler = alquiler;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNom_marca() {
        return nom_marca;
    }

    public void setNom_marca(String nom_marca) {
        this.nom_marca = nom_marca;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNom_modelo() {
        return nom_modelo;
    }

    public void setNom_modelo(String nom_modelo) {
        this.nom_modelo = nom_modelo;
    }

    public String getCod_monedaf() {
        return cod_monedaf;
    }

    public void setCod_monedaf(String cod_monedaf) {
        this.cod_monedaf = cod_monedaf;
    }

    public String getVigencia() {
        return vigencia;
    }

    public void setVigencia(String vigencia) {
        this.vigencia = vigencia;
    }

    public String getFecha_calibracion() {
        return fecha_calibracion;
    }

    public void setFecha_calibracion(String fecha_calibracion) {
        this.fecha_calibracion = fecha_calibracion;
    }

    public String getFecha_vencimiento() {
        return fecha_vencimiento;
    }

    public void setFecha_vencimiento(String fecha_vencimiento) {
        this.fecha_vencimiento = fecha_vencimiento;
    }
}
