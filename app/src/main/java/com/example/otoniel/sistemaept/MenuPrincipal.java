package com.example.otoniel.sistemaept;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Otoniel on 06/03/2016.
 */
public class MenuPrincipal extends Activity {
    Button estatus;
    Button salir;
    Button avance;

    int primerTramo;
    int segundoTramo;
    int tercerTramo;
    int cuartoTramo;
    String nombreEntidad;

    // Lista que contendra todos los proyectos
    List<Proyectos> listaProyectos;

    // Listas con los datos de los proyectos para generar las graficas de estatus
    List<Proyectos> aprobadoEstatusLista;
    List<Proyectos> diferidoEstatusLista;
    List<Proyectos> negadoEstatusLista;

    // Datos adicionales para graficas de estatus

    @Override
    protected void onCreate(Bundle salvar) {
        super.onCreate(salvar);
        setContentView(R.layout.menu_principal);

        Bundle extras = getIntent().getExtras();

        primerTramo = extras.getInt("uno");
        segundoTramo = extras.getInt("dos");
        tercerTramo = extras.getInt("tres");
        cuartoTramo = extras.getInt("cuatro");
        nombreEntidad = extras.getString("nombreEntidad");

        listaProyectos = (List<Proyectos>) extras.getSerializable("lista");

        avance = (Button) findViewById(R.id.avance);
        estatus = (Button) findViewById(R.id.estatus);
        salir = (Button) findViewById(R.id.salir);

        avance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent avance = new Intent(MenuPrincipal.this, Graficas.class);

                avance.putExtra("nombreEntidad", nombreEntidad);
                avance.putExtra("uno", primerTramo);
                avance.putExtra("dos", segundoTramo);
                avance.putExtra("tres", tercerTramo);
                avance.putExtra("cuatro", cuartoTramo);
                avance.putExtra("lista", (Serializable) listaProyectos);

                startActivity(avance);
            }
        });

        estatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listasDeEstatus estatus = new listasDeEstatus();

                Log.v("Menu Principal", "Tamaño de la lista de proyectos" + listaProyectos.size());
                estatus.execute(0);
            }
        });

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.salir:
                        startActivity(new Intent(getBaseContext(), MainActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));

                        finish();
                        break;
                }

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private class listasDeEstatus extends AsyncTask<Integer, Integer, Integer> {
        //private List<Proyectos> list;
        private int aprobados;
        private int diferidos;
        private int negados;

        public listasDeEstatus() {
            this.aprobados = 0;
            this.diferidos = 0;
            this.negados = 0;
        }

        @Override
        protected Integer doInBackground(Integer...var) {

            aprobadoEstatusLista = new ArrayList<>();
            diferidoEstatusLista = new ArrayList<>();
            negadoEstatusLista = new ArrayList<>();

            for (int i = 0; i < listaProyectos.size(); i++) {
                Proyectos proyectos = new Proyectos();
                proyectos = listaProyectos.get(i);

                if (proyectos.getEstatus().equals("aprobado")) {
                    aprobadoEstatusLista.add(proyectos);
                    this.aprobados = this.aprobados + 1;
                }
                else if (proyectos.getEstatus().equals("diferido")) {
                    diferidoEstatusLista.add(proyectos);
                    diferidos = diferidos + 1;
                }
                else if (proyectos.getEstatus().equals("negado")) {
                    negadoEstatusLista.add(proyectos);
                    negados = negados + 1;
                }
            }

            return 0;
        }

        @Override
        protected void onProgressUpdate(Integer... progreso) {
            Log.v("Menu principal", "Avanzado");
        }

        @Override
        protected void onPostExecute(Integer num) {
            if (num == 0) {
                Intent estatus = new Intent(MenuPrincipal.this, GraficasEstatus.class);

                estatus.putExtra("aprobados", aprobados);
                estatus.putExtra("lAprobados", (Serializable) aprobadoEstatusLista);
                estatus.putExtra("diferidos", diferidos);
                estatus.putExtra("lDiferidos", (Serializable) diferidoEstatusLista);
                estatus.putExtra("negados", negados);
                estatus.putExtra("lNegados", (Serializable) negadoEstatusLista);
                estatus.putExtra("tamano", listaProyectos.size());

                startActivity(estatus);
            }

        }
    }

}
