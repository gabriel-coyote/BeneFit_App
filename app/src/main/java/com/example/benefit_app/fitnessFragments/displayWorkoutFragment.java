package com.example.benefit_app.fitnessFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.benefit_app.MainActivity;
import com.example.benefit_app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;


public class displayWorkoutFragment extends Fragment {

    private TextView title_workout_message;
    private TextView user_workout_text;
    private ImageView back_button;
    private TextView workout_text;
    private TextView workout_title_text;
    private String date_text;


    View viewer;
    /* ********************************************************************** */
    public displayWorkoutFragment() {
        // Required empty public constructor
    }
    /* ********************************************************************** */
    public static displayWorkoutFragment newInstance(String param1, String param2) {
        displayWorkoutFragment fragment = new displayWorkoutFragment();
        Bundle args = new Bundle();

        return fragment;
    }
    /* ********************************************************************** */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }
    /* ********************************************************************** */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Date thisDate = new Date();
        SimpleDateFormat dateForm = new SimpleDateFormat("EEEE");
        // Inflate the layout for this fragment
        // IF the viewer doesn't exist then make one
        // Else keep the same viewer
       // if (viewer != null) {
         //   if ((ViewGroup)viewer.getParent() != null)
         //       ((ViewGroup)viewer.getParent()).removeView(viewer);
        //    return viewer;
        //}else {
            viewer = inflater.inflate(R.layout.fragment_display_workout, container, false);
        //}
        //Title of workout and user created workout.
        workout_title_text = viewer.findViewById(R.id.title_workout_message);
        workout_text = viewer.findViewById(R.id.user_workout_text);
        //back button
        back_button = viewer.findViewById(R.id.display_workout_back);
        back_button.setOnClickListener(view -> {loadFragment(MainActivity.fragmentFitness);

            // Hide fitness steps progress stuff
            MainActivity.TvSteps.setVisibility(View.VISIBLE);
            MainActivity.TvSteps_fractionLine.setVisibility(View.VISIBLE);
            MainActivity.TvStepsGoal.setVisibility(View.VISIBLE);

            MainActivity.TvWater.setVisibility(View.VISIBLE);
            MainActivity.TvWater_fractionLine.setVisibility(View.VISIBLE);
            MainActivity.TvWaterGoal.setVisibility(View.VISIBLE);

            MainActivity.TvCalories.setVisibility(View.VISIBLE);
            MainActivity.TvCalories_fractionLine.setVisibility(View.VISIBLE);
            MainActivity.TvCaloriesGoal.setVisibility(View.VISIBLE);

            workout_text = viewer.findViewById(R.id.user_workout_text);
            workout_title_text.findViewById(R.id.title_workout_message);


            // Our ProgressBar
            MainActivity.stepsProgress.setVisibility(View.VISIBLE);
            MainActivity.waterProgressBar.setVisibility(View.VISIBLE);
            MainActivity.caloriesProgressBar.setVisibility(View.VISIBLE);});


        //date_text = dateForm.format(thisDate);

        String todaysDay = dateForm.format(thisDate);

        switch (todaysDay){
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

                    workout_title_text.setText(snapshot.child("workoutTitle").getValue(String.class));
                    workout_text.setText(snapshot.child("workoutBody").getValue(String.class));
                }else{
                    // User doesn't have a specific workout created for this day

                    workout_title_text.setText(WorkoutDay+": blank");
                    workout_text.setText("No workout created for this day; \nClick create");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("LoadWorkouts_TAG:", error.getMessage()); //Don't ignore errors!

            }
        };

        userWorkouts.addListenerForSingleValueEvent(eventListener);




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