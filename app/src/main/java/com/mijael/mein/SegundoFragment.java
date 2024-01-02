package com.mijael.mein;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mijael.mein.DAO.DAO_Cines;
import com.mijael.mein.Entidades.Cines;
import com.mijael.mein.HELPER.CinesSQLiteHelper;
import com.mijael.mein.SINCRONIZACION.Cines_SyncWorker;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class SegundoFragment extends Fragment {
    AppCompatButton btn_abrirCamara;
    ImageView imageView;
    View rootView;
    List<Cines> listacines;
    LinearLayout linearLayout;
    public SegundoFragment() {
        // Required empty public constructor
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
        rootView = inflater.inflate(R.layout.fragment_segundo,container,false);
        linearLayout = rootView.findViewById(R.id.contenedor_Card);
        init(rootView);
        btn_abrirCamara.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {AbrirCamara();}});

        CinesSQLiteHelper cinesSQLiteHelper = CinesSQLiteHelper.getInstance(getActivity());
        DAO_Cines dao_cines = new DAO_Cines(getActivity());
        listacines = dao_cines.ListarCines();
        for (Cines registro:listacines) {
            View cardViewLayout = inflater.inflate(R.layout.card_alumno, null);

            TextView txtrazonsocial = cardViewLayout.findViewById(R.id.txtrazonsocial);
            TextView txtTelefono = cardViewLayout.findViewById(R.id.txtusuario);
            TextView txtdetalle = cardViewLayout.findViewById(R.id.txtnivel);

            // Aquí encuentras tus TextView dentro del CardView y los llenas con los datos del registro

            txtrazonsocial.setText(registro.getRazonSocial());
            txtTelefono.setText(registro.getTelefonos());
            txtdetalle.setText(registro.getDetalle());

            linearLayout.addView(cardViewLayout);
        }

        return rootView;
    }

    private void AbrirCamara() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getActivity().getPackageManager())!=null){
            startActivityForResult(intent,1);
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap img_bitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(img_bitmap);
        }
    }

    private void init(View view){
        btn_abrirCamara = view.findViewById(R.id.btn_camara);
        imageView = view.findViewById(R.id.mi_imagen);
    }

}