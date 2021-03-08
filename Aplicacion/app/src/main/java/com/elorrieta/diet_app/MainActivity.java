package com.elorrieta.diet_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ImageView Imagen, Titulo;
<<<<<<< HEAD
    RecyclerView oRecyclerView;
    ArrayList<Menu> menuArrayList;
    View view;

    // inicializarmos el adapter con nuestros datos.
    OnItemClickListener escuchador = new OnItemClickListener() {
        @Override
        public void onItemClick(Menu item) {
            view = new View(MainActivity.super.getApplicationContext());
            if(item.getItem().contentEquals("Recetas")){
                Recetas(view);
            } else if (item.getItem().contentEquals("Dietario")) {
                Dietario(view);
            } else {
                ListaCompra(view);
            }
        }
    };

=======
    final int iCODIGO = 1234;
    TextView inicio;
    Boolean Terminar=false;
    ObjectAnimator AparecertAnimator;
    ObjectAnimator DesaparecertAnimator;
    //VARIABLES TEMPORALES DE PRUEBA- SE SUSTITUIRA CON UN MENU BIEN HECHO
    Button temporal1,temporal2,temporal3;
>>>>>>> 6c8e56ae61e14276937fac935c42e839e5b0cba5
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Imagen= (ImageView)findViewById(R.id.imageView);
        Imagen.setImageResource(R.drawable.icono);

        Titulo= (ImageView)findViewById(R.id.imageView2);
        Titulo.setImageResource(R.drawable.nombre);

        Imagen.setOnClickListener(this::mover_ObjectAnimator);
        Titulo.setOnClickListener(this::mover_ObjectAnimator);

        // Llenamos el ArrayList.
        menuArrayList = new ArrayList<Menu>();
        menuArrayList.add(new Menu("Recetas"));
        menuArrayList.add(new Menu("Dietario"));
        menuArrayList.add(new Menu("Lista de la compra"));

        oRecyclerView = (RecyclerView) findViewById(R.id.Recycler);

        MenuAdapter ma = new MenuAdapter(menuArrayList, escuchador);
        oRecyclerView.setAdapter(ma);

        // establecemos el Layout Manager.
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        oRecyclerView.setLayoutManager(llm);
        oRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    public void mover_ObjectAnimator(View v) {
        ObjectAnimator oObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(
                Imagen,
                PropertyValuesHolder.ofFloat("alpha", 1f,0f),
                PropertyValuesHolder.ofFloat("translationX", -1150),
                PropertyValuesHolder.ofFloat("translationY", -350),
                PropertyValuesHolder.ofFloat("scaleX", 0.2f),
                PropertyValuesHolder.ofFloat("scaleY", 0.2f)
        );
        ObjectAnimator oObjectAnimator2 = ObjectAnimator.ofPropertyValuesHolder(
                Titulo,
                PropertyValuesHolder.ofFloat("alpha", 1f,0f),
                PropertyValuesHolder.ofFloat("translationX", -1140),
                PropertyValuesHolder.ofFloat("translationY", -800),
                PropertyValuesHolder.ofFloat("scaleX", 0.4f),
                PropertyValuesHolder.ofFloat("scaleY", 0.4f)
        );
        ObjectAnimator oObjectAnimator3 = ObjectAnimator.ofPropertyValuesHolder(
                oRecyclerView,
                PropertyValuesHolder.ofFloat("alpha", 0f,1f),
                PropertyValuesHolder.ofFloat("translationX", 200),
                PropertyValuesHolder.ofFloat("scaleX", 1.2f),
                PropertyValuesHolder.ofFloat("scaleY", 1.2f)
        );
        oObjectAnimator.setDuration(3000L);
        oObjectAnimator.setStartDelay(500L);

        oObjectAnimator2.setDuration(3000L);
        oObjectAnimator2.setStartDelay(500L);

        oObjectAnimator3.setDuration(3000L);
        oObjectAnimator3.setStartDelay(500L);

        oObjectAnimator.start();
        oObjectAnimator2.start();
        oObjectAnimator3.start();
    }

    public void Recetas(View poView){
        Intent oIntent = new Intent(this, Recetas.class);
        startActivity(oIntent);
    }
    public void Dietario(View poView){
        Intent oIntent = new Intent(this, Dietario.class);
        startActivity(oIntent);
    }

    public void ListaCompra(View poView){
        Intent oIntent = new Intent(this, ListaCompra.class);
        startActivity(oIntent);
    }
    public void IrVisualizar(View poView){
        Intent oIntent = new Intent(this, Visualizar_Recetas.class);
        startActivityForResult(oIntent, iCODIGO);
    }
}