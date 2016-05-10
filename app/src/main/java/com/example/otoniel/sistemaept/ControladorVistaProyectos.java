package com.example.otoniel.sistemaept;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * Created by Otoniel on 06/03/2016.
 */
public class ControladorVistaProyectos extends Activity {
    TextView nombreProyecto;
    TextView descripcion;
    TextView monto;
    TextView avanceFisico;
    TextView avanceFinanciero;
    TextView periodo;

    Proyectos proyectos;

    DecimalFormat format = new DecimalFormat("###,###,###.##");

    @Override
    public void onCreate(Bundle salvar) {
        super.onCreate(salvar);
        setContentView(R.layout.vista_proyectos);

        Bundle extras = getIntent().getExtras();

        Log.v("Hilo de Prueba", format.format(100000.00));

        proyectos = (Proyectos) extras.getSerializable("proyecto");

        nombreProyecto = (TextView) findViewById(R.id.nombre_proyecto);
        nombreProyecto.setText(proyectos.getNombreProyecto());
        descripcion = (TextView) findViewById(R.id.descripcion);
        descripcion.setText(proyectos.getDescripcion());
        monto = (TextView) findViewById(R.id.monto);
        monto.setText(proyectos.getMonto() + " Bs.");
        avanceFisico = (TextView) findViewById(R.id.avanceFisico);
        avanceFisico.setText(proyectos.getAvanceFisico() + "%");
        avanceFinanciero = (TextView) findViewById(R.id.avance);
        avanceFinanciero.setText(proyectos.getAvanceFinanciero() + "%");
        periodo = (TextView) findViewById(R.id.periodo);
        if (proyectos.getPeriodo() == 1) {
            periodo.setText("2015");
        } else if (proyectos.getPeriodo() == 2) {
            periodo.setText("2016");
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
