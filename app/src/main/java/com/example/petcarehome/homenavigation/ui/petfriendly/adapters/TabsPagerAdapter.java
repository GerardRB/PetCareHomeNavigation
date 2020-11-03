package com.example.petcarehome.homenavigation.ui.petfriendly.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.petcarehome.homenavigation.Objetos.LugarPetFriendly;
import com.example.petcarehome.homenavigation.ui.petfriendly.fragments.DescripcionLugarFragment;
import com.example.petcarehome.homenavigation.ui.petfriendly.fragments.GaleriaLugarFragment;
import com.example.petcarehome.homenavigation.ui.petfriendly.fragments.ResenasLugarFragment;

public class TabsPagerAdapter extends FragmentStatePagerAdapter {
    private LugarPetFriendly mLugar;

    public TabsPagerAdapter(FragmentManager fm, LugarPetFriendly lugar) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mLugar = lugar;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return DescripcionLugarFragment.newInstance(mLugar);
            case 1:
                return GaleriaLugarFragment.newInstance(mLugar);
            case 2:
                return ResenasLugarFragment.newInstance(mLugar);
        }

        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Descripción";
            case 1:
                return "Galería";
            case 2:
                return "Reseñas";
        }

        return "";
    }

}
