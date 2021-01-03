package com.example.petcarehome.InicioYRegistro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.HomeActivity_Cuidador;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registroCuidadorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    EditText mnombre_cuidador, mapellidos_cuidador, mcontrasena, mcalle_cuidador,
            mnoext_cuidador, mnoint_cuidador, mcorreo_cuidador, mtel_cuidador, mcolonia_cuidador;

    Button mRegistrocuidador;

    //Para la base de datos
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_cuidador);
        //Inicializando el objeto de la base de datos para la autenticacion
        mAuth = FirebaseAuth.getInstance();


        //Mandando a llamar todos los del xml
        mnombre_cuidador = findViewById(R.id.nombre_dueno);
        mapellidos_cuidador = findViewById(R.id.apellidos_dueno);
        mcalle_cuidador = findViewById(R.id.calle_dueno);
        mnoext_cuidador = findViewById(R.id.noext_dueno);
        mnoint_cuidador = findViewById(R.id.noint_dueno);
        mcolonia_cuidador = findViewById(R.id.colonia_dueno);

        mcorreo_cuidador = findViewById(R.id.correo2_dueno);
        mtel_cuidador = findViewById(R.id.tel_dueno);
        mcontrasena = findViewById(R.id.contraseña2_dueno);

        mRegistrocuidador = findViewById(R.id.registrarse2_dueno);

        //Spiner de las alcaldías
        final Spinner spinneralcal = findViewById(R.id.delegacion_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.alcaldias, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinneralcal.setAdapter(adapter);
        spinneralcal.setOnItemSelectedListener(this);


        //---------------------------------------Boton de registro acción....

        mRegistrocuidador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String correo = mcorreo_cuidador.getText().toString();
                String contrasena = mcontrasena.getText().toString();


                mAuth.createUserWithEmailAndPassword(correo, contrasena).addOnCompleteListener(registroCuidadorActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(registroCuidadorActivity.this, "Error al registrarse", Toast.LENGTH_SHORT).show();
                        }else{
                            //Objetos para llenar la base de datos
                            String nombre = mnombre_cuidador.getText().toString();
                            String apellido = mapellidos_cuidador.getText().toString();
                            String calle = mcalle_cuidador.getText().toString();
                            String noext = mnoext_cuidador.getText().toString();
                            String noint = mnoint_cuidador.getText().toString();
                            String colonia = mcolonia_cuidador.getText().toString();
                            String alcaldia = spinneralcal.getSelectedItem().toString();
                            //  String correo = mcorreo_dueno.getText().toString();
                            // String contrasena = mcontrasena.getText().toString();
                            String telefono = mtel_cuidador.getText().toString();
                            String email = mcorreo_cuidador.getText().toString();

                            Cuidador Cuidador = new Cuidador(nombre, apellido, calle, noext, noint, colonia, alcaldia, telefono, email, "", "", "Inactivo", "Cuidador", null, null, null, null);

                            //Creando referencia al usuario actual -> a los dueños en la bd
                            DatabaseReference currentUserDB = FirebaseDatabase.getInstance().getReference().child("usuario").child("cuidador");

                            //Haciendo que se ordenen por tel e insertando los valores en la BD
                            currentUserDB.child(mAuth.getCurrentUser().getUid()).setValue(Cuidador);

                            //Si es correcto llevar a otra actividad (login)
                            Intent intent = new Intent(registroCuidadorActivity.this, HomeActivity_Cuidador.class);
                            startActivity(intent);
                            finish();
                            return;
                        }
                    }
                });

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
