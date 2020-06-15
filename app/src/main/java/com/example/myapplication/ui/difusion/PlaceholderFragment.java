package com.example.myapplication.ui.difusion;

import androidx.fragment.app.Fragment;

import com.example.myapplication.ui.difusion.adopcion.AdopcionFragment;
import com.example.myapplication.ui.difusion.encontradas.EncontradasFragment;
import com.example.myapplication.ui.difusion.perdidas.PerdidasFragment;

public class PlaceholderFragment extends Fragment {

    //private static final String ARG_SECTION_NUMBER = "section_number";

    /*public PlaceholderFragment(){
    }*/

    //Seleccion del fragmento
    public static Fragment newInstance(int index) {
        Fragment fragment;
        switch (index){
            case 1: fragment = new PerdidasFragment();
                break;
            case 2: fragment = new EncontradasFragment();
                break;
            case 3: fragment = new AdopcionFragment();
                break;
            default: fragment = new PerdidasFragment();
        }
        return fragment;
    }
}