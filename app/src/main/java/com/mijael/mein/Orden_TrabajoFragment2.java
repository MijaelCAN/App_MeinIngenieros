package com.mijael.mein;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mijael.mein.DAO.DAO_OrdenTrabajo;
import com.mijael.mein.Entidades.Orden_Trabajo;
import com.mijael.mein.Extras.Validaciones;
import com.mijael.mein.GET.ApiOrdenesService;
import com.mijael.mein.HELPER.OrdenTrabajoSQLiteHelper;
import com.mijael.mein.SERVICIOS.OrdenesTrabajoService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Orden_TrabajoFragment2 extends Fragment {
    View rootView;
    LinearLayout linearLayout;
    EditText txt_buscar;
    //List<Orden_Trabajo> ordenTrabajos;
    OrdenesTrabajoService ordenesService;
    List<Orden_Trabajo> lista_ordenes_api;
    List<Orden_Trabajo> ordenesFiltradas;
    Validaciones validar;
    String idColaborador;
    private OnSearchListener searchListener;
    private FragmentContainerView fragmentContainer;
    public Orden_TrabajoFragment2() {
        // Required empty public constructor
        // Obtener el servicio de órdenes de trabajo
        ordenesService = ApiOrdenesService.getInstance().getOrdenesService();
        ordenesFiltradas = new ArrayList<>();
        validar = new Validaciones();
    }
    public void setOnSearchListener(OnSearchListener listener) {
        this.searchListener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MainActivity activity = (MainActivity) getActivity();
        idColaborador="";
        Bundle bundle = getArguments();
        if (bundle != null) {
            idColaborador = bundle.getString("idCola");
            // Now you can use the ID as needed in your fragment
        }
        fragmentContainer = activity.findViewById(R.id.fragmentContainerView);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) fragmentContainer.getLayoutParams();
        params.topMargin = 400;
        fragmentContainer.setLayoutParams(params);

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_orden__trabajo2, container, false);
        linearLayout = rootView.findViewById(R.id.contenedor_Card);
        txt_buscar = rootView.findViewById(R.id.txt_buscarOrden);
        txt_buscar.setHint("Buscar Orden");

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("id_colaborador", idColaborador);
        if(validar.isOnline(getContext())){ // verificar si tiene internet
            Call<List<Orden_Trabajo>> call = ordenesService.getOrdenesTrabajobyId(requestBody);
            call.enqueue(new Callback<List<Orden_Trabajo>>() {
                @Override
                public void onResponse(Call<List<Orden_Trabajo>> call, Response<List<Orden_Trabajo>> response) {
                    if(response.isSuccessful()){
                        ordenesFiltradas = response.body();
                        for (Orden_Trabajo registro : ordenesFiltradas) { // REEMPLAZAR LA LISTA DEPENDIENDO SI TIENE O NO INTERNET
                            MostrarOrdenes(registro,inflater);
                        }
                    }else{
                        Log.e("info: ", "No trajo datos del Servidor");
                    }
                }

                @Override
                public void onFailure(Call<List<Orden_Trabajo>> call, Throwable t) {
                    Log.e("Error de API", "Error al realizar la llamada a la API: " + t.getMessage());
                }
            });
        }else{
            // PARA CUANDO NO TENGA INTERNET ---------------> INICIO
            DAO_OrdenTrabajo dao_ordenTrabajo = new DAO_OrdenTrabajo(getActivity());
            List<Orden_Trabajo> ordenesFiltradas = dao_ordenTrabajo.obtenerOrdenesTrabajo(Integer.valueOf(idColaborador));
            // -------------------------------> FIN
            for (Orden_Trabajo registro : ordenesFiltradas) { // REEMPLAZAR LA LISTA DEPENDIENDO SI TIENE O NO INTERNET
                MostrarOrdenes(registro,inflater);
            }
        }

        /*tv_buscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String orden_aBuscar = s.toString().toLowerCase().trim();//Obtener el texto Actual Ingreso
                Filtrar(orden_aBuscar,ordenTrabajos, inflater);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/

        return rootView;
    }
    public void filtrarDesdeActivity(String textoABuscar) {
        String orden_aBuscar = textoABuscar.toLowerCase().trim();
        Filtrar(orden_aBuscar, ordenesFiltradas, getLayoutInflater());
    }

    private void Filtrar(String ordenABuscar, List<Orden_Trabajo> ordenesFiltradas, LayoutInflater inflater) { // REEMPLAZAR LA LISTA DEPENDIENDO SI TIENE O NO INTERNET
        List<Orden_Trabajo> lista_filtrada = new ArrayList<>();

        // Recorrer la lista original y agregar a la lista filtrada aquellos elementos que coincidan
        for (Orden_Trabajo registro : ordenesFiltradas) { // REEMPLAZAR LA LISTA DEPENDIENDO SI TIENE O NO INTERNET
            if (registro.getCod_ot().toLowerCase().contains(ordenABuscar.toLowerCase())) {
                lista_filtrada.add(registro);
            }
        }
        // Llamar a una función para actualizar la UI con la lista filtrada
        updateRecyclerView(lista_filtrada,inflater);
    }

    private void updateRecyclerView(List<Orden_Trabajo> listaFiltrada, LayoutInflater inflater) {
        linearLayout.removeAllViews(); // Limpiar las vistas anteriores

        for (Orden_Trabajo registro : listaFiltrada) {
            MostrarOrdenes(registro, inflater);
        }
    }

    private void MostrarOrdenes(Orden_Trabajo registro, LayoutInflater inflater){
        View cardViewLayout = inflater.inflate(R.layout.vista_card, null);

        TextView textViewId_plan_Trabajo = rootView.findViewById(R.id.id_pTrabajo);
        TextView textViewOT = cardViewLayout.findViewById(R.id.text_view_ot);
        TextView textViewFecha = cardViewLayout.findViewById(R.id.text_view_fecha);
        TextView textViewTipoServicio = cardViewLayout.findViewById(R.id.text_view_tiposervicio);
        TextView textViewCliente = cardViewLayout.findViewById(R.id.text_view_cliente);
        TextView textViewCotizacion = cardViewLayout.findViewById(R.id.text_view_cotizacion);

        // Aquí encuentras tus TextView dentro del CardView y los llenas con los datos del registro
        textViewId_plan_Trabajo.setText(String.valueOf(registro.getId_plan_trabajo()));
        textViewOT.setText(registro.getCod_ot());
        textViewFecha.setText(registro.getFecha_registro());
        textViewCotizacion.setText(registro.getCod_cotizacion());
        textViewCliente.setText(registro.getNom_cliente());
        textViewTipoServicio.setText(registro.getNom_tipo_servicio());

        textViewOT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("MENSAJE","ENTRO AL CARD");
                Fragment nuevo = new FormatosFragment(getActivity());
                Bundle bundle = new Bundle();
                bundle.putString("id_plan_Trabajo", registro.getId_plan_trabajo() );
                bundle.putString("numero_orden", registro.getCod_ot());
                bundle.putString("idCola", idColaborador);
                bundle.putString("nombre_empresa",registro.getNom_cliente());
                nuevo.setArguments(bundle);
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, nuevo)
                        .addToBackStack(null) // Si deseas agregar la transacción a la pila de retroceso
                        .commit();

            }
        });
        linearLayout.addView(cardViewLayout);
    }
    public interface OnSearchListener {
        void onSearch(String searchTerm);
    }

}