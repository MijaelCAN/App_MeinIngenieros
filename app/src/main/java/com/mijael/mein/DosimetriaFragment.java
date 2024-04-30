package com.mijael.mein;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mijael.mein.DAO.DAO_DatosLocal;
import com.mijael.mein.DAO.DAO_Equipos;
import com.mijael.mein.DAO.DAO_FormatosTrabajo;
import com.mijael.mein.DAO.DAO_RegistroDosimetria;
import com.mijael.mein.DAO.DAO_RegistroFormatos;
import com.mijael.mein.DAO.DAO_Usuario;
import com.mijael.mein.Entidades.Dosimetria_Registro;
import com.mijael.mein.Entidades.Equipos;
import com.mijael.mein.Entidades.Formatos_Trabajo;
import com.mijael.mein.Entidades.RegistroFormatos;
import com.mijael.mein.Entidades.Usuario;
import com.mijael.mein.Extras.FragmentoImagen;
import com.mijael.mein.Extras.InputDateConfiguration;
import com.mijael.mein.Extras.Validaciones;
import com.mijael.mein.SERVICIOS.DosimetriaService;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DosimetriaFragment extends Fragment implements FragmentoImagen.ImagePickerListener {
    Spinner cbx_nivel, cbx_variacion, cbx_tipoDoc, cbx_refrigerio, cbx_regimen, cbx_marcaTapon, cbx_marcaOrej,
            cbx_tiempoCargoAnios, cbx_tiempoCargoMeses, cbx_horarioTrabajo, cbx_meses, cbx_anio, cbx_modeloTapon, cbx_modeloOrej;
    ExtendedFloatingActionButton fabCancelar;
    RadioGroup radioGroup_Oido, radioGroup_Enferm, radioGroup_Ingenieria, radioGroup_Adminis, radioGroup_Tapones, radioGroup_Orejeras,
            radioGroup_Aislante, radioGroup_Fachada, radioGroup_Techo, radioGroup_Cerramiento, radioGroup_Cabinas, radioGroup_Capac, radioGroup_SenalPresion, radioGroup_SenalEpps,
            radioGroup_AdmTiempoExpo, radioGroup_Rotacion;
    RadioButton radio_OidoSi, radio_enferSi, radio_ingenierSI, radio_adminisSI, radio_taponesSI, radio_orejerasSI;
    CardView card_ingenier, card_adminis, card_tapones, card_orejeras;
    FloatingActionButton fabGuardar;
    AppCompatButton btn_BuscarDni;
    EditText txt_numDoc, txt_nombre, txt_edad, txt_areaTrabajo, txt_puestoTrabajo, txt_actividades,
            txt_leq, txt_lpico, txt_lmax, txt_lmin, txt_observaciones, txt_detalleEnferm;
    TextView tv_hora, tv_horaInicial, tv_horaFinal, tv_fecha, txt_otro, tv_imagen, tv_nombreUsuario, tv_nomEmpresa;
    EditText txt_otroMotivo, txt_jornada, txt_tiempoExposicion, txt_OtroIngenieria, txt_otroAdministrativo, tv_nrrTapones, tv_nrrOrej;
    CheckBox check_ruidoGenerado, check_ruidoExterno, check_Contiguo;
    AutoCompleteTextView tv_dosimetro, tv_calibrador;
    List<String> lista_codigos;
    ArrayList<String> listaCod_Equipo;
    ArrayList<String> tiposDocumento;
    String id_plan_trabajo, id_pt_trabajo, id_formato, id_colaborador, nom_Empresa;
    Uri uri;
    ImageView image;
    LinearLayout linearOtroHorario, linearOtroRegimen, linearOtroRefrigerio, linearOtroMarcaOrej, linearOtroMarcaTapones, linearOtroModeloOrej, linearOtroModeloTapones, linearBuscarDni;
    EditText txt_otroHorario, txt_otroRegimen, txt_otroRefrigerio, txt_otroMarcaOrej, txt_otroMarcaTapones, txt_otroModeloOrej, txt_otroModeloTapones;
    Formatos_Trabajo for_Dosimetria;
    RegistroFormatos registros;
    DAO_RegistroFormatos dao_registroFormatos;
    private Integer recordId = null;

    public DosimetriaFragment() {
        // Required empty public constructor
        dao_registroFormatos = new DAO_RegistroFormatos(getActivity());
    }

    Validaciones validar = new Validaciones();
    InputDateConfiguration config;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (getArguments() != null) {
            id_formato = bundle.getString("id_formato");
            id_plan_trabajo = bundle.getString("id_plan_Trabajo");
            id_pt_trabajo = bundle.getString("id_pt_formato");
            id_colaborador = bundle.getString("nomUsuario");
            nom_Empresa = bundle.getString("nomEmpresa");
            registros = bundle.getParcelable("registroForm");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_dosimetria, container, false);
        init(rootView);
        config = new InputDateConfiguration(getActivity(), id_colaborador, nom_Empresa, rootView);

        ConfigPantalla();

        DAO_Equipos nuevoequipo = new DAO_Equipos(getActivity());
        lista_codigos = nuevoequipo.obtener_CodEquipos();

        tiposDocumento = new ArrayList<>();
        tiposDocumento.add("DNI");
        tiposDocumento.add("CE");

        DAO_Usuario usuario = new DAO_Usuario(getActivity());
        Usuario nuevo = usuario.BuscarUsuario(Integer.parseInt(id_colaborador));
        String cadena = nuevo.getUsuario_nombres() + " " + nuevo.getUsuario_apater();
        tv_nombreUsuario.setText(cadena);
        tv_nomEmpresa.setText(nom_Empresa);


        configurarAutoCompleteTextView(tv_dosimetro, lista_codigos);
        configurarAutoCompleteTextView(tv_calibrador, lista_codigos);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, tiposDocumento);

        cbx_tipoDoc.setAdapter(adapter2);
        cbx_nivel.setAdapter(LlenarSpinner("nivel_formato_medicion", "nom_nivel", getActivity()));
        cbx_variacion.setAdapter(LlenarSpinner("variacion_formato_medicion", "nom_variacion", getActivity()));
        cbx_regimen.setAdapter(LlenarSpinner("regimen_formato_medicion", "nom_regimen", getActivity()));
        cbx_refrigerio.setAdapter(LlenarSpinner("horario_refrig_formato_medicion", "nom_horario", getActivity()));
        config.llenarSpinnerConNumeros(cbx_tiempoCargoAnios, 10, getActivity());
        config.llenarSpinnerConNumeros(cbx_tiempoCargoMeses, 11, getActivity());
        cbx_horarioTrabajo.setAdapter(LlenarSpinner("horario_trab_fromato_medicion", "desc_horario", getActivity()));
        cbx_meses.setAdapter(LlenarSpinner("meses", "nom_mes", getActivity()));
        cbx_anio.setAdapter(LlenarSpinner("anios", "nom_anio", getActivity()));
        cbx_marcaTapon.setAdapter(LlenarSpinner("marca_formato_medicion", "nom_marca", getActivity()));
        cbx_marcaOrej.setAdapter(LlenarSpinner("marca_formato_medicion", "nom_marca", getActivity()));
        cbx_modeloTapon.setAdapter(LlenarSpinner("modelo_Tapones", "nom_modelo", getActivity()));
        cbx_modeloOrej.setAdapter(LlenarSpinner("modelo_orejeras", "nom_modelo", getActivity()));
        tv_hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                config.showTimePickerDialog(rootView, tv_hora);
            }
        });
        tv_horaInicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                config.showTimePickerDialog(rootView, tv_horaInicial);
            }
        });
        tv_horaFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                config.showTimePickerDialog(rootView, tv_horaFinal);
            }
        });
        tv_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                config.showDatePickerDialog(rootView, tv_fecha);
            }
        });

        //LlenarNrr("modelo_Tapones","nrr",cbx_modeloTapon,tv_nrrTapones);
        //LlenarNrr("modelo_orejeras","nrr",cbx_modeloOrej,tv_nrrOrej);
        config.llenarNrrYMostrarCampos("modelo_Tapones", "nrr", cbx_modeloTapon, tv_nrrTapones, linearOtroModeloTapones);
        config.llenarNrrYMostrarCampos("modelo_orejeras", "nrr", cbx_modeloOrej, tv_nrrOrej, linearOtroModeloOrej);

        check_ruidoGenerado.setOnCheckedChangeListener(((buttonView, isChecked) -> txt_otroMotivo.setVisibility(isChecked ? View.VISIBLE : View.GONE)));

        radioGroup_Enferm.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mostrarOpcionesEnferm(group, checkedId);
            }
        });
        radioGroup_Ingenieria.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                config.mostrarOpcionesGone(group, checkedId, card_ingenier, radio_ingenierSI);
            }
        });
        radioGroup_Adminis.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                config.mostrarOpcionesGone(group, checkedId, card_adminis, radio_adminisSI);
            }
        });
        radioGroup_Tapones.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                config.mostrarOpcionesGone(group, checkedId, card_tapones, radio_taponesSI);
            }
        });
        radioGroup_Orejeras.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                config.mostrarOpcionesGone(group, checkedId, card_orejeras, radio_orejerasSI);
            }
        });

        config.MostrarCampos(linearOtroHorario, cbx_horarioTrabajo);
        config.MostrarCampos(linearOtroRegimen, cbx_regimen);
        config.MostrarCampos(linearOtroRefrigerio, cbx_refrigerio);
        config.MostrarCampos(linearOtroMarcaOrej, cbx_marcaOrej);
        config.MostrarCampos(linearOtroMarcaTapones, cbx_marcaTapon);
        //config.MostrarCampos(linearOtroModeloOrej,cbx_modeloOrej);
        //config.MostrarCampos(linearOtroModeloTapones,cbx_modeloTapon);


        cbx_tipoDoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemSelecionado = parent.getItemAtPosition(position).toString();
                if (itemSelecionado.equals("DNI")) {
                    if (config.isOnline()) {
                        linearBuscarDni.setVisibility(View.VISIBLE);
                    }
                } else {
                    linearBuscarDni.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn_BuscarDni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dni = txt_numDoc.getText().toString();
                if (!dni.isEmpty()) {
                    config.buscarTrabajador(dni, txt_nombre);
                }
            }
        });


        tv_imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentoImagen fragmentoImagen = new FragmentoImagen(DosimetriaFragment.this);
                fragmentoImagen.show(getActivity().getSupportFragmentManager(), "imagePicker");
            }
        });

        fabCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                if (fragmentManager.getBackStackEntryCount() > 0) {
                    // Retroceder a la pantalla anterior
                    fragmentManager.popBackStack();
                } else {
                    // Si no hay fragmentos en la pila, cerrar la actividad actual o realizar alguna otra acción
                    // Por ejemplo:
                    // requireActivity().finish();
                }
            }
        });
        fabGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (validar.validarCampoObligatorio(tv_dosimetro) &&
                        validar.validarCampoObligatorio(tv_calibrador) &&
                        validar.validarCampoObligatorio(tv_hora) &&
                        validar.validarCampoObligatorio(cbx_nivel) &&
                        validar.validarCampoObligatorio(cbx_variacion) &&
                        validar.validarCampoObligatorio(tv_fecha) &&
                        validar.validarCampoObligatorio(tv_horaInicial) &&
                        validar.validarCampoObligatorio(txt_jornada) &&
                        validar.validarCampoObligatorio(txt_tiempoExposicion) &&
                        //validar.validarCampoObligatorio(txt_otroMotivo)&&
                        validar.validarCampoObligatorio(txt_otro) &&
                        // CAMPO DE FOTOGRAFIA
                        validar.validarCampoObligatorio(cbx_tipoDoc) &&
                        validar.validarCampoObligatorio(txt_numDoc) &&
                        validar.validarCampoObligatorio(txt_nombre) &&
                        validar.validarCampoObligatorio(txt_edad) &&
                        validar.validarCampoObligatorio(txt_areaTrabajo) &&
                        validar.validarCampoObligatorio(txt_puestoTrabajo) &&
                        validar.validarCampoObligatorio(txt_actividades) &&
                        validar.validarCampoObligatorio(cbx_horarioTrabajo) &&
                        validar.validarCampoObligatorio(cbx_regimen) &&
                        validar.validarCampoObligatorio(cbx_refrigerio) &&
                        validar.validarCampoObligatorio(cbx_tiempoCargoMeses) &&
                        validar.validarCampoObligatorio(cbx_tiempoCargoAnios) &&
                        validar.validarCampoObligatorio(cbx_meses) &&
                        validar.validarCampoObligatorio(cbx_anio)
                    //validar.validarCampoObligatorio(txt_OtroIngenieria)&&
                    //validar.validarCampoObligatorio(txt_otroAdministrativo)&&
                    //validar.validarCampoObligatorio(txt_observaciones)
                ) {*/
                    String valorTvDosimetro = tv_dosimetro.getText().toString();
                    String valorTvCalibrador = tv_calibrador.getText().toString();
                    if (!valorTvDosimetro.equals(valorTvCalibrador)) {
                        String valorTvHora = tv_hora.getText().toString();
                        String f = tv_fecha.getText().toString();
                        String valorTvFecha = config.convertirFecha(f);
                        Log.e("valorFECHA", String.valueOf(valorTvFecha));
                        String valorTvH_Inicial = tv_horaInicial.getText().toString();
                        String valorTvH_Final = tv_horaFinal.getText().toString();

                        String valorTxtJornada = txt_jornada.getText().toString();
                        String valorTxtTiempo_Expo = txt_tiempoExposicion.getText().toString();
                        String valorTxtOtroMotivo = txt_otroMotivo.getText().toString();
                        String valorTxtOtro = txt_otro.getText().toString();
                        String valorTxtNumDoc = txt_numDoc.getText().toString();
                        String valorTxtNombre = txt_nombre.getText().toString();
                        String valorTxtEdad = txt_edad.getText().toString();
                        String valorTxtAreaTrabajo = txt_areaTrabajo.getText().toString();
                        String valorTxtPuestoTrabajo = txt_puestoTrabajo.getText().toString();
                        String valorTxtActividades = txt_actividades.getText().toString();

                        String valorTxtOtroIngenieria = txt_OtroIngenieria.getText().toString();
                        String valorTxtOtroAdministrativo = txt_otroAdministrativo.getText().toString();


                        /*String valorRuidoExterno = check_ruidoExterno.getText().toString();
                        String valorRuidocontiguo = check_Contiguo.getText().toString();
                        String valorRuidoGenePor = check_ruidoGenerado.getText().toString();*/
                        int valorRuidoExterno = check_ruidoExterno.isChecked() ? 1 : 0;
                        int valorRuidocontiguo = check_Contiguo.isChecked() ? 1 : 0;
                        int valorRuidoGenePor = check_ruidoGenerado.isChecked() ? 1 : 0;

                        int valorMolestiOido = validar.getValor2(radioGroup_Oido, rootView);
                        int valorEnferOido = validar.getValor2(radioGroup_Enferm, rootView);
                        String valorDetalleEnf = txt_detalleEnferm.getText().toString();
                        int valorGroupIng = validar.getValor2(radioGroup_Ingenieria, rootView);

                        int valorGroupAislante = 2;
                        int valorGroupFachada = 2;
                        int valorGroupTechos = 2;
                        int valorGroupCerramiento = 2;
                        int valorGroupCabinas = 2;
                        if(valorGroupIng == 1){
                            valorGroupAislante = validar.getValor2(radioGroup_Aislante, rootView);
                            valorGroupFachada = validar.getValor2(radioGroup_Fachada, rootView);
                            valorGroupTechos = validar.getValor2(radioGroup_Techo, rootView);
                            valorGroupCerramiento = validar.getValor2(radioGroup_Cerramiento, rootView);
                            valorGroupCabinas = validar.getValor2(radioGroup_Cabinas, rootView);
                        }


                        int valorGroupAdm = validar.getValor2(radioGroup_Adminis, rootView);

                        int valorGroupCapac = 2;
                        int valorGroupSenalPresion = 2;
                        int valorGroupSenalEpp = 2;
                        int valorGroupAdmTiempoExpo = 2;
                        int valorGroupRotacion = 2;

                        if(valorGroupAdm==1){
                            valorGroupCapac = validar.getValor2(radioGroup_Capac, rootView);
                            valorGroupSenalPresion = validar.getValor2(radioGroup_SenalPresion, rootView);
                            valorGroupSenalEpp = validar.getValor2(radioGroup_SenalEpps, rootView);
                            valorGroupAdmTiempoExpo = validar.getValor2(radioGroup_AdmTiempoExpo, rootView);
                            valorGroupRotacion = validar.getValor2(radioGroup_Rotacion, rootView);
                        }

                        int valorGroupoTapones = validar.getValor2(radioGroup_Tapones, rootView);
                        int valorGroupOrejeras = validar.getValor2(radioGroup_Orejeras, rootView);

                        String valorMarcaTapones = cbx_marcaTapon.getSelectedItem().toString();
                        String valorModeloTapones = cbx_modeloTapon.getSelectedItem().toString();
                        String valorNrrTapones = tv_nrrTapones.getText().toString();
                        String valorMarcaOrejeras = cbx_marcaOrej.getSelectedItem().toString();
                        String valorModeloOrjeras = cbx_modeloOrej.getSelectedItem().toString();
                        String valorNrrOrejeras = tv_nrrOrej.getText().toString();

                        String valorTxtLeq = txt_leq.getText().toString();
                        String valorTxtLpico = txt_lpico.getText().toString();
                        String valorTxtLmax = txt_lmax.getText().toString();
                        String valorTxtLmin = txt_lmin.getText().toString();
                        String valorTxtObservaciones = txt_observaciones.getText().toString();

                        String valorSeleccionadoCbxNivel = cbx_nivel.getSelectedItem().toString();
                        String valorSeleccionadoCbxVariacion = cbx_variacion.getSelectedItem().toString();
                        String valorSeleccionadoCbxTipoDoc = cbx_tipoDoc.getSelectedItem().toString();
                        String valorSeleccionadoCbxHoraioTrabajo = cbx_horarioTrabajo.getSelectedItem().toString();
                        String valorSeleccionadoCbxRegimen = cbx_regimen.getSelectedItem().toString();
                        String valorSeleccionadoCbxRefrigerio = cbx_refrigerio.getSelectedItem().toString();
                        String valorTiempoCargoAnio = cbx_tiempoCargoAnios.getSelectedItem().toString() + " año(s)";
                        String valorTiempoCargoMeses = cbx_tiempoCargoMeses.getSelectedItem().toString() + " mes(es)";
                        String valorSeleccionadoCbxMeses = cbx_meses.getSelectedItem().toString();
                        String valorSeleccionadoCbxAnio = cbx_anio.getSelectedItem().toString();

                        if (valorSeleccionadoCbxHoraioTrabajo.equals("OTRO"))
                            valorSeleccionadoCbxHoraioTrabajo = txt_otroHorario.getText().toString();
                        if (valorSeleccionadoCbxRegimen.equals("OTRO"))
                            valorSeleccionadoCbxRegimen = txt_otroRegimen.getText().toString();
                        if (valorSeleccionadoCbxRefrigerio.equals("OTRO"))
                            valorSeleccionadoCbxRefrigerio = txt_otroRefrigerio.getText().toString();
                        if (valorMarcaOrejeras.equals("OTRO"))
                            valorMarcaOrejeras = txt_otroMarcaOrej.getText().toString();
                        if (valorMarcaTapones.equals("OTRO"))
                            valorMarcaTapones = txt_otroMarcaTapones.getText().toString();
                        if (valorModeloOrjeras.equals("OTRO"))
                            valorModeloOrjeras = txt_otroModeloOrej.getText().toString();
                        if (valorModeloTapones.equals("OTRO"))
                            valorModeloTapones = txt_otroModeloTapones.getText().toString();
                        //valorSeleccionadoCbxHoraioTrabajo = valorSeleccionadoCbxHoraioTrabajo.equals("OTRO")?txt_otroHorario.getText().toString():cbx_horarioTrabajo.getSelectedItem().toString();
                        //valorSeleccionadoCbxRegimen = valorSeleccionadoCbxRegimen.equals("OTRO")?txt_otroRegimen.getText().toString():"null";
                        //valorSeleccionadoCbxRefrigerio = valorSeleccionadoCbxRefrigerio.equals("OTRO")?txt_otroRefrigerio.getText().toString():"null";
                        //valorMarcaOrejeras = valorMarcaOrejeras.equals("OTRO")?txt_otroMarcaOrej.getText().toString():"null";
                        //valorMarcaTapones = valorMarcaTapones.equals("OTRO")?txt_otroMarcaTapones.getText().toString():"null";
                        //valorModeloOrjeras = valorModeloOrjeras.equals("OTRO")?txt_otroModeloOrej.getText().toString():"null";
                        //valorModeloTapones = valorModeloTapones.equals("OTRO")?txt_otroModeloTapones.getText().toString():"null";

                        Equipos equipo1 = nuevoequipo.Buscar(valorTvDosimetro);
                        Equipos equipo2 = nuevoequipo.Buscar(valorTvCalibrador);

                        String estado_resultado = "1";
                        String fecha_registro;
                        String cod_formato = "";
                        String cod_registro = "";
                        String valorRutaFoto = "";

                        if(registros==null){
                            fecha_registro = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

                            ArrayList<HashMap<String, String>> resultList = dao_registroFormatos.getListCantidadFormatoId(Integer.parseInt(id_pt_trabajo));
                            int total_registros = dao_registroFormatos.get_cant_formato_medicion();
                            cod_formato = config.GenerarCodigoFormato(Integer.parseInt(id_formato), resultList.size());
                            cod_registro = config.generarCodigoRegistro(total_registros);
                            if(uri!=null){
                                valorRutaFoto = uri.getEncodedPath();
                            }
                        }else{
                            fecha_registro = registros.getFec_reg();
                            cod_registro = registros.getCod_registro();
                            cod_formato = registros.getCod_formato();
                            valorRutaFoto = registros.getRuta_foto();
                            id_formato = String.valueOf(registros.getId_formato());
                            id_plan_trabajo = String.valueOf(registros.getId_plan_trabajo());
                            id_pt_trabajo = String.valueOf(registros.getId_pt_formato());

                        }


                        int id_plan_formato_reg = dao_registroFormatos.getRecordIdByPosition();

                        Dosimetria_Registro cabecera = new Dosimetria_Registro( //AGREGANDO LOS VALORES A LA ENTIDAD DEL FORMATO DE DOSIMETRIA
                                -1,
                                cod_formato,
                                cod_registro,
                                id_formato,
                                id_plan_trabajo,
                                id_pt_trabajo,
                                equipo1.getCodigo(),
                                equipo1.getNombre(),
                                equipo2.getCodigo(),
                                equipo2.getNombre(),
                                equipo1.getSerie(),
                                equipo2.getSerie(),
                                String.valueOf(equipo1.getId_equipo_registro()),
                                String.valueOf(equipo2.getId_equipo_registro()),
                                id_colaborador,
                                nuevo.getUsuario_nombres() + " " + nuevo.getUsuario_apater() + " " + nuevo.getUsuario_amater(),
                                valorTvHora,
                                valorSeleccionadoCbxNivel,
                                valorSeleccionadoCbxVariacion,
                                valorTvFecha,
                                valorTvH_Inicial,
                                valorTvH_Final,
                                valorTxtTiempo_Expo,
                                valorTxtJornada,
                                valorSeleccionadoCbxTipoDoc,
                                valorTxtNumDoc,
                                valorTxtNombre,
                                valorTxtPuestoTrabajo,
                                valorTxtAreaTrabajo,
                                valorTxtActividades,
                                valorTxtEdad,
                                "" + valorRuidoExterno,
                                "" + valorRuidocontiguo,
                                "" + valorRuidoGenePor,
                                valorTxtOtroMotivo,
                                valorTxtOtro,
                                valorSeleccionadoCbxHoraioTrabajo,
                                valorSeleccionadoCbxRegimen,
                                valorSeleccionadoCbxRefrigerio,
                                valorTiempoCargoAnio,
                                valorTiempoCargoMeses,
                                String.valueOf(valorMolestiOido),
                                String.valueOf(valorEnferOido),
                                valorDetalleEnf,
                                "fecha ultimo examen",
                                valorSeleccionadoCbxMeses,
                                valorSeleccionadoCbxAnio,
                                String.valueOf(valorGroupIng),
                                String.valueOf(valorGroupAislante),
                                String.valueOf(valorGroupTechos),
                                String.valueOf(valorGroupCabinas),
                                String.valueOf(valorGroupFachada),
                                String.valueOf(valorGroupCerramiento),
                                valorTxtOtroIngenieria,
                                String.valueOf(valorGroupAdm),
                                String.valueOf(valorGroupCapac),
                                String.valueOf(valorGroupSenalPresion),
                                String.valueOf(valorGroupSenalEpp),
                                String.valueOf(valorGroupRotacion),
                                String.valueOf(valorGroupAdmTiempoExpo),
                                valorTxtOtroAdministrativo,
                                String.valueOf(valorGroupoTapones),
                                valorMarcaTapones,
                                valorModeloTapones,
                                valorNrrTapones,
                                String.valueOf(valorGroupOrejeras),
                                valorMarcaOrejeras,
                                valorModeloOrjeras,
                                valorNrrOrejeras,
                                valorTxtLeq,
                                valorTxtLpico,
                                valorTxtLmax,
                                valorTxtLmin,
                                valorTxtObservaciones,
                                estado_resultado,
                                id_colaborador,
                                fecha_registro,
                                "var1",
                                valorRutaFoto
                        );

                        if (config.isOnline()) {
                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl("https://test.meiningenieros.pe/")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();

                            DosimetriaService service1 = retrofit.create(DosimetriaService.class);
                            Dosimetria_Registro detalle = new Dosimetria_Registro("PRUEBA");
                            Gson gson = new Gson();
                            // Crear un objeto JSON principal
                            JsonObject jsonObject = new JsonObject();

                            JsonObject registroJson = gson.toJsonTree(cabecera).getAsJsonObject();
                            jsonObject.add("cabecera", registroJson);

                            JsonObject detalleJson = gson.toJsonTree(detalle).getAsJsonObject();
                            jsonObject.add("detalle", detalleJson);


                            String cadenaJson = gson.toJson(jsonObject);
                            RequestBody json = RequestBody.create(MediaType.parse("application/json"), cadenaJson);

                            Call<ResponseBody> call1 = service1.insertDosimetria(json);
                            String finalCod_formato = cod_formato; // Obsevacion
                            String finalCod_registro = cod_registro; // Observacion
                            call1.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    Log.e("exitoso", "se inserto el registro");
                                    File imageFile = new File(uri.getEncodedPath());
                                    config.uploadImage(imageFile, finalCod_formato, id_pt_trabajo, finalCod_registro);
                                    // Mostrar el JSON en el log
                                    Log.e("JSON", cadenaJson);
                                    Log.e("Respuesta", response.toString());
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Log.e("error", "Error al insertar el registro");
                                }
                            });
                            new AlertDialog.Builder(getContext())
                                    .setTitle("Registro guardado en WEB")
                                    .setMessage("El registro ha sido guardado exitosamente.")
                                    .setPositiveButton(android.R.string.ok, null)
                                    .show();
                            getFragmentManager().popBackStack();
                        } else {
                            DAO_RegistroDosimetria nuevoRegistro = new DAO_RegistroDosimetria(getActivity());//INSTANCIAR LA CLASE PARA USAR EL METODO DE INSERTAR;
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("Guardar formulario");
                            builder.setMessage("¿Deseas seguir llenando el formulario o terminar?");

                            builder.setPositiveButton("Seguir", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (registros == null) {
                                        // Realizar la inserción del registro en la base de datos
                                        boolean confirmar = nuevoRegistro.RegistrarFormato(cabecera); //LLAMAR AL METODO PARA INSERTAR LOS REGISTROS

                                        DAO_FormatosTrabajo dao_fromatosTrabajo = new DAO_FormatosTrabajo(getActivity());
                                        for_Dosimetria = dao_fromatosTrabajo.Buscar(id_plan_trabajo, id_formato);
                                        for_Dosimetria.setRealizado(for_Dosimetria.getRealizado() + 1);
                                        for_Dosimetria.setPor_realizar(for_Dosimetria.getPor_realizar() - 1);
                                        dao_fromatosTrabajo.actualizarFormatoTrabajo(for_Dosimetria);
                                    } else {
                                        // Realizar la actualización del registro en la base de datos
                                        nuevoRegistro.ActualizarFormato(cabecera, registros.getId_plan_trabajo_formato_reg());
                                    }
                                    // Permitir al usuario seguir llenando el formulario
                                }
                            });

                            builder.setNegativeButton("Terminar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (registros == null) {
                                        // Realizar la inserción del registro en la base de datos
                                        // Realizar la inserción del registro en la base de datos
                                        boolean confirmar = nuevoRegistro.RegistrarFormato(cabecera); //LLAMAR AL METODO PARA INSERTAR LOS REGISTROS

                                        DAO_FormatosTrabajo dao_fromatosTrabajo = new DAO_FormatosTrabajo(getActivity());
                                        for_Dosimetria = dao_fromatosTrabajo.Buscar(id_plan_trabajo, id_formato);
                                        for_Dosimetria.setRealizado(for_Dosimetria.getRealizado() + 1);
                                        for_Dosimetria.setPor_realizar(for_Dosimetria.getPor_realizar() - 1);
                                        dao_fromatosTrabajo.actualizarFormatoTrabajo(for_Dosimetria);
                                    } else {
                                        // Realizar la actualización del registro en la base de datos
                                        nuevoRegistro.ActualizarFormato(cabecera, registros.getId_plan_trabajo_formato_reg());
                                    }
                                    // Cerrar o volver atrás en el fragmento o actividad actual
                                    // O muestra un AlertDialog con el mensaje
                                    new AlertDialog.Builder(getActivity())
                                            .setTitle("Registro guardado Localmente")
                                            .setMessage("El registro ha sido guardado exitosamente.")
                                            .setPositiveButton(android.R.string.ok, null)
                                            .show();

                                    // Regresa al Fragment anterior
                                    getFragmentManager().popBackStack();

                                }
                            });

                            builder.show();


                        }

                    } else {
                        tv_calibrador.setError("Equipo no debe Repetir");
                        tv_calibrador.requestFocus();
                    }
                //}
            }
        });

        if (registros != null) {
            EditarCampos(registros);
        }

        return rootView;
    }

    @Override
    public void onImagePicked(Uri imageUri) {
        this.uri = imageUri;
        if (image != null && imageUri != null) {
            image.setImageURI(imageUri);
            /*File imageFile = new File(imageUri.getEncodedPath());
            config.uploadImage(imageFile);*/
        }
    }

    private void mostrarOpcionesEnferm(RadioGroup group, int checkedId) {
        if (checkedId == radio_enferSi.getId()) {
            txt_detalleEnferm.setVisibility(View.VISIBLE);
        } else {
            txt_detalleEnferm.setVisibility(View.GONE);
            txt_detalleEnferm.setText("");
        }
    }

    private void mostrarOpcionesGone(RadioGroup group, int checkedId, CardView card, RadioButton radio) {
        if (checkedId == radio.getId()) {
            card.setVisibility(View.VISIBLE);
        } else {
            card.setVisibility(View.GONE);
        }
    }

    private void init(View view) {
        //AutoCompletes Texts
        tv_dosimetro = view.findViewById(R.id.tv_dosimetro);
        tv_calibrador = view.findViewById(R.id.tv_calibrador);
        tv_nombreUsuario = view.findViewById(R.id.tv_nomUsuario);
        tv_nomEmpresa = view.findViewById(R.id.tv_nomEmpresa);

        image = view.findViewById(R.id.img_Dosimetria);

        //Botones Flotantes
        fabCancelar = view.findViewById(R.id.fabCancelar);
        fabGuardar = view.findViewById(R.id.fabGuardar);
        //btn_subirArchivo = view.findViewById(R.id.btn_abrirArchivo);

        cbx_nivel = view.findViewById(R.id.cbx_nivel);
        cbx_variacion = view.findViewById(R.id.cbx_variacion);
        cbx_regimen = view.findViewById(R.id.cbx_regimen);
        cbx_refrigerio = view.findViewById(R.id.cbx_refrigerio);
        cbx_tiempoCargoAnios = view.findViewById(R.id.cbx_tiempoCargoAnios);
        cbx_tiempoCargoMeses = view.findViewById(R.id.cbx_tiempoCargoMeses);
        cbx_horarioTrabajo = view.findViewById(R.id.cbx_horarioTrabajo);
        cbx_tipoDoc = view.findViewById(R.id.cbx_tipoDocumento);
        cbx_meses = view.findViewById(R.id.cbx_meses);
        cbx_anio = view.findViewById(R.id.cbx_anio);
        cbx_marcaTapon = view.findViewById(R.id.cbx_marcaTapones);
        cbx_marcaOrej = view.findViewById(R.id.cbx_marcaOrej);
        cbx_modeloTapon = view.findViewById(R.id.cbx_modeloTapones);
        cbx_modeloOrej = view.findViewById(R.id.cbx_modeloOrej);
        tv_nrrTapones = view.findViewById(R.id.tv_nrrTapones);
        tv_nrrOrej = view.findViewById(R.id.tv_nrrOrej);

        //Cajas de Texto
        tv_hora = view.findViewById(R.id.tv_Hora);
        tv_horaInicial = view.findViewById(R.id.tv_horaInicial);
        tv_horaFinal = view.findViewById(R.id.tv_horaFinal);
        tv_fecha = view.findViewById(R.id.tv_fecha);
        tv_imagen = view.findViewById(R.id.btn_subirFotoDosi);

        check_ruidoExterno = view.findViewById(R.id.check_ruidoExterno);
        check_Contiguo = view.findViewById(R.id.check_ruidoContiguo);
        check_ruidoGenerado = view.findViewById(R.id.check_ruidoGenerado);

        txt_otro = view.findViewById(R.id.txt_otro);
        txt_otroMotivo = view.findViewById(R.id.txt_otroMotivo);
        txt_jornada = view.findViewById(R.id.txt_jornada);
        txt_tiempoExposicion = view.findViewById(R.id.txt_tiempoExposicion);
        txt_numDoc = view.findViewById(R.id.txt_numDocumento);
        txt_nombre = view.findViewById(R.id.txt_nombre);
        txt_edad = view.findViewById(R.id.txt_edad);
        txt_areaTrabajo = view.findViewById(R.id.txt_areaTrabajo);
        txt_puestoTrabajo = view.findViewById(R.id.txt_puestoTrabajo);
        txt_actividades = view.findViewById(R.id.txt_actividades);
        txt_OtroIngenieria = view.findViewById(R.id.txt_OtrosIngenieria);
        txt_otroAdministrativo = view.findViewById(R.id.txt_OtrosAdministrativo);
        txt_leq = view.findViewById(R.id.txt_leq);
        txt_lpico = view.findViewById(R.id.txt_lpico);
        txt_lmax = view.findViewById(R.id.txt_lmax);
        txt_lmin = view.findViewById(R.id.txt_lmin);
        txt_observaciones = view.findViewById(R.id.txt_observaciones);
        txt_detalleEnferm = view.findViewById(R.id.txt_detalleEnferme);

        radioGroup_Oido = view.findViewById(R.id.radioGroupOido);
        radioGroup_Enferm = view.findViewById(R.id.radioGroupEnferm);
        radioGroup_Adminis = view.findViewById(R.id.radioGroupAdminis);
        radioGroup_Tapones = view.findViewById(R.id.radioGroupTapones);
        radioGroup_Orejeras = view.findViewById(R.id.radioGroupOrejeras);
        radioGroup_Ingenieria = view.findViewById(R.id.radioGroupIngenieria);

        radioGroup_Aislante = view.findViewById(R.id.radioGroup_Aislante);
        radioGroup_Fachada = view.findViewById(R.id.radioGroup_Fachada);
        radioGroup_Techo = view.findViewById(R.id.radioGroup_Techos);
        radioGroup_Cerramiento = view.findViewById(R.id.radioGroup_Cerramiento);
        radioGroup_Cabinas = view.findViewById(R.id.radioGroup_Cabinas);
        radioGroup_Capac = view.findViewById(R.id.radioGroup_Riesgos);
        radioGroup_SenalPresion = view.findViewById(R.id.radioGroup_SeñasPresion);
        radioGroup_SenalEpps = view.findViewById(R.id.radioGroup_EppObliga);
        radioGroup_AdmTiempoExpo = view.findViewById(R.id.radioGroup_AdminExpo);
        radioGroup_Rotacion = view.findViewById(R.id.radioGroup_Rotacion);

        radio_OidoSi = view.findViewById(R.id.radio_OidoSi);
        radio_enferSi = view.findViewById(R.id.radio_emfermedadSi);
        radio_ingenierSI = view.findViewById(R.id.radioIngenieriaSi);
        radio_adminisSI = view.findViewById(R.id.radio_AdministrativoSi);
        radio_taponesSI = view.findViewById(R.id.radioTaponesSi);
        radio_orejerasSI = view.findViewById(R.id.radio_OrejerasSi);

        card_ingenier = view.findViewById(R.id.Card_Ingenieria);
        card_adminis = view.findViewById(R.id.Card_Administrativo);
        card_tapones = view.findViewById(R.id.Card_Tapones);
        card_orejeras = view.findViewById(R.id.Card_Orejeras);

        linearOtroHorario = view.findViewById(R.id.linearOtroHorario);
        txt_otroHorario = view.findViewById(R.id.txt_otroHorario);
        linearOtroRegimen = view.findViewById(R.id.linearOtroRegimen);
        txt_otroRegimen = view.findViewById(R.id.txt_otroRegimen);
        linearOtroRefrigerio = view.findViewById(R.id.linearOtroRefrigerio);
        txt_otroRefrigerio = view.findViewById(R.id.txt_otroRefrigerio);

        linearOtroMarcaOrej = view.findViewById(R.id.linearOtroMarcaOrej);
        txt_otroMarcaOrej = view.findViewById(R.id.txt_otroMarcaOrej);
        linearOtroMarcaTapones = view.findViewById(R.id.linearOtroMarcaTapones);
        txt_otroMarcaTapones = view.findViewById(R.id.txt_otroMarcaTapones);
        linearOtroModeloOrej = view.findViewById(R.id.linearOtroModeloOrej);
        txt_otroModeloOrej = view.findViewById(R.id.txt_otroModeloOrej);
        linearOtroModeloTapones = view.findViewById(R.id.linearOtroModeloTapones);
        txt_otroModeloTapones = view.findViewById(R.id.txt_otroModeloTapones);
        linearBuscarDni = view.findViewById(R.id.linearBuscarDni);
        btn_BuscarDni = view.findViewById(R.id.btn_BuscarDni);


    }

    private ArrayAdapter<String> LlenarSpinner(String nombreTabla, String campoTabla, Context context) {

        List<String> datosParaSpinner = DAO_DatosLocal.obtenerDatosParaSpinner(nombreTabla, campoTabla, getActivity());
        ArrayList<String> listaDatos = new ArrayList<>();
        listaDatos.add("");
        listaDatos.addAll(datosParaSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, listaDatos);
        return adapter;
    }

    public void showTimePickerDialog(View view, TextView cajita) {
        // Crear un TimePicker como diálogo emergente
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                getActivity(), // o getActivity() si estás en un fragmento
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // Acción a realizar cuando se selecciona la hora
                        String selectedTime = String.format("%02d:%02d", hourOfDay, minute);
                        // Hacer algo con la hora seleccionada, por ejemplo, mostrarla en un TextView
                        cajita.setText(selectedTime);
                    }
                },
                // Establecer la hora actual como predeterminada al abrir el diálogo
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                true // Opcional, establecer formato de 24 horas
        );

        timePickerDialog.show(); // Mostrar el diálogo de selección de hora
    }

    public void showDatePickerDialog(View view, TextView cajita) {
        // Obtener la fecha actual
        final Calendar calendar = Calendar.getInstance();
        int año = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int día = calendar.get(Calendar.DAY_OF_MONTH);

        // Crear un DatePicker como diálogo emergente
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(), // o getActivity() si estás en un fragmento
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Acción a realizar cuando se selecciona la fecha
                        String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        // Hacer algo con la fecha seleccionada, por ejemplo, mostrarla en un TextView
                        cajita.setText(selectedDate);
                    }
                },
                año, mes, día);

        datePickerDialog.show(); // Mostrar el diálogo de selección de fecha
    }

    private void configurarAutoCompleteTextView(AutoCompleteTextView autoCompleteTextView, List<String> listaElementos) {
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, listaElementos);
        autoCompleteTextView.setAdapter(adapter3);
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setDropDownBackgroundResource(android.R.color.white);
        autoCompleteTextView.setFilterTouchesWhenObscured(true);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Realizar acciones al seleccionar un elemento del AutoCompleteTextView si es necesario
            }
        });
    }

    private void LlenarNrr(String nomTabla, String campoTabla, Spinner spinner, EditText txt) {
        List<Object[]> listaValorNRR = DAO_DatosLocal.obtenerDatos(nomTabla, getActivity());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemSelec = parent.getItemAtPosition(position).toString();
                Log.e("Valor Modelo ", itemSelec);
                for (Object[] item : listaValorNRR) {
                    if (itemSelec.equals(item[0].toString())) {
                        txt.setText(item[1].toString());
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No se seleccionó ningún elemento en el Spinner
            }
        });
    }

    public void ConfigPantalla() {
        MainActivity activity = (MainActivity) getActivity();
        EditText txt_buscar = activity.findViewById(R.id.txt_buscarOrden);
        TextView tv_usu2 = activity.findViewById(R.id.txt_usuario2);
        TextView tv_usu = activity.findViewById(R.id.txt_usuario);
        txt_buscar.setVisibility(View.GONE);
        tv_usu2.setText(tv_usu.getText());
        tv_usu.setVisibility(View.VISIBLE);
        FragmentContainerView fragmentContainer = activity.findViewById(R.id.fragmentContainerView);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) fragmentContainer.getLayoutParams();
        params.topMargin = 120;
        fragmentContainer.setLayoutParams(params);
    }

    private void limpiarElementos(ViewGroup viewGroup) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = viewGroup.getChildAt(i);
            if (childView instanceof EditText) {
                ((EditText) childView).setText("");
            } else if (childView instanceof Spinner) {
                ((Spinner) childView).setSelection(0);
            } else if (childView instanceof RadioGroup) {
                ((RadioGroup) childView).clearCheck();
            } else if (childView instanceof ViewGroup) {
                limpiarElementos((ViewGroup) childView);
            }
        }
    }

    private void EditarCampos(RegistroFormatos registros) {
        tv_dosimetro.setText(registros.getCod_equipo1());
        tv_calibrador.setText(registros.getCod_equipo2());
        tv_hora.setText(registros.getHora_situ());
        config.asignarAdaptadorYSeleccion(cbx_nivel, "nivel_formato_medicion", "nom_nivel", registros.getNivel(), getContext());
        config.asignarAdaptadorYSeleccion(cbx_variacion, "variacion_formato_medicion", "nom_variacion", registros.getVariacion(), getContext());

        //image.setImageURI(Uri.parse(registros.getRuta_foto()));

        String fecha ="";
        if(!registros.getFec_monitoreo().isEmpty()){
            String[] fec = registros.getFec_monitoreo().split(" ");
            String[] nueva_fec = fec[0].split("-");
            fecha = nueva_fec[0] +"/"+ nueva_fec[1] +"/"+ nueva_fec[2];
        }
        tv_fecha.setText(fecha);

        tv_horaInicial.setText(registros.getHora_inicial());
        tv_horaFinal.setText(registros.getHora_final());
        txt_jornada.setText(registros.getJornada());
        txt_tiempoExposicion.setText(registros.getTiempo_exposicion());
        Log.e("bista0", registros.getCh_ruido_externo());
        Log.e("bista01", registros.getCh_ruido_antiguo());
        Log.e("bista02", registros.getCh_ruido_generado_por());
        if (registros.getCh_ruido_externo().equals("1")) check_ruidoExterno.setChecked(true);
        if (registros.getCh_ruido_antiguo().equals("1")) check_Contiguo.setChecked(true);
        if (registros.getCh_ruido_generado_por().equals("1")) check_ruidoGenerado.setChecked(true);
        txt_otroMotivo.setText(registros.getRuido_generado_por());
        txt_otro.setText(registros.getOtro_ruido());
        // FALTA DESIGNAR LA FOTO
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, tiposDocumento);
        cbx_tipoDoc.setAdapter(adapter2);
        int position = adapter2.getPosition(registros.getTipo_doc_trabajador());
        cbx_tipoDoc.setSelection(position);

        txt_numDoc.setText(registros.getNum_doc_trabajador());
        txt_nombre.setText(registros.getNom_trabajador());
        txt_edad.setText(String.valueOf(registros.getEdad_trabajador()));
        txt_areaTrabajo.setText(registros.getArea_trabajo());
        txt_puestoTrabajo.setText(registros.getPuesto_trabajador());
        txt_actividades.setText(registros.getActividades_realizadas());


        config.asignarAdaptadorYSeleccion(cbx_horarioTrabajo, "horario_trab_fromato_medicion", "desc_horario", registros.getHora_trabajo(), getContext());
        if (cbx_horarioTrabajo.getSelectedItem().equals("OTRO")) {
            txt_otroHorario.setText(registros.getHora_trabajo());
        }
        config.asignarAdaptadorYSeleccion(cbx_regimen, "regimen_formato_medicion", "nom_regimen", registros.getRegimen_laboral(), getContext());
        if (cbx_regimen.getSelectedItem().equals("OTRO")) {
            txt_otroRegimen.setText(registros.getRegimen_laboral());
        }
        config.asignarAdaptadorYSeleccion(cbx_refrigerio, "horario_refrig_formato_medicion", "nom_horario", registros.getHorario_refrigerio(), getContext());
        if (cbx_refrigerio.getSelectedItem().equals("OTRO")) {
            txt_otroRefrigerio.setText(registros.getHora_trabajo());
        }

        String[] id_anio = registros.getAnio_ocu_cargo().split(" ");
        String[] id_mes = registros.getMes_ocu_cargo().split(" ");

        String idd_anio = registros.getAnio_ocu_cargo().replaceAll("\\D", "");
        String idd_mes = registros.getMes_ocu_cargo().replaceAll("\\D", "");

        if (!idd_anio.isEmpty()) {
            cbx_tiempoCargoAnios.setSelection(Integer.parseInt(idd_anio));
        } else {
            cbx_tiempoCargoAnios.setSelection(0);
        }
        if (!idd_mes.isEmpty()) {
            cbx_tiempoCargoMeses.setSelection(Integer.parseInt(idd_mes));
        } else {
            cbx_tiempoCargoMeses.setSelection(0);
        }


        // molestias al oido
        if (registros.getMolestia_oido().equals("1")) {
            radioGroup_Oido.check(R.id.radio_OidoSi);
        } else {
            radioGroup_Oido.check(R.id.radio_OidoNo);
        }
        if (registros.getEnfermedad_oido().equals("1")) {
            radioGroup_Enferm.check(R.id.radio_emfermedadSi);
        } else {
            radioGroup_Enferm.check(R.id.radio_emfermedadNo);
        }
        if (registros.getEnfermedad_oido().equals("1")) {
            txt_detalleEnferm.setText(registros.getDetalle_enf_oido());
        }
        // Presenta alguna enfermedad al oido

        config.asignarAdaptadorYSeleccion(cbx_anio, "anios", "nom_anio", registros.getAnio_ultimo_examen(), getContext());
        config.asignarAdaptadorYSeleccion(cbx_meses, "meses", "nom_mes", registros.getMes_ultimo_examen(), getContext());


        if (registros.getCtrl_ingenieria().equals("1")) {
            radioGroup_Ingenieria.check(R.id.radioIngenieriaSi);
        } else {
            radioGroup_Ingenieria.check(R.id.radioUngenieriaNo);
        }
        if (registros.getCtrl_administrativo().equals("1")) {
            radioGroup_Adminis.check(R.id.radio_AdministrativoSi);
        } else {
            radioGroup_Adminis.check(R.id.radio_AdministrativoNo);
        }

        if (registros.getCtrl_ingenieria().equals("1")) {
            if (registros.getAislamiento().equals("1")) {
                radioGroup_Aislante.check(R.id.radioAislanteSi);
            } else {
                radioGroup_Aislante.check(R.id.radioAislanteNo);
            }
            if (registros.getOrientacion().equals("1")) {
                radioGroup_Fachada.check(R.id.radioFachadaSi);
            } else {
                radioGroup_Fachada.check(R.id.radioFachadaNo);
            }
            if (registros.getTechos().equals("1")) {
                radioGroup_Techo.check(R.id.radioTechosSi);
            } else {
                radioGroup_Techo.check(R.id.radioTechosNo);
            }
            if (registros.getCerramiento().equals("1")) {
                radioGroup_Cerramiento.check(R.id.radioCerramientoSi);
            } else {
                radioGroup_Cerramiento.check(R.id.radioCerramientoNo);
            }
            if (registros.getCabinas().equals("1")) {
                radioGroup_Cabinas.check(R.id.radioCabinaSi);
            } else {
                radioGroup_Cabinas.check(R.id.radioCabinaNo);
            }
            txt_OtroIngenieria.setText(registros.getOtro_ingenieria());
        }

        if (registros.getCtrl_administrativo().equals("1")) {
            if (registros.getCapacitacion().equals("1")) {
                radioGroup_Capac.check(R.id.radioRiesgosSi);
            } else {
                radioGroup_Capac.check(R.id.radioRiesgosNo);
            }
            if (registros.getSenializacion_precion().equals("1")) {
                radioGroup_SenalPresion.check(R.id.radioPresionSi);
            } else {
                radioGroup_SenalPresion.check(R.id.radioPresionNo);
            }
            if (registros.getSenializacion_epp().equals("1")) {
                radioGroup_SenalEpps.check(R.id.radioEppSi);
            } else {
                radioGroup_SenalEpps.check(R.id.radioEppNo);
            }
            if (registros.getAdm_tiempo_expo().equals("1")) {
                radioGroup_AdmTiempoExpo.check(R.id.radioAdminExpoSi);
            } else {
                radioGroup_AdmTiempoExpo.check(R.id.radioAdminExpoNo);
            }
            if (registros.getRotacion().equals("1")) {
                radioGroup_Rotacion.check(R.id.radioRotacionSi);
            } else {
                radioGroup_Rotacion.check(R.id.radioRotacionNo);
            }
            txt_otroAdministrativo.setText(registros.getOtro_administrativo());
        }

        if (registros.getTapones_au().equals("1")) {
            radioGroup_Tapones.check(R.id.radioTaponesSi);
        } else {
            radioGroup_Tapones.check(R.id.radioTaponesNo);
        }
        if (registros.getOrejereas().equals("1")) {
            radioGroup_Orejeras.check(R.id.radio_OrejerasSi);
        } else {
            radioGroup_Orejeras.check(R.id.radio_OrejerasNo);
        }
        if (registros.getTapones_au().equals("1")) {
            config.asignarAdaptadorYSeleccion(cbx_marcaTapon, "marca_formato_medicion", "nom_marca", registros.getAnio_ultimo_examen(), getContext());
            if (cbx_marcaTapon.getSelectedItem().equals("OTRO")) {
                txt_otroMarcaTapones.setText(registros.getMarca_tapones_audi());
            }
            config.asignarAdaptadorYSeleccion(cbx_modeloTapon, "modelo_Tapones", "nom_modelo", registros.getMes_ultimo_examen(), getContext());
            if (cbx_modeloTapon.getSelectedItem().equals("OTRO")) {
                txt_otroModeloTapones.setText(registros.getModelo_tapones_audi());

            }
        }
        tv_nrrTapones.setText(registros.getNrr_tapones_audi());
        if (registros.getOrejereas().equals("1")) {
            config.asignarAdaptadorYSeleccion(cbx_marcaOrej, "marca_formato_medicion", "nom_marca", registros.getAnio_ultimo_examen(), getContext());
            if (cbx_marcaOrej.getSelectedItem().equals("OTRO")) {
                txt_otroMarcaOrej.setText(registros.getMarca_orejeras());
            }
            config.asignarAdaptadorYSeleccion(cbx_modeloOrej, "modelo_Tapones", "nom_modelo", registros.getMes_ultimo_examen(), getContext());
            if (cbx_modeloOrej.getSelectedItem().equals("OTRO")) {
                txt_otroModeloOrej.setText(registros.getModelo_orejeras());
            }

        }
        tv_nrrOrej.setText(registros.getNrr_orejeras());


        txt_leq.setText(String.valueOf(registros.getLequi()));
        txt_lpico.setText(String.valueOf(registros.getLpico_dba()));
        txt_lmax.setText(String.valueOf(registros.getLmax()));
        txt_lmin.setText(String.valueOf(registros.getLmin()));
        txt_observaciones.setText(registros.getObservacion());

    }
}