package com.mijael.mein;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ExistingWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mijael.mein.DAO.DAO_RegistroFormatos;
import com.mijael.mein.DAO.DAO_RegistroFormatosDetalle;
import com.mijael.mein.Entidades.RegistroFormatos;
import com.mijael.mein.Extras.InputDateConfiguration;
import com.mijael.mein.Extras.Validaciones;
import com.mijael.mein.SINCRONIZACION.RegistroFormatosDetalle_SyncWorker;
import com.mijael.mein.SINCRONIZACION.RegistroFormatos_SyncWorker;
import com.mijael.mein.Utilidades.Util_RegistroFormatos;
import com.mijael.mein.Utilidades.Util_RegistroFormatos_Detalle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class DetalleFormatosFragment extends Fragment {
    private boolean calculoRealizado = false;
    String id_plan_trabajo, id_pt_trabajo, id_formato, id_colaborador, nom_Empresa, nom_formato;
    TextView tv_fechaDetalle,tv_horaDetalle, tv_analistaDetalle, tv_areaTrabDetalle, tv_horarioDetalle, tv_pdf, tv_editar, tv_eliminar,tv_idRegistro,
    tv_horaFinal, tv_tiempoMedicion, tv_horaInicial, tv_resLmin, tv_resLmax, tv_resLeq;
    EditText txt_leq, txt_lmin, txt_lmax, txt_lpico,txt_wbgt01, txt_wbgt11, txt_wbgt17, txt_t_aire01, txt_t_aire11, txt_t_aire17, txt_t_globo01, txt_tasaMetabolicaW, txt_tasaMetabolicaK,
            txt_t_globo11, txt_t_globo17, txt_h_relativa01, txt_h_relativa11, txt_h_relativa17, txt_velViento, txt_velViento2, txt_velViento3, txt_x, txt_y, txt_z,
            txt_t_aire, txt_t_aireNegro, txt_bulboHumedo, txt_humedadRelativa, txt_velVientoCF,txt_x0, txt_x2, txt_x4, txt_x6, txt_y0, txt_y2, txt_y4, txt_y6, txt_z0, txt_z2, txt_z4, txt_z6,
            txt_leq1, txt_lmax1, txt_lmin1, txt_leq2, txt_lmax2, txt_lmin2, txt_leq3, txt_lmax3, txt_lmin3, txt_leq4, txt_lmax4, txt_lmin4, txt_leq5, txt_lmax5, txt_lmin5;
    View ViewDetalle, view_colorDetalle;
    AppCompatButton btn_subirDatos, btn_subirDatosReg,btn_calcularMedicion;
    LinearLayout linearLayout, linearAdvertencia,linearDosimetria, linearSonometria, linearHoraFinal, linearEstresTermico, linearVibracion, linearEstresFrio_Confort,
    linearBulbo, linearRadElectro;
    DAO_RegistroFormatos dao_registroFormatos;
    DAO_RegistroFormatosDetalle dao_registroDetalle;
    private static final int SYNC_INTERVAL_MIN = 15;
    public DetalleFormatosFragment() {
        // Required empty public constructor
        dao_registroFormatos = new DAO_RegistroFormatos(getActivity());
        dao_registroDetalle = new DAO_RegistroFormatosDetalle(getActivity());
    }
    Validaciones validar = new Validaciones();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (getArguments() != null) {
            nom_formato = bundle.getString("nombre_formato");
            id_formato = bundle.getString("id_formato");
            id_plan_trabajo = bundle.getString("id_plan_Trabajo");
            id_pt_trabajo = bundle.getString("id_pt_formato");
            id_colaborador = bundle.getString("nomUsuario");
            nom_Empresa = bundle.getString("nomEmpresa");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detalle_formatos, container, false);
        InputDateConfiguration config = new InputDateConfiguration(getActivity(),id_colaborador,nom_Empresa,rootView);
        linearLayout = rootView.findViewById(R.id.contenedor_Card);
        btn_subirDatos = rootView.findViewById(R.id.btn_subirDatosDet);
        btn_subirDatosReg = rootView.findViewById(R.id.btn_subirDatosReg);
        ConfigPantalla();


        //texto.setText("Lista de Registros de " + nom_formato);

        ArrayList<HashMap<String, String>> resultList = dao_registroFormatos.getListCantidadFormatoId(Integer.parseInt(id_pt_trabajo));

        int cantidadRegistros = resultList.size();
        Log.e("sdfgdf", String.valueOf(cantidadRegistros));
        for(int i = 0; i < cantidadRegistros; i++){
            MostrarFOrmatos(resultList,inflater,i);
        }


        return rootView;
    }

    private void MostrarFOrmatos(ArrayList<HashMap<String, String>> resultList, LayoutInflater inflater, int i) {
        ViewDetalle = inflater.inflate(R.layout.vista_detalle,null);
        init(ViewDetalle);
        InputDateConfiguration config = new InputDateConfiguration(getActivity(),id_colaborador,nom_Empresa,ViewDetalle);

        HashMap<String, String> primerRegistro = resultList.get(i);
        /*for (HashMap<String, String> map : resultList) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                if (key.equals("nom_analista")) nomAnalista += value + "\n";
                if (key.equals("area_trabajo")) areaTrabajo += value + "\n";
                if (key.equals("hora_trabajo")) horaTrabajo += value + "\n";
                if (key.equals("hora_inicial")) horaInicial += value + "\n";
                if (key.equals("fec_reg")) fechaRegistro += value + "\n";
            }
        }*/
        int colorAleatorio = generarColorAleatorio();
        String idregistro = primerRegistro.get("id_plan_trabajo_formato_reg");
        String nomAnalista = primerRegistro.get("nom_analista");
        String areaTrabajo = primerRegistro.get("area_trabajo");
        String horaTrabajo = primerRegistro.get("hora_trabajo");
        String horaInicial = primerRegistro.get("hora_inicial");
        String fechaRegistro = primerRegistro.get("fec_reg");

        view_colorDetalle.setBackgroundColor(colorAleatorio);
        tv_idRegistro.setText(idregistro);//MOMENTANEO SOLO DE PRUEBA
        tv_analistaDetalle.setText(nomAnalista);
        tv_areaTrabDetalle.setText(areaTrabajo);
        tv_horarioDetalle.setText(horaTrabajo);
        tv_horaDetalle.setText(horaInicial);
        tv_fechaDetalle.setText(fechaRegistro);
        tv_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistroFormatos reg = dao_registroFormatos.Buscar(idregistro);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Editar");
                //builder.setMessage("Aquí puedes poner el contenido de tu modal para editar");
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_edit, null);
                init2(dialogView);

                btn_calcularMedicion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(validar.validarCampoObligatorio(txt_leq1)&&
                                validar.validarCampoObligatorio(txt_leq2)&&
                                validar.validarCampoObligatorio(txt_leq3)&&
                                validar.validarCampoObligatorio(txt_lmax1)&&
                                validar.validarCampoObligatorio(txt_lmax2)&&
                                validar.validarCampoObligatorio(txt_lmax3)&&
                                validar.validarCampoObligatorio(txt_lmin1)&&
                                validar.validarCampoObligatorio(txt_lmin2)&&
                                validar.validarCampoObligatorio(txt_lmin3)
                        ) {
                            calcularMedicion();
                        }
                    }
                });

                if(Integer.parseInt(id_formato)==1){
                    linearHoraFinal.setVisibility(View.VISIBLE);
                    linearDosimetria.setVisibility(View.VISIBLE);
                }else if(Integer.parseInt(id_formato)==2){
                    linearHoraFinal.setVisibility(View.VISIBLE);
                    linearSonometria.setVisibility(View.VISIBLE);
                }else if(Integer.parseInt(id_formato)==4){
                    linearHoraFinal.setVisibility(View.VISIBLE);
                    linearEstresFrio_Confort.setVisibility(View.VISIBLE);
                    linearBulbo.setVisibility(View.GONE);
                }else if(Integer.parseInt(id_formato)==5){
                    linearHoraFinal.setVisibility(View.VISIBLE);
                    linearEstresTermico.setVisibility(View.VISIBLE);
                    if(reg.tipo_medicion.equals("1")){
                        txt_wbgt11.setVisibility(View.GONE);
                        txt_wbgt17.setVisibility(View.GONE);
                        txt_t_aire11.setVisibility(View.GONE);
                        txt_t_aire17.setVisibility(View.GONE);
                        txt_t_globo11.setVisibility(View.GONE);
                        txt_t_globo17.setVisibility(View.GONE);
                        txt_h_relativa11.setVisibility(View.GONE);
                        txt_h_relativa17.setVisibility(View.GONE);
                        txt_velViento2.setVisibility(View.GONE);
                        txt_velViento3.setVisibility(View.GONE);
                    }
                }else if(Integer.parseInt(id_formato)==6){
                    linearHoraFinal.setVisibility(View.VISIBLE);
                    linearEstresFrio_Confort.setVisibility(View.VISIBLE);
                    linearBulbo.setVisibility(View.VISIBLE);
                }else if(Integer.parseInt(id_formato)==8){
                    linearHoraFinal.setVisibility(View.VISIBLE);
                    linearVibracion.setVisibility(View.VISIBLE);
                }else if(Integer.parseInt(id_formato)==9){
                    linearHoraFinal.setVisibility(View.VISIBLE);
                    linearHoraFinal.setVisibility(View.VISIBLE);
                    linearRadElectro.setVisibility(View.VISIBLE);
                }else{
                    linearAdvertencia.setVisibility(View.VISIBLE);
                }
                tv_horaFinal.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {config.showTimePickerDialog(dialogView,tv_horaFinal);}});
                tv_horaInicial.setText(reg.hora_inicial);
                TextWatcher watcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String texto = s.toString();
                        String horainicial = reg.hora_inicial.toString();
                        String horafinal = tv_horaFinal.getText().toString();
                        if(!horainicial.isEmpty() && !horafinal.isEmpty()){
                            String valorTimeMed = InputDateConfiguration.calcularTiempoMedicion(horainicial,horafinal);
                            tv_tiempoMedicion.setTextColor(Color.BLACK);
                            if(valorTimeMed.equals("Hora inválida")){
                                tv_tiempoMedicion.setTextColor(Color.RED);
                            }
                            tv_tiempoMedicion.setText(valorTimeMed);
                        }else{
                            tv_tiempoMedicion.setText("");
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                };
                tv_horaFinal.addTextChangedListener(watcher);

                builder.setView(dialogView);
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (tv_tiempoMedicion.getText().toString().equals("Hora inválida")) {
                            new AlertDialog.Builder(getContext())
                                    .setTitle("Error en Hora Final")
                                    .setMessage("Ingrese un tiempo posterior a Hora Inicial.")
                                    .setPositiveButton(android.R.string.ok, null)
                                    .show();
                            //getFragmentManager().popBackStack();
                        }else{
                        String nuevahoraFinal = tv_horaFinal.getText().toString();
                        String tiempoMedicion = tv_tiempoMedicion.getText().toString();
                        Map<String, Object> camposCabecera = new HashMap<>();
                        Map<String, Object> camposDetalle = new HashMap<>();
                        if (Integer.parseInt(id_formato) == 1) {
                            String nuevoleq = txt_leq.getText().toString();
                            String nuevolpico = txt_lpico.getText().toString();
                            String nuevolmax = txt_lmax.getText().toString();
                            String nuevolmin = txt_lmin.getText().toString();

                            camposCabecera.put(Util_RegistroFormatos.CAMPO_HORA_FINAL, nuevahoraFinal);
                            camposCabecera.put(Util_RegistroFormatos.CAMPO_TIEMPO_MEDICION, tiempoMedicion);
                            camposCabecera.put(Util_RegistroFormatos.CAMPO_LEQUI, nuevoleq);
                            camposCabecera.put(Util_RegistroFormatos.CAMPO_LPICO_DBA, nuevolpico);
                            camposCabecera.put(Util_RegistroFormatos.CAMPO_LMAX, nuevolmax);
                            camposCabecera.put(Util_RegistroFormatos.CAMPO_LMIN, nuevolmin);
                            dao_registroFormatos.actualizarRegistro(Integer.parseInt(idregistro), camposCabecera);
                        }
                        if (Integer.parseInt(id_formato) == 2) {
                            String valorLeq_md1 = txt_leq1.getText().toString();
                            String valorLeq_md2 = txt_leq2.getText().toString();
                            String valorLeq_md3 = txt_leq3.getText().toString();
                            String valorLeq_md4 = txt_leq4.getText().toString();
                            String valorLeq_md5 = txt_leq5.getText().toString();
                            String valorLmax_md1 = txt_lmax1.getText().toString();
                            String valorLmax_md2 = txt_lmax2.getText().toString();
                            String valorLmax_md3 = txt_lmax3.getText().toString();
                            String valorLmax_md4 = txt_lmax4.getText().toString();
                            String valorLmax_md5 = txt_lmax5.getText().toString();
                            String valorLmin_md1 = txt_lmin1.getText().toString();
                            String valorLmin_md2 = txt_lmin2.getText().toString();
                            String valorLmin_md3 = txt_lmin3.getText().toString();
                            String valorLmin_md4 = txt_lmin4.getText().toString();
                            String valorLmin_md5 = txt_lmin5.getText().toString();

                            String lminFinal = tv_resLmin.getText().toString();
                            String lmaxFinal = tv_resLmax.getText().toString();
                            String LequiFinal = tv_resLeq.getText().toString();

                            if (validar.validarCalculo(calculoRealizado, getActivity())) {

                                camposCabecera.put(Util_RegistroFormatos.CAMPO_HORA_FINAL, nuevahoraFinal);
                                camposCabecera.put(Util_RegistroFormatos.CAMPO_TIEMPO_MEDICION, tiempoMedicion);
                                camposCabecera.put(Util_RegistroFormatos.CAMPO_LEQUI_MD1, valorLeq_md1);
                                camposCabecera.put(Util_RegistroFormatos.CAMPO_LEQUI_MD2, valorLeq_md2);
                                camposCabecera.put(Util_RegistroFormatos.CAMPO_LEQUI_MD3, valorLeq_md3);
                                camposCabecera.put(Util_RegistroFormatos.CAMPO_LEQUI_MD4, valorLeq_md4);
                                camposCabecera.put(Util_RegistroFormatos.CAMPO_LEQUI_MD5, valorLeq_md5);
                                camposCabecera.put(Util_RegistroFormatos.CAMPO_LMAX_MD1, valorLmax_md1);
                                camposCabecera.put(Util_RegistroFormatos.CAMPO_LMAX_MD2, valorLmax_md2);
                                camposCabecera.put(Util_RegistroFormatos.CAMPO_LMAX_MD3, valorLmax_md3);
                                camposCabecera.put(Util_RegistroFormatos.CAMPO_LMAX_MD4, valorLmax_md4);
                                camposCabecera.put(Util_RegistroFormatos.CAMPO_LMAX_MD5, valorLmax_md5);
                                camposCabecera.put(Util_RegistroFormatos.CAMPO_LMIN_MD1, valorLmin_md1);
                                camposCabecera.put(Util_RegistroFormatos.CAMPO_LMIN_MD2, valorLmin_md2);
                                camposCabecera.put(Util_RegistroFormatos.CAMPO_LMIN_MD3, valorLmin_md3);
                                camposCabecera.put(Util_RegistroFormatos.CAMPO_LMIN_MD4, valorLmin_md4);
                                camposCabecera.put(Util_RegistroFormatos.CAMPO_LMIN_MD5, valorLmin_md5);

                                camposCabecera.put(Util_RegistroFormatos.CAMPO_LMIN, lminFinal);
                                camposCabecera.put(Util_RegistroFormatos.CAMPO_LMAX, lmaxFinal);
                                camposCabecera.put(Util_RegistroFormatos.CAMPO_LEQUI, LequiFinal);

                                dao_registroFormatos.actualizarRegistro(Integer.parseInt(idregistro), camposCabecera);
                            }

                        }
                        if (Integer.parseInt(id_formato) == 4) {
                            String valorAire = txt_t_aire.getText().toString();
                            String valorTAireNegro = txt_t_aireNegro.getText().toString();
                            String valorHumedadRelativa = txt_humedadRelativa.getText().toString();
                            String valorVelViento = txt_velVientoCF.getText().toString();

                            camposCabecera.put(Util_RegistroFormatos.CAMPO_HORA_FINAL, nuevahoraFinal);
                            camposCabecera.put(Util_RegistroFormatos.CAMPO_TIEMPO_MEDICION, tiempoMedicion);
                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_T_AIRE, valorAire);
                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_T_GLOBO, valorTAireNegro);
                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_H_RELATIVA, valorHumedadRelativa);
                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_V_VIENTO, valorVelViento);

                            dao_registroFormatos.actualizarRegistro(Integer.parseInt(idregistro), camposCabecera);
                            dao_registroDetalle.actualizarRegistro(Integer.parseInt(idregistro), camposDetalle);
                        }
                        if (Integer.parseInt(id_formato) == 5) {
                            String nuevoBulbo = txt_wbgt01.getText().toString();
                            String nuevoAire01 = txt_t_aire01.getText().toString();
                            String nuevoGlobo01 = txt_t_globo01.getText().toString();
                            String nuevoRelativa01 = txt_h_relativa01.getText().toString();
                            String nuevoViento = txt_velViento.getText().toString();

                            camposCabecera.put(Util_RegistroFormatos.CAMPO_HORA_FINAL, nuevahoraFinal);
                            camposCabecera.put(Util_RegistroFormatos.CAMPO_TIEMPO_MEDICION, tiempoMedicion);

                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_T_BULBO, nuevoBulbo);
                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_T_AIRE, nuevoAire01);
                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_T_GLOBO, nuevoGlobo01);
                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_H_RELATIVA, nuevoRelativa01);
                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_V_VIENTO, nuevoViento);

                            if (reg.tipo_medicion.equals("2")) {

                                String nuevoBulbo2 = txt_wbgt11.getText().toString();
                                String nuevoBulbo3 = txt_wbgt17.getText().toString();
                                //nuevoAire01 = txt_t_aire01.getText().toString();
                                String nuevoAire11 = txt_t_aire11.getText().toString();
                                String nuevoAire17 = txt_t_aire17.getText().toString();
                                //nuevoGlobo01= txt_t_globo01.getText().toString();
                                String nuevoGlobo11 = txt_t_globo11.getText().toString();
                                String nuevoGlobo17 = txt_t_globo17.getText().toString();
                                //nuevoRelativa01 = txt_h_relativa01.getText().toString();
                                String nuevoRelativa11 = txt_h_relativa11.getText().toString();
                                String nuevoRelativa17 = txt_h_relativa17.getText().toString();
                                //nuevoViento = txt_velViento.getText().toString();
                                String nuevoViento2 = txt_velViento2.getText().toString();
                                String nuevoViento3 = txt_velViento3.getText().toString();

                                camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_T_BULBO2, nuevoBulbo2);
                                camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_T_BULBO3, nuevoBulbo3);
                                //camposAActualizar.put(Util_RegistroFormatos_Detalle.CAMPO_T_AIRE, nuevoAire01);
                                camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_T_AIRE_2, nuevoAire11);
                                camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_T_AIRE_3, nuevoAire17);
                                //camposAActualizar.put(Util_RegistroFormatos_Detalle.CAMPO_T_GLOBO, nuevoGlobo01);
                                camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_T_GLOBO_2, nuevoGlobo11);
                                camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_T_GLOBO_3, nuevoGlobo17);
                                //camposAActualizar.put(Util_RegistroFormatos_Detalle.CAMPO_H_RELATIVA, nuevoRelativa01);
                                camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_H_RELATIVA_2, nuevoRelativa11);
                                camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_H_RELATIVA_3, nuevoRelativa17);
                                //camposAActualizar.put(Util_RegistroFormatos_Detalle.CAMPO_V_VIENTO, nuevoViento);
                                camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_V_VIENTO_2, nuevoViento2);
                                camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_V_VIENTO_3, nuevoViento3);
                            }
                            dao_registroFormatos.actualizarRegistro(Integer.parseInt(idregistro), camposCabecera);
                            dao_registroDetalle.actualizarRegistro(Integer.parseInt(idregistro), camposDetalle);
                        }
                        if (Integer.parseInt(id_formato) == 6) {
                            String valorAire = txt_t_aire.getText().toString();
                            String valorTAireNegro = txt_t_aireNegro.getText().toString();
                            String valorBulboHumedo = txt_bulboHumedo.getText().toString();
                            String valorHumedadRelativa = txt_humedadRelativa.getText().toString();
                            String valorVelViento = txt_velVientoCF.getText().toString();

                            camposCabecera.put(Util_RegistroFormatos.CAMPO_HORA_FINAL, nuevahoraFinal);
                            camposCabecera.put(Util_RegistroFormatos.CAMPO_TIEMPO_MEDICION, tiempoMedicion);

                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_T_AIRE, valorAire);
                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_T_GLOBO, valorTAireNegro);
                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_T_BULBO, valorBulboHumedo);
                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_H_RELATIVA, valorHumedadRelativa);
                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_V_VIENTO, valorVelViento);


                            dao_registroFormatos.actualizarRegistro(Integer.parseInt(idregistro), camposCabecera);
                            dao_registroDetalle.actualizarRegistro(Integer.parseInt(idregistro), camposDetalle);
                        }
                        if (Integer.parseInt(id_formato) == 8) {
                            String nuevoX = txt_x.getText().toString();
                            String nuevoY = txt_y.getText().toString();
                            String nuevoZ = txt_z.getText().toString();

                            camposCabecera.put(Util_RegistroFormatos.CAMPO_HORA_FINAL, nuevahoraFinal);
                            camposCabecera.put(Util_RegistroFormatos.CAMPO_TIEMPO_MEDICION, tiempoMedicion);

                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_X, nuevoX);
                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_Y, nuevoY);
                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_Z, nuevoZ);

                            dao_registroFormatos.actualizarRegistro(Integer.parseInt(idregistro), camposCabecera);
                            dao_registroDetalle.actualizarRegistro(Integer.parseInt(idregistro), camposDetalle);
                        }
                        if (Integer.parseInt(id_formato) == 9) {

                            String valor_x0 = txt_x0.getText().toString();
                            String valor_x2 = txt_x2.getText().toString();
                            String valor_x4 = txt_x4.getText().toString();
                            String valor_x6 = txt_x6.getText().toString();
                            String valor_y0 = txt_y0.getText().toString();
                            String valor_y2 = txt_y2.getText().toString();
                            String valor_y4 = txt_y4.getText().toString();
                            String valor_y6 = txt_y6.getText().toString();
                            String valor_z0 = txt_z0.getText().toString();
                            String valor_z2 = txt_z2.getText().toString();
                            String valor_z4 = txt_z4.getText().toString();
                            String valor_z6 = txt_z6.getText().toString();

                            camposCabecera.put(Util_RegistroFormatos.CAMPO_HORA_FINAL, nuevahoraFinal);
                            camposCabecera.put(Util_RegistroFormatos.CAMPO_TIEMPO_MEDICION, tiempoMedicion);

                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_X, valor_x0);
                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_X2, valor_x2);
                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_X3, valor_x4);
                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_X4, valor_x6);
                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_Y, valor_y0);
                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_Y2, valor_y2);
                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_Y3, valor_y4);
                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_Y4, valor_y6);
                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_Z, valor_z0);
                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_Z2, valor_z2);
                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_Z3, valor_z4);
                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_Z4, valor_z6);

                            dao_registroFormatos.actualizarRegistro(Integer.parseInt(idregistro), camposCabecera);
                            dao_registroDetalle.actualizarRegistro(Integer.parseInt(idregistro), camposDetalle);
                        }
                        }
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Cierra el diálogo
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        tv_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Eliminar");
                builder.setMessage("¿Estás seguro de que quieres eliminar?");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Aquí puedes poner el código para eliminar
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Cierra el diálogo
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        /*btn_subirDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TareasProgramadas(RegistroFormatosDetalle_SyncWorker.class,"Registro_Detalle");
            }
        });
        btn_subirDatosReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TareasProgramadas(RegistroFormatos_SyncWorker.class,"Registro");
            }
        });*/

        linearLayout.addView(ViewDetalle);
    }

    public void init(View rootView){
        //texto = rootView.findViewById(R.id.tv_texto);
        view_colorDetalle = rootView.findViewById(R.id.view_colorDetalle);
        tv_fechaDetalle = rootView.findViewById(R.id.tv_fechaDetalle);
        tv_horaDetalle = rootView.findViewById(R.id.tv_horaDetalle);
        tv_analistaDetalle = rootView.findViewById(R.id.tv_analistaDetalle);
        tv_areaTrabDetalle = rootView.findViewById(R.id.tv_areaTrabDetalle);
        tv_horarioDetalle = rootView.findViewById(R.id.tv_horarioDetalle);
        tv_pdf = rootView.findViewById(R.id.tv_pdf);
        tv_editar = rootView.findViewById(R.id.tv_editar);
        tv_eliminar = rootView.findViewById(R.id.tv_eliminar);

        tv_idRegistro = rootView.findViewById(R.id.tv_idregistro);


    }
    public void init2(View rootView){
        linearAdvertencia = rootView.findViewById(R.id.linearAdvertencia);
        linearHoraFinal = rootView.findViewById(R.id.linearHoraFinal);
        linearDosimetria = rootView.findViewById(R.id.linearDosimetria);
        linearSonometria = rootView.findViewById(R.id.linearSonometria);
        linearVibracion = rootView.findViewById(R.id.linearVibracion);
        linearEstresTermico = rootView.findViewById(R.id.linearEstresTermico);
        linearEstresFrio_Confort = rootView.findViewById(R.id.linearConfort_Frio);
        linearBulbo = rootView.findViewById(R.id.linearBulbo);
        linearRadElectro = rootView.findViewById(R.id.linearRadElectro);

        // DATOS PARA MENSAJE
        tv_horaFinal = rootView.findViewById(R.id.tv_horaFinal);
        tv_tiempoMedicion = rootView.findViewById(R.id.tv_tiempoMedicion);
        tv_horaInicial = rootView.findViewById(R.id.tv_horaInicial);
        //DATOS PARA DOSIMETRIA
        txt_leq = rootView.findViewById(R.id.txt_leq);
        txt_lpico = rootView.findViewById(R.id.txt_lpico);
        txt_lmax = rootView.findViewById(R.id.txt_lmax);
        txt_lmin = rootView.findViewById(R.id.txt_lmin);

        //DATOS PARA SONOMETRIA
        txt_leq1 = rootView.findViewById(R.id.txt_leq_med_1);
        txt_lmax1 = rootView.findViewById(R.id.txt_lmax_med_1);
        txt_lmin1 = rootView.findViewById(R.id.txt_lmin_med_1);
        txt_leq2 = rootView.findViewById(R.id.txt_leq_med_2);
        txt_lmax2 = rootView.findViewById(R.id.txt_lmax_med_2);
        txt_lmin2 = rootView.findViewById(R.id.txt_lmin_med_2);
        txt_leq3 = rootView.findViewById(R.id.txt_leq_med_3);
        txt_lmax3 = rootView.findViewById(R.id.txt_lmax_med_3);
        txt_lmin3 = rootView.findViewById(R.id.txt_lmin_med_3);
        txt_leq4 = rootView.findViewById(R.id.txt_leq_med_4);
        txt_lmax4 = rootView.findViewById(R.id.txt_lmax_med_4);
        txt_lmin4 = rootView.findViewById(R.id.txt_lmin_med_4);
        txt_leq5 = rootView.findViewById(R.id.txt_leq_med_5);
        txt_lmax5 = rootView.findViewById(R.id.txt_lmax_med_5);
        txt_lmin5 = rootView.findViewById(R.id.txt_lmin_med_5);
        btn_calcularMedicion = rootView.findViewById(R.id.btn_calcularMedicionGeneral);
        tv_resLmin = rootView.findViewById(R.id.tv_resulLmin);
        tv_resLmax = rootView.findViewById(R.id.tv_resulLmax);
        tv_resLeq = rootView.findViewById(R.id.tv_resulLequi);

        //DATOS PARA VIBRACION
        txt_x = rootView.findViewById(R.id.txt_x);
        txt_y = rootView.findViewById(R.id.txt_y);
        txt_z = rootView.findViewById(R.id.txt_z);

        //DATOS PARA ESTRES TERMICO
        txt_wbgt01 = rootView.findViewById(R.id.txt_wbgt01);
        txt_wbgt11 = rootView.findViewById(R.id.txt_wbgt11);
        txt_wbgt17 = rootView.findViewById(R.id.txt_wbgt17);
        txt_t_aire01 = rootView.findViewById(R.id.txt_t_aire01);
        txt_t_aire11 = rootView.findViewById(R.id.txt_t_aire11);
        txt_t_aire17 = rootView.findViewById(R.id.txt_t_aire17);
        txt_t_globo01 = rootView.findViewById(R.id.txt_t_globo01);
        txt_t_globo11 = rootView.findViewById(R.id.txt_t_globo11);
        txt_t_globo17 = rootView.findViewById(R.id.txt_t_globo17);
        txt_h_relativa01 = rootView.findViewById(R.id.txt_h_relativa01);
        txt_h_relativa11 = rootView.findViewById(R.id.txt_h_relativa11);
        txt_h_relativa17 = rootView.findViewById(R.id.txt_h_relativa17);
        txt_velViento = rootView.findViewById(R.id.txt_velViento);
        txt_velViento2 = rootView.findViewById(R.id.txt_velViento2);
        txt_velViento3 = rootView.findViewById(R.id.txt_velViento3);

        //DATOS PARA CONFORT Y ESTRES POR FRIO
        txt_t_aire = rootView.findViewById(R.id.txt_t_aire);
        txt_t_aireNegro = rootView.findViewById(R.id.txt_t_aireNegro);
        txt_bulboHumedo = rootView.findViewById(R.id.txt_t_bulboHumedo);
        txt_humedadRelativa = rootView.findViewById(R.id.txt_humedadRelativa);
        txt_velVientoCF = rootView.findViewById(R.id.txt_velVientoCF);

        //DATOS PARA RADIACION ELECTROMAGNETICO
        txt_x0 = rootView.findViewById(R.id.txt_x_0);
        txt_x2 = rootView.findViewById(R.id.txt_x_2);
        txt_x4 = rootView.findViewById(R.id.txt_x_4);
        txt_x6 = rootView.findViewById(R.id.txt_x_6);
        txt_y0 = rootView.findViewById(R.id.txt_y_0);
        txt_y2 = rootView.findViewById(R.id.txt_y_2);
        txt_y4 = rootView.findViewById(R.id.txt_y_4);
        txt_y6 = rootView.findViewById(R.id.txt_y_6);
        txt_z0 = rootView.findViewById(R.id.txt_z_0);
        txt_z2 = rootView.findViewById(R.id.txt_z_2);
        txt_z4 = rootView.findViewById(R.id.txt_z_4);
        txt_z6 = rootView.findViewById(R.id.txt_z_6);


    }
    public void ConfigPantalla(){
        MainActivity activity = (MainActivity) getActivity();
        EditText txt_buscar = activity.findViewById(R.id.txt_buscarOrden);
        TextView tv_usu2 = activity.findViewById(R.id.txt_usuario2);
        TextView tv_usu = activity.findViewById(R.id.txt_usuario);
        txt_buscar.setVisibility(View.GONE);
        tv_usu2.setText("Detalle Formatos");
        tv_usu.setVisibility(View.GONE);
        FragmentContainerView fragmentContainer = activity.findViewById(R.id.fragmentContainerView);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) fragmentContainer.getLayoutParams();
        params.topMargin = 120;
        fragmentContainer.setLayoutParams(params);
    }
    private int generarColorAleatorio() {
        Random random = new Random();
        int color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
        return color;
    }
    public void TareasProgramadas(Class<? extends Worker> workerClass, String tag){
        Log.e("ENTRADA_SYNC","ENTRO A SINCRONIZAR por BOTON " + tag);
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        PeriodicWorkRequest syncRequest = new PeriodicWorkRequest.Builder(
                workerClass, // Clase que extiende de Worker
                SYNC_INTERVAL_MIN, // Intervalo de sincronización en minutos
                TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build();

        WorkManager.getInstance(getActivity()).enqueueUniquePeriodicWork(
                tag,
                ExistingPeriodicWorkPolicy.REPLACE,
                syncRequest);
    }
    private void calcularMedicion(){
        double med1 = Double.parseDouble(txt_leq1.getText().toString());
        double med2 = Double.parseDouble(txt_leq2.getText().toString());
        double med3 = Double.parseDouble(txt_leq3.getText().toString());
        double med4;
        double med5;
        double med6 = Double.parseDouble(txt_lmin1.getText().toString());
        double med7 = Double.parseDouble(txt_lmin2.getText().toString());
        double med8 = Double.parseDouble(txt_lmin3.getText().toString());
        double med9;
        double med10;
        double med11 =Double.parseDouble(txt_lmax1.getText().toString());
        double med12 = Double.parseDouble(txt_lmax2.getText().toString());
        double med13 = Double.parseDouble(txt_lmax3.getText().toString());
        double med14;
        double med15;

        try {
            String txtMed4 = txt_leq4.getText().toString();
            String txtMed5 = txt_leq5.getText().toString();
            String txtMed9 = txt_lmin4.getText().toString();
            String txtMed10 = txt_lmin5.getText().toString();
            String txtMed14 = txt_lmax4.getText().toString();
            String txtMed15 = txt_lmax5.getText().toString();
            med4 = txtMed4.isEmpty() ? 0.0 : Double.parseDouble(txtMed4);
            med5 = txtMed5.isEmpty() ? 0.0 : Double.parseDouble(txtMed5);
            med9 = txtMed9.isEmpty() ? 0.0 : Double.parseDouble(txtMed9);
            med10 = txtMed10.isEmpty() ? 0.0 : Double.parseDouble(txtMed10);
            med14 = txtMed14.isEmpty() ? 0.0 : Double.parseDouble(txtMed14);
            med15 = txtMed15.isEmpty() ? 0.0 : Double.parseDouble(txtMed15);
        } catch (NumberFormatException e) {
            // En caso de que la conversión a double falle (campo vacío o no numérico)
            med4 = 0.0;  // Puedes establecer cualquier valor predeterminado
            med5 = 0.0;  // Puedes establecer cualquier valor predeterminado
            med9 = 0.0;  // Puedes establecer cualquier valor predeterminado
            med10 = 0.0;  // Puedes establecer cualquier valor predeterminado
            med14 = 0.0;  // Puedes establecer cualquier valor predeterminado
            med15 = 0.0;  // Puedes establecer cualquier valor predeterminado
            e.printStackTrace();  // Puedes imprimir o registrar la excepción si es necesario
        }

        //double fj = 60.0 / 600.0;
        double fj = 5;
        double fj_final = 15.0;

        double[] matriz = { med1, med2, med3, med6, med7, med8, med11, med12, med13, med4, med5, med9, med10, med14, med15 };

        double maximo = Arrays.stream(matriz).max().orElse(0.0);
        double minimo = Arrays.stream(matriz)
                .filter(val -> val != 0.0) // Filtrar los valores que no son 0.0
                .min()
                .orElse(0.0);

       /* String cadena = med1 + "," + med2 + "," + med3 + "," + med6 + "," + med7 + "," + med8 + "," + med11 + "," + med12 + "," + med13 +
                (med4 != null ? "," + med4 : "") +
                (med5 != null ? "," + med5 : "") +
                (med9 != null ? "," + med5 : "") +
                (med10 != null ? "," + med5 : "") +
                (med14 != null ? "," + med5 : "") +
                (med15 != null ? "," + med5 : "");
                // ... y así sucesivamente para todos los valores de med

                String[] matriz = cadena.split(",");
        double maximo = Arrays.stream(matriz).mapToDouble(Double::parseDouble).max().getAsDouble();
        double minimo = Arrays.stream(matriz).mapToDouble(Double::parseDouble).min().getAsDouble();*/

        // Lj/10
        double lj1 = med1 / 10;
        double lj2 = med2 / 10;
        double lj3 = med3 / 10.0;
        double lj4 = med4 != 0.0 ? med4 / 10.0 : 0.0;
        double lj5 = med5 != 0.0 ? med5 / 10.0 : 0.0;
        double lj6 = med6 / 10.0;
        double lj7 = med7 / 10.0;
        double lj8 = med8 / 10.0;
        double lj9 = med9 != 0.0 ? med9 / 10.0 : 0.0;
        double lj10 = med10 != 0.0 ? med10 / 10.0 : 0.0;
        double lj11= med11 / 10.0;
        double lj12 = med12/ 10.0;
        double lj13 = med13 / 10.0;
        double lj14 = med14 != 0.0 ? med14 / 10.0 : 0.0;
        double lj15 = med15 != 0.0 ? med15 / 10.0 : 0.0;


        // 10^(Lj/10)
        double pot_lj1 = (Math.pow(10, lj1));
        double pot_lj2 = (Math.pow(10, lj2));
        double pot_lj3 = (Math.pow(10, lj3));
        double pot_lj4 = med4 != 0.0 ? (Math.pow(10, lj4)) : 0.0;
        double pot_lj5 = med5 != 0.0 ? (Math.pow(10, lj5)) : 0.0;
        double pot_lj6 = (Math.pow(10, lj6));
        double pot_lj7 = (Math.pow(10, lj7));
        double pot_lj8 = (Math.pow(10, lj8));
        double pot_lj9 = med9 != 0.0 ? Math.pow(10, lj9) : 0.0;
        double pot_lj10 = med10 != 0.0 ? (Math.pow(10, lj10)) : 0.0;
        double pot_lj11 = (Math.pow(10, lj11));
        double pot_lj12 = (Math.pow(10, lj12));
        double pot_lj13 = (Math.pow(10, lj13));
        double pot_lj14 = med14 != 0.0 ? (Math.pow(10, lj14)) : 0.0;
        double pot_lj15 = med15 != 0.0 ? (Math.pow(10, lj15)) : 0.0;

        // N°Repeticiones*Fj x 10^(Lj/10)
        double multi1 = (1 * fj * pot_lj1);
        double multi2 = (1 * fj * pot_lj2);
        double multi3 = (1 * fj * pot_lj3);
        double multi4 = med4 != 0.0 ? (1 * fj * pot_lj4) : 0.0;
        double multi5 = med5 != 0.0 ? (1 * fj * pot_lj5) : 0.0;
        /*double multi6 = (1 * fj * pot_lj6);
        double multi7 = (1 * fj * pot_lj7);
        double multi8 = (1 * fj * pot_lj8);
        double multi9 = !med9.isEmpty() ? (1 * fj * pot_lj9) : 0.0;
        double multi10 = !med10.isEmpty() ? (1 * fj * pot_lj10) : 0.0;
        double multi11 = (1 * fj * pot_lj11);
        double multi12 = (1 * fj * pot_lj12);
        double multi13 = (1 * fj * pot_lj13);
        double multi14 = !med14.isEmpty() ? (1 * fj * pot_lj14) : 0.0;
        double multi15 = !med15.isEmpty() ? (1 * fj * pot_lj15) : 0.0;*/
        //double multi15 = !med15.isEmpty() ? (1 * fj_final * pot_lj15) : 0.0;
        if(med4!=0.0){
            fj_final +=5;
        }
        if(med5!=0.0){
            fj_final +=5;
        }

        double suma = (multi1 + multi2 + multi3 + multi4 + multi5)/fj_final;
        //double suma = (multi1 + multi2 + multi3 + multi4 + multi5 + multi6 + multi7 + multi8 + multi9 + multi10 + multi11 + multi12 + multi13 + multi14 + multi15)/15;
        double Leq_dBA = Math.round(10 * Math.log10(suma) * 100) / 100.0;
        tv_resLmin.setText(String.valueOf(minimo));
        tv_resLmax.setText(String.valueOf(maximo));
        tv_resLeq.setText(String.valueOf(Leq_dBA));
        calculoRealizado = true;
    }
}