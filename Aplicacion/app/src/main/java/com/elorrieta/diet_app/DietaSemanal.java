package com.elorrieta.diet_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DietaSemanal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dieta_semanal);
    }

    public void elegirMenu(View poView){
        Intent oIntent = new Intent(this, Visualizar_Recetas.class);
        startActivity(oIntent);
    }
}