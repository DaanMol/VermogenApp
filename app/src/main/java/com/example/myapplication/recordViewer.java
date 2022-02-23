package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class recordViewer extends AppCompatActivity {

    ListView lv_vermogenList;
    DataBaseHelper dataBaseHelper;
    ArrayAdapter vermogenArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_record_viewer);
        super.onCreate(savedInstanceState);

        dataBaseHelper = new DataBaseHelper(recordViewer.this);
        lv_vermogenList = findViewById(R.id.lv_vermogenList);
        updateListView();

        lv_vermogenList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                meting clickedMeting = (meting) adapterView.getItemAtPosition(i);
                dataBaseHelper.deleteOne(clickedMeting);
                Toast.makeText(recordViewer.this, "deleted meting", Toast.LENGTH_SHORT).show();
                updateListView();
                return false;
            }
        });

//        lv_vermogenList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                meting clickedMeting = (meting) adapterView.getItemAtPosition(i);
//                Intent j = new Intent(this, recordEnd.class);
//                startActivity(j);
//            }
//        });
    }

    private void updateListView() {
        vermogenArrayAdapter = new ArrayAdapter<meting>(recordViewer.this, android.R.layout.simple_list_item_1, dataBaseHelper.getEveryone());
        lv_vermogenList.setAdapter(vermogenArrayAdapter);
    }


}