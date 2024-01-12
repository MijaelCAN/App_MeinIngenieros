package com.mijael.mein.Entidades;

import com.google.gson.annotations.SerializedName;

import java.util.Date;


public class Dosimetria_Registro {
    private int id;
    private String cod_formato;
    private String id_formato;
    private String id_plan_trabajo;
    private String id_pt_formato;
    private String cod_equipo1;
    private String nom_equipo1;
    private String cod_equipo2;
    private String nom_equipo2;
    private String serie_eq1;
    private String serie_eq2;
    private String id_equipo1;
    private String id_equipo2;
    private String id_analista;
    private String nom_analista;
    private String hora_situ;
    private String nivel;
    private String variacion;
    private Date fec_monitoreo;
    private String hora_inicial;
    private String hora_final;
    private String tiempo_exposicion;
    private String jornada;
    private String tipo_doc_trabajador;
    private String num_doc_trabajador;
    private String nom_trabajador;
    private String puesto_trabajador;
    private String area_trabajo;
    private String actividades_realizadas;
    private String edad_trabajador;
    private String ch_ruido_externo;
    private String ch_ruido_antiguo;
    private String ch_ruido_generado_por;
    private String ruido_generado_por;
    private String otro_ruido;
    private String hora_trabajo;
    private String regimen_laboral;
    private String horario_refrigerio;
    private String anio_ocu_cargo;
    private String mes_ocu_cargo;
    private String molestia_oido;
    private String enfermedad_oido;
    private String detalle_enf_oido;
    private String fecha_ultimo_examen;
    private String mes_ultimo_examen;
    private String anio_ultimo_examen;
    private String ctrl_ingenieria;
    private String aislamiento;
    private String techos;
    private String cabinas;
    private String orientacion;
    private String cerramiento;
    private String otro_ingenieria;
    private String ctrl_administrativo;
    private String capacitacion;
    private String senializacion_precion;
    private String senializacion_epp;
    private String rotacion;
    private String adm_tiempo_expo;
    private String otro_administrativo;
    private String tapones_au;
    private String marca_tapones_audi;
    private String modelo_tapones_audi;
    private String nrr_tapones_audi;
    private String orejereas;
    private String marca_orejeras;
    private String modelo_orejeras;
    private String nrr_orejeras;
    private String leq_dba;
    private String lpico_dba;
    private String lmax_dba;
    private String lmin_dba;
    private String observacion;
    private String estado_resultado;
    private int  estado;
    private String user_reg;
    private String fec_reg;

    private String var1;

    public Dosimetria_Registro(String var1){
        this.var1 = var1;
    }

