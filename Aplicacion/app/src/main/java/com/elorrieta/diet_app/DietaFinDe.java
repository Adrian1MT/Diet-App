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

public class DietaFinDe extends AppCompatActivity implements View.OnClickListener{
    String receta = "", textoBoton = "";
    int soyElBoton, id;
    int anio, mes, diaDelMes;
    Button boton, btnDesayunoSabado, btnAlmuerzoSabado, btnComidaSabado, btnMeriendaSabado, btnCenaSabado,
            btnDesayunoDomingo, btnAlmuerzoDomingo, btnComidaDomingo, btnMeriendaDomingo, btnCenaDomingo;
    Button btnLimpiarDieta, btnLimpiarMenu;
    Button btnGuardar;
    Button btnEliminarDieta;
    boolean limpiarMenuPulsado = false;

    EditText txtFecha;
    String fechaSabado = "";
    String fechaDomingo = "";
    TextView txtDia;
    String origen = "", fecha = "";

    //Arrays y variables para guardar los datos de la dieta
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
        setContentView(R.layout.activity_dieta_finde);

        txtFecha = (EditText) findViewById(R.id.txtFechaFinDe);
        txtFecha.setOnClickListener(this);

        btnDesayunoSabado = (Button) findViewById(R.id.btn10);
        btnAlmuerzoSabado = (Button) findViewById(R.id.btn18);
        btnComidaSabado = (Button) findViewById(R.id.btn26);
        btnMeriendaSabado = (Button) findViewById(R.id.btn34);
        btnCenaSabado = (Button) findViewById(R.id.btn42);
        btnDesayunoDomingo = (Button) findViewById(R.id.btn11);
        btnAlmuerzoDomingo = (Button) findViewById(R.id.btn19);
        btnComidaDomingo = (Button) findViewById(R.id.btn27);
        btnMeriendaDomingo = (Button) findViewById(R.id.btn35);
        btnCenaDomingo = (Button) findViewById(R.id.btn43);

        txtDia = (TextView) findViewById(R.id.txtDiaFinDe);
        btnLimpiarMenu = (Button) findViewById(R.id.btnLimpiarMenuFinDe);
        btnLimpiarDieta = (Button) findViewById(R.id.btnLimpiarDietaFinDe);
        btnGuardar = (Button) findViewById(R.id.btnGuardarFinDe);
        btnGuardar.setEnabled(false);

        //Recojo los valores de el adapter que llama a esta activity
        origen = getIntent().getStringExtra("origen");
        fecha = getIntent().getStringExtra("fecha");

        btnEliminarDieta = (Button) findViewById(R.id.btnEliminarFinDe);

