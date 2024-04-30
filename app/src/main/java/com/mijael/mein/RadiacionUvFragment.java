package com.mijael.mein;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mijael.mein.DAO.DAO_Equipos;
import com.mijael.mein.DAO.DAO_FormatosTrabajo;
import com.mijael.mein.DAO.DAO_RegistroFormatos;
import com.mijael.mein.DAO.DAO_RegistroRadiacion;
import com.mijael.mein.DAO.DAO_RegistroRadiacionUV;
import com.mijael.mein.DAO.DAO_Usuario;
import com.mijael.mein.Entidades.Equipos;
import com.mijael.mein.Entidades.Formatos_Trabajo;
import com.mijael.mein.Entidades.RadiacionUv_Registro;
import com.mijael.mein.Entidades.RadiacionUv_RegistroDetalle;
import com.mijael.mein.Entidades.RegistroFormatos;
import com.mijael.mein.Entidades.RegistroFormatos_Detalle;
import com.mijael.mein.Entidades.Usuario;
import com.mijael.mein.Extras.FragmentoImagen;
import com.mijael.mein.Extras.InputDateConfiguration;
import com.mijael.mein.Extras.Validaciones;
import com.mijael.mein.SERVICIOS.DosimetriaService;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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


public class RadiacionUvFragment extends Fragment implements FragmentoImagen.ImagePickerListener {
    private boolean cargarImagen = false;
    View rootView;
    String id_plan_trabajo, id_pt_trabajo, id_formato, id_colaborador, nom_Empresa;
    String[] arrayYN, arrayPiel, arrayDesc;
    AutoCompleteTextView tv_equipoRadiacion;
    TextView tv_horaVerificacion, tv_fechaMonitoreo, tv_horaInicioMoni, tv_horaFinalMoni, tv_tipoPiel;
    AppCompatButton btn_subirFotoRadiacion, btn_BuscarDni;
    FloatingActionButton btn_guardar;
    ExtendedFloatingActionButton btnCancelar;
    Spinner spn_tipoDoc, spn_colorPiel, spn_timeCargoAnyo, spn_timeCargoMes, spn_horarioTrabajo, spn_regimen,
            spn_horarioRefrigerio, spn_descTrabajo;
    EditText txt_numDoc, txt_nomTrabajador, txt_edad, txt_areaTrabajo, txt_puestoTrabajo, txt_aRealizada, txt_timeExpoHora, txt_jornadaTrabajo,
            txt_mantenimientoFuente, txt_OtrosIngenieria, txt_OtrosAdministrativo, txt_otrosVestimenta, txt_otrosEpps, txt_fuenteGen, txt_tipoFuenteRadiacion,
            txt_otroHorario, txt_otroRegimen, txt_otroRefrigerio, txt_otraFrecuencia, txt_uW_cm2;
    CheckBox check_casco, check_lentesOscuros, check_cubreNuca, check_gorroSombrero;
    RadioGroup radioGroupVerificacion, radioGroupSombraDescanso, radioGroupMallasTramo, radioGroupProgramaTrab,
            radioGroupAireLibre,
            radioGroupProteccionBrillo, radioGroupProteccionLateral, radioGroupLentesOscuroo, radioGroupCertificacion, radioGroupColorOscuro, radioGroupMangaLarga,
            radioGroupTramaGruesa, radioGroupUtilGorro, radioGroupProtLegionario, radioGroupAlaAncha, radioGroupUtilCasco, radioGroupCubreNuca, radioGroupUtilFPS, radioGroupGuiaFPS,
            radioGroupFrecuenciaAplicacion;
    LinearLayout linearOtroHorario, linearOtroRegimen, linearOtroRefrigerio, linearBuscarDni;
    ImageView imgRadiacion;
    Uri uri;
    DAO_RegistroFormatos dao_registroFormatos;
    RegistroFormatos registros;
    RegistroFormatos_Detalle detalles;

    public RadiacionUvFragment() {
        // Required empty public constructor
        dao_registroFormatos = new DAO_RegistroFormatos(getContext());
    }

    Formatos_Trabajo for_RadiacionUv;
    Validaciones validar = new Validaciones();
    InputDateConfiguration config;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (getArguments() != null) {
            id_plan_trabajo = bundle.getString("id_plan_Trabajo");
            id_pt_trabajo = bundle.getString("id_pt_formato");
            id_formato = bundle.getString("id_formato");
            id_colaborador = bundle.getString("nomUsuario");
            nom_Empresa = bundle.getString("nomEmpresa");
            registros = bundle.getParcelable("registroForm");
            detalles = bundle.getParcelable("detalleForm");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_radiacion_uv, container, false);
        config = new InputDateConfiguration(getActivity(), id_colaborador, nom_Empresa, rootView);
        init(rootView);
        config.ConfigPantalla();
        DAO_Equipos equipos = new DAO_Equipos(getActivity());
        List<String> lista_CodEquipos = equipos.obtener_CodEquipos();
        DAO_Usuario usuario = new DAO_Usuario(getActivity());
        Usuario nuevo = usuario.BuscarUsuario(Integer.parseInt(id_colaborador));
        arrayYN = new String[]{"DNI", "CE"};
        arrayPiel = new String[]{"Muy clara", "clara", "Morena clara", "Morena", "Oscura", "Muy oscura"};
        arrayDesc = new String[]{"Trabajo al aire libre sin carga solar o bajo techo", "Trabajo al aire libre con carga solar"};


