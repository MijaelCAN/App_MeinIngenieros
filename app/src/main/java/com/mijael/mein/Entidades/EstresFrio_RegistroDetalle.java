package com.mijael.mein.Entidades;

public class EstresFrio_RegistroDetalle {
    public int id_plan_trabajo_formato_reg;
    public String fuente_generadora;
    public String desc_fuente_frio;
    public int ropa_interior;
    public int camisa_blusa;
    public int pantalon;
    public int pullover;
    public int abrigo;
    public int chaqueta;
    public int forrada;
    /*public String ri_calz;
    public String ri_calzl;
    public String ri_camist;
    public String ri_camismc;
    public String ri_camisml;
    public String ri_sujetb;
    public String cb_mangac;
    public String cb_ligeramc;
    public String cb_normalml;
    public String cb_cfranml;
    public String cb_bligmc;
    public String p_cor;
    public String p_lig;
    public String p_norm;
    public String p_fran;
    public String pul_chasm;
    public String pul_lig;
    public String pul_med;
    public String pul_grue;
    public String pa_abri;
    public String pa_chal;
    public String pa_park;
    public String pa_monf;
    public String ch_lig;
    public String ch_chaq;
    public String ch_batat;
    public String ch_monot;*/
    public String d_zapsd;
    public String d_zapsg;
    public String d_calc;
    public String d_med;
    public String d_calcgc;
    public String d_calcgl;
    public String d_botas;
    public String d_guant;
    public String rotacion_personal;
    public String tiempo_recuperacion;
    public String dispensador;// NO ESTA EN EL FORMULARIO
    public String capa_expo_frio;
    public int id_nivel_d;
    public String nom_nivel_d;
    public int id_metodo_determ;
    public String metodo_determ;
    public int id_tipo_trabajo;
    public String tipo_trabajo;
    public String ocupacion;
    public String rango_tasa_metab;
    public String clase;
    public String actividad_deter;
    public String tasa_metab;
    public String tasa_metab_kcal;
    public String frecuencia_deter;
    public String genero_deter;
    public String ntareas;
    public String nom_tarea1;
    public String ciclo_trabajo1;
    public String posicion_1;
    public String pcuerpo_1;
    public String intensidad_1;
    public String nom_tarea2;
    public String ciclo_trabajo2;
    public String posicion_2;
    public String pcuerpo_2;
    public String intensidad_2;
    public String nom_tarea3;
    public String ciclo_trabajo3;
    public String posicion_3;
    public String pcuerpo_3;
    public String intensidad_3;
    public String nom_tarea4;
    public String ciclo_trabajo4;
    public String posicion_4;
    public String pcuerpo_4;
    public String intensidad_4;
    public String nom_tarea5;
    public String ciclo_trabajo5;
    public String posicion_5;
    public String pcuerpo_5;
    public String intensidad_5;
    public String mtr_subida;
    public String t_aire;
    public String t_globo;
    public String h_relativa;
    public String v_viento;
    public int estado;
    public String fec_reg;
    public String user_reg;

