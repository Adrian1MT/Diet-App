package com.elorrieta.diet_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
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
    }

    public void relleno(){
        AdapterListaRecetas adapterReceta = new AdapterListaRecetas(NombreReceta);// cambiar eleccion
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
    public void insercion(View View){
        BBDD admin = new BBDD(this,"administracion", null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        bd.execSQL("INSERT INTO receta (nombre, elaboracion, foto, tiempo, dificultad, tipo, origen) VALUES ('ALCACHOFAS RELLENAS DE PAN Y AJO', 'Exprimir el limón en un bol con agua, y dejar las dos mitades dentro. Sacar las hojas exteriores de las alcachofas, quitarles los tallos -reservar para otra preparación-, cortar la mitad superior de las hojas y ponerlas en el bol con agua y limón para que no se oscurezcan (si se quiere). Pelar y picar el ajo y parte del perejil -reservar un poco para emplatar, si se quiere-, trocear el pan con un cuchillo, salpimentar y mezclar bien. Abrir las alcachofas con los dedos y, si tienen pelillos, retirarlos con ayuda de una puntilla o una cucharilla. Ponerlas en una cazuela, apoyadas en la base, y salpimentar. Rellenar con la farsa de pan, ajo y perejil, añadir a la cazuela el agua y el aceite, un poco más de sal y pimienta y llevar a ebullición a fuego medio-bajo, tapado con una tapa que encaje bien. Cocinar durante unos 30-40 minutos. A la mitad de la cocción, añadir el vino blanco y, si se quiere, el vinagre. Cuando ya estén tiernas y con las puntas bien asadas, subir el fuego a medio y destapar para conseguir que la base quede un poco crujiente y la salsa se reduzca. Servir calientes, si se quiere con perejil picado por encima.', 'Enlace', 60, 'BAJA', 'PRIMERO','VEGANA')");
        bd.execSQL("INSERT INTO receta (nombre, elaboracion, foto, tiempo, dificultad, tipo, origen) VALUES ('PERAS POCHADAS EN AZAFRÁN Y CARDAMOMO', 'Pelar las peras dejando el rabillo y sumergirlas en agua fría con unas rodajas de limón para evitar que se oxiden. Romper ligeramente los granos de cardamomo (yo usé un mortero) y añadirlos junto al resto de las especias (excepto el azafrán) y las pieles de cítricos a una olla. Tostar a fuego bajo-medio sin parar de mover hasta que huela. Añadir 250 g de azúcar blanco y suficiente agua para disolverlo. Triturar con ¼ de cucharadita de sal las hebras de azafrán. Añadir el azafrán, las peras y suficiente agua para cubrirlas. Llevar a ebullición y cocinar a fuego bajo, con tapa, aproximadamente unos 45 minutos, o hasta que las peras se vuelvan tiernas y melosas pero no se deshagan (vigilar a partir de los 30 minutos de cocción). Retirar las peras con cuidado y reducir el almíbar de azafrán, sin tapar, 10 minutos más con el fuego alto. Conservar en un tarro con el propio almíbar en el que las hemos cocinado. Se pueden consumir directamente, pero tras dos días estarán mejor.', 'Enlace', 60, 'BAJA', 'POSTRE', 'VEGANA')");
        bd.execSQL("INSERT INTO receta (nombre, elaboracion, foto, tiempo, dificultad, tipo, origen) VALUES ('LECHE FRITA CLÁSICA, CRUJIENTE Y AL HORNO', 'Untar una fuente cuadrada de unos 20x20cm (o rectangular de tamaño similar) con aceite de girasol. Mezclar la harina, la maicena, el azúcar y entre 200 y 250 mililitros de leche con una batidora. Llevar a hervor el resto de la leche con la canela, y el cardamomo, la vainilla y el anís estrellado si se tienen. Mantenerlo a fuego muy suave durante cinco minutos. Colar la leche con las especias sobre la mezcla de leche y harina. Devolver todo a la cazuela y cocer a fuego suave removiendo con una espátula unos 10 minutos, hasta que espese. Dejar que la mezcla se temple y verterla sobre la fuente. Cubrirla con plástico transparente en contacto con la crema. Cuando la crema se haya enfriado, ya se puede manipular, pero para que sea más fácil es conveniente dejarla en la nevera toda la noche. Pintar con un poco de aceite una tabla. Dar la vuelta a la fuente sobre la tabla y cortar la crema en cuadrados. Pasar los trozos de crema primero por harina y después por huevo batido, y freírlos a fuego medio en una cazuela con aceite abundante. Ponerlos en un plato con papel de cocina para que pierdan el exceso de grasa, y servir.', 'Enlace', 25, 'MEDIA', 'POSTRE', 'CASERO')");
        bd.execSQL("INSERT INTO receta (nombre, elaboracion, foto, tiempo, dificultad, tipo, origen) VALUES ('ENSALADILLA ROSA', 'Si se usan remolachas frescas, envolverlas en papel de aluminio, ponerlas en una fuente de horno y asarlas a 200 grados unos 45 minutos o hasta que estén tiernas (que se puedan atravesar fácilmente con un cuchillo). Dejar que se enfríen y reservar. Poner agua abundante a hervir con un puñado de sal. Lavar bien las patatas. Cocerlas enteras y sin pelar. A los 25 minutos, pincharlas. Si el pincho o cuchillo entra con facilidad, es que están. Si no dejarlas 5 minutos más. Usar el mismo agua para cocer dos huevos durante 10 minutos. Pasarlos por agua fría y pelarlos. Dejar que todo se enfríe. Hacer medio litro aproximadamente de mayonesa: poner las anchoas, un huevo, un chorro de vinagre y otro de aceite de oliva en un recipiente alto. Batir con la batidora hasta que se mezcle y emulsione. Ir añadiendo aceite de girasol a hilo mientras se sigue batiendo y moviendo la batidora arriba y abajo con suavidad. Corregir de vinagre y sal, y si está demasiado espesa, añadir un par de cucharadas de agua y remover bien. Pelar las patatas y las remolachas cortarlas en dados pequeños de 1 cm. aproximadamente. Juntarlas en un bol con las habitas, las aceitunas cortadas en dos mitades y el atún escurrido y desmigado. Rociar la ensaladilla con un chorrito de aceite de oliva y mezclar todo bien. Sumar la mitad de la mayonesa y mezclar. Ir añadiendo el resto de la mayonesa hasta dar con la densidad justa: ni muy pastosa ni demasiado cremosa o líquida. Mezclar bien, corregir de sal, meter en la nevera y servir fría con más aceitunas cortadas por encima si se quiere.', 'Enlace', 65, 'BAJA', 'ENTRANTE','VEGANA')");
        bd.execSQL("INSERT INTO receta (nombre, elaboracion, foto, tiempo, dificultad, tipo, origen) VALUES ('MANTEQUILLA DE HIERBAS', 'Lavar y picar las hierbas. Mezclar con la mantequilla en pomada, la piel de limón (solo la parte amarilla) sal, pimienta y, si se quiere, el ajo. Poner la mezcla en un trozo de plástico transparente, doblarlo y formar un rulo. Dejarlo en la nevera un par de horas. Consejos de uso: esta mantequilla será –con pan o tostadas– la mejor amiga de cualquier pescado ahumado, y sola la base para una salsatipomeunièrecon una vuelta o el aderezo de unosescargotsa la francesa.', 'Enlace', 140, 'MEDIA', 'ENTRANTE','CASERO')");
        bd.execSQL("INSERT INTO receta (nombre, elaboracion, foto, tiempo, dificultad, tipo, origen) VALUES ('GALLETAS DE QUESO, TOMATE SECO Y ORÉGANO', 'Remojar en agua tibia los tomates durante media hora, secarlos bien y picarlos finos con un cuchillo bien afilado. Poner la mantequilla con la harina tamizada, la levadura, el tomate y el queso en un bol y amasar hasta que se integren y quede una masa elástica y que no se pega en las manos (si tenéis un robot de cocina, podéis dejarle esta parte del trabajo). Estirar la masa entre dos papeles de horno hasta que tenga un grosor de unos 3 mm aproximadamente. Ponerla en la nevera unos 30 minutos para que sea más fácil de cortar. Con un cortapastas –o un cuchillo, o un vaso– cortarlas con la forma deseada y disponer en una bandeja para hornear, que meteremos en el horno calentado a 160 grados centígrados con calor arriba y abajo. Hornear las galletas unos 25 minutos o hasta que estén doradas y apetitosas, dándole la vuelta a la bandeja –lo de delante, atrás– más o menos a los 15 minutos para una cocción más uniforme. Lo ideal sería vigilarlas a partir de los 20 minutos, por aquello de que cada horno es un mundo. Cuando estén hechas, sacarlas del horno, dejar enfriar y comer o guardar en un recipiente hermético.', 'Enlace', 130, 'ALTA', 'ENTRANTE','CASERO')");

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