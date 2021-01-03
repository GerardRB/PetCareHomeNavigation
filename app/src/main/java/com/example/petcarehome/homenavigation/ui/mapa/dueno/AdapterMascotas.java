package com.example.petcarehome.homenavigation.ui.mapa.dueno;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.Objetos.Mascota;

import java.util.ArrayList;


public class AdapterMascotas extends RecyclerView.Adapter<AdapterMascotas.ViewHolderMascotas>{

    ArrayList<Mascota> listMascotas;
    Context context;

    public AdapterMascotas(ArrayList<Mascota> listMascotas, Context context) {
        this.listMascotas = listMascotas;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderMascotas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mascotas_cuidador_info, null, false);
        ViewHolderMascotas holder = new ViewHolderMascotas(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMascotas.ViewHolderMascotas holder, int position) {
        holder.tipoMascota.setText(listMascotas.get(position).getTipo());
        holder.recyclerServicios.setLayoutManager(new LinearLayoutManager(context));
        AdapterServicios adapterServicios = new AdapterServicios(listMascotas.get(position).getServicios(), context);
        holder.recyclerServicios.setAdapter(adapterServicios);

    }

    @Override
    public int getItemCount() {
        return listMascotas.size();
    }

    public class ViewHolderMascotas extends RecyclerView.ViewHolder {
        TextView tipoMascota;
        RecyclerView recyclerServicios;

        public ViewHolderMascotas(@NonNull View itemView) {
            super(itemView);
            tipoMascota = (TextView) itemView.findViewById(R.id.text_tipo_mascota_cuidador);
            recyclerServicios = (RecyclerView) itemView.findViewById(R.id.recyclerServiciosInfo);
        }
    }
}
