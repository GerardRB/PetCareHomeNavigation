package com.example.myapplication.ui.inicio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class MainActivity extends AppCompatActivity {
    private Button mcuidador, mdueño, minfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio_app);
        mcuidador = (Button) findViewById(R.id.cuidador);
        mdueño = (Button) findViewById(R.id.dueño);
        minfo = (Button) findViewById(R.id.info);

        mcuidador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, cuidadorLoginActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        mdueño.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, duenoLoginActivity.class);
                startActivity(intent);
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
    dialogoInfo dialogoInfo = new dialogoInfo();
    dialogoInfo.show(getSupportFragmentManager(), "Información");

    }

}
