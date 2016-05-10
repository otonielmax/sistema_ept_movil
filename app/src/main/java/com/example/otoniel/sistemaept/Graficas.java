package com.example.otoniel.sistemaept;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Highlight;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Otoniel on 06/03/2016.
 */
public class Graficas extends Activity {
    int primerTramo;
    int segundoTramo;
    int tercerTramo;
    int cuartoTramo;
    String nombreEntidad;

    List<Proyectos> listaProyectos;

    List<Proyectos> list1 = new ArrayList<>();
    List<Proyectos> list2 = new ArrayList<>();
    List<Proyectos> list3 = new ArrayList<>();
    List<Proyectos> list4 = new ArrayList<>();

    int cont = 0;
    int comparar = 0;

    Button bLogout;
    TextView mensaje;

    @Override
    protected void onCreate(Bundle salvar) {
        super.onCreate(salvar);
        setContentView(R.layout.graficas);

        //layout = (LinearLayout) findViewById(R.id.layoutGraficas);

        Bundle extras = getIntent().getExtras();

        primerTramo = extras.getInt("uno");
        segundoTramo = extras.getInt("dos");
        tercerTramo = extras.getInt("tres");
        cuartoTramo = extras.getInt("cuatro");
        nombreEntidad = extras.getString("nombreEntidad");

        listaProyectos = (List<Proyectos>) extras.getSerializable("lista");

        for (int i = 0; i < listaProyectos.size(); i++) {
            Proyectos nuevo = new Proyectos();
            nuevo = (Proyectos) listaProyectos.get(i);

            if (nuevo.getAvanceFinanciero() >= 0 && nuevo.getAvanceFinanciero() <= 25) {
                list1.add(nuevo);
            }
            else if (nuevo.getAvanceFinanciero() > 25 && nuevo.getAvanceFinanciero() <= 50) {
                list2.add(nuevo);
            }
            else if (nuevo.getAvanceFinanciero() > 50 && nuevo.getAvanceFinanciero() <= 75) {
                list3.add(nuevo);
            }
            else if (nuevo.getAvanceFinanciero() > 75 && nuevo.getAvanceFinanciero() <= 100) {
                list4.add(nuevo);
            }
            Log.v("Graficas", "Index = " + i);
        }
        /*
        Proyectos prueba = new Proyectos();
        prueba = (Proyectos) listaProyectos.get(0);
        Log.v("Graficas", "Lista: " + prueba.getNombreProyecto());
        */
        ArrayList<Entry> lista = new ArrayList<>();

        lista.add(new Entry((int) primerTramo, 0));
        lista.add(new Entry((int) segundoTramo, 1));
        lista.add(new Entry((int) tercerTramo, 2));
        lista.add(new Entry((int) cuartoTramo, 3));

        PieDataSet torta = new PieDataSet(lista, "Porcentajes");
        torta.setSliceSpace(3);
        torta.setSelectionShift(5);

        final int colors[] = {
                //Color.rgb(182, 109, 156), Color.rgb(182, 79, 126), Color.rgb(182, 49, 96), Color.rgb(182, 19, 66)
                Color.rgb(243, 156, 18), Color.rgb(41, 128, 185), Color.rgb(142, 68, 173), Color.rgb(46, 204, 113)
        };

        torta.setColors(colors);

        final ArrayList<String> etiqutas = new ArrayList<>();

        etiqutas.add("0% - 25%");
        etiqutas.add("26% - 50%");
        etiqutas.add("51% - 75%");
        etiqutas.add("76% - 100%");

        final PieChart torta2 = (PieChart) findViewById(R.id.torta);

        //Texto central
        torta2.setDrawCenterText(true);
        torta2.setCenterText("Bienvenido al Sistema");
        torta2.setCenterTextSize(15.0f);

        //Descripcion de la grafica
        torta2.setDescription("Avance Financiero de los Proyectos");

        //Texto en los slice
        torta2.setDrawSliceText(false);
        torta2.setUsePercentValues(false);

        //Detalles esteticos
        torta2.setDrawHoleEnabled(true);
        torta2.setHoleColorTransparent(true);
        torta2.setHoleRadius(60.0f);
        torta2.setRotationAngle(0);
        torta2.setRotationEnabled(true);
        torta2.setTransparentCircleRadius(10);


        //Mensaje cuando halla problemas con el servidor
        torta2.setNoDataText("Parece haber un problemas con el servidor, por favor comuniquese con el personal de soporte");

        torta2.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, int i, Highlight highlight) {
                if (entry == null)
                    return;
                else {
                    //Log.v("Graficas", "Valor del get entry index = " + entry.getXIndex());
                    //Log.v("Graficas", "Valor del get entry val = " + entry.getVal());

                    int index = entry.getXIndex();
                    final int num = (int) entry.getVal();

                    if (num == 1) {
                        torta2.setCenterText(num + " Proyecto");
                        if (num == comparar) {
                            cont++;
                        }
                        else {
                            cont = 0;
                        }
                    }
                    else {
                        torta2.setCenterText(num + " Proyectos");
                        if (num == comparar) {
                            cont++;
                        }
                        else {
                            cont = 0;
                        }
                    }
                    torta2.setCenterTextColor(colors[index]);
                    torta2.setCenterTextSize(25.0f);

                    if (cont == 0) {

                        Intent list = new Intent(Graficas.this, GraficosBarChartAvance.class);

                        cont = 0;
                        /*
                        final ListView listView = (ListView) findViewById(R.id.list_view);

                        listView.setAdapter(new AdaptadorListas(Graficas.this, R.layout.vista_proyectos, (ArrayList<?>) listaProyectos) {
                            @Override
                            public void onEntrada(Object entrada, View view) {
                                if (entrada != null) {

                                }
                            }
                        });

                        final Adaptador adaptador;
                        */
                        if (entry.getXIndex() == 0) {
                            list.putExtra("lista", (Serializable) list1);
                        }
                        else if (entry.getXIndex() == 1) {
                            list.putExtra("lista", (Serializable) list2);
                        }
                        else if (entry.getXIndex() == 2) {
                            list.putExtra("lista", (Serializable) list3);
                        }
                        else if (entry.getXIndex() == 3) {
                            list.putExtra("lista", (Serializable) list4);
                        }

                        startActivity(list);

                    } else if (cont == 0) {
                        Toast.makeText(Graficas.this, "Presione seguidamente el pedazo que desee desglozar", Toast.LENGTH_LONG);
                    }

                    comparar = num;

                }
                //Toast.makeText(Graficas.this,"Proyectos en " + etiqutas.get(entry.getXIndex()) + " = " + entry.getVal() + "%", Toast.LENGTH_SHORT);
            }

