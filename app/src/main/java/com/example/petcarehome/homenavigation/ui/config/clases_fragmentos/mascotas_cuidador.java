package com.example.petcarehome.homenavigation.ui.config.clases_fragmentos;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.Objetos.FirebaseReferences;
import com.example.petcarehome.homenavigation.Objetos.Mascota;
import com.example.petcarehome.homenavigation.Objetos.Servicio;
import com.example.petcarehome.homenavigation.ui.difusion.perdidas.GenerarReporteExtravioActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class mascotas_cuidador extends Fragment {
    FloatingActionButton agregarMasco;
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mascotas_cuidador_fragment, container,false);
        //Clase para el fragmento de mascotas para el cuidador
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final List <Mascota> mascotas = new ArrayList();
        //  final List <Servicio> servicios = new ArrayList();
        Servicio servicio = new Servicio();
        recyclerView = view.findViewById(R.id.recyclerMascotas);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new AdaptadorMascotasCuidador(mascotas, servicio);
        recyclerView.setAdapter(mAdapter);

        //Referencias a la BD.
        firebaseDatabase = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference cuidadorRef = firebaseDatabase.getReference().child(FirebaseReferences.USERS_REFERENCE)
                .child(FirebaseReferences.CUIDADOR_REFERENCE).child(user.getUid()).child(FirebaseReferences.MASCOTAS_CUIDADOR_REFERENCE);


        cuidadorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mascotas.removeAll(mascotas);
             //   servicios.removeAll(servicios);
                for(DataSnapshot snapshot:
                dataSnapshot.getChildren()){
              Mascota m = snapshot.getValue(Mascota.class);
           //   Servicio s = snapshot.child("servicios").getValue(Servicio.class);
              mascotas.add(m);
             // mascotas.add(s);
             // servicios.add(s);
                }
                    mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        agregarMasco = view.findViewById(R.id.agregar_mascota);
        agregarMasco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFormularioMascotas = new Intent(getContext(), FormularioMascotas.class);
                startActivity(intentFormularioMascotas);
            }
        });

    }
}
