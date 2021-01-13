package com.example.petcarehome.homenavigation.ui.difusion.perdidas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.petcarehome.homenavigation.Objetos.FirebaseReferences;
import com.example.petcarehome.homenavigation.Objetos.ReportePerdidas;
import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.ui.difusion.FullScreenImageActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class DetalleReportePerdidasActivity extends AppCompatActivity implements View.OnClickListener{

    private ReportePerdidas reporteP;
    private TextView correoUser, nombreUser, telefonoUser;
    private ImageView foto, fotoUser;
    private String fotoUsr, nom, corr, tel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_reporte_perdidas);

        //Recibir reporte Seleccionado
        Bundle reporteSeleccionado = getIntent().getExtras();
        reporteP = null;
        boolean general = true;
        if (reporteSeleccionado != null){
            reporteP = (ReportePerdidas) reporteSeleccionado.getSerializable("reportePerdida");
            general = reporteSeleccionado.getBoolean("general");
        }

        //fotoUsr = "";

        //Referencia a textviews
        TextView nombreMascota = findViewById(R.id.text_nombre_DRMP);
        TextView tipoMascota = findViewById(R.id.text_tipo_DRMP);
        TextView edad = findViewById(R.id.text_edad_DRMP);
        TextView fechaExtravio = findViewById(R.id.text_fecha_extravio_DRMP);
        TextView horaExtravio = findViewById(R.id.text_hora_Extravio_DRMP);
        TextView alcaldia = findViewById(R.id.text_alcaldia_DRMP);
        TextView colonia = findViewById(R.id.text_colonia_DRMP);
        TextView calle = findViewById(R.id.text_calle_DRMP);
        TextView descripcion = findViewById(R.id.text_descricpion_DRMP);
        foto = findViewById(R.id.id_imageDRMP);
        fotoUser = findViewById(R.id.id_imagen_user_DRMP);
        correoUser = findViewById(R.id.text_email_user_DRMP);
        nombreUser = findViewById(R.id.text_nombre_user_DRMP);
        telefonoUser = findViewById(R.id.text_telefono_user_DRMP);
        Button btnEliminar = findViewById(R.id.id_btn_eliminar_reportep);
        CardView cardUser = findViewById(R.id.card_user_rp);
        if (!general){
            cardUser.setVisibility(View.GONE);
            btnEliminar.setVisibility(View.VISIBLE);
            btnEliminar.setOnClickListener(this);
        }


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference duenoRef = firebaseDatabase.getReference().child(FirebaseReferences.USERS_REFERENCE).child(FirebaseReferences.DUENO_REFERENCE);
        final DatabaseReference cuidadorRef = firebaseDatabase.getReference().child(FirebaseReferences.USERS_REFERENCE).child(FirebaseReferences.CUIDADOR_REFERENCE);



        cuidadorRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot snapshot:
                        dataSnapshot.getChildren()) {
                    String idUser = snapshot.getKey();
                    if (idUser.equals(reporteP.getIdUser())){
                        //Cuidador cuidador = snapshot.getValue(Cuidador.class);
                        nom = snapshot.child("nombre").getValue(String.class) + " " + snapshot.child("apellidos").getValue(String.class);
                        corr = snapshot.child("correo").getValue(String.class);
                        tel = snapshot.child("telefono").getValue(String.class);
                        if (snapshot.child("foto").getValue(String.class).isEmpty()){
                            Glide.with(getApplicationContext()).load(R.drawable.ic_user).apply(RequestOptions.circleCropTransform()).into(fotoUser);
                            fotoUsr = "user";
                        } else {
                            fotoUsr = snapshot.child("foto").getValue(String.class);
                            Glide.with(getApplicationContext()).load(fotoUsr).apply(RequestOptions.circleCropTransform()).into(fotoUser);
                        }
                        nombreUser.setText(nom);
                        correoUser.setText(corr);
                        telefonoUser.setText(tel);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        String correoU = correoUser.getText().toString();

        if (correoU.isEmpty()){
            duenoRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    for (DataSnapshot snapshot:
                            dataSnapshot.getChildren()) {
                        String idUser = snapshot.getKey();
                        if (idUser.equals(reporteP.getIdUser())){
                            nom = snapshot.child("nombre").getValue(String.class) + " " + snapshot.child("apellidos").getValue(String.class);
                            corr = snapshot.child("correo").getValue(String.class);
                            tel = snapshot.child("telefono").getValue(String.class);
                            if (snapshot.child("foto").getValue(String.class).isEmpty()){
                                Glide.with(getApplicationContext()).load(R.drawable.ic_user).apply(RequestOptions.circleCropTransform()).into(fotoUser);
                                fotoUsr = "user";
                            } else {
                                fotoUsr = snapshot.child("foto").getValue(String.class);
                                Glide.with(getApplicationContext()).load(snapshot.child(fotoUsr)).apply(RequestOptions.circleCropTransform()).into(fotoUser);
                            }

                            nombreUser.setText(nom);
                            correoUser.setText(corr);
                            telefonoUser.setText(tel);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        //Imprimir valores del objeto recibido
        nombreMascota.setText(reporteP.getNombre());
        tipoMascota.setText(reporteP.getTipo());
        edad.setText(reporteP.getEdad());
        fechaExtravio.setText(reporteP.getFecha());
        horaExtravio.setText(reporteP.getHora());
        alcaldia.setText(reporteP.getAlcaldia());
        colonia.setText(reporteP.getColonia());
        calle.setText(reporteP.getCalle());
        descripcion.setText(reporteP.getDescripcion());
        Glide.with(this).load(reporteP.getFoto()).apply(RequestOptions.circleCropTransform()).into(foto);
        foto.setOnClickListener(this);

        fotoUser.setOnClickListener(this);



    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_imageDRMP:
                Intent intent = new Intent(DetalleReportePerdidasActivity.this, FullScreenImageActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(DetalleReportePerdidasActivity.this, foto, Objects.requireNonNull(ViewCompat.getTransitionName(foto)));
                Bundle  bundle = new Bundle();
                bundle.putString("title", reporteP.getNombre());
                bundle.putString("foto", reporteP.getFoto());
                intent.putExtras(bundle);
                startActivity(intent, options.toBundle());
                break;
            case R.id.id_imagen_user_DRMP:
                Intent intentUser = new Intent(DetalleReportePerdidasActivity.this, FullScreenImageActivity.class);
                ActivityOptionsCompat optionsUser = ActivityOptionsCompat.makeSceneTransitionAnimation(DetalleReportePerdidasActivity.this, fotoUser, Objects.requireNonNull(ViewCompat.getTransitionName(fotoUser)));
                Bundle  bundleUser = new Bundle();
                bundleUser.putString("title", nom);
                bundleUser.putString("foto", fotoUsr);
                intentUser.putExtras(bundleUser);
                startActivity(intentUser, optionsUser.toBundle());
                break;
            case R.id.id_btn_eliminar_reportep:
                openDialogEliminar();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }

    private void openDialogEliminar() {
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("Elimiar reporte")
                .setMessage("Al eliminar el reporte, este ya no aparecerá en los resultados de difusión.\n¿Desea eliminar el reporte?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eliminarReporte();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alerta.show();
    }

    private void eliminarReporte() {
        DatabaseReference reporteRef = FirebaseDatabase.getInstance().getReference().child(FirebaseReferences.REPORTES_REFERENCE).child(FirebaseReferences.REPORTEPERDIDA_REFERENCE).child(reporteP.getIdRep());
        reporteRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    dataSnapshot.getRef().removeValue(new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            if (databaseError != null){
                                Toast.makeText(getApplicationContext(), "No se pudo eliminar el reporte", Toast.LENGTH_LONG).show();
                            } else {
                                StorageReference imgRef = FirebaseStorage.getInstance().getReference().child(FirebaseReferences.STORAGE_REPORTES_REFERENCE).child(FirebaseReferences.STORAGE_REPORTEPERDIDA_REFERENCE).child(reporteP.getIdUser()).child("img" + reporteP.getIdRep() + ".jpg");
                                imgRef.delete();
                                /*.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "Imagen eliminada con éxito", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al eliminar imagen", Toast.LENGTH_LONG).show();
            }
        });*/
                                Toast.makeText(getApplicationContext(), "Reporte eliminado con éxito", Toast.LENGTH_LONG).show();
                                onBackPressed();
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
