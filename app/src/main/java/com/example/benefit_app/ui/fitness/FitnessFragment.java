package com.example.benefit_app.ui.fitness;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.benefit_app.MainActivity;
import com.example.benefit_app.R;


public class FitnessFragment extends Fragment {


    private ProgressBar waterProgressBar;


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


        /* PURPOSE:             To get our items from the fragment_fitness.xml,
                                Also return viewer to 'inflate' into the Fragment container viewer */
        View viewer = inflater.inflate(R.layout.fragment_fitness, container, false);


        // For Water
        waterProgressBar = viewer.findViewById(R.id.WaterProgressBar);
        waterProgressBar.setOnClickListener(view -> loadFragment(MainActivity.fragmentWater));




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