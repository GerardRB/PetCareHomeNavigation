package com.example.petcarehome.homenavigation.ui.petfriendly.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.Objetos.FirebaseReferences;
import com.example.petcarehome.homenavigation.Objetos.LugarPetFriendly;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class GaleriaAdapter extends RecyclerView.Adapter<GaleriaAdapter.ItemHolder> {
    private static final String TAG = "GaleriaAdapter";
    private Context mContext;
    private DatabaseReference mDatabase;
    private StorageReference mStorage;
    private LugarPetFriendly mLugar;
    private LayoutInflater mInflater;
    private ChildEventListener mListener;
    private ArrayList<String> mImagenes;
    private Query mQuery;

    public GaleriaAdapter(Context mContext, DatabaseReference mDatabase, StorageReference mStorage,
                          LugarPetFriendly mLugar) {
        this.mContext = mContext;
        this.mDatabase = mDatabase;
        this.mStorage = mStorage;
        this.mLugar = mLugar;
        this.mImagenes = mLugar.getFotosGaleria();
        if (this.mImagenes == null) {
            this.mImagenes = new ArrayList<>();
        }

        this.mInflater = LayoutInflater.from(mContext);
    }

    public void conectar() {
        mListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());
                mImagenes.add(dataSnapshot.getValue(String.class));
                GaleriaAdapter.this.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());
                mImagenes.set(mImagenes.indexOf(dataSnapshot.getValue(String.class)), dataSnapshot.getValue(String.class));
                GaleriaAdapter.this.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());
                mImagenes.remove(dataSnapshot.getValue(String.class));
                GaleriaAdapter.this.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String idPrevio) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());
                mImagenes.remove(dataSnapshot.getValue(String.class));
                mImagenes.add(dataSnapshot.getValue(String.class));
                GaleriaAdapter.this.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "onCancelled", databaseError.toException());
                Toast.makeText(mContext, "No se ha podido cargar la lista de lugares Pet Friendly", Toast.LENGTH_LONG).show();
            }
        };

        mQuery = mDatabase.child(FirebaseReferences.LUGARES_PET_FRIENDLY_REFERENCE)
                .child(mLugar.getCategoria())
                .child("lugares")
                .child(mLugar.getId())
                .child("galeria")
                .orderByKey();
        mQuery.addChildEventListener(mListener);
    }

    public void desconectar() {
        if (mQuery != null) {
            mQuery.removeEventListener(mListener);
        }
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.card_foto_galeria, parent, false);
        return new GaleriaAdapter.ItemHolder(mContext, view, mStorage, mLugar.getId());
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        holder.setImagen(mImagenes.get(position));
    }

    @Override
    public int getItemCount() {
        return mImagenes.size();
    }

    public static class ItemHolder extends RecyclerView.ViewHolder {
        private StorageReference mStorage;
        private Context mContext;
        private String mImagen;
        private ImageView imagenView;
        private String mKey;

        public ItemHolder(Context context, @NonNull View view, StorageReference storage, String mKey) {
            super(view);
            this.imagenView = view.findViewById(R.id.imagen_galeria);
            this.mStorage = storage;
            this.mContext = context;
            this.mKey = mKey;
        }

        public void setImagen(String imagen) {
            this.mImagen = imagen;
            this.descargar();
        }

        public void descargar() {
            Log.d(TAG, "Cargando foto: " + mImagen);
            StorageReference ref = mStorage
                    .child(FirebaseReferences.STORAGE_GALERIA_LUGAR)
                    .child(mKey)
                    .child(mImagen);

            Glide.with(mContext)
                    .load(ref)
                    .into(imagenView);
        }
    }
}
