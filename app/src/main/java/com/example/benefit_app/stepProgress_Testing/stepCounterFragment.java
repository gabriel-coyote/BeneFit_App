package com.example.benefit_app.stepProgress_Testing;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.benefit_app.MainActivity;
import com.example.benefit_app.R;
import com.example.benefit_app.ui.fitness.FitnessFragment;


public class stepCounterFragment extends Fragment {

    View viewer;

    // Setting Variable to hold steps and a function to get
    // The variable in FitnessFragment.java
    private int stepsCounted;


    // TODO: Run this method every so often - somehow
    private  void calcSteps(){
        MainActivity.stepsCounted_main = stepsCounted;
    }
    /* ********************************************************************** */
    public stepCounterFragment() {
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
        // IF the viewer doesn't exist then make one
        // Else keep the same viewer
        if (viewer != null) {
            if ((ViewGroup)viewer.getParent() != null)
                ((ViewGroup)viewer.getParent()).removeView(viewer);
            return viewer;
        } else{
            viewer = inflater.inflate(R.layout.step_counter_testing, container, false);
        }






        return  viewer;
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