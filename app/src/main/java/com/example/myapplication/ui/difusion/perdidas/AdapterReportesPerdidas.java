package com.example.myapplication.ui.difusion.perdidas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class AdapterReportesPerdidas extends RecyclerView.Adapter<AdapterReportesPerdidas.ViewHolderReportesPerdidas> implements  View.OnClickListener{

    ArrayList<ReportePerdidas> listReportesPerdidas;
    private View.OnClickListener listener;

    public AdapterReportesPerdidas(ArrayList<ReportePerdidas> listReportesPerdidas) {
        this.listReportesPerdidas = listReportesPerdidas;
    }

    @NonNull
    @Override
    public ViewHolderReportesPerdidas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_perdidas_list, null, false);
        view.setOnClickListener(this);
        ViewHolderReportesPerdidas holder = new ViewHolderReportesPerdidas(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderReportesPerdidas holder, int position) {
        holder.etizona.setText(listReportesPerdidas.get(position).getAlcaldia());
        holder.etifecha.setText(listReportesPerdidas.get(position).getFecha());
        holder.etinombre.setText(listReportesPerdidas.get(position).getNombre());
        holder.etidescripcion.setText(listReportesPerdidas.get(position).getDescripcion());
        holder.foto.setImageResource(listReportesPerdidas.get(position).getFoto());

    }

    @Override
    public int getItemCount() {
        return listReportesPerdidas.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if(listener != null){
            listener.onClick(v);
        }

    }

    public class ViewHolderReportesPerdidas extends RecyclerView.ViewHolder {

        TextView etinombre, etifecha, etizona, etidescripcion;
        ImageView foto;

        public ViewHolderReportesPerdidas(@NonNull View itemView) {
            super(itemView);
            etizona = (TextView) itemView.findViewById(R.id.idZonaMP);
            etifecha = (TextView) itemView.findViewById(R.id.idFechaMP);
            etinombre = (TextView) itemView.findViewById(R.id.idNombreMP);
            etidescripcion = (TextView) itemView.findViewById(R.id.idDescripcionMP);
            foto = (ImageView) itemView.findViewById(R.id.idImagenMP);
        }

    }
}