        //Si venimos del recicler de ver dietas, apagamos los botones "limpiar" y "guardar" y encendemos el de "eliminar dieta"
        if(origen.contentEquals("ver")) {
            btnLimpiarMenu.setEnabled(false);
            btnLimpiarMenu.setVisibility(View.INVISIBLE);
            btnLimpiarDieta.setEnabled(false);
            btnLimpiarDieta.setVisibility(View.INVISIBLE);
            btnGuardar.setEnabled(false);
            btnGuardar.setVisibility(View.INVISIBLE);
            txtFecha.setVisibility(View.INVISIBLE);
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

            txtDia.setVisibility(View.VISIBLE);
            txtDia.setText(fecha);
            btnEliminarDieta.setVisibility(View.VISIBLE);

            String[] fechaArray = fecha.split(" / ");
            diaDelMes = Integer.parseInt(fechaArray[0].trim());
            mes = Integer.parseInt(fechaArray[1].trim());
            anio = Integer.parseInt(fechaArray[2].trim());
            String dia = diaDeLaSemana(anio, mes, diaDelMes);
            if(dia.contentEquals("Sábado")){
                fechaSabado = fecha;
                fechaDomingo = diaDelMes+1 + " / " + mes + " / " + anio;
            }
            if(dia.contentEquals("Domingo")){
                fechaDomingo = fecha;
                fechaSabado = diaDelMes-1 + " / " + mes + " / " + anio;
            }
            cargarDietaSabado(fechaSabado);
            cargarDietaDomingo(fechaDomingo);
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

    public void elegirMenu(View poView) {
        if (limpiarMenuPulsado) {
            boton = (Button) findViewById(poView.getId());
            boton.setTextSize(14);
            boton.setText("Menú");
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
            findViewById(R.id.btnLimpiarMenuFinDe).setBackgroundColor(0xffff8800);
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
            findViewById(R.id.btnLimpiarMenuFinDe).setBackgroundColor(0xFF4CAF50);
            btnLimpiarDieta.setEnabled(true);
        }
    }

    public void limpiarDieta(View poView) {
        //Array con los botones que hay en la pantalla
        Button[] BtnArraySabado = new Button[]{
                (Button) findViewById(R.id.btn10),
                (Button) findViewById(R.id.btn18),
                (Button) findViewById(R.id.btn26),
                (Button) findViewById(R.id.btn34),
                (Button) findViewById(R.id.btn42)};
        Button[] BtnArrayDomingo = new Button[]{
                (Button) findViewById(R.id.btn11),
                (Button) findViewById(R.id.btn19),
                (Button) findViewById(R.id.btn27),
                (Button) findViewById(R.id.btn35),
                (Button) findViewById(R.id.btn43)};
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
        for(int i=0;i<desayunoSabado.length;i++) {
            desayunoSabado[i] = 0;
            almuerzoSabado[i] = 0;
            meriendaSabado[i] = 0;
            cenaSabado[i] = 0;
        }
        for(int i=0;i<comidaSabado.length;i++) {
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
        for(int i=0;i<desayunoDomingo.length;i++) {
            desayunoDomingo[i] = 0;
            almuerzoDomingo[i] = 0;
            meriendaDomingo[i] = 0;
            cenaDomingo[i] = 0;
        }
        for(int i=0;i<comidaDomingo.length;i++) {
            comidaDomingo[i] = 0;
        }
    }

    // SACAMOS EL DIA DE LA SEMANA, a partir de la fecha
    public String diaDeLaSemana(int anio, int mes, int diaDelMes) {
        String diaSemana = "";
        //SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Calendar fecha = Calendar.getInstance();
        diaDelMes = diaDelMes - 2;
        //Para el caso de fin de semana a primeros de mes
        if(diaDelMes == 0){
            diaDelMes = 30;
            mes = mes - 1;
        }
        fecha.set(anio, mes, diaDelMes);
        int dia = fecha.get(Calendar.DAY_OF_WEEK);
        switch (dia) {
            case 1:
                diaSemana = "Domingo";
                break;
            case 2:
                diaSemana = "Lunes";
                break;
            case 3:
                diaSemana = "Martes";
                break;
            case 4:
                diaSemana = "Miércoles";
                break;
            case 5:
                diaSemana = "Jueves";
                break;
            case 6:
                diaSemana = "Viernes";
                break;
            case 7:
                diaSemana = "Sábado";
                break;
        }
        return diaSemana;
    }

    public void guardarDieta(View poView) {
        BBDD admin = new BBDD(this,"administracion", null,1);
        SQLiteDatabase dietaDiaria = admin.getWritableDatabase();

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

        String diaElegido = txtFecha.getText().toString();
        String[] fecha = diaElegido.split(" / ");
        diaDelMes = Integer.parseInt(fecha[0].trim());
        mes = Integer.parseInt(fecha[1].trim());
        anio = Integer.parseInt(fecha[2].trim());
        String dia = diaDeLaSemana(anio, mes, diaDelMes);
        if(dia.contentEquals("Sábado")){
            fechaSabado = diaElegido;
            fechaDomingo = diaDelMes+1 + " / " + mes + " / " + anio;
        }
        if(dia.contentEquals("Domingo")){
            fechaDomingo = diaElegido;
            fechaSabado = diaDelMes-1 + " / " + mes + " / " + anio;
        }

        if(listaFechas.contains(fechaSabado) || listaFechas.contains(fechaDomingo)) {
            //Mensaje de error
            Toast.makeText(this,
                    "Dieta FinDe con fecha " + fechaSabado + " ó " + fechaDomingo + " ya existe en la BBDD!", Toast.LENGTH_LONG).show();
        } else {
            dietaDiaria.execSQL("INSERT INTO fecha (dia, nomDieta) VALUES ('" + fechaSabado + "', 'Dieta FinDe')");
            dietaDiaria.execSQL("INSERT INTO fecha (dia, nomDieta) VALUES ('" + fechaDomingo + "', 'Dieta FinDe')");

            for (int i = 0; i < desayunoSabado.length; i++) {
                if (desayunoSabado[i] != 0) {
                    dietaDiaria.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta FinDe' , '" + fechaSabado + "', 'Desayuno'," + desayunoSabado[i] + " )");
                }
            }
            for (int i = 0; i < almuerzoSabado.length; i++) {
                if (almuerzoSabado[i] != 0) {
                    dietaDiaria.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta FinDe' , '" + fechaSabado + "', 'Almuerzo'," + almuerzoSabado[i] + " )");
                }
            }
            for (int i = 0; i < comidaSabado.length; i++) {
                if (comidaSabado[i] != 0) {
                    dietaDiaria.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta FinDe' , '" + fechaSabado + "', 'Comida'," + comidaSabado[i] + " )");
                }
            }
            for (int i = 0; i < meriendaSabado.length; i++) {
                if (meriendaSabado[i] != 0) {
                    dietaDiaria.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta FinDe' , '" + fechaSabado + "', 'Merienda'," + meriendaSabado[i] + " )");
                }
            }
            for (int i = 0; i < cenaSabado.length; i++) {
                if (cenaSabado[i] != 0) {
                    dietaDiaria.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta FinDe' , '" + fechaSabado + "', 'Cena'," + cenaSabado[i] + " )");
                }
            }

            for (int i = 0; i < desayunoDomingo.length; i++) {
                if (desayunoDomingo[i] != 0) {
                    dietaDiaria.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta FinDe' , '" + fechaDomingo + "', 'Desayuno'," + desayunoDomingo[i] + " )");
                }
            }
            for (int i = 0; i < almuerzoDomingo.length; i++) {
                if (almuerzoDomingo[i] != 0) {
                    dietaDiaria.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta FinDe' , '" + fechaDomingo + "', 'Almuerzo'," + almuerzoDomingo[i] + " )");
                }
            }
            for (int i = 0; i < comidaDomingo.length; i++) {
                if (comidaDomingo[i] != 0) {
                    dietaDiaria.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta FinDe' , '" + fechaDomingo + "', 'Comida'," + comidaDomingo[i] + " )");
                }
            }
            for (int i = 0; i < meriendaDomingo.length; i++) {
                if (meriendaDomingo[i] != 0) {
                    dietaDiaria.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta FinDe' , '" + fechaDomingo + "', 'Merienda'," + meriendaDomingo[i] + " )");
                }
            }
            for (int i = 0; i < cenaDomingo.length; i++) {
                if (cenaDomingo[i] != 0) {
                    dietaDiaria.execSQL("INSERT INTO contiene (nomDieta, dia, tipoComida, id) VALUES ('Dieta FinDe' , '" + fechaDomingo + "', 'Cena'," + cenaDomingo[i] + " )");
                }
            }
            dietaDiaria.close();
            //Mensaje de éxito y vuelve a la pantalla del dietario
            Toast.makeText(this,
                    "Dieta FinDe con fecha " + fechaSabado + " y " + fechaDomingo + " guardada con éxito!", Toast.LENGTH_LONG).show();
            Intent oIntento = new Intent(this, Dietario.class);
            startActivity(oIntento);
            finish();
        }
    }

    @Override
    public void onClick(View poView) {
        switch (poView.getId()) {
            case R.id.txtFechaFinDe:
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
                findViewById(R.id.btnGuardarFinDe).setEnabled(true);
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
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
        dietaDiaria.execSQL("DELETE FROM fecha WHERE dia LIKE '" + fechaSabado +"'");
        dietaDiaria.execSQL("DELETE FROM fecha WHERE dia LIKE '" + fechaDomingo +"'");
        dietaDiaria.execSQL("DELETE FROM contiene WHERE dia LIKE '" + fechaSabado +"'");
        dietaDiaria.execSQL("DELETE FROM contiene WHERE dia LIKE '" + fechaDomingo +"'");
        dietaDiaria.close();
        limpiarDieta(poView);
        txtDia.setText("");
        //Mensaje de éxito y vuelve a la pantalla del dietario
        Toast.makeText(this,
                "Dieta FinDe con fecha " + fechaSabado + " y " + fechaDomingo + " eliminada con éxito!", Toast.LENGTH_LONG).show();
        Intent oIntento = new Intent(this, Dietario.class);
        startActivity(oIntento);
        finish();
    }

    public void cargarDietaSabado(String fecha){
        String des="----- 0 -----\n", alm="----- 0 -----\n",
                com="----- 0 -----\n", mer="----- 0 -----\n", cen="----- 0 -----\n";
        BBDD admin = new BBDD(this,"administracion", null,1);
        SQLiteDatabase dietaFinDe = admin.getWritableDatabase();
        // Cargamos los distintos menús
        Cursor a = dietaFinDe.rawQuery("select dia, tipoComida, id from contiene", null);
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
        btnDesayunoSabado.setTextSize(10);
        btnDesayunoSabado.setText(des);
        btnAlmuerzoSabado.setTextSize(10);
        btnAlmuerzoSabado.setText(alm);
        btnComidaSabado.setTextSize(10);
        btnComidaSabado.setText(com);
        btnMeriendaSabado.setTextSize(10);
        btnMeriendaSabado.setText(mer);
        btnCenaSabado.setTextSize(10);
        btnCenaSabado.setText(cen);
    }

    public void cargarDietaDomingo(String fecha){
        String des="----- 0 -----\n", alm="----- 0 -----\n",
                com="----- 0 -----\n", mer="----- 0 -----\n", cen="----- 0 -----\n";
        BBDD admin = new BBDD(this,"administracion", null,1);
        SQLiteDatabase dietaFinDe = admin.getWritableDatabase();
        // Cargamos los distintos menús
        Cursor a = dietaFinDe.rawQuery("select dia, tipoComida, id from contiene", null);
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
        btnDesayunoDomingo.setTextSize(10);
        btnDesayunoDomingo.setText(des);
        btnAlmuerzoDomingo.setTextSize(10);
        btnAlmuerzoDomingo.setText(alm);
        btnComidaDomingo.setTextSize(10);
        btnComidaDomingo.setText(com);
        btnMeriendaDomingo.setTextSize(10);
        btnMeriendaDomingo.setText(mer);
        btnCenaDomingo.setTextSize(10);
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