package com.example.benefit_app.extraFitnessFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.benefit_app.MainActivity;
import com.example.benefit_app.R;


public class workoutsFragment extends Fragment {


    private TextView workout_text;
    private TextView workout_title_text;

    private Button openMondaysWorkoutButton, openTuesdayWorkoutButton, openWednesdayWorkoutButton, openThursdayWorkoutButton, openFridayWorkoutButton, openSaturdayWorkoutButton, openSundayWorkoutButton;
    View viewer;


    /* ********************************************************************** */

    public workoutsFragment() {
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
        viewer = inflater.inflate(R.layout.fragment_workouts, container, false);


        // Binding the button to fragments_workouts layout
        openMondaysWorkoutButton = viewer.findViewById(R.id.monday_workout);
        openTuesdayWorkoutButton = viewer.findViewById(R.id.tuesday_workout);
        openWednesdayWorkoutButton = viewer.findViewById(R.id.wednesday_workout);
        openThursdayWorkoutButton = viewer.findViewById(R.id.thursday_workout);
        openFridayWorkoutButton = viewer.findViewById(R.id.friday_workout);
        openSaturdayWorkoutButton = viewer.findViewById(R.id.saturday_workout);
        openSundayWorkoutButton = viewer.findViewById(R.id.sunday_workout);

        workout_text = viewer.findViewById(R.id.user_workout_text);
        workout_title_text.findViewById(R.id.title_workout_message);


        //Today's Day


        //On workout button clicked
        openMondaysWorkoutButton.setOnClickListener(view -> {
            loadFragment(MainActivity.fragmentDisplayWorkouts);

        });
        openTuesdayWorkoutButton.setOnClickListener(view -> {
            loadFragment(MainActivity.fragmentDisplayWorkouts);
        });
        openWednesdayWorkoutButton.setOnClickListener(view -> {
            loadFragment(MainActivity.fragmentDisplayWorkouts);

        });
        openThursdayWorkoutButton.setOnClickListener(view -> {
            loadFragment(MainActivity.fragmentDisplayWorkouts);

        });
        openFridayWorkoutButton.setOnClickListener(view -> {
            loadFragment(MainActivity.fragmentDisplayWorkouts);

        });
        openSaturdayWorkoutButton.setOnClickListener(view -> {
            loadFragment(MainActivity.fragmentDisplayWorkouts);

        });
        openSundayWorkoutButton.setOnClickListener(view -> {
            loadFragment(MainActivity.fragmentDisplayWorkouts);

        });



        // Opening the display workout on button clicks.




     return viewer;
    }










    /* ********************************************************************** */
    /* FUNCTION NAME:    loadFragment
       INPUT:            A Fragment
       OUTPUT:           n/a
       PURPOSE:          Switches/loads a fragment into the main fragment container */
    private void loadFragment(Fragment fragment) {

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment_Container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }



}