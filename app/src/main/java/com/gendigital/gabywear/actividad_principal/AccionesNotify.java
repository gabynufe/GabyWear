package com.gendigital.gabywear.actividad_principal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.gendigital.gabywear.actividad_principal.fragment_instagram.API_Instagram.API_Adapter;
import com.gendigital.gabywear.actividad_principal.fragment_instagram.API_Instagram.API_Config;
import com.gendigital.gabywear.actividad_principal.fragment_instagram.API_Instagram.API_Endpoints;
import com.gendigital.gabywear.actividad_principal.modelo.CuentaInstagram;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Gaby on 31/07/2016.
 */
public class AccionesNotify extends BroadcastReceiver {

    private static int usuarioReceptor;
    private static int usuarioEmisor;
    private Context context;


    @Override
    public void onReceive(Context context, Intent intent) {
        String accion = intent.getAction();
        this.context = context;

        Bundle extras = intent.getExtras();

        if( extras != null ) {
            usuarioEmisor = extras.getInt(Config_Acciones.EMISOR);
            usuarioReceptor = extras.getInt(Config_Acciones.RECEPTOR);
            Toast.makeText(context, "Parametros emisor:" + usuarioEmisor + "  receptor: " + usuarioReceptor, Toast.LENGTH_SHORT).show();
        } else {
            usuarioEmisor = (CuentaInstagram.DISPOSITIVO_ACTUAL == 0)? 3 : 0;
            usuarioReceptor = CuentaInstagram.DISPOSITIVO_ACTUAL;
        }

        if (Config_Acciones.ACTION_KEY_SEGUIR.equals(accion)){
            seguirUsuario();
            Toast.makeText(context, "Estas siguiendo a " + usuarioEmisor, Toast.LENGTH_SHORT).show();
        }

        if (Config_Acciones.ACTION_KEY_VER.equals(accion)){
            verUsuario();
            Toast.makeText(context, "Viendo a " + usuarioEmisor, Toast.LENGTH_SHORT).show();
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
                CuentaInstagram.getItem(usuarioEmisor).getUserID(),
                API_Config.ACCESS_TOKEN2, "follow");

        instagramResponseCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody instagramResponse = response.body();
                String message = response.code() + ": " + response.message();
                Log.d("INSTAGRAM_code", Integer.toString(response.code()));
                Log.i("INSTAGRAM_message", message);
                Toast.makeText(context, Config_Acciones.ACTION_KEY_SEGUIR + call.toString(), Toast.LENGTH_LONG).show();            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Log.e("INSTAGRAM_CALL", "ERROR AL " + Config_Acciones.ACTION_KEY_SEGUIR);
                Toast.makeText(context, "ERROR AL " + Config_Acciones.ACTION_KEY_SEGUIR,
                        Toast.LENGTH_LONG).show();
            }

        });

    }

    public void verUsuario(){
        Log.d(Config_Acciones.ACTION_KEY_VER, "true");
        CuentaInstagram.selectedIndice = usuarioEmisor;
        //MainActivity.ActualizaPerfil();
    }

    public void miPerfil(){
        Log.d(Config_Acciones.ACTION_KEY_YO, "true");
        CuentaInstagram.selectedIndice = usuarioReceptor;
    }
}