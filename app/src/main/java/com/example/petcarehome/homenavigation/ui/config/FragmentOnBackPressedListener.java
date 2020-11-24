package com.example.petcarehome.homenavigation.ui.config;

public interface FragmentOnBackPressedListener {
    //Interface para el método de onBackPressed. Utilizado el configFragment_dueno
    /**
     * If you return true the back press will not be taken into account, otherwise the activity will act naturally
     * @return true if your processing has priority if not false
     */
    boolean onBackPressed();
}
