package com.example.myapplication.ui.difusion.adopcion;

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
 * Use the {@link AdopcionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdopcionFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    //private DifusionTabViewModel difusionTabViewModel;
    ArrayList<ReporteAdopcion> listReportesAdopcion;
    RecyclerView recycler;
    AdapterReportesAdopcion adapter;
    ReporteAdopcion reporteA;
    SwipeRefreshLayout swipeRefreshLayout;

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
        //difusionTabViewModel = ViewModelProviders.of(this).get(DifusionTabViewModel.class);
        int index = 3;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        //difusionTabViewModel.setIndex(index);

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

        listReportesAdopcion = new ArrayList<>();
        recycler = view.findViewById(R.id.recyclerAdopcionId);
        recycler.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));

        llenarReportes();

        adapter = new AdapterReportesAdopcion(listReportesAdopcion);
        recycler.setAdapter(adapter);
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
                /*//Recibir objeto reporte perdida generado desde su actividad
                Bundle objetoEnviado = getActivity().getIntent().getExtras();
                reporteA = null;

                if (objetoEnviado != null){
                    reporteA = (ReporteAdopcion) objetoEnviado.getSerializable("reporteAdopcion");
                }

                if (reporteA != null){
                    listReportesAdopcion.add(reporteA);
                    adapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                }*/
                listReportesAdopcion.add(0, new ReporteAdopcion("Perro", "Mestizo", "1año", "Si", "No", "Gustavo A. Madero", "Ticoman", "Escuadron", "Perrito Bello", R.drawable.ic_perro, 10));
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void llenarReportes() {
        for(int i = 0; i<=5; i++){
            listReportesAdopcion.add(0, new ReporteAdopcion("Tipo", "Raza", "Edad", "Vacunas", "Esterilización", "Alcaldía", "Colonia", "Calle", "Descripción", R.drawable.ic_gato, i));
        }
    }

    public interface OnFragmentInteractionListener {
    }
}
