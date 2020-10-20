package com.example.petcarehome.homenavigation.ui.difusion.perdidas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petcarehome.InicioYRegistro.ayudaRegistroDuenoCuidador;
import com.example.petcarehome.homenavigation.Objetos.FirebaseReferences;
import com.example.petcarehome.homenavigation.Objetos.ReportePerdidasID;
import com.example.petcarehome.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

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
        final DatabaseReference userRef = firebaseDatabase.getReference().child(FirebaseReferences.USERS_REFERENCE).child(FirebaseReferences.DUENO_REFERENCE);


        /*
        Boolean isDueno = true;

        if (isDueno){
            //  Obtener datos del usuario que genero el reporte
            firebaseDatabase = FirebaseDatabase.getInstance();
            final DatabaseReference userReference = firebaseDatabase.getReference().child(FirebaseReferences.USERS_REFERENCE).child(FirebaseReferences.DUENO_REFERENCE);

            //userRep = null;
            userReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot :
                            dataSnapshot.getChildren()) {
                        String email = snapshot.child("correo").getValue(String.class);
                        if (email == reporteP.getReportePerdidas().getUsuario()){
                            ayudaRegistroDuenoCuidador userRep = (ayudaRegistroDuenoCuidador) snapshot.getValue();
                            //correoU = snapshot.getKey();
                            //correoUser.setText(userRep.getCorreo());
                            nombreUser.setText(userRep.getNombre() + userRep.getApellidos());
                            telefonoUser.setText(userRep.getCel());
                            domicilio.setText(userRep. getCalle() + "no. Ext: " + userRep.getNoext() + " no. Int: " + userRep.getNoint() + ", " + userRep.getAlcaldia());
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            if (userRep == null){
                isDueno = false;
            }
        } else {
            //  Obtener datos del usuario que genero el reporte
            firebaseDatabase = FirebaseDatabase.getInstance();
            final DatabaseReference userReference = firebaseDatabase.getReference().child(FirebaseReferences.USERS_REFERENCE).child(FirebaseReferences.CUIDADOR_REFERENCE);
            //userRep = new ayudaRegistroDuenoCuidador();
            userReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot :
                            dataSnapshot.getChildren()) {
                        String email = snapshot.child("correo").getValue(String.class);
                        if (email == reporteP.getReportePerdidas().getUsuario()){
                            userRep = (ayudaRegistroDuenoCuidador) snapshot.getValue();
                            //correoU = snapshot.getKey();
                            //correoUser.setText(userRep.getCorreo());
                            nombreUser.setText(userRep.getNombre() + userRep.getApellidos());
                            telefonoUser.setText(userRep.getCel());
                            domicilio.setText(userRep. getCalle() + "no. Ext: " + userRep.getNoext() + " no. Int: " + userRep.getNoint() + ", " + userRep.getAlcaldia());
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }*/




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
        correoUser.setText(reporteP.getReportePerdidas().getUsuario());
        Picasso.with(this).load(reporteP.getReportePerdidas().getFoto()).into(foto, new Callback() {
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
