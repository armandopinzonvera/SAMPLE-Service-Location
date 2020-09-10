package com.tracking.service_location;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tracking.service_location.SQLite.ConexionSQLite;
import com.tracking.service_location.SQLite.UtilidadesSQLite;

import java.sql.Time;
import java.text.SimpleDateFormat;

public class VistaTransecto extends AppCompatActivity {

    static TextView tvlatitud, tvhora, tvlongitud, tvaltura, tvfecha, tvNombreProyecto, tvIdTransecto;


    Fragment fragMapa = new MapFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_transecto);

        getSupportFragmentManager().beginTransaction().replace(R.id.marco_mapavw, fragMapa).commit();

        tvlatitud = findViewById(R.id.tv_latitude);
        tvlongitud = findViewById(R.id.tv_longitude);
        tvaltura = findViewById(R.id.tv_altura);
        tvhora = findViewById(R.id.tv_hora);
        tvfecha = findViewById(R.id.tv_fecha);
        tvNombreProyecto = findViewById(R.id.tv_nombre_proyecto);
        tvIdTransecto = findViewById(R.id.tv_ID_transecto);
        conectarExtras();
        /*Time time = new Time();
        time.execute();*/
        hilo();

      //  conectarSQLite();
        findViewById(R.id.btt_stopvw).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop();
            }
        });
    }

    //traer informacion de proyecto y transecto
    public void conectarExtras() {
        Intent intent = getIntent();
        String data1 = intent.getStringExtra(Iniciar.NOMBRE_PROYECTO);
        String data2 = intent.getStringExtra(Iniciar.ID_TRANSECTO);
        tvNombreProyecto.setText(data1);
        tvIdTransecto.setText(data2);
    }

    //Colocar valores en TextView
    public static void putValueTextView(double latitude, double longitude, int height, long time, long date) {
        tvlatitud.setText(String.valueOf(latitude));
        tvlongitud.setText(String.valueOf(longitude));
        tvaltura.setText(String.valueOf(height));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YY-MM-dd");
        tvfecha.setText(simpleDateFormat.format(date));
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("h:mm");
        tvhora.setText(simpleDateFormat2.format(time));
    }

    // Detener Servicio de localizacion
    public void stop() {
        Intent intent = new Intent(getApplicationContext(), Service_Location.class);
        intent.setAction(Service_Location.ACTION_STOP_LOCATION_SERVICE);
        startService(intent);
        Toast.makeText(this, "Servicio de Localizacion detenido", Toast.LENGTH_LONG).show();
        Intent intent2 = new Intent(this, MainActivity.class);
        startActivity(intent2);
    }
    //Iniciar conexion SQLite
    public void conectarSQLite() {
        ConexionSQLite conexionSQLite = new ConexionSQLite(this, UtilidadesSQLite.NOMBRE_PROYECTO, null, 1);
        SQLiteDatabase db = conexionSQLite.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(UtilidadesSQLite.NOMBRE_MUESTREO, tvIdTransecto.getText().toString());
        values.put(UtilidadesSQLite.LATITUD, tvlatitud.getText().toString());
        values.put(UtilidadesSQLite.LONGITUD, tvlongitud.getText().toString());
        values.put(UtilidadesSQLite.ALTURA, tvaltura.getText().toString());
        values.put(UtilidadesSQLite.FECHA, tvfecha.getText().toString());
        values.put(UtilidadesSQLite.HORA, tvhora.getText().toString());

        //(OJO::)El primer parametro en el nombre de la tabla y el otro es el primero que introducimos
        Long idResultante = db.insert(UtilidadesSQLite.NOMBRE_PROYECTO, UtilidadesSQLite.NOMBRE_MUESTREO, values);

        Toast.makeText(getApplicationContext(), "Numero de Registro = " + idResultante, Toast.LENGTH_SHORT).show();

    }
        ////////////////////////////////////////////////////////
    /*Boolean cambio = true;
    Time time = new Time();
    public  void hilo(){

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void ejecutar(){

        time.execute();

    }
    public class Time extends AsyncTask<Void, Integer, Boolean>{

        @Override
        protected Boolean doInBackground(Void... voids) {
            for(int i = 1; i<=3; i++){
                hilo();
            }

            return cambio;
        }
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            ejecutar();
            conectarSQLite();
            Toast.makeText(VistaTransecto.this, "HILLO", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void finalize() throws Throwable {
            super.finalize();
        }
    }*/

    ////////////////////////////////////////////////////////////

    // hilo
    private Boolean arrancar_hilo = false;
    public void hilo(){
        if (!arrancar_hilo){
        arrancar_hilo = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(arrancar_hilo){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            conectarSQLite();
                            Toast.makeText(VistaTransecto.this, "HILO", Toast.LENGTH_SHORT).show();
                        }
                    });
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(1);
    }
}