        config.configurarAutoCompleteTextView(tv_equipoRadiacion, lista_CodEquipos);
        tv_horaVerificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                config.showTimePickerDialog(rootView, tv_horaVerificacion);
            }
        });
        tv_fechaMonitoreo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                config.showDatePickerDialog(rootView, tv_fechaMonitoreo);
            }
        });
        tv_horaInicioMoni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                config.showTimePickerDialog(rootView, tv_horaInicioMoni);
            }
        });
        tv_horaFinalMoni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                config.showTimePickerDialog(rootView, tv_horaFinalMoni);
            }
        });

        btn_subirFotoRadiacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentoImagen fragmentoImagen = new FragmentoImagen(RadiacionUvFragment.this);
                fragmentoImagen.show(getActivity().getSupportFragmentManager(), "imagePicker");
                cargarImagen = true;
            }
        });

        spn_tipoDoc.setAdapter(config.LlenarSpinner(arrayYN));
        spn_colorPiel.setAdapter(config.LlenarSpinner(arrayPiel));
        spn_colorPiel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String seleccion = (String) parent.getItemAtPosition(position);
                if (seleccion.equals("Muy clara")) tv_tipoPiel.setText("Tipo I");
                else if (seleccion.equals("clara")) tv_tipoPiel.setText("Tipo II");
                else if (seleccion.equals("Morena clara")) tv_tipoPiel.setText("Tipo III");
                else if (seleccion.equals("Morena")) tv_tipoPiel.setText("Tipo IV");
                else if (seleccion.equals("Oscura")) tv_tipoPiel.setText("Tipo V");
                else if (seleccion.equals("Muy oscura")) tv_tipoPiel.setText("Tipo VI");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spn_tipoDoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemSelecionado = parent.getItemAtPosition(position).toString();
                if (itemSelecionado.equals("DNI")) {
                    if (config.isOnline()) {
                        linearBuscarDni.setVisibility(View.VISIBLE);
                    }
                } else {
                    linearBuscarDni.setVisibility(View.GONE);
                    txt_nomTrabajador.setText("");
                    txt_numDoc.setText("");
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
                    config.buscarTrabajador(dni, txt_nomTrabajador);
                }
            }
        });

        config.llenarSpinnerConNumeros(spn_timeCargoAnyo, 10, getActivity());
        config.llenarSpinnerConNumeros(spn_timeCargoMes, 11, getActivity());

        spn_horarioTrabajo.setAdapter(config.LlenarSpinner("horario_trab_fromato_medicion", "desc_horario", getActivity()));
        spn_regimen.setAdapter(config.LlenarSpinner("regimen_formato_medicion", "nom_regimen", getActivity()));
        spn_horarioRefrigerio.setAdapter(config.LlenarSpinner("horario_refrig_formato_medicion", "nom_horario", getActivity()));

        config.MostrarCampos(linearOtroHorario, spn_horarioTrabajo);
        config.MostrarCampos(linearOtroRegimen, spn_regimen);
        config.MostrarCampos(linearOtroRefrigerio, spn_horarioRefrigerio);

        spn_descTrabajo.setAdapter(config.LlenarSpinner(arrayDesc));
        /*spn_proteccionBrillo.setAdapter(config.LlenarSpinner(new String[]{"SI", "NO"}));
        spn_proteccionLateral.setAdapter(config.LlenarSpinner(new String[]{"SI", "NO"}));
        spn_gorro.setAdapter(config.LlenarSpinner(new String[]{"SI", "NO"}));
        spn_casco.setAdapter(config.LlenarSpinner(new String[]{"SI", "NO"}));
        spn_ninguno.setAdapter(config.LlenarSpinner(new String[]{"SI", "NO"}));
        spn_proteccionLegionario.setAdapter(config.LlenarSpinner(new String[]{"SI", "NO"}));
        spn_proteccionAlaAncha.setAdapter(config.LlenarSpinner(new String[]{"SI", "NO"}));
        spn_ropa.setAdapter(config.LlenarSpinner(new String[]{"SI", "NO"}));
        spn_colorOdcuro.setAdapter(config.LlenarSpinner(new String[]{"SI", "NO"}));
        spn_mangaLarga.setAdapter(config.LlenarSpinner(new String[]{"SI", "NO"}));
        spn_tramaGruesa.setAdapter(config.LlenarSpinner(new String[]{"SI", "NO"}));
        spn_utilizaEpps.setAdapter(config.LlenarSpinner(new String[]{"SI", "NO"}));
        spn_guiaUsoEpp.setAdapter(config.LlenarSpinner(new String[]{"SI", "NO"}));
        spn_frecuencia2horas.setAdapter(config.LlenarSpinner(new String[]{"SI", "NO"}));
        spn_frecuencia30min.setAdapter(config.LlenarSpinner(new String[]{"SI", "NO"}));*/
        radioGroupFrecuenciaAplicacion.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_otraFrecuencia) {
                    txt_otraFrecuencia.setEnabled(true);
                } else {
                    txt_otraFrecuencia.setEnabled(false);
                    txt_otraFrecuencia.setText("");
                }
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                if (fragmentManager.getBackStackEntryCount() > 0) {
                    // Retroceder a la pantalla anterior
                    fragmentManager.popBackStack();
                } else {
                    // Si no hay fragmentos en la pila, cerrar la actividad actual o realizar alguna otra acción
                }
            }
        });

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (
                        validar.validarCampoObligatorio(tv_equipoRadiacion) &&
                                validar.validarCampoObligatorio(tv_horaVerificacion) &&
                                validar.validarImagen(cargarImagen, getActivity()) &&
                                validar.validarCampoObligatorio(tv_fechaMonitoreo) &&
                                validar.validarCampoObligatorio(tv_horaInicioMoni) &&
                                validar.validarCampoObligatorio(tv_horaFinalMoni) &&
                                validar.validarCampoObligatorio(txt_timeExpoHora) &&
                                validar.validarCampoObligatorio(txt_jornadaTrabajo) &&
                                validar.validarCampoObligatorio(spn_tipoDoc) &&
                                validar.validarCampoObligatorio(txt_numDoc) &&
                                validar.validarCampoObligatorio(txt_nomTrabajador) &&
                                validar.validarCampoObligatorio(txt_edad) &&
                                validar.validarCampoObligatorio(txt_areaTrabajo) &&
                                validar.validarCampoObligatorio(txt_puestoTrabajo) &&
                                validar.validarCampoObligatorio(txt_aRealizada) &&
                                validar.validarCampoObligatorio(spn_colorPiel) &&
                                validar.validarCampoObligatorio(spn_timeCargoAnyo) &&
                                validar.validarCampoObligatorio(spn_timeCargoMes) &&
                                validar.validarCampoObligatorio(spn_horarioTrabajo) &&
                                validar.validarCampoObligatorio(spn_regimen) &&
                                validar.validarCampoObligatorio(spn_horarioRefrigerio) &&
                                validar.validarCampoObligatorio(txt_fuenteGen) &&
                                validar.validarCampoObligatorio(txt_tipoFuenteRadiacion) &&
                                validar.validarCampoObligatorio(spn_descTrabajo) &&
                                validar.validarCampoObligatorio(txt_mantenimientoFuente)

                ) {*/
                    String valorEquipoRadUv = tv_equipoRadiacion.getText().toString();
                    String valorHoraVerificacion = tv_horaVerificacion.getText().toString();
                    int valorGroupVerificacion = validar.getValor2(radioGroupVerificacion, rootView);

                    String f = tv_fechaMonitoreo.getText().toString();
                    String valorFechaMonitoreo = config.convertirFecha(f);
                    //String valorFechaMonitoreo = tv_fechaMonitoreo.getText().toString();

                    String valorHoraInicioMoni = tv_horaInicioMoni.getText().toString();
                    String valorHoraFinalMoni = tv_horaFinalMoni.getText().toString();
                    String valorTimeExpo = txt_timeExpoHora.getText().toString();
                    String valorJornada = txt_jornadaTrabajo.getText().toString();
                    String valorTipoDoc = spn_tipoDoc.getSelectedItem().toString();
                    String valorNumDoc = txt_numDoc.getText().toString();
                    String valorNombreTra = txt_nomTrabajador.getText().toString();
                    String valorEdad = txt_edad.getText().toString();
                    String valorAreaTra = txt_areaTrabajo.getText().toString();
                    String valorPuestoTra = txt_puestoTrabajo.getText().toString();
                    String valorActividades = txt_aRealizada.getText().toString();
                    String valorColorPiel = spn_colorPiel.getSelectedItem().toString();
                    String valorTipoPiel = tv_tipoPiel.getText().toString();
                    String valorTimeCargoAnyo = spn_timeCargoAnyo.getSelectedItem().toString() + "año(s)";
                    String valorTimeCargoMeses = spn_timeCargoMes.getSelectedItem().toString() + "mes(es)";

                    String valorHorarioTrabajo = spn_horarioTrabajo.getSelectedItem().toString();
                    String valorRegimen = spn_regimen.getSelectedItem().toString();
                    String valorRefrigerio = spn_horarioRefrigerio.getSelectedItem().toString();

                    if (valorHorarioTrabajo.equals("OTRO"))
                        valorHorarioTrabajo = txt_otroHorario.getText().toString();
                    if (valorRegimen.equals("OTRO"))
                        valorRegimen = txt_otroRegimen.getText().toString();
                    if (valorRefrigerio.equals("OTRO"))
                        valorRefrigerio = txt_otroRefrigerio.getText().toString();

                    String valorFuenteGen = txt_fuenteGen.getText().toString();
                    //String valorTipoFuenteRadiac = txt_tipoFuenteRadiacion.getText().toString();
                    String valorDesTrabajo = spn_descTrabajo.getSelectedItem().toString();
                    //String valorMantenFuente = txt_mantenimientoFuente.getText().toString();

                    int valorGroupSombraDescanso = validar.getValor2(radioGroupSombraDescanso, rootView);
                    int valorGroupMallasTramo = validar.getValor2(radioGroupMallasTramo, rootView);
                    String valorOtrosIng = txt_OtrosIngenieria.getText().toString();

                    int valorGroupProgramaTrab = validar.getValor2(radioGroupProgramaTrab, rootView);
                    int valorGroupAireLibre = validar.getValor2(radioGroupAireLibre, rootView);
                    String valorOtrosAdmin = txt_OtrosAdministrativo.getText().toString();


                    int valorProteccionBrillo = validar.getValor2(radioGroupProteccionBrillo, rootView);
                    int valorProteccionLateral = validar.getValor2(radioGroupProteccionLateral, rootView);
                    int valorLentesOscuros = validar.getValor2(radioGroupLentesOscuroo, rootView);
                    int valorCertificacion = validar.getValor2(radioGroupCertificacion, rootView);
                    int valorColorOscuro = validar.getValor2(radioGroupColorOscuro, rootView);
                    int valorMangaLarga = validar.getValor2(radioGroupMangaLarga, rootView);
                    int valorTramaGruesa = validar.getValor2(radioGroupTramaGruesa, rootView);
                    int valorUtilGorro = validar.getValor2(radioGroupUtilGorro, rootView);
                    int valorProtLegionario = validar.getValor2(radioGroupProtLegionario, rootView);
                    int valorAlaAncha = validar.getValor2(radioGroupAlaAncha, rootView);
                    int valorUtilCasco = validar.getValor2(radioGroupUtilCasco, rootView);
                    int valorCubreNuca = validar.getValor2(radioGroupCubreNuca, rootView);
                    int valorUtilFPS = validar.getValor2(radioGroupUtilFPS, rootView);
                    int valorGuiaFPS = validar.getValor2(radioGroupGuiaFPS, rootView);
                    int valorFrecuenciaApli = validar.getValorTag(radioGroupFrecuenciaAplicacion);
                    String valorOtraFrecuencia = txt_otraFrecuencia.getText().toString();
                    String valorOtrosEpps = txt_otrosEpps.getText().toString();
                    String valorUw_cm2 = txt_uW_cm2.getText().toString();

                    Equipos equipos1 = equipos.Buscar(valorEquipoRadUv);

                    String fecha_registro = "";
                    String cod_formato;
                    String cod_registro;
                    String valorRutaFoto;
                    int id_plan_formato_reg;

                    if(registros==null){
                        id_plan_formato_reg = dao_registroFormatos.getRecordIdByPosition() +1;
                        fecha_registro = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                        ArrayList<HashMap<String, String>> resultList = dao_registroFormatos.getListCantidadFormatoId(Integer.parseInt(id_pt_trabajo));
                        int total_registros = dao_registroFormatos.get_cant_formato_medicion();
                        cod_formato = config.GenerarCodigoFormato(Integer.parseInt(id_formato),resultList.size());
                        cod_registro = config.generarCodigoRegistro(total_registros);
                        valorRutaFoto = uri.getEncodedPath();
                        if(uri!=null){valorRutaFoto = uri.getEncodedPath();}
                    }else {
                        id_plan_formato_reg = registros.getId_plan_trabajo_formato_reg();
                        fecha_registro = registros.getFec_reg();
                        cod_registro = registros.getCod_registro();
                        cod_formato = registros.getCod_formato();
                        valorRutaFoto = registros.getRuta_foto();
                        id_formato = String.valueOf(registros.getId_formato());
                        id_plan_trabajo = String.valueOf(registros.getId_plan_trabajo());
                        id_pt_trabajo = String.valueOf(registros.getId_pt_formato());
                    }
                    RadiacionUv_Registro cabecera = new RadiacionUv_Registro(
                            -1,
                            cod_formato,
                            cod_registro,
                            id_formato,
                            id_plan_trabajo,
                            id_pt_trabajo,
                            equipos1.getCodigo(),
                            equipos1.getNombre(),
                            equipos1.getSerie(),
                            String.valueOf(equipos1.getId_equipo_registro()),
                            id_colaborador,
                            nuevo.getUsuario_nombres() + " " + nuevo.getUsuario_apater() + " " + nuevo.getUsuario_amater(),
                            "" + valorGroupVerificacion,
                            valorHoraVerificacion,
                            valorFechaMonitoreo,
                            valorHoraInicioMoni,
                            valorHoraFinalMoni,
                            valorTimeExpo,
                            valorJornada,
                            valorTipoDoc,
                            valorNumDoc,
                            valorNombreTra,
                            valorPuestoTra,
                            valorAreaTra,
                            valorActividades,
                            valorEdad,
                            valorTimeCargoMeses,
                            valorTimeCargoAnyo,
                            valorHorarioTrabajo,
                            valorRefrigerio,
                            valorRegimen,
                            valorDesTrabajo,
                            valorOtrosAdmin,
                            valorUw_cm2,
                            valorOtrosIng,
                            fecha_registro,
                            id_colaborador,
                            valorRutaFoto
                    );

                    RadiacionUv_RegistroDetalle detalle = new RadiacionUv_RegistroDetalle(
                            id_plan_formato_reg,
                            valorTipoPiel,
                            valorColorPiel,
                            valorFuenteGen,
                            "No requiere",
                            "" + valorGroupSombraDescanso,
                            "" + valorGroupMallasTramo,
                            "" + valorGroupProgramaTrab,
                            "" + valorGroupAireLibre,
                            "No requiere",
                            valorProteccionBrillo,
                            valorProteccionLateral,
                            valorUtilGorro,
                            valorUtilCasco,
                            "2",
                            valorProtLegionario,
                            valorAlaAncha,
                            valorCertificacion,
                            valorColorOscuro,
                            valorMangaLarga,
                            valorTramaGruesa,
                            valorUtilFPS,
                            valorGuiaFPS,
                            valorFrecuenciaApli,
                            valorOtraFrecuencia,
                            valorCubreNuca,
                            valorLentesOscuros,
                            valorOtrosEpps,
                            fecha_registro,
                            id_colaborador
                    );

                    /*Object detalle = new Object();//OPCIONAL INICIO
                    HashMap<String,Object> elemntos = new HashMap<>();
                    elemntos.put("nombre", "MEIN");//OPCIONAL FINAL*/

                    if (config.isOnline()) {
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("https://test.meiningenieros.pe/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        DosimetriaService service1 = retrofit.create(DosimetriaService.class);// DEBERIA CAMBIARSE EL SERVICIO DE MANERA GENERAL

                        Gson gson = new Gson();

                        // Crear un objeto JSON principal
                        JsonObject jsonObject = new JsonObject();

                        JsonObject registroJson = gson.toJsonTree(cabecera).getAsJsonObject();
                        jsonObject.add("cabecera", registroJson);

                        JsonObject detalleJson = gson.toJsonTree(detalle).getAsJsonObject();
                        jsonObject.add("detalle", detalleJson);


                        String cadenaJson = gson.toJson(jsonObject);
                        RequestBody json = RequestBody.create(MediaType.parse("application/json"), cadenaJson);

                        Call<ResponseBody> call1 = service1.insertRadiacionUV(json);//INSERT A RADIACION UV
                        call1.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                Log.e("exitoso", "se inserto el registro");
                                File imageFile = new File(uri.getEncodedPath());
                                config.uploadImage(imageFile, cod_formato, id_pt_trabajo, cod_registro);
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
                        // SECCION DE REGISTRO DE MANERA LOCAL
                        DAO_RegistroRadiacionUV nuevoRegistro = new DAO_RegistroRadiacionUV(getActivity());
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Guardar formulario");
                        builder.setMessage("¿Deseas seguir llenando el formulario o terminar?");

                        builder.setPositiveButton("Seguir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(registros==null){
                                    nuevoRegistro.RegistroRadiacionUV(cabecera);
                                    nuevoRegistro.RegistrarRadiacionUv_Detalle(detalle);
                                    DAO_FormatosTrabajo dao_fromatosTrabajo = new DAO_FormatosTrabajo(getActivity());
                                    for_RadiacionUv = dao_fromatosTrabajo.Buscar(id_plan_trabajo, id_formato);
                                    for_RadiacionUv.setRealizado(for_RadiacionUv.getRealizado() + 1);
                                    for_RadiacionUv.setPor_realizar(for_RadiacionUv.getPor_realizar() - 1);
                                    dao_fromatosTrabajo.actualizarFormatoTrabajo(for_RadiacionUv);

                                }else{
                                    nuevoRegistro.ActualizarRadiacionUV(cabecera,registros.getId_plan_trabajo_formato_reg());
                                    nuevoRegistro.ActualizarRadiacionUv_Detalle(detalle,detalles.getId_formato_reg_detalle());
                                }
                            }
                        });
                        builder.setNegativeButton("Terminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(registros==null){
                                    nuevoRegistro.RegistroRadiacionUV(cabecera);
                                    nuevoRegistro.RegistrarRadiacionUv_Detalle(detalle);
                                    DAO_FormatosTrabajo dao_fromatosTrabajo = new DAO_FormatosTrabajo(getActivity());
                                    for_RadiacionUv = dao_fromatosTrabajo.Buscar(id_plan_trabajo, id_formato);
                                    for_RadiacionUv.setRealizado(for_RadiacionUv.getRealizado() + 1);
                                    for_RadiacionUv.setPor_realizar(for_RadiacionUv.getPor_realizar() - 1);
                                    dao_fromatosTrabajo.actualizarFormatoTrabajo(for_RadiacionUv);

                                }else{
                                    nuevoRegistro.ActualizarRadiacionUV(cabecera,registros.getId_plan_trabajo_formato_reg());
                                    nuevoRegistro.ActualizarRadiacionUv_Detalle(detalle,detalles.getId_formato_reg_detalle());
                                }
                                new AlertDialog.Builder(getActivity())
                                        .setTitle("Registro guardado Localmente")
                                        .setMessage("El registro ha sido guardado exitosamente.")
                                        .setPositiveButton(android.R.string.ok, null)
                                        .show();
                                Volver();
                            }
                        });
                        builder.show();

                    }
                //}
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (registros != null) {
            if (detalles != null) {
                EditarCampos();
            } else {
                builder.setTitle("Aviso")
                        .setMessage("Registro sin Detalle.")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                Volver();
            }

        } else {
            builder.setTitle("Aviso")
                    .setMessage("Realizara un nuevo registro.")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        return rootView;
    }

    public void init(View view) {

        tv_equipoRadiacion = view.findViewById(R.id.tv_equipoRadiacion);
        tv_horaVerificacion = view.findViewById(R.id.tv_horaVerificacion);
        radioGroupVerificacion = view.findViewById(R.id.radioGroupVerificacion);
        btn_subirFotoRadiacion = view.findViewById(R.id.btn_subirFotoRadiacion);
        imgRadiacion = view.findViewById(R.id.img_radiacionUV);
        tv_fechaMonitoreo = view.findViewById(R.id.tv_fechaMonitoreo);
        tv_horaInicioMoni = view.findViewById(R.id.tv_horaInicioMoni);
        tv_horaFinalMoni = view.findViewById(R.id.tv_horaFinalMoni);
        txt_timeExpoHora = view.findViewById(R.id.txt_timeExpoHora);
        txt_jornadaTrabajo = view.findViewById(R.id.txt_jornadaTrabajo);
        spn_tipoDoc = view.findViewById(R.id.spn_tipoDoc);
        txt_numDoc = view.findViewById(R.id.txt_numDoc);
        txt_nomTrabajador = view.findViewById(R.id.txt_nomTrabajador);
        txt_edad = view.findViewById(R.id.txt_edad);
        txt_areaTrabajo = view.findViewById(R.id.txt_areaTrabajo);
        txt_puestoTrabajo = view.findViewById(R.id.txt_puestoTrabajo);
        txt_aRealizada = view.findViewById(R.id.txt_aRealizada);
        spn_colorPiel = view.findViewById(R.id.cbx_colorPiel);
        tv_tipoPiel = view.findViewById(R.id.tv_tipoPiel);
        spn_timeCargoAnyo = view.findViewById(R.id.cbx_tiempoCargoAnios);
        spn_timeCargoMes = view.findViewById(R.id.cbx_tiempoCargoMeses);
        spn_horarioTrabajo = view.findViewById(R.id.cbx_horarioTrabajo);
        spn_regimen = view.findViewById(R.id.cbx_regimen);
        spn_horarioRefrigerio = view.findViewById(R.id.cbx_refrigerio);
        txt_fuenteGen = view.findViewById(R.id.txt_fuenteGen);
        txt_tipoFuenteRadiacion = view.findViewById(R.id.txt_tipoFuenteRadiacion);
        spn_descTrabajo = view.findViewById(R.id.spn_descTrabajo);
        txt_mantenimientoFuente = view.findViewById(R.id.txt_mantenimientoFuente);

        radioGroupSombraDescanso = view.findViewById(R.id.radioGroupSombraDescanso);
        radioGroupMallasTramo = view.findViewById(R.id.radioGroupMallasTramo);
        txt_OtrosIngenieria = view.findViewById(R.id.txt_OtrosIngenieria);
        radioGroupProgramaTrab = view.findViewById(R.id.radioGroupProgramaTrab);
        radioGroupAireLibre = view.findViewById(R.id.radioGroupAireLibre);
        txt_OtrosAdministrativo = view.findViewById(R.id.txt_OtrosAdministrativo);


        radioGroupProteccionBrillo = view.findViewById(R.id.radioGroupProteccionBrillo);// desde aqui se va a editar
        radioGroupProteccionLateral = view.findViewById(R.id.radioGroupProteccionLateral);
        radioGroupLentesOscuroo = view.findViewById(R.id.radioGroupLentesOscuroo);
        radioGroupCertificacion = view.findViewById(R.id.radioGroupCertificacion);
        radioGroupColorOscuro = view.findViewById(R.id.radioGroupColorOscuro);
        radioGroupMangaLarga = view.findViewById(R.id.radioGroupMangaLarga);
        radioGroupTramaGruesa = view.findViewById(R.id.radioGroupTramaGruesa);
        radioGroupUtilGorro = view.findViewById(R.id.radioGroupUtilGorro);
        radioGroupProtLegionario = view.findViewById(R.id.radioGroupProtLegionario);
        radioGroupAlaAncha = view.findViewById(R.id.radioGroupAlaAncha);
        radioGroupUtilCasco = view.findViewById(R.id.radioGroupUtilCasco);
        radioGroupCubreNuca = view.findViewById(R.id.radioGroupCubreNuca);
        radioGroupUtilFPS = view.findViewById(R.id.radioGroupUtilFPS);// hasta aqui se va a editar
        radioGroupGuiaFPS = view.findViewById(R.id.radioGroupGuiaFPS);
        radioGroupFrecuenciaAplicacion = view.findViewById(R.id.radioGroupFrecuenciaAplicacion);
        txt_otraFrecuencia = view.findViewById(R.id.txt_otraFrecuencia);

        txt_otrosEpps = view.findViewById(R.id.txt_otrosEpps);
        txt_uW_cm2 = view.findViewById(R.id.txt_uW_cm2);

        btn_guardar = view.findViewById(R.id.fabGuardar);
        btnCancelar = view.findViewById(R.id.fabCancelar);

        linearOtroHorario = view.findViewById(R.id.linearOtroHorario);
        txt_otroHorario = view.findViewById(R.id.txt_otroHorario);
        linearOtroRegimen = view.findViewById(R.id.linearOtroRegimen);
        txt_otroRegimen = view.findViewById(R.id.txt_otroRegimen);
        linearOtroRefrigerio = view.findViewById(R.id.linearOtroRefrigerio);
        txt_otroRefrigerio = view.findViewById(R.id.txt_otroRefrigerio);
        linearBuscarDni = view.findViewById(R.id.linearBuscarDni);
        btn_BuscarDni = view.findViewById(R.id.btn_BuscarDni);


    }

    @Override
    public void onImagePicked(Uri imageUri) {
        this.uri = imageUri;
        if (imgRadiacion != null && imageUri != null) {
            imgRadiacion.setImageURI(imageUri);
            /*File imageFile = new File(imageUri.getEncodedPath());
            config.uploadImage(imageFile);*/
        }
    }

    private void EditarCampos() {
        tv_equipoRadiacion.setText(registros.getCod_equipo1());
        tv_horaVerificacion.setText(registros.getHora_situ());
        if (registros.getVerf_insitu().equals("1")) {
            radioGroupVerificacion.check(R.id.verf_insitusi);
        } else {
            radioGroupVerificacion.check(R.id.verf_insituno);
        }
        if(registros.getRuta_foto()!=null) {
            imgRadiacion.setImageURI(Uri.parse(registros.getRuta_foto()));
        }
        String fecha = "";
        if (!registros.getFec_monitoreo().isEmpty()) {
            String[] fec = registros.getFec_monitoreo().split(" ");
            String[] nueva_fec = fec[0].split("-");
            fecha = nueva_fec[0] + "/" + nueva_fec[1] + "/" + nueva_fec[2];
        }
        tv_fechaMonitoreo.setText(fecha);
        tv_horaInicioMoni.setText(registros.getHora_inicial());
        tv_horaFinalMoni.setText(registros.getHora_final());
        txt_timeExpoHora.setText(registros.getTiempo_exposicion());
        txt_jornadaTrabajo.setText(registros.getJornada());
        int indice1 = Arrays.asList(arrayYN).indexOf(registros.getTipo_doc_trabajador());
        spn_tipoDoc.setSelection(indice1 + 1);
        txt_numDoc.setText(registros.getNum_doc_trabajador());
        txt_nomTrabajador.setText(registros.getNom_trabajador());
        txt_edad.setText(String.valueOf(registros.getEdad_trabajador()));
        txt_areaTrabajo.setText(registros.getArea_trabajo());
        txt_puestoTrabajo.setText(registros.getPuesto_trabajador());
        txt_aRealizada.setText(registros.getActividades_realizadas());
        int indice2 = Arrays.asList(arrayPiel).indexOf(detalles.getColor_piel());
        spn_colorPiel.setSelection(indice2 + 1);
        tv_tipoPiel.setText(detalles.getTipo_piel());
        String idd_anio = registros.getAnio_ocu_cargo().replaceAll("\\D", "");
        String idd_mes = registros.getMes_ocu_cargo().replaceAll("\\D", "");

        if (!idd_anio.isEmpty()) {
            spn_timeCargoAnyo.setSelection(Integer.parseInt(idd_anio));
        } else {
            spn_timeCargoAnyo.setSelection(0);
        }
        if (!idd_mes.isEmpty()) {
            spn_timeCargoMes.setSelection(Integer.parseInt(idd_mes));
        } else {
            spn_timeCargoMes.setSelection(0);
        }
        config.asignarAdaptadorYSeleccion(spn_horarioTrabajo, "horario_trab_fromato_medicion", "desc_horario", registros.getHora_trabajo(), getContext());
        if (spn_horarioTrabajo.getSelectedItem().equals("OTRO")) {
            txt_otroHorario.setText(registros.getHora_trabajo());
        }
        config.asignarAdaptadorYSeleccion(spn_regimen, "regimen_formato_medicion", "nom_regimen", registros.getRegimen_laboral(), getContext());
        if (spn_regimen.getSelectedItem().equals("OTRO")) {
            txt_otroRegimen.setText(registros.getRegimen_laboral());
        }
        config.asignarAdaptadorYSeleccion(spn_horarioRefrigerio, "horario_refrig_formato_medicion", "nom_horario", registros.getHorario_refrigerio(), getContext());
        if (spn_horarioRefrigerio.getSelectedItem().equals("OTRO")) {
            txt_otroRefrigerio.setText(registros.getHora_trabajo());
        }
        txt_fuenteGen.setText(detalles.getFuente_generadora());
        //txt_tipoFuenteRadiacion.setText(detalles.getTipo_fuente()); // posiblemente sea frio
        String valorDesc = registros.getDesc_area_trabajo();
        int indice3 = Arrays.asList(arrayDesc).indexOf(valorDesc);
        spn_descTrabajo.setSelection(indice3 + 1);
        //txt_mantenimientoFuente.setText(detalles.getMant_fuente());

        // CONTROL INGENIERIA
        if (detalles.getSombra_descanso().equals("1")) {
            radioGroupSombraDescanso.check(R.id.radioSombraSi);
        } else {
            radioGroupSombraDescanso.check(R.id.radioSombraNo);
        }
        if (detalles.getMalla_oscura().equals("1")) {
            radioGroupMallasTramo.check(R.id.radioMallasSi);
        } else {
            radioGroupMallasTramo.check(R.id.radioMallasNo);
        }
        txt_OtrosIngenieria.setText(registros.getOtro_ingenieria());

        // CONTROL ADMINISTRATIVO
        if (detalles.getProg_expo_radiacion().equals("1")) {
            radioGroupProgramaTrab.check(R.id.radioProgramaSi);
        } else {
            radioGroupProgramaTrab.check(R.id.radioProgramaNo);
        }
        if (detalles.getTrab_aire_libre().equals("1")) {
            radioGroupAireLibre.check(R.id.radioAireLibreSi);
        } else {
            radioGroupAireLibre.check(R.id.radioAireLibreNo);
        }
        txt_OtrosAdministrativo.setText(registros.getOtro_administrativo());

        if (detalles.getEpp_lentes_brillo().equals("1")) {
            radioGroupProteccionBrillo.check(R.id.radioProtBrilloSi);
        } else {
            radioGroupProteccionBrillo.check(R.id.radioProtBrilloNo);
        }
        if (detalles.getProt_lat().equals("1")) {
            radioGroupProteccionLateral.check(R.id.radioProtLateralSi);
        } else {
            radioGroupProteccionLateral.check(R.id.radioProtLateralNo);
        }
        if (detalles.getLent_osc().equals("1")) {
            radioGroupLentesOscuroo.check(R.id.radioLentesOscSi);
        } else {
            radioGroupLentesOscuroo.check(R.id.radioLentesOscNo);
        }

        if (detalles.getRop_ccerti().equals("1")) {
            radioGroupCertificacion.check(R.id.radioCertificacionSi);
        } else {
            radioGroupCertificacion.check(R.id.radioCeritificacacionNo);
        }
        if (detalles.getRop_coscuro().equals("1")) {
            radioGroupColorOscuro.check(R.id.radioColorOscuroSi);
        } else {
            radioGroupColorOscuro.check(R.id.radioColorOscuroNo);
        }
        if (detalles.getRop_mlarga().equals("1")) {
            radioGroupMangaLarga.check(R.id.radioMangaLargaSi);
        } else {
            radioGroupMangaLarga.check(R.id.radioMangaLargaSi);
        }
        if (detalles.getTgruesa().equals("1")) {
            radioGroupTramaGruesa.check(R.id.radioTramaGruesaSi);
        } else {
            radioGroupTramaGruesa.check(R.id.radiTramaGruesaNo);
        }

        if (detalles.getEpp_gorro_2().equals("1")) {
            radioGroupUtilGorro.check(R.id.radioUtilGorroSi);
        } else {
            radioGroupUtilGorro.check(R.id.radioUtilGorroNo);
        }
        if (detalles.getProt_legion().equals("1")) {
            radioGroupProtLegionario.check(R.id.radioProtLegionarioSi);
        } else {
            radioGroupProtLegionario.check(R.id.radioProtLegionarioNo);
        }
        if (detalles.getProt_aancha().equals("1")) {
            radioGroupAlaAncha.check(R.id.radioAlaAnchaSi);
        } else {
            radioGroupAlaAncha.check(R.id.radioAlaAnchaSi);
        }

        if (detalles.getEpp_casco_2().equals("1")) {
            radioGroupUtilCasco.check(R.id.radioUtilCascoSi);
        } else {
            radioGroupUtilCasco.check(R.id.radioUtilCascoNo);
        }
        if (detalles.getCubre_nuca().equals("1")) {
            radioGroupCubreNuca.check(R.id.radioCubreNucaSi);
        } else {
            radioGroupCubreNuca.check(R.id.radioCubreNucaNo);
        }

        if (detalles.getUtil_fps().equals("1")) {
            radioGroupUtilFPS.check(R.id.radioUtilFPSSi);
        } else {
            radioGroupUtilFPS.check(R.id.radioUtilFPSNo);
        }
        if (detalles.getGuia_fps().equals("1")) {
            radioGroupGuiaFPS.check(R.id.radiGuiaFPSSi);
        } else {
            radioGroupGuiaFPS.check(R.id.radioGuiaFPSNo);
        }

        if (detalles.getFrec_aplic().equals("1")) {
            radioGroupFrecuenciaAplicacion.check(R.id.radioFrecuenciaAplicacionSi);
        } else if (detalles.getFrec_aplic().equals("2")) {
            radioGroupFrecuenciaAplicacion.check(R.id.radioFrecuenciaAplicacionNo);
        } else if (detalles.getFrec_aplic().equals("3")) {
            radioGroupFrecuenciaAplicacion.check(R.id.radio_otraFrecuencia);
            txt_otraFrecuencia.setText(detalles.getOtra_frecuencia());
        }

        txt_otrosEpps.setText(detalles.getOtro_epp());
        txt_uW_cm2.setText(registros.getResultado());
    }

    private void Volver() {
        getFragmentManager().popBackStack();// Regresa al Fragment anterior
    }
}