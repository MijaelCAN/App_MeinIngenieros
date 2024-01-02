package com.mijael.mein.Entidades;

public class RadiacionElect_RegistroDetalle {
    public int id_plan_trabajo_formato_reg;
    public String fuente_generadora;
    public String vestimenta_personal;
    public String x;
    public String x2;
    public String x3;
    public String x4;
    public String y;
    public String y2;
    public String y3;
    public String y4;
    public String z;
    public String z2;
    public String z3;
    public String z4;
    public int estado;
    public String fec_reg;
    public String user_reg;

    public RadiacionElect_RegistroDetalle(int id_plan_trabajo_formato_reg, String fuente_generadora, String vestimenta_personal, String x, String x2, String x3, String x4,
                                          String y, String y2, String y3, String y4, String z, String z2, String z3, String z4, String fec_reg, String user_reg) {
        this.id_plan_trabajo_formato_reg = id_plan_trabajo_formato_reg;
        this.fuente_generadora = fuente_generadora;
        this.vestimenta_personal = vestimenta_personal;
        this.x = x;
        this.x2 = x2;
        this.x3 = x3;
        this.x4 = x4;
        this.y = y;
        this.y2 = y2;
        this.y3 = y3;
        this.y4 = y4;
        this.z = z;
        this.z2 = z2;
        this.z3 = z3;
        this.z4 = z4;
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

    public String getVestimenta_personal() {
        return vestimenta_personal;
    }

    public void setVestimenta_personal(String vestimenta_personal) {
        this.vestimenta_personal = vestimenta_personal;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getX2() {
        return x2;
    }

    public void setX2(String x2) {
        this.x2 = x2;
    }

    public String getX3() {
        return x3;
    }

    public void setX3(String x3) {
        this.x3 = x3;
    }

    public String getX4() {
        return x4;
    }

    public void setX4(String x4) {
        this.x4 = x4;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getY2() {
        return y2;
    }

    public void setY2(String y2) {
        this.y2 = y2;
    }

    public String getY3() {
        return y3;
    }

    public void setY3(String y3) {
        this.y3 = y3;
    }

    public String getY4() {
        return y4;
    }

    public void setY4(String y4) {
        this.y4 = y4;
    }

    public String getZ() {
        return z;
    }

    public void setZ(String z) {
        this.z = z;
    }

    public String getZ2() {
        return z2;
    }

    public void setZ2(String z2) {
        this.z2 = z2;
    }

    public String getZ3() {
        return z3;
    }

    public void setZ3(String z3) {
        this.z3 = z3;
    }

    public String getZ4() {
        return z4;
    }

    public void setZ4(String z4) {
        this.z4 = z4;
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
