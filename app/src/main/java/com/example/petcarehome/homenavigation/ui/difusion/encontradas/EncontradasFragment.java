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

    private ArrayList<ReporteEncontradas> listReportesEncontradas;
    private RecyclerView recycler;
    private AdapterReportesEncontradas adapter;
    private ReporteEncontradas reporteE;
    private SwipeRefreshLayout swipeRefreshLayout;
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
        listReportesEncontradas = new ArrayList<>();
        recycler = view.findViewById(R.id.recyclerEncontradasId);
        recycler.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));

        adapter = new AdapterReportesEncontradas(listReportesEncontradas);
        recycler.setAdapter(adapter);

        //Instanciar la base de datos y referenciarla
        firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference reporteEncontradaReference = firebaseDatabase.getReference().child(FirebaseReferences.REPORTES_REFERENCE).child(FirebaseReferences.REPORTEENCONTRADA_REFERENCE);

        //Llenar list desde la base
        reporteEncontradaReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listReportesEncontradas.removeAll(listReportesEncontradas);
                for (DataSnapshot snapshot:
                        dataSnapshot.getChildren()) {
                    reporteE = snapshot.getValue(ReporteEncontradas.class);
                    listReportesEncontradas.add(0,reporteE);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //llenarReportes();



        //Abrir detalle del reporte
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "Selección: Reporte #" + listReportesEncontradas.get(recycler.getChildAdapterPosition(v)).getId(), Toast.LENGTH_SHORT).show();
                Intent intentDetalleRME = new Intent(getContext(), DetalleReporteEncontradaActivity.class);
                Bundle  bundle = new Bundle();
                bundle.putSerializable("reporteEncontrada", listReportesEncontradas.get(recycler.getChildAdapterPosition(v)));
                intentDetalleRME.putExtras(bundle);
                startActivity(intentDetalleRME);
            }
        });

        //SwipeRefresh
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshEncontradas);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                reporteEncontradaReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        listReportesEncontradas.removeAll(listReportesEncontradas);
                        for (DataSnapshot snapshot:
                                dataSnapshot.getChildren()) {
                            reporteE = snapshot.getValue(ReporteEncontradas.class);
                            listReportesEncontradas.add(0,reporteE);
                        }
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }
    /*
    private void llenarReportes() {
        for(int i = 0; i<=5; i++){
            listReportesEncontradas.add(0, new ReporteEncontradas("Tipo", "Fecha", "Hora", "Alcaldía", "Colonia", "Calle", "Descripcion", R.drawable.ic_gato, i));
        }
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 0:
                if (resultCode == Activity.RESULT_OK){
                    Bundle bundle = data.getExtras();

                    Filtro filtro = (Filtro) bundle.getSerializable("filtro");
                    String cad = "Encontradas Filtro: \n- Zona: " + filtro.getZona() + "\n- Tipo: " + filtro.getTipoM() + "\n- Del: " + filtro.getFecha1() + " Al: " + filtro.getFecha2();
                    Toast.makeText(getContext(), cad, Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    public interface OnFragmentInteractionListener {
    }
}
