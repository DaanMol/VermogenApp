package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class addUser extends AppCompatActivity {

    EditText naam, gewicht, datum;
    Spinner oefening;
    Button btn_start;

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

        meting meet;
        try {
            meet = new meting(-1, naam.getText().toString(), datum.getText().toString(), oefening.getSelectedItem().toString(), Integer.parseInt(gewicht.getText().toString()));
            Log.d("information", meet.toString());
        }
        catch (Exception e){
            Toast.makeText(addUser.this, "vul wat in, nerd", Toast.LENGTH_SHORT).show();
            meet = new meting(-1, "error", "error", "error", 0);
            Log.d("information", "niks ingevuld");
        }

        DataBaseHelper dataBaseHelper = new DataBaseHelper(addUser.this);

        boolean success = dataBaseHelper.addOne(meet);

        Log.d("information", String.valueOf(success));
    }
}