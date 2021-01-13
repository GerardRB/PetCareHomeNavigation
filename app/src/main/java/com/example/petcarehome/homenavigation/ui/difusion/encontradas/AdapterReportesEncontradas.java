package com.example.petcarehome.homenavigation.ui.difusion.encontradas;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.petcarehome.homenavigation.Objetos.ReporteEncontradas;
import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.ui.difusion.FullScreenImageActivity;

import java.util.ArrayList;
import java.util.Objects;

public class AdapterReportesEncontradas extends RecyclerView.Adapter<AdapterReportesEncontradas.ViewHolderReportesEncontradas>{

    ArrayList<ReporteEncontradas> listReportesEncontradas;
    private final Context context;

    public AdapterReportesEncontradas(ArrayList<ReporteEncontradas> listReportesEncontradas, Context context) {
        this.listReportesEncontradas = listReportesEncontradas;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterReportesEncontradas.ViewHolderReportesEncontradas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_encontradas_list, null, false);
        ViewHolderReportesEncontradas holder;
        holder = new ViewHolderReportesEncontradas(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderReportesEncontradas holder, final int position) {
        holder.etizona.setText(listReportesEncontradas.get(position).getAlcaldia());
        holder.etifecha.setText(listReportesEncontradas.get(position).getFecha());
        holder.etitipo.setText(listReportesEncontradas.get(position).getTipo());
        holder.etidescripcion.setText(listReportesEncontradas.get(position).getDescripcion());
        Glide.with(context).load(listReportesEncontradas.get(position).getFoto()).apply(RequestOptions.circleCropTransform()).into(holder.foto);
        holder.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDetalleRMP = new Intent(context, DetalleReporteEncontradaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("reporteEncontrada", listReportesEncontradas.get(position));
                bundle.putBoolean("general", true);
                intentDetalleRMP.putExtras(bundle);
                context.startActivity(intentDetalleRMP);
            }
        });
        holder.foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FullScreenImageActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, holder.foto, Objects.requireNonNull(ViewCompat.getTransitionName(holder.foto)));
                Bundle  bundle = new Bundle();
                bundle.putString("title", "la mascota");
                bundle.putString("foto", listReportesEncontradas.get(position).getFoto());
                intent.putExtras(bundle);
                context.startActivity(intent, options.toBundle());
            }
        });

    }

    @Override
    public int getItemCount() {
        return listReportesEncontradas.size();
    }


    public static class ViewHolderReportesEncontradas extends RecyclerView.ViewHolder {

        TextView etizona, etifecha, etitipo, etidescripcion;
        ImageView foto;
        LinearLayout info;

        public ViewHolderReportesEncontradas(@NonNull View itemView) {
            super(itemView);
            etizona = itemView.findViewById(R.id.idZonaME);
            etifecha = itemView.findViewById(R.id.idFechaME);
            etitipo = itemView.findViewById(R.id.idTipoME);
            etidescripcion = itemView.findViewById(R.id.idDescripcionME);
            foto = itemView.findViewById(R.id.idImagenME);
            info = itemView.findViewById(R.id.layout_info);
        }

    }
}
