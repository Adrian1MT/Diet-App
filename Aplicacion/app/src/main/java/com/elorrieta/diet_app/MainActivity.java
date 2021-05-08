package com.elorrieta.diet_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;

public class MainActivity extends AppCompatActivity {
    ImageView Imagen, Titulo;
    RecyclerView oRecyclerView;
    ArrayList<Menu> menuArrayList;
    View view;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String fechahoy=new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        String horahoy=DateFormat.getTimeInstance().format(new Date());
        comprobarBBDD();
        //comprobarDietas(fechahoy,horahoy);
        Imagen= (ImageView)findViewById(R.id.imageView);
        Imagen.setImageResource(R.drawable.icono);

        Titulo= (ImageView)findViewById(R.id.imageView2);
        Titulo.setImageResource(R.drawable.nombre);

        Imagen.setOnClickListener(this::mover_ObjectAnimator);
        Titulo.setOnClickListener(this::mover_ObjectAnimator);

        // Llenamos el ArrayList.
        menuArrayList = new ArrayList<Menu>();
        menuArrayList.add(new Menu("Recetas"));
        menuArrayList.add(new Menu("Dietario"));
        menuArrayList.add(new Menu("Almacen / Compra"));

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

    // inicializarmos el adapter con nuestros datos.
    OnItemClickListener escuchador = new OnItemClickListener() {
        @Override
        public void onItemClick(Menu item) {
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
    public void comprobarDietas(String fechahoy, String horahoy){
        ArrayList<String> ingredientes = new ArrayList<String>();
        ArrayList<Integer> cantidad = new ArrayList<Integer>();

        boolean bucle=false;
        BBDD admin = new BBDD(this,"administracion",null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select nomIngrediente, sum(cantidad)cantidad from tiene as ti left join receta as re on re.id=ti.id " +
                "where nombre in (select nombre from contiene as con left join receta as re on re.id=con.id where dia>='29/1/2021' " +
                "and dia<='30/4/2021' ) GROUP BY nomIngrediente;", null);
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
        // Toast.makeText(this,"Hay "+resultados.size()+" cantidades ",Toast.LENGTH_SHORT).show();
        bd.close();
        admin.close();
    }
    public void insercion(){
        BBDD admin = new BBDD(this,"administracion", null,1);
        admin.rellenar(admin);
    }
}