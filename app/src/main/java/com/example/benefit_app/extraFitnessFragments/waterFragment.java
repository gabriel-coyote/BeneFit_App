package com.example.benefit_app.extraFitnessFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.benefit_app.MainActivity;
import com.example.benefit_app.Objects.DailyWaterLog;
import com.example.benefit_app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class waterFragment extends Fragment{

    private ImageView bottle_size_plus;
    private ImageView bottle_size_minus;
    private ImageView todays_goal_plus;
    private ImageView todays_goal_minus;
    private ImageView progress_plus;
    private ImageView progress_minus;
    private ImageView back_button;

    private TextView todays_progress_text;
    private TextView bottle_size_text;
    private TextView todays_goal_text;

    //golabal variables for counting --------------->
    private int bottle_size_count;
    private int todays_goal_count;
    private int todays_progress_count;

    //buttons------------>
    private Button save_bottle_size_button;
    private Button save_todays_goal_button;
    private Button save_todays_progress_button;

    //Current Date
    LocalDate dateObj = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
    String date = dateObj.format(formatter);


    private View viewer;

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

        if (viewer != null) {
            if ((ViewGroup)viewer.getParent() != null)
                ((ViewGroup)viewer.getParent()).removeView(viewer);
            return viewer;
        } else{
            viewer = inflater.inflate(R.layout.fragment_water, container, false);
        }

        //buttons--------->
        bottle_size_plus = viewer.findViewById(R.id.Bottle_size_plus);
        bottle_size_minus = viewer.findViewById(R.id.bottle_size_minus);
        todays_goal_plus = viewer.findViewById(R.id.todays_goal_plus);
        todays_goal_minus = viewer.findViewById(R.id.todays_goal_minus);
        progress_plus = viewer.findViewById(R.id.progress_plus);
        progress_minus = viewer.findViewById(R.id.progress_minus);

        //back button----->
        back_button = viewer.findViewById(R.id.back_button);
        back_button.setOnClickListener(view -> {getActivity().onBackPressed();
                    MainActivity.TvSteps.setVisibility(View.VISIBLE);
                    MainActivity.TvSteps_fractionLine.setVisibility(View.VISIBLE);
                    MainActivity.TvStepsGoal.setVisibility(View.VISIBLE);
                    MainActivity.stepsProgress.setVisibility(View.VISIBLE);
                    MainActivity.caloriesProgressBar.setVisibility(View.VISIBLE);
                    MainActivity.waterProgressBar.setVisibility(View.VISIBLE);});


        //save buttons------------>
        save_bottle_size_button = viewer.findViewById(R.id.save_bottle_size_button);
        save_todays_goal_button = viewer.findViewById(R.id.save_todays_goal_button);
        save_todays_progress_button = viewer.findViewById(R.id.save_todays_progress_button);

        //text displayed when buttons are pressed in ounces.
        todays_progress_text = viewer.findViewById(R.id.todays_progress_text);
        todays_goal_text = viewer.findViewById(R.id.todays_goal_text);
        bottle_size_text = viewer.findViewById(R.id.bottle_size_text);

        //Bottle size listeners....
        bottle_size_minus.setOnClickListener(view -> changeWaterValue(1,0,0,0,0,0));
        bottle_size_plus.setOnClickListener(view -> changeWaterValue(0,1,0,0,0,0));

        //goal listeners
        todays_goal_plus.setOnClickListener(view -> changeWaterValue(0,0,0,1,0,0));
        todays_goal_minus.setOnClickListener(view -> changeWaterValue(0,0,1,0,0,0));

        //progress listeners
        progress_plus.setOnClickListener(view -> changeWaterValue(0,0,0,0,1,0));
        progress_minus.setOnClickListener(view -> changeWaterValue(0,0,0,0,0,1));


        //Creates a default DailywaterLog for user for todays date in Firebase Database
        createTodaysWaterLog();

        // Saved Buttons Functions on click
        save_bottle_size_button.setOnClickListener(view -> addWater(bottle_size_count));
        save_todays_goal_button.setOnClickListener(view -> setWaterGoal(todays_goal_count));

        return viewer;
    }

    /* ********************************************************************** */

    private void changeWaterValue(int bsm, int bsp, int tgm, int tgp, int pp, int pm){
        String bottle_size_temp;
        String todays_goal_temp;
        String progress_temp;





        if(bsp == 1){
            bottle_size_count += 1;
            bottle_size_temp = Integer.toString(bottle_size_count);
            bottle_size_text.setText(bottle_size_temp+" "+"Bottles");
        }
        else if(bsm == 1){
            bottle_size_count -= 1;
            bottle_size_temp = Integer.toString(bottle_size_count);
            bottle_size_text.setText(bottle_size_temp+" "+"Bottles");
        }
        else if(tgm == 1){
            todays_goal_count -= 1;
            todays_goal_temp = Integer.toString(todays_goal_count);
            todays_goal_text.setText(todays_goal_temp+" "+"Bottles");

        }
        else if(tgp == 1){
            todays_goal_count += 1;
            todays_goal_temp = Integer.toString(todays_goal_count);
            todays_goal_text.setText(todays_goal_temp+" "+"Bottles");
        }
        else if(pp == 1){
            todays_progress_count += 1;
            progress_temp = Integer.toString(todays_progress_count);
            todays_progress_text.setText(progress_temp+" "+"Bottles");
        }
        else if(pm == 1){
            todays_progress_count -= 1;
            progress_temp = Integer.toString(todays_progress_count);
            todays_progress_text.setText(progress_temp+" "+"Bottles");
        }


    }



    /* ********************************************************************** */
    private void addWater(int water_count){


        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentKey = currentUser.getUid()+"_"+date;


        DatabaseReference todaysWaterRef = FirebaseDatabase.getInstance().getReference()
                .child("DailyWaterLog")
                .child(currentKey);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {


                    //String todaysWater_str = snapshot.child("todaysProgress").getValue(String.class);
                    int todaysWater = snapshot.child("todaysProgress").getValue(Integer.class);


                    todaysWater += water_count;


                    //TODO: Fix water added to database
                    todaysWaterRef.child("todaysProgress").setValue(todaysWater);

                    alertDialog("Added "+todaysWater+"Bottles to Log");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("AddWaterUpdate_TAG:", error.getMessage()); //Don't ignore errors!
            }
        };


        todaysWaterRef.addValueEventListener(eventListener);




    }


    /* ********************************************************************** */
    private void setWaterGoal(int water_goal){


        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentKey = currentUser.getUid()+"_"+date;


        DatabaseReference todaysWaterRef = FirebaseDatabase.getInstance().getReference()
                .child("DailyWaterLog")
                .child(currentKey);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {



                    todaysWaterRef.child("todaysGoal").setValue(water_goal);

                    alertDialog("Set "+water_goal+"Bottles Goal to Log");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("AddWaterGoalUpdate_TAG:", error.getMessage()); //Don't ignore errors!
            }
        };


        todaysWaterRef.addValueEventListener(eventListener);




    }
    /* ********************************************************************** */
    private void createTodaysWaterLog(){

        DailyWaterLog waterLog = new DailyWaterLog(0,0);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String key = currentUser.getUid()+"_"+date;


        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userNameRef = rootRef.child("DailyWaterLog").child(key);

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()){
                    // Doesn't exist so create blank one

                    // Adding blank waterlog for today to firebase database.
                    FirebaseDatabase.getInstance().getReference("DailyWaterLog")
                            .child(key).setValue(waterLog);

                    alertDialog("Created Blank Water Log ");
                } else {
                    // they already have a saved today water log
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("BlankWaterLogUpdate_TAG:", error.getMessage()); //Don't ignore errors!
            }
        };

        userNameRef.addValueEventListener(eventListener);




    }


    /* ********************************************************************** */
    private void alertDialog(String text){ Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }
}