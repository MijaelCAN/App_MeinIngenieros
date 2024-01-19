package com.mijael.mein;

import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.mijael.mein.DAO.DAO_Equipos;
import com.mijael.mein.DAO.DAO_RegistroEstreTermico;
import com.mijael.mein.DAO.DAO_RegistroVibracion;
import com.mijael.mein.DAO.DAO_Usuario;
import com.mijael.mein.Entidades.Equipos;
import com.mijael.mein.Entidades.EstresTermico_Registro;
import com.mijael.mein.Entidades.EstresTermico_RegistroDetalle;
import com.mijael.mein.Entidades.Formatos_Trabajo;
import com.mijael.mein.Entidades.Usuario;
import com.mijael.mein.Extras.FragmentoImagen;
import com.mijael.mein.Extras.InputDateConfiguration;
import com.mijael.mein.Extras.Validaciones;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EstresFrioFragment extends Fragment implements FragmentoImagen.ImagePickerListener{
    private boolean cargarImagen = false;
    View rootView;
    TextView tv_nombreUsuario, tv_nomEmpresa;
    String id_plan_trabajo, id_pt_trabajo, id_formato,id_colaborador, nom_Empresa;
    CardView card_ingenier, card_adminis, card_tapones, card_orejeras;
    RadioButton radioIngSi;

    // Spinners
    AutoCompleteTextView spn_equipoEstresFrio, spn_equipoAnemometro;
    Spinner spn_tipoDoc, spn_horarioTrabajo, spn_horarioRefrig, spn_regimen, spn_fuenteGen, spn_condicionTrab, spn_rotacion,
            spn_tiempoRecuperacion, spn_ingestaBebidas, spn_CapacRiesgosExpo,
            spn_nivelDeterminacion, spn_metodoDeterminacion, spn_tiempoCargoAnios,
            spn_tiempoCargoMeses;

    // TextViews
    TextView tv_horaVerificacion, tv_fechaMonitoreo, tv_horaInicioMoni, tv_horaFinalMoni,
            txt_t_aire, txt_t_aireNegro, txt_humedadRelativa, txt_velViento, txt_observaciones,
            txt_nombreControl, txt_otroHorario, txt_otroRegimen, txt_otroRefrigerio;

    // RadioGroup
    RadioGroup radioGroupVerificacion, radioGroupIng;

    // EditTexts
    EditText txt_timeMed, txt_timeExpo, txt_jornada, txt_numDoc, txt_nomTrab, txt_areaTrab,
            txt_puestoTrab, txt_actRealizada, txt_pesoTrab, txt_edadTrab, txt_descFuenteGen;

    // CheckBoxes
    CheckBox check_calz, check_calzLargo, check_camisaTirantes, check_camisaManCorta,
            check_camisaManLarga, check_sujetadorBra, check_mangaCorta, check_ligMangasCortas,
            check_normalMangasLargas, check_franelaMangaLarga, check_ligMangaLarga, check_pantCorto,
            check_pantLigero, check_pantNormal, check_pantFranela, check_pullSinMangas,
            check_pullLigero, check_pullMedio, check_pullGrueso, check_prendaAbrigo,
            check_prendaChaqLarga, check_prendaParka, check_prendaMono, check_chaqLigera,
            check_chaqNormal, check_chaqBata, check_chaqMono, check_divZapatodelgado,
            check_divZapatogrueso, check_divCalcetines, check_divMediasNylon,
            check_divCalcetinesGrueoCorto, check_divCalcetinesGrueoLargo, check_divBotas,
            check_divGuantes;

    // Buttons
    AppCompatButton btnSubirFotoEstres;
    FloatingActionButton btn_guardar;
    ExtendedFloatingActionButton btnCancelar;

    // LinearLayouts
    LinearLayout linearOtroHorario, linearOtroRegimen, linearOtroRefrigerio;
    ImageView imgVibra;
    Uri uri;
    public EstresFrioFragment() {
        // Required empty public constructor
    }
    Formatos_Trabajo for_estresFrio;
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
        rootView = inflater.inflate(R.layout.fragment_estres_frio,container,false);
        InputDateConfiguration config = new InputDateConfiguration(getActivity(),id_colaborador,nom_Empresa,rootView);
        init(rootView);

        DAO_Equipos equipos = new DAO_Equipos(getActivity());
        List<String> lista_CodEquipos = equipos.obtener_CodEquipos();
        DAO_Usuario usuario = new DAO_Usuario(getActivity());
        Usuario nuevo = usuario.BuscarUsuario(Integer.parseInt(id_colaborador));

        config.ConfigPantalla();
        config.configurarAutoCompleteTextView(spn_equipoEstresFrio,lista_CodEquipos);
        config.configurarAutoCompleteTextView(spn_equipoAnemometro,lista_CodEquipos);
        tv_horaVerificacion.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {config.showTimePickerDialog(rootView,tv_horaVerificacion);}});
        tv_fechaMonitoreo.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {config.showDatePickerDialog(rootView,tv_fechaMonitoreo);}});
        tv_horaInicioMoni.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {config.showTimePickerDialog(rootView,tv_horaInicioMoni);}});
        tv_horaFinalMoni.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {config.showTimePickerDialog(rootView,tv_horaFinalMoni);}});
        btnSubirFotoEstres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentoImagen fragmentoImagen = new FragmentoImagen(EstresFrioFragment.this);
                fragmentoImagen.show(getActivity().getSupportFragmentManager(), "imagePicker");
                cargarImagen = true;
            }
        });
        spn_tipoDoc.setAdapter(config.LlenarSpinner(new String[]{"DNI", "CE"}));
        spn_horarioTrabajo.setAdapter(config.LlenarSpinner("horario_trab_fromato_medicion","desc_horario",getActivity()));
        spn_regimen.setAdapter(config.LlenarSpinner("regimen_formato_medicion","nom_regimen",getActivity()));
        spn_horarioRefrig.setAdapter(config.LlenarSpinner("horario_refrig_formato_medicion","nom_horario",getActivity()));
        spn_fuenteGen.setAdapter(config.LlenarSpinner(new String[]{"Natural","Artificail","Natural - artificial"}));
        spn_tiempoCargoAnios.setAdapter(config.LlenarSpinner("tiempoAnios","nom_anio",getActivity()));
        spn_tiempoCargoMeses.setAdapter(config.LlenarSpinner("tiempoMeses","nom_mes",getActivity()));
        spn_condicionTrab.setAdapter(config.LlenarSpinner(new String[]{"Aclimatado", "No aclimatado"}));
        spn_rotacion.setAdapter(config.LlenarSpinner(new String[]{"SI", "NO"}));
        spn_tiempoRecuperacion.setAdapter(config.LlenarSpinner(new String[]{"SI", "NO"}));
        spn_ingestaBebidas.setAdapter(config.LlenarSpinner(new String[]{"SI", "NO"}));
        spn_CapacRiesgosExpo.setAdapter(config.LlenarSpinner(new String[]{"SI", "NO"}));
        spn_nivelDeterminacion.setAdapter(config.LlenarSpinner(new String[]{"Tanteo", "Observación", "Análisis"}));
        spn_metodoDeterminacion.setAdapter(config.LlenarSpinner(new String[]{"Opcion 1", "Opcion 2", "etc"})); //NO SEVISUALIZA CAMPOS EN WEB

        config.MostrarCampos(linearOtroHorario,spn_horarioTrabajo);
        config.MostrarCampos(linearOtroRegimen,spn_regimen);
        config.MostrarCampos(linearOtroRefrigerio,spn_horarioRefrig);

        radioGroupIng.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {@Override public void onCheckedChanged(RadioGroup group, int checkedId) {config.mostrarOpcionesGone(group,checkedId,card_ingenier,radioIngSi);}});
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
                        validar.validarCampoObligatorio(spn_equipoEstresFrio) &&
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
                        validar.validarCampoObligatorio(spn_condicionTrab) &&
                        validar.validarCampoObligatorio(spn_rotacion) &&
                        validar.validarCampoObligatorio(spn_tiempoRecuperacion) &&
                        validar.validarCampoObligatorio(spn_ingestaBebidas) &&
                        validar.validarCampoObligatorio(spn_CapacRiesgosExpo) &&
                        validar.validarCampoObligatorio(spn_nivelDeterminacion) &&
                        validar.validarCampoObligatorio(spn_metodoDeterminacion) &&
                        validar.validarCampoObligatorio(txt_t_aire) &&
                        validar.validarCampoObligatorio(txt_t_aireNegro) &&
                        validar.validarCampoObligatorio(txt_humedadRelativa) &&
                        validar.validarCampoObligatorio(txt_velViento) &&
                        validar.validarCampoObligatorio(txt_observaciones)
                ){
                    String valorEstresFrio = spn_equipoEstresFrio.getText().toString();
                    String valorAnemometro = spn_equipoAnemometro.getText().toString();
                    String valorHoraVerificacion = tv_horaVerificacion.getText().toString();
                    int valorGroupVerificacion = validar.getValor2(radioGroupVerificacion,rootView);
                    String valorFechaMonitoreo = tv_fechaMonitoreo.getText().toString();
                    String valorHoraInicioMoni = tv_horaInicioMoni.getText().toString();
                    String valorHoraFinalMoni = tv_horaFinalMoni.getText().toString();
                    String valorTimeMed = txt_timeMed.getText().toString();
                    String valorTimeExpo = txt_timeExpo.getText().toString();
                    String valorJornada = txt_jornada.getText().toString();
                    String valorTipoDoc = spn_tipoDoc.getSelectedItem().toString();
                    String valorNumDoc = txt_numDoc.getText().toString();
                    String valorNombreTra = txt_nomTrab.getText().toString();
                    String valorEdad = txt_edadTrab.getText().toString();
                    String valorPeso = txt_pesoTrab.toString().toString();
                    String valorAreaTra = txt_areaTrab.toString().toString();
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

                    String valorcheck_calz = String.valueOf(check_calz.isChecked());
                    String valorcheck_calzLargo = String.valueOf(check_calzLargo.isChecked());
                    String valorcheck_camisaTirantes= String.valueOf(check_camisaTirantes.isChecked());
                    String valorcheck_camisaManCorta = String.valueOf(check_camisaManCorta.isChecked());
                    String valorcheck_camisaManLarga = String.valueOf(check_camisaManLarga.isChecked());
                    String valorcheck_sujetadorBra= String.valueOf(check_sujetadorBra.isChecked());

                    String valorcheck_mangacorta = String.valueOf(check_mangaCorta.isChecked());
                    String valorcheck_ligMangasCortas = String.valueOf(check_ligMangasCortas.isChecked());
                    String valorcheck_normalMangasLargas= String.valueOf(check_normalMangasLargas.isChecked());
                    String valorcheck_franelaMangaLarga = String.valueOf(check_franelaMangaLarga.isChecked());
                    String valorcheck_ligMangaLarga = String.valueOf(check_ligMangaLarga.isChecked());

                    String valorcheck_pantCorto = String.valueOf(check_pantCorto.isChecked());
                    String valorcheck_pantLigero = String.valueOf(check_pantLigero.isChecked());
                    String valorcheck_pantNormal= String.valueOf(check_pantNormal.isChecked());
                    String valorcheck_pantFranela= String.valueOf(check_pantFranela.isChecked());

                    String valorcheck_pullSinMangas = String.valueOf(check_pullSinMangas.isChecked());
                    String valorcheck_pullLigero = String.valueOf(check_pullLigero.isChecked());
                    String valorcheck_pullMedio = String.valueOf(check_pullMedio.isChecked());
                    String valorcheck_pullGrueso= String.valueOf(check_pullGrueso.isChecked());

                    String valorcheck_prendaAbrigo= String.valueOf(check_prendaAbrigo.isChecked());
                    String valorcheck_prendaChaqLarga= String.valueOf(check_prendaChaqLarga.isChecked());
                    String valorcheck_prendaParka = String.valueOf(check_prendaParka.isChecked());
                    String valorcheck_prendaMono = String.valueOf(check_prendaMono.isChecked());

                    String valorcheck_chaqLigera= String.valueOf(check_chaqLigera.isChecked());
                    String valorcheck_chaqNormal= String.valueOf(check_chaqNormal.isChecked());
                    String valorcheck_chaqBata = String.valueOf(check_chaqBata.isChecked());
                    String valorcheck_chaqMono = String.valueOf(check_chaqMono.isChecked());

                    String valorcheck_divZapatodelgado = String.valueOf(check_divZapatodelgado.isChecked());
                    String valorcheck_divZapatogrueso = String.valueOf(check_divZapatogrueso.isChecked());
                    String valorcheck_divCalcetines= String.valueOf(check_divCalcetines.isChecked());
                    String valorcheck_divMediasNylon = String.valueOf(check_divMediasNylon.isChecked());
                    String valorcheck_divCalcetinesGrueoCorto = String.valueOf(check_divCalcetinesGrueoCorto.isChecked());
                    String valorcheck_divCalcetinesGrueoLargo = String.valueOf(check_divCalcetinesGrueoLargo.isChecked());
                    String valorcheck_divBotas = String.valueOf(check_divBotas.isChecked());
                    String valorcheck_divGuantes = String.valueOf(check_divGuantes.isChecked());

                    int valorGroupIng = validar.getValor2(radioGroupIng,rootView);
                    String valorNombreControlIng = txt_nombreControl.getText().toString();
                    String valorTimeCargoAnyo = spn_tiempoCargoAnios.getSelectedItem().toString();
                    String valorTimeCargoMeses = spn_tiempoCargoMeses.getSelectedItem().toString();
                    String valorCondTrabajo = spn_condicionTrab.getSelectedItem().toString();
                    String valorRotacion = spn_rotacion.getSelectedItem().toString();
                    String valorRecuperacion = spn_tiempoRecuperacion.getSelectedItem().toString();
                    String valorIngestaBebida = spn_ingestaBebidas.getSelectedItem().toString();
                    String valorCapacRiesgosExpo = spn_CapacRiesgosExpo.getSelectedItem().toString();

                    String valorNivelDeterminacion = spn_nivelDeterminacion.getSelectedItem().toString();
                    String valorMetodoDeterminacion = spn_metodoDeterminacion.getSelectedItem().toString();

                    String valorAire = txt_t_aire.getText().toString();
                    String valorTAireNegro = txt_t_aireNegro.getText().toString();
                    String valorHumedadRelativa = txt_humedadRelativa.getText().toString();
                    String valorVelViento = txt_velViento.getText().toString();
                    String valorObserbaciones = txt_observaciones.getText().toString();

                    String estado_resultado = "1";
                    String fecha_registro = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

                    Equipos equipo1 = equipos.Buscar(valorEstresFrio);
                    Equipos equipo2 = equipos.Buscar(valorAnemometro);

                    /*Estres registro = new EstresTermico_Registro(
                            -1,
                            "ES-001",
                            id_formato,
                            id_plan_trabajo,
                            id_pt_trabajo,
                            String.valueOf(equipo1.getId_equipo_registro()),
                            equipo1.getCod_equipo(),
                            equipo1.getNombre(),
                            equipo1.getSerie(),
                            String.valueOf(equipo2.getId_equipo_registro()),
                            equipo2.getCod_equipo(),
                            equipo2.getNombre(),
                            equipo2.getSerie(),
                            id_colaborador,
                            nuevo.getUsuario_nombres(),
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
                            valorRefrigerio,
                            valorRegimen,
                    );

                    EstresTermico_RegistroDetalle detalle = new EstresTermico_RegistroDetalle(

                    );*/

                    if(config.isOnline()){

                        new AlertDialog.Builder(getContext())
                                .setTitle("Registro guardado en WEB")
                                .setMessage("El registro ha sido guardado exitosamente.")
                                .setPositiveButton(android.R.string.ok, null)
                                .show();
                        getFragmentManager().popBackStack();
                    }else{
                        DAO_RegistroEstreTermico nuevoRegistro = new DAO_RegistroEstreTermico(getActivity());
                        //boolean estadoRegistro = nuevoRegistro.RegistroEstresTermico(registro);
                        //boolean estadoDetalle = nuevoRegistro.RegistrarEstresTermicoDetalle(detalle);
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

        // Asignación de elementos de la interfaz a variables
        spn_equipoEstresFrio = view.findViewById(R.id.tv_estreFrio);
        spn_equipoAnemometro = view.findViewById(R.id.tv_anemometro);
        tv_horaVerificacion = view.findViewById(R.id.tv_horaVerificacion);
        radioGroupVerificacion = view.findViewById(R.id.radioGroup_verf_insitu);
        btnSubirFotoEstres = view.findViewById(R.id.btn_subirFotoEstres);
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
        check_calz = view.findViewById(R.id.check_calz);
        check_calzLargo = view.findViewById(R.id.check_calzLargo);
        check_camisaTirantes = view.findViewById(R.id.check_camisaTirantes);
        check_camisaManCorta = view.findViewById(R.id.check_camisaManCorta);
        check_camisaManLarga = view.findViewById(R.id.check_camisaManLarga);
        check_sujetadorBra = view.findViewById(R.id.check_sujetadorBra);
        check_mangaCorta = view.findViewById(R.id.check_mangaCorta);
        check_ligMangasCortas = view.findViewById(R.id.check_ligMangasCortas);
        check_normalMangasLargas = view.findViewById(R.id.check_normalMangasLargas);
        check_franelaMangaLarga = view.findViewById(R.id.check_franelaMangaLarga);
        check_ligMangaLarga = view.findViewById(R.id.check_ligMangaLarga);
        check_pantCorto = view.findViewById(R.id.check_pantCorto);
        check_pantLigero = view.findViewById(R.id.check_pantLigero);
        check_pantNormal = view.findViewById(R.id.check_pantNormal);
        check_pantFranela = view.findViewById(R.id.check_pantFranela);
        check_pullSinMangas = view.findViewById(R.id.check_pullSinMangas);
        check_pullLigero = view.findViewById(R.id.check_pullLigero);
        check_pullMedio = view.findViewById(R.id.check_pullMedio);
        check_pullGrueso = view.findViewById(R.id.check_pullGrueso);
        check_prendaAbrigo = view.findViewById(R.id.check_prendaAbrigo);
        check_prendaChaqLarga = view.findViewById(R.id.check_prendaChaqLarga);
        check_prendaParka = view.findViewById(R.id.check_prendaParka);
        check_prendaMono = view.findViewById(R.id.check_prendaMono);
        check_chaqLigera = view.findViewById(R.id.check_chaqLigera);
        check_chaqNormal = view.findViewById(R.id.check_chaqNormal);
        check_chaqBata = view.findViewById(R.id.check_chaqBata);
        check_chaqMono = view.findViewById(R.id.check_chaqMono);
        check_divZapatodelgado = view.findViewById(R.id.check_divZapatodelgado);
        check_divZapatogrueso = view.findViewById(R.id.check_divZapatogrueso);
        check_divCalcetines = view.findViewById(R.id.check_divCalcetines);
        check_divMediasNylon = view.findViewById(R.id.check_divMediasNylon);
        check_divCalcetinesGrueoCorto = view.findViewById(R.id.check_divCalcetinesGrueoCorto);
        check_divCalcetinesGrueoLargo = view.findViewById(R.id.check_divCalcetinesGrueoLargo);
        check_divBotas = view.findViewById(R.id.check_divBotas);
        check_divGuantes = view.findViewById(R.id.check_divGuantes);
        radioGroupIng = view.findViewById(R.id.radioGroupIngenieria);
        txt_nombreControl = view.findViewById(R.id.txt_nomControl);
        spn_tiempoCargoAnios = view.findViewById(R.id.cbx_tiempoCargoAnios);
        spn_tiempoCargoMeses = view.findViewById(R.id.cbx_tiempoCargoMeses);
        spn_condicionTrab = view.findViewById(R.id.spn_condicionTrab);
        spn_rotacion = view.findViewById(R.id.spn_rotacion);
        spn_tiempoRecuperacion = view.findViewById(R.id.spn_tiempoRecuperacion);
        spn_ingestaBebidas = view.findViewById(R.id.spn_ingestaBebidas);
        spn_CapacRiesgosExpo = view.findViewById(R.id.spn_CapacRiesgosExpo);
        spn_nivelDeterminacion = view.findViewById(R.id.spn_nivelDeterminacion);
        spn_metodoDeterminacion = view.findViewById(R.id.spn_metodoDeterminacion);
        txt_t_aire = view.findViewById(R.id.txt_t_aire);
        txt_t_aireNegro = view.findViewById(R.id.txt_t_aireNegro);
        txt_humedadRelativa = view.findViewById(R.id.txt_humedadRelativa);
        txt_velViento = view.findViewById(R.id.txt_velViento);
        txt_observaciones = view.findViewById(R.id.txt_observaciones);

        btn_guardar = view.findViewById(R.id.fabGuardar);
        btnCancelar = view.findViewById(R.id.fabCancelar);

        linearOtroHorario = view.findViewById(R.id.linearOtroHorario);
        txt_otroHorario = view.findViewById(R.id.txt_otroHorario);
        linearOtroRegimen = view.findViewById(R.id.linearOtroRegimen);
        txt_otroRegimen = view.findViewById(R.id.txt_otroRegimen);
        linearOtroRefrigerio = view.findViewById(R.id.linearOtroRefrigerio);
        txt_otroRefrigerio = view.findViewById(R.id.txt_otroRefrigerio);

        card_ingenier = view.findViewById(R.id.Card_Ingenieria);
        radioIngSi = view.findViewById(R.id.radioIngenieriaSi);


    }

    @Override
    public void onImagePicked(Uri imageUri) {
        this.uri = imageUri;
        if (imgVibra != null && imageUri != null) {
            imgVibra.setImageURI(imageUri);
        }
    }
}