package com.elorrieta.diet_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MenuAdapterCargar extends RecyclerView.Adapter<MenuAdapterCargar.MyViewHolder>{
    private List<Menu> listaItem;
    private final OnItemClickListener listener;

    /* Clase ViewHolder */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView icono;
        public TextView item;

        public MyViewHolder(View view) {
            super(view);
            icono = (ImageView) view.findViewById(R.id.icono);
            item = (TextView) view.findViewById(R.id.itemNombreIngrediente);
        }
    }

    //Constructor del Adaptador
    MenuAdapterCargar(List<Menu> listaItem, OnItemClickListener listener) {
        this.listaItem = listaItem;
        this.listener = listener;
    }

    // Este método es llamado por el RecyclerView para mostrar los datos del elemento de esa posición.
    @Override
    public void onBindViewHolder(MenuAdapterCargar.MyViewHolder holder, int position) {
        if(listaItem.get(position).getItem()=="Dieta Diaria"){
            holder.icono.setImageResource(R.drawable.dieta_diaria);
        } else if(listaItem.get(position).getItem()=="Dieta FinDe"){
            holder.icono.setImageResource(R.drawable.dieta_fin_de);
        } else {
            holder.icono.setImageResource(R.drawable.dieta_semanal);
        }
        Menu m = listaItem.get(position);
        holder.item.setText(m.getItem());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(m);
            }
        });
    }

    @Override
    public int getItemCount() {
            return listaItem.size();
    }

    // A este metodo se le llama cuando necesitamos crear una nueva linea para el RecyclerView.
    @Override
    public MenuAdapterCargar.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cargar_dieta,parent, false);
        return new MenuAdapterCargar.MyViewHolder(v);
    }
}
