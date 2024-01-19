package com.mijael.mein.Entidades;

public class RegistroFormatos {
    public int id_plan_trabajo_formato_reg;
    public int id_formato;
    public String cod_registro;
    public String cod_formato;
    public String id_plan_trabajo;
    public String id_pt_formato;
    public int id_analista;
    public String nom_analista;
    public String cod_equipo1;
    public String nom_equipo1;
    public String cod_equipo2;
    public String nom_equipo2;
    public String cod_equipo3;
    public String nom_equipo3;
    public String serie_eq1;
    public String serie_eq2;
    public String serie_eq3;
    public int id_equipo1;
    public int id_equipo2;
    public int id_equipo3;
    public String verf_insitu;
    public String hora_situ;
    public String nivel;
    public String variacion;
    public String luz;
    public String area_trabajo;
    public String desc_area_trabajo;
    public String area_trab_deta;
    public String actividades_realizadas;
    public int id_horario;
    public String hora_trabajo;
    public int n_personas;
    public String fec_monitoreo;
    public String hora_inicial;
    public String tiempo_medicion;
    public String hora_final;
    public String v_viento;
    public String h_relativa;
    public String hora_jornada;
    public String min_jornada;
    public String hora_exposicion;
    public String min_exposicion;
    public String jornada;
    public String nom_trabajador;
    public String tipo_doc_trabajador;
    public String num_doc_trabajador;
    public String puesto_trabajador;
    public int edad_trabajador;
    public String peso_trabajador;
    public String regimen_laboral;
    public String horario_refrigerio;
    public int tiempo_ocupado;
    public String molestia_oido;
    public String enfermedad_oido;
    public String detalle_enf_oido;
    public String fecha_ultimo_examen;
    public String mes_ultimo_examen;
    public String anio_ultimo_examen;
    public String ctrl_ingenieria;
    public String ctrl_administrativo;
    public String nom_epp; //no esta en la tabla general
    public String prot_auditivo;
    public String nom_ctrl_ingenieria;
    public String nom_ctrl_admin; //pendiente
    public String aislamiento;
    public String techos;
    public String cabinas;
    public String orientacion;
    public String cerramiento;
    public String otro_ingenieria;
    public String capacitacion;
    public String senializacion_precion;
    public String senializacion_epp;
    public String senializacion_vibracion;
    public String senializacion_area;
    public String mantenimiento_vibracion;
    public String rotacion;
    public String aislante_detalle;
    public String cabina_detalle;
    public String capacitacion_detalle;
    public String senializacion_nivel_detalle;
    public String senializacion_epp_detalle;
    public String rotacion_detalle;
    public String tiempo_exposicion_detalle;
    public String tapones_au;
    public String marca_tapones_audi;
    public String modelo_tapones_audi;
    public String orejereas;
    public String marca_orejeras;
    public String modelo_orejeras;
    public String nrr_orejeras;
    public String nrr_tapones_audi;
    public String adm_tiempo_expo;
    public String tiempo_exposicion;
    public String otro_administrativo;
    public String tareas_medicion;
    public String tarea_visual;
    public String tipo_tarea_visual;
    public String nivel_min_ilu;
    public String observacion;
    public String ch_ruido_externo;
    public String ch_ruido_antiguo;
    public String ch_ruido_generado_por;
    public String ruido_generado_por;
    public String otro_ruido;
    public String area_req_concentr;
    public String lim_max_permis;
    public String tipo_medicion;
    public String nom_tipo_medicion;
    public double lmin;
    public double lmax;
    public double lequi;

    public double lequi_md1; //AGREGADO RECIENTEMENTE INICIO
    public double lequi_md2;
    public double lequi_md3;
    public double lequi_md4;
    public double lequi_md5;
    public double lmax_md1;
    public double lmax_md2;
    public double lmax_md3;
    public double lmax_md4;
    public double lmax_md5;
    public double lmin_md1;
    public double lmin_md2;
    public double lmin_md3;
    public double lmin_md4;
    public double lmin_md5;//AGREGADO RECIENTEMENTE FINAL

