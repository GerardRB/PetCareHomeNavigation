package com.example.petcarehome.homenavigation.ui.difusion.perdidas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.petcarehome.InicioYRegistro.Cuidador;
import com.example.petcarehome.InicioYRegistro.Dueno;
import com.example.petcarehome.homenavigation.Objetos.FirebaseReferences;
import com.example.petcarehome.homenavigation.Objetos.ReportePerdidasID;
import com.example.petcarehome.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetalleReportePerdidasActivity extends AppCompatActivity {

    private ReportePerdidasID reporteP;
    private TextView idReporte, nombreMascota, tipoMascota, edad, fechaExtravio, horaExtravio, alcaldia, colonia, calle, descripcion, correoUser, nombreUser, telefonoUser, domicilio;
    private ImageView foto;

    private String correoU;
    private FirebaseDatabase firebaseDatabase;
    //private ayudaRegistroDuenoCuidador userRep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_reporte_perdidas);

        //Recibir reporte Seleccionado
        Bundle reporteSeleccionado = getIntent().getExtras();
        reporteP = null;
        if (reporteSeleccionado != null){
            reporteP = (ReportePerdidasID) reporteSeleccionado.getSerializable("reportePerdida");
        }

        //Referencia a textviews
        idReporte = findViewById(R.id.text_id_DRMP);
        nombreMascota = findViewById(R.id.text_nombre_DRMP);
        tipoMascota = findViewById(R.id.text_tipo_DRMP);
        edad = findViewById(R.id.text_edad_DRMP);
        fechaExtravio = findViewById(R.id.text_fecha_extravio_DRMP);
        horaExtravio = findViewById(R.id.text_hora_Extravio_DRMP);
        alcaldia = findViewById(R.id.text_alcaldia_DRMP);
        colonia = findViewById(R.id.text_colonia_DRMP);
        calle = findViewById(R.id.text_calle_DRMP);
        descripcion = findViewById(R.id.text_descricpion_DRMP);
        foto = findViewById(R.id.id_imageDRMP);
        correoUser = findViewById(R.id.text_email_user_DRMP);
        nombreUser = findViewById(R.id.text_nombre_user_DRMP);
        telefonoUser = findViewById(R.id.text_telefono_user_DRMP);
        domicilio = findViewById(R.id.text_domicilio_user_DRMP);


        firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference duenoRef = firebaseDatabase.getReference().child(FirebaseReferences.USERS_REFERENCE).child(FirebaseReferences.DUENO_REFERENCE);
        final DatabaseReference cuidadorRef = firebaseDatabase.getReference().child(FirebaseReferences.USERS_REFERENCE).child(FirebaseReferences.CUIDADOR_REFERENCE);



        cuidadorRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot snapshot:
                        dataSnapshot.getChildren()) {
                    String idUser = snapshot.getKey();
                    if (idUser.equals(reporteP.getReportePerdidas().getUsuario())){
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
                        if (idUser.equals(reporteP.getReportePerdidas().getUsuario())){
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
        idReporte.setText(reporteP.getId());
        nombreMascota.setText(reporteP.getReportePerdidas().getNombre());
        tipoMascota.setText(reporteP.getReportePerdidas().getTipo());
        edad.setText(reporteP.getReportePerdidas().getEdad());
        fechaExtravio.setText(reporteP.getReportePerdidas().getFecha());
        horaExtravio.setText(reporteP.getReportePerdidas().getHora());
        alcaldia.setText(reporteP.getReportePerdidas().getAlcaldia());
        colonia.setText(reporteP.getReportePerdidas().getColonia());
        calle.setText(reporteP.getReportePerdidas().getCalle());
        descripcion.setText(reporteP.getReportePerdidas().getDescripcion());
        //correoUser.setText(reporteP.getReportePerdidas().getUsuario());
        Glide.with(this).load(reporteP.getReportePerdidas().getFoto()).into(foto);



    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
