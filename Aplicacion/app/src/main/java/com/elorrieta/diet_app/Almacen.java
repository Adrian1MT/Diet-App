package com.elorrieta.diet_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.elorrieta.diet_app.ui.main.AdapterListaRecetas;

import java.util.ArrayList;

public class Almacen extends AppCompatActivity {
    RecyclerView Especiero;
    RecyclerView Nevera;
    RecyclerView Congelador;
    RecyclerView Despensa;

    ArrayList<String> ESingrediente = new ArrayList<String>();
    ArrayList<String> ESCantidad = new ArrayList<String>();

    ArrayList<String> NEingrediente = new ArrayList<String>();
    ArrayList<String> NECantidad = new ArrayList<String>();

    ArrayList<String> DEingrediente = new ArrayList<String>();
    ArrayList<String> DECantidad = new ArrayList<String>();

    ArrayList<String> COingrediente = new ArrayList<String>();
    ArrayList<String> COCantidad = new ArrayList<String>();

    Spinner Almacenes,AlmacenesNUEVO;
    String[] Almacen;
    ArrayList<String> listaAlmacen = new ArrayList<String>();

    Spinner Ingredientes,IngredientesNUEVO;
    String[] Ingrediente,IngredienteNUEVO;
    ArrayList<String> ListaIngredientes = new ArrayList<String>();
    ArrayList<String> ListaIngredientesNUEVO = new ArrayList<String>();

    TextView textunidad, textCantidad, Texttiene,TextAdvertencia;
    TextView textunidadNEW, textCantidadNEW;

    Boolean Nuevo=false;

