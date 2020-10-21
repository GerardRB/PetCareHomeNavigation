package com.example.petcarehome.homenavigation.ui.difusion.perdidas;

import java.util.ArrayList;
import java.util.List;

public class FotosLista {
    private ArrayList<String> arrayFotos;

    public FotosLista() {
    }

    public FotosLista(ArrayList<String> arrayFotos) {
        this.arrayFotos = arrayFotos;
    }

    public ArrayList<String> getArrayFotos() {
        return arrayFotos;
    }

    public void setArrayFotos(ArrayList<String> arrayFotos) {
        this.arrayFotos = arrayFotos;
    }

    public void AgregarFoto(String foto){
        arrayFotos = new ArrayList<String>();
        arrayFotos.add(foto);
    }

}
