package com.example.myapplication.ui.difusion.encontradas;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.ui.difusion.perdidas.DetalleReportePerdidasActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EncontradasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EncontradasFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SECTION_NUMBER = "section_number";

    //private DifusionTabViewModel difusionTabViewModel;
    ArrayList<ReporteEncontradas> listReportesEncontradas;
    RecyclerView recycler;

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



        AdapterReportesEncontradas adapter = new AdapterReportesEncontradas(listReportesEncontradas);
        recycler.setAdapter(adapter);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "Selección: Reporte #" + listReportesEncontradas.get(recycler.getChildAdapterPosition(v)).getId(), Toast.LENGTH_SHORT).show();
                Intent intentDetalleRME = new Intent(getContext(), DetalleReporteEncontradaActivity.class);
                startActivity(intentDetalleRME);
            }
        });
    }

    private void llenarReportes() {
        for(int i = 0; i<=15; i++){
            listReportesEncontradas.add(new ReporteEncontradas("Zona", "Fecha", "Nombre (opcional)", "Descripcion", R.drawable.ic_gato, i));
        }
    }

    public interface OnFragmentInteractionListener {
    }
}
