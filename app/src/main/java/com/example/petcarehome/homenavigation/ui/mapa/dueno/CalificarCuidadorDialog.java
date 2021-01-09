package com.example.petcarehome.homenavigation.ui.mapa.dueno;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.Objetos.Calificacion;
import com.example.petcarehome.homenavigation.Objetos.FirebaseReferences;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;


public class CalificarCuidadorDialog extends AppCompatDialogFragment {

    ViewPager viewPager;
    PreguntasPagerAdapter adapter;

    //TextView respuesta;
    EditText comentarios;
    RatingBar calif;
    View actual;
    float cal1, cal2, cal3, cal4, cal5;
    String coments;
    FirebaseUser dueno;

    public CalificarCuidadorDialog() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return crearDialogoCalificar();
    }

    @SuppressLint("ClickableViewAccessibility")
    private AlertDialog crearDialogoCalificar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.fragment_calificar_cuidador_dialog, null);
        builder.setView(v).setTitle("Encuesta de satisfacción");

        dueno = FirebaseAuth.getInstance().getCurrentUser();


        viewPager = v.findViewById(R.id.view_pager_preguntas);
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        adapter = new PreguntasPagerAdapter(getContext());
        adapter.addResource(R.layout.item_pregunta1);
        adapter.addResource(R.layout.item_pregunta2);
        adapter.addResource(R.layout.item_pregunta3);
        adapter.addResource(R.layout.item_pregunta4);
        adapter.addResource(R.layout.item_pregunta5);
        adapter.addResource(R.layout.item_comentarios);
        viewPager.setAdapter(adapter);



        builder.setView(v)
                .setTitle("Encuesta de satisfacción");
        builder.setPositiveButton("Siguiente", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //viewPager.setCurrentItem(viewPager.getCurrentItem()+1, true);
                }
            });
        /*builder.setNeutralButton("Anterior", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });*/
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });


        AlertDialog alert = builder.create();
        alert.show();
        return alert;
    }




    @Override
    public void onResume() {
        super.onResume();
        final AlertDialog d = (AlertDialog) getDialog();
        d.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        if (d != null){
            final Button next = d.getButton(DialogInterface.BUTTON_POSITIVE);
            //final Button last = d.getButton(DialogInterface.BUTTON_NEUTRAL);
            final Button cancel = d.getButton(DialogInterface.BUTTON_NEGATIVE);
            /*if (viewPager.getCurrentItem() == 0){
                last.setVisibility(View.GONE);
            }*/
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @SuppressLint("SetTextI18n")
                @Override
                public void onPageSelected(int position) {

                    /*Boton anterior desaparecer si está en la pregunta 1
                    if (position == 0){
                        last.setVisibility(View.GONE);
                    } else {
                        last.setVisibility(View.VISIBLE);
                    }*/
                    //Boton siguiente cambia a Finalizar si está en la ultima pregunta
                    if (position == adapter.getCount()-1){
                        next.setText("Finalizar");
                    } else {
                        next.setText("Siguiente");
                    }

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            /*last.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem()-1, true);
                }
            });*/

            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = viewPager.getCurrentItem();
                    actual = viewPager.getChildAt(pos);
                    if (pos == (adapter.getCount()-1)){
                        comentarios = actual.findViewById(R.id.id_input_comentarios);
                        coments = comentarios.getText().toString();
                        float prom = (cal1+cal2+cal3+cal4+cal5)/5;
                        Calificacion calificacion = new Calificacion(cal1, cal2, cal3, cal4, cal5, prom, coments);
                        //Toast.makeText(getContext(), "Calif: " + calificacion.getCalificacion(), Toast.LENGTH_LONG).show();
                        DialogCalificacionListener activity = (DialogCalificacionListener) getActivity();
                        activity.onReturnCalif(calificacion);
                        dismiss();
                    } else {
                        calif = actual.findViewById(R.id.id_calif);
                        switch (pos){
                            case 0:
                                if (calif.getRating() != 0){
                                    cal1 = calif.getRating();
                                    viewPager.setCurrentItem(viewPager.getCurrentItem()+1, true);
                                } else {
                                    Toast.makeText(getContext(), "Asigne una calificación", Toast.LENGTH_LONG).show();
                                }
                                break;
                            case 1:
                                if (calif.getRating() != 0){
                                    cal2 = calif.getRating();
                                    viewPager.setCurrentItem(viewPager.getCurrentItem()+1, true);
                                } else {
                                    Toast.makeText(getContext(), "Asigne una calificación", Toast.LENGTH_LONG).show();
                                }
                                break;
                            case 2:
                                if (calif.getRating() != 0){
                                    cal3 = calif.getRating();
                                    viewPager.setCurrentItem(viewPager.getCurrentItem()+1, true);
                                } else {
                                    Toast.makeText(getContext(), "Asigne una calificación", Toast.LENGTH_LONG).show();
                                }
                                break;
                            case 3:
                                if (calif.getRating() != 0){
                                    cal4 = calif.getRating();
                                    viewPager.setCurrentItem(viewPager.getCurrentItem()+1, true);
                                } else {
                                    Toast.makeText(getContext(), "Asigne una calificación", Toast.LENGTH_LONG).show();
                                }
                                break;
                            case 4:
                                if (calif.getRating() != 0){
                                    cal5 = calif.getRating();
                                    viewPager.setCurrentItem(viewPager.getCurrentItem()+1, true);
                                } else {
                                    Toast.makeText(getContext(), "Asigne una calificación", Toast.LENGTH_LONG).show();
                                }
                                break;
                        }
                    }
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    d.dismiss();
                }
            });
        }
    }
}