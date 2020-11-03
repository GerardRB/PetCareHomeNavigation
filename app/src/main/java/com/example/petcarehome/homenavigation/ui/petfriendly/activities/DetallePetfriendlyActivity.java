package com.example.petcarehome.homenavigation.ui.petfriendly.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.Objetos.LugarPetFriendly;
import com.example.petcarehome.homenavigation.ui.petfriendly.adapters.TabsPagerAdapter;

public class DetallePetfriendlyActivity extends AppCompatActivity {

    private TabsPagerAdapter mAdapter;
    private ViewPager mViewPager;
    private LugarPetFriendly mLugar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_petfriendly);

        mLugar = (LugarPetFriendly) getIntent().getExtras().getSerializable("lugar");
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager(), mLugar);
        mViewPager = findViewById(R.id.pager);
        mViewPager.setAdapter(mAdapter);
    }

}