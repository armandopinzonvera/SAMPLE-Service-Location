package com.tracking.service_location.SQLite;

public class UtilidadesSQLite {


    public static final String NOMBRE_PROYECTO = "nombre_proyecto";
    public static final String ID = "id";
    public static final String NOMBRE_MUESTREO = "nombre_muestreo";
    public static final String LATITUD = "latitud";
    public static final String LONGITUD = "longitud";
    public static final String ALTURA = "altura";
    public static final String FECHA = "fecha";
    public static final String HORA = "hora";
    public static final String TAXON = "taxon";
    public static final String CANTIDAD = "cantidad";


      public static final String CREAR_TABLA_PROYECTO = "CREATE TABLE "
           +NOMBRE_PROYECTO+" ("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
           +NOMBRE_MUESTREO+" TEXT, "
           +LATITUD+" REAL, "
           +LONGITUD+" TEXT, "
           +ALTURA+" TEXT, "
           +FECHA+" TEXT, "
           +HORA+" TEXT, "
           +TAXON+" TEXT, "
           +CANTIDAD+" TEXT)";


}
