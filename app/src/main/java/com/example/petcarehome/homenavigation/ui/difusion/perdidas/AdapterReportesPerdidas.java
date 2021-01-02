package com.example.petcarehome.homenavigation.ui.difusion.perdidas;

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
import com.example.petcarehome.homenavigation.Objetos.ReportePerdidas;
import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.ui.difusion.FullScreenImageActivity;

import java.util.ArrayList;

public class AdapterReportesPerdidas extends RecyclerView.Adapter<AdapterReportesPerdidas.ViewHolderReportesPerdidas> {

    ArrayList<ReportePerdidas> listReportesPerdidas;
    Context context;

    public AdapterReportesPerdidas(ArrayList<ReportePerdidas> listReportesPerdidas, Context context) {
        this.listReportesPerdidas = listReportesPerdidas;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderReportesPerdidas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_perdidas_list, null, false);
        ViewHolderReportesPerdidas holder;
        holder = new ViewHolderReportesPerdidas(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderReportesPerdidas holder, final int position) {
        holder.etizona.setText(listReportesPerdidas.get(position).getAlcaldia());
        holder.etifecha.setText(listReportesPerdidas.get(position).getFecha());
        holder.etinombre.setText(listReportesPerdidas.get(position).getNombre());
        holder.etidescripcion.setText(listReportesPerdidas.get(position).getDescripcion());
        //holder.foto.setImageURI(Uri.parse(listReportesPerdidas.get(position).getFoto()));
        Glide.with(context).load(listReportesPerdidas.get(position).getFoto()).apply(RequestOptions.circleCropTransform()).into(holder.foto);
        holder.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDetalleRMP = new Intent(context, DetalleReportePerdidasActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("reportePerdida", listReportesPerdidas.get(position));
                intentDetalleRMP.putExtras(bundle);
                context.startActivity(intentDetalleRMP);
            }
        });
        holder.foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FullScreenImageActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, holder.foto, ViewCompat.getTransitionName(holder.foto));
                Bundle  bundle = new Bundle();
                bundle.putString("title", listReportesPerdidas.get(position).getNombre());
                bundle.putString("foto", listReportesPerdidas.get(position).getFoto());
                intent.putExtras(bundle);
                context.startActivity(intent, options.toBundle());
            }
        });

    }

    @Override
    public int getItemCount() {
        return listReportesPerdidas.size();
    }


    public static class ViewHolderReportesPerdidas extends RecyclerView.ViewHolder {

        TextView etinombre, etifecha, etizona, etidescripcion;
        ImageView foto;
        LinearLayout info;

        public ViewHolderReportesPerdidas(@NonNull View itemView) {
            super(itemView);
            etizona = (TextView) itemView.findViewById(R.id.idZonaMP);
            etifecha = (TextView) itemView.findViewById(R.id.idFechaMP);
            etinombre = (TextView) itemView.findViewById(R.id.idNombreMP);
            etidescripcion = (TextView) itemView.findViewById(R.id.idDescripcionMP);
            foto = (ImageView) itemView.findViewById(R.id.idImagenMP);
            info = (LinearLayout) itemView.findViewById(R.id.layout_info);
        }

    }
}
