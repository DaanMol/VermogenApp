package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Home");
    }
    public void disable(View v){
        /*View myView = findViewById(R.id.button);
        myView.setEnabled(false);
        Button button = (Button) myView;
        button.setText("New Disabled");*/

        findViewById(R.id.button).setEnabled(false);
        ((Button)findViewById(R.id.button)).setText("new disabled 2");

        /*
        v.setEnabled(false);
        Log.d("success disable", "button disabled");
        Button button = (Button) v;
        button.setText("disabled");
        */

    }
    /*
    public void handleText(View v){
        EditText t = findViewById(R.id.source);
        String input = t.getText().toString();
        ((TextView)findViewById(R.id.output)).setText(input);
        Toast.makeText(this,input, Toast.LENGTH_LONG).show();
    }
    */
    public void launchSettings(View v){
        Intent i = new Intent(this, SettingsActivity.class);
        String message = ((EditText)findViewById(R.id.editText)).getText().toString();
        i.putExtra("cool", message);
        startActivity(i);
    }
    public void addUser(View v){
        Intent i = new Intent(this, addUser.class);
        startActivity(i);
    }
    public void toAccel(View v){
        Intent i = new Intent(this, Accelerometer.class);
        startActivity(i);
    }
    public void viewRecords(View v){
        Intent i = new Intent(this, recordViewer.class);
        startActivity(i);
    }
}