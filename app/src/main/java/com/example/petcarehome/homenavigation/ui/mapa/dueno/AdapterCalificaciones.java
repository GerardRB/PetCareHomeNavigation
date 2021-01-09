package com.example.petcarehome.homenavigation.ui.mapa.dueno;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.Objetos.Calificacion;

import java.util.ArrayList;

public class AdapterCalificaciones extends RecyclerView.Adapter<AdapterCalificaciones.ViewHolderCalificaciones>{

    ArrayList<Calificacion> listCalif;
    Context context;

    public AdapterCalificaciones(ArrayList<Calificacion> listCalif, Context context) {
        this.listCalif = listCalif;
        this.context = context;
    }



    @NonNull
    @Override
    public ViewHolderCalificaciones onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calificacion_cuidador, null, false);
        return new ViewHolderCalificaciones(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCalificaciones.ViewHolderCalificaciones holder, int position) {
        holder.cal.setText("" + listCalif.get(position).getCalificacion());
        holder.com.setText(listCalif.get(position).getComentarios());
    }

    @Override
    public int getItemCount() {
        return listCalif.size();
    }

    public static class ViewHolderCalificaciones extends RecyclerView.ViewHolder {

        TextView cal, com;

        public ViewHolderCalificaciones(@NonNull View itemView) {
            super(itemView);

            cal = itemView.findViewById(R.id.text_cal);
            com = itemView.findViewById(R.id.text_comentario);
        }
    }
}
