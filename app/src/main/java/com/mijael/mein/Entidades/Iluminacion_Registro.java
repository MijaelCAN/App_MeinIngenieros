package com.mijael.mein.Entidades;

public class Iluminacion_Registro {
    private int id;
    private String cod_formato;
    private String cod_registro;
    private String id_formato;
    private String id_plan_trabajo;
    private String id_pt_formato;
    private String id_analista;
    private String nom_analista;
    private String id_equipo1;
    private String cod_equipo1;
    private String nom_equipo1;
    private String serie_eq1;
    private String hora_situ;
    private String luz;
    private String tipo_doc_trabajador;
    private String num_doc_trabajador;
    private String nom_trabajador;
    private String puesto_trabajador;
    private String area_trabajo;
    private String n_personas;
    private String hora_trabajo;
    private String regimen_laboral;
    private String fec_monitoreo;
    private String hora_inicial;
    private String actividades_realizadas;
    private String observacion;
    private String ubic_equip;
    private String tarea_visual;
    private String tipo_tarea_visual;
    private String nivel_min_ilu;
    private String estado;
    private String fec_reg;
    private String user_reg;
    private String ruta_foto;

    public Iluminacion_Registro(int id, String cod_formato, String cod_registro, String id_formato, String id_plan_trabajo, String id_pt_formato, String id_analista, String nom_analista, String id_equipo1,
                                String cod_equipo1, String nom_equipo1, String serie_eq1, String hora_situ, String luz, String tipo_doc_trabajador, String num_doc_trabajador,
                                String nom_trabajador, String puesto_trabajador, String area_trabajo, String n_personas, String hora_trabajo, String regimen_laboral, String fec_monitoreo,
                                String hora_inicial, String actividades_realizadas, String observacion, String ubic_equip, String tarea_visual, String tipo_tarea_visual,
                                String nivel_min_ilu, String fec_reg, String user_reg, String ruta_foto) {
        this.id = id;
        this.cod_formato = cod_formato;
        this.cod_registro = cod_registro;
        this.id_formato = id_formato;
        this.id_plan_trabajo = id_plan_trabajo;
        this.id_pt_formato = id_pt_formato;
        this.id_analista = id_analista;
        this.nom_analista = nom_analista;
        this.id_equipo1 = id_equipo1;
        this.cod_equipo1 = cod_equipo1;
        this.nom_equipo1 = nom_equipo1;
        this.serie_eq1 = serie_eq1;
        this.hora_situ = hora_situ;
        this.luz = luz;
        this.tipo_doc_trabajador = tipo_doc_trabajador;
        this.num_doc_trabajador = num_doc_trabajador;
        this.nom_trabajador = nom_trabajador;
        this.puesto_trabajador = puesto_trabajador;
        this.area_trabajo = area_trabajo;
        this.n_personas = n_personas;
        this.hora_trabajo = hora_trabajo;
        this.regimen_laboral = regimen_laboral;
        this.fec_monitoreo = fec_monitoreo;
        this.hora_inicial = hora_inicial;
        this.actividades_realizadas = actividades_realizadas;
        this.observacion = observacion;
        this.ubic_equip = ubic_equip;
        this.tarea_visual = tarea_visual;
        this.tipo_tarea_visual = tipo_tarea_visual;
        this.nivel_min_ilu = nivel_min_ilu;
        this.fec_reg = fec_reg;
        this.user_reg = user_reg;
        this.ruta_foto = ruta_foto;
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

    public String getHora_situ() {
        return hora_situ;
    }

    public void setHora_situ(String hora_situ) {
        this.hora_situ = hora_situ;
    }

    public String getLuz() {
        return luz;
    }

    public void setLuz(String luz) {
        this.luz = luz;
    }

    public String getTipo_doc_trabajador() {
        return tipo_doc_trabajador;
    }

    public void setTipo_doc_trabajador(String tipo_doc_trabajador) {
        this.tipo_doc_trabajador = tipo_doc_trabajador;
    }

    public String getNum_doc_trabajador() {
        return num_doc_trabajador;
    }

    public void setNum_doc_trabajador(String num_doc_trabajador) {
        this.num_doc_trabajador = num_doc_trabajador;
    }

    public String getNom_trabajador() {
        return nom_trabajador;
    }

    public void setNom_trabajador(String nom_trabajador) {
        this.nom_trabajador = nom_trabajador;
    }

    public String getPuesto_trabajador() {
        return puesto_trabajador;
    }

    public void setPuesto_trabajador(String puesto_trabajador) {
        this.puesto_trabajador = puesto_trabajador;
    }

    public String getArea_trabajo() {
        return area_trabajo;
    }

    public void setArea_trabajo(String area_trabajo) {
        this.area_trabajo = area_trabajo;
    }

    public String getN_personas() {
        return n_personas;
    }

    public void setN_personas(String n_personas) {
        this.n_personas = n_personas;
    }

    public String getHora_trabajo() {
        return hora_trabajo;
    }

    public void setHora_trabajo(String hora_trabajo) {
        this.hora_trabajo = hora_trabajo;
    }

    public String getRegimen_laboral() {
        return regimen_laboral;
    }

    public void setRegimen_laboral(String regimen_laboral) {
        this.regimen_laboral = regimen_laboral;
    }

    public String getFec_monitoreo() {
        return fec_monitoreo;
    }

    public void setFec_monitoreo(String fec_monitoreo) {
        this.fec_monitoreo = fec_monitoreo;
    }

    public String getHora_inicial() {
        return hora_inicial;
    }

    public void setHora_inicial(String hora_inicial) {
        this.hora_inicial = hora_inicial;
    }

    public String getActividades_realizadas() {
        return actividades_realizadas;
    }

    public void setActividades_realizadas(String actividades_realizadas) {
        this.actividades_realizadas = actividades_realizadas;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getUbic_equip() {
        return ubic_equip;
    }

    public void setUbic_equip(String ubic_equip) {
        this.ubic_equip = ubic_equip;
    }

    public String getTarea_visual() {
        return tarea_visual;
    }

    public void setTarea_visual(String tarea_visual) {
        this.tarea_visual = tarea_visual;
    }

    public String getTipo_tarea_visual() {
        return tipo_tarea_visual;
    }

    public void setTipo_tarea_visual(String tipo_tarea_visual) {
        this.tipo_tarea_visual = tipo_tarea_visual;
    }

    public String getNivel_min_ilu() {
        return nivel_min_ilu;
    }

    public void setNivel_min_ilu(String nivel_min_ilu) {
        this.nivel_min_ilu = nivel_min_ilu;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
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

    public String getRuta_foto() {
        return ruta_foto;
    }

    public void setRuta_foto(String ruta_foto) {
        this.ruta_foto = ruta_foto;
    }
}
