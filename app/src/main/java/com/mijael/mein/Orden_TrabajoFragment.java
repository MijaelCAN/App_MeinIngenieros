package com.mijael.mein;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.mijael.mein.GET.getApi_Formato_pTrabajo;
import com.mijael.mein.Utilidades.Util_OrdenTrabajo;
import com.mijael.mein.getSQLite.Obtener_OrdenesTrabajo;

public class Orden_TrabajoFragment extends Fragment {

    int id;

    public Orden_TrabajoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt("id",0);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.e("LLUVIA",String.valueOf(id));

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_orden__trabajo, container, false);

        Obtener_OrdenesTrabajo ordenesTrabajo = new Obtener_OrdenesTrabajo(getActivity());
        Cursor cursor = ordenesTrabajo.obtenerTodos(id);
        cursor.moveToFirst();



        TableLayout tableLayout = rootView.findViewById(R.id.tb_ordenes);
        tableLayout.removeAllViews();

        TableRow headerRow = new TableRow(getActivity());

        // Agregar las cabeceras a la tabla*/
        headerRow.addView(createHeaderTextView("Acciones"));
        headerRow.addView(createHeaderTextView("Código OT"));
        headerRow.addView(createHeaderTextView("Código Cotización"));
        headerRow.addView(createHeaderTextView("Cliente"));
        headerRow.addView(createHeaderTextView("Tipo Servicio"));
        headerRow.addView(createHeaderTextView("Fecha Registro"));

        tableLayout.addView(headerRow);


        while (cursor.moveToNext()) {
            //TableRow row = new TableRow(getActivity());

            TableRow row = (TableRow) LayoutInflater.from(getActivity()).inflate(R.layout.custom_table_row, tableLayout, false);

            // Obtener la URL y el código de la fila actual del cursor
            String url = cursor.getString(cursor.getColumnIndex(Util_OrdenTrabajo.CAMPO_URL_PLAN_TRABAJO));
            String idPlanTrabajo = cursor.getString(cursor.getColumnIndex(Util_OrdenTrabajo.CAMPO_ID_PLAN_TRABAJO));
            String codOT = cursor.getString(cursor.getColumnIndex(Util_OrdenTrabajo.CAMPO_COD_OT));

            TextView tvCodOT = createContentTextView(codOT);
            row.addView(tvCodOT);
            row.addView(createContentTextView(cursor.getString(cursor.getColumnIndex(Util_OrdenTrabajo.CAMPO_COD_COTIZACION))));
            row.addView(createContentTextView(cursor.getString(cursor.getColumnIndex(Util_OrdenTrabajo.CAMPO_NOM_CLIENTE))));
            row.addView(createContentTextView(cursor.getString(cursor.getColumnIndex(Util_OrdenTrabajo.CAMPO_NOM_TIPO_SERVICIO))));
            row.addView(createContentTextView(cursor.getString(cursor.getColumnIndex(Util_OrdenTrabajo.CAMPO_FECHA_REGISTRO))));

            // Configurar el clic en el icono PDF
            ImageView iconUrl = row.findViewById(R.id.iconUrl);
            iconUrl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (url != null && !url.isEmpty()) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                    } else {
                        // URL vacía o nula, manejar según tu lógica
                    }
                }
            });

            // Configurar el clic en el icono del Fragmento
            ImageView iconFragment = row.findViewById(R.id.iconFragment);
            iconFragment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Acción al hacer clic en el icono del fragmento
                    // Usar codOT para realizar alguna acción con el Fragmento
                    FormatoFragment fragment = new FormatoFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("codPT", idPlanTrabajo);
                    fragment.setArguments(bundle);

                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });



            tableLayout.addView(row);
        }

        cursor.close();
        return rootView;
    }

    private TextView createHeaderTextView(String text) {
        TextView textView = new TextView(getActivity());
        textView.setText(text);
        textView.setTypeface(null, Typeface.BOLD); // Aplicar negrita
        textView.setTextColor(Color.WHITE); // Cambiar el color del texto
        textView.setBackgroundResource(R.drawable.header_background); // Agregar un fondo personalizado
        textView.setPadding(16, 8, 16, 8); // Agregar padding
        // Agregar mas Estilos
        return textView;
    }

    private TextView createContentTextView(String text) {
        TextView textView = new TextView(getActivity());
        textView.setText(text);
        textView.setBackgroundResource(R.drawable.cell_background); // Agregar un fondo personalizado
        textView.setPadding(16, 8, 16, 8); // Agregar padding
        // Puedes agregar más estilos según sea necesario
        return textView;
    }
}