package com.elorrieta.diet_app;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;

public class Almacen extends AppCompatActivity {
/*
Spinner sEstaciones;
ArrayList<String> listaEstaciones = new ArrayList<String>();
 estaciones = new String[listaEstaciones.size()];
 estaciones = listaEstaciones.toArray(estaciones);
 sEstaciones.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,estaciones));
 */
    Spinner Almacenes;
    String[] Almacen;
    ArrayList<String> listaAlmacen = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_almacen);
        almacen();
        Almacenes= (Spinner)findViewById(R.id.spinnerAlmacen);

        Almacen = new String[listaAlmacen.size()];
        Almacen = listaAlmacen.toArray(Almacen);
        Almacenes.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,Almacen));
        Almacenes.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,listaAlmacen));
    }
    public void LimpiarArrays(){
        listaAlmacen.clear();
    }
    public void almacen(){
        LimpiarArrays();
        boolean bucle=false;
        BBDD admin = new BBDD(this,"administracion",
                null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select * from almacen", null);
        do{
            if (fila.moveToNext()){
                listaAlmacen.add(fila.getString(0));
            }else{
                bucle=true;
            }
        }while (bucle==false);
        bd.close();
        admin.close();
    }
}