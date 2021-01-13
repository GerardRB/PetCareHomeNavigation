package com.example.petcarehome.homenavigation.ui.config.clases_fragmentos;

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
import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.Objetos.ReporteAdopcion;
import com.example.petcarehome.homenavigation.Objetos.ReporteEncontradas;
import com.example.petcarehome.homenavigation.Objetos.ReportePerdidas;
import com.example.petcarehome.homenavigation.ui.difusion.FullScreenImageActivity;
import com.example.petcarehome.homenavigation.ui.difusion.adopcion.DetalleReporteAdopcionActivity;
import com.example.petcarehome.homenavigation.ui.difusion.encontradas.DetalleReporteEncontradaActivity;
import com.example.petcarehome.homenavigation.ui.difusion.perdidas.DetalleReportePerdidasActivity;

import java.util.ArrayList;
import java.util.Objects;

public class AdapterMisReportes extends RecyclerView.Adapter<AdapterMisReportes.ViewHolderMisReportes>{

    ArrayList<ReportePerdidas> listReportesPerdidas;
    ArrayList<ReporteEncontradas> listReportesEncontradas;
    ArrayList<ReporteAdopcion> listReportesAdopcion;
    Context context;
    int count;
    //boolean isGeneral;


    public AdapterMisReportes(ArrayList<ReportePerdidas> listReportesPerdidas, ArrayList<ReporteEncontradas> listReportesEncontradas, ArrayList<ReporteAdopcion> listReportesAdopcion, Context context) {
        this.listReportesPerdidas = listReportesPerdidas;
        this.listReportesEncontradas = listReportesEncontradas;
        this.listReportesAdopcion = listReportesAdopcion;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterMisReportes.ViewHolderMisReportes onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mis_reportes, null, false);
        AdapterMisReportes.ViewHolderMisReportes holder;
        holder = new AdapterMisReportes.ViewHolderMisReportes(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterMisReportes.ViewHolderMisReportes holder, final int position) {
        if (position < listReportesPerdidas.size()){
            holder.tipoR.setText("MASCOTA EXTRAVIADA");
            holder.tipoM.setText(listReportesPerdidas.get(position).getTipo());
            holder.descripcion.setText(listReportesPerdidas.get(position).getDescripcion());
            Glide.with(context).load(listReportesPerdidas.get(position).getFoto()).apply(RequestOptions.circleCropTransform()).into(holder.fotoM);
            holder.info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentDetalleRMP = new Intent(context, DetalleReportePerdidasActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("reportePerdida", listReportesPerdidas.get(position));
                    bundle.putBoolean("general", false);
                    intentDetalleRMP.putExtras(bundle);
                    context.startActivity(intentDetalleRMP);
                }
            });
            holder.fotoM.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, FullScreenImageActivity.class);
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, holder.fotoM,  Objects.requireNonNull(ViewCompat.getTransitionName(holder.fotoM)));
                    Bundle  bundle = new Bundle();
                    bundle.putString("title", listReportesPerdidas.get(position).getNombre());
                    bundle.putString("foto", listReportesPerdidas.get(position).getFoto());
                    intent.putExtras(bundle);
                    context.startActivity(intent, options.toBundle());
                }
            });
        } else if (position >= listReportesPerdidas.size() && position  < (listReportesPerdidas.size() + listReportesEncontradas.size())){
            holder.tipoR.setText("MASCOTA ENCONTRADA");
            holder.tipoM.setText(listReportesEncontradas.get(position - listReportesPerdidas.size()).getTipo());
            holder.descripcion.setText(listReportesEncontradas.get(position - listReportesPerdidas.size()).getDescripcion());
            Glide.with(context).load(listReportesEncontradas.get(position - listReportesPerdidas.size()).getFoto()).apply(RequestOptions.circleCropTransform()).into(holder.fotoM);
            holder.info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentDetalleRMP = new Intent(context, DetalleReporteEncontradaActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("reporteEncontrada", listReportesEncontradas.get(position - listReportesPerdidas.size()));
                    bundle.putBoolean("general", false);
                    intentDetalleRMP.putExtras(bundle);
                    context.startActivity(intentDetalleRMP);
                }
            });
            holder.fotoM.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, FullScreenImageActivity.class);
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, holder.fotoM, Objects.requireNonNull(ViewCompat.getTransitionName(holder.fotoM)));
                    Bundle  bundle = new Bundle();
                    bundle.putString("title", "la mascota");
                    bundle.putString("foto", listReportesEncontradas.get(position - listReportesPerdidas.size()).getFoto());
                    intent.putExtras(bundle);
                    context.startActivity(intent, options.toBundle());
                }
            });
        } else if (position >= (listReportesPerdidas.size() + listReportesEncontradas.size()) && position < (listReportesPerdidas.size()) + listReportesEncontradas.size() +listReportesAdopcion.size()){
            holder.tipoR.setText("MASCOTA EN ADOPCIÓN");
            holder.tipoM.setText(listReportesAdopcion.get(position - (listReportesPerdidas.size() + listReportesEncontradas.size())).getTipo());
            holder.descripcion.setText(listReportesAdopcion.get(position - (listReportesPerdidas.size() + listReportesEncontradas.size())).getDescripcion());
            Glide.with(context).load(listReportesAdopcion.get(position - (listReportesPerdidas.size() + listReportesEncontradas.size())).getFoto()).apply(RequestOptions.circleCropTransform()).into(holder.fotoM);
            holder.info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentDetalleRMP = new Intent(context, DetalleReporteAdopcionActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("reporteAdopcion", listReportesAdopcion.get(position - (listReportesPerdidas.size() + listReportesEncontradas.size())));
                    bundle.putBoolean("general", false);
                    intentDetalleRMP.putExtras(bundle);
                    context.startActivity(intentDetalleRMP);
                }
            });
            holder.fotoM.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, FullScreenImageActivity.class);
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, holder.fotoM, Objects.requireNonNull(ViewCompat.getTransitionName(holder.fotoM)));
                    Bundle  bundle = new Bundle();
                    bundle.putString("title", "la mascota");
                    bundle.putString("foto", listReportesAdopcion.get(position - (listReportesPerdidas.size() + listReportesEncontradas.size())).getFoto());
                    intent.putExtras(bundle);
                    context.startActivity(intent, options.toBundle());
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        count = listReportesPerdidas.size() + listReportesEncontradas.size() + listReportesAdopcion.size();
        return count;
    }

    public static class ViewHolderMisReportes extends RecyclerView.ViewHolder {

        ImageView fotoM;
        LinearLayout info;
        TextView tipoM, descripcion, tipoR;

        public ViewHolderMisReportes(@NonNull View itemView) {
            super(itemView);

            fotoM = itemView.findViewById(R.id.idImagenMascota);
            info = itemView.findViewById(R.id.layout_info);
            tipoR = itemView.findViewById(R.id.idTipoRep);
            tipoM = itemView.findViewById(R.id.idTipoMascota);
            descripcion = itemView.findViewById(R.id.idDescripcionMisReportes);

        }
    }
}