    public EstresFrio_RegistroDetalle(int id_plan_trabajo_formato_reg, String fuente_generadora, String desc_fuente_frio, int ropa_interior, int camisa_blusa, int pantalon, int pullover, int abrigo, int chaqueta,int forrada, String d_zapsd, String d_zapsg, String d_calc, String d_med, String d_calcgc, String d_calcgl, String d_botas, String d_guant, String rotacion_personal, String tiempo_recuperacion, String dispensador, String capa_expo_frio, int id_nivel_d, String nom_nivel_d, int id_metodo_determ, String metodo_determ, int id_tipo_trabajo, String tipo_trabajo, String ocupacion, String rango_tasa_metab, String clase, String actividad_deter, String tasa_metab, String tasa_metab_kcal, String frecuencia_deter, String genero_deter, String ntareas, String nom_tarea1, String ciclo_trabajo1, String posicion_1, String pcuerpo_1, String intensidad_1, String nom_tarea2, String ciclo_trabajo2, String posicion_2, String pcuerpo_2, String intensidad_2, String nom_tarea3, String ciclo_trabajo3, String posicion_3, String pcuerpo_3, String intensidad_3, String nom_tarea4, String ciclo_trabajo4, String posicion_4, String pcuerpo_4, String intensidad_4, String nom_tarea5, String ciclo_trabajo5, String posicion_5, String pcuerpo_5, String intensidad_5, String mtr_subida, String t_aire, String t_globo, String h_relativa, String v_viento, String fec_reg, String user_reg) {
        this.id_plan_trabajo_formato_reg = id_plan_trabajo_formato_reg;
        this.fuente_generadora = fuente_generadora;
        this.desc_fuente_frio = desc_fuente_frio;
        this.ropa_interior = ropa_interior;
        this.camisa_blusa = camisa_blusa;
        this.pantalon = pantalon;
        this.pullover = pullover;
        this.abrigo = abrigo;
        this.chaqueta = chaqueta;
        this.forrada = forrada;
        this.d_zapsd = d_zapsd;
        this.d_zapsg = d_zapsg;
        this.d_calc = d_calc;
        this.d_med = d_med;
        this.d_calcgc = d_calcgc;
        this.d_calcgl = d_calcgl;
        this.d_botas = d_botas;
        this.d_guant = d_guant;
        this.rotacion_personal = rotacion_personal;
        this.tiempo_recuperacion = tiempo_recuperacion;
        this.dispensador = dispensador;
        this.capa_expo_frio = capa_expo_frio;
        this.id_nivel_d = id_nivel_d;
        this.nom_nivel_d = nom_nivel_d;
        this.id_metodo_determ = id_metodo_determ;
        this.metodo_determ = metodo_determ;
        this.id_tipo_trabajo = id_tipo_trabajo;
        this.tipo_trabajo = tipo_trabajo;
        this.ocupacion = ocupacion;
        this.rango_tasa_metab = rango_tasa_metab;
        this.clase = clase;
        this.actividad_deter = actividad_deter;
        this.tasa_metab = tasa_metab;
        this.tasa_metab_kcal = tasa_metab_kcal;
        this.frecuencia_deter = frecuencia_deter;
        this.genero_deter = genero_deter;
        this.ntareas = ntareas;
        this.nom_tarea1 = nom_tarea1;
        this.ciclo_trabajo1 = ciclo_trabajo1;
        this.posicion_1 = posicion_1;
        this.pcuerpo_1 = pcuerpo_1;
        this.intensidad_1 = intensidad_1;
        this.nom_tarea2 = nom_tarea2;
        this.ciclo_trabajo2 = ciclo_trabajo2;
        this.posicion_2 = posicion_2;
        this.pcuerpo_2 = pcuerpo_2;
        this.intensidad_2 = intensidad_2;
        this.nom_tarea3 = nom_tarea3;
        this.ciclo_trabajo3 = ciclo_trabajo3;
        this.posicion_3 = posicion_3;
        this.pcuerpo_3 = pcuerpo_3;
        this.intensidad_3 = intensidad_3;
        this.nom_tarea4 = nom_tarea4;
        this.ciclo_trabajo4 = ciclo_trabajo4;
        this.posicion_4 = posicion_4;
        this.pcuerpo_4 = pcuerpo_4;
        this.intensidad_4 = intensidad_4;
        this.nom_tarea5 = nom_tarea5;
        this.ciclo_trabajo5 = ciclo_trabajo5;
        this.posicion_5 = posicion_5;
        this.pcuerpo_5 = pcuerpo_5;
        this.intensidad_5 = intensidad_5;
        this.mtr_subida = mtr_subida;
        this.t_aire = t_aire;
        this.t_globo = t_globo;
        this.h_relativa = h_relativa;
        this.v_viento = v_viento;
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

    public int getRopa_interior() {
        return ropa_interior;
    }

    public void setRopa_interior(int ropa_interior) {
        this.ropa_interior = ropa_interior;
    }

    public int getCamisa_blusa() {
        return camisa_blusa;
    }

    public void setCamisa_blusa(int camisa_blusa) {
        this.camisa_blusa = camisa_blusa;
    }

    public int getPantalon() {
        return pantalon;
    }

    public void setPantalon(int pantalon) {
        this.pantalon = pantalon;
    }

    public int getPullover() {
        return pullover;
    }

    public void setPullover(int pullover) {
        this.pullover = pullover;
    }

    public int getAbrigo() {
        return abrigo;
    }

    public void setAbrigo(int abrigo) {
        this.abrigo = abrigo;
    }

    public int getChaqueta() {
        return chaqueta;
    }

    public void setChaqueta(int chaqueta) {
        this.chaqueta = chaqueta;
    }

    public int getForrada() {
        return forrada;
    }

    public void setForrada(int forrada) {
        this.forrada = forrada;
    }

    public String getD_zapsd() {
        return d_zapsd;
    }

    public void setD_zapsd(String d_zapsd) {
        this.d_zapsd = d_zapsd;
    }

    public String getD_zapsg() {
        return d_zapsg;
    }

    public void setD_zapsg(String d_zapsg) {
        this.d_zapsg = d_zapsg;
    }

    public String getD_calc() {
        return d_calc;
    }

    public void setD_calc(String d_calc) {
        this.d_calc = d_calc;
    }

    public String getD_med() {
        return d_med;
    }

    public void setD_med(String d_med) {
        this.d_med = d_med;
    }

    public String getD_calcgc() {
        return d_calcgc;
    }

    public void setD_calcgc(String d_calcgc) {
        this.d_calcgc = d_calcgc;
    }

    public String getD_calcgl() {
        return d_calcgl;
    }

    public void setD_calcgl(String d_calcgl) {
        this.d_calcgl = d_calcgl;
    }

    public String getD_botas() {
        return d_botas;
    }

    public void setD_botas(String d_botas) {
        this.d_botas = d_botas;
    }

    public String getD_guant() {
        return d_guant;
    }

    public void setD_guant(String d_guant) {
        this.d_guant = d_guant;
    }

    public String getRotacion_personal() {
        return rotacion_personal;
    }

    public void setRotacion_personal(String rotacion_personal) {
        this.rotacion_personal = rotacion_personal;
    }

    public String getTiempo_recuperacion() {
        return tiempo_recuperacion;
    }

    public void setTiempo_recuperacion(String tiempo_recuperacion) {
        this.tiempo_recuperacion = tiempo_recuperacion;
    }

    public String getDispensador() {
        return dispensador;
    }

    public void setDispensador(String dispensador) {
        this.dispensador = dispensador;
    }

    public String getCapa_expo_frio() {
        return capa_expo_frio;
    }

    public void setCapa_expo_frio(String capa_expo_frio) {
        this.capa_expo_frio = capa_expo_frio;
    }

    public int getId_nivel_d() {
        return id_nivel_d;
    }

    public void setId_nivel_d(int id_nivel_d) {
        this.id_nivel_d = id_nivel_d;
    }

    public String getNom_nivel_d() {
        return nom_nivel_d;
    }

    public void setNom_nivel_d(String nom_nivel_d) {
        this.nom_nivel_d = nom_nivel_d;
    }

    public int getId_metodo_determ() {
        return id_metodo_determ;
    }

    public void setId_metodo_determ(int id_metodo_determ) {
        this.id_metodo_determ = id_metodo_determ;
    }

    public String getMetodo_determ() {
        return metodo_determ;
    }

    public void setMetodo_determ(String metodo_determ) {
        this.metodo_determ = metodo_determ;
    }

    public int getId_tipo_trabajo() {
        return id_tipo_trabajo;
    }

    public void setId_tipo_trabajo(int id_tipo_trabajo) {
        this.id_tipo_trabajo = id_tipo_trabajo;
    }

    public String getTipo_trabajo() {
        return tipo_trabajo;
    }

    public void setTipo_trabajo(String tipo_trabajo) {
        this.tipo_trabajo = tipo_trabajo;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public String getRango_tasa_metab() {
        return rango_tasa_metab;
    }

    public void setRango_tasa_metab(String rango_tasa_metab) {
        this.rango_tasa_metab = rango_tasa_metab;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getActividad_deter() {
        return actividad_deter;
    }

    public void setActividad_deter(String actividad_deter) {
        this.actividad_deter = actividad_deter;
    }

    public String getTasa_metab() {
        return tasa_metab;
    }

    public void setTasa_metab(String tasa_metab) {
        this.tasa_metab = tasa_metab;
    }

    public String getTasa_metab_kcal() {
        return tasa_metab_kcal;
    }

    public void setTasa_metab_kcal(String tasa_metab_kcal) {
        this.tasa_metab_kcal = tasa_metab_kcal;
    }

    public String getFrecuencia_deter() {
        return frecuencia_deter;
    }

    public void setFrecuencia_deter(String frecuencia_deter) {
        this.frecuencia_deter = frecuencia_deter;
    }

    public String getGenero_deter() {
        return genero_deter;
    }

    public void setGenero_deter(String genero_deter) {
        this.genero_deter = genero_deter;
    }

    public String getNtareas() {
        return ntareas;
    }

    public void setNtareas(String ntareas) {
        this.ntareas = ntareas;
    }

    public String getNom_tarea1() {
        return nom_tarea1;
    }

    public void setNom_tarea1(String nom_tarea1) {
        this.nom_tarea1 = nom_tarea1;
    }

    public String getCiclo_trabajo1() {
        return ciclo_trabajo1;
    }

    public void setCiclo_trabajo1(String ciclo_trabajo1) {
        this.ciclo_trabajo1 = ciclo_trabajo1;
    }

    public String getPosicion_1() {
        return posicion_1;
    }

    public void setPosicion_1(String posicion_1) {
        this.posicion_1 = posicion_1;
    }

    public String getPcuerpo_1() {
        return pcuerpo_1;
    }

    public void setPcuerpo_1(String pcuerpo_1) {
        this.pcuerpo_1 = pcuerpo_1;
    }

    public String getIntensidad_1() {
        return intensidad_1;
    }

    public void setIntensidad_1(String intensidad_1) {
        this.intensidad_1 = intensidad_1;
    }

    public String getNom_tarea2() {
        return nom_tarea2;
    }

    public void setNom_tarea2(String nom_tarea2) {
        this.nom_tarea2 = nom_tarea2;
    }

    public String getCiclo_trabajo2() {
        return ciclo_trabajo2;
    }

    public void setCiclo_trabajo2(String ciclo_trabajo2) {
        this.ciclo_trabajo2 = ciclo_trabajo2;
    }

    public String getPosicion_2() {
        return posicion_2;
    }

    public void setPosicion_2(String posicion_2) {
        this.posicion_2 = posicion_2;
    }

    public String getPcuerpo_2() {
        return pcuerpo_2;
    }

    public void setPcuerpo_2(String pcuerpo_2) {
        this.pcuerpo_2 = pcuerpo_2;
    }

    public String getIntensidad_2() {
        return intensidad_2;
    }

    public void setIntensidad_2(String intensidad_2) {
        this.intensidad_2 = intensidad_2;
    }

    public String getNom_tarea3() {
        return nom_tarea3;
    }

    public void setNom_tarea3(String nom_tarea3) {
        this.nom_tarea3 = nom_tarea3;
    }

    public String getCiclo_trabajo3() {
        return ciclo_trabajo3;
    }

    public void setCiclo_trabajo3(String ciclo_trabajo3) {
        this.ciclo_trabajo3 = ciclo_trabajo3;
    }

    public String getPosicion_3() {
        return posicion_3;
    }

    public void setPosicion_3(String posicion_3) {
        this.posicion_3 = posicion_3;
    }

    public String getPcuerpo_3() {
        return pcuerpo_3;
    }

    public void setPcuerpo_3(String pcuerpo_3) {
        this.pcuerpo_3 = pcuerpo_3;
    }

    public String getIntensidad_3() {
        return intensidad_3;
    }

    public void setIntensidad_3(String intensidad_3) {
        this.intensidad_3 = intensidad_3;
    }

    public String getNom_tarea4() {
        return nom_tarea4;
    }

    public void setNom_tarea4(String nom_tarea4) {
        this.nom_tarea4 = nom_tarea4;
    }

    public String getCiclo_trabajo4() {
        return ciclo_trabajo4;
    }

    public void setCiclo_trabajo4(String ciclo_trabajo4) {
        this.ciclo_trabajo4 = ciclo_trabajo4;
    }

    public String getPosicion_4() {
        return posicion_4;
    }

    public void setPosicion_4(String posicion_4) {
        this.posicion_4 = posicion_4;
    }

    public String getPcuerpo_4() {
        return pcuerpo_4;
    }

    public void setPcuerpo_4(String pcuerpo_4) {
        this.pcuerpo_4 = pcuerpo_4;
    }

    public String getIntensidad_4() {
        return intensidad_4;
    }

    public void setIntensidad_4(String intensidad_4) {
        this.intensidad_4 = intensidad_4;
    }

    public String getNom_tarea5() {
        return nom_tarea5;
    }

    public void setNom_tarea5(String nom_tarea5) {
        this.nom_tarea5 = nom_tarea5;
    }

    public String getCiclo_trabajo5() {
        return ciclo_trabajo5;
    }

    public void setCiclo_trabajo5(String ciclo_trabajo5) {
        this.ciclo_trabajo5 = ciclo_trabajo5;
    }

    public String getPosicion_5() {
        return posicion_5;
    }

    public void setPosicion_5(String posicion_5) {
        this.posicion_5 = posicion_5;
    }

    public String getPcuerpo_5() {
        return pcuerpo_5;
    }

    public void setPcuerpo_5(String pcuerpo_5) {
        this.pcuerpo_5 = pcuerpo_5;
    }

    public String getIntensidad_5() {
        return intensidad_5;
    }

    public void setIntensidad_5(String intensidad_5) {
        this.intensidad_5 = intensidad_5;
    }

    public String getMtr_subida() {
        return mtr_subida;
    }

    public void setMtr_subida(String mtr_subida) {
        this.mtr_subida = mtr_subida;
    }

    public String getT_aire() {
        return t_aire;
    }

    public void setT_aire(String t_aire) {
        this.t_aire = t_aire;
    }

    public String getT_globo() {
        return t_globo;
    }

    public void setT_globo(String t_globo) {
        this.t_globo = t_globo;
    }

    public String getH_relativa() {
        return h_relativa;
    }

    public void setH_relativa(String h_relativa) {
        this.h_relativa = h_relativa;
    }

    public String getV_viento() {
        return v_viento;
    }

    public void setV_viento(String v_viento) {
        this.v_viento = v_viento;
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
