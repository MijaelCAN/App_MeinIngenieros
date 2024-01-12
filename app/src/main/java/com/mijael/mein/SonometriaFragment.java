package com.mijael.mein;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
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
import com.mijael.mein.DAO.DAO_DatosLocal;
import com.mijael.mein.DAO.DAO_Equipos;
import com.mijael.mein.DAO.DAO_FormatosTrabajo;
import com.mijael.mein.DAO.DAO_RegistroSonometria;
import com.mijael.mein.DAO.DAO_Usuario;
import com.mijael.mein.Entidades.Equipos;
import com.mijael.mein.Entidades.Formatos_Trabajo;
import com.mijael.mein.Entidades.Sonometria_Registro;
import com.mijael.mein.Entidades.Usuario;
import com.mijael.mein.Extras.FragmentoImagen;
import com.mijael.mein.Extras.InputDateConfiguration;
import com.mijael.mein.Extras.Validaciones;
import com.mijael.mein.HELPER.EquiposSQLiteHelper;
import com.mijael.mein.HELPER.FormatoTrabajoSQLiteHelper;
import com.mijael.mein.SERVICIOS.DosimetriaService;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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

public class SonometriaFragment extends Fragment implements FragmentoImagen.ImagePickerListener {
    private boolean calculoRealizado = false;
    private boolean cargarImagen = false;
    int hora,min;
    String id_plan_trabajo, id_pt_trabajo, id_formato,id_colaborador, nom_Empresa;
    EditText  txt_areaTrabajo, txt_actRealizadas, txt_numTrabajadores, txt_fuenteGenRuido, txt_jornada;
    AutoCompleteTextView tv_sonometro, tv_calibrador, tv_anemometro;
    TextView tv_horaCalibracion, tv_mensajeImagen, tv_limitePermisible;
    Spinner cbx_nivel, cbx_variacion, cbx_horario, cbx_aConcentracion;
    TextView tv_fechaMonitoreo, tv_horaInicioMonitoreo, tv_horaFinal, tv_nombreUsuario, tv_nomEmpresa;
    Spinner cbx_tiempoMedicion;
    EditText txt_leq1, txt_lmax1, txt_lmin1, txt_leq2, txt_lmax2, txt_lmin2, txt_leq3, txt_lmax3, txt_lmin3, txt_leq4, txt_lmax4, txt_lmin4, txt_leq5, txt_lmax5, txt_lmin5;
    AppCompatButton btn_calcularMedicion;
    TextView tv_resLmin, tv_resLmax, tv_resLeq;
    AppCompatButton btn_subirFotoSono;
    ImageView imagen_sono;
    CardView card_ingenier, card_adminis, card_tapones, card_orejeras;
    RadioGroup radioGroupIng, radioGroupAdmin, radioGroupRiesgos, radioGroupPresionSono, radioGroupEppOblig, radioGroup_Aislante, radioGroup_Cabinas,
            radioGroupTimeExpo, radioGroupRotacion, radioGroupTapones, radioGroupOrej;
    RadioButton radioIngSi, radioAdminSi, radioRiesgosSi, radioPresionSi, radioEppSi, radioTimeExpoSi, radioRotacionSi, radioTaponesSi, radioOrejSi, radioaislateSI, radioCabinaSI;
    EditText txt_otroIng, txt_otrosAdmin, txt_observaciones, txt_velViento, txt_humedadRelatva, tv_nrrTapones, tv_nrrOrej;
    Spinner cbx_marcaTapones, cbx_modeloTapones, cbx_marcaOrej, cbx_modeloOrej;
    FloatingActionButton btn_guardar;
    ExtendedFloatingActionButton btn_cancelar;

    LinearLayout linearOtroHorario, linearOtroRegimen, linearOtroRefrigerio, linearOtroMarcaOrej, linearOtroMarcaTapones, linearOtroModeloOrej, linearOtroModeloTapones;
    EditText txt_otroHorario, txt_otroRegimen, txt_otroRefrigerio, txt_otroMarcaOrej, txt_otroMarcaTapones, txt_otroModeloOrej, txt_otroModeloTapones;
    Uri uri;
    private View rootView;
    Formatos_Trabajo for_Sonometria;


    public SonometriaFragment() {
        // Required empty public constructor
    }

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
        rootView = inflater.inflate(R.layout.fragment_sonometria,container,false);
        init(rootView);
        InputDateConfiguration config = new InputDateConfiguration(getActivity(),id_colaborador,nom_Empresa,rootView);

        ConfigPantalla();

