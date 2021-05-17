package com.elorrieta.diet_app;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
    ArrayList<String> congelador = new ArrayList<String>();
    ArrayList<String> despensa = new ArrayList<String>();
    ArrayList<String> especiero = new ArrayList<String>();
    ArrayList<String> nevera = new ArrayList<String>();
    String origen, fin, fechaOrigen, fechaFin, fechaCompraOld = "", nombreAlmacen = "";
    TextView txtFechaOrigen, txtFechaFin;
    Button btnCompra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_compra);

        btnCompra = (Button) findViewById(R.id.btnRealizarCompra);
        cargarListaAlmacenes();

        txtFechaOrigen = (TextView) findViewById(R.id.txtOrigen);
        txtFechaFin = (TextView) findViewById(R.id.txtFin);

        //Recojo los valores del intent que llama a esta activity
        origen = getIntent().getStringExtra("fechaOrigen");
        fin = getIntent().getStringExtra("fechaFin");

        //Se llenan los textBoxes con los datos elegidos de la pantalla anterior
        txtFechaOrigen.setText(fecha_DD_MM_AAAA(origen));
        txtFechaFin.setText(fecha_DD_MM_AAAA(fin));

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

    //Guardará la fecha final de la compra y
    //acumula los ingredientes comprados en el almacén
    public void realizarCompra(View poView) {
        //Fecha
        boolean bucle = false;
        BBDD admin = new BBDD(this, "administracion",
                null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        //Consulto la última fecha guardada
        Cursor fila = bd.rawQuery("select fechaUltimaCompra from fechaCompra", null);
        do {
            if (fila.moveToNext()) {
                if (fila != null) {
                    fechaCompraOld = fila.getString(0);
                }
            } else {
                bucle = true;
            }
        } while (bucle == false);
        //Actualizo
        bd.execSQL("UPDATE fechaCompra SET fechaUltimaCompra = '" + fin + "' WHERE fechaUltimaCompra = '" + fechaCompraOld + "'");
        bd.close();
        admin.close();

        Toast.makeText(this, "Fecha de última compra realizada actualizada con éxito a fecha " + fecha_DD_MM_AAAA(fin), Toast.LENGTH_LONG).show();

        //Almacén
        //-->Update
        bucle = false;
        admin = new BBDD(this, "administracion",
                null, 1);
        bd = admin.getWritableDatabase();
        for (int i = 0; i < menuActualizacion_Almacen.size(); i++) { //Ingredientes que ya teníamos en el almacén
            bd.execSQL("UPDATE hay SET cantidad = '" + menuActualizacion_Almacen.get(i).getCantidad() + "' WHERE nomIngrediente like '" + menuActualizacion_Almacen.get(i).getNombre() + "'");
        }
        //-->Insert. Seleccionamos el almacén que le corresponde al ingrediente y lo insertamos
        for (int i = 0; i < menuInsercion_Almacen.size(); i++) { //Ingredientes nuevos en el almacén
            if (congelador.contains(menuInsercion_Almacen.get(i).getNombre())) {
                nombreAlmacen = "CONGELADOR";
            } else if (nevera.contains(menuInsercion_Almacen.get(i).getNombre())) {
                nombreAlmacen = "NEVERA";
            } else if (especiero.contains(menuInsercion_Almacen.get(i).getNombre())) {
                nombreAlmacen = "ESPECIERO";
            } else {
                nombreAlmacen = "DESPENSA";
            }
            bd.execSQL("INSERT INTO hay (nomAlmacen,nomIngrediente,cantidad,unidad) VALUES('" + nombreAlmacen + "','" + menuInsercion_Almacen.get(i).getNombre() + "'," + menuInsercion_Almacen.get(i).getCantidad() + ",'" + menuInsercion_Almacen.get(i).getUnidad() + "')");
        }
        bd.close();
        admin.close();

        //Vaciamos los arrays, para que cada vez que arranque la activity no arrastre valores
        menuArrList_Necesitamos.clear();
        almacenTodos.clear();
        menuArrList_Tenemos.clear();
        menuArrList_Compra.clear();
        menuActualizacion_Almacen.clear();
        menuInsercion_Almacen.clear();
    }

    //Carga los ingredientes de todas las recetas comprendidas entre dos fechas
    public void cargarNecesitamos() {

        fechaOrigen = origen;
        fechaFin = fin;

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
                        Ingrediente ingredienteAlm = new Ingrediente(menuArrList_Tenemos.get(j).getNombre(), menuArrList_Tenemos.get(j).cantidad + resultado, menuArrList_Tenemos.get(j).getUnidad());
                        menuActualizacion_Almacen.add(ingredienteAlm);
                        encontrado = true;
                    } else { //resta negativa
                        //Lista de la compra
                        //-->NO necesitamos comprar porque hay existencias en el almacén
                        //Almacén
                        Ingrediente ingrediente = new Ingrediente(menuArrList_Tenemos.get(j).getNombre(), menuArrList_Tenemos.get(j).cantidad, menuArrList_Tenemos.get(j).getUnidad());
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

        //Si la lista de la compra está vacía, deshabilitamos el botón
        if (menuArrList_Compra.size() == 0) {
            btnCompra.setEnabled(false);
        }
    }

    //Formato "AAAA / MM / DD" para las fechas
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

    //Formato "DD / MM / AAAA" para las fechas
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

    //Carga la lista total de ingredientes con su almacén por defecto,
    //para saber dónde se inscribirán por defecto si no existen
    public void cargarListaAlmacenes() {
        //Congelador
        congelador.add("Habas cocidas");

        //Despensa
        despensa.add("Aceite Oliva virgen");
        despensa.add("Aceite de girasol");
        despensa.add("Aceite de sésamo");
        despensa.add("Aceitunas sin hueso");
        despensa.add("Agua");
        despensa.add("Aguacate");
        despensa.add("Alcachofas");
        despensa.add("Almendras crudas");
        despensa.add("Almendras tostadas");
        despensa.add("Arroz");
        despensa.add("Azucar blanco");
        despensa.add("Azúcar glas");
        despensa.add("Azúcar moreno");
        despensa.add("Barra Pan blanco");
        despensa.add("Boniato");
        despensa.add("Bonito en aceite");
        despensa.add("Brandy");
        despensa.add("Cebolla");
        despensa.add("Cebolleta");
        despensa.add("Dientes de ajo");
        despensa.add("Fresas");
        despensa.add("Harina");
        despensa.add("Jamón serrano");
        despensa.add("Kiwis");
        despensa.add("Leche condensada");
        despensa.add("Levadura");
        despensa.add("Licor de cereza");
        despensa.add("Lima");
        despensa.add("Limon");
        despensa.add("Maicena");
        despensa.add("Mango");
        despensa.add("Melón");
        despensa.add("Nabo");
        despensa.add("Pan duro");
        despensa.add("Papaya");
        despensa.add("Parmesano");
        despensa.add("Patatas");
        despensa.add("Peras");
        despensa.add("Piel Limon");
        despensa.add("Piel naranja");
        despensa.add("Pimientos del Piquillo");
        despensa.add("Plátano");
        despensa.add("Queso curado");
        despensa.add("Queso de oveja");
        despensa.add("Queso poco curado");
        despensa.add("Quinua");
        despensa.add("Ras al hanut");
        despensa.add("Rebanada de pan de molde");
        despensa.add("Sal");
        despensa.add("Salsa de soja");
        despensa.add("Sirope de arce");
        despensa.add("Sésamo");
        despensa.add("Tomates secos");
        despensa.add("Vinagre");
        despensa.add("Vinagre Suave");
        despensa.add("Vino Blanco");

        //Especiero
        especiero.add("Anís estrellado");
        especiero.add("Azafrán en hebras");
        especiero.add("Canela");
        especiero.add("Canela molida");
        especiero.add("Cayena");
        especiero.add("Clavos de olor");
        especiero.add("Coco deshidratado");
        especiero.add("Comino");
        especiero.add("Cúrcuma");
        especiero.add("Granos de cardamomo verde");
        especiero.add("Guindilla picada");
        especiero.add("Guindilla roja");
        especiero.add("Hojas de hierbabuena");
        especiero.add("Hojas de laurel");
        especiero.add("Hojas de perejil");
        especiero.add("Hojas de romero picadas");
        especiero.add("Hojas de tomillo");
        especiero.add("Jengibre");
        especiero.add("Nuez moscada");
        especiero.add("Orégano");
        especiero.add("Perejil picado");
        especiero.add("Pimentón picante");
        especiero.add("Pimienta");
        especiero.add("Romero");
        especiero.add("Semillas de cilantro");
        especiero.add("Tabasco");
        especiero.add("Tomillo");
        especiero.add("Vaina de vainilla");
        especiero.add("Vainas de cardamomo");

        //Nevera
        nevera.add("Anchoas");
        nevera.add("Boquerones");
        nevera.add("Brécol");
        nevera.add("Calabacines");
        nevera.add("Calabaza");
        nevera.add("Carne de cordero");
        nevera.add("Cebollino picado");
        nevera.add("Champiñones");
        nevera.add("Coliflor");
        nevera.add("Costilla de cerdo");
        nevera.add("Espinacas");
        nevera.add("Huevos");
        nevera.add("Leche entera");
        nevera.add("Mantequilla");
        nevera.add("Manzana asada");
        nevera.add("Membrillos");
        nevera.add("Mermelada de frutos rojos");
        nevera.add("Nata líquida");
        nevera.add("Nata para montar");
        nevera.add("Perejil");
        nevera.add("Queso suizo");
        nevera.add("Remolachas");
        nevera.add("Sardinas");
        nevera.add("Setas");
        nevera.add("Yemas");
        nevera.add("Yogur natural");
        nevera.add("Zumo de naranja");
    }
}