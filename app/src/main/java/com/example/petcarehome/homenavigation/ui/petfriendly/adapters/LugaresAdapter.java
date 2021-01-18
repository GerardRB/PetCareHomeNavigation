package com.example.petcarehome.homenavigation.ui.petfriendly.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.Objetos.CategoriaLugar;
import com.example.petcarehome.homenavigation.Objetos.FirebaseReferences;
import com.example.petcarehome.homenavigation.Objetos.LugarPetFriendly;
import com.example.petcarehome.homenavigation.ui.petfriendly.activities.DetallePetfriendlyActivity;
import com.google.android.gms.common.internal.Objects;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class LugaresAdapter extends RecyclerView.Adapter<LugaresAdapter.ItemHolder> {
    private static final String TAG = "LugaresAdapter";
    private Context mContext;
    private DatabaseReference mDatabase;
    private StorageReference mStorage;
    private CategoriaLugar mCategoria;
    private ArrayList<LugarPetFriendly> mLugaresFiltrados;
    private ArrayList<LugarPetFriendly> mLugares;
    private LayoutInflater mInflater;
    private ChildEventListener mListener;
    private Query mQuery;
    private String mFiltro;

    public LugaresAdapter(Context mContext, DatabaseReference mDatabase, StorageReference mStorage, CategoriaLugar mCategoria) {
        this.mContext = mContext;
        this.mDatabase = mDatabase;
        this.mStorage  = mStorage;
        this.mCategoria = mCategoria;
        this.mFiltro = "";
        this.mLugaresFiltrados = new ArrayList<>();
        this.mLugares = new ArrayList<>();
        this.mInflater = LayoutInflater.from(mContext);
    }

    public void conectar() {
        mListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());
                mLugares.add(dataSnapshot.getValue(LugarPetFriendly.class));
                setFiltro(mFiltro);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey() + ", old key:" + s);

                LugarPetFriendly encontrar = new LugarPetFriendly(dataSnapshot.getKey());
                int indice = mLugares.indexOf(encontrar);
                if (indice < mLugares.size() && indice > -1) {
                    mLugares.set(indice, dataSnapshot.getValue(LugarPetFriendly.class));
                }
                setFiltro(mFiltro);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());
                mLugares.remove(dataSnapshot.getValue(LugarPetFriendly.class));
                setFiltro(mFiltro);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String idPrevio) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());
                mLugares.remove(new LugarPetFriendly(idPrevio));
                mLugares.add(dataSnapshot.getValue(LugarPetFriendly.class));
                setFiltro(mFiltro);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "onCancelled", databaseError.toException());
                Toast.makeText(mContext, "No se ha podido cargar la lista de lugares Pet Friendly", Toast.LENGTH_LONG);
            }
        };

        mQuery = mDatabase.child(FirebaseReferences.LUGARES_PET_FRIENDLY_REFERENCE)
                .child(mCategoria.getId())
                .child("lugares")
                .orderByKey();
        mQuery.addChildEventListener(mListener);
    }

    public void desconectar() {
        if (mQuery != null) {
            mQuery.removeEventListener(mListener);
        }
    }

    public void setFiltro(String filtro) {
        mFiltro = filtro;
        Log.d(TAG, "Lugares size " + mLugares.size());
        Log.d(TAG, "Lugares filtrados size " + mLugaresFiltrados.size());
        mLugaresFiltrados.clear();

        if (filtro == null || "".equals(filtro)) {
            mLugaresFiltrados.addAll(mLugares);
        } else {
            for (LugarPetFriendly lugar : mLugares) {
                if (lugar.getNombre().toLowerCase()
                        .contains(filtro.toLowerCase())) {
                    mLugaresFiltrados.add(lugar);
                }
            }
        }

        notifyDataSetChanged();
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.card_lugar_petfriendly, parent, false);
        return new ItemHolder(mContext, view, mStorage, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int adapterPosition) {
                LugarPetFriendly lugar = mLugaresFiltrados.get(adapterPosition);
                Intent intent = new Intent(mContext, DetallePetfriendlyActivity.class);
                intent.putExtra("lugar", lugar);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        holder.setLugar(mLugaresFiltrados.get(position));
    }

    @Override
    public int getItemCount() {
        return mLugaresFiltrados.size();
    }

    public static class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private static final String TAG = "ItemHolder";
        private TextView nombreView;
        private ImageView imagenView;
        private RatingBar ratingView;
        private LugarPetFriendly mLugar;
        private StorageReference mStorage;
        private Context mContext;
        private RecyclerViewClickListener mListener;

        public ItemHolder(@NonNull Context context,
                          @NonNull View itemView,
                          @NonNull StorageReference storage,
                          @Nullable RecyclerViewClickListener listener) {
            super(itemView);
            this.mContext = context;
            this.mStorage = storage;
            this.nombreView = itemView.findViewById(R.id.text_nombre_lugar);
            this.imagenView = itemView.findViewById(R.id.imagen_lugar);
            this.ratingView = itemView.findViewById(R.id.rating);
            this.mListener = listener;

            nombreView.setOnClickListener(this);
            imagenView.setOnClickListener(this);
        }

        public void setLugar(LugarPetFriendly lugar) {
            mLugar = lugar;
            nombreView.setText(mLugar.getNombre());
            ratingView.setRating(mLugar.getEstrellas());
            ratingView.setVisibility(View.VISIBLE);
            descargarFoto(lugar.getFoto());
        }

        private void descargarFoto(String archivo) {
            Log.d(TAG, "Cargando foto: " + archivo);
            if (archivo != null) {
                StorageReference ref = mStorage
                        .child(FirebaseReferences.STORAGE_FOTO_LUGAR_PETFRIENDLY)
                        .child(mLugar.getId())
                        .child(archivo);
                Glide.with(mContext)
                        .load(ref)
                        .into(imagenView);
            }
        }

        @Override
        public void onClick(View view) {
            mListener.onClick(view, getAdapterPosition());
        }
    }

    public interface RecyclerViewClickListener {
        void onClick(View view, int adapterPosition);
    }
}
