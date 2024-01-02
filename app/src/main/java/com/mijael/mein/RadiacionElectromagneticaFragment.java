package com.mijael.mein;

import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mijael.mein.DAO.DAO_Equipos;
import com.mijael.mein.DAO.DAO_FormatosTrabajo;
import com.mijael.mein.DAO.DAO_RegistroRadiacion;
import com.mijael.mein.DAO.DAO_RegistroVibracion;
import com.mijael.mein.Entidades.Equipos;
import com.mijael.mein.Entidades.Formatos_Trabajo;
import com.mijael.mein.Entidades.RadiacionElec_Registro;
import com.mijael.mein.Entidades.RadiacionElect_RegistroDetalle;
import com.mijael.mein.Extras.FragmentoImagen;
import com.mijael.mein.Extras.InputDateConfiguration;
import com.mijael.mein.Extras.Validaciones;
import com.mijael.mein.HELPER.EquiposSQLiteHelper;
import com.mijael.mein.HELPER.FormatoTrabajoSQLiteHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class RadiacionElectromagneticaFragment extends Fragment implements FragmentoImagen.ImagePickerListener{
    private boolean cargarImagen = false;
    View rootView;
    TextView tv_nombreUsuario, tv_nomEmpresa;
    String id_plan_trabajo, id_pt_trabajo, id_formato,id_colaborador, nom_Empresa;
    // Declaraciones de variables de Android en Java agrupadas por tipo en una sola línea
    AutoCompleteTextView spn_equipoMedicion;
    TextView tv_horaVerificacion, tv_fechaMonitoreo, tv_horaInicioMoni, tv_horaFinalMoni;
    RadioGroup radioGroupVerificacion;
    AppCompatButton btn_subirFotoElectro;
    FloatingActionButton btn_guardar;
    ExtendedFloatingActionButton btnCancelar;
    EditText txt_timeMedicion, txt_numDoc, txt_nomTrabajador, txt_edad, txt_areaTrabajo, txt_puestoTrabajo, txt_aRealizada,
            txt_jornadaTrabajo, txt_fuenteGen, txt_timeExpo, txt_vestimenta, txt_controlIng, txt_controlAdm, txt_Epps,
            txt_x0, txt_x2, txt_x4, txt_x6, txt_y0, txt_y2, txt_y4, txt_y6, txt_z0, txt_z2, txt_z4, txt_z6;
    Spinner spn_tipoDoc, spn_horarioTrabajo, spn_regimen, spn_horarioRefrigerio, spn_descTrabajo;
    ImageView imgVibra;
    Uri uri;
    Formatos_Trabajo for_Electro;
    Validaciones validar = new Validaciones();

    public RadiacionElectromagneticaFragment() {
        // Required empty public constructor
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_radiacion_electromagnetica, container, false);
        InputDateConfiguration config = new InputDateConfiguration(getActivity(),id_colaborador,nom_Empresa,rootView);
        init(rootView);
        config.ConfigPantalla();

        DAO_Equipos equipos = new DAO_Equipos(getActivity());
        List<String> lista_CodEquipos = equipos.obtener_CodEquipos();

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

        spn_tipoDoc.setAdapter(config.LlenarSpinner("DNI", "CE"));
            spn_descTrabajo.setAdapter(config.LlenarSpinner("Trabajo al aire libre sin carga solar o bajo techo", "Trabajo al aire libre con carga solar"));
        spn_horarioTrabajo.setAdapter(config.LlenarSpinner("horario_trab_fromato_medicion","desc_horario",getActivity()));
        spn_regimen.setAdapter(config.LlenarSpinner("regimen_formato_medicion","nom_regimen",getActivity()));
        spn_horarioRefrigerio.setAdapter(config.LlenarSpinner("horario_refrig_formato_medicion","nom_horario",getActivity()));

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
                if(
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
                ){
                    String valorEquipoMed = spn_equipoMedicion.getText().toString();
                    String valorHoraVerificacion = tv_horaVerificacion.getText().toString();
                    String valorGroupVerifi = validar.getValor2(radioGroupVerificacion,rootView);

                    String valorFechaMoni = tv_fechaMonitoreo.getText().toString();
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

                    String fecha_registro = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                    Equipos equipos1 = equipos.Buscar(valorEquipoMed);

                    RadiacionElec_Registro elecRegistro = new RadiacionElec_Registro(
                            -1,
                            "RE-001",
                            id_formato,
                            id_plan_trabajo,
                            id_pt_trabajo,
                            String.valueOf(equipos1.getId_equipo_registro()),
                            equipos1.getCod_equipo(),
                            equipos1.getNombre(),
                            equipos1.getSerie(),
                            id_colaborador,
                            "id_Colaborador.getNombre()",
                            valorHoraVerificacion,
                            valorGroupVerifi,
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
                            valorTimeExpo,
                            valorTimeExpo,
                            valorAreaTrab,
                            valorControlAdm,
                            valorControlAdm,
                            valorEpps,
                            fecha_registro,
                            "Usuario_Registro"
                    );
                    RadiacionElect_RegistroDetalle registroDetalle = new RadiacionElect_RegistroDetalle(
                            -1,
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
                            "Usario Registro"
                    );
                    DAO_RegistroRadiacion nuevoRegistro = new DAO_RegistroRadiacion(getActivity());
                    boolean estadoRegistro = nuevoRegistro.RegistroRadiacion(elecRegistro);
                    boolean estadoDetalle = nuevoRegistro.RegistrarRadiacionDetalle(registroDetalle);
                    if(estadoRegistro && estadoDetalle){
                        DAO_FormatosTrabajo dao_fromatosTrabajo = new DAO_FormatosTrabajo(getActivity());
                        for_Electro = dao_fromatosTrabajo.Buscar(id_plan_trabajo,id_formato);
                        for_Electro.setRealizado(for_Electro.getRealizado()+1);
                        for_Electro.setPor_realizar(for_Electro.getPor_realizar()-1);
                        dao_fromatosTrabajo.actualizarFormatoTrabajo(for_Electro);

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


        // Inicialización de variables mediante findViewById en Android
        spn_equipoMedicion = view.findViewById(R.id.spn_equipoMedicion);
        tv_horaVerificacion = view.findViewById(R.id.tv_horaVerificacion);
        tv_fechaMonitoreo = view.findViewById(R.id.tv_fechaMoni);
        tv_horaInicioMoni = view.findViewById(R.id.tv_horaInicial);
        tv_horaFinalMoni = view.findViewById(R.id.tv_horaFinal);
        radioGroupVerificacion = view.findViewById(R.id.radioGroupVerificacion);
        btn_subirFotoElectro = view.findViewById(R.id.btn_subirFotoElectro);
        txt_timeMedicion = view.findViewById(R.id.txt_timeMedicion);
        spn_tipoDoc = view.findViewById(R.id.spn_tipoDoc);
        txt_numDoc = view.findViewById(R.id.txt_numDoc);
        txt_nomTrabajador = view.findViewById(R.id.txt_nomTrabajador);
        txt_edad = view.findViewById(R.id.txt_edad);
        txt_areaTrabajo = view.findViewById(R.id.txt_areaTrabajo);
        txt_puestoTrabajo = view.findViewById(R.id.txt_puestoTrabajo);
        txt_aRealizada = view.findViewById(R.id.txt_aRealizada);
        spn_horarioTrabajo = view.findViewById(R.id.spn_horarioTrabajo);
        spn_regimen = view.findViewById(R.id.spn_regimen);
        spn_horarioRefrigerio = view.findViewById(R.id.spn_horarioRefrigerio);
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


    }

    @Override
    public void onImagePicked(Uri imageUri) {
        this.uri = imageUri;
        if (imgVibra != null && imageUri != null) {
            imgVibra.setImageURI(imageUri);
        }
    }
}