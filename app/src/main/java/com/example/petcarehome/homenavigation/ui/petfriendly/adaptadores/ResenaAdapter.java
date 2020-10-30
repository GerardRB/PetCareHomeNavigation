package com.example.petcarehome.homenavigation.ui.petfriendly.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.Objetos.LugarPetFriendly;

import java.util.ArrayList;
import java.util.Locale;

public class ResenaAdapter extends BaseAdapter {
    private ArrayList<LugarPetFriendly.Resena> mResenas;
    private LayoutInflater mLayout;
    private Context mContext;

    public ResenaAdapter(ArrayList<LugarPetFriendly.Resena> resenas, Context context) {
        this.mResenas = resenas;
        this.mContext = context;
        this.mLayout = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (mResenas != null) {
            return mResenas.size();
        }

        return 0;
    }

    @Override
    public Object getItem(int i) {
        if (mResenas != null) {
            return mResenas.get(i);
        }

        return null;
    }

    @Override
    public long getItemId(int i) {
        if (mResenas != null) {
            return mResenas.get(i).hashCode();
        }

        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LugarPetFriendly.Resena resena = mResenas.get(i);
        if (view == null) {
            view = mLayout.inflate(R.layout.item_resena, viewGroup, false);
        }

        TextView autor = view.findViewById(R.id.autor_resena);
        autor.setText(String.format(Locale.US, "%d/5 - %s", (Integer) resena.getEstrellas(), resena.getAutor()));

        TextView comentario = view.findViewById(R.id.texto_resena);
        comentario.setText((String) resena.getComentario());
        return view;
    }
}
