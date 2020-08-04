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

import com.example.myapplication.R;
import com.example.myapplication.ui.difusion.adopcion.GenerarReporteAdopcionActivity;

import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class PerdidasFragment extends Fragment {


    private static final String ARG_SECTION_NUMBER = "section_number";

    ArrayList<ReportePerdidas> listReportes;
    RecyclerView recyclerPerdidas;
    ReportePerdidas reporteP;
    AdapterReportesPerdidas adapter;
    SwipeRefreshLayout swipeRefreshLayout;


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




        llenarReportes();

        adapter = new AdapterReportesPerdidas(listReportes);
        recyclerPerdidas.setAdapter(adapter);
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
                /*//Recibir objeto reporte perdida generado desde su actividad
                Bundle objetoEnviado = getActivity().getIntent().getExtras();
                reporteP = null;

                if (objetoEnviado != null){
                    reporteP = (ReportePerdidas) objetoEnviado.getSerializable("reportePerdida");
                }

                if (reporteP != null){
                    listReportes.add(reporteP);
                    adapter.notifyDataSetChanged();
                }*/
                listReportes.add(new ReportePerdidas("Sirius", "Perro", "1 año", "03/AGO/2020", "2:43 pm", "Gustavo A. Madero", "Ticoman", "Escuadron", "Perrito Bello", R.drawable.ic_perro, 10));
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void llenarReportes() {
        for(int i = 0; i<=5; i++){
            listReportes.add(new ReportePerdidas("Nombre", "Tipo", "Edad", "Fecha", "Hora", "Alcaldía", "Colonia", "Calle", "Descripción", R.drawable.ic_perro, i));
        }
    }


    public interface OnFragmentInteractionListener {
    }
}