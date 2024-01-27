package com.mijael.mein.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mijael.mein.Entidades.RegistroFormatos_Detalle;
import com.mijael.mein.HELPER.RegistroFormatosSQLiteHelper;
import com.mijael.mein.Utilidades.Util_RegistroFormatos_Detalle;

import java.util.ArrayList;
import java.util.List;

public class DAO_RegistroFormatosDetalle {
    private RegistroFormatosSQLiteHelper dataHelper;
    public DAO_RegistroFormatosDetalle(Context context){dataHelper = RegistroFormatosSQLiteHelper.getInstance(context);}

    public List<RegistroFormatos_Detalle> listarRegistrosDetalle(){
        List<RegistroFormatos_Detalle> detalleList = new ArrayList<>();
        SQLiteDatabase db = dataHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Util_RegistroFormatos_Detalle.TABLA_REGISTRO_DETALLE , null);
        if(cursor.moveToFirst()){
            do{
                RegistroFormatos_Detalle detalle = new RegistroFormatos_Detalle(
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_ID_FORMATO_REG_DETALLE)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_ID_PLAN_TRABAJO_FORMATO_REG)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_ORDEN)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_LJ_DB)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_FJ)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_LJ_10)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_10_POT_LJ)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_MULTI)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_PLAN_MANTENIMIENTO_ILUM)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_IL_1)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_IL_2)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_IL_3)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_IL_4)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_IL_5)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_IL_6)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_IL_7)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_IL_8)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_PUNTOS_MED)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_TIPO_ILUMINACION)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_TIPO_ILUMINACION_ART)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_TIPO_ILUMINACION_NAT)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_DESC_AMB_TRABAJO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_CANT_ILUMINARIAS)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_AREA_TRABAJO_M2)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_ALTURA_P_TRABAJO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_N_LAMPARAS)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_ALTURA_P_LUMINARIA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_COLOR_PARED)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_COLOR_PISO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_ESTADO_FISICO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_FRECUENCIA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_FUENTE_GENERADORA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_DESC_FUENTE_FRIO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_TIPO_FUENTE)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_MANT_FUENTE)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_ROPA_INTERIOR)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_CAMISA_BLUSA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_PANTALON)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_PULLOVER)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_ABRIGO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_CHAQUETA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_DIVERSOS)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_ACLIMATADO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_ZONA_SOMBRA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_ROTACION_PERSONAL)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_TIEMPO_RECUPERACION)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_BEBIDA_CALIENTE)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_CAPA_EXPO_FRIO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_DISPENSADOR)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_CAT_TRABAJO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_PORC_DESCA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_VESTIMENTA_PERSONAL)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_MATERIAL_PRENDA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_AT_DESNUDO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_AT_LIGERO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_AT_MEDIO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_AT_PESADO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_COLOR_PREDOMINANTE)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_EPP_ZS)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_EPP_CASCO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_EPP_LENTES)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_EPP_GUANTES)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_EPP_OREJERAS)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_EPP_TAPONES)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_EPP_CNUCA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_EPP_GORRO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_EPP_BOTAS)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_OTRO_EPP)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_EPP_LENTES_BRILLO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_PROT_LAT)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_EPP_GORRO_2)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_EPP_CASCO_2)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_EPP_NINGUNO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_PROT_LEGION)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_PROT_AANCHA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_ROP_CCERTI)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_ROP_COSCURO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_ROP_MLARGA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_TGRUESA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_UTIL_FPS)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_FREC_APLIC)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_OTRA_FRECUENCIA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_CUBRE_NUCA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_LENT_OSCURO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_TECNICA_ACONDAIRE)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_DETALLE_TECNICA_ACONDAIRE)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_DESC_ATUENDO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_P_AACONDICIONADO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_T_GLOBO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_T_GLOBO_2)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_T_GLOBO_3)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_T_BULBO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_T_BULBO2)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_T_BULBO3)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_NOM_TAREA1)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_CICLO_TRABAJO1)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_POSICION_1)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_PCUERPO_1)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_INTENSIDAD_1)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_NOM_TAREA2)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_CICLO_TRABAJO2)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_POSICION_2)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_PCUERPO_2)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_INTENSIDAD_2)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_NOM_TAREA3)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_CICLO_TRABAJO3)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_POSICION_3)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_PCUERPO_3)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_INTENSIDAD_3)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_NOM_TAREA4)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_CICLO_TRABAJO4)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_POSICION_4)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_PCUERPO_4)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_INTENSIDAD_4)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_NOM_TAREA5)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_CICLO_TRABAJO5)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_POSICION_5)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_PCUERPO_5)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_INTENSIDAD_5)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_HORARIO_LV)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_HORARIO_S)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_WBGT)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_WBGT_2)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_WBGT_3)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_T_AIRE)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_T_AIRE_2)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_T_AIRE_3)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_H_RELATIVA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_H_RELATIVA_2)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_H_RELATIVA_3)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_V_VIENTO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_V_VIENTO_2)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_V_VIENTO_3)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_V_VTO4)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_V_VTO5)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_V_VTO6)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_V_VTO7)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_V_VTO8)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_V_VTO9)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_V_VTO10)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_OTRO_VEST)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_TIPO_MEDICION_ILU)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_LARG_ESCRIT)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_ANCH_ESCRIT)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_NUM_PMEDICION)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_ALT_PLTRABAJO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_LONG_SALON)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_ANCH_SALON)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_ALT_PLTRABAJO_ILU)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_CONST_SALON)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_NUM_MIN_PMEDIC)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_TEMP)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_MTR_SUBIDA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_TIPO_PIEL)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_COLOR_PIEL)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_SOMBRA_DESCANSO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_MALLA_OSCURA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_PROG_EXPO_RADIACION)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_TRAB_AIRE_LIBRE)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_R_CERTIFICACION)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_R_OSCURA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_MANGAL)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_TELAG)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_UTILIZA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_GUIA_FPS)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_FRECUENCIA_APLICACION)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_TRAJ_PROT_ELECTROMAGNETICA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_B_MAFNETICO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_X)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_Y)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_Z)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_RI_CALZ)),// -------------
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_RI_CALZL)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_RI_CAMIST)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_RI_CAMISMC)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_RI_CAMISML)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_RI_SUJETB)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_CB_MANGAC)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_CB_LIGERAMC)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_CB_NORMALML)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_CB_CFRANML)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_CB_BLIGMC)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_P_COR)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_P_LIG)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_P_NORM)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_P_FRAN)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_PUL_CHASM)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_PUL_LIG)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_PUL_MED)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_PUL_GRUE)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_PA_ABRI)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_PA_CHAL)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_PA_PARK)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_PA_MONF)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_CH_LIG)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_CH_CHAQ)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_CH_BATAT)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_CH_MONOT)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_D_ZAPSD)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_D_ZAPSG)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_D_CALC)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_D_MED)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_D_CALCGC)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_D_CALCGL)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_D_BOTAS)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_D_GUANT)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_ID_NIVEL_D)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_NOM_NIVEL_D)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_ID_METODO_DETERM)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_METODO_DETERM)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_ID_TIPO_TRABAJO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_TIPO_TRABAJO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_OCUPACION)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_RANGO_TASA_METAB)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_CLASE)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_ACTIVIDAD_DETER)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_TASA_METAB)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_TASA_METAB_KCAL)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_FRECUENCIA_DETER)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_GENERO_DETER)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_X2)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_Y2)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_Z2)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_X3)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_Y3)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_Z3)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_X4)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_Y4)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_Z4)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_ESTADO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_FEC_REG)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_USER_REG)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_FEC_ACT)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_USER_ACT)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_FEC_ELI)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_USER_ELI))

                        );
                detalleList.add(detalle);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return detalleList;
    }

    public void actualizarRegistroDetalle(RegistroFormatos_Detalle detalle) {
        SQLiteDatabase db = dataHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        //values.put(Util_RegistroFormatos_Detalle.CAMPO_ID_FORMATO_REG_DETALLE, detalle.getId_formato_reg_detalle());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ID_PLAN_TRABAJO_FORMATO_REG, detalle.getId_plan_trabajo_formato_reg());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ORDEN, detalle.getOrden());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_LJ_DB, detalle.getLj_db());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_FJ, detalle.getFj());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_LJ_10, detalle.getLj_10());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_10_POT_LJ, detalle.get_10_pot_lj());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_MULTI, detalle.getMulti());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PLAN_MANTENIMIENTO_ILUM, detalle.getPlan_mantenimiento_ilum());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_IL_1, detalle.getIl_1());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_IL_2, detalle.getIl_2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_IL_3, detalle.getIl_3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_IL_4, detalle.getIl_4());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_IL_5, detalle.getIl_5());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_IL_6, detalle.getIl_6());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_IL_7, detalle.getIl_7());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_IL_8, detalle.getIl_8());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PUNTOS_MED, detalle.getPuntos_med());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TIPO_ILUMINACION, detalle.getTipo_iluminacion());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TIPO_ILUMINACION_ART, detalle.getTipo_iluminacion_art());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TIPO_ILUMINACION_NAT, detalle.getTipo_iluminacion_nat());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_DESC_AMB_TRABAJO, detalle.getDesc_amb_trabajo());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CANT_ILUMINARIAS, detalle.getCant_iluminarias());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_AREA_TRABAJO_M2, detalle.getArea_trabajo_m2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ALTURA_P_TRABAJO, detalle.getAltura_p_trabajo());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_N_LAMPARAS, detalle.getN_lamparas());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ALTURA_P_LUMINARIA, detalle.getAltura_p_luminaria());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_COLOR_PARED, detalle.getColor_pared());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_COLOR_PISO, detalle.getColor_piso());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ESTADO_FISICO, detalle.getEstado_fisico());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_FRECUENCIA, detalle.getFrecuencia());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_FUENTE_GENERADORA, detalle.getFuente_generadora());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_DESC_FUENTE_FRIO, detalle.getDesc_fuente_frio());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TIPO_FUENTE, detalle.getTipo_fuente());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_MANT_FUENTE, detalle.getMant_fuente());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ROPA_INTERIOR, detalle.getRopa_interior());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CAMISA_BLUSA, detalle.getCamisa_blusa());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PANTALON, detalle.getPantalon());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PULLOVER, detalle.getPullover());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ABRIGO, detalle.getAbrigo());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CHAQUETA, detalle.getChaqueta());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_DIVERSOS, detalle.getDiversos());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ACLIMATADO, detalle.getAclimatado());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ZONA_SOMBRA, detalle.getZona_sombra());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ROTACION_PERSONAL, detalle.getRotacion_personal());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TIEMPO_RECUPERACION, detalle.getTiempo_recuperacion());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_BEBIDA_CALIENTE, detalle.getBebida_caliente());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CAPA_EXPO_FRIO, detalle.getCapa_expo_frio());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_DISPENSADOR, detalle.getDispensador());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CAT_TRABAJO, detalle.getCat_trabajo());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PORC_DESCA, detalle.getPorc_desca());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_VESTIMENTA_PERSONAL, detalle.getVestimenta_personal());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_MATERIAL_PRENDA, detalle.getMaterial_prenda());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_AT_DESNUDO, detalle.getAt_desnudo());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_AT_LIGERO, detalle.getAt_ligero());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_AT_MEDIO, detalle.getAt_medio());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_AT_PESADO, detalle.getAt_pesado());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_COLOR_PREDOMINANTE, detalle.getColor_predominante());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_ZS, detalle.getEpp_zs());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_CASCO, detalle.getEpp_casco());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_LENTES, detalle.getEpp_lentes());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_GUANTES, detalle.getEpp_guantes());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_OREJERAS, detalle.getEpp_orejeras());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_TAPONES, detalle.getEpp_tapones());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_CNUCA, detalle.getEpp_cnuca());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_GORRO, detalle.getEpp_gorro());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_BOTAS, detalle.getEpp_botas());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_OTRO_EPP, detalle.getOtro_epp());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_LENTES_BRILLO, detalle.getEpp_lentes_brillo());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PROT_LAT, detalle.getProt_lat());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_GORRO_2, detalle.getEpp_gorro_2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_CASCO_2, detalle.getEpp_casco_2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_NINGUNO, detalle.getEpp_ninguno());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PROT_LEGION, detalle.getProt_legion());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PROT_AANCHA, detalle.getProt_aancha());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ROP_CCERTI, detalle.getRop_ccerti());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ROP_COSCURO, detalle.getRop_coscuro());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ROP_MLARGA, detalle.getRop_mlarga());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TGRUESA, detalle.getTgruesa());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_UTIL_FPS, detalle.getUtil_fps());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_FREC_APLIC, detalle.getFrec_aplic());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_OTRA_FRECUENCIA, detalle.getOtra_frecuencia());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CUBRE_NUCA, detalle.getCubre_nuca());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_LENT_OSCURO, detalle.getLent_osc());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TECNICA_ACONDAIRE, detalle.getTecnica_acondaire());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_DETALLE_TECNICA_ACONDAIRE, detalle.getDetalle_tecnica_acondaire());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_DESC_ATUENDO, detalle.getDesc_atuendo());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_P_AACONDICIONADO, detalle.getP_aacondicionado());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_T_GLOBO, detalle.getT_globo());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_T_GLOBO_2, detalle.getT_globo_2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_T_GLOBO_3, detalle.getT_globo_3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_T_BULBO, detalle.getT_bulbo());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_T_BULBO2, detalle.getT_bulbo2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_T_BULBO3, detalle.getT_bulbo3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_NOM_TAREA1, detalle.getNom_tarea1());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CICLO_TRABAJO1, detalle.getCiclo_trabajo1());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_POSICION_1, detalle.getPosicion_1());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PCUERPO_1, detalle.getPcuerpo_1());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_INTENSIDAD_1, detalle.getIntensidad_1());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_NOM_TAREA2, detalle.getNom_tarea2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CICLO_TRABAJO2, detalle.getCiclo_trabajo2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_POSICION_2, detalle.getPosicion_2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PCUERPO_2, detalle.getPcuerpo_2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_INTENSIDAD_2, detalle.getIntensidad_2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_NOM_TAREA3, detalle.getNom_tarea3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CICLO_TRABAJO3, detalle.getCiclo_trabajo3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_POSICION_3, detalle.getPosicion_3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PCUERPO_3, detalle.getPcuerpo_3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_INTENSIDAD_3, detalle.getIntensidad_3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_NOM_TAREA4, detalle.getNom_tarea4());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CICLO_TRABAJO4, detalle.getCiclo_trabajo4());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_POSICION_4, detalle.getPosicion_4());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PCUERPO_4, detalle.getPcuerpo_4());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_INTENSIDAD_4, detalle.getIntensidad_4());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_NOM_TAREA5, detalle.getNom_tarea5());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CICLO_TRABAJO5, detalle.getCiclo_trabajo5());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_POSICION_5, detalle.getPosicion_5());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PCUERPO_5, detalle.getPcuerpo_5());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_INTENSIDAD_5, detalle.getIntensidad_5());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_HORARIO_LV, detalle.getHorario_lv());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_HORARIO_S, detalle.getHorario_s());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_WBGT, detalle.getWbgt());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_WBGT_2, detalle.getWbgt_2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_WBGT_3, detalle.getWbgt_3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_T_AIRE, detalle.getT_aire());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_T_AIRE_2, detalle.getT_aire_2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_T_AIRE_3, detalle.getT_aire_3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_H_RELATIVA, detalle.getH_relativa());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_H_RELATIVA_2, detalle.getH_relativa_2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_H_RELATIVA_3, detalle.getH_relativa_3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_V_VIENTO, detalle.getV_viento());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_V_VIENTO_2, detalle.getV_viento_2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_V_VIENTO_3, detalle.getV_viento_3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_V_VTO4, detalle.getV_vto4());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_V_VTO5, detalle.getV_vto5());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_V_VTO6, detalle.getV_vto6());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_V_VTO7, detalle.getV_vto7());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_V_VTO8, detalle.getV_vto8());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_V_VTO9, detalle.getV_vto9());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_V_VTO10, detalle.getV_vto10());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_OTRO_VEST, detalle.getOtro_vest());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TIPO_MEDICION_ILU, detalle.getTipo_medicion_ilu());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_LARG_ESCRIT, detalle.getLarg_escrit());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ANCH_ESCRIT, detalle.getAnch_escrit());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_NUM_PMEDICION, detalle.getNum_pmedicion());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ALT_PLTRABAJO, detalle.getAlt_pltrabajo());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_LONG_SALON, detalle.getLong_salon());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ANCH_SALON, detalle.getAnch_salon());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ALT_PLTRABAJO_ILU, detalle.getAlt_pltrabajo_ilu());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CONST_SALON, detalle.getConst_salon());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_NUM_MIN_PMEDIC, detalle.getNum_min_pmedic());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TEMP, detalle.getTemp());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_MTR_SUBIDA, detalle.getMtr_subida());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TIPO_PIEL, detalle.getTipo_piel());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_COLOR_PIEL, detalle.getColor_piel());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_SOMBRA_DESCANSO, detalle.getSombra_descanso());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_MALLA_OSCURA, detalle.getMalla_oscura());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PROG_EXPO_RADIACION, detalle.getProg_expo_radiacion());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TRAB_AIRE_LIBRE, detalle.getTrab_aire_libre());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_R_CERTIFICACION, detalle.getR_certificacion());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_R_OSCURA, detalle.getR_oscura());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_MANGAL, detalle.getMangal());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TELAG, detalle.getTelag());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_UTILIZA, detalle.getUtiliza());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_GUIA_FPS, detalle.getGuia_fps());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_FRECUENCIA_APLICACION, detalle.getFrecuencia_aplicacion());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TRAJ_PROT_ELECTROMAGNETICA, detalle.getTraj_prot_electromagnetica());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_B_MAFNETICO, detalle.getB_mafnetico());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_X, detalle.getX());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_Y, detalle.getY());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_Z, detalle.getZ());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_RI_CALZ, detalle.getRi_calz());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_RI_CALZL, detalle.getRi_calzl());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_RI_CAMIST, detalle.getRi_camist());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_RI_CAMISMC, detalle.getRi_camismc());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_RI_CAMISML, detalle.getRi_camisml());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_RI_SUJETB, detalle.getRi_sujetb());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CB_MANGAC, detalle.getCb_mangac());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CB_LIGERAMC, detalle.getCb_ligeramc());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CB_NORMALML, detalle.getCb_normalml());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CB_CFRANML, detalle.getCb_cfranml());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CB_BLIGMC, detalle.getCb_bligmc());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_P_COR, detalle.getP_cor());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_P_LIG, detalle.getP_lig());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_P_NORM, detalle.getP_norm());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_P_FRAN, detalle.getP_fran());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PUL_CHASM, detalle.getPul_chasm());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PUL_LIG, detalle.getPul_lig());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PUL_MED, detalle.getPul_med());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PUL_GRUE, detalle.getPul_grue());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PA_ABRI, detalle.getPa_abri());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PA_CHAL, detalle.getPa_chal());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PA_PARK, detalle.getPa_park());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PA_MONF, detalle.getPa_monf());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CH_LIG, detalle.getCh_lig());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CH_CHAQ, detalle.getCh_chaq());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CH_BATAT, detalle.getCh_batat());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CH_MONOT, detalle.getCh_monot());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_D_ZAPSD, detalle.getD_zapsd());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_D_ZAPSG, detalle.getD_zapsg());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_D_CALC, detalle.getD_calc());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_D_MED, detalle.getD_med());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_D_CALCGC, detalle.getD_calcgc());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_D_CALCGL, detalle.getD_calcgl());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_D_BOTAS, detalle.getD_botas());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_D_GUANT, detalle.getD_guant());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ID_NIVEL_D, detalle.getId_nivel_d());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_NOM_NIVEL_D, detalle.getNom_nivel_d());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ID_METODO_DETERM, detalle.getId_metodo_determ());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_METODO_DETERM, detalle.getMetodo_determ());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ID_TIPO_TRABAJO, detalle.getId_tipo_trabajo());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TIPO_TRABAJO, detalle.getTipo_trabajo());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_OCUPACION, detalle.getOcupacion());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_RANGO_TASA_METAB, detalle.getRango_tasa_metab());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CLASE, detalle.getClase());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ACTIVIDAD_DETER, detalle.getActividad_deter());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TASA_METAB, detalle.getTasa_metab());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TASA_METAB_KCAL, detalle.getTasa_metab_kcal());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_FRECUENCIA_DETER, detalle.getFrecuencia_deter());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_GENERO_DETER, detalle.getGenero_deter());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_X2, detalle.getX2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_Y2, detalle.getY2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_Z2, detalle.getZ2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_X3, detalle.getX3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_Y3, detalle.getY3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_Z3, detalle.getZ3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_X4, detalle.getX4());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_Y4, detalle.getY4());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_Z4, detalle.getZ4());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ESTADO, detalle.getEstado());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_FEC_REG, detalle.getFec_reg());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_USER_REG, detalle.getUser_reg());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_FEC_ACT, detalle.getFec_act());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_USER_ACT, detalle.getUser_act());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_FEC_ELI, detalle.getFec_eli());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_USER_ELI, detalle.getUser_eli());


        String whereClause = Util_RegistroFormatos_Detalle.CAMPO_ID_FORMATO_REG_DETALLE + "= ?"; // suponiendo que el identificador sea 'id'
        String[] whereArgs = { String.valueOf(detalle.getId_formato_reg_detalle()) };

        long resultado = db.update(Util_RegistroFormatos_Detalle.TABLA_REGISTRO_DETALLE, values, whereClause, whereArgs);
        if (resultado == -1) {
            Log.e("TAG-E", "Error al actualizar REGISTRO_DETALLE - FORMATOS en la base de datos local");
        } else {
            Log.d("TAG-D", "REGISTRO_DETALLE - FORMATOS actualizado en la base de datos local");
        }
        db.close();
    }

    public int contarRegistroDetalle() {
        SQLiteDatabase db = dataHelper.getReadableDatabase();
        Cursor cursor = null;
        int count = 0;

        try {
            cursor = db.rawQuery("SELECT COUNT(*) FROM " + Util_RegistroFormatos_Detalle.TABLA_REGISTRO_DETALLE, null);
            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null && db.isOpen()) {
                db.close(); // Cerrar la base de datos si está abierta
            }
        }
        return count;
    }
    public void insertarRegistrosDetalle(List<RegistroFormatos_Detalle> detallesList) {
        SQLiteDatabase db = dataHelper.getWritableDatabase();

        try {
            db.beginTransaction();
            ContentValues values;

            for (RegistroFormatos_Detalle detalle : detallesList) {
                values = new ContentValues();
                // Configurar los valores para la inserción
                values.put(Util_RegistroFormatos_Detalle.CAMPO_ID_FORMATO_REG_DETALLE, detalle.getId_formato_reg_detalle());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_ID_PLAN_TRABAJO_FORMATO_REG, detalle.getId_plan_trabajo_formato_reg());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_ORDEN, detalle.getOrden());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_LJ_DB, detalle.getLj_db());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_FJ, detalle.getFj());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_LJ_10, detalle.getLj_10());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_10_POT_LJ, detalle.get_10_pot_lj());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_MULTI, detalle.getMulti());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_PLAN_MANTENIMIENTO_ILUM, detalle.getPlan_mantenimiento_ilum());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_IL_1, detalle.getIl_1());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_IL_2, detalle.getIl_2());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_IL_3, detalle.getIl_3());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_IL_4, detalle.getIl_4());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_IL_5, detalle.getIl_5());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_IL_6, detalle.getIl_6());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_IL_7, detalle.getIl_7());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_IL_8, detalle.getIl_8());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_PUNTOS_MED, detalle.getPuntos_med());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_TIPO_ILUMINACION, detalle.getTipo_iluminacion());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_TIPO_ILUMINACION_ART, detalle.getTipo_iluminacion_art());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_TIPO_ILUMINACION_NAT, detalle.getTipo_iluminacion_nat());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_DESC_AMB_TRABAJO, detalle.getDesc_amb_trabajo());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_CANT_ILUMINARIAS, detalle.getCant_iluminarias());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_AREA_TRABAJO_M2, detalle.getArea_trabajo_m2());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_ALTURA_P_TRABAJO, detalle.getAltura_p_trabajo());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_N_LAMPARAS, detalle.getN_lamparas());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_ALTURA_P_LUMINARIA, detalle.getAltura_p_luminaria());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_COLOR_PARED, detalle.getColor_pared());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_COLOR_PISO, detalle.getColor_piso());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_ESTADO_FISICO, detalle.getEstado_fisico());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_FRECUENCIA, detalle.getFrecuencia());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_FUENTE_GENERADORA, detalle.getFuente_generadora());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_DESC_FUENTE_FRIO, detalle.getDesc_fuente_frio());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_TIPO_FUENTE, detalle.getTipo_fuente());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_MANT_FUENTE, detalle.getMant_fuente());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_ROPA_INTERIOR, detalle.getRopa_interior());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_CAMISA_BLUSA, detalle.getCamisa_blusa());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_PANTALON, detalle.getPantalon());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_PULLOVER, detalle.getPullover());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_ABRIGO, detalle.getAbrigo());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_CHAQUETA, detalle.getChaqueta());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_DIVERSOS, detalle.getDiversos());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_ACLIMATADO, detalle.getAclimatado());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_ZONA_SOMBRA, detalle.getZona_sombra());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_ROTACION_PERSONAL, detalle.getRotacion_personal());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_TIEMPO_RECUPERACION, detalle.getTiempo_recuperacion());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_BEBIDA_CALIENTE, detalle.getBebida_caliente());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_CAPA_EXPO_FRIO, detalle.getCapa_expo_frio());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_DISPENSADOR, detalle.getDispensador());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_CAT_TRABAJO, detalle.getCat_trabajo());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_PORC_DESCA, detalle.getPorc_desca());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_VESTIMENTA_PERSONAL, detalle.getVestimenta_personal());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_MATERIAL_PRENDA, detalle.getMaterial_prenda());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_AT_DESNUDO, detalle.getAt_desnudo());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_AT_LIGERO, detalle.getAt_ligero());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_AT_MEDIO, detalle.getAt_medio());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_AT_PESADO, detalle.getAt_pesado());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_COLOR_PREDOMINANTE, detalle.getColor_predominante());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_ZS, detalle.getEpp_zs());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_CASCO, detalle.getEpp_casco());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_LENTES, detalle.getEpp_lentes());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_GUANTES, detalle.getEpp_guantes());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_OREJERAS, detalle.getEpp_orejeras());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_TAPONES, detalle.getEpp_tapones());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_CNUCA, detalle.getEpp_cnuca());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_GORRO, detalle.getEpp_gorro());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_BOTAS, detalle.getEpp_botas());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_OTRO_EPP, detalle.getOtro_epp());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_LENTES_BRILLO, detalle.getEpp_lentes_brillo());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_PROT_LAT, detalle.getProt_lat());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_GORRO_2, detalle.getEpp_gorro_2());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_CASCO_2, detalle.getEpp_casco_2());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_NINGUNO, detalle.getEpp_ninguno());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_PROT_LEGION, detalle.getProt_legion());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_PROT_AANCHA, detalle.getProt_aancha());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_ROP_CCERTI, detalle.getRop_ccerti());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_ROP_COSCURO, detalle.getRop_coscuro());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_ROP_MLARGA, detalle.getRop_mlarga());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_TGRUESA, detalle.getTgruesa());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_UTIL_FPS, detalle.getUtil_fps());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_FREC_APLIC, detalle.getFrec_aplic());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_OTRA_FRECUENCIA, detalle.getOtra_frecuencia());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_CUBRE_NUCA, detalle.getCubre_nuca());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_LENT_OSCURO, detalle.getLent_osc());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_TECNICA_ACONDAIRE, detalle.getTecnica_acondaire());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_DETALLE_TECNICA_ACONDAIRE, detalle.getDetalle_tecnica_acondaire());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_DESC_ATUENDO, detalle.getDesc_atuendo());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_P_AACONDICIONADO, detalle.getP_aacondicionado());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_T_GLOBO, detalle.getT_globo());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_T_GLOBO_2, detalle.getT_globo_2());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_T_GLOBO_3, detalle.getT_globo_3());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_T_BULBO, detalle.getT_bulbo());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_T_BULBO2, detalle.getT_bulbo2());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_T_BULBO3, detalle.getT_bulbo3());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_NOM_TAREA1, detalle.getNom_tarea1());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_CICLO_TRABAJO1, detalle.getCiclo_trabajo1());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_POSICION_1, detalle.getPosicion_1());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_PCUERPO_1, detalle.getPcuerpo_1());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_INTENSIDAD_1, detalle.getIntensidad_1());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_NOM_TAREA2, detalle.getNom_tarea2());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_CICLO_TRABAJO2, detalle.getCiclo_trabajo2());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_POSICION_2, detalle.getPosicion_2());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_PCUERPO_2, detalle.getPcuerpo_2());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_INTENSIDAD_2, detalle.getIntensidad_2());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_NOM_TAREA3, detalle.getNom_tarea3());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_CICLO_TRABAJO3, detalle.getCiclo_trabajo3());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_POSICION_3, detalle.getPosicion_3());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_PCUERPO_3, detalle.getPcuerpo_3());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_INTENSIDAD_3, detalle.getIntensidad_3());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_NOM_TAREA4, detalle.getNom_tarea4());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_CICLO_TRABAJO4, detalle.getCiclo_trabajo4());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_POSICION_4, detalle.getPosicion_4());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_PCUERPO_4, detalle.getPcuerpo_4());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_INTENSIDAD_4, detalle.getIntensidad_4());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_NOM_TAREA5, detalle.getNom_tarea5());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_CICLO_TRABAJO5, detalle.getCiclo_trabajo5());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_POSICION_5, detalle.getPosicion_5());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_PCUERPO_5, detalle.getPcuerpo_5());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_INTENSIDAD_5, detalle.getIntensidad_5());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_HORARIO_LV, detalle.getHorario_lv());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_HORARIO_S, detalle.getHorario_s());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_WBGT, detalle.getWbgt());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_WBGT_2, detalle.getWbgt_2());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_WBGT_3, detalle.getWbgt_3());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_T_AIRE, detalle.getT_aire());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_T_AIRE_2, detalle.getT_aire_2());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_T_AIRE_3, detalle.getT_aire_3());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_H_RELATIVA, detalle.getH_relativa());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_H_RELATIVA_2, detalle.getH_relativa_2());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_H_RELATIVA_3, detalle.getH_relativa_3());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_V_VIENTO, detalle.getV_viento());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_V_VIENTO_2, detalle.getV_viento_2());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_V_VIENTO_3, detalle.getV_viento_3());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_V_VTO4, detalle.getV_vto4());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_V_VTO5, detalle.getV_vto5());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_V_VTO6, detalle.getV_vto6());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_V_VTO7, detalle.getV_vto7());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_V_VTO8, detalle.getV_vto8());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_V_VTO9, detalle.getV_vto9());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_V_VTO10, detalle.getV_vto10());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_OTRO_VEST, detalle.getOtro_vest());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_TIPO_MEDICION_ILU, detalle.getTipo_medicion_ilu());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_LARG_ESCRIT, detalle.getLarg_escrit());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_ANCH_ESCRIT, detalle.getAnch_escrit());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_NUM_PMEDICION, detalle.getNum_pmedicion());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_ALT_PLTRABAJO, detalle.getAlt_pltrabajo());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_LONG_SALON, detalle.getLong_salon());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_ANCH_SALON, detalle.getAnch_salon());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_ALT_PLTRABAJO_ILU, detalle.getAlt_pltrabajo_ilu());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_CONST_SALON, detalle.getConst_salon());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_NUM_MIN_PMEDIC, detalle.getNum_min_pmedic());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_TEMP, detalle.getTemp());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_MTR_SUBIDA, detalle.getMtr_subida());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_TIPO_PIEL, detalle.getTipo_piel());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_COLOR_PIEL, detalle.getColor_piel());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_SOMBRA_DESCANSO, detalle.getSombra_descanso());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_MALLA_OSCURA, detalle.getMalla_oscura());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_PROG_EXPO_RADIACION, detalle.getProg_expo_radiacion());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_TRAB_AIRE_LIBRE, detalle.getTrab_aire_libre());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_R_CERTIFICACION, detalle.getR_certificacion());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_R_OSCURA, detalle.getR_oscura());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_MANGAL, detalle.getMangal());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_TELAG, detalle.getTelag());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_UTILIZA, detalle.getUtiliza());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_GUIA_FPS, detalle.getGuia_fps());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_FRECUENCIA_APLICACION, detalle.getFrecuencia_aplicacion());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_TRAJ_PROT_ELECTROMAGNETICA, detalle.getTraj_prot_electromagnetica());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_B_MAFNETICO, detalle.getB_mafnetico());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_X, detalle.getX());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_Y, detalle.getY());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_Z, detalle.getZ());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_RI_CALZ, detalle.getRi_calz());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_RI_CALZL, detalle.getRi_calzl());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_RI_CAMIST, detalle.getRi_camist());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_RI_CAMISMC, detalle.getRi_camismc());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_RI_CAMISML, detalle.getRi_camisml());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_RI_SUJETB, detalle.getRi_sujetb());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_CB_MANGAC, detalle.getCb_mangac());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_CB_LIGERAMC, detalle.getCb_ligeramc());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_CB_NORMALML, detalle.getCb_normalml());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_CB_CFRANML, detalle.getCb_cfranml());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_CB_BLIGMC, detalle.getCb_bligmc());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_P_COR, detalle.getP_cor());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_P_LIG, detalle.getP_lig());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_P_NORM, detalle.getP_norm());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_P_FRAN, detalle.getP_fran());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_PUL_CHASM, detalle.getPul_chasm());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_PUL_LIG, detalle.getPul_lig());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_PUL_MED, detalle.getPul_med());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_PUL_GRUE, detalle.getPul_grue());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_PA_ABRI, detalle.getPa_abri());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_PA_CHAL, detalle.getPa_chal());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_PA_PARK, detalle.getPa_park());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_PA_MONF, detalle.getPa_monf());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_CH_LIG, detalle.getCh_lig());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_CH_CHAQ, detalle.getCh_chaq());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_CH_BATAT, detalle.getCh_batat());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_CH_MONOT, detalle.getCh_monot());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_D_ZAPSD, detalle.getD_zapsd());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_D_ZAPSG, detalle.getD_zapsg());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_D_CALC, detalle.getD_calc());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_D_MED, detalle.getD_med());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_D_CALCGC, detalle.getD_calcgc());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_D_CALCGL, detalle.getD_calcgl());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_D_BOTAS, detalle.getD_botas());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_D_GUANT, detalle.getD_guant());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_ID_NIVEL_D, detalle.getId_nivel_d());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_NOM_NIVEL_D, detalle.getNom_nivel_d());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_ID_METODO_DETERM, detalle.getId_metodo_determ());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_METODO_DETERM, detalle.getMetodo_determ());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_ID_TIPO_TRABAJO, detalle.getId_tipo_trabajo());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_TIPO_TRABAJO, detalle.getTipo_trabajo());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_OCUPACION, detalle.getOcupacion());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_RANGO_TASA_METAB, detalle.getRango_tasa_metab());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_CLASE, detalle.getClase());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_ACTIVIDAD_DETER, detalle.getActividad_deter());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_TASA_METAB, detalle.getTasa_metab());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_TASA_METAB_KCAL, detalle.getTasa_metab_kcal());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_FRECUENCIA_DETER, detalle.getFrecuencia_deter());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_GENERO_DETER, detalle.getGenero_deter());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_X2, detalle.getX2());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_Y2, detalle.getY2());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_Z2, detalle.getZ2());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_X3, detalle.getX3());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_Y3, detalle.getY3());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_Z3, detalle.getZ3());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_X4, detalle.getX4());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_Y4, detalle.getY4());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_Z4, detalle.getZ4());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_ESTADO, detalle.getEstado());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_FEC_REG, detalle.getFec_reg());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_USER_REG, detalle.getUser_reg());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_FEC_ACT, detalle.getFec_act());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_USER_ACT, detalle.getUser_act());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_FEC_ELI, detalle.getFec_eli());
                values.put(Util_RegistroFormatos_Detalle.CAMPO_USER_ELI, detalle.getUser_eli());

                // Insertar el cine en la base de datos
                long resultado = db.insert(Util_RegistroFormatos_Detalle.TABLA_REGISTRO_DETALLE, null, values);
                if(resultado == -1){
                    Log.e("TAG-E", "Error al insertar REGISTRO_DETALLE - FORMATOS en la base de datos local");
                } else {
                    Log.d("TAG-D", "REGISTRO_DETALLE - FORMATOS insertado en la base de datos local");
                }
            }
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (db != null && db.isOpen()) {
                db.endTransaction(); // Finalizar la transacción si aún está abierta
                db.close(); // Cerrar la base de datos
            }
        }
    }
    public void insertarRegistroDetalle(RegistroFormatos_Detalle detalle) {
        SQLiteDatabase db = dataHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ID_FORMATO_REG_DETALLE, detalle.getId_formato_reg_detalle());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ID_PLAN_TRABAJO_FORMATO_REG, detalle.getId_plan_trabajo_formato_reg());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ORDEN, detalle.getOrden());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_LJ_DB, detalle.getLj_db());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_FJ, detalle.getFj());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_LJ_10, detalle.getLj_10());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_10_POT_LJ, detalle.get_10_pot_lj());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_MULTI, detalle.getMulti());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PLAN_MANTENIMIENTO_ILUM, detalle.getPlan_mantenimiento_ilum());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_IL_1, detalle.getIl_1());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_IL_2, detalle.getIl_2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_IL_3, detalle.getIl_3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_IL_4, detalle.getIl_4());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_IL_5, detalle.getIl_5());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_IL_6, detalle.getIl_6());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_IL_7, detalle.getIl_7());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_IL_8, detalle.getIl_8());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PUNTOS_MED, detalle.getPuntos_med());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TIPO_ILUMINACION, detalle.getTipo_iluminacion());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TIPO_ILUMINACION_ART, detalle.getTipo_iluminacion_art());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TIPO_ILUMINACION_NAT, detalle.getTipo_iluminacion_nat());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_DESC_AMB_TRABAJO, detalle.getDesc_amb_trabajo());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CANT_ILUMINARIAS, detalle.getCant_iluminarias());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_AREA_TRABAJO_M2, detalle.getArea_trabajo_m2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ALTURA_P_TRABAJO, detalle.getAltura_p_trabajo());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_N_LAMPARAS, detalle.getN_lamparas());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ALTURA_P_LUMINARIA, detalle.getAltura_p_luminaria());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_COLOR_PARED, detalle.getColor_pared());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_COLOR_PISO, detalle.getColor_piso());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ESTADO_FISICO, detalle.getEstado_fisico());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_FRECUENCIA, detalle.getFrecuencia());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_FUENTE_GENERADORA, detalle.getFuente_generadora());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_DESC_FUENTE_FRIO, detalle.getDesc_fuente_frio());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TIPO_FUENTE, detalle.getTipo_fuente());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_MANT_FUENTE, detalle.getMant_fuente());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ROPA_INTERIOR, detalle.getRopa_interior());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CAMISA_BLUSA, detalle.getCamisa_blusa());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PANTALON, detalle.getPantalon());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PULLOVER, detalle.getPullover());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ABRIGO, detalle.getAbrigo());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CHAQUETA, detalle.getChaqueta());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_DIVERSOS, detalle.getDiversos());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ACLIMATADO, detalle.getAclimatado());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ZONA_SOMBRA, detalle.getZona_sombra());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ROTACION_PERSONAL, detalle.getRotacion_personal());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TIEMPO_RECUPERACION, detalle.getTiempo_recuperacion());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_BEBIDA_CALIENTE, detalle.getBebida_caliente());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CAPA_EXPO_FRIO, detalle.getCapa_expo_frio());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_DISPENSADOR, detalle.getDispensador());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CAT_TRABAJO, detalle.getCat_trabajo());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PORC_DESCA, detalle.getPorc_desca());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_VESTIMENTA_PERSONAL, detalle.getVestimenta_personal());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_MATERIAL_PRENDA, detalle.getMaterial_prenda());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_AT_DESNUDO, detalle.getAt_desnudo());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_AT_LIGERO, detalle.getAt_ligero());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_AT_MEDIO, detalle.getAt_medio());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_AT_PESADO, detalle.getAt_pesado());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_COLOR_PREDOMINANTE, detalle.getColor_predominante());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_ZS, detalle.getEpp_zs());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_CASCO, detalle.getEpp_casco());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_LENTES, detalle.getEpp_lentes());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_GUANTES, detalle.getEpp_guantes());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_OREJERAS, detalle.getEpp_orejeras());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_TAPONES, detalle.getEpp_tapones());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_CNUCA, detalle.getEpp_cnuca());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_GORRO, detalle.getEpp_gorro());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_BOTAS, detalle.getEpp_botas());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_OTRO_EPP, detalle.getOtro_epp());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_LENTES_BRILLO, detalle.getEpp_lentes_brillo());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PROT_LAT, detalle.getProt_lat());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_GORRO_2, detalle.getEpp_gorro_2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_CASCO_2, detalle.getEpp_casco_2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_EPP_NINGUNO, detalle.getEpp_ninguno());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PROT_LEGION, detalle.getProt_legion());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PROT_AANCHA, detalle.getProt_aancha());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ROP_CCERTI, detalle.getRop_ccerti());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ROP_COSCURO, detalle.getRop_coscuro());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ROP_MLARGA, detalle.getRop_mlarga());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TGRUESA, detalle.getTgruesa());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_UTIL_FPS, detalle.getUtil_fps());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_FREC_APLIC, detalle.getFrec_aplic());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_OTRA_FRECUENCIA, detalle.getOtra_frecuencia());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CUBRE_NUCA, detalle.getCubre_nuca());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_LENT_OSCURO, detalle.getLent_osc());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TECNICA_ACONDAIRE, detalle.getTecnica_acondaire());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_DETALLE_TECNICA_ACONDAIRE, detalle.getDetalle_tecnica_acondaire());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_DESC_ATUENDO, detalle.getDesc_atuendo());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_P_AACONDICIONADO, detalle.getP_aacondicionado());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_T_GLOBO, detalle.getT_globo());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_T_GLOBO_2, detalle.getT_globo_2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_T_GLOBO_3, detalle.getT_globo_3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_T_BULBO, detalle.getT_bulbo());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_T_BULBO2, detalle.getT_bulbo2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_T_BULBO3, detalle.getT_bulbo3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_NOM_TAREA1, detalle.getNom_tarea1());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CICLO_TRABAJO1, detalle.getCiclo_trabajo1());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_POSICION_1, detalle.getPosicion_1());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PCUERPO_1, detalle.getPcuerpo_1());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_INTENSIDAD_1, detalle.getIntensidad_1());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_NOM_TAREA2, detalle.getNom_tarea2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CICLO_TRABAJO2, detalle.getCiclo_trabajo2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_POSICION_2, detalle.getPosicion_2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PCUERPO_2, detalle.getPcuerpo_2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_INTENSIDAD_2, detalle.getIntensidad_2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_NOM_TAREA3, detalle.getNom_tarea3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CICLO_TRABAJO3, detalle.getCiclo_trabajo3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_POSICION_3, detalle.getPosicion_3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PCUERPO_3, detalle.getPcuerpo_3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_INTENSIDAD_3, detalle.getIntensidad_3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_NOM_TAREA4, detalle.getNom_tarea4());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CICLO_TRABAJO4, detalle.getCiclo_trabajo4());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_POSICION_4, detalle.getPosicion_4());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PCUERPO_4, detalle.getPcuerpo_4());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_INTENSIDAD_4, detalle.getIntensidad_4());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_NOM_TAREA5, detalle.getNom_tarea5());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CICLO_TRABAJO5, detalle.getCiclo_trabajo5());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_POSICION_5, detalle.getPosicion_5());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PCUERPO_5, detalle.getPcuerpo_5());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_INTENSIDAD_5, detalle.getIntensidad_5());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_HORARIO_LV, detalle.getHorario_lv());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_HORARIO_S, detalle.getHorario_s());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_WBGT, detalle.getWbgt());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_WBGT_2, detalle.getWbgt_2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_WBGT_3, detalle.getWbgt_3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_T_AIRE, detalle.getT_aire());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_T_AIRE_2, detalle.getT_aire_2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_T_AIRE_3, detalle.getT_aire_3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_H_RELATIVA, detalle.getH_relativa());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_H_RELATIVA_2, detalle.getH_relativa_2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_H_RELATIVA_3, detalle.getH_relativa_3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_V_VIENTO, detalle.getV_viento());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_V_VIENTO_2, detalle.getV_viento_2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_V_VIENTO_3, detalle.getV_viento_3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_V_VTO4, detalle.getV_vto4());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_V_VTO5, detalle.getV_vto5());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_V_VTO6, detalle.getV_vto6());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_V_VTO7, detalle.getV_vto7());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_V_VTO8, detalle.getV_vto8());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_V_VTO9, detalle.getV_vto9());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_V_VTO10, detalle.getV_vto10());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_OTRO_VEST, detalle.getOtro_vest());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TIPO_MEDICION_ILU, detalle.getTipo_medicion_ilu());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_LARG_ESCRIT, detalle.getLarg_escrit());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ANCH_ESCRIT, detalle.getAnch_escrit());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_NUM_PMEDICION, detalle.getNum_pmedicion());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ALT_PLTRABAJO, detalle.getAlt_pltrabajo());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_LONG_SALON, detalle.getLong_salon());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ANCH_SALON, detalle.getAnch_salon());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ALT_PLTRABAJO_ILU, detalle.getAlt_pltrabajo_ilu());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CONST_SALON, detalle.getConst_salon());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_NUM_MIN_PMEDIC, detalle.getNum_min_pmedic());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TEMP, detalle.getTemp());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_MTR_SUBIDA, detalle.getMtr_subida());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TIPO_PIEL, detalle.getTipo_piel());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_COLOR_PIEL, detalle.getColor_piel());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_SOMBRA_DESCANSO, detalle.getSombra_descanso());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_MALLA_OSCURA, detalle.getMalla_oscura());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PROG_EXPO_RADIACION, detalle.getProg_expo_radiacion());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TRAB_AIRE_LIBRE, detalle.getTrab_aire_libre());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_R_CERTIFICACION, detalle.getR_certificacion());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_R_OSCURA, detalle.getR_oscura());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_MANGAL, detalle.getMangal());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TELAG, detalle.getTelag());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_UTILIZA, detalle.getUtiliza());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_GUIA_FPS, detalle.getGuia_fps());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_FRECUENCIA_APLICACION, detalle.getFrecuencia_aplicacion());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TRAJ_PROT_ELECTROMAGNETICA, detalle.getTraj_prot_electromagnetica());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_B_MAFNETICO, detalle.getB_mafnetico());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_X, detalle.getX());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_Y, detalle.getY());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_Z, detalle.getZ());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_RI_CALZ, detalle.getRi_calz());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_RI_CALZL, detalle.getRi_calzl());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_RI_CAMIST, detalle.getRi_camist());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_RI_CAMISMC, detalle.getRi_camismc());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_RI_CAMISML, detalle.getRi_camisml());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_RI_SUJETB, detalle.getRi_sujetb());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CB_MANGAC, detalle.getCb_mangac());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CB_LIGERAMC, detalle.getCb_ligeramc());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CB_NORMALML, detalle.getCb_normalml());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CB_CFRANML, detalle.getCb_cfranml());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CB_BLIGMC, detalle.getCb_bligmc());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_P_COR, detalle.getP_cor());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_P_LIG, detalle.getP_lig());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_P_NORM, detalle.getP_norm());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_P_FRAN, detalle.getP_fran());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PUL_CHASM, detalle.getPul_chasm());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PUL_LIG, detalle.getPul_lig());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PUL_MED, detalle.getPul_med());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PUL_GRUE, detalle.getPul_grue());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PA_ABRI, detalle.getPa_abri());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PA_CHAL, detalle.getPa_chal());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PA_PARK, detalle.getPa_park());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_PA_MONF, detalle.getPa_monf());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CH_LIG, detalle.getCh_lig());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CH_CHAQ, detalle.getCh_chaq());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CH_BATAT, detalle.getCh_batat());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CH_MONOT, detalle.getCh_monot());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_D_ZAPSD, detalle.getD_zapsd());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_D_ZAPSG, detalle.getD_zapsg());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_D_CALC, detalle.getD_calc());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_D_MED, detalle.getD_med());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_D_CALCGC, detalle.getD_calcgc());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_D_CALCGL, detalle.getD_calcgl());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_D_BOTAS, detalle.getD_botas());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_D_GUANT, detalle.getD_guant());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ID_NIVEL_D, detalle.getId_nivel_d());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_NOM_NIVEL_D, detalle.getNom_nivel_d());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ID_METODO_DETERM, detalle.getId_metodo_determ());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_METODO_DETERM, detalle.getMetodo_determ());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ID_TIPO_TRABAJO, detalle.getId_tipo_trabajo());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TIPO_TRABAJO, detalle.getTipo_trabajo());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_OCUPACION, detalle.getOcupacion());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_RANGO_TASA_METAB, detalle.getRango_tasa_metab());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_CLASE, detalle.getClase());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ACTIVIDAD_DETER, detalle.getActividad_deter());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TASA_METAB, detalle.getTasa_metab());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_TASA_METAB_KCAL, detalle.getTasa_metab_kcal());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_FRECUENCIA_DETER, detalle.getFrecuencia_deter());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_GENERO_DETER, detalle.getGenero_deter());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_X2, detalle.getX2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_Y2, detalle.getY2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_Z2, detalle.getZ2());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_X3, detalle.getX3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_Y3, detalle.getY3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_Z3, detalle.getZ3());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_X4, detalle.getX4());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_Y4, detalle.getY4());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_Z4, detalle.getZ4());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_ESTADO, detalle.getEstado());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_FEC_REG, detalle.getFec_reg());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_USER_REG, detalle.getUser_reg());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_FEC_ACT, detalle.getFec_act());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_USER_ACT, detalle.getUser_act());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_FEC_ELI, detalle.getFec_eli());
        values.put(Util_RegistroFormatos_Detalle.CAMPO_USER_ELI, detalle.getUser_eli());

        Long idResultante = db.insert(Util_RegistroFormatos_Detalle.TABLA_REGISTRO_DETALLE, Util_RegistroFormatos_Detalle.CAMPO_ID_FORMATO_REG_DETALLE,values);

        db.close();
    }

    /*public List<String> obtener_CodEquipos(){
        List<String> codigosList = new ArrayList<>();
        SQLiteDatabase db = dataHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT "+ Util_Equipos.CAMPO_CODIGO +" FROM "+ Util_Equipos.TABLA_EQUIPOS, null);

        if(cursor.moveToFirst()){
            do{
                codigosList.add(cursor.getString(cursor.getColumnIndex(Util_Equipos.CAMPO_CODIGO)));
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return codigosList;
    }*/

    public RegistroFormatos_Detalle Buscar(String cod_detalle){
        RegistroFormatos_Detalle detalle = null;
        SQLiteDatabase db = dataHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Util_RegistroFormatos_Detalle.TABLA_REGISTRO_DETALLE + " WHERE id_formato_reg_detalle = ?", new String[]{cod_detalle});

        if(cursor.moveToFirst()){
            do{
                detalle = new RegistroFormatos_Detalle(
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_ID_FORMATO_REG_DETALLE)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_ID_PLAN_TRABAJO_FORMATO_REG)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_ORDEN)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_LJ_DB)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_FJ)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_LJ_10)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_10_POT_LJ)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_MULTI)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_PLAN_MANTENIMIENTO_ILUM)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_IL_1)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_IL_2)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_IL_3)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_IL_4)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_IL_5)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_IL_6)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_IL_7)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_IL_8)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_PUNTOS_MED)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_TIPO_ILUMINACION)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_TIPO_ILUMINACION_ART)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_TIPO_ILUMINACION_NAT)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_DESC_AMB_TRABAJO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_CANT_ILUMINARIAS)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_AREA_TRABAJO_M2)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_ALTURA_P_TRABAJO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_N_LAMPARAS)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_ALTURA_P_LUMINARIA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_COLOR_PARED)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_COLOR_PISO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_ESTADO_FISICO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_FRECUENCIA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_FUENTE_GENERADORA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_DESC_FUENTE_FRIO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_TIPO_FUENTE)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_MANT_FUENTE)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_ROPA_INTERIOR)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_CAMISA_BLUSA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_PANTALON)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_PULLOVER)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_ABRIGO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_CHAQUETA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_DIVERSOS)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_ACLIMATADO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_ZONA_SOMBRA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_ROTACION_PERSONAL)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_TIEMPO_RECUPERACION)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_BEBIDA_CALIENTE)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_CAPA_EXPO_FRIO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_DISPENSADOR)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_CAT_TRABAJO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_PORC_DESCA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_VESTIMENTA_PERSONAL)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_MATERIAL_PRENDA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_AT_DESNUDO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_AT_LIGERO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_AT_MEDIO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_AT_PESADO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_COLOR_PREDOMINANTE)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_EPP_ZS)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_EPP_CASCO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_EPP_LENTES)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_EPP_GUANTES)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_EPP_OREJERAS)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_EPP_TAPONES)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_EPP_CNUCA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_EPP_GORRO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_EPP_BOTAS)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_OTRO_EPP)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_EPP_LENTES_BRILLO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_PROT_LAT)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_EPP_GORRO_2)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_EPP_CASCO_2)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_EPP_NINGUNO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_PROT_LEGION)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_PROT_AANCHA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_ROP_CCERTI)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_ROP_COSCURO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_ROP_MLARGA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_TGRUESA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_UTIL_FPS)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_FREC_APLIC)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_OTRA_FRECUENCIA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_CUBRE_NUCA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_LENT_OSCURO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_TECNICA_ACONDAIRE)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_DETALLE_TECNICA_ACONDAIRE)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_DESC_ATUENDO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_P_AACONDICIONADO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_T_GLOBO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_T_GLOBO_2)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_T_GLOBO_3)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_T_BULBO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_T_BULBO2)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_T_BULBO3)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_NOM_TAREA1)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_CICLO_TRABAJO1)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_POSICION_1)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_PCUERPO_1)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_INTENSIDAD_1)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_NOM_TAREA2)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_CICLO_TRABAJO2)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_POSICION_2)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_PCUERPO_2)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_INTENSIDAD_2)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_NOM_TAREA3)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_CICLO_TRABAJO3)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_POSICION_3)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_PCUERPO_3)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_INTENSIDAD_3)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_NOM_TAREA4)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_CICLO_TRABAJO4)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_POSICION_4)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_PCUERPO_4)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_INTENSIDAD_4)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_NOM_TAREA5)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_CICLO_TRABAJO5)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_POSICION_5)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_PCUERPO_5)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_INTENSIDAD_5)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_HORARIO_LV)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_HORARIO_S)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_WBGT)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_WBGT_2)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_WBGT_3)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_T_AIRE)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_T_AIRE_2)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_T_AIRE_3)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_H_RELATIVA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_H_RELATIVA_2)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_H_RELATIVA_3)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_V_VIENTO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_V_VIENTO_2)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_V_VIENTO_3)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_V_VTO4)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_V_VTO5)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_V_VTO6)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_V_VTO7)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_V_VTO8)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_V_VTO9)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_V_VTO10)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_OTRO_VEST)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_TIPO_MEDICION_ILU)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_LARG_ESCRIT)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_ANCH_ESCRIT)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_NUM_PMEDICION)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_ALT_PLTRABAJO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_LONG_SALON)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_ANCH_SALON)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_ALT_PLTRABAJO_ILU)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_CONST_SALON)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_NUM_MIN_PMEDIC)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_TEMP)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_MTR_SUBIDA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_TIPO_PIEL)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_COLOR_PIEL)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_SOMBRA_DESCANSO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_MALLA_OSCURA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_PROG_EXPO_RADIACION)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_TRAB_AIRE_LIBRE)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_R_CERTIFICACION)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_R_OSCURA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_MANGAL)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_TELAG)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_UTILIZA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_GUIA_FPS)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_FRECUENCIA_APLICACION)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_TRAJ_PROT_ELECTROMAGNETICA)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_B_MAFNETICO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_X)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_Y)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_Z)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_RI_CALZ)),// -------------
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_RI_CALZL)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_RI_CAMIST)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_RI_CAMISMC)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_RI_CAMISML)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_RI_SUJETB)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_CB_MANGAC)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_CB_LIGERAMC)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_CB_NORMALML)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_CB_CFRANML)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_CB_BLIGMC)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_P_COR)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_P_LIG)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_P_NORM)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_P_FRAN)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_PUL_CHASM)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_PUL_LIG)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_PUL_MED)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_PUL_GRUE)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_PA_ABRI)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_PA_CHAL)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_PA_PARK)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_PA_MONF)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_CH_LIG)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_CH_CHAQ)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_CH_BATAT)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_CH_MONOT)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_D_ZAPSD)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_D_ZAPSG)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_D_CALC)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_D_MED)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_D_CALCGC)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_D_CALCGL)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_D_BOTAS)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_D_GUANT)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_ID_NIVEL_D)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_NOM_NIVEL_D)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_ID_METODO_DETERM)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_METODO_DETERM)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_ID_TIPO_TRABAJO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_TIPO_TRABAJO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_OCUPACION)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_RANGO_TASA_METAB)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_CLASE)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_ACTIVIDAD_DETER)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_TASA_METAB)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_TASA_METAB_KCAL)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_FRECUENCIA_DETER)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_GENERO_DETER)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_X2)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_Y2)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_Z2)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_X3)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_Y3)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_Z3)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_X4)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_Y4)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_Z4)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_ESTADO)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_FEC_REG)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_USER_REG)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_FEC_ACT)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_USER_ACT)),
                        cursor.getString(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_FEC_ELI)),
                        cursor.getInt(cursor.getColumnIndex(Util_RegistroFormatos_Detalle.CAMPO_USER_ELI))

                );
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return detalle;
    }
}
