package com.example.petcarehome.homenavigation.ui.difusion.adopcion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petcarehome.homenavigation.Objetos.ReporteAdopcion;
import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.Objetos.ReporteAdopcionID;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterReportesAdopcion extends RecyclerView.Adapter<AdapterReportesAdopcion.ViewHolderReportesAdopcion> implements  View.OnClickListener{

    ArrayList<ReporteAdopcionID> listReportesAdopcion;
    private View.OnClickListener listener;
    private Context context;

    public AdapterReportesAdopcion(ArrayList<ReporteAdopcionID> listReportesAdopcion, Context context) {
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
    public void onBindViewHolder(@NonNull final AdapterReportesAdopcion.ViewHolderReportesAdopcion holder, int position) {
        holder.etitipo.setText(listReportesAdopcion.get(position).getReporteAdopcion().getTipo());
        holder.etiedad.setText(listReportesAdopcion.get(position).getReporteAdopcion().getEdad());
        holder.etiRaza.setText(listReportesAdopcion.get(position).getReporteAdopcion().getRaza());
        holder.etidescripcion.setText(listReportesAdopcion.get(position).getReporteAdopcion().getDescripcion());
        Picasso.with(context).load(listReportesAdopcion.get(position).getReporteAdopcion().getFoto()).into(holder.foto, new Callback() {
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

        public ViewHolderReportesAdopcion(@NonNull View itemView) {
            super(itemView);
            etitipo = (TextView) itemView.findViewById(R.id.idTipoMA);
            etiedad = (TextView) itemView.findViewById(R.id.idEdadMA);
            etiRaza = (TextView) itemView.findViewById(R.id.idRazaMA);
            etidescripcion = (TextView) itemView.findViewById(R.id.idDescripcionMA);
            foto = (ImageView) itemView.findViewById(R.id.idImagenMA);
        }

    }
}
