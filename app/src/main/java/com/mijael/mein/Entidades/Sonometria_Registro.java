package com.mijael.mein.Entidades;

public class Sonometria_Registro {
    private int id;
    private String cod_formato;
    private String cod_registro;
    private String id_formato;
    private String id_plan_trabajo;
    private String id_pt_formato;
    private String cod_equipo1;
    private String nom_equipo1;
    private String cod_equipo2;
    private String nom_equipo2;
    private String cod_equipo3;
    private String nom_equipo3;
    private String serie_eq1;
    private String serie_eq2;
    private String serie_eq3;
    private String id_equipo1;
    private String id_equipo2;
    private String id_equipo3;
    private String id_analista;
    private String nom_analista;
    private String hora_situ;
    private String nivel;
    private String variacion;
    private String area_trabajo;
    private String actividades_realizadas;
    private String id_horario;
    private String hora_trabajo;
    private String n_personas;
    private String ruido_generado_por;
    private String area_req_concentr;
    private String lim_max_permis;
    private String fec_monitoreo;
    private String hora_inicial;
    private String tiempo_medicion;
    private String hora_final;
    private String v_viento;
    private String h_relativa;
    private String lmin;
    private String lmax;
    private String lequi;

    public String lequi_md1; //AGREGADO RECIENTEMENTE INICIO
    public String lequi_md2;
    public String lequi_md3;
    public String lequi_md4;
    public String lequi_md5;
    public String lmax_md1;
    public String lmax_md2;
    public String lmax_md3;
    public String lmax_md4;
    public String lmax_md5;
    public String lmin_md1;
    public String lmin_md2;
    public String lmin_md3;
    public String lmin_md4;
    public String lmin_md5; //AGREAGDO RECUIENTEMENTE FINAL


    private String ctrl_ingenieria;
    private String ctrl_administrativo;
    private String aislamiento;
    private String cabinas;
    private String otro_ingenieria;
    private String capacitacion;
    private String senializacion_precion;
    private String senializacion_epp;
    private String rotacion;
    private String tiempo_exposicion;
    private String otro_administrativo;
    private String observacion;
    private String tapones_au;
    private String marca_tapones_audi;
    private String modelo_tapones_audi;
    private String nrr_tapones_audi;
    private String orejereas;
    private String marca_orejeras;
    private String modelo_orejeras;
    private String nrr_orejeras;
    private String jornada;
    private int estado;
    private String fec_reg;
    private String user_reg;
    private String ruta_foto;

