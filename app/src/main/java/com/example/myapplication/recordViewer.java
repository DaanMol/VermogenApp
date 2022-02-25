package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class recordViewer extends AppCompatActivity {

    static ListView lv_vermogenList;
    static DataBaseHelper dataBaseHelper;
    static ArrayAdapter vermogenArrayAdapter;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_record_viewer);
        super.onCreate(savedInstanceState);

        dataBaseHelper = new DataBaseHelper(recordViewer.this);
        lv_vermogenList = findViewById(R.id.lv_vermogenList);
        updateListView();

        // verwijder meting
        lv_vermogenList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                return false;
            }
        });

        // bekijke oude meting
        lv_vermogenList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                meting clickedMeting = (meting) adapterView.getItemAtPosition(i);
                String naam = clickedMeting.getNaam();
                String datum = clickedMeting.getDatum();
                String oefening = clickedMeting.getOefening();
                String gewicht = String.valueOf(clickedMeting.getGewicht());
                String pointList = clickedMeting.getPointList();
                Intent intent = new Intent(recordViewer.this, recordEnd.class);
                intent.putExtra("meting", naam+ "," + datum + "," + oefening + "," + gewicht + "," + pointList);
                intent.putExtra("id", String.valueOf(clickedMeting.getId()));
                startActivity(intent);
            }
        });
    }

    public void updateListView() {
        vermogenArrayAdapter = new ArrayAdapter<meting>(recordViewer.this, android.R.layout.simple_list_item_1, dataBaseHelper.getEveryone());
        lv_vermogenList.setAdapter(vermogenArrayAdapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(recordViewer.this, MainActivity.class);
        startActivity(intent);
    }
}