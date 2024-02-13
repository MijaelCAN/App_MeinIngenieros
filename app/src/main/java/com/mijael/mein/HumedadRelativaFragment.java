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

public class HumedadRelativaFragment extends Fragment implements FragmentoImagen.ImagePickerListener{
    private boolean cargarImagen = false;
    View rootView;
    TextView tv_nombreUsuario, tv_nomEmpresa;
    String id_plan_trabajo, id_pt_trabajo, id_formato,id_colaborador, nom_Empresa;
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

        config.ConfigPantalla();

        config.configurarAutoCompleteTextView(spn_equipoMedicion,lista_CodEquipos);
        spn_horarioTrabajo.setAdapter(config.LlenarSpinner("horario_trab_fromato_medicion","desc_horario",getActivity()));
        spn_descAreaTrab.setAdapter(config.LlenarSpinner(new String[]{"Trabajo al aire libre sin carga solar o bajo techo", "Trabajo al aire libre con carga solar"}));
        //spn_humedadRelativaMax.setAdapter(config.LlenarSpinner("humedad_rel_formato_medicion","valor_humedad",getActivity()));
        //spn_humedadRelativaMin.setAdapter(config.LlenarSpinner("humedad_rel_formato_medicion","valor_humedad",getActivity()));
        spn_tecnicaAcon.setAdapter(config.LlenarSpinner(new String[]{"SI", "NO"}));
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
                if(
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
                        validar.validarCampoObligatorio(txt_humedadRelativaMin) &&
                        validar.validarCampoObligatorio(txt_observaciones)
                ){
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

                    String fecha_registro = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                    Equipos equipos1 = equipos.Buscar(valorEquipoMedicion);

                    ArrayList<HashMap<String, String>> resultList = dao_registroFormatos.getListCantidadFormatoId(Integer.parseInt(id_pt_trabajo));
                    int total_registros = dao_registroFormatos.get_cant_formato_medicion();
                    String cod_formato = config.GenerarCodigoFormato(Integer.parseInt(id_formato),resultList.size());
                    String cod_registro = config.generarCodigoRegistro(total_registros);

                    String valorRutaFoto = uri.getEncodedPath();
                    int id_plan_formato_reg = dao_registroFormatos.getRecordIdByPosition();

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
                            (id_plan_formato_reg+1),
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
                        DAO_RegistroHumedadRelativa registro = new DAO_RegistroHumedadRelativa(getActivity());
                        registro.RegistroHumedad(cabecera);
                        registro.RegistroHumedad_Detalle(detalle);

                        DAO_FormatosTrabajo dao_fromatosTrabajo = new DAO_FormatosTrabajo(getActivity());
                        for_Humedad = dao_fromatosTrabajo.Buscar(id_plan_trabajo,id_formato);
                        for_Humedad.setRealizado(for_Humedad.getRealizado()+1);
                        for_Humedad.setPor_realizar(for_Humedad.getPor_realizar()-1);

                        dao_fromatosTrabajo.actualizarFormatoTrabajo(for_Humedad);


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
}