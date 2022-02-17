package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class recordViewer extends AppCompatActivity {

    RecyclerView recordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        recordView = (RecyclerView) findViewById(R.id.recordView);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_viewer);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(recordViewer.this);
        List<meting> everyone = dataBaseHelper.getEveryone();
        Log.d("everyone", String.valueOf(everyone));
        ArrayAdapter vermogenArrayAdapter = new ArrayAdapter<meting>(recordViewer.this, android.R.layout.simple_list_item_1, everyone);
        //recordView.setAdapter(vermogenArrayAdapter);

    }
}