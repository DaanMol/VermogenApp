package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class recordEnd extends AppCompatActivity {

    GraphView resultGraph;
    private int graphIntervalCounter = 0;
    private Viewport viewport;
    AlertDialog.Builder builder;
    DataBaseHelper dataBaseHelper = new DataBaseHelper(recordEnd.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_end);

        Intent i = getIntent();
        String message = i.getStringExtra("meting").replace("[", "").replace("]", "");

        Log.d("iformati", message);
        List<String> metingList = new ArrayList<String>(Arrays.asList(message.split(",")));
        String naam = metingList.get(0);
        String datum = metingList.get(1);
        String oefening = metingList.get(2);
        int gewicht = Integer.parseInt(metingList.get(3));

        metingList.remove(0);
        metingList.remove(0);
        metingList.remove(0);
        metingList.remove(0);
        Log.d("iformati", metingList.toString());

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {});

        // graph code
        GraphView graph = (GraphView) findViewById(R.id.resultGraph);
        viewport = graph.getViewport();
        viewport.setScalable(true);
        viewport.setXAxisBoundsManual(true);

        // determine graph size
        int pointsPlotted = metingList.size();
        for (int j = 0; j < pointsPlotted; j++)
            series.appendData(new DataPoint(j, Float.parseFloat(metingList.get(j))), true, pointsPlotted);
        viewport.setMaxX(pointsPlotted);
        viewport.setMinX(0);
        viewport.setMaxY(20);
        graph.addSeries(series);
    }

    public void toMain(View v){
        try {

            // check if meting id already exists
            Intent intent = getIntent();
            String id = intent.getStringExtra("id");
            Log.d("iformati", id);
            List<meting> metingList = dataBaseHelper.getEveryone();
            for (int i = 0; i < metingList.size(); i++){
                if (id.equals(String.valueOf(metingList.get(i).getId()))){
                    Toast.makeText(recordEnd.this, "Meting bestaat al", Toast.LENGTH_SHORT).show();
                    Intent intentioneel = new Intent(this, MainActivity.class);
                    startActivity(intentioneel);
                    finish();
                }
            }
            Log.d("iformati", "BESTAAT AL");
        }
        catch (Exception e) {
            Log.d("iformati", "BESTAAT NOG NIET");
            // save meting
            Intent i = getIntent();
            String message = i.getStringExtra("meting").replace("[", "").replace("]", "");

            List<String> metingList = new ArrayList<String>(Arrays.asList(message.split(",")));
            String naam = metingList.get(0);
            String datum = metingList.get(1);
            String oefening = metingList.get(2);
            int gewicht = Integer.parseInt(metingList.get(3));

            metingList.remove(0);
            metingList.remove(0);
            metingList.remove(0);
            metingList.remove(0);

            String pointList = metingList.toString();

            meting meet = new meting(-1, naam, datum, oefening, gewicht, pointList);
            boolean success = dataBaseHelper.addOne(meet);

            Toast.makeText(recordEnd.this, "Meting opgeslagen", Toast.LENGTH_SHORT).show();
            Intent in = new Intent(this, MainActivity.class);
            startActivity(in);
        }
    }
    public void discard(View v){
        try {
            builder = new AlertDialog.Builder(recordEnd.this);
            builder.setTitle("Delete?")
                    .setMessage("Wil je de meting nu verwijderen??")
                    .setCancelable(true)
                    .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int j) {
                            Intent ids = getIntent();
                            String id = ids.getStringExtra("id");
                            Toast.makeText(recordEnd.this, "deleted meting", Toast.LENGTH_SHORT).show();
                            dataBaseHelper.deleteOne(String.valueOf(id));
                            Intent i = new Intent(recordEnd.this, recordViewer.class);
                            startActivity(i);
                        }
                    })
                    .show();
        }
        catch(Exception e) {
            Toast.makeText(recordEnd.this, "Meting verwijderd", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
    }
}