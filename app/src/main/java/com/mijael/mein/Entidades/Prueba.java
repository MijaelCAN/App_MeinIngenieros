package com.mijael.mein.Entidades;

public class Prueba {
    private int id;
    private String id_formato;
    private String id_plan_trabajo;
    private String id_pt_formato;

    public Prueba(int id, String id_formato, String id_plan_trabajo, String id_pt_formato) {
        this.id = id;
        this.id_formato = id_formato;
        this.id_plan_trabajo = id_plan_trabajo;
        this.id_pt_formato = id_pt_formato;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId_formato() {
        return id_formato;
    }

    public void setId_formato(String id_formato) {
        this.id_formato = id_formato;
    }

    public String getId_plan_trabajo() {
        return id_plan_trabajo;
    }

    public void setId_plan_trabajo(String id_plan_trabajo) {
        this.id_plan_trabajo = id_plan_trabajo;
    }

    public String getId_pt_formato() {
        return id_pt_formato;
    }

    public void setId_pt_formato(String id_pt_formato) {
        this.id_pt_formato = id_pt_formato;
    }
}
