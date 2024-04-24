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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mijael.mein.DAO.DAO_Equipos;
import com.mijael.mein.DAO.DAO_FormatosTrabajo;
import com.mijael.mein.DAO.DAO_RegistroFormatos;
import com.mijael.mein.DAO.DAO_RegistroHumedadRelativa;
import com.mijael.mein.DAO.DAO_Usuario;
import com.mijael.mein.Entidades.Equipos;
import com.mijael.mein.Entidades.Formatos_Trabajo;
import com.mijael.mein.Entidades.HumedadRelativa_Registro;
import com.mijael.mein.Entidades.HumedadRelativa_RegistroDetalle;
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

public class HumedadRelativaFragment extends Fragment implements FragmentoImagen.ImagePickerListener{
    private boolean cargarImagen = false;
    View rootView;
    TextView tv_nombreUsuario, tv_nomEmpresa;
    String id_plan_trabajo, id_pt_trabajo, id_formato,id_colaborador, nom_Empresa;
    String[] arrayDesc, arrayYN;
    AutoCompleteTextView spn_equipoMedicion;
    Spinner spn_horarioTrabajo,spn_descAreaTrab, spn_tecnicaAcon;
    EditText txt_areaTrab,txt_actRealizada, txt_otroHorario,txt_otroDetalleTecnica,txt_observaciones, txt_humedadRelativaMax, txt_humedadRelativaMin;
    LinearLayout linearOtroHorario, linearOtroDetalle;
    TextView tv_fechaMonitoreo,tv_horaInicioMoni, tv_horaFinalMoni;
    AppCompatButton btnSubirFotoHumedad;
    FloatingActionButton btn_guardar;
    ExtendedFloatingActionButton btnCancelar;
    ImageView imgHumedad;
    Uri uri;
    DAO_RegistroFormatos dao_registroFormatos;
    RegistroFormatos registros;
    RegistroFormatos_Detalle detalles;
    public HumedadRelativaFragment() {
        // Required empty public constructor
        dao_registroFormatos = new DAO_RegistroFormatos(getContext());
    }
    Formatos_Trabajo for_Humedad;
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
        rootView = inflater.inflate(R.layout.fragment_humedad_relativa,container,false);
        config = new InputDateConfiguration(getActivity(),id_colaborador,nom_Empresa,rootView);
        init(rootView);

        DAO_Equipos equipos = new DAO_Equipos(getActivity());
        List<String> lista_CodEquipos = equipos.obtener_CodEquipos();
        DAO_Usuario usuario = new DAO_Usuario(getActivity());
        Usuario nuevo = usuario.BuscarUsuario(Integer.parseInt(id_colaborador));

        arrayDesc = new String[]{"Trabajo al aire libre sin carga solar o bajo techo", "Trabajo al aire libre con carga solar"};
        arrayYN = new String[]{"SI", "NO"};

