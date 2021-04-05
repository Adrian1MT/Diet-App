package com.elorrieta.diet_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

    boolean entrante=false;
    boolean primero=false;
    boolean segundo=false;
    boolean postre=false;

    boolean Menor15=false;
    boolean Entre15_30=false;
    boolean Mayor30=false;

    boolean alta=false;
    boolean media=false;
    boolean baja=false;

    String ContenidoBuscar="";
    String Filtro="";

    String vengoDe="";
    int soyElBoton;

    EditText Texto;
    Integer Id=0;
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
                if(Texto.getText().toString().trim().length()>0){
                    ContenidoBuscar=" where nombre LIKE '"+Texto.getText().toString().trim()+"%'";
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
                int opcion = RecyclerMenu.getChildAdapterPosition(view);
                verificar_menu(opcion);
            }
        });
        adapterTiempo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int opcion = RecyclerTiempo.getChildAdapterPosition(view);
                verificar_Tiempo(opcion);
            }
        });
        adapterDifilcutad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int opcion = RecyclerDificultad.getChildAdapterPosition(view);
                verificar_Difilcutad(opcion);
            }
        });

        RecyclerMenu.setAdapter(adapterMenu);
        RecyclerTiempo.setAdapter(adapterTiempo);
        RecyclerDificultad.setAdapter(adapterDifilcutad);
        relleno();

        //Recojo los valores de la pantalla y el botón de la dieta que busca receta
        vengoDe=getIntent().getStringExtra("activity");
        soyElBoton=getIntent().getIntExtra("button",0);


       /* RecyclerListaRecetas.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mensaje();
                return false;
            }
        });*/
    }
    public void mensaje(){
        AlertDialog.Builder msj = new AlertDialog.Builder(this);
        msj.setTitle(Html.fromHtml("<b>+Nombre+</b>"));
        msj.setMessage("ggggg");
        msj.setNeutralButton("R.string.Entendido", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });;
        AlertDialog mostrarDialogo =msj.create();
        mostrarDialogo.show();
    }
    public void relleno(){
        AdapterListaRecetas adapterReceta = new AdapterListaRecetas(NombreReceta);// cambiar eleccion
        adapterReceta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(vengoDe == null) {
                    detallesReceta(NombreReceta.get(RecyclerListaRecetas.getChildAdapterPosition(view)));
                } else {
                    try {
                        volverAdieta(NombreReceta.get(RecyclerListaRecetas.getChildAdapterPosition(view)),vengoDe,soyElBoton);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        RecyclerListaRecetas.setAdapter(adapterReceta);
    }
    public void verificar_menu(int opcion){
        Toast.makeText(this,opcion+" ",Toast.LENGTH_LONG).show();
        switch(opcion) {
            case 0:
                if (entrante==false){
                    entrante=true;
                }else{
                    entrante=false;
                }
                break;
            case 1:
                if (primero==false){
                    primero=true;
                }else{
                    primero=false;
                }
                break;
            case 2:
                if (segundo==false){
                    segundo=true;
                }else{
                    segundo=false;
                }
                break;
            case 3:
                if (postre==false){
                    postre=true;
                }else{
                    postre=false;
                }
                break;
        }
        Buscar();
    }
    public void verificar_Tiempo(int opcion){
        switch(opcion) {
            case 0:
                if (Menor15==false){
                    Menor15=true;
                }else{
                    Menor15=false;
                }
                Buscar();
                break;
            case 1:
                if (Entre15_30==false){
                    Entre15_30=true;
                }else{
                    Entre15_30=false;
                }
                break;
            case 2:
                if (Mayor30==false){
                    Mayor30=true;
                }else{
                    Mayor30=false;
                }
                break;
        }
        Buscar();
    }
    public void verificar_Difilcutad(int opcion){
        switch(opcion) {
            case 0:
                if (alta==false){
                    alta=true;
                }else{
                    alta=false;
                }
                break;
            case 1:
                if (media==false){
                    media=true;
                }else{
                    media=false;
                }
                break;
            case 2:
                if (baja==false){
                    baja=true;
                }else{
                    baja=false;
                }
                break;
        }
        Buscar();
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
    public void filtro(){
        if (ContenidoBuscar.length()>0){
            Filtro=" and";
        }else{
            Filtro=" where";
        }

        if (entrante==true){
            if(!Filtro.equals(" where")&&!Filtro.equals(" and")){
                Filtro+=" or tipo='ENTRANTE'";
            }else{
                Filtro+=" tipo='ENTRANTE'";
            }
        }
        if (primero==true){
            if(!Filtro.equals(" where")&&!Filtro.equals(" and")){
                Filtro+=" or tipo='PRIMERO'";
            }else{
                Filtro+=" tipo='PRIMERO'";
            }
        }
        if (segundo==true){
            if(!Filtro.equals(" where")&&!Filtro.equals(" and")){
                Filtro+=" or tipo='SEGUNDO'";
            }else{
                Filtro+=" tipo='SEGUNDO'";
            }
        }
        if (postre==true){
            if(!Filtro.equals(" where")&&!Filtro.equals(" and")){
                Filtro+=" or tipo='POSTRE'";
            }else{
                Filtro+=" tipo='POSTRE'";
            }
        }

        if (Menor15==true){
            if(!Filtro.equals(" where")&&!Filtro.equals(" and")){
                Filtro+=" or tiempo<=15";
            }else{
                Filtro+=" tiempo<=15";
            }
        }
       if (Entre15_30==true){
            if(!Filtro.equals(" where")&&!Filtro.equals(" and")){
                Filtro+=" or tiempo BETWEEN 15 and 30";
            }else{
                Filtro+=" tiempo BETWEEN 15 and 30";
            }
        }
        if (Mayor30==true){
            if(!Filtro.equals(" where")&&!Filtro.equals(" and")){
                Filtro+=" or tiempo>=30";
            }else{
                Filtro+=" tiempo>=30";
            }
        }

        if (alta==true){
            if(!Filtro.equals(" where")&&!Filtro.equals(" and")){
                Filtro+=" or dificultad='ALTA'";
            }else{
                Filtro+=" dificultad='ALTA'";
            }
        }
        if (media==true){
            if(!Filtro.equals(" where")&&!Filtro.equals(" and")){
                Filtro+=" or dificultad='MEDIA'";
            }else{
                Filtro+=" dificultad='MEDIA'";
            }
        }
        if (baja==true){
            if(!Filtro.equals(" where")&&!Filtro.equals(" and")){
                Filtro+=" or dificultad='BAJA'";
            }else{
                Filtro+=" dificultad='BAJA'";
            }
        }
        if(Filtro.equals(" where")||Filtro.equals(" and")){
            Filtro="";
        }
    }
    public void Buscar(){
        LimpiarArrays();
        filtro();
        boolean bucle=false;
        BBDD admin = new BBDD(this,"administracion",
                null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();
       // Cursor fila = bd.rawQuery("select id,nombre from receta where"+ContenidoBuscar, null);
       Cursor fila = bd.rawQuery("select id,nombre from receta "+ContenidoBuscar+Filtro, null);
        //Cursor fila = bd.rawQuery("select id,nombre from receta where tiempo<=15", null);
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


    public void detallesReceta(String nombreReceta){
        Intent i = new Intent(this, detallesReceta.class);
        i.putExtra("nombre", nombreReceta);
        startActivity(i);
    }

    public void volverAdieta(String nombreReceta, String activity, int boton) throws ClassNotFoundException {

        if(activity.contentEquals("DietaDiaria")) {
            Intent respReceta = new Intent();
            respReceta.putExtra("btn", boton);
            respReceta.putExtra("nomReceta", nombreReceta);
            setResult(2, respReceta);
            finish();
        }
    }

    public void recetas3(View View){
        BBDD admin = new BBDD(this,"administracion", null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("id", 1);
        registro.put("nombre", "Arroz con tomate");
        registro.put("tiempo", "10");
        registro.put("dificultad", "ALTA");
        registro.put("tipo", "PRIMERO");
        registro.put("elaboracion", "Ponte a hacer arroz y echale tomate ostia");
        registro.put("origen", "Marte");
        bd.insert("receta",null, registro);

        ContentValues registro2 = new ContentValues();
        registro2.put("id", 1);
        registro2.put("numComensales", 4);
        bd.insert("nComensales",null, registro2);

        ContentValues registro3 = new ContentValues();
        registro3.put("id", 1);
        registro3.put("numComensales", 4);
        registro3.put("cantidad", 100);
        registro3.put("nomIngrediente", "Arroz");
        bd.insert("tiene",null, registro3);

        ContentValues registro4 = new ContentValues();
        registro4.put("id", 1);
        registro4.put("numComensales", 4);
        registro4.put("cantidad", 50);
        registro4.put("nomIngrediente", "Tomate");
        bd.insert("tiene",null, registro4);

        ContentValues registro5 = new ContentValues();
        registro5.put("unidad", "gramos");
        registro5.put("nomIngrediente", "Arroz");
        bd.insert("ingrediente",null, registro5);

        ContentValues registro6 = new ContentValues();
        registro6.put("unidad", "gramos");
        registro6.put("nomIngrediente", "Tomate");
        bd.insert("ingrediente",null, registro6);

        ContentValues registro7 = new ContentValues();
        registro7.put("id", 2);
        registro7.put("nombre", "Huevo frito");
        registro7.put("tiempo", "30");
        registro7.put("dificultad", "BAJA");
        registro7.put("tipo", "SEGUNDO");
        registro7.put("elaboracion", "Rompe un huevo y frielo inutil");
        registro7.put("origen", "Luna");
        bd.insert("receta",null, registro7);

        ContentValues registro8 = new ContentValues();
        registro8.put("id", 2);
        registro8.put("numComensales", 1);
        bd.insert("nComensales",null, registro8);

        ContentValues registro9 = new ContentValues();
        registro9.put("id", 2);
        registro9.put("numComensales", 1);
        registro9.put("cantidad", 1);
        registro9.put("nomIngrediente", "Huevo");
        bd.insert("tiene",null, registro9);

        ContentValues registro10 = new ContentValues();
        registro10.put("unidad", "huevo");
        registro10.put("nomIngrediente", "Huevo");
        bd.insert("ingrediente",null, registro10);

        bd.close();
        admin.close();
    }

    public void INSERTARTEMPORAL(View View){//igual se borrara
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
            if (vueltas==5){
                Dificultad="ALTA";
                Tipo="PRIMERO";
            }else if (vueltas==10){
                 Dificultad="MEDIA";
                Tipo="SEGUNDO";
            } if (vueltas==15){
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