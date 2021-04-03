package com.elorrieta.diet_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class detallesReceta extends AppCompatActivity {

    String nombre;
    TextView nomReceta;
    TextView elaboracion;
    TextView tipo;
    TextView tiempo;
    TextView dificultad;
    TextView comensales;
    ImageView foto;
    RecyclerView recycler;

    ArrayList<String> listaIngredientes = new ArrayList<String>();
    ArrayList<String> listaUnidades = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_receta);
        Bundle extras = getIntent().getExtras();
        nombre = extras.getString("nombre");

        foto = (ImageView)findViewById(R.id.foto);
        nomReceta = (TextView)findViewById(R.id.nomReceta);
        elaboracion = (TextView)findViewById(R.id.elaboracion);
        elaboracion.setMovementMethod(new ScrollingMovementMethod());
        tipo = (TextView)findViewById(R.id.tipo);
        tiempo = (TextView)findViewById(R.id.tiempo);
        dificultad = (TextView)findViewById(R.id.dificultad);
        comensales = (TextView)findViewById(R.id.comensales);
        recycler = (RecyclerView) findViewById(R.id.recyclerView);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        listaIngredientes = new ArrayList<String>();
        listaUnidades = new ArrayList<String>();

        foto.setImageResource(R.drawable.icono);

        BBDD agenda = new BBDD(this, "administracion", null, 1);
        SQLiteDatabase bd = agenda.getWritableDatabase();
        Cursor c = bd.rawQuery("select nombre, elaboracion, tipo, tiempo, dificultad, id from receta where nombre='"+ nombre +"'", null);
        Cursor d = bd.rawQuery("select numComensales from receta join nComensales on receta.id = nComensales.id where receta.nombre='"+ nombre +"'", null);

        if (c != null) {
            if (c.moveToFirst()) {
                c.moveToFirst();
                do {
                    nomReceta.setText(c.getString(0));
                    elaboracion.setText(c.getString(1));
                    tipo.setText("Tipo: " + c.getString(2));
                    tiempo.setText("Tiempo: " + c.getInt(3) + " minutos");
                    dificultad.setText("Dificultad: " + c.getString(4));

                    if (d != null) {
                        if (d.moveToFirst()) {
                            d.moveToFirst();
                            do {
                                comensales.setText("Comensales: " + d.getString(0));
                                Cursor e = bd.rawQuery("select cantidad from tiene join nComensales on tiene.id = nComensales.id where tiene.id="+ c.getInt(5) + " and nComensales.numComensales=" + d.getInt(0), null);
                                Cursor f = bd.rawQuery("select unidad, tiene.nomIngrediente from ingrediente join tiene on ingrediente.nomIngrediente = tiene.nomIngrediente where tiene.id="+ c.getInt(5) + " and tiene.numComensales=" + d.getInt(0), null);

                                if (f != null) {
                                    if (f.moveToFirst()) {
                                        f.moveToFirst();
                                        e.moveToFirst();
                                        do {
                                            listaIngredientes.add(f.getString(1));
                                            listaUnidades.add(e.getString(0) + "  " + f.getString(0));
                                        } while (f.moveToNext() && e.moveToNext());
                                    }
                                }

                                AdapterIngredientes adapter = new AdapterIngredientes(listaIngredientes, listaUnidades);
                                recycler.setAdapter(adapter);
                            } while (d.moveToNext());
                        }
                    }

                } while (c.moveToNext());
            }
        }


    }
}