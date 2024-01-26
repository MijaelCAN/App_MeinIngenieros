package com.mijael.mein.Entidades;

public class RadiacionUv_RegistroDetalle {

    public int id_plan_trabajo_formato_reg;
    public String tipo_piel;
    public String color_piel;
    public String fuente_generadora;
    public String tipo_fuente;
    public String sombra_descanso;
    public String malla_oscura;
    public String prog_expo_radiacion;
    public String trab_aire_libre;
    public String mant_fuente;//se ha eliminado 5 registros

    public int epp_lentes_brillo;
    public int prot_lat;
    public int epp_gorro_2;
    public int epp_casco_2;
    public String epp_ninguno;
    public int prot_legion;
    public int prot_aancha;
    public int rop_ccerti;
    public int rop_coscuro;
    public int rop_mlarga;
    public int tgruesa;
    public int util_fps;
    public int guia_fps;
    public int frec_aplic;

    public String otra_frecuencia;
    public int cubre_nuca;
    public int lent_osc;

    public String otro_epp;
    public int estado;
    public String fec_reg;
    public String user_reg;

    public RadiacionUv_RegistroDetalle(int id_plan_trabajo_formato_reg, String tipo_piel, String color_piel, String fuente_generadora, String tipo_fuente, String sombra_descanso, String malla_oscura, String prog_expo_radiacion, String trab_aire_libre, String mant_fuente, int epp_lentes_brillo, int prot_lat, int epp_gorro_2, int epp_casco_2, String epp_ninguno, int prot_legion, int prot_aancha, int rop_ccerti, int rop_coscuro, int rop_mlarga, int tgruesa, int util_fps, int guia_fps, int frec_aplic, String otra_frecuencia, int cubre_nuca, int lent_osc, String otro_epp, String fec_reg, String user_reg) {
        this.id_plan_trabajo_formato_reg = id_plan_trabajo_formato_reg;
        this.tipo_piel = tipo_piel;
        this.color_piel = color_piel;
        this.fuente_generadora = fuente_generadora;
        this.tipo_fuente = tipo_fuente;
        this.sombra_descanso = sombra_descanso;
        this.malla_oscura = malla_oscura;
        this.prog_expo_radiacion = prog_expo_radiacion;
        this.trab_aire_libre = trab_aire_libre;
        this.mant_fuente = mant_fuente;
        this.epp_lentes_brillo = epp_lentes_brillo;
        this.prot_lat = prot_lat;
        this.epp_gorro_2 = epp_gorro_2;
        this.epp_casco_2 = epp_casco_2;
        this.epp_ninguno = epp_ninguno;
        this.prot_legion = prot_legion;
        this.prot_aancha = prot_aancha;
        this.rop_ccerti = rop_ccerti;
        this.rop_coscuro = rop_coscuro;
        this.rop_mlarga = rop_mlarga;
        this.tgruesa = tgruesa;
        this.util_fps = util_fps;
        this.guia_fps = guia_fps;
        this.frec_aplic = frec_aplic;
        this.otra_frecuencia = otra_frecuencia;
        this.cubre_nuca = cubre_nuca;
        this.lent_osc = lent_osc;
        this.otro_epp = otro_epp;
        this.fec_reg = fec_reg;
        this.user_reg = user_reg;
    }

    public int getId_plan_trabajo_formato_reg() {
        return id_plan_trabajo_formato_reg;
    }

    public void setId_plan_trabajo_formato_reg(int id_plan_trabajo_formato_reg) {
        this.id_plan_trabajo_formato_reg = id_plan_trabajo_formato_reg;
    }

    public String getTipo_piel() {
        return tipo_piel;
    }

    public void setTipo_piel(String tipo_piel) {
        this.tipo_piel = tipo_piel;
    }

    public String getColor_piel() {
        return color_piel;
    }

    public void setColor_piel(String color_piel) {
        this.color_piel = color_piel;
    }

    public String getFuente_generadora() {
        return fuente_generadora;
    }

    public void setFuente_generadora(String fuente_generadora) {
        this.fuente_generadora = fuente_generadora;
    }

    public String getTipo_fuente() {
        return tipo_fuente;
    }

    public void setTipo_fuente(String tipo_fuente) {
        this.tipo_fuente = tipo_fuente;
    }

