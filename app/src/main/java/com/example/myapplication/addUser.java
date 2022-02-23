package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class addUser extends AppCompatActivity {

    EditText naam, gewicht, datum;
    Spinner oefening;
    Button btn_start;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        // fill spinner
        Spinner dropdown = findViewById(R.id.spinner1);
        String[] items = new String[]{"Voorslaan", "Squat"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        naam = findViewById(R.id.naam);
        gewicht = findViewById(R.id.gewicht);
        datum = findViewById(R.id.datum);
        oefening = findViewById(R.id.spinner1);
        btn_start = findViewById((R.id.btn_start));



    }
    //buttonslisteners
    public void startMeting(View v){

        String infoString;
        infoString = naam.getText().toString() + "," + datum.getText().toString() + "," + oefening.getSelectedItem().toString() + "," + gewicht.getText().toString();

        Intent i = new Intent(this, recordVermogen.class);
        i.putExtra("meting", infoString);
        startActivity(i);
    }
    public void checkBox(View v){

        boolean b = naam.getText().toString().isEmpty() | datum.getText().toString().isEmpty() | gewicht.getText().toString().isEmpty();
        Log.d("iformati", String.valueOf(naam.getText().toString().isEmpty()));
        if (b) {
            Toast.makeText(addUser.this, "vul wat in, nerd", Toast.LENGTH_SHORT).show();
            Log.d("information", "niks ingevuld");
        }
        else {
            builder = new AlertDialog.Builder(this);
            builder.setTitle("Start?")
                    .setMessage("Wil je de meting nu beginnen?")
                    .setCancelable(true)
                    .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startMeting(v);
                        }
                    })
                    .show();
        }
    }
}
