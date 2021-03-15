package com.elorrieta.diet_app;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class AdapterSeleccionDerecha extends RecyclerView.Adapter<AdapterSeleccionDerecha.ViewHolderDatos> implements View.OnClickListener{

    ArrayList<String> listaDatos;
    private View.OnClickListener listener;

    public AdapterSeleccionDerecha(ArrayList<String> listaDatos) {
        this.listaDatos = listaDatos;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_seleccion_derecho,null,false);
       // view.setOnClickListener(this);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        holder.asignarDatos(listaDatos.get(position));
        holder.setOnClickListeners();
    }

    @Override
    public int getItemCount() {
        return listaDatos.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView dato;
        Context context;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            dato = (TextView) itemView.findViewById(R.id.idSeleccion);
            context = itemView.getContext();
        }

        public void asignarDatos(String datos) {
            dato.setText(datos);
        }

        public void setOnClickListeners() {
            dato.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.idSeleccion:
                    int colorText = dato.getCurrentTextColor();
                    String hexColorText = "#" + Integer.toHexString(colorText).substring(2);

                    if(hexColorText.equals("#0a7d12")){
                        dato.setTextColor(context.getResources().getColor(R.color.black));
                        //dato.setBackgroundColor(context.getResources().getColor(R.color.white));
                    } else{
                        dato.setTextColor(Color.parseColor("#0a7d12"));
                        //dato.setBackgroundColor(context.getResources().getColor(R.color.verde_claro));
                    }

                    break;
            }
        }
    }
}