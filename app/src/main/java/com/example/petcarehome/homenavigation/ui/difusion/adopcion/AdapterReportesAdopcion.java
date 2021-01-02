package com.example.petcarehome.homenavigation.ui.difusion.adopcion;

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
import com.example.petcarehome.homenavigation.ui.difusion.encontradas.DetalleReporteEncontradaActivity;

import java.util.ArrayList;

public class AdapterReportesAdopcion extends RecyclerView.Adapter<AdapterReportesAdopcion.ViewHolderReportesAdopcion> implements  View.OnClickListener{

    ArrayList<ReporteAdopcion> listReportesAdopcion;
    private View.OnClickListener listener;
    private Context context;

    public AdapterReportesAdopcion(ArrayList<ReporteAdopcion> listReportesAdopcion, Context context) {
        this.listReportesAdopcion = listReportesAdopcion;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterReportesAdopcion.ViewHolderReportesAdopcion onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adopcion_list, null, false);
        view.setOnClickListener(this);
        ViewHolderReportesAdopcion holder = new ViewHolderReportesAdopcion(view);
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
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, holder.foto, ViewCompat.getTransitionName(holder.foto));
                Bundle  bundle = new Bundle();
                bundle.putString("title", "");
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

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if(listener != null){
            listener.onClick(v);
        }

    }

    public class ViewHolderReportesAdopcion extends RecyclerView.ViewHolder {

        TextView etitipo, etiedad, etiRaza, etidescripcion;
        ImageView foto;
        LinearLayout info;

        public ViewHolderReportesAdopcion(@NonNull View itemView) {
            super(itemView);
            etitipo = (TextView) itemView.findViewById(R.id.idTipoMA);
            etiedad = (TextView) itemView.findViewById(R.id.idEdadMA);
            etiRaza = (TextView) itemView.findViewById(R.id.idRazaMA);
            etidescripcion = (TextView) itemView.findViewById(R.id.idDescripcionMA);
            foto = (ImageView) itemView.findViewById(R.id.idImagenMA);
            info = (LinearLayout) itemView.findViewById(R.id.layout_info);
        }

    }
}
