package com.mijael.mein.Entidades;

public class RadiacionUv_Registro {
    public int id;
    public String cod_formato;
    public String cod_registro;
    public String id_formato;
    public String id_plan_trabajo;
    public String id_pt_formato;
    public String cod_equipo1;
    public String nom_equipo1;
    public String serie_eq1;
    public String id_equipo1;
    public String id_analista;
    public String nom_analista;
    public String verf_insitu;
    public String hora_situ;
    public String fec_monitoreo;
    public String hora_inicial;
    public String hora_final;
    public String tiempo_exposicion;
    public String jornada;
    public String tipo_doc_trabajador;
    public String num_doc_trabajador;
    public String nom_trabajador;
    public String puesto_trabajador;
    public String area_trabajo;
    public String actividades_realizadas;
    public String edad_trabajador;
    public String mes_ocu_cargo;
    public String anio_ocu_cargo;
    public String hora_trabajo;
    public String horario_refrigerio;
    public String regimen_laboral;
    public String desc_area_trabajo;
    public String otro_administrativo;
    public String resultado;
    public String otro_ingenieria;
    public int estado;
    public String fec_reg;
    public String user_reg;
    private String ruta_foto;
    public RadiacionUv_Registro(int id, String cod_formato, String cod_registro, String id_formato, String id_plan_trabajo, String id_pt_formato, String cod_equipo1, String nom_equipo1,
                                String serie_eq1, String id_equipo1, String id_analista, String nom_analista, String verf_insitu, String hora_situ, String fec_monitoreo,
                                String hora_inicial, String hora_final, String tiempo_exposicion, String jornada, String tipo_doc_trabajador, String num_doc_trabajador,
                                String nom_trabajador, String puesto_trabajador, String area_trabajo, String actividades_realizadas, String edad_trabajador, String mes_ocu_cargo,
                                String anio_ocu_cargo, String hora_trabajo, String horario_refrigerio, String regimen_laboral, String desc_area_trabajo, String otro_administrativo,
                                String resultado, String otro_ingenieria, String fec_reg, String user_reg, String ruta_foto ) {
        this.id = id;
        this.cod_formato = cod_formato;
        this.cod_registro = cod_registro;
        this.id_formato = id_formato;
        this.id_plan_trabajo = id_plan_trabajo;
        this.id_pt_formato = id_pt_formato;
        this.cod_equipo1 = cod_equipo1;
        this.nom_equipo1 = nom_equipo1;
        this.serie_eq1 = serie_eq1;
        this.id_equipo1 = id_equipo1;
        this.id_analista = id_analista;
        this.nom_analista = nom_analista;
        this.verf_insitu = verf_insitu;
        this.hora_situ = hora_situ;
        this.fec_monitoreo = fec_monitoreo;
        this.hora_inicial = hora_inicial;
        this.hora_final = hora_final;
        this.tiempo_exposicion = tiempo_exposicion;
        this.jornada = jornada;
        this.tipo_doc_trabajador = tipo_doc_trabajador;
        this.num_doc_trabajador = num_doc_trabajador;
        this.nom_trabajador = nom_trabajador;
        this.puesto_trabajador = puesto_trabajador;
        this.area_trabajo = area_trabajo;
        this.actividades_realizadas = actividades_realizadas;
        this.edad_trabajador = edad_trabajador;
        this.mes_ocu_cargo = mes_ocu_cargo;
        this.anio_ocu_cargo = anio_ocu_cargo;
        this.hora_trabajo = hora_trabajo;
        this.horario_refrigerio = horario_refrigerio;
        this.regimen_laboral = regimen_laboral;
        this.desc_area_trabajo = desc_area_trabajo;
        this.otro_administrativo = otro_administrativo;
        this.resultado = resultado;
        this.otro_ingenieria = otro_ingenieria;
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

    public String getSerie_eq1() {
        return serie_eq1;
    }

    public void setSerie_eq1(String serie_eq1) {
        this.serie_eq1 = serie_eq1;
    }

    public String getId_equipo1() {
        return id_equipo1;
    }

    public void setId_equipo1(String id_equipo1) {
        this.id_equipo1 = id_equipo1;
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

    public String getVerf_insitu() {
        return verf_insitu;
    }

    public void setVerf_insitu(String verf_insitu) {
        this.verf_insitu = verf_insitu;
    }

    public String getHora_situ() {
        return hora_situ;
    }

    public void setHora_situ(String hora_situ) {
        this.hora_situ = hora_situ;
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

    public String getHora_final() {
        return hora_final;
    }

    public void setHora_final(String hora_final) {
        this.hora_final = hora_final;
    }

    public String getTiempo_exposicion() {
        return tiempo_exposicion;
    }

    public void setTiempo_exposicion(String tiempo_exposicion) {
        this.tiempo_exposicion = tiempo_exposicion;
    }

    public String getJornada() {
        return jornada;
    }

    public void setJornada(String jornada) {
        this.jornada = jornada;
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

    public String getActividades_realizadas() {
        return actividades_realizadas;
    }

    public void setActividades_realizadas(String actividades_realizadas) {
        this.actividades_realizadas = actividades_realizadas;
    }

    public String getEdad_trabajador() {
        return edad_trabajador;
    }

    public void setEdad_trabajador(String edad_trabajador) {
        this.edad_trabajador = edad_trabajador;
    }

    public String getMes_ocu_cargo() {
        return mes_ocu_cargo;
    }

    public void setMes_ocu_cargo(String mes_ocu_cargo) {
        this.mes_ocu_cargo = mes_ocu_cargo;
    }

    public String getAnio_ocu_cargo() {
        return anio_ocu_cargo;
    }

    public void setAnio_ocu_cargo(String anio_ocu_cargo) {
        this.anio_ocu_cargo = anio_ocu_cargo;
    }

    public String getHora_trabajo() {
        return hora_trabajo;
    }

    public void setHora_trabajo(String hora_trabajo) {
        this.hora_trabajo = hora_trabajo;
    }

    public String getHorario_refrigerio() {
        return horario_refrigerio;
    }

    public void setHorario_refrigerio(String horario_refrigerio) {
        this.horario_refrigerio = horario_refrigerio;
    }

    public String getRegimen_laboral() {
        return regimen_laboral;
    }

    public void setRegimen_laboral(String regimen_laboral) {
        this.regimen_laboral = regimen_laboral;
    }

    public String getDesc_area_trabajo() {
        return desc_area_trabajo;
    }

    public void setDesc_area_trabajo(String desc_area_trabajo) {
        this.desc_area_trabajo = desc_area_trabajo;
    }

    public String getOtro_administrativo() {
        return otro_administrativo;
    }

    public void setOtro_administrativo(String otro_administrativo) {
        this.otro_administrativo = otro_administrativo;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getOtro_ingenieria() {
        return otro_ingenieria;
    }

    public void setOtro_ingenieria(String otro_ingenieria) {
        this.otro_ingenieria = otro_ingenieria;
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
