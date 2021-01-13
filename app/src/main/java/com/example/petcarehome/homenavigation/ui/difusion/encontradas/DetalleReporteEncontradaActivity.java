package com.example.petcarehome.homenavigation.ui.difusion.encontradas;

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
import com.example.petcarehome.homenavigation.Objetos.ReporteEncontradas;
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

public class DetalleReporteEncontradaActivity extends AppCompatActivity implements View.OnClickListener{

    private ReporteEncontradas reporteE;
    private ImageView foto, fotoUser;
    private TextView nombreUser, telefonoUser, correoUser;
    private String nom, corr, tel, fotoUsr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_reporte_encontrada);

        //Recibir reporte Seleccionado
        Bundle reporteSeleccionado = getIntent().getExtras();
        reporteE = null;
        boolean general = true;
        if (reporteSeleccionado != null){
            reporteE = (ReporteEncontradas) reporteSeleccionado.getSerializable("reporteEncontrada");
            general = reporteSeleccionado.getBoolean("general");
        }


        //Referencia a textviews
        TextView tipoMascota = findViewById(R.id.text_tipo_DRME);
        TextView fechaEncontrada = findViewById(R.id.text_fecha_encontrada_DRME);
        TextView horaEncontrada = findViewById(R.id.text_hora_encontrada_DRME);
        TextView alcaldia = findViewById(R.id.text_alcaldia_DRME);
        TextView colonia = findViewById(R.id.text_colonia_DRME);
        TextView calle = findViewById(R.id.text_calle_DRME);
        TextView descripcion = findViewById(R.id.text_descricpion_DRME);
        foto = findViewById(R.id.id_imageDRME);
        fotoUser = findViewById(R.id.id_imagen_user_DRME);
        correoUser = findViewById(R.id.text_id_user_DRME);
        //foto = findViewById(R.id.id_imageDRME);
        nombreUser = findViewById(R.id.text_nombre_user_DRME);
        telefonoUser = findViewById(R.id.text_telefono_user_DRME);
        Button btnEliminar = findViewById(R.id.id_btn_eliminar_reportee);
        CardView cardUser = findViewById(R.id.card_user_re);
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
                    if (idUser.equals(reporteE.getIdUser())){
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
                        if (idUser.equals(reporteE.getIdUser())){
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
        }




        //Imprimir valores del objeto recibido
        tipoMascota.setText(reporteE.getTipo());
        fechaEncontrada.setText(reporteE.getFecha());
        horaEncontrada.setText(reporteE.getHora());
        alcaldia.setText(reporteE.getAlcaldia());
        colonia.setText(reporteE.getColonia());
        calle.setText(reporteE.getCalle());
        descripcion.setText(reporteE.getDescripcion());
        Glide.with(this).load(reporteE.getFoto()).apply(RequestOptions.circleCropTransform()).into(foto);
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
            case R.id.id_imageDRME:
                Intent intent = new Intent(DetalleReporteEncontradaActivity.this, FullScreenImageActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(DetalleReporteEncontradaActivity.this, foto, Objects.requireNonNull(ViewCompat.getTransitionName(foto)));
                Bundle  bundle = new Bundle();
                bundle.putString("title", "la mascota");
                bundle.putString("foto", reporteE.getFoto());
                intent.putExtras(bundle);
                startActivity(intent, options.toBundle());
                break;
            case R.id.id_imagen_user_DRME:
                Intent intentUser = new Intent(DetalleReporteEncontradaActivity.this, FullScreenImageActivity.class);
                ActivityOptionsCompat optionsUser = ActivityOptionsCompat.makeSceneTransitionAnimation(DetalleReporteEncontradaActivity.this, fotoUser, Objects.requireNonNull(ViewCompat.getTransitionName(fotoUser)));
                Bundle  bundleUser = new Bundle();
                bundleUser.putString("title", nom);
                bundleUser.putString("foto", fotoUsr);
                intentUser.putExtras(bundleUser);
                startActivity(intentUser, optionsUser.toBundle());
               break;
            case R.id.id_btn_eliminar_reportee:
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
        DatabaseReference reporteRef = FirebaseDatabase.getInstance().getReference().child(FirebaseReferences.REPORTES_REFERENCE).child(FirebaseReferences.REPORTEENCONTRADA_REFERENCE).child(reporteE.getIdRep());
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
                                StorageReference imgRef = FirebaseStorage.getInstance().getReference().child(FirebaseReferences.STORAGE_REPORTES_REFERENCE).child(FirebaseReferences.STORAGE_REPORTEENCONTRADA_REFERENCE).child(reporteE.getIdUser()).child("img" + reporteE.getIdRep() + ".jpg");
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
