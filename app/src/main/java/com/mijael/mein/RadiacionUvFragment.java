package com.mijael.mein;

import android.app.AlertDialog;
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
import com.mijael.mein.Entidades.Usuario;
import com.mijael.mein.Extras.FragmentoImagen;
import com.mijael.mein.Extras.InputDateConfiguration;
import com.mijael.mein.Extras.Validaciones;
import com.mijael.mein.SERVICIOS.DosimetriaService;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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


public class RadiacionUvFragment extends Fragment implements FragmentoImagen.ImagePickerListener{
    private boolean cargarImagen = false;
    View rootView;
    String id_plan_trabajo, id_pt_trabajo, id_formato,id_colaborador, nom_Empresa;
    AutoCompleteTextView tv_equipoRadiacion;
    TextView  tv_horaVerificacion, tv_fechaMonitoreo, tv_horaInicioMoni, tv_horaFinalMoni, tv_tipoPiel;
    AppCompatButton btn_subirFotoRadiacion,btn_BuscarDni;
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
            radioGroupProteccionBrillo, radioGroupProteccionLateral,radioGroupLentesOscuroo,radioGroupCertificacion,radioGroupColorOscuro, radioGroupMangaLarga,
            radioGroupTramaGruesa, radioGroupUtilGorro, radioGroupProtLegionario, radioGroupAlaAncha,radioGroupUtilCasco, radioGroupCubreNuca, radioGroupUtilFPS, radioGroupGuiaFPS,
            radioGroupFrecuenciaAplicacion;
    LinearLayout linearOtroHorario, linearOtroRegimen, linearOtroRefrigerio,linearBuscarDni;
    ImageView imgRadiacion;
    Uri uri;
    DAO_RegistroFormatos dao_registroFormatos;

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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_radiacion_uv, container, false);
        config = new InputDateConfiguration(getActivity(),id_colaborador,nom_Empresa,rootView);
        init(rootView);
        config.ConfigPantalla();
        DAO_Equipos equipos = new DAO_Equipos(getActivity());
        List<String> lista_CodEquipos = equipos.obtener_CodEquipos();
        DAO_Usuario usuario = new DAO_Usuario(getActivity());
        Usuario nuevo = usuario.BuscarUsuario(Integer.parseInt(id_colaborador));

        config.configurarAutoCompleteTextView(tv_equipoRadiacion,lista_CodEquipos);
        tv_horaVerificacion.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {config.showTimePickerDialog(rootView,tv_horaVerificacion);}});
        tv_fechaMonitoreo.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {config.showDatePickerDialog(rootView,tv_fechaMonitoreo);}});
        tv_horaInicioMoni.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {config.showTimePickerDialog(rootView,tv_horaInicioMoni);}});
        tv_horaFinalMoni.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {config.showTimePickerDialog(rootView,tv_horaFinalMoni);}});

        btn_subirFotoRadiacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentoImagen fragmentoImagen = new FragmentoImagen(RadiacionUvFragment.this);
                fragmentoImagen.show(getActivity().getSupportFragmentManager(), "imagePicker");
                cargarImagen = true;
            }
        });

        spn_tipoDoc.setAdapter(config.LlenarSpinner(new String[]{"DNI", "CE"}));
        spn_colorPiel    .setAdapter(config.LlenarSpinner(new String[]{"Muy clara", "clara","Morena clara","Morena","Oscura","Muy oscura"}));
        spn_colorPiel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String seleccion = (String) parent.getItemAtPosition(position);
                if(seleccion.equals("Muy clara")) tv_tipoPiel.setText("Tipo I");
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
                if(itemSelecionado.equals("DNI")){
                    if(config.isOnline()){
                        linearBuscarDni.setVisibility(View.VISIBLE);
                    }
                }else{
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
                if(!dni.isEmpty()){
                    config.buscarTrabajador(dni,txt_nomTrabajador);
                }
            }
        });

        config.llenarSpinnerConNumeros(spn_timeCargoAnyo,10,getActivity());
        config.llenarSpinnerConNumeros(spn_timeCargoMes,11,getActivity());

        spn_horarioTrabajo.setAdapter(config.LlenarSpinner("horario_trab_fromato_medicion","desc_horario",getActivity()));
        spn_regimen.setAdapter(config.LlenarSpinner("regimen_formato_medicion","nom_regimen",getActivity()));
        spn_horarioRefrigerio.setAdapter(config.LlenarSpinner("horario_refrig_formato_medicion","nom_horario",getActivity()));

        config.MostrarCampos(linearOtroHorario,spn_horarioTrabajo);
        config.MostrarCampos(linearOtroRegimen,spn_regimen);
        config.MostrarCampos(linearOtroRefrigerio,spn_horarioRefrigerio);

        spn_descTrabajo.setAdapter(config.LlenarSpinner(new String[]{"Trabajo al aire libre sin carga solar o bajo techo", "Trabajo al aire libre con carga solar"}));
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
                if(
                        validar.validarCampoObligatorio(tv_equipoRadiacion)&&
                        validar.validarCampoObligatorio(tv_horaVerificacion) &&
                        validar.validarImagen(cargarImagen,getActivity()) &&
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
                        validar.validarCampoObligatorio(txt_aRealizada )&&
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

                ){
                    String valorEquipoRadUv = tv_equipoRadiacion.getText().toString();
                    String valorHoraVerificacion = tv_horaVerificacion.getText().toString();
                    int valorGroupVerificacion = validar.getValor2(radioGroupVerificacion,rootView);

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

                    if(valorHorarioTrabajo.equals("OTRO")) valorHorarioTrabajo = txt_otroHorario.getText().toString();
                    if(valorRegimen.equals("OTRO")) valorRegimen = txt_otroRegimen.getText().toString();
                    if(valorRefrigerio.equals("OTRO")) valorRefrigerio = txt_otroRefrigerio.getText().toString();

                    String valorFuenteGen = txt_fuenteGen.getText().toString();
                    String valorTipoFuenteRadiac = txt_tipoFuenteRadiacion.getText().toString();
                    String valorDesTrabajo = spn_descTrabajo.getSelectedItem().toString();
                    String valorMantenFuente = txt_mantenimientoFuente.getText().toString();

                    int valorGroupSombraDescanso = validar.getValor2(radioGroupSombraDescanso,rootView);
                    int valorGroupMallasTramo = validar.getValor2(radioGroupMallasTramo,rootView);
                    String valorOtrosIng = txt_OtrosIngenieria.getText().toString();

                    int valorGroupProgramaTrab = validar.getValor2(radioGroupProgramaTrab,rootView);
                    int valorGroupAireLibre = validar.getValor2(radioGroupAireLibre,rootView);
                    String valorOtrosAdmin = txt_OtrosAdministrativo.getText().toString();


                    int valorProteccionBrillo = validar.getValor2(radioGroupProteccionBrillo,rootView);
                    int valorProteccionLateral = validar.getValor2(radioGroupProteccionLateral,rootView);
                    int valorLentesOscuros = validar.getValor2(radioGroupLentesOscuroo,rootView);
                    int valorCertificacion = validar.getValor2(radioGroupCertificacion,rootView);
                    int valorColorOscuro = validar.getValor2(radioGroupColorOscuro,rootView);
                    int valorMangaLarga = validar.getValor2(radioGroupMangaLarga,rootView);
                    int valorTramaGruesa = validar.getValor2(radioGroupTramaGruesa,rootView);
                    int valorUtilGorro = validar.getValor2(radioGroupUtilGorro,rootView);
                    int valorProtLegionario = validar.getValor2(radioGroupProtLegionario,rootView);
                    int valorAlaAncha = validar.getValor2(radioGroupAlaAncha,rootView);
                    int valorUtilCasco = validar.getValor2(radioGroupUtilCasco,rootView);
                    int valorCubreNuca = validar.getValor2(radioGroupCubreNuca,rootView);
                    int valorUtilFPS = validar.getValor2(radioGroupUtilFPS,rootView);
                    int valorGuiaFPS= validar.getValor2(radioGroupGuiaFPS,rootView);
                    int valorFrecuenciaApli = validar.getValor2(radioGroupFrecuenciaAplicacion,rootView);
                    String valorOtraFrecuencia = txt_otraFrecuencia.getText().toString();
                    String valorOtrosEpps= txt_otrosEpps.getText().toString();
                    String valorUw_cm2= txt_uW_cm2.getText().toString();

                    String fecha_registro = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                    Equipos equipos1 = equipos.Buscar(valorEquipoRadUv);

                    ArrayList<HashMap<String, String>> resultList = dao_registroFormatos.getListCantidadFormatoId(Integer.parseInt(id_pt_trabajo));
                    int total_registros = dao_registroFormatos.get_cant_formato_medicion();
                    String cod_formato = config.GenerarCodigoFormato(Integer.parseInt(id_formato),resultList.size());
                    String cod_registro = config.generarCodigoRegistro(total_registros);

                    String valorRutaFoto = uri.getEncodedPath();
                    int id_plan_formato_reg = dao_registroFormatos.getRecordIdByPosition();

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
                            nuevo.getUsuario_nombres()+ " " +nuevo.getUsuario_apater()+" "+nuevo.getUsuario_amater(),
                            "" +valorGroupVerificacion,
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
                           (id_plan_formato_reg+1),
                            valorTipoPiel,
                            valorColorPiel,
                            valorFuenteGen,
                            valorTipoFuenteRadiac,
                            "" +valorGroupSombraDescanso,
                            "" +valorGroupMallasTramo,
                            "" +valorGroupProgramaTrab,
                            "" +valorGroupAireLibre,
                            valorMantenFuente,
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

                    if(config.isOnline()){
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
                                config.uploadImage(imageFile, cod_formato,id_pt_trabajo,cod_registro);
                                // Mostrar el JSON en el log
                                Log.e("JSON", cadenaJson);
                                Log.e("Respuesta",response.toString());
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
                    }else{
                        // SECCION DE REGISTRO DE MANERA LOCAL
                        DAO_RegistroRadiacionUV nuevoRegistro = new DAO_RegistroRadiacionUV(getActivity());
                        nuevoRegistro.RegistroRadiacionUV(cabecera);
                        //nuevoRegistro.RegistrarRadiacionUv_Detalle(detalle);

                        DAO_FormatosTrabajo dao_fromatosTrabajo = new DAO_FormatosTrabajo(getActivity());
                        for_RadiacionUv = dao_fromatosTrabajo.Buscar(id_plan_trabajo,id_formato);
                        for_RadiacionUv.setRealizado(for_RadiacionUv.getRealizado()+1);
                        for_RadiacionUv.setPor_realizar(for_RadiacionUv.getPor_realizar()-1);

                        dao_fromatosTrabajo.actualizarFormatoTrabajo(for_RadiacionUv);


                        new AlertDialog.Builder(getActivity())
                                .setTitle("Registro guardado Localmente")
                                .setMessage("El registro ha sido guardado exitosamente.")
                                .setPositiveButton(android.R.string.ok, null)
                                .show();

                        // Regresa al Fragment anterior
                        getFragmentManager().popBackStack();
                    }
                }
            }
        });



        return rootView;
    }

    public void init(View view){

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
}