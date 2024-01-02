package com.mijael.mein.Entidades;

public class Vibracion_RegistroDetalle {
    public int id_plan_trabajo_formato_reg;
    public String fuente_generadora;
    public String desc_fuente_frio;
    public String frecuencia;
    public String epp_botas;
    public String epp_guantes;
    public String epp_casco;
    public String epp_orejeras;
    public String otro_epp;
    public String x;
    public String y;
    public String z;
    public int estado;
    public String fec_reg;
    public String user_reg;

    public Vibracion_RegistroDetalle(int id_plan_trabajo_formato_reg, String fuente_generadora, String desc_fuente_frio, String frecuencia, String epp_botas, String epp_guantes,
                                     String epp_casco, String epp_orejeras, String otro_epp, String x, String y, String z, String fec_reg, String user_reg) {
        this.id_plan_trabajo_formato_reg = id_plan_trabajo_formato_reg;
        this.fuente_generadora = fuente_generadora;
        this.desc_fuente_frio = desc_fuente_frio;
        this.frecuencia = frecuencia;
        this.epp_botas = epp_botas;
        this.epp_guantes = epp_guantes;
        this.epp_casco = epp_casco;
        this.epp_orejeras = epp_orejeras;
        this.otro_epp = otro_epp;
        this.x = x;
        this.y = y;
        this.z = z;
        this.estado = 1;
        this.fec_reg = fec_reg;
        this.user_reg = user_reg;
    }

    public int getId_plan_trabajo_formato_reg() {
        return id_plan_trabajo_formato_reg;
    }

    public void setId_plan_trabajo_formato_reg(int id_plan_trabajo_formato_reg) {
        this.id_plan_trabajo_formato_reg = id_plan_trabajo_formato_reg;
    }

    public String getFuente_generadora() {
        return fuente_generadora;
    }

    public void setFuente_generadora(String fuente_generadora) {
        this.fuente_generadora = fuente_generadora;
    }

    public String getDesc_fuente_frio() {
        return desc_fuente_frio;
    }

    public void setDesc_fuente_frio(String desc_fuente_frio) {
        this.desc_fuente_frio = desc_fuente_frio;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getEpp_botas() {
        return epp_botas;
    }

    public void setEpp_botas(String epp_botas) {
        this.epp_botas = epp_botas;
    }

    public String getEpp_guantes() {
        return epp_guantes;
    }

    public void setEpp_guantes(String epp_guantes) {
        this.epp_guantes = epp_guantes;
    }

    public String getEpp_casco() {
        return epp_casco;
    }

    public void setEpp_casco(String epp_casco) {
        this.epp_casco = epp_casco;
    }

    public String getEpp_orejeras() {
        return epp_orejeras;
    }

    public void setEpp_orejeras(String epp_orejeras) {
        this.epp_orejeras = epp_orejeras;
    }

    public String getOtro_epp() {
        return otro_epp;
    }

    public void setOtro_epp(String otro_epp) {
        this.otro_epp = otro_epp;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getZ() {
        return z;
    }

    public void setZ(String z) {
        this.z = z;
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
