package com.mijael.mein;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
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
import com.mijael.mein.DAO.DAO_RegistroVibracion;
import com.mijael.mein.DAO.DAO_Usuario;
import com.mijael.mein.Entidades.Equipos;
import com.mijael.mein.Entidades.Formatos_Trabajo;
import com.mijael.mein.Entidades.RegistroFormatos;
import com.mijael.mein.Entidades.RegistroFormatos_Detalle;
import com.mijael.mein.Entidades.Usuario;
import com.mijael.mein.Entidades.Vibracion_Registro;
import com.mijael.mein.Entidades.Vibracion_RegistroDetalle;
import com.mijael.mein.Extras.FragmentoImagen;
import com.mijael.mein.Extras.InputDateConfiguration;
import com.mijael.mein.Extras.Validaciones;
import com.mijael.mein.SERVICIOS.DosimetriaService;

import java.io.File;
import java.io.IOException;
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


public class VibracionFragment extends Fragment implements FragmentoImagen.ImagePickerListener{
    private boolean cargarImagen = false;
    View rootView;
    TextView tv_nombreUsuario, tv_nomEmpresa;
    String id_plan_trabajo, id_pt_trabajo, id_formato,id_colaborador, nom_Empresa;
    String[] arrayTipoVibra, arrayYN,arrayLateralidad;
    TextView tv_horaVerificacion, tv_fechaMonitoreo, tv_horaInicioMoni, tv_horaFinalMoni;
    AutoCompleteTextView spn_equipoutilizado;
    Spinner spn_tipoVibracion, spn_tipoDoc, spn_horarioTrabajo, spn_regimen, spn_horarioRefrig, spn_frecuencia, spn_lateralidadMano;
    RadioGroup radioGroupVerificacion,radioGroupIng, radioGroupAdm, radioGroupSeñal, radioGroupCapac, radioGroupMante;
    RadioButton radio_ingSI, radio_admSI;
    AppCompatButton btnSubirFotoVibra, btn_BuscarDni;
    FloatingActionButton btn_guardar;
    ExtendedFloatingActionButton btnCancelar;
    EditText txt_jornada, txt_timeExpo, txt_numDoc, txt_nomTrab, txt_edadTrab, txt_areaTrab, txt_puestoTrab, txt_actRealizada;
    EditText txt_fuenteGenVibra, txt_descFuenteGen, txt_nombreControl, txt_otrosAdmin, txt_resulX, txt_resulY, txt_resulZ;
    CheckBox check_botas, check_guantes, check_casco, check_proteccionAud;
    CardView card_ing, card_admn;
    LinearLayout linearOtroHorario, linearOtroRegimen, linearOtroRefrigerio, linearBuscarDni, linearLateralidadMano;
    EditText txt_otroHorario, txt_otroRegimen, txt_otroRefrigerio;
    ImageView imgVibra;
    Uri uri;
    DAO_RegistroFormatos dao_registroFormatos;
    RegistroFormatos registros;
    RegistroFormatos_Detalle detalles;
    public VibracionFragment() {
        // Required empty public constructor
        dao_registroFormatos = new DAO_RegistroFormatos(getContext());
    }
    Formatos_Trabajo for_Vibracion;
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
                             Bundle savedInstanceStance) {

        rootView = inflater.inflate(R.layout.fragment_vibracion,container,false);
        config = new InputDateConfiguration(getActivity(),id_colaborador,nom_Empresa,rootView);
        init(rootView);

        DAO_Equipos equipos = new DAO_Equipos(getActivity());
        List<String> lista_CodEquipos = equipos.obtener_CodEquipos();
        DAO_Usuario usuario = new DAO_Usuario(getActivity());
        Usuario nuevo = usuario.BuscarUsuario(Integer.parseInt(id_colaborador));
        arrayTipoVibra = new String[]{"Cuerpo Completo", "Mano - Brazo"};
        arrayYN = new String[]{"DNI", "CE"};
        arrayLateralidad = new String[]{"Diestro","Zurdo"};

        config.ConfigPantalla();
        spn_tipoVibracion.setAdapter(config.LlenarSpinner(arrayTipoVibra));
        spn_lateralidadMano.setAdapter(config.LlenarSpinner(arrayLateralidad));
        spn_tipoDoc.setAdapter(config.LlenarSpinner(arrayYN));
        config.configurarAutoCompleteTextView(spn_equipoutilizado,lista_CodEquipos);

        spn_tipoVibracion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemSelecionado = parent.getItemAtPosition(position).toString();
                if(itemSelecionado.equals("Mano - Brazo")){
                    linearLateralidadMano.setVisibility(View.VISIBLE);
                }else {
                    linearLateralidadMano.setVisibility(View.GONE);
                }
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
                    config.buscarTrabajador(dni,txt_nomTrab);
                }
            }
        });

        spn_horarioTrabajo.setAdapter(config.LlenarSpinner("horario_trab_fromato_medicion","desc_horario",getActivity()));
        spn_regimen.setAdapter(config.LlenarSpinner("regimen_formato_medicion","nom_regimen",getActivity()));
        spn_horarioRefrig.setAdapter(config.LlenarSpinner("horario_refrig_formato_medicion","nom_horario",getActivity()));
        spn_frecuencia.setAdapter(config.LlenarSpinner("frecuencia","nom_frecuencia",getActivity()));

        config.MostrarCampos(linearOtroHorario,spn_horarioTrabajo);
        config.MostrarCampos(linearOtroRegimen,spn_regimen);
        config.MostrarCampos(linearOtroRefrigerio,spn_horarioRefrig);

        tv_horaVerificacion.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {config.showTimePickerDialog(rootView,tv_horaVerificacion);}});
        tv_fechaMonitoreo.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {config.showDatePickerDialog(rootView,tv_fechaMonitoreo);}});
        tv_horaInicioMoni.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {config.showTimePickerDialog(rootView,tv_horaInicioMoni);}});
        tv_horaFinalMoni.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {config.showTimePickerDialog(rootView,tv_horaFinalMoni);}});

        radioGroupIng.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {@Override public void onCheckedChanged(RadioGroup group, int checkedId) {mostrarOpcionesGone(group,checkedId,card_ing,radio_ingSI);}});
        radioGroupAdm.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {@Override public void onCheckedChanged(RadioGroup group, int checkedId) {mostrarOpcionesGone(group,checkedId,card_admn,radio_admSI);}});
        btnSubirFotoVibra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentoImagen fragmentoImagen = new FragmentoImagen(VibracionFragment.this);
                fragmentoImagen.show(getActivity().getSupportFragmentManager(), "imagePicker");
                cargarImagen = true;
            }
        });
        // Inflate the layout for this fragment
        btnCancelar.setOnClickListener(new View.OnClickListener() {
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
        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if(
                        validar.validarCampoObligatorio(spn_tipoVibracion) &&
                        validar.validarCampoObligatorio(spn_equipoutilizado) &&
                        validar.validarCampoObligatorio(tv_horaVerificacion) &&
                        validar.validarImagen(cargarImagen,getActivity()) &&
                        validar.validarCampoObligatorio(tv_fechaMonitoreo) &&
                        validar.validarCampoObligatorio(tv_horaInicioMoni) &&
                        validar.validarCampoObligatorio(tv_horaFinalMoni) &&
                        validar.validarCampoObligatorio(txt_timeExpo) &&
                        validar.validarCampoObligatorio(txt_jornada) &&
                        validar.validarCampoObligatorio(spn_tipoDoc) &&
                        validar.validarCampoObligatorio(txt_numDoc) &&
                        validar.validarCampoObligatorio(txt_nomTrab) &&
                        validar.validarCampoObligatorio(txt_puestoTrab) &&
                        validar.validarCampoObligatorio(txt_edadTrab) &&
                        validar.validarCampoObligatorio(txt_actRealizada) &&
                        validar.validarCampoObligatorio(spn_horarioTrabajo) &&
                        validar.validarCampoObligatorio(spn_horarioRefrig) &&
                        validar.validarCampoObligatorio(spn_regimen) &&
                        validar.validarCampoObligatorio(txt_fuenteGenVibra) &&
                        validar.validarCampoObligatorio(txt_descFuenteGen) &&
                        // FALTA VALIDAR LOS RADIOGROUP Y RADIOBUTTON
                        validar.validarCampoObligatorio(txt_resulX) &&
                        validar.validarCampoObligatorio(txt_resulY) &&
                        validar.validarCampoObligatorio(txt_resulZ)
                ){*/
                    String valorTipoVibracion = spn_tipoVibracion.getSelectedItem().toString();
                    String valorLaterMano = spn_lateralidadMano.getSelectedItem().toString();
                    String valorEquipoUtil = spn_equipoutilizado.getText().toString();
                    String valorHoraVerif = tv_horaVerificacion.getText().toString();
                    int valorVerificacion = validar.getValor2(radioGroupVerificacion,rootView);
                    //String valorFoto = uri.toString();
                    String f = tv_fechaMonitoreo.getText().toString();
                    String valorFechaMoni = config.convertirFecha(f);
                    String valorHoraInicioMoni = tv_horaInicioMoni.getText().toString();
                    String valorHoraFinalMoni = tv_horaFinalMoni.getText().toString();
                    String valorTimeExpo = txt_timeExpo.getText().toString();
                    String valorJornada = txt_jornada.getText().toString();
                    String valorTipoDoc = spn_tipoDoc.getSelectedItem().toString();
                    String valorNumDoc = txt_numDoc.getText().toString();
                    String valorNombreTrab = txt_nomTrab.getText().toString();
                    String valorPuestoTrabaj = txt_puestoTrab.getText().toString();
                    String valorAreaTrabajo = txt_areaTrab.getText().toString();
                    String valorEdad = txt_edadTrab.getText().toString();
                    String valor_aRealizada = txt_actRealizada.getText().toString();
                    String valorHorarioTrabajo = spn_horarioTrabajo.getSelectedItem().toString();
                    String valorRefrigerio = spn_horarioRefrig.getSelectedItem().toString();
                    String valorRegimen = spn_regimen.getSelectedItem().toString();
                    String valorFuenteGenVibra = txt_fuenteGenVibra.getText().toString();
                    String valorDescripcionFuente = txt_descFuenteGen.getText().toString();
                    int valorGroupIng = validar.getValor2(radioGroupIng,rootView);
                    String valorNombreControl = txt_nombreControl.getText().toString();
                    int valorGroupAdm = validar.getValor2(radioGroupAdm,rootView);
                    int valorGroupSeñal = validar.getValor2(radioGroupSeñal,rootView);
                    int valorGroupCapac = validar.getValor2(radioGroupCapac, rootView);
                    int valorGroupManten = validar.getValor2(radioGroupMante, rootView);
                    String valorFrecuencia = spn_frecuencia.getSelectedItem().toString();

                    /*String valorCheckBotas = String.valueOf(check_botas.isChecked());
                    String valorCheckGuantes = String.valueOf(check_guantes.isChecked());
                    String valorCheckCasco = String.valueOf(check_casco.isChecked());
                    String valorCheckProtAudi = String.valueOf(check_proteccionAud.isChecked());*/
                    int valorCheckBotas = check_botas.isChecked() ? 1 : 0;
                    int valorCheckGuantes = check_guantes.isChecked() ? 1 : 0;
                    int valorCheckCasco = check_casco.isChecked() ? 1 : 0;
                    int valorCheckProtAudi = check_proteccionAud.isChecked() ? 1 : 0;

                    String valorOtroAdm = txt_otrosAdmin.getText().toString();
                    String valorX = txt_resulX.getText().toString();
                    String valorY = txt_resulY.getText().toString();
                    String valorZ = txt_resulZ.getText().toString();

                    if(valorHorarioTrabajo.equals("OTRO")) valorHorarioTrabajo = txt_otroHorario.getText().toString();
                    if(valorRegimen.equals("OTRO")) valorRegimen = txt_otroRegimen.getText().toString();
                    if(valorRefrigerio.equals("OTRO")) valorRefrigerio = txt_otroRefrigerio.getText().toString();

                    Equipos equipos1 = equipos.Buscar(valorEquipoUtil);


                String fecha_registro = "";
                String cod_formato;
                String cod_registro;
                String valorRutaFoto;
                int id_plan_formato_reg;
                int id_plan_trabajo_formato_reg = -1;

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
                    id_plan_trabajo_formato_reg = registros.getId_plan_trabajo_formato_reg();
                    id_plan_formato_reg = registros.getId_plan_trabajo_formato_reg();
                    fecha_registro = registros.getFec_reg();
                    cod_registro = registros.getCod_registro();
                    cod_formato = registros.getCod_formato();
                    valorRutaFoto = registros.getRuta_foto();
                    id_formato = String.valueOf(registros.getId_formato());
                    id_plan_trabajo = String.valueOf(registros.getId_plan_trabajo());
                    id_pt_trabajo = String.valueOf(registros.getId_pt_formato());
                }


                    Vibracion_Registro registro = new Vibracion_Registro(
                            id_plan_trabajo_formato_reg,
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
                            valorTipoVibracion,
                            valorLaterMano,
                            String.valueOf(valorVerificacion),
                            valorHoraVerif,
                            valorFechaMoni,
                            valorHoraInicioMoni,
                            valorHoraFinalMoni,
                            valorTimeExpo,
                            valorJornada,
                            valorTipoDoc,
                            valorNumDoc,
                            valorNombreTrab,
                            valorPuestoTrabaj,
                            valorAreaTrabajo,
                            valor_aRealizada,
                            valorEdad,
                            valorHorarioTrabajo,
                            valorRefrigerio,
                            valorRegimen,
                            String.valueOf(valorGroupIng),
                            valorNombreControl,
                            String.valueOf(valorGroupAdm),
                            String.valueOf(valorGroupSeñal),
                            String.valueOf(valorGroupCapac),
                            String.valueOf(valorGroupManten),
                            fecha_registro,
                            id_colaborador,
                            valorRutaFoto
                    );
                    Vibracion_RegistroDetalle detalle = new Vibracion_RegistroDetalle(
                            id_plan_formato_reg,
                            valorFuenteGenVibra,
                            valorDescripcionFuente,
                            valorFrecuencia,
                            ""+valorCheckBotas,
                            ""+valorCheckGuantes,
                            ""+valorCheckCasco,
                            ""+valorCheckProtAudi,
                            valorOtroAdm,
                            valorX,
                            valorY,
                            valorZ,
                            fecha_registro,
                            id_colaborador
                    );

                    if (config.isOnline()) {

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("https://test.meiningenieros.pe/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        DosimetriaService service1 = retrofit.create(DosimetriaService.class);
                        Gson gson = new Gson();

                        // Crear un objeto JSON principal
                        JsonObject jsonObject = new JsonObject();

                        JsonObject registroJson = gson.toJsonTree(registro).getAsJsonObject();
                        jsonObject.add("cabecera", registroJson);

                        JsonObject detalleJson = gson.toJsonTree(detalle).getAsJsonObject();
                        jsonObject.add("detalle", detalleJson);


                        String cadenaJson = gson.toJson(jsonObject);
                        RequestBody json = RequestBody.create(MediaType.parse("application/json"), cadenaJson);

                        Call<ResponseBody> call1 = service1.insertVibracion(json);
                        call1.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {
                                    try {
                                        // Obtener el mensaje de respuesta del endpoint
                                        String respuesta = response.body().string();
                                        Log.e("Respuesta del endpoint", respuesta);
                                        // Aquí puedes agregar el código adicional que necesites
                                        if(uri!=null){
                                            File imageFile = new File(uri.getEncodedPath());
                                            config.uploadImage(imageFile, cod_formato,id_pt_trabajo,cod_registro);
                                        }
                                        // Mostrar el JSON en el log
                                        Log.e("JSON", cadenaJson);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    // Manejar la respuesta de error del servidor
                                    Log.e("Error en la respuesta", "Código de estado: " + response.code());
                                }
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
                        DAO_RegistroVibracion nuevoRegistro = new DAO_RegistroVibracion(getActivity());
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Guardar formulario");
                        builder.setMessage("¿Deseas seguir llenando el formulario o terminar?");

                        builder.setPositiveButton("Seguir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(registros == null){
                                    nuevoRegistro.RegistroVibracion(registro);
                                    nuevoRegistro.RegistrarVibracionDetalle(detalle);
                                    DAO_FormatosTrabajo dao_fromatosTrabajo = new DAO_FormatosTrabajo(getActivity());
                                    for_Vibracion = dao_fromatosTrabajo.Buscar(id_plan_trabajo,id_formato);
                                    for_Vibracion.setRealizado(for_Vibracion.getRealizado()+1);
                                    for_Vibracion.setPor_realizar(for_Vibracion.getPor_realizar()-1);
                                    dao_fromatosTrabajo.actualizarFormatoTrabajo(for_Vibracion);
                                }else{
                                    nuevoRegistro.ActualizarVibracion(registro,registros.getId_plan_trabajo_formato_reg());
                                    nuevoRegistro.ActualizarVibracionDetalle(detalle,detalles.getId_formato_reg_detalle());
                                }
                            }
                        });
                        builder.setNegativeButton("Terminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(registros == null){
                                    nuevoRegistro.RegistroVibracion(registro);
                                    nuevoRegistro.RegistrarVibracionDetalle(detalle);
                                    DAO_FormatosTrabajo dao_fromatosTrabajo = new DAO_FormatosTrabajo(getActivity());
                                    for_Vibracion = dao_fromatosTrabajo.Buscar(id_plan_trabajo,id_formato);
                                    for_Vibracion.setRealizado(for_Vibracion.getRealizado()+1);
                                    for_Vibracion.setPor_realizar(for_Vibracion.getPor_realizar()-1);
                                    dao_fromatosTrabajo.actualizarFormatoTrabajo(for_Vibracion);
                                }else{
                                    nuevoRegistro.ActualizarVibracion(registro,registros.getId_plan_trabajo_formato_reg());
                                    nuevoRegistro.ActualizarVibracionDetalle(detalle,detalles.getId_formato_reg_detalle());
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
        if(registros!=null){
            if(detalles!=null){
                EditarCampos();
            }else{
                builder.setTitle("Aviso")
                        .setMessage("Registro sin Detalle.")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                Volver();
            }

        }else{
            builder.setTitle("Aviso")
                    .setMessage("Realizara un nuevo registro.")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        return rootView;
    }
    public void init(View view){
        tv_nombreUsuario = view.findViewById(R.id.tv_nomUsuario);
        tv_nomEmpresa = view.findViewById(R.id.tv_nomEmpresa);

        // Asignación de elementos de la interfaz a variables
        spn_tipoVibracion = view.findViewById(R.id.tipo_vibracion);
        spn_lateralidadMano = view.findViewById(R.id.spn_lateralidadMano);
        spn_equipoutilizado = view.findViewById(R.id.spn_equipoUtilizado);
        tv_horaVerificacion = view.findViewById(R.id.hora_situ);
        radioGroupVerificacion = view.findViewById(R.id.radioGroup_verf_insitu);
        btnSubirFotoVibra = view.findViewById(R.id.btn_subirFotoVibra);
        tv_fechaMonitoreo = view.findViewById(R.id.tv_fecha);
        tv_horaInicioMoni = view.findViewById(R.id.tv_horaInicial);
        tv_horaFinalMoni = view.findViewById(R.id.tv_horaFinal);
        txt_jornada = view.findViewById(R.id.txt_jornada);
        txt_timeExpo = view.findViewById(R.id.txt_tiempoExposicion);
        spn_tipoDoc = view.findViewById(R.id.cbx_tipoDocumento);
        txt_numDoc = view.findViewById(R.id.txt_numDocumento);
        txt_nomTrab = view.findViewById(R.id.txt_nombre);
        txt_edadTrab = view.findViewById(R.id.txt_edad);
        txt_areaTrab = view.findViewById(R.id.txt_areaTrabajo);
        txt_puestoTrab = view.findViewById(R.id.txt_puestoTrabajo);
        txt_actRealizada = view.findViewById(R.id.txt_actividades);
        spn_horarioTrabajo = view.findViewById(R.id.cbx_horarioTrabajo);
        spn_regimen = view.findViewById(R.id.cbx_regimen);
        spn_horarioRefrig = view.findViewById(R.id.cbx_refrigerio);
        txt_fuenteGenVibra = view.findViewById(R.id.txt_fuenteGeneradora);
        txt_descFuenteGen = view.findViewById(R.id.txt_descripcionFuente);
        radioGroupIng = view.findViewById(R.id.radioGroupIngenieria);
        radioGroupAdm = view.findViewById(R.id.radioGroupAdminis);


        card_ing = view.findViewById(R.id.Card_Ingenieria);
        card_admn = view.findViewById(R.id.Card_Administrativo);
        radio_ingSI = view.findViewById(R.id.radioIngenieriaSi);
        radio_admSI = view.findViewById(R.id.radio_AdministrativoSi);

        txt_nombreControl = view.findViewById(R.id.txt_nombreControl);
        radioGroupSeñal = view.findViewById(R.id.radioGroup_SeñalArea);
        radioGroupCapac = view.findViewById(R.id.radioGroup_CapacitacionVibra);
        radioGroupMante = view.findViewById(R.id.radioGroup_MatenimientoVibra);
        spn_frecuencia = view.findViewById(R.id.spn_frecuencia);
        check_botas = view.findViewById(R.id.check_botas);
        check_guantes = view.findViewById(R.id.check_guantes);
        check_casco = view.findViewById(R.id.check_casco);
        check_proteccionAud = view.findViewById(R.id.check_proteccionAudi);
        txt_otrosAdmin = view.findViewById(R.id.txt_OtrosAdministrativo);
        txt_resulX = view.findViewById(R.id.txt_x);
        txt_resulY = view.findViewById(R.id.txt_y);
        txt_resulZ = view.findViewById(R.id.txt_z);

        imgVibra = view.findViewById(R.id.img_Vibracion);

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
        linearLateralidadMano = view.findViewById(R.id.linear_LateralidadMano);

    }
    @Override
    public void onImagePicked(Uri imageUri) {
        this.uri = imageUri;
        if (imgVibra != null && imageUri != null) {
            imgVibra.setImageURI(imageUri);
            /*File imageFile = new File(imageUri.getEncodedPath());
            config.uploadImage(imageFile);*/
        }
    }
    private void mostrarOpcionesGone(RadioGroup group, int checkedId, CardView card, RadioButton radio) {
        if (checkedId == radio.getId()) {
            card.setVisibility(View.VISIBLE);
        } else {
            card.setVisibility(View.GONE);
        }
    }

    private void EditarCampos(){
        int indice = Arrays.asList(arrayTipoVibra).indexOf(registros.getTipo_vibracion());
        //Log.e("preuba", registros.getTipo_vibracion() + " - " + indice);
        spn_tipoVibracion.setSelection(indice+1);
        if(indice+1==2){
            int indice2 = Arrays.asList(arrayLateralidad).indexOf(registros.getLateralidad_mano());
            spn_lateralidadMano.setSelection(indice2+1);
        }
        spn_equipoutilizado.setText(registros.getCod_equipo1());
        tv_horaVerificacion.setText(registros.getHora_situ());

        if(registros.getVerf_insitu().equals("1")){
            radioGroupVerificacion.check(R.id.verf_insitusi);
        }else{
            radioGroupVerificacion.check(R.id.verf_insituno);
        }
        if(registros.getRuta_foto()!=null) {
            imgVibra.setImageURI(Uri.parse(registros.getRuta_foto()));
        }
        String fecha = "";
        if (!registros.getFec_monitoreo().isEmpty()) {
            /*String[] fec = registros.getFec_monitoreo().split(" ");
            String[] nueva_fec = fec[0].split("-");
            fecha = nueva_fec[0] + "/" + nueva_fec[1] + "/" + nueva_fec[2];*/
            fecha = config.convertirFecha2(registros.getFec_monitoreo());
        }
        tv_fechaMonitoreo.setText(fecha);
        tv_horaInicioMoni.setText(registros.getHora_inicial());
        tv_horaFinalMoni.setText(registros.getHora_final());
        txt_jornada.setText(registros.getJornada());
        txt_timeExpo.setText(registros.getTiempo_exposicion());

        int indice3 = Arrays.asList(arrayYN).indexOf(registros.getTipo_doc_trabajador());
        spn_tipoDoc.setSelection(indice3+1);
        txt_numDoc.setText(registros.getNum_doc_trabajador());
        txt_nomTrab.setText(registros.getNom_trabajador());
        txt_edadTrab.setText(String.valueOf(registros.getEdad_trabajador()));
        txt_areaTrab.setText(registros.getArea_trabajo());
        txt_puestoTrab.setText(registros.getPuesto_trabajador());
        txt_actRealizada.setText(registros.getActividades_realizadas());

        config.asignarAdaptadorYSeleccion(spn_horarioTrabajo, "horario_trab_fromato_medicion", "desc_horario", registros.getHora_trabajo(), getContext());
        if (spn_horarioTrabajo.getSelectedItem().equals("OTRO")) {
            txt_otroHorario.setText(registros.getHora_trabajo());
        }
        config.asignarAdaptadorYSeleccion(spn_regimen, "regimen_formato_medicion", "nom_regimen", registros.getRegimen_laboral(), getContext());
        if (spn_regimen.getSelectedItem().equals("OTRO")) {
            txt_otroRegimen.setText(registros.getRegimen_laboral());
        }
        config.asignarAdaptadorYSeleccion(spn_horarioRefrig, "horario_refrig_formato_medicion", "nom_horario", registros.getHorario_refrigerio(), getContext());
        if (spn_horarioRefrig.getSelectedItem().equals("OTRO")) {
            txt_otroRefrigerio.setText(registros.getHora_trabajo());
        }

        txt_fuenteGenVibra.setText(detalles.getFuente_generadora());
        txt_descFuenteGen.setText(detalles.getDesc_fuente_frio());

        if(registros.getCtrl_ingenieria().equals("1")){
            radioGroupIng.check(R.id.radioIngenieriaSi);
            txt_nombreControl.setText(registros.getNom_ctrl_ingenieria());
        }else{
            radioGroupIng.check(R.id.radioUngenieriaNo);
        }
        if(registros.getCtrl_administrativo().equals("1")){
            radioGroupAdm.check(R.id.radio_AdministrativoSi);
            if(registros.getSenializacion_area().equals("1")){radioGroupSeñal.check(R.id.radioEppSi);}else{radioGroupSeñal.check(R.id.radioEppNo);}
            if(registros.getCapacitacion().equals("1")){radioGroupCapac.check(R.id.radioAdminExpoSi);}else{radioGroupCapac.check(R.id.radioAdminExpoNo);}
            if(registros.getMantenimiento_vibracion().equals("1")){radioGroupMante.check(R.id.radioRotacionSi);}else{radioGroupMante.check(R.id.radioRotacionSi);}
            config.asignarAdaptadorYSeleccion(spn_frecuencia, "frecuencia", "nom_frecuencia", detalles.getFrecuencia(), getContext());
        }else{
            radioGroupAdm.check(R.id.radio_AdministrativoNo);
        }

        check_botas.setChecked(detalles.getEpp_botas().equals("1"));
        check_guantes.setChecked(detalles.getEpp_guantes().equals("1"));
        check_casco.setChecked(detalles.getEpp_casco().equals("1"));
        check_proteccionAud.setChecked(detalles.getEpp_orejeras().equals("1"));
        txt_otrosAdmin.setText(detalles.getOtro_epp());

        txt_resulX.setText(detalles.getX());
        txt_resulY.setText(detalles.getY());
        txt_resulZ.setText(detalles.getZ());

    }
    private void Volver(){
        getFragmentManager().popBackStack();// Regresa al Fragment anterior
    }
}