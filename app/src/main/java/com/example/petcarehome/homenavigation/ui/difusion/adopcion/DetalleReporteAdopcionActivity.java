package com.example.petcarehome.homenavigation.ui.difusion.adopcion;

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
import com.example.petcarehome.homenavigation.Objetos.ReporteAdopcion;
import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.ui.difusion.FullScreenImageActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class DetalleReporteAdopcionActivity extends AppCompatActivity implements View.OnClickListener{

    private ReporteAdopcion reporteA;
    private TextView nombreUser, telefonoUser, domicilio, correoUser;
    private ImageView foto, fotoUser;
    private String nom, corr, tel, dir, fotoUsr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_reporte_adopcion);


        //Recibir reporte Seleccionado
        Bundle reporteSeleccionado = getIntent().getExtras();
        reporteA = null;
        boolean general = true;
        if (reporteSeleccionado != null){
            reporteA = (ReporteAdopcion) reporteSeleccionado.getSerializable("reporteAdopcion");
            general = reporteSeleccionado.getBoolean("general");
        }



        //Referencia a textviews
        TextView tipoMascota = findViewById(R.id.text_tipo_DRMA);
        TextView raza = findViewById(R.id.text_raza_DRMA);
        TextView edad = findViewById(R.id.text_edad_DRMA);
        TextView vacunas = findViewById(R.id.text_vacunas_DRMA);
        TextView esterilizacion = findViewById(R.id.text_esterilizacion_DRMA);
        TextView alcaldia = findViewById(R.id.text_alcaldia_DRMA);
        TextView colonia = findViewById(R.id.text_colonia_DRMA);
        TextView calle = findViewById(R.id.text_calle_DRMA);
        TextView descripcion = findViewById(R.id.text_descricpion_DRMA);
        correoUser = findViewById(R.id.text_id_user_DRMA);
        foto = findViewById(R.id.id_imageDRMA);
        fotoUser = findViewById(R.id.id_imagen_user_DRMA);
        nombreUser = findViewById(R.id.text_nombre_user_DRMA);
        telefonoUser = findViewById(R.id.text_telefono_user_DRMA);
        domicilio = findViewById(R.id.text_domicilio_user_DRMA);
        Button btnEliminar = findViewById(R.id.id_btn_eliminar_reportea);
        CardView cardUser = findViewById(R.id.card_user_ra);
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
                    if (idUser.equals(reporteA.getIdUser())){
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
                        if (idUser.equals(reporteA.getIdUser())){
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
        }



        //Imprimir valores del objeto recibido
        tipoMascota.setText(reporteA.getTipo());
        raza.setText(reporteA.getRaza());
        edad.setText(reporteA.getEdad());
        vacunas.setText(reporteA.getVacunas());
        esterilizacion.setText(reporteA.getEsterilizacion());
        alcaldia.setText(reporteA.getAlcaldia());
        colonia.setText(reporteA.getColonia());
        calle.setText(reporteA.getCalle());
        descripcion.setText(reporteA.getDescripcion());
        Glide.with(this).load(reporteA.getFoto()).apply(RequestOptions.circleCropTransform()).into(foto);
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
            case R.id.id_imageDRMA:
                Intent intent = new Intent(DetalleReporteAdopcionActivity.this, FullScreenImageActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(DetalleReporteAdopcionActivity.this, foto, Objects.requireNonNull(ViewCompat.getTransitionName(foto)));
                Bundle  bundle = new Bundle();
                bundle.putString("title", "la mascota");
                bundle.putString("foto", reporteA.getFoto());
                intent.putExtras(bundle);
                startActivity(intent, options.toBundle());
                break;
            case R.id.id_imagen_user_DRMA:
                Intent intentUser = new Intent(DetalleReporteAdopcionActivity.this, FullScreenImageActivity.class);
                ActivityOptionsCompat optionsUser = ActivityOptionsCompat.makeSceneTransitionAnimation(DetalleReporteAdopcionActivity.this, fotoUser, Objects.requireNonNull(ViewCompat.getTransitionName(fotoUser)));
                Bundle  bundleUser = new Bundle();
                bundleUser.putString("title", nom);
                bundleUser.putString("foto", fotoUsr);
                intentUser.putExtras(bundleUser);
                startActivity(intentUser, optionsUser.toBundle());
                break;
            case R.id.id_btn_eliminar_reportea:
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
        DatabaseReference reporteRef = FirebaseDatabase.getInstance().getReference().child(FirebaseReferences.REPORTES_REFERENCE).child(FirebaseReferences.REPORTEADOPCION_REFERENCE).child(reporteA.getIdRep());
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
