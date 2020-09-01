package com.example.myapplication.ui.difusion;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.myapplication.Objetos.Filtro;
import com.example.myapplication.R;
import com.example.myapplication.ui.difusion.perdidas.GenerarReporteExtravioActivity;

import java.util.Calendar;

public class FilterDialog extends AppCompatDialogFragment {
    private EditText fecha1, fecha2;
    private TextView etiFecha1, etiFecha2;
    private Spinner spinnerMascota, spinnerZona;
    private DatePickerDialog.OnDateSetListener fecha1SetListener, fecha2SetListener;
    private CheckBox checkBoxZona, checkBoxTipo, checkBoxFecha;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_filter, null);

        etiFecha1 = view.findViewById(R.id.label_fecha_del);
        etiFecha1.setVisibility(View.GONE);

        etiFecha2 = view.findViewById(R.id.label_fecha_al);
        etiFecha2.setVisibility(View.GONE);

        fecha1 = view.findViewById(R.id.id_dialog_fecha1);
        fecha1.setVisibility(View.GONE);
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
        fecha2.setVisibility(View.GONE);
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
        spinnerMascota.setVisibility(View.GONE);

        spinnerZona = view.findViewById(R.id.dialog_filter_zona);
        ArrayAdapter<CharSequence> adapterTipoCuidado = ArrayAdapter.createFromResource(getContext(), R.array.combo_alcaldia, android.R.layout.simple_spinner_item);
        spinnerZona.setAdapter(adapterTipoCuidado);
        spinnerZona.setVisibility(View.GONE);


        checkBoxZona = view.findViewById(R.id.checkBox_zona);
        checkBoxZona.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == false){
                    spinnerZona.setVisibility(View.GONE);
                } else{
                    spinnerZona.setVisibility(View.VISIBLE);
                }
            }
        });

        checkBoxTipo = view.findViewById(R.id.checkBox_tipo_mascota);
        checkBoxTipo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == false){
                    spinnerMascota.setVisibility(View.GONE);
                } else{
                    spinnerMascota.setVisibility(View.VISIBLE);
                }
            }
        });


        checkBoxFecha = view.findViewById(R.id.checkBox_fecha);
        checkBoxFecha.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == false){
                    fecha1.setVisibility(View.GONE);
                    fecha2.setVisibility(View.GONE);
                    etiFecha1.setVisibility(View.GONE);
                    etiFecha2.setVisibility(View.GONE);
                } else{
                    fecha1.setVisibility(View.VISIBLE);
                    fecha2.setVisibility(View.VISIBLE);
                    etiFecha1.setVisibility(View.VISIBLE);
                    etiFecha2.setVisibility(View.VISIBLE);
                }
            }
        });


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
        String error = "Faltan campos por ingresar.";
        String tipoF, zonaF, fecha1F, fecha2F;
        boolean e = false;
        Filtro filtro = new Filtro();
        tipoF = spinnerMascota.getSelectedItem().toString();
        zonaF = spinnerZona.getSelectedItem().toString();
        fecha1F = fecha1.getText().toString();
        fecha2F = fecha2.getText().toString();

        if (checkBoxZona.isChecked() || checkBoxTipo.isChecked() || checkBoxFecha.isChecked()){
            if (checkBoxZona.isChecked()){
                if (zonaF.equals("Seleccionar")){
                    error += "\n- Seleccione una alcaldía";
                    e = true;
                } else {
                    filtro.setZona(zonaF);
                    cad += "\n- ZONA: " + zonaF;
                }
            }

            if (checkBoxTipo.isChecked()){
                if (tipoF.equals("Seleccionar")){
                    error += "\n- Seleccione un tipo de mascota";
                    e = true;
                } else {
                    filtro.setTipoM(tipoF);
                    cad += "\n- TIPO DE MASCOTA: " + tipoF;
                }
            }

            if (checkBoxFecha.isChecked()){
                if (fecha1F.isEmpty() || fecha2F.isEmpty()){
                    error += "\n- Ingrese un intervalo de fechas";
                    e = true;
                } else {
                    filtro.setFecha1(fecha1F);
                    filtro.setFecha2(fecha2F);
                    cad += "\n- DEL: " + fecha1F + " AL: " + fecha2F;
                }
            }

            if (e == true){
                AlertDialog.Builder alerta = new AlertDialog.Builder(this.getContext());
                alerta.setMessage(error + "\n Por favor intente de nuevo.").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog errorFltr = alerta.create();
                errorFltr.setTitle("Error");
                errorFltr.show();
            } else {
                //Caso exitoso(Debe generar el filtro)
                Toast.makeText(getContext(), cad, Toast.LENGTH_LONG).show();
                Bundle bundle = new Bundle();
                bundle.putSerializable("filtro", filtro);
                Intent i = new Intent().putExtras(bundle);
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
                dismiss();;
            }
        } else {
            Toast.makeText(getContext(), "No seleccionó ningún filtro.", Toast.LENGTH_LONG).show();
            dismiss();
        }


    }
}
