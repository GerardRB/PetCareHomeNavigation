package com.example.petcarehome.homenavigation.ui.difusion.perdidas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.petcarehome.homenavigation.Objetos.ReportePerdidasID;
import com.example.petcarehome.R;

import java.util.ArrayList;

public class AdapterReportesPerdidas extends RecyclerView.Adapter<AdapterReportesPerdidas.ViewHolderReportesPerdidas> implements  View.OnClickListener{

    ArrayList<ReportePerdidasID> listReportesPerdidas;
    private View.OnClickListener listener;
    Context context;

    public AdapterReportesPerdidas(ArrayList<ReportePerdidasID> listReportesPerdidas, Context context) {
        this.listReportesPerdidas = listReportesPerdidas;
        this.context = context;
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
    public void onBindViewHolder(@NonNull final ViewHolderReportesPerdidas holder, int position) {
        holder.etizona.setText(listReportesPerdidas.get(position).getReportePerdidas().getAlcaldia());
        holder.etifecha.setText(listReportesPerdidas.get(position).getReportePerdidas().getFecha());
        holder.etinombre.setText(listReportesPerdidas.get(position).getReportePerdidas().getNombre());
        holder.etidescripcion.setText(listReportesPerdidas.get(position).getReportePerdidas().getDescripcion());
        //holder.foto.setImageURI(Uri.parse(listReportesPerdidas.get(position).getFoto()));
        Glide.with(context).load(listReportesPerdidas.get(position).getReportePerdidas().getFoto()).into(holder.foto);

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
