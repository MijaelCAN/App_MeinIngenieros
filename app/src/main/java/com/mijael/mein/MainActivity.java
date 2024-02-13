package com.mijael.mein;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.mijael.mein.Extras.OnBackPressedListener;
import com.mijael.mein.SINCRONIZACION.Cines_SyncWorker;
import com.mijael.mein.Utilidades.Util_OrdenTrabajo;
import com.mijael.mein.getSQLite.Obtener_OrdenesTrabajo;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements OnBackPressedListener {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerList;
    String user, password, nombres,apater,amater,codigo;
    TextView tv_usuario, tv_popupUsua;
    RequestQueue requestQueue;
    EditText txt_buscarOrden;
    String id;
    View nombre;
    private String lastFragmentClassName = "";
    private static final int SYNC_INTERVAL_HOURS = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Cambiar el color de la barra de estado
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.white)); // Reemplaza R.color.tu_color con tu color deseado
        }
        requestQueue = Volley.newRequestQueue(this);
        getSupportActionBar().hide();//OCULTA LA BARRA DE PROYECTO
        init();

        Intent intent = getIntent();
        id = intent.getStringExtra("idCola");
        apater = intent.getStringExtra("apater");
        nombres = intent.getStringExtra("nombres");
        //Log.e("SOL",id);
        tv_usuario.setText("Hi , " + nombres +" "+ apater);
        tv_usuario.setVisibility(View.VISIBLE);

        txt_buscarOrden.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().toLowerCase().trim();
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);

                if (currentFragment instanceof Orden_TrabajoFragment2) {
                    Orden_TrabajoFragment2 fragment = (Orden_TrabajoFragment2) currentFragment;
                    fragment.filtrarDesdeActivity(query);
                } else if (currentFragment instanceof FormatosFragment) {
                    FormatosFragment fragment = (FormatosFragment) currentFragment;
                    fragment.filtrarDesdeActivity(query);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tv_popupUsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(new ContextThemeWrapper(MainActivity.this,R.style.MenuTheme),v);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        String itemName = getResources().getResourceEntryName(item.getItemId());
                        switch (itemName) {
                            case "menu_perfil":
                                // Lógica para mostrar el perfil del usuario
                                return true;
                            case "menu_configuracion":
                                scheduleSyncTask();
                                SegundoFragment fragment = new SegundoFragment();
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.fragmentContainerView, fragment)
                                        .commit();
                                // Lógica para abrir la configuración
                                return true;
                            case "menu_cerrar_sesion"://CERRAR SESION
                                // BORRAR LOS DATOS DE SESION Y REDIRIGIR AL USUARIO A LA PANRALLA DE INICIO DE SESION
                                SharedPreferences sharedPreferences = getSharedPreferences("DatosdeSesion", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.clear();
                                editor.apply();
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish(); // Cierra la actividad actual para evitar que el usuario regrese a la pantalla anterior presionando el botón "Atrás"
                                return true;
                            default:
                                return false;
                        }
                    }
                });

                popup.show();
            }
        });

        Orden_TrabajoFragment2 fragment = new Orden_TrabajoFragment2();
        Bundle bundle = new Bundle();
        bundle.putString("idCola", id);
        //Log.e("ESTRELLA",id);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .commit();

    }
    private void init(){
        tv_usuario = findViewById(R.id.txt_usuario);
        txt_buscarOrden = findViewById(R.id.txt_buscarOrden);
        tv_popupUsua = findViewById(R.id.txt_usua);
        nombre = findViewById(R.id.menu_perfil);
    }
    private void scheduleSyncTask() {
        Log.e("ENTRADA1","ENTRO A SyncTag");
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        PeriodicWorkRequest syncRequest = new PeriodicWorkRequest.Builder(
                Cines_SyncWorker.class, // Clase que extiende de Worker
                SYNC_INTERVAL_HOURS, // Intervalo de sincronización en horas
                TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build();

        //WorkManager.getInstance(this).enqueue(syncRequest);
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                "cinesSyncTag",
                ExistingPeriodicWorkPolicy.KEEP,
                syncRequest);
    }

}