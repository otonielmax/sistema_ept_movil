package com.example.otoniel.sistemaept;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Highlight;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Otoniel on 06/03/2016.
 */
public class GraficasEstatus extends Activity {
    int aprobado = 0;
    int diferido = 0;
    int negado = 0;

    List<Proyectos> aprobados;
    List<Proyectos> diferidos;
    List<Proyectos> negados;

    @Override
    protected void onCreate(Bundle salvar) {
        super.onCreate(salvar);
        setContentView(R.layout.graficas_estatus);

        Bundle extras = getIntent().getExtras();

        aprobado = extras.getInt("aprobados");
        diferido = extras.getInt("diferidos");
        negado = extras.getInt("negados");

        aprobados = (List<Proyectos>) extras.getSerializable("lAprobados");
        diferidos = (List<Proyectos>) extras.getSerializable("lDiferidos");
        negados = (List<Proyectos>) extras.getSerializable("lNegados");

        int tamano = extras.getInt("tamano");

        BarChart chart = (BarChart) findViewById(R.id.grafico_estatus);

        BarData data = new BarData(getXAxisValues(), getDataSet(aprobado, diferido, negado));
        data.setGroupSpace(0);

        chart.setScaleYEnabled(false);
        chart.setPinchZoom(true);
        chart.setDoubleTapToZoomEnabled(false);
        chart.setHighlightPerDragEnabled(true);
        chart.setDrawValueAboveBar(true);
        chart.setDrawBarShadow(true);
        chart.setDrawHighlightArrow(true);
        chart.setData(data);
        chart.setDescription("");
        chart.animateXY(2000, 2000);
        chart.getAxisLeft().setAxisMaxValue(tamano);
        chart.getAxisRight().setAxisMaxValue(tamano);
        chart.getXAxis().setSpaceBetweenLabels(4);

        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, int i, Highlight highlight) {
                if (entry == null) {
                    return;
                } else {
                    int indice = entry.getXIndex();
                    //final ListView listView = (ListView) findViewById(R.id.list_view);
                    //final Adaptador adaptador;
                    Intent listaProyectosEstatus = new Intent(GraficasEstatus.this, ListaProyectos.class);
                    if (indice == 0) {
                        listaProyectosEstatus.putExtra("lista",(Serializable) aprobados);
                    }
                    else if (indice == 1) {
                        listaProyectosEstatus.putExtra("lista", (Serializable) diferidos);
                    }
                    else if (indice == 2) {
                        listaProyectosEstatus.putExtra("lista", (Serializable) negados);
                    }

                    startActivity(listaProyectosEstatus);
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });

        chart.invalidate();
    }

    private ArrayList<BarDataSet> getDataSet(int aprobado, int diferido, int negado) {
        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> value1 = new ArrayList<>();
        BarEntry v1 = new BarEntry(aprobado, 0);
        value1.add(v1);

        ArrayList<BarEntry> value2 = new ArrayList<>();
        BarEntry v2 = new BarEntry(diferido, 0);
        value2.add(v2);

        ArrayList<BarEntry> value3 = new ArrayList<>();
        BarEntry v3 = new BarEntry(negado, 0);
        value3.add(v3);

        BarDataSet barDataSet1 = new BarDataSet(value1, "Aprobados");
        BarDataSet barDataSet2 = new BarDataSet(value2, "Diferidos");
        BarDataSet barDataSet3 = new BarDataSet(value3, "Negados");

        barDataSet1.setColor(Color.rgb(39, 174, 96));
        barDataSet2.setColor(Color.rgb(241, 196, 15));
        barDataSet3.setColor(Color.rgb(231, 76, 60));

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);
        dataSets.add(barDataSet3);

        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("Estatus de Proyectos");

        return xAxis;
    }

    private class Adaptador extends ArrayAdapter<Proyectos> {
        public Adaptador(Context context, int textViewResourceId, List<Proyectos> objetos) {
            super(context, textViewResourceId, objetos);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Proyectos keyvalue = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.vista_proyectos_estatus, parent, false);
            }

            TextView nombre = (TextView) convertView.findViewById(R.id.nombre_proyecto_estatus);
            TextView correlativo = (TextView) convertView.findViewById(R.id.correlativo);
            TextView monto = (TextView) convertView.findViewById(R.id.monto_proyecto_estatus);

            nombre.setText(keyvalue.getNombreProyecto());
            correlativo.setText(keyvalue.getCorrelativo());
            monto.setText(keyvalue.getMonto());

            return convertView;
        }
    }
}
