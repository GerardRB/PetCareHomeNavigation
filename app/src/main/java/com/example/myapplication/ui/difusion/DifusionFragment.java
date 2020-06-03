package com.example.myapplication.ui.difusion;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.example.myapplication.R;
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

        sectionsPagerAdapter = new SectionsPagerAdapter( this.getContext(), getFragmentManager());
        viewPager = root.findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs = root.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = root.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        return root;
    }

    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment(){
        }


        //Seleccion del fragmento
        public static Fragment newInstance(int index) {
            Fragment fragment = null;
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




    public class SectionsPagerAdapter extends FragmentPagerAdapter {


        public SectionsPagerAdapter(Context context, FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "Mascotas perdidas";
                case 1:
                    return "Mascotas encontradas";
                case 2:
                    return "Mascotas en adopción";
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 3;
        }
    }





}
