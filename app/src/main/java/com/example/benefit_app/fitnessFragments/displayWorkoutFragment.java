package com.example.benefit_app.fitnessFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.benefit_app.MainActivity;
import com.example.benefit_app.R;

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
        if (viewer != null) {
            if ((ViewGroup)viewer.getParent() != null)
                ((ViewGroup)viewer.getParent()).removeView(viewer);
            return viewer;
        }else {
            viewer = inflater.inflate(R.layout.fragment_display_workout, container, false);
        }
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
               //ToDO: updateworkout title & main workout box
                workout_text.setText(MainActivity.mondayWorkoutString);
                workout_title_text.setText(todaysDay + ": \n  "+MainActivity.mondayWorkoutString_title);
                break;
            case "Tuesday":
                workout_text.setText(MainActivity.tuesdayWorkoutString);
                workout_title_text.setText(todaysDay + ": \n  "+MainActivity.tuesdayWorkoutString_title);
                break;
            case "Wednesday":
                workout_text.setText(MainActivity.wednesdayWorkoutString);
                workout_title_text.setText(todaysDay + ": \n  "+MainActivity.wednesdayWorkoutString_title);
                break;
            case "Thursday":
                workout_text.setText(MainActivity.thursdayWorkoutString);
                workout_title_text.setText(todaysDay + ": \n  "+MainActivity.thursdayWorkoutString_title);
                break;
            case "Friday":
                workout_text.setText(MainActivity.fridayWorkoutString);
                workout_title_text.setText(todaysDay + ": \n  "+MainActivity.fridayWorkoutString_title);
                break;
            case "Saturday":
                workout_text.setText(MainActivity.saturdayWorkoutString);
                workout_title_text.setText(todaysDay + ": \n  "+MainActivity.saturdayWorkoutString_title);
                break;
            case "Sunday":
                workout_text.setText(MainActivity.sundayWorkoutString);
                workout_title_text.setText(todaysDay + ": \n  "+MainActivity.sundayWorkoutString_title);
                break;

            default:

                break;
        }


        return viewer;
    }



    /* ********************************************************************** */
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