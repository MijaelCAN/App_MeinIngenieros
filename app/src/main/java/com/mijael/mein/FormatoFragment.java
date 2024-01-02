package com.mijael.mein;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.mijael.mein.Utilidades.Util_Formato_pTrabajo;
import com.mijael.mein.Utilidades.Util_OrdenTrabajo;
import com.mijael.mein.getSQLite.Obtener_FormatoTrabajo;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FormatoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FormatoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button btn_atras;
    String id_plan_trabajo;
    public FormatoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FormatoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FormatoFragment newInstance(String param1, String param2) {
        FormatoFragment fragment = new FormatoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            id_plan_trabajo = getArguments().getString("id_plan_Trabajo");//297
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_formato, container, false);

        // Inicializar btn_atras después de inflar la vista del fragmento
        btn_atras = rootView.findViewById(R.id.btn_atras);
        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });
        Obtener_FormatoTrabajo formatoTrabajo = new Obtener_FormatoTrabajo(getActivity());
        Cursor cursor = formatoTrabajo.obtenerRegistro(Integer.valueOf(id_plan_trabajo));

        TableLayout tableLayout = rootView.findViewById(R.id.tb_ordenes);
        tableLayout.removeAllViews();

        TableRow headerRow = new TableRow(getActivity());

        // Agregar las cabeceras a la tabla*/
        headerRow.addView(createHeaderTextView("ACCIONES"));
        headerRow.addView(createHeaderTextView("FORMATO"));
        headerRow.addView(createHeaderTextView("EMPRESA"));
        headerRow.addView(createHeaderTextView("REALIZADO"));
        headerRow.addView(createHeaderTextView("POR REALIZAR"));
        headerRow.addView(createHeaderTextView("TOTAL"));

        tableLayout.addView(headerRow);
        Log.e("Arcoirir", String.valueOf(id_plan_trabajo));

        while (cursor.moveToNext()) {
            //TableRow row = new TableRow(getActivity());

            TableRow row = (TableRow) LayoutInflater.from(getActivity()).inflate(R.layout.custom_table_row2, tableLayout, false);

            // Obtener la URL y el código de la fila actual del cursor
            //String url = cursor.getString(cursor.getColumnIndex(Util_OrdenTrabajo.CAMPO_URL_PLAN_TRABAJO));
            String url = "dato";
            //String codOT = cursor.getString(cursor.getColumnIndex(Util_Formato_pTrabajo.CAMPO_NOM_FORMATO));

            //TextView tvCodOT = createContentTextView(codOT);
            //row.addView(tvCodOT);
            row.addView(createContentTextView(cursor.getString(cursor.getColumnIndex(Util_Formato_pTrabajo.CAMPO_NOM_FORMATO))));
            row.addView(createContentTextView(cursor.getString(cursor.getColumnIndex(Util_Formato_pTrabajo.CAMPO_NOM_CLIENTE))));
            row.addView(createContentTextView(cursor.getString(cursor.getColumnIndex(Util_Formato_pTrabajo.CAMPO_REALIZADO))));
            row.addView(createContentTextView(cursor.getString(cursor.getColumnIndex(Util_Formato_pTrabajo.CAMPO_POR_REALIZAR))));
            row.addView(createContentTextView(cursor.getString(cursor.getColumnIndex(Util_Formato_pTrabajo.CAMPO_CANTIDAD))));

            // Configurar el clic en el icono PDF
            ImageView iconUrl = row.findViewById(R.id.iconVer);
            iconUrl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (url != null && !url.isEmpty()) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://meiningenieros.pe/test/Apk/lista_formatos_ptrabajo"));
                        startActivity(intent);
                    } else {
                        // URL vacía o nula, manejar según tu lógica
                    }
                }
            });

            // Configurar el clic en el icono del Fragmento
            ImageView iconFragment = row.findViewById(R.id.iconEdit);
            iconFragment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Acción al hacer clic en el icono del fragmento
                    // Usar codOT para realizar alguna acción con el Fragmento
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainerView, new FormatoFragment());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });



            tableLayout.addView(row);
        }

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