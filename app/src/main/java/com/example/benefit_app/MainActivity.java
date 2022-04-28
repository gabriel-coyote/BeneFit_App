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
import android.widget.ProgressBar;
import android.widget.TextView;


//import com.example.benefit_app.extraFitnessFragments.layout_part2_Fragment;
import com.example.benefit_app.extraFitnessFragments.caloriesGoalFragment;
import com.example.benefit_app.extraFitnessFragments.stepsGoalFragment;
import com.example.benefit_app.extraFitnessFragments.waterFragment;
import com.example.benefit_app.extraFitnessFragments.workoutsFragment;
import com.example.benefit_app.extraProfileFragments.notificationsFragment;
import com.example.benefit_app.extraFitnessFragments.layout_part2_Fragment;
import com.example.benefit_app.fitnessFragments.displayWorkoutFragment;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements SensorEventListener, StepListener {

    // Our Fitness Page Circular Progress Bar(s)
    public static ProgressBar stepsProgress;
    public static ProgressBar waterProgressBar;
    public static ProgressBar caloriesProgressBar;


    /* **************** STEP COUNTER STUFF - BEGIN **************** */

    public final static Fragment stepCounterFragment = new stepCounterFragment();
    // Holds the global variable of steps counted
    public static int stepsCounted_main;
    private TextView textView;
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private static final String TEXT_NUM_STEPS = "";
    public static int numSteps;
    public static int numStepsGoal;
    public FitnessFragment fitnessFragment;


    public static  TextView TvSteps, TvStepsGoal, TvWater, TvWaterGoal, TvCalories, TvCaloriesGoal;
    public  static View TvSteps_fractionLine, TvWater_fractionLine, TvCalories_fractionLine;
    private Button BtnStart, BtnStop;
    /* **************** STEP COUNTER STUFF - END **************** */

    /* PURPOSE:         Defining our fragments
                        To use in conjunction with loadfragment() */
    final Fragment fragmentGyms = new GymsFragment();
    final Fragment fragmentFood = new FoodFragment();
    final Fragment fragmentProfile = new ProfileFragment();
    public final static Fragment fragmentFitness = new FitnessFragment();

    /* PURPOSE:         Defining our sub/helper fragments ,,,*/
    // s
    public final static Fragment fragmentProfileEdit = new ProfileEditFragment();
    public final static Fragment fragmentWater = new waterFragment();
    public final static Fragment fragmentNotifications = new notificationsFragment();
    public final static Fragment fragmentWorkouts = new workoutsFragment();
    public final static Fragment fragmentLayout_part2 = new layout_part2_Fragment();
    public final static Fragment fragmentDisplayWorkouts = new displayWorkoutFragment();

    //Goals Fragment Setters
    public final static Fragment fragmentStepsGoal = new stepsGoalFragment();
    public final static Fragment fragmentCaloriesGoal = new caloriesGoalFragment();


    // Our navigation Menus
    public static  FloatingActionButton actionButton;
    public static BottomNavigationView bottomNavigationView;


    /* ********************************************************************** */
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Get an instance of the SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);

        // Binding all the progress bars to their layouts' ID
        stepsProgress = findViewById(R.id.StepsProgressBar);
        waterProgressBar = findViewById(R.id.WaterProgressBar);
        caloriesProgressBar = findViewById(R.id.caloriesProgressBar);

        // Excess Binding for progress bars
        TvSteps = findViewById(R.id.StepsProgress_text);
        TvSteps_fractionLine = findViewById(R.id.view);
        TvStepsGoal = findViewById(R.id.StepsGoal_text);

        TvWater = findViewById(R.id.WaterProgress_text);
        TvWater_fractionLine = findViewById(R.id.view2);
        TvWaterGoal = findViewById(R.id.WaterGoal_text);

        TvCalories = findViewById(R.id.caloriesProgress_text);
        TvCalories_fractionLine = findViewById(R.id.view3);
        TvCaloriesGoal = findViewById(R.id.caloriesGoal_text);




        BtnStart = findViewById(R.id.btn_start);
        // Starts Counting steps
        numStepsGoal = 0;
        BtnStart.setOnClickListener(arg0 -> {

            numSteps = 0;
            sensorManager.registerListener(MainActivity.this, accel, SensorManager.SENSOR_DELAY_FASTEST);

        });

        BtnStart.performClick();




        // Take us to our list of workouts on plus button click
        actionButton.setOnClickListener(view -> {
            loadFragment(fragmentWorkouts);

        });

        /* PURPOSE:         To handle the navigation selection on BottomNavigationView
                            Switches fragments/page based on current selected menu item */
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) item -> {

            switch (item.getItemId()){
                case R.id.menu_Geo:
                    loadFragment(fragmentGyms);


                    // Hide fitness steps progress stuff
                    TvSteps.setVisibility(View.INVISIBLE);
                    TvSteps_fractionLine.setVisibility(View.INVISIBLE);
                    TvStepsGoal.setVisibility(View.INVISIBLE);

                    TvWater.setVisibility(View.INVISIBLE);
                    TvWater_fractionLine.setVisibility(View.INVISIBLE);
                    TvWaterGoal.setVisibility(View.INVISIBLE);

                    TvCalories.setVisibility(View.INVISIBLE);
                    TvCalories_fractionLine.setVisibility(View.INVISIBLE);
                    TvCaloriesGoal.setVisibility(View.INVISIBLE);



                    // Our ProgressBar
                    stepsProgress.setVisibility(View.INVISIBLE);
                    waterProgressBar.setVisibility(View.INVISIBLE);
                    caloriesProgressBar.setVisibility(View.INVISIBLE);


                    break;
                case R.id.menu_Food:
                    loadFragment(fragmentFood);


                    // Hide fitness steps progress stuff
                    TvSteps.setVisibility(View.INVISIBLE);
                    TvSteps_fractionLine.setVisibility(View.INVISIBLE);
                    TvStepsGoal.setVisibility(View.INVISIBLE);

                    TvWater.setVisibility(View.INVISIBLE);
                    TvWater_fractionLine.setVisibility(View.INVISIBLE);
                    TvWaterGoal.setVisibility(View.INVISIBLE);

                    TvCalories.setVisibility(View.INVISIBLE);
                    TvCalories_fractionLine.setVisibility(View.INVISIBLE);
                    TvCaloriesGoal.setVisibility(View.INVISIBLE);

                    // Our ProgressBar
                    stepsProgress.setVisibility(View.INVISIBLE);
                    waterProgressBar.setVisibility(View.INVISIBLE);
                    caloriesProgressBar.setVisibility(View.INVISIBLE);


                    break;
                case R.id.menu_Fitness:
                    loadFragment(fragmentFitness);


                    // Show fitness steps progress stuff
                    TvSteps.setVisibility(View.VISIBLE);
                    TvSteps_fractionLine.setVisibility(View.VISIBLE);
                    TvStepsGoal.setVisibility(View.VISIBLE);

                    TvWater.setVisibility(View.VISIBLE);
                    TvWater_fractionLine.setVisibility(View.VISIBLE);
                    TvWaterGoal.setVisibility(View.VISIBLE);

                    TvCalories.setVisibility(View.VISIBLE);
                    TvCalories_fractionLine.setVisibility(View.VISIBLE);
                    TvCaloriesGoal.setVisibility(View.VISIBLE);

                    // Our ProgressBar
                    stepsProgress.setVisibility(View.VISIBLE);
                    waterProgressBar.setVisibility(View.VISIBLE);
                    caloriesProgressBar.setVisibility(View.VISIBLE);


                    break;
                case R.id.menu_Profile:
                    loadFragment(fragmentProfile);


                    // Hide fitness steps progress stuff
                    TvSteps.setVisibility(View.INVISIBLE);
                    TvSteps_fractionLine.setVisibility(View.INVISIBLE);
                    TvStepsGoal.setVisibility(View.INVISIBLE);

                    TvWater.setVisibility(View.INVISIBLE);
                    TvWater_fractionLine.setVisibility(View.INVISIBLE);
                    TvWaterGoal.setVisibility(View.INVISIBLE);

                    TvCalories.setVisibility(View.INVISIBLE);
                    TvCalories_fractionLine.setVisibility(View.INVISIBLE);
                    TvCaloriesGoal.setVisibility(View.INVISIBLE);

                    // Our ProgressBar
                    stepsProgress.setVisibility(View.INVISIBLE);
                    waterProgressBar.setVisibility(View.INVISIBLE);
                    caloriesProgressBar.setVisibility(View.INVISIBLE);


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




        if(numStepsGoal == 0){
            // Don't update the text from "set goals"
        }else{
            numSteps++;
            stepsProgress.setProgress(numSteps);
            //fitnessFragment.setStepsProgressBar(numSteps);
            // numStepsGoal hase been set ; update textview
            TvSteps.setText(TEXT_NUM_STEPS + numSteps);
            TvStepsGoal.setText(String.valueOf(numStepsGoal));

        }

    }



    /* **************** STEP COUNTER STUFF - END **************** */

}