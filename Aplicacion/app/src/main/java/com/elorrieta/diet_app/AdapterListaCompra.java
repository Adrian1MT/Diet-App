package com.elorrieta.diet_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterListaCompra extends RecyclerView.Adapter<AdapterListaCompra.ViewHolderDatos> implements View.OnClickListener{
    ArrayList<String> listaDatos1;
    ArrayList<String> listaDatos2;
    ArrayList<String> listaDatos3;
    private View.OnClickListener listener;

    public AdapterListaCompra(ArrayList<String> listaDatos1, ArrayList<String> listaDatos2, ArrayList<String> listaDatos3) {
        this.listaDatos1 = listaDatos1;
        this.listaDatos2 = listaDatos2;
        this.listaDatos3 = listaDatos3;
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
        holder.asignarDatos(listaDatos1.get(position), listaDatos2.get(position), listaDatos3.get(position));
    }

    @Override
    public int getItemCount() {
        return listaDatos1.size();
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

        public void asignarDatos(String dato4, String dato5, String dato6) {
            dato1.setText(dato4);
            dato2.setText(dato5);
            dato3.setText(dato6);
        }
    }
}
