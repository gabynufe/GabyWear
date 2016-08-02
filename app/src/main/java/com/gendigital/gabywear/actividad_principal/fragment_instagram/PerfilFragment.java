package com.gendigital.gabywear.actividad_principal.fragment_instagram;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gendigital.gabywear.R;
import com.gendigital.gabywear.actividad_principal.modelo.CuentaInstagram;
import com.gendigital.gabywear.actividad_principal.modelo.PerfilInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Gaby on 07/07/2016.
 */
public class PerfilFragment extends Fragment implements IPerfilFragment {
    private RecyclerView rvLista;
    private ImageView imgPerfil1, imgPerfil2;
    private TextView tvNombrePerfil1, tvNombrePerfil2;
    private IPerfilPresentador iPresentador;
    private Activity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_perfil, container, false);
        activity = getActivity();

        rvLista = (RecyclerView) v.findViewById(R.id.rvListaPerfil);

        imgPerfil1 = (ImageView) v.findViewById(R.id.imgPerfil1);
        tvNombrePerfil1 = (TextView) v.findViewById(R.id.tvNombrePerfil1);

        imgPerfil2 = (ImageView) v.findViewById(R.id.imgPerfil2);
        tvNombrePerfil2 = (TextView) v.findViewById(R.id.tvNombrePerfil2);

        iPresentador = new PerfilPresentador(this, getContext());
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (CuentaInstagram.actualIndice != CuentaInstagram.selectedIndice) {
            // refrescar fragment
            Log.d("PERFILFRAGMENT", "Cuenta seleccionada cambio: refrescando fragment");
            CuentaInstagram.actualIndice = CuentaInstagram.selectedIndice;
            iPresentador = new PerfilPresentador(this, getContext());
        }
    }

    @Override
    public void generarLayout() {
        GridLayoutManager glm = new GridLayoutManager(getActivity(),2);
        glm.setOrientation(GridLayoutManager.VERTICAL);
        rvLista.setLayoutManager(glm);

        CuentaInstagram usuarioDispositivo = CuentaInstagram.getItem(CuentaInstagram.DISPOSITIVO_ACTUAL);
        Picasso.with(activity)
                .load(usuarioDispositivo.getUserPicture())
                .placeholder(R.drawable.perro)
                .into(imgPerfil1);
        tvNombrePerfil1.setText(usuarioDispositivo.getUserFullName());

        Picasso.with(activity)
                .load(PerfilInfo.userPicture)
                .placeholder(R.drawable.gato)
                .into(imgPerfil2);
        tvNombrePerfil2.setText(PerfilInfo.userFullName);
    }

    @Override
    public PerfilAdapter crearAdaptador(ArrayList<PerfilInfo> listaPerfil) {
        PerfilAdapter adaptador = new PerfilAdapter(listaPerfil, getActivity()  );
        return adaptador;
    }

    @Override
    public void inicializarAdaptadorRV(PerfilAdapter adaptador) {
        rvLista.setAdapter(adaptador);
    }
}