        DAO_Usuario usuario = new DAO_Usuario(getActivity());
        Usuario nuevo = usuario.BuscarUsuario(Integer.parseInt(id_colaborador));
        String cadena = nuevo.getUsuario_nombres() + " "+ nuevo.getUsuario_apater();
        tv_nombreUsuario.setText(cadena);
        tv_nomEmpresa.setText(nom_Empresa);

        // LLENAR DATOS A SPINNERS

        DAO_Equipos equipos = new DAO_Equipos(getActivity());
        List<String> lista_CodEquipos = equipos.obtener_CodEquipos();
        configurarAutoCompleteTextView(tv_sonometro,lista_CodEquipos);
        configurarAutoCompleteTextView(tv_calibrador,lista_CodEquipos);
        configurarAutoCompleteTextView(tv_anemometro,lista_CodEquipos);// Fin

        cbx_nivel.setAdapter(LlenarSpinner("nivel_formato_medicion","nom_nivel",getActivity())); // Llenas Spinner (Nivel, Variacion)
        cbx_variacion.setAdapter(LlenarSpinner("variacion_formato_medicion","nom_variacion",getActivity()));
        cbx_horario.setAdapter(LlenarSpinner("horario_trab_fromato_medicion","desc_horario",getActivity()));
        //cbx_jornada.setAdapter(LlenarSpinner("jornada_trab_formato_medicion","valor_jornada",getActivity()));
        cbx_aConcentracion.setAdapter(LlenarSpinner("SI","NO"));
        cbx_tiempoMedicion .setAdapter(LlenarSpinner("15 min","25 min"));
        //cbx_velViento.setAdapter(LlenarSpinner("velocidadViento","valor_velocidad",getActivity()));
        //cbx_humedadRelatva.setAdapter(LlenarSpinner("humedad_rel_formato_medicion","valor_humedad",getActivity()));
        cbx_marcaTapones.setAdapter(LlenarSpinner("marca_formato_medicion","nom_marca",getActivity()));
        cbx_marcaOrej.setAdapter(LlenarSpinner("marca_formato_medicion","nom_marca",getActivity()));
        cbx_modeloOrej.setAdapter(LlenarSpinner("modelo_orejeras","nom_modelo",getActivity()));
        cbx_modeloTapones.setAdapter(LlenarSpinner("modelo_Tapones","nom_modelo",getActivity()));
        //LlenarNrr("modelo_Tapones","nrr",cbx_modeloTapones,tv_nrrTapones);
        //LlenarNrr("modelo_orejeras","nrr",cbx_modeloOrej,tv_nrrOrej);

