package com.example.petcarehome.homenavigation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.ui.config.FragmentOnBackPressedListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity_Cuidador extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__cuidador);

        BottomNavigationView navView = findViewById(R.id.nav_view_cuidador);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_cuidador);
        NavigationUI.setupWithNavController(navView, navController);

    }

    @Override public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.drawer_layout_configuracion);
        if (!(fragment instanceof FragmentOnBackPressedListener) || !((FragmentOnBackPressedListener) fragment).onBackPressed()) {
            super.onBackPressed();
        }
    }

}
