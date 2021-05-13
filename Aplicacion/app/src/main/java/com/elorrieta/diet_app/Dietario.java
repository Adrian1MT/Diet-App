package com.elorrieta.diet_app;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static android.graphics.Color.LTGRAY;

public class Dietario extends AppCompatActivity {
    RecyclerView rvIntroducirDietas;
    RecyclerView rvCargarDietas;
    String fecha, fechaDieta;
    ArrayList<MenuItem> menuArrayList;
    ArrayList<MenuItem> menuCargarDietaDiaria, menuCargarDietaFinDe, menuCargarDietaSemanal;

    View vistaCrear, vistaVer;
    boolean elegirDietaDiariaPulsado = false;
    boolean elegirDietaFinDePulsado = false;
    boolean elegirDietaSemanalPulsado = false;
    Button btnDiaria, btnFinDe, btnSemanal;
    MenuAdapterCargar mcdd, mcdf, mcds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dietario);

        btnDiaria = (Button) findViewById(R.id.btnDiaria);
        btnFinDe = (Button) findViewById(R.id.btnFinDe);
        btnSemanal = (Button) findViewById(R.id.btnSemanal);

        // Llenamos el ArrayList de introducir dietas.
        menuArrayList = new ArrayList<MenuItem>();
        menuArrayList.add(new MenuItem("Dieta Diaria"));
        menuArrayList.add(new MenuItem("Dieta FinDe"));
        menuArrayList.add(new MenuItem("Dieta Semanal"));

        rvIntroducirDietas = (RecyclerView) findViewById(R.id.RecyclerCargarDieta);

        MenuAdapterDietas mid = new MenuAdapterDietas(menuArrayList, escuchador);
        rvIntroducirDietas.setAdapter(mid);

        // establecemos el Layout Manager.
        LinearLayoutManager llmIntroducir = new LinearLayoutManager(this);
        llmIntroducir.setOrientation(LinearLayoutManager.VERTICAL);
        rvIntroducirDietas.setLayoutManager(llmIntroducir);
        rvIntroducirDietas.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvIntroducirDietas.setVisibility(View.VISIBLE);

        // Llenamos el ArrayList de cargar dietas guardadas.
        menuCargarDietaDiaria = new ArrayList<MenuItem>();
        menuCargarDietaFinDe = new ArrayList<MenuItem>();
        menuCargarDietaSemanal = new ArrayList<MenuItem>();
        cargarDietas();

        rvCargarDietas = (RecyclerView) findViewById(R.id.reciclerListaRecetas);

        mcdd = new MenuAdapterCargar(menuCargarDietaDiaria, escuchador2);
        mcdf = new MenuAdapterCargar(menuCargarDietaFinDe, escuchador2);
        mcds = new MenuAdapterCargar(menuCargarDietaSemanal, escuchador2);

        // establecemos el Layout Manager.
        LinearLayoutManager llmCargar = new LinearLayoutManager(this);
        llmCargar.setOrientation(LinearLayoutManager.VERTICAL);
        rvCargarDietas.setLayoutManager(llmCargar);
        rvCargarDietas.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvCargarDietas.setVisibility(View.INVISIBLE);
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
        return super.onOptionsItemSelected(item);
    }

    public void cargarDietas() {
        boolean bucle = false;
        BBDD admin = new BBDD(this, "administracion",
                null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select dia, nomDieta from fecha", null);
        do {
            if (fila.moveToNext()) {
                //En Diaria carga todas las dietas
                fecha = fila.getString(0);
                if (!fecha.isEmpty()) {
                    fechaDieta = fecha_DD_MM_AAAA(fecha);
                    menuCargarDietaDiaria.add(new MenuItem(fechaDieta));

                    if (fila.getString(1).contentEquals("Dieta FinDe")) {
                        menuCargarDietaFinDe.add(new MenuItem(fechaDieta));
                    }
                    if (fila.getString(1).contentEquals("Dieta Semanal")) {
                        menuCargarDietaSemanal.add(new MenuItem(fechaDieta));
                    }
                }
            } else {
                bucle = true;
            }
        } while (bucle == false);
        bd.close();
        admin.close();
    }

    // inicializarmos el adapter de cargar dietas con nuestros datos.
    OnItemClickListener escuchador2 = new OnItemClickListener() {
        @Override
        public void onItemClick(MenuItem item) {
            vistaVer = new View(Dietario.super.getApplicationContext());
            String origen = "ver";
            String fecha = item.getItem().toString();
            if (rvCargarDietas.getAdapter() == mcdd) {
                dietaDiaria(vistaVer, origen, fecha);
            } else if (rvCargarDietas.getAdapter() == mcdf) {
                dietaFinDe(vistaVer, origen, fecha);
            } else {
                dietaSemanal(vistaVer, origen, fecha);
            }
        }
    };

    // inicializarmos el adapter con nuestros datos.
    OnItemClickListener escuchador = new OnItemClickListener() {
        @Override
        public void onItemClick(MenuItem item) {
            String origen = "crear";
            vistaCrear = new View(Dietario.super.getApplicationContext());
            if (item.getItem().contentEquals("Dieta Diaria")) {
                dietaDiaria(vistaCrear, origen, "");
            } else if (item.getItem().contentEquals("Dieta FinDe")) {
                dietaFinDe(vistaCrear, origen, "");
            } else {
                dietaSemanal(vistaCrear, origen, "");
            }
        }
    };

    public void elegirDietaDiaria(View poView) {
        elegirDietaDiariaPulsado = !elegirDietaDiariaPulsado;
        if (elegirDietaDiariaPulsado) {
            findViewById(R.id.btnDiaria).setBackgroundColor(0xffff8800);
            rvCargarDietas.setAdapter(mcdd);
            rvCargarDietas.setVisibility(View.VISIBLE);

            btnFinDe.setEnabled(false);
            findViewById(R.id.btnFinDe).setBackgroundColor(LTGRAY);
            btnSemanal.setEnabled(false);
            findViewById(R.id.btnSemanal).setBackgroundColor(LTGRAY);
        } else {
            findViewById(R.id.btnDiaria).setBackgroundColor(0xFF4CAF50);
            btnFinDe.setEnabled(true);
            findViewById(R.id.btnFinDe).setBackgroundColor(0xFF4CAF50);
            btnSemanal.setEnabled(true);
            findViewById(R.id.btnSemanal).setBackgroundColor(0xFF4CAF50);
            rvCargarDietas.setVisibility(View.INVISIBLE);
        }
    }

    public void elegirDietaFinDe(View poView) {
        elegirDietaFinDePulsado = !elegirDietaFinDePulsado;
        if (elegirDietaFinDePulsado) {
            findViewById(R.id.btnFinDe).setBackgroundColor(0xffff8800);
            rvCargarDietas.setAdapter(mcdf);
            rvCargarDietas.setVisibility(View.VISIBLE);

            btnDiaria.setEnabled(false);
            findViewById(R.id.btnDiaria).setBackgroundColor(LTGRAY);
            btnSemanal.setEnabled(false);
            findViewById(R.id.btnSemanal).setBackgroundColor(LTGRAY);
        } else {
            findViewById(R.id.btnFinDe).setBackgroundColor(0xFF4CAF50);
            btnDiaria.setEnabled(true);
            findViewById(R.id.btnDiaria).setBackgroundColor(0xFF4CAF50);
            btnSemanal.setEnabled(true);
            findViewById(R.id.btnSemanal).setBackgroundColor(0xFF4CAF50);
            rvCargarDietas.setVisibility(View.INVISIBLE);
        }
    }

    public void elegirDietaSemanal(View poView) {
        elegirDietaSemanalPulsado = !elegirDietaSemanalPulsado;
        if (elegirDietaSemanalPulsado) {
            findViewById(R.id.btnSemanal).setBackgroundColor(0xffff8800);
            rvCargarDietas.setAdapter(mcds);
            rvCargarDietas.setVisibility(View.VISIBLE);

            btnDiaria.setEnabled(false);
            findViewById(R.id.btnDiaria).setBackgroundColor(LTGRAY);
            btnFinDe.setEnabled(false);
            findViewById(R.id.btnFinDe).setBackgroundColor(LTGRAY);
        } else {
            findViewById(R.id.btnSemanal).setBackgroundColor(0xFF4CAF50);
            btnDiaria.setEnabled(true);
            findViewById(R.id.btnDiaria).setBackgroundColor(0xFF4CAF50);
            btnFinDe.setEnabled(true);
            findViewById(R.id.btnFinDe).setBackgroundColor(0xFF4CAF50);
            rvCargarDietas.setVisibility(View.INVISIBLE);
        }
    }

    public String fecha_DD_MM_AAAA(String fechaAAAA_MM_DD) {
        String fecha_DD_MM_AAAA = "";

        String[] fechaArray = fechaAAAA_MM_DD.split(" / ");
        int diaDelMes, mesDelAnio, anio;
        diaDelMes = Integer.parseInt(fechaArray[2].trim());
        String diaM;
        if (diaDelMes < 10) {
            diaM = "0" + diaDelMes;
        } else {
            diaM = String.valueOf(diaDelMes);
        }
        mesDelAnio = Integer.parseInt(fechaArray[1].trim());
        String mes;
        if (mesDelAnio < 10) {
            mes = "0" + mesDelAnio;
        } else {
            mes = String.valueOf(mesDelAnio);
        }
        anio = Integer.parseInt(fechaArray[0].trim());
        fecha_DD_MM_AAAA = diaM + " / " + mes + " / " + anio;

        return fecha_DD_MM_AAAA;
    }

    public void dietaDiaria(View poView, String origen, String fecha) {
        Intent oIntent = new Intent(this, DietaDiaria.class);
        oIntent.putExtra("origen", origen);
        oIntent.putExtra("fecha", fecha);
        startActivity(oIntent);
        finish();
    }

    public void dietaFinDe(View poView, String origen, String fecha) {
        Intent oIntent = new Intent(this, DietaFinDe.class);
        oIntent.putExtra("origen", origen);
        oIntent.putExtra("fecha", fecha);
        startActivity(oIntent);
        finish();
    }

    public void dietaSemanal(View poView, String origen, String fecha) {
        Intent oIntent = new Intent(this, DietaSemanal.class);
        oIntent.putExtra("origen", origen);
        oIntent.putExtra("fecha", fecha);
        startActivity(oIntent);
        finish();
    }
}