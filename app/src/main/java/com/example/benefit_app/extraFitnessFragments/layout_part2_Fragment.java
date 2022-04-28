package com.example.benefit_app.extraFitnessFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.benefit_app.MainActivity;
import com.example.benefit_app.R;


public class layout_part2_Fragment extends Fragment {




    private TextView backButton;
    View viewer;



    public layout_part2_Fragment() {
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
        viewer = inflater.inflate(R.layout.fragment_layout_part2, container, false);

        // Opening the specific workout


        backButton = viewer.findViewById(R.id.back_button_calories_part2);
        backButton.setOnClickListener(view -> {getActivity().onBackPressed();

            // Show fitness steps progress stuff
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
            MainActivity.caloriesProgressBar.setVisibility(View.VISIBLE);
        });


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