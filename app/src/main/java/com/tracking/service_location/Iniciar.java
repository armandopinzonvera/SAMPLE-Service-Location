package com.tracking.service_location;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.tracking.service_location.SQLite.ConexionSQLite;
import com.tracking.service_location.SQLite.UtilidadesSQLite;

public class Iniciar extends AppCompatActivity {

    EditText etNombreProyecto, etIdTransecto;


    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar);

        etNombreProyecto = findViewById(R.id.et_nombreProyecto);
        etIdTransecto = findViewById(R.id.et_idTransecto);

    }

      public void comenzar(View view){
            conectarSQLite();
            if(ContextCompat.checkSelfPermission(
                    getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION
            )!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(
                        Iniciar.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_CODE_LOCATION_PERMISSION
                );
            }else{
                startLocationService();
            }
    }
    public void volver(View view) {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }



    public void conectarSQLite(){
        ConexionSQLite conexionSQLite = new ConexionSQLite(this, UtilidadesSQLite.NOMBRE_PROYECTO, null, 1 );
        SQLiteDatabase db = conexionSQLite.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(UtilidadesSQLite.NOMBRE_MUESTREO, etNombreProyecto.getText().toString());
        values.put(UtilidadesSQLite.LATITUD, etIdTransecto.getText().toString());
        //(OJO::)El primer parametro en el nombre de la tabla y el otro es el primero que introducimos
        Long idResultante = db.insert(UtilidadesSQLite.NOMBRE_PROYECTO, UtilidadesSQLite.NOMBRE_MUESTREO, values);

        Toast.makeText(getApplicationContext(), "Numero de Registro = "+idResultante, Toast.LENGTH_SHORT).show();
        etIdTransecto.setText("");
        etNombreProyecto.setText("");
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                startLocationService();
            }else{
                Toast.makeText(this, "**Permiso negado**", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void startLocationService(){
        Intent intent = new Intent(getApplicationContext(), Service_Location.class);
        intent.setAction(Service_Location.ACTION_START_LOCATION_SERVICE);
        startService(intent);
        Toast.makeText(this, "Servicio de Localizacion iniciado",Toast.LENGTH_SHORT).show();

        Intent intent2 = new Intent(this, VistaTransecto.class );
        startActivity(intent2);
    }



}
