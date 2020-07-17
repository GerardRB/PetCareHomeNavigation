package com.example.myapplication.ui.difusion;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.myapplication.R;
import com.example.myapplication.ui.difusion.perdidas.GenerarReporteExtravioActivity;

import java.util.Calendar;

public class FilterDialog extends AppCompatDialogFragment {
    private EditText fecha1, fecha2;
    private Spinner spinnerMascota, spinnerZona;
    private DatePickerDialog.OnDateSetListener fecha1SetListener, fecha2SetListener;
    private CheckBox checkBoxZona, checkBoxTipo, checkBoxFecha;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_filter, null);

        fecha1 = view.findViewById(R.id.id_dialog_fecha1);
        fecha1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    Calendar cal = Calendar.getInstance();
                    int myear = cal.get(Calendar.YEAR);
                    int mmonth = cal.get(Calendar.MONTH);
                    int mday = cal.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dialog = new DatePickerDialog(getContext(),
                            android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                            fecha1SetListener, myear, mmonth, mday);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();

                }
            }
        });
        fecha1SetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String mes, date;
                month = month +1;
                switch (month){
                    case 1:
                        mes = "ENE";
                        break;
                    case 2:
                        mes = "FEB";
                        break;
                    case 3:
                        mes = "MAR";
                        break;
                    case 4:
                        mes = "ABR";
                        break;
                    case 5:
                        mes = "MAY";
                        break;
                    case 6:
                        mes = "JUN";
                        break;
                    case 7:
                        mes = "JUL";
                        break;
                    case 8:
                        mes = "AGO";
                        break;
                    case 9:
                        mes = "SEP";
                        break;
                    case 10:
                        mes = "OCT";
                        break;
                    case 11:
                        mes = "NOV";
                        break;
                    case 12:
                        mes = "DIC";
                        break;
                    default:
                        mes = " ";
                }
                if (dayOfMonth <= 9){
                    date = "0" + dayOfMonth + "/" + mes +  "/" + year;
                } else {
                    date = dayOfMonth +  "/" + mes +  "/" + year;
                }
                fecha1.setText(date);
            }
        };


        fecha2 = view.findViewById(R.id.id_dialog_fecha2);
        fecha2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    Calendar cal = Calendar.getInstance();
                    int myear = cal.get(Calendar.YEAR);
                    int mmonth = cal.get(Calendar.MONTH);
                    int mday = cal.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dialog = new DatePickerDialog(getContext(),
                            android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                            fecha2SetListener, myear, mmonth, mday);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();

                }
            }
        });
        fecha2SetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String mes, date;
                month = month +1;
                switch (month){
                    case 1:
                        mes = "ENE";
                        break;
                    case 2:
                        mes = "FEB";
                        break;
                    case 3:
                        mes = "MAR";
                        break;
                    case 4:
                        mes = "ABR";
                        break;
                    case 5:
                        mes = "MAY";
                        break;
                    case 6:
                        mes = "JUN";
                        break;
                    case 7:
                        mes = "JUL";
                        break;
                    case 8:
                        mes = "AGO";
                        break;
                    case 9:
                        mes = "SEP";
                        break;
                    case 10:
                        mes = "OCT";
                        break;
                    case 11:
                        mes = "NOV";
                        break;
                    case 12:
                        mes = "DIC";
                        break;
                    default:
                        mes = " ";
                }
                if (dayOfMonth <= 9){
                    date = "0" + dayOfMonth + "/" + mes +  "/" + year;
                } else {
                    date = dayOfMonth +  "/" + mes +  "/" + year;
                }
                fecha2.setText(date);
            }
        };


        spinnerMascota = view.findViewById(R.id.dialog_filter_tipo_mascota);
        ArrayAdapter<CharSequence> adapterTipoMascota = ArrayAdapter.createFromResource(getContext(), R.array.combo_tipoRMP, android.R.layout.simple_spinner_item);
        spinnerMascota.setAdapter(adapterTipoMascota);

        spinnerZona = view.findViewById(R.id.dialog_filter_zona);
        ArrayAdapter<CharSequence> adapterTipoCuidado = ArrayAdapter.createFromResource(getContext(), R.array.combo_alcaldia, android.R.layout.simple_spinner_item);
        spinnerZona.setAdapter(adapterTipoCuidado);


        checkBoxZona = view.findViewById(R.id.checkBox_zona);

        /*if (checkBoxZona.isChecked() == false){
            spinnerZona.setEnabled(false);
        } else{
            spinnerZona.setEnabled(true);
        }*/

        checkBoxTipo = view.findViewById(R.id.checkBox_tipo_mascota);
        /*if (checkBoxTipo.isChecked() == false){
            spinnerMascota.setEnabled(false);
        } else{
            spinnerMascota.setEnabled(true);
        }*/

        checkBoxFecha = view.findViewById(R.id.checkBox_fecha);
        /*if (checkBoxFecha.isChecked() == false){
            fecha1.setEnabled(false);
            fecha2.setEnabled(false);
        } else{
            fecha1.setEnabled(true);
            fecha2.setEnabled(true);
        }*/


        builder.setView(view)
                .setTitle("Filtrar por:")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                    }
                })
                .setPositiveButton("Filtrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Filtrar();
                    }
                });


        AlertDialog alert = builder.create();
        return alert;

    }

    private void Filtrar() {
        String cad = "Aplicando filtros...";

        if (checkBoxZona.isChecked() == true){
            cad += "\nZona: " + spinnerZona.getSelectedItem();
        }

        if (checkBoxTipo.isChecked() == true){
            cad += "\nTipo: " + spinnerMascota.getSelectedItem();
        }

        if (checkBoxFecha.isChecked() == true){
            cad += "\nDel: " + fecha1.getText() + " Al: " + fecha2.getText();
        }

        Toast.makeText(getContext(), cad, Toast.LENGTH_LONG).show();

    }
}
