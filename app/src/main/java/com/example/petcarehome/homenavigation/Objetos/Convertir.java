package com.example.petcarehome.homenavigation.Objetos;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Convertir {
    public static Map<String, Object> aMapa(Object objeto) throws IllegalAccessException {
        Map<String, Object> mapa = new HashMap<String,Object>();

        Field[] allFields = objeto.getClass().getDeclaredFields();
        for (Field field : allFields) {
            field.setAccessible(true);
            Object value = field.get(objeto);
            mapa.put(field.getName(), value);
        }

        return mapa;
    }
}
