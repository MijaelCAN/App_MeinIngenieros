package com.mijael.mein;

import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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
import com.mijael.mein.DAO.DAO_RegistroEstreTermico;
import com.mijael.mein.DAO.DAO_RegistroFormatos;
import com.mijael.mein.DAO.DAO_Usuario;
import com.mijael.mein.Entidades.Equipos;
import com.mijael.mein.Entidades.EstresTermico_Registro;
import com.mijael.mein.Entidades.EstresTermico_RegistroDetalle;
import com.mijael.mein.Entidades.Formatos_Trabajo;
import com.mijael.mein.Entidades.Usuario;
import com.mijael.mein.Extras.FragmentoImagen;
import com.mijael.mein.Extras.InputDateConfiguration;
import com.mijael.mein.Extras.Validaciones;
import com.mijael.mein.SERVICIOS.DosimetriaService;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EstresTermicoFragment extends Fragment implements FragmentoImagen.ImagePickerListener{
    private boolean cargarImagen = false;
    private int contadorTareas = 0;
    View rootView;
    TextView tv_nombreUsuario, tv_nomEmpresa;
    String id_plan_trabajo, id_pt_trabajo, id_formato,id_colaborador, nom_Empresa;
    AutoCompleteTextView tv_estresTermico, tv_anemometro;
    TextView tv_horaVerificacion, tv_fechaMonitoreo, tv_horaInicioMoni, tv_horaFinalMoni,tv_desTrabajoDetalle, tv_tasaMetabolica;
    EditText txt_colorPredominante, txt_wbgt01, txt_wbgt11, txt_wbgt17, txt_t_aire01, txt_t_aire11, txt_t_aire17, txt_t_globo01, txt_tasaMetabolicaW, txt_tasaMetabolicaK,
            txt_t_globo11, txt_t_globo17, txt_h_relativa01, txt_h_relativa11, txt_h_relativa17, txt_velViento,  txt_observacion, txt_frecuenciaCardiaca,
            txt_velViento2,txt_velViento3, txt_actividad;
    CardView card_ingenier, Card_Tanteo, Card_Observacion, Card_Analisis;
    LinearLayout linear1A, linear1B,linear1_1,linear1_7, linearBuscarDni, linearContenedorTareas;
    RadioGroup radioGroupVerificacion, radioGroupIng, radioGroupZonaSombra, radioGroupRotacion, radioGroupRecuperacion,radioGroupDispensador, radioGroupCapacitacion;
    RadioButton radio_ingenierSI;
    Button btnAgregarTareas;
    AppCompatButton btn_subirFotoEstres, btn_BuscarDni;
    FloatingActionButton btn_guardar;
    ExtendedFloatingActionButton btnCancelar;
    EditText txt_timeMedMin, txt_timeExpoHora, txt_jornadaTrabajo, txt_numDoc, txt_nomTrabajador, txt_edad, txt_peso,txt_areaTrabajo, txt_puestoTrabajo, txt_aRealizada,
            txt_descFuenteGen, txt_nomControl, txt_otrosEpps, txt_nomTarea1, txt_cicloTrab1, txt_nomTarea2, txt_cicloTrab2, txt_nomTarea3, txt_cicloTrab3, txt_metroSubida;
    Spinner spn_tipoDoc, spn_horarioTrabajo, spn_regimen, spn_horarioRefrigerio, spn_fuenteGen, spn_descTrabajo, spn_timeCargoAnyo, spn_timeCargoMes, spn_condicionTrab,
            spn_porcActividad, spn_porcDescanso, spn_vestimenta, spn_materialPrenda, spn_posicion1, spn_pCuerpo1, spn_intesidad1, spn_posicion2, spn_pCuerpo2, spn_intesidad2,
            spn_posicion3, spn_pCuerpo3, spn_intesidad3, spn_nivelDeterminacion, spn_metodoDeterminacion, spn_tipoTrab, spn_tipoMedicion, spn_clase, spn_genero, spn_ocupacion;
    // CheckBox
    CheckBox check_zapatos, check_casco, check_lentes, check_guantes, check_orejeras, check_tapones, check_cubreNuca;
    ImageView imgE_Termico;
    Uri uri;

    LinearLayout linearOtroHorario, linearOtroRegimen, linearOtroRefrigerio;
    EditText txt_otroHorario, txt_otroRegimen, txt_otroRefrigerio;
    DAO_RegistroFormatos dao_registroFormatos;
    public EstresTermicoFragment() {
        // Required empty public constructor
        dao_registroFormatos = new DAO_RegistroFormatos(getActivity());
    }
    Formatos_Trabajo for_Estres;
    Validaciones validar = new Validaciones();
    InputDateConfiguration config;
    Map<String, String> valoresTareas = new HashMap<>();
    String valorTimeMed;

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
        rootView = inflater.inflate(R.layout.fragment_estres_termico, container, false);
        config = new InputDateConfiguration(getActivity(),id_colaborador,nom_Empresa,rootView);
        init(rootView);
        config.ConfigPantalla();
        DAO_Equipos equipos = new DAO_Equipos(getActivity());
        List<String> lista_CodEquipos = equipos.obtener_CodEquipos();
        DAO_Usuario usuario = new DAO_Usuario(getActivity());
        Usuario nuevo = usuario.BuscarUsuario(Integer.parseInt(id_colaborador));

        spn_tipoDoc.setAdapter(config.LlenarSpinner(new String[]{"DNI","CE"}));
        spn_fuenteGen.setAdapter(config.LlenarSpinner(new String[]{"Natural","Artificial","Natural - artificial"}));
        spn_descTrabajo.setAdapter(config.LlenarSpinner(new String[]{"Trabajo al aire libre sin carga solar o bajo techo", "Trabajo al aire libre con carga solar"}));
        spn_condicionTrab.setAdapter(config.LlenarSpinner(new String[]{"Aclimatado", "No aclimatado"}));
        spn_nivelDeterminacion.setAdapter(config.LlenarSpinner(new String[]{"Tanteo", "Observación","Análisis"}));
        spn_tipoMedicion.setAdapter(config.LlenarSpinner(new String[]{"Medición a una altura","Medición a tres alturas"}));
        spn_genero.setAdapter(config.LlenarSpinner(new String[]{"Hombre","Mujer"}));
        spn_tipoTrab.setAdapter(config.LlenarSpinner(new String[]{"Oficina","Artesanos","Minería","Industria","Artes","Agricultura","Transporte","Diversos"}));
        //spn_ocupacion.setAdapter(config.LlenarSpinner(new String[]{"Ayudante de laboratorio","Profesor","Dependiente de Comercio","Secretario"}));
        spn_clase.setAdapter(config.LlenarSpinner(new String[]{"Descanso","Tasa metabólica baja","Tasa metabólica moderada","Tasa metabólica alta","Tasa metabólica muy alta"}));

        HashMap<String, String[]> ocupacionesPorTipoTrabajo = new HashMap<>();
        ocupacionesPorTipoTrabajo.put("Oficina", new String[]{"Trabajo de sedentario", "Trabajo administrativo"});
        ocupacionesPorTipoTrabajo.put("Artesanos", new String[]{"Coserje", "Albañil", "Carpintero", "Cristalero", "Pintor", "Panadero", "Carnicero", "Relojero"});
        ocupacionesPorTipoTrabajo.put("Minería", new String[]{"Operador de vagoneta", "Picador de carbón", "Operador de horno de coque"});
        ocupacionesPorTipoTrabajo.put("Industria", new String[]{"Operador de alto horno", "Operador de horno eléctrico", "Moldeo manual", "Moldeo a máquina", "Fundidor", "Herrero", "Soldador", "Tornero", "Fresador", "Mecánico de precisión"});
        ocupacionesPorTipoTrabajo.put("Artes", new String[]{"Componedor a mano", "Encuadernor"});
        ocupacionesPorTipoTrabajo.put("Agricultura", new String[]{"Jardinero", "Tractorista"});
        ocupacionesPorTipoTrabajo.put("Transporte", new String[]{"Conductor de automóvil", "Conductor de autobús", "Conductor de tranvia", "Operador de grúa"});
        ocupacionesPorTipoTrabajo.put("Diversos", new String[]{"Ayudante de laboratorio", "Profesor", "Dependiente de comercio", "Secretario"});

        HashMap<String, String> rangosPorOcupacion = new HashMap<>();
        rangosPorOcupacion.put("Trabajo de sedentario", "55 a 70");
        rangosPorOcupacion.put("Trabajo administrativo", "70 a 100");
        rangosPorOcupacion.put("Coserje", "80 a 115");
        rangosPorOcupacion.put("Albañil", "110 a 160");
        rangosPorOcupacion.put("Carpintero", "110 a 175");
        rangosPorOcupacion.put("Cristalero", "90 a 125");
        rangosPorOcupacion.put("Pintor", "100 a 130");
        rangosPorOcupacion.put("Panadero", "110 a 140");
        rangosPorOcupacion.put("Carnicero", "105 a 140");
        rangosPorOcupacion.put("Relojero", "55 a 70");
        rangosPorOcupacion.put("Operador de vagoneta", "70 a 85");
        rangosPorOcupacion.put("Picador de carbón", "110");
        rangosPorOcupacion.put("Operador de horno de coque", "115 a 175");
        rangosPorOcupacion.put("Operador de alto horno", "170 a 220");
        rangosPorOcupacion.put("Operador de horno eléctrico", "125 a 145");
        rangosPorOcupacion.put("Moldeo manual", "140 a 240");
        rangosPorOcupacion.put("Moldeo a máquina", "105 a 165");
        rangosPorOcupacion.put("Fundidor", "140 a 240");
        rangosPorOcupacion.put("Herrero", "90 a 200");
        rangosPorOcupacion.put("Soldador", "75 a 125");
        rangosPorOcupacion.put("Tornero", "75 a 125");
        rangosPorOcupacion.put("Fresador", "80 a 140");
        rangosPorOcupacion.put("Mecánico de precisión", "70 a 110");
        rangosPorOcupacion.put("Componedor a mano", "70 a 95");
        rangosPorOcupacion.put("Encuadernor", "75 a 100");
        rangosPorOcupacion.put("Jardinero", "115 a 190");
        rangosPorOcupacion.put("Tractorista", "85 a 110");
        rangosPorOcupacion.put("Conductor de automóvil", "70 a 100");
        rangosPorOcupacion.put("Conductor de autobús", "70 a 125");
        rangosPorOcupacion.put("Conductor de tranvia", "80 a 115");
        rangosPorOcupacion.put("Operador de grúa", "65 a 145");
        rangosPorOcupacion.put("Ayudante de laboratorio", "85 a 100");
        rangosPorOcupacion.put("Profesor", "85 a 100");
        rangosPorOcupacion.put("Dependiente de comercio", "100 a 120");
        rangosPorOcupacion.put("Secretario", "70 a 85");


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
        spn_tipoTrab.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemseleccionado = parent.getItemAtPosition(position).toString();
                String[] ocupaciones = ocupacionesPorTipoTrabajo.get(itemseleccionado);

                // Si hay ocupaciones correspondientes, llenar el spinner con ellas
                if (ocupaciones != null) {
                    spn_ocupacion.setAdapter(config.LlenarSpinner(ocupaciones));
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

        spn_descTrabajo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if(selectedItem.equals("Trabajo al aire libre sin carga solar o bajo techo")){
                    tv_desTrabajoDetalle.setText("IN - Interior y Exterior sin luz solar");
                } else if (selectedItem.equals("Trabajo al aire libre con carga solar")) {
                    tv_desTrabajoDetalle.setText("OUT - Exterior con luz solar");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spn_nivelDeterminacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String seleccion = (String) parent.getItemAtPosition(position);
                Card_Tanteo.setVisibility(View.GONE);
                Card_Analisis.setVisibility(View.GONE);
                Card_Observacion.setVisibility(View.GONE);
                config.limpiarElementos((ViewGroup) Card_Tanteo.getChildAt(0));
                config.limpiarElementos((ViewGroup) Card_Analisis.getChildAt(0));
                config.limpiarElementos((ViewGroup) Card_Observacion.getChildAt(0));
                tv_tasaMetabolica.setText("");
                if (seleccion.equals("Tanteo")) {
                    spn_metodoDeterminacion.setAdapter(config.LlenarSpinner(new String[]{"1A - Clasificación del tamaño de la ocupación", "1B - Clasificación del tamaño de la actividad"}));
                } else if (seleccion.equals("Observación")) {
                    spn_metodoDeterminacion.setAdapter(config.LlenarSpinner(new String[]{"Tablas para actividades específicas"}));
                } else if (seleccion.equals("Análisis")){
                    spn_metodoDeterminacion.setAdapter(config.LlenarSpinner(new String[]{"Medida del ritmo cardiaco bajo condiciones determi"}));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spn_metodoDeterminacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String seleccion = (String) parent.getItemAtPosition(position);
                config.limpiarElementos((ViewGroup) Card_Tanteo.getChildAt(0));
                config.limpiarElementos((ViewGroup) Card_Analisis.getChildAt(0));
                config.limpiarElementos((ViewGroup) Card_Observacion.getChildAt(0));
                txt_actividad.setText("");
                if(seleccion.equals("1A - Clasificación del tamaño de la ocupación")){
                    Card_Tanteo.setVisibility(View.VISIBLE);
                    Card_Analisis.setVisibility(View.GONE);
                    Card_Observacion.setVisibility(View.GONE);
                    linear1A.setVisibility(View.VISIBLE);
                    linear1B.setVisibility(View.GONE);
                    txt_tasaMetabolicaW.setText("");
                    txt_tasaMetabolicaK.setText("");
                }else if(seleccion.equals("1B - Clasificación del tamaño de la actividad")){
                    Card_Tanteo.setVisibility(View.VISIBLE);
                    Card_Analisis.setVisibility(View.GONE);
                    Card_Observacion.setVisibility(View.GONE);
                    linear1A.setVisibility(View.GONE);
                    linear1B.setVisibility(View.VISIBLE);
                } else if (seleccion.equals("Tablas para actividades específicas")) {
                    Card_Tanteo.setVisibility(View.GONE);
                    Card_Analisis.setVisibility(View.GONE);
                    Card_Observacion.setVisibility(View.VISIBLE);
                    linear1A.setVisibility(View.GONE);
                    linear1B.setVisibility(View.GONE);
                } else if (seleccion.equals("Medida del ritmo cardiaco bajo condiciones determi")) {
                    Card_Tanteo.setVisibility(View.GONE);
                    Card_Analisis.setVisibility(View.VISIBLE);
                    Card_Observacion.setVisibility(View.GONE);
                    linear1A.setVisibility(View.GONE);
                    linear1B.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spn_tipoMedicion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String seleccion = (String) parent.getItemAtPosition(position);
                if(seleccion.equals("Medición a una altura")){
                    txt_wbgt01.setVisibility(View.VISIBLE);
                    linear1_1.setVisibility(View.GONE);
                    linear1_7.setVisibility(View.GONE);
                    txt_t_aire01.setVisibility(View.VISIBLE);
                    txt_t_aire11.setVisibility(View.GONE);
                    txt_t_aire17.setVisibility(View.GONE);
                    txt_t_globo01.setVisibility(View.VISIBLE);
                    txt_t_globo11.setVisibility(View.GONE);
                    txt_t_globo17.setVisibility(View.GONE);
                    txt_h_relativa01.setVisibility(View.VISIBLE);
                    txt_h_relativa11.setVisibility(View.GONE);
                    txt_h_relativa17.setVisibility(View.GONE);
                    txt_velViento.setVisibility(View.VISIBLE);
                    txt_velViento2.setVisibility(View.GONE);
                    txt_velViento3.setVisibility(View.GONE);
                } else if (seleccion.equals("Medición a tres alturas")) {
                    txt_wbgt01.setVisibility(View.VISIBLE);
                    linear1_1.setVisibility(View.VISIBLE);
                    linear1_7.setVisibility(View.VISIBLE);
                    txt_t_aire01.setVisibility(View.VISIBLE);
                    txt_t_aire11.setVisibility(View.VISIBLE);
                    txt_t_aire17.setVisibility(View.VISIBLE);
                    txt_t_globo01.setVisibility(View.VISIBLE);
                    txt_t_globo11.setVisibility(View.VISIBLE);
                    txt_t_globo17.setVisibility(View.VISIBLE);
                    txt_h_relativa01.setVisibility(View.VISIBLE);
                    txt_h_relativa11.setVisibility(View.VISIBLE);
                    txt_h_relativa17.setVisibility(View.VISIBLE);
                    txt_velViento.setVisibility(View.VISIBLE);
                    txt_velViento2.setVisibility(View.VISIBLE);
                    txt_velViento3.setVisibility(View.VISIBLE);
                }

                obtenerValoresTareas();
                Log.e("vvvvv", String.valueOf(contadorTareas));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spn_ocupacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();

                String rango = rangosPorOcupacion.get(selectedItem);
                // Si hay un rango correspondiente, mostrarlo en el TextView
                if (rango != null) {
                    tv_tasaMetabolica.setText(rango);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        txt_tasaMetabolicaW.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String valor = txt_tasaMetabolicaW.getText().toString();
                if(valor!=null && !valor.isEmpty()){
                    DecimalFormat decimalFormat = new DecimalFormat("#.##");
                    float resultado = Float.parseFloat(valor) / 0.644f;
                    String resultadoFormateado = decimalFormat.format(resultado);
                    txt_tasaMetabolicaK.setText(resultadoFormateado);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        spn_clase.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if(selectedItem.equals("Descanso")){
                    txt_actividad.setText("Descansando, sentado comodamente");
                    txt_tasaMetabolicaW.setText("65");
                } else if (selectedItem.equals("Tasa metabólica baja")) {
                    txt_actividad.setText("Trabajo manual ligero (escribir, teclear, coser); trabajo con brazos y manos (herramientas pequeñas, inspección, montaje); trabajo con pie y piernas (conducción de vehículos en condiciones normales, empleo de pedales de accionamiento). De pie, taladrado;");
                    txt_tasaMetabolicaW.setText("100");
                } else if (selectedItem.equals("Tasa metabólica moderada")) {
                    txt_actividad.setText("Trabajo sostenido con manos y brazos (clavar, limar); trabajo con brazos y piernas (conducción de camiones, tractores o máquinas en obras); trabajo con tronco y brazos (martillos neumáticos, escardar, recoger frutas y verduras, tirar de o empujar carretil");
                    txt_tasaMetabolicaW.setText("165");
                } else if (selectedItem.equals("Tasa metabólica alta")) {
                    txt_actividad.setText("Trabajo intenso con brazos y tronco; transporte de materiales pesados; palear, empleo de sierra; cepillado o escopelado de madera dura; corte de hierba o cavado manual; caminar a una velocidad de 5.5 km/h hasta 7 km/h. Empujar o tirar de carretillas o carros de mano muy cargados; desbarbado de fundición; colocación de bloques de hormigón.");
                    txt_tasaMetabolicaW.setText("230");
                } else if (selectedItem.equals("Tasa metabólica muy alta")) {
                    txt_actividad.setText("Actividad muy intensa a ritmo de muy rápido a máximo; trabajo con hacha; cavado o paleado intenso; subir escaleras, rampas o escalas; caminar rápidamente a pequeños pasos; correr; caminar a una velocidad superior a los 7 km/h.");
                    txt_tasaMetabolicaW.setText("290");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        config.configurarAutoCompleteTextView(tv_estresTermico,lista_CodEquipos);
        config.configurarAutoCompleteTextView(tv_anemometro,lista_CodEquipos);

        spn_horarioTrabajo.setAdapter(config.LlenarSpinner("horario_trab_fromato_medicion","desc_horario",getActivity()));
        spn_regimen.setAdapter(config.LlenarSpinner("regimen_formato_medicion","nom_regimen",getActivity()));
        spn_horarioRefrigerio.setAdapter(config.LlenarSpinner("horario_refrig_formato_medicion","nom_horario",getActivity()));
        config.llenarSpinnerConNumeros(spn_timeCargoAnyo,10,getActivity());
        config.llenarSpinnerConNumeros(spn_timeCargoMes,11,getActivity());
        spn_porcActividad.setAdapter(config.LlenarSpinner("actividad_Descanso","nom_act_des",getActivity()));
        spn_porcDescanso.setAdapter(config.LlenarSpinner("actividad_Descanso","nom_act_des",getActivity()));
        spn_vestimenta.setAdapter(config.LlenarSpinner("vestimenta","nom_vestimenta",getActivity()));
        spn_materialPrenda.setAdapter(config.LlenarSpinner("materialVestimenta","nom_material",getActivity()));
        /*spn_posicion1.setAdapter(config.LlenarSpinner("posicion","nom_pos",getActivity()));
        spn_posicion2.setAdapter(config.LlenarSpinner("posicion","nom_pos",getActivity()));
        spn_posicion3.setAdapter(config.LlenarSpinner("posicion","nom_pos",getActivity()));
        spn_pCuerpo1.setAdapter(config.LlenarSpinner("partes_cuerpo","nom_cuerpo",getActivity()));
        spn_pCuerpo2.setAdapter(config.LlenarSpinner("partes_cuerpo","nom_cuerpo",getActivity()));
        spn_pCuerpo3.setAdapter(config.LlenarSpinner("partes_cuerpo","nom_cuerpo",getActivity()));
        spn_intesidad1.setAdapter(config.LlenarSpinner("intensidad","nom_intensidad",getActivity()));
        spn_intesidad2.setAdapter(config.LlenarSpinner("intensidad","nom_intensidad",getActivity()));
        spn_intesidad3.setAdapter(config.LlenarSpinner("intensidad","nom_intensidad",getActivity()));*/

        config.MostrarCampos(linearOtroHorario,spn_horarioTrabajo);
        config.MostrarCampos(linearOtroRegimen,spn_regimen);
        config.MostrarCampos(linearOtroRefrigerio,spn_horarioRefrigerio);

        radioGroupIng.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {@Override public void onCheckedChanged(RadioGroup group, int checkedId) {mostrarOpcionesGone(group,checkedId,card_ingenier,radio_ingenierSI);}});




        tv_horaVerificacion.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {config.showTimePickerDialog(rootView,tv_horaVerificacion);}});
        tv_fechaMonitoreo.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {config.showDatePickerDialog(rootView,tv_fechaMonitoreo);}});
        tv_horaInicioMoni.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {config.showTimePickerDialog(rootView,tv_horaInicioMoni);}});
        tv_horaFinalMoni.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {config.showTimePickerDialog(rootView,tv_horaFinalMoni);}});


        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String texto = s.toString();
                String horainicial = tv_horaInicioMoni.getText().toString();
                String horafinal = tv_horaFinalMoni.getText().toString();
                if(!horainicial.isEmpty() && !horafinal.isEmpty()){
                    valorTimeMed = InputDateConfiguration.calcularTiempoMedicion(horainicial,horafinal);
                    txt_timeMedMin.setText(valorTimeMed);
                }else{
                    txt_timeMedMin.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        tv_horaInicioMoni.addTextChangedListener(watcher);
        tv_horaFinalMoni.addTextChangedListener(watcher);


        btn_subirFotoEstres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentoImagen fragmentoImagen = new FragmentoImagen(EstresTermicoFragment.this);
                fragmentoImagen.show(getActivity().getSupportFragmentManager(), "imagePicker");
                cargarImagen = true;
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
        btnAgregarTareas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contadorTareas++;
                if (contadorTareas <= 5) {
                    if(contadorTareas==5){
                        btnAgregarTareas.setVisibility(View.GONE);
                    }
                    agregarNuevaTarea(config);

                } else {
                    btnAgregarTareas.setVisibility(View.GONE);
                }
            }
        });
        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(
                        validar.validarCampoObligatorio(tv_estresTermico) &&
                        validar.validarCampoObligatorio(tv_anemometro) &&
                        validar.validarCampoObligatorio(tv_horaVerificacion) &&
                        validar.validarImagen(cargarImagen,getActivity()) &&
                        validar.validarCampoObligatorio(tv_fechaMonitoreo) &&
                        validar.validarCampoObligatorio(tv_horaInicioMoni) &&
                        //validar.validarCampoObligatorio(tv_horaFinalMoni) &&
                        validar.validarCampoObligatorio(txt_timeMedMin) &&
                        validar.validarCampoObligatorio(txt_timeExpoHora) &&
                        validar.validarCampoObligatorio(txt_jornadaTrabajo) &&
                        validar.validarCampoObligatorio(spn_tipoDoc) &&
                        validar.validarCampoObligatorio(txt_numDoc) &&
                        validar.validarCampoObligatorio(txt_nomTrabajador) &&
                        validar.validarCampoObligatorio(txt_edad) &&
                        validar.validarCampoObligatorio(txt_peso) &&
                        validar.validarCampoObligatorio(txt_areaTrabajo) &&
                        validar.validarCampoObligatorio(txt_puestoTrabajo) &&
                        validar.validarCampoObligatorio(txt_aRealizada )&&
                        validar.validarCampoObligatorio(spn_horarioTrabajo) &&
                        validar.validarCampoObligatorio(spn_regimen) &&
                        validar.validarCampoObligatorio(spn_horarioRefrigerio) &&
                        validar.validarCampoObligatorio(spn_fuenteGen) &&
                        validar.validarCampoObligatorio(txt_descFuenteGen) &&
                        validar.validarCampoObligatorio(spn_descTrabajo) &&
                        //validar.validarCampoObligatorio(tv_desTrabajoDetalle) &&
                        validar.validarCampoObligatorio(spn_timeCargoAnyo) &&
                        validar.validarCampoObligatorio(spn_timeCargoMes) &&
                        validar.validarCampoObligatorio(spn_condicionTrab) &&
                        validar.validarCampoObligatorio(spn_porcActividad) &&
                        validar.validarCampoObligatorio(spn_porcDescanso) &&
                        validar.validarCampoObligatorio(spn_vestimenta) &&
                        validar.validarCampoObligatorio(spn_materialPrenda) &&
                        validar.validarCampoObligatorio(txt_colorPredominante) &&
                        validar.validarCampoObligatorio(check_zapatos) &&
                        validar.validarCampoObligatorio(check_casco) &&
                        validar.validarCampoObligatorio(check_lentes) &&
                        validar.validarCampoObligatorio(check_guantes) &&
                        validar.validarCampoObligatorio(check_orejeras) &&
                        validar.validarCampoObligatorio(check_tapones) &&
                        validar.validarCampoObligatorio(check_cubreNuca) &&
                        validar.validarCampoObligatorio(txt_otrosEpps)
                        //validar.validarCampoObligatorio(txt_nomTarea1) &&
                        //validar.validarCampoObligatorio(txt_cicloTrab1) &&
                        //validar.validarCampoObligatorio(spn_posicion1) &&
                        //validar.validarCampoObligatorio(spn_pCuerpo1) &&
                        //validar.validarCampoObligatorio(spn_intesidad1) &&
                        //validar.validarCampoObligatorio(txt_nomTarea2) &&
                        //validar.validarCampoObligatorio(txt_cicloTrab2) &&
                        //validar.validarCampoObligatorio(spn_posicion2) &&
                        //validar.validarCampoObligatorio(spn_pCuerpo2) &&
                        //validar.validarCampoObligatorio(spn_intesidad2) &&
                        //validar.validarCampoObligatorio(txt_nomTarea3) &&
                        //validar.validarCampoObligatorio(txt_cicloTrab3) &&
                        //validar.validarCampoObligatorio(spn_posicion3) &&
                        //validar.validarCampoObligatorio(spn_pCuerpo3) &&
                        //validar.validarCampoObligatorio(spn_intesidad3) &&
                        //validar.validarCampoObligatorio(txt_wbgt01) &&
                        //validar.validarCampoObligatorio(txt_wbgt11) &&
                        //validar.validarCampoObligatorio(txt_wbgt17) &&
                        //validar.validarCampoObligatorio(txt_t_aire01) &&
                        /*validar.validarCampoObligatorio(txt_t_aire11) &&
                        validar.validarCampoObligatorio(txt_t_aire17    ) &&
                        validar.validarCampoObligatorio(txt_t_globo01) &&
                        validar.validarCampoObligatorio(txt_t_globo11) &&
                        validar.validarCampoObligatorio(txt_t_globo17) &&
                        validar.validarCampoObligatorio(txt_h_relativa01) &&
                        validar.validarCampoObligatorio(txt_h_relativa11) &&
                        validar.validarCampoObligatorio(txt_h_relativa17) &&
                        validar.validarCampoObligatorio(txt_velViento) &&
                        validar.validarCampoObligatorio(txt_observacion)*/
                ){
                    String valorEstresTermico = tv_estresTermico.getText().toString();
                    String valorAnemometro = tv_anemometro.getText().toString();
                    String valorHoraVerificacion = tv_horaVerificacion.getText().toString();
                    int valorGroupVerificacion = validar.getValor2(radioGroupVerificacion,rootView);

                    String f = tv_fechaMonitoreo.getText().toString();
                    String valorFechaMonitoreo = config.convertirFecha(f);
                    //String valorFechaMonitoreo = tv_fechaMonitoreo.getText().toString();

                    String valorHoraInicioMoni = tv_horaInicioMoni.getText().toString();
                    String valorHoraFinalMoni = tv_horaFinalMoni.getText().toString();
                    //iba tiempo de medicion
                    String valorTimeExpo = txt_timeExpoHora.getText().toString();
                    String valorJornada = txt_jornadaTrabajo.getText().toString();
                    String valorTipoDoc = spn_tipoDoc.getSelectedItem().toString();
                    String valorNumDoc = txt_numDoc.getText().toString();
                    String valorNombreTra = txt_nomTrabajador.getText().toString();
                    String valorEdad = txt_edad.getText().toString();
                    String valorPeso = txt_peso.getText().toString();
                    String valorAreaTra = txt_areaTrabajo.getText().toString();
                    String valorPuestoTra = txt_puestoTrabajo.getText().toString();
                    String valorActividades = txt_aRealizada.getText().toString();
                    String valorHorarioTrabajo = spn_horarioTrabajo.getSelectedItem().toString();
                    String valorRegimen = spn_regimen.getSelectedItem().toString();
                    String valorRefrigerio = spn_horarioRefrigerio.getSelectedItem().toString();

                    if(valorHorarioTrabajo.equals("OTRO")) valorHorarioTrabajo = txt_otroHorario.getText().toString();
                    if(valorRegimen.equals("OTRO")) valorRegimen = txt_otroRegimen.getText().toString();
                    if(valorRefrigerio.equals("OTRO")) valorRefrigerio = txt_otroRefrigerio.getText().toString();

                    String valorFuenteGen = spn_fuenteGen.getSelectedItem().toString();
                    String valorDesFuenteGen = txt_descFuenteGen.getText().toString();
                    String valorDesTrabajo = spn_descTrabajo.getSelectedItem().toString();
                    String valorDesTrabDetalle = tv_desTrabajoDetalle.getText().toString();
                    int valorGroupIng = validar.getValor2(radioGroupIng,rootView);
                    String valorControlIng = txt_nomControl.getText().toString();
                    String valorTimeCargoAnyo = spn_timeCargoAnyo.getSelectedItem().toString() + "año(s)";
                    String valorTimeCargoMeses = spn_timeCargoMes.getSelectedItem().toString() + "mes(es)";
                    String valorCondTrabajo = spn_condicionTrab.getSelectedItem().toString();
                    int valorGroupZonaSombra = validar.getValor2(radioGroupZonaSombra, rootView);
                    int valorGroupRotacion = validar.getValor2(radioGroupRotacion, rootView);
                    int valorGroupRecuperacion = validar.getValor2(radioGroupRecuperacion, rootView);
                    int valorGroupDispensador = validar.getValor2(radioGroupDispensador, rootView);
                    int valorGroupCapa = validar.getValor2(radioGroupCapacitacion, rootView);
                    String valorPorcActividad = spn_porcActividad.getSelectedItem().toString();
                    String valorPorDescanso = spn_porcDescanso.getSelectedItem().toString();
                    String valorVestimenta = spn_vestimenta.getSelectedItem().toString();
                    String valorMaterial = spn_materialPrenda.getSelectedItem().toString();
                    String valorColor = txt_colorPredominante.getText().toString();

                    /*String valorZapatos = String.valueOf(check_zapatos.isChecked());
                    String valorCasco = String.valueOf(check_casco.isChecked());
                    String valorLentes = String.valueOf(check_lentes.isChecked());
                    String valorGuantes = String.valueOf(check_guantes.isChecked());
                    String valorOrejeras = String.valueOf(check_orejeras.isChecked());
                    String valorTapones = String.valueOf(check_tapones.isChecked());
                    String valorCubreNuca = String.valueOf(check_cubreNuca.isChecked());*/

                    int valorZapatos = check_zapatos.isChecked() ? 1 : 0;
                    int valorCasco = check_casco.isChecked() ? 1 : 0;
                    int valorLentes = check_lentes.isChecked() ? 1 : 0;
                    int valorGuantes = check_guantes.isChecked() ? 1 : 0;
                    int valorOrejeras = check_orejeras.isChecked() ? 1 : 0;
                    int valorTapones = check_tapones.isChecked() ? 1 : 0;
                    int valorCubreNuca = check_cubreNuca.isChecked() ? 1 : 0;
                    String valorEpps = txt_otrosEpps.getText().toString();

                    String valorNom_Medicion = spn_tipoMedicion.getSelectedItem().toString();
                    int valorTipo_medicion = 0;
                    if(valorNom_Medicion.equals("Medición a una altura")){
                        valorTipo_medicion=1;
                    } else if (valorNom_Medicion.equals("Medición a tres alturas")) {
                        valorTipo_medicion=2;
                    }
                    String valorNivelDeter = spn_nivelDeterminacion.getSelectedItem().toString();
                    int valorId_Nivel_d = 0;
                    if(valorNivelDeter.equals("Observación")){
                        valorId_Nivel_d = 2;
                    }
                    int valorId_metodo_deter=0;
                    String valorTipoTrabajo = "";
                    String valorOcupacion = "";
                    String valorRangoTasaMeta = "";
                    String valorClase = "";
                    String valorActividadDeter = "";
                    String valorFrecencia = "";
                    String valorGenero = "";
                    String valorTasaMetaW = "";
                    String valorTasaMetaK = "";
                    String valorMetodoDeter = spn_metodoDeterminacion.getSelectedItem().toString();
                    if(valorMetodoDeter.equals("1A - Clasificación del tamaño de la ocupación")){
                        valorId_metodo_deter = 1;
                        valorTipoTrabajo = spn_tipoTrab.getSelectedItem().toString();
                        valorOcupacion = spn_ocupacion.getSelectedItem().toString();
                        valorRangoTasaMeta = tv_tasaMetabolica.getText().toString();
                        valorTasaMetaW = txt_tasaMetabolicaW.getText().toString();
                        valorTasaMetaK = txt_tasaMetabolicaK.getText().toString();
                    } else if (valorMetodoDeter.equals("1B - Clasificación del tamaño de la actividad")) {
                        valorId_metodo_deter = 2;
                        valorClase = spn_clase.getSelectedItem().toString();
                        valorActividadDeter = txt_actividad.getText().toString();
                        valorTasaMetaW = txt_tasaMetabolicaW.getText().toString();
                        valorTasaMetaK = txt_tasaMetabolicaK.getText().toString();
                    } else if (valorMetodoDeter.equals("Medida del ritmo cardiaco bajo condiciones determi")) {
                        valorId_metodo_deter = 4;
                        valorFrecencia = txt_frecuenciaCardiaca.getText().toString();
                        valorGenero = spn_genero.getSelectedItem().toString();
                    }
                    // Tarea 1
                    String tarea1 = "";
                    String cicloTrab1 = "";
                    String posicion1 = "";
                    String partesCuerpo1 = "";
                    String intensidad1 = "";
                    String tarea2 = "";
                    String cicloTrab2 = "";
                    String posicion2 = "";
                    String partesCuerpo2 = "";
                    String intensidad2 = "";
                    String tarea3 = "";
                    String cicloTrab3 = "";
                    String posicion3 = "";
                    String partesCuerpo3 = "";
                    String intensidad3 = "";
                    String tarea4 = "";
                    String cicloTrab4 = "";
                    String posicion4 = "";
                    String partesCuerpo4 = "";
                    String intensidad4 = "";
                    String tarea5 = "";
                    String cicloTrab5 = "";
                    String posicion5 = "";
                    String partesCuerpo5 = "";
                    String intensidad5 = "";
                    String valorMetrosSub = "";

                    if(1<=contadorTareas){
                         tarea1 = valoresTareas.get("tarea1");
                         cicloTrab1 = valoresTareas.get("cicloTrab1");
                         posicion1 = valoresTareas.get("posicion1");
                         partesCuerpo1 = valoresTareas.get("partesCuerpo1");
                         intensidad1 = valoresTareas.get("intensidad1");
                         valorMetrosSub = txt_metroSubida.getText().toString();
                    }
                    if(2<=contadorTareas){
                         tarea2 = valoresTareas.get("tarea2");
                         cicloTrab2 = valoresTareas.get("cicloTrab2");
                         posicion2 = valoresTareas.get("posicion2");
                         partesCuerpo2 = valoresTareas.get("partesCuerpo2");
                         intensidad2 = valoresTareas.get("intensidad2");
                    }
                    if(3<=contadorTareas){
                         tarea3 = valoresTareas.get("tarea3");
                         cicloTrab3 = valoresTareas.get("cicloTrab3");
                         posicion3 = valoresTareas.get("posicion3");
                         partesCuerpo3 = valoresTareas.get("partesCuerpo3");
                         intensidad3 = valoresTareas.get("intensidad3");
                    }
                    if(4<=contadorTareas){
                         tarea4 = valoresTareas.get("tarea4");
                         cicloTrab4 = valoresTareas.get("cicloTrab4");
                         posicion4 = valoresTareas.get("posicion4");
                         partesCuerpo4 = valoresTareas.get("partesCuerpo4");
                         intensidad4 = valoresTareas.get("intensidad4");
                    }
                    if(5<=contadorTareas){
                         tarea5 = valoresTareas.get("tarea5");
                         cicloTrab5 = valoresTareas.get("cicloTrab5");
                         posicion5 = valoresTareas.get("posicion5");
                         partesCuerpo5 = valoresTareas.get("partesCuerpo5");
                         intensidad5 = valoresTareas.get("intensidad5");
                    }
                    String valorT_Bulbo = txt_wbgt01.getText().toString();
                    String valorT_Bulbo2 = txt_wbgt11.getText().toString();
                    String valorT_Bulbo3 = txt_wbgt17.getText().toString();
                    String valorAire01 = txt_t_aire01.getText().toString();
                    String valorAire11 = txt_t_aire11.getText().toString();
                    String valorAire17 = txt_t_aire17.getText().toString();
                    String valorglobo01 = txt_t_globo01.getText().toString();
                    String valorglobo11 = txt_t_globo11.getText().toString();
                    String valorglobo17 = txt_t_globo17.getText().toString();
                    String valorRelativa01 = txt_h_relativa01.getText().toString();
                    String valorRelativa11 = txt_h_relativa11.getText().toString();
                    String valorRelativa17 = txt_h_relativa17.getText().toString();
                    String valorVelViento = txt_velViento.getText().toString();
                    String valorVelViento2 = txt_velViento2.getText().toString();
                    String valorVelViento3 = txt_velViento3.getText().toString();
                    String valorObserbaciones = txt_observacion.getText().toString();

                    String fecha_registro = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                    Equipos equipos1 = equipos.Buscar(valorEstresTermico);
                    Equipos equipos2 = equipos.Buscar(valorAnemometro);

                    ArrayList<HashMap<String, String>> resultList = dao_registroFormatos.getListCantidadFormatoId(Integer.parseInt(id_pt_trabajo));
                    int total_registros = dao_registroFormatos.get_cant_formato_medicion();
                    String cod_formato = config.GenerarCodigoFormato(Integer.parseInt(id_formato),resultList.size());
                    String cod_registro = config.generarCodigoRegistro(total_registros);

                    String valorRutaFoto = uri.getEncodedPath();
                    int id_plan_formato_reg = dao_registroFormatos.getRecordIdByPosition();

                    EstresTermico_Registro cabecera = new EstresTermico_Registro(
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
                            String.valueOf(equipos2.getId_equipo_registro()),
                            equipos2.getCodigo(),
                            equipos2.getNombre(),
                            equipos2.getSerie(),
                            id_colaborador,
                            nuevo.getUsuario_nombres()+ " " +nuevo.getUsuario_apater()+" "+nuevo.getUsuario_amater(),
                            valorHoraVerificacion,
                            "" +valorGroupVerificacion,
                            valorFechaMonitoreo,
                            valorHoraInicioMoni,
                            valorHoraFinalMoni,
                            valorTimeMed,
                            valorTimeExpo,
                            valorJornada,
                            valorTipoDoc,
                            valorNumDoc,
                            valorNombreTra,
                            valorPuestoTra,
                            valorAreaTra,
                            valorActividades,
                            valorPeso,
                            valorEdad,
                            valorHorarioTrabajo,
                            valorRefrigerio,
                            valorRegimen,
                            valorDesTrabajo,
                            valorDesTrabDetalle,
                            "" +valorGroupIng,
                            valorControlIng,
                            "false",
                            valorTimeCargoAnyo,
                            valorTimeCargoMeses,
                            valorCondTrabajo,
                            valorObserbaciones,
                            valorTipo_medicion,
                            valorNom_Medicion,
                            fecha_registro,
                            id_colaborador,
                            valorRutaFoto
                    );
                    EstresTermico_RegistroDetalle detalle = new EstresTermico_RegistroDetalle(
                            (id_plan_formato_reg+1),
                            valorFuenteGen,
                            valorDesFuenteGen,
                            "" +valorGroupZonaSombra,
                            "" +valorGroupRotacion,
                            "" +valorGroupRecuperacion,
                            "" +valorGroupDispensador,
                            "" +valorGroupCapa,
                            valorPorcActividad,
                            valorPorDescanso,
                            valorVestimenta,
                            valorMaterial,
                            valorColor,
                            valorZapatos,
                            valorCasco,
                            valorLentes,
                            valorGuantes,
                            valorOrejeras,
                            valorTapones,
                            valorCubreNuca,
                            valorEpps,
                            valorId_Nivel_d,
                            valorNivelDeter,
                            valorId_metodo_deter,
                            valorMetodoDeter,
                            valorTipoTrabajo,
                            valorOcupacion,
                            valorRangoTasaMeta,
                            valorClase,
                            valorActividadDeter,
                            valorTasaMetaW,
                            valorTasaMetaK,
                            valorFrecencia,
                            valorGenero,
                            ""+contadorTareas,
                            tarea1,
                            cicloTrab1,
                            posicion1,
                            partesCuerpo1,
                            intensidad1,
                            tarea2,
                            cicloTrab2,
                            posicion2,
                            partesCuerpo2,
                            intensidad2,
                            tarea3,
                            cicloTrab3,
                            posicion3,
                            partesCuerpo3,
                            intensidad3,
                            tarea4,
                            cicloTrab4,
                            posicion4,
                            partesCuerpo4,
                            intensidad4,
                            tarea5,
                            cicloTrab5,
                            posicion5,
                            partesCuerpo5,
                            intensidad5,
                            valorMetrosSub,
                            valorT_Bulbo,
                            valorT_Bulbo2,
                            valorT_Bulbo3,
                            valorAire01,
                            valorAire11,
                            valorAire17,
                            valorglobo01,
                            valorglobo11,
                            valorglobo17,
                            valorRelativa01,
                            valorRelativa11,
                            valorRelativa17,
                            valorVelViento,
                            valorVelViento2,
                            valorVelViento3,
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

                        Call<ResponseBody> call1 = service1.insertEstresTermico(json);//INSERT A ESTRES TERMICO
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
                        DAO_RegistroEstreTermico nuevoRegistro = new DAO_RegistroEstreTermico(getActivity());
                        nuevoRegistro.RegistroEstresTermico(cabecera);
                        nuevoRegistro.RegistrarEstresTermicoDetalle(detalle);

                        DAO_FormatosTrabajo dao_fromatosTrabajo = new DAO_FormatosTrabajo(getActivity());
                        for_Estres = dao_fromatosTrabajo.Buscar(id_plan_trabajo,id_formato);
                        for_Estres.setRealizado(for_Estres.getRealizado()+1);
                        for_Estres.setPor_realizar(for_Estres.getPor_realizar()-1);

                        dao_fromatosTrabajo.actualizarFormatoTrabajo(for_Estres);


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
        tv_nombreUsuario = view.findViewById(R.id.tv_nomUsuario);
        tv_nomEmpresa = view.findViewById(R.id.tv_nomEmpresa);

        tv_estresTermico = view.findViewById(R.id.tv_estresTermico);
        tv_anemometro = view.findViewById(R.id.tv_anemometro);
        tv_horaVerificacion = view.findViewById(R.id.tv_horaVerificacion);
        radioGroupVerificacion = view.findViewById(R.id.radioGroupVerificacion);
        btn_subirFotoEstres = view.findViewById(R.id.btn_subirFotoEstres);
        imgE_Termico = view.findViewById(R.id.img_Estres);
        tv_fechaMonitoreo = view.findViewById(R.id.tv_fechaMonitoreo);
        tv_horaInicioMoni = view.findViewById(R.id.tv_horaInicioMoni);
        tv_horaFinalMoni = view.findViewById(R.id.tv_horaFinalMoni);
        txt_timeMedMin = view.findViewById(R.id.txt_timeMedMin);
        txt_timeExpoHora = view.findViewById(R.id.txt_timeExpoHora);
        txt_jornadaTrabajo = view.findViewById(R.id.txt_jornadaTrabajo);
        spn_tipoDoc = view.findViewById(R.id.spn_tipoDoc);
        txt_numDoc = view.findViewById(R.id.txt_numDoc);
        txt_nomTrabajador = view.findViewById(R.id.txt_nomTrabajador);
        txt_edad = view.findViewById(R.id.txt_edad);
        txt_peso = view.findViewById(R.id.txt_peso);
        txt_areaTrabajo = view.findViewById(R.id.txt_areaTrabajo);
        txt_puestoTrabajo = view.findViewById(R.id.txt_puestoTrabajo);
        txt_aRealizada = view.findViewById(R.id.txt_aRealizada);
        spn_horarioTrabajo = view.findViewById(R.id.cbx_horarioTrabajo);
        spn_regimen = view.findViewById(R.id.cbx_regimen);
        spn_horarioRefrigerio = view.findViewById(R.id.cbx_refrigerio);
        spn_fuenteGen = view.findViewById(R.id.spn_fuenteGen);
        txt_descFuenteGen = view.findViewById(R.id.txt_descFuenteGen);
        spn_descTrabajo = view.findViewById(R.id.spn_descTrabajo);
        tv_desTrabajoDetalle = view.findViewById(R.id.tv_desTrabajoDetalle);

        radioGroupIng = view.findViewById(R.id.radioGroupIng);
        radio_ingenierSI = view.findViewById(R.id.radioIngenieriaSi);

        txt_nomControl = view.findViewById(R.id.txt_nomControl);
        spn_timeCargoAnyo = view.findViewById(R.id.spn_timeCargoAnyo);
        spn_timeCargoMes = view.findViewById(R.id.spn_timeCargoMes);
        spn_condicionTrab = view.findViewById(R.id.spn_condicionTrab);
        radioGroupZonaSombra = view.findViewById(R.id.radioGroupZonaSombra);
        radioGroupRotacion = view.findViewById(R.id.radioGroupRotacion);
        radioGroupRecuperacion = view.findViewById(R.id.radioGroupRecuperacion);
        radioGroupDispensador = view.findViewById(R.id.radioGroupDispensador);
        radioGroupCapacitacion = view.findViewById(R.id.radioGroupCapacitacion);
        spn_porcActividad = view.findViewById(R.id.spn_porcActividad);
        spn_porcDescanso = view.findViewById(R.id.spn_porcDescanso);
        spn_vestimenta = view.findViewById(R.id.spn_vestimenta);
        spn_materialPrenda = view.findViewById(R.id.spn_materialPrenda);
        txt_colorPredominante = view.findViewById(R.id.txt_colorPredominante);
        check_zapatos = view.findViewById(R.id.check_zapatos);
        check_casco = view.findViewById(R.id.check_casco);
        check_lentes = view.findViewById(R.id.check_lentes);
        check_guantes = view.findViewById(R.id.check_guantes);
        check_orejeras = view.findViewById(R.id.check_orejeras);
        check_tapones = view.findViewById(R.id.check_tapones);
        check_cubreNuca = view.findViewById(R.id.check_cubreNuca);
        txt_otrosEpps = view.findViewById(R.id.txt_otrosEpps);
        /*txt_nomTarea1 = view.findViewById(R.id.txt_tarea1);
        txt_cicloTrab1 = view.findViewById(R.id.txt_cicloTrabajo);
        spn_posicion1 = view.findViewById(R.id.cbx_posicion);
        spn_pCuerpo1 = view.findViewById(R.id.cbx_partesCuerpo);
        spn_intesidad1 = view.findViewById(R.id.cbx_intensidad);
        txt_nomTarea2 = view.findViewById(R.id.txt_tarea1);
        txt_cicloTrab2 = view.findViewById(R.id.txt_cicloTrabajo);
        spn_posicion2 = view.findViewById(R.id.cbx_posicion);
        spn_pCuerpo2 = view.findViewById(R.id.cbx_partesCuerpo);
        spn_intesidad2 = view.findViewById(R.id.cbx_intensidad);
        txt_nomTarea3 = view.findViewById(R.id.txt_tarea1);
        txt_cicloTrab3 = view.findViewById(R.id.txt_cicloTrabajo);
        spn_posicion3 = view.findViewById(R.id.cbx_posicion);
        spn_pCuerpo3 = view.findViewById(R.id.cbx_partesCuerpo);
        spn_intesidad3 = view.findViewById(R.id.cbx_intensidad);*/
        txt_metroSubida = view.findViewById(R.id.txt_metrosSubida);
        txt_wbgt01 = view.findViewById(R.id.txt_wbgt01);
        txt_wbgt11 = view.findViewById(R.id.txt_wbgt11);
        txt_wbgt17 = view.findViewById(R.id.txt_wbgt17);
        txt_t_aire01 = view.findViewById(R.id.txt_t_aire01);
        txt_t_aire11 = view.findViewById(R.id.txt_t_aire11);
        txt_t_aire17 = view.findViewById(R.id.txt_t_aire17);
        txt_t_globo01 = view.findViewById(R.id.txt_t_globo01);
        txt_t_globo11 = view.findViewById(R.id.txt_t_globo11);
        txt_t_globo17 = view.findViewById(R.id.txt_t_globo17);
        txt_h_relativa01 = view.findViewById(R.id.txt_h_relativa01);
        txt_h_relativa11 = view.findViewById(R.id.txt_h_relativa11);
        txt_h_relativa17 = view.findViewById(R.id.txt_h_relativa17);
        txt_velViento = view.findViewById(R.id.txt_velViento);
        txt_velViento2 = view.findViewById(R.id.txt_velViento2);
        txt_velViento3 = view.findViewById(R.id.txt_velViento3);
        txt_observacion = view.findViewById(R.id.txt_observaciones);

        btn_guardar = view.findViewById(R.id.fabGuardar);
        btnCancelar = view.findViewById(R.id.fabCancelar);

        linearOtroHorario = view.findViewById(R.id.linearOtroHorario);
        txt_otroHorario = view.findViewById(R.id.txt_otroHorario);
        linearOtroRegimen = view.findViewById(R.id.linearOtroRegimen);
        txt_otroRegimen = view.findViewById(R.id.txt_otroRegimen);
        linearOtroRefrigerio = view.findViewById(R.id.linearOtroRefrigerio);
        txt_otroRefrigerio = view.findViewById(R.id.txt_otroRefrigerio);

        spn_nivelDeterminacion = view.findViewById(R.id.spn_nivelDeterminacion);
        spn_metodoDeterminacion = view.findViewById(R.id.spn_metodoDeterminacion);
        spn_tipoTrab= view.findViewById(R.id.cbx_tipoTrabajo);
        spn_ocupacion = view.findViewById(R.id.cbx_ocupacion);
        tv_tasaMetabolica = view.findViewById(R.id.tv_tasaMetabolica);
        txt_tasaMetabolicaW = view.findViewById(R.id.txt_tasaMetaW);
        txt_tasaMetabolicaK = view.findViewById(R.id.txt_tasaMetaK);

        spn_clase = view.findViewById(R.id.cbx_clase);
        txt_actividad = view.findViewById(R.id.txt_actividad);
        spn_genero = view.findViewById(R.id.cbx_genero);
        txt_frecuenciaCardiaca = view.findViewById(R.id.txt_frecuenciaCardiaca);

        spn_tipoMedicion = view.findViewById(R.id.spn_tipoMedicion);

        Card_Tanteo = view.findViewById(R.id.Card_Tanteo);
        Card_Observacion = view.findViewById(R.id.Card_Observacion);
        Card_Analisis = view.findViewById(R.id.Card_Analisis);
        linear1A = view.findViewById(R.id.linear1A);
        linear1B = view.findViewById(R.id.linear1B);
        linear1_1 = view.findViewById(R.id.linear1_1);
        linear1_7 = view.findViewById(R.id.linear1_7);
        linearBuscarDni = view.findViewById(R.id.linearBuscarDni);
        btn_BuscarDni = view.findViewById(R.id.btn_BuscarDni);
        linearContenedorTareas = view.findViewById(R.id.linearContenedorTareas);
        btnAgregarTareas = view.findViewById(R.id.btn_agregar_tarea);

        card_ingenier = view.findViewById(R.id.Card_Ingenieria);


    }

    @Override
    public void onImagePicked(Uri imageUri) {
        this.uri = imageUri;
        if (imgE_Termico != null && imageUri != null) {
            imgE_Termico.setImageURI(imageUri);
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
    private void agregarNuevaTarea(InputDateConfiguration config) {
        // Inflar el diseño de la tarea y agregarlo al contenedor
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View nuevaTarea = inflater.inflate(R.layout.layout_tarea, null);
        TextView txtNumeroTarea  = nuevaTarea.findViewById(R.id.tv_nomTarea);
        Spinner spn_posicion = nuevaTarea.findViewById(R.id.cbx_posicion);
        Spinner spn_partesCuerpo = nuevaTarea.findViewById(R.id.cbx_partesCuerpo);
        Spinner spn_intensidad = nuevaTarea.findViewById(R.id.cbx_intensidad);
        AppCompatButton btn_eliminarTarea = nuevaTarea.findViewById(R.id.btn_eliminarTarea);
        spn_posicion.setAdapter(config.LlenarSpinner("posicion","nom_pos",getActivity()));
        spn_partesCuerpo.setAdapter(config.LlenarSpinner("partes_cuerpo","nom_cuerpo",getActivity()));
        spn_intensidad.setAdapter(config.LlenarSpinner("intensidad","nom_intensidad",getActivity()));

        // Hacer todos los botones de eliminar invisibles
        for (int i = 0; i < linearContenedorTareas.getChildCount(); i++) {
            View tarea = linearContenedorTareas.getChildAt(i);
            AppCompatButton btn_eliminar = tarea.findViewById(R.id.btn_eliminarTarea);
            btn_eliminar.setVisibility(View.GONE);
        }

        btn_eliminarTarea.setVisibility(View.VISIBLE);
        btn_eliminarTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contadorTareas--;
                // Eliminar la última vista
                int lastIndex = linearContenedorTareas.getChildCount() - 1;
                linearContenedorTareas.removeViewAt(lastIndex);
                if (lastIndex > 0) {
                    View ultimaTarea = linearContenedorTareas.getChildAt(lastIndex - 1);
                    AppCompatButton btn_eliminar = ultimaTarea.findViewById(R.id.btn_eliminarTarea);
                    btn_eliminar.setVisibility(View.VISIBLE);
                    if (lastIndex<5){
                        btnAgregarTareas.setVisibility(View.VISIBLE);
                    }
                }

            }
        });

        txtNumeroTarea.setText("TAREA " + contadorTareas);
        linearContenedorTareas.addView(nuevaTarea);
    }
    private void obtenerValoresTareas() {
        // Recorrer todas las tareas en el contenedor
        for (int i = 0; i < linearContenedorTareas.getChildCount(); i++) {
            View tarea = linearContenedorTareas.getChildAt(i);

            // Obtener valores de elementos dentro de la tarea
            TextView txt_tarea = tarea.findViewById(R.id.txt_tarea);
            EditText txt_cicloTrabajo = tarea.findViewById(R.id.txt_cicloTrabajo);
            Spinner spn_posicion = tarea.findViewById(R.id.cbx_posicion);
            Spinner spn_partesCuerpo = tarea.findViewById(R.id.cbx_partesCuerpo);
            Spinner spn_intensidad = tarea.findViewById(R.id.cbx_intensidad);


            // Obtener los valores específicos que necesitas
            String valorNomTarea = txt_tarea.getText().toString();
            String valorCicloTrabajo = txt_cicloTrabajo.getText().toString();
            String valorPosicion = spn_posicion.getSelectedItem().toString();
            String valorPartesCuerpo = spn_partesCuerpo.getSelectedItem().toString();
            String valorIntensidad = spn_intensidad.getSelectedItem().toString();

            // Hacer algo con los valores (por ejemplo, imprimirlos)
            valoresTareas.put("tarea"+(i+1) , valorNomTarea);
            valoresTareas.put("cicloTrab" +""+(i+1) , valorCicloTrabajo);
            valoresTareas.put("posicion" +""+(i+1) , valorPosicion);
            valoresTareas.put("partesCuerpo" +""+(i+1) , valorPartesCuerpo);
            valoresTareas.put("intensidad" +""+(i+1) , valorIntensidad);
        }
    }
}