package com.elorrieta.diet_app;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class listaCompra extends AppCompatActivity {
    RecyclerView rvNecesitamos, rvTenemos;
    ArrayList<String> menuArrayListNecesitamos1, menuArrayListNecesitamos2, menuArrayListNecesitamos3;
    ArrayList<String> almacenTodos1, almacenTodos2, almacenTodos3;
    ArrayList<String> menuArrayListTenemos1, menuArrayListTenemos2, menuArrayListTenemos3;
    String origen, fin, fechaOrigen, fechaFin;
    TextView txtFechaOrigen, txtFechaFin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_compra);

        txtFechaOrigen = (TextView) findViewById(R.id.txtOrigen);
        txtFechaFin = (TextView) findViewById(R.id.txtFin);

        //Recojo los valores del intent que llama a esta activity
        origen = getIntent().getStringExtra("fechaOrigen");
        fin = getIntent().getStringExtra("fechaFin");

        //Se llenan los textBoxes con los datos elegidos de la pantalla anterior
        txtFechaOrigen.setText(origen);
        txtFechaFin.setText(fin);

        //ReciclerViews

        // Llenamos el ArrayList de ingredientes que NECESITAMOS.
        menuArrayListNecesitamos1 = new ArrayList<String>();
        menuArrayListNecesitamos2 = new ArrayList<String>();
        menuArrayListNecesitamos3 = new ArrayList<String>();
        cargarNecesitamos();
        rvNecesitamos = (RecyclerView) findViewById(R.id.reciclerListaRecetas);

        AdapterListaCompra aN = new AdapterListaCompra(menuArrayListNecesitamos1, menuArrayListNecesitamos2, menuArrayListNecesitamos3);
        rvNecesitamos.setAdapter(aN);

        // establecemos el Layout Manager.
        LinearLayoutManager llmNecesitamos = new LinearLayoutManager(this);
        llmNecesitamos.setOrientation(LinearLayoutManager.VERTICAL);
        rvNecesitamos.setLayoutManager(llmNecesitamos);
        rvNecesitamos.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvNecesitamos.setVisibility(View.VISIBLE);

        // Llenamos el ArrayList de ingredientes que TENEMOS.
        almacenTodos1 = new ArrayList<String>();
        almacenTodos2 = new ArrayList<String>();
        almacenTodos3 = new ArrayList<String>();
        menuArrayListTenemos1 = new ArrayList<String>();
        menuArrayListTenemos2 = new ArrayList<String>();
        menuArrayListTenemos3 = new ArrayList<String>();
        cargarTenemosTodos();
        cargarTenemosNecesitados();
        rvTenemos = (RecyclerView) findViewById(R.id.reciclerListaAlmacen);

        AdapterListaCompra aT = new AdapterListaCompra(menuArrayListTenemos1, menuArrayListTenemos2, menuArrayListTenemos3);
        rvTenemos.setAdapter(aT);

        // establecemos el Layout Manager.
        LinearLayoutManager llmTenemos = new LinearLayoutManager(this);
        llmTenemos.setOrientation(LinearLayoutManager.VERTICAL);
        rvTenemos.setLayoutManager(llmTenemos);
        rvTenemos.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvTenemos.setVisibility(View.VISIBLE);
    }

    //Carga los ingredientes de todas las recetas comprendidas entre dos fechas
    public void cargarNecesitamos() {

        fechaOrigen = fecha_AAAA_MM_DD(origen);
        fechaFin = fecha_AAAA_MM_DD(fin);

        boolean bucle = false;
        BBDD admin = new BBDD(this, "administracion",
                null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select ing.nomIngrediente, sum(cantidad)cantidad, unidad " +
                "from ingrediente as ing join tiene as ti on ti.nomIngrediente=ing.nomIngrediente " +
                "left join receta as re on re.id=ti.id where nombre in " +
                "(select nombre from contiene as con left join receta as re on re.id=con.id where dia > '" + fechaOrigen + "' and dia <= '" + fechaFin + "') " +
                "GROUP BY ing.nomIngrediente", null);
        do {
            if (fila.moveToNext()) {
                if (fila != null) {
                    menuArrayListNecesitamos1.add(fila.getString(0));
                    menuArrayListNecesitamos2.add(fila.getString(1));
                    menuArrayListNecesitamos3.add(fila.getString(2));
                }
            } else {
                bucle = true;
            }
        } while (bucle == false);
        bd.close();
        admin.close();
    }

    //Carga todos los ingredientes del almacen
    public void cargarTenemosTodos() {
        boolean bucle = false;
        BBDD admin = new BBDD(this, "administracion",
                null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select ing.nomIngrediente, sum(cantidad)cantidad, ing.unidad " +
                "from ingrediente as ing join hay as ha on ha.nomIngrediente=ing.nomIngrediente " +
                "GROUP BY ing.nomIngrediente", null);
        do {
            if (fila.moveToNext()) {
                if (fila != null) {
                    almacenTodos1.add(fila.getString(0));
                    almacenTodos2.add(fila.getString(1));
                    almacenTodos3.add(fila.getString(2));
                }
            } else {
                bucle = true;
            }
        } while (bucle == false);
        bd.close();
        admin.close();
    }

    //Llena los arrayList de TENEMOS sólo con los ingredientes existentes en NECESITAMOS.
    public void cargarTenemosNecesitados() {
        for(int i = 0; i < almacenTodos1.size(); i++) {
            for(int j = 0; j < menuArrayListNecesitamos1.size(); j++) {
                if(almacenTodos1.get(i).contentEquals(menuArrayListNecesitamos1.get(j))){
                    menuArrayListTenemos1.add(almacenTodos1.get(i));
                    menuArrayListTenemos2.add(almacenTodos2.get(i));
                    menuArrayListTenemos3.add(almacenTodos3.get(i));
                }
            }
        }
    }

    public String fecha_AAAA_MM_DD(String fechaDD_MM_AAAA) {
        String fecha_AAAA_MM_DD = "";

        String[] fechaArray = fechaDD_MM_AAAA.split(" / ");
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

    public String fecha_DD_MM_AAAA(String fechaAAAA_MM_DD) {
        String fecha_DD_MM_AAAA = "";

        String[] fechaArray = fechaAAAA_MM_DD.split(" / ");
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