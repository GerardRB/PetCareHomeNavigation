package com.example.petcarehome.homenavigation.ui.config.clases_fragmentos;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.Objetos.FirebaseReferences;
import com.example.petcarehome.homenavigation.ui.difusion.FullScreenImageActivity;
import com.example.petcarehome.homenavigation.ui.difusion.perdidas.GenerarReporteExtravioActivity;
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
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.lang.ref.ReferenceQueue;
import java.util.Objects;

public class actualizar_datosc extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText nombre_c, apellido_c, calle_c, noext_c, noint_c, tel_c;
    Button actualizarDatos;
    ImageView profileImage;
    ExtendedFloatingActionButton fabAddPhoto;
    FirebaseDatabase firebaseDatabase;
    Spinner alcaldia_c;
    FirebaseStorage firebaseStorage;
    FirebaseUser user;
    Uri imageUri;
    private String fotoP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_datosc);
        nombre_c = findViewById(R.id.nombre_actualizarc);
        apellido_c = findViewById(R.id.apellidos_actualizarc);
        calle_c = findViewById(R.id.calle_actualizarc);
        noext_c = findViewById(R.id.noext_actualizarc);
        noint_c = findViewById(R.id.noint_actualizarc);
        tel_c = findViewById(R.id.tel_actualizarc);

        imageUri = null;

        //Spiner de las alcaldías
        alcaldia_c = findViewById(R.id.delegacion_spinner_actualizarc);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.alcaldias, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        alcaldia_c.setAdapter(adapter);
        alcaldia_c.setOnItemSelectedListener(this);

        profileImage = findViewById(R.id.profile_imagec);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageUri != null){
                    fotoP = imageUri.toString();
                }
                Intent intent = new Intent(actualizar_datosc.this, FullScreenImageActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(actualizar_datosc.this, profileImage, Objects.requireNonNull(ViewCompat.getTransitionName(profileImage)));
                Bundle  bundle = new Bundle();
                bundle.putString("title", "perfil");
                bundle.putString("foto", fotoP);
                intent.putExtras(bundle);
                startActivity(intent, options.toBundle());
            }
        });

        fabAddPhoto = findViewById(R.id.fab_addphotoC);
        fabAddPhoto.setIconResource(R.drawable.ic_cameraadd);
        fabAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPermissionsReadStorage();
            }
        });

        actualizarDatos = findViewById(R.id.registrarse2_actualizarc);

        firebaseDatabase = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference cuidadorReference = firebaseDatabase.getReference().child(FirebaseReferences.USERS_REFERENCE)
                .child(FirebaseReferences.CUIDADOR_REFERENCE).child(user.getUid());

        cuidadorReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("nombre").getValue().toString();
                String lastname = dataSnapshot.child("apellidos").getValue().toString();
                String street = dataSnapshot.child("calle").getValue().toString();
                String tel = dataSnapshot.child("telefono").getValue().toString();
                String noi = dataSnapshot.child("noint").getValue().toString();
                String noe = dataSnapshot.child("noext").getValue(String.class);
                //fotop = dataSnapshot.child("foto").getValue(String.class);
                //   String alca = dataSnapshot.child("alcaldia").getValue(String.class);
                if (dataSnapshot.child("foto").getValue(String.class).isEmpty()){
                    Glide.with(getApplicationContext()).load(R.drawable.ic_user).apply(RequestOptions.circleCropTransform()).into(profileImage);
                    fotoP = "user";
                }else {
                    fotoP = dataSnapshot.child("foto").getValue(String.class);
                    Glide.with(getApplicationContext()).load(fotoP).apply(RequestOptions.circleCropTransform()).into(profileImage);
                    fabAddPhoto.setIconResource(R.drawable.ic_editar);
                }
                nombre_c.setText(name);
                apellido_c.setText(lastname);
                calle_c.setText(street);
                tel_c.setText(tel);
                noext_c.setText(noe);
                noint_c.setText(noi);

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
                        .setAspectRatio(3, 3).start(actualizar_datosc.this);
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

    private void subirImagenFirebase(final DatabaseReference cuidador){
        String idUser = null;
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            idUser = user.getUid();
        }
        StorageReference storagePerfilCuidadorReference =
                firebaseStorage.getInstance().getReference
                        (FirebaseReferences.STORAGE_IMAGENPERFIL_REFERENCE).
                        child(FirebaseReferences.STORAGE_IMAGENPERFILCUIDADOR_REFERENCE).child(idUser);

        final StorageReference fileRef = storagePerfilCuidadorReference.child("img" + idUser + ".jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isSuccessful());
                Uri downloadUri = uriTask.getResult();
                 cuidador.child("foto").setValue(downloadUri.toString(), new DatabaseReference.CompletionListener() {
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


        final DatabaseReference cuidadoReference = firebaseDatabase.getReference().child(FirebaseReferences.USERS_REFERENCE)
                .child(FirebaseReferences.CUIDADOR_REFERENCE).child(user.getUid());

        if (imageUri != null){
            subirImagenFirebase(cuidadoReference);
        }


        cuidadoReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("nombre").getValue().toString();
                String lastname = dataSnapshot.child("apellidos").getValue().toString();
                String street = dataSnapshot.child("correo").getValue().toString();
                String tel = dataSnapshot.child("telefono").getValue().toString();
                String noi = dataSnapshot.child("noint").getValue().toString();
                String noe = dataSnapshot.child("noext").getValue(String.class);
                //   String alca = dataSnapshot.child("alcaldia").getValue(String.class);

                nombre_c.setText(name);
                apellido_c.setText(lastname);
                calle_c.setText(street);
                tel_c.setText(tel);
                noext_c.setText(noe);
                noint_c.setText(noi);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        String nombre = nombre_c.getText().toString();
        String apellido = apellido_c.getText().toString();
        String calle = calle_c.getText().toString();
        String noex = noext_c.getText().toString();
        String noin = noint_c.getText().toString();
        String alcal = alcaldia_c.getSelectedItem().toString();
        String tel = tel_c.getText().toString();

        //Validaciones
        String mensaje = "Debe seleccionar un campo válido";
        if (nombre.isEmpty() || apellido.isEmpty() || calle.isEmpty() || noex.isEmpty()  || alcal.equals("Seleccionar")
                || tel.isEmpty()) {
            if (nombre.isEmpty()) {
                nombre_c.setError("Obligatorio");
            }
            if (apellido.isEmpty()) {
                apellido_c.setError("Obligatorio");
            }
            if (calle.isEmpty()) {
                calle_c.setError("Obligatorio");
            }
            if (noex.isEmpty()) {
                noext_c.setError("Obligatorio");
            }

            if (alcal.equals("Seleccionar")) {
                mensaje += "\nSeleccione una alcaldía";
            }
            if (tel.isEmpty()) {
                tel_c.setError("Obligatorio");
            }
            Toast.makeText(this, "Debe llenar los campos obligatorios", Toast.LENGTH_SHORT).show();
        } else {
            cuidadoReference.child("nombre").getRef().setValue(nombre);
            cuidadoReference.child("apellidos").getRef().setValue(apellido);
            cuidadoReference.child("calle").getRef().setValue(calle);
            cuidadoReference.child("noext").getRef().setValue(noex);
            cuidadoReference.child("noint").getRef().setValue(noin);
            cuidadoReference.child("alcaldia").getRef().setValue(alcal);
            cuidadoReference.child("telefono").getRef().setValue(tel);
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