package com.example.petcarehome.homenavigation.ui.mapa.dueno;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class VerCalificaciones extends AppCompatActivity implements DialogCalificacionListener{

    private String cuidador;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_calificaciones);

        //Recibir reporte Seleccionado
        Bundle bundleRecibido = getIntent().getExtras();
        String titulo = null;
        cuidador = null;
        ArrayList<Calificacion> calificaciones = new ArrayList<>();
        Float calif = null;
        if (bundleRecibido != null){
            titulo = bundleRecibido.getString("title");
            calif = bundleRecibido.getFloat("calif");
            cuidador = bundleRecibido.getString("idCuidador");
            calificaciones = (ArrayList<Calificacion>) bundleRecibido.getSerializable("lista");
        }

        setTitle("Calificaciones");


        RatingBar calificacion;
        calificacion = findViewById(R.id.id_calif);
        if (calif != null){
            calificacion.setRating(calif);
        }

        TextView nombre, c1, c2, c3, c4, c5;
        nombre = findViewById(R.id.text_nombre_cuidador);
        if (titulo != null){
            nombre.setText(titulo);
        }

        c1 = findViewById(R.id.text_cal1);
        c2 = findViewById(R.id.text_cal2);
        c3 = findViewById(R.id.text_cal3);
        c4 = findViewById(R.id.text_cal4);
        c5 = findViewById(R.id.text_cal5);

        float suma1, suma2, suma3, suma4, suma5, cal1, cal2, cal3, cal4, cal5;
        suma1 = 0;
        suma2 = 0;
        suma3 = 0;
        suma4 = 0;
        suma5 = 0;
        cal1 = 0;
        cal2 = 0;
        cal3 = 0;
        cal4 = 0;
        cal5 = 0;

        if (!calificaciones.isEmpty()){
            for (int i = 0; i<calificaciones.size(); i++){
                suma1 += calificaciones.get(i).getC1();
                suma2 += calificaciones.get(i).getC2();
                suma3 += calificaciones.get(i).getC3();
                suma4 += calificaciones.get(i).getC4();
                suma5 += calificaciones.get(i).getC5();
            }
            cal1  = suma1 / calificaciones.size();
            cal2  = suma2 / calificaciones.size();
            cal3  = suma3 / calificaciones.size();
            cal4  = suma4 / calificaciones.size();
            cal5  = suma5 / calificaciones.size();
        }

        c1.setText(" " + cal1);
        c2.setText(" " + cal2);
        c3.setText(" " + cal3);
        c4.setText("" + cal4);
        c5.setText(" " +cal5);

        Button btnCalificar = findViewById(R.id.id_btn_evaluar_cuidador);
        btnCalificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalificarCuidadorDialog calificar = new CalificarCuidadorDialog();
                calificar.show(getSupportFragmentManager().beginTransaction(), "calificar");
            }
        });

        RecyclerView recyclerCalif = findViewById(R.id.recyclerCalificaciones);
        recyclerCalif.setLayoutManager(new LinearLayoutManager(this));
        AdapterCalificaciones adapter = new AdapterCalificaciones(calificaciones, this);
        recyclerCalif.setAdapter(adapter);

    }


    @Override
    public void onReturnCalif(Calificacion calificacion) {
        FirebaseUser dueno = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference cuidadorRef = FirebaseDatabase.getInstance().getReference().child(FirebaseReferences.USERS_REFERENCE).child(FirebaseReferences.CUIDADOR_REFERENCE).child(cuidador).child("calificaciones").child(dueno.getUid());
        cuidadorRef.setValue(calificacion);

        //Toast.makeText(this, "Calif: " + calificacion.getCalificacion() + "\nComentarios: " + calificacion.getComentarios(), Toast.LENGTH_LONG).show();
    }
}