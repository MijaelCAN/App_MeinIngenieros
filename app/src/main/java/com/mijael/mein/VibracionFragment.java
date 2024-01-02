package com.mijael.mein;

import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mijael.mein.DAO.DAO_Equipos;
import com.mijael.mein.DAO.DAO_FormatosTrabajo;
import com.mijael.mein.DAO.DAO_RegistroIluminacion;
import com.mijael.mein.DAO.DAO_RegistroVibracion;
import com.mijael.mein.DAO.DAO_Usuario;
import com.mijael.mein.Entidades.Equipos;
import com.mijael.mein.Entidades.Formatos_Trabajo;
import com.mijael.mein.Entidades.Usuario;
import com.mijael.mein.Entidades.Vibracion_Registro;
import com.mijael.mein.Entidades.Vibracion_RegistroDetalle;
import com.mijael.mein.Extras.FragmentoImagen;
import com.mijael.mein.Extras.InputDateConfiguration;
import com.mijael.mein.Extras.Validaciones;
import com.mijael.mein.HELPER.EquiposSQLiteHelper;
import com.mijael.mein.HELPER.FormatoTrabajoSQLiteHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class VibracionFragment extends Fragment implements FragmentoImagen.ImagePickerListener{
    private boolean cargarImagen = false;
    View rootView;
    TextView tv_nombreUsuario, tv_nomEmpresa;
    String id_plan_trabajo, id_pt_trabajo, id_formato,id_colaborador, nom_Empresa;
    // Ejemplos de variables para vistas
    TextView tv_horaVerificacion, tv_fechaMonitoreo, tv_horaInicioMoni, tv_horaFinalMoni;
    AutoCompleteTextView spn_equipoutilizado;
    Spinner spn_tipoVibracion, spn_tipoDoc, spn_horarioTrabajo, spn_regimen, spn_horarioRefrig, spn_frecuencia;
    RadioGroup radioGroupVerificacion,radioGroupIng, radioGroupAdm, radioGroupSeñal, radioGroupCapac, radioGroupMante;
    AppCompatButton btnSubirFotoVibra;
    FloatingActionButton btn_guardar;
    ExtendedFloatingActionButton btnCancelar;
    EditText txt_jornada, txt_timeExpo, txt_numDoc, txt_nomTrab, txt_edadTrab, txt_areaTrab, txt_puestoTrab, txt_actRealizada;
    EditText txt_fuenteGenVibra, txt_descFuenteGen, txt_nombreControl, txt_otrosAdmin, txt_resulX, txt_resulY, txt_resulZ;
    CheckBox check_botas, check_guantes, check_casco, check_proteccionAud;
    ImageView imgVibra;
    Uri uri;
    public VibracionFragment() {
        // Required empty public constructor
    }
    Formatos_Trabajo for_Vibracion;
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
                             Bundle savedInstanceStance) {

        rootView = inflater.inflate(R.layout.fragment_vibracion,container,false);
        InputDateConfiguration config = new InputDateConfiguration(getActivity(),id_colaborador,nom_Empresa,rootView);
        init(rootView);

        DAO_Equipos equipos = new DAO_Equipos(getActivity());
        List<String> lista_CodEquipos = equipos.obtener_CodEquipos();

        config.ConfigPantalla();
        spn_tipoVibracion.setAdapter(config.LlenarSpinner("Cuerpo Completo", "Mano Brazo"));
        spn_tipoDoc.setAdapter(config.LlenarSpinner("DNI", "CE"));
        config.configurarAutoCompleteTextView(spn_equipoutilizado,lista_CodEquipos);

        spn_horarioTrabajo.setAdapter(config.LlenarSpinner("horario_trab_fromato_medicion","desc_horario",getActivity()));
        spn_regimen.setAdapter(config.LlenarSpinner("regimen_formato_medicion","nom_regimen",getActivity()));
        spn_horarioRefrig.setAdapter(config.LlenarSpinner("horario_refrig_formato_medicion","nom_horario",getActivity()));
        spn_frecuencia.setAdapter(config.LlenarSpinner("frecuencia","nom_frecuencia",getActivity()));

        tv_horaVerificacion.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {config.showTimePickerDialog(rootView,tv_horaVerificacion);}});
        tv_fechaMonitoreo.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {config.showDatePickerDialog(rootView,tv_fechaMonitoreo);}});
        tv_horaInicioMoni.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {config.showTimePickerDialog(rootView,tv_horaInicioMoni);}});
        tv_horaFinalMoni.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {config.showTimePickerDialog(rootView,tv_horaFinalMoni);}});

        btnSubirFotoVibra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentoImagen fragmentoImagen = new FragmentoImagen(VibracionFragment.this);
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
                        validar.validarCampoObligatorio(spn_tipoVibracion) &&
                        validar.validarCampoObligatorio(spn_equipoutilizado) &&
                        validar.validarCampoObligatorio(tv_horaVerificacion) &&
                        validar.validarImagen(cargarImagen,getActivity()) &&
                        validar.validarCampoObligatorio(tv_fechaMonitoreo) &&
                        validar.validarCampoObligatorio(tv_horaInicioMoni) &&
                        validar.validarCampoObligatorio(tv_horaFinalMoni) &&
                        validar.validarCampoObligatorio(txt_timeExpo) &&
                        validar.validarCampoObligatorio(txt_jornada) &&
                        validar.validarCampoObligatorio(spn_tipoDoc) &&
                        validar.validarCampoObligatorio(txt_numDoc) &&
                        validar.validarCampoObligatorio(txt_nomTrab) &&
                        validar.validarCampoObligatorio(txt_puestoTrab) &&
                        validar.validarCampoObligatorio(txt_edadTrab) &&
                        validar.validarCampoObligatorio(txt_actRealizada) &&
                        validar.validarCampoObligatorio(spn_horarioTrabajo) &&
                        validar.validarCampoObligatorio(spn_horarioRefrig) &&
                        validar.validarCampoObligatorio(spn_regimen) &&
                        validar.validarCampoObligatorio(txt_fuenteGenVibra) &&
                        validar.validarCampoObligatorio(txt_descFuenteGen) &&
                        // FALTA VALIDAR LOS RADIOGROUP Y RADIOBUTTON
                        validar.validarCampoObligatorio(txt_resulX) &&
                        validar.validarCampoObligatorio(txt_resulY) &&
                        validar.validarCampoObligatorio(txt_resulZ)
                ){
                    String valorTipoVibracion = spn_tipoVibracion.getSelectedItem().toString();
                    String valorEquipoUtil = spn_equipoutilizado.getText().toString();
                    String valorHoraVerif = tv_horaVerificacion.getText().toString();
                    String valorVerificacion = validar.getValor2(radioGroupVerificacion,rootView);
                    //String valorFoto = uri.toString();
                    String valorFechaMoni = tv_fechaMonitoreo.getText().toString();
                    String valorHoraInicioMoni = tv_horaInicioMoni.getText().toString();
                    String valorHoraFinalMoni = tv_horaFinalMoni.getText().toString();
                    String valorTimeExpo = txt_timeExpo.getText().toString();
                    String valorJornada = txt_jornada.getText().toString();
                    String valorTipoDoc = spn_tipoDoc.getSelectedItem().toString();
                    String valorNumDoc = txt_numDoc.getText().toString();
                    String valorNombreTrab = tv_nombreUsuario.getText().toString();
                    String valorPuestoTrabaj = txt_puestoTrab.getText().toString();
                    String valorAreaTrabajo = txt_areaTrab.getText().toString();
                    String valorEdad = txt_edadTrab.getText().toString();
                    String valor_aRealizada = txt_actRealizada.getText().toString();
                    String valorHorarioTrabajo = spn_horarioTrabajo.getSelectedItem().toString();
                    String valorRefrigerio = spn_horarioRefrig.getSelectedItem().toString();
                    String valorRegimen = spn_regimen.getSelectedItem().toString();
                    String valorFuenteGenVibra = txt_fuenteGenVibra.getText().toString();
                    String valorDescripcionFuente = txt_descFuenteGen.getText().toString();
                    String valorGroupIng = validar.getValor2(radioGroupIng,rootView);
                    String valorNombreControl = txt_nombreControl.getText().toString();
                    String valorGroupAdm = validar.getValor2(radioGroupAdm,rootView);
                    String valorGroupSeñal = validar.getValor2(radioGroupSeñal,rootView);
                    String valorGroupCapac = validar.getValor2(radioGroupCapac, rootView);
                    String valorGroupManten = validar.getValor2(radioGroupMante, rootView);
                    String valorFrecuencia = spn_frecuencia.getSelectedItem().toString();
                    String valorCheckBotas = String.valueOf(check_botas.isChecked());
                    String valorCheckGuantes = String.valueOf(check_guantes.isChecked());
                    String valorCheckCasco = String.valueOf(check_casco.isChecked());
                    String valorCheckProtAudi = String.valueOf(check_proteccionAud.isChecked());
                    String valorOtroAdm = txt_otrosAdmin.getText().toString();
                    String valorX = txt_resulX.getText().toString();
                    String valorY = txt_resulY.getText().toString();
                    String valorZ = txt_resulZ.getText().toString();

                    String fecha_registro = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                    Equipos equipos1 = equipos.Buscar(valorEquipoUtil);

                    Vibracion_Registro registro = new Vibracion_Registro(
                            -1,
                            "VI-001",
                            id_formato,
                            id_plan_trabajo,
                            id_pt_trabajo,
                            equipos1.getCod_equipo(),
                            equipos1.getNombre(),
                            equipos1.getSerie(),
                            String.valueOf(equipos1.getId_equipo_registro()),
                            id_colaborador,
                            "id_Colaborador.getNombre()",
                            valorTipoVibracion,
                            valorVerificacion,
                            valorHoraVerif,
                            valorFechaMoni,
                            valorHoraInicioMoni,
                            valorHoraFinalMoni,
                            valorTimeExpo,
                            valorJornada,
                            valorTipoDoc,
                            valorNumDoc,
                            valorNombreTrab,
                            valorPuestoTrabaj,
                            valorAreaTrabajo,
                            valor_aRealizada,
                            valorEdad,
                            valorHorarioTrabajo,
                            valorRefrigerio,
                            valorRegimen,
                            valorGroupIng,
                            valorNombreControl,
                            valorGroupAdm,
                            valorGroupSeñal,
                            valorGroupCapac,
                            valorGroupManten,
                            fecha_registro,
                            "UserRegistro"
                    );
                    Vibracion_RegistroDetalle detalle = new Vibracion_RegistroDetalle(
                            -1,
                            valorFuenteGenVibra,
                            valorDescripcionFuente,
                            valorFrecuencia,
                            valorCheckBotas,
                            valorCheckGuantes,
                            valorCheckCasco,
                            valorCheckProtAudi,
                            valorOtroAdm,
                            valorX,
                            valorY,
                            valorZ,
                            fecha_registro,
                            "use_Registro"
                    );

                    DAO_RegistroVibracion nuevoRegistro = new DAO_RegistroVibracion(getActivity());
                    boolean estadoRegistro = nuevoRegistro.RegistroVibracion(registro);
                    boolean estadoDetalle = nuevoRegistro.RegistrarVibracionDetalle(detalle);
                    if(estadoRegistro && estadoDetalle){
                        DAO_FormatosTrabajo dao_fromatosTrabajo = new DAO_FormatosTrabajo(getActivity());
                        for_Vibracion = dao_fromatosTrabajo.Buscar(id_plan_trabajo,id_formato);
                        for_Vibracion.setRealizado(for_Vibracion.getRealizado()+1);
                        for_Vibracion.setPor_realizar(for_Vibracion.getPor_realizar()-1);
                        dao_fromatosTrabajo.actualizarFormatoTrabajo(for_Vibracion);

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
    public void init(View view){
        tv_nombreUsuario = view.findViewById(R.id.tv_nomUsuario);
        tv_nomEmpresa = view.findViewById(R.id.tv_nomEmpresa);

        // Asignación de elementos de la interfaz a variables
        spn_tipoVibracion = view.findViewById(R.id.tipo_vibracion);
        spn_equipoutilizado = view.findViewById(R.id.spn_equipoUtilizado);
        tv_horaVerificacion = view.findViewById(R.id.hora_situ);
        radioGroupVerificacion = view.findViewById(R.id.radioGroup_verf_insitu);
        btnSubirFotoVibra = view.findViewById(R.id.btn_subirFotoVibra);
        tv_fechaMonitoreo = view.findViewById(R.id.tv_fecha);
        tv_horaInicioMoni = view.findViewById(R.id.tv_horaInicial);
        tv_horaFinalMoni = view.findViewById(R.id.tv_horaFinal);
        txt_jornada = view.findViewById(R.id.txt_jornada);
        txt_timeExpo = view.findViewById(R.id.txt_tiempoExposicion);
        spn_tipoDoc = view.findViewById(R.id.cbx_tipoDocumento);
        txt_numDoc = view.findViewById(R.id.txt_numDocumento);
        txt_nomTrab = view.findViewById(R.id.txt_nombre);
        txt_edadTrab = view.findViewById(R.id.txt_edad);
        txt_areaTrab = view.findViewById(R.id.txt_areaTrabajo);
        txt_puestoTrab = view.findViewById(R.id.txt_puestoTrabajo);
        txt_actRealizada = view.findViewById(R.id.txt_actividades);
        spn_horarioTrabajo = view.findViewById(R.id.cbx_horarioTrabajo);
        spn_regimen = view.findViewById(R.id.cbx_regimen);
        spn_horarioRefrig = view.findViewById(R.id.cbx_refrigerio);
        txt_fuenteGenVibra = view.findViewById(R.id.txt_fuenteGeneradora);
        txt_descFuenteGen = view.findViewById(R.id.txt_descripcionFuente);
        radioGroupIng = view.findViewById(R.id.radioGroupIngenieria);
        radioGroupAdm = view.findViewById(R.id.radioGroupAdminis);
        txt_nombreControl = view.findViewById(R.id.txt_nombreControl);
        radioGroupSeñal = view.findViewById(R.id.radioGroup_SeñalArea);
        radioGroupCapac = view.findViewById(R.id.radioGroup_CapacitacionVibra);
        radioGroupMante = view.findViewById(R.id.radioGroup_MatenimientoVibra);
        spn_frecuencia = view.findViewById(R.id.spn_frecuencia);
        check_botas = view.findViewById(R.id.check_botas);
        check_guantes = view.findViewById(R.id.check_guantes);
        check_casco = view.findViewById(R.id.check_casco);
        check_proteccionAud = view.findViewById(R.id.check_proteccionAudi);
        txt_otrosAdmin = view.findViewById(R.id.txt_OtrosAdministrativo);
        txt_resulX = view.findViewById(R.id.txt_x);
        txt_resulY = view.findViewById(R.id.txt_y);
        txt_resulZ = view.findViewById(R.id.txt_z);

        imgVibra = view.findViewById(R.id.img_Vibracion);

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