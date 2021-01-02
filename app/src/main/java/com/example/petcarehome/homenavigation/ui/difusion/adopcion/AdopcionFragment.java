package com.example.petcarehome.homenavigation.ui.difusion.adopcion;

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
import com.example.petcarehome.homenavigation.Objetos.ReporteAdopcion;
import com.example.petcarehome.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdopcionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdopcionFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private ArrayList<ReporteAdopcion> listReportes;
    private RecyclerView recycler;
    private AdapterReportesAdopcion adapter;
    private ReporteAdopcion reporteA;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FirebaseDatabase firebaseDatabase;
    private Filtro filtro;

    public static AdopcionFragment newInstance(int index) {
        AdopcionFragment fragment = new AdopcionFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int index = 3;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_adopcion, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Construir el recycler
        listReportes = new ArrayList<>();
        recycler = view.findViewById(R.id.recyclerAdopcionId);
        recycler.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));

        adapter = new AdapterReportesAdopcion(listReportes, getContext());
        recycler.setAdapter(adapter);

        filtro = null;

        llenarReportes(filtro, false);

        //SwipeRefresh
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshAdopcion);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (filtro != null){
                    llenarReportes(filtro, true);
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    llenarReportes(filtro, false);
                    swipeRefreshLayout.setRefreshing(false);
                }

            }
        });
    }

    private void llenarReportes(final Filtro filtro, boolean filtrada) {
        //Instanciar la base de datos y referenciarla
        final DatabaseReference reportePerdidaReference = FirebaseDatabase.getInstance().getReference().child(FirebaseReferences.REPORTES_REFERENCE).child(FirebaseReferences.REPORTEADOPCION_REFERENCE);
        reportePerdidaReference.keepSynced(true);

        if (filtrada == true){
            //Llenar lista desde la base con filtro
            reportePerdidaReference.limitToLast(5).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    listReportes.removeAll(listReportes);
                    for (DataSnapshot snapshot:
                            dataSnapshot.getChildren()) {
                        String alcaldia = snapshot.child("alcaldia").getValue(String.class);
                        String tipo = snapshot.child("tipo").getValue(String.class);
                        if(filtro.getZona() != null){
                            if (filtro.getTipoM() != null){
                                if (alcaldia.equals(filtro.getZona()) && tipo.equals(filtro.getTipoM())){
                                    //reporteP = snapshot.getValue(ReportePerdidas.class);
                                    reporteA = snapshot.getValue(ReporteAdopcion.class);
                                    listReportes.add(0,reporteA);
                                }
                            } else if (alcaldia.equals(filtro.getZona())){
                                //reporteP = snapshot.getValue(ReportePerdidas.class);
                                reporteA = snapshot.getValue(ReporteAdopcion.class);
                                listReportes.add(0,reporteA);
                            }
                        } else if (filtro.getTipoM() != null){
                            if (tipo.equals(filtro.getTipoM())){
                                //reporteP = snapshot.getValue(ReportePerdidas.class);
                                reporteA = snapshot.getValue(ReporteAdopcion.class);
                                listReportes.add(0,reporteA);
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
                    listReportes.removeAll(listReportes);
                    for (DataSnapshot snapshot:
                            dataSnapshot.getChildren()) {
                        reporteA = snapshot.getValue(ReporteAdopcion.class);
                        listReportes.add(0,reporteA);
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
        switch (requestCode){
            case 0:
                if (resultCode == Activity.RESULT_OK){
                    Bundle bundle = data.getExtras();
                    filtro = (Filtro) bundle.getSerializable("filtro");
                    llenarReportes(filtro, true);
                }
                break;
        }
    }

    public interface OnFragmentInteractionListener {
    }
}
