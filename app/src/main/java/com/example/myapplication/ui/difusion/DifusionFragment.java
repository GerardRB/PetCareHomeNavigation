package com.example.myapplication.ui.difusion;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


import com.example.myapplication.R;
import com.example.myapplication.ui.difusion.adopcion.AdopcionFragment;
import com.example.myapplication.ui.difusion.adopcion.GenerarReporteAdopcionActivity;
import com.example.myapplication.ui.difusion.encontradas.EncontradasFragment;
import com.example.myapplication.ui.difusion.encontradas.GenerarReporteEncontradaActivity;
import com.example.myapplication.ui.difusion.perdidas.GenerarReporteExtravioActivity;
import com.example.myapplication.ui.difusion.perdidas.PerdidasFragment;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class DifusionFragment extends Fragment implements PerdidasFragment.OnFragmentInteractionListener,
        EncontradasFragment.OnFragmentInteractionListener , AdopcionFragment.OnFragmentInteractionListener, View.OnClickListener{


    private TabLayout tabs;
    private View root;
    private ViewPager viewPager;
    private SectionsPagerAdapter sectionsPagerAdapter;
    final boolean[] isOpen = {true};

    private ExtendedFloatingActionButton fabadd, fabperdidas, fabencontradas, fabadopcion;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_difusion, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Referencia a los componentes del adaptador de pestañas
        viewPager = view.findViewById(R.id.view_pager);
        tabs = view.findViewById(R.id.tabs);

        //Implementacion del metodo para adaptar pestañas de reportes
        sectionsPagerAdapter = new SectionsPagerAdapter( this.getContext(), getFragmentManager());
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs.setupWithViewPager(viewPager);

        //Referencias a los fabs del xml y habilitar el escuchador de cada fab
        fabadd = view.findViewById(R.id.fabadd);
        fabadd.setOnClickListener(this);
        fabperdidas = view.findViewById(R.id.id_add_perdida);
        fabperdidas.setOnClickListener(this);
        fabencontradas = view.findViewById(R.id.id_add_encontrada);
        fabencontradas.setOnClickListener(this);
        fabadopcion = view.findViewById(R.id.id_add_adopcion);
        fabadopcion.setOnClickListener(this);

        //Ocultar los fabs y efecto de extender el fab add
        fabadd.shrink();
        fabperdidas.hide();
        fabencontradas.hide();
        fabadopcion.hide();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fabadd: // Mostrar opciones y extender fabadd
                if (isOpen[0]){
                    fabadd.extend();
                    fabperdidas.show();
                    fabencontradas.show();
                    fabadopcion.show();
                    isOpen[0] = false;
                    break;
                } else{
                    fabadd.shrink();
                    fabperdidas.hide();
                    fabencontradas.hide();
                    fabadopcion.hide();
                    isOpen[0] = true;
                    break;
                }
            case R.id.id_add_perdida: //Abrir generar reporte mascota perdida
                fabperdidas.hide();
                fabencontradas.hide();
                fabadopcion.hide();
                fabadd.shrink();
                isOpen[0] = true;
                Intent intentReportePerdidas = new Intent(getContext(), GenerarReporteExtravioActivity. class);
                startActivity(intentReportePerdidas);
                break;
            case R.id.id_add_encontrada: //Abrir generar reporte mascota encontrada
                fabperdidas.hide();
                fabencontradas.hide();
                fabadopcion.hide();
                fabadd.shrink();
                isOpen[0] = true;
                Intent intentReporteEncontradas = new Intent(getContext(), GenerarReporteEncontradaActivity. class);
                startActivity(intentReporteEncontradas);
                break;
            case R.id.id_add_adopcion: //Abrir generar reporte mascota adopcion
                fabperdidas.hide();
                fabencontradas.hide();
                fabadopcion.hide();
                fabadd.shrink();
                isOpen[0] = true;
                Intent intentReporteAdopcion = new Intent(getContext(), GenerarReporteAdopcionActivity. class);
                startActivity(intentReporteAdopcion);
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }
}
