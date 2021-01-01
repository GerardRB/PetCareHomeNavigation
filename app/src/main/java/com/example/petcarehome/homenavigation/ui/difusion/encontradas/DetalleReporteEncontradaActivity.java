package com.example.petcarehome.homenavigation.ui.difusion.encontradas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.petcarehome.InicioYRegistro.Cuidador;
import com.example.petcarehome.InicioYRegistro.Dueno;
import com.example.petcarehome.homenavigation.Objetos.FirebaseReferences;
import com.example.petcarehome.homenavigation.Objetos.ReporteEncontradas;
import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.Objetos.ReporteEncontradasID;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetalleReporteEncontradaActivity extends AppCompatActivity {

    private ReporteEncontradasID reporteE;
    private TextView idReporte, tipoMascota, fechaEncontrada, horaEncontrada, alcaldia, colonia, calle, descripcion, correoUser, nombreUser, telefonoUser, domicilio;
    private ImageView foto;

    private String correoU;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_reporte_encontrada);

        //Recibir reporte Seleccionado
        Bundle reporteSeleccionado = getIntent().getExtras();
        reporteE = null;
        if (reporteSeleccionado != null){
            reporteE = (ReporteEncontradasID) reporteSeleccionado.getSerializable("reporteEncontrada");
        }

        //Referencia a textviews
        idReporte = findViewById(R.id.text_id_DRME);
        tipoMascota = findViewById(R.id.text_tipo_DRME);
        fechaEncontrada = findViewById(R.id.text_fecha_encontrada_DRME);
        horaEncontrada = findViewById(R.id.text_hora_encontrada_DRME);
        alcaldia = findViewById(R.id.text_alcaldia_DRME);
        colonia = findViewById(R.id.text_colonia_DRME);
        calle = findViewById(R.id.text_calle_DRME);
        descripcion = findViewById(R.id.text_descricpion_DRME);
        correoUser = findViewById(R.id.text_id_user_DRME);
        foto = findViewById(R.id.id_imageDRME);
        nombreUser = findViewById(R.id.text_nombre_user_DRME);
        telefonoUser = findViewById(R.id.text_telefono_user_DRME);
        domicilio = findViewById(R.id.text_domicilio_user_DRME);


        firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference duenoRef = firebaseDatabase.getReference().child(FirebaseReferences.USERS_REFERENCE).child(FirebaseReferences.DUENO_REFERENCE);
        final DatabaseReference cuidadorRef = firebaseDatabase.getReference().child(FirebaseReferences.USERS_REFERENCE).child(FirebaseReferences.CUIDADOR_REFERENCE);



        cuidadorRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot snapshot:
                        dataSnapshot.getChildren()) {
                    String idUser = snapshot.getKey();
                    if (idUser.equals(reporteE.getReporteEncontradas().getIdUser())){
                        String nom, corr, tel, dir;
                        nom = snapshot.child("nombre").getValue(String.class) + " " + snapshot.child("apellidos").getValue(String.class);
                        corr = snapshot.child("correo").getValue(String.class);
                        tel = snapshot.child("telefono").getValue(String.class);
                        dir = snapshot.child("calle").getValue(String.class) + ""
                                + snapshot.child("noext").getValue(String.class) + ""
                                + snapshot.child("noint").getValue(String.class) + ", "
                                + snapshot.child("alcaldia").getValue(String.class);
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


        correoU = correoUser.getText().toString();

        if (correoU.isEmpty()){
            duenoRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    for (DataSnapshot snapshot:
                            dataSnapshot.getChildren()) {
                        String idUser = snapshot.getKey();
                        if (idUser.equals(reporteE.getReporteEncontradas().getIdUser())){
                            String nom, corr, tel, dir;
                            nom = snapshot.child("nombre").getValue(String.class) + " " + snapshot.child("apellidos").getValue(String.class);
                            corr = snapshot.child("correo").getValue(String.class);
                            tel = snapshot.child("telefono").getValue(String.class);
                            dir = snapshot.child("calle").getValue(String.class) + ""
                                    + snapshot.child("noext").getValue(String.class) + ""
                                    + snapshot.child("noint").getValue(String.class) + ", "
                                    + snapshot.child("alcaldia").getValue(String.class);
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
        idReporte.setText(reporteE.getId());
        tipoMascota.setText(reporteE.getReporteEncontradas().getTipo());
        fechaEncontrada.setText(reporteE.getReporteEncontradas().getFecha());
        horaEncontrada.setText(reporteE.getReporteEncontradas().getHora());
        alcaldia.setText(reporteE.getReporteEncontradas().getAlcaldia());
        colonia.setText(reporteE.getReporteEncontradas().getColonia());
        calle.setText(reporteE.getReporteEncontradas().getCalle());
        descripcion.setText(reporteE.getReporteEncontradas().getDescripcion());
        //correoUser.setText(reporteE.getReporteEncontradas().getIdUser());
        Glide.with(this).load(reporteE.getReporteEncontradas().getFoto()).apply(RequestOptions.circleCropTransform()).into(foto);


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
