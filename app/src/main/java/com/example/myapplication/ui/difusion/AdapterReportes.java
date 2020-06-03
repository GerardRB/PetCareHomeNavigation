package com.example.myapplication.ui.difusion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class AdapterReportes extends RecyclerView.Adapter<AdapterReportes.ViewHolderReportes>{

    ArrayList<ReporteVo> listReportes;

    public AdapterReportes(ArrayList<ReporteVo> listReportes) {
        this.listReportes = listReportes;
    }

    @NonNull
    @Override
    public ViewHolderReportes onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, null, false);
        return new ViewHolderReportes(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderReportes holder, int position) {
        holder.etinombre.setText(listReportes.get(position).getNombre());
        holder.etifecha.setText(listReportes.get(position).getFecha());
        holder.etidescripcion.setText(listReportes.get(position).getDescripcion());
        holder.foto.setImageResource(listReportes.get(position).getFoto());

    }

    @Override
    public int getItemCount() {
        return listReportes.size();
    }

    public class ViewHolderReportes extends RecyclerView.ViewHolder {

        TextView etinombre, etifecha, etidescripcion;
        ImageView foto;

        public ViewHolderReportes(@NonNull View itemView) {
            super(itemView);
            etinombre = (TextView) itemView.findViewById(R.id.idNombre);
            etifecha = (TextView) itemView.findViewById(R.id.idFecha);
            etidescripcion = (TextView) itemView.findViewById(R.id.idDescripcion);
            foto = (ImageView) itemView.findViewById(R.id.idImagen);
        }

    }
}
