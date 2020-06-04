package com.example.myapplication.ui.difusion;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;

import java.util.ArrayList;

import static com.example.myapplication.R.drawable.ic_perro;

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
        listReportesEncontradas = new ArrayList<>();
        recycler = root.findViewById(R.id.recyclerEncontradasId);
        recycler.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));

        llenarReportes();



        AdapterReportesEncontradas adapter = new AdapterReportesEncontradas(listReportesEncontradas);
        recycler.setAdapter(adapter);
        //final TextView textView = root.findViewById(R.id.section_label);
        /*difusionTabViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
    }

    private void llenarReportes() {
        for(int i = 0; i<=15; i++){
            listReportesEncontradas.add(new ReporteEncontradas("Zona", "Fecha", "Nombre (opcional)", "Descripcion", R.drawable.ic_gato));
        }
    }

    public interface OnFragmentInteractionListener {
    }
}
