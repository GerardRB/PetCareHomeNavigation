package com.example.petcarehome.homenavigation.ui.difusion.encontradas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.petcarehome.homenavigation.Objetos.Filtro;
import com.example.petcarehome.homenavigation.Objetos.FirebaseReferences;
import com.example.petcarehome.homenavigation.Objetos.ReporteEncontradas;
import com.example.petcarehome.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class EncontradasFragment extends Fragment {

    private ArrayList<ReporteEncontradas> listReportes;
    private ReporteEncontradas reporteE;
    private AdapterReportesEncontradas adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Filtro filtro;


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_encontradas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Construir Recycler
        listReportes = new ArrayList<>();
        RecyclerView recycler = view.findViewById(R.id.recyclerEncontradasId);
        recycler.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));

        adapter = new AdapterReportesEncontradas(listReportes, getContext());
        recycler.setAdapter(adapter);

        filtro = null;

        llenarReportes(null, false);


        //SwipeRefresh
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshEncontradas);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                llenarReportes(filtro, filtro != null);
                swipeRefreshLayout.setRefreshing(false);

            }
        });
    }

    private void llenarReportes(final Filtro filtro, boolean filtrada) {
        //Instanciar la base de datos y referenciarla
        final DatabaseReference reportePerdidaReference = FirebaseDatabase.getInstance().getReference().child(FirebaseReferences.REPORTES_REFERENCE).child(FirebaseReferences.REPORTEENCONTRADA_REFERENCE);
        reportePerdidaReference.keepSynced(true);

        if (filtrada){
            //Llenar lista desde la base con filtro
            reportePerdidaReference.limitToLast(50).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    listReportes.clear();
                    for (DataSnapshot snapshot:
                            dataSnapshot.getChildren()) {
                        String alcaldia = snapshot.child("alcaldia").getValue(String.class);
                        String tipo = snapshot.child("tipo").getValue(String.class);
                        if(filtro.getZona() != null){
                            if (filtro.getTipoM() != null){
                                if (alcaldia.equals(filtro.getZona()) && tipo.equals(filtro.getTipoM())){
                                    //reporteP = snapshot.getValue(ReportePerdidas.class);
                                    reporteE = snapshot.getValue(ReporteEncontradas.class);
                                    listReportes.add(0,reporteE);
                                }
                            } else if (alcaldia.equals(filtro.getZona())){
                                //reporteP = snapshot.getValue(ReportePerdidas.class);
                                reporteE = snapshot.getValue(ReporteEncontradas.class);
                                listReportes.add(0,reporteE);
                            }
                        } else if (filtro.getTipoM() != null){
                            if (tipo.equals(filtro.getTipoM())){
                                //reporteP = snapshot.getValue(ReportePerdidas.class);
                                reporteE = snapshot.getValue(ReporteEncontradas.class);
                                listReportes.add(0,reporteE);
                            }
                        }
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
            //Llenar lista desde la base sin filtro
            reportePerdidaReference.limitToLast(50).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    listReportes.clear();
                    for (DataSnapshot snapshot:
                            dataSnapshot.getChildren()) {
                        reporteE = snapshot.getValue(ReporteEncontradas.class);
                        listReportes.add(0,reporteE);
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = data.getExtras();
                filtro = (Filtro) bundle.getSerializable("filtro");
                llenarReportes(filtro, true);
            }
        }
    }

    public interface OnFragmentInteractionListener {
    }
}