    public Sonometria_Registro(int id, String cod_formato, String cod_registro, String id_formato, String id_plan_trabajo, String id_pt_formato, String cod_equipo1, String nom_equipo1, String cod_equipo2,
                               String nom_equipo2, String cod_equipo3, String nom_equipo3, String serie_eq1, String serie_eq2, String serie_eq3, String id_equipo1, String id_equipo2,
                               String id_equipo3, String id_analista, String nom_analista, String hora_situ, String nivel, String variacion, String area_trabajo, String actividades_realizadas,
                               String id_horario, String hora_trabajo, String n_personas, String ruido_generado_por, String area_req_concentr, String lim_max_permis, String fec_monitoreo,
                               String hora_inicial, String tiempo_medicion, String hora_final, String v_viento, String h_relativa, String lmin, String lmax, String lequi, String lequi_md1,
                               String lequi_md2, String lequi_md3, String lequi_md4, String lequi_md5, String lmax_md1, String lmax_md2, String lmax_md3, String lmax_md4, String lmax_md5,
                               String lmin_md1, String lmin_md2, String lmin_md3, String lmin_md4, String lmin_md5, String ctrl_ingenieria, String ctrl_administrativo, String aislamiento,
                               String cabinas, String otro_ingenieria, String capacitacion, String senializacion_precion, String senializacion_epp, String rotacion, String tiempo_exposicion,
                               String otro_administrativo, String observacion, String tapones_au, String marca_tapones_audi, String modelo_tapones_audi, String nrr_tapones_audi, String orejereas,
                               String marca_orejeras, String modelo_orejeras, String nrr_orejeras, String jornada, String fec_reg, String user_reg, String ruta_foto) {
        this.id = id;
        this.cod_formato = cod_formato;
        this.cod_registro = cod_registro;
        this.id_formato = id_formato;
        this.id_plan_trabajo = id_plan_trabajo;
        this.id_pt_formato = id_pt_formato;
        this.cod_equipo1 = cod_equipo1;
        this.nom_equipo1 = nom_equipo1;
        this.cod_equipo2 = cod_equipo2;
        this.nom_equipo2 = nom_equipo2;
        this.cod_equipo3 = cod_equipo3;
        this.nom_equipo3 = nom_equipo3;
        this.serie_eq1 = serie_eq1;
        this.serie_eq2 = serie_eq2;
        this.serie_eq3 = serie_eq3;
        this.id_equipo1 = id_equipo1;
        this.id_equipo2 = id_equipo2;
        this.id_equipo3 = id_equipo3;
        this.id_analista = id_analista;
        this.nom_analista = nom_analista;
        this.hora_situ = hora_situ;
        this.nivel = nivel;
        this.variacion = variacion;
        this.area_trabajo = area_trabajo;
        this.actividades_realizadas = actividades_realizadas;
        this.id_horario = id_horario;
        this.hora_trabajo = hora_trabajo;
        this.n_personas = n_personas;
        this.ruido_generado_por = ruido_generado_por;
        this.area_req_concentr = area_req_concentr;
        this.lim_max_permis = lim_max_permis;
        this.fec_monitoreo = fec_monitoreo;
        this.hora_inicial = hora_inicial;
        this.tiempo_medicion = tiempo_medicion;
        this.hora_final = hora_final;
        this.v_viento = v_viento;
        this.h_relativa = h_relativa;
        this.lmin = lmin;
        this.lmax = lmax;
        this.lequi = lequi;
        this.lequi_md1 = lequi_md1;
        this.lequi_md2 = lequi_md2;
        this.lequi_md3 = lequi_md3;
        this.lequi_md4 = lequi_md4;
        this.lequi_md5 = lequi_md5;
        this.lmax_md1 = lmax_md1;
        this.lmax_md2 = lmax_md2;
        this.lmax_md3 = lmax_md3;
        this.lmax_md4 = lmax_md4;
        this.lmax_md5 = lmax_md5;
        this.lmin_md1 = lmin_md1;
        this.lmin_md2 = lmin_md2;
        this.lmin_md3 = lmin_md3;
        this.lmin_md4 = lmin_md4;
        this.lmin_md5 = lmin_md5;
        this.ctrl_ingenieria = ctrl_ingenieria;
        this.ctrl_administrativo = ctrl_administrativo;
        this.aislamiento = aislamiento;
        this.cabinas = cabinas;
        this.otro_ingenieria = otro_ingenieria;
        this.capacitacion = capacitacion;
        this.senializacion_precion = senializacion_precion;
        this.senializacion_epp = senializacion_epp;
        this.rotacion = rotacion;
        this.tiempo_exposicion = tiempo_exposicion;
        this.otro_administrativo = otro_administrativo;
        this.observacion = observacion;
        this.tapones_au = tapones_au;
        this.marca_tapones_audi = marca_tapones_audi;
        this.modelo_tapones_audi = modelo_tapones_audi;
        this.nrr_tapones_audi = nrr_tapones_audi;
        this.orejereas = orejereas;
        this.marca_orejeras = marca_orejeras;
        this.modelo_orejeras = modelo_orejeras;
        this.nrr_orejeras = nrr_orejeras;
        this.jornada = jornada;
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

    public String getCod_equipo2() {
        return cod_equipo2;
    }

    public void setCod_equipo2(String cod_equipo2) {
        this.cod_equipo2 = cod_equipo2;
    }

    public String getNom_equipo2() {
        return nom_equipo2;
    }

    public void setNom_equipo2(String nom_equipo2) {
        this.nom_equipo2 = nom_equipo2;
    }

    public String getCod_equipo3() {
        return cod_equipo3;
    }

    public void setCod_equipo3(String cod_equipo3) {
        this.cod_equipo3 = cod_equipo3;
    }

    public String getNom_equipo3() {
        return nom_equipo3;
    }

    public void setNom_equipo3(String nom_equipo3) {
        this.nom_equipo3 = nom_equipo3;
    }

    public String getSerie_eq1() {
        return serie_eq1;
    }

    public void setSerie_eq1(String serie_eq1) {
        this.serie_eq1 = serie_eq1;
    }

    public String getSerie_eq2() {
        return serie_eq2;
    }

    public void setSerie_eq2(String serie_eq2) {
        this.serie_eq2 = serie_eq2;
    }

    public String getSerie_eq3() {
        return serie_eq3;
    }

    public void setSerie_eq3(String serie_eq3) {
        this.serie_eq3 = serie_eq3;
    }

    public String getId_equipo1() {
        return id_equipo1;
    }

    public void setId_equipo1(String id_equipo1) {
        this.id_equipo1 = id_equipo1;
    }

    public String getId_equipo2() {
        return id_equipo2;
    }

    public void setId_equipo2(String id_equipo2) {
        this.id_equipo2 = id_equipo2;
    }

    public String getId_equipo3() {
        return id_equipo3;
    }

    public void setId_equipo3(String id_equipo3) {
        this.id_equipo3 = id_equipo3;
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

    public String getHora_situ() {
        return hora_situ;
    }

    public void setHora_situ(String hora_situ) {
        this.hora_situ = hora_situ;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getVariacion() {
        return variacion;
    }

    public void setVariacion(String variacion) {
        this.variacion = variacion;
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

    public String getId_horario() {
        return id_horario;
    }

    public void setId_horario(String id_horario) {
        this.id_horario = id_horario;
    }

    public String getHora_trabajo() {
        return hora_trabajo;
    }

    public void setHora_trabajo(String hora_trabajo) {
        this.hora_trabajo = hora_trabajo;
    }

    public String getN_personas() {
        return n_personas;
    }

    public void setN_personas(String n_personas) {
        this.n_personas = n_personas;
    }

    public String getRuido_generado_por() {
        return ruido_generado_por;
    }

    public void setRuido_generado_por(String ruido_generado_por) {
        this.ruido_generado_por = ruido_generado_por;
    }

    public String getArea_req_concentr() {
        return area_req_concentr;
    }

    public void setArea_req_concentr(String area_req_concentr) {
        this.area_req_concentr = area_req_concentr;
    }

    public String getLim_max_permis() {
        return lim_max_permis;
    }

    public void setLim_max_permis(String lim_max_permis) {
        this.lim_max_permis = lim_max_permis;
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

    public String getTiempo_medicion() {
        return tiempo_medicion;
    }

    public void setTiempo_medicion(String tiempo_medicion) {
        this.tiempo_medicion = tiempo_medicion;
    }

    public String getHora_final() {
        return hora_final;
    }

    public void setHora_final(String hora_final) {
        this.hora_final = hora_final;
    }

    public String getV_viento() {
        return v_viento;
    }

    public void setV_viento(String v_viento) {
        this.v_viento = v_viento;
    }

    public String getH_relativa() {
        return h_relativa;
    }

    public void setH_relativa(String h_relativa) {
        this.h_relativa = h_relativa;
    }

    public String getLmin() {
        return lmin;
    }

    public void setLmin(String lmin) {
        this.lmin = lmin;
    }

    public String getLmax() {
        return lmax;
    }

    public void setLmax(String lmax) {
        this.lmax = lmax;
    }

    public String getLequi() {
        return lequi;
    }

    public void setLequi(String lequi) {
        this.lequi = lequi;
    }


    public String getLequi_md1() {
        return lequi_md1;
    }

    public void setLequi_md1(String lequi_md1) {
        this.lequi_md1 = lequi_md1;
    }

    public String getLequi_md2() {
        return lequi_md2;
    }

    public void setLequi_md2(String lequi_md2) {
        this.lequi_md2 = lequi_md2;
    }

    public String getLequi_md3() {
        return lequi_md3;
    }

    public void setLequi_md3(String lequi_md3) {
        this.lequi_md3 = lequi_md3;
    }

    public String getLequi_md4() {
        return lequi_md4;
    }

    public void setLequi_md4(String lequi_md4) {
        this.lequi_md4 = lequi_md4;
    }

    public String getLequi_md5() {
        return lequi_md5;
    }

    public void setLequi_md5(String lequi_md5) {
        this.lequi_md5 = lequi_md5;
    }

    public String getLmax_md1() {
        return lmax_md1;
    }

    public void setLmax_md1(String lmax_md1) {
        this.lmax_md1 = lmax_md1;
    }

    public String getLmax_md2() {
        return lmax_md2;
    }

    public void setLmax_md2(String lmax_md2) {
        this.lmax_md2 = lmax_md2;
    }

    public String getLmax_md3() {
        return lmax_md3;
    }

    public void setLmax_md3(String lmax_md3) {
        this.lmax_md3 = lmax_md3;
    }

    public String getLmax_md4() {
        return lmax_md4;
    }

    public void setLmax_md4(String lmax_md4) {
        this.lmax_md4 = lmax_md4;
    }

    public String getLmax_md5() {
        return lmax_md5;
    }

    public void setLmax_md5(String lmax_md5) {
        this.lmax_md5 = lmax_md5;
    }

    public String getLmin_md1() {
        return lmin_md1;
    }

    public void setLmin_md1(String lmin_md1) {
        this.lmin_md1 = lmin_md1;
    }

    public String getLmin_md2() {
        return lmin_md2;
    }

    public void setLmin_md2(String lmin_md2) {
        this.lmin_md2 = lmin_md2;
    }

    public String getLmin_md3() {
        return lmin_md3;
    }

    public void setLmin_md3(String lmin_md3) {
        this.lmin_md3 = lmin_md3;
    }

    public String getLmin_md4() {
        return lmin_md4;
    }

    public void setLmin_md4(String lmin_md4) {
        this.lmin_md4 = lmin_md4;
    }

    public String getLmin_md5() {
        return lmin_md5;
    }

    public void setLmin_md5(String lmin_md5) {
        this.lmin_md5 = lmin_md5;
    }

    public String getCtrl_ingenieria() {
        return ctrl_ingenieria;
    }

    public void setCtrl_ingenieria(String ctrl_ingenieria) {
        this.ctrl_ingenieria = ctrl_ingenieria;
    }

    public String getCtrl_administrativo() {
        return ctrl_administrativo;
    }

    public void setCtrl_administrativo(String ctrl_administrativo) {
        this.ctrl_administrativo = ctrl_administrativo;
    }

    public String getAislamiento() {
        return aislamiento;
    }

    public void setAislamiento(String aislamiento) {
        this.aislamiento = aislamiento;
    }

    public String getCabinas() {
        return cabinas;
    }

    public void setCabinas(String cabinas) {
        this.cabinas = cabinas;
    }

    public String getOtro_ingenieria() {
        return otro_ingenieria;
    }

    public void setOtro_ingenieria(String otro_ingenieria) {
        this.otro_ingenieria = otro_ingenieria;
    }

    public String getCapacitacion() {
        return capacitacion;
    }

    public void setCapacitacion(String capacitacion) {
        this.capacitacion = capacitacion;
    }

    public String getSenializacion_precion() {
        return senializacion_precion;
    }

    public void setSenializacion_precion(String senializacion_precion) {
        this.senializacion_precion = senializacion_precion;
    }

    public String getSenializacion_epp() {
        return senializacion_epp;
    }

    public void setSenializacion_epp(String senializacion_epp) {
        this.senializacion_epp = senializacion_epp;
    }

    public String getRotacion() {
        return rotacion;
    }

    public void setRotacion(String rotacion) {
        this.rotacion = rotacion;
    }

    public String getTiempo_exposicion() {
        return tiempo_exposicion;
    }

    public void setTiempo_exposicion(String tiempo_exposicion) {
        this.tiempo_exposicion = tiempo_exposicion;
    }

    public String getOtro_administrativo() {
        return otro_administrativo;
    }

    public void setOtro_administrativo(String otro_administrativo) {
        this.otro_administrativo = otro_administrativo;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getTapones_au() {
        return tapones_au;
    }

    public void setTapones_au(String tapones_au) {
        this.tapones_au = tapones_au;
    }

    public String getMarca_tapones_audi() {
        return marca_tapones_audi;
    }

    public void setMarca_tapones_audi(String marca_tapones_audi) {
        this.marca_tapones_audi = marca_tapones_audi;
    }

    public String getModelo_tapones_audi() {
        return modelo_tapones_audi;
    }

    public void setModelo_tapones_audi(String modelo_tapones_audi) {
        this.modelo_tapones_audi = modelo_tapones_audi;
    }

    public String getNrr_tapones_audi() {
        return nrr_tapones_audi;
    }

    public void setNrr_tapones_audi(String nrr_tapones_audi) {
        this.nrr_tapones_audi = nrr_tapones_audi;
    }

    public String getOrejereas() {
        return orejereas;
    }

    public void setOrejereas(String orejereas) {
        this.orejereas = orejereas;
    }

    public String getMarca_orejeras() {
        return marca_orejeras;
    }

    public void setMarca_orejeras(String marca_orejeras) {
        this.marca_orejeras = marca_orejeras;
    }

    public String getModelo_orejeras() {
        return modelo_orejeras;
    }

    public void setModelo_orejeras(String modelo_orejeras) {
        this.modelo_orejeras = modelo_orejeras;
    }

    public String getNrr_orejeras() {
        return nrr_orejeras;
    }

    public void setNrr_orejeras(String nrr_orejeras) {
        this.nrr_orejeras = nrr_orejeras;
    }

    public String getJornada() {
        return jornada;
    }

    public void setJornada(String jornada) {
        this.jornada = jornada;
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

    public String getRuta_foto() {
        return ruta_foto;
    }

    public void setRuta_foto(String ruta_foto) {
        this.ruta_foto = ruta_foto;
    }
}
