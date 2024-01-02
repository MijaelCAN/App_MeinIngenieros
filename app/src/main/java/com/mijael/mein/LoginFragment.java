package com.mijael.mein;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.mijael.mein.DAO.DAO_Usuario;
import com.mijael.mein.Entidades.Usuario;
import com.mijael.mein.Extras.Validaciones;

import com.mijael.mein.HELPER.DatosLocalSQLiteHelper;
import com.mijael.mein.SINCRONIZACION.Equipos_SyncWorker;
import com.mijael.mein.SINCRONIZACION.Formatos_SyncWorker;
import com.mijael.mein.SINCRONIZACION.Ordenes_SyncWorker;
import com.mijael.mein.SINCRONIZACION.Usuarios_SyncWorker;


import java.util.concurrent.TimeUnit;


public class LoginFragment extends Fragment {

    Button btn_login, btn_update;
    ImageView btn_visible;
    EditText user, pass;
    RequestQueue requestQueue;
    CheckBox cbx_rememberMe;
    private static final int SYNC_INTERVAL_MIN = 15;
    private boolean isPasswordVisible = false;
    public LoginFragment() {
        // Required empty public constructor
    }
    Validaciones validar = new Validaciones();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        init(rootView);
        // Inflate the layout for this fragment
        requestQueue = Volley.newRequestQueue(getActivity());
        init(rootView);
        TareasProgramadas(Ordenes_SyncWorker.class, "Ordenes trabajo");
        TareasProgramadas(Usuarios_SyncWorker.class, "Usuarios");
        TareasProgramadas(Formatos_SyncWorker.class, "Formatos");
        TareasProgramadas(Equipos_SyncWorker.class, "Equipos");
        DatosLocalSQLiteHelper helperdatos = new DatosLocalSQLiteHelper(getActivity(), "TablasLocales",null,1);
        SQLiteDatabase db = helperdatos.getWritableDatabase();



        //---BOTON PARA INICIAR SESION---
        btn_login.setOnClickListener((view) -> {
            if(validar.validarCampoObligatorio(user) && validar.validarCampoObligatorio(pass) ){
                init(rootView);
                String nombre = user.getText().toString();
                String contrasena = pass.getText().toString();
                /*validarUusario validarUusario = new validarUusario(getActivity());
                Usuario datos = validarUusario.Consultar(nombre,contrasena);*/

                DAO_Usuario dao_usuario = new DAO_Usuario(getActivity());
                Usuario usuario = dao_usuario.BuscarUsuario(nombre,contrasena);
                if (usuario != null) {
                    Log.e("IDD:", String.valueOf(usuario.getId_usuario()));
                    String colabo = String.valueOf(usuario.getId_usuario());
                    //PASAR PARAMETRO DE UNA ACTIVIDAD A OTRA
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.putExtra("idCola",colabo);
                    intent.putExtra("nombres", usuario.getUsuario_nombres());
                    intent.putExtra("apater", usuario.getUsuario_apater());
                    startActivity(intent);

                } else {
                    Toast.makeText(getActivity(), "Usuario no encontrado", Toast.LENGTH_LONG).show();
                }
            }
        });

        //BOTON PARA ACTUALIZAR TABLAS DE BASE DE DATOS
        /*btn_update.setOnClickListener((view) -> {
            init(rootView);
            getApi_Usuarios api_usuarios = new getApi_Usuarios(getActivity());//Instanciar la clase GETAPPI para utilizar los metodos y campos DE TABLAS LOCALES
            api_usuarios.getRegistros();//Tomar datos e Insertarlos en la tabla SQLite
            getApi_OrdenTrabajo ordenTrabajo = new getApi_OrdenTrabajo(getActivity());
            ordenTrabajo.getRegistros();
            getApi_Formato_pTrabajo formatoPTrabajo = new getApi_Formato_pTrabajo(getActivity());
            formatoPTrabajo.getRegistros();
            getApi_Equipos api_equipos = new getApi_Equipos(getActivity());
            api_equipos.getRegistros();

            ToastUtils.showCustomToast(getActivity(), "Datos Actualizados", 5000);//Mostrar mensaje de Confirmacion de la actualizacion personzalida
            btn_update.setVisibility(view.INVISIBLE);
        });*/


        //BOTON PARA VISUALIZAR LA CONTRASEÑA
        /*btn_visible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPasswordVisible) {
                    // Si la contraseña es visible, ocultarla
                    pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    // Si la contraseña está oculta, mostrarla
                    pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                // Mover el cursor al final del texto
                pass.setSelection(pass.getText().length());
                isPasswordVisible = !isPasswordVisible;
            }
        });*/

        //BOTON RECORDAR CREDENCIALES
        SharedPreferences preferences = getActivity().getSharedPreferences("DatosdeSesion", Context.MODE_PRIVATE);
        boolean rememberMe = preferences.getBoolean("rememberMe", false);
        if (rememberMe) {
            user.setText(preferences.getString("username", ""));
            pass.setText(preferences.getString("password", ""));
            cbx_rememberMe.setChecked(true);
        }
        cbx_rememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                init(rootView);
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("DatosdeSesion", Context.MODE_PRIVATE).edit();
                if (isChecked) {
                    editor.putString("username", user.getText().toString());
                    editor.putString("password", pass.getText().toString());
                } else {
                    editor.remove("username");
                    editor.remove("password");
                }
                editor.putBoolean("rememberMe", isChecked);
                editor.apply();
            }
        });


        return rootView;
    }
    private void init(View rootView) {
        btn_login = rootView.findViewById(R.id.login_button);
        btn_update = rootView.findViewById(R.id.btn_actualizar);
        //btn_visible = rootView.findViewById(R.id.password_toggle);//password_toggle = id del boton de visualizacion
        pass = rootView.findViewById(R.id.password);
        user = rootView.findViewById(R.id.username);
        cbx_rememberMe = rootView.findViewById(R.id.remember_me);
    }
    public void TareasProgramadas(Class<? extends Worker> workerClass, String tag){
        Log.e("ENTRADA_SYNC","ENTRO A SINCRONIZAR " + tag);
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        PeriodicWorkRequest syncRequest = new PeriodicWorkRequest.Builder(
                workerClass, // Clase que extiende de Worker
                SYNC_INTERVAL_MIN, // Intervalo de sincronización en horas
                TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build();

        //WorkManager.getInstance(this).enqueue(syncRequest);
        WorkManager.getInstance(getActivity()).enqueueUniquePeriodicWork(
                tag,
                ExistingPeriodicWorkPolicy.KEEP,
                syncRequest);
    }
}