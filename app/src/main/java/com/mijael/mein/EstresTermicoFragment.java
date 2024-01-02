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
import com.mijael.mein.DAO.DAO_RegistroEstreTermico;
import com.mijael.mein.DAO.DAO_RegistroVibracion;
import com.mijael.mein.Entidades.Equipos;
import com.mijael.mein.Entidades.EstresTermico_Registro;
import com.mijael.mein.Entidades.EstresTermico_RegistroDetalle;
import com.mijael.mein.Entidades.Formatos_Trabajo;
import com.mijael.mein.Extras.FragmentoImagen;
import com.mijael.mein.Extras.InputDateConfiguration;
import com.mijael.mein.Extras.Validaciones;
import com.mijael.mein.HELPER.EquiposSQLiteHelper;
import com.mijael.mein.HELPER.FormatoTrabajoSQLiteHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EstresTermicoFragment extends Fragment implements FragmentoImagen.ImagePickerListener{
    private boolean cargarImagen = false;
    View rootView;
    TextView tv_nombreUsuario, tv_nomEmpresa;
    String id_plan_trabajo, id_pt_trabajo, id_formato,id_colaborador, nom_Empresa;
    AutoCompleteTextView tv_estresTermico, tv_anemometro;
    TextView tv_horaVerificacion, tv_fechaMonitoreo, tv_horaInicioMoni, tv_horaFinalMoni,tv_desTrabajoDetalle, txt_colorPredominante, txt_wbgt01, txt_wbgt11, txt_wbgt17,
            txt_t_aire01, txt_t_aire11, txt_t_aire17, txt_t_globo01, txt_t_globo11, txt_t_globo17, txt_h_relativa01, txt_h_relativa11, txt_h_relativa17,
            txt_velViento, txt_observacion;
    RadioGroup radioGroupVerificacion, radioGroupIng, radioGroupZonaSombra, radioGroupRotacion, radioGroupRecuperacion,radioGroupDispensador, radioGroupCapacitacion;

    AppCompatButton btn_subirFotoEstres;
    FloatingActionButton btn_guardar;
    ExtendedFloatingActionButton btnCancelar;
    EditText txt_timeMedMin, txt_timeExpoHora, txt_jornadaTrabajo, txt_numDoc, txt_nomTrabajador, txt_edad, txt_peso,txt_areaTrabajo, txt_puestoTrabajo, txt_aRealizada,
            txt_descFuenteGen, txt_nomControl, txt_otrosEpps, txt_nomTarea1, txt_cicloTrab1, txt_nomTarea2, txt_cicloTrab2, txt_nomTarea3, txt_cicloTrab3, txt_metroSubida;
    Spinner spn_tipoDoc, spn_horarioTrabajo, spn_regimen, spn_horarioRefrigerio, spn_fuenteGen, spn_descTrabajo, spn_timeCargoAnyo, spn_timeCargoMes, spn_condicionTrab,
            spn_porcActividad, spn_porcDescanso, spn_vestimenta, spn_materialPrenda, spn_posicion1, spn_pCuerpo1, spn_intesidad1, spn_posicion2, spn_pCuerpo2, spn_intesidad2,
            spn_posicion3, spn_pCuerpo3, spn_intesidad3;
    // CheckBox
    CheckBox check_zapatos, check_casco, check_lentes, check_guantes, check_orejeras, check_tapones, check_cubreNuca;
    ImageView imgVibra;
    Uri uri;
    public EstresTermicoFragment() {
        // Required empty public constructor
    }
    Formatos_Trabajo for_Estres;
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
        rootView = inflater.inflate(R.layout.fragment_estres_termico, container, false);
        InputDateConfiguration config = new InputDateConfiguration(getActivity(),id_colaborador,nom_Empresa,rootView);
        init(rootView);
        config.ConfigPantalla();
        DAO_Equipos equipos = new DAO_Equipos(getActivity());
        List<String> lista_CodEquipos = equipos.obtener_CodEquipos();

        spn_tipoDoc.setAdapter(config.LlenarSpinner("DNI", "CE"));
        spn_fuenteGen.setAdapter(config.LlenarSpinner("Natural", "Artificial"));
        spn_descTrabajo.setAdapter(config.LlenarSpinner("Trabajo al aire libre sin carga solar o bajo techo", "Trabajo al aire libre con carga solar"));
        spn_condicionTrab.setAdapter(config.LlenarSpinner("Aclimatado", "No aclimatado"));

        config.configurarAutoCompleteTextView(tv_estresTermico,lista_CodEquipos);
        config.configurarAutoCompleteTextView(tv_anemometro,lista_CodEquipos);

        spn_horarioTrabajo.setAdapter(config.LlenarSpinner("horario_trab_fromato_medicion","desc_horario",getActivity()));
        spn_regimen.setAdapter(config.LlenarSpinner("regimen_formato_medicion","nom_regimen",getActivity()));
        spn_horarioRefrigerio.setAdapter(config.LlenarSpinner("horario_refrig_formato_medicion","nom_horario",getActivity()));
        spn_timeCargoAnyo.setAdapter(config.LlenarSpinner("tiempoAnios","nom_anio",getActivity()));
        spn_timeCargoMes.setAdapter(config.LlenarSpinner("tiempoMeses","nom_mes",getActivity()));
        spn_porcActividad.setAdapter(config.LlenarSpinner("actividad_Descanso","nom_act_des",getActivity()));
        spn_porcDescanso.setAdapter(config.LlenarSpinner("actividad_Descanso","nom_act_des",getActivity()));
        spn_vestimenta.setAdapter(config.LlenarSpinner("vestimenta","nom_vestimenta",getActivity()));
        spn_materialPrenda.setAdapter(config.LlenarSpinner("materialVestimenta","nom_material",getActivity()));
        spn_posicion1.setAdapter(config.LlenarSpinner("posicion","nom_pos",getActivity()));
        spn_posicion2.setAdapter(config.LlenarSpinner("posicion","nom_pos",getActivity()));
        spn_posicion3.setAdapter(config.LlenarSpinner("posicion","nom_pos",getActivity()));
        spn_pCuerpo1.setAdapter(config.LlenarSpinner("partes_cuerpo","nom_cuerpo",getActivity()));
        spn_pCuerpo2.setAdapter(config.LlenarSpinner("partes_cuerpo","nom_cuerpo",getActivity()));
        spn_pCuerpo3.setAdapter(config.LlenarSpinner("partes_cuerpo","nom_cuerpo",getActivity()));
        spn_intesidad1.setAdapter(config.LlenarSpinner("intensidad","nom_intensidad",getActivity()));
        spn_intesidad2.setAdapter(config.LlenarSpinner("intensidad","nom_intensidad",getActivity()));
        spn_intesidad3.setAdapter(config.LlenarSpinner("intensidad","nom_intensidad",getActivity()));



        tv_horaVerificacion.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {config.showTimePickerDialog(rootView,tv_horaVerificacion);}});
        tv_fechaMonitoreo.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {config.showDatePickerDialog(rootView,tv_fechaMonitoreo);}});
        tv_horaInicioMoni.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {config.showTimePickerDialog(rootView,tv_horaInicioMoni);}});
        tv_horaFinalMoni.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {config.showTimePickerDialog(rootView,tv_horaFinalMoni);}});

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
                                validar.validarCampoObligatorio(tv_horaFinalMoni) &&
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
                                validar.validarCampoObligatorio(txt_otrosEpps) &&
                                validar.validarCampoObligatorio(txt_nomTarea1) &&
                                validar.validarCampoObligatorio(txt_cicloTrab1) &&
                                validar.validarCampoObligatorio(spn_posicion1) &&
                                validar.validarCampoObligatorio(spn_pCuerpo1) &&
                                validar.validarCampoObligatorio(spn_intesidad1) &&
                                validar.validarCampoObligatorio(txt_nomTarea2) &&
                                validar.validarCampoObligatorio(txt_cicloTrab2) &&
                                validar.validarCampoObligatorio(spn_posicion2) &&
                                validar.validarCampoObligatorio(spn_pCuerpo2) &&
                                validar.validarCampoObligatorio(spn_intesidad2) &&
                                validar.validarCampoObligatorio(txt_nomTarea3) &&
                                validar.validarCampoObligatorio(txt_cicloTrab3) &&
                                validar.validarCampoObligatorio(spn_posicion3) &&
                                validar.validarCampoObligatorio(spn_pCuerpo3) &&
                                validar.validarCampoObligatorio(spn_intesidad3) &&
                                validar.validarCampoObligatorio(txt_wbgt01) &&
                                validar.validarCampoObligatorio(txt_wbgt11) &&
                                validar.validarCampoObligatorio(txt_wbgt17) &&
                                validar.validarCampoObligatorio(txt_t_aire01) &&
                                validar.validarCampoObligatorio(txt_t_aire11) &&
                                validar.validarCampoObligatorio(txt_t_aire17    ) &&
                                validar.validarCampoObligatorio(txt_t_globo01) &&
                                validar.validarCampoObligatorio(txt_t_globo11) &&
                                validar.validarCampoObligatorio(txt_t_globo17) &&
                                validar.validarCampoObligatorio(txt_h_relativa01) &&
                                validar.validarCampoObligatorio(txt_h_relativa11) &&
                                validar.validarCampoObligatorio(txt_h_relativa17) &&
                                validar.validarCampoObligatorio(txt_velViento) &&
                                validar.validarCampoObligatorio(txt_observacion)
                ){
                    String valorEstresTermico = tv_estresTermico.getText().toString();
                    String valorAnemometro = tv_anemometro.getText().toString();
                    String valorHoraVerificacion = tv_horaVerificacion.getText().toString();
                    String valorGroupVerificacion = validar.getValor2(radioGroupVerificacion,rootView);
                    String valorFechaMonitoreo = tv_fechaMonitoreo.getText().toString();
                    String valorHoraInicioMoni = tv_horaInicioMoni.getText().toString();
                    String valorHoraFinalMoni = tv_horaFinalMoni.getText().toString();
                    String valorTimeMed = txt_timeMedMin.getText().toString();
                    String valorTimeExpo = txt_timeExpoHora.getText().toString();
                    String valorJornada = txt_jornadaTrabajo.getText().toString();
                    String valorTipoDoc = spn_tipoDoc.getSelectedItem().toString();
                    String valorNumDoc = txt_numDoc.getText().toString();
                    String valorNombreTra = txt_nomTrabajador.getText().toString();
                    String valorEdad = txt_edad.getText().toString();
                    String valorPeso = txt_peso.toString().toString();
                    String valorAreaTra = txt_areaTrabajo.toString().toString();
                    String valorPuestoTra = txt_puestoTrabajo.getText().toString();
                    String valorActividades = txt_aRealizada.getText().toString();
                    String valorHorarioTrabajo = spn_horarioTrabajo.getSelectedItem().toString();
                    String valorRegimen = spn_regimen.getSelectedItem().toString();
                    String valorRefrigerio = spn_horarioRefrigerio.getSelectedItem().toString();
                    String valorFuenteGen = spn_fuenteGen.getSelectedItem().toString();
                    String valorDesFuenteGen = txt_descFuenteGen.getText().toString();
                    String valorDesTrabajo = spn_descTrabajo.getSelectedItem().toString();
                    String valorDesTrabDetalle = tv_desTrabajoDetalle.getText().toString();
                    String valorGroupIng = validar.getValor2(radioGroupIng,rootView);
                    String valorControlIng = txt_nomControl.getText().toString();
                    String valorTimeCargoAnyo = spn_timeCargoAnyo.getSelectedItem().toString();
                    String valorTimeCargoMeses = spn_timeCargoMes.getSelectedItem().toString();
                    String valorCondTrabajo = spn_condicionTrab.getSelectedItem().toString();
                    String valorGroupZonaSombra = validar.getValor2(radioGroupZonaSombra, rootView);
                    String valorGroupRotacion = validar.getValor2(radioGroupRotacion, rootView);
                    String valorGroupRecuperacion = validar.getValor2(radioGroupRecuperacion, rootView);
                    String valorGroupDispensador = validar.getValor2(radioGroupDispensador, rootView);
                    String valorGroupCapa = validar.getValor2(radioGroupCapacitacion, rootView);
                    String valorPorcActividad = spn_porcActividad.getSelectedItem().toString();
                    String valorPorDescanso = spn_porcDescanso.getSelectedItem().toString();
                    String valorVestimenta = spn_vestimenta.getSelectedItem().toString();
                    String valorMaterial = spn_materialPrenda.getSelectedItem().toString();
                    String valorColor = txt_colorPredominante.getText().toString();
                    String valorZapatos = String.valueOf(check_zapatos.isChecked());
                    String valorCasco = String.valueOf(check_casco.isChecked());
                    String valorLentes = String.valueOf(check_lentes.isChecked());
                    String valorGuantes = String.valueOf(check_guantes.isChecked());
                    String valorOrejeras = String.valueOf(check_orejeras.isChecked());
                    String valorTapones = String.valueOf(check_tapones.isChecked());
                    String valorCubreNuca = String.valueOf(check_cubreNuca.isChecked());
                    String valorEpps = txt_otrosEpps.getText().toString();
                    String valorTarea1 = txt_nomTarea1.getText().toString();
                    String valorCicloTrab1 = txt_cicloTrab1.getText().toString();
                    String valorPosicion1 = spn_posicion1.getSelectedItem().toString();
                    String valor_pCuerpo1 = spn_pCuerpo1.getSelectedItem().toString();
                    String valorIntensidad1 = spn_intesidad1.getSelectedItem().toString();
                    String valorTarea2 = txt_nomTarea2.getText().toString();
                    String valorCicloTrab2 = txt_cicloTrab2.getText().toString();
                    String valorPosicion2 = spn_posicion2.getSelectedItem().toString();
                    String valor_pCuerpo2 = spn_pCuerpo2.getSelectedItem().toString();
                    String valorIntensidad2 = spn_intesidad2.getSelectedItem().toString();
                    String valorTarea3 = txt_nomTarea3.getText().toString();
                    String valorCicloTrab3 = txt_cicloTrab3.getText().toString();
                    String valorPosicion3 = spn_posicion3.getSelectedItem().toString();
                    String valor_pCuerpo3 = spn_pCuerpo3.getSelectedItem().toString();
                    String valorIntensidad3 = spn_intesidad3.getSelectedItem().toString();
                    String valorMetrosSubida = txt_metroSubida.getText().toString();
                    String valorWbgt01 = txt_wbgt01.getText().toString();
                    String valorWbgt11 = txt_wbgt11.getText().toString();
                    String valorWbgt17 = txt_wbgt17.getText().toString();
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
                    String valorObserbaciones = txt_observacion.getText().toString();

                    String fecha_registro = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                    Equipos equipos1 = equipos.Buscar(valorEstresTermico);
                    Equipos equipos2 = equipos.Buscar(valorAnemometro);

                    EstresTermico_Registro termicoRegistro = new EstresTermico_Registro(
                            -1,
                            "ET-001",
                            id_formato,
                            id_plan_trabajo,
                            id_pt_trabajo,
                            String.valueOf(equipos1.getId_equipo_registro()),
                            equipos1.getCod_equipo(),
                            equipos1.getNombre(),
                            equipos1.getSerie(),
                            String.valueOf(equipos2.getId_equipo_registro()),
                            equipos2.getCod_equipo(),
                            equipos2.getNombre(),
                            equipos2.getSerie(),
                            id_colaborador,
                            "id_Colaboraror.getNombre()",
                            valorHoraVerificacion,
                            valorGroupVerificacion,
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
                            valorHorarioTrabajo,
                            valorRegimen,
                            valorAreaTra,
                            valorDesTrabDetalle,
                            valorGroupIng,
                            valorControlIng,
                            "false",
                            valorTimeCargoAnyo,
                            valorTimeCargoMeses,
                            valorCondTrabajo,
                            valorObserbaciones,
                            fecha_registro,
                            "Usuario Registrado"
                    );
                    EstresTermico_RegistroDetalle registroDetalle = new EstresTermico_RegistroDetalle(
                            -1,
                            valorFuenteGen,
                            valorDesFuenteGen,
                            valorGroupZonaSombra,
                            valorGroupRotacion,
                            valorGroupRecuperacion,
                            valorGroupDispensador,
                            valorGroupCapa,
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
                            valorTarea1,
                            valorCicloTrab1,
                            valorPosicion1,
                            valor_pCuerpo1,
                            valorIntensidad1,
                            valorTarea2,
                            valorCicloTrab2,
                            valorPosicion2,
                            valor_pCuerpo2,
                            valorIntensidad2,
                            valorTarea3,
                            valorCicloTrab3,
                            valorPosicion3,
                            valor_pCuerpo3,
                            valorIntensidad3,
                            valorMetrosSubida,
                            valorWbgt01,
                            valorWbgt11,
                            valorWbgt17,
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
                            fecha_registro,
                            "Usuario registro"
                    );
                    DAO_RegistroEstreTermico nuevoRegistro = new DAO_RegistroEstreTermico(getActivity());
                    boolean estadoRegistro = nuevoRegistro.RegistroEstresTermico(termicoRegistro);
                    boolean estadoDetalle = nuevoRegistro.RegistrarEstresTermicoDetalle(registroDetalle);
                    if(estadoRegistro && estadoDetalle){
                        DAO_FormatosTrabajo dao_fromatosTrabajo = new DAO_FormatosTrabajo(getActivity());
                        for_Estres = dao_fromatosTrabajo.Buscar(id_plan_trabajo,id_formato);
                        for_Estres.setRealizado(for_Estres.getRealizado()+1);
                        for_Estres.setPor_realizar(for_Estres.getPor_realizar()-1);
                        dao_fromatosTrabajo.actualizarFormatoTrabajo(for_Estres);

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

        tv_estresTermico = view.findViewById(R.id.tv_estresTermico);
        tv_anemometro = view.findViewById(R.id.tv_anemometro);
        tv_horaVerificacion = view.findViewById(R.id.tv_horaVerificacion);
        radioGroupVerificacion = view.findViewById(R.id.radioGroupVerificacion);
        btn_subirFotoEstres = view.findViewById(R.id.btn_subirFotoEstres);
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
        spn_horarioTrabajo = view.findViewById(R.id.spn_horarioTrabajo);
        spn_regimen = view.findViewById(R.id.spn_regimen);
        spn_horarioRefrigerio = view.findViewById(R.id.spn_horarioRefrigerio);
        spn_fuenteGen = view.findViewById(R.id.spn_fuenteGen);
        txt_descFuenteGen = view.findViewById(R.id.txt_descFuenteGen);
        spn_descTrabajo = view.findViewById(R.id.spn_descTrabajo);
        tv_desTrabajoDetalle = view.findViewById(R.id.tv_desTrabajoDetalle);
        radioGroupIng = view.findViewById(R.id.radioGroupIng);
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
        txt_nomTarea1 = view.findViewById(R.id.txt_tarea1);
        txt_cicloTrab1 = view.findViewById(R.id.txt_cicloTrabajo);
        spn_posicion1 = view.findViewById(R.id.cbx_posicion);
        spn_pCuerpo1 = view.findViewById(R.id.cbx_partesCuerpo);
        spn_intesidad1 = view.findViewById(R.id.cbx_intensidad);
        txt_nomTarea2 = view.findViewById(R.id.txt_tarea2);
        txt_cicloTrab2 = view.findViewById(R.id.txt_cicloTrabajo2);
        spn_posicion2 = view.findViewById(R.id.cbx_posicion2);
        spn_pCuerpo2 = view.findViewById(R.id.cbx_partesCuerpo2);
        spn_intesidad2 = view.findViewById(R.id.cbx_intensidad2);
        txt_nomTarea3 = view.findViewById(R.id.txt_tarea3);
        txt_cicloTrab3 = view.findViewById(R.id.txt_cicloTrabajo3);
        spn_posicion3 = view.findViewById(R.id.cbx_posicion3);
        spn_pCuerpo3 = view.findViewById(R.id.cbx_partesCuerpo3);
        spn_intesidad3 = view.findViewById(R.id.cbx_intensidad3);
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
        txt_observacion = view.findViewById(R.id.txt_observaciones);

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