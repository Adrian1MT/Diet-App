package com.elorrieta.diet_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class Visualizar_Recetas extends AppCompatActivity {
    RecyclerView RecyclerMenu;
    RecyclerView RecyclerTiempo;
    RecyclerView RecyclerDificultad;
    ArrayList<String> NombreMenu = new ArrayList<String>();
    ArrayList<String> NombreTiempo = new ArrayList<String>();
    ArrayList<String> NombreDificultad = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualizar__recetas);

        RecyclerMenu = (RecyclerView)findViewById(R.id.idmenu);
        RecyclerTiempo = (RecyclerView)findViewById(R.id.idtiempo);
        RecyclerDificultad = (RecyclerView)findViewById(R.id.iddificultad);

        RecyclerMenu.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        RecyclerMenu.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        RecyclerTiempo.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        RecyclerTiempo.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        RecyclerDificultad.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        RecyclerDificultad.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        TodoElListado();

        AdapterSeleccionDerecha adapterMenu = new AdapterSeleccionDerecha(NombreMenu);
        AdapterSeleccionDerecha adapterTiempo = new AdapterSeleccionDerecha(NombreTiempo);
        AdapterSeleccionDerecha adapterDifilcutad = new AdapterSeleccionDerecha(NombreDificultad);

        adapterMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = RecyclerMenu.getChildAdapterPosition(view);
            }
        });

        RecyclerMenu.setAdapter(adapterMenu);
        RecyclerTiempo.setAdapter(adapterTiempo);
        RecyclerDificultad.setAdapter(adapterDifilcutad);
    }
    public void TodoElListado(){
        NombreMenu.add("ENTRANTE");
        NombreMenu.add("PRIMERO");
        NombreMenu.add("SEGUNDO");
        NombreMenu.add("POSTRE");

        NombreTiempo.add("< 15 MIN");
        NombreTiempo.add("15 MIN > < 30 MIN");
        NombreTiempo.add("30 MIN <");

        NombreDificultad.add("ALTA");
        NombreDificultad.add("MEDIA");
        NombreDificultad.add("BAJA");
    }
}