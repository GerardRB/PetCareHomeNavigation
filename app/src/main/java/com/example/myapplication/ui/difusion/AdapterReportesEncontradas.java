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

public class AdapterReportesEncontradas extends RecyclerView.Adapter<AdapterReportesEncontradas.ViewHolderReportesEncontradas> {

    ArrayList<ReporteEncontradas> listReportesEncontradas;

    public AdapterReportesEncontradas(ArrayList<ReporteEncontradas> listReportesEncontradas) {
        this.listReportesEncontradas = listReportesEncontradas;
    }

    @NonNull
    @Override
    public AdapterReportesEncontradas.ViewHolderReportesEncontradas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_encontradas_list, null, false);
        return new AdapterReportesEncontradas.ViewHolderReportesEncontradas(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterReportesEncontradas.ViewHolderReportesEncontradas holder, int position) {
        holder.etifecha.setText(listReportesEncontradas.get(position).getFecha());
        holder.etizona.setText(listReportesEncontradas.get(position).getZona());
        holder.etidescripcion.setText(listReportesEncontradas.get(position).getDescripcion());
        holder.foto.setImageResource(listReportesEncontradas.get(position).getFoto());

    }

    @Override
    public int getItemCount() {
        return listReportesEncontradas.size();
    }

    public class ViewHolderReportesEncontradas extends RecyclerView.ViewHolder {

        TextView etizona, etifecha, etidescripcion;
        ImageView foto;

        public ViewHolderReportesEncontradas(@NonNull View itemView) {
            super(itemView);
            etizona = (TextView) itemView.findViewById(R.id.idZonaME);
            etifecha = (TextView) itemView.findViewById(R.id.idFechaME);
            etidescripcion = (TextView) itemView.findViewById(R.id.idDescripcionME);
            foto = (ImageView) itemView.findViewById(R.id.idImagenME
            );
        }

    }
}
