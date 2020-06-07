package com.example.myapplication.ui.difusion.adopcion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class AdapterReportesAdopcion extends RecyclerView.Adapter<AdapterReportesAdopcion.ViewHolderReportesAdopcion> implements  View.OnClickListener{

    ArrayList<ReporteAdopcion> listReportesAdopcion;
    private View.OnClickListener listener;

    public AdapterReportesAdopcion(ArrayList<ReporteAdopcion> listReportesAdopcion) {
        this.listReportesAdopcion = listReportesAdopcion;
    }

    @NonNull
    @Override
    public AdapterReportesAdopcion.ViewHolderReportesAdopcion onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adopcion_list, null, false);
        view.setOnClickListener(this);
        return new ViewHolderReportesAdopcion(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterReportesAdopcion.ViewHolderReportesAdopcion holder, int position) {
        holder.etitipo.setText(listReportesAdopcion.get(position).getTipo());
        holder.etiedad.setText(listReportesAdopcion.get(position).getEdad());
        holder.eticantidad.setText(listReportesAdopcion.get(position).getCantidad());
        holder.etidescripcion.setText(listReportesAdopcion.get(position).getDescripcion());
        holder.foto.setImageResource(listReportesAdopcion.get(position).getFoto());

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

        TextView etitipo, etiedad, eticantidad, etidescripcion;
        ImageView foto;

        public ViewHolderReportesAdopcion(@NonNull View itemView) {
            super(itemView);
            etitipo = (TextView) itemView.findViewById(R.id.idTipoMA);
            etiedad = (TextView) itemView.findViewById(R.id.idEdadMA);
            eticantidad = (TextView) itemView.findViewById(R.id.idCantidadMA);
            etidescripcion = (TextView) itemView.findViewById(R.id.idDescripcionMA);
            foto = (ImageView) itemView.findViewById(R.id.idImagenMA);
        }

    }
}
