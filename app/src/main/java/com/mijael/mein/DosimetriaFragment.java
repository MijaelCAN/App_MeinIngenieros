package com.mijael.mein;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.mijael.mein.DAO.DAO_DatosLocal;
import com.mijael.mein.DAO.DAO_Equipos;
import com.mijael.mein.DAO.DAO_FormatosTrabajo;
import com.mijael.mein.DAO.DAO_RegistroDosimetria;
import com.mijael.mein.DAO.DAO_Usuario;
import com.mijael.mein.Entidades.Dosimetria_Registro;
import com.mijael.mein.Entidades.Equipos;
import com.mijael.mein.Entidades.Formatos_Trabajo;
import com.mijael.mein.Entidades.Prueba;
import com.mijael.mein.Entidades.Usuario;
import com.mijael.mein.Extras.FragmentoImagen;
import com.mijael.mein.Extras.OnBackPressedListener;
import com.mijael.mein.Extras.Validaciones;
import com.mijael.mein.HELPER.EquiposSQLiteHelper;
import com.mijael.mein.HELPER.FormatoTrabajoSQLiteHelper;
import com.mijael.mein.SERVICIOS.DosimetriaService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DosimetriaFragment extends Fragment implements FragmentoImagen.ImagePickerListener{
    Spinner cbx_nivel,cbx_variacion,cbx_tipoDoc, cbx_refrigerio, cbx_regimen, cbx_marcaTapon, cbx_marcaOrej,
            cbx_tiempoCargo, cbx_horarioTrabajo, cbx_meses, cbx_anio, cbx_modeloTapon, cbx_modeloOrej;
    ExtendedFloatingActionButton fabCancelar;
    RadioGroup radioGroup_Oido, radioGroup_Enferm, radioGroup_Ingenieria, radioGroup_Adminis, radioGroup_Tapones, radioGroup_Orejeras,
            radioGroup_Aislante, radioGroup_Fachada, radioGroup_Techo, radioGroup_Cerramiento,radioGroup_Cabinas, radioGroup_Capac, radioGroup_SenalPresion, radioGroup_SenalEpps,
    radioGroup_AdmTiempoExpo,radioGroup_Rotacion;
    RadioButton radio_OidoSi, radio_enferSi, radio_ingenierSI, radio_adminisSI, radio_taponesSI,radio_orejerasSI;
    CardView card_ingenier, card_adminis, card_tapones, card_orejeras;
    FloatingActionButton fabGuardar;
    EditText txt_numDoc, txt_nombre, txt_edad, txt_areaTrabajo, txt_puestoTrabajo, txt_actividades,
    txt_leq, txt_lpico, txt_lmax, txt_lmin, txt_observaciones, txt_detalleEnferm;
    TextView tv_hora,tv_horaInicial, tv_horaFinal, tv_fecha, txt_otro, tv_imagen, tv_nombreUsuario, tv_nomEmpresa, tv_nrrTapones, tv_nrrOrej;
    EditText txt_otroMotivo, txt_jornada, txt_tiempoExposicion, txt_OtroIngenieria, txt_otroAdministrativo;
    CheckBox check_ruidoGenerado, check_ruidoExterno, check_Contiguo;
    AutoCompleteTextView tv_dosimetro, tv_calibrador;
    List<String> lista_codigos;
    ArrayList<String> listaCod_Equipo;
    String id_plan_trabajo, id_pt_trabajo, id_formato, id_colaborador, nom_Empresa;
    Uri uri;
    ImageView image;
    Formatos_Trabajo for_Dosimetria;
    public DosimetriaFragment() {
        // Required empty public constructor
    }
    Validaciones validar = new Validaciones();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (getArguments() != null) {
            id_formato = bundle.getString("id_formato");
            id_plan_trabajo = bundle.getString("id_plan_Trabajo");
            id_pt_trabajo = bundle.getString("id_pt_formato");
            id_colaborador = bundle.getString("nomUsuario");
            nom_Empresa = bundle.getString("nomEmpresa");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_dosimetria, container, false);
        init(rootView);

        ConfigPantalla();

        DAO_Equipos nuevoequipo = new DAO_Equipos(getActivity());
        lista_codigos = nuevoequipo.obtener_CodEquipos();

        ArrayList<String> tiposDocumento = new ArrayList<>();
        tiposDocumento.add("DNI");
        tiposDocumento.add("CE");

        DAO_Usuario usuario = new DAO_Usuario(getActivity());
        Usuario nuevo = usuario.BuscarUsuario(Integer.parseInt(id_colaborador));
        String cadena = nuevo.getUsuario_nombres() + " "+ nuevo.getUsuario_apater();
        tv_nombreUsuario.setText(cadena);
        tv_nomEmpresa.setText(nom_Empresa);



        configurarAutoCompleteTextView(tv_dosimetro, lista_codigos);
        configurarAutoCompleteTextView(tv_calibrador, lista_codigos);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, tiposDocumento);

        cbx_tipoDoc.setAdapter(adapter2);
        cbx_nivel.setAdapter(LlenarSpinner("nivel_formato_medicion","nom_nivel",getActivity()));
        cbx_variacion.setAdapter(LlenarSpinner("variacion_formato_medicion","nom_variacion",getActivity()));
        cbx_regimen.setAdapter(LlenarSpinner("regimen_formato_medicion","nom_regimen",getActivity()));
        cbx_refrigerio.setAdapter(LlenarSpinner("horario_refrig_formato_medicion","nom_horario",getActivity()));
        cbx_tiempoCargo.setAdapter(LlenarSpinner("tiempo_trab_formato_medicion","nom_tiempo_trab",getActivity()));
        cbx_horarioTrabajo.setAdapter(LlenarSpinner("horario_trab_fromato_medicion","desc_horario",getActivity()));
        cbx_meses.setAdapter(LlenarSpinner("meses","nom_mes",getActivity()));
        cbx_anio.setAdapter(LlenarSpinner("anios","nom_anio",getActivity()));
        cbx_marcaTapon.setAdapter(LlenarSpinner("marca_formato_medicion","nom_marca",getActivity()));
        cbx_marcaOrej.setAdapter(LlenarSpinner("marca_formato_medicion","nom_marca",getActivity()));
        cbx_modeloTapon.setAdapter(LlenarSpinner("modelo_Tapones","nom_modelo",getActivity()));
        cbx_modeloOrej.setAdapter(LlenarSpinner("modelo_orejeras","nom_modelo",getActivity()));
        tv_hora.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {showTimePickerDialog(rootView,tv_hora);}});
        tv_horaInicial.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {showTimePickerDialog(rootView,tv_horaInicial);}});
        tv_horaFinal.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {showTimePickerDialog(rootView,tv_horaFinal);}});
        tv_fecha.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {showDatePickerDialog(rootView,tv_fecha);}});

        LlenarNrr("modelo_Tapones","nrr",cbx_modeloTapon,tv_nrrTapones);
        LlenarNrr("modelo_orejeras","nrr",cbx_modeloOrej,tv_nrrOrej);

        check_ruidoGenerado.setOnCheckedChangeListener(((buttonView, isChecked) -> txt_otroMotivo.setVisibility(isChecked?View.VISIBLE:View.GONE)));

        radioGroup_Enferm.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {@Override public void onCheckedChanged(RadioGroup group, int checkedId) {mostrarOpcionesEnferm(group,checkedId);}});
        radioGroup_Ingenieria.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {@Override public void onCheckedChanged(RadioGroup group, int checkedId) {mostrarOpcionesGone(group,checkedId,card_ingenier,radio_ingenierSI);}});
        radioGroup_Adminis.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {@Override public void onCheckedChanged(RadioGroup group, int checkedId) {mostrarOpcionesGone(group,checkedId,card_adminis,radio_adminisSI);}});
        radioGroup_Tapones.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {@Override public void onCheckedChanged(RadioGroup group, int checkedId) {mostrarOpcionesGone(group,checkedId,card_tapones,radio_taponesSI);}});
        radioGroup_Orejeras.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {@Override public void onCheckedChanged(RadioGroup group, int checkedId) {mostrarOpcionesGone(group,checkedId,card_orejeras,radio_orejerasSI);}});

        tv_imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentoImagen fragmentoImagen = new FragmentoImagen(DosimetriaFragment.this);
                fragmentoImagen.show(getActivity().getSupportFragmentManager(), "imagePicker");
            }
        });

        fabCancelar.setOnClickListener(new View.OnClickListener() {
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
        fabGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(     validar.validarCampoObligatorio(tv_dosimetro)&&
                        validar.validarCampoObligatorio(tv_calibrador)&&
                        validar.validarCampoObligatorio(tv_hora)&&
                        validar.validarCampoObligatorio(cbx_nivel)&&
                        validar.validarCampoObligatorio(cbx_variacion)&&
                        validar.validarCampoObligatorio(tv_fecha)&&
                        validar.validarCampoObligatorio(tv_horaInicial)&&
                        validar.validarCampoObligatorio(tv_horaFinal)&&
                        validar.validarCampoObligatorio(txt_jornada)&&
                        validar.validarCampoObligatorio(txt_tiempoExposicion)&&
                        validar.validarCampoObligatorio(txt_otroMotivo)&&
                        validar.validarCampoObligatorio(txt_otro)&&
                        // CAMPO DE FOTOGRAFIA
                        validar.validarCampoObligatorio(cbx_tipoDoc)&&
                        validar.validarCampoObligatorio(txt_numDoc)&&
                        validar.validarCampoObligatorio(txt_nombre)&&
                        validar.validarCampoObligatorio(txt_edad)&&
                        validar.validarCampoObligatorio(txt_areaTrabajo)&&
                        validar.validarCampoObligatorio(txt_puestoTrabajo)&&
                        validar.validarCampoObligatorio(txt_actividades)&&
                        validar.validarCampoObligatorio(cbx_horarioTrabajo)&&
                        validar.validarCampoObligatorio(cbx_regimen)&&
                        validar.validarCampoObligatorio(cbx_refrigerio)&&
                        validar.validarCampoObligatorio(cbx_tiempoCargo)&&
                        validar.validarCampoObligatorio(cbx_meses)&&
                        validar.validarCampoObligatorio(cbx_anio)&&
                        validar.validarCampoObligatorio(txt_OtroIngenieria)&&
                        validar.validarCampoObligatorio(txt_otroAdministrativo)&&
                        validar.validarCampoObligatorio(txt_leq)&&
                        validar.validarCampoObligatorio(txt_lpico)&&
                        validar.validarCampoObligatorio(txt_lmax)&&
                        validar.validarCampoObligatorio(txt_lmin)&&
                        validar.validarCampoObligatorio(txt_observaciones)
                ){
                    String valorTvDosimetro = tv_dosimetro.getText().toString();
                    String valorTvCalibrador = tv_calibrador.getText().toString();
                    if(!valorTvDosimetro.equals(valorTvCalibrador)){
                        String valorTvHora = tv_hora.getText().toString();
                        String valorTvFecha = tv_fecha.getText().toString();
                        String valorTvH_Inicial = tv_horaInicial.getText().toString();
                        String valorTvH_Final = tv_horaFinal.getText().toString();

                        String valorTxtJornada = txt_jornada.getText().toString();
                        String valorTxtTiempo_Expo = txt_tiempoExposicion.getText().toString();
                        String valorTxtOtroMotivo = txt_otroMotivo.getText().toString();
                        String valorTxtOtro = txt_otro.getText().toString();
                        String valorTxtNumDoc = txt_numDoc.getText().toString();
                        String valorTxtNombre = txt_nombre.getText().toString();
                        String valorTxtEdad = txt_edad.getText().toString();
                        String valorTxtAreaTrabajo = txt_areaTrabajo.getText().toString();
                        String valorTxtPuestoTrabajo = txt_puestoTrabajo.getText().toString();
                        String valorTxtActividades = txt_actividades.getText().toString();

                        String valorTxtOtroIngenieria = txt_OtroIngenieria.getText().toString();
                        String valorTxtOtroAdministrativo = txt_otroAdministrativo.getText().toString();


                        String valorRuidoExterno = check_ruidoExterno.getText().toString();
                        String valorRuidocontiguo = check_Contiguo.getText().toString();
                        String valorRuidoGenePor = check_ruidoGenerado.getText().toString();

                        String valorMolestiOido = validar.getValor(radioGroup_Oido,rootView);
                        String valorEnferOido= validar.getValor(radioGroup_Enferm,rootView);
                        String valorDetalleEnf = txt_detalleEnferm.getText().toString();
                        String valorGroupIng = validar.getValor2(radioGroup_Ingenieria,rootView);
                        String valorGroupAislante = validar.getValor2(radioGroup_Aislante,rootView);
                        String valorGroupFachada = validar.getValor2(radioGroup_Fachada,rootView);
                        String valorGroupTechos = validar.getValor2(radioGroup_Techo,rootView);
                        String valorGroupCerramiento = validar.getValor2(radioGroup_Cerramiento,rootView);
                        String valorGroupCabinas = validar.getValor2(radioGroup_Cabinas,rootView);
                        String valorGroupAdm = validar.getValor2(radioGroup_Adminis,rootView);
                        String valorGroupCapac = validar.getValor2(radioGroup_Capac,rootView);
                        String valorGroupSenalPresion = validar.getValor2(radioGroup_SenalPresion,rootView);
                        String valorGroupSenalEpp = validar.getValor2(radioGroup_SenalEpps,rootView);
                        String valorGroupAdmTiempoExpo = validar.getValor2(radioGroup_AdmTiempoExpo,rootView);
                        String valorGroupRotacion = validar.getValor2(radioGroup_Rotacion,rootView);
                        String valorGroupoTapones = validar.getValor2(radioGroup_Tapones,rootView);
                        String valorGroupOrejeras = validar.getValor2(radioGroup_Orejeras,rootView);
                        String valorMarcaTapones = cbx_marcaTapon.getSelectedItem().toString();
                        String valorModeloTapones = cbx_modeloTapon.getSelectedItem().toString();
                        String valorNrrTapones = tv_nrrTapones.getText().toString();
                        String valorMarcaOrejeras = cbx_marcaOrej.getSelectedItem().toString();
                        String valorModeloOrjeras = cbx_modeloOrej.getSelectedItem().toString();
                        String valorNrrOrejeras = tv_nrrOrej.getText().toString();

                        String valorTxtLeq = txt_leq.getText().toString();
                        String valorTxtLpico = txt_lpico.getText().toString();
                        String valorTxtLmax = txt_lmax.getText().toString();
                        String valorTxtLmin = txt_lmin.getText().toString();
                        String valorTxtObservaciones = txt_observaciones.getText().toString();

                        String valorSeleccionadoCbxNivel = cbx_nivel.getSelectedItem().toString();
                        String valorSeleccionadoCbxVariacion = cbx_variacion.getSelectedItem().toString();
                        String valorSeleccionadoCbxTipoDoc = cbx_tipoDoc.getSelectedItem().toString();
                        String valorSeleccionadoCbxHoraioTrabajo = cbx_horarioTrabajo.getSelectedItem().toString();
                        String valorSeleccionadoCbxRegimen = cbx_regimen.getSelectedItem().toString();
                        String valorSeleccionadoCbxRefrigerio = cbx_refrigerio.getSelectedItem().toString();
                        String valorSeleccionadoCbxTiempoCargo = cbx_tiempoCargo.getSelectedItem().toString();
                        String valorSeleccionadoCbxMeses = cbx_meses.getSelectedItem().toString();
                        String valorSeleccionadoCbxAnio = cbx_anio.getSelectedItem().toString();

                        String estado_resultado = "1";
                        String fecha_registro = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

                        Equipos equipo1 = nuevoequipo.Buscar(valorTvDosimetro);
                        Equipos equipo2 = nuevoequipo.Buscar(valorTvCalibrador);

                        Dosimetria_Registro registro = new Dosimetria_Registro( //AGREGANDO LOS VALORES A LA ENTIDAD DEL FORMATO DE DOSIMETRIA
                                -1,
                                "DO-001",
                                id_formato,
                                id_plan_trabajo,
                                id_pt_trabajo,
                                equipo1.getCod_equipo(),
                                equipo1.getNombre(),
                                equipo2.getCod_equipo(),
                                equipo2.getNombre(),
                                equipo1.getSerie(),
                                equipo2.getSerie(),
                                String.valueOf(equipo1.getId_equipo_registro()),
                                String.valueOf(equipo2.getId_equipo_registro()),
                                id_colaborador,
                                "id_Colaborador.getNombre()",
                                valorTvHora,
                                valorSeleccionadoCbxNivel,
                                valorSeleccionadoCbxVariacion,
                                valorTvFecha,
                                valorTvH_Inicial,
                                valorTvH_Final,
                                valorTxtTiempo_Expo,
                                valorTxtJornada,
                                valorSeleccionadoCbxTipoDoc,
                                valorTxtNumDoc,
                                valorTxtNombre,
                                valorTxtPuestoTrabajo,
                                valorTxtAreaTrabajo,
                                valorTxtActividades,
                                valorTxtEdad,
                                valorRuidoExterno,
                                valorRuidocontiguo,
                                valorRuidoGenePor,
                                valorTxtOtroMotivo,
                                valorTxtOtro,
                                valorSeleccionadoCbxHoraioTrabajo,
                                valorSeleccionadoCbxRegimen,
                                valorSeleccionadoCbxRefrigerio,
                                valorSeleccionadoCbxTiempoCargo,
                                valorMolestiOido,
                                valorEnferOido,
                                valorDetalleEnf,
                                "valor referencial",
                                valorSeleccionadoCbxMeses,
                                valorSeleccionadoCbxAnio,
                                valorGroupIng,
                                valorGroupAislante,
                                valorGroupTechos,
                                valorGroupCabinas,
                                valorGroupFachada,
                                valorGroupCerramiento,
                                valorTxtOtroIngenieria,
                                valorGroupAdm,
                                valorGroupCapac,
                                valorGroupSenalPresion,
                                valorGroupSenalEpp,
                                valorGroupRotacion,
                                valorGroupAdmTiempoExpo,
                                valorTxtOtroAdministrativo,
                                valorGroupoTapones,
                                valorMarcaTapones,
                                valorModeloTapones,
                                valorNrrTapones,
                                valorGroupOrejeras,
                                valorMarcaOrejeras,
                                valorModeloOrjeras,
                                valorNrrOrejeras,
                                valorTxtLeq,
                                valorTxtLpico,
                                valorTxtLmax,
                                valorTxtLmin,
                                valorTxtObservaciones,
                                "1",
                                id_colaborador,
                                fecha_registro
                        );
                        DAO_RegistroDosimetria nuevoRegistro = new DAO_RegistroDosimetria(getActivity());//INSTANCIAR LA CLASE PARA USAR EL METODO DE INSERTAR
                        boolean confirmar = nuevoRegistro.RegistrarFormato(registro); //LLAMAR AL METODO PARA INSERTAR LOS REGISTROS
                        if(confirmar){

                            //Prueba
                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl("https://test.meiningenieros.pe/")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();

                            DosimetriaService service1 = retrofit.create(DosimetriaService.class);

                            Gson gson = new Gson();
                            // Convertir el objeto persona a JSON
                            String cadenaJson = gson.toJson(registro);
                            RequestBody json = RequestBody.create(MediaType.parse("application/json"), cadenaJson);

                            Call<ResponseBody> call1 = service1.insertData(json);
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
                            for_Dosimetria = dao_fromatosTrabajo.Buscar(id_plan_trabajo,id_formato);
                            for_Dosimetria.setRealizado(for_Dosimetria.getRealizado()+1);
                            for_Dosimetria.setPor_realizar(for_Dosimetria.getPor_realizar()-1);
                            dao_fromatosTrabajo.actualizarFormatoTrabajo(for_Dosimetria);

                            // O muestra un AlertDialog con el mensaje
                            new AlertDialog.Builder(getActivity())
                                    .setTitle("Registro guardado")
                                    .setMessage("El registro ha sido guardado exitosamente.")
                                    .setPositiveButton(android.R.string.ok, null)
                                    .show();

                            // Regresa al Fragment anterior
                            getFragmentManager().popBackStack();

                        }

                    }else {
                        tv_calibrador.setError("Equipo no debe Repetir");
                        tv_calibrador.requestFocus();
                    }
                }
            }
        });

        return rootView;
    }
    @Override
    public void onImagePicked(Uri imageUri) {
        this.uri = imageUri;
        if (image != null && imageUri != null) {
            image.setImageURI(imageUri);
        }
    }

    private void mostrarOpcionesEnferm(RadioGroup group, int checkedId) {
        if (checkedId == radio_enferSi.getId()) {
            txt_detalleEnferm.setVisibility(View.VISIBLE);
        } else {
            txt_detalleEnferm.setVisibility(View.GONE);
        }
    }
    private void mostrarOpcionesGone(RadioGroup group, int checkedId, CardView card, RadioButton radio) {
        if (checkedId == radio.getId()) {
            card.setVisibility(View.VISIBLE);
        } else {
            card.setVisibility(View.GONE);
        }
    }

    private void init(View view){
        //AutoCompletes Texts
        tv_dosimetro =  view.findViewById(R.id.tv_dosimetro);
        tv_calibrador = view.findViewById(R.id.tv_calibrador);
        tv_nombreUsuario = view.findViewById(R.id.tv_nomUsuario);
        tv_nomEmpresa = view.findViewById(R.id.tv_nomEmpresa);

        image = view.findViewById(R.id.img_Dosimetria);

        //Botones Flotantes
        fabCancelar = view.findViewById(R.id.fabCancelar);
        fabGuardar = view.findViewById(R.id.fabGuardar);
        //btn_subirArchivo = view.findViewById(R.id.btn_abrirArchivo);

        cbx_nivel = view.findViewById(R.id.cbx_nivel);
        cbx_variacion = view.findViewById(R.id.cbx_variacion);
        cbx_regimen = view.findViewById(R.id.cbx_regimen);
        cbx_refrigerio = view.findViewById(R.id.cbx_refrigerio);
        cbx_tiempoCargo = view.findViewById(R.id.cbx_tiempoCargo);
        cbx_horarioTrabajo = view.findViewById(R.id.cbx_horarioTrabajo);
        cbx_tipoDoc = view.findViewById(R.id.cbx_tipoDocumento);
        cbx_meses = view.findViewById(R.id.cbx_meses);
        cbx_anio = view.findViewById(R.id.cbx_anio);
        cbx_marcaTapon = view.findViewById(R.id.cbx_marcaTapones);
        cbx_marcaOrej = view.findViewById(R.id.cbx_marcaOrej);
        cbx_modeloTapon = view.findViewById(R.id.cbx_modeloTapones);
        cbx_modeloOrej =view.findViewById(R.id.cbx_modeloOrej);
        tv_nrrTapones = view.findViewById(R.id.tv_nrrTapones);
        tv_nrrOrej = view.findViewById(R.id.tv_nrrOrej);

        //Cajas de Texto
        tv_hora = view.findViewById(R.id.tv_Hora);
        tv_horaInicial = view.findViewById(R.id.tv_horaInicial);
        tv_horaFinal = view.findViewById(R.id.tv_horaFinal);
        tv_fecha = view.findViewById(R.id.tv_fecha);
        tv_imagen = view.findViewById(R.id.btn_subirFotoDosi);

        check_ruidoExterno = view.findViewById(R.id.check_ruidoExterno);
        check_Contiguo = view.findViewById(R.id.check_ruidoContiguo);
        check_ruidoGenerado = view.findViewById(R.id.check_ruidoGenerado);

        txt_otro = view.findViewById(R.id.txt_otro);
        txt_otroMotivo = view.findViewById(R.id.txt_otroMotivo);
        txt_jornada = view.findViewById(R.id.txt_jornada);
        txt_tiempoExposicion = view.findViewById(R.id.txt_tiempoExposicion);
        txt_numDoc = view.findViewById(R.id.txt_numDocumento);
        txt_nombre = view.findViewById(R.id.txt_nombre);
        txt_edad = view.findViewById(R.id.txt_edad);
        txt_areaTrabajo = view.findViewById(R.id.txt_areaTrabajo);
        txt_puestoTrabajo = view.findViewById(R.id.txt_puestoTrabajo);
        txt_actividades = view.findViewById(R.id.txt_actividades);
        txt_OtroIngenieria = view.findViewById(R.id.txt_OtrosIngenieria);
        txt_otroAdministrativo = view.findViewById(R.id.txt_OtrosAdministrativo);
        txt_leq = view.findViewById(R.id.txt_leq);
        txt_lpico = view.findViewById(R.id.txt_lpico);
        txt_lmax = view.findViewById(R.id.txt_lmax);
        txt_lmin = view.findViewById(R.id.txt_lmin);
        txt_observaciones = view.findViewById(R.id.txt_observaciones);
        txt_detalleEnferm = view.findViewById(R.id.txt_detalleEnferme);

        radioGroup_Oido = view.findViewById(R.id.radioGroupOido);
        radioGroup_Enferm = view.findViewById(R.id.radioGroupEnferm);
        radioGroup_Adminis = view.findViewById(R.id.radioGroupAdminis);
        radioGroup_Tapones = view.findViewById(R.id.radioGroupTapones);
        radioGroup_Orejeras = view.findViewById(R.id.radioGroupOrejeras);
        radioGroup_Ingenieria = view.findViewById(R.id.radioGroupIngenieria);

        radioGroup_Aislante = view.findViewById(R.id.radioGroupOido);
        radioGroup_Fachada = view.findViewById(R.id.radioGroupEnferm);
        radioGroup_Techo = view.findViewById(R.id.radioGroupAdminis);
        radioGroup_Cerramiento = view.findViewById(R.id.radioGroupTapones);
        radioGroup_Cabinas = view.findViewById(R.id.radioGroupOrejeras);
        radioGroup_Capac = view.findViewById(R.id.radioGroupOido);
        radioGroup_SenalPresion = view.findViewById(R.id.radioGroupEnferm);
        radioGroup_SenalEpps = view.findViewById(R.id.radioGroupAdminis);
        radioGroup_AdmTiempoExpo = view.findViewById(R.id.radioGroupTapones);
        radioGroup_Rotacion = view.findViewById(R.id.radioGroupOrejeras);

        radio_OidoSi = view.findViewById(R.id.radio_OidoSi);
        radio_enferSi = view.findViewById(R.id.radio_emfermedadSi);
        radio_ingenierSI = view.findViewById(R.id.radioIngenieriaSi);
        radio_adminisSI = view.findViewById(R.id.radio_AdministrativoSi);
        radio_taponesSI = view.findViewById(R.id.radioTaponesSi);
        radio_orejerasSI = view.findViewById(R.id.radio_OrejerasSi);

        card_ingenier = view.findViewById(R.id.Card_Ingenieria);
        card_adminis = view.findViewById(R.id.Card_Administrativo);
        card_tapones = view.findViewById(R.id.Card_Tapones);
        card_orejeras = view.findViewById(R.id.Card_Orejeras);



    }
    private ArrayAdapter<String> LlenarSpinner(String nombreTabla, String campoTabla, Context context){

        List<String> datosParaSpinner = DAO_DatosLocal.obtenerDatosParaSpinner(nombreTabla, campoTabla,getActivity());
        ArrayList<String> listaDatos = new ArrayList<>();
        listaDatos.add("Seleccione");
        listaDatos.addAll(datosParaSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, listaDatos);
        return adapter;
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

}