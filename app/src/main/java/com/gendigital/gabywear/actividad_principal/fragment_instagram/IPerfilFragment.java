package com.gendigital.gabywear.actividad_principal.fragment_instagram;

import com.gendigital.gabywear.actividad_principal.modelo.PerfilInfo;

import java.util.ArrayList;

/**
 * Created by Gaby on 07/07/2016.
 */
public interface IPerfilFragment {

    //public void onResume();

    public void generarLayout();

    public PerfilAdapter crearAdaptador(ArrayList<PerfilInfo> listaPerfil);

    public void inicializarAdaptadorRV(PerfilAdapter adaptador);
}
