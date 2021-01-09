package com.example.petcarehome.homenavigation.ui.mapa.dueno;

import android.annotation.SuppressLint;
import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.renderscript.ScriptGroup;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;


import com.example.petcarehome.R;


import java.util.ArrayList;
import java.util.List;

import static androidx.core.content.ContextCompat.getSystemService;

public class PreguntasPagerAdapter extends PagerAdapter {

    private final List<Integer> listaResources = new ArrayList<>();
    private final Context context;


    public PreguntasPagerAdapter(Context context) {
                this.context = context;
    }

    public void addResource(int resource){
        listaResources.add(resource);
    }

    @Override
    public int getCount() {
        return listaResources.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        final View view = layoutInflater.inflate(listaResources.get(position), container, false);
        if (position != 5){
            TextView respuesta = view.findViewById(R.id.text_respuesta);
            RatingBar calif = view.findViewById(R.id.id_calif);
            mostrarRespuesta(respuesta, calif, position);
        } else {
            final EditText comentarios = view.findViewById(R.id.id_input_comentarios);
        }
        container.addView(view, position);
        return view;
    }

    private void mostrarRespuesta(final TextView respuesta, RatingBar calif, int pos) {
        switch (pos){
            case 0:
            case 3:
                calif.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        if (rating <= 1) {
                            respuesta.setText("Pésima");
                        } else if (rating > 1 && rating <= 2) {
                            respuesta.setText("Mala");
                        } else if (rating > 2 && rating <= 3) {
                            respuesta.setText("Regular");
                        } else if (rating > 3 && rating <= 4) {
                            respuesta.setText("Buena");
                        } else if (rating > 4 && rating <= 5) {
                            respuesta.setText("Excelente");
                        }
                    }
                });
                break;
            case 1:
                calif.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        if (rating <= 1) {
                            respuesta.setText("Pésimo");
                        } else if (rating > 1 && rating <= 2) {
                            respuesta.setText("Malo");
                        } else if (rating > 2 && rating <= 3) {
                            respuesta.setText("Regular");
                        } else if (rating > 3 && rating <= 4) {
                            respuesta.setText("Bueno");
                        } else if (rating > 4 && rating <= 5) {
                            respuesta.setText("Excelente");
                        }
                    }
                });
                break;
            case 2:
                calif.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        if (rating <= 1) {
                            respuesta.setText("Pésimas");
                        } else if (rating > 1 && rating <= 2) {
                            respuesta.setText("Malas");
                        } else if (rating > 2 && rating <= 3) {
                            respuesta.setText("Regulares");
                        } else if (rating > 3 && rating <= 4) {
                            respuesta.setText("Buenas");
                        } else if (rating > 4 && rating <= 5) {
                            respuesta.setText("Excelentes");
                        }
                    }
                });
                break;
            case 4:
                calif.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        if (rating <= 1) {
                            respuesta.setText("Muy Insatisfecho");
                        } else if (rating > 1 && rating <= 2) {
                            respuesta.setText("Insatisfecho");
                        } else if (rating > 2 && rating <= 3) {
                            respuesta.setText("Neutral");
                        } else if (rating > 3 && rating <= 4) {
                            respuesta.setText("Satisfecho");
                        } else if (rating > 4 && rating <= 5) {
                            respuesta.setText("Muy Satisfecho");
                        }
                    }
                });
                break;
        }
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }
}
