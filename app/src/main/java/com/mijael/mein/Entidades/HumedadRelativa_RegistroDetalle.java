package com.mijael.mein.Entidades;

public class HumedadRelativa_RegistroDetalle {
    public int id_plan_trabajo_formato_reg;
    public String tecnica_acondaire;
    public String detalle_tecnica_acondaire;
    public String h_relativa;
    public String h_relativa_2;
    public int estado;
    public String fec_reg;
    public String user_reg;

    public HumedadRelativa_RegistroDetalle(int id_plan_trabajo_formato_reg, String tecnica_acondaire, String detalle_tecnica_acondaire, String h_relativa, String h_relativa_2, String fec_reg, String user_reg) {
        this.id_plan_trabajo_formato_reg = id_plan_trabajo_formato_reg;
        this.tecnica_acondaire = tecnica_acondaire;
        this.detalle_tecnica_acondaire = detalle_tecnica_acondaire;
        this.h_relativa = h_relativa;
        this.h_relativa_2 = h_relativa_2;
        this.fec_reg = fec_reg;
        this.user_reg = user_reg;
    }

    public int getId_plan_trabajo_formato_reg() {
        return id_plan_trabajo_formato_reg;
    }

    public void setId_plan_trabajo_formato_reg(int id_plan_trabajo_formato_reg) {
        this.id_plan_trabajo_formato_reg = id_plan_trabajo_formato_reg;
    }

    public String getTecnica_acondaire() {
        return tecnica_acondaire;
    }

    public void setTecnica_acondaire(String tecnica_acondaire) {
        this.tecnica_acondaire = tecnica_acondaire;
    }

    public String getDetalle_tecnica_acondaire() {
        return detalle_tecnica_acondaire;
    }

    public void setDetalle_tecnica_acondaire(String detalle_tecnica_acondaire) {
        this.detalle_tecnica_acondaire = detalle_tecnica_acondaire;
    }

    public String getH_relativa() {
        return h_relativa;
    }

    public void setH_relativa(String h_relativa) {
        this.h_relativa = h_relativa;
    }

    public String getH_relativa_2() {
        return h_relativa_2;
    }

    public void setH_relativa_2(String h_relativa_2) {
        this.h_relativa_2 = h_relativa_2;
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
