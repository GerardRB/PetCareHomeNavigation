package com.example.myapplication.ui.difusion.perdidas;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myapplication.Objetos.FirebaseReferences;
import com.example.myapplication.R;
import com.example.myapplication.ui.difusion.adopcion.GenerarReporteAdopcionActivity;
import com.google.firebase.database.ChildEventListener;
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

    private ArrayList<ReportePerdidas> listReportes;
    private RecyclerView recyclerPerdidas;
    private ReportePerdidas reporteP;
    private AdapterReportesPerdidas adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FirebaseDatabase firebaseDatabase;


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

        adapter = new AdapterReportesPerdidas(listReportes);
        recyclerPerdidas.setAdapter(adapter);

        //Instanciar la base de datos y referenciarla
        firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference reportePerdidaReference = firebaseDatabase.getReference().child(FirebaseReferences.PETCARE_REFERENCE).child(FirebaseReferences.REPORTEPERDIDA_REFERENCE);

        //Llenar lista desde la base
        reportePerdidaReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listReportes.removeAll(listReportes);
                for (DataSnapshot snapshot:
                        dataSnapshot.getChildren()) {
                    reporteP = snapshot.getValue(ReportePerdidas.class);
                    listReportes.add(0,reporteP);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //llenarReportes();


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

                reportePerdidaReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        listReportes.removeAll(listReportes);
                        for (DataSnapshot snapshot:
                                dataSnapshot.getChildren()) {
                            reporteP = snapshot.getValue(ReportePerdidas.class);
                            listReportes.add(0,reporteP);
                        }
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                /*reportePerdidaReference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        listReportes.removeAll(listReportes);
                        for (DataSnapshot snapshot:
                                dataSnapshot.getChildren()) {
                            ReportePerdidas reporte = dataSnapshot.getValue(ReportePerdidas.class);
                            listReportes.add(reporte);
                        }
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });*/

                //listReportes.add(0, new ReportePerdidas("Sirius", "Perro", "1 año", "03/AGO/2020", "2:43 pm", "Gustavo A. Madero", "Ticoman", "Escuadron", "Perrito Bello", R.drawable.ic_perro, 10));
                //adapter.notifyDataSetChanged();
                //swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    /*
    private void llenarReportes() {
        for(int i = 0; i<=5; i++){
            listReportes.add(0, new ReportePerdidas("Nombre", "Tipo", "Edad", "Fecha", "Hora", "Alcaldía", "Colonia", "Calle", "Descripción", R.drawable.ic_perro, i));
        }
    }*/


    public interface OnFragmentInteractionListener {
    }
}