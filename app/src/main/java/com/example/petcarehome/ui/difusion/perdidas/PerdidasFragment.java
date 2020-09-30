package com.example.petcarehome.ui.difusion.perdidas;

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

import com.example.petcarehome.Objetos.Filtro;
import com.example.petcarehome.Objetos.FirebaseReferences;
import com.example.petcarehome.Objetos.ReportePerdidas;
import com.example.petcarehome.Objetos.ReportePerdidasID;
import com.example.petcarehome.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class PerdidasFragment extends Fragment {


    private static final String ARG_SECTION_NUMBER = "section_number";

    private ArrayList<ReportePerdidasID> listReportes;
    private RecyclerView recyclerPerdidas;
    private ReportePerdidas reporteP;
    private ReportePerdidasID reportePID;
    private AdapterReportesPerdidas adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FirebaseDatabase firebaseDatabase;
    private Filtro filtro;


    public static PerdidasFragment newInstance(int index) {
        PerdidasFragment fragment = new PerdidasFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }


    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_perdidas, container, false);
        return root;

    }


    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        //Construir Recycler
        listReportes = new ArrayList<>();
        recyclerPerdidas = view.findViewById(R.id.recyclerPerdidasId);
        recyclerPerdidas.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new AdapterReportesPerdidas(listReportes, getContext());
        recyclerPerdidas.setAdapter(adapter);

        filtro = null;

        //Instanciar la base de datos y referenciarla
        firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference reportePerdidaReference = firebaseDatabase.getReference().child(FirebaseReferences.REPORTES_REFERENCE).child(FirebaseReferences.REPORTEPERDIDA_REFERENCE);

        llenarReportes(filtro, false);


        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "Selección: Reporte #" + listReportes.get(recyclerPerdidas.getChildAdapterPosition(v)).getId(), Toast.LENGTH_SHORT).show();
                Intent intentDetalleRMP = new Intent(getContext(), DetalleReportePerdidasActivity.class);
                Bundle  bundle = new Bundle();
                bundle.putSerializable("reportePerdida", listReportes.get(recyclerPerdidas.getChildAdapterPosition(v)));
                intentDetalleRMP.putExtras(bundle);
                startActivity(intentDetalleRMP);
            }
        });

        //SwipeRefresh
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshPerdidas);
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


    private void llenarReportes(final Filtro filtro, Boolean filtrada) {
        //Instanciar la base de datos y referenciarla
        firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference reportePerdidaReference = firebaseDatabase.getReference().child(FirebaseReferences.REPORTES_REFERENCE).child(FirebaseReferences.REPORTEPERDIDA_REFERENCE);

        if (filtrada == true){
            //Llenar lista desde la base con filtro
            reportePerdidaReference.addValueEventListener(new ValueEventListener() {
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
                                    reportePID = new ReportePerdidasID(snapshot.getKey(), snapshot.getValue(ReportePerdidas.class));
                                    listReportes.add(0,reportePID);
                                }
                            } else if (alcaldia.equals(filtro.getZona())){
                                //reporteP = snapshot.getValue(ReportePerdidas.class);
                                reportePID = new ReportePerdidasID(snapshot.getKey(), snapshot.getValue(ReportePerdidas.class));
                                listReportes.add(0,reportePID);
                            }
                        } else if (filtro.getTipoM() != null){
                            if (tipo.equals(filtro.getTipoM())){
                                //reporteP = snapshot.getValue(ReportePerdidas.class);
                                reportePID = new ReportePerdidasID(snapshot.getKey(), snapshot.getValue(ReportePerdidas.class));
                                listReportes.add(0,reportePID);
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
            reportePerdidaReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    listReportes.removeAll(listReportes);
                    for (DataSnapshot snapshot:
                            dataSnapshot.getChildren()) {
                        //reporteP = snapshot.getValue(ReportePerdidas.class);
                        reportePID = new ReportePerdidasID(snapshot.getKey(), snapshot.getValue(ReportePerdidas.class));
                        listReportes.add(0,reportePID);
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