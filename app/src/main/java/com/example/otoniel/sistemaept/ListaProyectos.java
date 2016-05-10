package com.example.otoniel.sistemaept;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Otoniel on 06/03/2016.
 */
public class ListaProyectos extends Activity {

    List<Proyectos> listaProyectos;

    @Override
    protected void onCreate(Bundle salvar) {
        super.onCreate(salvar);
        setContentView(R.layout.list_view_proyectos);

        Bundle graficas = getIntent().getExtras();

        listaProyectos = (List<Proyectos>) graficas.getSerializable("lista");

        final ListView listView = (ListView) findViewById(R.id.list_view);
        final Adaptador adaptador = new Adaptador(this, android.R.layout.simple_list_item_2, listaProyectos);

        listView.setAdapter(adaptador);

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
            monto.setText("" + keyvalue.getMonto());

            return convertView;
        }
    }
}
