package com.example.benefit_app.extraFitnessFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.benefit_app.MainActivity;
import com.example.benefit_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class stepsGoalFragment extends Fragment {

    private ProgressBar stepsProgressBar;

    // For access daily logs of water, calories, steps
    //Current Date
    LocalDate dateObj = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
    String date = dateObj.format(formatter);


    private ImageView backButton;

    private TextView stepsGoal_text;
    private ImageView stepsPlus, stepsMinus;
    private Button saveStepsButton;
    private int stepsGoal_int;

    View viewer;
    /* ********************************************************************** */
    public stepsGoalFragment() {
        // Required empty public constructor
    }


    /* ********************************************************************** */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    /* ********************************************************************** */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // IF the viewer doesn't exist then make one
        // Else keep the same viewer
        if (viewer != null) {
            if ((ViewGroup)viewer.getParent() != null)
                ((ViewGroup)viewer.getParent()).removeView(viewer);
            return viewer;
        }else {
            viewer = inflater.inflate(R.layout.fragment_steps_goal, container, false);
        }

//        stepsProgressBar = (ProgressBar) viewer.findViewById(R.id.StepsProgressBar);
//        stepsProgressBar.setProgress(6);

        /*PURPOSE:          To go back on back arrow click */
        backButton = viewer.findViewById(R.id.back_button_steps);
        backButton.setOnClickListener(view -> {getActivity().onBackPressed();
            // Hide fitness steps progress stuff
            MainActivity.TvSteps.setVisibility(View.VISIBLE);
            MainActivity.TvSteps_fractionLine.setVisibility(View.VISIBLE);
            MainActivity.TvStepsGoal.setVisibility(View.VISIBLE);

            MainActivity.TvWater.setVisibility(View.VISIBLE);
            MainActivity.TvWater_fractionLine.setVisibility(View.VISIBLE);
            MainActivity.TvWaterGoal.setVisibility(View.VISIBLE);

            MainActivity.TvCalories.setVisibility(View.VISIBLE);
            MainActivity.TvCalories_fractionLine.setVisibility(View.VISIBLE);
            MainActivity.TvCaloriesGoal.setVisibility(View.VISIBLE);



            // Our ProgressBar
            MainActivity.stepsProgress.setVisibility(View.VISIBLE);
            MainActivity.waterProgressBar.setVisibility(View.VISIBLE);
            MainActivity.caloriesProgressBar.setVisibility(View.VISIBLE);});


        stepsGoal_int = 0;
        stepsGoal_text = viewer.findViewById(R.id.todays_step_goal_text);

        stepsPlus = viewer.findViewById(R.id.todays_step_goal_plus);
        stepsMinus = viewer.findViewById(R.id.todays_step_goal_minus);

        // On plus or minus image click, adjust our TextView Goal
        stepsPlus.setOnClickListener(view -> displayStepsGoal_plus());
        stepsMinus.setOnClickListener(view -> displayStepsGoal_minus());


        saveStepsButton = viewer.findViewById(R.id.save_step_goal_button);
        saveStepsButton.setOnClickListener(view -> {saveStepsGoal();});

        return viewer;
    }


    /* ********************************************************************** */
    private void saveStepsGoal(){

        MainActivity.numStepsGoal = stepsGoal_int;
        alertDialog("Saved steps Goal: " + String.valueOf(stepsGoal_int));
        MainActivity.stepsProgress.setMax(stepsGoal_int);




        // Saving steps progress to todays' daily step log
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String key = currentUser.getUid()+"_"+date;


        // Adding user info to Firebase database
        FirebaseDatabase.getInstance().getReference("DailyStepLog")
                .child(key).child("todaysGoal")
                .setValue(stepsGoal_int).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    //alertDialog("Saving Complete ????");

                }else if(!task.isSuccessful()){
                    //alertDialog("Saving Failed... :(");
                }
            }
        });




    }
    /* ********************************************************************** */
    private void displayStepsGoal_plus(){
        stepsGoal_int += 100;
        stepsGoal_text.setText(String.valueOf(stepsGoal_int));
    }
    /* ********************************************************************** */
    private void displayStepsGoal_minus(){

        if(stepsGoal_int >= 100){
            stepsGoal_int -= 100;
            stepsGoal_text.setText(String.valueOf(stepsGoal_int));
        }else{
            //don't subtract 100 , we don't want negatives
            alertDialog("Can't be negative ????");
        }
    }
    /* ********************************************************************** */
    /* FUNCTION NAME:    alertDialog
       INPUT:            A String
       OUTPUT:           n/a
       PURPOSE:          To make the code more readable,
                         outputs an alert style text box    */
    private void alertDialog(String text){
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

}