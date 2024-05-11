package com.mijael.mein;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.mijael.mein.DAO.DAO_RegistroConfort;
import com.mijael.mein.DAO.DAO_RegistroEstresFrio;
import com.mijael.mein.DAO.DAO_RegistroFormatos;
import com.mijael.mein.DAO.DAO_Usuario;
import com.mijael.mein.Entidades.Confort_Registro;
import com.mijael.mein.Entidades.Confort_RegistroDetalle;
import com.mijael.mein.Entidades.Equipos;
import com.mijael.mein.Entidades.EstresFrio_Registro;
import com.mijael.mein.Entidades.EstresFrio_RegistroDetalle;
import com.mijael.mein.Entidades.Formatos_Trabajo;
import com.mijael.mein.Entidades.RegistroFormatos;
import com.mijael.mein.Entidades.RegistroFormatos_Detalle;
import com.mijael.mein.Entidades.Usuario;
import com.mijael.mein.Extras.FragmentoImagen;
import com.mijael.mein.Extras.InputDateConfiguration;
import com.mijael.mein.Extras.Validaciones;
import com.mijael.mein.SERVICIOS.DosimetriaService;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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


public class ConfortTermicoFragment extends Fragment implements FragmentoImagen.ImagePickerListener {
    private boolean cargarImagen = false;
    private int contadorTareas = 0;
    View rootView;
    TextView tv_nombreUsuario, tv_nomEmpresa;
    String id_plan_trabajo, id_pt_trabajo, id_formato,id_colaborador, nom_Empresa;
    String[] arrayYN,arrayFuent,arrayDesc,arrayCond, arrayNivel, arrayTanteo, arrayObs, arrayAnalisis,arrayClase,arrayTipTrab,
            arrayGenero,arrayTipMed,arraySINO;
    HashMap<String, String[]> ocupacionesPorTipoTrabajo;
    CardView Card_Tanteo, Card_Observacion, Card_Analisis;

    // Spinners
    AutoCompleteTextView spn_equipoConfort, spn_equipoAnemometro;
    Spinner spn_tipoDoc, spn_horarioTrabajo, spn_horarioRefrig, spn_regimen, spn_fuenteGen,spn_tipoTrab, spn_clase, spn_genero, spn_ocupacion,
            spn_nivelDeterminacion, spn_metodoDeterminacion, spn_tiempoCargoAnios,
            spn_tiempoCargoMeses;

    // TextViews
    TextView tv_horaVerificacion, tv_fechaMonitoreo, tv_horaInicioMoni, tv_horaFinalMoni, tv_tasaMetabolica,
            txt_t_aire, txt_t_aireNegro, txt_bulboHumedo, txt_humedadRelativa, txt_velViento, txt_observaciones,
            txt_otroHorario, txt_otroRegimen, txt_otroRefrigerio;

    // RadioGroup
    RadioGroup radioGroupVerificacion, radioGroup_Atuendo;

    // EditTexts
    EditText txt_timeMed, txt_timeExpo, txt_jornada, txt_numDoc, txt_nomTrab, txt_areaTrab,txt_tasaMetabolicaW, txt_tasaMetabolicaK,txt_frecuenciaCardiaca,
            txt_puestoTrab, txt_actRealizada, txt_pesoTrab, txt_edadTrab, txt_descFuenteGen,txt_actividad, txt_metroSubida;

    // CheckBoxes
    CheckBox check_desnudo, check_ligero, check_medio, check_pesado;

    // Buttons
    Button btnAgregarTareas;
    AppCompatButton btnSubirFotoConfort, btn_BuscarDni;
    FloatingActionButton btn_guardar;
    ExtendedFloatingActionButton btnCancelar;

    // LinearLayouts
    LinearLayout linearOtroHorario, linearOtroRegimen, linearOtroRefrigerio, linearBuscarDni, linear1A, linear1B, linear1_1, linear1_7, linearContenedorTareas;
    ImageView imgConfort;
    Uri uri;
    DAO_RegistroFormatos dao_registroFormatos;
    RegistroFormatos registros;
    RegistroFormatos_Detalle detalles;
    public ConfortTermicoFragment() {
        // Required empty public constructor
        dao_registroFormatos = new DAO_RegistroFormatos(getActivity());
    }
    Formatos_Trabajo for_confort;
    Validaciones validar = new Validaciones();
    InputDateConfiguration config;
    Map<String, String> valoresTareas = new HashMap<>();
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
        rootView = inflater.inflate(R.layout.fragment_confort_termico,container,false);
        config = new InputDateConfiguration(getActivity(),id_colaborador,nom_Empresa,rootView);
        init(rootView);

