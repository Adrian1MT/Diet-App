package com.elorrieta.diet_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.elorrieta.diet_app.ui.main.dialog.DatePickerFragment;

public class FiltroListaCompraAlmacen extends AppCompatActivity implements View.OnClickListener{
    String fechaOrigen, fechaFin;
    int anio, mes, diaDelMes;
    TextView txtFechaOrigen, txtFechaFin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro_lista_compra_almacen);

        txtFechaOrigen = (TextView) findViewById(R.id.txtFechaOrigen);
        txtFechaFin = (TextView) findViewById(R.id.txtFechaFin);
        txtFechaOrigen.setOnClickListener(this);
        txtFechaFin.setOnClickListener(this);
    }

    @Override
    public void onClick(View poView) {
        switch (poView.getId()) {
            case R.id.txtFechaOrigen:
                mostrarCalendarioDialogOrigen();
                break;
            case R.id.txtFechaFin:
                mostrarCalendarioDialogFinal();
                break;
        }
    }

    private void mostrarCalendarioDialogOrigen() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                //Asigno los datos locales a globales, para usarlos en otros métodos
                anio = year;
                mes = month;
                diaDelMes = day;
                // +1 porque Enero es 0
                final String selectedDate = day + " / " + (month + 1) + " / " + year;
                txtFechaOrigen.setText(selectedDate);
                fechaOrigen = txtFechaOrigen.getText().toString();
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void mostrarCalendarioDialogFinal() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                //Asigno los datos locales a globales, para usarlos en otros métodos
                anio = year;
                mes = month;
                diaDelMes = day;
                // +1 porque Enero es 0
                final String selectedDate = day + " / " + (month + 1) + " / " + year;
                txtFechaFin.setText(selectedDate);
                fechaFin = txtFechaFin.getText().toString();
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
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