    public double lpico_dba;
    public String conformidad;
    public String eval_20p;
    public String tipo_vibracion;
    public String ubic_equip;
    public String anio_ocu_cargo;
    public String mes_ocu_cargo;
    public String cond_trab;
    public String porc_descan;
    public String foto;
    public String resultado;
    public int estado_resultado;
    public int estado;
    public String fec_reg;
    public int user_reg;
    public String fec_act;
    public int user_act;
    public String fec_eli;
    public int user_eli;

    public RegistroFormatos(int id_plan_trabajo_formato_reg, int id_formato, String cod_registro, String cod_formato, String id_plan_trabajo, String id_pt_formato,
                            int id_analista, String nom_analista, String cod_equipo1, String nom_equipo1, String cod_equipo2, String nom_equipo2, String cod_equipo3,
                            String nom_equipo3, String serie_eq1, String serie_eq2, String serie_eq3, int id_equipo1, int id_equipo2, int id_equipo3, String verf_insitu,
                            String hora_situ, String nivel, String variacion, String luz, String area_trabajo, String desc_area_trabajo, String area_trab_deta,
                            String actividades_realizadas, int id_horario, String hora_trabajo, int n_personas, String fec_monitoreo, String hora_inicial,
                            String tiempo_medicion, String hora_final, String v_viento, String h_relativa, String hora_jornada, String min_jornada, String hora_exposicion,
                            String min_exposicion, String jornada, String nom_trabajador, String tipo_doc_trabajador, String num_doc_trabajador, String puesto_trabajador,
                            int edad_trabajador, String peso_trabajador, String regimen_laboral, String horario_refrigerio, int tiempo_ocupado, String molestia_oido,
                            String enfermedad_oido, String detalle_enf_oido, String fecha_ultimo_examen, String mes_ultimo_examen, String anio_ultimo_examen,
                            String ctrl_ingenieria, String ctrl_administrativo, String nom_epp, String prot_auditivo, String nom_ctrl_ingenieria, String nom_ctrl_admin,
                            String aislamiento, String techos, String cabinas, String orientacion, String cerramiento, String otro_ingenieria, String capacitacion,
                            String senializacion_precion, String senializacion_epp, String senializacion_vibracion, String senializacion_area, String mantenimiento_vibracion,
                            String rotacion, String aislante_detalle, String cabina_detalle, String capacitacion_detalle, String senializacion_nivel_detalle,
                            String senializacion_epp_detalle, String rotacion_detalle, String tiempo_exposicion_detalle, String tapones_au, String marca_tapones_audi,
                            String modelo_tapones_audi, String orejereas, String marca_orejeras, String modelo_orejeras, String nrr_orejeras, String nrr_tapones_audi,
                            String adm_tiempo_expo, String tiempo_exposicion, String otro_administrativo, String tareas_medicion, String tarea_visual,
                            String tipo_tarea_visual, String nivel_min_ilu, String observacion, String ch_ruido_externo, String ch_ruido_antiguo,
                            String ch_ruido_generado_por, String ruido_generado_por, String otro_ruido, String area_req_concentr, String lim_max_permis,
                            String tipo_medicion, String nom_tipo_medicion, double lmin, double lmax, double lequi, double lequi_md1, double lequi_md2, double lequi_md3,
                            double lequi_md4, double lequi_md5, double lmax_md1, double lmax_md2, double lmax_md3, double lmax_md4, double lmax_md5, double lmin_md1,
                            double lmin_md2, double lmin_md3, double lmin_md4, double lmin_md5, double lpico_dba, String conformidad, String eval_20p, String tipo_vibracion,
                            String ubic_equip, String anio_ocu_cargo, String mes_ocu_cargo, String cond_trab,String porc_descan, String foto, String resultado, int estado_resultado,
                            int estado, String fec_reg, int user_reg, String fec_act, int user_act, String fec_eli, int user_eli) {
        this.id_plan_trabajo_formato_reg = id_plan_trabajo_formato_reg;
        this.id_formato = id_formato;
        this.cod_registro = cod_registro;
        this.cod_formato = cod_formato;
        this.id_plan_trabajo = id_plan_trabajo;
        this.id_pt_formato = id_pt_formato;
        this.id_analista = id_analista;
        this.nom_analista = nom_analista;
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
        this.verf_insitu = verf_insitu;
        this.hora_situ = hora_situ;
        this.nivel = nivel;
        this.variacion = variacion;
        this.luz = luz;
        this.area_trabajo = area_trabajo;
        this.desc_area_trabajo = desc_area_trabajo;
        this.area_trab_deta = area_trab_deta;
        this.actividades_realizadas = actividades_realizadas;
        this.id_horario = id_horario;
        this.hora_trabajo = hora_trabajo;
        this.n_personas = n_personas;
        this.fec_monitoreo = fec_monitoreo;
        this.hora_inicial = hora_inicial;
        this.tiempo_medicion = tiempo_medicion;
        this.hora_final = hora_final;
        this.v_viento = v_viento;
        this.h_relativa = h_relativa;
        this.hora_jornada = hora_jornada;
        this.min_jornada = min_jornada;
        this.hora_exposicion = hora_exposicion;
        this.min_exposicion = min_exposicion;
        this.jornada = jornada;
        this.nom_trabajador = nom_trabajador;
        this.tipo_doc_trabajador = tipo_doc_trabajador;
        this.num_doc_trabajador = num_doc_trabajador;
        this.puesto_trabajador = puesto_trabajador;
        this.edad_trabajador = edad_trabajador;
        this.peso_trabajador = peso_trabajador;
        this.regimen_laboral = regimen_laboral;
        this.horario_refrigerio = horario_refrigerio;
        this.tiempo_ocupado = tiempo_ocupado;
        this.molestia_oido = molestia_oido;
        this.enfermedad_oido = enfermedad_oido;
        this.detalle_enf_oido = detalle_enf_oido;
        this.fecha_ultimo_examen = fecha_ultimo_examen;
        this.mes_ultimo_examen = mes_ultimo_examen;
        this.anio_ultimo_examen = anio_ultimo_examen;
        this.ctrl_ingenieria = ctrl_ingenieria;
        this.ctrl_administrativo = ctrl_administrativo;
        this.nom_epp = nom_epp;
        this.prot_auditivo = prot_auditivo;
        this.nom_ctrl_ingenieria = nom_ctrl_ingenieria;
        this.nom_ctrl_admin = nom_ctrl_admin;
        this.aislamiento = aislamiento;
        this.techos = techos;
        this.cabinas = cabinas;
        this.orientacion = orientacion;
        this.cerramiento = cerramiento;
        this.otro_ingenieria = otro_ingenieria;
        this.capacitacion = capacitacion;
        this.senializacion_precion = senializacion_precion;
        this.senializacion_epp = senializacion_epp;
        this.senializacion_vibracion = senializacion_vibracion;
        this.senializacion_area = senializacion_area;
        this.mantenimiento_vibracion = mantenimiento_vibracion;
        this.rotacion = rotacion;
        this.aislante_detalle = aislante_detalle;
        this.cabina_detalle = cabina_detalle;
        this.capacitacion_detalle = capacitacion_detalle;
        this.senializacion_nivel_detalle = senializacion_nivel_detalle;
        this.senializacion_epp_detalle = senializacion_epp_detalle;
        this.rotacion_detalle = rotacion_detalle;
        this.tiempo_exposicion_detalle = tiempo_exposicion_detalle;
        this.tapones_au = tapones_au;
        this.marca_tapones_audi = marca_tapones_audi;
        this.modelo_tapones_audi = modelo_tapones_audi;
        this.orejereas = orejereas;
        this.marca_orejeras = marca_orejeras;
        this.modelo_orejeras = modelo_orejeras;
        this.nrr_orejeras = nrr_orejeras;
        this.nrr_tapones_audi = nrr_tapones_audi;
        this.adm_tiempo_expo = adm_tiempo_expo;
        this.tiempo_exposicion = tiempo_exposicion;
        this.otro_administrativo = otro_administrativo;
        this.tareas_medicion = tareas_medicion;
        this.tarea_visual = tarea_visual;
        this.tipo_tarea_visual = tipo_tarea_visual;
        this.nivel_min_ilu = nivel_min_ilu;
        this.observacion = observacion;
        this.ch_ruido_externo = ch_ruido_externo;
        this.ch_ruido_antiguo = ch_ruido_antiguo;
        this.ch_ruido_generado_por = ch_ruido_generado_por;
        this.ruido_generado_por = ruido_generado_por;
        this.otro_ruido = otro_ruido;
        this.area_req_concentr = area_req_concentr;
        this.lim_max_permis = lim_max_permis;
        this.tipo_medicion = tipo_medicion;
        this.nom_tipo_medicion = nom_tipo_medicion;
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
        this.lpico_dba = lpico_dba;
        this.conformidad = conformidad;
        this.eval_20p = eval_20p;
        this.tipo_vibracion = tipo_vibracion;
        this.ubic_equip = ubic_equip;
        this.anio_ocu_cargo = anio_ocu_cargo;
        this.mes_ocu_cargo = mes_ocu_cargo;
        this.cond_trab = cond_trab;
        this.porc_descan = porc_descan;
        this.foto = foto;
        this.resultado = resultado;
        this.estado_resultado = estado_resultado;
        this.estado = estado;
        this.fec_reg = fec_reg;
        this.user_reg = user_reg;
        this.fec_act = fec_act;
        this.user_act = user_act;
        this.fec_eli = fec_eli;
        this.user_eli = user_eli;
    }

