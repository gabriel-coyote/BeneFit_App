package com.example.benefit_app.fitness;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.benefit_app.R;

import java.io.IOException;

public class waterFragment extends Fragment{

    private Button bottle_size_plus;
    private Button bottle_size_minus;
    private Button todays_goal_plus;
    private Button todays_goal_minus;
    private Button progress_plus;
    private Button progress_minus;



    /* ********************************************************************** */
    public waterFragment(){
        // Required empty public constructor
    }

    /* ********************************************************************** */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    /* ********************************************************************** */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        /* PURPOSE:             To get our items from the fragment_food.xml,
                                Also return viewer to 'inflate' into the Fragment container viewer */
        View viewer = inflater.inflate(R.layout.fragment_water, container, false);
        bottle_size_plus = viewer.findViewById(R.id.Bottle_size_plus);
        bottle_size_minus = viewer.findViewById(R.id.bottle_size_minus);
        todays_goal_plus = viewer.findViewById(R.id.todays_goal_plus);
        todays_goal_minus = viewer.findViewById(R.id.todays_goal_minus);
        progress_plus = viewer.findViewById(R.id.progress_plus);
        progress_minus = viewer.findViewById(R.id.progress_minus);

        //Bottle size listeners
        bottle_size_minus.setOnClickListener(view -> changeWaterValue());
        bottle_size_plus.setOnClickListener(view -> changeWaterValue());

        //goal listeners
        todays_goal_plus.setOnClickListener(view -> changeWaterValue());
        todays_goal_minus.setOnClickListener(view -> changeWaterValue());

        //progress listeners
        progress_plus.setOnClickListener(view -> changeWaterValue());
        progress_minus.setOnClickListener(view -> changeWaterValue());




        return viewer;
    }

    /* ********************************************************************** */

    private void changeWaterValue(){


    }

}