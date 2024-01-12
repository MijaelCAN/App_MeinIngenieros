package com.mijael.mein.Entidades;

public class VelocidadAire_Registro {
    public int id;
    public String cod_formato;
    public String cod_registro;
    public String id_formato;
    public String id_plan_trabajo;
    public String id_pt_formato;
    public String id_equipo1;
    public String cod_equipo1;
    public String nom_equipo1;
    public String serie_eq1;
    public String id_analista;
    public String nom_analista;
    public String hora_inicial;
    public String hora_final;
    public String area_trabajo;
    public String actividades_realizadas;
    public String hora_trabajo;
    public String desc_area_trabajo;
    public String fec_monitoreo;
    public String observacion;
    public int estado;
    public String fec_reg;
    public String user_reg;

    public VelocidadAire_Registro(int id, String cod_formato, String cod_registro, String id_formato, String id_plan_trabajo, String id_pt_formato, String id_equipo1,
                                  String cod_equipo1, String nom_equipo1, String serie_eq1, String id_analista, String nom_analista, String hora_inicial, String hora_final,
                                  String area_trabajo, String actividades_realizadas, String hora_trabajo, String desc_area_trabajo, String fec_monitoreo, String observacion,
                                  String fec_reg, String user_reg) {
        this.id = id;
        this.cod_formato = cod_formato;
        this.cod_registro = cod_registro;
        this.id_formato = id_formato;
        this.id_plan_trabajo = id_plan_trabajo;
        this.id_pt_formato = id_pt_formato;
        this.id_equipo1 = id_equipo1;
        this.cod_equipo1 = cod_equipo1;
        this.nom_equipo1 = nom_equipo1;
        this.serie_eq1 = serie_eq1;
        this.id_analista = id_analista;
        this.nom_analista = nom_analista;
        this.hora_inicial = hora_inicial;
        this.hora_final = hora_final;
        this.area_trabajo = area_trabajo;
        this.actividades_realizadas = actividades_realizadas;
        this.hora_trabajo = hora_trabajo;
        this.desc_area_trabajo = desc_area_trabajo;
        this.fec_monitoreo = fec_monitoreo;
        this.observacion = observacion;
        this.fec_reg = fec_reg;
        this.user_reg = user_reg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCod_formato() {
        return cod_formato;
    }

    public void setCod_formato(String cod_formato) {
        this.cod_formato = cod_formato;
    }

    public String getCod_registro() {
        return cod_registro;
    }

    public void setCod_registro(String cod_registro) {
        this.cod_registro = cod_registro;
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

    public String getId_equipo1() {
        return id_equipo1;
    }

    public void setId_equipo1(String id_equipo1) {
        this.id_equipo1 = id_equipo1;
    }

    public String getCod_equipo1() {
        return cod_equipo1;
    }

    public void setCod_equipo1(String cod_equipo1) {
        this.cod_equipo1 = cod_equipo1;
    }

    public String getNom_equipo1() {
        return nom_equipo1;
    }

    public void setNom_equipo1(String nom_equipo1) {
        this.nom_equipo1 = nom_equipo1;
    }

    public String getSerie_eq1() {
        return serie_eq1;
    }

    public void setSerie_eq1(String serie_eq1) {
        this.serie_eq1 = serie_eq1;
    }

    public String getId_analista() {
        return id_analista;
    }

    public void setId_analista(String id_analista) {
        this.id_analista = id_analista;
    }

    public String getNom_analista() {
        return nom_analista;
    }

    public void setNom_analista(String nom_analista) {
        this.nom_analista = nom_analista;
    }

    public String getHora_inicial() {
        return hora_inicial;
    }

    public void setHora_inicial(String hora_inicial) {
        this.hora_inicial = hora_inicial;
    }

    public String getHora_final() {
        return hora_final;
    }

    public void setHora_final(String hora_final) {
        this.hora_final = hora_final;
    }

    public String getArea_trabajo() {
        return area_trabajo;
    }

    public void setArea_trabajo(String area_trabajo) {
        this.area_trabajo = area_trabajo;
    }

    public String getActividades_realizadas() {
        return actividades_realizadas;
    }

    public void setActividades_realizadas(String actividades_realizadas) {
        this.actividades_realizadas = actividades_realizadas;
    }

    public String getHora_trabajo() {
        return hora_trabajo;
    }

    public void setHora_trabajo(String hora_trabajo) {
        this.hora_trabajo = hora_trabajo;
    }

    public String getDesc_area_trabajo() {
        return desc_area_trabajo;
    }

    public void setDesc_area_trabajo(String desc_area_trabajo) {
        this.desc_area_trabajo = desc_area_trabajo;
    }

    public String getFec_monitoreo() {
        return fec_monitoreo;
    }

    public void setFec_monitoreo(String fec_monitoreo) {
        this.fec_monitoreo = fec_monitoreo;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getFec_reg() {
        return fec_reg;
    }

    public void setFec_reg(String fec_reg) {
        this.fec_reg = fec_reg;
    }

    public String getUser_reg() {
        return user_reg;
    }

    public void setUser_reg(String user_reg) {
        this.user_reg = user_reg;
    }
}
