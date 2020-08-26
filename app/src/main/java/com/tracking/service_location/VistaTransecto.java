package com.tracking.service_location;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

public class VistaTransecto extends AppCompatActivity {

    static TextView tvlatitud, tvhora, tvlongitud, tvaltura, tvfecha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_transecto);

        tvlatitud = findViewById(R.id.tv_latitude);
        tvlongitud = findViewById(R.id.tv_longitude);
        tvaltura = findViewById(R.id.tv_altura);
        tvhora = findViewById(R.id.tv_hora);
        tvfecha = findViewById(R.id.tv_fecha);

        findViewById(R.id.btt_stopvw).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop();
            }
        });
    }

    public static void putValue(double latitude, double longitude, int height, long time, long date) {
        tvlatitud.setText(String.valueOf(latitude));
        tvlongitud.setText(String.valueOf(longitude));
        tvaltura.setText(String.valueOf(height));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YY-MM-dd");
        tvfecha.setText(simpleDateFormat.format(date));
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("h:mm");
        tvhora.setText(simpleDateFormat2.format(time));
    }

    public void stop() {
        Intent intent = new Intent(getApplicationContext(), Service_Location.class);
        intent.setAction(Service_Location.ACTION_STOP_LOCATION_SERVICE);
        startService(intent);
        Toast.makeText(this, "Servicio de Localizacion detenido",Toast.LENGTH_LONG).show();
        Intent intent2 = new Intent(this, MainActivity.class );
        startActivity(intent2);
    }
}
