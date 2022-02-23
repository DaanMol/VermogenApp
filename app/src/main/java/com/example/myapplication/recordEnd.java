package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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
}