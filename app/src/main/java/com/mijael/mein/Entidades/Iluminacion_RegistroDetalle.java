package com.mijael.mein.Entidades;

public class Iluminacion_RegistroDetalle {
    private int id_plan_trabajo_formato_reg;
    private  String tipo_iluminacion;
    private String id_tipo_medicion_ilu;
    private String tipo_medicion_ilu;
    private String larg_escrit;
    private String anch_escrit;
    private String num_pmedicion;
    private String alt_pltrabajo;

    private String long_salon;
    private String anch_salon;
    private String alt_pltrabajo_ilu;
    private String const_salon;
    private String num_min_pmedic;
    private String cant_iluminarias;
    public String puntos_med; //AGREGADO RECIENTEMENETE
    private String plan_mantenimiento_ilum;
    private String area_trabajo_m2;
    private String altura_p_trabajo;
    private String n_lamparas;
    private String altura_p_luminaria;
    private String color_pared;
    private String color_piso;
    private String estado_fisico;
    public int estado;
    private String fec_reg;
    private String user_reg;

    public Iluminacion_RegistroDetalle(int id_plan_trabajo_formato_reg, String tipo_iluminacion, String id_tipo_medicion_ilu, String tipo_medicion_ilu, String larg_escrit, String anch_escrit, String num_pmedicion, String alt_pltrabajo, String long_salon, String anch_salon, String alt_pltrabajo_ilu, String const_salon, String num_min_pmedic, String cant_iluminarias, String puntos_med, String plan_mantenimiento_ilum, String area_trabajo_m2, String altura_p_trabajo, String n_lamparas, String altura_p_luminaria, String color_pared, String color_piso, String estado_fisico, String fec_reg, String user_reg) {
        this.id_plan_trabajo_formato_reg = id_plan_trabajo_formato_reg;
        this.tipo_iluminacion = tipo_iluminacion;
        this.id_tipo_medicion_ilu = id_tipo_medicion_ilu;
        this.tipo_medicion_ilu = tipo_medicion_ilu;
        this.larg_escrit = larg_escrit;
        this.anch_escrit = anch_escrit;
        this.num_pmedicion = num_pmedicion;
        this.alt_pltrabajo = alt_pltrabajo;
        this.long_salon = long_salon;
        this.anch_salon = anch_salon;
        this.alt_pltrabajo_ilu = alt_pltrabajo_ilu;
        this.const_salon = const_salon;
        this.num_min_pmedic = num_min_pmedic;
        this.cant_iluminarias = cant_iluminarias;
        this.puntos_med = puntos_med;
        this.plan_mantenimiento_ilum = plan_mantenimiento_ilum;
        this.area_trabajo_m2 = area_trabajo_m2;
        this.altura_p_trabajo = altura_p_trabajo;
        this.n_lamparas = n_lamparas;
        this.altura_p_luminaria = altura_p_luminaria;
        this.color_pared = color_pared;
        this.color_piso = color_piso;
        this.estado_fisico = estado_fisico;
        this.fec_reg = fec_reg;
        this.user_reg = user_reg;
    }

    public int getId_plan_trabajo_formato_reg() {
        return id_plan_trabajo_formato_reg;
    }

    public void setId_plan_trabajo_formato_reg(int id_plan_trabajo_formato_reg) {
        this.id_plan_trabajo_formato_reg = id_plan_trabajo_formato_reg;
    }

    public String getTipo_iluminacion() {
        return tipo_iluminacion;
    }

    public void setTipo_iluminacion(String tipo_iluminacion) {
        this.tipo_iluminacion = tipo_iluminacion;
    }

    public String getId_tipo_medicion_ilu() {
        return id_tipo_medicion_ilu;
    }

    public void setId_tipo_medicion_ilu(String id_tipo_medicion_ilu) {
        this.id_tipo_medicion_ilu = id_tipo_medicion_ilu;
    }