            @Override
            public void onNothingSelected() {
                Toast.makeText(Graficas.this, "Selecciona un segemento para ver cuantos proyectos tienes", Toast.LENGTH_SHORT);
            }
        });

        PieData tortaData = new PieData(etiqutas, torta);

        Legend l = torta2.getLegend();
        l.setPosition(Legend.LegendPosition.LEFT_OF_CHART_INSIDE);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);

        torta2.setData(tortaData);

        torta2.highlightValues(null);

        torta2.invalidate();

        mensaje = (TextView) findViewById(R.id.mensaje);
        mensaje.setText(nombreEntidad);

        bLogout = (Button) findViewById(R.id.bLogout);
        bLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.bLogout:
                        startActivity(new Intent(getBaseContext(), MainActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                        finish();
                        break;
                }

            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v("Graficas", "Se destruye la actividad");
    }

    private class Adaptador extends ArrayAdapter<Proyectos> {
        public Adaptador(Context context, int textViewResourceId, List<Proyectos> objetos) {
            super(context, textViewResourceId, objetos);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Proyectos keyvalue = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.vista_proyectos, parent, false);
            }

            TextView nombre = (TextView) convertView.findViewById(R.id.nombre_proyecto);
            TextView descripcion = (TextView) convertView.findViewById(R.id.descripcion);
            TextView avance = (TextView) convertView.findViewById(R.id.avance);
            TextView monto = (TextView) convertView.findViewById(R.id.monto);

            nombre.setText(keyvalue.getNombreProyecto());
            descripcion.setText(keyvalue.getDescripcion());
            avance.setText(keyvalue.getAvanceFinanciero());
            monto.setText(keyvalue.getMonto());

            return convertView;
        }
    }
}
