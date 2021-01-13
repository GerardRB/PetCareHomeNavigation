package com.example.petcarehome.homenavigation.ui.config.clases_fragmentos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.Objetos.FirebaseReferences;
import com.example.petcarehome.homenavigation.Objetos.ReporteAdopcion;
import com.example.petcarehome.homenavigation.Objetos.ReporteEncontradas;
import com.example.petcarehome.homenavigation.Objetos.ReportePerdidas;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MisReportesFragment extends Fragment {

    private ArrayList<ReportePerdidas> listPerdidas;
    private ArrayList<ReporteEncontradas> listEncontradas;
    private ArrayList<ReporteAdopcion> listAdopcion;


    private AdapterMisReportes adapterMisReportes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mis_reportes_fragment, container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final SwipeRefreshLayout swipeRefresh;
        RecyclerView recyclerMisReportes;

        swipeRefresh = view.findViewById(R.id.swipeRefreshMisReportes);

        recyclerMisReportes = view.findViewById(R.id.recyclerMisReportes);
        recyclerMisReportes.setLayoutManager(new LinearLayoutManager(getContext()));

        listPerdidas = new ArrayList<>();
        listEncontradas = new ArrayList<>();
        listAdopcion = new ArrayList<>();

        adapterMisReportes = new AdapterMisReportes(listPerdidas, listEncontradas, listAdopcion, getContext());
        recyclerMisReportes.setAdapter(adapterMisReportes);


        //adapterPerdidas= new AdapterReportesPerdidas(listPerdidas, getContext(), false);
        //recyclerPerdidas.setAdapter(adapterPerdidas);

        //adapterEncontradas = new AdapterReportesEncontradas(listEncontradas, getContext(), false);
        //recyclerEncontradas.setAdapter(adapterEncontradas);

        //adapterAdopcion = new AdapterReportesAdopcion(listAdopcion, getContext(), false);
        //recyclerAdopcion.setAdapter(adapterAdopcion);

        llenarReportes();

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                llenarReportes();
                swipeRefresh.setRefreshing(false);
            }
        });

    }

    private void llenarReportes() {

        final String idUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference reportesRef = FirebaseDatabase.getInstance().getReference().child(FirebaseReferences.REPORTES_REFERENCE);

        reportesRef.child(FirebaseReferences.REPORTEPERDIDA_REFERENCE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listPerdidas.clear();
                for (DataSnapshot snapreporte:
                        dataSnapshot.getChildren()) {
                    if (snapreporte.exists()){
                        String user = snapreporte.child("idUser").getValue(String.class);
                        if (user.equals(idUser)){
                            ReportePerdidas rp = snapreporte.getValue(ReportePerdidas.class);
                            listPerdidas.add(0, rp);
                        }
                    }
                }
                adapterMisReportes.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        reportesRef.child(FirebaseReferences.REPORTEENCONTRADA_REFERENCE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listEncontradas.clear();
                for (DataSnapshot snapreporte:
                        dataSnapshot.getChildren()) {
                    if (snapreporte.exists()){
                        String user = snapreporte.child("idUser").getValue(String.class);
                        if (user.equals(idUser)){
                            ReporteEncontradas re = snapreporte.getValue(ReporteEncontradas.class);
                            listEncontradas.add(0, re);
                        }
                    }
                }
                adapterMisReportes.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        reportesRef.child(FirebaseReferences.REPORTEADOPCION_REFERENCE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listAdopcion.clear();
                for (DataSnapshot snapreporte:
                        dataSnapshot.getChildren()) {
                    if (snapreporte.exists()){
                        String user = snapreporte.child("idUser").getValue(String.class);
                        if (user.equals(idUser)){
                            ReporteAdopcion ra = snapreporte.getValue(ReporteAdopcion.class);
                            listAdopcion.add(0, ra);
                        }
                    }
                }
                adapterMisReportes.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
