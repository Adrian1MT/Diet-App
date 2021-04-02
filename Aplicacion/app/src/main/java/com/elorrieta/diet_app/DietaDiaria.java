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
        Intent oIntent = new Intent(this, Visualizar_Recetas.class);

        String NameOriginActivity = this.getLocalClassName().toString();
        String[] txtView = (poView.toString()).split("/");
        int OriginButtonId = poView.getId();

        oIntent.putExtra("activity", NameOriginActivity);
        oIntent.putExtra("button", OriginButtonId);
        startActivityForResult(oIntent, 2);
    }
}