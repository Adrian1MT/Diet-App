package com.elorrieta.diet_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterIngredientes extends RecyclerView.Adapter<AdapterIngredientes.ViewHolderDatos> implements View.OnClickListener{

    ArrayList<String> listaDatos1;
    ArrayList<String> listaDatos2;
    private View.OnClickListener listener;

    public AdapterIngredientes(ArrayList<String> listaDatos1, ArrayList<String> listaDatos2) {
        this.listaDatos1 = listaDatos1;
        this.listaDatos2 = listaDatos2;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingrediente,null,false);
        view.setOnClickListener(this);
       // view.setOnLongClickListener((View.OnLongClickListener) this);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        holder.asignarDatos(listaDatos1.get(position), listaDatos2.get(position));
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

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            dato1 = (TextView) itemView.findViewById(R.id.ingrediente);
            dato2 = (TextView) itemView.findViewById(R.id.unidad);
        }

        public void asignarDatos(String dato3, String dato4) {
            dato1.setText(dato3);
            dato2.setText(dato4);
        }
    }
}
