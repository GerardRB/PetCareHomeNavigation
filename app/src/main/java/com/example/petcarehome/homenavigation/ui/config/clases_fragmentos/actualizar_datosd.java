package com.example.petcarehome.homenavigation.ui.config.clases_fragmentos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.Objetos.FirebaseReferences;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class actualizar_datosd extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText nombre_d, apellido_d, calle_d, noext_d, noint_d, tel_d;
    Button actualizarDatos;
    ImageView profileImage;
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;
    Spinner alcaldia_d;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_datosd);

        nombre_d = findViewById(R.id.nombre_actualizard);
        apellido_d = findViewById(R.id.apellidos_actualizard);
        calle_d = findViewById(R.id.calle_actualizard);
        noext_d = findViewById(R.id.noext_actualizard);
        noint_d = findViewById(R.id.noint_actualizard);
        tel_d = findViewById(R.id.tel_actualizard);

        //Spiner de las alcaldías
        alcaldia_d = findViewById(R.id.delegacion_spinner_actualizard);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.alcaldias, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        alcaldia_d.setAdapter(adapter);
        alcaldia_d.setOnItemSelectedListener(this);


        actualizarDatos = findViewById(R.id.registrarse2_actualizard);
        profileImage = findViewById(R.id.profile_imaged);


        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Abrir galeria
                Intent abrirgaleria = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(abrirgaleria, 1000);
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference dueReference = firebaseDatabase.getReference().child(FirebaseReferences.USERS_REFERENCE)
                .child(FirebaseReferences.DUENO_REFERENCE).child(user.getUid());

        dueReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("nombre").getValue().toString();
                String lastname = dataSnapshot.child("apellidos").getValue().toString();
                String street = dataSnapshot.child("calle").getValue().toString();
                String tel = dataSnapshot.child("telefono").getValue().toString();
                String noi = dataSnapshot.child("noint").getValue().toString();
                String noe = dataSnapshot.child("noext").getValue(String.class);
                //   String alca = dataSnapshot.child("alcaldia").getValue(String.class);

                nombre_d.setText(name);
                apellido_d.setText(lastname);
                calle_d.setText(street);
                tel_d.setText(tel);
                noext_d.setText(noe);
                noint_d.setText(noi);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        actualizarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActualizarPerfil();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
              //  Uri imageUri = data.getData();
                //  profileImage.setImageURI(imageUri);
               // subirImagenFirebase(imageUri);
                for (int i = 0; i < data.getClipData().getItemCount(); i++) {
                    CropImage.activity(data.getClipData().getItemAt(i).getUri())
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .setRequestedSize(1024, 1024)
                            .setAspectRatio(3, 3).start(actualizar_datosd.this);
                }

            } else {
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setRequestedSize(1024, 1024)
                        .setAspectRatio(3, 3).start(actualizar_datosd.this);
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri imageUri = result.getUri();
                subirImagenFirebase(imageUri);
            }
        }
    }

    private void subirImagenFirebase(Uri imageUri) {
        String idUser = null;
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            idUser = user.getUid();
        }
        StorageReference storagePerfilCuidadorReference =
                firebaseStorage.getInstance().getReference
                        (FirebaseReferences.STORAGE_IMAGENPERFIL_REFERENCE).
                        child(FirebaseReferences.STORAGE_IMAGENPERFILDUENO_REFERENCE).child(idUser);


        final StorageReference fileRef = storagePerfilCuidadorReference.child("img" + idUser + ".jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(actualizar_datosd.this, "Imagen cargada", Toast.LENGTH_SHORT).show();
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profileImage);
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(actualizar_datosd.this, "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ActualizarPerfil() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        //Referencia a la base de datos

        final DatabaseReference duenoReference = firebaseDatabase.getReference().child(FirebaseReferences.USERS_REFERENCE)
                .child(FirebaseReferences.DUENO_REFERENCE).child(user.getUid());

        duenoReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("nombre").getValue().toString();
                String lastname = dataSnapshot.child("apellidos").getValue().toString();
                String street = dataSnapshot.child("correo").getValue().toString();
                String tel = dataSnapshot.child("telefono").getValue().toString();
                String noi = dataSnapshot.child("noint").getValue().toString();
                String noe = dataSnapshot.child("noext").getValue(String.class);
                //   String alca = dataSnapshot.child("alcaldia").getValue(String.class);

                nombre_d.setText(name);
                apellido_d.setText(lastname);
                calle_d.setText(street);
                tel_d.setText(tel);
                noext_d.setText(noe);
                noint_d.setText(noi);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        String nombre = nombre_d.getText().toString();
        String apellido = apellido_d.getText().toString();
        String calle = calle_d.getText().toString();
        String noex = noext_d.getText().toString();
        String noin = noint_d.getText().toString();
        String alcal = alcaldia_d.getSelectedItem().toString();
        String tel = tel_d.getText().toString();

        //Validaciones
        String mensaje = "Debe seleccionar un campo válido";
        if (nombre.isEmpty() || apellido.isEmpty() || calle.isEmpty() || noex.isEmpty() || noin.isEmpty() || alcal.equals("Seleccionar")
                || tel.isEmpty()) {
            if (nombre.isEmpty()) {
                nombre_d.setError("Obligatorio");
            }
            if (apellido.isEmpty()) {
                apellido_d.setError("Obligatorio");
            }
            if (calle.isEmpty()) {
                calle_d.setError("Obligatorio");
            }
            if (noex.isEmpty()) {
                noext_d.setError("Obligatorio");
            }
            if (noin.isEmpty()) {
                noint_d.setError("Obligatorio");
            }
            if (alcal.equals("Seleccionar")) {
                mensaje += "\nSeleccione una alcaldía";
            }
            if (tel.isEmpty()) {
                tel_d.setError("Obligatorio");
            }
            Toast.makeText(this, "Debe llenar los campos obligatorios", Toast.LENGTH_SHORT).show();
        } else {
            duenoReference.child("nombre").getRef().setValue(nombre);
            duenoReference.child("apellidos").getRef().setValue(apellido);
            duenoReference.child("calle").getRef().setValue(calle);
            duenoReference.child("noext").getRef().setValue(noex);
            duenoReference.child("noint").getRef().setValue(noin);
            duenoReference.child("alcaldia").getRef().setValue(alcal);
            duenoReference.child("telefono").getRef().setValue(tel);
            onBackPressed();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}