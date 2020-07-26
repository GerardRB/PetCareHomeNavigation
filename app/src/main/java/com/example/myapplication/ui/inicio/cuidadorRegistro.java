package com.example.myapplication.ui.inicio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.HomeActivity_Cuidador;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class cuidadorRegistro extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {
    private EditText mcorreo2, mcontraseña2, mnombre, mapellidom, mapellidop, mtel;
    private Button mregistrarse2;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    DatabaseReference mdatabaseReference;

    //Variables de los datos a registrar
    private String nombre, apellidos, telefono, correo, contraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuidador_registro);

        //Para el spinner
        Spinner spinner = findViewById(R.id.delegacion_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.alcaldias, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //spinner.setOnItemClickListener(this);
//

        mregistrarse2 = (Button) findViewById(R.id.registrarse2);

        mregistrarse2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cuidadorRegistro.this, HomeActivity_Cuidador.class);
                startActivity(intent);
                finish();
                return;
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}