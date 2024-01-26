package com.mijael.mein.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.mijael.mein.Entidades.RadiacionUv_Registro;
import com.mijael.mein.Entidades.RadiacionUv_RegistroDetalle;
import com.mijael.mein.HELPER.RegistroFormatosSQLiteHelper;
import com.mijael.mein.Utilidades.Util_RegistroFormatos;
import com.mijael.mein.Utilidades.Util_RegistroFormatos_Detalle;

public class DAO_RegistroRadiacionUV {
    public RegistroFormatosSQLiteHelper helper;
    public DAO_RegistroRadiacionUV(Context context){
        this.helper = RegistroFormatosSQLiteHelper.getInstance(context);
    }

    public void RegistroRadiacionUV(RadiacionUv_Registro registro){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Util_RegistroFormatos.CAMPO_COD_FORMATO, registro.getCod_formato());
        values.put(Util_RegistroFormatos.CAMPO_COD_REGISTRO, registro.getCod_registro());
        values.put(Util_RegistroFormatos.CAMPO_ID_FORMATO, registro.getId_formato());
        values.put(Util_RegistroFormatos.CAMPO_ID_PLAN_TRABAJO, registro.getId_plan_trabajo());
        values.put(Util_RegistroFormatos.CAMPO_ID_PT_FORMATO, registro.getId_pt_formato());
        values.put(Util_RegistroFormatos.CAMPO_ID_EQUIPO1, registro.getId_equipo1());
        values.put(Util_RegistroFormatos.CAMPO_COD_EQUIPO1, registro.getCod_equipo1());
        values.put(Util_RegistroFormatos.CAMPO_NOM_EQUIPO1, registro.getNom_equipo1());
        values.put(Util_RegistroFormatos.CAMPO_SERIE_EQ1, registro.getSerie_eq1());
        values.put(Util_RegistroFormatos.CAMPO_ID_ANALISTA, registro.getId_analista());
        values.put(Util_RegistroFormatos.CAMPO_NOM_ANALISTA, registro.getNom_analista());
        values.put(Util_RegistroFormatos.CAMPO_VERF_INSITU, registro.getVerf_insitu());
        values.put(Util_RegistroFormatos.CAMPO_HORA_SITU, registro.getHora_situ());
        values.put(Util_RegistroFormatos.CAMPO_FEC_MONITOREO, registro.getFec_monitoreo());
        values.put(Util_RegistroFormatos.CAMPO_HORA_INICIAL, registro.getHora_inicial());
        values.put(Util_RegistroFormatos.CAMPO_TIEMPO_EXPOSICION, registro.getTiempo_exposicion());
        values.put(Util_RegistroFormatos.CAMPO_JORNADA, registro.getJornada());
        values.put(Util_RegistroFormatos.CAMPO_TIPO_DOC_TRABAJADOR, registro.getTipo_doc_trabajador());
        values.put(Util_RegistroFormatos.CAMPO_NUM_DOC_TRABAJADOR, registro.getNum_doc_trabajador());
        values.put(Util_RegistroFormatos.CAMPO_NOM_TRABAJADOR, registro.getNom_trabajador());
        values.put(Util_RegistroFormatos.CAMPO_PUESTO_TRABAJADOR, registro.getPuesto_trabajador());
        values.put(Util_RegistroFormatos.CAMPO_AREA_TRABAJO, registro.getArea_trabajo());
        values.put(Util_RegistroFormatos.CAMPO_ACTIVIDADES_REALIZADAS, registro.getActividades_realizadas());
        values.put(Util_RegistroFormatos.CAMPO_EDAD_TRABAJADOR, registro.getEdad_trabajador());
        values.put(Util_RegistroFormatos.CAMPO_ANIO_OCU_CARGO, registro.getAnio_ocu_cargo());
        values.put(Util_RegistroFormatos.CAMPO_MES_OCU_CARGO, registro.getMes_ocu_cargo());
        values.put(Util_RegistroFormatos.CAMPO_HORA_TRABAJO, registro.getHora_trabajo());
        values.put(Util_RegistroFormatos.CAMPO_HORARIO_REFRIGERIO, registro.getHorario_refrigerio());
        values.put(Util_RegistroFormatos.CAMPO_REGIMEN_LABORAL, registro.getRegimen_laboral());
        values.put(Util_RegistroFormatos.CAMPO_DESC_AREA_TRABAJO, registro.getDesc_area_trabajo());
        values.put(Util_RegistroFormatos.CAMPO_OTRO_ADMINISTRATIVO, registro.getOtro_administrativo());
        values.put(Util_RegistroFormatos.CAMPO_RESULTADO, registro.getResultado());
        values.put(Util_RegistroFormatos.CAMPO_OTRO_INGENIERIA, registro.getOtro_ingenieria());
        values.put(Util_RegistroFormatos.CAMPO_ESTADO, registro.getEstado());
        values.put(Util_RegistroFormatos.CAMPO_FEC_REG, registro.getFec_reg());
        values.put(Util_RegistroFormatos.CAMPO_USER_REG, registro.getUser_reg());

        db.insert(Util_RegistroFormatos.TABLA_REGISTRO_FORMATOS, null, values);
    }

    public void RegistrarRadiacionUv_Detalle(RadiacionUv_RegistroDetalle registro){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Util_RegistroFormatos_Detalle.CAMPO_ID_FORMATO_REG_DETALLE,-1); //--------------------> PRUEBA PARA EL REGISTRO EN LA TABLA GENERAL
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ID_PLAN_TRABAJO_FORMATO_REG, registro.getId_plan_trabajo_formato_reg());//----------------> PRUEBA PARA EL REGISTRO EN LA TABLA GENERAL
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TIPO_PIEL, registro.getTipo_piel());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_COLOR_PIEL, registro.getColor_piel());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_FUENTE_GENERADORA, registro.getFuente_generadora());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TIPO_FUENTE, registro.getTipo_fuente());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_SOMBRA_DESCANSO, registro.getSombra_descanso());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_MALLA_OSCURA, registro.getMalla_oscura());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PROG_EXPO_RADIACION, registro.getProg_expo_radiacion());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TRAB_AIRE_LIBRE, registro.getTrab_aire_libre());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_MANT_FUENTE, registro.getMant_fuente());//SE QUITO 5 CAMPOS
        values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_LENTES_BRILLO, registro.getEpp_lentes_brillo());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PROT_LAT, registro.getProt_lat());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_GORRO_2, registro.getEpp_gorro_2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_CASCO_2, registro.getEpp_casco_2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_NINGUNO, registro.getEpp_ninguno());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PROT_LEGION, registro.getProt_legion());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PROT_AANCHA, registro.getProt_aancha());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ROP_CCERTI, registro.getRop_ccerti());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ROP_COSCURO, registro.getRop_coscuro());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ROP_MLARGA, registro.getRop_mlarga());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TGRUESA, registro.getTgruesa());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_UTIL_FPS, registro.getUtil_fps());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_GUIA_FPS, registro.getGuia_fps());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_FREC_APLIC, registro.getFrec_aplic());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_OTRA_FRECUENCIA, registro.getOtra_frecuencia());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CUBRE_NUCA, registro.getCubre_nuca());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_LENT_OSCURO, registro.getLent_osc());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_OTRO_EPP, registro.getOtro_epp());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ESTADO, registro.getEstado());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_FEC_REG, registro.getFec_reg());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_USER_REG, registro.getUser_reg());

        db.insert(Util_RegistroFormatos_Detalle.TABLA_REGISTRO_DETALLE, null, values);

    }
}
