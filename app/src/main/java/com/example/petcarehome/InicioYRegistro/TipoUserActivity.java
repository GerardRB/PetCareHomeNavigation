package com.example.petcarehome.InicioYRegistro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.HomeActivity_Cuidador;
import com.example.petcarehome.homenavigation.HomeActivity_Dueno;
import com.example.petcarehome.homenavigation.Objetos.FirebaseReferences;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TipoUserActivity extends AppCompatActivity {
    Button mcuidador, mdueño, minfo;
    FirebaseDatabase firebaseDatabase;
    private FirebaseAuth mAuth;
    TextView invisibled, invisiblec;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private FirebaseUser usuario;
    private boolean a, c;
    private String idUser;
    ArrayList<String> listadueno = new ArrayList<>();
    ArrayList<String> listacuidador = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tipo_user);
        mcuidador = findViewById(R.id.cuidador);
        mdueño = findViewById(R.id.dueño);
        minfo = findViewById(R.id.info);
    
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase.getInstance();
        usuario = mAuth.getCurrentUser();
        invisibled = findViewById(R.id.textinvisibled);
        invisiblec = findViewById(R.id.textinvisiblec);


        a = false;
        c = false;
    //    Toast.makeText(this, "UID"+usuario.getUid(), Toast.LENGTH_SHORT).show();
        if (usuario!=null) {
            a = ValidarDueno();
            c = ValidarCuidador();
        }

/*
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                 usuario = mAuth.getCurrentUser();
                idUser = usuario.getUid();
                //  if(usuario!=null){
                //    Intent intent = new Intent(TipoUserActivity.this, HomeActivity_Dueno.class);
                //   startActivity(intent);
                //   finish();
                //    return;
                if(usuario!= null){
                    listUser.add(idUser);
                }
            }
        };

 */






        mdueño.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(a){
                    Intent intent = new Intent(TipoUserActivity.this, HomeActivity_Dueno.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(TipoUserActivity.this, duenoLoginActivity.class);
                    startActivity(intent);
                    finish();
                }


            }
        });


        mcuidador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c){
                    Intent intent = new Intent(TipoUserActivity.this, HomeActivity_Cuidador.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(TipoUserActivity.this, cuidadorLoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        minfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    private boolean ValidarCuidador() {


        final DatabaseReference cuidadorRef = FirebaseDatabase.getInstance().getReference().child(FirebaseReferences.USERS_REFERENCE)
                .child(FirebaseReferences.CUIDADOR_REFERENCE);

        cuidadorRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapdueno :
                        dataSnapshot.getChildren()) {
                    if(snapdueno.exists()){
                        if(snapdueno.getKey().equals(usuario.getUid())){
                          String a =   snapdueno.getKey();
                          invisiblec.setText(a);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Toast.makeText(this, "UID"+invisiblec.getText().toString(), Toast.LENGTH_SHORT).show();

        if(!invisiblec.getText().toString().isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    private boolean ValidarDueno() {

        final DatabaseReference duenoRef = FirebaseDatabase.getInstance().getReference().child(FirebaseReferences.USERS_REFERENCE)
                .child(FirebaseReferences.DUENO_REFERENCE);

        duenoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapdueno :
                        dataSnapshot.getChildren()) {
                    if(snapdueno.exists()){
                        if(snapdueno.getKey().equals(usuario.getUid())){
                          String b = snapdueno.getKey();
                          invisibled.setText(b);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Toast.makeText(this, "UID"+invisibled.getText().toString(), Toast.LENGTH_SHORT).show();
        if(invisibled.getText().toString().isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    public void openDialog(){
        DialogoInfo dialogoInfo = new DialogoInfo();
        dialogoInfo.show(getSupportFragmentManager(), "Información");

    }

/*
    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListener);
    }

 */




}
