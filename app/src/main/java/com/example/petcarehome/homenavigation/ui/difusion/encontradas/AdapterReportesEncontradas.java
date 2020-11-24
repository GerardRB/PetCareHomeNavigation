package com.example.petcarehome.homenavigation.ui.difusion.encontradas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petcarehome.homenavigation.Objetos.ReporteEncontradas;
import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.Objetos.ReporteEncontradasID;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterReportesEncontradas extends RecyclerView.Adapter<AdapterReportesEncontradas.ViewHolderReportesEncontradas> implements View.OnClickListener{

    ArrayList<ReporteEncontradasID> listReportesEncontradas;
    private View.OnClickListener listener;
    private Context context;

    public AdapterReportesEncontradas(ArrayList<ReporteEncontradasID> listReportesEncontradas, Context context) {
        this.listReportesEncontradas = listReportesEncontradas;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterReportesEncontradas.ViewHolderReportesEncontradas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_encontradas_list, null, false);
        view.setOnClickListener(this);
        ViewHolderReportesEncontradas holder =  new ViewHolderReportesEncontradas(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderReportesEncontradas holder, int position) {
        holder.etizona.setText(listReportesEncontradas.get(position).getReporteEncontradas().getAlcaldia());
        holder.etifecha.setText(listReportesEncontradas.get(position).getReporteEncontradas().getFecha());
        holder.etitipo.setText(listReportesEncontradas.get(position).getReporteEncontradas().getTipo());
        holder.etidescripcion.setText(listReportesEncontradas.get(position).getReporteEncontradas().getDescripcion());
        Picasso.with(context).load(listReportesEncontradas.get(position).getReporteEncontradas().getFoto()).into(holder.foto, new Callback() {
            @Override
            public void onSuccess() {
                holder.foto.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError() {
                Toast.makeText(context, "Error al cargar imagenes", Toast.LENGTH_LONG).show();
            }
        });

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
