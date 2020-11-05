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
import android.widget.Toast;

import com.example.petcarehome.homenavigation.Objetos.Filtro;
import com.example.petcarehome.homenavigation.Objetos.FirebaseReferences;
import com.example.petcarehome.homenavigation.Objetos.ReporteEncontradas;
import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.Objetos.ReporteEncontradasID;
import com.example.petcarehome.homenavigation.Objetos.ReportePerdidas;
import com.example.petcarehome.homenavigation.Objetos.ReportePerdidasID;
import com.example.petcarehome.homenavigation.ui.difusion.perdidas.AdapterReportesPerdidas;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EncontradasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EncontradasFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private ArrayList<ReporteEncontradasID> listReportes;
    private RecyclerView recycler;
    private ReporteEncontradasID reporteEID;
    private AdapterReportesEncontradas adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Filtro filtro;
    private FirebaseDatabase firebaseDatabase;

    public static EncontradasFragment newInstance(int index) {
        EncontradasFragment fragment = new EncontradasFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int index = 2;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_encontradas, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Construir Recycler
        listReportes = new ArrayList<>();
        recycler = view.findViewById(R.id.recyclerEncontradasId);
        recycler.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));

        adapter = new AdapterReportesEncontradas(listReportes, getContext());
        recycler.setAdapter(adapter);

        filtro = null;

        llenarReportes(filtro, false);


        //Abrir detalle del reporte
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "Selección: Reporte #" + listReportesEncontradas.get(recycler.getChildAdapterPosition(v)).getId(), Toast.LENGTH_SHORT).show();
                Intent intentDetalleRME = new Intent(getContext(), DetalleReporteEncontradaActivity.class);
                Bundle  bundle = new Bundle();
                bundle.putSerializable("reporteEncontrada", listReportes.get(recycler.getChildAdapterPosition(v)));
                intentDetalleRME.putExtras(bundle);
                startActivity(intentDetalleRME);
            }
        });

        //SwipeRefresh
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshEncontradas);
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
        final DatabaseReference reportePerdidaReference = FirebaseDatabase.getInstance().getReference().child(FirebaseReferences.REPORTES_REFERENCE).child(FirebaseReferences.REPORTEENCONTRADA_REFERENCE);
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
                                    reporteEID = new ReporteEncontradasID(snapshot.getKey(), snapshot.getValue(ReporteEncontradas.class));
                                    listReportes.add(0,reporteEID);
                                }
                            } else if (alcaldia.equals(filtro.getZona())){
                                //reporteP = snapshot.getValue(ReportePerdidas.class);
                                reporteEID = new ReporteEncontradasID(snapshot.getKey(), snapshot.getValue(ReporteEncontradas.class));
                                listReportes.add(0,reporteEID);
                            }
                        } else if (filtro.getTipoM() != null){
                            if (tipo.equals(filtro.getTipoM())){
                                //reporteP = snapshot.getValue(ReportePerdidas.class);
                                reporteEID = new ReporteEncontradasID(snapshot.getKey(), snapshot.getValue(ReporteEncontradas.class));
                                listReportes.add(0,reporteEID);
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
                        reporteEID = new ReporteEncontradasID(snapshot.getKey(), snapshot.getValue(ReporteEncontradas.class));
                        listReportes.add(0,reporteEID);
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
