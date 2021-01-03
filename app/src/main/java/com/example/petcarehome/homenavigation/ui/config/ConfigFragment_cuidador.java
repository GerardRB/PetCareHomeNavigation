package com.example.petcarehome.homenavigation.ui.config;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

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

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.petcarehome.InicioYRegistro.TipoUserActivity;
import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.Objetos.FirebaseReferences;
import com.example.petcarehome.homenavigation.ui.config.clases_fragmentos.ayuda;
import com.example.petcarehome.homenavigation.ui.config.clases_fragmentos.cambiar_contrasena;
import com.example.petcarehome.homenavigation.ui.config.clases_fragmentos.idioma;
import com.example.petcarehome.homenavigation.ui.config.clases_fragmentos.mascotas_cuidador;
import com.example.petcarehome.homenavigation.ui.config.clases_fragmentos.notificaciones;
import com.example.petcarehome.homenavigation.ui.config.clases_fragmentos.perfil_usuario_cuidador;
import com.example.petcarehome.homenavigation.ui.config.clases_fragmentos.terminos_y_condiciones;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ConfigFragment_cuidador extends Fragment implements FragmentOnBackPressedListener, NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout_confi_cuidador;
    private Toolbar toolbar_confi_cuidador;
    private ActionBarDrawerToggle actionBarDrawerToggle_confi_cuidador;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private ImageView imagenHeaderc;
    FirebaseUser user;
    FirebaseStorage firebaseStorage;
    FragmentManager fragmentManager;
    // private TextView textView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_config_cuidador, container, false);

    }

    //Llamando a las variables e instanciando
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        //textView.setText("Fragmento de Configuración_cuidador");

        //LLamando y asignando la toolbar de configuración (se encuentra en su xml (fragment_config_dueno.xml))
        toolbar_confi_cuidador = (Toolbar) view.findViewById(R.id.toolar_configuracion_cuidador);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar_confi_cuidador);


        //Llamando el DrawerLayout que se utiliza para el menú lateral
        drawerLayout_confi_cuidador = (DrawerLayout) view.findViewById(R.id.drawer_layout_configuracion_cuidador);

        //Generando el ActionBarDrawerToggle / la hamburguesa
        actionBarDrawerToggle_confi_cuidador = new ActionBarDrawerToggle(
                getActivity(), drawerLayout_confi_cuidador, toolbar_confi_cuidador, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        //Syncronizar el ícono de hamburguesa con el drawerlayout
        drawerLayout_confi_cuidador.addDrawerListener(actionBarDrawerToggle_confi_cuidador);
        actionBarDrawerToggle_confi_cuidador.syncState();


        //Creando referencia al Navigation View para manejar los eventos al hacer clic
        NavigationView navigationView = view.findViewById(R.id.navigation_drawer_configuracion_cuidador);
        navigationView.setNavigationItemSelectedListener(this);

        //Que se guarde este fragmento al volver/ir a configuración y que se guarde al girar el dispositivo
        if (savedInstanceState == null) {

            //Fragmento principal perfil - perfil_usuario_cuidador() = fragmento del perfil, el que se muestra al ir al apartado de configuración
            getFragmentManager().beginTransaction().replace(R.id.contenedor_fragmentos_cuidador, new perfil_usuario_cuidador()).commit();
            navigationView.setCheckedItem(R.id.volver_menuconfig);


        }
    }

    //Regresar/cerrar el menú lateral al dar hacia atrás
    @Override
    public boolean onBackPressed() {
        if (drawerLayout_confi_cuidador.isDrawerOpen(GravityCompat.END)) {
            drawerLayout_confi_cuidador.closeDrawer(GravityCompat.END);
            //action not popBackStack
            return true;
        } else {
            return false;
        }
    }

    //Manejador del menú (iconos)
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            //Primer icono, volver a apartado de configuración
            case R.id.volver_menuconfig:
                getFragmentManager().beginTransaction().replace(R.id.contenedor_fragmentos_cuidador, new perfil_usuario_cuidador()).commit();
                break;
            //Segundo icono, ir a fragmento para cambiar la contraseña
            case R.id.contrasena_menuconfig:
                getFragmentManager().beginTransaction().replace(R.id.contenedor_fragmentos_cuidador, new cambiar_contrasena()).commit();
                break;
            //Tercer icono, ir a fragmento para manejar las notificaciones
            case R.id.mascotas_cuidador:
                getFragmentManager().beginTransaction().replace(R.id.contenedor_fragmentos_cuidador, new mascotas_cuidador()).commit();
                break;
            //Tercer icono, ir a fragmento para manejar las notificaciones
            case R.id.notificaciones_menuconfig:
                getFragmentManager().beginTransaction().replace(R.id.contenedor_fragmentos_cuidador, new notificaciones()).commit();
                break;
            //Cuarto icono, ir a fragmento que solo tendrá el idioma
            case R.id.idioma_menuconfig:
                getFragmentManager().beginTransaction().replace(R.id.contenedor_fragmentos_cuidador, new idioma()).commit();
                break;
            //Quinto icono, ir a fragmento que tendrá los términos y condiciones
            case R.id.terms_menuconfig:
                getFragmentManager().beginTransaction().replace(R.id.contenedor_fragmentos_cuidador, new terminos_y_condiciones()).commit();
                break;
            //Sexto icono, ir a fragmento que tendrá la ayuda
            case R.id.Ayuda_menuconfig:
                getFragmentManager().beginTransaction().replace(R.id.contenedor_fragmentos_cuidador, new ayuda()).commit();
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
        drawerLayout_confi_cuidador.closeDrawer(GravityCompat.START);
        return true;
    }

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
                "Volverás a la pantalla principal y tendrás que crear una nueva.");

        // Btn Positivo "Yes"
        alertDialog2.setPositiveButton("SÍ",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mAuth = FirebaseAuth.getInstance();
                        firebaseUser = mAuth.getCurrentUser();
                        // Lo que se va a ejecutar después de dar sí
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