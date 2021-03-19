package com.elorrieta.diet_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.elorrieta.diet_app.ui.main.AdapterListaRecetas;

import java.util.ArrayList;

public class Visualizar_Recetas extends AppCompatActivity {
    RecyclerView RecyclerMenu;
    RecyclerView RecyclerTiempo;
    RecyclerView RecyclerDificultad;
    RecyclerView RecyclerListaRecetas;
    ArrayList<String> NombreMenu = new ArrayList<String>();
    ArrayList<String> NombreTiempo = new ArrayList<String>();
    ArrayList<String> NombreDificultad = new ArrayList<String>();
    ArrayList<Integer> IDs = new ArrayList<Integer>();
    ArrayList<String> NombreReceta = new ArrayList<String>();

    String ContenidoBuscar;
    EditText Texto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualizar__recetas);

        RecyclerMenu = (RecyclerView)findViewById(R.id.idmenu);
        RecyclerTiempo = (RecyclerView)findViewById(R.id.idtiempo);
        RecyclerDificultad = (RecyclerView)findViewById(R.id.iddificultad);

        RecyclerListaRecetas = (RecyclerView)findViewById(R.id.ListadoReceta);

        RecyclerMenu.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        RecyclerMenu.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        RecyclerTiempo.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        RecyclerTiempo.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        RecyclerDificultad.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        RecyclerDificultad.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        RecyclerListaRecetas.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        RecyclerListaRecetas.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        TodoElMenuDerecha();
        Texto= (EditText)findViewById(R.id.idBuscador);
        Texto.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ContenidoBuscar="";
                if(Texto.getText().toString().length()>0){
                    ContenidoBuscar=" nombre LIKE '"+Texto.getText().toString().trim()+"%'";
                    Buscar();
                }else{
                    TodoElListado();
                    relleno();
                }

            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
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
        relleno();
    }
    public void relleno(){
        AdapterListaRecetas adapterReceta = new AdapterListaRecetas(NombreReceta);// cambiar adapter y eleccion
        RecyclerListaRecetas.setAdapter(adapterReceta);
    }
    public void TodoElMenuDerecha(){
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
    public void TodoElListado(){
        LimpiarArrays();
        boolean bucle=false;
        BBDD admin = new BBDD(this,"administracion",
                null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select id,nombre from receta", null);
        do{
            if (fila.moveToNext()){
                IDs.add(fila.getInt(0));
                NombreReceta.add(fila.getString(1));
            }else{
                bucle=true;
            }
        }while (bucle==false);
        bd.close();
        admin.close();
    }
    public void Buscar(){
        LimpiarArrays();
        boolean bucle=false;
        BBDD admin = new BBDD(this,"administracion",
                null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select id,nombre from receta where"+ContenidoBuscar, null);
      //  Cursor fila = bd.rawQuery("select id,nombre from receta where nombre = 'Menu1'", null);
        do{
            if (fila.moveToNext()){
                IDs.add(fila.getInt(0));
                NombreReceta.add(fila.getString(1));
            }else{
                bucle=true;
            }
        }while (bucle==false);
        bd.close();
        admin.close();
        relleno();
    }
    public void LimpiarArrays(){
        IDs.clear();
        NombreReceta.clear();
    }
    public void INSERTARTEMPORAL(View View){
        int vueltas =0;
        boolean bucle=false;
        String Nombre="Menu";
        int Tiempo =12;
        String Dificultad="ALTA";
        String Tipo="ENTRANTE";
        String NombreCompleto;
        do{
            vueltas+=1;
            NombreCompleto=Nombre+vueltas;
            if (Tiempo==5){
                Dificultad="ALTA";
                Tipo="PRIMERO";
            }else if (Tiempo==10){
                 Dificultad="MEDIA";
                Tipo="SEGUNDO";
            } if (Tiempo==15){
                Dificultad="BAJA";
                Tipo="POSTRE";
            }
            Tiempo +=vueltas;
        BBDD admin = new BBDD(this,"administracion", null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("nombre",NombreCompleto);
        registro.put("tiempo",Tiempo);
        registro.put("dificultad",Dificultad);
        registro.put("tipo",Tipo);
        bd.insert("receta",null,registro);
        if (vueltas==20){
                bucle=true;
                bd.close();
                admin.close();
            }
        }while (bucle==false);
    }
}