    Button BtnSumRest, BTNnuevo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_almacen);
        Especiero = (RecyclerView)findViewById(R.id.RecyEspeciero);
        Especiero.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        Especiero.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        Nevera = (RecyclerView)findViewById(R.id.RecyNevera);
        Nevera.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        Nevera.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        Congelador = (RecyclerView)findViewById(R.id.RecyCongelador);
        Congelador.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        Congelador.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        Despensa = (RecyclerView)findViewById(R.id.RecyDespensa);
        Despensa.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        Despensa.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        textunidad=findViewById(R.id.unidadtext);
        textCantidad=findViewById(R.id.TexCantidad);
        Texttiene=findViewById(R.id.Tienetext);

        TextAdvertencia=findViewById(R.id.MensajeAdvertencia);

        textunidadNEW=findViewById(R.id.unidadtext2);
        textunidadNEW.setVisibility(View.INVISIBLE);
        textCantidadNEW=findViewById(R.id.texCantidad2);
        textCantidadNEW.setVisibility(View.INVISIBLE);

        BtnSumRest=findViewById(R.id.BmasMenos);
        BTNnuevo=findViewById(R.id.btnNuevo);
        cargarAlmacen();

        Almacenes=findViewById(R.id.spinnerAlmacen);
        Ingredientes= (Spinner)findViewById(R.id.spinneringrediente);

        IngredientesNUEVO= (Spinner)findViewById(R.id.spinneringrediente2);
        IngredientesNUEVO.setVisibility(View.INVISIBLE);
        Almacen = new String[listaAlmacen.size()];
        Almacen = listaAlmacen.toArray(Almacen);

        Almacenes.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,Almacen));
        Almacenes.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,listaAlmacen));

        AlmacenesNUEVO=findViewById(R.id.spinnerAlmacen2);
        AlmacenesNUEVO.setVisibility(View.INVISIBLE);
        AlmacenesNUEVO.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,Almacen));
        AlmacenesNUEVO.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,listaAlmacen));

        ingrediente();

        Almacenes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ingrediente();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        Ingredientes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               String Ingrediente=Ingredientes.getSelectedItem().toString();
                String almacen=Almacenes.getSelectedItem().toString();
                contenido(Ingrediente,almacen);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        AlmacenesNUEVO.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ingredientenew();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        IngredientesNUEVO.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String Ingrediente=IngredientesNUEVO.getSelectedItem().toString();
                contenidoNEW(Ingrediente);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        ingredientenew();
    }
    public void cargarAlmacen(){
        especiero();
        Nevera();
        Congelador();
        Despensa();
        almacen();
    }
    public void Nuevo(View view){
        if(Nuevo==false){
            Nuevo=true;
            BTNnuevo.setBackgroundColor(0xffff8800);
            BTNnuevo.setText("Modificar cantidad");
            textunidadNEW.setVisibility(View.VISIBLE);
            textCantidadNEW.setVisibility(View.VISIBLE);
            AlmacenesNUEVO.setVisibility(View.VISIBLE);
            IngredientesNUEVO.setVisibility(View.VISIBLE);

            textunidad.setVisibility(View.INVISIBLE);
            textCantidad.setVisibility(View.INVISIBLE);
            Texttiene.setVisibility(View.INVISIBLE);
            BtnSumRest.setVisibility(View.INVISIBLE);
            Almacenes.setVisibility(View.INVISIBLE);
            Ingredientes.setVisibility(View.INVISIBLE);

            TextAdvertencia.setVisibility(View.INVISIBLE);
        }else{
            Nuevo=false;
            BTNnuevo.setBackgroundColor(getResources().getColor(R.color.verde_medio));
            BTNnuevo.setText("Nuevo ingrediente");
            AlmacenesNUEVO.setVisibility(View.INVISIBLE);
            IngredientesNUEVO.setVisibility(View.INVISIBLE);
            textunidadNEW.setVisibility(View.INVISIBLE);
            textCantidadNEW.setVisibility(View.INVISIBLE);

            textunidad.setVisibility(View.VISIBLE);
            textCantidad.setVisibility(View.VISIBLE);
            Texttiene.setVisibility(View.VISIBLE);
            BtnSumRest.setVisibility(View.VISIBLE);
            Almacenes.setVisibility(View.VISIBLE);
            Ingredientes.setVisibility(View.VISIBLE);
            ingrediente();
        }
    }
    public void TextoBoton(View view){
        if (BtnSumRest.getText().equals("+")){
            BtnSumRest.setText("-");
        }else{
            BtnSumRest.setText("+");
        }
    }
    public void Actualizar(View view){
        int resultado=0;
        int tiene=0;
        int insertado=0;
        BBDD admin = new BBDD(this,"administracion",null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        if(Nuevo==false){
        try {
            tiene=Integer.parseInt(Texttiene.getText().toString());
            if (textCantidad.length()==0){
                insertado=0;
            }else{
                insertado=Integer.parseInt(textCantidad.getText().toString());
            }
        } catch(NumberFormatException nfe) {
            Toast.makeText(this,"Error de insercion de cantidad",Toast.LENGTH_SHORT).show();
        }
        String Ingrediente=Ingredientes.getSelectedItem().toString();
        String almacen=Almacenes.getSelectedItem().toString();
        if (BtnSumRest.getText().equals("+")){
            resultado=tiene+insertado;
        }else{
            resultado=tiene-insertado;
            if (resultado<0){
                resultado=0;
            }
        }
       // Toast.makeText(this,tiene+" "+insertado,Toast.LENGTH_SHORT).show();
       ContentValues registro = new ContentValues();
       registro.put("cantidad",resultado);
        bd.update("hay",registro,"nomIngrediente='"+Ingrediente+"' and nomAlmacen='"+almacen+"'", null);
        textCantidad.setText("");
        }
        else{
            String unidad=textunidadNEW.getText().toString();
            try {
                if (textCantidadNEW.length()==0){
                    insertado=0;
                }else{
                    insertado=Integer.parseInt(textCantidadNEW.getText().toString());
                }
            } catch(NumberFormatException nfe) {
                Toast.makeText(this,"Error de insercion de cantidad",Toast.LENGTH_SHORT).show();
            }
            String Ingrediente=IngredientesNUEVO.getSelectedItem().toString();
            String almacen=AlmacenesNUEVO.getSelectedItem().toString();

            bd.execSQL("INSERT INTO hay(nomAlmacen,nomIngrediente,cantidad,unidad) VALUES ('"+almacen+"','"+Ingrediente+"',"+insertado+",'"+unidad+"')");
            textCantidadNEW.setText("");
        }
        bd.close();
        admin.close();
       cargarAlmacen();
       ingrediente();
        ingredientenew();
    }
    public void almacen(){
        listaAlmacen.clear();
        boolean bucle=false;
        BBDD admin = new BBDD(this,"administracion",
                null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select * from almacen", null);
        do{
            if (fila.moveToNext()){
                listaAlmacen.add(fila.getString(0));
            }else{
                bucle=true;
            }
        }while (bucle==false);
        bd.close();
        admin.close();
    }
    public void ingrediente(){
        ListaIngredientes.clear();
        String almacen=Almacenes.getSelectedItem().toString();;
        boolean bucle=false;
        BBDD admin = new BBDD(this,"administracion",
                null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select nomIngrediente from hay where nomAlmacen='"+almacen+"'", null);
        do{
            if (fila.moveToNext()){
                ListaIngredientes.add(fila.getString(0));
            }else{
                bucle=true;
            }
        }while (bucle==false);
        bd.close();
        admin.close();
        Ingrediente= new String[ListaIngredientes.size()];
        Ingrediente = ListaIngredientes.toArray(Ingrediente);

        Ingredientes.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,Ingrediente));
        Ingredientes.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,ListaIngredientes));

        int numero=ListaIngredientes.size();

        if (numero>0){
            Ingredientes.setVisibility(View.VISIBLE);
            textunidad.setVisibility(View.VISIBLE);
            textCantidad.setVisibility(View.VISIBLE);
            Texttiene.setVisibility(View.VISIBLE);
            BtnSumRest.setVisibility(View.VISIBLE);

            TextAdvertencia.setVisibility(View.INVISIBLE);
        }else{
            Ingredientes.setVisibility(View.INVISIBLE);
            textunidad.setVisibility(View.INVISIBLE);
            textCantidad.setVisibility(View.INVISIBLE);
            Texttiene.setVisibility(View.INVISIBLE);
            BtnSumRest.setVisibility(View.INVISIBLE);

            TextAdvertencia.setVisibility(View.VISIBLE);
        };
    }
    public void ingredientenew(){
        ListaIngredientesNUEVO.clear();
        String almacen=AlmacenesNUEVO.getSelectedItem().toString();;
        boolean bucle=false;
        BBDD admin = new BBDD(this,"administracion",
                null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select nomIngrediente from ingrediente where nomIngrediente not in (select nomIngrediente from hay where nomAlmacen='"+almacen+"')", null);
        do{
            if (fila.moveToNext()){
                ListaIngredientesNUEVO.add(fila.getString(0));
            }else{
                bucle=true;
            }
        }while (bucle==false);
        bd.close();
        admin.close();
        IngredienteNUEVO= new String[ListaIngredientesNUEVO.size()];
        IngredienteNUEVO = ListaIngredientesNUEVO.toArray(Ingrediente);

        IngredientesNUEVO.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,IngredienteNUEVO));
        IngredientesNUEVO.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,ListaIngredientesNUEVO));
    }
    public void especiero(){
        ESingrediente.clear();
        ESCantidad.clear();
        boolean bucle=false;
        BBDD admin = new BBDD(this,"administracion",
                null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select nomIngrediente,cantidad,unidad from hay where nomAlmacen='ESPECIERO'", null);
        do{
            if (fila.moveToNext()){
                ESingrediente.add(" - "+fila.getString(0));
                ESCantidad.add(fila.getString(1) + " "+ fila.getString(2));
            }else{
                bucle=true;
            }
        }while (bucle==false);
        bd.close();
        admin.close();
        AdapterIngredientes adapter = new AdapterIngredientes(ESingrediente, ESCantidad);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Ingrediente=ESingrediente.get(Especiero.getChildAdapterPosition(view));
                Ingrediente=Ingrediente.substring(3);
                String almacen="ESPECIERO";
                eliminarIngrediente(Ingrediente,almacen);
            }
        });
        Especiero.setAdapter(adapter);
    }
    public void Nevera(){
        NEingrediente.clear();
        NECantidad.clear();
        boolean bucle=false;
        BBDD admin = new BBDD(this,"administracion",
                null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select nomIngrediente,cantidad,unidad from hay where nomAlmacen='NEVERA'", null);
        do{
            if (fila.moveToNext()){
                NEingrediente.add(" - "+fila.getString(0));
                NECantidad.add(fila.getString(1) + " "+ fila.getString(2));
            }else{
                bucle=true;
            }
        }while (bucle==false);
        bd.close();
        admin.close();
        AdapterIngredientes adapter = new AdapterIngredientes(NEingrediente, NECantidad);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Ingrediente=NEingrediente.get(Nevera.getChildAdapterPosition(view));
                Ingrediente=Ingrediente.substring(3);
                String almacen="NEVERA";
                eliminarIngrediente(Ingrediente,almacen);
            }
        });
        Nevera.setAdapter(adapter);
    }
    public void Congelador(){
        COingrediente.clear();
        COCantidad.clear();
        boolean bucle=false;
        BBDD admin = new BBDD(this,"administracion",
                null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select nomIngrediente,cantidad,unidad from hay where nomAlmacen='CONGELADOR'", null);
        do{
            if (fila.moveToNext()){
                COingrediente.add(" - "+fila.getString(0));
                COCantidad.add(fila.getString(1) + " "+ fila.getString(2));
            }else{
                bucle=true;
            }
        }while (bucle==false);
        bd.close();
        admin.close();
        AdapterIngredientes adapter = new AdapterIngredientes(COingrediente, COCantidad);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Ingrediente=COingrediente.get(Congelador.getChildAdapterPosition(view));
                Ingrediente=Ingrediente.substring(3);
                String almacen="CONGELADOR";
                eliminarIngrediente(Ingrediente,almacen);
            }
        });
        Congelador.setAdapter(adapter);
    }
    public void Despensa(){
        DEingrediente.clear();
        DECantidad.clear();
        boolean bucle=false;
        BBDD admin = new BBDD(this,"administracion",
                null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select nomIngrediente,cantidad,unidad from hay where nomAlmacen='DESPENSA'", null);
        do{
            if (fila.moveToNext()){
                DEingrediente.add(" - "+fila.getString(0));
                DECantidad.add(fila.getString(1) + " "+ fila.getString(2));
            }else{
                bucle=true;
            }
        }while (bucle==false);
        bd.close();
        admin.close();
        AdapterIngredientes adapter = new AdapterIngredientes(DEingrediente, DECantidad);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Ingrediente=DEingrediente.get(Despensa.getChildAdapterPosition(view));
                Ingrediente=Ingrediente.substring(3);
                String almacen="DESPENSA";
                eliminarIngrediente(Ingrediente,almacen);
            }
        });
        Despensa.setAdapter(adapter);
    }

    public void contenido(String ingrediente,String almacen){
        boolean bucle=false;
        String unidad = "";
        String tiene = "";
        BBDD admin = new BBDD(this,"administracion",
                null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select unidad from ingrediente where nomIngrediente='"+ingrediente+"'", null);
        Cursor fila2 = bd.rawQuery("select cantidad from hay where nomIngrediente='"+ingrediente+"' and nomAlmacen='"+almacen+"'", null);
        do{
            if (fila.moveToNext() & fila2.moveToNext()){
                unidad=fila.getString(0);
                tiene=fila2.getString(0);
            }else{
                bucle=true;
            }
        }while (bucle==false);
        if (tiene.length()==0){
            tiene="0";
        }
        bd.close();
        admin.close();
        textunidad.setText(unidad);
        Texttiene.setText(tiene);
    }
    public void contenidoNEW(String ingrediente){
        boolean bucle=false;
        String unidad = "";
        BBDD admin = new BBDD(this,"administracion",
                null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select unidad from ingrediente where nomIngrediente='"+ingrediente+"'", null);
        do{
            if (fila.moveToNext()){
                unidad=fila.getString(0);
            }else{
                bucle=true;
            }
        }while (bucle==false);

        bd.close();
        admin.close();
        textunidadNEW.setText(unidad);

    }

    public void eliminarIngrediente(String ingrediente, String almacen) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);

        builder.setMessage("De verdad quieres eliminar el ingrediente "+ingrediente+" situado en "+almacen+"?")
                .setCancelable(false)
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        borrarIngrediente(ingrediente,almacen);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Acción para el botón 'NO'
                        dialog.cancel();
                    }
                });
        //Crea la caja de dialogo
        AlertDialog alert = builder.create();
        //Pongo el título manualmente
        alert.setTitle("¡CUIDADO!");
        alert.show();
    }
    public void borrarIngrediente(String ingrediente,String almacen){
        int eliminacion=0;
        BBDD admin = new BBDD(this,"administracion",
                null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        eliminacion=bd.delete("hay","nomIngrediente='"+ingrediente+"' and nomAlmacen='"+almacen+"'", null);
        bd.close();
        admin.close();
        if (eliminacion==0){
            Toast.makeText(this,"No se ha podido eliminar correctamente",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"El ingrediente "+ingrediente+" se ha podido eliminar de "+almacen,Toast.LENGTH_SHORT).show();
        }
        cargarAlmacen();
        ingrediente();
        ingredientenew();
    }

}