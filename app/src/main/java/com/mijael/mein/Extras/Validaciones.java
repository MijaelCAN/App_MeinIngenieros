package com.mijael.mein.Extras;

import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Validaciones {
    public  boolean validarCampoObligatorio(EditText editText) {
        String inputText = editText.getText().toString().trim();
        if (inputText.isEmpty()) {
            editText.setError("Campo Obligatorio");
            editText.requestFocus();
            return false;
        } else {
            editText.setError(null); // Borrar el mensaje de error si no está vacío
            return true;
        }
    }
    public  boolean validarCampoObligatorio(Spinner spinner) {
        String selectedItemPosition = spinner.getSelectedItem().toString();
        if (selectedItemPosition.equals("seleccione") || selectedItemPosition.isEmpty()) {
            // No se ha seleccionado ningún elemento en el Spinner
            // Puedes mostrar un mensaje de error o realizar alguna acción adicional si es necesario
            View selectedView = spinner.getSelectedView();
            if (selectedView != null && selectedView instanceof TextView) {
                ((TextView) selectedView).setError("Selecciona una opción");
                selectedView.requestFocus(); // Mueve el enfoque al Spinner
            }
            return false;
        } else {
            View selectedView = spinner.getSelectedView();
            if (selectedView != null && selectedView instanceof TextView) {
                ((TextView) selectedView).setError(null); // Borrar el mensaje de error si no está vacío
            }
            return true;
        }
    }
    public  boolean validarCampoObligatorio(TextView text) {
        String inputText = text.getText().toString().trim();
        if (inputText.isEmpty()) {
            text.setError("Campo Obligatorio");
            text.requestFocus();
            return false;
        } else {
            text.setError(null); // Borrar el mensaje de error si no está vacío
            return true;
        }
    }
    public  boolean validarCampoObligatorio(RadioGroup radioGroup, Context context) {
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            // No se ha seleccionado ningún radioButton
            // Puedes mostrar un mensaje de error o realizar alguna acción aquí
            radioGroup.requestFocus(); // Enfoca el RadioGroup
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Seleccione una opcion")
                    .setTitle("Error")
                    .setPositiveButton("Aceptar", null)
                    .show();
            radioGroup.requestFocus(); // Enfoca el RadioGroup
            //builder.show();
            return false;
        } else {
            // Al menos un radioButton ha sido seleccionado
            return true;
        }
    }
    public  boolean validarCalculo(Boolean estado,Context context) {
        if (!estado) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Realiza el calculo antes de Guardar!")
                    .setTitle("Error")
                    .setPositiveButton("Aceptar", null)
                    .show();
            return false;
        }else{
            return true;
        }
    }

    public  boolean validarImagen(Boolean estado, Context context) {
        if (!estado) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Subir o cargar imagen antes de guardar")
                    .setTitle("Error")
                    .setPositiveButton("Aceptar", null)
                    .show();
            return false;
        }else{
            return true;
        }
    }
    public  boolean validarCampoObligatorio(RadioButton radioButton, Context context) {
        if (radioButton.isChecked()) {
            // El RadioButton específico ha sido seleccionado
            return true;
        } else {
            // El RadioButton específico no ha sido seleccionado
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Por favor, seleccione una opción")
                    .setTitle("Error")
                    .setPositiveButton("Aceptar", null)
                    .show();
            return false;
        }
    }

    public  String getValor(Spinner spinner){
        return spinner.getSelectedItem().toString();
    }
    public  String getValor(EditText editText){
        return editText.getText().toString();
    }
    public  String getValor(TextView textView){
        return textView.getText().toString();
    }
    public String getValor(RadioGroup group, View view) {
        final String[] opcionSeleccionada = {""};
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Obtener el RadioButton seleccionado
                RadioButton radioButton = view.findViewById(checkedId);
                // Obtener el texto del RadioButton seleccionado
                opcionSeleccionada[0] = radioButton.getText().toString();
            }
        });

        return opcionSeleccionada[0]; // Devolver el valor seleccionado
    }
    public int getValor2(RadioGroup radioGroup, View view) {
        int radioButtonId = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = radioGroup.findViewById(radioButtonId);
        String texto = radioButton.getText().toString();
        // Verificar el texto seleccionado y devolver el valor correspondiente
        if (texto.equals("Sí")) {
            return 1;
        } else if (texto.equals("No")) {
            return 2;
        } else if (texto.equals("Otro")) {
            return 3;
        }
        return 0;
    }
    public int getValorTag(RadioGroup radioGroup){
        int radioButtonId = radioGroup.getCheckedRadioButtonId();
        if(radioButtonId!=-1){
            RadioButton radioButton = radioGroup.findViewById(radioButtonId);
            String valorTag = radioButton.getTag().toString();
            return Integer.parseInt(valorTag);
        }
        return 0;
    }
    /*public void dispplayPDF(String pdfURL, Context context, PdfViewerPreferences pdfView){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(pdfURL);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.connect();
                    InputStream inputStream = connection.getInputStream();
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

                    pdfView.fromStream(bufferedInputStream)
                            .defaultPage(0)
                            .onLoad(context)
                            .scrollHandle(new DefaultScrollHandle(context))
                            .spacing(10) // Espacio entre las páginas
                            .pageFitPolicy(FitPolicy.BOTH) // Ajustar la página tanto en ancho como en alto
                            .load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }*/
    public boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
