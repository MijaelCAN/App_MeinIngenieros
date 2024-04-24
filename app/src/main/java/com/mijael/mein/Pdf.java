package com.mijael.mein;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class Pdf extends Fragment {
    String rutaPdf;
    PDFView pdfView;
    public Pdf() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (getArguments() != null) {
            rutaPdf = bundle.getString("rutaPdf");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_pdf, container, false);
        if (rutaPdf != null) {
            // Abrir y cargar el archivo PDF en la vista PDFView
            pdfView = rootView.findViewById(R.id.pdfView);
            pdfView.fromFile(new File(rutaPdf)).load();
            Log.e("ppp","Entro a PDF");
        }


        return rootView;
    }
}