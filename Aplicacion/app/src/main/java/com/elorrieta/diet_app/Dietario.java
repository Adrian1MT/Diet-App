package com.elorrieta.diet_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Dietario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dietario);
    }

    public void dietaDiaria(View poView) {
        Intent oIntent = new Intent(this, DietaDiaria.class);
        startActivity(oIntent);
    }

    public void dietaFinDe(View poView) {
        Intent oIntent = new Intent(this, DietaFinDe.class);
        startActivity(oIntent);
    }

    public void dietaSemanal(View poView) {
        Intent oIntent = new Intent(this, DietaSemanal.class);
        startActivity(oIntent);
    }

    public void volver(View poView) {
        finish();
    }
}