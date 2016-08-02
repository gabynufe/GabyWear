package com.gendigital.gabywear.actividad_principal.fragment_instagram;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.gendigital.gabywear.actividad_principal.fragment_instagram.API_Instagram.API_Adapter;
import com.gendigital.gabywear.actividad_principal.modelo.CuentaInstagram;
import com.gendigital.gabywear.actividad_principal.modelo.PerfilInfo;
import com.gendigital.gabywear.actividad_principal.fragment_instagram.API_Instagram.API_Endpoints;
import com.gendigital.gabywear.actividad_principal.fragment_instagram.API_Instagram.API_model;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Gaby on 07/07/2016.
 */
public class PerfilPresentador implements IPerfilPresentador {

    private IPerfilFragment iPerfilFragment;
    private Context context;
    private ArrayList<PerfilInfo> listaPerfil;
    private static final String TAG = "CONEXION_INSTAGRAM";
    private CuentaInstagram cuentaActual;

    public PerfilPresentador(IPerfilFragment iPerfilFragment, Context context) {
        this.iPerfilFragment = iPerfilFragment;
        this.context = context;
        if (CuentaInstagram.actualIndice > -1) {
            obtenerListaInstagram();
        }
    }

    @Override
    public void obtenerListaInstagram() {
        API_Adapter apiAdapter = new API_Adapter();
        Gson gsonMediaRecent = apiAdapter.construyeGsonDeserializadorMediaRecent();
        API_Endpoints apiEndpoints = apiAdapter.establecerConexionApiInstagram(gsonMediaRecent);
        CuentaInstagram cuentaAct =  CuentaInstagram.getItem(CuentaInstagram.actualIndice);
        Log.v(TAG, "USERPERFIL:" + cuentaAct.getUserFullName());
        Call<API_model> apiModelCall = apiEndpoints.getRecentMedia(cuentaAct.getUserID());

        apiModelCall.enqueue(new Callback<API_model>() {
            @Override
            public void onResponse(Call<API_model> call, Response<API_model> response) {
                Log.v(TAG, "CONEXION API REALIZADA");
                API_model apiModel = response.body();
                listaPerfil = apiModel.getListaInstagram();
                mostrarListaRV();
            }

            @Override
            public void onFailure(Call<API_model> call, Throwable t) {
                Toast.makeText(context, "¡falló conexión! Intenta de nuevo", Toast.LENGTH_LONG).show();
                Log.e(TAG, "FALLO LA CONEXION API[" + t.toString() + "]");
            }
        });
    }


    @Override
    public void mostrarListaRV() {
        iPerfilFragment.inicializarAdaptadorRV(iPerfilFragment.crearAdaptador(listaPerfil));
        iPerfilFragment.generarLayout();
    }

}