    public Dosimetria_Registro(int id, String cod_formato, String id_formato, String id_plan_trabajo, String id_pt_formato, String cod_equipo1, String nom_equipo1, String cod_equipo2, String nom_equipo2, String serie_eq1, String serie_eq2, String id_equipo1, String id_equipo2, String id_analista, String nom_analista, String hora_situ, String nivel, String variacion, Date fec_monitoreo, String hora_inicial, String hora_final, String tiempo_exposicion, String jornada, String tipo_doc_trabajador, String num_doc_trabajador, String nom_trabajador, String puesto_trabajador, String area_trabajo, String actividades_realizadas, String edad_trabajador, String ch_ruido_externo, String ch_ruido_antiguo, String ch_ruido_generado_por, String ruido_generado_por, String otro_ruido, String hora_trabajo, String regimen_laboral, String horario_refrigerio, String anio_ocu_cargo, String mes_ocu_cargo, String molestia_oido, String enfermedad_oido, String detalle_enf_oido, String fecha_ultimo_examen, String mes_ultimo_examen, String anio_ultimo_examen, String ctrl_ingenieria, String aislamiento, String techos, String cabinas, String orientacion, String cerramiento, String otro_ingenieria, String ctrl_administrativo, String capacitacion, String senializacion_precion, String senializacion_epp, String rotacion, String adm_tiempo_expo, String otro_administrativo, String tapones_au, String marca_tapones_audi, String modelo_tapones_audi, String nrr_tapones_audi, String orejereas, String marca_orejeras, String modelo_orejeras, String nrr_orejeras, String leq_dba, String lpico_dba, String lmax_dba, String lmin_dba, String observacion, String estado_resultado, String user_reg, String fec_reg, String var1) {
        this.id = id;
        this.cod_formato = cod_formato;
        this.id_formato = id_formato;
        this.id_plan_trabajo = id_plan_trabajo;
        this.id_pt_formato = id_pt_formato;
        this.cod_equipo1 = cod_equipo1;
        this.nom_equipo1 = nom_equipo1;
        this.cod_equipo2 = cod_equipo2;
        this.nom_equipo2 = nom_equipo2;
        this.serie_eq1 = serie_eq1;
        this.serie_eq2 = serie_eq2;
        this.id_equipo1 = id_equipo1;
        this.id_equipo2 = id_equipo2;
        this.id_analista = id_analista;
        this.nom_analista = nom_analista;
        this.hora_situ = hora_situ;
        this.nivel = nivel;
        this.variacion = variacion;
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
        this.ch_ruido_externo = ch_ruido_externo;
        this.ch_ruido_antiguo = ch_ruido_antiguo;
        this.ch_ruido_generado_por = ch_ruido_generado_por;
        this.ruido_generado_por = ruido_generado_por;
        this.otro_ruido = otro_ruido;
        this.hora_trabajo = hora_trabajo;
        this.regimen_laboral = regimen_laboral;
        this.horario_refrigerio = horario_refrigerio;
        this.anio_ocu_cargo = anio_ocu_cargo;
        this.mes_ocu_cargo = mes_ocu_cargo;
        this.molestia_oido = molestia_oido;
        this.enfermedad_oido = enfermedad_oido;
        this.detalle_enf_oido = detalle_enf_oido;
        this.fecha_ultimo_examen = fecha_ultimo_examen;
        this.mes_ultimo_examen = mes_ultimo_examen;
        this.anio_ultimo_examen = anio_ultimo_examen;
        this.ctrl_ingenieria = ctrl_ingenieria;
        this.aislamiento = aislamiento;
        this.techos = techos;
        this.cabinas = cabinas;
        this.orientacion = orientacion;
        this.cerramiento = cerramiento;
        this.otro_ingenieria = otro_ingenieria;
        this.ctrl_administrativo = ctrl_administrativo;
        this.capacitacion = capacitacion;
        this.senializacion_precion = senializacion_precion;
        this.senializacion_epp = senializacion_epp;
        this.rotacion = rotacion;
        this.adm_tiempo_expo = adm_tiempo_expo;
        this.otro_administrativo = otro_administrativo;
        this.tapones_au = tapones_au;
        this.marca_tapones_audi = marca_tapones_audi;
        this.modelo_tapones_audi = modelo_tapones_audi;
        this.nrr_tapones_audi = nrr_tapones_audi;
        this.orejereas = orejereas;
        this.marca_orejeras = marca_orejeras;
        this.modelo_orejeras = modelo_orejeras;
        this.nrr_orejeras = nrr_orejeras;
        this.leq_dba = leq_dba;
        this.lpico_dba = lpico_dba;
        this.lmax_dba = lmax_dba;
        this.lmin_dba = lmin_dba;
        this.observacion = observacion;
        this.estado_resultado = estado_resultado;
        this.user_reg = user_reg;
        this.fec_reg = fec_reg;
        this.var1 = var1;
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

    public Date getFec_monitoreo() {
        return fec_monitoreo;
    }

    public void setFec_monitoreo(Date fec_monitoreo) {
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

    public String getHorario_refrigerio() {
        return horario_refrigerio;
    }

    public void setHorario_refrigerio(String horario_refrigerio) {
        this.horario_refrigerio = horario_refrigerio;
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

    public String getCtrl_administrativo() {
        return ctrl_administrativo;
    }

    public void setCtrl_administrativo(String ctrl_administrativo) {
        this.ctrl_administrativo = ctrl_administrativo;
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

    public String getAdm_tiempo_expo() {
        return adm_tiempo_expo;
    }

    public void setAdm_tiempo_expo(String adm_tiempo_expo) {
        this.adm_tiempo_expo = adm_tiempo_expo;
    }

    public String getOtro_administrativo() {
        return otro_administrativo;
    }

    public void setOtro_administrativo(String otro_administrativo) {
        this.otro_administrativo = otro_administrativo;
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

    public String getLeq_dba() {
        return leq_dba;
    }

    public void setLeq_dba(String leq_dba) {
        this.leq_dba = leq_dba;
    }

    public String getLpico_dba() {
        return lpico_dba;
    }

    public void setLpico_dba(String lpico_dba) {
        this.lpico_dba = lpico_dba;
    }

    public String getLmax_dba() {
        return lmax_dba;
    }

    public void setLmax_dba(String lmax_dba) {
        this.lmax_dba = lmax_dba;
    }

    public String getLmin_dba() {
        return lmin_dba;
    }

    public void setLmin_dba(String lmin_dba) {
        this.lmin_dba = lmin_dba;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getEstado_resultado() {
        return estado_resultado;
    }

    public void setEstado_resultado(String estado_resultado) {
        this.estado_resultado = estado_resultado;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getUser_reg() {
        return user_reg;
    }

    public void setUser_reg(String user_reg) {
        this.user_reg = user_reg;
    }

    public String getFec_reg() {
        return fec_reg;
    }

    public void setFec_reg(String fec_reg) {
        this.fec_reg = fec_reg;
    }

    public String getVar1() {
        return var1;
    }

    public void setVar1(String var1) {
        this.var1 = var1;
    }
}