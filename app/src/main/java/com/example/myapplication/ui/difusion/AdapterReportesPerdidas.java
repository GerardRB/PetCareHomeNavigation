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

public class AdapterReportesPerdidas extends RecyclerView.Adapter<AdapterReportesPerdidas.ViewHolderReportesPerdidas>{

    ArrayList<ReportePerdidas> listReportesPerdidas;

    public AdapterReportesPerdidas(ArrayList<ReportePerdidas> listReportesPerdidas) {
        this.listReportesPerdidas = listReportesPerdidas;
    }

    @NonNull
    @Override
    public ViewHolderReportesPerdidas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_perdidas_list, null, false);
        return new ViewHolderReportesPerdidas(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderReportesPerdidas holder, int position) {
        holder.etinombre.setText(listReportesPerdidas.get(position).getNombre());
        holder.etifecha.setText(listReportesPerdidas.get(position).getFecha());
        holder.etidescripcion.setText(listReportesPerdidas.get(position).getDescripcion());
        holder.foto.setImageResource(listReportesPerdidas.get(position).getFoto());

    }

    @Override
    public int getItemCount() {
        return listReportesPerdidas.size();
    }

    public class ViewHolderReportesPerdidas extends RecyclerView.ViewHolder {

        TextView etinombre, etifecha, etizona, etidescripcion;
        ImageView foto;

        public ViewHolderReportesPerdidas(@NonNull View itemView) {
            super(itemView);
            etinombre = (TextView) itemView.findViewById(R.id.idNombreMP);
            etifecha = (TextView) itemView.findViewById(R.id.idFechaMP);
            etizona = (TextView) itemView.findViewById(R.id.idZonaMP);
            etidescripcion = (TextView) itemView.findViewById(R.id.idDescripcionMP);
            foto = (ImageView) itemView.findViewById(R.id.idImagenMP);
        }

    }
}
