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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.mijael.mein.DAO.DAO_RegistroVibracion;
import com.mijael.mein.DAO.DAO_Usuario;
import com.mijael.mein.DAO.DAO_VelocidadAire;
import com.mijael.mein.Entidades.Equipos;
import com.mijael.mein.Entidades.Formatos_Trabajo;
import com.mijael.mein.Entidades.RadiacionElec_Registro;
import com.mijael.mein.Entidades.RadiacionElect_RegistroDetalle;
import com.mijael.mein.Entidades.RegistroFormatos;
import com.mijael.mein.Entidades.RegistroFormatos_Detalle;
import com.mijael.mein.Entidades.Usuario;
import com.mijael.mein.Extras.FragmentoImagen;
import com.mijael.mein.Extras.InputDateConfiguration;
import com.mijael.mein.Extras.Validaciones;
import com.mijael.mein.HELPER.EquiposSQLiteHelper;
import com.mijael.mein.HELPER.FormatoTrabajoSQLiteHelper;
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


public class RadiacionElectromagneticaFragment extends Fragment implements FragmentoImagen.ImagePickerListener{
    private boolean cargarImagen = false;
    View rootView;
    TextView tv_nombreUsuario, tv_nomEmpresa;
    String id_plan_trabajo, id_pt_trabajo, id_formato,id_colaborador, nom_Empresa;
    // Declaraciones de variables de Android en Java agrupadas por tipo en una sola línea
    String[] arrayYN,arrayDesc;
    AutoCompleteTextView spn_equipoMedicion;
    TextView tv_horaVerificacion, tv_fechaMonitoreo, tv_horaInicioMoni, tv_horaFinalMoni;
    RadioGroup radioGroupVerificacion;
    AppCompatButton btn_subirFotoElectro,btn_BuscarDni;
    FloatingActionButton btn_guardar;
    ExtendedFloatingActionButton btnCancelar;
    EditText txt_timeMedicion, txt_numDoc, txt_nomTrabajador, txt_edad, txt_areaTrabajo, txt_puestoTrabajo, txt_aRealizada,
            txt_jornadaTrabajo, txt_fuenteGen, txt_timeExpo, txt_vestimenta, txt_controlIng, txt_controlAdm, txt_Epps,
            txt_x0, txt_x2, txt_x4, txt_x6, txt_y0, txt_y2, txt_y4, txt_y6, txt_z0, txt_z2, txt_z4, txt_z6;
    Spinner spn_tipoDoc, spn_horarioTrabajo, spn_regimen, spn_horarioRefrigerio, spn_descTrabajo;
    ImageView imgRadiacion;
    Uri uri;

    LinearLayout linearOtroHorario, linearOtroRegimen, linearOtroRefrigerio,linearBuscarDni;
    EditText txt_otroHorario, txt_otroRegimen, txt_otroRefrigerio;
    Formatos_Trabajo for_Electro;
    Validaciones validar = new Validaciones();
    InputDateConfiguration config;
    DAO_RegistroFormatos dao_registroFormatos;
    RegistroFormatos registros;
    RegistroFormatos_Detalle detalles;
    public RadiacionElectromagneticaFragment() {
        // Required empty public constructor
        dao_registroFormatos = new DAO_RegistroFormatos(getActivity());
    }


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
        rootView = inflater.inflate(R.layout.fragment_radiacion_electromagnetica, container, false);
        config = new InputDateConfiguration(getActivity(),id_colaborador,nom_Empresa,rootView);
        init(rootView);
        config.ConfigPantalla();

        DAO_Equipos equipos = new DAO_Equipos(getActivity());
        List<String> lista_CodEquipos = equipos.obtener_CodEquipos();
        DAO_Usuario usuario = new DAO_Usuario(getActivity());
        Usuario nuevo = usuario.BuscarUsuario(Integer.parseInt(id_colaborador));
        arrayYN = new String[]{"DNI", "CE"};
        arrayDesc = new String[]{"Trabajo al aire libre sin carga solar o bajo techo", "Trabajo al aire libre con carga solar"};


        config.configurarAutoCompleteTextView(spn_equipoMedicion,lista_CodEquipos);

