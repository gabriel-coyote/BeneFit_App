package com.example.benefit_app.ui.fitness;


import android.media.Image;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.benefit_app.MainActivity;
import com.example.benefit_app.R;
import com.example.benefit_app.stepProgress_Testing.stepCounterFragment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FitnessFragment extends Fragment {

    private ProgressBar stepsProgressBar;

    private ProgressBar waterProgressBar;
    private ImageView workoutsIcon;
    private ImageView stepsBox;
    private ImageView caloriesBox;
    private ImageView notificationBell;
    private TextView date_text;

    public TextView stepsProgressText;
    View viewer;

    /* ********************************************************************** */
    public FitnessFragment() {
        // Required empty public constructor
    }


    /* ********************************************************************** */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    /* ********************************************************************** */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Date thisDate = new Date();
        SimpleDateFormat dateForm = new SimpleDateFormat("MM/dd/Y");

        /* PURPOSE:             To get our items from the fragment_fitness.xml,
                                Also return viewer to 'inflate' into the Fragment container viewer */
        //View viewer = inflater.inflate(R.layout.fragment_fitness, container, false);
        // IF the viewer doesn't exist then make one
        // Else keep the same viewer
        if (viewer != null) {
            if ((ViewGroup)viewer.getParent() != null)
                ((ViewGroup)viewer.getParent()).removeView(viewer);
            return viewer;
        }else {
            viewer = inflater.inflate(R.layout.fragment_fitness, container, false);
        }
        //For time
        date_text = viewer.findViewById(R.id.date_text);
        date_text.setText(dateForm.format(thisDate));
        // For Water
        waterProgressBar = viewer.findViewById(R.id.WaterProgressBar);
        waterProgressBar.setOnClickListener(view -> {loadFragment(MainActivity.fragmentWater);
            MainActivity.TvSteps.setVisibility(View.INVISIBLE);
            MainActivity.TvSteps_fractionLine.setVisibility(View.INVISIBLE);
            MainActivity.TvStepsGoal.setVisibility(View.INVISIBLE);});

        // Redirects to notifications
        notificationBell = viewer.findViewById(R.id.fitness_notification_bell);
        notificationBell.setOnClickListener(view -> {loadFragment(MainActivity.fragmentNotifications);
            MainActivity.TvSteps.setVisibility(View.INVISIBLE);
            MainActivity.TvSteps_fractionLine.setVisibility(View.INVISIBLE);
            MainActivity.TvStepsGoal.setVisibility(View.INVISIBLE);});

        // Redirects to workouts Page
        workoutsIcon = viewer.findViewById(R.id.StartWorkoutsIcon);
        workoutsIcon.setOnClickListener(view -> {loadFragment(MainActivity.fragmentWorkouts);
            MainActivity.TvSteps.setVisibility(View.INVISIBLE);
            MainActivity.TvSteps_fractionLine.setVisibility(View.INVISIBLE);
            MainActivity.TvStepsGoal.setVisibility(View.INVISIBLE);});


        //stepsProgressBar = viewer.findViewById(R.id.StepsProgressBar);
        //stepsProgressBar.setOnClickListener(view -> loadFragment(MainActivity.stepCounterFragment));

        //stepsProgressText = viewer.findViewById(R.id.stepsCounter_Text_fitnessPage);


        // TODO: GET the steps on the fitness page to display & might have to transfer code from main to here
        //stepsProgressText.setText(MainActivity.numSteps);


        // To go to our goals pages for steps and Calories
        stepsBox = viewer.findViewById(R.id.StepSquare);
        caloriesBox = viewer.findViewById(R.id.CaloriesSquare);

        stepsBox.setOnClickListener(view -> {loadFragment(MainActivity.fragmentStepsGoal);
            MainActivity.TvSteps.setVisibility(View.INVISIBLE);
            MainActivity.TvSteps_fractionLine.setVisibility(View.INVISIBLE);
            MainActivity.TvStepsGoal.setVisibility(View.INVISIBLE);});
        caloriesBox.setOnClickListener(view -> {loadFragment(MainActivity.fragmentCaloriesGoal);
            MainActivity.TvSteps.setVisibility(View.INVISIBLE);
            MainActivity.TvSteps_fractionLine.setVisibility(View.INVISIBLE);
            MainActivity.TvStepsGoal.setVisibility(View.INVISIBLE);});

        return viewer;
    }



    // TODO: Run this method every so often - somehow
    public void setStepsProgressBar() {
        stepsProgressBar.setProgress(MainActivity.stepsCounted_main);
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