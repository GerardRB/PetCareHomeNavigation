package com.example.petcarehome.homenavigation.ui.config;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.petcarehome.InicioYRegistro.TipoUserActivity;
import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.Objetos.FirebaseReferences;
import com.example.petcarehome.homenavigation.ui.config.clases_fragmentos.ayuda;
import com.example.petcarehome.homenavigation.ui.config.clases_fragmentos.cambiar_contrasenad;
import com.example.petcarehome.homenavigation.ui.config.clases_fragmentos.idioma;
import com.example.petcarehome.homenavigation.ui.config.clases_fragmentos.notificaciones;
import com.example.petcarehome.homenavigation.ui.config.clases_fragmentos.perfil_usuario_dueno;
import com.example.petcarehome.homenavigation.ui.config.clases_fragmentos.terminos_y_condiciones;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfigFragment_dueno extends Fragment implements FragmentOnBackPressedListener, NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout_confi;
    private Toolbar toolbar_confi;
    private ActionBarDrawerToggle actionBarDrawerToggle_confi;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    FragmentManager fragmentManager;
    // private TextView textView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_config_dueno, container, false);
        return root;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        //textView.setText("Fragmento de Configuración");


        //LLamando y asignando la toolbar de configuración (se encuentra en su xml (fragment_config_dueno.xml))
        toolbar_confi = (Toolbar) view.findViewById(R.id.toolar_configuracion);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar_confi);

        //Llamando el DrawerLayout que se utiliza para el menú lateral
        drawerLayout_confi = (DrawerLayout) view.findViewById(R.id.drawer_layout_configuracion);

        //Generando el ActionBarDrawerToggle / la hamburguesa
        actionBarDrawerToggle_confi = new ActionBarDrawerToggle(
                getActivity(), drawerLayout_confi, toolbar_confi, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        //Syncronizar el ícono de hamburguesa con el drawerlayout
        drawerLayout_confi.addDrawerListener(actionBarDrawerToggle_confi);
        actionBarDrawerToggle_confi.syncState();


        //Creando referencia al Navigation View para manejar los eventos al hacer clic
        NavigationView navigationView = view.findViewById(R.id.navigation_drawer_configuracion);
        navigationView.setNavigationItemSelectedListener(this);

        //Que se guarde este fragmento al volver/ir a configuración y que se guarde al girar el dispositivo
        if (savedInstanceState == null) {

            //Fragmento principal perfil - perfil_usuario_cuidador() = fragmento del perfil, el que se muestra al ir al apartado de configuración
            getFragmentManager().beginTransaction().replace(R.id.contenedor_fragmentos, new perfil_usuario_dueno()).commit();
            navigationView.setCheckedItem(R.id.volver_menuconfig);

        }
    }


    //Regresar/cerrar el menú lateral al dar hacia atrás
    @Override
    public boolean onBackPressed() {
        if (drawerLayout_confi.isDrawerOpen(GravityCompat.END)) {
            drawerLayout_confi.closeDrawer(GravityCompat.END);
            //action not popBackStack
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            //Primer icono, volver a apartado de configuración
            case R.id.volver_menuconfig:
                getFragmentManager().beginTransaction().replace(R.id.contenedor_fragmentos, new perfil_usuario_dueno()).commit();
                break;
            //Segundo icono, ir a fragmento para cambiar la contraseña
            case R.id.contrasena_menuconfig:
                getFragmentManager().beginTransaction().replace(R.id.contenedor_fragmentos, new cambiar_contrasenad()).commit();
                break;
            //Tercer icono, ir a fragmento para manejar las notificaciones
            case R.id.notificaciones_menuconfig:
                getFragmentManager().beginTransaction().replace(R.id.contenedor_fragmentos, new notificaciones()).commit();
                break;
            //Cuarto icono, ir a fragmento que solo tendrá el idioma
            case R.id.idioma_menuconfig:
                getFragmentManager().beginTransaction().replace(R.id.contenedor_fragmentos, new idioma()).commit();
                break;
            //Quinto icono, ir a fragmento que tendrá los términos y condiciones
            case R.id.terms_menuconfig:
                getFragmentManager().beginTransaction().replace(R.id.contenedor_fragmentos, new terminos_y_condiciones()).commit();
                break;
            //Sexto icono, ir a fragmento que tendrá la ayuda
            case R.id.Ayuda_menuconfig:
                getFragmentManager().beginTransaction().replace(R.id.contenedor_fragmentos, new ayuda()).commit();
                break;
            //Séptimo icono, llamar a función para salir de la aplicación
            case R.id.salir_menuconfig:
                alertCerrarSesion();
                break;
            //Octavo icono, llamar a función para eliminar la cuenta
            case R.id.eliminarcuenta_menuconfig:
                alertBorrarCuenta();
                break;
        }
        drawerLayout_confi.closeDrawer(GravityCompat.START);
        return true;
    }

    /* Para moverse de fragmento a una actividad
    private void moveToNewActivity () {
        Intent i = new Intent(getActivity(), DetailActivity.class);
        startActivity(i);
        ((Activity) getActivity()).overridePendingTransition(0, 0);
    }
     */

    //Función para que al presionar el icono de cerrar sesión muestre un mensaje y cierre o no la sesión.
    public void alertCerrarSesion() {
        AlertDialog.Builder alertDialog2 = new
                AlertDialog.Builder(
                getActivity());

        // Título del dialogo
        alertDialog2.setTitle("Confirmar Cerrar sesión");

        // Mensaje del dialogo
        alertDialog2.setMessage("¿Estás seguro de querer cerrar sesión? Volverás a la pantalla principal.");

        // Btn Positivo "Yes"
        alertDialog2.setPositiveButton("SÍ",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Lo que va a pasar después de dar sí
                        mAuth.getInstance().signOut();
                        Intent i = new Intent(getActivity(),
                                TipoUserActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    }
                });

        // Btn Negativo "NO"
        alertDialog2.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Lo que va a pasar después de dar no
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Seleccionaste: NO", Toast.LENGTH_SHORT)
                                .show();
                        dialog.cancel();
                    }
                });

        // Mostrando el alertDialog
        alertDialog2.show();
    }

    //Función para borrar la cuenta y al presionar el icono elimine o no la cuenta
    public void alertBorrarCuenta() {
        AlertDialog.Builder alertDialog2 = new
                AlertDialog.Builder(
                getActivity());

        // Título del dialogo
        alertDialog2.setTitle("Confirmar borrar cuenta");

        // Mensaje del dialogo
        alertDialog2.setMessage("¿Estás seguro de querer borrar tu cuenta? No podrás recuperarla. " +
                "Saldrás de la aplicación y tendrás que crear una nueva.");

        // Btn Positivo "Yes"
        alertDialog2.setPositiveButton("SÍ",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Lo que se va a ejecutar después de dar sí
                        mAuth = FirebaseAuth.getInstance();
                        firebaseUser = mAuth.getCurrentUser();
                        firebaseDatabase = FirebaseDatabase.getInstance();

                        user = FirebaseAuth.getInstance().getCurrentUser();

                        final DatabaseReference duenoReference = firebaseDatabase.getReference().child(FirebaseReferences.USERS_REFERENCE)
                                .child(FirebaseReferences.DUENO_REFERENCE).child(user.getUid());


                        firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {

                                    Toast.makeText(getActivity(), "Usuario eliminado", Toast.LENGTH_SHORT).show();

                                    Intent i = new Intent(getActivity(),
                                            TipoUserActivity.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                            Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(i);
                                } else {
                                    Toast.makeText(getActivity(), "No se pudo borrar el usuario", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        duenoReference.removeValue();
                    }
                });

        // Btn Negativo "NO"
        alertDialog2.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Lo que se va a ejecutar después de dar no
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Seleccionaste: NO", Toast.LENGTH_SHORT)
                                .show();
                        dialog.cancel();
                    }
                });

        // Mostrando el alertDialog
        alertDialog2.show();
    }

}