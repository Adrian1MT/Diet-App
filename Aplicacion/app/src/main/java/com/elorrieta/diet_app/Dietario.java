package com.elorrieta.diet_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import static android.graphics.Color.*;

public class Dietario extends AppCompatActivity {
    RecyclerView rvIntroducirDietas;
    RecyclerView rvCargarDietas;
    ArrayList<Menu> menuArrayList;
    ArrayList<Menu> menuCargarDietaDiaria, menuCargarDietaFinDe, menuCargarDietaSemanal;

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
        menuArrayList = new ArrayList<Menu>();
        menuArrayList.add(new Menu("Dieta Diaria"));
        menuArrayList.add(new Menu("Dieta FinDe"));
        menuArrayList.add(new Menu("Dieta Semanal"));

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
        menuCargarDietaDiaria = new ArrayList<Menu>();
        menuCargarDietaFinDe = new ArrayList<Menu>();
        menuCargarDietaSemanal = new ArrayList<Menu>();
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

    public void cargarDietas() {
        boolean bucle = false;
        BBDD admin = new BBDD(this, "administracion",
                null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select dia, nomDieta from fecha", null);
        do {
            if (fila.moveToNext()) {
                //En Diaria carga todas las dietas
                menuCargarDietaDiaria.add(new Menu(fila.getString(0)));

                if (fila.getString(1).contentEquals("Dieta FinDe")) {
                    menuCargarDietaFinDe.add(new Menu(fila.getString(0)));
                }
                if (fila.getString(1).contentEquals("Dieta Semanal")) {
                    menuCargarDietaSemanal.add(new Menu(fila.getString(0)));
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
        public void onItemClick(Menu item) {
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
        public void onItemClick(Menu item) {
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

    public void dietaDiaria(View poView, String origen, String fecha) {
        Intent oIntent = new Intent(this, DietaDiaria.class);
        oIntent.putExtra("origen", origen);
        oIntent.putExtra("fecha", fecha);
        startActivity(oIntent);
    }

    public void dietaFinDe(View poView, String origen, String fecha) {
        Intent oIntent = new Intent(this, DietaFinDe.class);
        oIntent.putExtra("origen", origen);
        oIntent.putExtra("fecha", fecha);
        startActivity(oIntent);
    }

    public void dietaSemanal(View poView, String origen, String fecha) {
        Intent oIntent = new Intent(this, DietaSemanal.class);
        oIntent.putExtra("origen", origen);
        oIntent.putExtra("fecha", fecha);
        startActivity(oIntent);
    }
}