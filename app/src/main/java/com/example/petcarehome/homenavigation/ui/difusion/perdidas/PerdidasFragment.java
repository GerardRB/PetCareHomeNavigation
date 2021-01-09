package com.example.petcarehome.homenavigation.ui.difusion.perdidas;

import android.app.Activity;
import android.content.Intent;
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

import com.example.petcarehome.homenavigation.Objetos.Filtro;
import com.example.petcarehome.homenavigation.Objetos.FirebaseReferences;
import com.example.petcarehome.homenavigation.Objetos.ReportePerdidas;
import com.example.petcarehome.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class PerdidasFragment extends Fragment {


    private ArrayList<ReportePerdidas> listReportes;
    private ReportePerdidas reporteP;
    private AdapterReportesPerdidas adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Filtro filtro;


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_perdidas, container, false);

    }


    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        //Construir Recycler
        listReportes = new ArrayList<>();
        RecyclerView recyclerPerdidas = view.findViewById(R.id.recyclerPerdidasId);
        recyclerPerdidas.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new AdapterReportesPerdidas(listReportes, getContext(), true);
        recyclerPerdidas.setAdapter(adapter);

        filtro = null;


        //final DatabaseReference reportePerdidaReference = FirebaseDatabase.getInstance().getReference().child(FirebaseReferences.REPORTES_REFERENCE).child(FirebaseReferences.REPORTEPERDIDA_REFERENCE);

        llenarReportes(null, false);


        //SwipeRefresh
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshPerdidas);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                llenarReportes(filtro, filtro != null);
                swipeRefreshLayout.setRefreshing(false);

            }
        });

    }


    private void llenarReportes(final Filtro filtro, Boolean filtrada) {
        //Instanciar la base de datos y referenciarla
        final DatabaseReference reportePerdidaReference = FirebaseDatabase.getInstance().getReference().child(FirebaseReferences.REPORTES_REFERENCE).child(FirebaseReferences.REPORTEPERDIDA_REFERENCE);
        reportePerdidaReference.keepSynced(true);

        if (filtrada){
            //Llenar lista desde la base con filtro
            reportePerdidaReference.limitToLast(5).addValueEventListener(new ValueEventListener() {
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
                                    reporteP = snapshot.getValue(ReportePerdidas.class);
                                    listReportes.add(0,reporteP);
                                }
                            } else if (alcaldia.equals(filtro.getZona())){
                                //reporteP = snapshot.getValue(ReportePerdidas.class);
                                reporteP = snapshot.getValue(ReportePerdidas.class);
                                listReportes.add(0,reporteP);
                            }
                        } else if (filtro.getTipoM() != null){
                            if (tipo.equals(filtro.getTipoM())){
                                //reporteP = snapshot.getValue(ReportePerdidas.class);
                                reporteP = snapshot.getValue(ReportePerdidas.class);
                                listReportes.add(0,reporteP);
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
            reportePerdidaReference.limitToLast(5).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    listReportes.clear();
                    for (DataSnapshot snapshot:
                            dataSnapshot.getChildren()) {
                        //reporteP = snapshot.getValue(ReportePerdidas.class);
                        reporteP = snapshot.getValue(ReportePerdidas.class);
                        listReportes.add(0,reporteP);
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