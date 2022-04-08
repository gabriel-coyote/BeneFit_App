package com.example.benefit_app.fitnessFragments;

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

import java.text.SimpleDateFormat;
import java.util.Date;


public class workoutsFragment extends Fragment {



    private Button openWorkoutsButton;
    private TextView date_text_workout;
    View viewer;



    public workoutsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewer = inflater.inflate(R.layout.fragment_workouts, container, false);

        Date thisDate = new Date();
        SimpleDateFormat dateForm = new SimpleDateFormat("MM/dd/Y");

        //set date
        date_text_workout = viewer.findViewById(R.id.textViewWorkout2);
        date_text_workout.setText(dateForm.format(thisDate));

        // Opening the specific workout
        openWorkoutsButton = viewer.findViewById(R.id.workouts_openButton);
        openWorkoutsButton.setOnClickListener(view -> loadFragment(MainActivity.fragmentLayout_part2));



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