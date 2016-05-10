package com.example.otoniel.sistemaept;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

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
public class GraficosBarChartAvance extends Activity {
    List<Proyectos> proyectos;

    @Override
    protected void onCreate(Bundle salvar) {
        super.onCreate(salvar);
        setContentView(R.layout.graficas_bar_chart_avance);

        Bundle grafico = getIntent().getExtras();

        proyectos = (List<Proyectos>) grafico.getSerializable("lista");

        cargarDataGraficos();
    }

    private void cargarDataGraficos() {

        BarChart barChart = (BarChart) findViewById(R.id.graficoBarChartAvance);

        BarData data = new BarData(getXValores(), getDataSet());

        barChart.setData(data);
        barChart.setDescription("Estadisticas de Avance");
        barChart.animateXY(2000, 2000);
        barChart.setDoubleTapToZoomEnabled(false);
        //barChart.setDragEnabled(false);
        //barChart.setScaleXEnabled(true);
        //barChart.setHighlightPerDragEnabled(true);

        //barChart.setBorderWidth(10f);
        //barChart.setDrawHighlightArrow(true);
        barChart.setDrawValueAboveBar(true);
        barChart.setDrawBarShadow(false);

        barChart.setMaxVisibleValueCount(30);

        barChart.setPinchZoom(false);

        barChart.setDrawGridBackground(false);

        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, int i, Highlight highlight) {
                if (entry == null) {
                    return;
                } else {
                    int indice = entry.getXIndex();
                    Log.v("Graficas", "Valor del get " + entry.getXIndex());
                    Proyectos pro = new Proyectos();
                    pro = proyectos.get(indice);

                    Intent vistaProyecto = new Intent(GraficosBarChartAvance.this, ControladorVistaProyectos.class);

                    vistaProyecto.putExtra("proyecto",(Serializable) pro);
                    /*
                    vistaProyecto.putExtra("nombre", pro.getNombreProyecto());
                    vistaProyecto.putExtra("descripcion", pro.getDescripcion());
                    vistaProyecto.putExtra("monto", pro.getMonto());
                    vistaProyecto.putExtra("financiero", pro.getAvanceFinanciero());
                    vistaProyecto.putExtra("fisico", pro.getAvanceFisico());
                    vistaProyecto.putExtra("periodo", pro.getPeriodo());
                    */
                    startActivity(vistaProyecto);
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });

        barChart.invalidate();
    }

    private ArrayList<BarDataSet> getDataSet() {

        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> avance_financiero = new ArrayList<>();

        for (int i = 0; i < proyectos.size(); i++) {
            Proyectos proy = new Proyectos();
            proy = proyectos.get(i);

            BarEntry valor = new BarEntry(proy.getAvanceFinanciero(), i);

            avance_financiero.add(valor);

        }

        BarDataSet barDataSet = new BarDataSet(avance_financiero, "Avance Financiero");
        barDataSet.setColor(Color.rgb(255, 110, 64));

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet);

        return dataSets;

    }

    private ArrayList<String> getXValores() {
        ArrayList<String> cod_proyectos = new ArrayList<>();

        for (int i = 0; i < proyectos.size(); i++) {
            int x = i + 1;
            cod_proyectos.add(x + "");
        }

        return cod_proyectos;
    }
}
