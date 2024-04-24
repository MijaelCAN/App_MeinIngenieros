package com.mijael.mein;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.content.pm.PackageManager;
import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;


import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;



public class LoginActivity extends AppCompatActivity {
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    Button btn_login, btn_update;
    ImageView btn_visible;
    EditText user, pass;
    RequestQueue requestQueue;
    CheckBox cbx_rememberMe;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Verificar y solicitar permisos de la cámara
        checkCameraPermission();
        //Stetho.initializeWithDefaults(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.generic_color)); // Reemplaza R.color.tu_color con tu color deseado
        }
        requestQueue = Volley.newRequestQueue(this);
        getSupportActionBar().hide();//OCULTA LA BARRA DE PROYECTO


        //---BOTON PARA INICIAR SESION---
        /*btn_login.setOnClickListener((view) -> {
            init();
            //NavController navController = Navigation.findNavController(this, R.id.fragmentContainerView);

            String nombre = user.getText().toString();
            String contrasena = pass.getText().toString();
            validarUusario validarUusario = new validarUusario(this);
            Usuario datos = validarUusario.Consultar(nombre, contrasena);
            if (datos != null) {
                Log.e("IDD:", String.valueOf(datos.getId()));
                String colabo = String.valueOf(datos.getId());
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("idCola",colabo);
                intent.putExtra("nombres", datos.getNombres());
                intent.putExtra("apater", datos.getApater());
                startActivity(intent);

                /*Orden_TrabajoFragment2 fragmento = new Orden_TrabajoFragment2();
                Bundle bundle = new Bundle();
                bundle.putString("idCola", colabo);
                Log.e("ESTRELLA",colabo);
                fragmento.setArguments(bundle);

                //PASAR PARAMETROS USSANDO SAFE ARGS
                /*Orden_TrabajoFragmentDirections.ActionOrdenTrabajoFragmentToOrdenTrabajoFragment2 accion =
                        Orden_TrabajoFragmentDirections.actionOrdenTrabajoFragmentToOrdenTrabajoFragment2(colabo);
                navController.navigate(accion);
            } else {
                Toast.makeText(getApplicationContext(), "Usuario no encontrado", Toast.LENGTH_LONG).show();
            }
        });*/

        /*
        //BOTON PARA ACTUALIZAR TABLA DE USUARIOS
        btn_update.setOnClickListener((view) -> {
            init();
            getApi_Usuarios api_datos = new getApi_Usuarios(this);//Instanciar la clase GETAPPI para utilizar los metodos y campos
            api_datos.getRegistros();//Tomar datos e Insertarlos en la tabla SQLite
            getApi_OrdenTrabajo ordenTrabajo = new getApi_OrdenTrabajo(this);
            ordenTrabajo.getRegistros();
            getApi_Formato_pTrabajo formatoPTrabajo = new getApi_Formato_pTrabajo(this);
            formatoPTrabajo.getRegistros();
            ToastUtils.showCustomToast(this, "Datos Actualizados", 5000);//Mostrar mensaje de Confirmacion de la actualizacion personzalida
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

        /*
        //BOTON RECORDAR CREDENCIALES
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean rememberMe = preferences.getBoolean("rememberMe", false);
        if (rememberMe) {
            user.setText(preferences.getString("username", ""));
            pass.setText(preferences.getString("password", ""));
            cbx_rememberMe.setChecked(true);
        }
        cbx_rememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                init();
                SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
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
        });*/
    }

    /*private void init() {
        btn_login = findViewById(R.id.login_button);
        btn_update = findViewById(R.id.btn_actualizar);
        //btn_visible = findViewById(R.id.password_toggle);//password_toggle = id del boton de visualizacion
        pass = findViewById(R.id.password);
        user = findViewById(R.id.username);
        cbx_rememberMe = findViewById(R.id.remember_me);
    }*/

    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA }, CAMERA_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso de cámara concedido, puedes continuar con la lógica de tu actividad aquí
            } else {
                // Permiso de cámara denegado, muestra un mensaje o toma alguna acción adicional
            }
        }
    }
}