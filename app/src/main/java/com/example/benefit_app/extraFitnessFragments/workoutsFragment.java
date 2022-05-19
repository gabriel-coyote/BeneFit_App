package com.example.benefit_app.extraFitnessFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.benefit_app.MainActivity;
import com.example.benefit_app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


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



//        // Set workouts buttons title
//        openMondaysWorkoutButton.setText("MONDAY: "+MainActivity.mondayWorkoutString_title);
//        openTuesdayWorkoutButton.setText("TUESDAY: "+MainActivity.tuesdayWorkoutString_title);
//        openWednesdayWorkoutButton.setText("WEDNESDAY: "+MainActivity.wednesdayWorkoutString_title);
//        openThursdayWorkoutButton.setText("THURSDAY: "+MainActivity.thursdayWorkoutString_title);
//        openFridayWorkoutButton.setText("FRIDAY: "+MainActivity.fridayWorkoutString_title);
//        openSaturdayWorkoutButton.setText("SATURDAY: "+MainActivity.saturdayWorkoutString_title);
//        openSundayWorkoutButton.setText("SUNDAY: "+MainActivity.sundayWorkoutString_title);

        loadWorkoutInfo();
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

    public void loadWorkoutInfo(){



        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference rootRef  = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userWorkouts = rootRef.child("UsersWorkouts").child(currentUser);


        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("Monday").exists()){
                    // User does have workout created for today in database; so load data into view
                    openMondaysWorkoutButton.setText("MONDAY: "+snapshot.child("Monday").child("workoutTitle").getValue(String.class));
                }else{
                    // User doesn't have a specific workout created for this day
                    openMondaysWorkoutButton.setText("MONDAY: ");
                }

                if(snapshot.child("Tuesday").exists()){
                    // User does have workout created for today in database; so load data into view
                    openTuesdayWorkoutButton.setText("TUESDAY: "+snapshot.child("Tuesday").child("workoutTitle").getValue(String.class));
                }else{
                    // User doesn't have a specific workout created for this day
                    openTuesdayWorkoutButton.setText("TUESDAY: ");
                }

                if(snapshot.child("Wednesday").exists()){
                    // User does have workout created for today in database; so load data into view
                    openWednesdayWorkoutButton.setText("WEDNESDAY: "+snapshot.child("Wednesday").child("workoutTitle").getValue(String.class));
                }else{
                    // User doesn't have a specific workout created for this day
                    openWednesdayWorkoutButton.setText("WEDNESDAY: ");
                }

                if(snapshot.child("Thursday").exists()){
                    // User does have workout created for today in database; so load data into view
                    openThursdayWorkoutButton.setText("THURSDAY: "+snapshot.child("Thursday").child("workoutTitle").getValue(String.class));
                }else{
                    // User doesn't have a specific workout created for this day
                    openThursdayWorkoutButton.setText("THUSDAY: ");
                }


                if(snapshot.child("Friday").exists()){
                    // User does have workout created for today in database; so load data into view
                    openFridayWorkoutButton.setText("FRIDAY: "+snapshot.child("Friday").child("workoutTitle").getValue(String.class));
                }else{
                    // User doesn't have a specific workout created for this day
                    openFridayWorkoutButton.setText("FRIDAY: ");
                }


                if(snapshot.child("Saturday").exists()){
                    // User does have workout created for today in database; so load data into view
                    openSaturdayWorkoutButton.setText("SATURDAY: "+snapshot.child("Saturday").child("workoutTitle").getValue(String.class));
                }else{
                    // User doesn't have a specific workout created for this day
                    openSaturdayWorkoutButton.setText("SATURDAY: ");
                }


                if(snapshot.child("Sunday").exists()){
                    // User does have workout created for today in database; so load data into view
                    openSundayWorkoutButton.setText("SUNDAY: "+snapshot.child("Sunday").child("workoutTitle").getValue(String.class));
                }else{
                    // User doesn't have a specific workout created for this day
                    openSundayWorkoutButton.setText("SUNDAY: ");
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("LoadWorkoutsNAMES_TAG:", error.getMessage()); //Don't ignore errors!

            }
        };

        userWorkouts.addListenerForSingleValueEvent(eventListener);

        // FirebaseDatabase dbRef = new

//        if(){
//            // User does have workout created for today in database; so load data into view
//            createdWorkoutTitle.setText();
//            createdWorkout.setText();
//            createdWorkoutCalories.setText(String.valueOf());
//        }else{
//            // User doesn't have a specific workout created for this day
//            createdWorkoutTitle.setText("");
//            createdWorkout.setText("");
//            createdWorkoutCalories.setText("");
//        }


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