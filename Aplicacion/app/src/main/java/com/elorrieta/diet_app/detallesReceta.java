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
    ImageView foto, fmenu,fdificultad,ftiempo,fcomensal;
    RecyclerView recycler;

    ArrayList<String> listaIngredientes = new ArrayList<String>();
    ArrayList<String> cantidad = new ArrayList<String>();
    ArrayList<String> categoria = new ArrayList<String>();
    ArrayList<String> listaUnidades = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_receta);
        Bundle extras = getIntent().getExtras();
        nombre = extras.getString("nombre");

        foto = (ImageView)findViewById(R.id.foto);
        fmenu = (ImageView)findViewById(R.id.Fmenu);
        fdificultad = (ImageView)findViewById(R.id.Fdificultad);
        ftiempo = (ImageView)findViewById(R.id.Ftiempo);
        fcomensal = (ImageView)findViewById(R.id.Fcomensal);

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
        fmenu.setImageResource(R.drawable.menu);
        fdificultad.setImageResource(R.drawable.dificultad);
        ftiempo.setImageResource(R.drawable.tiempo);
        fcomensal.setImageResource(R.drawable.comensal);

        BBDD agenda = new BBDD(this, "administracion", null, 1);
        SQLiteDatabase bd = agenda.getWritableDatabase();
        Cursor c = bd.rawQuery("select nombre, elaboracion, tipo, tiempo, dificultad, id from receta where nombre='"+ nombre +"'", null);
        Cursor d = bd.rawQuery("select numComensales from receta join nComensales on receta.id = nComensales.id where receta.nombre='"+ nombre +"'", null);
        int longitud = 0;
        int vueltas=0;
        if (c != null) {
            if (c.moveToFirst()) {
                c.moveToFirst();
                do {
                    nomReceta.setText(c.getString(0));
                    elaboracion.setText(c.getString(1));
                    tipo.setText( c.getString(2));
                    tiempo.setText( c.getInt(3) + " minutos");
                    dificultad.setText("Dificultad: " + c.getString(4));

                    if (d != null) {
                        if (d.moveToFirst()) {
                            d.moveToFirst();
                            do {
                                comensales.setText(d.getString(0));
                                Cursor f = bd.rawQuery("select unidad, tiene.nomIngrediente ,tiene.cantidad from ingrediente " +
                                        "join tiene on ingrediente.nomIngrediente = tiene.nomIngrediente " +
                                        "where tiene.id="+ c.getInt(5) + " and tiene.numComensales=" + d.getInt(0), null);
                                if (f != null) {
                                    if (f.moveToFirst()) {
                                        f.moveToFirst();
                                        do {
                                            listaIngredientes.add(f.getString(1));
                                            listaUnidades.add(f.getString(2) + "  " + f.getString(0));
                                        } while (f.moveToNext());
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