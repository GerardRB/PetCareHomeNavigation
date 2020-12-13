package com.example.petcarehome.homenavigation;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.ui.config.FragmentOnBackPressedListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class HomeActivity_Dueno extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_dueno);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

    }

    @Override public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.drawer_layout_configuracion);
        if (!(fragment instanceof FragmentOnBackPressedListener) || !((FragmentOnBackPressedListener) fragment).onBackPressed()) {
            super.onBackPressed();
        }
    }
}
