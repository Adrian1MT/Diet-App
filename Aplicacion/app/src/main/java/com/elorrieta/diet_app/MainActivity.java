package com.elorrieta.diet_app;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ImageView Imagen, Titulo;

    TextView inicio;
    Boolean Terminar=false;
    ObjectAnimator AparecertAnimator;
    ObjectAnimator DesaparecertAnimator;
    //VARIABLES TEMPORALES DE PRUEBA- SE SUSTITUIRA CON UN MENU BIEN HECHO
    Button temporal1,temporal2,temporal3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Imagen= (ImageView)findViewById(R.id.imageView);
        Imagen.setImageResource(R.drawable.icono);

        Titulo= (ImageView)findViewById(R.id.imageView2);
        Titulo.setImageResource(R.drawable.nombre);
        inicio= findViewById(R.id.textTocar);
        //VARIABLES TEMPORALES DE PRUEBA- SE SUSTITUIRA CON UN MENU BIEN HECHO
        temporal1= (Button)findViewById(R.id.button);
        temporal2= (Button)findViewById(R.id.button2);
        temporal3= (Button)findViewById(R.id.button3);

        Imagen.setOnClickListener(this::mover_ObjectAnimator);
        Titulo.setOnClickListener(this::mover_ObjectAnimator);

        invisible();
    }
    public void invisible(){
        temporal1.setVisibility(View.INVISIBLE);
        temporal2.setVisibility(View.INVISIBLE);
        temporal3.setVisibility(View.INVISIBLE);
        ViewPropertyAnimator oAnimation = temporal1.animate();
        oAnimation.alpha(0f);
        oAnimation.start();

        ViewPropertyAnimator oAnimation2 = temporal2.animate();
        oAnimation2.alpha(0f);
        oAnimation2.start();

        ViewPropertyAnimator oAnimation3 = temporal3.animate();
        oAnimation3.alpha(0f);
        oAnimation3.start();
        Cartel();
    }
    public void mover_ObjectAnimator(View v) {
        ObjectAnimator oObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(
                Imagen,
                PropertyValuesHolder.ofFloat("translationX", -1150),
                PropertyValuesHolder.ofFloat("translationY", -350),
                PropertyValuesHolder.ofFloat("scaleX", 0.2f),
                PropertyValuesHolder.ofFloat("scaleY", 0.2f)
        );
        ObjectAnimator oObjectAnimator2 = ObjectAnimator.ofPropertyValuesHolder(
                Titulo,
                PropertyValuesHolder.ofFloat("translationX", -1140),
                PropertyValuesHolder.ofFloat("translationY", -800),
                PropertyValuesHolder.ofFloat("scaleX", 0.4f),
                PropertyValuesHolder.ofFloat("scaleY", 0.4f)
        );
        oObjectAnimator.setDuration(3000L);
        oObjectAnimator.setStartDelay(500L);

        oObjectAnimator2.setDuration(3000L);
        oObjectAnimator2.setStartDelay(500L);

        oObjectAnimator.start();
        oObjectAnimator2.start();

        visible();
    }
    public void visible(){
        Terminar=true;
        temporal1.setVisibility(View.VISIBLE);
        temporal2.setVisibility(View.VISIBLE);
        temporal3.setVisibility(View.VISIBLE);
        ViewPropertyAnimator oAnimation = temporal1.animate();
        oAnimation.alpha(1f);
        oAnimation.setDuration(2000);
        oAnimation.setStartDelay(2500L);
        oAnimation.start();

        ViewPropertyAnimator oAnimation2 = temporal2.animate();
        oAnimation2.alpha(1f);
        oAnimation2.setDuration(2000);
        oAnimation2.setStartDelay(2500L);
        oAnimation2.start();

        ViewPropertyAnimator oAnimation3 = temporal3.animate();
        oAnimation3.alpha(1f);
        oAnimation3.setDuration(2000);
        oAnimation3.setStartDelay(2500L);
        oAnimation3.start();
    }
    public void Cartel(){
        if (Terminar==false){

            AparecertAnimator = ObjectAnimator.ofFloat(inicio,View.ALPHA,1f,0f);
            AparecertAnimator.setDuration(500);
            AnimatorSet Animacioncontinua =new AnimatorSet();
            Animacioncontinua.playTogether(AparecertAnimator);
            Animacioncontinua.addListener(new AnimatorListenerAdapter() {

                @Override
                public void onAnimationEnd(Animator animation) {
                    DesaparecertAnimator=ObjectAnimator.ofFloat(inicio,View.ALPHA,0f,1f);
                    DesaparecertAnimator.setDuration(500);

                    AnimatorSet AnimacionRepetir =new AnimatorSet();
                    AnimacionRepetir.playTogether(DesaparecertAnimator);
                    AnimacionRepetir.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            Cartel();
                        }
                    });
                    AnimacionRepetir.start();
                }
            });
            Animacioncontinua.start();
        }if (Terminar==true){
            ViewPropertyAnimator oAnimation7 = inicio.animate();
            oAnimation7.alpha(0f);
            oAnimation7.setDuration(500L);
            oAnimation7.start();
        }
    }
}