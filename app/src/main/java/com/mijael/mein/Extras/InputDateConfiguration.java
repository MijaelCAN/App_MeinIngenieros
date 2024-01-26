package com.mijael.mein.Extras;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentContainerView;

import com.mijael.mein.DAO.DAO_DatosLocal;
import com.mijael.mein.DAO.DAO_Usuario;
import com.mijael.mein.Entidades.Usuario;
import com.mijael.mein.MainActivity;
import com.mijael.mein.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class InputDateConfiguration {
    public int hora=0;
    public int min=0;
    public Context context;
    public String id_colaborador;
    public String nom_Empresa;
    public View view;

    public InputDateConfiguration(Context context, String id_colaborador, String nom_Empresa, View view) {
        this.context = context;
        this.id_colaborador = id_colaborador;
        this.nom_Empresa = nom_Empresa;
        this.view = view;
    }

    public void ConfigPantalla(){
        DAO_Usuario usuario = new DAO_Usuario(context);
        Usuario nuevo = usuario.BuscarUsuario(Integer.parseInt(id_colaborador));
        String cadena = nuevo.getUsuario_nombres() + " "+ nuevo.getUsuario_apater();

        MainActivity activity = (MainActivity) context;
        EditText txt_buscar = activity.findViewById(R.id.txt_buscarOrden);
        TextView tv_usu2 = activity.findViewById(R.id.txt_usuario2);
        TextView tv_usu = activity.findViewById(R.id.txt_usuario);
        TextView tv_nombreUsuario = view.findViewById(R.id.tv_nomUsuario);
        TextView tv_nomEmpresa = view.findViewById(R.id.tv_nomEmpresa);

        tv_nombreUsuario.setText(cadena);
        tv_nomEmpresa.setText(nom_Empresa);
        txt_buscar.setVisibility(View.GONE);
        tv_usu2.setText(tv_usu.getText());
        FragmentContainerView fragmentContainer = activity.findViewById(R.id.fragmentContainerView);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) fragmentContainer.getLayoutParams();
        params.topMargin = 120;
        fragmentContainer.setLayoutParams(params);
    }
    public void configurarAutoCompleteTextView(AutoCompleteTextView autoCompleteTextView, List<String> listaElementos) {
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, listaElementos);
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
                context, // o getActivity() si estás en un fragmento
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
                context, // o getActivity() si estás en un fragmento
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
    public ArrayAdapter<String> LlenarSpinner(String nombreTabla, String campoTabla, Context context){

        List<String> datosParaSpinner = DAO_DatosLocal.obtenerDatosParaSpinner(nombreTabla, campoTabla,context);
        ArrayList<String> listaDatos = new ArrayList<>();
        listaDatos.add("");//SUGERENCIA PARA QUE NO APAREZCA SELECCIONE EN EL PDF
        listaDatos.addAll(datosParaSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, listaDatos);
        return adapter;
    }
    public ArrayAdapter<String> LlenarSpinner(String[] opciones){
        ArrayList<String> listaDatos = new ArrayList<>();
        listaDatos.add("");//SUGERENCIA PARA QUE NO APAREZCA SELECCIONE EN EL PDF
        listaDatos.addAll(Arrays.asList(opciones));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, listaDatos);
        return adapter;
    }
    public void MostrarCampos(LinearLayout layout, Spinner spinner){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemSeleccionado = parent.getItemAtPosition(position).toString();
                if (itemSeleccionado.equals("OTRO") || itemSeleccionado.equals("SI")) {
                    layout.setVisibility(View.VISIBLE);
                } else {
                    layout.setVisibility(View.GONE);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void llenarNrrYMostrarCampos(String nomTabla, String campoTabla, Spinner spinner, EditText txt, LinearLayout layout) {
        List<Object[]> listaValorNRR = DAO_DatosLocal.obtenerDatos(nomTabla,context);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemSelec = parent.getItemAtPosition(position).toString();
                //Log.e("Valor Modelo ", itemSelec);
                for (Object[] item : listaValorNRR) {
                    if (itemSelec.equals(item[0].toString())) {
                        txt.setText(item[1].toString());
                        break;
                    }
                }

                // Lógica para mostrar/ocultar campos
                if (itemSelec.equals("OTRO")) {
                    layout.setVisibility(View.VISIBLE);
                } else {
                    layout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No se seleccionó ningún elemento en el Spinner
            }
        });
    }

    public void llenarSpinnerConNumeros(Spinner spinner, int numeroMaximo, Context context) {
        // Crear una lista para almacenar los números
        List<String> numeros = new ArrayList<>();

        // Llenar la lista con números del 1 hasta el número máximo
        for (int i = 0; i <= numeroMaximo; i++) {
            numeros.add(String.valueOf(i));
        }
        // Crear un ArrayAdapter y establecerlo en el Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, numeros);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public String convertirFecha(String fechaEntradaStr) {
        String fechaSalidaStr="";
        if(!fechaEntradaStr.isEmpty()){
            SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            try {
                Date fechadate = formatoEntrada.parse(fechaEntradaStr);
                SimpleDateFormat formatoSalida = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                fechaSalidaStr = formatoSalida.format(fechadate);
                //tv_mostarFecha.setText(String.valueOf(fechaSalidaStr));

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        return fechaSalidaStr;
    }
    public void mostrarOpcionesGone(RadioGroup group, int checkedId, CardView card, RadioButton radio) {
        if (checkedId == radio.getId()) {
            card.setVisibility(View.VISIBLE);
        } else {
            card.setVisibility(View.GONE);
            limpiarElementos((ViewGroup) card.getChildAt(0));
        }
    }
    public void limpiarElementos(ViewGroup viewGroup) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = viewGroup.getChildAt(i);
            if (childView instanceof EditText) {
                ((EditText) childView).setText("");
            } else if (childView instanceof Spinner) {
                ((Spinner) childView).setSelection(0);
            } else if (childView instanceof RadioGroup) {
                ((RadioGroup) childView).clearCheck();
            } else if (childView instanceof ViewGroup) {
                limpiarElementos((ViewGroup) childView);
            }
        }
    }
    Handler mainHandler = new Handler(Looper.getMainLooper());
    public void buscarTrabajador(String dni,EditText tv_nombreUsuario) {
        OkHttpClient client = new OkHttpClient();

        String url = "https://apiperu.dev/api/dni/" + dni + "?api_token=12c87a5e7047a743a9f8f8df0b9d8b02a53d1df8e23941f746330fa4df675d7a";

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();

                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject json = new JSONObject(myResponse);
                                boolean success = json.getBoolean("success");
                                if (success) {
                                    String nombreCompleto = json.getJSONObject("data").getString("nombre_completo");
                                    tv_nombreUsuario.setText(nombreCompleto);
                                } else {
                                    String message = json.getString("message");
                                    // Manejar el error
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }
    public static String calcularTiempoMedicion(String horaInicial, String horaFinal) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            Date fechaInicial = format.parse(horaInicial);
            Date fechaFinal = format.parse(horaFinal);

            long diferencia = fechaFinal.getTime() - fechaInicial.getTime();

            long minutos = TimeUnit.MILLISECONDS.toMinutes(diferencia) % 60;
            long horas = TimeUnit.MILLISECONDS.toHours(diferencia);

            String tiempoMedicion = String.format("%02d:%02d", horas, minutos);
            return tiempoMedicion;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
