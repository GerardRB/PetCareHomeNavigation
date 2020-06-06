package com.example.myapplication.ui.difusion;

import android.content.Intent;
import android.os.Bundle;
import android.os.Trace;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


import com.example.myapplication.HomeActivity;
import com.example.myapplication.R;
import com.example.myapplication.ui.difusion.adopcion.AdopcionFragment;
import com.example.myapplication.ui.difusion.encontradas.EncontradasFragment;
import com.example.myapplication.ui.difusion.perdidas.PerdidasFragment;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

public class DifusionFragment extends Fragment implements PerdidasFragment.OnFragmentInteractionListener,
        EncontradasFragment.OnFragmentInteractionListener , AdopcionFragment.OnFragmentInteractionListener{


    TabLayout tabs;
    View root;
    private ViewPager viewPager;
    private SectionsPagerAdapter sectionsPagerAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_difusion, container, false);
        //Implementacion del metodo para adaptar pestañas de reportes
        sectionsPagerAdapter = new SectionsPagerAdapter( this.getContext(), getFragmentManager());
        viewPager = root.findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs = root.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        //Botonos Flotantes para generar reportes
        //Referencias a los fabs del xml
        final ExtendedFloatingActionButton fabadd = root.findViewById(R.id.fabadd);
        final ExtendedFloatingActionButton fabperdidas = root.findViewById(R.id.id_add_perdida);
        final ExtendedFloatingActionButton fabencontradas = root.findViewById(R.id.id_add_encontrada);
        final ExtendedFloatingActionButton fabadopcion = root.findViewById(R.id.id_add_adopcion);
        //Ocultar los fabs y efecto de extender el fab add
        fabadd.shrink();
        fabperdidas.hide();
        fabencontradas.hide();
        fabadopcion.hide();
        //Mostrar los fabs al hacer clic en fabb add
        final boolean[] isOpen = {true};
        fabadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOpen[0]){
                    fabadd.extend();
                    fabperdidas.show();
                    fabencontradas.show();
                    fabadopcion.show();
                    isOpen[0] = false;
                } else{
                    fabadd.shrink();
                    fabperdidas.hide();
                    fabencontradas.hide();
                    fabadopcion.hide();
                    isOpen[0] = true; }
            }
        });

        //Abrir generar reporte mascota perdida
        fabperdidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabperdidas.hide();
                fabencontradas.hide();
                fabadopcion.hide();
                fabadd.shrink();
                isOpen[0] = true;
                Intent intentReportePerdidas = new Intent(getContext(), GenerarReporteExtravioActivity. class);
                startActivity(intentReportePerdidas);
            }
        });

        //Abrir generar reporte mascota encontrada
        fabencontradas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabperdidas.hide();
                fabencontradas.hide();
                fabadopcion.hide();
                fabadd.shrink();
                isOpen[0] = true;
                /*Intent intentReportePerdidas = new Intent(getActivity(), GenerarReporteExtravioActivity. class);
                startActivity(intentReportePerdidas);*/
            }
        });

        //Abrir generar reporte mascota adopcion
        fabadopcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabperdidas.hide();
                fabencontradas.hide();
                fabadopcion.hide();
                fabadd.shrink();
                isOpen[0] = true;
                /*Intent intentReportePerdidas = new Intent(getActivity(), GenerarReporteExtravioActivity. class);
                startActivity(intentReportePerdidas);*/
            }
        });

        return root;

    }

}
