package com.elorrieta.diet_app;

import androidx.appcompat.app.AppCompatActivity;

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

import com.elorrieta.diet_app.ui.main.dialog.DatePickerFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DietaSemanal extends AppCompatActivity implements View.OnClickListener{
    String receta = "", textoBoton = "";
    int soyElBoton, id;
    int anio, mes, diaDelMes;

    Button boton,
            btnDesayunoLunes, btnAlmuerzoLunes, btnComidaLunes, btnMeriendaLunes, btnCenaLunes,
            btnDesayunoMartes, btnAlmuerzoMartes, btnComidaMartes, btnMeriendaMartes, btnCenaMartes,
            btnDesayunoMiercoles, btnAlmuerzoMiercoles, btnComidaMiercoles, btnMeriendaMiercoles, btnCenaMiercoles,
            btnDesayunoJueves, btnAlmuerzoJueves, btnComidaJueves, btnMeriendaJueves, btnCenaJueves,
            btnDesayunoViernes, btnAlmuerzoViernes, btnComidaViernes, btnMeriendaViernes, btnCenaViernes,
            btnDesayunoSabado, btnAlmuerzoSabado, btnComidaSabado, btnMeriendaSabado, btnCenaSabado,
            btnDesayunoDomingo, btnAlmuerzoDomingo, btnComidaDomingo, btnMeriendaDomingo, btnCenaDomingo;
    Button btnLimpiarDieta, btnLimpiarMenu;
    Button btnGuardar;
    Button btnEliminarDieta;
    boolean limpiarMenuPulsado = false;

    EditText txtFecha;
    String fechaLunes = "";
    String fechaMartes = "";
    String fechaMiercoles = "";
    String fechaJueves = "";
    String fechaViernes = "";
    String fechaSabado = "";
    String fechaDomingo = "";
    TextView txtdiaLunes;
    String origen = "", fecha = "";

    //Arrays y variables para guardar los datos de la dieta
    int[] desayunoLunes = new int[2];
    int cuentaPlatosDesayunoLunes;
    int[] almuerzoLunes = new int[2];
    int cuentaPlatosAlmuerzoLunes;
    int[] comidaLunes = new int[3];
    int cuentaPlatosComidaLunes;
    int[] meriendaLunes = new int[2];
    int cuentaPlatosMeriendaLunes;
    int[] cenaLunes = new int[2];
    int cuentaPlatosCenaLunes;

    int[] desayunoMartes = new int[2];
    int cuentaPlatosDesayunoMartes;
    int[] almuerzoMartes = new int[2];
    int cuentaPlatosAlmuerzoMartes;
    int[] comidaMartes = new int[3];
    int cuentaPlatosComidaMartes;
    int[] meriendaMartes = new int[2];
    int cuentaPlatosMeriendaMartes;
    int[] cenaMartes = new int[2];
    int cuentaPlatosCenaMartes;

    int[] desayunoMiercoles = new int[2];
    int cuentaPlatosDesayunoMiercoles;
    int[] almuerzoMiercoles = new int[2];
    int cuentaPlatosAlmuerzoMiercoles;
    int[] comidaMiercoles = new int[3];
    int cuentaPlatosComidaMiercoles;
    int[] meriendaMiercoles = new int[2];
    int cuentaPlatosMeriendaMiercoles;
    int[] cenaMiercoles = new int[2];
    int cuentaPlatosCenaMiercoles;

    int[] desayunoJueves = new int[2];
    int cuentaPlatosDesayunoJueves;
    int[] almuerzoJueves = new int[2];
    int cuentaPlatosAlmuerzoJueves;
    int[] comidaJueves = new int[3];
    int cuentaPlatosComidaJueves;
    int[] meriendaJueves = new int[2];
    int cuentaPlatosMeriendaJueves;
    int[] cenaJueves = new int[2];
    int cuentaPlatosCenaJueves;

    int[] desayunoViernes = new int[2];
    int cuentaPlatosDesayunoViernes;
    int[] almuerzoViernes = new int[2];
    int cuentaPlatosAlmuerzoViernes;
    int[] comidaViernes = new int[3];
    int cuentaPlatosComidaViernes;
    int[] meriendaViernes = new int[2];
    int cuentaPlatosMeriendaViernes;
    int[] cenaViernes = new int[2];
    int cuentaPlatosCenaViernes;

    int[] desayunoSabado = new int[2];
    int cuentaPlatosDesayunoSabado;
    int[] almuerzoSabado = new int[2];
    int cuentaPlatosAlmuerzoSabado;
    int[] comidaSabado = new int[3];
    int cuentaPlatosComidaSabado;
    int[] meriendaSabado = new int[2];
    int cuentaPlatosMeriendaSabado;
    int[] cenaSabado = new int[2];
    int cuentaPlatosCenaSabado;

    int[] desayunoDomingo = new int[2];
    int cuentaPlatosDesayunoDomingo;
    int[] almuerzoDomingo = new int[2];
    int cuentaPlatosAlmuerzoDomingo;
    int[] comidaDomingo = new int[3];
    int cuentaPlatosComidaDomingo;
    int[] meriendaDomingo = new int[2];
    int cuentaPlatosMeriendaDomingo;
    int[] cenaDomingo = new int[2];
    int cuentaPlatosCenaDomingo;

    ArrayList<String> listaFechas = new ArrayList<String>();
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dieta_semanal);

        txtFecha = (EditText) findViewById(R.id.txtFechaSemanal);
        txtFecha.setOnClickListener(this);

        btnDesayunoLunes = (Button) findViewById(R.id.btn10);
        btnAlmuerzoLunes = (Button) findViewById(R.id.btn18);
        btnComidaLunes = (Button) findViewById(R.id.btn26);
        btnMeriendaLunes = (Button) findViewById(R.id.btn34);
        btnCenaLunes = (Button) findViewById(R.id.btn42);
        btnDesayunoMartes = (Button) findViewById(R.id.btn11);
        btnAlmuerzoMartes = (Button) findViewById(R.id.btn19);
        btnComidaMartes = (Button) findViewById(R.id.btn27);
        btnMeriendaMartes = (Button) findViewById(R.id.btn35);
        btnCenaMartes = (Button) findViewById(R.id.btn43);
        btnDesayunoMiercoles = (Button) findViewById(R.id.btn12);
        btnAlmuerzoMiercoles = (Button) findViewById(R.id.btn20);
        btnComidaMiercoles = (Button) findViewById(R.id.btn28);
        btnMeriendaMiercoles = (Button) findViewById(R.id.btn36);
        btnCenaMiercoles = (Button) findViewById(R.id.btn44);
        btnDesayunoJueves = (Button) findViewById(R.id.btn13);
        btnAlmuerzoJueves = (Button) findViewById(R.id.btn21);
        btnComidaJueves = (Button) findViewById(R.id.btn29);
        btnMeriendaJueves = (Button) findViewById(R.id.btn37);
        btnCenaJueves = (Button) findViewById(R.id.btn45);
        btnDesayunoViernes = (Button) findViewById(R.id.btn14);
        btnAlmuerzoViernes = (Button) findViewById(R.id.btn22);
        btnComidaViernes = (Button) findViewById(R.id.btn30);
        btnMeriendaViernes = (Button) findViewById(R.id.btn38);
        btnCenaViernes = (Button) findViewById(R.id.btn46);
        btnDesayunoSabado = (Button) findViewById(R.id.btn15);
        btnAlmuerzoSabado = (Button) findViewById(R.id.btn23);
        btnComidaSabado = (Button) findViewById(R.id.btn31);
        btnMeriendaSabado = (Button) findViewById(R.id.btn39);
        btnCenaSabado = (Button) findViewById(R.id.btn47);
        btnDesayunoDomingo = (Button) findViewById(R.id.btn16);
        btnAlmuerzoDomingo = (Button) findViewById(R.id.btn24);
        btnComidaDomingo = (Button) findViewById(R.id.btn32);
        btnMeriendaDomingo = (Button) findViewById(R.id.btn40);
        btnCenaDomingo = (Button) findViewById(R.id.btn48);

        txtdiaLunes= (TextView) findViewById(R.id.txtDiaLunes);
        btnLimpiarMenu = (Button) findViewById(R.id.btnLimpiarMenuSemanal);
        btnLimpiarDieta = (Button) findViewById(R.id.btnLimpiarDietaSemanal);
        btnGuardar = (Button) findViewById(R.id.btnGuardarSemanal);
        btnGuardar.setEnabled(false);

        //Recojo los valores de el adapter que llama a esta activity
        origen = getIntent().getStringExtra("origen");
        fecha = getIntent().getStringExtra("fecha");

        btnEliminarDieta = (Button) findViewById(R.id.btnEliminarSemanal);

        //Si venimos del recicler de ver dietas, apagamos los botones "limpiar" y "guardar" y encendemos el de "eliminar dieta"
        if(origen.contentEquals("ver")) {
            btnLimpiarMenu.setEnabled(false);
            btnLimpiarMenu.setVisibility(View.INVISIBLE);
            btnLimpiarDieta.setEnabled(false);
            btnLimpiarDieta.setVisibility(View.INVISIBLE);
            btnGuardar.setEnabled(false);
            btnGuardar.setVisibility(View.INVISIBLE);
            txtFecha.setVisibility(View.INVISIBLE);

            btnDesayunoLunes.setClickable(false);
            btnAlmuerzoLunes.setClickable(false);
            btnComidaLunes.setClickable(false);
            btnMeriendaLunes.setClickable(false);
            btnCenaLunes.setClickable(false);
            btnDesayunoMartes.setClickable(false);
            btnAlmuerzoMartes.setClickable(false);
            btnComidaMartes.setClickable(false);
            btnMeriendaMartes.setClickable(false);
            btnCenaMartes.setClickable(false);
            btnDesayunoMiercoles.setClickable(false);
            btnAlmuerzoMiercoles.setClickable(false);
            btnComidaMiercoles.setClickable(false);
            btnMeriendaMiercoles.setClickable(false);
            btnCenaMiercoles.setClickable(false);
            btnDesayunoJueves.setClickable(false);
            btnAlmuerzoJueves.setClickable(false);
            btnComidaJueves.setClickable(false);
            btnMeriendaJueves.setClickable(false);
            btnCenaJueves.setClickable(false);
            btnDesayunoViernes.setClickable(false);
            btnAlmuerzoViernes.setClickable(false);
            btnComidaViernes.setClickable(false);
            btnMeriendaViernes.setClickable(false);
            btnCenaViernes.setClickable(false);
            btnDesayunoSabado.setClickable(false);
            btnAlmuerzoSabado.setClickable(false);
            btnComidaSabado.setClickable(false);
            btnMeriendaSabado.setClickable(false);
            btnCenaSabado.setClickable(false);
            btnDesayunoDomingo.setClickable(false);
            btnAlmuerzoDomingo.setClickable(false);
            btnComidaDomingo.setClickable(false);
            btnMeriendaDomingo.setClickable(false);
            btnCenaDomingo.setClickable(false);

            calcularDias(fecha);

            txtdiaLunes.setVisibility(View.VISIBLE);
            txtdiaLunes.setText(fechaLunes);

            btnEliminarDieta.setVisibility(View.VISIBLE);

            cargarDietaLunes(fechaLunes);
            cargarDietaMartes(fechaMartes);
            cargarDietaMiercoles(fechaMiercoles);
            cargarDietaJueves(fechaJueves);
            cargarDietaViernes(fechaViernes);
            cargarDietaSabado(fechaSabado);
            cargarDietaDomingo(fechaDomingo);
        }
    }

    public void calcularDias(String fecha){
        String[] fechaArray = fecha.split(" / ");
        diaDelMes = Integer.parseInt(fechaArray[0].trim());
        mes = Integer.parseInt(fechaArray[1].trim());
        anio = Integer.parseInt(fechaArray[2].trim());

//        SimpleDateFormat formato = new SimpleDateFormat("dd / MM / yyyy");
        Calendar fechaElegida = Calendar.getInstance();
        fechaElegida.set(anio, mes - 1, diaDelMes);
        int dia = fechaElegida.get(Calendar.DAY_OF_WEEK);

        //Resto 1 al mes porque cCalendar trata los meses de 0-11
        //La idea para calcular las fechas de toda la semana es que parto del lunes y voy sumando un día,
        switch (dia) {
            case 1: //Domingo
                calcularDiasSemana(fechaElegida, -6);
                break;
            case 2: //Lunes
                calcularDiasSemana(fechaElegida, 0);
                break;
            case 3: //Martes
                calcularDiasSemana(fechaElegida, -1);
                break;
            case 4: //Miercoles
                calcularDiasSemana(fechaElegida, -2);
                break;
            case 5: //Jueves
                calcularDiasSemana(fechaElegida, -3);
                break;
            case 6: //Viernes
                calcularDiasSemana(fechaElegida, -4);
                break;
            case 7: //Sábado
                calcularDiasSemana(fechaElegida, -5);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            receta = data.getStringExtra("nomReceta");
            soyElBoton = data.getIntExtra("btn", 0);
            boton = (Button) findViewById(soyElBoton);
            boton.setTextSize(8);
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

    //GUARDAMOS LOS IDs DE RECETAS EN EL ARRAY CORRESPONDIENTE
    public void introducirIdArrays(int id, int idBoton) {
        if (idBoton == btnDesayunoLunes.getId()) {
            for(int i=0;i<desayunoLunes.length;i++) {
                if (desayunoLunes[i] == 0) {
                    desayunoLunes[i] = id;
                    cuentaPlatosDesayunoLunes ++;
                    break;
                }
            }
            if(cuentaPlatosDesayunoLunes == 2){
                Toast.makeText(this,"Número máximo de platos alcanzado", Toast.LENGTH_LONG).show();
                btnDesayunoLunes.setClickable(false);
            }
        }
        if (idBoton == btnAlmuerzoLunes.getId()) {
            for(int i=0;i<almuerzoLunes.length;i++) {
                if (almuerzoLunes[i] == 0) {
                    almuerzoLunes[i] = id;
                    cuentaPlatosAlmuerzoLunes ++;
                    break;
                }
            }
            if(cuentaPlatosAlmuerzoLunes == 2){
                Toast.makeText(this,"Número máximo de platos alcanzado", Toast.LENGTH_LONG).show();
                btnAlmuerzoLunes.setClickable(false);
            }
        }
        if (idBoton == btnComidaLunes.getId()) {
            for(int i=0;i<comidaLunes.length;i++) {
                if (comidaLunes[i] == 0) {
                    comidaLunes[i] = id;
                    cuentaPlatosComidaLunes ++;
                    break;
                }
            }
            if(cuentaPlatosComidaLunes == 3){
                Toast.makeText(this,"Número máximo de platos alcanzado", Toast.LENGTH_LONG).show();
                btnComidaLunes.setClickable(false);
            }
        }
        if (idBoton == btnMeriendaLunes.getId()) {
            for(int i=0;i<meriendaLunes.length;i++) {
                if (meriendaLunes[i] == 0) {
                    meriendaLunes[i] = id;
                    cuentaPlatosMeriendaLunes ++;
                    break;
                }
            }
            if(cuentaPlatosMeriendaLunes == 2){
                Toast.makeText(this,"Número máximo de platos alcanzado", Toast.LENGTH_LONG).show();
                btnMeriendaLunes.setClickable(false);
            }
        }
        if (idBoton == btnCenaLunes.getId()) {
            for(int i=0;i<cenaLunes.length;i++) {
                if (cenaLunes[i] == 0) {
                    cenaLunes[i] = id;
                    cuentaPlatosCenaLunes ++;
                    break;
                }
            }
            if(cuentaPlatosCenaLunes == 2){
                Toast.makeText(this,"Número máximo de platos alcanzado", Toast.LENGTH_LONG).show();
                btnCenaLunes.setClickable(false);
            }
        }

        if (idBoton == btnDesayunoMartes.getId()) {
            for(int i=0;i<desayunoMartes.length;i++) {
                if (desayunoMartes[i] == 0) {
                    desayunoMartes[i] = id;
                    cuentaPlatosDesayunoMartes ++;
                    break;
                }
            }
            if(cuentaPlatosDesayunoMartes == 2){
                Toast.makeText(this,"Número máximo de platos alcanzado", Toast.LENGTH_LONG).show();
                btnDesayunoMartes.setClickable(false);
            }
        }
        if (idBoton == btnAlmuerzoMartes.getId()) {
            for(int i=0;i<almuerzoMartes.length;i++) {
                if (almuerzoMartes[i] == 0) {
                    almuerzoMartes[i] = id;
                    cuentaPlatosAlmuerzoMartes ++;
                    break;
                }
            }
            if(cuentaPlatosAlmuerzoMartes == 2){
                Toast.makeText(this,"Número máximo de platos alcanzado", Toast.LENGTH_LONG).show();
                btnAlmuerzoMartes.setClickable(false);
            }
        }
        if (idBoton == btnComidaMartes.getId()) {
            for(int i=0;i<comidaMartes.length;i++) {
                if (comidaMartes[i] == 0) {
                    comidaMartes[i] = id;
                    cuentaPlatosComidaMartes ++;
                    break;
                }
            }
            if(cuentaPlatosComidaMartes == 3){
                Toast.makeText(this,"Número máximo de platos alcanzado", Toast.LENGTH_LONG).show();
                btnComidaMartes.setClickable(false);
            }
        }
        if (idBoton == btnMeriendaMartes.getId()) {
            for(int i=0;i<meriendaMartes.length;i++) {
                if (meriendaMartes[i] == 0) {
                    meriendaMartes[i] = id;
                    cuentaPlatosMeriendaMartes ++;
                    break;
                }
            }
            if(cuentaPlatosMeriendaMartes == 2){
                Toast.makeText(this,"Número máximo de platos alcanzado", Toast.LENGTH_LONG).show();
                btnMeriendaMartes.setClickable(false);
            }
        }
        if (idBoton == btnCenaMartes.getId()) {
            for(int i=0;i<cenaMartes.length;i++) {
                if (cenaMartes[i] == 0) {
                    cenaMartes[i] = id;
                    cuentaPlatosCenaMartes ++;
                    break;
                }
            }
            if(cuentaPlatosCenaMartes == 2){
                Toast.makeText(this,"Número máximo de platos alcanzado", Toast.LENGTH_LONG).show();
                btnCenaMartes.setClickable(false);
            }
        }

        if (idBoton == btnDesayunoMiercoles.getId()) {
            for(int i=0;i<desayunoMiercoles.length;i++) {
                if (desayunoMiercoles[i] == 0) {
                    desayunoMiercoles[i] = id;
                    cuentaPlatosDesayunoMiercoles ++;
                    break;
                }
            }
            if(cuentaPlatosDesayunoMiercoles == 2){
                Toast.makeText(this,"Número máximo de platos alcanzado", Toast.LENGTH_LONG).show();
                btnDesayunoMiercoles.setClickable(false);
            }
        }
        if (idBoton == btnAlmuerzoMiercoles.getId()) {
            for(int i=0;i<almuerzoMiercoles.length;i++) {
                if (almuerzoMiercoles[i] == 0) {
                    almuerzoMiercoles[i] = id;
                    cuentaPlatosAlmuerzoMiercoles ++;
                    break;
                }
            }
            if(cuentaPlatosAlmuerzoMiercoles == 2){
                Toast.makeText(this,"Número máximo de platos alcanzado", Toast.LENGTH_LONG).show();
                btnAlmuerzoMiercoles.setClickable(false);
            }
        }
        if (idBoton == btnComidaMiercoles.getId()) {
            for(int i=0;i<comidaMiercoles.length;i++) {
                if (comidaMiercoles[i] == 0) {
                    comidaMiercoles[i] = id;
                    cuentaPlatosComidaMiercoles ++;
                    break;
                }
            }
            if(cuentaPlatosComidaMiercoles == 3){
                Toast.makeText(this,"Número máximo de platos alcanzado", Toast.LENGTH_LONG).show();
                btnComidaMiercoles.setClickable(false);
            }
        }
        if (idBoton == btnMeriendaMiercoles.getId()) {
            for(int i=0;i<meriendaMiercoles.length;i++) {
                if (meriendaMiercoles[i] == 0) {
                    meriendaMiercoles[i] = id;
                    cuentaPlatosMeriendaMiercoles ++;
                    break;
                }
            }
            if(cuentaPlatosMeriendaMiercoles == 2){
                Toast.makeText(this,"Número máximo de platos alcanzado", Toast.LENGTH_LONG).show();
                btnMeriendaMiercoles.setClickable(false);
            }
        }
        if (idBoton == btnCenaMiercoles.getId()) {
            for(int i=0;i<cenaMiercoles.length;i++) {
                if (cenaMiercoles[i] == 0) {
                    cenaMiercoles[i] = id;
                    cuentaPlatosCenaMiercoles ++;
                    break;
                }
            }
            if(cuentaPlatosCenaMiercoles == 2){
                Toast.makeText(this,"Número máximo de platos alcanzado", Toast.LENGTH_LONG).show();
                btnCenaMiercoles.setClickable(false);
            }
        }

        if (idBoton == btnDesayunoJueves.getId()) {
            for(int i=0;i<desayunoJueves.length;i++) {
                if (desayunoJueves[i] == 0) {
                    desayunoJueves[i] = id;
                    cuentaPlatosDesayunoJueves ++;
                    break;
                }
            }
            if(cuentaPlatosDesayunoJueves == 2){
                Toast.makeText(this,"Número máximo de platos alcanzado", Toast.LENGTH_LONG).show();
                btnDesayunoJueves.setClickable(false);
            }
        }
        if (idBoton == btnAlmuerzoJueves.getId()) {
            for(int i=0;i<almuerzoJueves.length;i++) {
                if (almuerzoJueves[i] == 0) {
                    almuerzoJueves[i] = id;
                    cuentaPlatosAlmuerzoJueves ++;
                    break;
                }
            }
            if(cuentaPlatosAlmuerzoJueves == 2){
                Toast.makeText(this,"Número máximo de platos alcanzado", Toast.LENGTH_LONG).show();
                btnAlmuerzoJueves.setClickable(false);
            }
        }
        if (idBoton == btnComidaJueves.getId()) {
            for(int i=0;i<comidaJueves.length;i++) {
                if (comidaJueves[i] == 0) {
                    comidaJueves[i] = id;
                    cuentaPlatosComidaJueves ++;
                    break;
                }
            }
            if(cuentaPlatosComidaJueves == 3){
                Toast.makeText(this,"Número máximo de platos alcanzado", Toast.LENGTH_LONG).show();
                btnComidaJueves.setClickable(false);
            }
        }
        if (idBoton == btnMeriendaJueves.getId()) {
            for(int i=0;i<meriendaJueves.length;i++) {
                if (meriendaJueves[i] == 0) {
                    meriendaJueves[i] = id;
                    cuentaPlatosMeriendaJueves ++;
                    break;
                }
            }
            if(cuentaPlatosMeriendaJueves == 2){
                Toast.makeText(this,"Número máximo de platos alcanzado", Toast.LENGTH_LONG).show();
                btnMeriendaJueves.setClickable(false);
            }
        }
        if (idBoton == btnCenaJueves.getId()) {
            for(int i=0;i<cenaJueves.length;i++) {
                if (cenaJueves[i] == 0) {
                    cenaJueves[i] = id;
                    cuentaPlatosCenaJueves ++;
                    break;
                }
            }
            if(cuentaPlatosCenaJueves == 2){
                Toast.makeText(this,"Número máximo de platos alcanzado", Toast.LENGTH_LONG).show();
                btnCenaJueves.setClickable(false);
            }
        }

        if (idBoton == btnDesayunoViernes.getId()) {
            for(int i=0;i<desayunoViernes.length;i++) {
                if (desayunoViernes[i] == 0) {
                    desayunoViernes[i] = id;
                    cuentaPlatosDesayunoViernes ++;
                    break;
                }
            }
            if(cuentaPlatosDesayunoViernes == 2){
                Toast.makeText(this,"Número máximo de platos alcanzado", Toast.LENGTH_LONG).show();
                btnDesayunoViernes.setClickable(false);
            }
        }
        if (idBoton == btnAlmuerzoViernes.getId()) {
            for(int i=0;i<almuerzoViernes.length;i++) {
                if (almuerzoViernes[i] == 0) {
                    almuerzoViernes[i] = id;
                    cuentaPlatosAlmuerzoViernes ++;
                    break;
                }
            }
            if(cuentaPlatosAlmuerzoViernes == 2){
                Toast.makeText(this,"Número máximo de platos alcanzado", Toast.LENGTH_LONG).show();
                btnAlmuerzoViernes.setClickable(false);
            }
        }
        if (idBoton == btnComidaViernes.getId()) {
            for(int i=0;i<comidaViernes.length;i++) {
                if (comidaViernes[i] == 0) {
                    comidaViernes[i] = id;
                    cuentaPlatosComidaViernes ++;
                    break;
                }
            }
            if(cuentaPlatosComidaViernes == 3){
                Toast.makeText(this,"Número máximo de platos alcanzado", Toast.LENGTH_LONG).show();
                btnComidaViernes.setClickable(false);
            }
        }
        if (idBoton == btnMeriendaViernes.getId()) {
            for(int i=0;i<meriendaViernes.length;i++) {
                if (meriendaViernes[i] == 0) {
                    meriendaViernes[i] = id;
                    cuentaPlatosMeriendaViernes ++;
                    break;
                }
            }
            if(cuentaPlatosMeriendaViernes == 2){
                Toast.makeText(this,"Número máximo de platos alcanzado", Toast.LENGTH_LONG).show();
                btnMeriendaViernes.setClickable(false);
            }
        }
        if (idBoton == btnCenaViernes.getId()) {
            for(int i=0;i<cenaViernes.length;i++) {
                if (cenaViernes[i] == 0) {
                    cenaViernes[i] = id;
                    cuentaPlatosCenaViernes ++;
                    break;
                }
            }
            if(cuentaPlatosCenaViernes == 2){
                Toast.makeText(this,"Número máximo de platos alcanzado", Toast.LENGTH_LONG).show();
                btnCenaViernes.setClickable(false);
            }
        }

        if (idBoton == btnDesayunoSabado.getId()) {
            for(int i=0;i<desayunoSabado.length;i++) {
                if (desayunoSabado[i] == 0) {
                    desayunoSabado[i] = id;
                    cuentaPlatosDesayunoSabado ++;
                    break;
                }
            }
            if(cuentaPlatosDesayunoSabado == 2){
                Toast.makeText(this,"Número máximo de platos alcanzado", Toast.LENGTH_LONG).show();
                btnDesayunoSabado.setClickable(false);
            }
        }
        if (idBoton == btnAlmuerzoSabado.getId()) {
            for(int i=0;i<almuerzoSabado.length;i++) {
                if (almuerzoSabado[i] == 0) {
                    almuerzoSabado[i] = id;
                    cuentaPlatosAlmuerzoSabado ++;
                    break;
                }
            }
            if(cuentaPlatosAlmuerzoSabado == 2){
                Toast.makeText(this,"Número máximo de platos alcanzado", Toast.LENGTH_LONG).show();
                btnAlmuerzoSabado.setClickable(false);
            }
        }
        if (idBoton == btnComidaSabado.getId()) {
            for(int i=0;i<comidaSabado.length;i++) {
                if (comidaSabado[i] == 0) {
                    comidaSabado[i] = id;
                    cuentaPlatosComidaSabado ++;
                    break;
                }
            }
            if(cuentaPlatosComidaSabado == 3){
                Toast.makeText(this,"Número máximo de platos alcanzado", Toast.LENGTH_LONG).show();
                btnComidaSabado.setClickable(false);
            }
        }
        if (idBoton == btnMeriendaSabado.getId()) {
            for(int i=0;i<meriendaSabado.length;i++) {
                if (meriendaSabado[i] == 0) {
                    meriendaSabado[i] = id;
                    cuentaPlatosMeriendaSabado ++;
                    break;
                }
            }
            if(cuentaPlatosMeriendaSabado == 2){
                Toast.makeText(this,"Número máximo de platos alcanzado", Toast.LENGTH_LONG).show();
                btnMeriendaSabado.setClickable(false);
            }
        }
        if (idBoton == btnCenaSabado.getId()) {
            for(int i=0;i<cenaSabado.length;i++) {
                if (cenaSabado[i] == 0) {
                    cenaSabado[i] = id;
                    cuentaPlatosCenaSabado ++;
                    break;
                }
            }
            if(cuentaPlatosCenaSabado == 2){
                Toast.makeText(this,"Número máximo de platos alcanzado", Toast.LENGTH_LONG).show();
                btnCenaSabado.setClickable(false);
            }
        }

        if (idBoton == btnDesayunoDomingo.getId()) {
            for(int i=0;i<desayunoDomingo.length;i++) {
                if (desayunoDomingo[i] == 0) {
                    desayunoDomingo[i] = id;
                    cuentaPlatosDesayunoDomingo ++;
                    break;
                }
            }
            if(cuentaPlatosDesayunoDomingo == 2){
                Toast.makeText(this,"Número máximo de platos alcanzado", Toast.LENGTH_LONG).show();
                btnDesayunoDomingo.setClickable(false);
            }
        }
        if (idBoton == btnAlmuerzoDomingo.getId()) {
            for(int i=0;i<almuerzoDomingo.length;i++) {
                if (almuerzoDomingo[i] == 0) {
                    almuerzoDomingo[i] = id;
                    cuentaPlatosAlmuerzoDomingo ++;
                    break;
                }
            }
            if(cuentaPlatosAlmuerzoDomingo == 2){
                Toast.makeText(this,"Número máximo de platos alcanzado", Toast.LENGTH_LONG).show();
                btnAlmuerzoDomingo.setClickable(false);
            }
        }
        if (idBoton == btnComidaDomingo.getId()) {
            for(int i=0;i<comidaDomingo.length;i++) {
                if (comidaDomingo[i] == 0) {
                    comidaDomingo[i] = id;
                    cuentaPlatosComidaDomingo ++;
                    break;
                }
            }
            if(cuentaPlatosComidaDomingo == 3){
                Toast.makeText(this,"Número máximo de platos alcanzado", Toast.LENGTH_LONG).show();
                btnComidaDomingo.setClickable(false);
            }
        }
        if (idBoton == btnMeriendaDomingo.getId()) {
            for(int i=0;i<meriendaDomingo.length;i++) {
                if (meriendaDomingo[i] == 0) {
                    meriendaDomingo[i] = id;
                    cuentaPlatosMeriendaDomingo ++;
                    break;
                }
            }
            if(cuentaPlatosMeriendaDomingo == 2){
                Toast.makeText(this,"Número máximo de platos alcanzado", Toast.LENGTH_LONG).show();
                btnMeriendaDomingo.setClickable(false);
            }
        }
        if (idBoton == btnCenaDomingo.getId()) {
            for(int i=0;i<cenaDomingo.length;i++) {
                if (cenaDomingo[i] == 0) {
                    cenaDomingo[i] = id;
                    cuentaPlatosCenaDomingo ++;
                    break;
                }
            }
            if(cuentaPlatosCenaDomingo == 2){
                Toast.makeText(this,"Número máximo de platos alcanzado", Toast.LENGTH_LONG).show();
                btnCenaDomingo.setClickable(false);
            }
        }
    }

    //OBTIENE LA ID DE LA RECETA, en función del nombre
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

    public void elegirMenu(View poView) {
        if (limpiarMenuPulsado) {
            boton = (Button) findViewById(poView.getId());
            boton.setTextSize(14);
            boton.setText("Menú");

            if (boton.getId() == btnDesayunoLunes.getId()) {
                cuentaPlatosDesayunoLunes = 0;
                for(int i=0;i<desayunoLunes.length;i++) {
                    desayunoLunes[i] = 0;
                }
            }
            if (boton.getId() == btnAlmuerzoLunes.getId()) {
                cuentaPlatosAlmuerzoLunes = 0;
                for(int i=0;i<almuerzoLunes.length;i++) {
                    almuerzoLunes[i] = 0;
                }
            }
            if (boton.getId() == btnComidaLunes.getId()) {
                cuentaPlatosComidaLunes = 0;
                for(int i=0;i<comidaLunes.length;i++) {
                    comidaLunes[i] = 0;
                }
            }
            if (boton.getId() == btnMeriendaLunes.getId()) {
                cuentaPlatosMeriendaLunes = 0;
                for(int i=0;i<meriendaLunes.length;i++) {
                    meriendaLunes[i] = 0;
                }
            }
            if (boton.getId() == btnCenaLunes.getId()) {
                cuentaPlatosCenaLunes = 0;
                for(int i=0;i<cenaLunes.length;i++) {
                    cenaLunes[i] = 0;
                }
            }
            if (boton.getId() == btnDesayunoMartes.getId()) {
                cuentaPlatosDesayunoMartes = 0;
                for(int i=0;i<desayunoMartes.length;i++) {
                    desayunoMartes[i] = 0;
                }
            }
            if (boton.getId() == btnAlmuerzoMartes.getId()) {
                cuentaPlatosAlmuerzoMartes = 0;
                for(int i=0;i<almuerzoMartes.length;i++) {
                    almuerzoMartes[i] = 0;
                }
            }
            if (boton.getId() == btnComidaMartes.getId()) {
                cuentaPlatosComidaMartes = 0;
                for(int i=0;i<comidaMartes.length;i++) {
                    comidaMartes[i] = 0;
                }
            }
            if (boton.getId() == btnMeriendaMartes.getId()) {
                cuentaPlatosMeriendaMartes = 0;
                for(int i=0;i<meriendaMartes.length;i++) {
                    meriendaMartes[i] = 0;
                }
            }
            if (boton.getId() == btnCenaMartes.getId()) {
                cuentaPlatosCenaMartes = 0;
                for(int i=0;i<cenaMartes.length;i++) {
                    cenaMartes[i] = 0;
                }
            }
            if (boton.getId() == btnDesayunoMiercoles.getId()) {
                cuentaPlatosDesayunoMiercoles = 0;
                for(int i=0;i<desayunoMiercoles.length;i++) {
                    desayunoMiercoles[i] = 0;
                }
            }
            if (boton.getId() == btnAlmuerzoMiercoles.getId()) {
                cuentaPlatosAlmuerzoMiercoles = 0;
                for(int i=0;i<almuerzoMiercoles.length;i++) {
                    almuerzoMiercoles[i] = 0;
                }
            }
            if (boton.getId() == btnComidaMiercoles.getId()) {
                cuentaPlatosComidaMiercoles = 0;
                for(int i=0;i<comidaMiercoles.length;i++) {
                    comidaMiercoles[i] = 0;
                }
            }
            if (boton.getId() == btnMeriendaMiercoles.getId()) {
                cuentaPlatosMeriendaMiercoles = 0;
                for(int i=0;i<meriendaMiercoles.length;i++) {
                    meriendaMiercoles[i] = 0;
                }
            }
            if (boton.getId() == btnCenaMiercoles.getId()) {
                cuentaPlatosCenaMiercoles = 0;
                for(int i=0;i<cenaMiercoles.length;i++) {
                    cenaMiercoles[i] = 0;
                }
            }
            if (boton.getId() == btnDesayunoJueves.getId()) {
                cuentaPlatosDesayunoJueves = 0;
                for(int i=0;i<desayunoJueves.length;i++) {
                    desayunoJueves[i] = 0;
                }
            }
            if (boton.getId() == btnAlmuerzoJueves.getId()) {
                cuentaPlatosAlmuerzoJueves = 0;
                for(int i=0;i<almuerzoJueves.length;i++) {
                    almuerzoJueves[i] = 0;
                }
            }
            if (boton.getId() == btnComidaJueves.getId()) {
                cuentaPlatosComidaJueves = 0;
                for(int i=0;i<comidaJueves.length;i++) {
                    comidaJueves[i] = 0;
                }
            }
            if (boton.getId() == btnMeriendaJueves.getId()) {
                cuentaPlatosMeriendaJueves = 0;
                for(int i=0;i<meriendaJueves.length;i++) {
                    meriendaJueves[i] = 0;
                }
            }
            if (boton.getId() == btnCenaJueves.getId()) {
                cuentaPlatosCenaJueves = 0;
                for(int i=0;i<cenaJueves.length;i++) {
                    cenaJueves[i] = 0;
                }
            }
            if (boton.getId() == btnDesayunoViernes.getId()) {
                cuentaPlatosDesayunoViernes = 0;
                for(int i=0;i<desayunoViernes.length;i++) {
                    desayunoViernes[i] = 0;
                }
            }
            if (boton.getId() == btnAlmuerzoViernes.getId()) {
                cuentaPlatosAlmuerzoViernes = 0;
                for(int i=0;i<almuerzoViernes.length;i++) {
                    almuerzoViernes[i] = 0;
                }
            }
            if (boton.getId() == btnComidaViernes.getId()) {
                cuentaPlatosComidaViernes = 0;
                for(int i=0;i<comidaViernes.length;i++) {
                    comidaViernes[i] = 0;
                }
            }
            if (boton.getId() == btnMeriendaViernes.getId()) {
                cuentaPlatosMeriendaViernes = 0;
                for(int i=0;i<meriendaViernes.length;i++) {
                    meriendaViernes[i] = 0;
                }
            }
            if (boton.getId() == btnCenaViernes.getId()) {
                cuentaPlatosCenaViernes = 0;
                for(int i=0;i<cenaViernes.length;i++) {
                    cenaViernes[i] = 0;
                }
            }
            if (boton.getId() == btnDesayunoSabado.getId()) {
                cuentaPlatosDesayunoSabado = 0;
                for(int i=0;i<desayunoSabado.length;i++) {
                    desayunoSabado[i] = 0;
                }
            }
            if (boton.getId() == btnAlmuerzoSabado.getId()) {
                cuentaPlatosAlmuerzoSabado = 0;
                for(int i=0;i<almuerzoSabado.length;i++) {
                    almuerzoSabado[i] = 0;
                }
            }
            if (boton.getId() == btnComidaSabado.getId()) {
                cuentaPlatosComidaSabado = 0;
                for(int i=0;i<comidaSabado.length;i++) {
                    comidaSabado[i] = 0;
                }
            }
            if (boton.getId() == btnMeriendaSabado.getId()) {
                cuentaPlatosMeriendaSabado = 0;
                for(int i=0;i<meriendaSabado.length;i++) {
                    meriendaSabado[i] = 0;
                }
            }
            if (boton.getId() == btnCenaSabado.getId()) {
                cuentaPlatosCenaSabado = 0;
                for(int i=0;i<cenaSabado.length;i++) {
                    cenaSabado[i] = 0;
                }
            }
            if (boton.getId() == btnDesayunoDomingo.getId()) {
                cuentaPlatosDesayunoDomingo = 0;
                for(int i=0;i<desayunoDomingo.length;i++) {
                    desayunoDomingo[i] = 0;
                }
            }
            if (boton.getId() == btnAlmuerzoDomingo.getId()) {
                cuentaPlatosAlmuerzoDomingo = 0;
                for(int i=0;i<almuerzoDomingo.length;i++) {
                    almuerzoDomingo[i] = 0;
                }
            }
            if (boton.getId() == btnComidaDomingo.getId()) {
                cuentaPlatosComidaDomingo = 0;
                for(int i=0;i<comidaDomingo.length;i++) {
                    comidaDomingo[i] = 0;
                }
            }
            if (boton.getId() == btnMeriendaDomingo.getId()) {
                cuentaPlatosMeriendaDomingo = 0;
                for(int i=0;i<meriendaDomingo.length;i++) {
                    meriendaDomingo[i] = 0;
                }
            }
            if (boton.getId() == btnCenaDomingo.getId()) {
                cuentaPlatosCenaDomingo = 0;
                for(int i=0;i<cenaDomingo.length;i++) {
                    cenaDomingo[i] = 0;
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
            findViewById(R.id.btnLimpiarMenuSemanal).setBackgroundColor(0xffff8800);
            btnDesayunoLunes.setClickable(true);
            btnAlmuerzoLunes.setClickable(true);
            btnComidaLunes.setClickable(true);
            btnMeriendaLunes.setClickable(true);
            btnCenaLunes.setClickable(true);
            btnDesayunoMartes.setClickable(true);
            btnAlmuerzoMartes.setClickable(true);
            btnComidaMartes.setClickable(true);
            btnMeriendaMartes.setClickable(true);
            btnCenaMartes.setClickable(true);
            btnDesayunoMiercoles.setClickable(true);
            btnAlmuerzoMiercoles.setClickable(true);
            btnComidaMiercoles.setClickable(true);
            btnMeriendaMiercoles.setClickable(true);
            btnCenaMiercoles.setClickable(true);
            btnDesayunoJueves.setClickable(true);
            btnAlmuerzoJueves.setClickable(true);
            btnComidaJueves.setClickable(true);
            btnMeriendaJueves.setClickable(true);
            btnCenaJueves.setClickable(true);
            btnDesayunoViernes.setClickable(true);
            btnAlmuerzoViernes.setClickable(true);
            btnComidaViernes.setClickable(true);
            btnMeriendaViernes.setClickable(true);
            btnCenaViernes.setClickable(true);
            btnDesayunoSabado.setClickable(true);
            btnAlmuerzoSabado.setClickable(true);
            btnComidaSabado.setClickable(true);
            btnMeriendaSabado.setClickable(true);
            btnCenaSabado.setClickable(true);
            btnDesayunoDomingo.setClickable(true);
            btnAlmuerzoDomingo.setClickable(true);
            btnComidaDomingo.setClickable(true);
            btnMeriendaDomingo.setClickable(true);
            btnCenaDomingo.setClickable(true);

            btnLimpiarDieta.setEnabled(false);
        } else {
            findViewById(R.id.btnLimpiarMenuSemanal).setBackgroundColor(0xFF4CAF50);
            btnLimpiarDieta.setEnabled(true);
        }
    }

    public void limpiarDieta(View poView) {
        //Array con los botones que hay en la pantalla
        Button[] BtnArrayLunes = new Button[]{
                (Button) findViewById(R.id.btn10),
                (Button) findViewById(R.id.btn18),
                (Button) findViewById(R.id.btn26),
                (Button) findViewById(R.id.btn34),
                (Button) findViewById(R.id.btn42)};
        Button[] BtnArrayMartes = new Button[]{
                (Button) findViewById(R.id.btn11),
                (Button) findViewById(R.id.btn19),
                (Button) findViewById(R.id.btn27),
                (Button) findViewById(R.id.btn35),
                (Button) findViewById(R.id.btn43)};
        Button[] BtnArrayMiercoles = new Button[]{
                (Button) findViewById(R.id.btn12),
                (Button) findViewById(R.id.btn20),
                (Button) findViewById(R.id.btn28),
                (Button) findViewById(R.id.btn36),
                (Button) findViewById(R.id.btn44)};
        Button[] BtnArrayJueves = new Button[]{
                (Button) findViewById(R.id.btn13),
                (Button) findViewById(R.id.btn21),
                (Button) findViewById(R.id.btn29),
                (Button) findViewById(R.id.btn37),
                (Button) findViewById(R.id.btn45)};
        Button[] BtnArrayViernes = new Button[]{
                (Button) findViewById(R.id.btn14),
                (Button) findViewById(R.id.btn22),
                (Button) findViewById(R.id.btn30),
                (Button) findViewById(R.id.btn38),
                (Button) findViewById(R.id.btn46)};
        Button[] BtnArraySabado = new Button[]{
                (Button) findViewById(R.id.btn15),
                (Button) findViewById(R.id.btn23),
                (Button) findViewById(R.id.btn31),
                (Button) findViewById(R.id.btn39),
                (Button) findViewById(R.id.btn47)};
        Button[] BtnArrayDomingo = new Button[]{
                (Button) findViewById(R.id.btn16),
                (Button) findViewById(R.id.btn24),
                (Button) findViewById(R.id.btn32),
                (Button) findViewById(R.id.btn40),
                (Button) findViewById(R.id.btn48)};
        //Los recorre y los limpia
        for (int i = 0; i < BtnArrayLunes.length; i++) {
            boton = BtnArrayLunes[i];
            boton.setTextSize(14);
            boton.setText("Menú");
            boton.setClickable(true);
        }
        //Limpia los contadores
        cuentaPlatosDesayunoLunes = 0;
        cuentaPlatosAlmuerzoLunes = 0;
        cuentaPlatosComidaLunes = 0;
        cuentaPlatosMeriendaLunes = 0;
        cuentaPlatosCenaLunes = 0;
        //Limpia los arrays
        for(int i=0;i < desayunoLunes.length;i++) {
            desayunoLunes[i] = 0;
            almuerzoLunes[i] = 0;
            meriendaLunes[i] = 0;
            cenaLunes[i] = 0;
        }
        for(int i=0;i < comidaLunes.length;i++) {
            comidaLunes[i] = 0;
        }
        //Los recorre y los limpia
        for (int i = 0; i < BtnArrayMartes.length; i++) {
            boton = BtnArrayMartes[i];
            boton.setTextSize(14);
            boton.setText("Menú");
            boton.setClickable(true);
        }
        //Limpia los contadores
        cuentaPlatosDesayunoMartes = 0;
        cuentaPlatosAlmuerzoMartes = 0;
        cuentaPlatosComidaMartes = 0;
        cuentaPlatosMeriendaMartes = 0;
        cuentaPlatosCenaMartes = 0;
        //Limpia los arrays
        for(int i=0;i < desayunoMartes.length;i++) {
            desayunoMartes[i] = 0;
            almuerzoMartes[i] = 0;
            meriendaMartes[i] = 0;
            cenaMartes[i] = 0;
        }
        for(int i=0;i < comidaMartes.length;i++) {
            comidaMartes[i] = 0;
        }
        //Los recorre y los limpia
        for (int i = 0; i < BtnArrayMiercoles.length; i++) {
            boton = BtnArrayMiercoles[i];
            boton.setTextSize(14);
            boton.setText("Menú");
            boton.setClickable(true);
        }
        //Limpia los contadores
        cuentaPlatosDesayunoMiercoles = 0;
        cuentaPlatosAlmuerzoMiercoles = 0;
        cuentaPlatosComidaMiercoles = 0;
        cuentaPlatosMeriendaMiercoles = 0;
        cuentaPlatosCenaMiercoles = 0;
        //Limpia los arrays
        for(int i=0;i < desayunoMiercoles.length;i++) {
            desayunoMiercoles[i] = 0;
            almuerzoMiercoles[i] = 0;
            meriendaMiercoles[i] = 0;
            cenaMiercoles[i] = 0;
        }
        for(int i=0;i < comidaMiercoles.length;i++) {
            comidaMiercoles[i] = 0;
        }
        //Los recorre y los limpia
        for (int i = 0; i < BtnArrayJueves.length; i++) {
            boton = BtnArrayJueves[i];
            boton.setTextSize(14);
            boton.setText("Menú");
            boton.setClickable(true);
        }
        //Limpia los contadores
        cuentaPlatosDesayunoJueves = 0;
        cuentaPlatosAlmuerzoJueves = 0;
        cuentaPlatosComidaJueves = 0;
        cuentaPlatosMeriendaJueves = 0;
        cuentaPlatosCenaJueves = 0;
        //Limpia los arrays
        for(int i=0;i < desayunoJueves.length;i++) {
            desayunoJueves[i] = 0;
            almuerzoJueves[i] = 0;
            meriendaJueves[i] = 0;
            cenaJueves[i] = 0;
        }
        for(int i=0;i < comidaJueves.length;i++) {
            comidaJueves[i] = 0;
        }
        //Los recorre y los limpia
        for (int i = 0; i < BtnArrayViernes.length; i++) {
            boton = BtnArrayViernes[i];
            boton.setTextSize(14);
            boton.setText("Menú");
            boton.setClickable(true);
        }
        //Limpia los contadores
        cuentaPlatosDesayunoViernes = 0;
        cuentaPlatosAlmuerzoViernes = 0;
        cuentaPlatosComidaViernes = 0;
        cuentaPlatosMeriendaViernes = 0;
        cuentaPlatosCenaViernes = 0;
        //Limpia los arrays
        for(int i=0;i < desayunoViernes.length;i++) {
            desayunoViernes[i] = 0;
            almuerzoViernes[i] = 0;
            meriendaViernes[i] = 0;
            cenaViernes[i] = 0;
        }
        for(int i=0;i < comidaViernes.length;i++) {
            comidaViernes[i] = 0;
        }
        //Los recorre y los limpia
        for (int i = 0; i < BtnArraySabado.length; i++) {
            boton = BtnArraySabado[i];
            boton.setTextSize(14);
            boton.setText("Menú");
            boton.setClickable(true);
        }
        //Limpia los contadores
        cuentaPlatosDesayunoSabado = 0;
        cuentaPlatosAlmuerzoSabado = 0;
        cuentaPlatosComidaSabado = 0;
        cuentaPlatosMeriendaSabado = 0;
        cuentaPlatosCenaSabado = 0;
        //Limpia los arrays
        for(int i=0;i < desayunoSabado.length;i++) {
            desayunoSabado[i] = 0;
            almuerzoSabado[i] = 0;
            meriendaSabado[i] = 0;
            cenaSabado[i] = 0;
        }
        for(int i=0;i < comidaSabado.length;i++) {
            comidaSabado[i] = 0;
        }
        //Los recorre y los limpia
        for (int i = 0; i < BtnArrayDomingo.length; i++) {
            boton = BtnArrayDomingo[i];
            boton.setTextSize(14);
            boton.setText("Menú");
            boton.setClickable(true);
        }
        //Limpia los contadores
        cuentaPlatosDesayunoDomingo = 0;
        cuentaPlatosAlmuerzoDomingo = 0;
        cuentaPlatosComidaDomingo = 0;
        cuentaPlatosMeriendaDomingo = 0;
        cuentaPlatosCenaDomingo = 0;
        //Limpia los arrays
        for(int i=0;i < desayunoDomingo.length;i++) {
            desayunoDomingo[i] = 0;
            almuerzoDomingo[i] = 0;
            meriendaDomingo[i] = 0;
            cenaDomingo[i] = 0;
        }
        for(int i=0;i < comidaDomingo.length;i++) {
            comidaDomingo[i] = 0;
        }
    }

    public void guardarDieta(View poView) {
        BBDD admin = new BBDD(this,"administracion", null,1);
        SQLiteDatabase dietaSemanal = admin.getWritableDatabase();

        // Guardamos en una lista todas las fechas existentes en la BBDD
        Cursor a = dietaSemanal.rawQuery("select dia from fecha", null);
        if (a != null) {
            if (a.moveToFirst()) {
                a.moveToFirst();
                do {
                    listaFechas.add(a.getString(0));
                } while (a.moveToNext());
            }
        }

        String diaElegido = txtFecha.getText().toString();
        calcularDias(diaElegido);

        if(listaFechas.contains(fechaLunes) ||
                listaFechas.contains(fechaMartes) ||
                listaFechas.contains(fechaMiercoles) ||
                listaFechas.contains(fechaJueves) ||
                listaFechas.contains(fechaViernes) ||
                listaFechas.contains(fechaSabado) ||
                listaFechas.contains(fechaDomingo)) {
            //Mensaje de error
            Toast.makeText(this,
                    "Dieta Semanal con fecha de lunes " + fechaLunes + " a " + fechaDomingo + " ya existe en la BBDD!", Toast.LENGTH_LONG).show();
        } else {
            dietaSemanal.execSQL("INSERT INTO fecha (dia, nomDieta) VALUES ('" + fechaLunes + "', 'Dieta Semanal')");
            dietaSemanal.execSQL("INSERT INTO fecha (dia, nomDieta) VALUES ('" + fechaMartes + "', 'Dieta Semanal')");
            dietaSemanal.execSQL("INSERT INTO fecha (dia, nomDieta) VALUES ('" + fechaMiercoles + "', 'Dieta Semanal')");
            dietaSemanal.execSQL("INSERT INTO fecha (dia, nomDieta) VALUES ('" + fechaJueves + "', 'Dieta Semanal')");
            dietaSemanal.execSQL("INSERT INTO fecha (dia, nomDieta) VALUES ('" + fechaViernes + "', 'Dieta Semanal')");
            dietaSemanal.execSQL("INSERT INTO fecha (dia, nomDieta) VALUES ('" + fechaSabado + "', 'Dieta Semanal')");
            dietaSemanal.execSQL("INSERT INTO fecha (dia, nomDieta) VALUES ('" + fechaDomingo + "', 'Dieta Semanal')");

            for (int i = 0; i < desayunoLunes.length; i++) {
                if (desayunoLunes[i] != 0) {
                    dietaSemanal.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta Semanal' , '" + fechaLunes + "', 'Desayuno'," + desayunoLunes[i] + " )");
                }
            }
            for (int i = 0; i < almuerzoLunes.length; i++) {
                if (almuerzoLunes[i] != 0) {
                    dietaSemanal.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta Semanal' , '" + fechaLunes + "', 'Almuerzo'," + almuerzoLunes[i] + " )");
                }
            }
            for (int i = 0; i < comidaLunes.length; i++) {
                if (comidaLunes[i] != 0) {
                    dietaSemanal.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta Semanal' , '" + fechaLunes + "', 'Comida'," + comidaLunes[i] + " )");
                }
            }
            for (int i = 0; i < meriendaLunes.length; i++) {
                if (meriendaLunes[i] != 0) {
                    dietaSemanal.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta Semanal' , '" + fechaLunes + "', 'Merienda'," + meriendaLunes[i] + " )");
                }
            }
            for (int i = 0; i < cenaLunes.length; i++) {
                if (cenaLunes[i] != 0) {
                    dietaSemanal.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta Semanal' , '" + fechaLunes + "', 'Cena'," + cenaLunes[i] + " )");
                }
            }

            for (int i = 0; i < desayunoMartes.length; i++) {
                if (desayunoMartes[i] != 0) {
                    dietaSemanal.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta Semanal' , '" + fechaMartes + "', 'Desayuno'," + desayunoMartes[i] + " )");
                }
            }
            for (int i = 0; i < almuerzoMartes.length; i++) {
                if (almuerzoMartes[i] != 0) {
                    dietaSemanal.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta Semanal' , '" + fechaMartes + "', 'Almuerzo'," + almuerzoMartes[i] + " )");
                }
            }
            for (int i = 0; i < comidaMartes.length; i++) {
                if (comidaMartes[i] != 0) {
                    dietaSemanal.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta Semanal' , '" + fechaMartes + "', 'Comida'," + comidaMartes[i] + " )");
                }
            }
            for (int i = 0; i < meriendaMartes.length; i++) {
                if (meriendaMartes[i] != 0) {
                    dietaSemanal.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta Semanal' , '" + fechaMartes + "', 'Merienda'," + meriendaMartes[i] + " )");
                }
            }
            for (int i = 0; i < cenaMartes.length; i++) {
                if (cenaMartes[i] != 0) {
                    dietaSemanal.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta Semanal' , '" + fechaMartes + "', 'Cena'," + cenaMartes[i] + " )");
                }
            }

            for (int i = 0; i < desayunoMiercoles.length; i++) {
                if (desayunoMiercoles[i] != 0) {
                    dietaSemanal.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta Semanal' , '" + fechaMiercoles + "', 'Desayuno'," + desayunoMiercoles[i] + " )");
                }
            }
            for (int i = 0; i < almuerzoMiercoles.length; i++) {
                if (almuerzoMiercoles[i] != 0) {
                    dietaSemanal.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta Semanal' , '" + fechaMiercoles + "', 'Almuerzo'," + almuerzoMiercoles[i] + " )");
                }
            }
            for (int i = 0; i < comidaMiercoles.length; i++) {
                if (comidaMiercoles[i] != 0) {
                    dietaSemanal.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta Semanal' , '" + fechaMiercoles + "', 'Comida'," + comidaMiercoles[i] + " )");
                }
            }
            for (int i = 0; i < meriendaMiercoles.length; i++) {
                if (meriendaMiercoles[i] != 0) {
                    dietaSemanal.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta Semanal' , '" + fechaMiercoles + "', 'Merienda'," + meriendaMiercoles[i] + " )");
                }
            }
            for (int i = 0; i < cenaMiercoles.length; i++) {
                if (cenaMiercoles[i] != 0) {
                    dietaSemanal.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta Semanal' , '" + fechaMiercoles + "', 'Cena'," + cenaMiercoles[i] + " )");
                }
            }

            for (int i = 0; i < desayunoJueves.length; i++) {
                if (desayunoJueves[i] != 0) {
                    dietaSemanal.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta Semanal' , '" + fechaJueves + "', 'Desayuno'," + desayunoJueves[i] + " )");
                }
            }
            for (int i = 0; i < almuerzoJueves.length; i++) {
                if (almuerzoJueves[i] != 0) {
                    dietaSemanal.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta Semanal' , '" + fechaJueves + "', 'Almuerzo'," + almuerzoJueves[i] + " )");
                }
            }
            for (int i = 0; i < comidaJueves.length; i++) {
                if (comidaJueves[i] != 0) {
                    dietaSemanal.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta Semanal' , '" + fechaJueves + "', 'Comida'," + comidaJueves[i] + " )");
                }
            }
            for (int i = 0; i < meriendaJueves.length; i++) {
                if (meriendaJueves[i] != 0) {
                    dietaSemanal.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta Semanal' , '" + fechaJueves + "', 'Merienda'," + meriendaJueves[i] + " )");
                }
            }
            for (int i = 0; i < cenaJueves.length; i++) {
                if (cenaJueves[i] != 0) {
                    dietaSemanal.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta Semanal' , '" + fechaJueves + "', 'Cena'," + cenaJueves[i] + " )");
                }
            }

            for (int i = 0; i < desayunoViernes.length; i++) {
                if (desayunoViernes[i] != 0) {
                    dietaSemanal.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta Semanal' , '" + fechaViernes + "', 'Desayuno'," + desayunoViernes[i] + " )");
                }
            }
            for (int i = 0; i < almuerzoViernes.length; i++) {
                if (almuerzoViernes[i] != 0) {
                    dietaSemanal.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta Semanal' , '" + fechaViernes + "', 'Almuerzo'," + almuerzoViernes[i] + " )");
                }
            }
            for (int i = 0; i < comidaViernes.length; i++) {
                if (comidaViernes[i] != 0) {
                    dietaSemanal.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta Semanal' , '" + fechaViernes + "', 'Comida'," + comidaViernes[i] + " )");
                }
            }
            for (int i = 0; i < meriendaViernes.length; i++) {
                if (meriendaViernes[i] != 0) {
                    dietaSemanal.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta Semanal' , '" + fechaViernes + "', 'Merienda'," + meriendaViernes[i] + " )");
                }
            }
            for (int i = 0; i < cenaViernes.length; i++) {
                if (cenaViernes[i] != 0) {
                    dietaSemanal.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta Semanal' , '" + fechaViernes + "', 'Cena'," + cenaViernes[i] + " )");
                }
            }

            for (int i = 0; i < desayunoSabado.length; i++) {
                if (desayunoSabado[i] != 0) {
                    dietaSemanal.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta Semanal' , '" + fechaSabado + "', 'Desayuno'," + desayunoSabado[i] + " )");
                }
            }
            for (int i = 0; i < almuerzoSabado.length; i++) {
                if (almuerzoSabado[i] != 0) {
                    dietaSemanal.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta Semanal' , '" + fechaSabado + "', 'Almuerzo'," + almuerzoSabado[i] + " )");
                }
            }
            for (int i = 0; i < comidaSabado.length; i++) {
                if (comidaSabado[i] != 0) {
                    dietaSemanal.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta Semanal' , '" + fechaSabado + "', 'Comida'," + comidaSabado[i] + " )");
                }
            }
            for (int i = 0; i < meriendaSabado.length; i++) {
                if (meriendaSabado[i] != 0) {
                    dietaSemanal.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta Semanal' , '" + fechaSabado + "', 'Merienda'," + meriendaSabado[i] + " )");
                }
            }
            for (int i = 0; i < cenaSabado.length; i++) {
                if (cenaSabado[i] != 0) {
                    dietaSemanal.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta Semanal' , '" + fechaSabado + "', 'Cena'," + cenaSabado[i] + " )");
                }
            }

            for (int i = 0; i < desayunoDomingo.length; i++) {
                if (desayunoDomingo[i] != 0) {
                    dietaSemanal.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta Semanal' , '" + fechaDomingo + "', 'Desayuno'," + desayunoDomingo[i] + " )");
                }
            }
            for (int i = 0; i < almuerzoDomingo.length; i++) {
                if (almuerzoDomingo[i] != 0) {
                    dietaSemanal.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta Semanal' , '" + fechaDomingo + "', 'Almuerzo'," + almuerzoDomingo[i] + " )");
                }
            }
            for (int i = 0; i < comidaDomingo.length; i++) {
                if (comidaDomingo[i] != 0) {
                    dietaSemanal.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta Semanal' , '" + fechaDomingo + "', 'Comida'," + comidaDomingo[i] + " )");
                }
            }
            for (int i = 0; i < meriendaDomingo.length; i++) {
                if (meriendaDomingo[i] != 0) {
                    dietaSemanal.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta Semanal' , '" + fechaDomingo + "', 'Merienda'," + meriendaDomingo[i] + " )");
                }
            }
            for (int i = 0; i < cenaDomingo.length; i++) {
                if (cenaDomingo[i] != 0) {
                    dietaSemanal.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta Semanal' , '" + fechaDomingo + "', 'Cena'," + cenaDomingo[i] + " )");
                }
            }
            dietaSemanal.close();
            //Mensaje de éxito y vuelve a la pantalla del dietario
            Toast.makeText(this,
                    "Dieta Semanal con fecha de lunes " + fechaLunes + " a domingo  " + fechaDomingo + " guardada con éxito!", Toast.LENGTH_LONG).show();
            Intent oIntento = new Intent(this, Dietario.class);
            startActivity(oIntento);
            finish();
        }
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
        dietaDiaria.execSQL("DELETE FROM fecha WHERE dia LIKE '" + fechaLunes +"'");
        dietaDiaria.execSQL("DELETE FROM contiene WHERE dia LIKE '" + fechaLunes +"'");
        dietaDiaria.execSQL("DELETE FROM fecha WHERE dia LIKE '" + fechaMartes +"'");
        dietaDiaria.execSQL("DELETE FROM contiene WHERE dia LIKE '" + fechaMartes +"'");
        dietaDiaria.execSQL("DELETE FROM fecha WHERE dia LIKE '" + fechaMiercoles +"'");
        dietaDiaria.execSQL("DELETE FROM contiene WHERE dia LIKE '" + fechaMiercoles +"'");
        dietaDiaria.execSQL("DELETE FROM fecha WHERE dia LIKE '" + fechaJueves +"'");
        dietaDiaria.execSQL("DELETE FROM contiene WHERE dia LIKE '" + fechaJueves +"'");
        dietaDiaria.execSQL("DELETE FROM fecha WHERE dia LIKE '" + fechaViernes +"'");
        dietaDiaria.execSQL("DELETE FROM contiene WHERE dia LIKE '" + fechaViernes +"'");
        dietaDiaria.execSQL("DELETE FROM fecha WHERE dia LIKE '" + fechaSabado +"'");
        dietaDiaria.execSQL("DELETE FROM contiene WHERE dia LIKE '" + fechaSabado +"'");
        dietaDiaria.execSQL("DELETE FROM fecha WHERE dia LIKE '" + fechaDomingo +"'");
        dietaDiaria.execSQL("DELETE FROM contiene WHERE dia LIKE '" + fechaDomingo +"'");
        dietaDiaria.close();
        limpiarDieta(poView);
        txtdiaLunes.setText("");
        //Mensaje de éxito y vuelve a la pantalla del dietario
        Toast.makeText(this,
                "Dieta Semanal con fecha del lunes " + fechaLunes + " al domingo " + fechaDomingo + " eliminada con éxito!", Toast.LENGTH_LONG).show();
        Intent oIntento = new Intent(this, Dietario.class);
        startActivity(oIntento);
        finish();
    }

    public void calcularDiasSemana(Calendar date, int diasDiferencia){
        date.add(Calendar.DAY_OF_YEAR, diasDiferencia);

        fechaLunes = date.get(Calendar.DAY_OF_MONTH) + " / " + (date.get(Calendar.MONTH) + 1) + " / " + date.get(Calendar.YEAR);
        date.add(Calendar.DAY_OF_YEAR, 1);
        fechaMartes = date.get(Calendar.DAY_OF_MONTH) + " / " + (date.get(Calendar.MONTH) + 1) + " / " + date.get(Calendar.YEAR);
        date.add(Calendar.DAY_OF_YEAR, 1);
        fechaMiercoles = date.get(Calendar.DAY_OF_MONTH) + " / " + (date.get(Calendar.MONTH) + 1) + " / " + date.get(Calendar.YEAR);
        date.add(Calendar.DAY_OF_YEAR, 1);
        fechaJueves = date.get(Calendar.DAY_OF_MONTH) + " / " + (date.get(Calendar.MONTH) + 1) + " / " + date.get(Calendar.YEAR);
        date.add(Calendar.DAY_OF_YEAR, 1);
        fechaViernes = date.get(Calendar.DAY_OF_MONTH) + " / " + (date.get(Calendar.MONTH) + 1) + " / " + date.get(Calendar.YEAR);
        date.add(Calendar.DAY_OF_YEAR, 1);
        fechaSabado = date.get(Calendar.DAY_OF_MONTH) + " / " + (date.get(Calendar.MONTH) + 1) + " / " + date.get(Calendar.YEAR);
        date.add(Calendar.DAY_OF_YEAR, 1);
        fechaDomingo = date.get(Calendar.DAY_OF_MONTH) + " / " + (date.get(Calendar.MONTH) + 1) + " / " + date.get(Calendar.YEAR);
    }

    @Override
    public void onClick(View poView) {
        switch (poView.getId()) {
            case R.id.txtFechaSemanal:
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
                findViewById(R.id.btnGuardarSemanal).setEnabled(true);
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void cargarDietaLunes(String fecha) {
        String des="----- 0 -----\n", alm="----- 0 -----\n",
                com="----- 0 -----\n", mer="----- 0 -----\n", cen="----- 0 -----\n";
        BBDD admin = new BBDD(this,"administracion", null,1);
        SQLiteDatabase dietaSemanal = admin.getWritableDatabase();
        // Cargamos los distintos menús
        Cursor a = dietaSemanal.rawQuery("select dia, tipoComida, id from contiene", null);
        if (a != null) {
            if (a.moveToFirst()) {
                a.moveToFirst();
                do {
                    if(a.getString(0).contentEquals(fecha)){
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
        btnDesayunoLunes.setTextSize(8);
        btnDesayunoLunes.setText(des);
        btnAlmuerzoLunes.setTextSize(8);
        btnAlmuerzoLunes.setText(alm);
        btnComidaLunes.setTextSize(8);
        btnComidaLunes.setText(com);
        btnMeriendaLunes.setTextSize(8);
        btnMeriendaLunes.setText(mer);
        btnCenaLunes.setTextSize(8);
        btnCenaLunes.setText(cen);
    }

    public void cargarDietaMartes(String fecha){
        String des="----- 0 -----\n", alm="----- 0 -----\n",
                com="----- 0 -----\n", mer="----- 0 -----\n", cen="----- 0 -----\n";
        BBDD admin = new BBDD(this,"administracion", null,1);
        SQLiteDatabase dietaSemanal = admin.getWritableDatabase();
        // Cargamos los distintos menús
        Cursor a = dietaSemanal.rawQuery("select dia, tipoComida, id from contiene", null);
        if (a != null) {
            if (a.moveToFirst()) {
                a.moveToFirst();
                do {
                    if(a.getString(0).contentEquals(fecha)){
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
        btnDesayunoMartes.setTextSize(8);
        btnDesayunoMartes.setText(des);
        btnAlmuerzoMartes.setTextSize(8);
        btnAlmuerzoMartes.setText(alm);
        btnComidaMartes.setTextSize(8);
        btnComidaMartes.setText(com);
        btnMeriendaMartes.setTextSize(8);
        btnMeriendaMartes.setText(mer);
        btnCenaMartes.setTextSize(8);
        btnCenaMartes.setText(cen);
    }

    public void cargarDietaMiercoles(String fecha) {
        String des="----- 0 -----\n", alm="----- 0 -----\n",
                com="----- 0 -----\n", mer="----- 0 -----\n", cen="----- 0 -----\n";
        BBDD admin = new BBDD(this,"administracion", null,1);
        SQLiteDatabase dietaSemanal = admin.getWritableDatabase();
        // Cargamos los distintos menús
        Cursor a = dietaSemanal.rawQuery("select dia, tipoComida, id from contiene", null);
        if (a != null) {
            if (a.moveToFirst()) {
                a.moveToFirst();
                do {
                    if(a.getString(0).contentEquals(fecha)){
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
        btnDesayunoMiercoles.setTextSize(8);
        btnDesayunoMiercoles.setText(des);
        btnAlmuerzoMiercoles.setTextSize(8);
        btnAlmuerzoMiercoles.setText(alm);
        btnComidaMiercoles.setTextSize(8);
        btnComidaMiercoles.setText(com);
        btnMeriendaMiercoles.setTextSize(8);
        btnMeriendaMiercoles.setText(mer);
        btnCenaMiercoles.setTextSize(8);
        btnCenaMiercoles.setText(cen);
    }

    public void cargarDietaJueves(String fecha) {
        String des="----- 0 -----\n", alm="----- 0 -----\n",
                com="----- 0 -----\n", mer="----- 0 -----\n", cen="----- 0 -----\n";
        BBDD admin = new BBDD(this,"administracion", null,1);
        SQLiteDatabase dietaSemanal = admin.getWritableDatabase();
        // Cargamos los distintos menús
        Cursor a = dietaSemanal.rawQuery("select dia, tipoComida, id from contiene", null);
        if (a != null) {
            if (a.moveToFirst()) {
                a.moveToFirst();
                do {
                    if(a.getString(0).contentEquals(fecha)){
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
        btnDesayunoJueves.setTextSize(8);
        btnDesayunoJueves.setText(des);
        btnAlmuerzoJueves.setTextSize(8);
        btnAlmuerzoJueves.setText(alm);
        btnComidaJueves.setTextSize(8);
        btnComidaJueves.setText(com);
        btnMeriendaJueves.setTextSize(8);
        btnMeriendaJueves.setText(mer);
        btnCenaJueves.setTextSize(8);
        btnCenaJueves.setText(cen);
    }

    public void cargarDietaViernes(String fecha) {
        String des="----- 0 -----\n", alm="----- 0 -----\n",
                com="----- 0 -----\n", mer="----- 0 -----\n", cen="----- 0 -----\n";
        BBDD admin = new BBDD(this,"administracion", null,1);
        SQLiteDatabase dietaSemanal = admin.getWritableDatabase();
        // Cargamos los distintos menús
        Cursor a = dietaSemanal.rawQuery("select dia, tipoComida, id from contiene", null);
        if (a != null) {
            if (a.moveToFirst()) {
                a.moveToFirst();
                do {
                    if(a.getString(0).contentEquals(fecha)){
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
        btnDesayunoViernes.setTextSize(8);
        btnDesayunoViernes.setText(des);
        btnAlmuerzoViernes.setTextSize(8);
        btnAlmuerzoViernes.setText(alm);
        btnComidaViernes.setTextSize(8);
        btnComidaViernes.setText(com);
        btnMeriendaViernes.setTextSize(8);
        btnMeriendaViernes.setText(mer);
        btnCenaViernes.setTextSize(8);
        btnCenaViernes.setText(cen);
    }

    public void cargarDietaSabado(String fecha) {
        String des="----- 0 -----\n", alm="----- 0 -----\n",
                com="----- 0 -----\n", mer="----- 0 -----\n", cen="----- 0 -----\n";
        BBDD admin = new BBDD(this,"administracion", null,1);
        SQLiteDatabase dietaSemanal = admin.getWritableDatabase();
        // Cargamos los distintos menús
        Cursor a = dietaSemanal.rawQuery("select dia, tipoComida, id from contiene", null);
        if (a != null) {
            if (a.moveToFirst()) {
                a.moveToFirst();
                do {
                    if(a.getString(0).contentEquals(fecha)){
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
        btnDesayunoSabado.setTextSize(8);
        btnDesayunoSabado.setText(des);
        btnAlmuerzoSabado.setTextSize(8);
        btnAlmuerzoSabado.setText(alm);
        btnComidaSabado.setTextSize(8);
        btnComidaSabado.setText(com);
        btnMeriendaSabado.setTextSize(8);
        btnMeriendaSabado.setText(mer);
        btnCenaSabado.setTextSize(8);
        btnCenaSabado.setText(cen);
    }

    public void cargarDietaDomingo(String fecha) {
        String des="----- 0 -----\n", alm="----- 0 -----\n",
                com="----- 0 -----\n", mer="----- 0 -----\n", cen="----- 0 -----\n";
        BBDD admin = new BBDD(this,"administracion", null,1);
        SQLiteDatabase dietaSemanal = admin.getWritableDatabase();
        // Cargamos los distintos menús
        Cursor a = dietaSemanal.rawQuery("select dia, tipoComida, id from contiene", null);
        if (a != null) {
            if (a.moveToFirst()) {
                a.moveToFirst();
                do {
                    if(a.getString(0).contentEquals(fecha)){
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
        btnDesayunoDomingo.setTextSize(8);
        btnDesayunoDomingo.setText(des);
        btnAlmuerzoDomingo.setTextSize(8);
        btnAlmuerzoDomingo.setText(alm);
        btnComidaDomingo.setTextSize(8);
        btnComidaDomingo.setText(com);
        btnMeriendaDomingo.setTextSize(8);
        btnMeriendaDomingo.setText(mer);
        btnCenaDomingo.setTextSize(8);
        btnCenaDomingo.setText(cen);
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
}