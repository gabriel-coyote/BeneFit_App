package com.example.benefit_app.ui.fitness;


import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.benefit_app.MainActivity;
import com.example.benefit_app.R;
import com.example.benefit_app.stepProgress_Testing.stepCounterFragment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FitnessFragment extends Fragment {

    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int RESULT_OK = 1;
    //private ProgressBar waterProgressBar;
    private ImageView workoutsIcon;
    private ImageView stepsBox, waterBox;
    private ImageView caloriesBox;
    private ImageView notificationBell;
    private TextView date_text;

    //profile picture
    private ImageView imageToUpload;
    private Button change_picture_button;



    View viewer;

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
        Date thisDate = new Date();
        SimpleDateFormat dateForm = new SimpleDateFormat("MM/dd/Y");

        /* PURPOSE:             To get our items from the fragment_fitness.xml,
                                Also return viewer to 'inflate' into the Fragment container viewer */
        //View viewer = inflater.inflate(R.layout.fragment_fitness, container, false);
        // IF the viewer doesn't exist then make one
        // Else keep the same viewer
        if (viewer != null) {
            if ((ViewGroup)viewer.getParent() != null)
                ((ViewGroup)viewer.getParent()).removeView(viewer);
            return viewer;
        }else {
            viewer = inflater.inflate(R.layout.fragment_fitness, container, false);
        }
        //For time
        date_text = viewer.findViewById(R.id.date_text);
        date_text.setText(dateForm.format(thisDate));


        //profile picture
        imageToUpload = viewer.findViewById(R.id.imageToUpload);
        change_picture_button = viewer.findViewById(R.id.change_picture_button);
        change_picture_button.setOnClickListener(view -> {});





        // Redirects to notifications
        notificationBell = viewer.findViewById(R.id.fitness_notification_bell);
        notificationBell.setOnClickListener(view -> {loadFragment(MainActivity.fragmentNotifications);
            // Hide fitness steps progress stuff
            MainActivity.TvSteps.setVisibility(View.INVISIBLE);
            MainActivity.TvSteps_fractionLine.setVisibility(View.INVISIBLE);
            MainActivity.TvStepsGoal.setVisibility(View.INVISIBLE);

            MainActivity.TvWater.setVisibility(View.INVISIBLE);
            MainActivity.TvWater_fractionLine.setVisibility(View.INVISIBLE);
            MainActivity.TvWaterGoal.setVisibility(View.INVISIBLE);

            MainActivity.TvCalories.setVisibility(View.INVISIBLE);
            MainActivity.TvCalories_fractionLine.setVisibility(View.INVISIBLE);
            MainActivity.TvCaloriesGoal.setVisibility(View.INVISIBLE);



            // Our ProgressBar
            MainActivity.stepsProgress.setVisibility(View.INVISIBLE);
            MainActivity.waterProgressBar.setVisibility(View.INVISIBLE);
            MainActivity.caloriesProgressBar.setVisibility(View.INVISIBLE);



        });

        // Redirects to workouts Page
        workoutsIcon = viewer.findViewById(R.id.StartWorkoutsIcon);
        workoutsIcon.setOnClickListener(view -> {



            loadFragment(MainActivity.fragmentDisplayWorkouts);



            // Hide fitness steps progress stuff
            MainActivity.TvSteps.setVisibility(View.INVISIBLE);
            MainActivity.TvSteps_fractionLine.setVisibility(View.INVISIBLE);
            MainActivity.TvStepsGoal.setVisibility(View.INVISIBLE);

            MainActivity.TvWater.setVisibility(View.INVISIBLE);
            MainActivity.TvWater_fractionLine.setVisibility(View.INVISIBLE);
            MainActivity.TvWaterGoal.setVisibility(View.INVISIBLE);

            MainActivity.TvCalories.setVisibility(View.INVISIBLE);
            MainActivity.TvCalories_fractionLine.setVisibility(View.INVISIBLE);
            MainActivity.TvCaloriesGoal.setVisibility(View.INVISIBLE);



            // Our ProgressBar
            MainActivity.stepsProgress.setVisibility(View.INVISIBLE);
            MainActivity.waterProgressBar.setVisibility(View.INVISIBLE);
            MainActivity.caloriesProgressBar.setVisibility(View.INVISIBLE);});


        waterBox = viewer.findViewById(R.id.WaterSquare);
        waterBox.setOnClickListener(view -> {loadFragment(MainActivity.fragmentWater);

            // Hide fitness steps progress stuff
            MainActivity.TvSteps.setVisibility(View.INVISIBLE);
            MainActivity.TvSteps_fractionLine.setVisibility(View.INVISIBLE);
            MainActivity.TvStepsGoal.setVisibility(View.INVISIBLE);

            MainActivity.TvWater.setVisibility(View.INVISIBLE);
            MainActivity.TvWater_fractionLine.setVisibility(View.INVISIBLE);
            MainActivity.TvWaterGoal.setVisibility(View.INVISIBLE);

            MainActivity.TvCalories.setVisibility(View.INVISIBLE);
            MainActivity.TvCalories_fractionLine.setVisibility(View.INVISIBLE);
            MainActivity.TvCaloriesGoal.setVisibility(View.INVISIBLE);



            // Our ProgressBar
            MainActivity.stepsProgress.setVisibility(View.INVISIBLE);
            MainActivity.waterProgressBar.setVisibility(View.INVISIBLE);
            MainActivity.caloriesProgressBar.setVisibility(View.INVISIBLE);});


        // To go to our goals pages for steps and Calories
        stepsBox = viewer.findViewById(R.id.StepSquare);
        caloriesBox = viewer.findViewById(R.id.CaloriesSquare);

        stepsBox.setOnClickListener(view -> {loadFragment(MainActivity.fragmentStepsGoal);
            // Hide fitness steps progress stuff
            MainActivity.TvSteps.setVisibility(View.INVISIBLE);
            MainActivity.TvSteps_fractionLine.setVisibility(View.INVISIBLE);
            MainActivity.TvStepsGoal.setVisibility(View.INVISIBLE);

            MainActivity.TvWater.setVisibility(View.INVISIBLE);
            MainActivity.TvWater_fractionLine.setVisibility(View.INVISIBLE);
            MainActivity.TvWaterGoal.setVisibility(View.INVISIBLE);

            MainActivity.TvCalories.setVisibility(View.INVISIBLE);
            MainActivity.TvCalories_fractionLine.setVisibility(View.INVISIBLE);
            MainActivity.TvCaloriesGoal.setVisibility(View.INVISIBLE);



            // Our ProgressBar
            MainActivity.stepsProgress.setVisibility(View.INVISIBLE);
            MainActivity.waterProgressBar.setVisibility(View.INVISIBLE);
            MainActivity.caloriesProgressBar.setVisibility(View.INVISIBLE);});


        caloriesBox.setOnClickListener(view -> {loadFragment(MainActivity.fragmentCaloriesGoal);
            // Hide fitness steps progress stuff
            MainActivity.TvSteps.setVisibility(View.INVISIBLE);
            MainActivity.TvSteps_fractionLine.setVisibility(View.INVISIBLE);
            MainActivity.TvStepsGoal.setVisibility(View.INVISIBLE);

            MainActivity.TvWater.setVisibility(View.INVISIBLE);
            MainActivity.TvWater_fractionLine.setVisibility(View.INVISIBLE);
            MainActivity.TvWaterGoal.setVisibility(View.INVISIBLE);

            MainActivity.TvCalories.setVisibility(View.INVISIBLE);
            MainActivity.TvCalories_fractionLine.setVisibility(View.INVISIBLE);
            MainActivity.TvCaloriesGoal.setVisibility(View.INVISIBLE);



            // Our ProgressBar
            MainActivity.stepsProgress.setVisibility(View.INVISIBLE);
            MainActivity.waterProgressBar.setVisibility(View.INVISIBLE);
            MainActivity.caloriesProgressBar.setVisibility(View.INVISIBLE);});

        return viewer;
    }



    //For changing picture//////////////////////////////////////////////////////////////
    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.change_picture_button:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                break;

            default:
                break;
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            imageToUpload.setImageURI(selectedImage);
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