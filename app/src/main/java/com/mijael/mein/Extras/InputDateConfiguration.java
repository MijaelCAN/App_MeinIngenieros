package com.mijael.mein.Extras;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.fragment.app.FragmentContainerView;

import com.mijael.mein.DAO.DAO_DatosLocal;
import com.mijael.mein.DAO.DAO_Usuario;
import com.mijael.mein.Entidades.Usuario;
import com.mijael.mein.MainActivity;
import com.mijael.mein.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
        listaDatos.add("seleccione");
        listaDatos.addAll(datosParaSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, listaDatos);
        return adapter;
    }
    public ArrayAdapter<String> LlenarSpinner(String opcion1, String opcion2){
        ArrayList<String> listaDatos = new ArrayList<>();
        listaDatos.add("seleccione");
        listaDatos.add(opcion1);
        listaDatos.add(opcion2);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, listaDatos);
        return adapter;
    }
    public void MostrarCampos(LinearLayout layout, Spinner spinner){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemSeleccionado = parent.getItemAtPosition(position).toString();
                if (itemSeleccionado.equals("OTRO")) {
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
        for (int i = 1; i <= numeroMaximo; i++) {
            numeros.add(String.valueOf(i));
        }
        // Crear un ArrayAdapter y establecerlo en el Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, numeros);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
