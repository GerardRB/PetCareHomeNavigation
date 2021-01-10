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
import com.example.petcarehome.homenavigation.HomeActivity_Dueno;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class   registroDuenoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    EditText mnombre_dueno, mapellidos_dueno, mcontrasena, mcalle_dueno,
            mnoext_dueno, mnoint_dueno, mcorreo_dueno, mtel_dueno, mcolonia_dueno;
    Button mRegistroDueno;

    //Para la base de datos
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_dueno);

        //Inicializando el objeto de la base de datos para la autenticacion
        mAuth = FirebaseAuth.getInstance();


        //Mandando a llamar todos los del xml
        mnombre_dueno = findViewById(R.id.nombre_dueno);
        mapellidos_dueno = findViewById(R.id.apellidos_dueno);
        mcalle_dueno = findViewById(R.id.calle_dueno);
        mnoext_dueno = findViewById(R.id.noext_dueno);
        mnoint_dueno = findViewById(R.id.noint_dueno);
        mcolonia_dueno = findViewById(R.id.colonia_dueno);

        mcorreo_dueno = findViewById(R.id.correo2_dueno);
        mtel_dueno = findViewById(R.id.tel_dueno);
        mcontrasena = findViewById(R.id.contraseña2_dueno);

        mRegistroDueno = findViewById(R.id.registrarse2_dueno);

        //Spiner de las alcaldías
        final Spinner spinneralcal = findViewById(R.id.delegacion_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.alcaldias, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinneralcal.setAdapter(adapter);
        spinneralcal.setOnItemSelectedListener(this);


        //---------------------------------------Boton de registro acción....

        mRegistroDueno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String correo = mcorreo_dueno.getText().toString();
                String mcontrasenad = mcontrasena.getText().toString();
                //Objetos para llenar la base de datos
                String nombre = mnombre_dueno.getText().toString();
                String apellido = mapellidos_dueno.getText().toString();
                String calle = mcalle_dueno.getText().toString();
                String noext = mnoext_dueno.getText().toString();
                String noint = mnoint_dueno.getText().toString();
                String colonia = mcolonia_dueno.getText().toString();
                String alcaldia = spinneralcal.getSelectedItem().toString();
                //  String correo = mcorreo_dueno.getText().toString();
                // String contrasena = mcontrasena.getText().toString();
                String telefono = mtel_dueno.getText().toString();
                //String email = mcorreo_dueno.getText().toString();
              //  String contraseña = mcontrasena.getText().toString();
                if (correo.isEmpty() || mcontrasenad.isEmpty()||nombre.isEmpty()||apellido.isEmpty()||calle.isEmpty()||noext.isEmpty()
                        ||colonia.isEmpty()||alcaldia.equals("Seleccionar")||telefono.isEmpty()||telefono.length()>11||telefono.length()<9
                        ||!correo.matches(emailPattern))
                {
                    if (correo.isEmpty()) {
                        mcorreo_dueno.setError("Campo obligatorio");
                    }
                    if (mcontrasenad.isEmpty()) {
                        mcontrasena.setError("Campo obligatorio");
                    }
                    if(nombre.isEmpty()){
                        mnombre_dueno.setError("Campo obligatorio");
                    }
                    if(apellido.isEmpty()){
                        mapellidos_dueno.setError("Campo obligatorio");
                    }
                    if(calle.isEmpty()){
                        mcalle_dueno.setError("Campo obligatorio");
                    }
                    if(noext.isEmpty()){
                        mnoext_dueno.setError("Campo obligatorio");
                    }
                    if (colonia.isEmpty()){
                        mnoint_dueno.setError("Campo obligatorio");
                    }
                    if(alcaldia.equals("Seleccionar")){
                        Toast.makeText(registroDuenoActivity.this, "Selecciona una alcadía", Toast.LENGTH_SHORT).show();
                    }
                    if (telefono.isEmpty()){
                        mtel_dueno.setError("Campo obligatorio");
                    }
                    if(telefono.length()>11||telefono.length()<9){
                        mtel_dueno.setError("El teléfono debe ser de 10 dígitos");
                    }
                    if(!correo.matches(emailPattern)){
                        mcorreo_dueno.setError("Correo no válido");
                    }
                    /*
                    if(!telefono.matches(celPattern)){
                        mtel_cuidador.setError("Teléfono no válido");
                    }
                    */
                }
                else {
                    mAuth.createUserWithEmailAndPassword(correo, mcontrasenad).addOnCompleteListener(registroDuenoActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(registroDuenoActivity.this, "Error al registrarse", Toast.LENGTH_SHORT).show();
                            } else {
                                //Objetos para llenar la base de datos
                                String nombre = mnombre_dueno.getText().toString();
                                String apellido = mapellidos_dueno.getText().toString();
                                String calle = mcalle_dueno.getText().toString();
                                String noext = mnoext_dueno.getText().toString();
                                String noint = mnoint_dueno.getText().toString();
                                String colonia = mcolonia_dueno.getText().toString();
                                String alcaldia = spinneralcal.getSelectedItem().toString();
                                //  String correo = mcorreo_dueno.getText().toString();
                                // String contrasena = mcontrasena.getText().toString();
                                String telefono = mtel_dueno.getText().toString();
                                String email = mcorreo_dueno.getText().toString();
                                String contraseña = mcontrasena.getText().toString();

                                Dueno Dueno = new Dueno(nombre, apellido, calle, noext, noint, colonia, alcaldia, telefono, email, "", "Dueno", contraseña);

                                //Creando referencia al usuario actual -> a los dueños en la bd
                                DatabaseReference currentUserDB = FirebaseDatabase.getInstance().getReference().child("usuario").child("dueno");

                                //Haciendo que se ordenen por tel e insertando los valores en la BD
                                currentUserDB.child(mAuth.getCurrentUser().getUid()).setValue(Dueno);

                                //Si es correcto llevar a otra actividad (login)
                                Intent intent = new Intent(registroDuenoActivity.this, HomeActivity_Dueno.class);
                                startActivity(intent);
                                finish();
                                return;
                            }
                        }
                    });
                }
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