    public String getTipo_medicion_ilu() {
        return tipo_medicion_ilu;
    }

    public void setTipo_medicion_ilu(String tipo_medicion_ilu) {
        this.tipo_medicion_ilu = tipo_medicion_ilu;
    }

    public String getLarg_escrit() {
        return larg_escrit;
    }

    public void setLarg_escrit(String larg_escrit) {
        this.larg_escrit = larg_escrit;
    }

    public String getAnch_escrit() {
        return anch_escrit;
    }

    public void setAnch_escrit(String anch_escrit) {
        this.anch_escrit = anch_escrit;
    }

    public String getNum_pmedicion() {
        return num_pmedicion;
    }

    public void setNum_pmedicion(String num_pmedicion) {
        this.num_pmedicion = num_pmedicion;
    }

    public String getAlt_pltrabajo() {
        return alt_pltrabajo;
    }

    public void setAlt_pltrabajo(String alt_pltrabajo) {
        this.alt_pltrabajo = alt_pltrabajo;
    }

    public String getLong_salon() {
        return long_salon;
    }

    public void setLong_salon(String long_salon) {
        this.long_salon = long_salon;
    }

    public String getAnch_salon() {
        return anch_salon;
    }

    public void setAnch_salon(String anch_salon) {
        this.anch_salon = anch_salon;
    }

    public String getAlt_pltrabajo_ilu() {
        return alt_pltrabajo_ilu;
    }

    public void setAlt_pltrabajo_ilu(String alt_pltrabajo_ilu) {
        this.alt_pltrabajo_ilu = alt_pltrabajo_ilu;
    }

    public String getConst_salon() {
        return const_salon;
    }

    public void setConst_salon(String const_salon) {
        this.const_salon = const_salon;
    }

    public String getNum_min_pmedic() {
        return num_min_pmedic;
    }

    public void setNum_min_pmedic(String num_min_pmedic) {
        this.num_min_pmedic = num_min_pmedic;
    }

    public String getCant_iluminarias() {
        return cant_iluminarias;
    }

    public void setCant_iluminarias(String cant_iluminarias) {
        this.cant_iluminarias = cant_iluminarias;
    }

    public String getPuntos_med() {
        return puntos_med;
    }

    public void setPuntos_med(String puntos_med) {
        this.puntos_med = puntos_med;
    }

    public String getPlan_mantenimiento_ilum() {
        return plan_mantenimiento_ilum;
    }

    public void setPlan_mantenimiento_ilum(String plan_mantenimiento_ilum) {
        this.plan_mantenimiento_ilum = plan_mantenimiento_ilum;
    }

    public String getArea_trabajo_m2() {
        return area_trabajo_m2;
    }

    public void setArea_trabajo_m2(String area_trabajo_m2) {
        this.area_trabajo_m2 = area_trabajo_m2;
    }

    public String getAltura_p_trabajo() {
        return altura_p_trabajo;
    }

    public void setAltura_p_trabajo(String altura_p_trabajo) {
        this.altura_p_trabajo = altura_p_trabajo;
    }

    public String getN_lamparas() {
        return n_lamparas;
    }

    public void setN_lamparas(String n_lamparas) {
        this.n_lamparas = n_lamparas;
    }

    public String getAltura_p_luminaria() {
        return altura_p_luminaria;
    }

    public void setAltura_p_luminaria(String altura_p_luminaria) {
        this.altura_p_luminaria = altura_p_luminaria;
    }

    public String getColor_pared() {
        return color_pared;
    }

    public void setColor_pared(String color_pared) {
        this.color_pared = color_pared;
    }

    public String getColor_piso() {
        return color_piso;
    }

    public void setColor_piso(String color_piso) {
        this.color_piso = color_piso;
    }

    public String getEstado_fisico() {
        return estado_fisico;
    }

    public void setEstado_fisico(String estado_fisico) {
        this.estado_fisico = estado_fisico;
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
