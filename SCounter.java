package com.example.nicole.counter;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SCounter extends AppCompatActivity implements SensorEventListener, StepListener {
    private TextView TVSteps;
    private Button BtnStart, BtnStop,BtnClear;
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private static final String TEXT_NUM_STEPS = "Number of Steps: ";
    private int numSteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);

        TVSteps = (TextView) findViewById(R.id.TVSteps);
        BtnStart = (Button) findViewById(R.id.BtnStart);
        BtnStop = (Button) findViewById(R.id.BtnStop);
        BtnClear = (Button) findViewById(R.id.BtnClear);

        BtnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TVSteps.setText("");
            }
        });

        BtnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                numSteps = 0;
                sensorManager.registerListener(SCounter.this, accel, SensorManager.SENSOR_DELAY_FASTEST);

            }
        });


        BtnStop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                sensorManager.unregisterListener(SCounter.this);

            }
        });

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    public void step(long timeNs) {
        numSteps++;
        TVSteps.setText(TEXT_NUM_STEPS + numSteps);
    }

    public void CounterGoHome(View view)
    {

        Intent counterhome = new Intent(SCounter.this, SCounter.class);
        startActivity(counterhome);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.navigation_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.profile){

        }
        else if(item.getItemId() == R.id.reminder){
            //Toast.makeText(getApplicationContext(), "Already at Reminder", Toast.LENGTH_LONG).show();
        }
        else if(item.getItemId() == R.id.appointment){
            intent = new Intent(SCounter.this, Appointment.class);
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.faq){

        }
        else if(item.getItemId() == R.id.aboutUs){

        }
        else if(item.getItemId() == R.id.stepCounter){
            Toast.makeText(getApplicationContext(), "Already at StepCounter", Toast.LENGTH_LONG).show();
        }
        else if(item.getItemId() == R.id.logOut){
            Toast.makeText(getApplicationContext(), "You can only logout at MainPage,", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }*/


}