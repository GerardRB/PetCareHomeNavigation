package com.example.petcarehome.homenavigation.ui.config.clases_fragmentos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.Objetos.FirebaseReferences;
import com.example.petcarehome.homenavigation.ui.difusion.FullScreenImageActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
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

import java.util.Objects;

public class actualizar_datosd extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText nombre_d, apellido_d, calle_d, col_d, noext_d, noint_d, tel_d;
    Button actualizarDatos;
    ImageView profileImage;
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;
    Spinner alcaldia_d;
    FirebaseUser user;
    ExtendedFloatingActionButton fabAddPhoto;
    Uri imageUri;
    private String fotoP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_datosd);

        nombre_d = findViewById(R.id.nombre_actualizard);
        apellido_d = findViewById(R.id.apellidos_actualizard);
        calle_d = findViewById(R.id.calle_actualizard);
        col_d = findViewById(R.id.colonia_actualizard);
        noext_d = findViewById(R.id.noext_actualizard);
        noint_d = findViewById(R.id.noint_actualizard);
        tel_d = findViewById(R.id.tel_actualizard);
        imageUri = null;
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
                if (imageUri != null){
                    fotoP = imageUri.toString();
                }
                Intent intent = new Intent(actualizar_datosd.this, FullScreenImageActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(actualizar_datosd.this, profileImage, Objects.requireNonNull(ViewCompat.getTransitionName(profileImage)));
                Bundle  bundle = new Bundle();
                bundle.putString("title", "perfil");
                bundle.putString("foto", fotoP);
                intent.putExtras(bundle);
                startActivity(intent, options.toBundle());
            }
        });

        fabAddPhoto = findViewById(R.id.fab_addphotoD);
        fabAddPhoto.setIconResource(R.drawable.ic_cameraadd);
        fabAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getPermissionsReadStorage();
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
                String colonia = dataSnapshot.child("colonia").getValue().toString();
                String tel = dataSnapshot.child("telefono").getValue().toString();
                String noi = dataSnapshot.child("noint").getValue().toString();
                String noe = dataSnapshot.child("noext").getValue(String.class);
                //   String alca = dataSnapshot.child("alcaldia").getValue(String.class);
                if (dataSnapshot.child("foto").getValue(String.class).isEmpty()){
                    Glide.with(getApplicationContext()).load(R.drawable.ic_user).apply(RequestOptions.circleCropTransform()).into(profileImage);
                    fotoP = "user";
                }else {
                    fotoP = dataSnapshot.child("foto").getValue(String.class);
                    Glide.with(getApplicationContext()).load(fotoP).apply(RequestOptions.circleCropTransform()).into(profileImage);
                    fabAddPhoto.setIconResource(R.drawable.ic_editar);
                }

                nombre_d.setText(name);
                apellido_d.setText(lastname);
                calle_d.setText(street);
                col_d.setText(colonia);
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
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setRequestedSize(1024, 1024)
                        .setAspectRatio(3, 3).start(actualizar_datosd.this);

            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUri = result.getUri();
                //subirImagenFirebase(imageUri);
                Glide.with(this).load(imageUri).apply(RequestOptions.circleCropTransform()).into(profileImage);
                fabAddPhoto.setIconResource(R.drawable.ic_editar);
            }
        }
    }

    private void subirImagenFirebase(final DatabaseReference dueno) {
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
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isSuccessful());
                Uri downloadUri = uriTask.getResult();
                dueno.child("foto").setValue(downloadUri.toString(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        if (databaseError != null){
                            Toast.makeText(getApplicationContext(), "No se actualizar la foto de perfil", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Foto de perfil actualizada con éxito", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    private void ActualizarPerfil() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        //Referencia a la base de datos

        final DatabaseReference duenoReference = firebaseDatabase.getReference().child(FirebaseReferences.USERS_REFERENCE)
                .child(FirebaseReferences.DUENO_REFERENCE).child(user.getUid());

        if (imageUri != null){
            subirImagenFirebase(duenoReference);
        }
        duenoReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("nombre").getValue().toString();
                String lastname = dataSnapshot.child("apellidos").getValue().toString();
                String street = dataSnapshot.child("calle").getValue().toString();
                String colonia = dataSnapshot.child("colonia").getValue().toString();
                String tel = dataSnapshot.child("telefono").getValue().toString();
                String noi = dataSnapshot.child("noint").getValue().toString();
                String noe = dataSnapshot.child("noext").getValue(String.class);
                //   String alca = dataSnapshot.child("alcaldia").getValue(String.class);

                nombre_d.setText(name);
                apellido_d.setText(lastname);
                calle_d.setText(street);
                col_d.setText(colonia);
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
        String colonia = col_d.getText().toString();
        String noex = noext_d.getText().toString();
        String noin = noint_d.getText().toString();
        String alcal = alcaldia_d.getSelectedItem().toString();
        String tel = tel_d.getText().toString();

        //Validaciones
        String mensaje = "Debe seleccionar un campo válido";
        if (nombre.isEmpty() || apellido.isEmpty() || calle.isEmpty() || colonia.isEmpty()|| noex.isEmpty() || alcal.equals("Seleccionar")
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
            if(colonia.isEmpty()){
                col_d.setError("Obligatorio");
            }
            if (noex.isEmpty()) {
                noext_d.setError("Obligatorio");
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
            duenoReference.child("colonia").getRef().setValue(colonia);
            duenoReference.child("noext").getRef().setValue(noex);
            duenoReference.child("noint").getRef().setValue(noin);
            duenoReference.child("alcaldia").getRef().setValue(alcal);
            duenoReference.child("telefono").getRef().setValue(tel);
            onBackPressed();
        }
    }

    private void abrirGeleria(){
        //Abrir galeria
        Intent abrirgaleria = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(abrirgaleria, 1000);
    }

    private void getPermissionsReadStorage() {
        if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(this),
                android.Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED){
            abrirGeleria();
        } else {
            ActivityCompat.requestPermissions(Objects.requireNonNull(this),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}