    public int getId_plan_trabajo_formato_reg() {
        return id_plan_trabajo_formato_reg;
    }

    public void setId_plan_trabajo_formato_reg(int id_plan_trabajo_formato_reg) {
        this.id_plan_trabajo_formato_reg = id_plan_trabajo_formato_reg;
    }

    public int getId_formato() {
        return id_formato;
    }

    public void setId_formato(int id_formato) {
        this.id_formato = id_formato;
    }

    public String getCod_registro() {
        return cod_registro;
    }

    public void setCod_registro(String cod_registro) {
        this.cod_registro = cod_registro;
    }

    public String getCod_formato() {
        return cod_formato;
    }

    public void setCod_formato(String cod_formato) {
        this.cod_formato = cod_formato;
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

    public int getId_analista() {
        return id_analista;
    }

    public void setId_analista(int id_analista) {
        this.id_analista = id_analista;
    }

    public String getNom_analista() {
        return nom_analista;
    }

    public void setNom_analista(String nom_analista) {
        this.nom_analista = nom_analista;
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

    public int getId_equipo1() {
        return id_equipo1;
    }

    public void setId_equipo1(int id_equipo1) {
        this.id_equipo1 = id_equipo1;
    }

    public int getId_equipo2() {
        return id_equipo2;
    }

    public void setId_equipo2(int id_equipo2) {
        this.id_equipo2 = id_equipo2;
    }

    public int getId_equipo3() {
        return id_equipo3;
    }

    public void setId_equipo3(int id_equipo3) {
        this.id_equipo3 = id_equipo3;
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

    public String getLuz() {
        return luz;
    }

    public void setLuz(String luz) {
        this.luz = luz;
    }

    public String getArea_trabajo() {
        return area_trabajo;
    }

    public void setArea_trabajo(String area_trabajo) {
        this.area_trabajo = area_trabajo;
    }

    public String getDesc_area_trabajo() {
        return desc_area_trabajo;
    }

    public void setDesc_area_trabajo(String desc_area_trabajo) {
        this.desc_area_trabajo = desc_area_trabajo;
    }

    public String getArea_trab_deta() {
        return area_trab_deta;
    }

    public void setArea_trab_deta(String area_trab_deta) {
        this.area_trab_deta = area_trab_deta;
    }

    public String getActividades_realizadas() {
        return actividades_realizadas;
    }

    public void setActividades_realizadas(String actividades_realizadas) {
        this.actividades_realizadas = actividades_realizadas;
    }

    public int getId_horario() {
        return id_horario;
    }

    public void setId_horario(int id_horario) {
        this.id_horario = id_horario;
    }

    public String getHora_trabajo() {
        return hora_trabajo;
    }

    public void setHora_trabajo(String hora_trabajo) {
        this.hora_trabajo = hora_trabajo;
    }

    public int getN_personas() {
        return n_personas;
    }

    public void setN_personas(int n_personas) {
        this.n_personas = n_personas;
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

    public String getHora_jornada() {
        return hora_jornada;
    }

    public void setHora_jornada(String hora_jornada) {
        this.hora_jornada = hora_jornada;
    }

    public String getMin_jornada() {
        return min_jornada;
    }

    public void setMin_jornada(String min_jornada) {
        this.min_jornada = min_jornada;
    }

    public String getHora_exposicion() {
        return hora_exposicion;
    }

    public void setHora_exposicion(String hora_exposicion) {
        this.hora_exposicion = hora_exposicion;
    }

    public String getMin_exposicion() {
        return min_exposicion;
    }

    public void setMin_exposicion(String min_exposicion) {
        this.min_exposicion = min_exposicion;
    }

    public String getJornada() {
        return jornada;
    }

    public void setJornada(String jornada) {
        this.jornada = jornada;
    }

    public String getNom_trabajador() {
        return nom_trabajador;
    }

    public void setNom_trabajador(String nom_trabajador) {
        this.nom_trabajador = nom_trabajador;
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

    public String getPuesto_trabajador() {
        return puesto_trabajador;
    }

    public void setPuesto_trabajador(String puesto_trabajador) {
        this.puesto_trabajador = puesto_trabajador;
    }

    public int getEdad_trabajador() {
        return edad_trabajador;
    }

    public void setEdad_trabajador(int edad_trabajador) {
        this.edad_trabajador = edad_trabajador;
    }

    public String getPeso_trabajador() {
        return peso_trabajador;
    }

    public void setPeso_trabajador(String peso_trabajador) {
        this.peso_trabajador = peso_trabajador;
    }

    public String getRegimen_laboral() {
        return regimen_laboral;
    }

    public void setRegimen_laboral(String regimen_laboral) {
        this.regimen_laboral = regimen_laboral;
    }

    public String getHorario_refrigerio() {
        return horario_refrigerio;
    }

    public void setHorario_refrigerio(String horario_refrigerio) {
        this.horario_refrigerio = horario_refrigerio;
    }

    public int getTiempo_ocupado() {
        return tiempo_ocupado;
    }

    public void setTiempo_ocupado(int tiempo_ocupado) {
        this.tiempo_ocupado = tiempo_ocupado;
    }

    public String getMolestia_oido() {
        return molestia_oido;
    }

    public void setMolestia_oido(String molestia_oido) {
        this.molestia_oido = molestia_oido;
    }

    public String getEnfermedad_oido() {
        return enfermedad_oido;
    }

    public void setEnfermedad_oido(String enfermedad_oido) {
        this.enfermedad_oido = enfermedad_oido;
    }

    public String getDetalle_enf_oido() {
        return detalle_enf_oido;
    }

    public void setDetalle_enf_oido(String detalle_enf_oido) {
        this.detalle_enf_oido = detalle_enf_oido;
    }

    public String getFecha_ultimo_examen() {
        return fecha_ultimo_examen;
    }

    public void setFecha_ultimo_examen(String fecha_ultimo_examen) {
        this.fecha_ultimo_examen = fecha_ultimo_examen;
    }

    public String getMes_ultimo_examen() {
        return mes_ultimo_examen;
    }

    public void setMes_ultimo_examen(String mes_ultimo_examen) {
        this.mes_ultimo_examen = mes_ultimo_examen;
    }

    public String getAnio_ultimo_examen() {
        return anio_ultimo_examen;
    }

    public void setAnio_ultimo_examen(String anio_ultimo_examen) {
        this.anio_ultimo_examen = anio_ultimo_examen;
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

    public String getNom_epp() {
        return nom_epp;
    }

    public void setNom_epp(String nom_epp) {
        this.nom_epp = nom_epp;
    }

    public String getProt_auditivo() {
        return prot_auditivo;
    }

    public void setProt_auditivo(String prot_auditivo) {
        this.prot_auditivo = prot_auditivo;
    }

    public String getNom_ctrl_ingenieria() {
        return nom_ctrl_ingenieria;
    }

    public void setNom_ctrl_ingenieria(String nom_ctrl_ingenieria) {
        this.nom_ctrl_ingenieria = nom_ctrl_ingenieria;
    }

    public String getNom_ctrl_admin() {
        return nom_ctrl_admin;
    }

    public void setNom_ctrl_admin(String nom_ctrl_admin) {
        this.nom_ctrl_admin = nom_ctrl_admin;
    }

    public String getAislamiento() {
        return aislamiento;
    }

    public void setAislamiento(String aislamiento) {
        this.aislamiento = aislamiento;
    }

    public String getTechos() {
        return techos;
    }

    public void setTechos(String techos) {
        this.techos = techos;
    }

    public String getCabinas() {
        return cabinas;
    }

    public void setCabinas(String cabinas) {
        this.cabinas = cabinas;
    }

    public String getOrientacion() {
        return orientacion;
    }

    public void setOrientacion(String orientacion) {
        this.orientacion = orientacion;
    }

    public String getCerramiento() {
        return cerramiento;
    }

    public void setCerramiento(String cerramiento) {
        this.cerramiento = cerramiento;
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

    public String getSenializacion_vibracion() {
        return senializacion_vibracion;
    }

    public void setSenializacion_vibracion(String senializacion_vibracion) {
        this.senializacion_vibracion = senializacion_vibracion;
    }

    public String getSenializacion_area() {
        return senializacion_area;
    }

    public void setSenializacion_area(String senializacion_area) {
        this.senializacion_area = senializacion_area;
    }

    public String getMantenimiento_vibracion() {
        return mantenimiento_vibracion;
    }

    public void setMantenimiento_vibracion(String mantenimiento_vibracion) {
        this.mantenimiento_vibracion = mantenimiento_vibracion;
    }

    public String getRotacion() {
        return rotacion;
    }

    public void setRotacion(String rotacion) {
        this.rotacion = rotacion;
    }

    public String getAislante_detalle() {
        return aislante_detalle;
    }

    public void setAislante_detalle(String aislante_detalle) {
        this.aislante_detalle = aislante_detalle;
    }

    public String getCabina_detalle() {
        return cabina_detalle;
    }

    public void setCabina_detalle(String cabina_detalle) {
        this.cabina_detalle = cabina_detalle;
    }

    public String getCapacitacion_detalle() {
        return capacitacion_detalle;
    }

    public void setCapacitacion_detalle(String capacitacion_detalle) {
        this.capacitacion_detalle = capacitacion_detalle;
    }

    public String getSenializacion_nivel_detalle() {
        return senializacion_nivel_detalle;
    }

    public void setSenializacion_nivel_detalle(String senializacion_nivel_detalle) {
        this.senializacion_nivel_detalle = senializacion_nivel_detalle;
    }

    public String getSenializacion_epp_detalle() {
        return senializacion_epp_detalle;
    }

    public void setSenializacion_epp_detalle(String senializacion_epp_detalle) {
        this.senializacion_epp_detalle = senializacion_epp_detalle;
    }

    public String getRotacion_detalle() {
        return rotacion_detalle;
    }

    public void setRotacion_detalle(String rotacion_detalle) {
        this.rotacion_detalle = rotacion_detalle;
    }

    public String getTiempo_exposicion_detalle() {
        return tiempo_exposicion_detalle;
    }

    public void setTiempo_exposicion_detalle(String tiempo_exposicion_detalle) {
        this.tiempo_exposicion_detalle = tiempo_exposicion_detalle;
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

    public String getNrr_tapones_audi() {
        return nrr_tapones_audi;
    }

    public void setNrr_tapones_audi(String nrr_tapones_audi) {
        this.nrr_tapones_audi = nrr_tapones_audi;
    }

    public String getAdm_tiempo_expo() {
        return adm_tiempo_expo;
    }

    public void setAdm_tiempo_expo(String adm_tiempo_expo) {
        this.adm_tiempo_expo = adm_tiempo_expo;
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

    public String getTareas_medicion() {
        return tareas_medicion;
    }

    public void setTareas_medicion(String tareas_medicion) {
        this.tareas_medicion = tareas_medicion;
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

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getCh_ruido_externo() {
        return ch_ruido_externo;
    }

    public void setCh_ruido_externo(String ch_ruido_externo) {
        this.ch_ruido_externo = ch_ruido_externo;
    }

    public String getCh_ruido_antiguo() {
        return ch_ruido_antiguo;
    }

    public void setCh_ruido_antiguo(String ch_ruido_antiguo) {
        this.ch_ruido_antiguo = ch_ruido_antiguo;
    }

    public String getCh_ruido_generado_por() {
        return ch_ruido_generado_por;
    }

    public void setCh_ruido_generado_por(String ch_ruido_generado_por) {
        this.ch_ruido_generado_por = ch_ruido_generado_por;
    }

    public String getRuido_generado_por() {
        return ruido_generado_por;
    }

    public void setRuido_generado_por(String ruido_generado_por) {
        this.ruido_generado_por = ruido_generado_por;
    }

    public String getOtro_ruido() {
        return otro_ruido;
    }

    public void setOtro_ruido(String otro_ruido) {
        this.otro_ruido = otro_ruido;
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

    public String getTipo_medicion() {
        return tipo_medicion;
    }

    public void setTipo_medicion(String tipo_medicion) {
        this.tipo_medicion = tipo_medicion;
    }

    public String getNom_tipo_medicion() {
        return nom_tipo_medicion;
    }

    public void setNom_tipo_medicion(String nom_tipo_medicion) {
        this.nom_tipo_medicion = nom_tipo_medicion;
    }

    public double getLmin() {
        return lmin;
    }

    public void setLmin(double lmin) {
        this.lmin = lmin;
    }

    public double getLmax() {
        return lmax;
    }

    public void setLmax(double lmax) {
        this.lmax = lmax;
    }

    public double getLequi() {
        return lequi;
    }

    public void setLequi(double lequi) {
        this.lequi = lequi;
    }

    public double getLequi_md1() {
        return lequi_md1;
    }

    public void setLequi_md1(double lequi_md1) {
        this.lequi_md1 = lequi_md1;
    }

    public double getLequi_md2() {
        return lequi_md2;
    }

    public void setLequi_md2(double lequi_md2) {
        this.lequi_md2 = lequi_md2;
    }

    public double getLequi_md3() {
        return lequi_md3;
    }

    public void setLequi_md3(double lequi_md3) {
        this.lequi_md3 = lequi_md3;
    }

    public double getLequi_md4() {
        return lequi_md4;
    }

    public void setLequi_md4(double lequi_md4) {
        this.lequi_md4 = lequi_md4;
    }

    public double getLequi_md5() {
        return lequi_md5;
    }

    public void setLequi_md5(double lequi_md5) {
        this.lequi_md5 = lequi_md5;
    }

    public double getLmax_md1() {
        return lmax_md1;
    }

    public void setLmax_md1(double lmax_md1) {
        this.lmax_md1 = lmax_md1;
    }

    public double getLmax_md2() {
        return lmax_md2;
    }

    public void setLmax_md2(double lmax_md2) {
        this.lmax_md2 = lmax_md2;
    }

    public double getLmax_md3() {
        return lmax_md3;
    }

    public void setLmax_md3(double lmax_md3) {
        this.lmax_md3 = lmax_md3;
    }

    public double getLmax_md4() {
        return lmax_md4;
    }

    public void setLmax_md4(double lmax_md4) {
        this.lmax_md4 = lmax_md4;
    }

    public double getLmax_md5() {
        return lmax_md5;
    }

    public void setLmax_md5(double lmax_md5) {
        this.lmax_md5 = lmax_md5;
    }

    public double getLmin_md1() {
        return lmin_md1;
    }

    public void setLmin_md1(double lmin_md1) {
        this.lmin_md1 = lmin_md1;
    }

    public double getLmin_md2() {
        return lmin_md2;
    }

    public void setLmin_md2(double lmin_md2) {
        this.lmin_md2 = lmin_md2;
    }

    public double getLmin_md3() {
        return lmin_md3;
    }

    public void setLmin_md3(double lmin_md3) {
        this.lmin_md3 = lmin_md3;
    }

    public double getLmin_md4() {
        return lmin_md4;
    }

    public void setLmin_md4(double lmin_md4) {
        this.lmin_md4 = lmin_md4;
    }

    public double getLmin_md5() {
        return lmin_md5;
    }

    public void setLmin_md5(double lmin_md5) {
        this.lmin_md5 = lmin_md5;
    }

    public double getLpico_dba() {
        return lpico_dba;
    }

    public void setLpico_dba(double lpico_dba) {
        this.lpico_dba = lpico_dba;
    }

    public String getConformidad() {
        return conformidad;
    }

    public void setConformidad(String conformidad) {
        this.conformidad = conformidad;
    }

    public String getEval_20p() {
        return eval_20p;
    }

    public void setEval_20p(String eval_20p) {
        this.eval_20p = eval_20p;
    }

    public String getTipo_vibracion() {
        return tipo_vibracion;
    }

    public void setTipo_vibracion(String tipo_vibracion) {
        this.tipo_vibracion = tipo_vibracion;
    }

    public String getUbic_equip() {
        return ubic_equip;
    }

    public void setUbic_equip(String ubic_equip) {
        this.ubic_equip = ubic_equip;
    }

    public String getAnio_ocu_cargo() {
        return anio_ocu_cargo;
    }

    public void setAnio_ocu_cargo(String anio_ocu_cargo) {
        this.anio_ocu_cargo = anio_ocu_cargo;
    }

    public String getMes_ocu_cargo() {
        return mes_ocu_cargo;
    }

    public void setMes_ocu_cargo(String mes_ocu_cargo) {
        this.mes_ocu_cargo = mes_ocu_cargo;
    }

    public String getCond_trab() {
        return cond_trab;
    }

    public void setCond_trab(String cond_trab) {
        this.cond_trab = cond_trab;
    }

    public String getPorc_descan() {
        return porc_descan;
    }

    public void setPorc_descan(String porc_descan) {
        this.porc_descan = porc_descan;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public int getEstado_resultado() {
        return estado_resultado;
    }

    public void setEstado_resultado(int estado_resultado) {
        this.estado_resultado = estado_resultado;
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

    public int getUser_reg() {
        return user_reg;
    }

    public void setUser_reg(int user_reg) {
        this.user_reg = user_reg;
    }

    public String getFec_act() {
        return fec_act;
    }

    public void setFec_act(String fec_act) {
        this.fec_act = fec_act;
    }

    public int getUser_act() {
        return user_act;
    }

    public void setUser_act(int user_act) {
        this.user_act = user_act;
    }

    public String getFec_eli() {
        return fec_eli;
    }

    public void setFec_eli(String fec_eli) {
        this.fec_eli = fec_eli;
    }

    public int getUser_eli() {
        return user_eli;
    }

    public void setUser_eli(int user_eli) {
        this.user_eli = user_eli;
    }
}
