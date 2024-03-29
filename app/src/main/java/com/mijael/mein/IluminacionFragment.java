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

import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mijael.mein.DAO.DAO_DatosLocal;
import com.mijael.mein.DAO.DAO_Equipos;
import com.mijael.mein.DAO.DAO_FormatosTrabajo;
import com.mijael.mein.DAO.DAO_RegistroFormatos;
import com.mijael.mein.DAO.DAO_RegistroIluminacion;
import com.mijael.mein.DAO.DAO_RegistroRadiacion;
import com.mijael.mein.DAO.DAO_Usuario;
import com.mijael.mein.Entidades.Equipos;
import com.mijael.mein.Entidades.Formatos_Trabajo;
import com.mijael.mein.Entidades.Iluminacion_Registro;
import com.mijael.mein.Entidades.Iluminacion_RegistroDetalle;
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
import java.util.Calendar;
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


public class IluminacionFragment extends Fragment implements FragmentoImagen.ImagePickerListener {
    private boolean cargarImagen = false;
    int hora,min;
    String id_plan_trabajo, id_pt_trabajo, id_formato,id_colaborador, nom_Empresa;
    View rootView;
    CardView Card_Puesto, Card_Area;
    TextView tv_nombreUsuario, tv_nomEmpresa;
    AutoCompleteTextView tv_luxometro;
    TextView hora_verificacion, fechaMonitoreo, hora_monitoreo, tv_altura;
    EditText ubicacionEquipo, numDoc, nomTrabajador, puestoTrabajador, areaTrabajo, numTrabajadores, nivelMinimo, numLuminarias, tipoArea,
    il1, il2, il3, il4, il5, il6, il7, il8, areaTrabajoM2, txt_altura_pTrabajo, numLamparas, altura_pLuminaria, colorPared, colorPiso, tareasRealizadas, observaciones,
    txt_otroHorario, txt_otroRegimen,
    txt_longSalon, txt_anchoSalon, txt_alt_PlanosTrabajo_ilu, txt_constanteSalon, txt_numMinPuntosMedicion, txt_largoEscri, txt_anchoEscri, txt_numPuntosMedicion;
    RadioGroup radioGroupLuminaria;
    Spinner cbx_lux, tipoDoc, horario_Trab, regimen, tareaVisual, estadoLuminarias, spn_tipoIluminacion, spn_tipoMedicion;
    Button btnSubirFotoIlu;
    FloatingActionButton btn_guardar;
    ExtendedFloatingActionButton btnCancelar;
    AppCompatButton btn_BuscarDni;
    LinearLayout linearOtroHorario, linearOtroRegimen, linearPuntosMedicion, linearBuscarDni;
    ImageView imgIliminacion;
    Uri uri;
    DAO_RegistroFormatos dao_registroFormatos;
    public IluminacionFragment() {
        // Required empty public constructor
        dao_registroFormatos = new DAO_RegistroFormatos(getContext());
    }
    Formatos_Trabajo for_Iluminacion;
    Validaciones validar = new Validaciones();
    InputDateConfiguration config;
    String valorLargoEscri, valorAnchoEscri;


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
        List<EditText> editTextList = new ArrayList<>();
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_iluminacion,container,false);
        config = new InputDateConfiguration(getActivity(),id_colaborador,nom_Empresa,rootView);
        init(rootView);

        config.ConfigPantalla();

        DAO_Equipos equipos = new DAO_Equipos(getActivity());
        List<String> lista_CodEquipos = equipos.obtener_CodEquipos();
        DAO_Usuario usuario = new DAO_Usuario(getActivity());
        Usuario nuevo = usuario.BuscarUsuario(Integer.parseInt(id_colaborador));

        config.configurarAutoCompleteTextView(tv_luxometro,lista_CodEquipos);
        hora_verificacion.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {config.showTimePickerDialog(rootView,hora_verificacion);}});
        fechaMonitoreo.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {config.showDatePickerDialog(rootView,fechaMonitoreo);}});
        hora_monitoreo.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {config.showTimePickerDialog(rootView,hora_monitoreo);}});
        cbx_lux.setAdapter(config.LlenarSpinner(new String[]{"0.0 lux"}));
        tipoDoc.setAdapter(config.LlenarSpinner(new String[]{"DNI", "CE"}));


        tipoDoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                String dni = numDoc.getText().toString();
                if(!dni.isEmpty()){
                    config.buscarTrabajador(dni,nomTrabajador);
                }
            }
        });


        btnSubirFotoIlu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentoImagen fragmentoImagen = new FragmentoImagen(IluminacionFragment.this);
                fragmentoImagen.show(getActivity().getSupportFragmentManager(), "imagePicker");
                cargarImagen = true;
            }
        });

        horario_Trab.setAdapter(config.LlenarSpinner("horario_trab_fromato_medicion","desc_horario",getActivity()));
        regimen.setAdapter(config.LlenarSpinner("regimen_formato_medicion","nom_regimen",getActivity()));
        config.MostrarCampos(linearOtroHorario,horario_Trab);
        config.MostrarCampos(linearOtroRegimen,regimen);

        HashMap<String, String> lista = new HashMap<>();
        lista.put("1", "En exteriores");
        lista.put("2", "En interiores");
        lista.put("3", "Requerimiento visual simple");
        lista.put("4", "Distinción moderada de detalles");
        lista.put("5", "Distinción clara de detalles");
        lista.put("6", "Distinción fina de detalles");
        lista.put("7", "Alta exactitud en la distinción de detalles");
        lista.put("8", "Alto grado de especialización en la distinción de detalles");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, new ArrayList<>(lista.values()));
        tareaVisual.setAdapter(adapter);
        tareaVisual.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String seleccion = (String) parent.getItemAtPosition(position);
                if(seleccion.equals(lista.get("1"))){
                    tipoArea.setText("Áreas generales exteriores: patios y estacionamientos");
                    nivelMinimo.setText("20");
                } else if (seleccion.equals(lista.get("2"))) {
                    tipoArea.setText("Áreas generales interiores: almacenes de poco movimiento, pasillos, escaleras, estacionamientos cubiertos, labores en minas subterráneas, iluminación de emergencia.");
                    nivelMinimo.setText("50");
                } else if (seleccion.equals(lista.get("3"))) {
                    tipoArea.setText("Áreas de servicios al personal: almacenaje rudo, recepción y despacho, casetas de vigilancia, cuartos de compresoras y calderos.");
                    nivelMinimo.setText("200");
                } else if (seleccion.equals(lista.get("4"))) {
                    tipoArea.setText("Talleres: áreas de empaque y ensamble, aulas y oficinas.");
                    nivelMinimo.setText("300");
                } else if (seleccion.equals(lista.get("5"))) {
                    tipoArea.setText("Talleres de precisión: salas de cómputo, áreas de dibujo, laboratorios.");
                    nivelMinimo.setText("500");
                } else if (seleccion.equals(lista.get("6"))) {
                    tipoArea.setText("Talleres de alta precisión: de pintura y acabado de superficies, y laboratorios de control de calidad.");
                    nivelMinimo.setText("750");
                } else if (seleccion.equals(lista.get("7"))) {
                    tipoArea.setText("Áreas de proceso: ensamble e inspección de piezas complejas y acabados con pulido fino.");
                    nivelMinimo.setText("1000");
                } else if (seleccion.equals(lista.get("8"))) {
                    tipoArea.setText("Áreas de proceso de gran exactitud.");
                    nivelMinimo.setText("2000");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //FECHA Y HORA DE MONITOREO ARRIBA
        spn_tipoIluminacion.setAdapter(config.LlenarSpinner(new String[]{"Natural", "Artificial", "Natural y Artificial"}));
        spn_tipoMedicion.setAdapter(config.LlenarSpinner(new String[]{"Medición por puesto de trabajo", "Medición por área de trabajo"}));
        spn_tipoMedicion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String seleccion = (String) parent.getItemAtPosition(position);
                if(seleccion.equals("Medición por puesto de trabajo")){
                    Card_Puesto.setVisibility(View.VISIBLE);;
                    Card_Area.setVisibility(View.GONE);
                    tv_altura.setText("Altura de plano a Luminaria(m)");
                } else if (seleccion.equals("Medición por área de trabajo")) {
                    Card_Puesto.setVisibility(View.GONE);;
                    Card_Area.setVisibility(View.VISIBLE);
                    tv_altura.setText("Alt. de Plano de Trabajo(m):");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // SECCION DONDE HACE EL CALCULO AUTOMATICO PARA LOS CAMPOS  * CONSTANTE DE SALON Y NUMERO MINIMO DE PUNTOS DE MEDICION
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String texto = s.toString();
                valorLargoEscri = txt_largoEscri.getText().toString();
                valorAnchoEscri = txt_anchoEscri.getText().toString();
                Log.e("gggg","ENTRO AL CAMBIO");
                if (!valorLargoEscri.isEmpty() && esNumero(texto)&&
                    !valorAnchoEscri.isEmpty() && esNumero(texto)) {
                        Valida_Punto_Medicion(valorLargoEscri, valorAnchoEscri);
                }else {
                    txt_numPuntosMedicion.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };
        txt_largoEscri.addTextChangedListener(watcher);
        txt_anchoEscri.addTextChangedListener(watcher);

        TextWatcher watcher1 = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String texto = s.toString();
                String val1 = txt_longSalon.getText().toString();
                String val2 = txt_anchoSalon.getText().toString();
                String val3 = txt_alt_PlanosTrabajo_ilu.getText().toString();
                Log.e("gggg","ENTRO AL CAMBIO");
                if (!val1.isEmpty() && TextUtils.isDigitsOnly(texto)&&
                        !val2.isEmpty() && TextUtils.isDigitsOnly(texto)&&
                        !val3.isEmpty() && TextUtils.isDigitsOnly(texto)) {
                    Constante_Salon(val1, val2, val3);
                }else {
                    txt_constanteSalon.setText("");
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        };

        txt_longSalon.addTextChangedListener(watcher1);
        txt_anchoSalon.addTextChangedListener(watcher1);
        txt_alt_PlanosTrabajo_ilu.addTextChangedListener(watcher1);

        TextWatcher watcher2 = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                linearPuntosMedicion.removeAllViews(); // Elimina todos los EditText previos
                editTextList.clear();
                int puntos;
                try {
                    puntos = Integer.parseInt(s.toString());
                } catch (NumberFormatException e) {
                    puntos = 0;
                }
                Log.e("sdfsdfs", "ENTRO AL METODO Y HAY " + puntos + "puntos");
                // Crea los EditText de forma dinámica y los agrega al layout
                for (int i = 0; i < puntos; i++) {
                    EditText editText = new EditText(getActivity());
                    editText.setHint("IL-0" + (i + 1));
                    editText.setPadding(8,0,0,0);
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    editText.setBackgroundResource(R.drawable.style_input);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    params.setMargins(0, 8, 0, 0);
                    editText.setLayoutParams(params);
                    linearPuntosMedicion.addView(editText);
                    editTextList.add(editText); //
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        txt_numPuntosMedicion.addTextChangedListener(watcher2);
        txt_numMinPuntosMedicion.addTextChangedListener(watcher2);

        estadoLuminarias.setAdapter(config.LlenarSpinner(new String[]{"Operativa","Inoperativa/Averiada","Tenues/Amarillas"}));


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
                        validar.validarCampoObligatorio(tv_luxometro) &&
                        validar.validarCampoObligatorio(ubicacionEquipo) &&
                        validar.validarCampoObligatorio(hora_verificacion) &&
                        validar.validarCampoObligatorio(cbx_lux) &&
                        validar.validarCampoObligatorio(tipoDoc) &&
                        validar.validarCampoObligatorio(numDoc) &&
                        validar.validarCampoObligatorio(nomTrabajador) &&
                        validar.validarCampoObligatorio(puestoTrabajador) &&
                        validar.validarCampoObligatorio(areaTrabajo) &&
                        validar.validarCampoObligatorio(numTrabajadores) &&
                        validar.validarCampoObligatorio(horario_Trab) &&
                        validar.validarCampoObligatorio(regimen) &&
                        validar.validarCampoObligatorio(tareaVisual) &&
                        //validar.validarCampoObligatorio(tipoArea) &&
                        //validar.validarCampoObligatorio(nivelMinimo) &&
                        validar.validarCampoObligatorio(fechaMonitoreo) &&
                        validar.validarCampoObligatorio(hora_monitoreo) &&
                        validar.validarCampoObligatorio(spn_tipoIluminacion) &&
                        validar.validarCampoObligatorio(spn_tipoMedicion) &&

                                //todo lo que es Oculto no es Validado

                        //validar.validarImagen(cargarImagen,getActivity()) &&
                        //validar.validarCampoObligatorio(il1) &&
                        //validar.validarCampoObligatorio(il2) &&
                        //validar.validarCampoObligatorio(il3) &&
                        //validar.validarCampoObligatorio(il4) &&
                        //validar.validarCampoObligatorio(il5) &&
                        //validar.validarCampoObligatorio(il6) &&
                        //validar.validarCampoObligatorio(il7) &&
                        //validar.validarCampoObligatorio(il8) &&
                        //validar.validarCampoObligatorio(areaTrabajoM2) &&
                        validar.validarCampoObligatorio(numLuminarias) &&
                        //validar.validarCampoObligatorio(txt_altura_pTrabajo) &&
                        validar.validarCampoObligatorio(numLamparas) &&
                        validar.validarCampoObligatorio(altura_pLuminaria) &&
                        validar.validarCampoObligatorio(colorPared) &&
                        validar.validarCampoObligatorio(colorPiso) &&
                        validar.validarCampoObligatorio(estadoLuminarias) &&
                        validar.validarCampoObligatorio(tareasRealizadas) &&
                        validar.validarCampoObligatorio(observaciones)
                ){
                    String valorTvLuxometro = tv_luxometro.getText().toString();
                    int valorGroupLuminaria = validar.getValor2(radioGroupLuminaria,rootView);
                    String valorUbiEquipo = ubicacionEquipo.getText().toString();
                    String valorHoraVerificacion = hora_verificacion.getText().toString();
                    String valorLux = cbx_lux.getSelectedItem().toString();
                    //Valor de foto o referencia hacia la foto
                    String valorTipoDoc = tipoDoc.getSelectedItem().toString();
                    String valorNumDocumento = numDoc.getText().toString();
                    String valorNomTrabajador = nomTrabajador.getText().toString();
                    String valorPuestoTrabajo = puestoTrabajador.getText().toString();
                    String valorAreaTrabajo = areaTrabajo.getText().toString();
                    String valorNumPersonas = numTrabajadores.getText().toString();
                    String valorHorarioTrabajo = horario_Trab.getSelectedItem().toString();
                    String valorRegimen = regimen.getSelectedItem().toString();

                    if(valorHorarioTrabajo.equals("OTRO")) valorHorarioTrabajo = txt_otroHorario.getText().toString();
                    if(valorRegimen.equals("OTRO")) valorRegimen = txt_otroRegimen.getText().toString();

                    String valorTareaVisual = tareaVisual.getSelectedItem().toString();
                    String valor_tipoAreaTrabajo = tipoArea.getText().toString();
                    String valorNivelIluminacion = nivelMinimo.getText().toString();

                    String f = fechaMonitoreo.getText().toString();
                    String valorFechaMonitoreo = config.convertirFecha(f);
                    //String valorFechaMonitoreo = fechaMonitoreo.getText().toString();

                    String valorHoraMonitoreo = hora_monitoreo.getText().toString();
                    String valorTipoIluminacion = spn_tipoIluminacion.getSelectedItem().toString();
                    String valorTipoMedicion = spn_tipoMedicion.getSelectedItem().toString();

                    String valorIdTipoMedicion = "";
                    if(valorTipoMedicion.equals("Medición por puesto de trabajo")){
                        valorIdTipoMedicion = "1";
                    } else if (valorTipoMedicion.equals("Medición por área de trabajo")) {
                        valorIdTipoMedicion = "2";
                    }

                    String valorLongSalon = txt_longSalon.getText().toString();
                    String valorAnchoSalon = txt_anchoSalon.getText().toString();
                    String valorAltPlanosTrabajoIlu = txt_alt_PlanosTrabajo_ilu.getText().toString();
                    String valoConstanteSalon = txt_constanteSalon.getText().toString();
                    String valorNumMinPuntoMed= txt_numMinPuntosMedicion.getText().toString();
                    valorLargoEscri = txt_largoEscri.getText().toString();
                    valorAnchoEscri = txt_anchoEscri.getText().toString();
                    String valorNumPuntoMed = txt_numPuntosMedicion.getText().toString();
                    String valorAltura_pTrabajo = txt_altura_pTrabajo.getText().toString();

                    List<String> editTextValues = new ArrayList<>();
                    for (EditText editText : editTextList) {
                        editTextValues.add(editText.getText().toString());
                    }
                    Log.e("dfsdfsdf",editTextValues.toString());
                    String valorPuntosMedicion = String.join("*", editTextValues.toArray(new String[0]));
                    String valorIL1 = il1.getText().toString();
                    String valorIL2 = il2.getText().toString();
                    String valorIL3 = il3.getText().toString();
                    String valorIL4 = il4.getText().toString();
                    String valorIL5 = il5.getText().toString();
                    String valorIL6 = il6.getText().toString();
                    String valorIL7 = il7.getText().toString();
                    String valorIL8 = il8.getText().toString();



                    String valorNumLuminarias = numLuminarias.getText().toString();
                    String valorNumLamparas = numLamparas.getText().toString();
                    String valorAltura_pLuminaria = altura_pLuminaria.getText().toString();
                    String valorColorPared = colorPared.getText().toString();
                    String valorColorPiso = colorPiso.getText().toString();
                    String valorEstadoLuminarias = estadoLuminarias.getSelectedItem().toString();
                    String valorTareasRealizadas = tareasRealizadas.getText().toString();
                    String valorObservaciones = observaciones.getText().toString();

                    String fecha_registro = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                    Equipos equipos1 = equipos.Buscar(valorTvLuxometro);

                    ArrayList<HashMap<String, String>> resultList = dao_registroFormatos.getListCantidadFormatoId(Integer.parseInt(id_pt_trabajo));
                    int total_registros = dao_registroFormatos.get_cant_formato_medicion();
                    String cod_formato = config.GenerarCodigoFormato(Integer.parseInt(id_formato),resultList.size());// CODIGO DE FORMATO
                    String cod_registro = config.generarCodigoRegistro(total_registros);// CODIGO DE REGISTRO

                    String valorRutaFoto = uri.getEncodedPath();
                    int id_plan_formato_reg = dao_registroFormatos.getRecordIdByPosition();

                    Iluminacion_Registro cabecera = new Iluminacion_Registro(
                            -1,
                            cod_formato,
                            cod_registro,
                            id_formato,
                            id_plan_trabajo,
                            id_pt_trabajo,
                            id_colaborador,
                            nuevo.getUsuario_nombres()+ " " +nuevo.getUsuario_apater()+" "+nuevo.getUsuario_amater(),
                            String.valueOf(equipos1.getId_equipo_registro()),
                            equipos1.getCodigo(),
                            equipos1.getNombre(),
                            equipos1.getSerie(),
                            valorHoraVerificacion,
                            valorLux,
                            valorTipoDoc,
                            valorNumDocumento,
                            valorNomTrabajador,
                            valorPuestoTrabajo,
                            valorAreaTrabajo,
                            valorNumPersonas,
                            valorHorarioTrabajo,
                            valorRegimen,
                            valorFechaMonitoreo,
                            valorHoraMonitoreo,
                            valorTareasRealizadas,
                            valorObservaciones,
                            valorUbiEquipo,
                            valorTareaVisual,
                            valor_tipoAreaTrabajo,
                            valorNivelIluminacion,
                            fecha_registro,
                            id_colaborador,
                            valorRutaFoto //solo para tema local. se envia a web pero no lo recibe
                    );

                    Iluminacion_RegistroDetalle detalle = new Iluminacion_RegistroDetalle(
                            (id_plan_formato_reg+1),
                            valorTipoIluminacion,
                            valorIdTipoMedicion,
                            valorTipoMedicion,
                            valorLargoEscri,
                            valorAnchoEscri,
                            valorNumPuntoMed,
                            valorAltura_pTrabajo,
                            valorLongSalon,
                            valorAnchoSalon,
                            valorAltPlanosTrabajoIlu,
                            valoConstanteSalon,
                            valorNumMinPuntoMed,
                            valorNumLuminarias,
                            valorPuntosMedicion,
                            "" +valorGroupLuminaria,
                            "area m2",
                            valorAltura_pTrabajo,
                            valorNumLamparas,
                            valorAltura_pLuminaria,
                            valorColorPared,
                            valorColorPiso,
                            valorEstadoLuminarias,
                            fecha_registro,id_colaborador
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

                        Call<ResponseBody> call1 = service1.insertIluminacion(json);//INSERT A RILUMINACION
                        call1.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                Log.e("exitoso", "se inserto el registro");
                                // Mostrar el JSON en el log
                                File imageFile = new File(uri.getEncodedPath());
                                config.uploadImage(imageFile, cod_formato,id_pt_trabajo,cod_registro);
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
                        DAO_RegistroIluminacion nuevoRegistro = new DAO_RegistroIluminacion(getActivity());
                        nuevoRegistro.RegistroIluminacion(cabecera);
                        nuevoRegistro.RegistroIluminacionDetalle(detalle);

                        DAO_FormatosTrabajo dao_formatosTrabajo = new DAO_FormatosTrabajo(getActivity());
                        for_Iluminacion = dao_formatosTrabajo.Buscar(id_plan_trabajo,id_formato);
                        for_Iluminacion.setRealizado(for_Iluminacion.getRealizado()+1);
                        for_Iluminacion.setPor_realizar(for_Iluminacion.getPor_realizar()-1);

                        dao_formatosTrabajo.actualizarFormatoTrabajo(for_Iluminacion);


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
        tv_nombreUsuario = view.findViewById(R.id.tv_nomUsuario);
        tv_nomEmpresa = view.findViewById(R.id.tv_nomEmpresa);

        // Asignación de elementos de la interfaz a variables
        tv_luxometro = view.findViewById(R.id.tv_luxometro);
        radioGroupLuminaria = view.findViewById(R.id.radioGroupLuminaria);
        ubicacionEquipo = view.findViewById(R.id.tv_ubicacionEquipo);
        hora_verificacion = view.findViewById(R.id.tv_HoraVerificacion);
        cbx_lux = view.findViewById(R.id.cbx_lux);
        tipoDoc = view.findViewById(R.id.cbx_tipoDocumento);
        numDoc = view.findViewById(R.id.cbx_numDocumento);
        nomTrabajador = view.findViewById(R.id.txt_Nom_Trabajador);
        puestoTrabajador = view.findViewById(R.id.txt_PuestoTrabajador);
        areaTrabajo = view.findViewById(R.id.txt_areaTrabajo);
        numTrabajadores = view.findViewById(R.id.txt_numTrabajadores);
        horario_Trab = view.findViewById(R.id.cbx_horarioTrabajo);
        regimen = view.findViewById(R.id.cbx_regimen);
        tareaVisual = view.findViewById(R.id.cbx_tareaVisual);
        tipoArea = view.findViewById(R.id.tipo_trabajo);
        nivelMinimo = view.findViewById(R.id.nivel_min_iluminacion);
        fechaMonitoreo = view.findViewById(R.id.tv_fechaMonitoreo);
        hora_monitoreo = view.findViewById(R.id.tv_horaMonitoreo);
        spn_tipoIluminacion = view.findViewById(R.id.cbx_tipoIluminacion);//Agregado recientemente

        spn_tipoMedicion = view.findViewById(R.id.cbx_tipoMedicion);
        txt_longSalon = view.findViewById(R.id.txt_longSalon);
        txt_anchoSalon = view.findViewById(R.id.txt_anchoSalon);
        txt_alt_PlanosTrabajo_ilu = view.findViewById(R.id.txt_alt_PlanosTrabajo_ilu);
        txt_constanteSalon = view.findViewById(R.id.txt_constanteSalon);
        txt_numMinPuntosMedicion = view.findViewById(R.id.txt_numMinPuntosMedicion);
        txt_largoEscri = view.findViewById(R.id.txt_largoEscri);
        txt_anchoEscri = view.findViewById(R.id.txt_anchoEscri);
        txt_numPuntosMedicion = view.findViewById(R.id.txt_numPuntosMedicion);
        txt_altura_pTrabajo = view.findViewById(R.id.txt_alturaPlanosTrabajo);

        numLuminarias = view.findViewById(R.id.txt_CantLuminarias);
        btnSubirFotoIlu = view.findViewById(R.id.btn_subirFotoIlu);
        imgIliminacion = view.findViewById(R.id.img_Iluminacion);
        il1 = view.findViewById(R.id.txt_IL_01);
        il2 = view.findViewById(R.id.txt_IL_02);
        il3 = view.findViewById(R.id.txt_IL_03);
        il4 = view.findViewById(R.id.txt_IL_04);
        il5 = view.findViewById(R.id.txt_IL_05);
        il6 = view.findViewById(R.id.txt_IL_06);
        il7 = view.findViewById(R.id.txt_IL_07);
        il8 = view.findViewById(R.id.txt_IL_08);
        areaTrabajoM2 = view.findViewById(R.id.txt_areaTrabajoM2);

        numLamparas = view.findViewById(R.id.txt_Num_lamparas);
        altura_pLuminaria = view.findViewById(R.id.txt_alturaPlanosLuminaria);
        colorPared = view.findViewById(R.id.txt_colorPared);
        colorPiso = view.findViewById(R.id.txt_colorPiso);
        estadoLuminarias = view.findViewById(R.id.cbx_estadoLuminarias);
        tareasRealizadas = view.findViewById(R.id.txt_tareasRealizadas);
        observaciones = view.findViewById(R.id.txt_observaciones);

        btn_guardar = view.findViewById(R.id.fabGuardar);
        btnCancelar = view.findViewById(R.id.fabCancelar);

        linearOtroHorario = view.findViewById(R.id.linearOtroHorario);
        txt_otroHorario = view.findViewById(R.id.txt_otroHorario);
        linearOtroRegimen = view.findViewById(R.id.linearOtroRegimen);
        txt_otroRegimen = view.findViewById(R.id.txt_otroRegimen);
        linearPuntosMedicion = view.findViewById(R.id.linearPuntosMedicion);
        linearBuscarDni = view.findViewById(R.id.linearBuscarDni);
        btn_BuscarDni = view.findViewById(R.id.btn_BuscarDni);

        Card_Puesto = view.findViewById(R.id.Card_Puesto);
        Card_Area = view.findViewById(R.id.Card_Area);

        tv_altura = view.findViewById(R.id.altura);


    }

    @Override
    public void onImagePicked(Uri imageUri) {
        this.uri = imageUri;
        if (imgIliminacion != null && imageUri != null) {
            imgIliminacion.setImageURI(imageUri);
        }
    }

    public void Valida_Punto_Medicion(String largoEscri, String anchoEscri){
        Log.e("FFFF", "entro a validacion de punto");
        Log.e("valores", largoEscri + " - " + anchoEscri);
        if (largoEscri==null|| anchoEscri==null) {
            txt_numPuntosMedicion.setText("");
            return;
        }
        float valorLargoEs = Float.parseFloat(largoEscri);
        float valorAnchoEs = Float.parseFloat(anchoEscri);
        float valorMax = Math.max(valorLargoEs, valorAnchoEs);
        float valorMin = Math.min(valorLargoEs, valorAnchoEs);

        Double resultado = (valorMin < 2 ? valorMax : valorMin) / (0.2 * Math.pow(5, Math.log10(valorMin < 2 ? valorMax : valorMin)));
        int resultado1 = (int)Math.round(resultado);
        Log.e("gghfg",String.valueOf(resultado1));
        txt_numPuntosMedicion.setText(String.valueOf(resultado1));
    }

    public void Constante_Salon(String longSalon, String anchoSalon, String altPlanLumin){
        if (longSalon==null || anchoSalon==null || altPlanLumin==null) {
            txt_constanteSalon.setText("");
            return;
        }
        float valorlongSalon = Float.parseFloat(longSalon);
        float valorAnchoSalon = Float.parseFloat(anchoSalon);
        float valorAltPlanLum = Float.parseFloat(altPlanLumin);

        float resultado = (valorlongSalon * valorAnchoSalon) / (valorAltPlanLum * (valorlongSalon + valorAnchoSalon));
        float resultado1 = Math.round(resultado * 100) / 100f;
        Log.e("gghfg",String.valueOf(resultado));
        txt_constanteSalon.setText(String.valueOf(resultado1));
        Num_Minimo_Punto_Med(resultado1);
    }

    public void Num_Minimo_Punto_Med(float constSalon){
        // Aplicar la lógica de la fórmula
        int resultado;
        if (constSalon < 1) {
            resultado = 4;
        } else if (constSalon >= 1 && constSalon < 2) {
            resultado = 9;
        } else if (constSalon >= 2 && constSalon < 3) {
            resultado = 16;
        } else if (constSalon >= 3) {
            resultado = 25;
        } else {
            resultado = 0;
        }
        txt_numMinPuntosMedicion.setText(String.valueOf(resultado));
    }
    public boolean esNumero(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}