package com.mijael.mein;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;

import com.github.barteksc.pdfviewer.PDFView;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;
import com.mijael.mein.DAO.DAO_FormatosTrabajo;
import com.mijael.mein.DAO.DAO_RegistroFormatos;
import com.mijael.mein.DAO.DAO_RegistroFormatosDetalle;
import com.mijael.mein.Entidades.Formatos_Trabajo;
import com.mijael.mein.Entidades.RegistroFormatos;
import com.mijael.mein.Entidades.RegistroFormatos_Detalle;
import com.mijael.mein.Extras.InputDateConfiguration;
import com.mijael.mein.Extras.Validaciones;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class DetalleFormatosFragment extends Fragment {

    private static final String FILENAME = "formulario.pdf";
    private boolean calculoRealizado = false;
    String id_plan_trabajo, id_pt_trabajo, id_formato, id_colaborador, nom_Empresa, nom_formato;
    TextView tv_fechaDetalle,tv_horaDetalle, tv_analistaDetalle, tv_areaTrabDetalle, tv_horarioDetalle, tv_pdf, tv_editar, tv_eliminar,tv_idRegistro,
    tv_horaFinal, tv_tiempoMedicion, tv_horaInicial, tv_resLmin, tv_resLmax, tv_resLeq;
    EditText txt_leq, txt_lmin, txt_lmax, txt_lpico,txt_wbgt01, txt_wbgt11, txt_wbgt17, txt_t_aire01, txt_t_aire11, txt_t_aire17, txt_t_globo01, txt_tasaMetabolicaW, txt_tasaMetabolicaK,
            txt_t_globo11, txt_t_globo17, txt_h_relativa01, txt_h_relativa11, txt_h_relativa17, txt_velViento, txt_velViento2, txt_velViento3, txt_x, txt_y, txt_z,
            txt_t_aire, txt_t_aireNegro, txt_bulboHumedo, txt_humedadRelativa, txt_velVientoCF,txt_x0, txt_x2, txt_x4, txt_x6, txt_y0, txt_y2, txt_y4, txt_y6, txt_z0, txt_z2, txt_z4, txt_z6,
            txt_leq1, txt_lmax1, txt_lmin1, txt_leq2, txt_lmax2, txt_lmin2, txt_leq3, txt_lmax3, txt_lmin3, txt_leq4, txt_lmax4, txt_lmin4, txt_leq5, txt_lmax5, txt_lmin5;
    View ViewDetalle, view_colorDetalle,ViewPdf;
    AppCompatButton btn_subirDatos, btn_subirDatosReg,btn_calcularMedicion;
    LinearLayout linearLayout, linearAdvertencia,linearDosimetria, linearSonometria, linearHoraFinal, linearEstresTermico, linearVibracion, linearEstresFrio_Confort,
    linearBulbo, linearRadElectro;
    InputDateConfiguration config;
    DAO_RegistroFormatos dao_registroFormatos;
    DAO_RegistroFormatosDetalle dao_registroDetalle;
    private static final int SYNC_INTERVAL_MIN = 15;
    Bundle bundle;
    public DetalleFormatosFragment() {
        // Required empty public constructor
        dao_registroFormatos = new DAO_RegistroFormatos(getActivity());
        dao_registroDetalle = new DAO_RegistroFormatosDetalle(getActivity());
        bundle = new Bundle();
    }
    Validaciones validar = new Validaciones();
    Paint paint = new Paint();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (getArguments() != null) {
            nom_formato = bundle.getString("nombre_formato");
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
        View rootView = inflater.inflate(R.layout.fragment_detalle_formatos, container, false);

        config = new InputDateConfiguration(getActivity(),id_colaborador,nom_Empresa,rootView);
        linearLayout = rootView.findViewById(R.id.contenedor_Card);
        btn_subirDatos = rootView.findViewById(R.id.btn_subirDatosDet);
        btn_subirDatosReg = rootView.findViewById(R.id.btn_subirDatosReg);
        ConfigPantalla();


        //texto.setText("Lista de Registros de " + nom_formato);

        ArrayList<HashMap<String, String>> resultList = dao_registroFormatos.getListCantidadFormatoId(Integer.parseInt(id_pt_trabajo));

        int cantidadRegistros = resultList.size();
        Log.e("sdfgdf", String.valueOf(cantidadRegistros));
        for(int i = 0; i < cantidadRegistros; i++){
            MostrarFOrmatos(resultList,inflater,i);
        }

        return rootView;
    }

    private void MostrarFOrmatos(ArrayList<HashMap<String, String>> resultList, LayoutInflater inflater, int i) {

        ViewDetalle = inflater.inflate(R.layout.vista_detalle,null);
        ViewPdf = inflater.inflate(R.layout.vista_pdf, null);
        init(ViewDetalle);
        InputDateConfiguration config = new InputDateConfiguration(getActivity(),id_colaborador,nom_Empresa,ViewDetalle);

        HashMap<String, String> primerRegistro = resultList.get(i);

        int colorAleatorio = generarColorAleatorio();
        String idregistro = primerRegistro.get("id_plan_trabajo_formato_reg");
        String nomAnalista = primerRegistro.get("nom_analista");
        String areaTrabajo = primerRegistro.get("area_trabajo");
        String horaTrabajo = primerRegistro.get("hora_trabajo");
        String horaInicial = primerRegistro.get("hora_inicial");
        String fechaRegistro = primerRegistro.get("fec_reg");

        view_colorDetalle.setBackgroundColor(colorAleatorio);
        tv_idRegistro.setText(idregistro);//MOMENTANEO SOLO DE PRUEBA
        tv_analistaDetalle.setText(nomAnalista);
        tv_areaTrabDetalle.setText(areaTrabajo);
        tv_horarioDetalle.setText(horaTrabajo);
        tv_horaDetalle.setText(horaInicial);
        tv_fechaDetalle.setText(fechaRegistro);


        PDFView pdfView = ViewPdf.findViewById(R.id.pdfView);
        tv_pdf.setOnClickListener(new View.OnClickListener() {
            RegistroFormatos reg = dao_registroFormatos.Buscar(idregistro);
            RegistroFormatos_Detalle reg_detalle = dao_registroDetalle.Buscar_Id_Registro(String.valueOf(reg.id_plan_trabajo_formato_reg));
            @Override
            public void onClick(View v) {
                String pdfFileName = reg.getCod_registro() + ".pdf";
                File pdfFile = new File(requireActivity().getExternalFilesDir(null), pdfFileName);
                String pdfFilePath = pdfFile.getAbsolutePath();

                // Verificar si el archivo existente debe ser eliminado
                if (pdfFile.exists()) {
                    // Eliminar el archivo existente
                    if (pdfFile.delete()) {
                        Log.d("PDF", "Archivo existente eliminado");
                    } else {
                        Log.d("PDF", "No se pudo eliminar el archivo existente");
                    }
                }
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(getContext());
                }
                // Crear el archivo PDF
                if(reg.getId_formato()==1){ // Dosimetria
                    generarPdf_Dosimetria(pdfFile,pdfFilePath,reg,reg_detalle);
                } else if (reg.getId_formato()==2) { // Sonometria
                    generarPdf_Sonometria(pdfFile,pdfFilePath,reg,reg_detalle);
                }else if (reg.getId_formato()==3) { // Iluminacion
                    generarPdf_Iluminacion(pdfFile,pdfFilePath,reg,reg_detalle);
                }else if (reg.getId_formato()==4) { //ESTRES POR FRIO
                    generarPdf_EstresFrio(pdfFile,pdfFilePath,reg,reg_detalle);
                }else if (reg.getId_formato()==5) { // ESTRES TERMICO
                    generarPdf_EstresTermico(pdfFile,pdfFilePath,reg,reg_detalle);
                }else if (reg.getId_formato()==6) { // CONFORT TERMICO
                    generarPdf_ConfortTermico(pdfFile,pdfFilePath,reg,reg_detalle);
                }else if (reg.getId_formato()==7) { // Radiacion UV
                    generarPdf_RadiacionUV(pdfFile,pdfFilePath,reg,reg_detalle);
                }else if (reg.getId_formato()==8) { // Vibracion
                    generarPdf_Vibracion(pdfFile,pdfFilePath,reg,reg_detalle);
                }else if (reg.getId_formato()==9) {// Radiacion Electromanetico
                    generarPdf_RadiacionElectro(pdfFile,pdfFilePath,reg,reg_detalle);
                }else if (reg.getId_formato()==29) {// Humedad Relativa
                    generarPdf_HumedadRelativa(pdfFile,pdfFilePath,reg,reg_detalle);
                }else if (reg.getId_formato()==31) {// Velocidad del Aire
                    generarPdf_VelocidadAire(pdfFile,pdfFilePath,reg,reg_detalle);
                }
            }
        });
        tv_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistroFormatos reg = dao_registroFormatos.Buscar(idregistro);
                RegistroFormatos_Detalle detalle = dao_registroDetalle.Buscar_Id_Registro(String.valueOf(reg.id_plan_trabajo_formato_reg));
                if(reg.id_formato==1){
                    Fragment dosimetria = new DosimetriaFragment();//INSTANCIA DEL FRAGMENTO A DONDE QUIERO IR
                    AbrirFormato(dosimetria, reg,detalle);
                } else if (reg.id_formato==2) {
                    Fragment sonometria = new SonometriaFragment();
                    AbrirFormato(sonometria,reg,detalle);
                } else if (reg.id_formato==3) {
                    Fragment iluminacion = new IluminacionFragment();
                    AbrirFormato(iluminacion,reg,detalle);
                } else if (reg.id_formato==4) {
                    Fragment estresfrio = new EstresFrioFragment();
                    AbrirFormato(estresfrio,reg,detalle);
                } else if (reg.id_formato==5) {
                    Fragment estresTermico = new EstresTermicoFragment();
                    AbrirFormato(estresTermico,reg,detalle);
                } else if (reg.id_formato==6) {
                    Fragment confort = new ConfortTermicoFragment();
                    AbrirFormato(confort,reg,detalle);
                } else if (reg.id_formato == 7) {
                    Fragment radiacionUV = new RadiacionUvFragment();
                    AbrirFormato(radiacionUV,reg,detalle);
                } else if (reg.id_formato == 8) {
                    Fragment vibracion = new VibracionFragment();
                    AbrirFormato(vibracion,reg,detalle);
                } else if (reg.id_formato == 9) {
                    Fragment rad_electro = new RadiacionElectromagneticaFragment();
                    AbrirFormato(rad_electro,reg,detalle);
                } else if (reg.id_formato==29) {
                    Fragment humedad = new HumedadRelativaFragment();
                    AbrirFormato(humedad,reg,detalle);
                } else if (reg.id_formato ==31) {
                    Fragment velocidad = new VelocidadAireFragment();
                    AbrirFormato(velocidad, reg,detalle);
                }

                /*RegistroFormatos reg = dao_registroFormatos.Buscar(idregistro);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Editar");
                //builder.setMessage("Aquí puedes poner el contenido de tu modal para editar");
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_edit, null);
                init2(dialogView);

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
                        ) {
                            calcularMedicion();
                        }
                    }
                });

                if(Integer.parseInt(id_formato)==1){
                    linearHoraFinal.setVisibility(View.VISIBLE);
                    linearDosimetria.setVisibility(View.VISIBLE);
                }else if(Integer.parseInt(id_formato)==2){
                    linearHoraFinal.setVisibility(View.VISIBLE);
                    linearSonometria.setVisibility(View.VISIBLE);
                }else if(Integer.parseInt(id_formato)==4){
                    linearHoraFinal.setVisibility(View.VISIBLE);
                    linearEstresFrio_Confort.setVisibility(View.VISIBLE);
                    linearBulbo.setVisibility(View.GONE);
                }else if(Integer.parseInt(id_formato)==5){
                    linearHoraFinal.setVisibility(View.VISIBLE);
                    linearEstresTermico.setVisibility(View.VISIBLE);
                    if(reg.tipo_medicion.equals("1")){
                        txt_wbgt11.setVisibility(View.GONE);
                        txt_wbgt17.setVisibility(View.GONE);
                        txt_t_aire11.setVisibility(View.GONE);
                        txt_t_aire17.setVisibility(View.GONE);
                        txt_t_globo11.setVisibility(View.GONE);
                        txt_t_globo17.setVisibility(View.GONE);
                        txt_h_relativa11.setVisibility(View.GONE);
                        txt_h_relativa17.setVisibility(View.GONE);
                        txt_velViento2.setVisibility(View.GONE);
                        txt_velViento3.setVisibility(View.GONE);
                    }
                }else if(Integer.parseInt(id_formato)==6){
                    linearHoraFinal.setVisibility(View.VISIBLE);
                    linearEstresFrio_Confort.setVisibility(View.VISIBLE);
                    linearBulbo.setVisibility(View.VISIBLE);
                }else if(Integer.parseInt(id_formato)==8){
                    linearHoraFinal.setVisibility(View.VISIBLE);
                    linearVibracion.setVisibility(View.VISIBLE);
                }else if(Integer.parseInt(id_formato)==9){
                    linearHoraFinal.setVisibility(View.VISIBLE);
                    linearHoraFinal.setVisibility(View.VISIBLE);
                    linearRadElectro.setVisibility(View.VISIBLE);
                }else{
                    linearAdvertencia.setVisibility(View.VISIBLE);
                }
                tv_horaFinal.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {config.showTimePickerDialog(dialogView,tv_horaFinal);}});
                tv_horaInicial.setText(reg.hora_inicial);
                TextWatcher watcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String texto = s.toString();
                        String horainicial = reg.hora_inicial.toString();
                        String horafinal = tv_horaFinal.getText().toString();
                        if(!horainicial.isEmpty() && !horafinal.isEmpty()){
                            String valorTimeMed = InputDateConfiguration.calcularTiempoMedicion(horainicial,horafinal);
                            tv_tiempoMedicion.setTextColor(Color.BLACK);
                            if(valorTimeMed.equals("Hora inválida")){
                                tv_tiempoMedicion.setTextColor(Color.RED);
                            }
                            tv_tiempoMedicion.setText(valorTimeMed);
                        }else{
                            tv_tiempoMedicion.setText("");
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                };
                tv_horaFinal.addTextChangedListener(watcher);

                builder.setView(dialogView);
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (tv_tiempoMedicion.getText().toString().equals("Hora inválida")) {
                            new AlertDialog.Builder(getContext())
                                    .setTitle("Error en Hora Final")
                                    .setMessage("Ingrese un tiempo posterior a Hora Inicial.")
                                    .setPositiveButton(android.R.string.ok, null)
                                    .show();
                            //getFragmentManager().popBackStack();
                        }else{
                        String nuevahoraFinal = tv_horaFinal.getText().toString();
                        String tiempoMedicion = tv_tiempoMedicion.getText().toString();
                        Map<String, Object> camposCabecera = new HashMap<>();
                        Map<String, Object> camposDetalle = new HashMap<>();
                        if (Integer.parseInt(id_formato) == 1) {
                            String nuevoleq = txt_leq.getText().toString();
                            String nuevolpico = txt_lpico.getText().toString();
                            String nuevolmax = txt_lmax.getText().toString();
                            String nuevolmin = txt_lmin.getText().toString();

                            camposCabecera.put(Util_RegistroFormatos.CAMPO_HORA_FINAL, nuevahoraFinal);
                            camposCabecera.put(Util_RegistroFormatos.CAMPO_TIEMPO_MEDICION, tiempoMedicion);
                            camposCabecera.put(Util_RegistroFormatos.CAMPO_LEQUI, nuevoleq);
                            camposCabecera.put(Util_RegistroFormatos.CAMPO_LPICO_DBA, nuevolpico);
                            camposCabecera.put(Util_RegistroFormatos.CAMPO_LMAX, nuevolmax);
                            camposCabecera.put(Util_RegistroFormatos.CAMPO_LMIN, nuevolmin);
                            dao_registroFormatos.actualizarRegistro(Integer.parseInt(idregistro), camposCabecera);
                        }
                        if (Integer.parseInt(id_formato) == 2) {
                            String valorLeq_md1 = txt_leq1.getText().toString();
                            String valorLeq_md2 = txt_leq2.getText().toString();
                            String valorLeq_md3 = txt_leq3.getText().toString();
                            String valorLeq_md4 = txt_leq4.getText().toString();
                            String valorLeq_md5 = txt_leq5.getText().toString();
                            String valorLmax_md1 = txt_lmax1.getText().toString();
                            String valorLmax_md2 = txt_lmax2.getText().toString();
                            String valorLmax_md3 = txt_lmax3.getText().toString();
                            String valorLmax_md4 = txt_lmax4.getText().toString();
                            String valorLmax_md5 = txt_lmax5.getText().toString();
                            String valorLmin_md1 = txt_lmin1.getText().toString();
                            String valorLmin_md2 = txt_lmin2.getText().toString();
                            String valorLmin_md3 = txt_lmin3.getText().toString();
                            String valorLmin_md4 = txt_lmin4.getText().toString();
                            String valorLmin_md5 = txt_lmin5.getText().toString();

                            String lminFinal = tv_resLmin.getText().toString();
                            String lmaxFinal = tv_resLmax.getText().toString();
                            String LequiFinal = tv_resLeq.getText().toString();

                            if (validar.validarCalculo(calculoRealizado, getActivity())) {

                                camposCabecera.put(Util_RegistroFormatos.CAMPO_HORA_FINAL, nuevahoraFinal);
                                camposCabecera.put(Util_RegistroFormatos.CAMPO_TIEMPO_MEDICION, tiempoMedicion);
                                camposCabecera.put(Util_RegistroFormatos.CAMPO_LEQUI_MD1, valorLeq_md1);
                                camposCabecera.put(Util_RegistroFormatos.CAMPO_LEQUI_MD2, valorLeq_md2);
                                camposCabecera.put(Util_RegistroFormatos.CAMPO_LEQUI_MD3, valorLeq_md3);
                                camposCabecera.put(Util_RegistroFormatos.CAMPO_LEQUI_MD4, valorLeq_md4);
                                camposCabecera.put(Util_RegistroFormatos.CAMPO_LEQUI_MD5, valorLeq_md5);
                                camposCabecera.put(Util_RegistroFormatos.CAMPO_LMAX_MD1, valorLmax_md1);
                                camposCabecera.put(Util_RegistroFormatos.CAMPO_LMAX_MD2, valorLmax_md2);
                                camposCabecera.put(Util_RegistroFormatos.CAMPO_LMAX_MD3, valorLmax_md3);
                                camposCabecera.put(Util_RegistroFormatos.CAMPO_LMAX_MD4, valorLmax_md4);
                                camposCabecera.put(Util_RegistroFormatos.CAMPO_LMAX_MD5, valorLmax_md5);
                                camposCabecera.put(Util_RegistroFormatos.CAMPO_LMIN_MD1, valorLmin_md1);
                                camposCabecera.put(Util_RegistroFormatos.CAMPO_LMIN_MD2, valorLmin_md2);
                                camposCabecera.put(Util_RegistroFormatos.CAMPO_LMIN_MD3, valorLmin_md3);
                                camposCabecera.put(Util_RegistroFormatos.CAMPO_LMIN_MD4, valorLmin_md4);
                                camposCabecera.put(Util_RegistroFormatos.CAMPO_LMIN_MD5, valorLmin_md5);

                                camposCabecera.put(Util_RegistroFormatos.CAMPO_LMIN, lminFinal);
                                camposCabecera.put(Util_RegistroFormatos.CAMPO_LMAX, lmaxFinal);
                                camposCabecera.put(Util_RegistroFormatos.CAMPO_LEQUI, LequiFinal);

                                dao_registroFormatos.actualizarRegistro(Integer.parseInt(idregistro), camposCabecera);
                            }

                        }
                        if (Integer.parseInt(id_formato) == 4) {
                            String valorAire = txt_t_aire.getText().toString();
                            String valorTAireNegro = txt_t_aireNegro.getText().toString();
                            String valorHumedadRelativa = txt_humedadRelativa.getText().toString();
                            String valorVelViento = txt_velVientoCF.getText().toString();

                            camposCabecera.put(Util_RegistroFormatos.CAMPO_HORA_FINAL, nuevahoraFinal);
                            camposCabecera.put(Util_RegistroFormatos.CAMPO_TIEMPO_MEDICION, tiempoMedicion);
                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_T_AIRE, valorAire);
                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_T_GLOBO, valorTAireNegro);
                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_H_RELATIVA, valorHumedadRelativa);
                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_V_VIENTO, valorVelViento);

                            dao_registroFormatos.actualizarRegistro(Integer.parseInt(idregistro), camposCabecera);
                            dao_registroDetalle.actualizarRegistro(Integer.parseInt(idregistro), camposDetalle);
                        }
                        if (Integer.parseInt(id_formato) == 5) {
                            String nuevoBulbo = txt_wbgt01.getText().toString();
                            String nuevoAire01 = txt_t_aire01.getText().toString();
                            String nuevoGlobo01 = txt_t_globo01.getText().toString();
                            String nuevoRelativa01 = txt_h_relativa01.getText().toString();
                            String nuevoViento = txt_velViento.getText().toString();

                            camposCabecera.put(Util_RegistroFormatos.CAMPO_HORA_FINAL, nuevahoraFinal);
                            camposCabecera.put(Util_RegistroFormatos.CAMPO_TIEMPO_MEDICION, tiempoMedicion);

                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_T_BULBO, nuevoBulbo);
                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_T_AIRE, nuevoAire01);
                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_T_GLOBO, nuevoGlobo01);
                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_H_RELATIVA, nuevoRelativa01);
                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_V_VIENTO, nuevoViento);

                            if (reg.tipo_medicion.equals("2")) {

                                String nuevoBulbo2 = txt_wbgt11.getText().toString();
                                String nuevoBulbo3 = txt_wbgt17.getText().toString();
                                //nuevoAire01 = txt_t_aire01.getText().toString();
                                String nuevoAire11 = txt_t_aire11.getText().toString();
                                String nuevoAire17 = txt_t_aire17.getText().toString();
                                //nuevoGlobo01= txt_t_globo01.getText().toString();
                                String nuevoGlobo11 = txt_t_globo11.getText().toString();
                                String nuevoGlobo17 = txt_t_globo17.getText().toString();
                                //nuevoRelativa01 = txt_h_relativa01.getText().toString();
                                String nuevoRelativa11 = txt_h_relativa11.getText().toString();
                                String nuevoRelativa17 = txt_h_relativa17.getText().toString();
                                //nuevoViento = txt_velViento.getText().toString();
                                String nuevoViento2 = txt_velViento2.getText().toString();
                                String nuevoViento3 = txt_velViento3.getText().toString();

                                camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_T_BULBO2, nuevoBulbo2);
                                camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_T_BULBO3, nuevoBulbo3);
                                //camposAActualizar.put(Util_RegistroFormatos_Detalle.CAMPO_T_AIRE, nuevoAire01);
                                camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_T_AIRE_2, nuevoAire11);
                                camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_T_AIRE_3, nuevoAire17);
                                //camposAActualizar.put(Util_RegistroFormatos_Detalle.CAMPO_T_GLOBO, nuevoGlobo01);
                                camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_T_GLOBO_2, nuevoGlobo11);
                                camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_T_GLOBO_3, nuevoGlobo17);
                                //camposAActualizar.put(Util_RegistroFormatos_Detalle.CAMPO_H_RELATIVA, nuevoRelativa01);
                                camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_H_RELATIVA_2, nuevoRelativa11);
                                camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_H_RELATIVA_3, nuevoRelativa17);
                                //camposAActualizar.put(Util_RegistroFormatos_Detalle.CAMPO_V_VIENTO, nuevoViento);
                                camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_V_VIENTO_2, nuevoViento2);
                                camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_V_VIENTO_3, nuevoViento3);
                            }
                            dao_registroFormatos.actualizarRegistro(Integer.parseInt(idregistro), camposCabecera);
                            dao_registroDetalle.actualizarRegistro(Integer.parseInt(idregistro), camposDetalle);
                        }
                        if (Integer.parseInt(id_formato) == 6) {
                            String valorAire = txt_t_aire.getText().toString();
                            String valorTAireNegro = txt_t_aireNegro.getText().toString();
                            String valorBulboHumedo = txt_bulboHumedo.getText().toString();
                            String valorHumedadRelativa = txt_humedadRelativa.getText().toString();
                            String valorVelViento = txt_velVientoCF.getText().toString();

                            camposCabecera.put(Util_RegistroFormatos.CAMPO_HORA_FINAL, nuevahoraFinal);
                            camposCabecera.put(Util_RegistroFormatos.CAMPO_TIEMPO_MEDICION, tiempoMedicion);

                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_T_AIRE, valorAire);
                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_T_GLOBO, valorTAireNegro);
                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_T_BULBO, valorBulboHumedo);
                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_H_RELATIVA, valorHumedadRelativa);
                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_V_VIENTO, valorVelViento);


                            dao_registroFormatos.actualizarRegistro(Integer.parseInt(idregistro), camposCabecera);
                            dao_registroDetalle.actualizarRegistro(Integer.parseInt(idregistro), camposDetalle);
                        }
                        if (Integer.parseInt(id_formato) == 8) {
                            String nuevoX = txt_x.getText().toString();
                            String nuevoY = txt_y.getText().toString();
                            String nuevoZ = txt_z.getText().toString();

                            camposCabecera.put(Util_RegistroFormatos.CAMPO_HORA_FINAL, nuevahoraFinal);
                            camposCabecera.put(Util_RegistroFormatos.CAMPO_TIEMPO_MEDICION, tiempoMedicion);

                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_X, nuevoX);
                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_Y, nuevoY);
                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_Z, nuevoZ);

                            dao_registroFormatos.actualizarRegistro(Integer.parseInt(idregistro), camposCabecera);
                            dao_registroDetalle.actualizarRegistro(Integer.parseInt(idregistro), camposDetalle);
                        }
                        if (Integer.parseInt(id_formato) == 9) {

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

                            camposCabecera.put(Util_RegistroFormatos.CAMPO_HORA_FINAL, nuevahoraFinal);
                            camposCabecera.put(Util_RegistroFormatos.CAMPO_TIEMPO_MEDICION, tiempoMedicion);

                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_X, valor_x0);
                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_X2, valor_x2);
                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_X3, valor_x4);
                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_X4, valor_x6);
                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_Y, valor_y0);
                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_Y2, valor_y2);
                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_Y3, valor_y4);
                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_Y4, valor_y6);
                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_Z, valor_z0);
                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_Z2, valor_z2);
                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_Z3, valor_z4);
                            camposDetalle.put(Util_RegistroFormatos_Detalle.CAMPO_Z4, valor_z6);

                            dao_registroFormatos.actualizarRegistro(Integer.parseInt(idregistro), camposCabecera);
                            dao_registroDetalle.actualizarRegistro(Integer.parseInt(idregistro), camposDetalle);
                        }
                        }
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Cierra el diálogo
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();*/
            }
        });
        tv_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Eliminar");
                builder.setMessage("¿Estás seguro de que quieres eliminar?");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Aquí puedes poner el código para eliminar


                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Cierra el diálogo
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        linearLayout.addView(ViewDetalle);
    }
    /*
    * builder.setTitle("EN PROCESO") MOSTRAR MENSAJE DE ALERTA ______________________________-----_____-__--_--__--_-____-_
                            .setMessage("El procesamiento está en progreso.")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();*/

    public void init(View rootView){
        //texto = rootView.findViewById(R.id.tv_texto);
        view_colorDetalle = rootView.findViewById(R.id.view_colorDetalle);
        tv_fechaDetalle = rootView.findViewById(R.id.tv_fechaDetalle);
        tv_horaDetalle = rootView.findViewById(R.id.tv_horaDetalle);
        tv_analistaDetalle = rootView.findViewById(R.id.tv_analistaDetalle);
        tv_areaTrabDetalle = rootView.findViewById(R.id.tv_areaTrabDetalle);
        tv_horarioDetalle = rootView.findViewById(R.id.tv_horarioDetalle);
        tv_pdf = rootView.findViewById(R.id.tv_pdf);
        tv_editar = rootView.findViewById(R.id.tv_editar);
        tv_eliminar = rootView.findViewById(R.id.tv_eliminar);

        tv_idRegistro = rootView.findViewById(R.id.tv_idregistro);


    }
    public void init2(View rootView){
        linearAdvertencia = rootView.findViewById(R.id.linearAdvertencia);
        linearHoraFinal = rootView.findViewById(R.id.linearHoraFinal);
        linearDosimetria = rootView.findViewById(R.id.linearDosimetria);
        linearSonometria = rootView.findViewById(R.id.linearSonometria);
        linearVibracion = rootView.findViewById(R.id.linearVibracion);
        linearEstresTermico = rootView.findViewById(R.id.linearEstresTermico);
        linearEstresFrio_Confort = rootView.findViewById(R.id.linearConfort_Frio);
        linearBulbo = rootView.findViewById(R.id.linearBulbo);
        linearRadElectro = rootView.findViewById(R.id.linearRadElectro);

        // DATOS PARA MENSAJE
        tv_horaFinal = rootView.findViewById(R.id.tv_horaFinal);
        tv_tiempoMedicion = rootView.findViewById(R.id.tv_tiempoMedicion);
        tv_horaInicial = rootView.findViewById(R.id.tv_horaInicial);
        //DATOS PARA DOSIMETRIA
        txt_leq = rootView.findViewById(R.id.txt_leq);
        txt_lpico = rootView.findViewById(R.id.txt_lpico);
        txt_lmax = rootView.findViewById(R.id.txt_lmax);
        txt_lmin = rootView.findViewById(R.id.txt_lmin);

        //DATOS PARA SONOMETRIA
        txt_leq1 = rootView.findViewById(R.id.txt_leq_med_1);
        txt_lmax1 = rootView.findViewById(R.id.txt_lmax_med_1);
        txt_lmin1 = rootView.findViewById(R.id.txt_lmin_med_1);
        txt_leq2 = rootView.findViewById(R.id.txt_leq_med_2);
        txt_lmax2 = rootView.findViewById(R.id.txt_lmax_med_2);
        txt_lmin2 = rootView.findViewById(R.id.txt_lmin_med_2);
        txt_leq3 = rootView.findViewById(R.id.txt_leq_med_3);
        txt_lmax3 = rootView.findViewById(R.id.txt_lmax_med_3);
        txt_lmin3 = rootView.findViewById(R.id.txt_lmin_med_3);
        txt_leq4 = rootView.findViewById(R.id.txt_leq_med_4);
        txt_lmax4 = rootView.findViewById(R.id.txt_lmax_med_4);
        txt_lmin4 = rootView.findViewById(R.id.txt_lmin_med_4);
        txt_leq5 = rootView.findViewById(R.id.txt_leq_med_5);
        txt_lmax5 = rootView.findViewById(R.id.txt_lmax_med_5);
        txt_lmin5 = rootView.findViewById(R.id.txt_lmin_med_5);
        btn_calcularMedicion = rootView.findViewById(R.id.btn_calcularMedicionGeneral);
        tv_resLmin = rootView.findViewById(R.id.tv_resulLmin);
        tv_resLmax = rootView.findViewById(R.id.tv_resulLmax);
        tv_resLeq = rootView.findViewById(R.id.tv_resulLequi);

        //DATOS PARA VIBRACION
        txt_x = rootView.findViewById(R.id.txt_x);
        txt_y = rootView.findViewById(R.id.txt_y);
        txt_z = rootView.findViewById(R.id.txt_z);

        //DATOS PARA ESTRES TERMICO
        txt_wbgt01 = rootView.findViewById(R.id.txt_wbgt01);
        txt_wbgt11 = rootView.findViewById(R.id.txt_wbgt11);
        txt_wbgt17 = rootView.findViewById(R.id.txt_wbgt17);
        txt_t_aire01 = rootView.findViewById(R.id.txt_t_aire01);
        txt_t_aire11 = rootView.findViewById(R.id.txt_t_aire11);
        txt_t_aire17 = rootView.findViewById(R.id.txt_t_aire17);
        txt_t_globo01 = rootView.findViewById(R.id.txt_t_globo01);
        txt_t_globo11 = rootView.findViewById(R.id.txt_t_globo11);
        txt_t_globo17 = rootView.findViewById(R.id.txt_t_globo17);
        txt_h_relativa01 = rootView.findViewById(R.id.txt_h_relativa01);
        txt_h_relativa11 = rootView.findViewById(R.id.txt_h_relativa11);
        txt_h_relativa17 = rootView.findViewById(R.id.txt_h_relativa17);
        txt_velViento = rootView.findViewById(R.id.txt_velViento);
        txt_velViento2 = rootView.findViewById(R.id.txt_velViento2);
        txt_velViento3 = rootView.findViewById(R.id.txt_velViento3);

        //DATOS PARA CONFORT Y ESTRES POR FRIO
        txt_t_aire = rootView.findViewById(R.id.txt_t_aire);
        txt_t_aireNegro = rootView.findViewById(R.id.txt_t_aireNegro);
        txt_bulboHumedo = rootView.findViewById(R.id.txt_t_bulboHumedo);
        txt_humedadRelativa = rootView.findViewById(R.id.txt_humedadRelativa);
        txt_velVientoCF = rootView.findViewById(R.id.txt_velVientoCF);

        //DATOS PARA RADIACION ELECTROMAGNETICO
        txt_x0 = rootView.findViewById(R.id.txt_x_0);
        txt_x2 = rootView.findViewById(R.id.txt_x_2);
        txt_x4 = rootView.findViewById(R.id.txt_x_4);
        txt_x6 = rootView.findViewById(R.id.txt_x_6);
        txt_y0 = rootView.findViewById(R.id.txt_y_0);
        txt_y2 = rootView.findViewById(R.id.txt_y_2);
        txt_y4 = rootView.findViewById(R.id.txt_y_4);
        txt_y6 = rootView.findViewById(R.id.txt_y_6);
        txt_z0 = rootView.findViewById(R.id.txt_z_0);
        txt_z2 = rootView.findViewById(R.id.txt_z_2);
        txt_z4 = rootView.findViewById(R.id.txt_z_4);
        txt_z6 = rootView.findViewById(R.id.txt_z_6);


    }
    public void ConfigPantalla(){
        MainActivity activity = (MainActivity) getActivity();
        EditText txt_buscar = activity.findViewById(R.id.txt_buscarOrden);
        TextView tv_usu2 = activity.findViewById(R.id.txt_usuario2);
        TextView tv_usu = activity.findViewById(R.id.txt_usuario);
        txt_buscar.setVisibility(View.GONE);
        tv_usu2.setText("Detalle Formatos");
        tv_usu.setVisibility(View.GONE);
        FragmentContainerView fragmentContainer = activity.findViewById(R.id.fragmentContainerView);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) fragmentContainer.getLayoutParams();
        params.topMargin = 120;
        fragmentContainer.setLayoutParams(params);
    }
    private int generarColorAleatorio() {
        Random random = new Random();
        int color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
        return color;
    }
    public void TareasProgramadas(Class<? extends Worker> workerClass, String tag){
        Log.e("ENTRADA_SYNC","ENTRO A SINCRONIZAR por BOTON " + tag);
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        PeriodicWorkRequest syncRequest = new PeriodicWorkRequest.Builder(
                workerClass, // Clase que extiende de Worker
                SYNC_INTERVAL_MIN, // Intervalo de sincronización en minutos
                TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build();

        WorkManager.getInstance(getActivity()).enqueueUniquePeriodicWork(
                tag,
                ExistingPeriodicWorkPolicy.REPLACE,
                syncRequest);
    }
    private void calcularMedicion(){
        double med1 = Double.parseDouble(txt_leq1.getText().toString());
        double med2 = Double.parseDouble(txt_leq2.getText().toString());
        double med3 = Double.parseDouble(txt_leq3.getText().toString());
        double med4;
        double med5;
        double med6 = Double.parseDouble(txt_lmin1.getText().toString());
        double med7 = Double.parseDouble(txt_lmin2.getText().toString());
        double med8 = Double.parseDouble(txt_lmin3.getText().toString());
        double med9;
        double med10;
        double med11 =Double.parseDouble(txt_lmax1.getText().toString());
        double med12 = Double.parseDouble(txt_lmax2.getText().toString());
        double med13 = Double.parseDouble(txt_lmax3.getText().toString());
        double med14;
        double med15;

        try {
            String txtMed4 = txt_leq4.getText().toString();
            String txtMed5 = txt_leq5.getText().toString();
            String txtMed9 = txt_lmin4.getText().toString();
            String txtMed10 = txt_lmin5.getText().toString();
            String txtMed14 = txt_lmax4.getText().toString();
            String txtMed15 = txt_lmax5.getText().toString();
            med4 = txtMed4.isEmpty() ? 0.0 : Double.parseDouble(txtMed4);
            med5 = txtMed5.isEmpty() ? 0.0 : Double.parseDouble(txtMed5);
            med9 = txtMed9.isEmpty() ? 0.0 : Double.parseDouble(txtMed9);
            med10 = txtMed10.isEmpty() ? 0.0 : Double.parseDouble(txtMed10);
            med14 = txtMed14.isEmpty() ? 0.0 : Double.parseDouble(txtMed14);
            med15 = txtMed15.isEmpty() ? 0.0 : Double.parseDouble(txtMed15);
        } catch (NumberFormatException e) {
            // En caso de que la conversión a double falle (campo vacío o no numérico)
            med4 = 0.0;  // Puedes establecer cualquier valor predeterminado
            med5 = 0.0;  // Puedes establecer cualquier valor predeterminado
            med9 = 0.0;  // Puedes establecer cualquier valor predeterminado
            med10 = 0.0;  // Puedes establecer cualquier valor predeterminado
            med14 = 0.0;  // Puedes establecer cualquier valor predeterminado
            med15 = 0.0;  // Puedes establecer cualquier valor predeterminado
            e.printStackTrace();  // Puedes imprimir o registrar la excepción si es necesario
        }

        //double fj = 60.0 / 600.0;
        double fj = 5;
        double fj_final = 15.0;

        double[] matriz = { med1, med2, med3, med6, med7, med8, med11, med12, med13, med4, med5, med9, med10, med14, med15 };

        double maximo = Arrays.stream(matriz).max().orElse(0.0);
        double minimo = Arrays.stream(matriz)
                .filter(val -> val != 0.0) // Filtrar los valores que no son 0.0
                .min()
                .orElse(0.0);

       /* String cadena = med1 + "," + med2 + "," + med3 + "," + med6 + "," + med7 + "," + med8 + "," + med11 + "," + med12 + "," + med13 +
                (med4 != null ? "," + med4 : "") +
                (med5 != null ? "," + med5 : "") +
                (med9 != null ? "," + med5 : "") +
                (med10 != null ? "," + med5 : "") +
                (med14 != null ? "," + med5 : "") +
                (med15 != null ? "," + med5 : "");
                // ... y así sucesivamente para todos los valores de med

                String[] matriz = cadena.split(",");
        double maximo = Arrays.stream(matriz).mapToDouble(Double::parseDouble).max().getAsDouble();
        double minimo = Arrays.stream(matriz).mapToDouble(Double::parseDouble).min().getAsDouble();*/

        // Lj/10
        double lj1 = med1 / 10;
        double lj2 = med2 / 10;
        double lj3 = med3 / 10.0;
        double lj4 = med4 != 0.0 ? med4 / 10.0 : 0.0;
        double lj5 = med5 != 0.0 ? med5 / 10.0 : 0.0;
        double lj6 = med6 / 10.0;
        double lj7 = med7 / 10.0;
        double lj8 = med8 / 10.0;
        double lj9 = med9 != 0.0 ? med9 / 10.0 : 0.0;
        double lj10 = med10 != 0.0 ? med10 / 10.0 : 0.0;
        double lj11= med11 / 10.0;
        double lj12 = med12/ 10.0;
        double lj13 = med13 / 10.0;
        double lj14 = med14 != 0.0 ? med14 / 10.0 : 0.0;
        double lj15 = med15 != 0.0 ? med15 / 10.0 : 0.0;


        // 10^(Lj/10)
        double pot_lj1 = (Math.pow(10, lj1));
        double pot_lj2 = (Math.pow(10, lj2));
        double pot_lj3 = (Math.pow(10, lj3));
        double pot_lj4 = med4 != 0.0 ? (Math.pow(10, lj4)) : 0.0;
        double pot_lj5 = med5 != 0.0 ? (Math.pow(10, lj5)) : 0.0;
        double pot_lj6 = (Math.pow(10, lj6));
        double pot_lj7 = (Math.pow(10, lj7));
        double pot_lj8 = (Math.pow(10, lj8));
        double pot_lj9 = med9 != 0.0 ? Math.pow(10, lj9) : 0.0;
        double pot_lj10 = med10 != 0.0 ? (Math.pow(10, lj10)) : 0.0;
        double pot_lj11 = (Math.pow(10, lj11));
        double pot_lj12 = (Math.pow(10, lj12));
        double pot_lj13 = (Math.pow(10, lj13));
        double pot_lj14 = med14 != 0.0 ? (Math.pow(10, lj14)) : 0.0;
        double pot_lj15 = med15 != 0.0 ? (Math.pow(10, lj15)) : 0.0;

        // N°Repeticiones*Fj x 10^(Lj/10)
        double multi1 = (1 * fj * pot_lj1);
        double multi2 = (1 * fj * pot_lj2);
        double multi3 = (1 * fj * pot_lj3);
        double multi4 = med4 != 0.0 ? (1 * fj * pot_lj4) : 0.0;
        double multi5 = med5 != 0.0 ? (1 * fj * pot_lj5) : 0.0;
        /*double multi6 = (1 * fj * pot_lj6);
        double multi7 = (1 * fj * pot_lj7);
        double multi8 = (1 * fj * pot_lj8);
        double multi9 = !med9.isEmpty() ? (1 * fj * pot_lj9) : 0.0;
        double multi10 = !med10.isEmpty() ? (1 * fj * pot_lj10) : 0.0;
        double multi11 = (1 * fj * pot_lj11);
        double multi12 = (1 * fj * pot_lj12);
        double multi13 = (1 * fj * pot_lj13);
        double multi14 = !med14.isEmpty() ? (1 * fj * pot_lj14) : 0.0;
        double multi15 = !med15.isEmpty() ? (1 * fj * pot_lj15) : 0.0;*/
        //double multi15 = !med15.isEmpty() ? (1 * fj_final * pot_lj15) : 0.0;
        if(med4!=0.0){
            fj_final +=5;
        }
        if(med5!=0.0){
            fj_final +=5;
        }

        double suma = (multi1 + multi2 + multi3 + multi4 + multi5)/fj_final;
        //double suma = (multi1 + multi2 + multi3 + multi4 + multi5 + multi6 + multi7 + multi8 + multi9 + multi10 + multi11 + multi12 + multi13 + multi14 + multi15)/15;
        double Leq_dBA = Math.round(10 * Math.log10(suma) * 100) / 100.0;
        tv_resLmin.setText(String.valueOf(minimo));
        tv_resLmax.setText(String.valueOf(maximo));
        tv_resLeq.setText(String.valueOf(Leq_dBA));
        calculoRealizado = true;
    }

    private void generarPdf_EstresFrio(File pdfFile, String pdfFilePath, RegistroFormatos reg, RegistroFormatos_Detalle reg_detalle){
        try {
            DAO_FormatosTrabajo daoFormato = new DAO_FormatosTrabajo(getContext());
            Formatos_Trabajo formato = daoFormato.Buscar(reg.getId_plan_trabajo(), String.valueOf(reg.getId_formato()));

            PdfWriter writer = new PdfWriter(pdfFile);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument, PageSize.A4);

            float[] columnWidths = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}; // Definir anchos de columna
            Table table = new Table(UnitValue.createPercentArray(columnWidths));
            // Configurar la tabla para ocupar todo el ancho de la página
            table.setWidth(UnitValue.createPercentValue(100));

            Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.logo);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] bitmapData = stream.toByteArray();
            ImageData imageData = ImageDataFactory.create(bitmapData);
            /*String imagePath = "logo.jpg";
            ImageData imageData = ImageDataFactory.create(getContext().getAssets().open(imagePath));*/
            Image image = new Image(imageData);
            float maxWidthSize = 80f; // Tamaño máximo en píxeles
            float maxHeightSize = 50f; // Tamaño máximo en píxeles
            image.scaleToFit(maxWidthSize, maxHeightSize);

            // Primera fila con diferentes anchos de columna
            table.addCell(new Cell(4,2).add(image).setVerticalAlignment(VerticalAlignment.MIDDLE).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(2,6).add(new Paragraph("FORMATO DE CAMPO PARA MEDICIÓN\n" + "DE ESTRÉS POR FRÍO")).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph("Código")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("MEIN-FORMATO-MVA-01")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            // Segunda fila con diferentes anchos de columna
            table.addCell(new Cell(1,1).add(new Paragraph("Versión")).setWidth(2).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("1")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(2,6).add(new Paragraph("SISTEMA INTEGRADO DE GESTIÓN")).setFontSize(10).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,1).add(new Paragraph("Fecha")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("02/01/2024")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph("Página")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("1")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Empresa")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(formato.getNom_cliente())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Analista de campo:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,6).add(new Paragraph(reg.getNom_analista())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph("Foto")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getFoto()!=null || reg.getRuta_foto()!=null?"X":"")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Equipo utilizado:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Equipo de EF: ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg.getCod_equipo1())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Número de serie: ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getSerie_eq1())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Equipo utilizado:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Anemómetro: ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg.getCod_equipo2())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Número de serie: ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getSerie_eq2())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,3).add(new Paragraph("Hora de verificación:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getHora_situ())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Verificación In Situ:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            String contenido = reg.getVerf_insitu().equals("1") ? "SI" : "NO";
            table.addCell(new Cell(1,3).add(new Paragraph(contenido).setFontSize(8).setTextAlignment(TextAlignment.CENTER)));

            table.addCell(new Cell(1,12).add(new Paragraph("HORA DE MONITOREO")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Fecha:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Hora inicial")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Hora final")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Tiempo exposición (h)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Jornada (h)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph(config.convertirFecha2(reg.getFec_monitoreo()))).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg.getHora_inicial())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg.getHora_final())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getTiempo_exposicion())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getJornada())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,12).add(new Paragraph("DATOS PERSONALES DEL TRABAJADOR")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Nombres y\n"+"apellidos")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,5).add(new Paragraph(reg.getNom_trabajador())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph("Doc. identidad")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph(reg.getTipo_doc_trabajador())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getNum_doc_trabajador())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Puesto:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(reg.getPuesto_trabajador())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Área de Trabajo:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(reg.getArea_trabajo())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Actividades\n" +
                    "Realizadas:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(reg.getActividades_realizadas())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Peso (Kg)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,5).add(new Paragraph(reg.getPeso_trabajador())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph("Edad")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,4).add(new Paragraph(String.valueOf(reg.getEdad_trabajador()))).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,12).add(new Paragraph("HORARIO DE TRABAJO")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(2,2).add(new Paragraph("Horario de Trabajo")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(2,5).add(new Paragraph(reg.getHora_trabajo())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Horario de Refrigerio: ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getHorario_refrigerio())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Régimen laboral")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getRegimen_laboral())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,12).add(new Paragraph("CONDICIONES DE TRABAJO")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Fuente Generadora")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,10).add(new Paragraph(reg_detalle.getFuente_generadora())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Descripción de la\n" +
                    "fuente de frío:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,10).add(new Paragraph(reg_detalle.getDesc_fuente_frio())).setFontSize(8).setTextAlignment(TextAlignment.LEFT));

            table.addCell(new Cell(11,2).add(new Paragraph("Vestimenta del\n" +
                    "Personal Evaluado")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(new DeviceRgb(221, 235, 247)));
            table.addCell(new Cell(1,2).add(new Paragraph("ROPA INTERIOR")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(new DeviceRgb(238,236,225)));
            table.addCell(new Cell(1,2).add(new Paragraph("CAMISAS / BLUSAS")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(new DeviceRgb(238,236,225)));
            table.addCell(new Cell(1,2).add(new Paragraph("PANTALONES")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(new DeviceRgb(238,236,225)));
            table.addCell(new Cell(1,2).add(new Paragraph("PULLOVER")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(new DeviceRgb(238,236,225)));
            table.addCell(new Cell(1,2).add(new Paragraph("PRENDA DE ABRIGO")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(new DeviceRgb(238,236,225)));

            table.addCell(new Cell(1,2).add(new Paragraph("(" + (reg_detalle.getRopa_interior().equals("1") ? "X":"\u00A0") + ") Calzoncillos")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,2).add(new Paragraph("(" + (reg_detalle.getCamisa_blusa().equals("1") ? "X":"\u00A0") + ") Manga Corta")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,2).add(new Paragraph("(" + (reg_detalle.getPantalon().equals("1") ? "X":"\u00A0") + ") Corto")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,2).add(new Paragraph("(" + (reg_detalle.getPullover().equals("1") ? "X":"\u00A0") + ") Chaleco Sin\n" + "Mangas")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,2).add(new Paragraph("(" + (reg_detalle.getAbrigo().equals("1") ? "X":"\u00A0") + ") Abrigo")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));

            table.addCell(new Cell(1,2).add(new Paragraph("(" + (reg_detalle.getRopa_interior().equals("2") ? "X":"\u00A0") +") Calzoncillos\n" + "largos")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,2).add(new Paragraph("(" + (reg_detalle.getCamisa_blusa().equals("2") ? "X":"\u00A0") + ") Ligera, Mangas\n" + "Cortas")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,2).add(new Paragraph("(" + (reg_detalle.getPantalon().equals("2") ? "X":"\u00A0") + ") Ligero")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,2).add(new Paragraph("(" + (reg_detalle.getPullover().equals("2") ? "X":"\u00A0") + ") Pullover\n" + "Ligero")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,2).add(new Paragraph("(" + (reg_detalle.getAbrigo().equals("2") ? "X":"\u00A0") + ") Chaqueta Larga")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));

            table.addCell(new Cell(1,2).add(new Paragraph("(" + (reg_detalle.getRopa_interior().equals("3") ? "X":"\u00A0") + ") Camiseta de\n" + "tirantes\n")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,2).add(new Paragraph("(" + (reg_detalle.getCamisa_blusa().equals("3") ? "X":"\u00A0") +") Normal Mangas\n" + "Largas")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(2,2).add(new Paragraph("(" + (reg_detalle.getPantalon().equals("3") ? "X":"\u00A0") + ") Normal")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(2,2).add(new Paragraph("(" + (reg_detalle.getPullover().equals("3") ? "X":"\u00A0") + ") Pullover\n" + "Medio")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(2,2).add(new Paragraph("(" + (reg_detalle.getAbrigo().equals("3") ? "X":"\u00A0")  + ") Parka")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));

            table.addCell(new Cell(1,2).add(new Paragraph("(" + (reg_detalle.getRopa_interior().equals("4") ? "X":"\u00A0") + ") Camiseta de\n" + "manga corta ")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(2,2).add(new Paragraph("(" + (reg_detalle.getCamisa_blusa().equals("4") ? "X":"\u00A0") + ") Camiseta franela\n" + "Manga Larga ")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));

            table.addCell(new Cell(1,2).add(new Paragraph("(" + (reg_detalle.getRopa_interior().equals("5") ? "X":"\u00A0") + ") Camiseta de\n" + "maga larga")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(2,2).add(new Paragraph("(" + (reg_detalle.getPantalon().equals("4") ? "X":"\u00A0") + ") Franela")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(2,2).add(new Paragraph("(" + (reg_detalle.getPullover().equals("4") ? "X":"\u00A0") + ") Pullover\n" + "Grueso")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(2,2).add(new Paragraph("(" + (reg_detalle.getAbrigo().equals("4") ? "X":"\u00A0") + ") Mono forrado")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));

            table.addCell(new Cell(1,2).add(new Paragraph("(" + (reg_detalle.getRopa_interior().equals("6") ? "X":"\u00A0") + ") Sujetadores y\n" + "bragas")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,2).add(new Paragraph("(" + (reg_detalle.getCamisa_blusa().equals("5") ? "X":"\u00A0") + ") Blusa Ligera Manga\n" + "Larga\n")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));

            table.addCell(new Cell(1,2).add(new Paragraph("FORRADAS")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT).setBackgroundColor(new DeviceRgb(238,236,225)));
            table.addCell(new Cell(1,2).add(new Paragraph("(" + (reg_detalle.getForrada() != null && reg_detalle.getForrada().equals("1") ? "X":"\u00A0") + ") Mono de trabajo")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,2).add(new Paragraph("(" + (reg_detalle.getForrada() != null && reg_detalle.getForrada().equals("2") ? "X":"\u00A0") + ") Pantalón")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,2).add(new Paragraph("(" + (reg_detalle.getForrada() != null && reg_detalle.getForrada().equals("3") ? "X":"\u00A0") + ") Chaqueta")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,2).add(new Paragraph("(" + (reg_detalle.getForrada() != null && reg_detalle.getForrada().equals("4") ? "X":"\u00A0") + ") Chaleco")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));

            table.addCell(new Cell(1,2).add(new Paragraph("CHAQUETA")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT).setBackgroundColor(new DeviceRgb(238,236,225)));
            table.addCell(new Cell(1,2).add(new Paragraph("(" + (reg_detalle.getChaqueta() != null && reg_detalle.getChaqueta().equals("1") ? "X":"\u00A0") + ") Chaqueta Ligera")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,2).add(new Paragraph("(" + (reg_detalle.getChaqueta() != null && reg_detalle.getChaqueta().equals("2") ? "X":"\u00A0") + ") Chaqueta\n" + "Normal")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,2).add(new Paragraph("(" + (reg_detalle.getChaqueta() != null && reg_detalle.getChaqueta().equals("3") ? "X":"\u00A0") + ") Bata de\n" + "trabajo")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,2).add(new Paragraph("(" + (reg_detalle.getChaqueta() != null && reg_detalle.getChaqueta().equals("4") ? "X":"\u00A0") + ") Mono de Trabajo")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));

            table.addCell(new Cell(2,2).add(new Paragraph("DIVERSOS")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT).setBackgroundColor(new DeviceRgb(238,236,225)));
            table.addCell(new Cell(1,2).add(new Paragraph("(" + (reg_detalle.getD_zapsd().equals("1") ? "X":"\u00A0") + ") Zapato Suela\n" + "Delgada")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,2).add(new Paragraph("(" + (reg_detalle.getD_calc().equals("1") ? "X":"\u00A0") + ") Calcetines")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,2).add(new Paragraph("(" + (reg_detalle.getD_calcgc().equals("1") ? "X":"\u00A0") + ") Calcetín Grueso\n" + " Corto")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,2).add(new Paragraph("(" + (reg_detalle.getD_botas().equals("1") ? "X":"\u00A0") + ") Botas")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));

            table.addCell(new Cell(1,2).add(new Paragraph("(" + (reg_detalle.getD_zapsg().equals("1") ? "X":"\u00A0") + ") Zapato Suela\n" + "Gruesa\n")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,2).add(new Paragraph("(" + (reg_detalle.getD_med().equals("1") ? "X":"\u00A0") + ") Medias\n" + "Nylon\n")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,2).add(new Paragraph("(" + (reg_detalle.getD_calcgl().equals("1") ? "X":"\u00A0") + ") Calcetín\n" + "Grueso Largo")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,2).add(new Paragraph("(" + (reg_detalle.getD_guant().equals("1") ? "X":"\u00A0") + ") Guantes")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));

            table.addCell(new Cell(6,2).add(new Paragraph("Control de\n" + "Ingeniería ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph(reg.getCtrl_ingenieria().equals("1") ? "SI":"NO")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Nombre del control")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,6).add(new Paragraph(reg.getCtrl_ingenieria().equals("1") ? reg.getNom_ctrl_ingenieria():"No tiene")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,4).add(new Paragraph("Tiempo ocupando el cargo:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph(reg.getAnio_ocu_cargo() + " año(s)")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph(reg.getMes_ocu_cargo() + "mes(es)")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph("Condición del\n" + "trabajador: ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getCond_trab())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,7).add(new Paragraph("Rotación de personal ")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,3).add(new Paragraph("Si (" + (reg_detalle.getRotacion_personal().equals("1") ? "X":" ") + ") No (" + (reg_detalle.getRotacion_personal().equals("2") ? "X": " ") + ")")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));

            table.addCell(new Cell(1,7).add(new Paragraph("Realiza tiempos de recuperación")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,3).add(new Paragraph("Si (" + (reg_detalle.getTiempo_recuperacion().equals("1") ? "X":" ") + ") No (" + (reg_detalle.getTiempo_recuperacion().equals("2") ? "X": " ") + ")")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));

            table.addCell(new Cell(1,7).add(new Paragraph("Ingesta de bebidas calientes proporcionadas por la empresa ")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,3).add(new Paragraph("Si (" + (reg_detalle.getDispensador().equals("1") ? "X":" ") + ") No (" + (reg_detalle.getDispensador().equals("2") ? "X": " ") + ")")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));

            table.addCell(new Cell(1,7).add(new Paragraph("Capacitación sobre los riesgos a la exposición a fuentes de frío ")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,3).add(new Paragraph("Si (" + (reg_detalle.getCapa_expo_frio().equals("1") ? "X":" ") + ") No (" + (reg_detalle.getCapa_expo_frio().equals("2") ? "X": " ") + ")")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));

            table.addCell(new Cell(1,12).add(new Paragraph("DETERMINACIÓN DE LA TASA METABÓLICA\n" + "(ISO 8996 - Ergonomía del ambiente térmico)\n")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Nivel de\n" + "Determinación:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(reg_detalle.getNom_nivel_d())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Método de\n" + "Determinación")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(reg_detalle.getMetodo_determ())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            if(reg_detalle.getId_metodo_determ().equals("1")){
                table.addCell(new Cell(1,2).add(new Paragraph("Tipo de trabajo")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,4).add(new Paragraph("Ocupación")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,3).add(new Paragraph("Rango de la tasa metabólica (W/m2)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,3).add(new Paragraph("Tasa metabólica media (W/m2)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));

                table.addCell(new Cell(1,2).add(new Paragraph(reg_detalle.getTipo_trabajo())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,4).add(new Paragraph(reg_detalle.getOcupacion())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,3).add(new Paragraph(reg_detalle.getRango_tasa_metab())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,3).add(new Paragraph(reg_detalle.getTasa_metab())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            }
            if(reg_detalle.getId_metodo_determ().equals("2")){
                table.addCell(new Cell(1,2).add(new Paragraph("Clase")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,7).add(new Paragraph("Actividad")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,3).add(new Paragraph("Tasa metabólica media (W/m2")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));

                table.addCell(new Cell(1,2).add(new Paragraph(reg_detalle.getClase())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));
                table.addCell(new Cell(1,7).add(new Paragraph(reg_detalle.getActividad_deter())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));
                table.addCell(new Cell(1,3).add(new Paragraph(reg_detalle.getTasa_metab())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));
            }
            if(reg_detalle.getId_metodo_determ().equals("4")){
                table.addCell(new Cell(1,4).add(new Paragraph("Frecuencia cardiaca (ppm)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,3).add(new Paragraph(reg_detalle.getFrecuencia_deter())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));
                table.addCell(new Cell(1,2).add(new Paragraph("Género")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,3).add(new Paragraph(reg_detalle.getGenero_deter())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));
            }
            if(reg_detalle.getId_nivel_d().equals("2")){
                if(Integer.parseInt(reg_detalle.getNtareas())>0){
                    table.addCell(new Cell(1,3).add(new Paragraph("Tarea 01:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,3).add(new Paragraph("Posición")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,3).add(new Paragraph("Parte del cuerpo")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,3).add(new Paragraph("Intensidad")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));

                    table.addCell(new Cell(1,3).add(new Paragraph(reg_detalle.getNom_tarea1())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(2,3).add(new Paragraph(reg_detalle.getPosicion_1())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(2,3).add(new Paragraph(reg_detalle.getPcuerpo_1())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(2,3).add(new Paragraph(reg_detalle.getIntensidad_1())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));

                    table.addCell(new Cell(1,2).add(new Paragraph("Ciclo Trab. (min):")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,1).add(new Paragraph(reg_detalle.getCiclo_trabajo1())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                }
                if(Integer.parseInt(reg_detalle.getNtareas())>1){
                    table.addCell(new Cell(1,3).add(new Paragraph("Tarea 02:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,3).add(new Paragraph("Posición")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,3).add(new Paragraph("Parte del cuerpo")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,3).add(new Paragraph("Intensidad")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));

                    table.addCell(new Cell(1,3).add(new Paragraph(reg_detalle.getNom_tarea2())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(2,3).add(new Paragraph(reg_detalle.getPosicion_2())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(2,3).add(new Paragraph(reg_detalle.getPcuerpo_2())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(2,3).add(new Paragraph(reg_detalle.getIntensidad_2())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));

                    table.addCell(new Cell(1,2).add(new Paragraph("Ciclo Trab. (min):")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,1).add(new Paragraph(reg_detalle.getCiclo_trabajo2())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                }
                if(Integer.parseInt(reg_detalle.getNtareas())>2){
                    table.addCell(new Cell(1,3).add(new Paragraph("Tarea 03:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,3).add(new Paragraph("Posición")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,3).add(new Paragraph("Parte del cuerpo")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,3).add(new Paragraph("Intensidad")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));

                    table.addCell(new Cell(1,3).add(new Paragraph(reg_detalle.getNom_tarea3())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(2,3).add(new Paragraph(reg_detalle.getPosicion_3())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(2,3).add(new Paragraph(reg_detalle.getPcuerpo_3())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(2,3).add(new Paragraph(reg_detalle.getIntensidad_3())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));

                    table.addCell(new Cell(1,2).add(new Paragraph("Ciclo Trab. (min):")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,1).add(new Paragraph(reg_detalle.getCiclo_trabajo3())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                }
                if(Integer.parseInt(reg_detalle.getNtareas())>3){
                    table.addCell(new Cell(1,3).add(new Paragraph("Tarea 04:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,3).add(new Paragraph("Posición")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,3).add(new Paragraph("Parte del cuerpo")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,3).add(new Paragraph("Intensidad")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));

                    table.addCell(new Cell(1,3).add(new Paragraph(reg_detalle.getNom_tarea4())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(2,3).add(new Paragraph(reg_detalle.getPosicion_4())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(2,3).add(new Paragraph(reg_detalle.getPcuerpo_4())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(2,3).add(new Paragraph(reg_detalle.getIntensidad_4())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));

                    table.addCell(new Cell(1,2).add(new Paragraph("Ciclo Trab. (min):")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,1).add(new Paragraph(reg_detalle.getCiclo_trabajo4())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                }
                if(Integer.parseInt(reg_detalle.getNtareas())>4){
                    table.addCell(new Cell(1,3).add(new Paragraph("Tarea 05:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,3).add(new Paragraph("Posición")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,3).add(new Paragraph("Parte del cuerpo")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,3).add(new Paragraph("Intensidad")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));

                    table.addCell(new Cell(1,3).add(new Paragraph(reg_detalle.getNom_tarea5())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(2,3).add(new Paragraph(reg_detalle.getPosicion_5())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(2,3).add(new Paragraph(reg_detalle.getPcuerpo_5())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(2,3).add(new Paragraph(reg_detalle.getIntensidad_5())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));

                    table.addCell(new Cell(1,2).add(new Paragraph("Ciclo Trab. (min):")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,1).add(new Paragraph(reg_detalle.getCiclo_trabajo5())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                }

                table.addCell(new Cell(1,7).add(new Paragraph("Nota: En caso la POSICIÓN sea \"SUBIDA DE UNA PENDIENTE ANDANDO\"")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));
                table.addCell(new Cell(1,2).add(new Paragraph("Metros de\n" + "subida:")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));
                table.addCell(new Cell(1,3).add(new Paragraph(reg_detalle.getMtr_subida())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));
            }


            table.addCell(new Cell(1,12).add(new Paragraph("RESULTADOS DEL MONITOREO")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(4,2).add(new Paragraph("Resultados")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,6).add(new Paragraph("TA (T° aire)")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,4).add(new Paragraph(reg_detalle.getT_aire())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,6).add(new Paragraph("Temperatura del globo negro (TG) - (°C))")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,4).add(new Paragraph(reg_detalle.getT_globo())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,6).add(new Paragraph("HR % (Humedad Relativa)")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,4).add(new Paragraph(reg_detalle.getH_relativa())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,6).add(new Paragraph("Velocidad del Viento (m/s) ")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,4).add(new Paragraph(reg_detalle.getV_viento())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,12).add(new Paragraph("OBSERVACIONES")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(4,2).add(new Paragraph("Supervisor del\n" + "Servicio\n")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph("Nombre:")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,10).add(new Paragraph("DNI:")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(2,10).add(new Paragraph("Firma:")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));

            document.add(table);

            // Cerrar el documento y guardar el archivo
            document.close();

            // Notificar al usuario que el archivo PDF se ha guardado exitosamente
            Toast.makeText(requireActivity(), "Archivo PDF guardado en " + pdfFilePath, Toast.LENGTH_SHORT).show();

            Bundle bundle = new Bundle();
            bundle.putString("rutaPdf",pdfFilePath);
            Pdf pdfFragment = new Pdf();
            pdfFragment.setArguments(bundle);

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, pdfFragment)
                    .addToBackStack(null) // Si deseas agregar la transacción a la pila de retroceso
                    .commit();

        } catch (Exception e) {
            e.printStackTrace();
            // Notificar al usuario si ocurre algún error al guardar el archivo PDF
            Toast.makeText(requireActivity(), "Error al guardar el archivo PDF", Toast.LENGTH_SHORT).show();
        }
    }
    private void generarPdf_VelocidadAire(File pdfFile, String pdfFilePath, RegistroFormatos reg, RegistroFormatos_Detalle reg_detalle){
        try {
            DAO_FormatosTrabajo daoFormato = new DAO_FormatosTrabajo(getContext());
            Formatos_Trabajo formato = daoFormato.Buscar(reg.getId_plan_trabajo(), String.valueOf(reg.getId_formato()));

            PdfWriter writer = new PdfWriter(pdfFile);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument, PageSize.A4);

            float[] columnWidths = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}; // Definir anchos de columna
            Table table = new Table(UnitValue.createPercentArray(columnWidths));
            // Configurar la tabla para ocupar todo el ancho de la página
            table.setWidth(UnitValue.createPercentValue(100));

            Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.logo);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] bitmapData = stream.toByteArray();
            ImageData imageData = ImageDataFactory.create(bitmapData);
            /*String imagePath = "logo.jpg";
            ImageData imageData = ImageDataFactory.create(getContext().getAssets().open(imagePath));*/
            Image image = new Image(imageData);
            float maxWidthSize = 80f; // Tamaño máximo en píxeles
            float maxHeightSize = 50f; // Tamaño máximo en píxeles
            image.scaleToFit(maxWidthSize, maxHeightSize);

            // Primera fila con diferentes anchos de columna
            table.addCell(new Cell(4,2).add(image).setVerticalAlignment(VerticalAlignment.MIDDLE).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(2,6).add(new Paragraph("FORMATO DE CAMPO PARA\n" +
                    "MEDICIÓN DE\n" +
                    "VELOCIDAD DEL AIRE\n")).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph("Código")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("MEIN-FORMATO-MVA-01")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            // Segunda fila con diferentes anchos de columna
            table.addCell(new Cell(1,1).add(new Paragraph("Versión")).setWidth(2).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("1")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(2,6).add(new Paragraph("SISTEMA INTEGRADO DE GESTIÓN")).setFontSize(10).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,1).add(new Paragraph("Fecha")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("02/01/2024")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph("Página")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("1")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Empresa")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(formato.getNom_cliente())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Analista de campo:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(reg.getNom_analista())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Equipo utilizado:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Equipo de EF: ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg.getCod_equipo1())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Número de serie: ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getSerie_eq1())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,12).add(new Paragraph("LUGAR DE TRABAJO")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Área de Trabajo")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(reg.getArea_trabajo())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Actividades Realizadas")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(reg.getActividades_realizadas())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Horario de Trabajo:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(reg.getHora_trabajo())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Técnica de\n" + "acondicionamiento de aire ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg_detalle.getTecnica_acondaire())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Detalle de técnica de\n" + "acondicionamiento de\n" + "aire\n")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,4).add(new Paragraph(reg_detalle.getDetalle_tecnica_acondaire()).setFontSize(8).setTextAlignment(TextAlignment.CENTER)));

            table.addCell(new Cell(1,2).add(new Paragraph("Descripción del área de\n" + "trabajo: ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(reg.getDesc_area_trabajo())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,12).add(new Paragraph("RESULTADOS DEL MONITOREO")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(2,2).add(new Paragraph("Fecha de Monitoreo:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(2,3).add(new Paragraph(config.convertirFecha2(reg.getFec_monitoreo()))).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Hora inicial ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg.getHora_inicial())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Foto")).setFontSize(8).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(new DeviceRgb(221, 235, 247)));

            table.addCell(new Cell(1,2).add(new Paragraph("Hora Final ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg.getHora_final())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getFoto()!=null || reg.getRuta_foto()!=null?"X":"")).setFontSize(8).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(new DeviceRgb(221, 235, 247)));

            table.addCell(new Cell(5,2).add(new Paragraph("Velocidad del viento (m/s)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,5).add(new Paragraph(reg_detalle.getV_viento())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,5).add(new Paragraph(reg_detalle.getV_viento_2())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,5).add(new Paragraph(reg_detalle.getV_viento_3())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,5).add(new Paragraph(reg_detalle.getV_vto4())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,5).add(new Paragraph(reg_detalle.getV_vto5())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,5).add(new Paragraph(reg_detalle.getV_vto6())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,5).add(new Paragraph(reg_detalle.getV_vto7())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,5).add(new Paragraph(reg_detalle.getV_vto8())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,5).add(new Paragraph(reg_detalle.getV_vto9())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,5).add(new Paragraph(reg_detalle.getV_vto10())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Temperatura (°C)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(reg_detalle.getTemp())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Observación")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(reg.getObservacion())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));


            table.addCell(new Cell(4,2).add(new Paragraph("Supervisor del\n" + "Servicio\n")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph("Nombre:")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,10).add(new Paragraph("DNI:")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(2,10).add(new Paragraph("Firma:")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));

            document.add(table);

            // Cerrar el documento y guardar el archivo
            document.close();

            // Notificar al usuario que el archivo PDF se ha guardado exitosamente
            Toast.makeText(requireActivity(), "Archivo PDF guardado en " + pdfFilePath, Toast.LENGTH_SHORT).show();

            Bundle bundle = new Bundle();
            bundle.putString("rutaPdf",pdfFilePath);
            Pdf pdfFragment = new Pdf();
            pdfFragment.setArguments(bundle);

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, pdfFragment)
                    .addToBackStack(null) // Si deseas agregar la transacción a la pila de retroceso
                    .commit();

        } catch (Exception e) {
            e.printStackTrace();
            // Notificar al usuario si ocurre algún error al guardar el archivo PDF
            Toast.makeText(requireActivity(), "Error al guardar el archivo PDF", Toast.LENGTH_SHORT).show();
        }
    }
    private void generarPdf_HumedadRelativa(File pdfFile, String pdfFilePath, RegistroFormatos reg, RegistroFormatos_Detalle reg_detalle){
        try {
            DAO_FormatosTrabajo daoFormato = new DAO_FormatosTrabajo(getContext());
            Formatos_Trabajo formato = daoFormato.Buscar(reg.getId_plan_trabajo(), String.valueOf(reg.getId_formato()));

            PdfWriter writer = new PdfWriter(pdfFile);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument, PageSize.A4);

            float[] columnWidths = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}; // Definir anchos de columna
            Table table = new Table(UnitValue.createPercentArray(columnWidths));
            // Configurar la tabla para ocupar todo el ancho de la página
            table.setWidth(UnitValue.createPercentValue(100));

            Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.logo);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] bitmapData = stream.toByteArray();
            ImageData imageData = ImageDataFactory.create(bitmapData);
            /*String imagePath = "logo.jpg";
            ImageData imageData = ImageDataFactory.create(getContext().getAssets().open(imagePath));*/
            Image image = new Image(imageData);
            float maxWidthSize = 80f; // Tamaño máximo en píxeles
            float maxHeightSize = 50f; // Tamaño máximo en píxeles
            image.scaleToFit(maxWidthSize, maxHeightSize);

            // Primera fila con diferentes anchos de columna
            table.addCell(new Cell(4,2).add(image).setVerticalAlignment(VerticalAlignment.MIDDLE).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(2,6).add(new Paragraph("FORMATO DE CAMPO\n" +
                    "HUMEDAD RELATIVA\n")).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph("Código")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("MEIN-FORMATO-MHR-01")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            // Segunda fila con diferentes anchos de columna
            table.addCell(new Cell(1,1).add(new Paragraph("Versión")).setWidth(2).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("1")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(2,6).add(new Paragraph("SISTEMA INTEGRADO DE GESTIÓN")).setFontSize(10).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,1).add(new Paragraph("Fecha")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("02/01/2024")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph("Página")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("1")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Empresa")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(formato.getNom_cliente())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Analista de campo:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(reg.getNom_analista())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Equipo utilizado:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Anemómetro")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg.getCod_equipo1())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Número de serie: ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getSerie_eq1())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,12).add(new Paragraph("LUGAR DE TRABAJO")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Área de Trabajo")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(reg.getArea_trabajo())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Actividades Realizadas")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(reg.getActividades_realizadas())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Horario de Trabajo:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(reg.getHora_trabajo())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Técnica de\n" + "acondicionamiento de aire ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg_detalle.getTecnica_acondaire())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Detalle de técnica de\n" + "acondicionamiento de\n" + "aire\n")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,4).add(new Paragraph(reg_detalle.getDetalle_tecnica_acondaire()).setFontSize(8).setTextAlignment(TextAlignment.CENTER)));

            table.addCell(new Cell(1,2).add(new Paragraph("Descripción del área de\n" + "trabajo: ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(reg.getDesc_area_trabajo())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,12).add(new Paragraph("RESULTADOS DEL MONITOREO")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(2,2).add(new Paragraph("Fecha de Monitoreo:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(2,3).add(new Paragraph(config.convertirFecha2(reg.getFec_monitoreo()))).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Hora inicial ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg.getHora_inicial())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Foto")).setFontSize(8).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(new DeviceRgb(221, 235, 247)));

            table.addCell(new Cell(1,2).add(new Paragraph("Hora Final ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg.getHora_final())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getFoto()!=null || reg.getRuta_foto()!=null?"X":"")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Humedad Relativa Máxima\n" + "(%)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg_detalle.getH_relativa())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Humedad Relativa Mínima\n" + "(%)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,4).add(new Paragraph(reg_detalle.getH_relativa_2()).setFontSize(8).setTextAlignment(TextAlignment.CENTER)));

            table.addCell(new Cell(1,2).add(new Paragraph("Observación")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(reg.getObservacion())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(4,2).add(new Paragraph("Supervisor del\n" + "Servicio\n")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph("Nombre:")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,10).add(new Paragraph("DNI:")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(2,10).add(new Paragraph("Firma:")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));

            document.add(table);

            // Cerrar el documento y guardar el archivo
            document.close();

            // Notificar al usuario que el archivo PDF se ha guardado exitosamente
            Toast.makeText(requireActivity(), "Archivo PDF guardado en " + pdfFilePath, Toast.LENGTH_SHORT).show();

            Bundle bundle = new Bundle();
            bundle.putString("rutaPdf",pdfFilePath);
            Pdf pdfFragment = new Pdf();
            pdfFragment.setArguments(bundle);

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, pdfFragment)
                    .addToBackStack(null) // Si deseas agregar la transacción a la pila de retroceso
                    .commit();

        } catch (Exception e) {
            e.printStackTrace();
            // Notificar al usuario si ocurre algún error al guardar el archivo PDF
            Toast.makeText(requireActivity(), "Error al guardar el archivo PDF", Toast.LENGTH_SHORT).show();
        }
    }
    private void generarPdf_RadiacionElectro(File pdfFile, String pdfFilePath, RegistroFormatos reg, RegistroFormatos_Detalle reg_detalle){
        try {
            DAO_FormatosTrabajo daoFormato = new DAO_FormatosTrabajo(getContext());
            Formatos_Trabajo formato = daoFormato.Buscar(reg.getId_plan_trabajo(), String.valueOf(reg.getId_formato()));

            PdfWriter writer = new PdfWriter(pdfFile);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument, PageSize.A4);

            float[] columnWidths = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}; // Definir anchos de columna
            Table table = new Table(UnitValue.createPercentArray(columnWidths));
            // Configurar la tabla para ocupar todo el ancho de la página
            table.setWidth(UnitValue.createPercentValue(100));

            //-------------------------------------- CABECERA -----------------------------------------------------------------------------------------------
            Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.logo);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] bitmapData = stream.toByteArray();
            ImageData imageData = ImageDataFactory.create(bitmapData);
            /*String imagePath = "logo.jpg";
            ImageData imageData = ImageDataFactory.create(getContext().getAssets().open(imagePath));*/
            Image image = new Image(imageData);
            float maxWidthSize = 80f; // Tamaño máximo en píxeles
            float maxHeightSize = 50f; // Tamaño máximo en píxeles
            image.scaleToFit(maxWidthSize, maxHeightSize);

            // Primera fila con diferentes anchos de columna
            table.addCell(new Cell(4,2).add(image).setVerticalAlignment(VerticalAlignment.MIDDLE).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(2,6).add(new Paragraph("FORMATO DE CAMPO PARA\n" +
                    "MEDICIÓN DE\n" + "RADIACION ELECTROMAGNÉTICA\n")).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph("Código")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("MEIN-FORMATO-MRE-01")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            // Segunda fila con diferentes anchos de columna
            table.addCell(new Cell(1,1).add(new Paragraph("Versión")).setWidth(2).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("1")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(2,6).add(new Paragraph("SISTEMA INTEGRADO DE GESTIÓN")).setFontSize(10).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,1).add(new Paragraph("Fecha")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("02/01/2024")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph("Página")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("1")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            //-------------------------------------------------------------------------------------------------------------------------------------------------

            table.addCell(new Cell(1,2).add(new Paragraph("Empresa")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(formato.getNom_cliente())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Analista de campo:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,6).add(new Paragraph(reg.getNom_analista())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph("Foto")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getFoto()!=null || reg.getRuta_foto()!=null?"X":"")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Equipo utilizado:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,4).add(new Paragraph(reg.getCod_equipo1())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Número de serie: ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getSerie_eq1())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,3).add(new Paragraph("Hora")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getHora_situ())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Verificación In Situ:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            String contenido = reg.getVerf_insitu().equals("1") ? "SI" : "NO";
            table.addCell(new Cell(1,3).add(new Paragraph(contenido).setFontSize(8).setTextAlignment(TextAlignment.CENTER)));

            table.addCell(new Cell(1,12).add(new Paragraph("HORA DE MONITOREO")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Fecha:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Hora inicial")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Hora final")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,4).add(new Paragraph("Tiempo medición (min)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            //table.addCell(new Cell(1,3).add(new Paragraph("Jornada (h)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph(config.convertirFecha2(reg.getFec_monitoreo()))).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getHora_inicial())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getHora_final())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,4).add(new Paragraph(reg.getTiempo_medicion())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            //table.addCell(new Cell(1,3).add(new Paragraph(reg.getJornada())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,12).add(new Paragraph("DATOS DEL TRABAJADOR")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Nombres y\n"+"apellidos")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,5).add(new Paragraph(reg.getNom_trabajador())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph("Doc. identidad")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph(reg.getTipo_doc_trabajador())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getNum_doc_trabajador())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Puesto:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(reg.getPuesto_trabajador())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Área de Trabajo:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(reg.getArea_trabajo())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Función/Actividad")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(reg.getActividades_realizadas())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Edad")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(String.valueOf(reg.getEdad_trabajador()))).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,12).add(new Paragraph("HORARIO DE TRABAJO")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Horario de Trabajo")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getHora_trabajo())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,4).add(new Paragraph("Régimen laboral")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getRegimen_laboral())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Horario de Refrigerio: ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getHorario_refrigerio())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,4).add(new Paragraph("Jornada (horas) ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getJornada())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,12).add(new Paragraph("CONDICIONES DE TRABAJO")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Fuente Generadora")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,10).add(new Paragraph(reg_detalle.getFuente_generadora())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Tiempo de Exposición (h)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(reg.getTiempo_exposicion())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Descripción del ambiente de\n" + "trabajo ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,10).add(new Paragraph(reg.getDesc_area_trabajo())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Vestimenta o Indumentaria\n" + "del personal evaluado")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,10).add(new Paragraph(reg_detalle.getVestimenta_personal())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Control de Ingeniería")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(reg.getNom_ctrl_ingenieria())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Control Administrativo")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(reg.getNom_ctrl_admin())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("EPPs")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(reg.getNom_epp())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,12).add(new Paragraph("RESULTADOS")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,12).add(new Paragraph("Nivel de Radiación (A/m)")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("")).setFontSize(8).setBackgroundColor(new DeviceRgb(221, 235, 247)).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("0 m")).setFontSize(8).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("2 m")).setFontSize(8).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("4 m")).setFontSize(8).setBackgroundColor(new DeviceRgb(221, 235, 247)).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("6 m")).setFontSize(8).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("X")).setFontSize(8).setBackgroundColor(new DeviceRgb(221, 235, 247)).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg_detalle.getX())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg_detalle.getX2())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg_detalle.getX3())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg_detalle.getX4())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Y")).setFontSize(8).setBackgroundColor(new DeviceRgb(221, 235, 247)).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg_detalle.getY())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg_detalle.getY2())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg_detalle.getY3())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg_detalle.getY4())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Z")).setFontSize(8).setBackgroundColor(new DeviceRgb(221, 235, 247)).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg_detalle.getZ())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg_detalle.getZ2())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg_detalle.getZ3())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg_detalle.getZ4())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(4,2).add(new Paragraph("Supervisor del\n" + "Servicio\n")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph("Nombre:")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,10).add(new Paragraph("DNI:")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(2,10).add(new Paragraph("Firma:")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));

            document.add(table);

            // Cerrar el documento y guardar el archivo
            document.close();

            // Notificar al usuario que el archivo PDF se ha guardado exitosamente
            Toast.makeText(requireActivity(), "Archivo PDF guardado en " + pdfFilePath, Toast.LENGTH_SHORT).show();

            Bundle bundle = new Bundle();
            bundle.putString("rutaPdf",pdfFilePath);
            Pdf pdfFragment = new Pdf();
            pdfFragment.setArguments(bundle);

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, pdfFragment)
                    .addToBackStack(null) // Si deseas agregar la transacción a la pila de retroceso
                    .commit();

        } catch (Exception e) {
            e.printStackTrace();
            // Notificar al usuario si ocurre algún error al guardar el archivo PDF
            Toast.makeText(requireActivity(), "Error al guardar el archivo PDF", Toast.LENGTH_SHORT).show();
        }
    }
    private void generarPdf_RadiacionUV(File pdfFile, String pdfFilePath, RegistroFormatos reg, RegistroFormatos_Detalle reg_detalle){
        try {
            DAO_FormatosTrabajo daoFormato = new DAO_FormatosTrabajo(getContext());
            Formatos_Trabajo formato = daoFormato.Buscar(reg.getId_plan_trabajo(), String.valueOf(reg.getId_formato()));

            PdfWriter writer = new PdfWriter(pdfFile);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument, PageSize.A4);

            float[] columnWidths = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}; // Definir anchos de columna
            Table table = new Table(UnitValue.createPercentArray(columnWidths));
            // Configurar la tabla para ocupar todo el ancho de la página
            table.setWidth(UnitValue.createPercentValue(100));

            //-------------------------------------- CABECERA -----------------------------------------------------------------------------------------------
            Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.logo);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] bitmapData = stream.toByteArray();
            ImageData imageData = ImageDataFactory.create(bitmapData);
            /*String imagePath = "logo.jpg";
            ImageData imageData = ImageDataFactory.create(getContext().getAssets().open(imagePath));*/
            Image image = new Image(imageData);
            float maxWidthSize = 80f; // Tamaño máximo en píxeles
            float maxHeightSize = 50f; // Tamaño máximo en píxeles
            image.scaleToFit(maxWidthSize, maxHeightSize);

            // Primera fila con diferentes anchos de columna
            table.addCell(new Cell(4,2).add(image).setVerticalAlignment(VerticalAlignment.MIDDLE).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(2,6).add(new Paragraph("FORMATO DE CAMPO PARA\n" +
                    "MEDICIÓN DE RADIACIÓN UV\n")).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph("Código")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("MEIN-FORMATO-MRUV-01")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            // Segunda fila con diferentes anchos de columna
            table.addCell(new Cell(1,1).add(new Paragraph("Versión")).setWidth(2).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("1")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(2,6).add(new Paragraph("SISTEMA INTEGRADO DE GESTIÓN")).setFontSize(10).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,1).add(new Paragraph("Fecha")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("02/01/2024")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph("Página")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("1")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            //-------------------------------------------------------------------------------------------------------------------------------------------------

            table.addCell(new Cell(1,2).add(new Paragraph("Empresa")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(formato.getNom_cliente())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Analista de campo:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,6).add(new Paragraph(reg.getNom_analista())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph("Foto")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getFoto()!=null || reg.getRuta_foto()!=null?"X":"")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Equipo utilizado:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,4).add(new Paragraph(reg.getCod_equipo1())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Número de serie: ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getSerie_eq1())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,3).add(new Paragraph("Hora de verificación:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getHora_situ())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Verificación In Situ:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            String contenido = reg.getVerf_insitu().equals("1") ? "SI" : "NO";
            table.addCell(new Cell(1,3).add(new Paragraph(contenido).setFontSize(8).setTextAlignment(TextAlignment.CENTER)));

            table.addCell(new Cell(1,12).add(new Paragraph("HORA DE MONITOREO")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Fecha:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Hora inicial")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Hora final")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Tiempo Exposición (h)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Jornada")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph(config.convertirFecha2(reg.getFec_monitoreo()))).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg.getHora_inicial())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg.getHora_final())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getTiempo_exposicion())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getJornada())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,12).add(new Paragraph("DATOS DEL TRABAJADOR")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Nombres y\n"+"apellidos")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,5).add(new Paragraph(reg.getNom_trabajador())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph("Doc. identidad")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph(reg.getTipo_doc_trabajador())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getNum_doc_trabajador())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Puesto:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(reg.getPuesto_trabajador())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Área de Trabajo:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(reg.getArea_trabajo())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Actividades Realizadas")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(reg.getActividades_realizadas())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Edad")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(String.valueOf(reg.getEdad_trabajador()))).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(2,2).add(new Paragraph("Fototipo")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Color de piel")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg_detalle.getColor_piel())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(2,3).add(new Paragraph("Tiempo ocupando el\n" + "cargo (en la empresa)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(2,1).add(new Paragraph(reg.getAnio_ocu_cargo())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(2,2).add(new Paragraph(reg.getMes_ocu_cargo())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Tipo de piel")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg_detalle.getTipo_piel())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));


            table.addCell(new Cell(1,12).add(new Paragraph("HORARIO DE TRABAJO")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(2,2).add(new Paragraph("Horario de Trabajo")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(2,4).add(new Paragraph(reg.getHora_trabajo())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Horario de Refrigerio: ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getHorario_refrigerio())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Régimen laboral")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getRegimen_laboral())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,12).add(new Paragraph("CONDICIONES DE TRABAJO")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Fuente de radiación")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,10).add(new Paragraph(reg_detalle.getFuente_generadora())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Descripción del ambiente de\n" + "trabajo ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,10).add(new Paragraph(reg.getDesc_area_trabajo())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));


            table.addCell(new Cell(3,2).add(new Paragraph("Control de Ingeniería")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,7).add(new Paragraph("Espacios con sombra para el descanso: ")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Si (" + (reg_detalle.getSombra_descanso().equals("1") ? "X":" ") + ") No (" + (reg_detalle.getSombra_descanso().equals("2") ? "X": " ") + ")")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,7).add(new Paragraph("Existe en el ambiente mallas oscuras y tramo tupido:")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Si (" + (reg_detalle.getMalla_oscura().equals("1") ? "X":" ") + ") No (" + (reg_detalle.getMalla_oscura().equals("2") ? "X": " ") + ")")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph("Otros: " + reg.getOtro_ingenieria())).setFontSize(8).setTextAlignment(TextAlignment.LEFT));

            table.addCell(new Cell(3,2).add(new Paragraph("Control Administrativo")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,7).add(new Paragraph("Existe un programa de trabajos, faenas y tareas según el riesgo y\n" + "exposición a la radiación UV: ")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Si (" + (reg_detalle.getProg_expo_radiacion().equals("1") ? "X":" ") + ") No (" + (reg_detalle.getProg_expo_radiacion().equals("2") ? "X": " ") + ")")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,7).add(new Paragraph("Entre las 13:00 - 15:00 horas el trabajo es al aire libre:")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Si (" + (reg_detalle.getTrab_aire_libre().equals("1") ? "X":" ") + ") No (" + (reg_detalle.getTrab_aire_libre().equals("2") ? "X": " ") + ")")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph("Otros: " + reg.getOtro_administrativo())).setFontSize(8).setTextAlignment(TextAlignment.LEFT));





            table.addCell(new Cell(13,2).add(new Paragraph("EPPs")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,5).add(new Paragraph("Lentes")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,5).add(new Paragraph("Ropa")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,5).add(new Paragraph("Con protector de brillo: SI (" + (reg_detalle.getEpp_lentes_brillo().equals("1")?"X":"") + ") NO (" + (reg_detalle.getEpp_lentes_brillo().equals("2")?"X":"") + ")")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,5).add(new Paragraph("Cuenta con certificación: SI (" + (reg_detalle.getRop_ccerti().equals("1")?"X":"") + ") NO (" + (reg_detalle.getRop_ccerti().equals("2")?"X":"") + ")")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,5).add(new Paragraph("Con protector lateral: SI (" + (reg_detalle.getProt_lat().equals("1")?"X":"") + ") NO (" + (reg_detalle.getProt_lat().equals("2")?"X":"") + ")")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,5).add(new Paragraph("Color oscuro: SI (" + (reg_detalle.getRop_coscuro().equals("1")?"X":"") + ") NO (" + (reg_detalle.getRop_coscuro().equals("2")?"X":"") + ")")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,5).add(new Paragraph("Lentes oscuros: SI (" + (reg_detalle.getLent_osc().equals("1")?"X":"") + ") NO (" + (reg_detalle.getLent_osc().equals("2")?"X":"") + ")")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,5).add(new Paragraph("Manga larga: SI (" + (reg_detalle.getRop_mlarga().equals("1")?"X":"") + ") NO (" + (reg_detalle.getRop_mlarga().equals("2")?"X":"") + ")")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,5).add(new Paragraph("Gorro")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,5).add(new Paragraph("Trama gruesa:  SI (" + (reg_detalle.getTgruesa().equals("1")?"X":"") + ") NO (" + (reg_detalle.getTgruesa().equals("2")?"X":"") + ")")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,5).add(new Paragraph("Utiliza gorro: SI (" + (reg_detalle.getEpp_gorro_2().equals("1")?"X":"") + ") NO (" + (reg_detalle.getEpp_gorro_2().equals("2")?"X":"") + ")")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,5).add(new Paragraph("Casco")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,5).add(new Paragraph("Protección tipo legionario: SI (" + (reg_detalle.getProt_legion().equals("1")?"X":"") + ") NO (" + (reg_detalle.getProt_legion().equals("2")?"X":"") + ")")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,5).add(new Paragraph("Utiliza casco: SI (" + (reg_detalle.getEpp_casco_2().equals("1")?"X":"") + ") NO (" + (reg_detalle.getEpp_casco_2().equals("2")?"X":"") + ")")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,5).add(new Paragraph("Protección con ala ancha: SI (" + (reg_detalle.getProt_aancha().equals("1")?"X":"") + ") NO (" + (reg_detalle.getProt_aancha().equals("2")?"X":"") + ")")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,5).add(new Paragraph("Cubre nuca: SI (" + (reg_detalle.getCubre_nuca().equals("1")?"X":"") + ") NO (" + (reg_detalle.getCubre_nuca().equals("2")?"X":"") + ")")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,10).add(new Paragraph("FPS")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,5).add(new Paragraph("Utiliza FPS: SI (" + (reg_detalle.getUtil_fps().equals("1")?"X":"") + ") NO (" + (reg_detalle.getUtil_fps().equals("2")?"X":"") + ")")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,5).add(new Paragraph("Cuentan con una guía de uso del FPS: SI (" + (reg_detalle.getGuia_fps().equals("1")?"X":"") + ") NO (" + (reg_detalle.getGuia_fps().equals("2")?"X":"") + ")")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,5).add(new Paragraph("Frecuencia de aplicación: cada 2-3 horas: (" + (reg_detalle.getFrec_aplic().equals("1")?"X":"") + ")")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,5).add(new Paragraph("Cada 30 minutos - 1 hora: (" + (reg_detalle.getFrec_aplic().equals("2")?"X":"") + ")")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,5).add(new Paragraph("Otra frecuencia: (" + (reg_detalle.getFrec_aplic().equals("3")?"X":"") + ")")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,5).add(new Paragraph(reg_detalle.getOtra_frecuencia())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,10).add(new Paragraph("Otros: " + reg_detalle.getOtro_epp())).setFontSize(8).setTextAlignment(TextAlignment.LEFT));

            table.addCell(new Cell(1,12).add(new Paragraph("RESULTADOS DE MONITOREO")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Resultados")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,8).add(new Paragraph(reg.getResultado())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("uW/cm2")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(3,2).add(new Paragraph("Supervisor del\n" + "Servicio\n")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph("Nombre:")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,10).add(new Paragraph("DNI:")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(2,10).add(new Paragraph("Firma:")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));

            document.add(table);

            // Cerrar el documento y guardar el archivo
            document.close();

            // Notificar al usuario que el archivo PDF se ha guardado exitosamente
            Toast.makeText(requireActivity(), "Archivo PDF guardado en " + pdfFilePath, Toast.LENGTH_SHORT).show();

            Bundle bundle = new Bundle();
            bundle.putString("rutaPdf",pdfFilePath);
            Pdf pdfFragment = new Pdf();
            pdfFragment.setArguments(bundle);

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, pdfFragment)
                    .addToBackStack(null) // Si deseas agregar la transacción a la pila de retroceso
                    .commit();

        } catch (Exception e) {
            e.printStackTrace();
            // Notificar al usuario si ocurre algún error al guardar el archivo PDF
            Toast.makeText(requireActivity(), "Error al guardar el archivo PDF", Toast.LENGTH_SHORT).show();
        }
    }
    private void generarPdf_EstresTermico(File pdfFile, String pdfFilePath, RegistroFormatos reg, RegistroFormatos_Detalle reg_detalle){
        try {
            DAO_FormatosTrabajo daoFormato = new DAO_FormatosTrabajo(getContext());
            Formatos_Trabajo formato = daoFormato.Buscar(reg.getId_plan_trabajo(), String.valueOf(reg.getId_formato()));

            PdfWriter writer = new PdfWriter(pdfFile);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument, PageSize.A4);

            float[] columnWidths = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}; // Definir anchos de columna
            Table table = new Table(UnitValue.createPercentArray(columnWidths));
            // Configurar la tabla para ocupar todo el ancho de la página
            table.setWidth(UnitValue.createPercentValue(100));

            Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.logo);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] bitmapData = stream.toByteArray();
            ImageData imageData = ImageDataFactory.create(bitmapData);
            /*String imagePath = "logo.jpg";
            ImageData imageData = ImageDataFactory.create(getContext().getAssets().open(imagePath));*/
            Image image = new Image(imageData);
            float maxWidthSize = 80f; // Tamaño máximo en píxeles
            float maxHeightSize = 50f; // Tamaño máximo en píxeles
            image.scaleToFit(maxWidthSize, maxHeightSize);

            // Primera fila con diferentes anchos de columna
            table.addCell(new Cell(4,2).add(image).setVerticalAlignment(VerticalAlignment.MIDDLE).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(2,6).add(new Paragraph("FORMATO DE CAMPO PARA MEDICIÓN DE\n" + "ESTRÉS POR CALOR\n")).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph("Código")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("MEIN-FORMATO-MEC-01")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            // Segunda fila con diferentes anchos de columna
            table.addCell(new Cell(1,1).add(new Paragraph("Versión")).setWidth(2).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("1")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(2,6).add(new Paragraph("SISTEMA INTEGRADO DE GESTIÓN")).setFontSize(10).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,1).add(new Paragraph("Fecha")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("02/01/2024")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph("Página")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("1")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            //------------------------------------------------------------------------------------------------------------------------------------------------------------------

            table.addCell(new Cell(1,2).add(new Paragraph("Empresa")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(formato.getNom_cliente())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Analista de campo:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,6).add(new Paragraph(reg.getNom_analista())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph("Foto")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getFoto()!=null || reg.getRuta_foto()!=null?"X":"")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Equipo utilizado:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Equipo de ET: ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg.getCod_equipo1())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Número de serie: ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getSerie_eq1())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Equipo utilizado:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Anemómetro: ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg.getCod_equipo2())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Número de serie: ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getSerie_eq2())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,3).add(new Paragraph("Hora de verificación:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getHora_situ())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Verificación In Situ:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            String contenido = reg.getVerf_insitu().equals("1") ? "SI" : "NO";
            table.addCell(new Cell(1,3).add(new Paragraph(contenido).setFontSize(8).setTextAlignment(TextAlignment.CENTER)));

            table.addCell(new Cell(1,12).add(new Paragraph("HORA DE MONITOREO")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Fecha:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Hora inicial")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Hora final")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Tiempo exposición (h)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Jornada (h)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph(config.convertirFecha2(reg.getFec_monitoreo()))).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg.getHora_inicial())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg.getHora_final())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getTiempo_exposicion())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getJornada())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,12).add(new Paragraph("DATOS PERSONALES DEL TRABAJADOR")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Nombres y\n"+"apellidos")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,5).add(new Paragraph(reg.getNom_trabajador())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph("Doc. identidad")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph(reg.getTipo_doc_trabajador())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getNum_doc_trabajador())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Puesto:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(reg.getPuesto_trabajador())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Área de Trabajo:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(reg.getArea_trabajo())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Actividades\n" + "Realizadas:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(reg.getActividades_realizadas())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Peso (Kg)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,5).add(new Paragraph(reg.getPeso_trabajador())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph("Edad")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,4).add(new Paragraph(String.valueOf(reg.getEdad_trabajador()))).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,12).add(new Paragraph("HORARIO DE TRABAJO")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(2,2).add(new Paragraph("Horario de Trabajo")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(2,5).add(new Paragraph(reg.getHora_trabajo())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Horario de Refrigerio: ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getHorario_refrigerio())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Régimen laboral")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getRegimen_laboral())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,12).add(new Paragraph("CONDICIONES DE TRABAJO")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Fuente Generadora")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,10).add(new Paragraph(reg_detalle.getFuente_generadora())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Descripción de la\n" + "fuente de calor")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,10).add(new Paragraph(reg_detalle.getDesc_fuente_frio())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Descripción del\n" + "Ambiente de\n" + "Trabajo\n")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,5).add(new Paragraph(reg.getDesc_area_trabajo())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,5).add(new Paragraph(reg.getArea_trab_deta())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Control de\n" + "Ingeniería")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg.getCtrl_ingenieria().equals("1")?"SI":"NO")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Nombre del control")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,5).add(new Paragraph(reg.getCtrl_ingenieria().equals("1")?reg.getNom_ctrl_ingenieria():"No Presenta")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(6,2).add(new Paragraph("Control\n" + "Administrativo")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Tiempo ocupando el cargo: ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph(reg.getAnio_ocu_cargo())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg.getMes_ocu_cargo())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Condición\n" + "del trabajo:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getCond_trab())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,7).add(new Paragraph("Si la actividad realizada es al aire libre sin techo: Existe zonas de sombra")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,3).add(new Paragraph("Si (" + (reg_detalle.getZona_sombra().equals("1") ? "X":" ") + ") No (" + (reg_detalle.getZona_sombra().equals("2") ? "X": " ") + ")")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,7).add(new Paragraph("Rotación de personal")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,3).add(new Paragraph("Si (" + (reg_detalle.getRotacion_personal().equals("1") ? "X":" ") + ") No (" + (reg_detalle.getRotacion_personal().equals("2") ? "X": " ") + ")")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,7).add(new Paragraph("Realiza tiempos de recuperación")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,3).add(new Paragraph("Si (" + (reg_detalle.getTiempo_recuperacion().equals("1") ? "X":" ") + ") No (" + (reg_detalle.getTiempo_recuperacion().equals("2") ? "X": " ") + ")")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,7).add(new Paragraph("Existe dispensador de agua cercanas al área")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,3).add(new Paragraph("Si (" + (reg_detalle.getDispensador().equals("1") ? "X":" ") + ") No (" + (reg_detalle.getDispensador().equals("2") ? "X": " ") + ")")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,7).add(new Paragraph("Capacitación sobre los riesgos a la exposición a fuentes de calor")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,3).add(new Paragraph("Si (" + (reg_detalle.getCapa_expo_frio().equals("1") ? "X":" ") + ") No (" + (reg_detalle.getCapa_expo_frio().equals("2") ? "X": " ") + ")")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Categoría de\n" + "trabajo ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("% Trabajo en actividad ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg_detalle.getCat_trabajo())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("% Descanso")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg_detalle.getPorc_desca())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(2,2).add(new Paragraph("Vestimenta o\n" + "Indumentaria del\n" + "personal evaluado")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(2,5).add(new Paragraph(reg_detalle.getVestimenta_personal())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Material de prenda ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg_detalle.getMaterial_prenda())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Color predominante")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg_detalle.getColor_predominante())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(2,2).add(new Paragraph("EPPs")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Zapatos de seguridad  (" + (reg_detalle.getEpp_zs().equals("1") ? "X":" ") + ")")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph("Casco (" + (reg_detalle.getEpp_casco().equals("1") ? "X":" ") + ")")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph("Lentes (" + (reg_detalle.getEpp_lentes().equals("1") ? "X":" ") + ")")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph("Guantes (" + (reg_detalle.getEpp_guantes().equals("1") ? "X":" ") + ")")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph("Orejeras (" + (reg_detalle.getEpp_orejeras().equals("1") ? "X":" ") + ")")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Tapones (" + (reg_detalle.getEpp_tapones().equals("1") ? "X":" ") + ")")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Cubre nuca (" + (reg_detalle.getEpp_cnuca().equals("1") ? "X":" ") + ")")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph("Otros: " + reg_detalle.getOtro_epp())).setFontSize(8).setTextAlignment(TextAlignment.LEFT));

            table.addCell(new Cell(1,12).add(new Paragraph("DETERMINACIÓN DE LA TASA METABÓLICA\n" + "(ISO 8996 - Ergonomía del ambiente térmico)\n")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Nivel de\n" + "Determinación:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(reg_detalle.getNom_nivel_d())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Método de\n" + "Determinación")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(reg_detalle.getMetodo_determ())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            if(reg_detalle.getId_metodo_determ().equals("1")){
                table.addCell(new Cell(1,2).add(new Paragraph("Tipo de trabajo")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,3).add(new Paragraph("Ocupación")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,3).add(new Paragraph("Rango de la tasa metabólica (W/m2)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,2).add(new Paragraph("Tasa metabólica media (W/m2)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,2).add(new Paragraph("Tasa metabólica media (KCAL/h)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));

                table.addCell(new Cell(1,2).add(new Paragraph(reg_detalle.getTipo_trabajo())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,3).add(new Paragraph(reg_detalle.getOcupacion())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,3).add(new Paragraph(reg_detalle.getRango_tasa_metab())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,2).add(new Paragraph(reg_detalle.getTasa_metab())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,2).add(new Paragraph(reg_detalle.getTasa_metab_kcal())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            }
            if(reg_detalle.getId_metodo_determ().equals("2")){
                table.addCell(new Cell(1,2).add(new Paragraph("Clase")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,5).add(new Paragraph("Actividad")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,2).add(new Paragraph("Tasa metabólica media (W/m2)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,3).add(new Paragraph("Tasa metabólica media (Kcal/h)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));

                table.addCell(new Cell(1,2).add(new Paragraph(reg_detalle.getClase())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));
                table.addCell(new Cell(1,5).add(new Paragraph(reg_detalle.getActividad_deter())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));
                table.addCell(new Cell(1,2).add(new Paragraph(reg_detalle.getTasa_metab())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));
                table.addCell(new Cell(1,3).add(new Paragraph(reg_detalle.getTasa_metab_kcal())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));
            }
            if(reg_detalle.getId_metodo_determ().equals("4")){
                table.addCell(new Cell(1,4).add(new Paragraph("Frecuencia cardiaca (ppm)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,3).add(new Paragraph(reg_detalle.getFrecuencia_deter())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,2).add(new Paragraph("Género")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,3).add(new Paragraph(reg_detalle.getGenero_deter())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            }
            if(reg_detalle.getId_nivel_d().equals("2")){
                if(Integer.parseInt(reg_detalle.getNtareas())>0){
                    table.addCell(new Cell(1,3).add(new Paragraph("Tarea 01:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,3).add(new Paragraph("Posición")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,3).add(new Paragraph("Parte del cuerpo")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,3).add(new Paragraph("Intensidad")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));

                    table.addCell(new Cell(1,3).add(new Paragraph(reg_detalle.getNom_tarea1())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(2,3).add(new Paragraph(reg_detalle.getPosicion_1())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(2,3).add(new Paragraph(reg_detalle.getPcuerpo_1())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(2,3).add(new Paragraph(reg_detalle.getIntensidad_1())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));

                    table.addCell(new Cell(1,2).add(new Paragraph("Ciclo Trab. (min):")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,1).add(new Paragraph(reg_detalle.getCiclo_trabajo1())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                }
                if(Integer.parseInt(reg_detalle.getNtareas())>1){
                    table.addCell(new Cell(1,3).add(new Paragraph("Tarea 02:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,3).add(new Paragraph("Posición")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,3).add(new Paragraph("Parte del cuerpo")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,3).add(new Paragraph("Intensidad")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));

                    table.addCell(new Cell(1,3).add(new Paragraph(reg_detalle.getNom_tarea2())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(2,3).add(new Paragraph(reg_detalle.getPosicion_2())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(2,3).add(new Paragraph(reg_detalle.getPcuerpo_2())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(2,3).add(new Paragraph(reg_detalle.getIntensidad_2())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));

                    table.addCell(new Cell(1,2).add(new Paragraph("Ciclo Trab. (min):")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,1).add(new Paragraph(reg_detalle.getCiclo_trabajo2())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                }
                if(Integer.parseInt(reg_detalle.getNtareas())>2){
                    table.addCell(new Cell(1,3).add(new Paragraph("Tarea 03:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,3).add(new Paragraph("Posición")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,3).add(new Paragraph("Parte del cuerpo")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,3).add(new Paragraph("Intensidad")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));

                    table.addCell(new Cell(1,3).add(new Paragraph(reg_detalle.getNom_tarea3())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(2,3).add(new Paragraph(reg_detalle.getPosicion_3())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(2,3).add(new Paragraph(reg_detalle.getPcuerpo_3())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(2,3).add(new Paragraph(reg_detalle.getIntensidad_3())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));

                    table.addCell(new Cell(1,2).add(new Paragraph("Ciclo Trab. (min):")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,1).add(new Paragraph(reg_detalle.getCiclo_trabajo3())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                }
                if(Integer.parseInt(reg_detalle.getNtareas())>3){
                    table.addCell(new Cell(1,3).add(new Paragraph("Tarea 04:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,3).add(new Paragraph("Posición")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,3).add(new Paragraph("Parte del cuerpo")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,3).add(new Paragraph("Intensidad")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));

                    table.addCell(new Cell(1,3).add(new Paragraph(reg_detalle.getNom_tarea4())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(2,3).add(new Paragraph(reg_detalle.getPosicion_4())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(2,3).add(new Paragraph(reg_detalle.getPcuerpo_4())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(2,3).add(new Paragraph(reg_detalle.getIntensidad_4())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));

                    table.addCell(new Cell(1,2).add(new Paragraph("Ciclo Trab. (min):")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,1).add(new Paragraph(reg_detalle.getCiclo_trabajo4())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                }
                if(Integer.parseInt(reg_detalle.getNtareas())>4){
                    table.addCell(new Cell(1,3).add(new Paragraph("Tarea 05:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,3).add(new Paragraph("Posición")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,3).add(new Paragraph("Parte del cuerpo")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,3).add(new Paragraph("Intensidad")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));

                    table.addCell(new Cell(1,3).add(new Paragraph(reg_detalle.getNom_tarea5())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(2,3).add(new Paragraph(reg_detalle.getPosicion_5())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(2,3).add(new Paragraph(reg_detalle.getPcuerpo_5())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(2,3).add(new Paragraph(reg_detalle.getIntensidad_5())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));

                    table.addCell(new Cell(1,2).add(new Paragraph("Ciclo Trab. (min):")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,1).add(new Paragraph(reg_detalle.getCiclo_trabajo5())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                }

                table.addCell(new Cell(1,7).add(new Paragraph("Nota: En caso la POSICIÓN sea \"SUBIDA DE UNA PENDIENTE ANDANDO\"")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));
                table.addCell(new Cell(1,2).add(new Paragraph("Metros de\n" + "subida:")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));
                table.addCell(new Cell(1,3).add(new Paragraph(reg_detalle.getMtr_subida())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));
            }


            table.addCell(new Cell(1,12).add(new Paragraph("RESULTADOS DEL MONITOREO")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Tipo de medición ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(reg.getNom_tipo_medicion())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));


            if(reg.getTipo_medicion().equals("1")){
                table.addCell(new Cell(6,2).add(new Paragraph("Resultados")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,7).add(new Paragraph("Parámetros")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,3).add(new Paragraph("1.1 m")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,7).add(new Paragraph("Temperatura del bulbo húmedo (WBT) (WET) (Tbh) - (°C) ")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,3).add(new Paragraph(reg_detalle.getT_bulbo())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,7).add(new Paragraph("Temperatura del aire (TA)/Temperatura del bulbo seco (Tbs) - (°C)")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,3).add(new Paragraph(reg_detalle.getT_aire())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,7).add(new Paragraph("Temperatura del globo negro (TG) - (°C)")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,3).add(new Paragraph(reg_detalle.getT_globo())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,7).add(new Paragraph("HR % (Humedad Relativa) ")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,3).add(new Paragraph(reg_detalle.getH_relativa())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,7).add(new Paragraph("Velocidad del Viento (m/s)")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,3).add(new Paragraph(reg_detalle.getV_viento())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            }

            if(reg.getTipo_medicion().equals("2")){
                table.addCell(new Cell(6,2).add(new Paragraph("Resultados")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,7).add(new Paragraph("Parámetros")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,1).add(new Paragraph("0.1 m")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,1).add(new Paragraph("1.1 m")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,1).add(new Paragraph("1.7 m")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

                table.addCell(new Cell(1,7).add(new Paragraph("Temperatura del bulbo húmedo (WBT) (WET) (Tbh) - (°C) ")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,1).add(new Paragraph(reg_detalle.getT_bulbo())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,1).add(new Paragraph(reg_detalle.getT_bulbo2())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,1).add(new Paragraph(reg_detalle.getT_bulbo3())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

                table.addCell(new Cell(1,7).add(new Paragraph("Temperatura del aire (TA)/Temperatura del bulbo seco (Tbs) - (°C) ")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,1).add(new Paragraph(reg_detalle.getT_aire())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,1).add(new Paragraph(reg_detalle.getT_aire_2())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,1).add(new Paragraph(reg_detalle.getT_aire_3())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

                table.addCell(new Cell(1,7).add(new Paragraph("Temperatura del globo negro (TG) - (°C)")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,1).add(new Paragraph(reg_detalle.getT_globo())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,1).add(new Paragraph(reg_detalle.getT_globo_2())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,1).add(new Paragraph(reg_detalle.getT_globo_3())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

                table.addCell(new Cell(1,7).add(new Paragraph("HR % (Humedad Relativa)")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,1).add(new Paragraph(reg_detalle.getH_relativa())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,1).add(new Paragraph(reg_detalle.getH_relativa_2())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,1).add(new Paragraph(reg_detalle.getH_relativa_3())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

                table.addCell(new Cell(1,7).add(new Paragraph("Velocidad del Viento (m/s) ")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,1).add(new Paragraph(reg_detalle.getV_viento())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,1).add(new Paragraph(reg_detalle.getV_viento_2())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,1).add(new Paragraph(reg_detalle.getV_viento_3())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            }



            table.addCell(new Cell(1,12).add(new Paragraph("OBSERVACIONES")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,12).add(new Paragraph(reg.getObservacion())).setFontSize(9).setWidth(2).setTextAlignment(TextAlignment.CENTER));


            table.addCell(new Cell(4,2).add(new Paragraph("Supervisor del\n" + "Servicio\n")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph("Nombre:")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,10).add(new Paragraph("DNI:")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(2,10).add(new Paragraph("Firma:")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));

            document.add(table);

            // Cerrar el documento y guardar el archivo
            document.close();

            // Notificar al usuario que el archivo PDF se ha guardado exitosamente
            Toast.makeText(requireActivity(), "Archivo PDF guardado en " + pdfFilePath, Toast.LENGTH_SHORT).show();

            Bundle bundle = new Bundle();
            bundle.putString("rutaPdf",pdfFilePath);
            Pdf pdfFragment = new Pdf();
            pdfFragment.setArguments(bundle);

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, pdfFragment)
                    .addToBackStack(null) // Si deseas agregar la transacción a la pila de retroceso
                    .commit();

        } catch (Exception e) {
            e.printStackTrace();
            // Notificar al usuario si ocurre algún error al guardar el archivo PDF
            Toast.makeText(requireActivity(), "Error al guardar el archivo PDF", Toast.LENGTH_SHORT).show();
        }
    }
    private void generarPdf_ConfortTermico(File pdfFile, String pdfFilePath, RegistroFormatos reg, RegistroFormatos_Detalle reg_detalle){
        try {
            DAO_FormatosTrabajo daoFormato = new DAO_FormatosTrabajo(getContext());
            Formatos_Trabajo formato = daoFormato.Buscar(reg.getId_plan_trabajo(), String.valueOf(reg.getId_formato()));

            PdfWriter writer = new PdfWriter(pdfFile);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument, PageSize.A4);

            float[] columnWidths = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}; // Definir anchos de columna
            Table table = new Table(UnitValue.createPercentArray(columnWidths));
            // Configurar la tabla para ocupar todo el ancho de la página
            table.setWidth(UnitValue.createPercentValue(100));

            // -------------------------------------------- C A B E C E R A ------------------------------------------------------------------------------
            Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.logo);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] bitmapData = stream.toByteArray();
            ImageData imageData = ImageDataFactory.create(bitmapData);
            /*String imagePath = "logo.jpg";
            ImageData imageData = ImageDataFactory.create(getContext().getAssets().open(imagePath));*/
            Image image = new Image(imageData);
            float maxWidthSize = 80f; // Tamaño máximo en píxeles
            float maxHeightSize = 50f; // Tamaño máximo en píxeles
            image.scaleToFit(maxWidthSize, maxHeightSize);

            // Primera fila con diferentes anchos de columna
            table.addCell(new Cell(4,2).add(image).setVerticalAlignment(VerticalAlignment.MIDDLE).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(2,6).add(new Paragraph("FORMATO DE CAMPO PARA\n" + "MEDICIÓN DE CONFORT TÉRMICO")).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph("Código")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("MEIN-FORMATO-MAF-01")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            // Segunda fila con diferentes anchos de columna
            table.addCell(new Cell(1,1).add(new Paragraph("Versión")).setWidth(2).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("1")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(2,6).add(new Paragraph("SISTEMA INTEGRADO DE GESTIÓN")).setFontSize(10).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,1).add(new Paragraph("Fecha")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("13/12/2023")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph("Página")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("1")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            // -------------------------------------------------------------------------------------------------------------------------------------------

            table.addCell(new Cell(1,2).add(new Paragraph("Empresa")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(formato.getNom_cliente())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Analista de campo:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,6).add(new Paragraph(reg.getNom_analista())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph("Foto")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getFoto()!=null || reg.getRuta_foto()!=null?"X":"")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Equipo utilizado:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Equipo")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg.getCod_equipo1())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Número de serie: ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getSerie_eq1())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Equipo utilizado:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Anemómetro: ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg.getCod_equipo2())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Número de serie: ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getSerie_eq2())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,3).add(new Paragraph("Hora de verificación:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getHora_situ())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Verificación In Situ:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            String contenido = reg.getVerf_insitu().equals("1") ? "SI" : "NO";
            table.addCell(new Cell(1,3).add(new Paragraph(contenido).setFontSize(8).setTextAlignment(TextAlignment.CENTER)));

            table.addCell(new Cell(1,12).add(new Paragraph("HORA DE MONITOREO")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Fecha:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Hora inicial")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Hora final")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Tiempo exposición (h)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Jornada (h)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph(config.convertirFecha2(reg.getFec_monitoreo()))).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg.getHora_inicial())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg.getHora_final())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getTiempo_exposicion())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getJornada())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,12).add(new Paragraph("DATOS PERSONALES DEL TRABAJADOR")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Nombres y\n"+"apellidos")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,5).add(new Paragraph(reg.getNom_trabajador())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph("Doc. identidad")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph(reg.getTipo_doc_trabajador())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getNum_doc_trabajador())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Puesto:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(reg.getPuesto_trabajador())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Área de Trabajo:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(reg.getArea_trabajo())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Actividades\n" + "Realizadas:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(reg.getActividades_realizadas())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Peso (Kg)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,5).add(new Paragraph(reg.getPeso_trabajador())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph("Edad")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,4).add(new Paragraph(String.valueOf(reg.getEdad_trabajador()))).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,12).add(new Paragraph("HORARIO DE TRABAJO")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Horario de Trabajo")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getHora_trabajo())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,4).add(new Paragraph("Régimen laboral")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getRegimen_laboral())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Horario de Refrigerio: ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getHorario_refrigerio())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Tiempo ocupando el\n" + "cargo\n")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg.getAnio_ocu_cargo())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg.getMes_ocu_cargo())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,12).add(new Paragraph("CONDICIONES DE TRABAJO")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Fuente Generadora")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,10).add(new Paragraph(reg_detalle.getFuente_generadora())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Descripción de la\n" + "fuente de frío:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,10).add(new Paragraph(reg_detalle.getDesc_fuente_frio())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,12).add(new Paragraph("DESCRIPCIÓN DEL ATUENDO")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Desnudo (" + (reg_detalle.getDesc_atuendo().equals("1") ? "X":" ") + ")")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Ligero (" + (reg_detalle.getDesc_atuendo().equals("2") ? "X":" ") + ")")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Medio (" + (reg_detalle.getDesc_atuendo().equals("3") ? "X":" ") + ")")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Pesado (" + (reg_detalle.getDesc_atuendo().equals("4") ? "X":" ") + ")")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,12).add(new Paragraph("DETERMINACIÓN DE LA TASA METABÓLICA\n" + "(ISO 8996 - Ergonomía del ambiente térmico)\n")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Nivel de\n" + "Determinación:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(reg_detalle.getNom_nivel_d())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Método de\n" + "Determinación")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(reg_detalle.getMetodo_determ())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            if(reg_detalle.getId_metodo_determ().equals("1")){
                table.addCell(new Cell(1,2).add(new Paragraph("Tipo de trabajo")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,4).add(new Paragraph("Ocupación")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,3).add(new Paragraph("Rango de la tasa metabólica (W/m2)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,3).add(new Paragraph("Tasa metabólica media (W/m2)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));

                table.addCell(new Cell(1,2).add(new Paragraph(reg_detalle.getTipo_trabajo())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,4).add(new Paragraph(reg_detalle.getOcupacion())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,3).add(new Paragraph(reg_detalle.getRango_tasa_metab())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,3).add(new Paragraph(reg_detalle.getTasa_metab())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            }
            if(reg_detalle.getId_metodo_determ().equals("2")){
                table.addCell(new Cell(1,2).add(new Paragraph("Clase")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,7).add(new Paragraph("Actividad")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,3).add(new Paragraph("Tasa metabólica media (W/m2")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));

                table.addCell(new Cell(1,2).add(new Paragraph(reg_detalle.getClase())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));
                table.addCell(new Cell(1,7).add(new Paragraph(reg_detalle.getActividad_deter())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));
                table.addCell(new Cell(1,3).add(new Paragraph(reg_detalle.getTasa_metab())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));
            }
            if(reg_detalle.getId_metodo_determ().equals("4")){
                table.addCell(new Cell(1,4).add(new Paragraph("Frecuencia cardiaca (ppm)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,3).add(new Paragraph(reg_detalle.getFrecuencia_deter())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,2).add(new Paragraph("Género")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1,3).add(new Paragraph(reg_detalle.getGenero_deter())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            }
            if(reg_detalle.getId_nivel_d().equals("2")){
                if(Integer.parseInt(reg_detalle.getNtareas())>0){
                    table.addCell(new Cell(1,3).add(new Paragraph("Tarea 01:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,3).add(new Paragraph("Posición")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,3).add(new Paragraph("Parte del cuerpo")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,3).add(new Paragraph("Intensidad")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));

                    table.addCell(new Cell(1,3).add(new Paragraph(reg_detalle.getNom_tarea1())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(2,3).add(new Paragraph(reg_detalle.getPosicion_1())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(2,3).add(new Paragraph(reg_detalle.getPcuerpo_1())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(2,3).add(new Paragraph(reg_detalle.getIntensidad_1())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));

                    table.addCell(new Cell(1,2).add(new Paragraph("Ciclo Trab. (min):")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,1).add(new Paragraph(reg_detalle.getCiclo_trabajo1())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                }
                if(Integer.parseInt(reg_detalle.getNtareas())>1){
                    table.addCell(new Cell(1,3).add(new Paragraph("Tarea 02:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,3).add(new Paragraph("Posición")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,3).add(new Paragraph("Parte del cuerpo")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,3).add(new Paragraph("Intensidad")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));

                    table.addCell(new Cell(1,3).add(new Paragraph(reg_detalle.getNom_tarea2())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(2,3).add(new Paragraph(reg_detalle.getPosicion_2())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(2,3).add(new Paragraph(reg_detalle.getPcuerpo_2())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(2,3).add(new Paragraph(reg_detalle.getIntensidad_2())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));

                    table.addCell(new Cell(1,2).add(new Paragraph("Ciclo Trab. (min):")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,1).add(new Paragraph(reg_detalle.getCiclo_trabajo2())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                }
                if(Integer.parseInt(reg_detalle.getNtareas())>2){
                    table.addCell(new Cell(1,3).add(new Paragraph("Tarea 03:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,3).add(new Paragraph("Posición")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,3).add(new Paragraph("Parte del cuerpo")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,3).add(new Paragraph("Intensidad")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));

                    table.addCell(new Cell(1,3).add(new Paragraph(reg_detalle.getNom_tarea3())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(2,3).add(new Paragraph(reg_detalle.getPosicion_3())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(2,3).add(new Paragraph(reg_detalle.getPcuerpo_3())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(2,3).add(new Paragraph(reg_detalle.getIntensidad_3())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));

                    table.addCell(new Cell(1,2).add(new Paragraph("Ciclo Trab. (min):")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,1).add(new Paragraph(reg_detalle.getCiclo_trabajo3())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                }
                if(Integer.parseInt(reg_detalle.getNtareas())>3){
                    table.addCell(new Cell(1,3).add(new Paragraph("Tarea 04:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,3).add(new Paragraph("Posición")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,3).add(new Paragraph("Parte del cuerpo")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,3).add(new Paragraph("Intensidad")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));

                    table.addCell(new Cell(1,3).add(new Paragraph(reg_detalle.getNom_tarea4())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(2,3).add(new Paragraph(reg_detalle.getPosicion_4())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(2,3).add(new Paragraph(reg_detalle.getPcuerpo_4())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(2,3).add(new Paragraph(reg_detalle.getIntensidad_4())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));

                    table.addCell(new Cell(1,2).add(new Paragraph("Ciclo Trab. (min):")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,1).add(new Paragraph(reg_detalle.getCiclo_trabajo4())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                }
                if(Integer.parseInt(reg_detalle.getNtareas())>4){
                    table.addCell(new Cell(1,3).add(new Paragraph("Tarea 05:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,3).add(new Paragraph("Posición")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,3).add(new Paragraph("Parte del cuerpo")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,3).add(new Paragraph("Intensidad")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));

                    table.addCell(new Cell(1,3).add(new Paragraph(reg_detalle.getNom_tarea5())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(2,3).add(new Paragraph(reg_detalle.getPosicion_5())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(2,3).add(new Paragraph(reg_detalle.getPcuerpo_5())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(2,3).add(new Paragraph(reg_detalle.getIntensidad_5())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));

                    table.addCell(new Cell(1,2).add(new Paragraph("Ciclo Trab. (min):")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1,1).add(new Paragraph(reg_detalle.getCiclo_trabajo5())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                }

                table.addCell(new Cell(1,7).add(new Paragraph("Nota: En caso la POSICIÓN sea \"SUBIDA DE UNA PENDIENTE ANDANDO\"")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));
                table.addCell(new Cell(1,2).add(new Paragraph("Metros de\n" + "subida:")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));
                table.addCell(new Cell(1,3).add(new Paragraph(reg_detalle.getMtr_subida())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.LEFT));
            }


            table.addCell(new Cell(1,12).add(new Paragraph("RESULTADOS DEL MONITOREO")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(4,2).add(new Paragraph("Resultados")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,6).add(new Paragraph("TA (T° aire)")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,4).add(new Paragraph(reg_detalle.getT_aire())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,6).add(new Paragraph("Temperatura del globo negro (TG) - (°C))")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,4).add(new Paragraph(reg_detalle.getT_globo())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,6).add(new Paragraph("HR % (Humedad Relativa)")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,4).add(new Paragraph(reg_detalle.getH_relativa())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,6).add(new Paragraph("Velocidad del Viento (m/s) ")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,4).add(new Paragraph(reg_detalle.getV_viento())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,12).add(new Paragraph("OBSERVACIONES")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,12).add(new Paragraph(reg.getObservacion())).setFontSize(9).setWidth(2).setTextAlignment(TextAlignment.CENTER));


            table.addCell(new Cell(4,2).add(new Paragraph("Supervisor del\n" + "Servicio\n")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph("Nombre:")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,10).add(new Paragraph("DNI:")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(2,10).add(new Paragraph("Firma:")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));

            document.add(table);

            // Cerrar el documento y guardar el archivo
            document.close();

            // Notificar al usuario que el archivo PDF se ha guardado exitosamente
            Toast.makeText(requireActivity(), "Archivo PDF guardado en " + pdfFilePath, Toast.LENGTH_SHORT).show();

            Bundle bundle = new Bundle();
            bundle.putString("rutaPdf",pdfFilePath);
            Pdf pdfFragment = new Pdf();
            pdfFragment.setArguments(bundle);

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, pdfFragment)
                    .addToBackStack(null) // Si deseas agregar la transacción a la pila de retroceso
                    .commit();

        } catch (Exception e) {
            e.printStackTrace();
            // Notificar al usuario si ocurre algún error al guardar el archivo PDF
            Toast.makeText(requireActivity(), "Error al guardar el archivo PDF", Toast.LENGTH_SHORT).show();
        }
    }
    private void generarPdf_Dosimetria(File pdfFile, String pdfFilePath, RegistroFormatos reg, RegistroFormatos_Detalle reg_detalle){
        try {
            DAO_FormatosTrabajo daoFormato = new DAO_FormatosTrabajo(getContext());
            Formatos_Trabajo formato = daoFormato.Buscar(reg.getId_plan_trabajo(), String.valueOf(reg.getId_formato()));

            PdfWriter writer = new PdfWriter(pdfFile);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument, PageSize.A4);

            float[] columnWidths = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}; // Definir anchos de columna
            Table table = new Table(UnitValue.createPercentArray(columnWidths));
            // Configurar la tabla para ocupar todo el ancho de la página
            table.setWidth(UnitValue.createPercentValue(100));

            Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.logo);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] bitmapData = stream.toByteArray();
            ImageData imageData = ImageDataFactory.create(bitmapData);
            /*String imagePath = "logo.jpg";
            ImageData imageData = ImageDataFactory.create(getContext().getAssets().open(imagePath));*/
            Image image = new Image(imageData);
            float maxWidthSize = 80f; // Tamaño máximo en píxeles
            float maxHeightSize = 50f; // Tamaño máximo en píxeles
            image.scaleToFit(maxWidthSize, maxHeightSize);

            // Primera fila con diferentes anchos de columna
            table.addCell(new Cell(4,2).add(image).setVerticalAlignment(VerticalAlignment.MIDDLE).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(2,6).add(new Paragraph("FORMATO DE CAMPO PARA MEDICIÓN DE\n" + "DOSIMETRÍA\n" +
                    "METODOLOGÍA: GUIA N° 1 - MEDICION DE RUIDO \\ D.S. N°024-20146-EM")).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph("Código")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("MEIN-FORMATO-MAF-01")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            // Segunda fila con diferentes anchos de columna
            table.addCell(new Cell(1,1).add(new Paragraph("Versión")).setWidth(2).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("3")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(2,6).add(new Paragraph("SISTEMA INTEGRADO DE GESTIÓN")).setFontSize(10).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,1).add(new Paragraph("Fecha")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("26/10/2023")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph("Página")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("1")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,3).add(new Paragraph("NOMBRE DE LA EMPRESA: ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,4).add(new Paragraph(formato.getNom_cliente())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Analista de campo:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getNom_analista())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,9).add(new Paragraph("DOSIMETRIA")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("MV-01")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,12).add(new Paragraph("EQUIPO UTILIZADO PARA LA MEDICIÓN")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("DOSÍMETRO:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,5).add(new Paragraph(reg.getCod_equipo1())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Número de serie: ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getSerie_eq1())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("CALIBRADOR")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,5).add(new Paragraph(reg.getCod_equipo2())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Número de serie: ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getSerie_eq2())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,12).add(new Paragraph("CALIBRACIÓN IN SITU DEL DOSÍMETRO")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Hora")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,4).add(new Paragraph(reg.getHora_situ())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph("Nivel")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg.getNivel())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Variación")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph(reg.getVariacion())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,12).add(new Paragraph("PARÁMETROS DE TIEMPO")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Fecha")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,4).add(new Paragraph("Hora Inicial ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph("Hora Final ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Tiempo de exposición (h) ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Jornada (h)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph("Foto")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(0,32,96)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph(config.convertirFecha2(reg.getFec_monitoreo()))).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,4).add(new Paragraph(reg.getHora_inicial())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph(reg.getHora_final())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg.getTiempo_exposicion())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg.getJornada())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph(reg.getFoto()!=null || reg.getRuta_foto()!=null?"X":"")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Nombre de Trabajador")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,5).add(new Paragraph(reg.getNom_trabajador())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph("Doc. identidad")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph(reg.getTipo_doc_trabajador())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getNum_doc_trabajador())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Puesto de Trabajador:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(reg.getPuesto_trabajador())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Área de Trabajo:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(reg.getArea_trabajo())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Actividades Realizadas:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(reg.getActividades_realizadas())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Edad (años)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(String.valueOf(reg.getEdad_trabajador()))).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(4,2).add(new Paragraph("Fuente generadora de\n" + "Ruido")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph( "Ruido externo (fuera de instalaciones) " + (reg.getCh_ruido_externo().equals("1")?"SI":"NO"))).setFontSize(8).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,10).add(new Paragraph( "Ruido proveniendnte de áreas contiguas " + (reg.getCh_ruido_antiguo().equals("1")?"SI":"NO"))).setFontSize(8).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,10).add(new Paragraph( "Rudio generado por el funcionamiento de:  " + (reg.getCh_ruido_generado_por().equals("1")?reg.getRuido_generado_por():" - "))).setFontSize(8).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,10).add(new Paragraph("Otros: " + reg.getOtro_ruido())).setFontSize(8).setTextAlignment(TextAlignment.LEFT));

            table.addCell(new Cell(1,12).add(new Paragraph("HORARIO DE TRABAJO")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Horario de Trabajo")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,5).add(new Paragraph(reg.getHora_trabajo())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Régimen laboral")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getRegimen_laboral())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Horario de Refrigerio: ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,5).add(new Paragraph(reg.getHorario_refrigerio())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Tiempo ocupando el\n" + "cargo (en la empresa) ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph(reg.getAnio_ocu_cargo())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg.getMes_ocu_cargo())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,12).add(new Paragraph("ANTECEDENTES MEDICOS")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Molestias al oído")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,5).add(new Paragraph("Si (" + (reg.getMolestia_oido().equals("1") ? "X":" ") + ") No (" + (reg.getMolestia_oido().equals("2") ? "X": " ") + ")")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Presenta alguna\n" + "enfermedad al oído")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Si (" + (reg.getEnfermedad_oido().equals("1") ? "X":" ") + ") No (" + (reg.getMolestia_oido().equals("2") ? "X": " ") + ")" + (reg.getEnfermedad_oido().equals("1")?reg.getDetalle_enf_oido():""))).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Fecha de último\n" + "examen médico ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,5).add(new Paragraph(reg.getMes_ultimo_examen())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,5).add(new Paragraph(reg.getAnio_ultimo_examen())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(12,2).add(new Paragraph("CONTROLES\n" + "EXISTENTES PARA EL\n" + "RUIDO")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(6,2).add(new Paragraph("Control de\n" + "Ingeniería")).setBackgroundColor(new DeviceRgb(237,237,237)).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(5,2).add(new Paragraph("SI (" +(reg.getCtrl_ingenieria().equals("1")?"X":"")+ ")")).setBackgroundColor(new DeviceRgb(237,237,237)).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,6).add(new Paragraph("Aislantes de sonido: SÍ ( " +(reg.getAislamiento() != null && reg.getAislamiento().equals("1")?"X":"")+ " ) NO ( " + (!reg.getAislamiento().equals("1")?"X":"") +" )")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,6).add(new Paragraph("Techos Altos/Techos Asorbentes: SÍ ( " +(reg.getTechos() != null && reg.getTechos().equals("1")?"X":"")+ " ) NO ( " + (!reg.getTechos().equals("1")?"X":"") +" )")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,6).add(new Paragraph("Cabinas de Aislamiento: SÍ ( " +(reg.getCabinas() != null && reg.getCabinas().equals("1")?"X":"")+ " ) NO ( " + (!reg.getCabinas().equals("1")?"X":"") +" )")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,6).add(new Paragraph("Orientación de Fachadas: SÍ ( " +(reg.getOrientacion() != null && reg.getOrientacion().equals("1")?"X":"")+ " ) NO ( " + (!reg.getOrientacion().equals("1")?"X":"") +" )")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,6).add(new Paragraph("Cerramientos: SÍ ( " +(reg.getCerramiento() != null && reg.getCerramiento().equals("1")?"X":"")+ " ) NO ( " + (!reg.getCerramiento().equals("1")?"X":"") +" )")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));

            table.addCell(new Cell(1,2).add(new Paragraph("NO (" +(reg.getCtrl_ingenieria().equals("2")?"X":"")+ ")")).setBackgroundColor(new DeviceRgb(237,237,237)).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,6).add(new Paragraph("Otros: " + reg.getOtro_ingenieria())).setFontSize(8).setTextAlignment(TextAlignment.LEFT));

            table.addCell(new Cell(6,2).add(new Paragraph("Control de\n" + "Administrativo")).setBackgroundColor(new DeviceRgb(237,237,237)).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(5,2).add(new Paragraph("SI (" +(reg.getCtrl_administrativo().equals("1")?"X":"")+ ")")).setBackgroundColor(new DeviceRgb(237,237,237)).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,6).add(new Paragraph("Capacitación sobre riesgos /uso correcto de EPP´s: SÍ ( " +(reg.getCapacitacion() != null && reg.getCapacitacion().equals("1")?"X":"")+ " ) NO ( " + (!reg.getCapacitacion().equals("1")?"X":"") +" )")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,6).add(new Paragraph("Señalización de Niveles de Presión Sonora: SÍ ( " +(reg.getSenializacion_precion() != null && reg.getSenializacion_precion().equals("1")?"X":"")+ " ) NO ( " + (!reg.getSenializacion_precion().equals("1")?"X":"") +" )")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,6).add(new Paragraph("Señalización de Uso Obligatorio de EPP's: SÍ ( " +(reg.getSenializacion_epp() != null && reg.getSenializacion_epp().equals("1")?"X":"")+ " ) NO ( " + (!reg.getSenializacion_epp().equals("1")?"X":"") +" )")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,6).add(new Paragraph("Rotación de Personal: SÍ ( " +(reg.getRotacion() != null && reg.getRotacion().equals("1")?"X":"")+ " ) NO ( " + (!reg.getRotacion().equals("1")?"X":"") +" )")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,6).add(new Paragraph("Administración de Tiempo de Exposición: SÍ ( " +(reg.getAdm_tiempo_expo() != null && reg.getAdm_tiempo_expo().equals("1")?"X":"")+ " ) NO ( " + (!reg.getAdm_tiempo_expo().equals("1")?"X":"") +" )")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));

            table.addCell(new Cell(1,2).add(new Paragraph("NO (" +(reg.getCtrl_administrativo().equals("2")?"X":"")+ ")")).setBackgroundColor(new DeviceRgb(237,237,237)).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,6).add(new Paragraph("Otros: " + reg.getOtro_administrativo())).setFontSize(8).setTextAlignment(TextAlignment.LEFT));

            table.addCell(new Cell(1,12).add(new Paragraph("PROTECTORES AUDITÍVOS")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("TAPONES AUDITIVOS")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,4).add(new Paragraph("Si (" + (reg.getTapones_au().equals("1") ? "X":" ") + ") No (" + (reg.getTapones_au().equals("2") ? "X": " ") + ")")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("OREJERAS")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,4).add(new Paragraph("Si (" + (reg.getOrejereas().equals("1") ? "X":" ") + ") No (" + (reg.getOrejereas().equals("2") ? "X": " ") + ")")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Marca")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,4).add(new Paragraph(reg.getMarca_tapones_audi())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Marca")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,4).add(new Paragraph(reg.getMarca_orejeras())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Modelo")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,4).add(new Paragraph(reg.getModelo_tapones_audi())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Modelo")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,4).add(new Paragraph(reg.getModelo_orejeras())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("NRR")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,4).add(new Paragraph(reg.getNrr_tapones_audi())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("NRR")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,4).add(new Paragraph(reg.getNrr_orejeras())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,12).add(new Paragraph("RESULTADOS DEL MONITOREO")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Leq dB(A)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph(reg.getLequi()!=0.0? String.valueOf(reg.getLequi()):"")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Lpico dB(A)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph(reg.getLpico_dba()!=0.0? String.valueOf(reg.getLpico_dba()):"")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Lmax dB(A)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph(reg.getLmax()!=0.0? String.valueOf(reg.getLmax()):"")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Lmin\n" + "dB(A)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph(reg.getLmin()!=0.0? String.valueOf(reg.getLmin()):"")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,12).add(new Paragraph("OBSERVACIONES")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,12).add(new Paragraph(reg.getObservacion())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(4,2).add(new Paragraph("Supervisor del\n" + "Servicio\n")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph("Nombre:")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,10).add(new Paragraph("DNI:")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(2,10).add(new Paragraph("Firma:")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));

            document.add(table);

            // Cerrar el documento y guardar el archivo
            document.close();

            // Notificar al usuario que el archivo PDF se ha guardado exitosamente
            Toast.makeText(requireActivity(), "Archivo PDF guardado en " + pdfFilePath, Toast.LENGTH_SHORT).show();

            Bundle bundle = new Bundle();
            bundle.putString("rutaPdf",pdfFilePath);
            Pdf pdfFragment = new Pdf();
            pdfFragment.setArguments(bundle);

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, pdfFragment)
                    .addToBackStack(null) // Si deseas agregar la transacción a la pila de retroceso
                    .commit();

        } catch (Exception e) {
            e.printStackTrace();
            // Notificar al usuario si ocurre algún error al guardar el archivo PDF
            Toast.makeText(requireActivity(), "Error al guardar el archivo PDF", Toast.LENGTH_SHORT).show();
        }
    }
    private void generarPdf_Sonometria(File pdfFile, String pdfFilePath, RegistroFormatos reg, RegistroFormatos_Detalle reg_detalle){
        try {
            DAO_FormatosTrabajo daoFormato = new DAO_FormatosTrabajo(getContext());
            Formatos_Trabajo formato = daoFormato.Buscar(reg.getId_plan_trabajo(), String.valueOf(reg.getId_formato()));

            PdfWriter writer = new PdfWriter(pdfFile);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument, PageSize.A4);

            float[] columnWidths = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}; // Definir anchos de columna
            Table table = new Table(UnitValue.createPercentArray(columnWidths));
            // Configurar la tabla para ocupar todo el ancho de la página
            table.setWidth(UnitValue.createPercentValue(100));

            Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.logo);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] bitmapData = stream.toByteArray();
            ImageData imageData = ImageDataFactory.create(bitmapData);
            /*String imagePath = "logo.jpg";
            ImageData imageData = ImageDataFactory.create(getContext().getAssets().open(imagePath));*/
            Image image = new Image(imageData);
            float maxWidthSize = 80f; // Tamaño máximo en píxeles
            float maxHeightSize = 50f; // Tamaño máximo en píxeles
            image.scaleToFit(maxWidthSize, maxHeightSize);

            // Primera fila con diferentes anchos de columna
            table.addCell(new Cell(4,2).add(image).setVerticalAlignment(VerticalAlignment.MIDDLE).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(2,6).add(new Paragraph("FORMATO DE CAMPO PARA MEDICIÓN DE SONOMETRÍA\n" +
                    "METODOLOGIA: NTP-ISO 9612-2010 - ESTRATEGIA 1")).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph("Código")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("MEIN-FORMATO-MAF-01")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            // Segunda fila con diferentes anchos de columna
            table.addCell(new Cell(1,1).add(new Paragraph("Versión")).setWidth(2).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("4")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(2,6).add(new Paragraph("SISTEMA INTEGRADO DE GESTIÓN")).setFontSize(10).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,1).add(new Paragraph("Fecha")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("24/10/2023")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph("Página")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("1")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,3).add(new Paragraph("NOMBRE DE LA EMPRESA: ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,4).add(new Paragraph(formato.getNom_cliente())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Analista de campo:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getNom_analista())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(3,4).add(new Paragraph("EQUIPO UTILIZADO PARA LA MEDICIÓN")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Sonómetro:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg.getCod_equipo1())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("N° Serie")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg.getSerie_eq1())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Calibrador Acústico:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg.getCod_equipo2())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("N° Serie")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg.getSerie_eq2())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Anemómetro")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg.getCod_equipo3())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("N° Serie")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg.getSerie_eq3())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,3).add(new Paragraph("CALIBRACIÓN IN SITU DEL SONÓMETRO")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph("Hora")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph(reg.getHora_situ())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph("Nivel")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg.getNivel())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Variación")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg.getVariacion())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,9).add(new Paragraph("SONOMETRÍA")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("SO-01")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Área de Trabajo:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(reg.getArea_trabajo())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Actividades Realizadas:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(reg.getActividades_realizadas())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Horario de Trabajo")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg.getHora_trabajo())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Jornada de trabajo")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg.getJornada())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("N° de personas\n" + "Trabajando ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(String.valueOf(reg.getN_personas()))).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Fuente generadora de\n" + "Ruido")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(reg.getRuido_generado_por())).setFontSize(8).setTextAlignment(TextAlignment.LEFT));

            table.addCell(new Cell(1,4).add(new Paragraph("Área que requiere concentración")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg.getArea_req_concentr())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Límite Máximo Permisible\n" + "(dB) ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getLim_max_permis())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,12).add(new Paragraph("RESULTADOS DEL MONITOREO")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Fecha")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,4).add(new Paragraph(config.convertirFecha2(reg.getFec_monitoreo()))).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Hora Inicial ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg.getHora_inicial())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph("Foto")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(0,32,96)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Tiempo de Medición")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,4).add(new Paragraph(reg.getTiempo_medicion())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Hora Final ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg.getHora_final())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(2,1).add(new Paragraph(reg.getFoto()!=null || reg.getRuta_foto()!=null?"X":"")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Velocidad del Viento (m/s) ")).setBackgroundColor(new DeviceRgb(221, 235, 247)).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,4).add(new Paragraph(reg.getV_viento())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Humedad Relativa (%)")).setBackgroundColor(new DeviceRgb(221, 235, 247)).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg.getH_relativa())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(4,2).add(new Paragraph("Registro (db)\n" + "por 5 Minutos\n")).setBackgroundColor(new DeviceRgb(221, 235, 247)).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph("MEDICIÓN")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("MEDICIÓN 1 ")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("MEDICIÓN 2 ")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("MEDICIÓN 3 ")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("MEDICIÓN 4 ")).setBackgroundColor(new DeviceRgb(231,230,230)).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph("MEDICIÓN 5 ")).setBackgroundColor(new DeviceRgb(231,230,230)).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,1).add(new Paragraph("LEQ")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(String.valueOf(reg.getLequi_md1()))).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(String.valueOf(reg.getLequi_md2()))).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(String.valueOf(reg.getLequi_md3()))).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(String.valueOf(reg.getLequi_md4()))).setBackgroundColor(new DeviceRgb(231,230,230)).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph(String.valueOf(reg.getLequi_md5()))).setBackgroundColor(new DeviceRgb(231,230,230)).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,1).add(new Paragraph("Lmáx.")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(String.valueOf(reg.getLmax_md1()))).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(String.valueOf(reg.getLmax_md2()))).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(String.valueOf(reg.getLmax_md3()))).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(String.valueOf(reg.getLmax_md4()))).setBackgroundColor(new DeviceRgb(231,230,230)).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph(String.valueOf(reg.getLmax_md5()))).setBackgroundColor(new DeviceRgb(231,230,230)).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,1).add(new Paragraph("Lmín.")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(String.valueOf(reg.getLmin_md1()))).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(String.valueOf(reg.getLmin_md2()))).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(String.valueOf(reg.getLmin_md3()))).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(String.valueOf(reg.getLmin_md4()))).setBackgroundColor(new DeviceRgb(231,230,230)).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph(String.valueOf(reg.getLmin_md5()))).setBackgroundColor(new DeviceRgb(231,230,230)).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,12).add(new Paragraph("NOTA: Se realizarán 2 mediciones adicionales en caso los resultados de las tres primeras mediciones del LEQ difieran en más de 3 dB")).setBackgroundColor(new DeviceRgb(231,230,230)).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(9,2).add(new Paragraph("Controles")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(3,2).add(new Paragraph("Control de\n" + "Ingeniería")).setBackgroundColor(new DeviceRgb(237,237,237)).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("SI (" +(reg.getCtrl_ingenieria().equals("1")?"X":"")+ ")")).setBackgroundColor(new DeviceRgb(237,237,237)).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,6).add(new Paragraph("Aislantes de sonido: SÍ ( " +(reg.getAislamiento() != null && reg.getAislamiento().equals("1")?"X":"")+ " ) NO ( " + (!reg.getAislamiento().equals("1")?"X":"") +" )")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(2,2).add(new Paragraph("NO (" +(reg.getCtrl_ingenieria().equals("2")?"X":"")+ ")")).setBackgroundColor(new DeviceRgb(237,237,237)).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,6).add(new Paragraph("Cabinas de Aislamiento: SÍ ( " +(reg.getCabinas() != null && reg.getCabinas().equals("1")?"X":"")+ " ) NO ( " + (!reg.getCabinas().equals("1")?"X":"") +" )")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,6).add(new Paragraph("Otros: " + reg.getOtro_ingenieria())).setFontSize(8).setTextAlignment(TextAlignment.LEFT));

            table.addCell(new Cell(6,2).add(new Paragraph("Control de\n" + "Administrativo")).setBackgroundColor(new DeviceRgb(237,237,237)).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(3,2).add(new Paragraph("SI (" +(reg.getCtrl_administrativo().equals("1")?"X":"")+ ")")).setBackgroundColor(new DeviceRgb(237,237,237)).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,6).add(new Paragraph("Capacitación sobre riesgos asociados a la exposición al ruido y uso de EPP´s: SÍ ( " +(reg.getCapacitacion() != null && reg.getCapacitacion().equals("1")?"X":"")+ " ) NO ( " + (!reg.getCapacitacion().equals("1")?"X":"") +" )")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,6).add(new Paragraph("Señalización de Niveles de Presión Sonora: SÍ ( " +(reg.getSenializacion_precion() != null && reg.getSenializacion_precion().equals("1")?"X":"")+ " ) NO ( " + (!reg.getSenializacion_precion().equals("1")?"X":"") +" )")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,6).add(new Paragraph("Señalización de Uso Obligatorio de EPP's: SÍ ( " +(reg.getSenializacion_epp() != null && reg.getSenializacion_epp().equals("1")?"X":"")+ " ) NO ( " + (!reg.getSenializacion_epp().equals("1")?"X":"") +" )")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(3,2).add(new Paragraph("NO (" +(reg.getCtrl_administrativo().equals("2")?"X":"")+ ")")).setBackgroundColor(new DeviceRgb(237,237,237)).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,6).add(new Paragraph("Rotación de Personal: SÍ ( " +(reg.getRotacion() != null && reg.getRotacion().equals("1")?"X":"")+ " ) NO ( " + (!reg.getRotacion().equals("1")?"X":"") +" )")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,6).add(new Paragraph("Administración de Tiempo de Exposición: SÍ ( " +(reg.getTiempo_exposicion() != null && reg.getTiempo_exposicion().equals("1")?"X":"")+ " ) NO ( " + (!reg.getTiempo_exposicion().equals("1")?"X":"") +" )")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,6).add(new Paragraph("Otros: " + reg.getOtro_administrativo())).setFontSize(8).setTextAlignment(TextAlignment.LEFT));

            table.addCell(new Cell(1,12).add(new Paragraph("PROTECTORES AUDITÍVOS")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("TAPONES AUDITIVOS")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,4).add(new Paragraph("Si (" + (reg.getTapones_au().equals("1") ? "X":" ") + ") No (" + (reg.getTapones_au().equals("2") ? "X": " ") + ")")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("OREJERAS")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,4).add(new Paragraph("Si (" + (reg.getOrejereas().equals("1") ? "X":" ") + ") No (" + (reg.getOrejereas().equals("2") ? "X": " ") + ")")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Marca")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,4).add(new Paragraph(reg.getMarca_tapones_audi())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Marca")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,4).add(new Paragraph(reg.getMarca_orejeras())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Modelo")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,4).add(new Paragraph(reg.getModelo_tapones_audi())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Modelo")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,4).add(new Paragraph(reg.getModelo_orejeras())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("NRR")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,4).add(new Paragraph(reg.getNrr_tapones_audi())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("NRR")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,4).add(new Paragraph(reg.getNrr_orejeras())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));


            table.addCell(new Cell(1,2).add(new Paragraph("OBSERVACIONES")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(reg.getObservacion())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(4,2).add(new Paragraph("Supervisor del\n" + "Servicio\n")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph("Nombre:")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,10).add(new Paragraph("DNI:")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(2,10).add(new Paragraph("Firma:")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));

            document.add(table);

            // Cerrar el documento y guardar el archivo
            document.close();

            // Notificar al usuario que el archivo PDF se ha guardado exitosamente
            Toast.makeText(requireActivity(), "Archivo PDF guardado en " + pdfFilePath, Toast.LENGTH_SHORT).show();

            Bundle bundle = new Bundle();
            bundle.putString("rutaPdf",pdfFilePath);
            Pdf pdfFragment = new Pdf();
            pdfFragment.setArguments(bundle);

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, pdfFragment)
                    .addToBackStack(null) // Si deseas agregar la transacción a la pila de retroceso
                    .commit();

        } catch (Exception e) {
            e.printStackTrace();
            // Notificar al usuario si ocurre algún error al guardar el archivo PDF
            Toast.makeText(requireActivity(), "Error al guardar el archivo PDF", Toast.LENGTH_SHORT).show();
        }
    }
    private void generarPdf_Iluminacion(File pdfFile, String pdfFilePath, RegistroFormatos reg, RegistroFormatos_Detalle reg_detalle){
        try {
            DAO_FormatosTrabajo daoFormato = new DAO_FormatosTrabajo(getContext());
            Formatos_Trabajo formato = daoFormato.Buscar(reg.getId_plan_trabajo(), String.valueOf(reg.getId_formato()));

            PdfWriter writer = new PdfWriter(pdfFile);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument, PageSize.A4);

            float[] columnWidths = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}; // Definir anchos de columna
            Table table = new Table(UnitValue.createPercentArray(columnWidths));
            // Configurar la tabla para ocupar todo el ancho de la página
            table.setWidth(UnitValue.createPercentValue(100));

            //-------------------------------------- CABECERA -----------------------------------------------------------------------------------------------
            Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.logo);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] bitmapData = stream.toByteArray();
            ImageData imageData = ImageDataFactory.create(bitmapData);
            /*String imagePath = "logo.jpg";
            ImageData imageData = ImageDataFactory.create(getContext().getAssets().open(imagePath));*/
            Image image = new Image(imageData);
            float maxWidthSize = 80f; // Tamaño máximo en píxeles
            float maxHeightSize = 50f; // Tamaño máximo en píxeles
            image.scaleToFit(maxWidthSize, maxHeightSize);

            // Primera fila con diferentes anchos de columna
            table.addCell(new Cell(4, 2).add(image).setVerticalAlignment(VerticalAlignment.MIDDLE).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(2, 6).add(new Paragraph("FORMATO DE CAMPO PARA MEDICIÓN DE\n" + "ILUMINACIÓN")).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1, 1).add(new Paragraph("Código")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1, 3).add(new Paragraph("MEIN-FORMATO-MI-01")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            // Segunda fila con diferentes anchos de columna
            table.addCell(new Cell(1, 1).add(new Paragraph("Versión")).setWidth(2).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1, 3).add(new Paragraph("1")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(2, 6).add(new Paragraph("SISTEMA INTEGRADO DE GESTIÓN")).setFontSize(10).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1, 1).add(new Paragraph("Fecha")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1, 3).add(new Paragraph("02/01/2024")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1, 1).add(new Paragraph("Página")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1, 3).add(new Paragraph("1")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            //-------------------------------------------------------------------------------------------------------------------------------------------------

            table.addCell(new Cell(1, 2).add(new Paragraph("Nombre de la Empresa:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1, 5).add(new Paragraph(formato.getNom_cliente())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(2, 3).add(new Paragraph("PLAN DE\n" + "MANTENIMIENTO DE\n" + "LUMINARIAS")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1, 1).add(new Paragraph("SI")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1, 1).add(new Paragraph("NO")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1, 2).add(new Paragraph("Analista de campo:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1, 5).add(new Paragraph(reg.getNom_analista())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1, 1).add(new Paragraph("(" + (reg_detalle.getPlan_mantenimiento_ilum().equals("1") ? "X" : " ") + ")")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1, 1).add(new Paragraph("(" + (reg_detalle.getPlan_mantenimiento_ilum().equals("2") ? "X" : " ") + ")")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1, 12).add(new Paragraph("EQUIPO UTILIZADO PARA LA MEDICIÓN")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1, 2).add(new Paragraph("Luxómetro")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1, 2).add(new Paragraph(reg.getCod_equipo1())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1, 2).add(new Paragraph("N° Serie")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1, 2).add(new Paragraph(reg.getSerie_eq1())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1, 2).add(new Paragraph("Ubicación de equipo")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1, 2).add(new Paragraph(reg.getUbic_equip())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1, 12).add(new Paragraph("VERIFICACIÓN IN SITU DEL LUXÓMETRO")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1, 2).add(new Paragraph("Hora de verificación:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1, 2).add(new Paragraph(reg.getHora_situ())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1, 4).add(new Paragraph("Lux")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1, 4).add(new Paragraph(reg.getLuz()).setFontSize(8).setTextAlignment(TextAlignment.CENTER)));

            table.addCell(new Cell(1, 9).add(new Paragraph("ILUMINACIÓN")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1, 2).add(new Paragraph("ILU-01")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1, 1).add(new Paragraph("Foto: " + (reg.getFoto() != null || reg.getRuta_foto() != null ? "X" : ""))).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1, 2).add(new Paragraph("Nombre de trabajador")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1, 5).add(new Paragraph(reg.getNom_trabajador())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1, 1).add(new Paragraph("Doc. identidad")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1, 1).add(new Paragraph(reg.getTipo_doc_trabajador())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1, 3).add(new Paragraph(reg.getNum_doc_trabajador())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1, 2).add(new Paragraph("Puesto:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1, 10).add(new Paragraph(reg.getPuesto_trabajador())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1, 2).add(new Paragraph("Área de Trabajo:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1, 5).add(new Paragraph(reg.getArea_trabajo())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1, 3).add(new Paragraph("N° de personas en la\n" + "estación de trabajo ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1, 2).add(new Paragraph(String.valueOf(reg.getN_personas()))).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1, 2).add(new Paragraph("Horario de Trabajo")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1, 5).add(new Paragraph(reg.getHora_trabajo())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1, 3).add(new Paragraph("Régimen laboral")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1, 2).add(new Paragraph(reg.getRegimen_laboral())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1, 2).add(new Paragraph("Tarea visual del puesto\n" + "de trabajo ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1, 10).add(new Paragraph(reg.getTarea_visual())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1, 2).add(new Paragraph("Tipo de área de trabajo")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1, 5).add(new Paragraph(reg.getTipo_tarea_visual())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1, 3).add(new Paragraph("Nivel Mínimo de\n" + "Iluminación (Lux)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1, 2).add(new Paragraph(reg.getNivel_min_ilu())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));


            table.addCell(new Cell(1, 12).add(new Paragraph("RESULTADOS DEL MONITOREO")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1, 3).add(new Paragraph("Fecha de Monitoreo:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1, 3).add(new Paragraph("Hora de Monitoreo")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1, 3).add(new Paragraph("Tipo de iluminación")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1, 3).add(new Paragraph("Tipo de medición")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1, 3).add(new Paragraph(config.convertirFecha2(reg.getFec_monitoreo()))).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1, 3).add(new Paragraph(reg.getHora_inicial())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1, 3).add(new Paragraph(reg_detalle.getTipo_iluminacion())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1, 3).add(new Paragraph(reg_detalle.getTipo_medicion_ilu())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));


            if (reg_detalle.getId_tipo_medicion_ilu() != null && reg_detalle.getId_tipo_medicion_ilu().equals("1")) {
                table.addCell(new Cell(1, 3).add(new Paragraph("Largo de escritorio (m)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1, 3).add(new Paragraph("Ancho de escritorio (m)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1, 3).add(new Paragraph("Número de puntos de medición")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1, 3).add(new Paragraph("Altura de plano de\n" + "trabajo (m)\n")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));

                table.addCell(new Cell(1, 3).add(new Paragraph(reg_detalle.getLarg_escrit())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1, 3).add(new Paragraph(reg_detalle.getAnch_escrit())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1, 3).add(new Paragraph(reg_detalle.getNum_pmedicion())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1, 3).add(new Paragraph(reg_detalle.getAlt_pltrabajo())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            }
            if (reg_detalle.getId_tipo_medicion_ilu() != null && reg_detalle.getId_tipo_medicion_ilu().equals("2")) {
                table.addCell(new Cell(1, 2).add(new Paragraph("Longitud de salón (m)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1, 2).add(new Paragraph("Ancho de salón (m)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1, 3).add(new Paragraph("Altura desde el plano de trabajo a luminarias (m)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1, 2).add(new Paragraph("Constante de salón (k)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1, 3).add(new Paragraph("Número mínimo de puntos de medición")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));

                table.addCell(new Cell(1, 2).add(new Paragraph(reg_detalle.getLong_salon())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1, 2).add(new Paragraph(reg_detalle.getAnch_salon())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1, 3).add(new Paragraph(reg_detalle.getAlt_pltrabajo_ilu())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1, 2).add(new Paragraph(reg_detalle.getConst_salon())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell(1, 3).add(new Paragraph(reg_detalle.getNum_min_pmedic())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            }

            int n_puntos = 0;
            if (reg_detalle.getId_tipo_medicion_ilu() != null && reg_detalle.getId_tipo_medicion_ilu().equals("1")) {
                if (reg_detalle.getNum_pmedicion() != null) {
                    n_puntos = Integer.parseInt(reg_detalle.getNum_pmedicion());
                }
            } else if (reg_detalle.getId_tipo_medicion_ilu() != null && reg_detalle.getId_tipo_medicion_ilu().equals("2")) {
                if (reg_detalle.getNum_min_pmedic() != null) {
                    n_puntos = Integer.parseInt(reg_detalle.getNum_min_pmedic());
                }
            }
            if (reg_detalle.getPuntos_med() != null) {
                String[] array = reg_detalle.getPuntos_med().split("\\*");
                if (n_puntos > 0) {
                    table.addCell(new Cell(1, 3).add(new Paragraph("IL-01 (Lux)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1, 3).add(new Paragraph(array[0])).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                }
                if (n_puntos > 1) {
                    table.addCell(new Cell(1, 3).add(new Paragraph("IL-02 (Lux)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1, 3).add(new Paragraph(array[1])).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                }
                if (n_puntos > 2) {
                    table.addCell(new Cell(1, 3).add(new Paragraph("IL-03 (Lux)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1, 3).add(new Paragraph(array[2])).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                }
                if (n_puntos > 3) {
                    table.addCell(new Cell(1, 3).add(new Paragraph("IL-04 (Lux)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1, 3).add(new Paragraph(array[3])).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                }
                if (n_puntos > 4) {
                    table.addCell(new Cell(1, 3).add(new Paragraph("IL-05 (Lux)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1, 3).add(new Paragraph(array[4])).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                }
                if (n_puntos > 5) {
                    table.addCell(new Cell(1, 3).add(new Paragraph("IL-06 (Lux)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1, 3).add(new Paragraph(array[5])).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                }
                if (n_puntos > 6) {
                    table.addCell(new Cell(1, 3).add(new Paragraph("IL-07 (Lux)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1, 3).add(new Paragraph(array[6])).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                }
                if (n_puntos > 7) {
                    table.addCell(new Cell(1, 3).add(new Paragraph("IL-08 (Lux)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1, 3).add(new Paragraph(array[7])).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                }
                if (n_puntos > 8) {
                    table.addCell(new Cell(1, 3).add(new Paragraph("IL-09 (Lux)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1, 3).add(new Paragraph(array[8])).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                }
                if (n_puntos > 9) {
                    table.addCell(new Cell(1, 3).add(new Paragraph("IL-10 (Lux)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1, 3).add(new Paragraph(array[9])).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                }
                if (n_puntos > 10) {
                    table.addCell(new Cell(1, 3).add(new Paragraph("IL-11 (Lux)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1, 3).add(new Paragraph(array[10])).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                }
                if (n_puntos > 11) {
                    table.addCell(new Cell(1, 3).add(new Paragraph("IL-12 (Lux)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1, 3).add(new Paragraph(array[11])).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                }
                if (n_puntos > 12) {
                    table.addCell(new Cell(1, 3).add(new Paragraph("IL-13 (Lux)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1, 3).add(new Paragraph(array[12])).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                }
                if (n_puntos > 13) {
                    table.addCell(new Cell(1, 3).add(new Paragraph("IL-14 (Lux)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1, 3).add(new Paragraph(array[13])).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                }
                if (n_puntos > 14) {
                    table.addCell(new Cell(1, 3).add(new Paragraph("IL-15 (Lux)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1, 3).add(new Paragraph(array[14])).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                }
                if (n_puntos > 15) {
                    table.addCell(new Cell(1, 3).add(new Paragraph("IL-16 (Lux)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1, 3).add(new Paragraph(array[15])).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                }
                if (n_puntos > 16) {
                    table.addCell(new Cell(1, 3).add(new Paragraph("IL-17 (Lux)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1, 3).add(new Paragraph(array[16])).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                }
                if (n_puntos > 17) {
                    table.addCell(new Cell(1, 3).add(new Paragraph("IL-18 (Lux)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1, 3).add(new Paragraph(array[17])).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                }
                if (n_puntos > 18) {
                    table.addCell(new Cell(1, 3).add(new Paragraph("IL-19 (Lux)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1, 3).add(new Paragraph(array[18])).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                }
                if (n_puntos > 19) {
                    table.addCell(new Cell(1, 3).add(new Paragraph("IL-20 (Lux)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1, 3).add(new Paragraph(array[19])).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                }
                if (n_puntos > 20) {
                    table.addCell(new Cell(1, 3).add(new Paragraph("IL-21 (Lux)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1, 3).add(new Paragraph(array[20])).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                }
                if (n_puntos > 21) {
                    table.addCell(new Cell(1, 3).add(new Paragraph("IL-22 (Lux)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1, 3).add(new Paragraph(array[21])).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                }
                if (n_puntos > 22) {
                    table.addCell(new Cell(1, 3).add(new Paragraph("IL-23 (Lux)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1, 3).add(new Paragraph(array[22])).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                }
                if (n_puntos > 23) {
                    table.addCell(new Cell(1, 3).add(new Paragraph("IL-24 (Lux)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1, 3).add(new Paragraph(array[23])).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                }
                if (n_puntos > 24) {
                    table.addCell(new Cell(1, 3).add(new Paragraph("IL-25 (Lux)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(new Cell(1, 3).add(new Paragraph(array[24])).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                }
                if (n_puntos % 2 == 1) {
                    table.addCell(new Cell(1, 6).add(new Paragraph(" ")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
                }
            }

            table.addCell(new Cell(1,2).add(new Paragraph("Cantidad de luminarias")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("N° de lámparas\n" + "por luminaria\n")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Altura de Plano\n" + "a Luminaria (m) ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Color Pared")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Color Piso")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Estado físico de\n" + "luminarias")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph(reg_detalle.getCant_iluminarias())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg_detalle.getN_lamparas())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg_detalle.getAltura_p_luminaria())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg_detalle.getColor_pared())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg_detalle.getColor_piso())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg_detalle.getEstado_fisico())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(2,12).add(new Paragraph("Tareas realizadas durante la medición")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,12).add(new Paragraph(reg.getActividades_realizadas())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,12).add(new Paragraph("OBSERVACIONES")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,12).add(new Paragraph(reg.getObservacion())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(4,2).add(new Paragraph("Supervisor del\n" + "Servicio\n")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph("Nombre:")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,10).add(new Paragraph("DNI:")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(2,10).add(new Paragraph("Firma:")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));

            document.add(table);

            // Cerrar el documento y guardar el archivo
            document.close();

            // Notificar al usuario que el archivo PDF se ha guardado exitosamente
            Toast.makeText(requireActivity(), "Archivo PDF guardado en " + pdfFilePath, Toast.LENGTH_SHORT).show();

            Bundle bundle = new Bundle();
            bundle.putString("rutaPdf",pdfFilePath);
            Pdf pdfFragment = new Pdf();
            pdfFragment.setArguments(bundle);

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, pdfFragment)
                    .addToBackStack(null) // Si deseas agregar la transacción a la pila de retroceso
                    .commit();

        } catch (Exception e) {
            e.printStackTrace();
            // Notificar al usuario si ocurre algún error al guardar el archivo PDF
            Toast.makeText(requireActivity(), "Error al guardar el archivo PDF", Toast.LENGTH_SHORT).show();
        }
    }
    private void generarPdf_Vibracion(File pdfFile, String pdfFilePath, RegistroFormatos reg, RegistroFormatos_Detalle reg_detalle){
        try {
            DAO_FormatosTrabajo daoFormato = new DAO_FormatosTrabajo(getContext());
            Formatos_Trabajo formato = daoFormato.Buscar(reg.getId_plan_trabajo(), String.valueOf(reg.getId_formato()));

            PdfWriter writer = new PdfWriter(pdfFile);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument, PageSize.A4);

            float[] columnWidths = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}; // Definir anchos de columna
            Table table = new Table(UnitValue.createPercentArray(columnWidths));
            // Configurar la tabla para ocupar todo el ancho de la página
            table.setWidth(UnitValue.createPercentValue(100));

            // -------------------------------------------- C A B E C E R A ------------------------------------------------------------------------------
            Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.logo);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] bitmapData = stream.toByteArray();
            ImageData imageData = ImageDataFactory.create(bitmapData);
            /*String imagePath = "logo.jpg";
            ImageData imageData = ImageDataFactory.create(getContext().getAssets().open(imagePath));*/
            Image image = new Image(imageData);
            float maxWidthSize = 80f; // Tamaño máximo en píxeles
            float maxHeightSize = 50f; // Tamaño máximo en píxeles
            image.scaleToFit(maxWidthSize, maxHeightSize);

            // Primera fila con diferentes anchos de columna
            table.addCell(new Cell(4,2).add(image).setVerticalAlignment(VerticalAlignment.MIDDLE).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(2,6).add(new Paragraph("FORMATO DE CAMPO PARA\n" + "MEDICIÓN DE VIBRACIÓN")).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph("Código")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("MEIN-FORMATO-MV-01")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            // Segunda fila con diferentes anchos de columna
            table.addCell(new Cell(1,1).add(new Paragraph("Versión")).setWidth(2).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("1")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(2,6).add(new Paragraph("SISTEMA INTEGRADO DE GESTIÓN")).setFontSize(10).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,1).add(new Paragraph("Fecha")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("02/01/2024")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph("Página")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("1")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            // -------------------------------------------------------------------------------------------------------------------------------------------

            table.addCell(new Cell(1,2).add(new Paragraph("Empresa")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(formato.getNom_cliente())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Analista de campo:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,6).add(new Paragraph(reg.getNom_analista())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph("Foto")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getFoto()!=null || reg.getRuta_foto()!=null?"X":"")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));// !reg.getRutaFoto().trim().isEmpty()

            table.addCell(new Cell(1,2).add(new Paragraph("Tipo de vibración:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(reg.getTipo_vibracion())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Equipo utilizado:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getCod_equipo1())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,4).add(new Paragraph("Número de serie: ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getSerie_eq1())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Hora de verificación:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getHora_situ())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,4).add(new Paragraph("Verificación In Situ:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            String contenido = reg.getVerf_insitu().equals("1") ? "SI" : "NO";
            table.addCell(new Cell(1,3).add(new Paragraph(contenido).setFontSize(8).setTextAlignment(TextAlignment.CENTER)));

            table.addCell(new Cell(1,12).add(new Paragraph("HORA DE MONITOREO")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Fecha:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Hora inicial")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Hora final")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Tiempo exposición (h)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Jornada (h)")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph(config.convertirFecha2(reg.getFec_monitoreo()))).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg.getHora_inicial())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph(reg.getHora_final())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getTiempo_exposicion())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getJornada())).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,12).add(new Paragraph("DATOS DEL TRABAJADOR")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Nombre de trabajador")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,5).add(new Paragraph(reg.getNom_trabajador())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph("Doc. identidad")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph(reg.getTipo_doc_trabajador())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getNum_doc_trabajador())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Puesto:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(reg.getPuesto_trabajador())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Área de Trabajo:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(reg.getArea_trabajo())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Actividades\n" + "Realizadas:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(reg.getActividades_realizadas())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Edad")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph(String.valueOf(reg.getEdad_trabajador()))).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,12).add(new Paragraph("HORARIO DE TRABAJO")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(2,2).add(new Paragraph("Horario de Trabajo")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(2,3).add(new Paragraph(reg.getHora_trabajo())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Horario de Refrigerio: ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,4).add(new Paragraph(reg.getHorario_refrigerio())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Régimen laboral")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,4).add(new Paragraph(reg.getRegimen_laboral())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,12).add(new Paragraph("CONDICIONES DE TRABAJO")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Fuente Generadora de\n" + "Vibración ")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,10).add(new Paragraph(reg_detalle.getFuente_generadora())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(1,2).add(new Paragraph("Descripción de la\n" + "fuente de vibracion:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,10).add(new Paragraph(reg_detalle.getDesc_fuente_frio())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            if(reg.getTipo_vibracion().equals("Mano - Brazo")){
                table.addCell(new Cell(1,2).add(new Paragraph("Lateralidad en la mano")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.LEFT));
                table.addCell(new Cell(1,10).add(new Paragraph(reg_detalle.getDesc_fuente_frio())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            }

            table.addCell(new Cell(1,2).add(new Paragraph("Control de Ingeniería")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph(reg.getCtrl_ingenieria().equals("1")?"SI":"NO")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Nombre del control")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,7).add(new Paragraph(reg.getCtrl_ingenieria().equals("1")?reg.getNom_ctrl_ingenieria():"No tiene")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            // Por revisar semializacion
            table.addCell(new Cell(3,2).add(new Paragraph("Control Administrativo")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,7).add(new Paragraph("Señalización de las áreas")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getSenializacion_vibracion().equals("1")?"SI":"NO")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,7).add(new Paragraph("Capacitación sobre de los riesgos a la exposición de vibración")).setFontSize(8).setWidth(2).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph(reg.getCapacitacion().equals("1")?"SI":"No")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Manteminiento de la\n" + "fuente generadora de\n" + "vibración")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,1).add(new Paragraph(reg.getMantenimiento_vibracion().equals("1")?"SI":"No")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Frecuencia")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,4).add(new Paragraph(reg_detalle.getFrecuencia())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(2,2).add(new Paragraph("EPPs")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Botas (" + (reg_detalle.getEpp_botas().equals("1")?"X":" ") + ")")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Guantes (" + (reg_detalle.getEpp_guantes().equals("1")?"X":" ") + ")")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Casco (" + (reg_detalle.getEpp_casco().equals("1")?"X":" ") + ")")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,3).add(new Paragraph("Protección auditiva (" + (reg_detalle.getEpp_orejeras().equals("1")?"X":" ") + ")")).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph("Otros: " + reg_detalle.getOtro_epp())).setFontSize(8).setTextAlignment(TextAlignment.LEFT));

            table.addCell(new Cell(1,12).add(new Paragraph("RESULTADOS DEL MONITOREO")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell(3,2).add(new Paragraph("Resultados")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("X:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,8).add(new Paragraph(reg_detalle.getX())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Y:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,8).add(new Paragraph(reg_detalle.getY())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,2).add(new Paragraph("Z:")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,8).add(new Paragraph(reg_detalle.getZ())).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            /*table.addCell(new Cell(1,12).add(new Paragraph("OBSERVACIONES")).setFontSize(9).setWidth(2).setBackgroundColor(new DeviceRgb(91, 144, 194)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,12).add(new Paragraph(reg.getObservacion())).setFontSize(9).setWidth(2).setTextAlignment(TextAlignment.CENTER));*/

            table.addCell(new Cell(4,2).add(new Paragraph("Supervisor del\n" + "Servicio\n")).setFontSize(8).setWidth(2).setBackgroundColor(new DeviceRgb(221, 235, 247)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell(1,10).add(new Paragraph("Nombre:")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(1,10).add(new Paragraph("DNI:")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell(2,10).add(new Paragraph("Firma:")).setFontSize(8).setTextAlignment(TextAlignment.LEFT));

            document.add(table);

            // Cerrar el documento y guardar el archivo
            document.close();

            // Notificar al usuario que el archivo PDF se ha guardado exitosamente
            Toast.makeText(requireActivity(), "Archivo PDF guardado en " + pdfFilePath, Toast.LENGTH_SHORT).show();

            Bundle bundle = new Bundle();
            bundle.putString("rutaPdf",pdfFilePath);
            Pdf pdfFragment = new Pdf();
            pdfFragment.setArguments(bundle);

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, pdfFragment)
                    .addToBackStack(null) // Si deseas agregar la transacción a la pila de retroceso
                    .commit();

        } catch (Exception e) {
            e.printStackTrace();
            // Notificar al usuario si ocurre algún error al guardar el archivo PDF
            Toast.makeText(requireActivity(), "Error al guardar el archivo PDF", Toast.LENGTH_SHORT).show();
        }
    }

    // Abrir Formatos para edidcion

    private void AbrirFormato(Fragment nuevo, RegistroFormatos registro, RegistroFormatos_Detalle detalle) {
        /*bundle.putString("nombre_formato",registro.getNom_formato());
        bundle.putString("id_pt_formato", String.valueOf(registro.getId_pt_formato()));// ENPAQUETAR DE PARAMETROS
        bundle.putString("id_plan_Trabajo", String.valueOf(registro.getId_plan_trabajo()));// ENPAQUETAR DE PARAMETROS
        bundle.putString("id_formato", String.valueOf(registro.getId_formato()));
        bundle.putString("cantidad", String.valueOf(registro.getCantidad()));
        bundle.putString("nomEmpresa",String.valueOf(registro.getNom_cliente()));*/
        bundle.putParcelable("registroForm",registro);
        bundle.putParcelable("detalleForm",detalle);
        bundle.putString("nomUsuario", id_colaborador);
        nuevo.setArguments(bundle);//ENVIAR PARAMETROS
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, nuevo)
                .addToBackStack(null) // Si deseas agregar la transacción a la pila de retroceso
                .commit();
    }
}

