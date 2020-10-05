package com.example.petcarehome.InicioYRegistro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.petcarehome.R;

public class TipoUserActivity extends AppCompatActivity {
    Button mcuidador, mdueño, minfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_user);

        mcuidador = findViewById(R.id.cuidador);
        mdueño = findViewById(R.id.dueño);
        minfo = findViewById(R.id.info);

        mdueño.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TipoUserActivity.this, duenoLoginActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        mcuidador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(TipoUserActivity.this, cuidadorLoginActivity.class);
                startActivity(intent2);
                finish();
                return;
            }
        });

        minfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    public void openDialog(){
        DialogoInfo dialogoInfo = new DialogoInfo();
        dialogoInfo.show(getSupportFragmentManager(), "Información");

    }

}
