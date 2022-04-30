package com.example.benefit_app.extraFitnessFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.benefit_app.MainActivity;
import com.example.benefit_app.R;


public class workoutsFragment extends Fragment {



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



        // Set workouts buttons title
        openMondaysWorkoutButton.setText("MONDAY: "+MainActivity.mondayWorkoutString_title);
        openTuesdayWorkoutButton.setText("TUESDAY: "+MainActivity.tuesdayWorkoutString_title);
        openWednesdayWorkoutButton.setText("WEDNESDAY: "+MainActivity.wednesdayWorkoutString_title);
        openThursdayWorkoutButton.setText("THURSDAY: "+MainActivity.thursdayWorkoutString_title);
        openFridayWorkoutButton.setText("FRIDAY: "+MainActivity.fridayWorkoutString_title);
        openSaturdayWorkoutButton.setText("SATURDAY: "+MainActivity.saturdayWorkoutString_title);
        openSundayWorkoutButton.setText("SUNDAY: "+MainActivity.sundayWorkoutString_title);

        //On workout button clicked
        openMondaysWorkoutButton.setOnClickListener(view -> {

            layout_part2_Fragment.selectedWorkout = "Monday";
           loadFragment(MainActivity.fragmentLayout_part2);
        });
        openTuesdayWorkoutButton.setOnClickListener(view -> {
            layout_part2_Fragment.selectedWorkout = "Tuesday";
            loadFragment(MainActivity.fragmentLayout_part2);
        });
        openWednesdayWorkoutButton.setOnClickListener(view -> {
            layout_part2_Fragment.selectedWorkout = "Wednesday";
            loadFragment(MainActivity.fragmentLayout_part2);
        });
        openThursdayWorkoutButton.setOnClickListener(view -> {
            layout_part2_Fragment.selectedWorkout = "Thursday";
            loadFragment(MainActivity.fragmentLayout_part2);
        });
        openFridayWorkoutButton.setOnClickListener(view -> {
            layout_part2_Fragment.selectedWorkout = "Friday";
            loadFragment(MainActivity.fragmentLayout_part2);
        });
        openSaturdayWorkoutButton.setOnClickListener(view -> {
            layout_part2_Fragment.selectedWorkout = "Saturday";
            loadFragment(MainActivity.fragmentLayout_part2);
        });
        openSundayWorkoutButton.setOnClickListener(view -> {
            layout_part2_Fragment.selectedWorkout = "Sunday";
            loadFragment(MainActivity.fragmentLayout_part2);
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

    /* ********************************************************************** */
    private void alertDialog(String text){
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT)
                .show();
    }

}