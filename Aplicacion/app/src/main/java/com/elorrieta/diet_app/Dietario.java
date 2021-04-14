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
    ArrayList<Menu> menuCargarDieta;

    View vista;
    boolean elegirDietaDiariaPulsado = false;
    boolean elegirDietaFinDePulsado = false;
    boolean elegirDietaSemanalPulsado = false;
    Button btnDiaria, btnFinDe, btnSemanal;

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
        menuCargarDieta = new ArrayList<Menu>();
        cargarDietas();

        rvCargarDietas = (RecyclerView) findViewById(R.id.reciclerLista);

        MenuAdapterCargar mcd = new MenuAdapterCargar(menuCargarDieta, escuchador2);
        rvCargarDietas.setAdapter(mcd);

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
        Cursor fila = bd.rawQuery("select distinct dia from fecha", null);
        do {
            if (fila.moveToNext()) {
                menuCargarDieta.add(new Menu(fila.getString(0)));
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
            vista = new View(Dietario.super.getApplicationContext());
            if (item.getItem().contentEquals("01 / 1 / 2021")) {
                dietaDiaria(vista);
            } else if (item.getItem().contentEquals("02 / 1 / 2021") || item.getItem().contentEquals("03 / 1 / 2021")) {
                dietaFinDe(vista);
            } else {
                dietaSemanal(vista);
            }
        }
    };

    // inicializarmos el adapter con nuestros datos.
    OnItemClickListener escuchador = new OnItemClickListener() {
        @Override
        public void onItemClick(Menu item) {
            vista = new View(Dietario.super.getApplicationContext());
            if (item.getItem().contentEquals("Dieta Diaria")) {
                dietaDiaria(vista);
            } else if (item.getItem().contentEquals("Dieta FinDe")) {
                dietaFinDe(vista);
            } else {
                dietaSemanal(vista);
            }
        }
    };

    public void elegirDietaDiaria(View poView) {
        elegirDietaDiariaPulsado = !elegirDietaDiariaPulsado;
        if (elegirDietaDiariaPulsado) {
            findViewById(R.id.btnDiaria).setBackgroundColor(0xffff8800);
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

    public void dietaDiaria(View poView) {
        Intent oIntent = new Intent(this, DietaDiaria.class);
        startActivity(oIntent);
    }

    public void dietaFinDe(View poView) {
        Intent oIntent = new Intent(this, DietaFinDe.class);
        startActivity(oIntent);
    }

    public void dietaSemanal(View poView) {
        Intent oIntent = new Intent(this, DietaSemanal.class);
        startActivity(oIntent);
    }
}