package com.example.petcarehome.homenavigation.ui.mapa.dueno;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.Objetos.Calificacion;
import com.example.petcarehome.homenavigation.Objetos.FirebaseReferences;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class VerCalificaciones extends AppCompatActivity implements DialogCalificacionListener{

    private String cuidador;
    private ProgressDialog calificando;
    private ArrayList<Calificacion> listCal;
    private DatabaseReference cuidadorRef;
    private AdapterCalificaciones adapter;
    private RatingBar calificacion;
    private float suma1, suma2, suma3, suma4, suma5, cal1, cal2, cal3, cal4, cal5, sumagen, calgen;
    private TextView c1, c2, c3, c4, c5;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_calificaciones);

        //Recibir reporte Seleccionado
        Bundle bundleRecibido = getIntent().getExtras();
        String titulo = null;
        boolean isGeneral = true;
        cuidador = null;
        ArrayList<Calificacion> calificaciones = new ArrayList<>();
        suma1 = 0;
        suma2 = 0;
        suma3 = 0;
        suma4 = 0;
        suma5 = 0;
        sumagen = 0;
        cal1 = 0;
        cal2 = 0;
        cal3 = 0;
        cal4 = 0;
        cal5 = 0;
        calgen = 0;
        //Float calif = null;
        if (bundleRecibido != null){
            titulo = bundleRecibido.getString("title");
            //calif = bundleRecibido.getFloat("calif");
            cuidador = bundleRecibido.getString("idCuidador");
            //calificaciones = (ArrayList<Calificacion>) bundleRecibido.getSerializable("lista");
            isGeneral = bundleRecibido.getBoolean("general");
        }

        listCal = new ArrayList<>();
        cuidadorRef = FirebaseDatabase.getInstance().getReference().child(FirebaseReferences.USERS_REFERENCE).child(FirebaseReferences.CUIDADOR_REFERENCE).child(cuidador).child("calificaciones");

        setTitle("Calificaciones");




        calificacion = findViewById(R.id.id_calif);
        /*if (calif != null){
            calificacion.setRating(calif);
        }*/

        TextView nombre;
        nombre = findViewById(R.id.text_nombre_cuidador);
        if (titulo != null){
            nombre.setText(titulo);
        }

        c1 = findViewById(R.id.text_cal1);
        c2 = findViewById(R.id.text_cal2);
        c3 = findViewById(R.id.text_cal3);
        c4 = findViewById(R.id.text_cal4);
        c5 = findViewById(R.id.text_cal5);

        //float suma1, suma2, suma3, suma4, suma5, cal1, cal2, cal3, cal4, cal5, sumagen, calgen;






        Button btnCalificar = findViewById(R.id.id_btn_evaluar_cuidador);
        if(!isGeneral){
            btnCalificar.setVisibility(View.GONE);
        }
        btnCalificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalificarCuidadorDialog calificar = new CalificarCuidadorDialog();
                calificar.show(getSupportFragmentManager().beginTransaction(), "calificar");
            }
        });


        RecyclerView recyclerCalif = findViewById(R.id.recyclerCalificaciones);
        recyclerCalif.setLayoutManager(new LinearLayoutManager(this));
        adapter  = new AdapterCalificaciones(listCal, this);
        recyclerCalif.setAdapter(adapter);
        llenarCalificaciones();

    }

    private void llenarCalificaciones() {
        cuidadorRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listCal.clear();
                suma1 = 0;
                suma2 = 0;
                suma3 = 0;
                suma4 = 0;
                suma5 = 0;
                sumagen = 0;
                cal1 = 0;
                cal2 = 0;
                cal3 = 0;
                cal4 = 0;
                cal5 = 0;
                calgen = 0;
                for (DataSnapshot snapCalif:
                        dataSnapshot.getChildren()) {
                    if (snapCalif.exists()){
                        Calificacion calN = snapCalif.getValue(Calificacion.class);
                        listCal.add(calN);
                    }
                }
                adapter.notifyDataSetChanged();
                if (!listCal.isEmpty()){
                    for (int i = 0; i<listCal.size(); i++){
                        suma1 += listCal.get(i).getC1();
                        suma2 += listCal.get(i).getC2();
                        suma3 += listCal.get(i).getC3();
                        suma4 += listCal.get(i).getC4();
                        suma5 += listCal.get(i).getC5();
                        sumagen += listCal.get(i).getCalificacion();
                    }
                    cal1  = suma1 / listCal.size();
                    cal2  = suma2 / listCal.size();
                    cal3  = suma3 / listCal.size();
                    cal4  = suma4 / listCal.size();
                    cal5  = suma5 / listCal.size();
                    calgen = sumagen / listCal.size();
                    DecimalFormat calFormat = new DecimalFormat();
                    calFormat.setMaximumFractionDigits(1);

                    c1.setText(calFormat.format(cal1));
                    c2.setText(calFormat.format(cal2));
                    c3.setText(calFormat.format(cal3));
                    c4.setText(calFormat.format(cal4));
                    c5.setText(calFormat.format(cal5));
                    calificacion.setRating(calgen);
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onReturnCalif(Calificacion calificacion) {
        calificando = new ProgressDialog(VerCalificaciones.this);
        calificando.show();
        calificando.setContentView(R.layout.progress_calificacion);
        calificando.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        FirebaseUser dueno = FirebaseAuth.getInstance().getCurrentUser();

        cuidadorRef.child(dueno.getUid()).setValue(calificacion, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError != null){
                    calificando.dismiss();
                    Toast.makeText(getApplicationContext(), "Error al calificar\nInténtelo nuevamente", Toast.LENGTH_LONG).show();
                } else {
                    calificando.dismiss();
                    Toast.makeText(getApplicationContext(), "Gracias por calificar al cuidador", Toast.LENGTH_LONG).show();
                    //onBackPressed();
                }
            }
        });

        //Toast.makeText(this, "Calif: " + calificacion.getCalificacion() + "\nComentarios: " + calificacion.getComentarios(), Toast.LENGTH_LONG).show();
    }
}