        config.ConfigPantalla();
        config.configurarAutoCompleteTextView(spn_equipoMedicion,lista_CodEquipos);
        spn_horarioTrabajo.setAdapter(config.LlenarSpinner("horario_trab_fromato_medicion","desc_horario",getActivity()));
        spn_descAreaTrab.setAdapter(config.LlenarSpinner(arrayDesc));
        //spn_humedadRelativaMax.setAdapter(config.LlenarSpinner("humedad_rel_formato_medicion","valor_humedad",getActivity()));
        //spn_humedadRelativaMin.setAdapter(config.LlenarSpinner("humedad_rel_formato_medicion","valor_humedad",getActivity()));
        spn_tecnicaAcon.setAdapter(config.LlenarSpinner(arrayYN));
        tv_fechaMonitoreo.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {config.showDatePickerDialog(rootView,tv_fechaMonitoreo);}});
        tv_horaInicioMoni.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {config.showTimePickerDialog(rootView,tv_horaInicioMoni);}});
        tv_horaFinalMoni.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {config.showTimePickerDialog(rootView,tv_horaFinalMoni);}});

        config.MostrarCampos(linearOtroHorario,spn_horarioTrabajo);
        config.MostrarCampos(linearOtroDetalle,spn_tecnicaAcon);
        btnSubirFotoHumedad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentoImagen fragmentoImagen = new FragmentoImagen(HumedadRelativaFragment.this);
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
                        validar.validarCampoObligatorio(spn_equipoMedicion) &&
                        validar.validarCampoObligatorio(txt_areaTrab) &&
                        validar.validarCampoObligatorio(txt_actRealizada) &&
                        validar.validarCampoObligatorio(spn_horarioTrabajo) &&
                        validar.validarCampoObligatorio(spn_descAreaTrab) &&
                        validar.validarCampoObligatorio(spn_tecnicaAcon) &&
                        validar.validarCampoObligatorio(tv_fechaMonitoreo) &&
                        validar.validarCampoObligatorio(tv_horaInicioMoni) &&
                        validar.validarCampoObligatorio(tv_horaFinalMoni) &&

                        validar.validarCampoObligatorio(txt_humedadRelativaMax) &&
                        validar.validarCampoObligatorio(txt_humedadRelativaMin)
                        //validar.validarCampoObligatorio(txt_observaciones)
                ){*/
                    String valorEquipoMedicion = spn_equipoMedicion.getText().toString();
                    String valorAreaTrab = txt_areaTrab.getText().toString();
                    String valorActRealizada = txt_actRealizada.getText().toString();
                    String valorHorarioTrabajo = spn_horarioTrabajo.getSelectedItem().toString();
                    if(valorHorarioTrabajo.equals("OTRO")) valorHorarioTrabajo = txt_otroHorario.getText().toString();
                    String valorDescAreaTrab = spn_descAreaTrab.getSelectedItem().toString();
                    String valorTecnicaAcon = spn_tecnicaAcon.getSelectedItem().toString();
                    String valorDetalleTecnica ="";
                    if(valorTecnicaAcon.equals("SI")){valorDetalleTecnica = txt_otroDetalleTecnica.getText().toString();}

                    String f = tv_fechaMonitoreo.getText().toString();
                    String valorFechaMoni = config.convertirFecha(f);
                    //String valorFechaMoni = tv_fechaMonitoreo.getText().toString();

                    String valorHoraInicioMoni = tv_horaInicioMoni.getText().toString();
                    String valorHoraFinalMoni = tv_horaFinalMoni.getText().toString();
                    String valorHumedadMax= txt_humedadRelativaMax.getText().toString();
                    String valorHumedadMin = txt_humedadRelativaMin.getText().toString();
                    String valorObservaciones = txt_observaciones.getText().toString();

                    Equipos equipos1 = equipos.Buscar(valorEquipoMedicion);

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
                    HumedadRelativa_Registro cabecera = new HumedadRelativa_Registro(
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
                            valorHoraInicioMoni,
                            valorHoraFinalMoni,
                            valorAreaTrab,
                            valorActRealizada,
                            valorHorarioTrabajo,
                            valorDescAreaTrab,
                            valorFechaMoni,
                            valorObservaciones,
                            fecha_registro,
                            id_colaborador,
                            valorRutaFoto
                    );

                    HumedadRelativa_RegistroDetalle detalle = new HumedadRelativa_RegistroDetalle(
                            id_plan_formato_reg,
                            valorTecnicaAcon,
                            valorDetalleTecnica,
                            valorHumedadMax,
                            valorHumedadMin,
                            fecha_registro,
                            id_colaborador

                    );

                    if(config.isOnline()){
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("https://test.meiningenieros.pe/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        DosimetriaService service1 = retrofit.create(DosimetriaService.class);
                        Gson gson = new Gson();

                        // Crear un objeto JSON principal
                        JsonObject jsonObject = new JsonObject();

                        JsonObject registroJson = gson.toJsonTree(cabecera).getAsJsonObject();
                        jsonObject.add("cabecera", registroJson);

                        JsonObject detalleJson = gson.toJsonTree(detalle).getAsJsonObject();
                        jsonObject.add("detalle", detalleJson);


                        String cadenaJson = gson.toJson(jsonObject);
                        RequestBody json = RequestBody.create(MediaType.parse("application/json"), cadenaJson);

                        Call<ResponseBody> call1 = service1.insertHumedadRelativa(json);//INSERT A HUMEDAD RELATIVA
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
                        DAO_RegistroHumedadRelativa nuevoRegistro = new DAO_RegistroHumedadRelativa(getActivity());
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Guardar formulario");
                        builder.setMessage("¿Deseas seguir llenando el formulario o terminar?");

                        builder.setPositiveButton("Seguir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(registros == null){
                                    nuevoRegistro.RegistroHumedad(cabecera);
                                    nuevoRegistro.RegistroHumedad_Detalle(detalle);
                                    DAO_FormatosTrabajo dao_fromatosTrabajo = new DAO_FormatosTrabajo(getActivity());
                                    for_Humedad = dao_fromatosTrabajo.Buscar(id_plan_trabajo,id_formato);
                                    for_Humedad.setRealizado(for_Humedad.getRealizado()+1);
                                    for_Humedad.setPor_realizar(for_Humedad.getPor_realizar()-1);
                                    dao_fromatosTrabajo.actualizarFormatoTrabajo(for_Humedad);
                                }else{
                                    nuevoRegistro.ActualizarHumedad(cabecera,registros.getId_plan_trabajo_formato_reg());
                                    nuevoRegistro.ActualizarHumedad_Detalle(detalle,detalles.getId_formato_reg_detalle());
                                }
                            }
                        });
                        builder.setNegativeButton("Terminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(registros == null){
                                    nuevoRegistro.RegistroHumedad(cabecera);
                                    nuevoRegistro.RegistroHumedad_Detalle(detalle);
                                    DAO_FormatosTrabajo dao_fromatosTrabajo = new DAO_FormatosTrabajo(getActivity());
                                    for_Humedad = dao_fromatosTrabajo.Buscar(id_plan_trabajo,id_formato);
                                    for_Humedad.setRealizado(for_Humedad.getRealizado()+1);
                                    for_Humedad.setPor_realizar(for_Humedad.getPor_realizar()-1);
                                    dao_fromatosTrabajo.actualizarFormatoTrabajo(for_Humedad);
                                }else{
                                    nuevoRegistro.ActualizarHumedad(cabecera,registros.getId_plan_trabajo_formato_reg());
                                    nuevoRegistro.ActualizarHumedad_Detalle(detalle,detalles.getId_formato_reg_detalle());
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

        spn_equipoMedicion = view.findViewById(R.id.tv_equipoUtilizado);
        txt_areaTrab = view.findViewById(R.id.txt_areaTrabajo);
        txt_actRealizada = view.findViewById(R.id.txt_aRealizada);
        spn_horarioTrabajo = view.findViewById(R.id.cbx_horarioTrabajo);
        linearOtroHorario = view.findViewById(R.id.linearOtroHorario);
        txt_otroHorario = view.findViewById(R.id.txt_otroHorario);
        spn_descAreaTrab = view.findViewById(R.id.cbx_descAreaTrab);
        spn_tecnicaAcon = view.findViewById(R.id.cbx_tecnicaAcon);
        linearOtroDetalle = view.findViewById(R.id.linearOtroDetalle);
        txt_otroDetalleTecnica = view.findViewById(R.id.txt_otroDetalleTecnica);
        tv_fechaMonitoreo = view.findViewById(R.id.tv_fechaMonitoreo);
        tv_horaInicioMoni = view.findViewById(R.id.tv_horaInicial);
        tv_horaFinalMoni = view.findViewById(R.id.tv_horaFinal);
        txt_humedadRelativaMax = view.findViewById(R.id.txt_humedadRelativaMax);
        txt_humedadRelativaMin = view.findViewById(R.id.txt_humedadRelativaMin);
        btnSubirFotoHumedad= view.findViewById(R.id.btn_subirFotoHumedad);
        txt_observaciones = view.findViewById(R.id.txt_observaciones);

        btn_guardar = view.findViewById(R.id.fabGuardar);
        btnCancelar = view.findViewById(R.id.fabCancelar);

        imgHumedad = view.findViewById(R.id.img_Humedad);
    }
    @Override
    public void onImagePicked(Uri imageUri) {
        this.uri = imageUri;
        if (imgHumedad != null && imageUri != null) {
            imgHumedad.setImageURI(imageUri);
            /*File imageFile = new File(imageUri.getEncodedPath());
            config.uploadImage(imageFile);*/
        }
    }
    private void EditarCampos(){
        spn_equipoMedicion.setText(registros.getCod_equipo1());
        spn_equipoMedicion.setText(registros.getCod_equipo1());
        txt_areaTrab.setText(registros.getArea_trabajo());
        txt_actRealizada.setText(registros.getActividades_realizadas());
        config.asignarAdaptadorYSeleccion(spn_horarioTrabajo, "horario_trab_fromato_medicion", "desc_horario", registros.getHora_trabajo(), getContext());
        if (spn_horarioTrabajo.getSelectedItem().equals("OTRO")) {
            txt_otroHorario.setText(registros.getHora_trabajo());
        }
        // DESCRIPCCION DEL AREA DE TRABAJO
        String valorDesc = registros.getDesc_area_trabajo();
        int indice = Arrays.asList(arrayDesc).indexOf(valorDesc);
        spn_descAreaTrab.setSelection(indice+1);


        // TECNICA DE ACONDICIONAMIENTO
        String valorTec = detalles.getTecnica_acondaire();
        if(!valorTec.isEmpty() || valorTec!=null){valorTec.toUpperCase();}
        int indice2 = Arrays.asList(arrayYN).indexOf(valorTec);
        spn_tecnicaAcon.setSelection(indice2+1);
        if(valorTec.equals("SI")){
            txt_otroDetalleTecnica.setText(detalles.getDetalle_tecnica_acondaire());
        }
        Log.e("AREATRABAJO",registros.getDesc_area_trabajo() + "-" + indice);
        Log.e("TECNICA",detalles.getTecnica_acondaire() + "-" + indice2);
        Log.e("TECNICA-detalle",detalles.getDetalle_tecnica_acondaire());


        String fecha = "";
        if (!registros.getFec_monitoreo().isEmpty()) {
            String[] fec = registros.getFec_monitoreo().split(" ");
            String[] nueva_fec = fec[0].split("-");
            fecha = nueva_fec[0] + "/" + nueva_fec[1] + "/" + nueva_fec[2];
        }
        tv_fechaMonitoreo.setText(fecha);
        tv_horaInicioMoni.setText(registros.getHora_inicial());
        tv_horaFinalMoni.setText(registros.getHora_final());
        txt_humedadRelativaMax.setText(detalles.getH_relativa());
        txt_humedadRelativaMin.setText(detalles.getH_relativa_2());
        imgHumedad.setImageURI(Uri.parse(registros.getRuta_foto()));
        txt_observaciones.setText(registros.getObservacion());
    }
    private void Volver(){
        getFragmentManager().popBackStack();// Regresa al Fragment anterior
    }
}