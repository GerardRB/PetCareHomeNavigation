package com.example.petcarehome.homenavigation.ui.difusion.perdidas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.petcarehome.homenavigation.Objetos.FirebaseReferences;
import com.example.petcarehome.homenavigation.Objetos.ReportePerdidas;
import com.example.petcarehome.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class GenerarReporteExtravioActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int COD_SELECCIONA = 10;
    //private static final int COD_SELECCIONA = 10;

    private Spinner comboAlcaldias, comboTipoMascota;
    private EditText fecha, hora, nombre, edad, colonia, calle, descripcion;
    private DatePickerDialog.OnDateSetListener fechaSetListener;
    private TimePickerDialog.OnTimeSetListener horaSetListener;
    private ImageView imageView;
    private Button buttonGenerar;
    private ArrayList<ReportePerdidas> listReportes;

    private Bitmap thumbBitmap;
    private byte [] thumb_byte;
    private Uri downloadUri;
    private Uri resultUri;
    private String aleatorio;
    private ArrayList<Uri> listImagesRec = new ArrayList<Uri>();;
    private ArrayList<String> listDwonloadUri;
    //private List fotosList;


    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generar_reporte_extravio);




        //Referencia a elementos
        nombre = findViewById(R.id.id_input_nombreRMP);
        edad = findViewById(R.id.id_input_edadRMP);
        colonia = findViewById(R.id.id_input_coloniaRMP);
        calle = findViewById(R.id.id_input_calleRMP);
        descripcion = findViewById(R.id.id_input_descripcionRMP);

        //Referencia al componente ImageView en xml
        imageView = findViewById(R.id.id_input_imageRMP);
        imageView.setOnClickListener(this);

        //Referencia al Boton generar reporte en xml
        buttonGenerar = findViewById(R.id.id_btn_generarRMP);
        buttonGenerar.setOnClickListener(this);

        //spinner de alcaldias
        comboAlcaldias = findViewById(R.id.spinner_alcaldiaRMP);
        ArrayAdapter<CharSequence> adapterAlcaldia = ArrayAdapter.createFromResource(this, R.array.combo_alcaldia, android.R.layout.simple_spinner_item);
        comboAlcaldias.setAdapter(adapterAlcaldia);

        //spinner de tipo de mascota
        comboTipoMascota = findViewById(R.id.spinner_tipoRMP);
        ArrayAdapter<CharSequence> adapterTipoMascota = ArrayAdapter.createFromResource(this, R.array.combo_tipoRMP, android.R.layout.simple_spinner_item);
        comboTipoMascota.setAdapter(adapterTipoMascota);


        //fecha con dialogo date picker
        fecha = (EditText) findViewById(R.id.id_input_fechaRMP);
        fecha.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    Calendar cal = Calendar.getInstance();
                    int myear = cal.get(Calendar.YEAR);
                    int mmonth = cal.get(Calendar.MONTH);
                    int mday = cal.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dialog = new DatePickerDialog(GenerarReporteExtravioActivity.this,
                            android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                            fechaSetListener, myear, mmonth, mday);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();

                }
            }
        });

        //Obtener fecha del dialogo y escribirla en el editText
        fechaSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String mes, date;
                month = month +1;
                switch (month){
                    case 1:
                        mes = "ENE";
                        break;
                    case 2:
                        mes = "FEB";
                        break;
                    case 3:
                        mes = "MAR";
                        break;
                    case 4:
                        mes = "ABR";
                        break;
                    case 5:
                        mes = "MAY";
                        break;
                    case 6:
                        mes = "JUN";
                        break;
                    case 7:
                        mes = "JUL";
                        break;
                    case 8:
                        mes = "AGO";
                        break;
                    case 9:
                        mes = "SEP";
                        break;
                    case 10:
                        mes = "OCT";
                        break;
                    case 11:
                        mes = "NOV";
                        break;
                    case 12:
                        mes = "DIC";
                        break;
                    default:
                        mes = " ";
                }
                if (dayOfMonth <= 9){
                    date = "0" + dayOfMonth + "/" + mes +  "/" + year;
                } else {
                    date = dayOfMonth +  "/" + mes +  "/" + year;
                }
                fecha.setText(date);

            }
        };


        //hora con dialogo time picker
        hora = (EditText) findViewById(R.id.id_input_horaRMP);
        hora.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    Calendar cal = Calendar.getInstance();
                    int mhour = cal.get(Calendar.HOUR);
                    int mminute = cal.get(Calendar.MINUTE);

                    TimePickerDialog dialog = new TimePickerDialog(GenerarReporteExtravioActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, horaSetListener, mhour, mminute, android.text.format.DateFormat.is24HourFormat(GenerarReporteExtravioActivity.this));
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();

                }
            }
        });
        //Obtener la hora, darle fomrato 12hrs am-pm y mostrarla en el editText
        horaSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String ampm, horas, minuto, datetime;
                if (hourOfDay < 12) {
                    ampm = " am";
                }
                else{
                    ampm = " pm";
                }
                if (minute <= 9){
                    minuto = ":0" + minute;
                }
                else{
                    minuto = ":" + minute;
                }

                switch (hourOfDay){
                    case 0:
                        horas = "12";
                        break;
                    case 1:
                        horas = "01";
                        break;
                    case 2:
                        horas = "02";
                        break;
                    case 3:
                        horas = "03";
                        break;
                    case 4:
                        horas = "04";
                        break;
                    case 5:
                        horas = "05";
                        break;
                    case 6:
                        horas = "06";
                        break;
                    case 7:
                        horas = "07";
                        break;
                    case 8:
                        horas = "08";
                        break;
                    case 9:
                        horas = "09";
                        break;
                    case 10:
                        horas = "10";
                        break;
                    case 11:
                        horas = "11";
                        break;
                    case 12:
                        horas = "12";
                        break;
                    case 13:
                        horas = "01";
                        break;
                    case 14:
                        horas = "02";
                        break;
                    case 15:
                        horas = "03";
                        break;
                    case 16:
                        horas = "04";
                        break;
                    case 17:
                        horas = "05";
                        break;
                    case 18:
                        horas = "06";
                        break;
                    case 19:
                        horas = "07";
                        break;
                    case 20:
                        horas = "08";
                        break;
                    case 21:
                        horas = "09";
                        break;
                    case 22:
                        horas = "10";
                        break;
                    case 23:
                        horas = "11";
                        break;
                    default:
                        horas = " ";
                        break;
                }

                datetime = horas + minuto + ampm;
                hora.setText(datetime);


            }
        };
    }



    //Acceso a la galería
    private void cargarImagen() {
        /*Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Seleccione la Aplicación"), 10);*/



        Intent intent = new Intent();
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 10);

        //CropImage.startPickImageActivity(GenerarReporteExtravioActivity.this);

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
                            .setAspectRatio(3,3).start(GenerarReporteExtravioActivity.this);
                }

            } else {
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setRequestedSize(1024, 1024)
                        .setAspectRatio(3,3).start(GenerarReporteExtravioActivity.this);
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
        switch(v.getId()){
            case R.id.id_btn_generarRMP:
                //Toast.makeText(getApplicationContext(), "Reporte generado exitosamente", Toast.LENGTH_LONG).show();
                ValidarCampos();
                break;
            case R.id.id_input_imageRMP:
                cargarImagen();
                break;
        }


    }


    public String SubirFoto(String idUser, String idRep, int noFoto){
        final StorageReference storageReportesReference = firebaseStorage.getInstance().getReference(FirebaseReferences.STORAGE_REPORTES_REFERENCE).child(FirebaseReferences.STORAGE_REPORTEPERDIDA_REFERENCE).child(idUser).child("img" + idRep + noFoto + ".jpg");
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

    public void ValidarCampos() {
        final String nombreM, tipoM, edadM, fechaE, horaE, alcaldiaE, coloniaE, calleE, descripcionE, idRep;
        listDwonloadUri = new ArrayList<String>();
        String mensaje = "Faltan campos por ingresar";
        String idUser = null;
        nombreM = nombre.getText().toString();
        tipoM = comboTipoMascota.getSelectedItem().toString();
        edadM = edad.getText().toString();
        fechaE = fecha.getText().toString();
        horaE = hora.getText().toString();
        alcaldiaE = comboAlcaldias.getSelectedItem().toString();
        coloniaE = colonia.getText().toString();
        calleE = calle.getText().toString();
        descripcionE = descripcion.getText().toString();


        if(nombreM.isEmpty() || tipoM.equals("Seleccionar") || edadM.isEmpty() || fechaE.isEmpty() || horaE.isEmpty() || alcaldiaE.equals("Seleccionar") || descripcionE.isEmpty()){
            if(nombreM.isEmpty())
                nombre.setError("Obligatorio");
            if (tipoM.equals("Seleccionar"))
                mensaje += "\nSeleccione un tipo de mascota";
            if (edadM.isEmpty())
                edad.setError("Obligatorio");
            if (fechaE.isEmpty())
                fecha.setError("Obligatorio");
            if (horaE.isEmpty())
                hora.setError("Obligatorio");
            if (alcaldiaE.equals("Seleccionar"))
                mensaje += "\nSeleccione una alcaldía";
            if (descripcionE.isEmpty())
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
            final DatabaseReference reportesPReference = firebaseDatabase.getReference(FirebaseReferences.REPORTES_REFERENCE).child(FirebaseReferences.REPORTEPERDIDA_REFERENCE).push();
            idRep = reportesPReference.getKey();

            /*
            //Varias fotos(error: no sube al realtime el atributo list String fotos
            //Subir las fotos al Storage y guardar su url en listDownloaduri
            for (int i = 0; i < listImagesRec.size(); i++){
                String downUri = SubirFoto(idUser, idRep, i);
                listDwonloadUri.add(downUri);
                //Toast.makeText(getApplicationContext(), "Foto subida URL:" + downUri, Toast.LENGTH_LONG).show();
            }


            //Convertir el array en list y creacion del reporte
            List<String> fotosList = new ArrayList<String>(Arrays.<String>asList(String.valueOf(listDwonloadUri)));
            String idUserf = user.getEmail();
            ReportePerdidas reporteP = new ReportePerdidas(nombreM, tipoM, edadM, fechaE, horaE, alcaldiaE, coloniaE, calleE, descripcionE, idUserf, fotosList);


            //Subir reporte al realtime database
            firebaseDatabase = FirebaseDatabase.getInstance();
            final DatabaseReference reportesReference = firebaseDatabase.getReference(FirebaseReferences.REPORTES_REFERENCE).child(idRep);
            reportesReference.setValue(reporteP, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    if (databaseError != null){
                        Toast.makeText(getApplicationContext(), "No se pudo generar el reporte", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Reporte generado con éxito", Toast.LENGTH_LONG).show();
                        onBackPressed();
                    }
                }
            });//Fin varias Fotos

             */




            //Bien una foto
            //final String finalIdUser = idUser;
            final StorageReference storageReportesReference = firebaseStorage.getInstance().getReference(FirebaseReferences.STORAGE_REPORTES_REFERENCE).child(FirebaseReferences.STORAGE_REPORTEPERDIDA_REFERENCE).child(idUser).child("img" + idRep  + ".jpg");
            storageReportesReference.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful());
                    downloadUri = uriTask.getResult();
                    String idUserf = user.getUid();
                    ReportePerdidas reporteP = new ReportePerdidas(nombreM, tipoM, edadM, fechaE, horaE, alcaldiaE, coloniaE, calleE, descripcionE, downloadUri.toString(), idUserf);
                    //Guardar en base de datos


                    reportesPReference.setValue(reporteP, new DatabaseReference.CompletionListener() {
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



        }//fin else del if  de datos completos



    }//fin validar campos
}



