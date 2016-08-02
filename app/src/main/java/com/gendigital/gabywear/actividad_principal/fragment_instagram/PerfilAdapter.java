package com.gendigital.gabywear.actividad_principal.fragment_instagram;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gendigital.gabywear.R;
import com.gendigital.gabywear.actividad_principal.fragment_instagram.API_Firebase.API_Google_Adapter;
import com.gendigital.gabywear.actividad_principal.fragment_instagram.API_Firebase.Google_Endpoint;
import com.gendigital.gabywear.actividad_principal.fragment_instagram.API_Instagram.API_Adapter;
import com.gendigital.gabywear.actividad_principal.fragment_instagram.API_Instagram.API_Config;
import com.gendigital.gabywear.actividad_principal.fragment_instagram.API_Instagram.API_Endpoints;
import com.gendigital.gabywear.actividad_principal.modelo.CuentaInstagram;
import com.gendigital.gabywear.actividad_principal.modelo.PerfilInfo;

import java.util.ArrayList;

import com.squareup.picasso.Picasso;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Gaby on 07/07/2016.
 */
public class PerfilAdapter extends RecyclerView.Adapter<PerfilAdapter.adaViewHolder> {

    private Activity activity;
    private ArrayList<PerfilInfo> perfilDataset;

    // Provide a suitable constructor (depends on the kind of dataset)
    public PerfilAdapter(ArrayList<PerfilInfo> myDataset, Activity activity) {
            this.perfilDataset = myDataset;
            this.activity = activity;
    }

    public static class adaViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgFoto;
        private TextView tvMeGusta;
        private ImageButton btnMegustaFoto;

        public adaViewHolder(View itemView) {
            super(itemView);
            imgFoto = (ImageView) itemView.findViewById(R.id.imgFoto);
            tvMeGusta = (TextView) itemView.findViewById(R.id.tvMeGusta);
            btnMegustaFoto = (ImageButton) itemView.findViewById(R.id.btnMegustaFoto);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PerfilAdapter.adaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View vw = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_perfil, parent, false);
        adaViewHolder vh = new adaViewHolder(vw);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final adaViewHolder vHolder, int position) {
        // - get element from your dataset at this position

        final PerfilInfo perfilInfo = perfilDataset.get(position);
        Picasso.with(activity)
                .load(perfilInfo.getUrlFoto())
                .placeholder(R.drawable.loro)
                .into(vHolder.imgFoto);
        //vHolder.imgFoto.setImageResource(perfilInfo.getFoto());
        vHolder.tvMeGusta.setText(Integer.toString(perfilInfo.getMeGusta()));
        if (perfilInfo.isLiked()) {
            vHolder.btnMegustaFoto.setEnabled(false);
            vHolder.btnMegustaFoto.setVisibility(View.INVISIBLE);
        }
        vHolder.btnMegustaFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perfilInfo.incrementaLikes();
                Toast.makeText(activity, "Diste like a " + perfilInfo.getIdPicture(), Toast.LENGTH_SHORT).show();
                vHolder.tvMeGusta.setText(Integer.toString(perfilInfo.getMeGusta()));
                vHolder.btnMegustaFoto.setVisibility(View.INVISIBLE);
                ActualizaLikesInstagram(perfilInfo.getIdPicture());
                ActualizaLikesFirebase(perfilInfo.getIdPicture());
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return perfilDataset.size();
    }

    public void ActualizaLikesInstagram (String idFoto) {

        // guarda likes en instagram
        API_Adapter restApiAdapter = new API_Adapter();
        API_Endpoints endpoints = restApiAdapter.establecerConexionApiInstagram2();
        Call<ResponseBody> instagramResponseCall = endpoints.registrarLikes(idFoto, API_Config.ACCESS_TOKEN2);

        instagramResponseCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody instagramResponse = response.body();
                String message = response.code() + ": " + response.message();
                Log.d("INSTAGRAM_code", Integer.toString(response.code()));
                Log.i("INSTAGRAM_message", message);
                Toast.makeText(activity, "GUARDANDO LIKES:" + call.toString(), Toast.LENGTH_LONG).show();            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Log.e("INSTAGRAM_CALL", "ERROR AL GUARDAR LIKES");
                Toast.makeText(activity, "ERROR AL GUARDAR LIKES",
                        Toast.LENGTH_LONG).show();
            }

        });
    }

    public void ActualizaLikesFirebase (String idFoto) {

        String usuarioDispositivo = CuentaInstagram.getItem(CuentaInstagram.DISPOSITIVO_ACTUAL).getUserFullName();


        CuentaInstagram userSeleccionado = CuentaInstagram.getItem(CuentaInstagram.actualIndice);

        // guarda like y envia notificacion al usuario
        API_Google_Adapter restApiAdapter = new API_Google_Adapter();
        Google_Endpoint endpoints = restApiAdapter.establecerConexionRestAPI();
        Call<ResponseBody> respuestaCall = endpoints.registrarLike(Integer.toString(
                CuentaInstagram.DISPOSITIVO_ACTUAL),
                usuarioDispositivo, userSeleccionado.getUserRegistro(), idFoto);

        respuestaCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody apiRespuesta = response.body();
                Log.d("FIREBASE_response", response.body().toString());
                Toast.makeText(activity, "idRegistro:" + response.body().toString(),
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("FIREBASE_ERROR", "ERROR AL GUARDAR LIKE");
                Toast.makeText(activity, "ERROR AL GUARDAR LIKE [" + CuentaInstagram.getItem(CuentaInstagram.actualIndice).getUserName(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

}