        DAO_Equipos equipos = new DAO_Equipos(getActivity());
        List<String> lista_CodEquipos = equipos.obtener_CodEquipos();
        DAO_Usuario usuario = new DAO_Usuario(getActivity());
        Usuario nuevo = usuario.BuscarUsuario(Integer.parseInt(id_colaborador));

        arrayYN = new String[]{"DNI","CE"};
        arrayFuent = new String[]{"Natural","Artificial","Natural - artificial"};
        arrayDesc = new String[]{"Trabajo al aire libre sin carga solar o bajo techo", "Trabajo al aire libre con carga solar"};//
        arrayCond = new String[]{"Aclimatado", "No aclimatado"};//
        arrayNivel = new String[]{"Tanteo", "Observación","Análisis"};
        arrayTanteo = new String[]{"1A - Clasificación del tamaño de la ocupación", "1B - Clasificación del tamaño de la actividad"};
        arrayObs = new String[]{"Tablas para actividades específicas"};
        arrayAnalisis = new String[]{"Medida del ritmo cardiaco bajo condiciones determi"};
        arrayTipMed = new String[]{"Medición a una altura","Medición a tres alturas"};//
        arrayGenero = new String[]{"Hombre","Mujer"};
        arrayTipTrab = new String[]{"Oficina","Artesanos","Minería","Industria","Artes","Agricultura","Transporte","Diversos"};
        arrayClase = new String[]{"Descanso","Tasa metabólica baja","Tasa metabólica moderada","Tasa metabólica alta","Tasa metabólica muy alta"};
        arraySINO = new String[]{"SI","NO"};//

