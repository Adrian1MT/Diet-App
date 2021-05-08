package com.elorrieta.diet_app;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.elorrieta.diet_app.ui.main.dialog.DatePickerFragment;

import java.util.Calendar;

public class FiltroListaCompraAlmacen extends AppCompatActivity implements View.OnClickListener{
    String fechaOrigen, fechaFin;
    int anio, mes;
    String dia;
    TextView txtFechaOrigen, txtFechaFin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro_lista_compra_almacen);

        txtFechaOrigen = (TextView) findViewById(R.id.txtFechaOrigen);
        txtFechaFin = (TextView) findViewById(R.id.txtFechaFin);
        txtFechaOrigen.setOnClickListener(this);
        txtFechaFin.setOnClickListener(this);

        String selectedDate = "";
        Calendar now = Calendar.getInstance();
        String dia;
        if (now.get(now.DAY_OF_MONTH)<10){
            dia = "0" + now.get(now.DAY_OF_MONTH);
        } else {
            dia = String.valueOf(now.get(now.DAY_OF_MONTH));
        }

        selectedDate = dia + " / " + (Integer.parseInt(String.valueOf(now.get(now.MONTH))) + 1) + " / " + now.get(now.YEAR);
        txtFechaOrigen.setText(fecha_DD_MM_AAAA(fecha_AAAA_MM_DD(selectedDate)));
        fechaOrigen = txtFechaOrigen.getText().toString();
    }

    @Override
    public void onClick(View poView) {
        switch (poView.getId()) {
            case R.id.txtFechaFin:
                mostrarCalendarioDialogFinal();
                break;
        }
    }

    private void mostrarCalendarioDialogFinal() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                //Asigno los datos locales a globales, para usarlos en otros métodos
                anio = year;
                mes = month;
                // para días del 1 al 9, le añado un 0 delante (para la gestión de fechas)
                if (day<10){
                    dia = "0" + String.valueOf(day);
                } else {
                    dia = String.valueOf(day);
                }
                // +1 porque Enero es 0
                final String selectedDate = dia + " / " + (month + 1) + " / " + year;
                txtFechaFin.setText(fecha_DD_MM_AAAA(fecha_AAAA_MM_DD(selectedDate)));
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

    public String fecha_AAAA_MM_DD(String fechaDD_MM_AAA) {
        String fecha_AAAA_MM_DD = "";

        String[] fechaArray = fechaDD_MM_AAA.split(" / ");
        int diaDelMes, mesDelAnio, anio;
        diaDelMes = Integer.parseInt(fechaArray[0].trim());
        String dia;
        if (diaDelMes<10){
            dia = "0" + diaDelMes;
        } else {
            dia = String.valueOf(diaDelMes);
        }
        mesDelAnio = Integer.parseInt(fechaArray[1].trim());
        String mes;
        if (mesDelAnio<10){
            mes = "0" + mesDelAnio;
        } else {
            mes = String.valueOf(mesDelAnio);
        }
        anio = Integer.parseInt(fechaArray[2].trim());
        fecha_AAAA_MM_DD = anio + " / " + mes + " / " + dia;

        return fecha_AAAA_MM_DD;
    }

    public String fecha_DD_MM_AAAA(String fechaDD_MM_AAA) {
        String fecha_DD_MM_AAAA = "";

        String[] fechaArray = fechaDD_MM_AAA.split(" / ");
        int diaDelMes, mesDelAnio, anio;
        diaDelMes = Integer.parseInt(fechaArray[2].trim());
        String dia;
        if (diaDelMes<10){
            dia = "0" + diaDelMes;
        } else {
            dia = String.valueOf(diaDelMes);
        }
        mesDelAnio = Integer.parseInt(fechaArray[1].trim());
        String mes;
        if (mesDelAnio<10){
            mes = "0" + mesDelAnio;
        } else {
            mes = String.valueOf(mesDelAnio);
        }
        anio = Integer.parseInt(fechaArray[0].trim());
        fecha_DD_MM_AAAA = dia + " / " + mes + " / " + anio;

        return fecha_DD_MM_AAAA;
    }

}