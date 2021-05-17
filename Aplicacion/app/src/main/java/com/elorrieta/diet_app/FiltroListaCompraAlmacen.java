package com.elorrieta.diet_app;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.elorrieta.diet_app.ui.main.dialog.DatePickerFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

public class FiltroListaCompraAlmacen extends AppCompatActivity implements View.OnClickListener {
    String fechaOrigen, fechaFin, fechaCompra;
    int anio, mes;
    String dia;
    TextView txtFechaOrigen, txtFechaFin;
    Button btnLista;
    BottomNavigationView BotonNavegacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro_lista_compra_almacen);

        BotonNavegacion = findViewById(R.id.BotonNavegacion);
        BotonNavegacion.setItemIconTintList(null);
        BotonNavegacion.setItemIconSize(100);

        BotonNavegacion.setOnNavigationItemSelectedListener(Navegacion);

        btnLista = (Button) findViewById(R.id.btnLista);
        btnLista.setEnabled(false);

        txtFechaOrigen = (TextView) findViewById(R.id.txtFechaOrigen);
        txtFechaFin = (TextView) findViewById(R.id.txtFechaFin);
        txtFechaOrigen.setOnClickListener(this);
        txtFechaFin.setOnClickListener(this);

        String selectedDate = "";
        Calendar now = Calendar.getInstance();
        String dia;
        if (now.get(now.DAY_OF_MONTH) < 10) {
            dia = "0" + now.get(now.DAY_OF_MONTH);
        } else {
            dia = String.valueOf(now.get(now.DAY_OF_MONTH));
        }

        selectedDate = dia + " / " + (Integer.parseInt(String.valueOf(now.get(now.MONTH))) + 1) + " / " + now.get(now.YEAR);
        txtFechaOrigen.setText(fecha_DD_MM_AAAA(fecha_AAAA_MM_DD(selectedDate)));
        fechaOrigen = txtFechaOrigen.getText().toString();
    }
    private final BottomNavigationView.OnNavigationItemSelectedListener Navegacion = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull android.view.MenuItem item) {
            switch(item.getItemId()){
                case R.id.idPrimero:
                    Recetas();
                    return true;
                case R.id.idSegundo:
                    Dietario();
                    return true;
            }
            return false;
        }
    };
    public void Recetas(){
        Intent oIntent = new Intent(this, Visualizar_Recetas.class);
        startActivity(oIntent);
        finish();
    }
    public void Dietario(){
        Intent oIntent = new Intent(this, Dietario.class);
        startActivity(oIntent);
        finish();
    }
    //Métodos Action Bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.acercade, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.acercade) {
            Intent i = new Intent(this, AcercaDeActivity.class);
            startActivity(i);
        }
        if (id==R.id.manual) {
            Intent i = new Intent(this, Manual.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
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
                if (day < 10) {
                    dia = "0" + String.valueOf(day);
                } else {
                    dia = String.valueOf(day);
                }
                // +1 porque Enero es 0
                final String selectedDate = dia + " / " + (month + 1) + " / " + year;
                txtFechaFin.setText(fecha_DD_MM_AAAA(fecha_AAAA_MM_DD(selectedDate)));
                fechaFin = txtFechaFin.getText().toString();
                btnLista.setEnabled(true);
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void almacen(View poView) {
        Intent oIntent = new Intent(this, Almacen.class);
        startActivity(oIntent);
    }

    public void listaCompra(View poView) {
        //Hacemos comprobación de que existen dietas en esa franja de fechas
        //Pasamos la fecha de la última dieta
        fechaOrigen = fecha_AAAA_MM_DD(fechaOrigen);
        fechaFin = fecha_AAAA_MM_DD(fechaFin);
        boolean bucle = false;
        BBDD admin = new BBDD(this, "administracion",
                null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select dia from fecha where dia > '" + fechaOrigen + "' and dia <= '" + fechaFin + "' order by 1 asc", null);
        do {
            if (fila.moveToNext()) {
                if (fila != null) {
                    fechaCompra = fila.getString(0);
                }
            } else {
                bucle = true;
            }
        } while (bucle == false);
        bd.close();
        admin.close();
        if (fechaCompra == null) {
            Toast.makeText(this, "No existen dietas en la franja desde el " + fecha_DD_MM_AAAA(fechaOrigen) + " hasta el " + fecha_DD_MM_AAAA(fechaFin), Toast.LENGTH_LONG).show();
            fechaOrigen = txtFechaOrigen.getText().toString();
            txtFechaFin.setText("");
        } else {
            Intent oIntent = new Intent(this, listaCompra.class);
            oIntent.putExtra("fechaOrigen", fechaOrigen);
            oIntent.putExtra("fechaFin", fechaCompra);
            startActivity(oIntent);
            finish();
        }
    }

    public String fecha_AAAA_MM_DD(@NotNull String fechaDD_MM_AAA) {
        String fecha_AAAA_MM_DD = "";

        String[] fechaArray = fechaDD_MM_AAA.split(" / ");
        int diaDelMes, mesDelAnio, anio;
        diaDelMes = Integer.parseInt(fechaArray[0].trim());
        String dia;
        if (diaDelMes < 10) {
            dia = "0" + diaDelMes;
        } else {
            dia = String.valueOf(diaDelMes);
        }
        mesDelAnio = Integer.parseInt(fechaArray[1].trim());
        String mes;
        if (mesDelAnio < 10) {
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
        if (diaDelMes < 10) {
            dia = "0" + diaDelMes;
        } else {
            dia = String.valueOf(diaDelMes);
        }
        mesDelAnio = Integer.parseInt(fechaArray[1].trim());
        String mes;
        if (mesDelAnio < 10) {
            mes = "0" + mesDelAnio;
        } else {
            mes = String.valueOf(mesDelAnio);
        }
        anio = Integer.parseInt(fechaArray[0].trim());
        fecha_DD_MM_AAAA = dia + " / " + mes + " / " + anio;

        return fecha_DD_MM_AAAA;
    }

}