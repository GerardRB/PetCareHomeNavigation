package com.example.myapplication.ui.difusion.adopcion;

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

import com.example.myapplication.Objetos.Filtro;
import com.example.myapplication.Objetos.FirebaseReferences;
import com.example.myapplication.Objetos.ReporteAdopcion;
import com.example.myapplication.R;
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

    private ArrayList<ReporteAdopcion> listReportesAdopcion;
    private RecyclerView recycler;
    private AdapterReportesAdopcion adapter;
    private ReporteAdopcion reporteA;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FirebaseDatabase firebaseDatabase;

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
        listReportesAdopcion = new ArrayList<>();
        recycler = view.findViewById(R.id.recyclerAdopcionId);
        recycler.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));

        adapter = new AdapterReportesAdopcion(listReportesAdopcion);
        recycler.setAdapter(adapter);

        //Instanciar la base de datos y referenciarla
        firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference reporteAdopcionReference = firebaseDatabase.getReference().child(FirebaseReferences.PETCARE_REFERENCE).child(FirebaseReferences.REPORTEADOPCION_REFERENCE);

        //Llenar list desde la base
        reporteAdopcionReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listReportesAdopcion.removeAll(listReportesAdopcion);
                for (DataSnapshot snapshot:
                        dataSnapshot.getChildren()) {
                    reporteA = snapshot.getValue(ReporteAdopcion.class);
                    listReportesAdopcion.add(0,reporteA);
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
                //Toast.makeText(getContext(), "Selección: Reporte #" + listReportesAdopcion.get(recycler.getChildAdapterPosition(v)).getId(), Toast.LENGTH_SHORT).show();
                Intent intentDetalleRMA = new Intent(getContext(), DetalleReporteAdopcionActivity.class);
                Bundle  bundle = new Bundle();
                bundle.putSerializable("reporteAdopcion", listReportesAdopcion.get(recycler.getChildAdapterPosition(v)));
                intentDetalleRMA.putExtras(bundle);
                startActivity(intentDetalleRMA);
            }
        });

        //SwipeRefresh
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshAdopcion);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                reporteAdopcionReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        listReportesAdopcion.removeAll(listReportesAdopcion);
                        for (DataSnapshot snapshot:
                                dataSnapshot.getChildren()) {
                            reporteA = snapshot.getValue(ReporteAdopcion.class);
                            listReportesAdopcion.add(0,reporteA);
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
            listReportesAdopcion.add(0, new ReporteAdopcion("Tipo", "Raza", "Edad", "Vacunas", "Esterilización", "Alcaldía", "Colonia", "Calle", "Descripción", R.drawable.ic_gato, i));
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
                    String cad = "Adopción Filtro: \n- Zona: " + filtro.getZona() + "\n- Tipo: " + filtro.getTipoM() + "\n- Del: " + filtro.getFecha1() + " Al: " + filtro.getFecha2();
                    Toast.makeText(getContext(), cad, Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    public interface OnFragmentInteractionListener {
    }
}
