package com.juanjiga.feeds;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button boton_as, boton_marca;
    ListView listado;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    Conexion hilo_conexion;
    //String conexion_Url = "http://www.elpais.com/rss/feed.html?feedId=1022";
    String marca_atleti = "http://estaticos.marca.com/rss/futbol/atletico.xml";
    String as_atleti = "http://masdeporte.as.com/tag/rss/atletico_madrid/";
    String url_rss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boton_as = (Button) findViewById(R.id.as_Button);
        boton_as.setOnClickListener(this);
        boton_marca = (Button) findViewById(R.id.marca_Button);
        boton_marca.setOnClickListener(this);

        listado = (ListView) findViewById(R.id.listado_ListView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.marca_Button:
                url_rss = marca_atleti;
                hilo_conexion = new Conexion();
                hilo_conexion.execute();
                break;
            case R.id.as_Button:
                url_rss = as_atleti;
                hilo_conexion = new Conexion();
                hilo_conexion.execute();
            default:
                break;
        }
    }

    public class Conexion extends AsyncTask<Object, Object, ArrayList<String>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<String> doInBackground(Object... params) {

            int i=0, j=0, k=0, l=0;
            arrayList = new ArrayList<String>();

            try {
                URL url = new URL(url_rss);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                        " (Linux; Android 5.0; es-ES) Ejemplo HTTP");

                int respuesta = connection.getResponseCode();

                if (respuesta==HttpURLConnection.HTTP_OK){

                    BufferedReader lector = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
                    String linea = lector.readLine();

                    while (linea != null) {
                        if (linea.contains("<title><![CDATA")){
                            /*i = linea.indexOf("<title>")+16;
                            j = linea.indexOf("</title>")-3;
                            arrayList.add(linea.substring(i, j));*/
                            k = linea.indexOf("<link>")+6;
                            l = linea.indexOf("</link>");
                            arrayList.add(linea.substring(k, l));
                        }
                        linea = lector.readLine();
                    }
                    lector.close();
                }
                else {
                    arrayList.add("No encontrado");
                }
                connection.disconnect();

            } catch (MalformedURLException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return arrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<String> salida_Background) {
            arrayAdapter = new ArrayAdapter<String>(getBaseContext(), R.layout.fila,
                    R.id.texto_fila_TextView, salida_Background);
            listado.setAdapter(arrayAdapter);
        }

        @Override
        protected void onProgressUpdate(Object... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }

}
