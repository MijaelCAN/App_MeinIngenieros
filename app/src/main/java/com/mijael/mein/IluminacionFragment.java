package com.mijael.mein;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

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
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mijael.mein.DAO.DAO_DatosLocal;
import com.mijael.mein.DAO.DAO_Equipos;
import com.mijael.mein.DAO.DAO_FormatosTrabajo;
import com.mijael.mein.DAO.DAO_RegistroIluminacion;
import com.mijael.mein.DAO.DAO_Usuario;
import com.mijael.mein.Entidades.Equipos;
import com.mijael.mein.Entidades.Formatos_Trabajo;
import com.mijael.mein.Entidades.Iluminacion_Registro;
import com.mijael.mein.Entidades.Iluminacion_RegistroDetalle;
import com.mijael.mein.Entidades.Usuario;
import com.mijael.mein.Extras.FragmentoImagen;
import com.mijael.mein.Extras.Validaciones;
import com.mijael.mein.HELPER.EquiposSQLiteHelper;
import com.mijael.mein.HELPER.FormatoTrabajoSQLiteHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class IluminacionFragment extends Fragment implements FragmentoImagen.ImagePickerListener {
    private boolean cargarImagen = false;
    int hora,min;
    String id_plan_trabajo, id_pt_trabajo, id_formato,id_colaborador, nom_Empresa;
    View rootView;
    TextView tv_nombreUsuario, tv_nomEmpresa;
    AutoCompleteTextView tv_luxometro;
    TextView hora_verificacion, fechaMonitoreo, hora_monitoreo;
    EditText ubicacionEquipo, numDoc, nomTrabajador, puestoTrabajador, areaTrabajo, numTrabajadores, nivelMinimo, numLuminarias, tipoArea,
    il1, il2, il3, il4, il5, il6, il7, il8, areaTrabajoM2, altura_pTrabajo, numLamparas, altura_pLuminaria, colorPared, colorPiso, tareasRealizadas, observaciones;
    RadioGroup radioGroupLuminaria;
    CheckBox chekArtificial, checkNatural;
    Spinner cbx_lux, tipoDoc, horario_Trab, regimen, tareaVisual, estadoLuminarias;
    Button btnSubirFotoIlu;
    FloatingActionButton btn_guardar;
    ExtendedFloatingActionButton btnCancelar;
    ImageView imgIliminacion;
    Uri uri;
    public IluminacionFragment() {
        // Required empty public constructor
    }
    Formatos_Trabajo for_Iluminacion;
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
        rootView = inflater.inflate(R.layout.fragment_iluminacion,container,false);
        init(rootView);

        ConfigPantalla();

        DAO_Equipos equipos = new DAO_Equipos(getActivity());
        List<String> lista_CodEquipos = equipos.obtener_CodEquipos();
        configurarAutoCompleteTextView(tv_luxometro,lista_CodEquipos);


        horario_Trab.setAdapter(LlenarSpinner("horario_trab_fromato_medicion","desc_horario",getActivity()));
        regimen.setAdapter(LlenarSpinner("regimen_formato_medicion","nom_regimen",getActivity()));
        fechaMonitoreo.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {showDatePickerDialog(rootView,fechaMonitoreo);}});
        hora_monitoreo.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {showTimePickerDialog(rootView,hora_monitoreo);}});
        btnSubirFotoIlu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentoImagen fragmentoImagen = new FragmentoImagen(IluminacionFragment.this);
                fragmentoImagen.show(getActivity().getSupportFragmentManager(), "imagePicker");
                cargarImagen = true;
            }
        });

        tipoDoc.setAdapter(LlenarSpinner("DNI","CE"));
        cbx_lux.setAdapter(LlenarSpinner("0.0 lux","0.1 lux")); //PROVISIONAL
        tareaVisual.setAdapter(LlenarSpinner("Exteriores","Interiores")); //PROVISIONAL
        estadoLuminarias.setAdapter(LlenarSpinner("Operativa","Inoperativa")); //PROVISIONAL

        hora_verificacion.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {showTimePickerDialog(rootView,hora_verificacion);}});
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
                        validar.validarCampoObligatorio(radioGroupLuminaria,getActivity()) &&
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
                        validar.validarCampoObligatorio(tipoArea) &&
                        validar.validarCampoObligatorio(nivelMinimo) &&
                        validar.validarCampoObligatorio(fechaMonitoreo) &&
                        validar.validarCampoObligatorio(hora_monitoreo) &&
                        validar.validarCampoObligatorio(numLuminarias) &&
                        validar.validarImagen(cargarImagen,getActivity()) &&
                        validar.validarCampoObligatorio(il1) &&
                        validar.validarCampoObligatorio(il2) &&
                        validar.validarCampoObligatorio(il3) &&
                        validar.validarCampoObligatorio(il4) &&
                        validar.validarCampoObligatorio(il5) &&
                        validar.validarCampoObligatorio(il6) &&
                        validar.validarCampoObligatorio(il7) &&
                        validar.validarCampoObligatorio(il8) &&
                        validar.validarCampoObligatorio(areaTrabajoM2) &&
                        validar.validarCampoObligatorio(altura_pTrabajo) &&
                        validar.validarCampoObligatorio(numLamparas) &&
                        validar.validarCampoObligatorio(altura_pLuminaria) &&
                        validar.validarCampoObligatorio(colorPared) &&
                        validar.validarCampoObligatorio(colorPiso) &&
                        validar.validarCampoObligatorio(estadoLuminarias) &&
                        validar.validarCampoObligatorio(tareasRealizadas) &&
                        validar.validarCampoObligatorio(observaciones)
                ){
                    String valorTvLuxometro = tv_luxometro.getText().toString();
                    //FALTA PLAN DE MANTENIMIENTO
                    String valorUbiEquipo = ubicacionEquipo.getText().toString();
                    String valorHoraVerificacion = hora_monitoreo.getText().toString();
                    String valorLux = cbx_lux.getSelectedItem().toString();
                    String valorTipoDoc = tipoDoc.getSelectedItem().toString();
                    String valorNumDocumento = numDoc.getText().toString();
                    String valorNomTrabajador = nomTrabajador.getText().toString();
                    String valorPuestoTrabajo = puestoTrabajador.getText().toString();
                    String valorAreaTrabajo = areaTrabajo.getText().toString();
                    String valorNumPersonas = numTrabajadores.getText().toString();
                    String valorHorarioTrabajo = horario_Trab.getSelectedItem().toString();
                    String valorRegimen = regimen.getSelectedItem().toString();
                    String valorTareaVisual = tareaVisual.getSelectedItem().toString();
                    String valor_tipoAreaTrabajo = tipoArea.getText().toString();
                    String valorNivelIluminacion = nivelMinimo.getText().toString();
                    String valorFechaMonitoreo = fechaMonitoreo.getText().toString();
                    String valorHoraMonitoreo = hora_monitoreo.getText().toString();
                    //FALTA LOS DOS CHECKS
                    String valorNumLuminarias = numLuminarias.getText().toString();
                    //String valorImagen = Uri.Imagen
                    String valorIL1 = il1.getText().toString();
                    String valorIL2 = il2.getText().toString();
                    String valorIL3 = il3.getText().toString();
                    String valorIL4 = il4.getText().toString();
                    String valorIL5 = il5.getText().toString();
                    String valorIL6 = il6.getText().toString();
                    String valorIL7 = il7.getText().toString();
                    String valorIL8 = il8.getText().toString();
                    String valorAreaTrabajoM2 = areaTrabajoM2.getText().toString();
                    String valorAltura_pTrabajo = altura_pTrabajo.getText().toString();
                    String valorNumLamparas = numLamparas.getText().toString();
                    String valorAltura_pLuminaria = altura_pLuminaria.getText().toString();
                    String valorColorPared = colorPared.getText().toString();
                    String valorColorPiso = colorPiso.getText().toString();
                    String valorEstadoLuminarias = estadoLuminarias.getSelectedItem().toString();
                    String valorTareasRealizadas = tareasRealizadas.getText().toString();
                    String valorObservaciones = observaciones.getText().toString();

                    String fecha_registro = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                    Equipos equipos1 = equipos.Buscar(valorTvLuxometro);

                    Iluminacion_Registro registro = new Iluminacion_Registro(
                            -1,
                            "IL-001",
                            id_formato,
                            id_plan_trabajo,
                            id_pt_trabajo,
                            id_colaborador,
                            "id_Colaborador.getNomAnali()",
                            String.valueOf(equipos1.getId_equipo_registro()),
                            equipos1.getCod_equipo(),
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
                            valorNivelIluminacion,
                            valorNivelIluminacion,
                            valorEstadoLuminarias,
                            fecha_registro,
                            "usuario_Registro"
                    );

                    Iluminacion_RegistroDetalle detalle = new Iluminacion_RegistroDetalle(
                            -1,
                            "ilumi_artificial",
                            "ilumi_natural",
                            valorNumLuminarias,
                            valorIL1,
                            valorIL2,
                            valorIL3,
                            valorIL4,
                            valorIL5,
                            valorIL6,
                            valorIL7,
                            valorIL8,
                            "valorMantenimiento",
                            valorAreaTrabajoM2,
                            valorAltura_pTrabajo,
                            valorNumLamparas,
                            valorAltura_pLuminaria,
                            valorColorPared,
                            valorColorPiso,
                            valorEstadoLuminarias,
                            fecha_registro,
                            "usuario_reg"
                    );

                    DAO_RegistroIluminacion nuevoRegistro = new DAO_RegistroIluminacion(getActivity());
                    boolean estadoRegistro = nuevoRegistro.RegistroIluminacion(registro);
                    boolean estadoDetalle = nuevoRegistro.RegistroIluminacionDetalle(detalle);
                    if(estadoRegistro && estadoDetalle){
                        DAO_FormatosTrabajo dao_fromatosTrabajo = new DAO_FormatosTrabajo(getActivity());
                        for_Iluminacion = dao_fromatosTrabajo.Buscar(id_plan_trabajo,id_formato);
                        for_Iluminacion.setRealizado(for_Iluminacion.getRealizado()+1);
                        for_Iluminacion.setPor_realizar(for_Iluminacion.getPor_realizar()-1);
                        dao_fromatosTrabajo.actualizarFormatoTrabajo(for_Iluminacion);

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
        chekArtificial = view.findViewById(R.id.check_artificial);
        checkNatural = view.findViewById(R.id.check_natural);
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
        altura_pTrabajo = view.findViewById(R.id.txt_alturaPlanosTrabajo);
        numLamparas = view.findViewById(R.id.txt_Num_lamparas);
        altura_pLuminaria = view.findViewById(R.id.txt_alturaPlanosLuminaria);
        colorPared = view.findViewById(R.id.txt_colorPared);
        colorPiso = view.findViewById(R.id.txt_colorPiso);
        estadoLuminarias = view.findViewById(R.id.cbx_estadoLuminarias);
        tareasRealizadas = view.findViewById(R.id.txt_tareasRealizadas);
        observaciones = view.findViewById(R.id.txt_observaciones);

        btn_guardar = view.findViewById(R.id.fabGuardar);
        btnCancelar = view.findViewById(R.id.fabCancelar);

    }
    public void ConfigPantalla(){
        DAO_Usuario usuario = new DAO_Usuario(getActivity());
        Usuario nuevo = usuario.BuscarUsuario(Integer.parseInt(id_colaborador));
        String cadena = nuevo.getUsuario_nombres() + " "+ nuevo.getUsuario_apater();
        tv_nombreUsuario.setText(cadena);
        tv_nomEmpresa.setText(nom_Empresa);

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
        if (imgIliminacion != null && imageUri != null) {
            imgIliminacion.setImageURI(imageUri);
        }
    }
}