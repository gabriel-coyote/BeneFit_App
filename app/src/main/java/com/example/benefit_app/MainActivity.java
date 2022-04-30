package com.example.benefit_app;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


//import com.example.benefit_app.extraFitnessFragments.layout_part2_Fragment;
import com.example.benefit_app.Objects.DailyCaloriesLog;
import com.example.benefit_app.Objects.DailyStepLog;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements SensorEventListener, StepListener {


    // For access daily logs of water, calories, steps
    //Current Date
    LocalDate dateObj = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
    String date = dateObj.format(formatter);



    // Our Fitness Page Circular Progress Bar(s)
    public static ProgressBar stepsProgress;
    public static ProgressBar waterProgressBar;
    public static ProgressBar caloriesProgressBar;



    // OUR WORKOUTS STRINGS ; for each day of the week
    public static String mondayWorkoutString = "empty workout :(";
    public static String tuesdayWorkoutString = "empty workout :(";;
    public static String wednesdayWorkoutString= "empty workout :(";;
    public static String thursdayWorkoutString= "empty workout :(";;
    public static String fridayWorkoutString= "empty workout :(";;
    public static String saturdayWorkoutString= "empty workout :(";;
    public static String sundayWorkoutString= "empty workout :(";;

    public static String mondayWorkoutString_title = "blank";
    public static String tuesdayWorkoutString_title = "blank";;
    public static String wednesdayWorkoutString_title= "blank";;
    public static String thursdayWorkoutString_title= "blank";;
    public static String fridayWorkoutString_title= "blank";;
    public static String saturdayWorkoutString_title= "blank";;
    public static String sundayWorkoutString_title= "blank";;

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

            // TODO: update this
            //numSteps = 0;
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            String key = currentUser.getUid()+"_"+date;






            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            DatabaseReference userNameRef = rootRef.child("DailyStepLog").child(key);
            DatabaseReference userNameRefcalories = rootRef.child("DailyCaloriesLog").child(key);


            ValueEventListener eventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(!snapshot.exists()){
                        // Doesn't exist so create blank one

                        DailyStepLog todayStepLog = new DailyStepLog(0,0);



                        // Adding blank dailySteplog & dailyCalories Log for today to firebase database.
                        FirebaseDatabase.getInstance().getReference("DailyStepLog")
                                .child(key).setValue(todayStepLog);

                        alertDialog("Created Blank Step Log ");
                        numSteps = 0;
                    } else {
                        // they already have a saved today step log
                        //alertDialog("You have a step Log already created for today");

                       // Load todaysProgress into numsteps.
                       numSteps = snapshot.child("todaysProgress").getValue(Integer.class) ;
                       numStepsGoal = snapshot.child("todaysGoal").getValue(Integer.class);
                       MainActivity.stepsProgress.setMax(numStepsGoal);

                       if(MainActivity.stepsProgress.getMax() != 0){
                           // Already Set Goal so show textview
                           MainActivity.TvSteps.setText(String.valueOf(snapshot.child("todaysProgress").getValue(Integer.class)));
                           MainActivity.TvStepsGoal.setText(String.valueOf(snapshot.child("todaysGoal").getValue(Integer.class)));

                       }
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("LoadingTodayStepProgress_TAG:", error.getMessage()); //Don't ignore errors!
                }
            };

            ValueEventListener eventListener2 = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(!snapshot.exists()){

                        DailyCaloriesLog todayCaloriesLog = new DailyCaloriesLog(0,0);
                        FirebaseDatabase.getInstance().getReference("DailyCaloriesLog")
                                .child(key).setValue(todayCaloriesLog);

                    } else {
                        MainActivity.caloriesProgressBar.setMax(snapshot.child("todaysGoal").getValue(Integer.class));

                        if(MainActivity.caloriesProgressBar.getMax() != 0){
                            //Goal has been set so show TextViews
                            MainActivity.TvCalories.setText(String.valueOf(snapshot.child("todaysProgress").getValue(Integer.class)));
                            MainActivity.TvCaloriesGoal.setText(String.valueOf(snapshot.child("todaysGoal").getValue(Integer.class)));


                        }
//

                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("LoadingTodayStepProgress_TAG:", error.getMessage()); //Don't ignore errors!
                }
            };


            userNameRef.addValueEventListener(eventListener);
            userNameRefcalories.addValueEventListener(eventListener2);


            sensorManager.registerListener(MainActivity.this, accel, SensorManager.SENSOR_DELAY_FASTEST);

        });

        BtnStart.performClick();



        actionButton = findViewById(R.id.actionButton);
        // Take us to our list of workouts on plus button click
        actionButton.setOnClickListener(view -> {

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

        double temp = 1;
        int newInt;

//
//        if(numStepsGoal == 0){
//            // Don't update the text from "set goals"
//        }else{
            numSteps++;
            temp = numSteps * .04;
            newInt = (int)temp;
            caloriesProgressBar.setProgress(newInt);
            stepsProgress.setProgress(numSteps);

            //fitnessFragment.setStepsProgressBar(numSteps);
            // numStepsGoal hase been set ; update textview
            TvSteps.setText(TEXT_NUM_STEPS + numSteps);
            if(numStepsGoal == 0){
                TvStepsGoal.setText("Set Goal!");
            }else {
                TvStepsGoal.setText(String.valueOf(numStepsGoal));
            }

            // Saving steps progress to todays' daily step log
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            String key = currentUser.getUid()+"_"+date;


            // Adding user info to Firebase database
            FirebaseDatabase.getInstance().getReference("DailyStepLog")
                    .child(key).child("todaysProgress")
                    .setValue(numSteps).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        //alertDialog("Saving Complete 😌");

                    }else if(!task.isSuccessful()){
                        //alertDialog("Saving Failed... :(");
                    }
                }
            });

        // Adding user info to Firebase database
        FirebaseDatabase.getInstance().getReference("DailyCaloriesLog")
                .child(key).child("todaysProgress")
                .setValue(newInt).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    //alertDialog("Saving Complete 😌");

                }else if(!task.isSuccessful()){
                    //alertDialog("Saving Failed... :(");
                }
            }
        });


        if(MainActivity.caloriesProgressBar.getMax() == 0){
            //Calories goal hasn't been sent; don't change textview
        }else{

            DatabaseReference caloriesDB = FirebaseDatabase.getInstance().getReference().child("DailyCaloriesLog").child(key);

            ValueEventListener updatingTextViewListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        MainActivity.TvCalories.setText(String.valueOf(snapshot.child("todaysProgress").getValue(Integer.class)));
                        MainActivity.TvCaloriesGoal.setText(String.valueOf(snapshot.child("todaysGoal").getValue(Integer.class)));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            };

        }
//        }

    }
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            Uri selectedImaage = data.getData();
            imageToUpload.setImageURI(selectedImaage);
        }
    }

*/

    /* **************** STEP COUNTER STUFF - END **************** */

//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
//            Uri selectedImaage = data.getData();
//            imageToUpload.setImageURI(selectedImaage);
//        }
// }



    /* ********************************************************************** */




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


    /* ********************************************************************** */
    private void alertDialog(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT)
                .show();
    }
}