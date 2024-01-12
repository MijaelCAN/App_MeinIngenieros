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

import java.text.SimpleDateFormat;
import java.util.Date;
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
    TextView  tv_horaVerificacion, tv_fechaMonitoreo, tv_horaInicioMoni, tv_horaFinalMoni, tv_colorPiel;
    AppCompatButton btn_subirFotoRadiacion;
    FloatingActionButton btn_guardar;
    ExtendedFloatingActionButton btnCancelar;
    Spinner spn_tipoDoc, spn_tipoPiel, spn_timeCargoAnyo, spn_timeCargoMes, spn_horarioTrabajo, spn_regimen,
            spn_horarioRefrigerio, spn_descTrabajo, spn_proteccionBrillo, spn_proteccionLateral, spn_gorro,
            spn_casco, spn_ninguno, spn_proteccionLegionario, spn_proteccionAlaAncha, spn_ropa, spn_colorOdcuro,
            spn_mangaLarga, spn_tramaGruesa, spn_utilizaEpps, spn_guiaUsoEpp, spn_frecuencia2horas, spn_frecuencia30min;
    EditText txt_numDoc, txt_nomTrabajador, txt_edad, txt_areaTrabajo, txt_puestoTrabajo, txt_aRealizada, txt_timeExpoHora, txt_jornadaTrabajo,
            txt_mantenimientoFuente, txt_OtrosIngenieria, txt_OtrosAdministrativo, txt_otrosVestimenta, txt_otrosEpps, txt_fuenteGen, txt_tipoFuenteRadiacion,
            txt_otroHorario, txt_otroRegimen, txt_otroRefrigerio, txt_uW_cm2;
    CheckBox check_casco, check_lentesOscuros, check_cubreNuca, check_gorroSombrero;
    RadioGroup radioGroupVerificacion, radioGroupSombraDescanso, radioGroupMallasTramo, radioGroupProgramaTrab,
            radioGroupAireLibre;
    LinearLayout linearOtroHorario, linearOtroRegimen, linearOtroRefrigerio;
    ImageView imgRadiacion;
    Uri uri;


    public RadiacionUvFragment() {
        // Required empty public constructor
    }
    Formatos_Trabajo for_RadiacionUv;
    Validaciones validar = new Validaciones();


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
        InputDateConfiguration config = new InputDateConfiguration(getActivity(),id_colaborador,nom_Empresa,rootView);
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
        spn_tipoPiel    .setAdapter(config.LlenarSpinner(new String[]{"Tipo I", "Tipo II","Tipo III","Tipo IV","Tipo V","Tipo VI"}));
        spn_tipoPiel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String seleccion = (String) parent.getItemAtPosition(position);
                if(seleccion.equals("Tipo I")) tv_colorPiel.setText("Muy clara");
                else if (seleccion.equals("Tipo II")) tv_colorPiel.setText("clara");
                else if (seleccion.equals("Tipo III")) tv_colorPiel.setText("Morena clara");
                else if (seleccion.equals("Tipo IV")) tv_colorPiel.setText("Morena");
                else if (seleccion.equals("Tipo V")) tv_colorPiel.setText("Oscura");
                else if (seleccion.equals("Tipo VI")) tv_colorPiel.setText("Muy oscura");
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
        spn_proteccionBrillo.setAdapter(config.LlenarSpinner(new String[]{"SI", "NO"}));
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
        spn_frecuencia30min.setAdapter(config.LlenarSpinner(new String[]{"SI", "NO"}));

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
                        validar.validarCampoObligatorio(spn_tipoPiel) &&
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
                    String valorGroupVerificacion = validar.getValor2(radioGroupVerificacion,rootView);
                    String valorFechaMonitoreo = tv_fechaMonitoreo.getText().toString();
                    String valorHoraInicioMoni = tv_horaInicioMoni.getText().toString();
                    String valorHoraFinalMoni = tv_horaFinalMoni.getText().toString();
                    String valorTimeExpo = txt_timeExpoHora.getText().toString();
                    String valorJornada = txt_jornadaTrabajo.getText().toString();
                    String valorTipoDoc = spn_tipoDoc.getSelectedItem().toString();
                    String valorNumDoc = txt_numDoc.getText().toString();
                    String valorNombreTra = txt_nomTrabajador.getText().toString();
                    String valorEdad = txt_edad.getText().toString();
                    String valorAreaTra = txt_areaTrabajo.toString().toString();
                    String valorPuestoTra = txt_puestoTrabajo.getText().toString();
                    String valorActividades = txt_aRealizada.getText().toString();
                    String valorTipoPiel = spn_tipoPiel.getSelectedItem().toString();
                    String valorColorPiel = tv_colorPiel.getText().toString();
                    String valorTimeCargoAnyo = spn_timeCargoAnyo.getSelectedItem().toString();
                    String valorTimeCargoMeses = spn_timeCargoMes.getSelectedItem().toString();

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

                    String valorGroupSombraDescanso = validar.getValor2(radioGroupSombraDescanso,rootView);
                    String valorGroupMallasTramo = validar.getValor2(radioGroupMallasTramo,rootView);
                    String valorOtrosIng = txt_OtrosIngenieria.getText().toString();

                    String valorGroupProgramaTrab = validar.getValor2(radioGroupProgramaTrab,rootView);
                    String valorGroupAireLibre = validar.getValor2(radioGroupAireLibre,rootView);
                    String valorOtrosAdmin = txt_OtrosAdministrativo.getText().toString();

                    String valorCasco = String.valueOf(check_casco.isChecked());
                    String valorLentesOscuros = String.valueOf(check_lentesOscuros.isChecked());
                    String valorCubreNuca = String.valueOf(check_cubreNuca.isChecked());
                    String valorGorroSombrero = String.valueOf(check_gorroSombrero.isChecked());
                    String valorOtrosVestimenta = txt_otrosVestimenta.getText().toString();

                    String valorProteccionBrillo = spn_proteccionBrillo.getSelectedItem().toString();
                    String valorProteccionLateral = spn_proteccionLateral.getSelectedItem().toString();
                    String valorGorro = spn_gorro.getSelectedItem().toString();
                    String valorCascospn = spn_casco.getSelectedItem().toString();
                    String valorNinguno = spn_ninguno.getSelectedItem().toString();
                    String valorProteccionLegionario = spn_proteccionLegionario.getSelectedItem().toString();
                    String valorProteccionAlaAncho = spn_proteccionAlaAncha.getSelectedItem().toString();
                    String valorRopa = spn_ropa.getSelectedItem().toString();
                    String valorColorOscuro = spn_colorOdcuro.getSelectedItem().toString();
                    String valorMangaLarga = spn_mangaLarga.getSelectedItem().toString();
                    String valorTramaGruesa = spn_tramaGruesa.getSelectedItem().toString();
                    String valorUtilizaEpps = spn_utilizaEpps.getSelectedItem().toString();
                    String valorGuiaEpps = spn_guiaUsoEpp.getSelectedItem().toString();
                    String valorFrecuencia2h = spn_frecuencia2horas.getSelectedItem().toString();
                    String valorFrecuencia30min = spn_frecuencia30min.getSelectedItem().toString();
                    String valorOtrosEpps= txt_otrosEpps.getText().toString();
                    String valorUw_cm2= txt_uW_cm2.getText().toString();

                    String fecha_registro = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                    Equipos equipos1 = equipos.Buscar(valorEquipoRadUv);

                    RadiacionUv_Registro cabecera = new RadiacionUv_Registro(
                            -1,
                            "RV-0001",
                            "cod_registro",
                            id_formato,
                            id_plan_trabajo,
                            id_pt_trabajo,
                            String.valueOf(equipos1.getCod_equipo()),
                            equipos1.getNombre(),
                            equipos1.getSerie(),
                            String.valueOf(equipos1.getId_equipo_registro()),
                            id_colaborador,
                            nuevo.getUsuario_nombres(),
                            valorGroupVerificacion,
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
                            "1",
                            valorOtrosIng,
                            fecha_registro,
                            id_colaborador
                    );

                    RadiacionUv_RegistroDetalle detalle = new RadiacionUv_RegistroDetalle(
                            cabecera.getId(),
                            valorTipoPiel,
                            valorColorPiel,
                            valorFuenteGen,
                            valorTipoFuenteRadiac,
                            valorGroupSombraDescanso,
                            valorGroupMallasTramo,
                            valorGroupProgramaTrab,
                            valorGroupAireLibre,
                            valorMantenFuente,
                            valorCasco,
                            valorLentesOscuros,
                            valorCubreNuca,
                            valorGorroSombrero,
                            valorOtrosVestimenta,
                            valorProteccionBrillo,
                            valorProteccionLateral,
                            valorGorro,
                            valorCascospn,
                            valorNinguno,
                            valorProteccionLegionario,
                            valorProteccionAlaAncho,
                            valorRopa,
                            valorColorOscuro,
                            valorMangaLarga,
                            valorTramaGruesa,
                            valorUtilizaEpps,
                            valorGuiaEpps,
                            valorFrecuencia2h,
                            valorFrecuencia30min,
                            valorOtrosEpps,
                            fecha_registro,
                            id_colaborador
                    );

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
                        nuevoRegistro.RegistrarRadiacionUv_Detalle(detalle);

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
        spn_tipoPiel = view.findViewById(R.id.cbx_tipoPiel);
        tv_colorPiel = view.findViewById(R.id.tv_colorPiel);
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

        check_casco = view.findViewById(R.id.check_casco);
        check_lentesOscuros = view.findViewById(R.id.check_lentesOscuros);
        check_cubreNuca = view.findViewById(R.id.check_cubreNuca);
        check_gorroSombrero = view.findViewById(R.id.check_gorroSombrero);
        txt_otrosVestimenta = view.findViewById(R.id.txt_otrosVestimenta);

        spn_proteccionBrillo = view.findViewById(R.id.spn_proteccionBrillo);
        spn_proteccionLateral = view.findViewById(R.id.spn_proteccionLateral);
        spn_gorro = view.findViewById(R.id.spn_gorro);
        spn_casco = view.findViewById(R.id.spn_casco);
        spn_ninguno = view.findViewById(R.id.spn_ninguno);
        spn_proteccionLegionario = view.findViewById(R.id.spn_proteccionLegionario);
        spn_proteccionAlaAncha = view.findViewById(R.id.spn_proteccionAlaAncha);
        spn_ropa = view.findViewById(R.id.spn_ropa);
        spn_colorOdcuro = view.findViewById(R.id.spn_colorOdcuro);
        spn_mangaLarga = view.findViewById(R.id.spn_mangaLarga);
        spn_tramaGruesa = view.findViewById(R.id.spn_tramaGruesa);
        spn_utilizaEpps = view.findViewById(R.id.spn_utilizaEpps);
        spn_guiaUsoEpp = view.findViewById(R.id.spn_guiaUsoEpp);
        spn_frecuencia2horas = view.findViewById(R.id.spn_frecuencia2horas);
        spn_frecuencia30min = view.findViewById(R.id.spn_frecuencia30min);
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


    }

    @Override
    public void onImagePicked(Uri imageUri) {
        this.uri = imageUri;
        if (imgRadiacion != null && imageUri != null) {
            imgRadiacion.setImageURI(imageUri);
        }
    }
}