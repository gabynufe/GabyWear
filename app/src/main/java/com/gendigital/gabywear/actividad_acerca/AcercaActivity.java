package com.gendigital.gabywear.actividad_acerca;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.gendigital.gabywear.R;

/**
 * Created by Gaby on 07/07/2016.
 */
public class AcercaActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca);
        Toolbar actionBar = (Toolbar) findViewById(R.id.barraSup);
        setSupportActionBar(actionBar);

        actionBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AcercaActivity.this, "Regresando", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}
