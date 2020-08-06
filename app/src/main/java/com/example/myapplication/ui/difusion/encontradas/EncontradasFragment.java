package com.example.myapplication.ui.difusion.encontradas;

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

import com.example.myapplication.R;
import com.example.myapplication.ui.difusion.perdidas.DetalleReportePerdidasActivity;
import com.example.myapplication.ui.difusion.perdidas.ReportePerdidas;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EncontradasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EncontradasFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    //private DifusionTabViewModel difusionTabViewModel;
    ArrayList<ReporteEncontradas> listReportesEncontradas;
    RecyclerView recycler;
    AdapterReportesEncontradas adapter;
    ReporteEncontradas reporteE;
    SwipeRefreshLayout swipeRefreshLayout;

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
        //difusionTabViewModel = ViewModelProviders.of(this).get(DifusionTabViewModel.class);
        int index = 2;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        //difusionTabViewModel.setIndex(index);

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

        listReportesEncontradas = new ArrayList<>();
        recycler = view.findViewById(R.id.recyclerEncontradasId);
        recycler.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));

        llenarReportes();



        adapter = new AdapterReportesEncontradas(listReportesEncontradas);
        recycler.setAdapter(adapter);
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
                /*//Recibir objeto reporte perdida generado desde su actividad
                Bundle objetoEnviado = getActivity().getIntent().getExtras();
                reporteE = null;

                if (objetoEnviado != null){
                    reporteE = (ReporteEncontradas) objetoEnviado.getSerializable("reporteEncontrada");
                }

                if (reporteE != null){
                    listReportesEncontradas.add(reporteE);
                    adapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                }*/
                listReportesEncontradas.add(0, new ReporteEncontradas("Perro", "03/AGO/2020", "2:43 pm", "Gustavo A. Madero", "Ticoman", "Escuadron", "Perrito Bello", R.drawable.ic_perro, 10));
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void llenarReportes() {
        for(int i = 0; i<=5; i++){
            listReportesEncontradas.add(0, new ReporteEncontradas("Tipo", "Fecha", "Hora", "Alcaldía", "Colonia", "Calle", "Descripcion", R.drawable.ic_gato, i));
        }
    }

    public interface OnFragmentInteractionListener {
    }
}
