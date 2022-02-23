package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class recordVermogen extends AppCompatActivity {

    TextView txt_currentAccel, txt_prevAccel, txt_acceleration;
    ProgressBar prog_bar;
    List<Double> pointList = new ArrayList<>(Collections.emptyList());

    // define sensor variables
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    private double accelerationCurrentValue;
    private double accelerationPreviousValue;

    private int pointsPlotted = 5;
    private int graphIntervalCounter = 0;

    private Viewport viewport;

    LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
            new DataPoint(0, 1),
            new DataPoint(1, 5),
            new DataPoint(2, 3),
            new DataPoint(3, 2),
            new DataPoint(4, 6)
    });

    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            accelerationCurrentValue = Math.sqrt( ( x*x+y*y+z*z ) );
            pointList.add(accelerationCurrentValue);
            double changeInAcceleration = Math.abs(accelerationCurrentValue - accelerationPreviousValue);
            accelerationPreviousValue = accelerationCurrentValue;

            // update text fields
            txt_currentAccel.setText("Current = " + (int) accelerationCurrentValue);
            txt_prevAccel.setText("Prev = " + (int) accelerationPreviousValue);
            txt_acceleration.setText("Acceleration change = " + (int) changeInAcceleration);

            prog_bar.setProgress((int) changeInAcceleration);

            //change colors
            if (changeInAcceleration > 14) {
                txt_acceleration.setBackgroundColor(Color.RED);
            }
            else if (changeInAcceleration > 5){
                txt_acceleration.setBackgroundColor(Color.parseColor("#fcad03"));
            }
            else if (changeInAcceleration > 2){
                txt_acceleration.setBackgroundColor(Color.YELLOW);
            }
            else {
                txt_acceleration.setBackgroundColor(getResources().getColor(com.google.android.material.R.color.design_default_color_background));
            }

            // update the graph
            pointsPlotted++;

            if (pointsPlotted> 1000){
                pointsPlotted = 1;
                series.resetData( new DataPoint[] { new DataPoint(1,0) });
            }

            series.appendData(new DataPoint(pointsPlotted, changeInAcceleration), true, pointsPlotted);
            viewport.setMaxX(pointsPlotted);
            viewport.setMinX(pointsPlotted - 200);
            viewport.setMaxY(20);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_vermogen);

        txt_acceleration = findViewById(R.id.txt_Accel);
        txt_currentAccel = findViewById(R.id.txt_currentAccel);
        txt_prevAccel = findViewById(R.id.txt_prevAccel);

        prog_bar = findViewById(R.id.prog_bar);

        // init sensor variables
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        // graph code
        GraphView graph = (GraphView) findViewById(R.id.graph);
        viewport = graph.getViewport();
        viewport.setScalable(true);
        viewport.setXAxisBoundsManual(true);
        graph.addSeries(series);
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(sensorEventListener, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(sensorEventListener);
    }

    public void saveMeting(View v){

        DataBaseHelper dataBaseHelper = new DataBaseHelper(recordVermogen.this);

        // get user info
        Intent i = getIntent();
        String message = i.getStringExtra("meting");
        Log.d("iformati", message);
        List<String> metingList = Arrays.asList(message.split(","));
        String naam = metingList.get(0);
        String datum = metingList.get(1);
        String oefening = metingList.get(2);
        int gewicht = Integer.parseInt(metingList.get(3));

        // save record to db
        meting meet = new meting(-1, naam, datum, oefening, gewicht, pointList.toString());
        boolean success = dataBaseHelper.addOne(meet);
        Log.d("iformati", meet.toString());

        Intent j = new Intent(this, recordEnd.class);
        j.putExtra("meting", message + "," + pointList.toString());
        startActivity(j);
    }

}

