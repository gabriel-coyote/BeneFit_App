package com.example.benefit_app;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;


import com.example.benefit_app.fitnessFragments.layout_part2_Fragment;
import com.example.benefit_app.fitnessFragments.waterFragment;
import com.example.benefit_app.fitnessFragments.workoutsFragment;
import com.example.benefit_app.profileFragments.notificationsFragment;
import com.example.benefit_app.ui.fitness.FitnessFragment;
import com.example.benefit_app.ui.food.FoodFragment;
import com.example.benefit_app.ui.gyms.GymsFragment;
import com.example.benefit_app.ui.profile.ProfileEditFragment;
import com.example.benefit_app.ui.profile.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    /* PURPOSE:         Defining our fragments
                        To use in conjunction with loadfragment() */
    final Fragment fragmentGyms = new GymsFragment();
    final Fragment fragmentFood = new FoodFragment();
    final Fragment fragmentFitness = new FitnessFragment();
    final Fragment fragmentProfile = new ProfileFragment();

    /* PURPOSE:         Defining our sub/helper fragments */
    public final static Fragment fragmentProfileEdit = new ProfileEditFragment();
    public final static Fragment fragmentWater = new waterFragment();
    public final static Fragment fragmentNotifications = new notificationsFragment();
    public final static Fragment fragmentWorkouts = new workoutsFragment();
    public final static Fragment fragmentLayout_part2 = new layout_part2_Fragment();


    public static BottomNavigationView bottomNavigationView;
    /* ********************************************************************** */
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        /* PURPOSE:         To handle the navigation selection on BottomNavigationView
                            Switches fragments/page based on current selected menu item */
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) item -> {

            switch (item.getItemId()){
                case R.id.menu_Geo:
                    loadFragment(fragmentGyms);
                    break;
                case R.id.menu_Food:
                    loadFragment(fragmentFood);
                    break;
                case R.id.menu_Fitness:
                    loadFragment(fragmentFitness);
                    break;
                case R.id.menu_Profile:
                    loadFragment(fragmentProfile);
                    break;
                default: break;
            }

            return true;
        });


        /* PURPOSE:         To set our default fragment once user logs in */
        bottomNavigationView.setSelectedItemId(R.id.menu_Profile);

    }



    /* ********************************************************************** */
    /* FUNCTION NAME:    loadFragment
       INPUT:            A Fragment
       OUTPUT:           n/a
       PURPOSE:          Switches/loads a fragment into the main fragment container */
    public void loadFragment(Fragment fragment) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment_Container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }




}