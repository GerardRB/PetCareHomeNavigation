package com.example.petcarehome.homenavigation.ui.config.clases_fragmentos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.Objetos.Mascota;
import com.example.petcarehome.homenavigation.Objetos.Servicio;

import java.util.List;

public class AdaptadorMascotasCuidador extends RecyclerView.Adapter<AdaptadorMascotasCuidador.MascotasViewHolder>{
List<Mascota> mascotasList;
Servicio serviciosList;

    public AdaptadorMascotasCuidador(List<Mascota> mascotasList, Servicio servicios) {
        this.mascotasList = mascotasList;
        this.serviciosList = servicios;
    }

    @NonNull
    @Override
    public MascotasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mascotas_recycler, parent, false);
        MascotasViewHolder holder = new MascotasViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MascotasViewHolder holder, int position) {
        Mascota mascota = mascotasList.get(position);
      // Servicio servicio = serviciosList.get(position);

        holder.tipo_masco.setText(mascota.getTipo());
           holder.tipo_servP.setText(serviciosList.getTipoServicio());

       // String stringdouble = Double.toString(servicio.getPrecio());
      //  holder.precio_servP.setText(stringdouble);

    }

    @Override
    public int getItemCount() {
        return mascotasList.size();
    }

    public static class MascotasViewHolder extends RecyclerView.ViewHolder{

        TextView tipo_masco, tipo_servP, precio_servP, tipo_servH, precio_servH;
        public MascotasViewHolder(@NonNull View itemView) {
            super(itemView);
            tipo_masco = itemView.findViewById(R.id.Tipo_mascota_bd);

            tipo_servP =itemView.findViewById(R.id.Tipo_servicioP_bd);
            precio_servP = itemView.findViewById(R.id.Precio_servicioP_bd);

         //   tipo_servH = itemView.findViewById(R.id.Tipo_servicioH_bd);
          //  precio_servH = itemView.findViewById(R.id.Precio_servicioH_bd);

        }
    }
}
