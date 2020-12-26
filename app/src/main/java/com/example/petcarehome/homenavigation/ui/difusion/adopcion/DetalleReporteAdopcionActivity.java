package com.example.petcarehome.homenavigation.ui.difusion.adopcion;

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
import com.example.petcarehome.homenavigation.Objetos.ReporteAdopcion;
import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.Objetos.ReporteAdopcionID;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetalleReporteAdopcionActivity extends AppCompatActivity {

    private ReporteAdopcionID reporteA;
    private TextView idReporte, tipoMascota, raza, edad, vacunas, esterilizacion, alcaldia, colonia, calle, descripcion, correoUser, nombreUser, telefonoUser, domicilio;
    private ImageView foto;

    private String correoU;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_reporte_adopcion);


        //Recibir reporte Seleccionado
        Bundle reporteSeleccionado = getIntent().getExtras();
        reporteA = null;
        if (reporteSeleccionado != null){
            reporteA = (ReporteAdopcionID) reporteSeleccionado.getSerializable("reporteAdopcion");
        }

        //Referencia a textviews
        idReporte = findViewById(R.id.text_id_DRMA);
        tipoMascota = findViewById(R.id.text_tipo_DRMA);
        raza = findViewById(R.id.text_raza_DRMA);
        edad = findViewById(R.id.text_edad_DRMA);
        vacunas = findViewById(R.id.text_vacunas_DRMA);
        esterilizacion = findViewById(R.id.text_esterilizacion_DRMA);
        alcaldia = findViewById(R.id.text_alcaldia_DRMA);
        colonia = findViewById(R.id.text_colonia_DRMA);
        calle = findViewById(R.id.text_calle_DRMA);
        descripcion = findViewById(R.id.text_descricpion_DRMA);
        correoUser = findViewById(R.id.text_id_user_DRMA);
        foto = findViewById(R.id.id_imageDRMA);
        nombreUser = findViewById(R.id.text_nombre_user_DRMA);
        telefonoUser = findViewById(R.id.text_telefono_user_DRMA);
        domicilio = findViewById(R.id.text_domicilio_user_DRMA);

        firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference duenoRef = firebaseDatabase.getReference().child(FirebaseReferences.USERS_REFERENCE).child(FirebaseReferences.DUENO_REFERENCE);
        final DatabaseReference cuidadorRef = firebaseDatabase.getReference().child(FirebaseReferences.USERS_REFERENCE).child(FirebaseReferences.CUIDADOR_REFERENCE);



        cuidadorRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot snapshot:
                        dataSnapshot.getChildren()) {
                    String idUser = snapshot.getKey();
                    if (idUser.equals(reporteA.getReporteAdopcion().getIdUser())){
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
                        if (idUser.equals(reporteA.getReporteAdopcion().getIdUser())){
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
        idReporte.setText(reporteA.getId());
        tipoMascota.setText(reporteA.getReporteAdopcion().getTipo());
        raza.setText(reporteA.getReporteAdopcion().getRaza());
        edad.setText(reporteA.getReporteAdopcion().getEdad());
        vacunas.setText(reporteA.getReporteAdopcion().getVacunas());
        esterilizacion.setText(reporteA.getReporteAdopcion().getEsterilizacion());
        alcaldia.setText(reporteA.getReporteAdopcion().getAlcaldia());
        colonia.setText(reporteA.getReporteAdopcion().getColonia());
        calle.setText(reporteA.getReporteAdopcion().getCalle());
        descripcion.setText(reporteA.getReporteAdopcion().getDescripcion());
        //correoUser.setText(reporteA.getReporteAdopcion().getIdUser());
        Glide.with(this).load(reporteA.getReporteAdopcion().getFoto()).into(foto);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
