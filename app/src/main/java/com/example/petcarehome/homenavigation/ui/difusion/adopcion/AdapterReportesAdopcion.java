package com.example.petcarehome.homenavigation.ui.difusion.adopcion;

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
import com.example.petcarehome.homenavigation.Objetos.ReporteAdopcion;
import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.ui.difusion.FullScreenImageActivity;

import java.util.ArrayList;
import java.util.Objects;

public class AdapterReportesAdopcion extends RecyclerView.Adapter<AdapterReportesAdopcion.ViewHolderReportesAdopcion>{

    ArrayList<ReporteAdopcion> listReportesAdopcion;
    private final Context context;

    public AdapterReportesAdopcion(ArrayList<ReporteAdopcion> listReportesAdopcion, Context context) {
        this.listReportesAdopcion = listReportesAdopcion;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterReportesAdopcion.ViewHolderReportesAdopcion onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adopcion_list, null, false);
        ViewHolderReportesAdopcion holder;
        holder = new ViewHolderReportesAdopcion(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterReportesAdopcion.ViewHolderReportesAdopcion holder, final int position) {
        holder.etitipo.setText(listReportesAdopcion.get(position).getTipo());
        holder.etiedad.setText(listReportesAdopcion.get(position).getEdad());
        holder.etiRaza.setText(listReportesAdopcion.get(position).getRaza());
        holder.etidescripcion.setText(listReportesAdopcion.get(position).getDescripcion());
        Glide.with(context).load(listReportesAdopcion.get(position).getFoto()).apply(RequestOptions.circleCropTransform()).into(holder.foto);
        holder.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDetalleRMP = new Intent(context, DetalleReporteAdopcionActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("reporteAdopcion", listReportesAdopcion.get(position));
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
                bundle.putString("foto", listReportesAdopcion.get(position).getFoto());
                intent.putExtras(bundle);
                context.startActivity(intent, options.toBundle());
            }
        });


    }

    @Override
    public int getItemCount() {
        return listReportesAdopcion.size();
    }


    public static class ViewHolderReportesAdopcion extends RecyclerView.ViewHolder {

        TextView etitipo, etiedad, etiRaza, etidescripcion;
        ImageView foto;
        LinearLayout info;

        public ViewHolderReportesAdopcion(@NonNull View itemView) {
            super(itemView);
            etitipo = itemView.findViewById(R.id.idTipoMA);
            etiedad = itemView.findViewById(R.id.idEdadMA);
            etiRaza = itemView.findViewById(R.id.idRazaMA);
            etidescripcion = itemView.findViewById(R.id.idDescripcionMA);
            foto = itemView.findViewById(R.id.idImagenMA);
            info = itemView.findViewById(R.id.layout_info);
        }

    }
}
