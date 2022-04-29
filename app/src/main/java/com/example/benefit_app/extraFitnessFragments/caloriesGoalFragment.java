package com.example.benefit_app.extraFitnessFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.benefit_app.MainActivity;
import com.example.benefit_app.R;


public class caloriesGoalFragment extends Fragment {


    private ImageView backButton;

    private TextView caloriesGoal_text;
    private ImageView caloriesPlus, caloriesMinus;
    private int caloriesGoal_int;

    View viewer;
    /* ********************************************************************** */
    public caloriesGoalFragment() {
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
            viewer = inflater.inflate(R.layout.fragment_calories_goal, container, false);
        }


        /*PURPOSE:          To go back on back arrow click */
        backButton = viewer.findViewById(R.id.back_button_calories);
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

        caloriesGoal_int = 0;
        caloriesGoal_text = viewer.findViewById(R.id.todays_calories_goal_text);

        caloriesPlus = viewer.findViewById(R.id.todays_calories_goal_plus);
        caloriesMinus = viewer.findViewById(R.id.todays_calories_goal_minus);

        // On plus or minus image click, adjust our TextView Goal
        caloriesPlus.setOnClickListener(view -> displayCaloriesGoal_plus());
        caloriesMinus.setOnClickListener(view -> displayCaloriesGoal_minus());


        return viewer;
    }



    /* ********************************************************************** */
    private void displayCaloriesGoal_plus(){
        caloriesGoal_int += 100;
        caloriesGoal_text.setText(String.valueOf(caloriesGoal_int));
    }
    /* ********************************************************************** */
    private void displayCaloriesGoal_minus(){

        if(caloriesGoal_int >= 100){
            caloriesGoal_int -= 100;
            caloriesGoal_text.setText(String.valueOf(caloriesGoal_int));
        }else{
            //don't subtract 100 , we don't want negatives
            alertDialog("Can't be negative 👿");
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