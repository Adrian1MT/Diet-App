package com.elorrieta.diet_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DietaDiaria extends AppCompatActivity {
    String receta = "", textoBoton = "";
    int soyElBoton;
    Button boton;
    Boolean limpiarMenuPulsado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dieta_diaria);
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
            if (textoBoton.contentEquals("Menú"))
            {
                textoBoton = "----- 0 -----\n";
            }
            textoBoton += receta + "\n----- 0 -----\n";
            boton.setText(textoBoton);
        }
    }

    public void elegirMenu(View poView) {
        if (limpiarMenuPulsado) {
            boton = (Button) findViewById(poView.getId());
            boton.setTextSize(14);
            boton.setText("Menú");
        }else {
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
        if(limpiarMenuPulsado) {
            findViewById(R.id.btnLimpiarMenu).setBackgroundColor(0xffff8800);
        } else {
            findViewById(R.id.btnLimpiarMenu).setBackgroundColor(0xFF4CAF50);
        }
    }

    public void limpiarDieta(View poView) {
        //Array con los botones que hay en la pantalla
        Button[] BtnArray = new Button[] {
                (Button) findViewById(R.id.btn10),
                (Button) findViewById(R.id.btn18),
                (Button) findViewById(R.id.btn26),
                (Button) findViewById(R.id.btn34),
                (Button) findViewById(R.id.btn42)};
        //Los recorre y los limpia
        for (int i=0; i<5;i++) {
            boton = BtnArray[i];
            boton.setTextSize(14);
            boton.setText("Menú");
        }
    }

    public void guardarDieta(View poView) {
        Toast.makeText(this, "Guardar Dieta", Toast.LENGTH_LONG).show();
    }
}