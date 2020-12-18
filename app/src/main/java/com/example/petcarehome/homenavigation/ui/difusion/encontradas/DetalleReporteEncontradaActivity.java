package com.example.petcarehome.homenavigation.ui.difusion.encontradas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

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
                        Cuidador cuidador = snapshot.getValue(Cuidador.class);
                        nombreUser.setText(cuidador.getNombre() + " " + cuidador.getApellidos());
                        correoUser.setText(cuidador.getCorreo());
                        telefonoUser.setText(cuidador.getTelefono());
                        domicilio.setText(cuidador.getCalle() + " " + cuidador.getNoext() + " " + cuidador.getNoint() + ", " + cuidador.getAlcaldia());
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
                            Dueno dueno = snapshot.getValue(Dueno.class);
                            nombreUser.setText(dueno.getNombre() + " " + dueno.getApellidos());
                            correoUser.setText(dueno.getCorreo());
                            telefonoUser.setText(dueno.getCel());
                            domicilio.setText(dueno.getCalle() + " " + dueno.getNoext() + " " + dueno.getNoint() + ", " + dueno.getAlcaldia());
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
        Picasso.with(this).load(reporteE.getReporteEncontradas().getFoto()).into(foto, new Callback() {
            @Override
            public void onSuccess() {
                foto.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError() {
                Toast.makeText(getApplicationContext(),"Error al cargar imagen", Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
