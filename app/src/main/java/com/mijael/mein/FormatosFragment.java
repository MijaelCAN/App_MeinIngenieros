package com.mijael.mein;

import static java.lang.Integer.parseInt;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mijael.mein.DAO.DAO_FormatosTrabajo;
import com.mijael.mein.Entidades.Formatos_Trabajo;
import com.mijael.mein.Extras.Validaciones;
import com.mijael.mein.GET.ApiFormatosService;
import com.mijael.mein.HELPER.MeinSQLiteHelper;
import com.mijael.mein.SERVICIOS.FormatosService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormatosFragment extends Fragment {
    TextView tv_total, tv_porRealizar, tv_Realizado, tv_empresa, tv_formato, Num_orden;
    TextView tv_usu2, progressText, tv_usu;
    EditText txt_buscar;
    View cardViewLayout, rootView;
    CardView cardView;
    LinearLayout linearLayout;
    FormatosService formatosService;
    List<Formatos_Trabajo> formatos_trabajoList;
    Validaciones validar;
    String idColaborador;
    Bundle bundle;
    ProgressBar progressBar;
    private FragmentContainerView fragmentContainer;
    private MeinSQLiteHelper dataHelper;
    public FormatosFragment(Context context) {
        dataHelper = MeinSQLiteHelper.getInstance(context);
        formatosService = ApiFormatosService.getInstance().getFormatosService();
        validar = new Validaciones();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String id_plan_trabajo = "";
        String numero_orden = "";
        String nombre_empresa = "";

        MainActivity activity = (MainActivity) getActivity();
        assert activity != null;

        tv_usu = activity.findViewById(R.id.txt_usuario);
        txt_buscar = activity.findViewById(R.id.txt_buscarOrden);
        tv_usu2 = activity.findViewById(R.id.txt_usuario2);
        fragmentContainer = activity.findViewById(R.id.fragmentContainerView);

        txt_buscar.setVisibility(View.VISIBLE);
        tv_usu2.setText("");
        tv_usu.setVisibility(View.VISIBLE);


        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) fragmentContainer.getLayoutParams();
        params.topMargin = 400;
        fragmentContainer.setLayoutParams(params);

        bundle = new Bundle();
        Bundle bundle = getArguments();
        if (bundle != null) {
            id_plan_trabajo = bundle.getString("id_plan_Trabajo");
            numero_orden = bundle.getString("numero_orden");
            idColaborador = bundle.getString("idCola");
            nombre_empresa = bundle.getString("nombre_empresa");
        }

        assert id_plan_trabajo != null; //verificacion en tiempo real que la variable no sea nula

        // INFLANDO LA VISTA FROMATOS-FRAGMENT
        rootView = inflater.inflate(R.layout.fragment_formatos, container, false);


        Num_orden = rootView.findViewById(R.id.numero_orden);
        linearLayout = rootView.findViewById(R.id.contenedor_Card);
        tv_empresa = rootView.findViewById(R.id.nombre_empresa);

        txt_buscar.setHint("Buscar Formato");
        Num_orden.setText(numero_orden);
        tv_empresa.setText(nombre_empresa);

        if(validar.isOnline(getActivity())){
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("id_plan_Trabajo", id_plan_trabajo);

            Call<List<Formatos_Trabajo>> call = formatosService.getFormatosTrabajobyId(requestBody);
            call.enqueue(new Callback<List<Formatos_Trabajo>>() {
                @Override
                public void onResponse(Call<List<Formatos_Trabajo>> call, Response<List<Formatos_Trabajo>> response) {
                    if(response.isSuccessful()){
                        formatos_trabajoList = response.body();
                        for (Formatos_Trabajo registro : formatos_trabajoList) {
                            MostrarFOrmatos(registro, inflater);
                        }
                    }else{
                        Log.e("info: ", "No trajo datos del Servidor");
                    }
                }

                @Override
                public void onFailure(Call<List<Formatos_Trabajo>> call, Throwable t) {
                    Log.e("Error de API", "Error al realizar la llamada a la API: " + t.getMessage());
                }
            });
        }
        else{
            DAO_FormatosTrabajo dao_fromatosTrabajo = new DAO_FormatosTrabajo(getActivity());
            formatos_trabajoList = dao_fromatosTrabajo.obtenerFormatosTrabajo(parseInt(id_plan_trabajo));
            // aqui el metodo Mostrar
            for (Formatos_Trabajo registro : formatos_trabajoList) {
                MostrarFOrmatos(registro, inflater);
            }
        }

        return rootView;
    }

    public void filtrarDesdeActivity(String textoABuscar) {
        String formato_aBuscar = textoABuscar.toLowerCase().trim();
        Filtrar(formato_aBuscar, formatos_trabajoList, getLayoutInflater());
    }

    private void Filtrar(String formatoABuscar, List<Formatos_Trabajo> formatos_trabajoList, LayoutInflater inflater) {
        List<Formatos_Trabajo> lista_filtrada = new ArrayList<>();

        // Recorrer la lista original y agregar a la lista filtrada aquellos elementos que coincidan
        for (Formatos_Trabajo registro : formatos_trabajoList) {
            if (registro.getNom_formato().toLowerCase().contains(formatoABuscar.toLowerCase())) {
                lista_filtrada.add(registro);
            }
        }

        // Llamar a una función para actualizar la UI con la lista filtrada
        updateRecyclerView(lista_filtrada, inflater);
    }

    private void updateRecyclerView(List<Formatos_Trabajo> listaFiltrada, LayoutInflater inflater) {
        linearLayout.removeAllViews(); // Limpiar las vistas anteriores

        for (Formatos_Trabajo registro : listaFiltrada) {
            MostrarFOrmatos(registro, inflater);
        }
    }

    public void LlenarComboDosimetria(LayoutInflater inflater, ViewGroup container) {

    }

    private void init(View view) {
        tv_formato = view.findViewById(R.id.text_view_formato);
        //tv_empresa = view.findViewById(R.id.nombre_empresa);
        tv_total = view.findViewById(R.id.text_view_total);
        //tv_Realizado = view.findViewById(R.id.text_view_realizado);
        //tv_porRealizar = view.findViewById(R.id.text_view_realizar);
        progressBar = view.findViewById(R.id.progressBar);

    }

    private void MostrarFOrmatos(Formatos_Trabajo registro, LayoutInflater inflater) {

        cardViewLayout = inflater.inflate(R.layout.vista_card2, null);
        init(cardViewLayout);
        //tv_formato = cardViewLayout.findViewById(R.id.text_view_formato);
        /*tv_empresa = cardViewLayout.findViewById(R.id.text_view_empresa);
        tv_Realizado = cardViewLayout.findViewById(R.id.text_view_realizado);
        tv_porRealizar = cardViewLayout.findViewById(R.id.text_view_realizar);*/
        tv_total = cardViewLayout.findViewById(R.id.text_view_total);
        progressText = cardViewLayout.findViewById(R.id.progressText);

        tv_formato.setText(registro.getNom_formato());
        /*tv_empresa.setText(registro.getNom_cliente());
        tv_Realizado.setText(String.valueOf(registro.getRealizado()));
        tv_porRealizar.setText(String.valueOf(registro.getPor_realizar()));*/
        tv_total.setText(String.valueOf(registro.getCantidad()));
        String texto = String.valueOf(registro.getRealizado())+" / "+String.valueOf(registro.getCantidad());
        progressBar.setMax(registro.getCantidad());
        progressBar.setProgress(registro.getRealizado());
        progressText.setText(texto);

        CardView nuevo;
        nuevo = cardViewLayout.findViewById(R.id.contenerCard_OrdenTrabajo);
        LinearLayout layout = cardViewLayout.findViewById(R.id.idCard);
        if (registro.getRealizado() >= registro.getCantidad()) {
            layout.setBackgroundResource(R.drawable.style_border_1_inhab);
            tv_formato.setTextColor(Color.parseColor("#808080")); // Color gris claro como ejemplo
            /*tv_empresa.setTextColor(Color.parseColor("#808080"));
            tv_Realizado.setBackgroundResource(R.drawable.style_circ_inhab);
            tv_porRealizar.setBackgroundResource(R.drawable.style_circ_inhab);*/
            nuevo.setCardBackgroundColor(Color.parseColor("#D3D3D3"));
        }

        tv_total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatButton button = new AppCompatButton(getActivity());//CREANDO UN BOTON NUEVO PARA REDIRIGIR AL FORMATO
                button.setText("Llenar");
                button.setBackgroundResource(R.drawable.style_1);
                button.setTextColor(Color.WHITE);
                button.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                LinearLayout layout = new LinearLayout(getActivity()); //CREANDO UN OCNTENEDOR PARA PONER EL Button
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setPadding(150,0,0,0);
                layout.addView(button);

                if(registro.getRealizado()>0){
                    DetalleFormatosFragment Detallefragment = new DetalleFormatosFragment();
                    AbrirFormato(Detallefragment, registro);
                }else{
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                            .setTitle(registro.getNom_formato())
                            .setMessage("No hay Registros por visualizar\nRegistre uno")
                            .setView(layout)
                            .setPositiveButton(android.R.string.cancel, null)
                            .show();
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            NavegarFormato(registro);
                            alertDialog.dismiss();
                        }
                    });
                }

            }
        });

        tv_formato.setOnClickListener(new View.OnClickListener() { //EVENTO DE ONCLICK AL TIPO DE FORMATO
            @Override
            public void onClick(View v) {
                //Log.e("MENSAJE", registro.getNomFormato());
                NavegarFormato(registro);
            }
        });
        linearLayout.addView(cardViewLayout); // AÑADIR LA VISTA DE TARJETAS A LA SECCION ITERABLE
    }

    private void MensajeAlerta(String titulo) {
        new AlertDialog.Builder(getActivity())
                .setTitle(titulo)
                .setMessage("El total de Formatos ya se Completaron")
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    private void AbrirFormato(Fragment nuevo, Formatos_Trabajo registro) {
        bundle.putString("nombre_formato",registro.getNom_formato());
        bundle.putString("id_pt_formato", String.valueOf(registro.getId_pt_formato()));// ENPAQUETAR DE PARAMETROS
        bundle.putString("id_plan_Trabajo", String.valueOf(registro.getId_plan_trabajo()));// ENPAQUETAR DE PARAMETROS
        bundle.putString("id_formato", String.valueOf(registro.getId_formato()));
        bundle.putString("cantidad", String.valueOf(registro.getCantidad()));
        bundle.putString("nomEmpresa",String.valueOf(registro.getNom_cliente()));
        bundle.putString("nomUsuario", idColaborador);
        nuevo.setArguments(bundle);//ENVIAR PARAMETROS
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, nuevo)
                .addToBackStack(null) // Si deseas agregar la transacción a la pila de retroceso
                .commit();
    }

    public void NavegarFormato(Formatos_Trabajo registro){
        switch (registro.getNom_formato()) {
            case "DOSIMETRÍA"://PODRIA EVALUARSE CON EL -- idFormato --
                // Opción 1 - Navegar a Dosimetria
                if (registro.getRealizado() < registro.getCantidad()) {//EVALUAR SI EL TOTAL DE FORMATOS REALIZADOS ES MENOR AL TOTAL ASIGNADO
                    Fragment dosimetria = new DosimetriaFragment();//INSTANCIA DEL FRAGMENTO A DONDE QUIERO IR
                    AbrirFormato(dosimetria, registro);
                } else {
                    MensajeAlerta(registro.getNom_formato());
                }
                break;
            case "SONOMETRÍA":
                // Opción 2 - Navegar a SONOMETRIA
                if (registro.getRealizado() < registro.getCantidad()) {
                    Fragment sonometria = new SonometriaFragment();
                    AbrirFormato(sonometria, registro);
                } else {
                    MensajeAlerta(registro.getNom_formato());
                }
                break;
            case "LUXOMETRO":
            case "ILUMINACIÓN":
                // Opción 3 - Navegar a ILUMINACION
                if (registro.getRealizado() < registro.getCantidad()) {
                    Fragment iluminacion = new IluminacionFragment();
                    AbrirFormato(iluminacion, registro);
                } else {
                    MensajeAlerta(registro.getNom_formato());
                }
                break;
            case "VIBRACIÓN":
                // Opción 3 - Navegar a VIBRACION
                if (registro.getRealizado() < registro.getCantidad()) {
                    Fragment vibracion = new VibracionFragment();
                    AbrirFormato(vibracion, registro);
                } else {
                    MensajeAlerta(registro.getNom_formato());
                }
                break;
            case "ESTRES POR CALOR":
            case "ESTRÉS TÉRMICO":
                // Opción 3 - Navegar a ESTRES TERMICO
                if (registro.getRealizado() < registro.getCantidad()) {
                    Fragment estres = new EstresTermicoFragment();
                    AbrirFormato(estres, registro);
                } else {
                    MensajeAlerta(registro.getNom_formato());
                }
                break;
            case "RADIACION ELECTROMAGNÉTICA":
                // Opción 3 - Navegar a RADIACION
                if (registro.getRealizado() < registro.getCantidad()) {
                    Fragment radiacion = new RadiacionElectromagneticaFragment();
                    AbrirFormato(radiacion, registro);
                } else {
                    MensajeAlerta(registro.getNom_formato());
                }
                break;
            case "ESTRÉS POR FRÍO":
                // Opción 3 - Navegar a RADIACION
                if (registro.getRealizado() < registro.getCantidad()) {
                    Fragment estreFrio = new EstresFrioFragment();
                    AbrirFormato(estreFrio, registro);
                } else {
                    MensajeAlerta(registro.getNom_formato());
                }
                break;
            case "VELOCIDAD DEL AIRE":
                // Opción 3 - Navegar a RADIACION
                if (registro.getRealizado() < registro.getCantidad()) {
                    Fragment velocidadAire = new VelocidadAireFragment();
                    AbrirFormato(velocidadAire, registro);
                } else {
                    MensajeAlerta(registro.getNom_formato());
                }
                break;
            case "HUMEDAD RELATIVA":
                // Opción 3 - Navegar a RADIACION
                if (registro.getRealizado() < registro.getCantidad()) {
                    Fragment humedadRelativa = new HumedadRelativaFragment();
                    AbrirFormato(humedadRelativa, registro);
                } else {
                    MensajeAlerta(registro.getNom_formato());
                }
                break;
            case "RADIACIÓN UV":
                // Opción 3 - Navegar a RADIACION
                if (registro.getRealizado() < registro.getCantidad()) {
                    Fragment radiacionUv = new RadiacionUvFragment();
                    AbrirFormato(radiacionUv, registro);
                } else {
                    MensajeAlerta(registro.getNom_formato());
                }
                break;
            case "CONFORT TÉRMICO":
                // Opción 3 - Navegar a RADIACION
                if (registro.getRealizado() < registro.getCantidad()) {
                    Fragment confort = new ConfortTermicoFragment();
                    AbrirFormato(confort, registro);
                } else {
                    MensajeAlerta(registro.getNom_formato());
                }
                break;
        }
    }

}