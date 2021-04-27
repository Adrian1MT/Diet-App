package com.elorrieta.diet_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class FiltroListaCompraAlmacen extends AppCompatActivity {
    String fechaOrigen, fechaFin;
    TextView txtFechaOrigen, txtFechaFin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro_lista_compra_almacen);

        txtFechaOrigen = (TextView) findViewById(R.id.txtFechaOrigen);
        txtFechaFin = (TextView) findViewById(R.id.txtFechaFin);

//        txtFechaOrigen.setText("01/04/2021");
//        txtFechaFin.setText("10/04/2021");

        fechaOrigen = txtFechaOrigen.getText().toString();
        fechaFin = txtFechaFin.getText().toString();
    }

    public void almacen(View poView) {
        Intent oIntent = new Intent(this, Almacen.class);
        startActivity(oIntent);
    }

    public void listaCompra(View poView) {
        Intent oIntent = new Intent(this, listaCompra.class);
        oIntent.putExtra("fechaOrigen", fechaOrigen);
        oIntent.putExtra("fechaFin", fechaFin);
        startActivity(oIntent);
    }

}