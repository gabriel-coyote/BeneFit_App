package com.example.benefit_app.fitness;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.benefit_app.Objects.DailyWaterLog;
import com.example.benefit_app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class waterFragment extends Fragment{

    private Button bottle_size_plus;
    private Button bottle_size_minus;
    private Button todays_goal_plus;
    private Button todays_goal_minus;
    private Button progress_plus;
    private Button progress_minus;


    LocalDate today;

    // Accessing Firebase Database
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference userReference;



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
        bottle_size_minus.setOnClickListener(view -> addTodaysWater());
        bottle_size_plus.setOnClickListener(view -> addTodaysWater());

        //goal listeners
        todays_goal_plus.setOnClickListener(view -> addTodaysWater());
        todays_goal_minus.setOnClickListener(view -> addTodaysWater());

        //progress listeners
        progress_plus.setOnClickListener(view -> addTodaysWater());
        progress_minus.setOnClickListener(view -> addTodaysWater());


        //TODO: Setting the middle numbers as integer? to increment and decrement
        // jshdfjkh


        return viewer;
    }

    /* ********************************************************************** */

    private void addTodaysWater(){



        //String todaysAddition =

        LocalDate dateObj = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String date = dateObj.format(formatter);

        DailyWaterLog todaysWaterLog = new DailyWaterLog();

        /* TODO: Making two functions - one to set todays goal, another to add water to todays progress
        FirebaseDatabase.getInstance().getReference("DailyWaterLog")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()+"_"+date)
                .child("todayProgress")
                .setValue()


                */
    }

}