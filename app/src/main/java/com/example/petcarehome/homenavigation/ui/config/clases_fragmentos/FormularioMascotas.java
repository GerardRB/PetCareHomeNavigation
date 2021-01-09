package com.example.petcarehome.homenavigation.ui.config.clases_fragmentos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.Option;
import com.example.petcarehome.InicioYRegistro.Cuidador;
import com.example.petcarehome.InicioYRegistro.registroCuidadorActivity;
import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.HomeActivity_Cuidador;
import com.example.petcarehome.homenavigation.Objetos.FirebaseReferences;
import com.example.petcarehome.homenavigation.Objetos.Mascota;
import com.example.petcarehome.homenavigation.Objetos.Servicio;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormularioMascotas extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button mregistrarMascota;
    CheckBox paseo, estancia;
    TextView preciopaseo, preciohospedaje, comentariospaseo, comentarioshospedaje;
    Spinner spinnermascotas;
    //Para la bd
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_mascotas);

        //Llamando textviews
        preciopaseo = findViewById(R.id.precio_paseo);
        preciohospedaje = findViewById(R.id.precio_hospedaje);
        comentariospaseo = findViewById(R.id.comentarios_paseo);
        comentarioshospedaje = findViewById(R.id.comentarios_hospedaje);

        //Llamando botón para agregar mascota
        mregistrarMascota = findViewById(R.id.registrar_mascota);

        //Lamando checkbox
        paseo = findViewById(R.id.checkbox_paseo);
        estancia = findViewById(R.id.checkbox_estancia);

        //Spiner de las mascotas
        spinnermascotas = findViewById(R.id.mascot_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.combo_tipoRMP, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnermascotas.setAdapter(adapter);
        spinnermascotas.setOnItemSelectedListener(this);

        // onCheckboxClicked();

        //Si se selecciona el paseo...
        paseo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (paseo.isChecked() == true) {

                    preciopaseo.setVisibility(View.VISIBLE);
                    comentariospaseo.setVisibility(View.VISIBLE);


                } else {
                    preciopaseo.setVisibility(View.INVISIBLE);
                    comentariospaseo.setVisibility(View.INVISIBLE);
                }
            }
        });

        //Si se selecciona el hospedaje...
        estancia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (estancia.isChecked() == true) {

                    preciohospedaje.setVisibility(View.VISIBLE);
                    comentarioshospedaje.setVisibility(View.VISIBLE);
                } else {
                    preciohospedaje.setVisibility(View.INVISIBLE);
                    comentarioshospedaje.setVisibility(View.INVISIBLE);
                }
            }
        });

        //Cuando se presiona el botón para agregar mascota
        mregistrarMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckboxClicked();

            }

        });

    }

   /* private void AgregarMascotasPaseo() {
        //Referencias a la BD.
          firebaseDatabase = FirebaseDatabase.getInstance();
          user = FirebaseAuth.getInstance().getCurrentUser();

          final DatabaseReference cuidadorRef = firebaseDatabase.getReference().child(FirebaseReferences.USERS_REFERENCE)
              .child(FirebaseReferences.CUIDADOR_REFERENCE).child(user.getUid()).child(FirebaseReferences.MASCOTAS_CUIDADOR_REFERENCE);



        String tipopaseo = paseo.getText().toString();
        Double prepaseo = Double.parseDouble(preciopaseo.getText().toString());
        String comenpaseo = comentariospaseo.getText().toString();
        String tipomasco = spinnermascotas.getSelectedItem().toString();


        if(prepaseo.isNaN()){
            preciopaseo.setError("El campo debe ser un número");
        }else {
           // if(prepaseo(String.format(%3f,(double)prepaseo)){
           //     preciopaseo.setError("El precio debe ser menor de 3 digitos");
          // }
        }

        Servicio s = new Servicio(tipopaseo,prepaseo,comenpaseo);
        ArrayList<Servicio> servip = new ArrayList<>();
        servip.add(s);

        Mascota mp = new Mascota(tipomasco,servip);
        List<Mascota> mascop = new ArrayList<>();
        mascop.add(mp);

      //  Map<String, Object> cuidadorMap = new HashMap<>();
        // cuidadorMap.put("Mascotas", mascop);

        for(Mascota masco : mascop) {
            cuidadorRef.child(tipomasco).setValue(masco);
        }
    }
*/

  /*  private void AgregarMascotaHospedaje(){
        //Referencias a la BD.
        firebaseDatabase = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        final DatabaseReference cuidadorRef = firebaseDatabase.getReference().child(FirebaseReferences.USERS_REFERENCE)
                .child(FirebaseReferences.CUIDADOR_REFERENCE).child(user.getUid()).child(FirebaseReferences.MASCOTAS_CUIDADOR_REFERENCE);



        String tipohospe = estancia.getText().toString();
        Double prehospe = Double.parseDouble(preciohospedaje.getText().toString());
        String comenhospe = comentarioshospedaje.getText().toString();
        String tipomasco = spinnermascotas.getSelectedItem().toString();


        if(prehospe.isNaN()){
            preciohospedaje.setError("El campo debe ser un número");
        }else {
            // if(prepaseo(String.format(%3f,(double)prepaseo)){
            //     preciopaseo.setError("El precio debe ser menor de 3 digitos");
            // }
        }

        Servicio s = new Servicio(tipohospe,prehospe,comenhospe);
        ArrayList<Servicio> servih = new ArrayList<>();
        servih.add(s);

        Mascota mh = new Mascota(tipomasco,servih);
        List<Mascota> mascoh = new ArrayList<>();
        mascoh.add(mh);
        //  Map<String, Object> cuidadorMap = new HashMap<>();
        // cuidadorMap.put("Mascotas", mascop);

        for(Mascota masco : mascoh) {
            cuidadorRef.child(tipomasco).setValue(masco);
        }
    }
*/


    public void onCheckboxClicked() {

        //Referencias a la BD.
        firebaseDatabase = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        final DatabaseReference cuidadorRef = firebaseDatabase.getReference().child(FirebaseReferences.USERS_REFERENCE)
                .child(FirebaseReferences.CUIDADOR_REFERENCE).child(user.getUid()).child(FirebaseReferences.MASCOTAS_CUIDADOR_REFERENCE);


        // Is the view now checked?

        //Crear mascota que solo tendrá paseo
        if (paseo.isChecked() == true) {

            String mensaje = "Faltan campos por ingresar";
            String tipopaseo = paseo.getText().toString();
            String pretxt = preciopaseo.getText().toString();


            String comenpaseo = comentariospaseo.getText().toString();
            String tipomasco = spinnermascotas.getSelectedItem().toString();

            //Validaciones
            if(tipomasco.equals("Seleccionar")|| comenpaseo.isEmpty()||comenpaseo.length()<=50||pretxt.isEmpty()) {
                if(pretxt.isEmpty()){
                    preciopaseo.setError("Campo obligatorio");
                }

                if (tipomasco.equals("Seleccionar")) {
                    Toast.makeText(this, "Seleccione un tipo de mascota", Toast.LENGTH_SHORT).show();
                }
                if (comenpaseo.isEmpty()) {
                    comentariospaseo.setError("La descripción no puede estar vacía");
                }
                if (comenpaseo.length() <= 50) {
                    comentariospaseo.setError("La descripción debe ser mayor a 50 caracteres");
                }
            } else {
                Double prepaseo = Double.parseDouble(preciopaseo.getText().toString());
                Servicio s = new Servicio(tipopaseo, prepaseo, comenpaseo);
                ArrayList<Servicio> servip = new ArrayList<>();
                servip.add(s);

                Mascota mp = new Mascota(tipomasco, servip);
                List<Mascota> mascop = new ArrayList<>();
                mascop.add(mp);

                for (Mascota masco : mascop) {
                    cuidadorRef.child(tipomasco).setValue(masco).addOnCompleteListener(FormularioMascotas.this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(FormularioMascotas.this, "Error al registrar mascota", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(FormularioMascotas.this, "Mascota registrada", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(FormularioMascotas.this, mascotas_cuidador.class);
                                startActivity(intent);
                                finish();
                                return;
                            }
                        }
                    });
                }
            }
        }

        //Crear mascota que solo tendrá hospedaje
        if (estancia.isChecked() == true) {

            String tipohospe = estancia.getText().toString();

            String comenhospe = comentarioshospedaje.getText().toString();
            String tipomasco = spinnermascotas.getSelectedItem().toString();
            String pretxt = preciohospedaje.getText().toString();
            //Validaciones
            if(tipomasco.equals("Seleccionar")|| comenhospe.isEmpty()||comenhospe.length()<=50||pretxt.isEmpty()) {
                if(pretxt.isEmpty()){
                    preciohospedaje.setError("Campo obligatorio");
                }
                if (tipomasco.equals("Seleccionar")) {
                    Toast.makeText(this, "Seleccione un tipo de mascota", Toast.LENGTH_SHORT).show();
                }
                if (comenhospe.isEmpty()) {
                    comentarioshospedaje.setError("La descripción no puede estar vacía");
                }
                if (comenhospe.length() <= 50) {
                    comentarioshospedaje.setError("La descripción debe ser mayor a 50 caracteres");
                }
            } else {
                Double prehospe = Double.parseDouble(preciohospedaje.getText().toString());
                Servicio s = new Servicio(tipohospe, prehospe, comenhospe);
                ArrayList<Servicio> servih = new ArrayList<>();
                servih.add(s);

                Mascota mh = new Mascota(tipomasco, servih);
                List<Mascota> mascoh = new ArrayList<>();
                mascoh.add(mh);
                //  Map<String, Object> cuidadorMap = new HashMap<>();
                // cuidadorMap.put("Mascotas", mascop);

                for (Mascota masco : mascoh) {
                    cuidadorRef.child(tipomasco).setValue(masco).addOnCompleteListener(FormularioMascotas.this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(FormularioMascotas.this, "Error al registrar mascota", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(FormularioMascotas.this, "Mascota registrada", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(FormularioMascotas.this, mascotas_cuidador.class);
                                startActivity(i);
                                finish();
                                return;
                            }
                        }
                    });
                }
            }
        }

        //Crear mascota que tendrá paseo y hospedaje
        if ((paseo.isChecked() == true && estancia.isChecked() == true)) {
            String tipopaseo = paseo.getText().toString();


            String comenpaseo = comentariospaseo.getText().toString();
            String tipomasco = spinnermascotas.getSelectedItem().toString();

            String tipohospe = estancia.getText().toString();
            Double prehospe = Double.parseDouble(preciohospedaje.getText().toString());
            Double prepaseo = Double.parseDouble(preciopaseo.getText().toString());
            String comenhospe = comentarioshospedaje.getText().toString();


            //Validaciones
            if (tipomasco.equals("Seleccionar") || comenpaseo.isEmpty()
                    || comenpaseo.length() <= 50 ||
                    comenhospe.isEmpty() || comenhospe.length() <= 50) {

                //Validaciones paseo
                if (tipomasco.equals("Seleccionar")) {
                    Toast.makeText(this, "Seleccione un tipo de mascota", Toast.LENGTH_SHORT).show();
                }
                if (comenpaseo.isEmpty()) {
                    comentariospaseo.setError("La descripción no puede estar vacía");
                }
                if (comenpaseo.length() <= 50) {
                    comentariospaseo.setError("La descripción debe ser mayor a 50 caracteres");
                }

                //Validaciones hospedaje
                if (comenhospe.isEmpty()) {
                    comentarioshospedaje.setError("La descripción no puede estar vacía");
                }
                if (comenhospe.length() <= 50) {
                    comentarioshospedaje.setError("La descripción debe ser mayor a 50 caracteres");
                }

            } else {

                Servicio s = new Servicio(tipopaseo, prepaseo, comenpaseo);

                Servicio s1 = new Servicio(tipohospe, prehospe, comenhospe);

                ArrayList<Servicio> servip = new ArrayList<>();
                servip.add(s);
                servip.add(s1);

                Mascota mph = new Mascota(tipomasco, servip);

                List<Mascota> mascop = new ArrayList<>();
                mascop.add(mph);


                for (Mascota masco : mascop) {
                    cuidadorRef.child(tipomasco).setValue(masco).addOnCompleteListener(FormularioMascotas.this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(FormularioMascotas.this, "Error al registrar mascota", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(FormularioMascotas.this, "Mascota registrada", Toast.LENGTH_LONG).show();
                                Intent ii = new Intent(FormularioMascotas.this, mascotas_cuidador.class);
                                startActivity(ii);
                                finish();
                                return;
                            }
                        }
                    });
                }
            }
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}