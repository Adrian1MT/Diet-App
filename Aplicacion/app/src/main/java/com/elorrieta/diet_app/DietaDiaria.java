package com.elorrieta.diet_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.elorrieta.diet_app.ui.main.dialog.DatePickerFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static java.security.AccessController.getContext;

public class DietaDiaria extends AppCompatActivity implements View.OnClickListener {
    String receta = "", textoBoton = "";
    String dia = "";
    int soyElBoton, id;
    Button boton, btnDesayuno, btnAlmuerzo, btnComida, btnMerienda, btnCena;
    Button btnLimpiarDieta;
    boolean limpiarMenuPulsado = false;
    EditText txtFecha;
    String diaSemana = "";
    int anio, mes, diaDelMes;
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

        btnLimpiarDieta = (Button) findViewById(R.id.btnLimpiarDieta);

        boton = (Button) findViewById(R.id.btnGuardar);
        boton.setEnabled(false);
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
            String[] txtView = (poView.toString()).split("/");
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
        for (int i = 0; i < 5; i++) {
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

    public void guardarDieta(View poView) {
//        // SACAMOS EL DIA DE LA SEMANA, a partir de la fecha
//        diaSemana = diaDeLaSemana(anio, mes, diaDelMes);


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
                    "Dieta Diaria con fecha " + txtFecha.getText().toString() + " ya existe en la BBDD!", Toast.LENGTH_LONG).show();
        } else {
            dietaDiaria.execSQL("INSERT INTO fecha (dia, nomDieta) VALUES ('" + txtFecha.getText().toString() + "', 'Dieta Diaria')");

            for (int i = 0; i < desayuno.length; i++) {
                if (desayuno[i] != 0) {
                    dietaDiaria.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta Diaria' , '" + txtFecha.getText().toString() + "', 'Desayuno'," + desayuno[i] + " )");
                }
            }
            for (int i = 0; i < almuerzo.length; i++) {
                if (almuerzo[i] != 0) {
                    dietaDiaria.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta Diaria' , '" + txtFecha.getText().toString() + "', 'Almuerzo'," + almuerzo[i] + " )");
                }
            }
            for (int i = 0; i < comida.length; i++) {
                if (comida[i] != 0) {
                    dietaDiaria.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta Diaria' , '" + txtFecha.getText().toString() + "', 'Comida'," + comida[i] + " )");
                }
            }
            for (int i = 0; i < merienda.length; i++) {
                if (merienda[i] != 0) {
                    dietaDiaria.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta Diaria' , '" + txtFecha.getText().toString() + "', 'Merienda'," + merienda[i] + " )");
                }
            }
            for (int i = 0; i < cena.length; i++) {
                if (cena[i] != 0) {
                    dietaDiaria.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta Diaria' , '" + txtFecha.getText().toString() + "', 'Cena'," + cena[i] + " )");
                }
            }
            dietaDiaria.close();
            //Mensaje de éxito y vuelve a la pantalla del dietario
            Toast.makeText(this,
                    "Dieta Diaria con fecha " + txtFecha.getText().toString() + " guardada con éxito!", Toast.LENGTH_LONG).show();
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
                diaDelMes = day;
                // +1 porque Enero es 0
                final String selectedDate = day + " / " + (month + 1) + " / " + year;
                txtFecha.setText(selectedDate);
                findViewById(R.id.btnGuardar).setEnabled(true);
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

//    // SACAMOS EL DIA DE LA SEMANA, a partir de la fecha
//    public String diaDeLaSemana(int anio, int mes, int diaDelMes) {
//        String diaDeLaSemana = "";
//        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
//        Calendar fecha = Calendar.getInstance();
//        fecha.set(anio, mes, diaDelMes);
//        int dia = fecha.get(Calendar.DAY_OF_WEEK);
//        switch (dia) {
//            case 1:
//                diaDeLaSemana = "Domingo";
//                break;
//            case 2:
//                diaDeLaSemana = "Lunes";
//                break;
//            case 3:
//                diaDeLaSemana = "Martes";
//                break;
//            case 4:
//                diaDeLaSemana = "Miércoles";
//                break;
//            case 5:
//                diaDeLaSemana = "Jueves";
//                break;
//            case 6:
//                diaDeLaSemana = "Viernes";
//                break;
//            case 7:
//                diaDeLaSemana = "Sábado";
//                break;
//        }
//        return diaDeLaSemana;
//    }

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

}