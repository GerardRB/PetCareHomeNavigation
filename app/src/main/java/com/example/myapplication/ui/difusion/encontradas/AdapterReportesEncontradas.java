package com.example.myapplication.ui.difusion.encontradas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class AdapterReportesEncontradas extends RecyclerView.Adapter<AdapterReportesEncontradas.ViewHolderReportesEncontradas> implements View.OnClickListener{

    ArrayList<ReporteEncontradas> listReportesEncontradas;
    private View.OnClickListener listener;

    public AdapterReportesEncontradas(ArrayList<ReporteEncontradas> listReportesEncontradas) {
        this.listReportesEncontradas = listReportesEncontradas;
    }

    @NonNull
    @Override
    public AdapterReportesEncontradas.ViewHolderReportesEncontradas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_encontradas_list, null, false);
        view.setOnClickListener(this);
        return new ViewHolderReportesEncontradas(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterReportesEncontradas.ViewHolderReportesEncontradas holder, int position) {
        holder.etizona.setText(listReportesEncontradas.get(position).getAlcaldia());
        holder.etifecha.setText(listReportesEncontradas.get(position).getFecha());
        holder.etitipo.setText(listReportesEncontradas.get(position).getTipo());
        holder.etidescripcion.setText(listReportesEncontradas.get(position).getDescripcion());
        holder.foto.setImageResource(listReportesEncontradas.get(position).getFoto());

    }

    @Override
    public int getItemCount() {
        return listReportesEncontradas.size();
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

    public class ViewHolderReportesEncontradas extends RecyclerView.ViewHolder {

        TextView etizona, etifecha, etitipo, etidescripcion;
        ImageView foto;

        public ViewHolderReportesEncontradas(@NonNull View itemView) {
            super(itemView);
            etizona = (TextView) itemView.findViewById(R.id.idZonaME);
            etifecha = (TextView) itemView.findViewById(R.id.idFechaME);
            etitipo = (TextView) itemView.findViewById(R.id.idTipoME);
            etidescripcion = (TextView) itemView.findViewById(R.id.idDescripcionME);
            foto = (ImageView) itemView.findViewById(R.id.idImagenME);
        }

    }
}