        config.ConfigPantalla();
        config.configurarAutoCompleteTextView(spn_equipoConfort,lista_CodEquipos);
        config.configurarAutoCompleteTextView(spn_equipoAnemometro,lista_CodEquipos);
        tv_horaVerificacion.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {config.showTimePickerDialog(rootView,tv_horaVerificacion);}});
        tv_fechaMonitoreo.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {config.showDatePickerDialog(rootView,tv_fechaMonitoreo);}});
        tv_horaInicioMoni.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {config.showTimePickerDialog(rootView,tv_horaInicioMoni);}});
        tv_horaFinalMoni.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {config.showTimePickerDialog(rootView,tv_horaFinalMoni);}});
        btnSubirFotoConfort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentoImagen fragmentoImagen = new FragmentoImagen(ConfortTermicoFragment.this);
                fragmentoImagen.show(getActivity().getSupportFragmentManager(), "imagePicker");
                cargarImagen = true;
            }
        });
        spn_tipoDoc.setAdapter(config.LlenarSpinner(arrayYN));
        spn_horarioTrabajo.setAdapter(config.LlenarSpinner("horario_trab_fromato_medicion","desc_horario",getActivity()));
        spn_regimen.setAdapter(config.LlenarSpinner("regimen_formato_medicion","nom_regimen",getActivity()));
        spn_horarioRefrig.setAdapter(config.LlenarSpinner("horario_refrig_formato_medicion","nom_horario",getActivity()));
        spn_fuenteGen.setAdapter(config.LlenarSpinner(arrayFuent));
        config.llenarSpinnerConNumeros(spn_tiempoCargoAnios,10,getActivity());
        config.llenarSpinnerConNumeros(spn_tiempoCargoMeses,11,getActivity());

        spn_nivelDeterminacion.setAdapter(config.LlenarSpinner(arrayNivel));
        //spn_metodoDeterminacion.setAdapter(config.LlenarSpinner(new String[]{"Opcion 1", "Opcion 2", "etc"})); //NO SEVISUALIZA CAMPOS EN WEB
        spn_tipoTrab.setAdapter(config.LlenarSpinner(arrayTipTrab));
        spn_clase.setAdapter(config.LlenarSpinner(arrayClase));
        spn_genero.setAdapter(config.LlenarSpinner(arrayGenero));

        config.MostrarCampos(linearOtroHorario,spn_horarioTrabajo);
        config.MostrarCampos(linearOtroRegimen,spn_regimen);
        config.MostrarCampos(linearOtroRefrigerio,spn_horarioRefrig);

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
                    spn_metodoDeterminacion.setAdapter(config.LlenarSpinner(arrayTanteo));
                    if(registros!=null){
                        ActualizarTanteo();
                    }
                } else if (seleccion.equals("Observación")) {
                    spn_metodoDeterminacion.setAdapter(config.LlenarSpinner(arrayObs));
                    if(registros!=null){
                        ActualizarObservacion();
                    }
                } else if (seleccion.equals("Análisis")){
                    spn_metodoDeterminacion.setAdapter(config.LlenarSpinner(arrayAnalisis));
                    if(registros!=null){
                        ActualizarAnalisis();
                    }
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
                    if(registros!=null){
                        for(int i = 0; i<Integer.parseInt(detalles.getNtareas());i++){
                            agregarNuevaTarea(config,(i+1));
                        }
                    }
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
        btnAgregarTareas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contadorTareas++;
                if (contadorTareas <= 5) {
                    if(contadorTareas==5){
                        btnAgregarTareas.setVisibility(View.GONE);
                    }
                    agregarNuevaTarea(config,contadorTareas);

                } else {
                    btnAgregarTareas.setVisibility(View.GONE);
                }
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
                    txt_nomTrab.setText("");
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
                    config.buscarTrabajador(dni,txt_nomTrab);
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
                        validar.validarCampoObligatorio(spn_equipoConfort) &&
                                validar.validarCampoObligatorio(spn_equipoAnemometro) &&
                                validar.validarCampoObligatorio(tv_horaVerificacion) &&
                                //validar.validarImagen(cargarImagen,getActivity()) &&
                                validar.validarCampoObligatorio(tv_fechaMonitoreo) &&
                                validar.validarCampoObligatorio(tv_horaInicioMoni) &&
                                //validar.validarCampoObligatorio(tv_horaFinalMoni) &&
                                validar.validarCampoObligatorio(txt_timeMed) &&
                                validar.validarCampoObligatorio(txt_timeExpo) &&
                                validar.validarCampoObligatorio(txt_jornada) &&
                                validar.validarCampoObligatorio(spn_tipoDoc) &&
                                validar.validarCampoObligatorio(txt_numDoc) &&
                                validar.validarCampoObligatorio(txt_nomTrab) &&
                                validar.validarCampoObligatorio(txt_edadTrab) &&
                                validar.validarCampoObligatorio(txt_pesoTrab) &&
                                validar.validarCampoObligatorio(txt_areaTrab) &&
                                validar.validarCampoObligatorio(txt_puestoTrab) &&
                                validar.validarCampoObligatorio(txt_actRealizada )&&
                                validar.validarCampoObligatorio(spn_horarioTrabajo) &&
                                validar.validarCampoObligatorio(spn_regimen) &&
                                validar.validarCampoObligatorio(spn_horarioRefrig) &&
                                validar.validarCampoObligatorio(spn_fuenteGen) &&
                                validar.validarCampoObligatorio(txt_descFuenteGen) &&
                                validar.validarCampoObligatorio(spn_tiempoCargoAnios) &&
                                validar.validarCampoObligatorio(spn_tiempoCargoMeses) &&
                                validar.validarCampoObligatorio(spn_nivelDeterminacion) &&
                                validar.validarCampoObligatorio(spn_metodoDeterminacion) &&
                                validar.validarCampoObligatorio(txt_t_aire) &&
                                validar.validarCampoObligatorio(txt_t_aireNegro) &&
                                validar.validarCampoObligatorio(txt_humedadRelativa) &&
                                validar.validarCampoObligatorio(txt_velViento)
                                //validar.validarCampoObligatorio(txt_observaciones)
                ){*/
                    String valorConfort = spn_equipoConfort.getText().toString();
                    String valorAnemometro = spn_equipoAnemometro.getText().toString();
                    String valorHoraVerificacion = tv_horaVerificacion.getText().toString();
                    int valorGroupVerificacion = validar.getValor2(radioGroupVerificacion,rootView);
                    String f = tv_fechaMonitoreo.getText().toString();
                    String valorFechaMonitoreo = config.convertirFecha(f);
                    String valorHoraInicioMoni = tv_horaInicioMoni.getText().toString();
                    String valorHoraFinalMoni = tv_horaFinalMoni.getText().toString();
                    String valorTimeMed = txt_timeMed.getText().toString();
                    String valorTimeExpo = txt_timeExpo.getText().toString();
                    String valorJornada = txt_jornada.getText().toString();
                    String valorTipoDoc = spn_tipoDoc.getSelectedItem().toString();
                    String valorNumDoc = txt_numDoc.getText().toString();
                    String valorNombreTra = txt_nomTrab.getText().toString();
                    String valorEdad = txt_edadTrab.getText().toString();
                    String valorPeso = txt_pesoTrab.getText().toString();
                    String valorAreaTra = txt_areaTrab.getText().toString();
                    String valorPuestoTra = txt_puestoTrab.getText().toString();
                    String valorActividades = txt_actRealizada.getText().toString();

                    String valorHorarioTrabajo = spn_horarioTrabajo.getSelectedItem().toString();
                    String valorRegimen = spn_regimen.getSelectedItem().toString();
                    String valorRefrigerio = spn_horarioRefrig.getSelectedItem().toString();

                    if(valorHorarioTrabajo.equals("OTRO")) valorHorarioTrabajo = txt_otroHorario.getText().toString();
                    if(valorRegimen.equals("OTRO")) valorRegimen = txt_otroRegimen.getText().toString();
                    if(valorRefrigerio.equals("OTRO")) valorRefrigerio = txt_otroRefrigerio.getText().toString();

                    String valorFuenteGen = spn_fuenteGen.getSelectedItem().toString();
                    String valorDesFuenteGen = txt_descFuenteGen.getText().toString();

                    int valorDesc_atuendo = validar.getValorTag(radioGroup_Atuendo);


                    /*String valorcheck_desnudo = String.valueOf(check_desnudo.isChecked());
                    String valorcheck_ligero = String.valueOf(check_ligero.isChecked());
                    String valorcheck_medio= String.valueOf(check_medio.isChecked());
                    String valorcheck_pesado = String.valueOf(check_pesado.isChecked());*/

                    String valorTimeCargoAnyo = spn_tiempoCargoAnios.getSelectedItem().toString();
                    String valorTimeCargoMeses = spn_tiempoCargoMeses.getSelectedItem().toString();

                    String valorNivelDeterminacion = spn_nivelDeterminacion.getSelectedItem().toString();
                    int valorId_Nivel_d = 0;
                    if(valorNivelDeterminacion.equals("Observación")){
                        valorId_Nivel_d = 2;
                    }
                    String valorMetodoDeterminacion = spn_metodoDeterminacion.getSelectedItem().toString();
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
                    String valorMetodoDeter = "";

                    if(spn_metodoDeterminacion.getSelectedItem()!=null){
                        valorMetodoDeter = spn_metodoDeterminacion.getSelectedItem().toString();
                    }
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


                    obtenerValoresTareas();
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

                    String valorAire = txt_t_aire.getText().toString();
                    String valorTAireNegro = txt_t_aireNegro.getText().toString();
                    String valorBulboHumedo = txt_bulboHumedo.getText().toString();
                    String valorHumedadRelativa = txt_humedadRelativa.getText().toString();
                    String valorVelViento = txt_velViento.getText().toString();
                    String valorObserbaciones = txt_observaciones.getText().toString();

                    String estado_resultado = "1";

                    Equipos equipo1 = equipos.Buscar(valorConfort);
                    Equipos equipo2 = equipos.Buscar(valorAnemometro);


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

                    obtenerValoresTareas();

                    Confort_Registro cabecera = new Confort_Registro(
                            -1,
                            cod_formato,
                            cod_registro,
                            id_formato,
                            id_plan_trabajo,
                            id_pt_trabajo,
                            String.valueOf(equipo1.getId_equipo_registro()),
                            equipo1.getCodigo(),
                            equipo1.getNombre(),
                            equipo1.getSerie(),
                            String.valueOf(equipo2.getId_equipo_registro()),
                            equipo2.getCodigo(),
                            equipo2.getNombre(),
                            equipo2.getSerie(),
                            id_colaborador,
                            nuevo.getUsuario_nombres()+ " " +nuevo.getUsuario_apater()+" "+nuevo.getUsuario_amater(),
                            valorHoraVerificacion,
                            ""+valorGroupVerificacion,
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
                            valorTimeCargoAnyo,
                            valorTimeCargoMeses,
                            valorObserbaciones,
                            fecha_registro,
                            id_colaborador,
                            valorRutaFoto
                    );

                    Confort_RegistroDetalle detalle = new Confort_RegistroDetalle(
                            id_plan_formato_reg,
                            valorFuenteGen,
                            valorDesFuenteGen,
                            ""+valorDesc_atuendo,
                            valorId_Nivel_d,
                            valorNivelDeterminacion,
                            valorId_metodo_deter,
                            valorMetodoDeterminacion,
                            0,
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
                            valorAire,
                            valorTAireNegro,
                            valorBulboHumedo,
                            valorHumedadRelativa,
                            valorVelViento,
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

                        Call<ResponseBody> call1 = service1.insertConfort(json);//INSERT A CONFORT TERMICO
                        call1.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                Log.e("exitoso", "se inserto el registro");
                                if(uri!=null){
                                    File imageFile = new File(uri.getEncodedPath());
                                    config.uploadImage(imageFile, cod_formato,id_pt_trabajo,cod_registro);
                                }
                                // Mostrar el JSON en el log
                                Log.e("JSON", cadenaJson);
                                String responseBody = null;
                                try {
                                    responseBody = response.body() != null ? response.body().string(): "No hay contenido en la respuesta";
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                // Muestra el mensaje de respuesta del servidor
                                Log.i("Upload", "Respuesta del servidor: " + responseBody);
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
                        DAO_RegistroConfort nuevoRegistro = new DAO_RegistroConfort(getActivity());
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Guardar formulario");
                        builder.setMessage("¿Deseas seguir llenando el formulario o terminar?");

                        builder.setPositiveButton("Seguir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(registros == null){
                                    nuevoRegistro.InsertCabecera(cabecera);
                                    nuevoRegistro.InsertDetalle(detalle);
                                    DAO_FormatosTrabajo dao_fromatosTrabajo = new DAO_FormatosTrabajo(getActivity());
                                    for_confort = dao_fromatosTrabajo.Buscar(id_plan_trabajo,id_formato);
                                    for_confort.setRealizado(for_confort.getRealizado()+1);
                                    for_confort.setPor_realizar(for_confort.getPor_realizar()-1);
                                    dao_fromatosTrabajo.actualizarFormatoTrabajo(for_confort);
                                }else{
                                    nuevoRegistro.ActualizarConfort(cabecera,registros.getId_plan_trabajo_formato_reg());
                                    nuevoRegistro.ActualizarConfort_Detalle(detalle,detalles.getId_formato_reg_detalle());
                                }
                            }
                        });
                        builder.setNegativeButton("Terminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(registros == null){
                                    nuevoRegistro.InsertCabecera(cabecera);
                                    nuevoRegistro.InsertDetalle(detalle);
                                    DAO_FormatosTrabajo dao_fromatosTrabajo = new DAO_FormatosTrabajo(getActivity());
                                    for_confort = dao_fromatosTrabajo.Buscar(id_plan_trabajo,id_formato);
                                    for_confort.setRealizado(for_confort.getRealizado()+1);
                                    for_confort.setPor_realizar(for_confort.getPor_realizar()-1);
                                    dao_fromatosTrabajo.actualizarFormatoTrabajo(for_confort);
                                }else{
                                    nuevoRegistro.ActualizarConfort(cabecera,registros.getId_plan_trabajo_formato_reg());
                                    nuevoRegistro.ActualizarConfort_Detalle(detalle,detalles.getId_formato_reg_detalle());
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
    private void init(View view){
        tv_nombreUsuario = view.findViewById(R.id.tv_nomUsuario);
        tv_nomEmpresa = view.findViewById(R.id.tv_nomEmpresa);

        // Asignación de elementos de la interfaz a variables
        spn_equipoConfort = view.findViewById(R.id.tv_confort);
        spn_equipoAnemometro = view.findViewById(R.id.tv_anemometro);
        tv_horaVerificacion = view.findViewById(R.id.tv_horaVerificacion);
        radioGroupVerificacion = view.findViewById(R.id.radioGroup_verf_insitu);
        btnSubirFotoConfort= view.findViewById(R.id.btn_subirFotoConfort);
        imgConfort = view.findViewById(R.id.img_Confort);
        tv_fechaMonitoreo = view.findViewById(R.id.tv_fechaMonitoreo);
        tv_horaInicioMoni = view.findViewById(R.id.tv_horaInicial);
        tv_horaFinalMoni = view.findViewById(R.id.tv_horaFinal);
        txt_timeMed = view.findViewById(R.id.txt_timeMedHora);
        txt_timeExpo = view.findViewById(R.id.txt_timeExpoHora);
        txt_jornada = view.findViewById(R.id.txt_jornada);
        spn_tipoDoc = view.findViewById(R.id.spn_tipoDoc);
        txt_numDoc = view.findViewById(R.id.txt_numDoc);
        txt_nomTrab = view.findViewById(R.id.txt_nomTrabajador);
        txt_areaTrab = view.findViewById(R.id.txt_areaTrabajo);
        txt_puestoTrab = view.findViewById(R.id.txt_puestoTrabajo);
        txt_actRealizada = view.findViewById(R.id.txt_aRealizada);
        txt_pesoTrab = view.findViewById(R.id.txt_peso);
        txt_edadTrab = view.findViewById(R.id.txt_edad);
        spn_horarioTrabajo = view.findViewById(R.id.cbx_horarioTrabajo);
        spn_horarioRefrig = view.findViewById(R.id.cbx_refrigerio);
        spn_regimen = view.findViewById(R.id.cbx_regimen);
        spn_fuenteGen = view.findViewById(R.id.spn_fuenteGen);
        txt_descFuenteGen = view.findViewById(R.id.txt_descFuenteGen);



        radioGroup_Atuendo = view.findViewById(R.id.radioGroup_Atuendo);

        spn_tiempoCargoAnios = view.findViewById(R.id.cbx_tiempoCargoAnios);
        spn_tiempoCargoMeses = view.findViewById(R.id.cbx_tiempoCargoMeses);
        //spn_condicionTrab = view.findViewById(R.id.spn_condicionTrab);

        spn_nivelDeterminacion = view.findViewById(R.id.spn_nivelDeterminacion);
        spn_metodoDeterminacion = view.findViewById(R.id.spn_metodoDeterminacion);
        txt_t_aire = view.findViewById(R.id.txt_t_aire);
        txt_t_aireNegro = view.findViewById(R.id.txt_t_aireNegro);
        txt_bulboHumedo = view.findViewById(R.id.txt_t_bulboHumedo);
        txt_humedadRelativa = view.findViewById(R.id.txt_humedadRelativa);
        txt_velViento = view.findViewById(R.id.txt_velViento);
        txt_observaciones = view.findViewById(R.id.txt_observaciones);

        spn_tipoTrab= view.findViewById(R.id.cbx_tipoTrabajo);
        spn_ocupacion = view.findViewById(R.id.cbx_ocupacion);
        tv_tasaMetabolica = view.findViewById(R.id.tv_tasaMetabolica);
        txt_tasaMetabolicaW = view.findViewById(R.id.txt_tasaMetaW);
        txt_tasaMetabolicaK = view.findViewById(R.id.txt_tasaMetaK);

        spn_clase = view.findViewById(R.id.cbx_clase);
        txt_actividad = view.findViewById(R.id.txt_actividad);
        spn_genero = view.findViewById(R.id.cbx_genero);
        txt_frecuenciaCardiaca = view.findViewById(R.id.txt_frecuenciaCardiaca);

        btn_guardar = view.findViewById(R.id.fabGuardar);
        btnCancelar = view.findViewById(R.id.fabCancelar);

        Card_Tanteo = view.findViewById(R.id.Card_Tanteo);
        Card_Observacion = view.findViewById(R.id.Card_Observacion);
        Card_Analisis = view.findViewById(R.id.Card_Analisis);
        linear1A = view.findViewById(R.id.linear1A);
        linear1B = view.findViewById(R.id.linear1B);
        linear1_1 = view.findViewById(R.id.linear1_1);
        linear1_7 = view.findViewById(R.id.linear1_7);

        linearOtroHorario = view.findViewById(R.id.linearOtroHorario);
        txt_otroHorario = view.findViewById(R.id.txt_otroHorario);
        linearOtroRegimen = view.findViewById(R.id.linearOtroRegimen);
        txt_otroRegimen = view.findViewById(R.id.txt_otroRegimen);
        linearOtroRefrigerio = view.findViewById(R.id.linearOtroRefrigerio);
        txt_otroRefrigerio = view.findViewById(R.id.txt_otroRefrigerio);
        linearBuscarDni = view.findViewById(R.id.linearBuscarDni);
        btn_BuscarDni = view.findViewById(R.id.btn_BuscarDni);
        linearContenedorTareas = view.findViewById(R.id.linearContenedorTareas);
        btnAgregarTareas = view.findViewById(R.id.btn_agregar_tarea);
        txt_metroSubida = view.findViewById(R.id.txt_metrosSubida);

    }

    @Override
    public void onImagePicked(Uri imageUri) {
        this.uri = imageUri;
        if (imgConfort != null && imageUri != null) {
            imgConfort.setImageURI(imageUri);
            /*File imageFile = new File(imageUri.getEncodedPath());
            config.uploadImage(imageFile);*/
        }
    }
    private void agregarNuevaTarea(InputDateConfiguration config,int n) {
        // Inflar el diseño de la tarea y agregarlo al contenedor
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View nuevaTarea = inflater.inflate(R.layout.layout_tarea, null);
        TextView txtNumeroTarea  = nuevaTarea.findViewById(R.id.tv_nomTarea);
        EditText txt_nomTarea = nuevaTarea.findViewById(R.id.txt_tarea);
        EditText txt_cicloTrabajo = nuevaTarea.findViewById(R.id.txt_cicloTrabajo);
        Spinner spn_posicion = nuevaTarea.findViewById(R.id.cbx_posicion);
        Spinner spn_partesCuerpo = nuevaTarea.findViewById(R.id.cbx_partesCuerpo);
        Spinner spn_intensidad = nuevaTarea.findViewById(R.id.cbx_intensidad);
        AppCompatButton btn_eliminarTarea = nuevaTarea.findViewById(R.id.btn_eliminarTarea);
        spn_posicion.setAdapter(config.LlenarSpinner("posicion","nom_pos",getActivity()));
        spn_partesCuerpo.setAdapter(config.LlenarSpinner("partes_cuerpo","nom_cuerpo",getActivity()));
        spn_intensidad.setAdapter(config.LlenarSpinner("intensidad","nom_intensidad",getActivity()));

        if(registros!=null){
            if(n==1){
                txt_nomTarea.setText(detalles.getNom_tarea1());
                txt_cicloTrabajo.setText(detalles.getCiclo_trabajo1());
                config.asignarAdaptadorYSeleccion(spn_posicion, "posicion", "nom_pos", detalles.getPosicion_1(), getContext());
                config.asignarAdaptadorYSeleccion(spn_partesCuerpo, "partes_cuerpo", "nom_cuerpo", detalles.getPcuerpo_1(), getContext());
                config.asignarAdaptadorYSeleccion(spn_intensidad, "intensidad", "nom_intensidad", detalles.getIntensidad_1(), getContext());
            }
            if(n==2){
                txt_nomTarea.setText(detalles.getNom_tarea2());
                txt_cicloTrabajo.setText(detalles.getCiclo_trabajo2());
                config.asignarAdaptadorYSeleccion(spn_posicion, "posicion", "nom_pos", detalles.getPosicion_2(), getContext());
                config.asignarAdaptadorYSeleccion(spn_partesCuerpo, "partes_cuerpo", "nom_cuerpo", detalles.getPcuerpo_2(), getContext());
                config.asignarAdaptadorYSeleccion(spn_intensidad, "intensidad", "nom_intensidad", detalles.getIntensidad_2(), getContext());
            }
            if(n==3){
                txt_nomTarea.setText(detalles.getNom_tarea3());
                txt_cicloTrabajo.setText(detalles.getCiclo_trabajo3());
                config.asignarAdaptadorYSeleccion(spn_posicion, "posicion", "nom_pos", detalles.getPosicion_3(), getContext());
                config.asignarAdaptadorYSeleccion(spn_partesCuerpo, "partes_cuerpo", "nom_cuerpo", detalles.getPcuerpo_3(), getContext());
                config.asignarAdaptadorYSeleccion(spn_intensidad, "intensidad", "nom_intensidad", detalles.getIntensidad_3(), getContext());
            }
            if(n==4){
                txt_nomTarea.setText(detalles.getNom_tarea4());
                txt_cicloTrabajo.setText(detalles.getCiclo_trabajo4());
                config.asignarAdaptadorYSeleccion(spn_posicion, "posicion", "nom_pos", detalles.getPosicion_4(), getContext());
                config.asignarAdaptadorYSeleccion(spn_partesCuerpo, "partes_cuerpo", "nom_cuerpo", detalles.getPcuerpo_4(), getContext());
                config.asignarAdaptadorYSeleccion(spn_intensidad, "intensidad", "nom_intensidad", detalles.getIntensidad_4(), getContext());
            }
            if(n==5){
                txt_nomTarea.setText(detalles.getNom_tarea5());
                txt_cicloTrabajo.setText(detalles.getCiclo_trabajo5());
                config.asignarAdaptadorYSeleccion(spn_posicion, "posicion", "nom_pos", detalles.getPosicion_5(), getContext());
                config.asignarAdaptadorYSeleccion(spn_partesCuerpo, "partes_cuerpo", "nom_cuerpo", detalles.getPcuerpo_5(), getContext());
                config.asignarAdaptadorYSeleccion(spn_intensidad, "intensidad", "nom_intensidad", detalles.getIntensidad_5(), getContext());
            }
            txt_metroSubida.setText(detalles.getMtr_subida());

        }

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

        if(registros!=null){
            txtNumeroTarea.setText("TAREA " + n);
        }else{
            txtNumeroTarea.setText("TAREA " + contadorTareas);
        }
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
    private void EditarCampos(){
        spn_equipoConfort.setText(registros.getCod_equipo1());
        spn_equipoAnemometro.setText(registros.getCod_equipo2());
        tv_horaVerificacion.setText(registros.getHora_situ());
        if (registros.getVerf_insitu().equals("1")) {
            radioGroupVerificacion.check(R.id.verf_insitusi);
        } else {
            radioGroupVerificacion.check(R.id.verf_insituno);
        }
        if(registros.getRuta_foto()!=null) {
            imgConfort.setImageURI(Uri.parse(registros.getRuta_foto()));
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
        txt_timeMed.setText(registros.getTiempo_medicion());
        txt_timeExpo.setText(registros.getTiempo_exposicion());
        txt_jornada.setText(registros.getJornada());
        int indice1 = Arrays.asList(arrayYN).indexOf(registros.getTipo_doc_trabajador());
        spn_tipoDoc.setSelection(indice1 + 1);
        txt_numDoc.setText(registros.getNum_doc_trabajador());
        txt_nomTrab.setText(registros.getNom_trabajador());
        txt_edadTrab.setText(String.valueOf(registros.getEdad_trabajador()));
        txt_pesoTrab.setText(registros.getPeso_trabajador());
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
        int indice2 = Arrays.asList(arrayFuent).indexOf(detalles.getFuente_generadora());
        spn_fuenteGen.setSelection(indice2 +1);
        txt_descFuenteGen.setText(detalles.getDesc_fuente_frio());
        config.marcarRadioButtonPorTag(radioGroup_Atuendo,Integer.parseInt(detalles.getDesc_atuendo()));
        String idd_anio = registros.getAnio_ocu_cargo().replaceAll("\\D", "");
        String idd_mes = registros.getMes_ocu_cargo().replaceAll("\\D", "");

        if (!idd_anio.isEmpty()) {
            spn_tiempoCargoAnios.setSelection(Integer.parseInt(idd_anio));
        } else {
            spn_tiempoCargoAnios.setSelection(0);
        }
        if (!idd_mes.isEmpty()) {
            spn_tiempoCargoMeses.setSelection(Integer.parseInt(idd_mes));
        } else {
            spn_tiempoCargoMeses.setSelection(0);
        }

        int indice9 = Arrays.asList(arrayNivel).indexOf(detalles.getNom_nivel_d());
        spn_nivelDeterminacion.setSelection(indice9 +1);
        // ------------------- ACTUALIZAR METODOS DE DETERMINACION ----------------------------

        txt_t_aire.setText(detalles.getT_aire());
        txt_t_aireNegro.setText(detalles.getT_globo());
        txt_bulboHumedo.setText(detalles.getT_bulbo());
        txt_humedadRelativa.setText(detalles.getH_relativa());
        txt_velViento.setText(detalles.getV_viento());

        txt_observaciones.setText(registros.getObservacion());

    }
    private void Volver() {
        getFragmentManager().popBackStack();// Regresa al Fragment anterior
    }
    private void ActualizarObservacion(){
        int indice5 = Arrays.asList(arrayNivel).indexOf(detalles.getNom_nivel_d());
        //spn_nivelDeterminacion.setSelection(indice5 +1);
        Log.e("indice", indice5+"");

        if((indice5+1)==2){
            Log.e("tan","ENTRO A OBS");
            int in1 = Arrays.asList(arrayObs).indexOf(detalles.getMetodo_determ());
            spn_metodoDeterminacion.setSelection(in1+1);
            int nTareas= Integer.parseInt(detalles.getNtareas());
            contadorTareas = nTareas;

            txt_metroSubida.setText(detalles.getMtr_subida());
        }
    }
    private void ActualizarTanteo(){
        int indice5 = Arrays.asList(arrayNivel).indexOf(detalles.getNom_nivel_d());
        if((indice5+1)==1){
            Log.e("tan","ENTRO A TANTEOI");
            int in1 = Arrays.asList(arrayTanteo).indexOf(detalles.getMetodo_determ());
            spn_metodoDeterminacion.setSelection(in1+1);
            if((in1+1)==1){
                int in2 = Arrays.asList(arrayTipTrab).indexOf(detalles.getTipo_trabajo());
                spn_tipoTrab.setSelection(in2+1);
                String[] ocupaciones = ocupacionesPorTipoTrabajo.get(detalles.getTipo_trabajo());
                int in3 = Arrays.asList(ocupaciones).indexOf(detalles.getOcupacion());
                spn_ocupacion.setSelection(in3+1);
                tv_tasaMetabolica.setText(detalles.getRango_tasa_metab());
            }
            if((in1+1)==2){
                int in2 = Arrays.asList(arrayClase).indexOf(detalles.getClase());
                spn_clase.setSelection(in2+1);
                txt_actividad.setText(detalles.getActividad_deter());
            }
            txt_tasaMetabolicaW.setText(detalles.getTasa_metab());
            txt_tasaMetabolicaK.setText(detalles.getTasa_metab_kcal());
        }
    }
    private void ActualizarAnalisis(){
        int indice5 = Arrays.asList(arrayNivel).indexOf(detalles.getNom_nivel_d());
        if((indice5+1)==3){
            Log.e("tan","ENTRO A ANALISIS");
            int in1 = Arrays.asList(arrayAnalisis).indexOf(detalles.getMetodo_determ());
            spn_metodoDeterminacion.setSelection(in1+1);
            int in2 = Arrays.asList(arrayGenero).indexOf(detalles.getGenero_deter());
            spn_genero.setSelection(in2+1);
            Log.e("sdfdf",detalles.getFrecuencia_deter());
            txt_frecuenciaCardiaca.setText(detalles.getFrecuencia_deter());
        }
    }

}