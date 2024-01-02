package com.mijael.mein.Entidades;

public class Formatos_Trabajo {
    private int id_pt_formato;
    private int id_plan_trabajo;
    private int id_formato;
    private String nom_formato;
    private int cantidad;
    private String nom_cliente;
    private int realizado;
    private int por_realizar;
    private int valida_gremision;

    public Formatos_Trabajo(int id_pt_formato, int id_plan_trabajo, int id_formato, String nom_formato, int cantidad, String nom_cliente, int realizado, int por_realizar, int valida_gremision) {
        this.id_pt_formato = id_pt_formato;
        this.id_plan_trabajo = id_plan_trabajo;
        this.id_formato = id_formato;
        this.nom_formato = nom_formato;
        this.cantidad = cantidad;
        this.nom_cliente = nom_cliente;
        this.realizado = realizado;
        this.por_realizar = por_realizar;
        this.valida_gremision = valida_gremision;
    }

    public int getId_pt_formato() {
        return id_pt_formato;
    }

    public void setId_pt_formato(int id_pt_formato) {
        this.id_pt_formato = id_pt_formato;
    }

    public int getId_plan_trabajo() {
        return id_plan_trabajo;
    }

    public void setId_plan_trabajo(int id_plan_trabajo) {
        this.id_plan_trabajo = id_plan_trabajo;
    }

    public int getId_formato() {
        return id_formato;
    }

    public void setId_formato(int id_formato) {
        this.id_formato = id_formato;
    }

    public String getNom_formato() {
        return nom_formato;
    }

    public void setNom_formato(String nom_formato) {
        this.nom_formato = nom_formato;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getNom_cliente() {
        return nom_cliente;
    }

    public void setNom_cliente(String nom_cliente) {
        this.nom_cliente = nom_cliente;
    }

    public int getRealizado() {
        return realizado;
    }

    public void setRealizado(int realizado) {
        this.realizado = realizado;
    }

    public int getPor_realizar() {
        return por_realizar;
    }

    public void setPor_realizar(int por_realizar) {
        this.por_realizar = por_realizar;
    }

    public int getValida_gremision() {
        return valida_gremision;
    }

    public void setValida_gremision(int valida_gremision) {
        this.valida_gremision = valida_gremision;
    }
}
