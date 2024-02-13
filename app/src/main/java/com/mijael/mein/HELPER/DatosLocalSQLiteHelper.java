package com.mijael.mein.HELPER;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatosLocalSQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "TablasLocales";
    private static final int DATABASE_VERSION = 2;
    public DatosLocalSQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE `nivel_formato_medicion` (" +
                "`id_nivel_f` INTEGER PRIMARY KEY AUTOINCREMENT," +
                "`nom_nivel` TEXT NOT NULL," +
                "`estado` INTEGER NOT NULL," +
                "`fec_reg` DATETIME NOT NULL," +
                "`user_reg` INTEGER NOT NULL," +
                "`fec_act` DATETIME NOT NULL," +
                "`user_act` INTEGER NOT NULL," +
                "`fec_eli` DATETIME NOT NULL," +
                "`user_eli` INTEGER NOT NULL" +
                ")");
        db.execSQL("INSERT INTO `nivel_formato_medicion` (`nom_nivel`, `estado`, `fec_reg`, `user_reg`, `fec_act`, `user_act`, `fec_eli`, `user_eli`) VALUES " +
                "('114 dB', 1, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0)," +
                "('94 dB', 1, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0)");


        // -------------------------  TABLA -> HORARIO de REFRIGERIO ------------------------------------------------------------
        db.execSQL("CREATE TABLE horario_refrig_formato_medicion (" +
                "id_horario_r INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nom_horario TEXT NOT NULL," +
                "desc_horario TEXT NOT NULL," +
                "estado INTEGER NOT NULL," +
                "fec_reg TEXT NOT NULL," +
                "user_reg INTEGER NOT NULL," +
                "fec_act TEXT NOT NULL," +
                "user_act INTEGER NOT NULL," +
                "fec_eli TEXT NOT NULL," +
                "user_eli INTEGER NOT NULL" +
                ")");
        db.execSQL("INSERT INTO `horario_refrig_formato_medicion` (`id_horario_r`, `nom_horario`, `desc_horario`, `estado`, `fec_reg`, `user_reg`, `fec_act`, `user_act`, `fec_eli`, `user_eli`) VALUES " +
                "(1, ' 12:00 pm - 1:00 pm', '', 1, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0)," +
                "(2, ' 12:30 pm - 1:30 pm', '', 1, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0)," +
                "(3, ' 1:00 pm - 2:00 pm', '', 1, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0)," +
                "(4, ' 1:30 pm - 2:30 pm', '', 1, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0)," +
                "(5, 'OTRO', '', 1, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0)");


        // ----------------------   TABLA -> HUMEDAD REAL ----------------------------------------------------------------------
        db.execSQL("CREATE TABLE humedad_rel_formato_medicion (" +
                "  `id_humedad` INTEGER PRIMARY KEY AUTOINCREMENT," +
                "  `valor_humedad` TEXT NOT NULL," +
                "  `estado` INTEGER NOT NULL," +
                "  `fec_reg` datetime NOT NULL," +
                "  `user_reg` INTEGER NOT NULL," +
                "  `fec_act` datetime NOT NULL," +
                "  `user_act` INTEGER NOT NULL," +
                "  `fec_eli` datetime NOT NULL," +
                "  `user_eli` INTEGER NOT NULL" +
                ")");
        db.execSQL("INSERT INTO `humedad_rel_formato_medicion` (`id_humedad`, `valor_humedad`, `estado`, `fec_reg`, `user_reg`, `fec_act`, `user_act`, `fec_eli`, `user_eli`) VALUES" +
                "(1, '50', 1, '2023-11-10 15:36:35', 0, '2023-11-10 15:36:35', 0, '2023-11-10 15:36:35', 0)," +
                "(2, '55', 1, '2023-11-10 15:36:35', 0, '2023-11-10 15:36:35', 0, '2023-11-10 15:36:35', 0)," +
                "(3, '60', 1, '2023-11-10 15:36:35', 0, '2023-11-10 15:36:35', 0, '2023-11-10 15:36:35', 0)," +
                "(4, '65', 1, '2023-11-10 15:36:35', 0, '2023-11-10 15:36:35', 0, '2023-11-10 15:36:35', 0)," +
                "(5, '70', 1, '2023-11-10 15:36:35', 0, '2023-11-10 15:36:35', 0, '2023-11-10 15:36:35', 0)," +
                "(6, '75', 1, '2023-11-10 15:36:35', 0, '2023-11-10 15:36:35', 0, '2023-11-10 15:36:35', 0)," +
                "(7, '80', 1, '2023-11-10 15:36:35', 0, '2023-11-10 15:36:35', 0, '2023-11-10 15:36:35', 0)," +
                "(8, '81', 1, '2023-11-10 15:36:35', 0, '2023-11-10 15:36:35', 0, '2023-11-10 15:36:35', 0)," +
                "(9, '82', 1, '2023-11-10 15:36:35', 0, '2023-11-10 15:36:35', 0, '2023-11-10 15:36:35', 0)," +
                "(10, '83', 1, '2023-11-10 15:36:35', 0, '2023-11-10 15:36:35', 0, '2023-11-10 15:36:35', 0)," +
                "(11, '84', 1, '2023-11-10 15:36:35', 0, '2023-11-10 15:36:35', 0, '2023-11-10 15:36:35', 0)," +
                "(12, '85', 1, '2023-11-10 15:36:35', 0, '2023-11-10 15:36:35', 0, '2023-11-10 15:36:35', 0)," +
                "(13, '86', 1, '2023-11-10 15:36:35', 0, '2023-11-10 15:36:35', 0, '2023-11-10 15:36:35', 0)," +
                "(14, '87', 1, '2023-11-10 15:36:35', 0, '2023-11-10 15:36:35', 0, '2023-11-10 15:36:35', 0)," +
                "(15, '88', 1, '2023-11-10 15:36:35', 0, '2023-11-10 15:36:35', 0, '2023-11-10 15:36:35', 0)," +
                "(16, '89', 1, '2023-11-10 15:36:35', 0, '2023-11-10 15:36:35', 0, '2023-11-10 15:36:35', 0)," +
                "(17, '90', 1, '2023-11-10 15:36:35', 0, '2023-11-10 15:36:35', 0, '2023-11-10 15:36:35', 0)");


        //--------------------------------- TABLA -> MARCA FORMATO MEDICION ---------------------------------------------------------------------
        db.execSQL("CREATE TABLE marca_formato_medicion(" +
                "  `id_marca` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "  `nom_marca` TEXT NOT NULL," +
                "  `estado` INTEGER NOT NULL," +
                "  `fec_reg` datetime NOT NULL," +
                "  `user_reg` INTEGER NOT NULL," +
                "  `fec_act` datetime NOT NULL," +
                "  `user_act` INTEGER NOT NULL," +
                "  `fec_eli` datetime NOT NULL," +
                "  `user_eli` INTEGER NOT NULL" +
                ")");
        db.execSQL("INSERT INTO `marca_formato_medicion` (`id_marca`, `nom_marca`, `estado`, `fec_reg`, `user_reg`, `fec_act`, `user_act`, `fec_eli`, `user_eli`) VALUES" +
                "(1, '3M', 1, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0)," +
                "(2, 'STEELPRO', 1, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0)," +
                "(3, 'LIBUS', 1, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0)," +
                "(4, 'OTRO', 1, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0)");


        // ----------------------------------- TABLA -> REGIMEN -------------------------------------------------------------------------------------
        db.execSQL("CREATE TABLE regimen_formato_medicion (" +
                "  `id_regimen` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "  `nom_regimen` TEXT NOT NULL," +
                "  `estado` INTEGER NOT NULL,\n" +
                "  `fec_reg` datetime NOT NULL,\n" +
                "  `user_reg` INTEGER NOT NULL,\n" +
                "  `fec_act` datetime NOT NULL,\n" +
                "  `user_act` INTEGER NOT NULL,\n" +
                "  `fec_eli` datetime NOT NULL,\n" +
                "  `user_eli` INTEGER NOT NULL\n" +
                ")");
        db.execSQL("INSERT INTO `regimen_formato_medicion` (`id_regimen`, `nom_regimen`, `estado`, `fec_reg`, `user_reg`, `fec_act`, `user_act`, `fec_eli`, `user_eli`) VALUES" +
                "(1, '6 x 1', 1, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0)," +
                "(2, '10 x 2', 1, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0)," +
                "(3, '12 x 2', 1, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0)," +
                "(4, '15 x 3', 1, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0)," +
                "(5, 'OTRO', 1, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0)");


        // ----------------------------------- TABLA -> TIEMPO DE MEDICION -------------------------------------------------------------------------------
        db.execSQL("CREATE TABLE tiempo_medic_formato_medicion (" +
                "  `id_tiempo_medic` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "  `valor_tiempo_medic` TEXT NOT NULL," +
                "  `estado` INTEGER NOT NULL," +
                "  `fec_reg` datetime NOT NULL," +
                "  `user_reg` INTEGER NOT NULL," +
                "  `fec_act` datetime NOT NULL," +
                "  `user_act` INTEGER NOT NULL," +
                "  `fec_eli` datetime NOT NULL," +
                "  `user_eli` INTEGER NOT NULL" +
                ")");
        db.execSQL("INSERT INTO `tiempo_medic_formato_medicion` (`id_tiempo_medic`, `valor_tiempo_medic`, `estado`, `fec_reg`, `user_reg`, `fec_act`, `user_act`, `fec_eli`, `user_eli`) VALUES" +
                "(1, '15', 1, '2023-11-10 15:20:33', 0, '2023-11-10 15:20:33', 0, '2023-11-10 15:20:33', 0)," +
                "(2, '25', 1, '2023-11-10 15:20:33', 0, '2023-11-10 15:20:33', 0, '2023-11-10 15:20:33', 0)");


        // --------------------------------------TABLA -> TIEMPO DE TRABAJO ------------------------------------------------------------------------------
        db.execSQL("CREATE TABLE `tiempo_trab_formato_medicion` (" +
                "  `id_tiempotrab` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "  `nom_tiempo_trab` TEXT NOT NULL," +
                "  `estado` INTEGER NOT NULL," +
                "  `fec_reg` datetime NOT NULL," +
                "  `user_reg` INTEGER NOT NULL," +
                "  `fec_act` datetime NOT NULL," +
                "  `user_act` INTEGER NOT NULL," +
                "  `fec_eli` datetime NOT NULL," +
                "  `user_eli` INTEGER NOT NULL" +
                ")");
        db.execSQL("INSERT INTO `tiempo_trab_formato_medicion` (`id_tiempotrab`, `nom_tiempo_trab`, `estado`, `fec_reg`, `user_reg`, `fec_act`, `user_act`, `fec_eli`, `user_eli`) VALUES" +
                "(1, '1 mes', 1, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0)," +
                "(2, '2 meses', 1, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0)," +
                "(3, '3 meses', 1, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0)," +
                "(4, '4 meses', 1, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0)," +
                "(5, '5 meses', 1, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0)," +
                "(6, '6 meses', 1, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0)," +
                "(7, '8 meses', 1, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0)," +
                "(8, '1 año', 1, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0)," +
                "(9, '1 año y 6 meses', 1, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0)," +
                "(10, '2 años', 1, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0)," +
                "(11, '3 años', 1, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0)," +
                "(12, '4 años', 1, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0)," +
                "(13, '5 años', 1, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0)," +
                "(14, '6 años', 1, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0)," +
                "(15, '7 años', 1, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0)," +
                "(16, '8 años', 1, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0)," +
                "(17, '9 años', 1, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0)," +
                "(18, '10 años', 1, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0)," +
                "(19, '12 años', 1, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0)," +
                "(20, '15 años', 1, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0)");


        // ------------------------ TABLA -> VARIACION ---------------------------------------------------------------------------------------------------------
        db.execSQL("CREATE TABLE `variacion_formato_medicion` (" +
                "  `id_variacion` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "  `nom_variacion` TEXT NOT NULL," +
                "  `estado` INTEGER NOT NULL," +
                "  `fec_reg` datetime NOT NULL," +
                "  `user_reg` INTEGER NOT NULL," +
                "  `fec_act` datetime NOT NULL," +
                "  `user_act` INTEGER NOT NULL," +
                "  `fec_eli` datetime NOT NULL," +
                "  `user_eli` INTEGER NOT NULL" +
                ")");
        db.execSQL("INSERT INTO `variacion_formato_medicion` (`id_variacion`, `nom_variacion`, `estado`, `fec_reg`, `user_reg`, `fec_act`, `user_act`, `fec_eli`, `user_eli`) VALUES " +
                "(1, '- 0.1 dB(A)', 1, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0)," +
                "(2, '0 dB(A)', 1, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0)," +
                "(3, '+ 0.1 dB(A)', 1, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0, '0000-00-00 00:00:00', 0)");


        // --------------------------------TABLA -> JORNADA DE TRABAJO ---------------------------------------------------------------------------------------------
        db.execSQL("CREATE TABLE `jornada_trab_formato_medicion` (" +
                "  `id_jornada` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "  `valor_jornada` INTEGER NOT NULL," +
                "  `estado` INTEGER NOT NULL," +
                "  `fec_reg` datetime NOT NULL," +
                "  `user_reg` INTEGER NOT NULL," +
                "  `fec_act` datetime NOT NULL," +
                "  `user_act` INTEGER NOT NULL," +
                "  `fec_eli` datetime NOT NULL," +
                "  `user_eli` INTEGER NOT NULL" +
                ")");
        db.execSQL("INSERT INTO `jornada_trab_formato_medicion` (`id_jornada`, `valor_jornada`, `estado`,`fec_reg`, `user_reg`, `fec_act`, `user_act`, `fec_eli`, `user_eli`) VALUES" +
                "(1, 8, 1, '2023-11-13 16:46:55', 0, '2023-11-13 16:46:55', 0, '2023-11-13 16:46:55', 0)," +
                "(2, 10, 1, '2023-11-13 16:46:55', 0, '2023-11-13 16:46:55', 0, '2023-11-13 16:46:55', 0)," +
                "(3, 12, 1, '2023-11-13 16:46:55', 0, '2023-11-13 16:46:55', 0, '2023-11-13 16:46:55', 0)");


        // -------------------- TABLA -> HORARIO DE TRABAJO ---------------------------------------------------------------------
        db.execSQL("CREATE TABLE horario_trab_fromato_medicion (" +
                " id_horario INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                " desc_horario TEXT NOT NULL," +
                " estado TEXT NOT NULL" +
                ")");
        db.execSQL("INSERT INTO horario_trab_fromato_medicion (id_horario, desc_horario, estado) VALUES" +
                "(1,'L - V: 7:30 am - 4:30 pm  S: 7:30 am - 12:30 pm',1)," +
                "(2,'L - V: 8:00 am - 5:00 pm  S: 8:00 am - 1:00 pm',1)," +
                "(3,'L - V: 8:30 am - 5:30 pm  S: 8:30 am - 1:30 pm',1)," +
                "(4,'L - V: 8:00 am - 6:00 pm  S: 8:00 am - 1:00 pm',1)," +
                "(5,'L - V: 7:00 pm - 4:00 am  S: 7:00 pm - 4:00 am',1)," +
                "(6,'L - V: 7:30 pm - 4:30 am  S: 7:00 pm - 4:30 am',1)," +
                "(7,'L - V: 8:00 pm - 5:00 am  S: 8:00 pm - 5:00 am',1)," +
                "(8,'L - V: 8:30 am - 5:30 pm  S: 8:30 am - 1:00 pm',1)," +
                "(9,'OTRO',1)");

        // ---------------------- TABLA -> MESES DEL AÑO -------------------------------------------------------------------------
        db.execSQL("CREATE TABLE meses(" +
                " id_mes INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                " nom_mes TEXT NOT NULL " +
                ")");
        db.execSQL("INSERT INTO meses (id_mes, nom_mes) VALUES " +
                "(1,'Enero'),(2,'Febrero'),(3,'Marzo'),(4,'Abril'),(5,'Mayo'),(6,'Junio'),"+
                "(7,'Julio'),(8,'Agosto'),(9,'Septiembre'),(10,'Octubre'),(11,'Noviembre'),(12,'Diciembre')");

        // ---------------------- TABLA -> AÑOS -------------------------------------------------------------------------
        db.execSQL("CREATE TABLE anios(" +
                " id_anio INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                " nom_anio TEXT NOT NULL " +
                ")");
        db.execSQL("INSERT INTO anios (id_anio, nom_anio) VALUES " +
                "(1,'2024'),(2,'2023'),(3,'2022'),(4,'2021'),(5,'2020'),(6,'2019'),(7,'2018'),"+
                "(8,'2017'),(9,'2016'),(10,'2015'),(11,'2014'),(12,'2013'),(13,'2012')");

        // ---------------------- TABLA -> VELOCIDAD DEL VIENTO -------------------------------------------------------------------------
        db.execSQL("CREATE TABLE velocidadViento(" +
                " id_velViento INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                " valor_velocidad TEXT NOT NULL " +
                ")");
        db.execSQL("INSERT INTO velocidadViento (id_velViento, valor_velocidad) VALUES " +
                "(1,'1 m/s'),(2,'1.5 m/s'),(3,'2 m/s'),(4,'2.5 m/s'),(5,'3 m/s'),(6,'3.5 m/s'),"+
                "(7,'4 m/s'),(8,'4.5 m/s'),(9,'5 m/s')");

        // ---------------------- TABLA -> MODELO DE TAPONES-------------------------------------------------------------------------
        db.execSQL("CREATE TABLE modelo_Tapones(" +
                " id_modelo INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                " tipo TEXT NOT NULL, " +
                " id_marca INTEGER, "+
                " nom_modelo TEXT, "+
                " nrr INTEGER" +
                ")");
        db.execSQL("INSERT INTO modelo_Tapones(id_modelo, tipo, id_marca, nom_modelo, nrr) VALUES " +
                "('1', '1', '0', 'E-A-R SOFT', '33')," +
                "('2', '1', '0', 'ULTRAFIT 25', '27')," +
                "('3', '1', '0', 'ULTRAFIT 27', '27'),"+
                "('4', '1', '0', '1270', '24'),"+
                "('5', '1', '0', '1271', '24'),"+
                "('6', '1', '0', '1100', '29')," +
                "('7', '1', '0', 'OTRO', '00')");

        // ---------------------- TABLA -> MODELO DE OREJERAS-------------------------------------------------------------------------
        db.execSQL("CREATE TABLE modelo_orejeras(" +
                " id_modelo INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                " tipo TEXT NOT NULL, " +
                " id_marca INTEGER, "+
                " nom_modelo TEXT, "+
                " nrr INTEGER" +
                ")");
        db.execSQL("INSERT INTO modelo_orejeras(id_modelo, tipo, id_marca, nom_modelo, nrr) VALUES " +
                "('1', '2', '0', 'OPTIME 98 - H9P3E', '23'),"+
                "('2', '2', '0', 'OPTIME 105 - H10P3E', '27'),"+
                "('3', '2', '0', 'OPTIME 105 - H10A', '30'),"+
                "('4', '2', '0', 'OPTIME 101 - H7A', '27'),"+
                "('5', '2', '0', 'OPTIME 101 - HP3E', '24'),"+
                "('6', '2', '0', 'PELTOR PTL', '26'),"+
                "('7', '2', '0', 'PELTOR X - X4P3', '25')," +
                "('8', '2', '0', 'OTRO', '00')");

        // ---------------------- TABLA -> FRECUENCIA-------------------------------------------------------------------------
        db.execSQL("CREATE TABLE frecuencia(" +
                " id_frecuencia INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                " nom_frecuencia TEXT NOT NULL " +
                ")");
        db.execSQL("INSERT INTO frecuencia(id_frecuencia, nom_frecuencia) VALUES " +
                "(1,'No tiene'),(2,'Diaria'),(3,'Dos veces por semana'),(4,'Tres veces por semana'),(5,'Semanal'),(6,'Quincenal'),"+
                "(7,'Mensual'),(8,'Bimestral'),(9,'Trimestral'),(10,'Semestaral'),(11,'Anual')");

        // ---------------------- TABLA -> TIEMPO EN AÑOS -------------------------------------------------------------------------
        db.execSQL("CREATE TABLE tiempoAnios(" +
                " id_anio INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                " nom_anio TEXT NOT NULL " +
                ")");
        db.execSQL("INSERT INTO tiempoAnios (id_anio, nom_anio) VALUES " +
                "(1,'1 año'),(2,'2 años'),(3,'3 años'),(4,'4 años'),(5,'5 años'),(6,'6 años'),"+
                "(7,'7 años'),(8,'8 años'),(9,'9 años'),(10,'10 años'),(11,'11 años'),(12,'12 años')," +
                "(13,'13 años'),(14,'14 años'),(15,'15 años'),(16,'16 años'),(17,'17 años'),(18,'18 años')");

        // ---------------------- TABLA -> TIEMPO EN MESES -------------------------------------------------------------------------
        db.execSQL("CREATE TABLE tiempoMeses(" +
                " id_mes INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                " nom_mes TEXT NOT NULL " +
                ")");
        db.execSQL("INSERT INTO tiempoMeses (id_mes, nom_mes) VALUES " +
                "(1,'1 mes'),(2,'2 meses'),(3,'3 meses'),(4,'4 meses'),(5,'5 meses'),(6,'6 meses'),"+
                "(7,'7 meses'),(8,'8 meses'),(9,'9 meses'),(10,'10 meses'),(11,'11 meses')");

        // ---------------------- TABLA -> VESTIMENTA DEL PERSONAL -------------------------------------------------------------------------
        db.execSQL("CREATE TABLE vestimenta(" +
                " id_vestimenta INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                " nom_vestimenta TEXT NOT NULL " +
                ")");
        db.execSQL("INSERT INTO vestimenta (id_vestimenta, nom_vestimenta) VALUES " +
                "(1,'Ropa de Trabajo'),(2,'Mamelucos'),(3,'Ropa tejido a doble cara'),(4,'Ropa sintética poco porosa'),(5,'Ropa de trabajo de uso limitado')");

        // ---------------------- TABLA -> MATERIAL DE VESTIMENTA -------------------------------------------------------------------------
        db.execSQL("CREATE TABLE materialVestimenta(" +
                " id_material INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                " nom_material TEXT NOT NULL " +
                ")");
        db.execSQL("INSERT INTO materialVestimenta (id_material, nom_material) VALUES " +
                "(1,'Algodón'),(2,'Poliéster'),(3,'Lino'),(4,'Lana'),(5,'Seda'),(6,'Cuero')");

        // ---------------------- TABLA -> PORCENTAJES DE ACTIVIDAD Y DESCANSO -------------------------------------------------------------------------
        db.execSQL("CREATE TABLE actividad_Descanso(" +
                " id_act INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                " nom_act_des TEXT NOT NULL " +
                ")");
        db.execSQL("INSERT INTO actividad_Descanso (id_act, nom_act_des) VALUES " +
                "(1,'0'),(2,'25'),(3,'50'),(4,'75'),(5,'100')");

        // ---------------------- TABLA -> POSICION -------------------------------------------------------------------------
        db.execSQL("CREATE TABLE posicion(" +
                " id_pos INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                " nom_pos TEXT NOT NULL " +
                ")");
        db.execSQL("INSERT INTO posicion (id_pos, nom_pos) VALUES " +
                "(1,'Andando L'),(2,'Sentado'),(3,'De pie'),(4,'Andando P'),(5,'Subida de una pendiente andando')");

        // ---------------------- TABLA -> PARTES DEL CUERPO -------------------------------------------------------------------------
        db.execSQL("CREATE TABLE partes_cuerpo(" +
                " id_cuerpo INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                " nom_cuerpo TEXT NOT NULL " +
                ")");
        db.execSQL("INSERT INTO partes_cuerpo (id_cuerpo, nom_cuerpo) VALUES " +
                "(1,'Trabajo manual'),(2,'Trabajo con un brazo'),(3,'Trabajo con dos brazos'),(4,'Trabajo con el Cuerpo')");

        // ---------------------- TABLA -> INTENSIDAD -------------------------------------------------------------------------
        db.execSQL("CREATE TABLE intensidad(" +
                " id_intensidad INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                " nom_intensidad TEXT NOT NULL " +
                ")");
        db.execSQL("INSERT INTO intensidad (id_intensidad, nom_intensidad) VALUES " +
                "(1,'Descanso'),(2,'Ligero'),(3,'Moderado'),(4,'Pesado'),(5,'Muy pesado')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS nivel_formato_medicion");
        db.execSQL("DROP TABLE IF EXISTS horario_refrig_formato_medicion");
        db.execSQL("DROP TABLE IF EXISTS humedad_rel_formato_medicion");
        db.execSQL("DROP TABLE IF EXISTS marca_formato_medicion");
        db.execSQL("DROP TABLE IF EXISTS regimen_formato_medicion");
        db.execSQL("DROP TABLE IF EXISTS tiempo_medic_formato_medicion");
        db.execSQL("DROP TABLE IF EXISTS tiempo_trab_formato_medicion");
        db.execSQL("DROP TABLE IF EXISTS variacion_formato_medicion");
        db.execSQL("DROP TABLE IF EXISTS jornada_trab_formato_medicion");

        db.execSQL("DROP TABLE IF EXISTS velocidadViento");
        db.execSQL("DROP TABLE IF EXISTS modelo_Tapones");
        db.execSQL("DROP TABLE IF EXISTS modelo_orejeras");
        db.execSQL("DROP TABLE IF EXISTS frecuencia");
        db.execSQL("DROP TABLE IF EXISTS tiempoAnios");
        db.execSQL("DROP TABLE IF EXISTS tiempoMeses");
        db.execSQL("DROP TABLE IF EXISTS vestimenta");
        db.execSQL("DROP TABLE IF EXISTS materialVestimenta");
        db.execSQL("DROP TABLE IF EXISTS actividad_Descanso");
        db.execSQL("DROP TABLE IF EXISTS posicion");
        db.execSQL("DROP TABLE IF EXISTS partes_cuerpo");
        db.execSQL("DROP TABLE IF EXISTS intensidad");

    }
}
