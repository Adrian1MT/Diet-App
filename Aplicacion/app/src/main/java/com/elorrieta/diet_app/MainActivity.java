package com.elorrieta.diet_app;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    ImageView Imagen, Titulo;
    RecyclerView oRecyclerView;
   // ArrayList<Menu> menuArrayList;
    ArrayList<MenuItem> menuArrayList;
    View view;
    String fechaAntes="",horaantes="",sql="";
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String fechahoy=new SimpleDateFormat("yyyy / MM / dd").format(new Date());
        String horahoy= DateFormat.getTimeInstance().format(new Date());
        comprobarBBDD();
        comprobarActualizacion();
        SetenciaWHere(fechahoy,horahoy);
        comprobarDietas(fechahoy,horahoy);
        Imagen= (ImageView)findViewById(R.id.imageView);
        Imagen.setImageResource(R.drawable.icono);

        Titulo= (ImageView)findViewById(R.id.imageView2);
        Titulo.setImageResource(R.drawable.nombre);

        Imagen.setOnClickListener(this::mover_ObjectAnimator);
        Titulo.setOnClickListener(this::mover_ObjectAnimator);

        // Llenamos el ArrayList.
        menuArrayList = new ArrayList<MenuItem>();
        menuArrayList.add(new MenuItem("Recetas"));
        menuArrayList.add(new MenuItem("Dietario"));
        menuArrayList.add(new MenuItem("Almacen / Compra"));

        oRecyclerView = (RecyclerView) findViewById(R.id.RecyclerCargarDieta);

        MenuAdapter ma = new MenuAdapter(menuArrayList, escuchador);
        oRecyclerView.setAdapter(ma);

        // establecemos el Layout Manager.
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        oRecyclerView.setLayoutManager(llm);
        oRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        oRecyclerView.setVisibility(View.INVISIBLE);
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

    // inicializarmos el adapter con nuestros datos.
    OnItemClickListener escuchador = new OnItemClickListener() {
        @Override
        public void onItemClick(MenuItem item) {
            view = new View(MainActivity.super.getApplicationContext());
            if(item.getItem().contentEquals("Recetas")){
                Recetas(view);
            } else if (item.getItem().contentEquals("Dietario")) {
                Dietario(view);
            } else {
                ListaCompra(view);
            }
        }
    };

    public void mover_ObjectAnimator(View v) {
        ObjectAnimator oObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(
                Imagen,
                PropertyValuesHolder.ofFloat("alpha", 1f,0f),
                PropertyValuesHolder.ofFloat("translationX", -1150),
                PropertyValuesHolder.ofFloat("translationY", -350),
                PropertyValuesHolder.ofFloat("scaleX", 0.2f),
                PropertyValuesHolder.ofFloat("scaleY", 0.2f)
        );
        ObjectAnimator oObjectAnimator2 = ObjectAnimator.ofPropertyValuesHolder(
                Titulo,
                PropertyValuesHolder.ofFloat("alpha", 1f,0f),
                PropertyValuesHolder.ofFloat("translationX", -1140),
                PropertyValuesHolder.ofFloat("translationY", -800),
                PropertyValuesHolder.ofFloat("scaleX", 0.4f),
                PropertyValuesHolder.ofFloat("scaleY", 0.4f)
        );
        ObjectAnimator oObjectAnimator3 = ObjectAnimator.ofPropertyValuesHolder(
                oRecyclerView,
                PropertyValuesHolder.ofFloat("alpha", 0f,1f),
                PropertyValuesHolder.ofFloat("translationX", 200),
                PropertyValuesHolder.ofFloat("scaleX", 0f, 1.2f),
                PropertyValuesHolder.ofFloat("scaleY", 0f, 1.2f)
        );
        oObjectAnimator.setDuration(3000L);
        oObjectAnimator.setStartDelay(500L);

        oObjectAnimator2.setDuration(3000L);
        oObjectAnimator2.setStartDelay(500L);

        oObjectAnimator3.setDuration(3000L);
        oObjectAnimator3.setStartDelay(500L);

        oObjectAnimator.start();
        oObjectAnimator2.start();
        oRecyclerView.setVisibility(View.VISIBLE);
        oObjectAnimator3.start();
    }

    public void Recetas(View poView){
        Intent oIntent = new Intent(this, Visualizar_Recetas.class);
        startActivity(oIntent);
    }

    public void Dietario(View poView){
        Intent oIntent = new Intent(this, Dietario.class);
        startActivity(oIntent);
    }

    public void ListaCompra(View poView){
        Intent oIntent = new Intent(this, FiltroListaCompraAlmacen.class);
        startActivity(oIntent);
    }

    public void comprobarBBDD(){
        ArrayList<Integer> IDs = new ArrayList<Integer>();
        IDs.add(1);
        boolean bucle=false;
        BBDD admin = new BBDD(this,"administracion",null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select id from receta", null);
        do{
            if (fila.moveToNext()){
                IDs.add(fila.getInt(0));
            }else{
                bucle=true;
            }
        }while (bucle==false);
        bd.close();
        admin.close();
        if (IDs.size()<2){
            insercion();
        }
    }

    public void comprobarActualizacion(){
        boolean bucle=false;
        BBDD admin = new BBDD(this,"administracion",null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("SELECT dia, hora from Actualizar;", null);
        do{
            if (fila.moveToNext()){
                fechaAntes=fila.getString(0);
                horaantes=fila.getString(1);
            }else{
                bucle=true;
            }
        }while (bucle==false);
        bd.close();
        admin.close();
    }
    public void SetenciaWHere(String fechahoy, String horahoy){
        //09:00:00 Desayuno, 12:00:00 Almuerzo, 15:00:00 Comida, 18:00:00 Merienda, 22:00:00 Cena
        String ComidaActualizada="";

        int CambiarDia=Integer.parseInt(fechahoy.substring(12,14))-1;
        String fechaAntesDeHoy=fechahoy.substring(0,12)+CambiarDia;

        int CambiarDia2=Integer.parseInt(fechahoy.substring(12,14))+1;
        String fechaDespuesDeAntes=fechaAntes.substring(0,12)+CambiarDia2;

        String comversionHoraHoy=horahoy.substring(0,2);
        int Hhoy =0;
        int HAntes=0;
        if(comversionHoraHoy.contains(":")){
            Hhoy = Integer.parseInt(horahoy.substring(0, 1));
        }else {
           Hhoy = Integer.parseInt(horahoy.substring(0, 2));
        }

        String comversionHoraantes=horaantes.substring(0,2);
        if(comversionHoraantes.contains(":")){
            HAntes = Integer.parseInt(horaantes.substring(0, 1));
        }else {
            HAntes = Integer.parseInt(horaantes.substring(0, 2));
        }

        String comido = null;
        if (HAntes>=22){
            comido="tipoComida<>'Desayuno' and tipoComida<>'Almuerzo' and tipoComida<>'Comida' and tipoComida<>'Merienda' and tipoComida<>'Cena'";
        } else if (HAntes>=18){
            comido="tipoComida<>'Desayuno' and tipoComida<>'Almuerzo' and tipoComida<>'Comida' and tipoComida<>'Merienda'";
        } else if (HAntes>=15){
            comido="tipoComida<>'Desayuno' and tipoComida<>'Almuerzo' and tipoComida<>'Comida'";
        } else if (HAntes>=12){
            comido="tipoComida<>'Desayuno' and tipoComida<>'Almuerzo'";
        } else if (HAntes>=9){
            comido="tipoComida<>'Desayuno'";
        } else if (HAntes<9){
            comido="tipoComida<>''";
        }

        String Pcomer = null;
        if (Hhoy<9){
            Pcomer="tipoComida<>'Desayuno' and tipoComida<>'Almuerzo' and tipoComida<>'Comida' and tipoComida<>'Merienda' and tipoComida<>'Cena'";
        } else if (Hhoy<12){
            Pcomer="tipoComida<>'Almuerzo' and tipoComida<>'Comida' and tipoComida<>'Merienda' and tipoComida<>'Cena'";
        } else if (Hhoy<15){
            Pcomer="tipoComida<>'Comida' and tipoComida<>'Merienda' and tipoComida<>'Cena'";
        } else if (Hhoy<18){
            Pcomer="tipoComida<>'Merienda' and tipoComida<>'Cena'";
        } else if (Hhoy<22){
            Pcomer="tipoComida<>'Cena'";
        }else if (Hhoy>=22){
            Pcomer="tipoComida<>''";
        }

        if(fechahoy.equals(fechaAntes)){
            ComidaActualizada= comido +" and "+ Pcomer;
            sql="select nomIngrediente, sum(cantidad)cantidad from tiene as ti left join receta as re on re.id=ti.id " +
                    "where nombre in (select nombre from contiene as con left join receta as re on re.id=con.id where " +
                    "dia='"+fechaAntes+"' and ("+ComidaActualizada+")" +
                    " ) GROUP BY nomIngrediente;";
        } else if(fechaAntesDeHoy.equals(fechaAntes)){
            sql="select nomIngrediente, sum(cantidad)cantidad from tiene as ti left join receta as re on re.id=ti.id " +
                    "where nombre in (select nombre from contiene as con left join receta as re on re.id=con.id " +
                    "where  (dia='"+fechaAntes+"' and ("+comido+")) or (dia='"+fechahoy+"' and ("+Pcomer+"))) " +
                    "GROUP BY nomIngrediente;";
        }else{
            sql="select nomIngrediente, sum(cantidad)cantidad from tiene as ti left join receta as re on re.id=ti.id " +
                    "where nombre in (select nombre from contiene as con left join receta as re on re.id=con.id " +
                    "where  (dia BETWEEN '"+fechaDespuesDeAntes+"' and '"+fechaAntesDeHoy+"') or (dia='"+
                    fechaAntes+"' and ("+comido+")) or (dia='"+fechahoy+"' and ("+Pcomer+"))) " +
                    "GROUP BY nomIngrediente;";
        }

    }
    public void comprobarDietas(String fechahoy, String horahoy){
        ArrayList<String> ingredientes = new ArrayList<String>();
        ArrayList<Integer> cantidad = new ArrayList<Integer>();

        SetenciaWHere(fechahoy,horahoy);

        boolean bucle=false;
        BBDD admin = new BBDD(this,"administracion",null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery(sql, null);
        do{
            if (fila.moveToNext()){
                ingredientes.add(fila.getString(0));
                cantidad.add(fila.getInt(1));
            }else{
                bucle=true;
            }
        }while (bucle==false);
        bd.close();
        admin.close();
        int numero=ingredientes.size();
        if (numero>0){
            comprobarAlmacen(ingredientes,cantidad);
        }
        RegistrarUpdate(fechahoy,horahoy);
    }
    public void comprobarAlmacen(ArrayList<String> ingredientes, ArrayList<Integer> cantidad){
       // Toast.makeText(this,"Hay "+ingredientes.size()+" ingredientes y cantidades "+cantidad.size() ,Toast.LENGTH_SHORT).show();
        ArrayList<String> IngreAlmacen = new ArrayList<String>();
        ArrayList<Integer> CantiAlmacen = new ArrayList<Integer>();
        int numero=0;
        do{
        boolean bucle=false;
        BBDD admin = new BBDD(this,"administracion",null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select nomIngrediente, sum(cantidad)cantidad from hay " +
                "where nomIngrediente='"+ingredientes.get(numero).toString()+"'" +
                "GROUP BY nomIngrediente", null);
        do{
            //En caso del que el cursor este vacio
            if (fila.getCount()==0){
                IngreAlmacen.add("Nada");
                CantiAlmacen.add(0);
            }
            if (fila.moveToNext()){
                    IngreAlmacen.add(fila.getString(0));
                    CantiAlmacen.add(fila.getInt(1));
            }else{
                bucle=true;
            }
        }while (bucle==false);
        bd.close();
        admin.close();
            numero=numero+1;
        }while (numero<ingredientes.size());
      //  Toast.makeText(this,"Hay "+IngreAlmacen.size()+" ingredientes y cantidades "+CantiAlmacen.size() ,Toast.LENGTH_SHORT).show();
        int comprobar=IngreAlmacen.size();
        if (comprobar>0){
            restarAlmacen(ingredientes,cantidad,CantiAlmacen);
        }
    }
    public void restarAlmacen(ArrayList<String> ingredientes, ArrayList<Integer> cantidad,ArrayList<Integer> CantAlmacen){
        ArrayList<Integer> resultados = new ArrayList<Integer>();

        BBDD admin = new BBDD(this,"administracion",null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        int numero=0;
        int cantidadtotal,cantidadAlmacen, resultado;
        do{
            cantidadtotal=cantidad.get(numero);
            cantidadAlmacen=CantAlmacen.get(numero);
            resultado=cantidadAlmacen-cantidadtotal;
            if(resultado<0){
                resultado=0;
            }
            resultados.add(resultado);
            numero=numero+1;
        }while (numero<ingredientes.size());
        numero=0;
        do{
        ContentValues registro = new ContentValues();
        registro.put("cantidad",resultados.get(numero));
        bd.update("hay",registro,"nomIngrediente='"+ingredientes.get(numero).toString()+"'", null);
        numero=numero+1;
        }while (numero<resultados.size());
        bd.close();
        admin.close();
    }
    public void RegistrarUpdate(String fechahoy, String horahoy){
        BBDD admin = new BBDD(this,"administracion",null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("dia",fechahoy);
        registro.put("hora",horahoy);
        bd.update("Actualizar",registro,"id=1", null);
        bd.close();
        admin.close();
    }
    public void insercion(){
        BBDD admin = new BBDD(this,"administracion", null,1);
        admin.rellenar(admin);
    }

    public void lanzarAcercaDe(View view) {
        Intent i = new Intent(this, AcercaDeActivity.class);
        startActivity(i);
    }
}