        tv_horaVerificacion.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {config.showTimePickerDialog(rootView,tv_horaVerificacion);}});
        tv_fechaMonitoreo.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {config.showDatePickerDialog(rootView,tv_fechaMonitoreo);}});
        tv_horaInicioMoni.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {config.showTimePickerDialog(rootView,tv_horaInicioMoni);}});
        tv_horaFinalMoni.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {config.showTimePickerDialog(rootView,tv_horaFinalMoni);}});

        btn_subirFotoElectro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentoImagen fragmentoImagen = new FragmentoImagen(RadiacionElectromagneticaFragment.this);
                fragmentoImagen.show(getActivity().getSupportFragmentManager(), "imagePicker");
                cargarImagen = true;
            }
        });

        spn_tipoDoc.setAdapter(config.LlenarSpinner(arrayYN));
        spn_descTrabajo.setAdapter(config.LlenarSpinner(arrayDesc));
        spn_horarioTrabajo.setAdapter(config.LlenarSpinner("horario_trab_fromato_medicion","desc_horario",getActivity()));
        spn_regimen.setAdapter(config.LlenarSpinner("regimen_formato_medicion","nom_regimen",getActivity()));
        spn_horarioRefrigerio.setAdapter(config.LlenarSpinner("horario_refrig_formato_medicion","nom_horario",getActivity()));

        config.MostrarCampos(linearOtroHorario,spn_horarioTrabajo);
        config.MostrarCampos(linearOtroRegimen,spn_regimen);
        config.MostrarCampos(linearOtroRefrigerio,spn_horarioRefrigerio);


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
                /*if(
                        validar.validarCampoObligatorio(spn_equipoMedicion) &&
                        validar.validarCampoObligatorio(tv_horaVerificacion) &&
                        validar.validarImagen(cargarImagen,getActivity()) &&
                        validar.validarCampoObligatorio(tv_fechaMonitoreo) &&
                        validar.validarCampoObligatorio(tv_fechaMonitoreo) &&
                        validar.validarCampoObligatorio(tv_horaInicioMoni) &&
                        validar.validarCampoObligatorio(tv_horaFinalMoni) &&
                        validar.validarCampoObligatorio(txt_timeMedicion) &&
                        validar.validarCampoObligatorio(spn_tipoDoc) &&
                        validar.validarCampoObligatorio(txt_numDoc) &&
                        validar.validarCampoObligatorio(txt_nomTrabajador) &&
                        validar.validarCampoObligatorio(txt_edad) &&
                        validar.validarCampoObligatorio(txt_areaTrabajo) &&
                        validar.validarCampoObligatorio(txt_puestoTrabajo) &&
                        validar.validarCampoObligatorio(txt_aRealizada) &&
                        validar.validarCampoObligatorio(spn_horarioTrabajo) &&
                        validar.validarCampoObligatorio(spn_regimen) &&
                        validar.validarCampoObligatorio(spn_horarioRefrigerio) &&
                        validar.validarCampoObligatorio(txt_jornadaTrabajo) &&
                        validar.validarCampoObligatorio(txt_fuenteGen) &&
                        validar.validarCampoObligatorio(txt_timeExpo) &&
                        validar.validarCampoObligatorio(spn_descTrabajo) &&
                        validar.validarCampoObligatorio(txt_vestimenta) &&
                        validar.validarCampoObligatorio(txt_controlIng) &&
                        validar.validarCampoObligatorio(txt_controlAdm) &&
                        validar.validarCampoObligatorio(txt_Epps) &&
                        validar.validarCampoObligatorio(txt_x0) &&
                        validar.validarCampoObligatorio(txt_x2) &&
                        validar.validarCampoObligatorio(txt_x4) &&
                        validar.validarCampoObligatorio(txt_x6) &&
                        validar.validarCampoObligatorio(txt_y0) &&
                        validar.validarCampoObligatorio(txt_y2) &&
                        validar.validarCampoObligatorio(txt_y4) &&
                        validar.validarCampoObligatorio(txt_y6) &&
                        validar.validarCampoObligatorio(txt_z0) &&
                        validar.validarCampoObligatorio(txt_z2) &&
                        validar.validarCampoObligatorio(txt_z4) &&
                        validar.validarCampoObligatorio(txt_z6)
                ){*/
                    String valorEquipoMed = spn_equipoMedicion.getText().toString();
                    String valorHoraVerificacion = tv_horaVerificacion.getText().toString();
                    int valorGroupVerifi = validar.getValor2(radioGroupVerificacion,rootView);

                    String f = tv_fechaMonitoreo.getText().toString();
                    String valorFechaMoni = config.convertirFecha(f);
                    //String valorFechaMoni = tv_fechaMonitoreo.getText().toString();

                    String valorHoraInicioMoni = tv_horaInicioMoni.getText().toString();
                    String valorHoraFinalMoni = tv_horaFinalMoni.getText().toString();
                    String valorTimeMedi = txt_timeMedicion.getText().toString();
                    String valorTipoDoc = spn_tipoDoc.getSelectedItem().toString();
                    String valorNumDoc = txt_numDoc.getText().toString();
                    String valorNomTrabajador = txt_nomTrabajador.getText().toString();
                    String valorEdad = txt_edad.getText().toString();
                    String valorAreaTrab = txt_areaTrabajo.getText().toString();
                    String valorPuestoTrab = txt_puestoTrabajo.getText().toString();
                    String valorRealizadas = txt_aRealizada.getText().toString();
                    String valorHorarioTrab = spn_horarioTrabajo.getSelectedItem().toString();
                    String valorRegimen = spn_regimen.getSelectedItem().toString();
                    String valorRefrigerio = spn_horarioRefrigerio.getSelectedItem().toString();
                    String valorJornada = txt_jornadaTrabajo.getText().toString();
                    String valorFuenteGen = txt_fuenteGen.getText().toString();
                    String valorTimeExpo = txt_timeExpo.getText().toString();
                    String valorDescTrabaj = spn_descTrabajo.getSelectedItem().toString();
                    String valorVestimenta = txt_vestimenta.getText().toString();
                    String valorControlIng = txt_controlIng.getText().toString();
                    String valorControlAdm = txt_controlAdm.getText().toString();
                    String valorEpps = txt_Epps.getText().toString();
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

                    if(valorHorarioTrab.equals("OTRO")) valorHorarioTrab = txt_otroHorario.getText().toString();
                    if(valorRegimen.equals("OTRO")) valorRegimen = txt_otroRegimen.getText().toString();
                    if(valorRefrigerio.equals("OTRO")) valorRefrigerio = txt_otroRefrigerio.getText().toString();

                    Equipos equipos1 = equipos.Buscar(valorEquipoMed);

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

                    RadiacionElec_Registro cabecera = new RadiacionElec_Registro(
                            -1,
                            cod_formato,
                            cod_registro,
                            id_formato,
                            id_plan_trabajo,
                            id_pt_trabajo,
                            String.valueOf(equipos1.getId_equipo_registro()),
                            equipos1.getCodigo(),
                            equipos1.getNombre(),
                            equipos1.getSerie(),
                            id_colaborador,
                            nuevo.getUsuario_nombres()+ " " +nuevo.getUsuario_apater()+" "+nuevo.getUsuario_amater(),
                            valorHoraVerificacion,
                            "" +valorGroupVerifi,
                            valorFechaMoni,
                            valorHoraInicioMoni,
                            valorHoraFinalMoni,
                            valorTipoDoc,
                            valorNumDoc,
                            valorNomTrabajador,
                            valorPuestoTrab,
                            valorAreaTrab,
                            valorRealizadas,
                            valorEdad,
                            valorHorarioTrab,
                            valorRefrigerio,
                            valorRegimen,
                            valorJornada,
                            valorTimeMedi,
                            valorTimeExpo,
                            valorDescTrabaj,
                            valorControlIng,
                            valorControlAdm,
                            valorEpps,
                            fecha_registro,
                            id_colaborador,
                            valorRutaFoto
                    );
                    RadiacionElect_RegistroDetalle detalle = new RadiacionElect_RegistroDetalle(
                            id_plan_formato_reg,
                            valorFuenteGen,
                            valorVestimenta,
                            valor_x0,
                            valor_x2,
                            valor_x4,
                            valor_x6,
                            valor_y0,
                            valor_y2,
                            valor_y4,
                            valor_y6,
                            valor_z0,
                            valor_z2,
                            valor_z4,
                            valor_z6,
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

                        Call<ResponseBody> call1 = service1.insertRadiacionElectro(json);//INSERT A RADIACION ELECTROMAGNETICA
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
                        DAO_RegistroRadiacion nuevoRegistro = new DAO_RegistroRadiacion(getActivity());
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Guardar formulario");
                        builder.setMessage("¿Deseas seguir llenando el formulario o terminar?");

                        builder.setPositiveButton("Seguir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(registros == null){
                                    nuevoRegistro.RegistroRadiacion(cabecera);
                                    nuevoRegistro.RegistrarRadiacionDetalle(detalle);
                                    DAO_FormatosTrabajo dao_fromatosTrabajo = new DAO_FormatosTrabajo(getActivity());
                                    for_Electro = dao_fromatosTrabajo.Buscar(id_plan_trabajo,id_formato);
                                    for_Electro.setRealizado(for_Electro.getRealizado()+1);
                                    for_Electro.setPor_realizar(for_Electro.getPor_realizar()-1);
                                    dao_fromatosTrabajo.actualizarFormatoTrabajo(for_Electro);

                                }else{
                                    nuevoRegistro.ActualizarRadiacion(cabecera,registros.getId_plan_trabajo_formato_reg());
                                    nuevoRegistro.ActualizarRadiacionDetalle(detalle,detalles.getId_formato_reg_detalle());
                                }
                            }
                        });
                        builder.setNegativeButton("Terminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(registros == null){
                                    nuevoRegistro.RegistroRadiacion(cabecera);
                                    nuevoRegistro.RegistrarRadiacionDetalle(detalle);
                                    DAO_FormatosTrabajo dao_fromatosTrabajo = new DAO_FormatosTrabajo(getActivity());
                                    for_Electro = dao_fromatosTrabajo.Buscar(id_plan_trabajo,id_formato);
                                    for_Electro.setRealizado(for_Electro.getRealizado()+1);
                                    for_Electro.setPor_realizar(for_Electro.getPor_realizar()-1);
                                    dao_fromatosTrabajo.actualizarFormatoTrabajo(for_Electro);

                                }else{
                                    nuevoRegistro.ActualizarRadiacion(cabecera,registros.getId_plan_trabajo_formato_reg());
                                    nuevoRegistro.ActualizarRadiacionDetalle(detalle,detalles.getId_formato_reg_detalle());
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

    private void init(View view) {
        tv_nombreUsuario = view.findViewById(R.id.tv_nomUsuario);
        tv_nomEmpresa = view.findViewById(R.id.tv_nomEmpresa);


        // Inicialización de variables mediante findViewById en Android
        spn_equipoMedicion = view.findViewById(R.id.spn_equipoMedicion);
        tv_horaVerificacion = view.findViewById(R.id.tv_horaVerificacion);
        tv_fechaMonitoreo = view.findViewById(R.id.tv_fechaMoni);
        tv_horaInicioMoni = view.findViewById(R.id.tv_horaInicial);
        tv_horaFinalMoni = view.findViewById(R.id.tv_horaFinal);
        radioGroupVerificacion = view.findViewById(R.id.radioGroupVerificacion);
        btn_subirFotoElectro = view.findViewById(R.id.btn_subirFotoElectro);
        imgRadiacion = view.findViewById(R.id.img_Radiacion);
        txt_timeMedicion = view.findViewById(R.id.txt_timeMedicion);
        spn_tipoDoc = view.findViewById(R.id.spn_tipoDoc);
        txt_numDoc = view.findViewById(R.id.txt_numDoc);
        txt_nomTrabajador = view.findViewById(R.id.txt_nomTrabajador);
        txt_edad = view.findViewById(R.id.txt_edad);
        txt_areaTrabajo = view.findViewById(R.id.txt_areaTrabajo);
        txt_puestoTrabajo = view.findViewById(R.id.txt_puestoTrabajo);
        txt_aRealizada = view.findViewById(R.id.txt_aRealizada);
        spn_horarioTrabajo = view.findViewById(R.id.cbx_horarioTrabajo);
        spn_regimen = view.findViewById(R.id.cbx_regimen);
        spn_horarioRefrigerio = view.findViewById(R.id.cbx_refrigerio);
        txt_jornadaTrabajo = view.findViewById(R.id.txt_jornada);
        txt_fuenteGen = view.findViewById(R.id.txt_fuenteGen);
        txt_timeExpo = view.findViewById(R.id.txt_timeExpo);
        spn_descTrabajo = view.findViewById(R.id.spn_descTrabajo);
        txt_vestimenta = view.findViewById(R.id.txt_vestimenta);
        txt_controlIng = view.findViewById(R.id.txt_controlIng);
        txt_controlAdm = view.findViewById(R.id.txt_controlAdm);
        txt_Epps = view.findViewById(R.id.txt_epps);
        txt_x0 = view.findViewById(R.id.txt_x_0);
        txt_x2 = view.findViewById(R.id.txt_x_2);
        txt_x4 = view.findViewById(R.id.txt_x_4);
        txt_x6 = view.findViewById(R.id.txt_x_6);
        txt_y0 = view.findViewById(R.id.txt_y_0);
        txt_y2 = view.findViewById(R.id.txt_y_2);
        txt_y4 = view.findViewById(R.id.txt_y_4);
        txt_y6 = view.findViewById(R.id.txt_y_6);
        txt_z0 = view.findViewById(R.id.txt_z_0);
        txt_z2 = view.findViewById(R.id.txt_z_2);
        txt_z4 = view.findViewById(R.id.txt_z_4);
        txt_z6 = view.findViewById(R.id.txt_z_6);

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
    private void EditarCampos(){
        spn_equipoMedicion.setText(registros.getCod_equipo1());
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
        txt_timeMedicion.setText(registros.getTiempo_medicion());
        int indice1 = Arrays.asList(arrayYN).indexOf(registros.getTipo_doc_trabajador());
        spn_tipoDoc.setSelection(indice1 + 1);
        txt_numDoc.setText(registros.getNum_doc_trabajador());
        txt_nomTrabajador.setText(registros.getNom_trabajador());
        txt_edad.setText(String.valueOf(registros.getEdad_trabajador()));
        txt_areaTrabajo.setText(registros.getArea_trabajo());
        txt_puestoTrabajo.setText(registros.getPuesto_trabajador());
        txt_aRealizada.setText(registros.getActividades_realizadas());
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
        txt_jornadaTrabajo.setText(registros.getJornada());
        txt_fuenteGen.setText(detalles.getFuente_generadora());
        txt_timeExpo.setText(registros.getTiempo_exposicion());
        String valorDesc = registros.getDesc_area_trabajo();
        int indice3 = Arrays.asList(arrayDesc).indexOf(valorDesc);
        spn_descTrabajo.setSelection(indice3 + 1);
        txt_vestimenta.setText(detalles.getVestimenta_personal());
        txt_controlIng.setText(registros.getNom_ctrl_ingenieria());
        txt_controlAdm.setText(registros.getNom_ctrl_admin());
        txt_Epps.setText(registros.getNom_epp());
        txt_x0.setText(detalles.getX());
        txt_x2.setText(detalles.getX2());
        txt_x4.setText(detalles.getX3());
        txt_x6.setText(detalles.getX4());
        txt_y0.setText(detalles.getY());
        txt_y2.setText(detalles.getY2());
        txt_y4.setText(detalles.getY3());
        txt_y6.setText(detalles.getY4());
        txt_z0.setText(detalles.getZ());
        txt_z2.setText(detalles.getZ2());
        txt_z4.setText(detalles.getZ3());
        txt_z6.setText(detalles.getZ4());

    }
    private void Volver(){
        getFragmentManager().popBackStack();// Regresa al Fragment anterior
    }
}