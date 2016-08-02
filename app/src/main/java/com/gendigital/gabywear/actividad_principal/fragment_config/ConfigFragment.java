package com.gendigital.gabywear.actividad_principal.fragment_config;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gendigital.gabywear.R;
import com.gendigital.gabywear.actividad_principal.MainActivity;
import com.gendigital.gabywear.actividad_principal.modelo.CuentaInstagram;

/**
 * Created by Gaby on 30/07/2016.
 */
public class ConfigFragment  extends Fragment implements IConfigFragment{

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private TextView ctaSeleccionada;
    private Button btnNext;
    private CuentaInstagram cuentaActual;
    private RadioButton[] radios = new RadioButton[4];
    private RadioGroup grupoRadio;
    private int iRadio;
    private String nRadio;
    private View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.activity_config_perfil, container, false);

        radios[0] = (RadioButton) v.findViewById(R.id.op1);
        radios[1] = (RadioButton) v.findViewById(R.id.op2);
        radios[2] = (RadioButton) v.findViewById(R.id.op3);
        radios[3] = (RadioButton) v.findViewById(R.id.op4);

        ctaSeleccionada=(TextView) v.findViewById(R.id.ctaSeleccionada);
        btnNext = (Button) v.findViewById(R.id.btn_next);

        for (int i=0; i < CuentaInstagram.listaCuentas.size(); i++) {
            cuentaActual = CuentaInstagram.getItem(i);
            radios[i].setText(cuentaActual.getUserFullName());

            radios[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View rb) {
                    iRadio = grupoRadio.indexOfChild(rb);
                    fueSeleccionado();
                }
            });

        }

        grupoRadio = (RadioGroup) v.findViewById(R.id.grupoRadios);

        // selecciona la cuenta actual
        iRadio = (CuentaInstagram.actualIndice > -1)? CuentaInstagram.actualIndice : CuentaInstagram.selectedIndice;
        radios[iRadio].setChecked(true);

        fueSeleccionado();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CuentaInstagram.selectedIndice = iRadio;
                Toast.makeText(getContext(), "Guardando usuario seleccionado", Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).ActualizaPerfil();
            }
        });

        return v;
    }

    @Override
    public void fueSeleccionado() {
        cuentaActual = CuentaInstagram.getItem(iRadio);
        ctaSeleccionada.setText(cuentaActual.getUserName());
    }

}
