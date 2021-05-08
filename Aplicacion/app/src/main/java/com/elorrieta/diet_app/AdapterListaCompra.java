package com.elorrieta.diet_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterListaCompra extends RecyclerView.Adapter<AdapterListaCompra.ViewHolderDatos> implements View.OnClickListener{
    ArrayList<Ingrediente> listaIngredientes;
    private View.OnClickListener listener;

    public AdapterListaCompra(ArrayList<Ingrediente> listaIngredientes) {
        this.listaIngredientes = listaIngredientes;
    }

    @NonNull
    @Override
    public AdapterListaCompra.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_ingredientes,null,false);
        view.setOnClickListener(this);
        // view.setOnLongClickListener((View.OnLongClickListener) this);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListaCompra.ViewHolderDatos holder, int position) {
        holder.asignarDatos(listaIngredientes.get(position));
    }

    @Override
    public int getItemCount() {
        return listaIngredientes.size();
    }

    /*public void setOnLongClickListener(View.OnLongClickListener listener){
        this.listener = (View.OnClickListener) listener;
    }*/

    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        TextView dato1;
        TextView dato2;
        TextView dato3;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            dato1 = (TextView) itemView.findViewById(R.id.itemNombreIngrediente);
            dato2 = (TextView) itemView.findViewById(R.id.itemCantidad);
            dato3 = (TextView) itemView.findViewById(R.id.itemUnidad);
        }

        public void asignarDatos(Ingrediente ingrediente) {
            dato1.setText(ingrediente.nombre);
            dato2.setText(String.valueOf(ingrediente.cantidad));
            dato3.setText(ingrediente.unidad);
        }
    }
}
