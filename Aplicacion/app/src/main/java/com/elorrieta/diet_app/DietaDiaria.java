package com.elorrieta.diet_app;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.elorrieta.diet_app.ui.main.dialog.DatePickerFragment;

import java.util.ArrayList;

public class DietaDiaria extends AppCompatActivity implements View.OnClickListener {
    String receta = "", textoBoton = "";
    int soyElBoton, id;
    Button boton, btnDesayuno, btnAlmuerzo, btnComida, btnMerienda, btnCena;
    Button btnLimpiarDieta, btnLimpiarMenu;
    Button btnGuardar;
    Button btnEliminarDieta;
    boolean limpiarMenuPulsado = false;
    EditText txtFecha;
    TextView txtDia;
    String origen = "", fecha = "", fechaAAAAMMDD = "";
    int anio, mes;
    String dia;
    //Array y variables para guardar los datos de la dieta
    int[] desayuno = new int[2];
    int cuentaPlatosDesayuno;
    int[] almuerzo = new int[2];
    int cuentaPlatosAlmuerzo;
    int[] comida = new int[3];
    int cuentaPlatosComida;
    int[] merienda = new int[2];
    int cuentaPlatosMerienda;
    int[] cena = new int[2];
    int cuentaPlatosCena;
    ArrayList<String> listaFechas = new ArrayList<String>();
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dieta_diaria);

        txtFecha = (EditText) findViewById(R.id.txtFecha);
        txtFecha.setOnClickListener(this);

        btnDesayuno = (Button) findViewById(R.id.btn10);
        btnAlmuerzo = (Button) findViewById(R.id.btn18);
        btnComida = (Button) findViewById(R.id.btn26);
        btnMerienda = (Button) findViewById(R.id.btn34);
        btnCena = (Button) findViewById(R.id.btn42);

        txtDia = (TextView) findViewById(R.id.txtDia);
        btnLimpiarMenu = (Button) findViewById(R.id.btnLimpiarMenu);
        btnLimpiarDieta = (Button) findViewById(R.id.btnLimpiarDieta);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);

        boton = (Button) findViewById(R.id.btnGuardar);
        boton.setEnabled(false);

        //Recojo los valores de el adapter que llama a esta activity
        origen = getIntent().getStringExtra("origen");
        fecha = getIntent().getStringExtra("fecha");

        btnEliminarDieta = (Button) findViewById(R.id.btnEliminar);
        //Si venimos del recicler de ver dietas, apagamos los botones "limpiar" y "guardar" y encendemos el de "eliminar dieta"
        if(origen.contentEquals("ver")) {
            btnLimpiarMenu.setEnabled(false);
            btnLimpiarMenu.setVisibility(View.INVISIBLE);
            btnLimpiarDieta.setEnabled(false);
            btnLimpiarDieta.setVisibility(View.INVISIBLE);
            btnGuardar.setEnabled(false);
            btnGuardar.setVisibility(View.INVISIBLE);
            txtFecha.setVisibility(View.INVISIBLE);
            btnDesayuno.setClickable(false);
            btnAlmuerzo.setClickable(false);
            btnComida.setClickable(false);
            btnMerienda.setClickable(false);
            btnCena.setClickable(false);

            txtDia.setVisibility(View.VISIBLE);
            txtDia.setText(fecha);
            btnEliminarDieta.setVisibility(View.VISIBLE);
            cargarDieta(fecha);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            receta = data.getStringExtra("nomReceta");
            soyElBoton = data.getIntExtra("btn", 0);
            boton = (Button) findViewById(soyElBoton);
            boton.setTextSize(10);
            textoBoton = boton.getText().toString();
            if (textoBoton.contentEquals("Menú")) {
                textoBoton = "----- 0 -----\n";
            }
            textoBoton += receta + "\n----- 0 -----\n";
            boton.setText(textoBoton);
            id = recuperarIdReceta(receta);

            introducirIdArrays(id, soyElBoton);
        }
    }

    public void elegirMenu(View poView) {
        if (limpiarMenuPulsado) {
            boton = (Button) findViewById(poView.getId());
            boton.setTextSize(14);
            boton.setText("Menú");
            if (boton.getId() == btnDesayuno.getId()) {
                cuentaPlatosDesayuno = 0;
                for(int i=0;i<desayuno.length;i++) {
                    desayuno[i] = 0;
                }
            }
            if (boton.getId() == btnAlmuerzo.getId()) {
                cuentaPlatosAlmuerzo = 0;
                for(int i=0;i<almuerzo.length;i++) {
                    almuerzo[i] = 0;
                }
            }
            if (boton.getId() == btnComida.getId()) {
                cuentaPlatosComida = 0;
                for(int i=0;i<comida.length;i++) {
                    comida[i] = 0;
                }
            }
            if (boton.getId() == btnMerienda.getId()) {
                cuentaPlatosMerienda = 0;
                for(int i=0;i<merienda.length;i++) {
                    merienda[i] = 0;
                }
            }
            if (boton.getId() == btnCena.getId()) {
                cuentaPlatosCena = 0;
                for(int i=0;i<cena.length;i++) {
                    cena[i] = 0;
                }
            }
        } else {
            Intent oIntent = new Intent(this, Visualizar_Recetas.class);

            String NameOriginActivity = this.getLocalClassName().toString();
            int OriginButtonId = poView.getId();

            oIntent.putExtra("activity", NameOriginActivity);
            oIntent.putExtra("button", OriginButtonId);
            startActivityForResult(oIntent, 2);
        }
    }

    public void limpiarMenu(View poView) {
        limpiarMenuPulsado = !limpiarMenuPulsado;
        if (limpiarMenuPulsado) {
            findViewById(R.id.btnLimpiarMenu).setBackgroundColor(0xffff8800);
            btnDesayuno.setClickable(true);
            btnAlmuerzo.setClickable(true);
            btnComida.setClickable(true);
            btnMerienda.setClickable(true);
            btnCena.setClickable(true);
            btnLimpiarDieta.setEnabled(false);
        } else {
            findViewById(R.id.btnLimpiarMenu).setBackgroundColor(0xFF4CAF50);
            btnLimpiarDieta.setEnabled(true);
        }
    }

    public void limpiarDieta(View poView) {
        //Array con los botones que hay en la pantalla
        Button[] BtnArray = new Button[]{
                (Button) findViewById(R.id.btn10),
                (Button) findViewById(R.id.btn18),
                (Button) findViewById(R.id.btn26),
                (Button) findViewById(R.id.btn34),
                (Button) findViewById(R.id.btn42)};
        //Los recorre y los limpia
        for (int i = 0; i < BtnArray.length; i++) {
            boton = BtnArray[i];
            boton.setTextSize(14);
            boton.setText("Menú");
            boton.setClickable(true);
        }
        //Limpia los contadores
        cuentaPlatosDesayuno = 0;
        cuentaPlatosAlmuerzo = 0;
        cuentaPlatosComida = 0;
        cuentaPlatosMerienda = 0;
        cuentaPlatosCena = 0;
        //Limpia los arrays
        for(int i=0;i<desayuno.length;i++) {
            desayuno[i] = 0;
            almuerzo[i] = 0;
            merienda[i] = 0;
            cena[i] = 0;
        }
        for(int i=0;i<comida.length;i++) {
            comida[i] = 0;
        }
    }

    public void cargarDieta(String fecha){
        String des="----- 0 -----\n", alm="----- 0 -----\n",
                com="----- 0 -----\n", mer="----- 0 -----\n", cen="----- 0 -----\n";
        BBDD admin = new BBDD(this,"administracion", null,1);
        SQLiteDatabase dietaDiaria = admin.getWritableDatabase();
        // Cargamos los distintos menús
        Cursor a = dietaDiaria.rawQuery("select dia, tipoComida, id from contiene", null);
        if (a != null) {
            if (a.moveToFirst()) {
                a.moveToFirst();
                do {
                    if(a.getString(0).contentEquals(fecha_AAAA_MM_DD(fecha))){
                        if(a.getString(1).contentEquals("Desayuno")){
                            des += cargarReceta(a.getInt(2))+"\n----- 0 -----\n";
                        } else if(a.getString(1).contentEquals("Almuerzo")){
                            alm += cargarReceta(a.getInt(2))+"\n----- 0 -----\n";
                        } else if(a.getString(1).contentEquals("Comida")){
                            com += cargarReceta(a.getInt(2))+"\n----- 0 -----\n";
                        } else if(a.getString(1).contentEquals("Merienda")){
                            mer += cargarReceta(a.getInt(2))+"\n----- 0 -----\n";
                        } else if(a.getString(1).contentEquals("Cena")){
                            cen += cargarReceta(a.getInt(2))+"\n----- 0 -----\n";
                        }
                    }
                    listaFechas.add(a.getString(0));
                } while (a.moveToNext());
            }
        }
        btnDesayuno.setTextSize(10);
        btnDesayuno.setText(des);
        btnAlmuerzo.setTextSize(10);
        btnAlmuerzo.setText(alm);
        btnComida.setTextSize(10);
        btnComida.setText(com);
        btnMerienda.setTextSize(10);
        btnMerienda.setText(mer);
        btnCena.setTextSize(10);
        btnCena.setText(cen);
    }

    public String cargarReceta(int idReceta){
        String receta = "";
        BBDD admin = new BBDD(this,"administracion", null,1);
        SQLiteDatabase dietaDiaria = admin.getWritableDatabase();

        // Guardamos en una lista todas las fechas existentes en la BBDD
        Cursor a = dietaDiaria.rawQuery("select nombre from receta where id = '"+idReceta+"'", null);
        if (a != null) {
            if (a.moveToFirst()) {
                a.moveToFirst();
                do {
                    receta = a.getString(0);
                } while (a.moveToNext());
            }
        }
        return receta;
    }

    //Elimina la dieta mostrando mensaje de confirmación
    public void eliminarDieta(View poView) {
        builder = new AlertDialog.Builder(this);

        builder.setMessage("De verdad quieres eliminar las dieta de la BBDD ?")
                .setCancelable(false)
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        borrarDieta(poView);
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

    public void borrarDieta(View poView){
        BBDD admin = new BBDD(this,"administracion", null,1);
        SQLiteDatabase dietaDiaria = admin.getWritableDatabase();
        dietaDiaria.execSQL("DELETE FROM fecha WHERE dia LIKE '" + fecha_AAAA_MM_DD(fecha) +"'");
        dietaDiaria.execSQL("DELETE FROM contiene WHERE dia LIKE '" + fecha_AAAA_MM_DD(fecha) +"'");
        dietaDiaria.close();
        limpiarDieta(poView);
        txtDia.setText("");
        //Mensaje de éxito y vuelve a la pantalla del dietario
        Toast.makeText(this,
                "Dieta Diaria con fecha " + fecha + " eliminada con éxito!", Toast.LENGTH_LONG).show();
        Intent oIntento = new Intent(this, Dietario.class);
        startActivity(oIntento);
        finish();
    }

    public void guardarDieta(View poView) {
        BBDD admin = new BBDD(this,"administracion", null,1);
        SQLiteDatabase dietaDiaria = admin.getWritableDatabase();
//        // Otra forma de hacer un insert
//        ContentValues valor = new ContentValues();
//        valor.put("nomDieta", "Dieta Diaria");
//        dietaDiaria.insert("dieta", null, valor);

        // Guardamos en una lista todas las fechas existentes en la BBDD
        Cursor a = dietaDiaria.rawQuery("select dia from fecha", null);
        if (a != null) {
            if (a.moveToFirst()) {
                a.moveToFirst();
                do {
                    listaFechas.add(a.getString(0));
                } while (a.moveToNext());
            }
        }


        if(listaFechas.contains(txtFecha.getText().toString())) {
            //Mensaje de error
            Toast.makeText(this,
                    "Dieta Diaria con fecha " + fecha_DD_MM_AAAA(fecha_AAAA_MM_DD(txtFecha.getText().toString())) + " ya existe en la BBDD!", Toast.LENGTH_LONG).show();
        } else {
            fechaAAAAMMDD = fecha_AAAA_MM_DD(txtFecha.getText().toString());
            dietaDiaria.execSQL("INSERT INTO fecha (dia, nomDieta) VALUES ('" + fechaAAAAMMDD + "', 'Dieta Diaria')");

            for (int i = 0; i < desayuno.length; i++) {
                if (desayuno[i] != 0) {
                    dietaDiaria.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta Diaria' , '" + fechaAAAAMMDD + "', 'Desayuno'," + desayuno[i] + " )");
                }
            }
            for (int i = 0; i < almuerzo.length; i++) {
                if (almuerzo[i] != 0) {
                    dietaDiaria.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta Diaria' , '" + fechaAAAAMMDD + "', 'Almuerzo'," + almuerzo[i] + " )");
                }
            }
            for (int i = 0; i < comida.length; i++) {
                if (comida[i] != 0) {
                    dietaDiaria.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta Diaria' , '" + fechaAAAAMMDD + "', 'Comida'," + comida[i] + " )");
                }
            }
            for (int i = 0; i < merienda.length; i++) {
                if (merienda[i] != 0) {
                    dietaDiaria.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta Diaria' , '" + fechaAAAAMMDD + "', 'Merienda'," + merienda[i] + " )");
                }
            }
            for (int i = 0; i < cena.length; i++) {
                if (cena[i] != 0) {
                    dietaDiaria.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta Diaria' , '" + fechaAAAAMMDD + "', 'Cena'," + cena[i] + " )");
                }
            }
            dietaDiaria.close();
            //Mensaje de éxito y vuelve a la pantalla del dietario
            Toast.makeText(this,
                    "Dieta Diaria con fecha " + fecha_DD_MM_AAAA(fecha_AAAA_MM_DD(txtFecha.getText().toString())) + " guardada con éxito!", Toast.LENGTH_LONG).show();
            Intent oIntento = new Intent(this, Dietario.class);
            startActivity(oIntento);
            finish();
        }
    }

    @Override
    public void onClick(View poView) {
        switch (poView.getId()) {
            case R.id.txtFecha:
                mostrarCalendarioDialog();
                break;
        }
    }

    private void mostrarCalendarioDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                //Asigno los datos locales a globales, para usarlos en otros métodos
                anio = year;
                mes = month;
                // para días del 1 al 9, le añado un 0 delante (para la gestión de fechas)
                if (day<10){
                    dia = "0" + String.valueOf(day);
                } else {
                    dia = String.valueOf(day);
                }
                // +1 en el mes porque Enero es 0
                final String selectedDate = dia + " / " + (month + 1) + " / " + year;
                txtFecha.setText(fecha_DD_MM_AAAA(fecha_AAAA_MM_DD(selectedDate)));
                findViewById(R.id.btnGuardar).setEnabled(true);
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    //OBTIENE LA ID DE LA RECETA, en fonción del nombre
    public int recuperarIdReceta(String nombre) {
        int recetaId=-1;
        BBDD recuperarId = new BBDD(this, "administracion", null, 1);
        SQLiteDatabase bd = recuperarId.getWritableDatabase();
        Cursor c = bd.rawQuery("select id from receta where nombre='"+ nombre +"'", null);
        if (c != null) {
            if (c.moveToFirst()) {
                c.moveToFirst();
                do {
                    recetaId = c.getInt(0);
                } while (c.moveToNext());
            }
        }
        return recetaId;
    }

    //GUARDAMOS LOS IDs DE RECETAS EN EL ARRAY CORRESPONDIENTE
    public void introducirIdArrays(int id, int idBoton) {
        if (idBoton == btnDesayuno.getId()) {
            for(int i=0;i<desayuno.length;i++) {
                if (desayuno[i] == 0) {
                    desayuno[i] = id;
                    cuentaPlatosDesayuno ++;
                    break;
                }
            }
            if(cuentaPlatosDesayuno == 2){
                Toast.makeText(this,"Número máximo de platos alcanzado", Toast.LENGTH_LONG).show();
                btnDesayuno.setClickable(false);
            }
        }
        if (idBoton == btnAlmuerzo.getId()) {
            for(int i=0;i<almuerzo.length;i++) {
                if (almuerzo[i] == 0) {
                    almuerzo[i] = id;
                    cuentaPlatosAlmuerzo ++;
                    break;
                }
            }
            if(cuentaPlatosAlmuerzo == 2){
                Toast.makeText(this,"Número máximo de platos alcanzado", Toast.LENGTH_LONG).show();
                btnAlmuerzo.setClickable(false);
            }
        }
        if (idBoton == btnComida.getId()) {
            for(int i=0;i<comida.length;i++) {
                if (comida[i] == 0) {
                    comida[i] = id;
                    cuentaPlatosComida ++;
                    break;
                }
            }
            if(cuentaPlatosComida == 3){
                Toast.makeText(this,"Número máximo de platos alcanzado", Toast.LENGTH_LONG).show();
                btnComida.setClickable(false);
            }
        }
        if (idBoton == btnMerienda.getId()) {
            for(int i=0;i<merienda.length;i++) {
                if (merienda[i] == 0) {
                    merienda[i] = id;
                    cuentaPlatosMerienda ++;
                    break;
                }
            }
            if(cuentaPlatosMerienda == 2){
                Toast.makeText(this,"Número máximo de platos alcanzado", Toast.LENGTH_LONG).show();
                btnMerienda.setClickable(false);
            }
        }
        if (idBoton == btnCena.getId()) {
            for(int i=0;i<cena.length;i++) {
                if (cena[i] == 0) {
                    cena[i] = id;
                    cuentaPlatosCena ++;
                    break;
                }
            }
            if(cuentaPlatosCena == 2){
                Toast.makeText(this,"Número máximo de platos alcanzado", Toast.LENGTH_LONG).show();
                btnCena.setClickable(false);
            }
        }

    }

    public String fecha_AAAA_MM_DD(String fechaDD_MM_AAA) {
        String fecha_AAAA_MM_DD = "";

        String[] fechaArray = fechaDD_MM_AAA.split(" / ");
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