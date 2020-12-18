package com.example.petcarehome.homenavigation.ui.difusion.adopcion;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.petcarehome.homenavigation.Objetos.FirebaseReferences;
import com.example.petcarehome.homenavigation.Objetos.ReporteAdopcion;
import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.Objetos.ReporteEncontradas;
import com.example.petcarehome.homenavigation.ui.difusion.encontradas.GenerarReporteEncontradaActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;

public class GenerarReporteAdopcionActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int COD_SELECCIONA = 10;
    //private static final int COD_SELECCIONA = 10;

    private Spinner comboAlcaldias, comboTipoMascota, comboVacunas, comboEsterilizacion;
    private EditText raza, edad, colonia, calle, descripcion;
    private ImageView imageView;
    private Button button;
    private FirebaseDatabase firebaseDatabase;

    private Uri resultUri;
    private ArrayList<Uri> listImagesRec = new ArrayList<Uri>();
    private FirebaseStorage firebaseStorage;
    private Uri downloadUri;
    private ArrayList<String> listDwonloadUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generar_reporte_adopcion);

        //Referencia a editTexts
        raza = findViewById(R.id.id_input_razaRMA);
        edad = findViewById(R.id.id_input_edadRMA);
        colonia = findViewById(R.id.id_input_coloniaRMA);
        calle = findViewById(R.id.id_input_calleRMA);
        descripcion = findViewById(R.id.id_input_descripcionRMA);

        //Referencia al componente ImageView en xml
        imageView = findViewById(R.id.id_input_imageRMA);
        imageView.setOnClickListener(this);

        //Referencia al Boton generar reporte en xml
        button = findViewById(R.id.id_btn_generarRMA);
        button.setOnClickListener(this);

        //spinner de alcaldias
        comboAlcaldias = findViewById(R.id.spinner_alcaldiaRMA);
        ArrayAdapter<CharSequence> adapterAlcaldia = ArrayAdapter.createFromResource(this, R.array.combo_alcaldia, android.R.layout.simple_spinner_item);
        comboAlcaldias.setAdapter(adapterAlcaldia);

        //spinner de tipo de mascota
        comboTipoMascota = findViewById(R.id.spinner_tipoRMA);
        ArrayAdapter<CharSequence> adapterTipoMascota = ArrayAdapter.createFromResource(this, R.array.combo_tipoRMP, android.R.layout.simple_spinner_item);
        comboTipoMascota.setAdapter(adapterTipoMascota);

        //spinner de vacunas
        comboVacunas = findViewById(R.id.spinner_vacunasRMA);
        ArrayAdapter<CharSequence> adapterVacunas = ArrayAdapter.createFromResource(this, R.array.combo_sino, android.R.layout.simple_spinner_item);
        comboVacunas.setAdapter(adapterVacunas);

        //spinner de esterilizacion
        comboEsterilizacion = findViewById(R.id.spinner_esterilizacionRMA);
        ArrayAdapter<CharSequence> adapterEsterilizacion = ArrayAdapter.createFromResource(this, R.array.combo_sino, android.R.layout.simple_spinner_item);
        comboEsterilizacion.setAdapter(adapterEsterilizacion);

    }

    //Acceso a la galería
    private void cargarImagen() {
        Intent intent = new Intent();
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 10);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == Activity.RESULT_OK){

            if (data.getClipData() != null){
                for (int i = 0; i < data.getClipData().getItemCount(); i++){
                    CropImage.activity(data.getClipData().getItemAt(i).getUri())
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .setRequestedSize(1024, 1024)
                            .setAspectRatio(3,3).start(GenerarReporteAdopcionActivity.this);
                }

            } else {
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setRequestedSize(1024, 1024)
                        .setAspectRatio(3,3).start(GenerarReporteAdopcionActivity.this);
            }

            /*
            //Uri imageUri = CropImage.getPickImageResultUri(this, data);
            Uri imageUri = data.getData();
            //Recortar imagen
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setRequestedSize(1024, 1024)
                    .setAspectRatio(3,3).start(GenerarReporteExtravioActivity.this);
            //imageView.setImageURI(imageUri);*/
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK){
                resultUri = result.getUri();
                listImagesRec.add(resultUri);
                imageView.setImageURI(listImagesRec.get(0));
                //Toast.makeText(getApplicationContext(), "Fotos seleccionadas: " + listImagesRec.size(), Toast.LENGTH_LONG).show();
            }
        }
    }

    //Regresar a la actividad anterior con la flecha de action bar(por defecto)
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    //Eventos Onclick dentro de la actividad
    @Override
    public void onClick(View v) {
        if (v.getId()==button.getId()){
            validarCampos();
        }
        if(v.getId()==imageView.getId()){
            cargarImagen();
        }
    }


    public String SubirFoto(String idUser, String idRep, int noFoto){
        final StorageReference storageReportesReference = firebaseStorage.getInstance().getReference(FirebaseReferences.STORAGE_REPORTES_REFERENCE).child(FirebaseReferences.STORAGE_REPORTEADOPCION_REFERENCE).child(idUser).child("img" + idRep + noFoto + ".jpg");
        //final Uri[] downloadUri = new Uri[1];
        final UploadTask subeFoto = (UploadTask) storageReportesReference.putFile(listImagesRec.get(noFoto))
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful());
                        downloadUri = uriTask.getResult();
                        Toast.makeText(getApplicationContext(), "Foto subida URL:" + downloadUri.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        //Toast.makeText(getApplicationContext(), "Foto subida URL:" + downloadUri[1].toString(), Toast.LENGTH_LONG).show();



        String stringUrl = "Url " + noFoto;
        return stringUrl;
    }

    private void validarCampos() {
        final String tipoM, razaM, edadM, vacunas, esterilizacion, alcaldiaA, coloniaA, calleA, descripcionA, idRep;
        String mensaje = "Faltan campos por ingresar";
        String idUser = null;

        //Obtener valores ingresados
        tipoM = comboTipoMascota.getSelectedItem().toString();
        razaM = raza.getText().toString();
        edadM = edad.getText().toString();
        vacunas = comboVacunas.getSelectedItem().toString();
        esterilizacion = comboEsterilizacion.getSelectedItem().toString();
        alcaldiaA = comboAlcaldias.getSelectedItem().toString();
        coloniaA = colonia.getText().toString();
        calleA = calle.getText().toString();
        descripcionA = descripcion.getText().toString();

        //validacion
        if(tipoM.equals("Seleccionar") || razaM.isEmpty() || edadM.isEmpty() || vacunas.equals("Seleccionar") || esterilizacion.equals("Seleccionar") || alcaldiaA.equals("Seleccionar") || descripcionA.isEmpty()){
            if (tipoM.equals("Seleccionar"))
                mensaje += "\nSeleccione un tipo de mascota";
            if (razaM.isEmpty())
                raza.setError("Obligatorio");
            if (edadM.isEmpty())
                edad.setError("Obligatorio");
            if (vacunas.equals("Seleccionar"))
                mensaje += "\nSeleccione si o no está vacunada la mascota";
            if (esterilizacion.equals("Seleccionar"))
                mensaje += "\nSeleccione si o no está esterilizada la mascota";
            if (alcaldiaA.equals("Seleccionar"))
                mensaje += "\nSeleccione una alcaldía";
            if (descripcionA.isEmpty())
                descripcion.setError("Obligatorio");
            Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
        } else {

            //Referencia al Storage de reportes
            firebaseStorage = FirebaseStorage.getInstance();
            firebaseDatabase = FirebaseDatabase.getInstance();
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null){
                idUser = user.getUid();
            }
            final DatabaseReference reportesPReference = firebaseDatabase.getReference(FirebaseReferences.REPORTES_REFERENCE).child(FirebaseReferences.REPORTEADOPCION_REFERENCE).push();
            idRep = reportesPReference.getKey();


            //Bien una foto
            //final String finalIdUser = idUser;
            final StorageReference storageReportesReference = firebaseStorage.getInstance().getReference(FirebaseReferences.STORAGE_REPORTES_REFERENCE).child(FirebaseReferences.STORAGE_REPORTEADOPCION_REFERENCE).child(idUser).child("img" + idRep  + ".jpg");
            storageReportesReference.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful());
                    downloadUri = uriTask.getResult();
                    String idUserf = user.getUid();
                    ReporteAdopcion reporteA = new ReporteAdopcion(tipoM, razaM, edadM, vacunas, esterilizacion, alcaldiaA, coloniaA, calleA, descripcionA, downloadUri.toString(), idUserf);
                    //Guardar en base de datos


                    reportesPReference.setValue(reporteA, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            if (databaseError != null){
                                Toast.makeText(getApplicationContext(), "No se pudo generar el reporte", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Reporte generado con éxito", Toast.LENGTH_LONG).show();
                                onBackPressed();
                            }
                        }
                    });
                }
            });//Fin una foto

        }



    }

}
