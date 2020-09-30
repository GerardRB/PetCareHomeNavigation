package com.example.petcarehome.homenavigation.ui.petfriendly;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PetfriendlyViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public PetfriendlyViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Fragmento de Pet Friendly");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
