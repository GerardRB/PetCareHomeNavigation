package com.example.petcarehome.homenavigation.ui.difusion.perdidas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

import java.util.Objects;

public class DetalleReportePerdidasActivity extends AppCompatActivity implements View.OnClickListener{

    private ReportePerdidas reporteP;
    private TextView correoUser, nombreUser, telefonoUser, domicilio;
    private ImageView foto, fotoUser;
    private String fotoUsr, nom, corr, tel, dir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_reporte_perdidas);

        //Recibir reporte Seleccionado
        Bundle reporteSeleccionado = getIntent().getExtras();
        reporteP = null;
        if (reporteSeleccionado != null){
            reporteP = (ReportePerdidas) reporteSeleccionado.getSerializable("reportePerdida");
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
        domicilio = findViewById(R.id.text_domicilio_user_DRMP);


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference duenoRef = firebaseDatabase.getReference().child(FirebaseReferences.USERS_REFERENCE).child(FirebaseReferences.DUENO_REFERENCE);
        final DatabaseReference cuidadorRef = firebaseDatabase.getReference().child(FirebaseReferences.USERS_REFERENCE).child(FirebaseReferences.CUIDADOR_REFERENCE);



        cuidadorRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot snapshot:
                        dataSnapshot.getChildren()) {
                    String idUser = snapshot.getKey();
                    if (idUser.equals(reporteP.getUsuario())){
                        //Cuidador cuidador = snapshot.getValue(Cuidador.class);
                        nom = snapshot.child("nombre").getValue(String.class) + " " + snapshot.child("apellidos").getValue(String.class);
                        corr = snapshot.child("correo").getValue(String.class);
                        tel = snapshot.child("telefono").getValue(String.class);
                        dir = snapshot.child("calle").getValue(String.class) + ""
                                + snapshot.child("noext").getValue(String.class) + ""
                                + snapshot.child("noint").getValue(String.class) + ", "
                                + snapshot.child("alcaldia").getValue(String.class);
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
                        domicilio.setText(dir);
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
                        if (idUser.equals(reporteP.getUsuario())){
                            nom = snapshot.child("nombre").getValue(String.class) + " " + snapshot.child("apellidos").getValue(String.class);
                            corr = snapshot.child("correo").getValue(String.class);
                            tel = snapshot.child("telefono").getValue(String.class);
                            dir = snapshot.child("calle").getValue(String.class) + ""
                                    + snapshot.child("noext").getValue(String.class) + ""
                                    + snapshot.child("noint").getValue(String.class) + ", "
                                    + snapshot.child("alcaldia").getValue(String.class);
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
                            domicilio.setText(dir);
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
                bundle.putString("title", "la mascota");
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
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }
}
