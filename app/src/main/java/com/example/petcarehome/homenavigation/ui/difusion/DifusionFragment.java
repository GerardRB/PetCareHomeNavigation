package com.example.petcarehome.homenavigation.ui.difusion;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.ui.difusion.adopcion.AdopcionFragment;
import com.example.petcarehome.homenavigation.ui.difusion.adopcion.GenerarReporteAdopcionActivity;
import com.example.petcarehome.homenavigation.ui.difusion.encontradas.EncontradasFragment;
import com.example.petcarehome.homenavigation.ui.difusion.encontradas.GenerarReporteEncontradaActivity;
import com.example.petcarehome.homenavigation.ui.difusion.perdidas.GenerarReporteExtravioActivity;
import com.example.petcarehome.homenavigation.ui.difusion.perdidas.PerdidasFragment;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class DifusionFragment extends Fragment implements PerdidasFragment.OnFragmentInteractionListener,
        EncontradasFragment.OnFragmentInteractionListener , AdopcionFragment.OnFragmentInteractionListener, View.OnClickListener{


    private TabLayout tabs;
    private View root;
    private ViewPager viewPager;
    private SectionsPagerAdapter sectionsPagerAdapter;
    private AppBarLayout appBar;
    final boolean[] isOpen = {true};

    private ExtendedFloatingActionButton fabadd, fabperdidas, fabencontradas, fabadopcion;
    private ImageButton fltrbtn;

    //public static final int DIFUSION_FRAGMENT = 1;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_difusion, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        //Referencia a los componentes del adaptador de pestañas
        tabs = view.findViewById(R.id.tabs);
        //tabs = new TabLayout(getActivity());
        viewPager = view.findViewById(R.id.view_pager);
        llenarViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        });
        tabs.setupWithViewPager(viewPager);


        //Implementacion del metodo para adaptar pestañas de reportes


        //tabs.setupWithViewPager(viewPager);

        //Referencias a los fabs del xml y habilitar el escuchador de cada fab
        fabadd = view.findViewById(R.id.fabadd);
        fabadd.setOnClickListener(this);
        fabperdidas = view.findViewById(R.id.id_add_perdida);
        fabperdidas.setOnClickListener(this);
        fabencontradas = view.findViewById(R.id.id_add_encontrada);
        fabencontradas.setOnClickListener(this);
        fabadopcion = view.findViewById(R.id.id_add_adopcion);
        fabadopcion.setOnClickListener(this);
        fltrbtn = view.findViewById(R.id.btn_filter);
        fltrbtn.setOnClickListener(this);

        //Ocultar los fabs y efecto de extender el fab add
        fabadd.shrink();
        fabperdidas.hide();
        fabencontradas.hide();
        fabadopcion.hide();
    }

    private void llenarViewPager(ViewPager viewPager) {
        sectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
        sectionsPagerAdapter.addFragment(new PerdidasFragment(), "Mascotas perdidas");
        sectionsPagerAdapter.addFragment(new EncontradasFragment(), "Mascotas   encontradas");
        sectionsPagerAdapter.addFragment(new AdopcionFragment(), "Mascotas en adopción");
        viewPager.setAdapter(sectionsPagerAdapter);
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
            case R.id.btn_filter:
                openFilterDialog();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }



    private void openFilterDialog() {
        FilterDialog filterDialog = new FilterDialog();
        filterDialog.setTargetFragment(sectionsPagerAdapter.getItem(tabs.getSelectedTabPosition()), 0);
        filterDialog.show(getFragmentManager().beginTransaction(), "Filtrar");
    }
}
