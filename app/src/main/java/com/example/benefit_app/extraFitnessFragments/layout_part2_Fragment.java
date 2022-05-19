package com.example.benefit_app.extraFitnessFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.benefit_app.MainActivity;
import com.example.benefit_app.Objects.CreatedWorkout;
import com.example.benefit_app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;


public class layout_part2_Fragment extends Fragment {


    //For saving workouts
    public static String selectedWorkout =  "";
    private EditText createdWorkoutTitle, createdWorkout, createdWorkoutCalories;
    private TextView doneButton;

    //private TextView titleText;
    private ImageButton backButton;
    View viewer;


    /* ********************************************************************** */
    public layout_part2_Fragment() {
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
        Date thisDate = new Date();
        SimpleDateFormat dateForm = new SimpleDateFormat("EEEE") ;
        String todaysDAY = dateForm.format(thisDate);;
        // Inflate the layout for this fragment
        // IF the viewer doesn't exist then make one
        // Else keep the same viewer
       // if (viewer != null) {
        //    if ((ViewGroup)viewer.getParent() != null)
           //    ((ViewGroup)viewer.getParent()).removeView(viewer);
           // return viewer;
      //  }else {
            viewer = inflater.inflate(R.layout.fragment_layout_part2, container, false);
       // }

        // Opening the specific workout
        //titleText = viewer.findViewById(R.id.textViewWorkout);

        backButton = viewer.findViewById(R.id.back_button_calories_part2);
        backButton.setOnClickListener(view -> {getActivity().onBackPressed(); });


        // For saving workouts to main string
        createdWorkout = viewer.findViewById(R.id.editTextCreateWorkout);
        createdWorkoutTitle = viewer.findViewById(R.id.editTextCreateTitle);
        createdWorkoutCalories = viewer.findViewById(R.id.editTextCaloriesWorkout);





        switch (selectedWorkout){
            case "Monday":
                loadWorkoutInfo("Monday");
                break;

            case "Tuesday":
                loadWorkoutInfo("Tuesday");
                break;

            case "Wednesday":
                loadWorkoutInfo("Wednesday");
                break;

            case "Thursday":
                loadWorkoutInfo("Thursday");
                break;

            case "Friday":
                loadWorkoutInfo("Friday");
                break;

            case "Saturday":
                loadWorkoutInfo("Saturday");
                break;

            case "Sunday":
                loadWorkoutInfo("Sunday");
                break;

            default:
                break;
        }

        doneButton = viewer.findViewById(R.id.textView5);
        doneButton.setOnClickListener(view ->  {saveWorkout();});




     return viewer;
    }


    /* ********************************************************************** */

    public void loadWorkoutInfo(String WorkoutDay){



        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference rootRef  = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userWorkouts = rootRef.child("UsersWorkouts").child(currentUser).child(WorkoutDay);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    // User does have workout created for today in database; so load data into view
                    createdWorkoutTitle.setText(snapshot.child("workoutTitle").getValue(String.class));
                    createdWorkout.setText(snapshot.child("workoutBody").getValue(String.class));
                    createdWorkoutCalories.setText(String.valueOf(snapshot.child("workoutCalories").getValue(Integer.class)));
                }else{
                    // User doesn't have a specific workout created for this day
                    createdWorkoutTitle.setText("");
                    createdWorkout.setText("");
                    createdWorkoutCalories.setText("");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("LoadWorkouts_TAG:", error.getMessage()); //Don't ignore errors!

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

    public void saveWorkout(){



        String workout_str = createdWorkout.getText().toString();
        String workout_name = createdWorkoutTitle.getText().toString();
        int workout_calories = Integer.parseInt(createdWorkoutCalories.getText().toString());


        // Create a workout obj to push onto database
        CreatedWorkout createdWorkout = new CreatedWorkout(workout_name, workout_str, workout_calories);

        FirebaseDatabase.getInstance().getReference("UsersWorkouts")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(selectedWorkout).setValue(createdWorkout);


        switch (selectedWorkout){
            case "Monday":
                MainActivity.mondayWorkoutString_title = workout_name;
                MainActivity.mondayWorkoutString = workout_str;
                //titleText.setText( "Monday:\n" +workout_name);
                alertDialog("Monday's Workout Saved");
                break;


            case "Tuesday":
                MainActivity.tuesdayWorkoutString_title = workout_name;
                MainActivity.tuesdayWorkoutString = workout_str;
                //titleText.setText(  "Tuesday:\n" +workout_name);
                alertDialog("Tuesday's Workout Saved");
                break;


            case "Wednesday":
                MainActivity.wednesdayWorkoutString_title = workout_name;
                MainActivity.wednesdayWorkoutString = workout_str;
                //titleText.setText(  "Wednesday:\n" +workout_name);
                alertDialog("Wednesday's Workout Saved");
                break;


            case "Thursday":
                MainActivity.thursdayWorkoutString_title = workout_name;
                MainActivity.thursdayWorkoutString = workout_str;
                //titleText.setText(  "Thursday:\n" +workout_name);
                alertDialog("Thursday's Workout Saved");
                break;


            case "Friday":
                MainActivity.fridayWorkoutString_title = workout_name;
                MainActivity.fridayWorkoutString = workout_str;
                //titleText.setText(  "Friday:\n" +workout_name);
                alertDialog("Friday's Workout Saved");
                break;


            case "Saturday":
                MainActivity.saturdayWorkoutString_title = workout_name;
                MainActivity.saturdayWorkoutString = workout_str;
                //titleText.setText( "Saturday:\n" +workout_name);
                alertDialog("Saturday's Workout Saved");
                break;


            case "Sunday":
                MainActivity.sundayWorkoutString_title = workout_name;
                MainActivity.sundayWorkoutString = workout_str;
                //titleText.setText( "Sunday:\n" +workout_name);
                alertDialog("Sunday's Workout Saved");
                break;

            default:
                break;
        }

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