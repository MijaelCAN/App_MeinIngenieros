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
import com.mijael.mein.DAO.DAO_Usuario;
import com.mijael.mein.DAO.DAO_VelocidadAire;
import com.mijael.mein.Entidades.Equipos;
import com.mijael.mein.Entidades.Formatos_Trabajo;
import com.mijael.mein.Entidades.Usuario;
import com.mijael.mein.Entidades.VelocidadAire_Registro;
import com.mijael.mein.Entidades.VelocidadAire_RegistroDetalle;
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

public class VelocidadAireFragment extends Fragment implements FragmentoImagen.ImagePickerListener{
    private boolean cargarImagen = false;
    View rootView;
    TextView tv_nombreUsuario, tv_nomEmpresa;
    String id_plan_trabajo, id_pt_trabajo, id_formato,id_colaborador, nom_Empresa;

    AutoCompleteTextView spn_equipoMedicion;
    Spinner spn_horarioTrabajo,spn_descAreaTrab, spn_tecnicaAcon;
    EditText txt_areaTrab,txt_actRealizada, txt_otroHorario,txt_otroDetalleTecnica,txt_observaciones, txt_temperatura,
    txt_vel1, txt_vel2, txt_vel3, txt_vel4,txt_vel5,txt_vel6,txt_vel7,txt_vel8,txt_vel9,txt_vel10;
    LinearLayout linearOtroHorario, linearOtroDetalle;
    TextView tv_fechaMonitoreo,tv_horaInicioMoni, tv_horaFinalMoni;
    AppCompatButton btnSubirFotoVel;
    FloatingActionButton btn_guardar;
    ExtendedFloatingActionButton btnCancelar;
    ImageView imgVelo;
    Uri uri;

    public VelocidadAireFragment() {
        // Required empty public constructor
    }
    Formatos_Trabajo for_Velocidad;
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
        rootView = inflater.inflate(R.layout.fragment_velocidad_aire,container,false);
        InputDateConfiguration config = new InputDateConfiguration(getActivity(),id_colaborador,nom_Empresa,rootView);
        init(rootView);

        DAO_Equipos equipos = new DAO_Equipos(getActivity());
        List<String> lista_CodEquipos = equipos.obtener_CodEquipos();
        DAO_Usuario usuario = new DAO_Usuario(getActivity());
        Usuario nuevo = usuario.BuscarUsuario(Integer.parseInt(id_colaborador));

        config.ConfigPantalla();
        config.configurarAutoCompleteTextView(spn_equipoMedicion,lista_CodEquipos);
        spn_horarioTrabajo.setAdapter(config.LlenarSpinner("horario_trab_fromato_medicion","desc_horario",getActivity()));
        spn_descAreaTrab.setAdapter(config.LlenarSpinner(new String[]{"Trabajo al aire libre sin carga solar o bajo techo", "Trabajo al aire libre con carga solar"}));
        spn_tecnicaAcon.setAdapter(config.LlenarSpinner(new String[]{"SI", "NO"}));

        config.MostrarCampos(linearOtroHorario,spn_horarioTrabajo);
        config.MostrarCampos(linearOtroDetalle,spn_tecnicaAcon);

