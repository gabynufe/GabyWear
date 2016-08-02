package com.gendigital.gabywear.actividad_principal;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.gendigital.gabywear.actividad_principal.fragment_config.ConfigFragment;
import com.gendigital.gabywear.actividad_principal.fragment_instagram.API_Firebase.API_Respuesta;
import com.gendigital.gabywear.actividad_principal.fragment_instagram.API_Firebase.Google_Endpoint;
import com.gendigital.gabywear.actividad_principal.fragment_instagram.API_Firebase.API_Google_Adapter;
import com.gendigital.gabywear.R;
import com.gendigital.gabywear.actividad_acerca.AcercaActivity;
import com.gendigital.gabywear.actividad_contacto.ContactoActivity;
import com.gendigital.gabywear.actividad_principal.fragment_instagram.API_Instagram.API_Adapter;
import com.gendigital.gabywear.actividad_principal.fragment_instagram.API_Instagram.API_Config;
import com.gendigital.gabywear.actividad_principal.fragment_instagram.API_Instagram.API_Endpoints;
import com.gendigital.gabywear.actividad_principal.modelo.CuentaInstagram;
import com.gendigital.gabywear.actividad_principal.fragment_instagram.PerfilFragment;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{

    private static final String KEY_EXTRA_NAME = "name";
    private static final String TAG = "FIREBASE_TOKEN";
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (CuentaInstagram.listaCuentas.size() == 0) {
            LlenaCuentas ();
        }

        obtieneGuardaTokenDispositivo();

        toolbar = (Toolbar) findViewById(R.id.barraSup);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        Log.e("MainActivity", "onCreate");
        setUpViewPager();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(001);

    }


    @Override
    public void onResume() {
        super.onResume();
        Fragment fragment = ((PagerAdaptador) viewPager.getAdapter()).getFragment(1);
        if (fragment != null) {
            fragment.onResume();
        }
    }

    private void setUpViewPager(){
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new ConfigFragment());
        fragments.add(new PerfilFragment());

        viewPager.setAdapter(new PagerAdaptador(getSupportFragmentManager(), fragments));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_photos);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mContacto:
                Intent ventanaContacto = new Intent(this, ContactoActivity.class);
                startActivity(ventanaContacto);
                break;
            case R.id.mAcerca:
                Intent ventanaAcerca = new Intent(this, AcercaActivity.class);
                startActivity(ventanaAcerca);
                break;
        }
        return true;
    }

    public void obtieneGuardaTokenDispositivo () {
        String usuarioDispositivo = CuentaInstagram.getItem(CuentaInstagram.DISPOSITIVO_ACTUAL).getUserFullName();

        // obtiene token del dispositivo
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "InstanceID token: " + token);
        //Log.d(TAG, "user selected: " + CuentaInstagram.getItem(CuentaInstagram.actualIndice).getUserName());

        // guarda token del dispositivo y cuenta de instagram seleccionada en configuración
        API_Google_Adapter restApiAdapter = new API_Google_Adapter();
        Google_Endpoint endponits = restApiAdapter.establecerConexionRestAPI();
        Call<API_Respuesta> respuestaCall = endponits.registrarTokenID(token, usuarioDispositivo, Integer.toString(CuentaInstagram.DISPOSITIVO_ACTUAL));

        respuestaCall.enqueue(new Callback<API_Respuesta>() {
            @Override
            public void onResponse(Call<API_Respuesta> call, Response<API_Respuesta> response) {
                API_Respuesta apiRespuesta = response.body();
                Log.d("FIREBASE_response", response.body().toString());
                Log.d("FIREBASE_idRegistro", apiRespuesta.getIdRegistro());
                //Log.d("FIREBASE_idDispositivo", apiRespuesta.getIdDispositivo());
                //Log.d("FIREBASE_idUsuario", apiRespuesta.getIdUsuario());
                Toast.makeText(getApplicationContext(), "idRegistro:" + apiRespuesta.getIdRegistro(),
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<API_Respuesta> call, Throwable t) {
                Log.e("FIREBASE_ERROR", "ERROR AL GUARDAR TOKEN");
                Toast.makeText(getApplicationContext(), "ERROR AL GUARDAR TOKEN [" + CuentaInstagram.getItem(CuentaInstagram.actualIndice).getUserName(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void LlenaCuentas () {

        CuentaInstagram.actualIndice = -1;
        CuentaInstagram.selectedIndice = 0; // indice del usuario del dispositivo actual

        CuentaInstagram cuentaActual = new CuentaInstagram("3470121575", "gaby_petfly", "Gaby Nuñez",
                "https://scontent.cdninstagram.com/t51.2885-19/s150x150/13557166_1637974566521422_1967247359_a.jpg",
                "-KO2R8C9GlfDkLICkrmR"); // notifica a "AndroidGaby EmuladorPC"
        cuentaActual = new CuentaInstagram("3502625180", "rodaghero7102", "Rod Aghero",
                "https://scontent.cdninstagram.com/t51.2885-19/s150x150/13549451_1924392194454213_640488733_a.jpg",
                "-KO2R8C9GlfDkLICkrmR"); // notifica a "Moto XT1058"
        cuentaActual = new CuentaInstagram("1367162880", "flyn139566", "Isaac",
                "https://scontent.cdninstagram.com/t51.2885-19/s150x150/13534406_1048087365282770_1025401511_a.jpg",
                "-KO2AquXzOhNj5G5LLKQ");// notifica a "Moto XT1058"
        cuentaActual = new CuentaInstagram("3453931761", "perritinsta", "Perrito",
                "https://scontent.cdninstagram.com/t51.2885-19/13473368_1137847402953694_2092432719_a.jpg",
                "-KO2AquXzOhNj5G5LLKQ"); // notifica a "Moto XT1058"

        CuentaInstagram.DISPOSITIVO_ACTUAL =  CuentaInstagram.selectedIndice;
        //CuentaInstagram.DISPOSITIVO_ACTUAL =  "AndroidGaby EmuladorPC"; // "Moto XT1058"; //
        //Log.v(TAG, "DATOS INICIALIZADOS");
    }

    public void ActualizaPerfil() {
        // The user selected the headline of an article from the HeadlinesFragment
        // Do something here to display that article
        Toast.makeText(getApplicationContext(), "actualizando fragment", Toast.LENGTH_LONG).show();
        Fragment fragment = ((PagerAdaptador) viewPager.getAdapter()).getFragment(1);
        if (fragment != null) {
            fragment.onResume();
            viewPager.setCurrentItem(1);
        }
    }

    @Override
    public void onNewIntent(Intent intent){
        Toast.makeText(getApplicationContext(), "ENTRANDO POR INTENT",
                Toast.LENGTH_LONG).show();
        String accion = intent.getAction();
        Context context = getApplicationContext();

        if (Config_Acciones.ACTION_KEY_SEGUIR.equals(accion)){
            seguirUsuario();
            Toast.makeText(context, "Estas siguiendo a " + CuentaInstagram.EMISOR, Toast.LENGTH_SHORT).show();
        }

        if (Config_Acciones.ACTION_KEY_VER.equals(accion)){
            verUsuario();
            Toast.makeText(context, "Viendo a " + CuentaInstagram.EMISOR, Toast.LENGTH_SHORT).show();
        }

        if (Config_Acciones.ACTION_KEY_YO.equals(accion)){
            miPerfil();
            Toast.makeText(context, "Viendo mi perfil", Toast.LENGTH_SHORT).show();
        }
    }


    public void seguirUsuario(){
        Log.d(Config_Acciones.ACTION_KEY_SEGUIR, "true");

        API_Adapter restApiAdapter = new API_Adapter();
        API_Endpoints endpoints = restApiAdapter.establecerConexionApiInstagram2();
        Call<ResponseBody> instagramResponseCall = endpoints.seguirUsuario(
                CuentaInstagram.getItem(CuentaInstagram.EMISOR).getUserID(),
                API_Config.ACCESS_TOKEN2, "follow");

        instagramResponseCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody instagramResponse = response.body();
                String message = response.code() + ": " + response.message();
                Log.d("INSTAGRAM_code", Integer.toString(response.code()));
                Log.i("INSTAGRAM_message", message);
                Toast.makeText(getApplicationContext(), Config_Acciones.ACTION_KEY_SEGUIR + call.toString(), Toast.LENGTH_LONG).show();            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Log.e("INSTAGRAM_CALL", "ERROR AL " + Config_Acciones.ACTION_KEY_SEGUIR);
                Toast.makeText(getApplicationContext(), "ERROR AL " + Config_Acciones.ACTION_KEY_SEGUIR,
                        Toast.LENGTH_LONG).show();
            }

        });
        ActualizaPerfil();

    }

    public void verUsuario(){
        Log.d(Config_Acciones.ACTION_KEY_VER, "true");
        CuentaInstagram.selectedIndice = CuentaInstagram.EMISOR;
        ActualizaPerfil();
    }

    public void miPerfil(){
        Log.d(Config_Acciones.ACTION_KEY_YO, "true");
        CuentaInstagram.selectedIndice = CuentaInstagram.RECEPTOR;
        ActualizaPerfil();
    }
}
