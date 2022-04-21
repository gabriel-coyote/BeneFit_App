package com.example.benefit_app;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


//import com.example.benefit_app.extraFitnessFragments.layout_part2_Fragment;
import com.example.benefit_app.extraFitnessFragments.waterFragment;
import com.example.benefit_app.extraFitnessFragments.workoutsFragment;
import com.example.benefit_app.extraProfileFragments.notificationsFragment;
import com.example.benefit_app.fitnessFragments.layout_part2_Fragment;
import com.example.benefit_app.stepProgress_Testing.SensorFilter;
import com.example.benefit_app.stepProgress_Testing.StepDetector;
import com.example.benefit_app.stepProgress_Testing.StepListener;
import com.example.benefit_app.stepProgress_Testing.stepCounterFragment;
import com.example.benefit_app.ui.fitness.FitnessFragment;
import com.example.benefit_app.ui.food.FoodFragment;
import com.example.benefit_app.ui.gyms.GymsFragment;
import com.example.benefit_app.ui.profile.ProfileEditFragment;
import com.example.benefit_app.ui.profile.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements SensorEventListener, StepListener {



    // TODO: Might need to have all the step counter stuff in its own fragment class under 'stepProgress_Testing'
    /* **************** STEP COUNTER STUFF - BEGIN **************** */

    public final static Fragment stepCounterFragment = new stepCounterFragment();

    // Holds the global variable of steps counted
    public static int stepsCounted_main;

    private TextView textView;
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private static final String TEXT_NUM_STEPS = "Number of Steps: ";
    private int numSteps;


    private TextView TvSteps;
    private Button BtnStart, BtnStop;
    /* **************** STEP COUNTER STUFF - END **************** */


    /* PURPOSE:         Defining our fragments
                        To use in conjunction with loadfragment() */
    final Fragment fragmentGyms = new GymsFragment();
    final Fragment fragmentFood = new FoodFragment();
    public final static Fragment fragmentFitness = new FitnessFragment();
    final Fragment fragmentProfile = new ProfileFragment();

    /* PURPOSE:         Defining our sub/helper fragments */
    // s
    public final static Fragment fragmentProfileEdit = new ProfileEditFragment();
    public final static Fragment fragmentWater = new waterFragment();
    public final static Fragment fragmentNotifications = new notificationsFragment();
    public final static Fragment fragmentWorkouts = new workoutsFragment();
    public final static Fragment fragmentLayout_part2 = new layout_part2_Fragment();


    public static BottomNavigationView bottomNavigationView;
    /* ********************************************************************** */
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* **************** STEP COUNTER STUFF - BEGIN **************** */

        // Get an instance of the SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);

                                        // TODO: Might need to change these ID's
        TvSteps = (TextView) findViewById(R.id.tv_steps);
        BtnStart = (Button) findViewById(R.id.btn_start);
        BtnStop = (Button) findViewById(R.id.btn_stop);



        /*
        BtnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                numSteps = 0;
                sensorManager.registerListener(MainActivity.this, accel, SensorManager.SENSOR_DELAY_FASTEST);

            }
        });


        BtnStop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                sensorManager.unregisterListener(MainActivity.this);

            }
        });

         */
        /* **************** STEP COUNTER STUFF - END **************** */

        /* PURPOSE:         To handle the navigation selection on BottomNavigationView
                            Switches fragments/page based on current selected menu item */
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) item -> {

            switch (item.getItemId()){
                case R.id.menu_Geo:
                    loadFragment(fragmentGyms);
                    break;
                case R.id.menu_Food:
                    loadFragment(fragmentFood);
                    break;
                case R.id.menu_Fitness:
                    loadFragment(fragmentFitness);
                    break;
                case R.id.menu_Profile:
                    loadFragment(fragmentProfile);
                    break;
                default: break;
            }

            return true;
        });


        /* PURPOSE:         To set our default fragment once user logs in */
        bottomNavigationView.setSelectedItemId(R.id.menu_Profile);

    }



    /* ********************************************************************** */
    /* FUNCTION NAME:    loadFragment
       INPUT:            A Fragment
       OUTPUT:           n/a
       PURPOSE:          Switches/loads a fragment into the main fragment container */
    public void loadFragment(Fragment fragment) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment_Container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /* **************** STEP COUNTER STUFF - BEGIN **************** */
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
        TvSteps.setText(TEXT_NUM_STEPS + numSteps);
    }


    /* **************** STEP COUNTER STUFF - END **************** */

}