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
    RecyclerView rvNecesitamos, rvTenemos, rvCompramos;
    ArrayList<Ingrediente> menuArrList_Necesitamos;
    ArrayList<Ingrediente> almacenTodos;
    ArrayList<Ingrediente> menuArrList_Tenemos;
    ArrayList<Ingrediente> menuArrList_Compra;
    ArrayList<Ingrediente> menuActualizacion_Almacen;
    ArrayList<Ingrediente> menuInsercion_Almacen;
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
        menuArrList_Necesitamos = new ArrayList<Ingrediente>();
        cargarNecesitamos();
        rvNecesitamos = (RecyclerView) findViewById(R.id.reciclerListaRecetas);

        AdapterListaCompra aN = new AdapterListaCompra(menuArrList_Necesitamos);
        rvNecesitamos.setAdapter(aN);

        // establecemos el Layout Manager.
        LinearLayoutManager llmNecesitamos = new LinearLayoutManager(this);
        llmNecesitamos.setOrientation(LinearLayoutManager.VERTICAL);
        rvNecesitamos.setLayoutManager(llmNecesitamos);
        rvNecesitamos.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvNecesitamos.setVisibility(View.VISIBLE);

        // Llenamos el ArrayList de ingredientes que TENEMOS.
        almacenTodos = new ArrayList<Ingrediente>();
        menuArrList_Tenemos = new ArrayList<Ingrediente>();

        cargarTenemosTodos();
        cargarTenemosNecesitados();
        rvTenemos = (RecyclerView) findViewById(R.id.reciclerListaAlmacen);

        AdapterListaCompra aT = new AdapterListaCompra(menuArrList_Tenemos);
        rvTenemos.setAdapter(aT);

        // establecemos el Layout Manager.
        LinearLayoutManager llmTenemos = new LinearLayoutManager(this);
        llmTenemos.setOrientation(LinearLayoutManager.VERTICAL);
        rvTenemos.setLayoutManager(llmTenemos);
        rvTenemos.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvTenemos.setVisibility(View.VISIBLE);

        // Llenamos el ArrayList de ingredientes que COMPRAMOS.
        menuArrList_Compra = new ArrayList<Ingrediente>();
        //Creamos los Arrays para los ingredientes del almacen
        menuActualizacion_Almacen = new ArrayList<Ingrediente>();
        menuInsercion_Almacen = new ArrayList<Ingrediente>();
        cargarCompramos();
        rvCompramos = (RecyclerView) findViewById(R.id.reciclerListaCompra);

        AdapterListaCompra aC = new AdapterListaCompra(menuArrList_Compra);
        rvCompramos.setAdapter(aC);

        // establecemos el Layout Manager.
        LinearLayoutManager llmCompramos = new LinearLayoutManager(this);
        llmCompramos.setOrientation(LinearLayoutManager.VERTICAL);
        rvCompramos.setLayoutManager(llmCompramos);
        rvCompramos.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvCompramos.setVisibility(View.VISIBLE);
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
                    Ingrediente ingrediente = new Ingrediente(fila.getString(0), fila.getFloat(1), fila.getString(2));
                    menuArrList_Necesitamos.add(ingrediente);
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
                    Ingrediente ingrediente = new Ingrediente(fila.getString(0), fila.getFloat(1), fila.getString(2));
                    almacenTodos.add(ingrediente);
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
        for (int i = 0; i < almacenTodos.size(); i++) {
            for (int j = 0; j < menuArrList_Necesitamos.size(); j++) {
                if (almacenTodos.get(i).nombre.contentEquals(menuArrList_Necesitamos.get(j).nombre)) {
                    menuArrList_Tenemos.add(almacenTodos.get(i));
                }
            }
        }
    }

    //Llena los arrayList de COMPRAMOS y guarda la actualización para el almacén con los ingredientes restando las cantidades de NECESITAMOS menos TENEMOS.
    public void cargarCompramos() {
        float resultado;
        boolean encontrado;
        for (int i = 0; i < menuArrList_Necesitamos.size(); i++) { //Ingredientes que ya teníamos en el almacén
            resultado = 0;
            encontrado = false;
            for (int j = 0; j < menuArrList_Tenemos.size() && !encontrado; j++) {
                if (menuArrList_Tenemos.get(j).getNombre().contentEquals(menuArrList_Necesitamos.get(i).getNombre())) { //Rastrea los ingredientes con el mismo nombre
                    resultado = (menuArrList_Necesitamos.get(i).getCantidad() - menuArrList_Tenemos.get(j).getCantidad());
                    if (resultado > 0) { //resta positiva
                        //Lista de la compra
                        Ingrediente ingrediente = new Ingrediente(menuArrList_Necesitamos.get(i).getNombre(), resultado, menuArrList_Necesitamos.get(i).getUnidad());
                        menuArrList_Compra.add(ingrediente);
                        //Almacen
                        Ingrediente ingredienteAlm = new Ingrediente(menuArrList_Tenemos.get(j).getNombre(), menuArrList_Tenemos.get(j).cantidad, menuArrList_Tenemos.get(j).getUnidad());
                        menuActualizacion_Almacen.add(ingredienteAlm);
                        encontrado = true;
                    } else { //resta negativa
                        //Lista de la compra
                        //-->NO necesitamos comprar porque hay existencias en el almacén
                        //Almacén
                        Ingrediente ingrediente = new Ingrediente(menuArrList_Necesitamos.get(i).getNombre(), resultado * (-1), menuArrList_Necesitamos.get(i).getUnidad());
                        menuActualizacion_Almacen.add(ingrediente);
                        encontrado = true;
                    }
                }
            }
            if (!encontrado) {
                //Lista de la compra
                Ingrediente ingrediente = new Ingrediente(menuArrList_Necesitamos.get(i).getNombre(), menuArrList_Necesitamos.get(i).getCantidad(), menuArrList_Necesitamos.get(i).getUnidad());
                menuArrList_Compra.add(ingrediente);
                //Almacen, estos ingredientes son nuevos, habrá que insertarlos
                Ingrediente ingredienteAlm = new Ingrediente(menuArrList_Necesitamos.get(i).getNombre(), menuArrList_Necesitamos.get(i).cantidad, menuArrList_Necesitamos.get(i).getUnidad());
                menuInsercion_Almacen.add(ingredienteAlm);
            }
        }
    }

    public String fecha_AAAA_MM_DD(String fechaDD_MM_AAAA) {
        String fecha_AAAA_MM_DD = "";

        String[] fechaArray = fechaDD_MM_AAAA.split(" / ");
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

    public String fecha_DD_MM_AAAA(String fechaAAAA_MM_DD) {
        String fecha_DD_MM_AAAA = "";

        String[] fechaArray = fechaAAAA_MM_DD.split(" / ");
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