package com.example.petcarehome.homenavigation.ui.mapa.dueno;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.Objetos.Servicio;

import java.util.ArrayList;

public class AdapterServicios extends RecyclerView.Adapter<AdapterServicios.ViewHolderServicios>{

    ArrayList<Servicio> listServicios;
    Context context;

    public AdapterServicios(ArrayList<Servicio> listServicios, Context context) {
        this.listServicios = listServicios;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderServicios onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_servicios_info, null, false);
        return new ViewHolderServicios(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AdapterServicios.ViewHolderServicios holder, int position) {
        holder.tipoServicio.setText(listServicios.get(position).getTipoServicio());
        holder.precio.setText(listServicios.get(position).getPrecio().toString());
        holder.descipcion.setText(listServicios.get(position).getDescripcion());
    }

    @Override
    public int getItemCount() {
        return listServicios.size();
    }

    public static class ViewHolderServicios extends RecyclerView.ViewHolder {
        TextView tipoServicio, precio, descipcion;

        public ViewHolderServicios(@NonNull View itemView) {
            super(itemView);

            tipoServicio = itemView.findViewById(R.id.text_tipo_servicio);
            precio = itemView.findViewById(R.id.text_precio);
            descipcion = itemView.findViewById(R.id.text_descripcion_servicio);
        }
    }
}