        config.MostrarCampos(linearOtroHorario,cbx_horario);
        config.MostrarCampos(linearOtroMarcaOrej,cbx_marcaOrej);
        config.MostrarCampos(linearOtroMarcaTapones,cbx_marcaTapones);
        config.llenarNrrYMostrarCampos("modelo_Tapones","nrr",cbx_modeloTapones,tv_nrrTapones,linearOtroModeloTapones);
        config.llenarNrrYMostrarCampos("modelo_orejeras","nrr",cbx_modeloOrej,tv_nrrOrej,linearOtroModeloOrej);
        cbx_aConcentracion.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                String res = !cbx_aConcentracion.getSelectedItem().toString().equals("SI") ? "85" : "65";
                tv_limitePermisible.setText(res);
            }
        });
        cbx_tiempoMedicion.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    //sumarTiempo();
            }
        });
        btn_subirFotoSono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentoImagen fragmentoImagen = new FragmentoImagen(SonometriaFragment.this);
                fragmentoImagen.show(getActivity().getSupportFragmentManager(), "imagePicker");
                cargarImagen = true;
            }
        });

        radioGroupIng.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {@Override public void onCheckedChanged(RadioGroup group, int checkedId) {mostrarOpcionesGone(group,checkedId,card_ingenier,radioIngSi);}});
        radioGroupAdmin.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {@Override public void onCheckedChanged(RadioGroup group, int checkedId) {mostrarOpcionesGone(group,checkedId,card_adminis,radioAdminSi);}});
        radioGroupOrej.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {@Override public void onCheckedChanged(RadioGroup group, int checkedId) {mostrarOpcionesGone(group,checkedId,card_orejeras,radioOrejSi);}});
        radioGroupTapones.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {@Override public void onCheckedChanged(RadioGroup group, int checkedId) {mostrarOpcionesGone(group,checkedId,card_tapones,radioTaponesSi);}});

        tv_horaCalibracion.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {showTimePickerDialog(rootView,tv_horaCalibracion);}});
        tv_horaInicioMonitoreo.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {showTimePickerDialog(rootView,tv_horaInicioMonitoreo);}});
        tv_horaFinal.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {showTimePickerDialog(rootView,tv_horaFinal);}});
        tv_fechaMonitoreo.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {showDatePickerDialog(rootView,tv_fechaMonitoreo);}});

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
                ){
                    calcularMedicion();
                }

            }
        });

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
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
                if(validar.validarCampoObligatorio(tv_sonometro)&&
                    validar.validarCampoObligatorio(tv_calibrador)&&
                    validar.validarCampoObligatorio(tv_anemometro)&&
                    validar.validarCampoObligatorio(tv_horaCalibracion)&&
                    validar.validarCampoObligatorio(cbx_nivel)&&
                        validar.validarCampoObligatorio(cbx_variacion)&&
                        validar.validarCampoObligatorio(txt_areaTrabajo)&&
                        validar.validarCampoObligatorio(txt_actRealizadas)&&
                        validar.validarCampoObligatorio(cbx_horario)&&
                        validar.validarCampoObligatorio(txt_jornada)&&
                        validar.validarCampoObligatorio(txt_numTrabajadores)&&
                        validar.validarCampoObligatorio(txt_fuenteGenRuido)&&
                        validar.validarCampoObligatorio(cbx_aConcentracion)&&
                        validar.validarCampoObligatorio(tv_limitePermisible)&&
                        validar.validarCampoObligatorio(tv_fechaMonitoreo)&&
                        validar.validarCampoObligatorio(tv_horaInicioMonitoreo)&&
                        validar.validarCampoObligatorio(cbx_tiempoMedicion)&&
                        validar.validarCampoObligatorio(txt_velViento)&&
                        validar.validarCampoObligatorio(txt_humedadRelatva)&&
                        validar.validarCalculo(calculoRealizado,getActivity())&&
                        validar.validarImagen(cargarImagen,getActivity())&&
                        //validar.validarCampoObligatorio(radioGroupIng,getActivity())&&
                        //validar.validarCampoObligatorio(radioaislateSI,getActivity())&&
                        //validar.validarCampoObligatorio(radioCabinaSI,getActivity())&&
                        //validar.validarCampoObligatorio(radioGroupTapones,getActivity())&&
                        //validar.validarCampoObligatorio(cbx_marcaTapones)&&
                        //validar.validarCampoObligatorio(cbx_modeloTapones)&&
                        //validar.validarCampoObligatorio(cbx_marcaOrej)&&
                        //validar.validarCampoObligatorio(cbx_modeloOrej)&&
                        //validar.validarCampoObligatorio(radioGroupOrej,getActivity())&&
                        validar.validarCampoObligatorio(txt_observaciones))
                {
                    String valorTvSonometro = tv_sonometro.getText().toString();
                    String valorTvCalibrador = tv_calibrador.getText().toString();
                    String valorTvAnemometro = tv_anemometro.getText().toString();
                    if(ValidarReticion(valorTvSonometro, valorTvCalibrador, valorTvAnemometro)){
                        String horacalibracion = validar.getValor(tv_horaCalibracion);
                        String nivell =  validar.getValor(cbx_nivel);
                        String variacion = validar.getValor(cbx_variacion);
                        String areaTrabajo = validar.getValor(txt_areaTrabajo);
                        String act_realizadas = validar.getValor(txt_actRealizadas);
                        String horarioTrabajo = validar.getValor(cbx_horario);
                        String jornadaTrabajo = txt_jornada.getText().toString();
                        String numTrabajadores = txt_numTrabajadores.getText().toString();
                        String fuenteRuido = validar.getValor(txt_fuenteGenRuido);
                        String a_concentracion = validar.getValor(cbx_aConcentracion);
                        String limPermisible = validar.getValor(tv_limitePermisible);
                        String fechaMonitoreo = validar.getValor(tv_fechaMonitoreo);
                        String horaInicial = validar.getValor(tv_horaInicioMonitoreo);
                        String tiempoMedicion = validar.getValor(cbx_tiempoMedicion);
                        String horaFinal = validar.getValor(tv_horaFinal);
                        String velViento = txt_velViento.getText().toString();
                        String humedadRelativa = txt_humedadRelatva.getText().toString();
                        String lminFinal = validar.getValor(tv_resLmin);
                        String lmaxFinal = validar.getValor(tv_resLmax);
                        String LequiFinal = validar.getValor(tv_resLeq);
                        //falta traerel valor de la imagen
                        String valorIng = validar.getValor(radioGroupIng,rootView);
                        String valorAislante = validar.getValor(radioGroup_Aislante,rootView);
                        String valorCabinas = validar.getValor(radioGroup_Cabinas,rootView);
                        String otroIng = validar.getValor(txt_otroIng);
                        String valorAdm = validar.getValor(radioGroupAdmin,rootView);
                        String valorRiesgos = validar.getValor(radioGroupRiesgos,rootView);
                        String valorPresionSono = validar.getValor(radioGroupPresionSono,rootView);
                        String valorEppOblig = validar.getValor(radioGroupEppOblig,rootView);
                        String valorTimeExpo = validar.getValor(radioGroupTimeExpo,rootView);
                        String valorRotacion = validar.getValor(radioGroupRotacion,rootView);
                        String otroAdm = validar.getValor(txt_otrosAdmin);
                        String valorTapones = validar.getValor(radioGroupTapones,rootView);
                        String valorOrej = validar.getValor(radioGroupOrej,rootView);

                        String marcaTapones = validar.getValor(cbx_marcaTapones);
                        String modeloTapones = validar.getValor(cbx_modeloTapones);
                        String nrrTapones = validar.getValor(tv_nrrTapones);
                        String marcaOrej = validar.getValor(cbx_marcaOrej);
                        String modeloOrej = validar.getValor(cbx_modeloOrej);
                        String nrrOrej = validar.getValor(tv_nrrOrej);
                        String oberv = validar.getValor(txt_observaciones);

                        if(horarioTrabajo.equals("OTRO")) horarioTrabajo = txt_otroHorario.getText().toString();
                        if(marcaOrej.equals("OTRO")) marcaOrej = txt_otroMarcaOrej.getText().toString();
                        if(marcaTapones.equals("OTRO")) marcaTapones = txt_otroMarcaTapones.getText().toString();
                        if(modeloOrej.equals("OTRO")) modeloOrej = txt_otroModeloOrej.getText().toString();
                        if(modeloTapones.equals("OTRO")) modeloTapones = txt_otroModeloTapones.getText().toString();

                        String estado_resultado = "1";
                        String fecha_registro = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

                        Equipos equipo1 = equipos.Buscar(valorTvSonometro);
                        Equipos equipo2 = equipos.Buscar(valorTvCalibrador);
                        Equipos equipo3 = equipos.Buscar(valorTvAnemometro);

                        Sonometria_Registro registro = new Sonometria_Registro(
                                -1,
                                "SO-0001",// irrelevante por ahira
                                id_formato,
                                id_plan_trabajo,
                                id_pt_trabajo,
                                equipo1.getCod_equipo(),
                                equipo1.getNombre(),
                                equipo2.getCod_equipo(),
                                equipo2.getNombre(),
                                equipo3.getCod_equipo(),
                                equipo3.getNombre(),
                                equipo1.getSerie(),
                                equipo2.getSerie(),
                                equipo3.getSerie(),
                                String.valueOf(equipo1.getId_equipo_registro()),
                                String.valueOf(equipo2.getId_equipo_registro()),
                                String.valueOf(equipo3.getId_equipo_registro()),
                                id_colaborador,
                                nuevo.getUsuario_nombres(),
                                horacalibracion,
                                nivell,
                                variacion,
                                areaTrabajo,
                                act_realizadas,
                                "0",
                                horarioTrabajo,
                                numTrabajadores,
                                fuenteRuido,
                                a_concentracion,
                                limPermisible,
                                fechaMonitoreo,
                                horaInicial,
                                tiempoMedicion,
                                horaFinal,
                                velViento,
                                humedadRelativa,
                                lminFinal,
                                lmaxFinal,
                                LequiFinal,
                                valorIng,
                                valorAdm,
                                valorAislante,
                                valorCabinas,
                                otroIng,
                                valorRiesgos,
                                valorPresionSono,
                                valorEppOblig,
                                valorRotacion,
                                valorTimeExpo,
                                otroAdm,
                                oberv,
                                valorTapones,
                                marcaTapones,
                                modeloTapones,
                                nrrTapones,
                                valorOrej,
                                marcaOrej,
                                modeloOrej,
                                nrrOrej,
                                jornadaTrabajo,
                                fecha_registro,
                                id_colaborador
                        );
                        DAO_RegistroSonometria nuevoRegistro = new DAO_RegistroSonometria(getActivity());
                        boolean respuesta = nuevoRegistro.RegistroSonometria(registro);
                        if(respuesta){

                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl("https://test.meiningenieros.pe/")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();

                            DosimetriaService service1 = retrofit.create(DosimetriaService.class);

                            Gson gson = new Gson();
                            // Convertir el objeto persona a JSON
                            String cadenaJson = gson.toJson(registro);
                            RequestBody json = RequestBody.create(MediaType.parse("application/json"), cadenaJson);

                            Call<ResponseBody> call1 = service1.insertDosimetria(json);
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


                            DAO_FormatosTrabajo dao_fromatosTrabajo = new DAO_FormatosTrabajo(getActivity());
                            for_Sonometria = dao_fromatosTrabajo.Buscar(id_plan_trabajo,id_formato);
                            for_Sonometria.setRealizado(for_Sonometria.getRealizado()+1);
                            for_Sonometria.setPor_realizar(for_Sonometria.getPor_realizar()-1);
                            dao_fromatosTrabajo.actualizarFormatoTrabajo(for_Sonometria);
                        }

                        // O muestra un AlertDialog con el mensaje
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Registro guardado")
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

    private boolean ValidarReticion(String valorTvSonometro, String valorTvCalibrador, String valorTvAnemometro) {
        if (valorTvCalibrador.equals(valorTvSonometro)) {
            tv_calibrador.setError("Equipo no debe Repetir");
            tv_calibrador.requestFocus();
            return false;
        } else if (valorTvAnemometro.equals(valorTvCalibrador)) {
            tv_anemometro.setError("Equipo no debe Repetir");
            tv_anemometro.requestFocus();
            return false;
        } else if (valorTvAnemometro.equals(valorTvSonometro)) {
            tv_anemometro.setError("Equipo no debe Repetir");
            tv_anemometro.requestFocus();
            return false;
        }
        return true;
    }
    public void init(View view){
        tv_nombreUsuario = view.findViewById(R.id.tv_nomUsuario);
        tv_nomEmpresa = view.findViewById(R.id.tv_nomEmpresa);


        // COMBO-BOX
        tv_sonometro = view.findViewById(R.id.tv_sonometro);
        tv_calibrador = view.findViewById(R.id.tv_calibrador);
        tv_anemometro = view.findViewById(R.id.tv_anemometro);
        tv_horaCalibracion = view.findViewById(R.id.tv_Hora);
        cbx_nivel = view.findViewById(R.id.cbx_nivel);
        cbx_variacion =view.findViewById(R.id.cbx_variacion);
        txt_areaTrabajo = view.findViewById(R.id.txt_areaTrabajo);
        txt_actRealizadas = view.findViewById(R.id.txt_actividades);
        cbx_horario = view.findViewById(R.id.cbx_horarioTrabajo);
        txt_jornada = view.findViewById(R.id.txt_jornadaTrabajo);
        txt_numTrabajadores = view.findViewById(R.id.txt_numTrabajadores);
        txt_fuenteGenRuido = view.findViewById(R.id.txt_fuenteGenRuido);
        cbx_aConcentracion = view.findViewById(R.id.cbx_Aconcentracion);
        tv_limitePermisible = view.findViewById(R.id.tv_lim_permisible);
        tv_fechaMonitoreo = view.findViewById(R.id.tv_fecha);
        tv_horaInicioMonitoreo = view.findViewById(R.id.tv_horaInicial);
        cbx_tiempoMedicion = view.findViewById(R.id.cbx_tiempoMedicion);
        tv_horaFinal = view.findViewById(R.id.tv_horaFinal);
        txt_velViento = view.findViewById(R.id.txt_velViento);
        txt_humedadRelatva = view.findViewById(R.id.txt_humedadRelativa);
        txt_leq1 = view.findViewById(R.id.txt_leq_med_1);
        txt_lmax1 = view.findViewById(R.id.txt_lmax_med_1);
        txt_lmin1 = view.findViewById(R.id.txt_lmin_med_1);
        txt_leq2 = view.findViewById(R.id.txt_leq_med_2);
        txt_lmax2 = view.findViewById(R.id.txt_lmax_med_2);
        txt_lmin2 = view.findViewById(R.id.txt_lmin_med_2);
        txt_leq3 = view.findViewById(R.id.txt_leq_med_3);
        txt_lmax3 = view.findViewById(R.id.txt_lmax_med_3);
        txt_lmin3 = view.findViewById(R.id.txt_lmin_med_3);
        txt_leq4 = view.findViewById(R.id.txt_leq_med_4);
        txt_lmax4 = view.findViewById(R.id.txt_lmax_med_4);
        txt_lmin4 = view.findViewById(R.id.txt_lmin_med_4);
        txt_leq5 = view.findViewById(R.id.txt_leq_med_5);
        txt_lmax5 = view.findViewById(R.id.txt_lmax_med_5);
        txt_lmin5 = view.findViewById(R.id.txt_lmin_med_5);
        btn_calcularMedicion = view.findViewById(R.id.btn_calcularMedicionGeneral);
        tv_resLmin = view.findViewById(R.id.tv_resulLmin);
        tv_resLmax = view.findViewById(R.id.tv_resulLmax);
        tv_resLeq = view.findViewById(R.id.tv_resulLequi);
        btn_subirFotoSono = view.findViewById(R.id.btn_subirFotoSono);
        imagen_sono = view.findViewById(R.id.img_Sonometria);
        tv_mensajeImagen = view.findViewById(R.id.tv_mensajeError);
        radioGroupIng = view.findViewById(R.id.radioGroupIngenieria);
        radioIngSi = view.findViewById(R.id.radioIngenieriaSi);
        radioGroup_Aislante = view.findViewById(R.id.radioGroup_Aislante);
        radioaislateSI = view.findViewById(R.id.radioAislanteSi);
        radioGroup_Cabinas = view.findViewById(R.id.radioGroup_Cabinas);
        radioCabinaSI = view.findViewById(R.id.radioCabinaSi);
        txt_otroIng = view.findViewById(R.id.txt_OtrosIngenieria);
        radioGroupAdmin = view.findViewById(R.id.radioGroupAdminis);
        radioAdminSi = view.findViewById(R.id.radio_AdministrativoSi);
        radioGroupRiesgos = view.findViewById(R.id.radioGroup_Riesgos);
        radioRiesgosSi = view.findViewById(R.id.radioRiesgosSi);
        radioGroupPresionSono = view.findViewById(R.id.radioGroup_SeñalPresionSonora);
        radioPresionSi = view.findViewById(R.id.radioPresionSi);
        radioGroupEppOblig = view.findViewById(R.id.radioGroup_EppObliga);
        radioEppSi = view.findViewById(R.id.radioEppSi);
        radioGroupTimeExpo = view.findViewById(R.id.radioGroup_AdminExpo);
        radioTimeExpoSi = view.findViewById(R.id.radioAdminExpoSi);
        radioGroupRotacion = view.findViewById(R.id.radioGroup_Rotacion);
        radioRotacionSi = view.findViewById(R.id.radioRotacionSi);
        txt_otrosAdmin = view.findViewById(R.id.txt_OtrosAdministrativo);
        radioGroupTapones = view.findViewById(R.id.radioGroupTapones);
        radioTaponesSi = view.findViewById(R.id.radioTaponesSi);
        cbx_marcaTapones = view.findViewById(R.id.cbx_marcaTapones);
        cbx_modeloTapones = view.findViewById(R.id.cbx_modeloTapones);
        tv_nrrTapones = view.findViewById(R.id.tv_nrrTapones);
        radioGroupOrej = view.findViewById(R.id.radioGroupOrejeras);
        radioOrejSi = view.findViewById(R.id.radio_OrejerasSi);
        cbx_marcaOrej = view.findViewById(R.id.cbx_marcaOrej);
        cbx_modeloOrej = view.findViewById(R.id.cbx_modeloOrej);
        tv_nrrOrej = view.findViewById(R.id.tv_nrrOrej);
        txt_observaciones = view.findViewById(R.id.txt_observaciones);

        btn_guardar = view.findViewById(R.id.fabGuardar);
        btn_cancelar = view.findViewById(R.id.fabCancelar);

        card_ingenier = view.findViewById(R.id.Card_Ingenieria);
        card_adminis = view.findViewById(R.id.Card_Administrativo);
        card_tapones = view.findViewById(R.id.Card_Tapones);
        card_orejeras = view.findViewById(R.id.Card_Orejeras);


        linearOtroHorario = view.findViewById(R.id.linearOtroHorario);
        txt_otroHorario = view.findViewById(R.id.txt_otroHorario);

        linearOtroMarcaOrej = view.findViewById(R.id.linearOtroMarcaOrej);
        txt_otroMarcaOrej = view.findViewById(R.id.txt_otroMarcaOrej);
        linearOtroMarcaTapones= view.findViewById(R.id.linearOtroMarcaTapones);
        txt_otroMarcaTapones = view.findViewById(R.id.txt_otroMarcaTapones);
        linearOtroModeloOrej = view.findViewById(R.id.linearOtroModeloOrej);
        txt_otroModeloOrej = view.findViewById(R.id.txt_otroModeloOrej);
        linearOtroModeloTapones= view.findViewById(R.id.linearOtroModeloTapones);
        txt_otroModeloTapones = view.findViewById(R.id.txt_otroModeloTapones);
    }
    public void ConfigPantalla(){
        MainActivity activity = (MainActivity) getActivity();
        EditText txt_buscar = activity.findViewById(R.id.txt_buscarOrden);
        TextView tv_usu2 = activity.findViewById(R.id.txt_usuario2);
        TextView tv_usu = activity.findViewById(R.id.txt_usuario);
        txt_buscar.setVisibility(View.GONE);
        tv_usu2.setText(tv_usu.getText());
        FragmentContainerView fragmentContainer = activity.findViewById(R.id.fragmentContainerView);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) fragmentContainer.getLayoutParams();
        params.topMargin = 120;
        fragmentContainer.setLayoutParams(params);
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
                        hora = hourOfDay; min = minute;
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
    private ArrayAdapter<String> LlenarSpinner(String nombreTabla, String campoTabla, Context context){

        List<String> datosParaSpinner = DAO_DatosLocal.obtenerDatosParaSpinner(nombreTabla, campoTabla,getActivity());
        ArrayList<String> listaDatos = new ArrayList<>();
        listaDatos.add("seleccione");
        listaDatos.addAll(datosParaSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, listaDatos);
        return adapter;
    }
    private ArrayAdapter<String> LlenarSpinner(String opcion1, String opcion2){
        ArrayList<String> listaDatos = new ArrayList<>();
        listaDatos.add("seleccione");
        listaDatos.add(opcion1);
        listaDatos.add(opcion2);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, listaDatos);
        return adapter;
    }
    @Override
    public void onImagePicked(Uri imageUri) {
        this.uri = imageUri;
        if (imagen_sono != null && imageUri != null) {
            imagen_sono.setImageURI(imageUri);
        }
    }
    private void sumarTiempo() {
        int tiempo =0;
        switch (cbx_tiempoMedicion.getSelectedItem().toString()) {
            case "15 min":
                tiempo = 15;
                break;
            case "25 min":
                tiempo = 25 ;
                break;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hora);
        calendar.set(Calendar.MINUTE, min);
        // Suma el intervalo seleccionado
        calendar.add(Calendar.MINUTE, tiempo);
        // Obtiene la hora y los minutos finales
        int endHour = calendar.get(Calendar.HOUR_OF_DAY);
        int endMinute = calendar.get(Calendar.MINUTE);
        String formattedMinute = String.format("%02d", endMinute);
        // Actualiza el otro TextView con la hora final
        tv_horaFinal.setText(endHour + ":" + formattedMinute);
    }
    private void mostrarOpcionesGone(RadioGroup group, int checkedId, CardView card, RadioButton radio) {
        if (checkedId == radio.getId()) {
            card.setVisibility(View.VISIBLE);
        } else {
            card.setVisibility(View.GONE);
        }
    }
    private void LlenarNrr(String nomTabla, String campoTabla, Spinner spinner, TextView txt) {
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
    private void calcularMedicion(){
        String med1 = txt_leq1.getText().toString();
        String med2 = txt_leq2.getText().toString();
        String med3 = txt_leq3.getText().toString();
        String med4 = txt_leq4.getText().toString();
        String med5 = txt_leq5.getText().toString();
        String med6 = txt_lmin1.getText().toString();
        String med7 = txt_lmin2.getText().toString();
        String med8 = txt_lmin3.getText().toString();
        String med9 = txt_lmin4.getText().toString();
        String med10 = txt_lmin5.getText().toString();
        String med11 =txt_lmax1.getText().toString();
        String med12 = txt_lmax2.getText().toString();
        String med13 = txt_lmax3.getText().toString();
        String med14 = txt_lmax4.getText().toString();
        String med15 = txt_lmax5.getText().toString();

        double fj = 60.0 / 600.0;
        double fj_final = 15.0 / 600.0;


            String cadena = med1 + "," + med2 + "," + med3 + "," + med6 + "," + med7 + "," + med8 + "," + med11 + "," + med12 + "," + med13 +
                (med4 != null ? "," + med4 : "") +
                (med5 != null ? "," + med5 : "") +
                (med9 != null ? "," + med5 : "") +
                (med10 != null ? "," + med5 : "") +
                (med14 != null ? "," + med5 : "") +
                (med15 != null ? "," + med5 : "");
                // ... y así sucesivamente para todos los valores de med

                String[] matriz = cadena.split(",");
        double maximo = Arrays.stream(matriz).mapToDouble(Double::parseDouble).max().getAsDouble();
        double minimo = Arrays.stream(matriz).mapToDouble(Double::parseDouble).min().getAsDouble();

        // Lj/10
        double lj1 = (double) Integer.parseInt(med1) / 10;
        double lj2 = (double) Integer.parseInt(med2) / 10;
        double lj3 = (double) (Integer.parseInt(med3) / 10.0);
        double lj4 = !med4.isEmpty() ? (double) (Integer.parseInt(med4) / 10.0) : 0.0;
        double lj5 = !med5.isEmpty() ? (double) (Integer.parseInt(med5) / 10.0) : 0.0;
        double lj6 = (double) (Integer.parseInt(med6) / 10.0);
        double lj7 = (double) (Integer.parseInt(med7) / 10.0);
        double lj8 = (double) (Integer.parseInt(med8) / 10.0);
        double lj9 = !med9.isEmpty() ? (double) (Integer.parseInt(med9) / 10.0) : 0.0;
        double lj10 = !med10.isEmpty() ? (double) (Integer.parseInt(med10) / 10.0) : 0.0;
        double lj11= (double) (Integer.parseInt(med11) / 10.0);
        double lj12 = (double) (Integer.parseInt(med12) / 10.0);
        double lj13 = (double) (Integer.parseInt(med13) / 10.0);
        double lj14 = !med14.isEmpty() ? (double) (Integer.parseInt(med14) / 10.0) : 0.0;
        double lj15 = !med15.isEmpty() ? (double) (Integer.parseInt(med15) / 10.0) : 0.0;


        // 10^(Lj/10)
        double pot_lj1 = (Math.pow(10, lj1));
        double pot_lj2 = (Math.pow(10, lj2));
        double pot_lj3 = (Math.pow(10, lj3));
        double pot_lj4 = !med4.isEmpty() ? (Math.pow(10, lj4)) : 0.0;
        double pot_lj5 = !med5.isEmpty() ? (Math.pow(10, lj5)) : 0.0;
        double pot_lj6 = (Math.pow(10, lj6));
        double pot_lj7 = (Math.pow(10, lj7));
        double pot_lj8 = (Math.pow(10, lj8));
        double pot_lj9 = !med9.isEmpty() ? Math.pow(10, lj9) : 0.0;
        double pot_lj10 = !med10.isEmpty() ? (Math.pow(10, lj10)) : 0.0;
        double pot_lj11 = (Math.pow(10, lj11));
        double pot_lj12 = (Math.pow(10, lj12));
        double pot_lj13 = (Math.pow(10, lj13));
        double pot_lj14 = !med11.isEmpty() ? (Math.pow(10, lj14)) : 0.0;
        double pot_lj15 = !med12.isEmpty() ? (Math.pow(10, lj15)) : 0.0;

        // N°Repeticiones*Fj x 10^(Lj/10)
        double multi1 = (1 * fj * pot_lj1);
        double multi2 = (1 * fj * pot_lj2);
        double multi3 = (1 * fj * pot_lj3);
        double multi4 = !med4.isEmpty() ? (1 * fj * pot_lj4) : 0.0;
        double multi5 = !med5.isEmpty() ? (1 * fj * pot_lj5) : 0.0;
        double multi6 = (1 * fj * pot_lj6);
        double multi7 = (1 * fj * pot_lj7);
        double multi8 = (1 * fj * pot_lj8);
        double multi9 = !med9.isEmpty() ? (1 * fj * pot_lj9) : 0.0;
        double multi10 = !med10.isEmpty() ? (1 * fj * pot_lj10) : 0.0;
        double multi11 = (1 * fj * pot_lj11);
        double multi12 = (1 * fj * pot_lj12);
        double multi13 = (1 * fj * pot_lj13);
        double multi14 = !med14.isEmpty() ? (1 * fj * pot_lj14) : 0.0;
        double multi15 = !med15.isEmpty() ? (1 * fj_final * pot_lj15) : 0.0;

        double suma = multi1 + multi2 + multi3 + multi4 + multi5 + multi6 + multi7 + multi8 + multi9 + multi10 + multi11 + multi12 + multi13 + multi14 + multi15;
        double Leq_dBA = Math.round(10 * Math.log10(suma) * 10) / 10.0;
        tv_resLmin.setText(String.valueOf(minimo));
        tv_resLmax.setText(String.valueOf(maximo));
        tv_resLeq.setText(String.valueOf(Leq_dBA));
        calculoRealizado = true;
    }
}