package com.elorrieta.diet_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class listaCompra extends AppCompatActivity {
    String origen, fin;
    TextView txtFechaOrigen, txtFechaFin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_compra);

        txtFechaOrigen = (TextView) findViewById(R.id.txtOrigen);
        txtFechaFin = (TextView) findViewById(R.id.txtFin);

        //Recojo los valores del intent que llama a esta activity
        origen = getIntent().getStringExtra("fechaOrigen");
        fin = getIntent().getStringExtra("fechaFin");

        txtFechaOrigen.setText(origen);
        txtFechaFin.setText(fin);
    }
}