    public String getSombra_descanso() {
        return sombra_descanso;
    }

    public void setSombra_descanso(String sombra_descanso) {
        this.sombra_descanso = sombra_descanso;
    }

    public String getMalla_oscura() {
        return malla_oscura;
    }

    public void setMalla_oscura(String malla_oscura) {
        this.malla_oscura = malla_oscura;
    }

    public String getProg_expo_radiacion() {
        return prog_expo_radiacion;
    }

    public void setProg_expo_radiacion(String prog_expo_radiacion) {
        this.prog_expo_radiacion = prog_expo_radiacion;
    }

    public String getTrab_aire_libre() {
        return trab_aire_libre;
    }

    public void setTrab_aire_libre(String trab_aire_libre) {
        this.trab_aire_libre = trab_aire_libre;
    }

    public String getMant_fuente() {
        return mant_fuente;
    }

    public void setMant_fuente(String mant_fuente) {
        this.mant_fuente = mant_fuente;
    }

    public int getEpp_lentes_brillo() {
        return epp_lentes_brillo;
    }

    public void setEpp_lentes_brillo(int epp_lentes_brillo) {
        this.epp_lentes_brillo = epp_lentes_brillo;
    }

    public int getProt_lat() {
        return prot_lat;
    }

    public void setProt_lat(int prot_lat) {
        this.prot_lat = prot_lat;
    }

    public int getEpp_gorro_2() {
        return epp_gorro_2;
    }

    public void setEpp_gorro_2(int epp_gorro_2) {
        this.epp_gorro_2 = epp_gorro_2;
    }

    public int getEpp_casco_2() {
        return epp_casco_2;
    }

    public void setEpp_casco_2(int epp_casco_2) {
        this.epp_casco_2 = epp_casco_2;
    }

    public String getEpp_ninguno() {
        return epp_ninguno;
    }

    public void setEpp_ninguno(String epp_ninguno) {
        this.epp_ninguno = epp_ninguno;
    }

    public int getProt_legion() {
        return prot_legion;
    }

    public void setProt_legion(int prot_legion) {
        this.prot_legion = prot_legion;
    }

    public int getProt_aancha() {
        return prot_aancha;
    }

    public void setProt_aancha(int prot_aancha) {
        this.prot_aancha = prot_aancha;
    }

    public int getRop_ccerti() {
        return rop_ccerti;
    }

    public void setRop_ccerti(int rop_ccerti) {
        this.rop_ccerti = rop_ccerti;
    }

    public int getRop_coscuro() {
        return rop_coscuro;
    }

    public void setRop_coscuro(int rop_coscuro) {
        this.rop_coscuro = rop_coscuro;
    }

    public int getRop_mlarga() {
        return rop_mlarga;
    }

    public void setRop_mlarga(int rop_mlarga) {
        this.rop_mlarga = rop_mlarga;
    }

    public int getTgruesa() {
        return tgruesa;
    }

    public void setTgruesa(int tgruesa) {
        this.tgruesa = tgruesa;
    }

    public int getUtil_fps() {
        return util_fps;
    }

    public void setUtil_fps(int util_fps) {
        this.util_fps = util_fps;
    }

    public int getGuia_fps() {
        return guia_fps;
    }

    public void setGuia_fps(int guia_fps) {
        this.guia_fps = guia_fps;
    }

    public int getFrec_aplic() {
        return frec_aplic;
    }

    public void setFrec_aplic(int frec_aplic) {
        this.frec_aplic = frec_aplic;
    }

    public String getOtra_frecuencia() {
        return otra_frecuencia;
    }

    public void setOtra_frecuencia(String otra_frecuencia) {
        this.otra_frecuencia = otra_frecuencia;
    }

    public int getCubre_nuca() {
        return cubre_nuca;
    }

    public void setCubre_nuca(int cubre_nuca) {
        this.cubre_nuca = cubre_nuca;
    }

    public int getLent_osc() {
        return lent_osc;
    }

    public void setLent_osc(int lent_osc) {
        this.lent_osc = lent_osc;
    }

    public String getOtro_epp() {
        return otro_epp;
    }

    public void setOtro_epp(String otro_epp) {
        this.otro_epp = otro_epp;
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
