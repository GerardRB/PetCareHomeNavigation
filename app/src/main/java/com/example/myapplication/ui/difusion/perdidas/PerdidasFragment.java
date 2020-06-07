package com.example.myapplication.ui.difusion.perdidas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

import static com.example.myapplication.R.drawable.*;


/**
 * A placeholder fragment containing a simple view.
 */
public class PerdidasFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    //private DifusionTabViewModel difusionTabViewModel;
    ArrayList<ReportePerdidas> listReportes;
    RecyclerView recycler;

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
        View root = inflater.inflate(R.layout.fragment_perdidas, container, false);

        //Construir Recycler
        listReportes = new ArrayList<>();
        recycler = root.findViewById(R.id.recyclerPerdidasId);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        llenarReportes();

        AdapterReportesPerdidas adapter = new AdapterReportesPerdidas(listReportes);
        recycler.setAdapter(adapter);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Selección: Reporte #" + listReportes.get(recycler.getChildAdapterPosition(v)).getId(), Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    private void llenarReportes() {
        for(int i = 0; i<=15; i++){
            listReportes.add(new ReportePerdidas("Zona", "Fecha", "Nombre", "Descripcion", ic_perro, i));
        }
    }

    public interface OnFragmentInteractionListener {
    }
}