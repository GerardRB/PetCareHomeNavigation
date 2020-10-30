package com.example.petcarehome.homenavigation.ui.petfriendly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.Objetos.LugarPetFriendly;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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