        tv_fechaMonitoreo.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {config.showDatePickerDialog(rootView,tv_fechaMonitoreo);}});
        tv_horaInicioMoni.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {config.showTimePickerDialog(rootView,tv_horaInicioMoni);}});
        tv_horaFinalMoni.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {config.showTimePickerDialog(rootView,tv_horaFinalMoni);}});

        btnSubirFotoVel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentoImagen fragmentoImagen = new FragmentoImagen(VelocidadAireFragment.this);
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
                        validar.validarCampoObligatorio(txt_vel1) &&
                        validar.validarCampoObligatorio(txt_vel2) &&
                        validar.validarCampoObligatorio(txt_vel3) &&
                        validar.validarCampoObligatorio(txt_vel4) &&
                        validar.validarCampoObligatorio(txt_vel5) &&
                        validar.validarCampoObligatorio(txt_vel6) &&
                        validar.validarCampoObligatorio(txt_vel7) &&
                        validar.validarCampoObligatorio(txt_vel8) &&
                        validar.validarCampoObligatorio(txt_vel9) &&
                        validar.validarCampoObligatorio(txt_vel10) &&
                        validar.validarCampoObligatorio(txt_temperatura) &&
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
                    if(valorHorarioTrabajo.equals("SI")){valorDetalleTecnica = txt_otroDetalleTecnica.getText().toString();}
                    String valorFechaMoni = tv_fechaMonitoreo.getText().toString();
                    String valorHoraInicioMoni = tv_horaInicioMoni.getText().toString();
                    String valorHoraFinalMoni = tv_horaFinalMoni.getText().toString();
                    String valorVel1= txt_vel1.getText().toString();
                    String valorVel2 = txt_vel2.getText().toString();
                    String valorVel3= txt_vel3.getText().toString();
                    String valorVel4 = txt_vel4.getText().toString();
                    String valorVel5= txt_vel5.getText().toString();
                    String valorVel6 = txt_vel6.getText().toString();
                    String valorVel7= txt_vel7.getText().toString();
                    String valorVel8 = txt_vel8.getText().toString();
                    String valorVel9= txt_vel9.getText().toString();
                    String valorVel10 = txt_vel10.getText().toString();
                    String valorTemperatura = txt_temperatura.getText().toString();
                    String valorObservaciones = txt_observaciones.getText().toString();

                    String fecha_registro = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                    Equipos equipos1 = equipos.Buscar(valorEquipoMedicion);

                    VelocidadAire_Registro cabecera = new VelocidadAire_Registro(
                      -1,
                      "VA-001",
                      "cod_registro()",
                            id_formato,
                            id_plan_trabajo,
                            id_pt_trabajo,
                            String.valueOf(equipos1.getId_equipo_registro()),
                            equipos1.getCod_equipo(),
                            equipos1.getNombre(),
                            equipos1.getSerie(),
                            id_colaborador,
                            nuevo.getUsuario_nombres(),
                            valorHoraInicioMoni,
                            valorHoraFinalMoni,
                            valorAreaTrab,
                            valorActRealizada,
                            valorHorarioTrabajo,
                            valorDescAreaTrab,
                            valorFechaMoni,
                            valorObservaciones,
                            fecha_registro,
                            id_colaborador
                    );

                    VelocidadAire_RegistroDetalle detalle = new VelocidadAire_RegistroDetalle(
                            cabecera.getId(),
                            valorTecnicaAcon,
                            valorDetalleTecnica,
                            valorVel1,
                            valorVel2,
                            valorVel3,
                            valorVel4,
                            valorVel5,
                            valorVel6,
                            valorVel7,
                            valorVel8,
                            valorVel9,
                            valorVel10,
                            valorTemperatura,
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

                        Call<ResponseBody> call1 = service1.insertVelocidadAire(json);//INSERT A VELOCIDAD DEL AIRE
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
                        DAO_VelocidadAire registro = new DAO_VelocidadAire(getActivity());
                        registro.RegistroVelocidad(cabecera);
                        registro.RegistroVelocidad_Detalle(detalle);

                        DAO_FormatosTrabajo dao_fromatosTrabajo = new DAO_FormatosTrabajo(getActivity());
                        for_Velocidad = dao_fromatosTrabajo.Buscar(id_plan_trabajo,id_formato);
                        for_Velocidad.setRealizado(for_Velocidad.getRealizado()+1);
                        for_Velocidad.setPor_realizar(for_Velocidad.getPor_realizar()-1);

                        dao_fromatosTrabajo.actualizarFormatoTrabajo(for_Velocidad);


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

    private void init(View view) {
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
        btnSubirFotoVel= view.findViewById(R.id.btn_subirFotoVelo);

        txt_vel1 = view.findViewById(R.id.txt_vel1);
        txt_vel2 = view.findViewById(R.id.txt_vel2);
        txt_vel3 = view.findViewById(R.id.txt_vel3);
        txt_vel4 = view.findViewById(R.id.txt_vel4);
        txt_vel5 = view.findViewById(R.id.txt_vel5);
        txt_vel6 = view.findViewById(R.id.txt_vel6);
        txt_vel7 = view.findViewById(R.id.txt_vel7);
        txt_vel8 = view.findViewById(R.id.txt_vel8);
        txt_vel9 = view.findViewById(R.id.txt_vel9);
        txt_vel10 = view.findViewById(R.id.txt_vel10);

        txt_temperatura = view.findViewById(R.id.txt_temperatura);
        txt_observaciones = view.findViewById(R.id.txt_observaciones);

        btn_guardar = view.findViewById(R.id.fabGuardar);
        btnCancelar = view.findViewById(R.id.fabCancelar);
    }

    @Override
    public void onImagePicked(Uri imageUri) {
        this.uri = imageUri;
        if (imgVelo != null && imageUri != null) {
            imgVelo.setImageURI(imageUri);
        }
    }
}