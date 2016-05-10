package com.example.otoniel.sistemaept;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.gms.appindexing.Action;
//import com.google.android.gms.appindexing.AppIndex;
//import com.google.android.gms.common.api.GoogleApiClient;

import java.io.Serializable;
import java.net.URL;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

import java.io.IOException;

import de.timroes.axmlrpc.XMLRPCClient;

import helma.xmlrpc.*;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

/**
 * Created by Otoniel on 06/03/2016.
 */
public class MainActivity extends Activity {
    //Session odooSession = new Session("172.16.52.150", 8079, "produccion_ept", "cfgadmin", "");

    Button boton_login;
    EditText usuario, password;
    ProgressDialog dialog;
    List<Proyectos> lista;
    Entidad entidad;
    //Proyectos proyectos = new Proyectos();

    TextView textView;

    //private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuario = (EditText) findViewById(R.id.str_usuario);
        password = (EditText) findViewById(R.id.str_password);

        textView = (TextView) findViewById(R.id.header);

        boton_login = (Button) findViewById(R.id.login_boton);

        boton_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usuario.getText().length() > 0 && password.getText().length() > 0) {
                    dialog = new ProgressDialog(MainActivity.this);
                    dialog.setMessage("Iniciando...");
                    WebService hilo = new WebService(usuario.getText().toString(), password.getText().toString(), dialog);
                    Vector parametros = new Vector();

                    hilo.execute(parametros);
                } else {
                    Toast.makeText(getApplicationContext(), "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private class WebService extends AsyncTask<Vector, Integer, Integer> {
        private ProgressDialog dialog;
        private XMLRPCClient client;
        private String usuario, password;

        public WebService(String usuario, String password, ProgressDialog dialog) {
            this.usuario = usuario;
            this.password = password;
            this.dialog = dialog;
        }

        @Override
        protected void onPreExecute() {
            Log.v("Hilo Secundario", "Iniciando Sesion");
            dialog.show();
        }

        @Override
        protected Integer doInBackground(Vector... var) {
            Log.v("Hilo Secundario", "Antes de Ejecutar la consulta");
            try {
                //client = new XMLRPCClient(new URL("http://10.0.2.2:4142"));
                client = new XMLRPCClient(new URL("http://190.153.27.98:4142"));
                Log.v("Hilo Secundario", "Buscando al usuario: " + usuario);

                JSONParser parser = new JSONParser();
                String result = (String) client.call("ValidarUsuario", usuario, password);

                if (result != null) {
                    try {
                        entidad = new Entidad();
                        lista = new ArrayList<>();

                        Object obj = parser.parse(result);
                        JSONObject array = (JSONObject) obj;

                        Long evento = (Long) array.get("Evento");
                        entidad.setEvento(evento.intValue());

                        if (entidad.getEvento() == 1) {
                            String ept = (String) array.get("Entidad");
                            entidad.setNombreEntidad(ept);
                            Long tramo1 = (Long) array.get("PrimerTramo");
                            entidad.setPrimerTramo(tramo1.intValue());

                            Log.v("Hilo Secundario", "primer tramo" + entidad.getPrimerTramo());
                            Long tramo2 = (Long) array.get("SegundoTramo");
                            entidad.setSegundoTramo(tramo2.intValue());
                            Long tramo3 = (Long) array.get("TercerTramo");
                            entidad.setTercerTramo(tramo3.intValue());
                            Long tramo4 = (Long) array.get("CuartoTramo");
                            entidad.setCuartoTramo(tramo4.intValue());

                            int tam = entidad.getTam();

                            Log.v("Hilo Secundario", "Retorno del objeto proyecto: " + array.toJSONString());
                            Log.v("Hilo Secundario", "Proyectos: " + array.get("Proyectos"));

                            //JSONParser parser = new JSONParser();
                            //String map = (String) client.call("CrearObjetoProyecto", entidad.getIdUsuario());

                            //try {
                            //Object obj = parser.parse(map);

                            //JSONObject array = (JSONObject) obj;
                            //Log.v("Hilo Secundario", "Retorno del objeto proyecto: " + array.toJSONString());
                            //Log.v("Hilo Secundario", "Proyectos: " + array.get("Proyectos"));

                            JSONArray array2 = (JSONArray) array.get("Proyectos");

                            for (int x = 0; x < tam; x++) {
                                JSONObject object = (JSONObject) array2.get(x);

                                Proyectos proyecto = new Proyectos();
                                Long avanceFinanciero = (Long) object.get("avanceFinanciero");
                                Long avanceFisico = (Long) object.get("avanceFisico");
                                String nombreProyecto = (String) object.get("nombreProyecto");
                                String descripcion = (String) object.get("descripcion");
                                Double monto = (Double) object.get("monto");
                                String estatus = (String) object.get("estatus");
                                Long periodo = (Long) object.get("periodo");
                                String correlativo = (String) object.get("correlativo");

                                int id_periodo = periodo.intValue();
                                int avance = avanceFinanciero.intValue();
                                int avance2 = avanceFisico.intValue();

                                float mon = monto.floatValue();

                                proyecto.setAvanceFinanciero(avance);
                                proyecto.setAvanceFisico(avance2);
                                proyecto.setNombreProyecto(nombreProyecto);
                                proyecto.setDescripcion(descripcion);
                                proyecto.setMonto(mon);
                                proyecto.setPeriodo(id_periodo);
                                proyecto.setEstatus(estatus);
                                proyecto.setCorrelativo(correlativo);

                                lista.add(proyecto);
                            }
                        }
                        else if (entidad.getEvento() == 2) {
                            Log.v("Hilo Secundario", "El usuario esta intentando acceder con una clave erronea");
                        }
                        else if (entidad.getEvento() == 3) {
                            Log.v("Hilo Secundario", "El usuario ingresado no se encuentra en nuestro sistema");
                        }

                    } catch (ParseException exc) {
                        Log.v("Hilo Secundario", "posicion: " + exc.getPosition());
                    }

                }
                else {
                    Log.v("Hilo Secundario", "No se han cargado los datos");
                    entidad.setEvento(0);
                }

                return (int) entidad.getEvento();

            } catch (Exception exc) {
                Log.v("XML-RPC: ", exc.toString());
                return 4;
            }
        }

        @Override
        protected void onProgressUpdate(Integer... progreso) {
            dialog.setProgress(progreso[0]);
        }

        @Override
        protected void onPostExecute(Integer num) {
            dialog.dismiss();
            if (num == 1) {
                Intent menuPrincipal = new Intent(MainActivity.this, MenuPrincipal.class);

                Log.v("Hilo Principal", "valor de nombre: " + entidad.getNombreEntidad());

                menuPrincipal.putExtra("nombreEntidad", entidad .getNombreEntidad());
                menuPrincipal.putExtra("uno", entidad.getPrimerTramo());
                menuPrincipal.putExtra("dos", entidad.getSegundoTramo());
                menuPrincipal.putExtra("tres", entidad.getTercerTramo());
                menuPrincipal.putExtra("cuatro", entidad.getCuartoTramo());
                menuPrincipal.putExtra("lista", (Serializable) lista);

                startActivity(menuPrincipal);
            } else if (num == 0) {
                Toast.makeText(MainActivity.this, "Disculpe hay problemas con el Servidor", Toast.LENGTH_SHORT).show();
            } else if (num == 2) {
                Toast.makeText(MainActivity.this, "La clave ingresada no coincide con el usuario", Toast.LENGTH_SHORT).show();
            } else if (num == 3) {
                Toast.makeText(MainActivity.this, "El usuario no se encuentra en nuestra base de datos", Toast.LENGTH_SHORT).show();
            } else if (num == 4) {
                Toast.makeText(MainActivity.this, "Error al intentar conectarse", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        startService(new Intent(MainActivity.this, ServicioDeNotoficaciones.class));
    }

/*
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

*/
}
