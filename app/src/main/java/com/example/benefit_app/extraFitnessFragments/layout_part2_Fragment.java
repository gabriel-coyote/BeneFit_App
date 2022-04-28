package com.example.benefit_app.extraFitnessFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.benefit_app.MainActivity;
import com.example.benefit_app.R;

import java.text.SimpleDateFormat;
import java.util.Date;


public class layout_part2_Fragment extends Fragment {


    //For saving workouts
    private EditText createdWorkoutTitle, createdWorkout;
    private TextView doneButton;

    private TextView date_text;
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
        SimpleDateFormat dateForm = new SimpleDateFormat("EEEE");
        // Inflate the layout for this fragment
        // IF the viewer doesn't exist then make one
        // Else keep the same viewer
        if (viewer != null) {
            if ((ViewGroup)viewer.getParent() != null)
                ((ViewGroup)viewer.getParent()).removeView(viewer);
            return viewer;
        }else {
            viewer = inflater.inflate(R.layout.fragment_layout_part2, container, false);
        }
        date_text = viewer.findViewById(R.id.textViewWorkout);
        date_text.setText(dateForm.format(thisDate));
        // Opening the specific workout


        backButton = viewer.findViewById(R.id.back_button_calories_part2);
        backButton.setOnClickListener(view -> {getActivity().onBackPressed(); });


        // For saving workouts to main string
        createdWorkout = viewer.findViewById(R.id.editTextCreateWorkout);
        createdWorkoutTitle = viewer.findViewById(R.id.editTextCreateTitle);
        doneButton = viewer.findViewById(R.id.textView5);
        doneButton.setOnClickListener(view ->  {saveWorkout();});




     return viewer;
    }


    /* ********************************************************************** */

    public void saveWorkout(){

        String todaysDAY;

        String workout_str = createdWorkout.getText().toString();

        switch (""){
            case "Monday":
                MainActivity.mondayWorkoutString = workout_str;
                break;


            case "Tuesday":
                MainActivity.tuesdayWorkoutString = workout_str;
                break;


            case "Wednesday":
                MainActivity.wednesdayWorkoutString = workout_str;
                break;


            case "Thursday":
                MainActivity.thursdayWorkoutString = workout_str;
                break;


            case "Friday":
                MainActivity.fridayWorkoutString = workout_str;
                break;


            case "Saturday":
                MainActivity.saturdayWorkoutString = workout_str;
                break;


            case "Sunday":
                MainActivity.sundayWorkoutString = workout_str;
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
}